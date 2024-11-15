package codegen;

import asm.*;

import java.util.ArrayList;
import java.util.HashMap;

class PatchLocation {
    public final String functionName;
    public final int argumentCount;
    public final int ldiLocation;

    public PatchLocation(String functionName, int argumentCount, int ldiLocation) {
        this.functionName = functionName;
        this.argumentCount = argumentCount;
        this.ldiLocation = ldiLocation;
    }
}

class FunctionDesc {
    public final int functionIndex;
    public final int argumentCount;

    public FunctionDesc(int functionIndex, int argumentCount) {
        this.functionIndex = functionIndex;
        this.argumentCount = argumentCount;
    }
}

public class CodeGenerationVisitor implements ProgramVisitor {
    /*
     * Liste für die generierten Instruktionen. Eine ArrayList verhält sich wie ein
     * Array, welches wachsen kann.
     */
    private ArrayList<Instruction> instructions = new ArrayList<Instruction>();
    /*
     * HashMap für lokale Variablen. Man beachte, dass es lokale Variablen nur als
     * Funktionsparameter und am Anfang von Funktionen gibt; daher gibt es nur genau
     * einen Scope.
     */
    private HashMap<String, Parameter> locals = new HashMap<String, Parameter>();
    /*
     * Frame-Zellen der aktuellen Funktion. Wird für den Rücksprung benötigt.
     */
    private int frameCells = 0;
    /*
     * Funktionen, die bereits assembliert wurden. Wir merken uns alle Funktionen,
     * sodass wir am Ende die Call-Sites patchen können.
     */
    private HashMap<String, FunctionDesc> functions = new HashMap<>();
    /*
     * Call-Sites; Funktionsaufrufe müssen ganz am Ende generiert werden, nämlich
     * dann, wenn die Funktionen schon bekannte Adressen haben.
     */
    private ArrayList<PatchLocation> patchLocations = new ArrayList<>();

    public Instruction[] getProgram() {
        System.out.println(new AsmFormatVisitor(instructions.toArray(new Instruction[0])).getFormattedCode());
        return instructions.toArray(new Instruction[0]);
    }

    private int add(Instruction instruction) {
        int index = instructions.size();
        instructions.add(instruction);
        return index;
    }

    private int addDummy() {
        return add(new Nop());
    }

    /*
     * Expression
     */

    @Override
    public void visit(Number number) {
        add(new Ldi(number.getValue()));
    }

    @Override
    public void visit(Variable variable) {
        if (!locals.containsKey(variable.getName()))
            throw new UnknownVariableException(variable.getName());
        int variableLocation = locals.get(variable.getName()).indexinmemory;
        add(new Lfs(variableLocation));
    }

    @Override
    public void visit(Unary unary) {
        switch (unary.getOperator()) {
            case Minus:
                unary.getOperand().accept(this);
                add(new Ldi(0));
                add(new Sub());
                break;
        }
    }

    @Override
    public void visit(Binary binary) {
        binary.getRhs().accept(this);
        binary.getLhs().accept(this);
        switch (binary.getOperator()) {
            case Minus:
                add(new Sub());
                break;
            case Plus:
                add(new Add());
                break;
            case MultiplicationOperator:
                add(new Mul());
                break;
            case DivisionOperator:
                add(new Div());
                break;
            case Modulo:
                add(new Mod());
                break;
        }
    }

    @Override
    public void visit(Call call) {

        if(!call.isFork()) {
            for (Expression e : call.getArguments())
                e.accept(this);
            int patchLocation = addDummy();
            // Wir können die Instruktion zum Laden der Adresse hier noch nicht generieren;
            // die
            // Funktion wird evtl. erst später assembliert.
            patchLocations.add(new PatchLocation(call.getFunctionName(), call.getArguments().length, patchLocation));
            add(new asm.Call(call.getArguments().length));
        } else {

            for(Expression e : call.getArguments()){
                e.accept(this);
            }
            int patchLocation = addDummy();
            // Wir können die Instruktion zum Laden der Adresse hier noch nicht generieren;
            // die
            // Funktion wird evtl. erst später assembliert.
            patchLocations.add(new PatchLocation(call.getFunctionName(), call.getArguments().length, patchLocation));
            add(new asm.Fork(call.getArguments().length));

//TODO: do somehting
        }
    }

    /*
     * Statement
     */

    @Override
    public void visit(Read read) {
        add(new asm.In());
    }

    @Override
    public void visit(Write write) {
        write.getExpression().accept(this);
        add(new asm.Out());
        add(new Ldi(0));
    }

    @Override
    public void visit(Assignment assignment) {
        assignment.getExpression().accept(this);
        Parameter location = locals.get(assignment.getName());
        if (location == null)
            throw new UnknownVariableException(assignment.getName());
        add(new Sts(location.indexinmemory));
    }

    @Override
    public void visit(Composite composite) {
        for (Statement s : composite.getStatements())
            s.accept(this);
    }

    @Override
    public void visit(IfThenElse ifThenElse) {
        ifThenElse.getCond().accept(this);
        int jumpToElseAddress = addDummy();
        ifThenElse.getElseBranch().accept(this);
        new True().accept(this);
        int jumpToEndAddress = addDummy();
        // Wir setzen den Sprung zum Else-Zweig
        instructions.set(jumpToElseAddress, new Brc(instructions.size()));
        ifThenElse.getThenBranch().accept(this);
        // Wir setzen den Sprung zum Ende am Ende des Else-Zweiges
        instructions.set(jumpToEndAddress, new Brc(instructions.size()));
    }

    @Override
    public void visit(IfThen ifThen) {
        ifThen.getCond().accept(this);
        add(new Not());
        int jumpToEndAddress = addDummy();
        ifThen.getThenBranch().accept(this);
        instructions.set(jumpToEndAddress, new Brc(instructions.size()));
    }

    private ArrayList<Integer> currentSwitchBreaks = null;

    @Override
    public void visit(Switch sw) {
        ArrayList<Integer> oldSwitchBreaks = currentSwitchBreaks;
        currentSwitchBreaks = new ArrayList<>();
        sw.getSwitchExpression().accept(this);
        add(new asm.Pop(0));
        SwitchCase[] cases = sw.getCases();
        int[] caseJumps = new int[cases.length];
        for (int i = 0; i < cases.length; i++) {
            add(new Push(0));
            cases[i].getNumber().accept(this);
            add(new Cmp(CompareType.EQ));
            int jumpToCaseAddress = addDummy();
            caseJumps[i] = jumpToCaseAddress;
        }
        add(new Ldi(-1));
        int jumpToDefaultAddress = addDummy();
        for (int i = 0; i < cases.length; i++) {
            instructions.set(caseJumps[i], new Brc(instructions.size()));
            cases[i].getCaseStatement().accept(this);
        }
        instructions.set(jumpToDefaultAddress, new Brc(instructions.size()));
        if (sw.getDefault() != null)
            sw.getDefault().accept(this);
        for (int i = 0; i < currentSwitchBreaks.size(); i++)
            instructions.set(currentSwitchBreaks.get(i), new Brc(instructions.size()));
        currentSwitchBreaks = oldSwitchBreaks;
    }

    @Override
    public void visit(Break br) {
        add(new Ldi(-1));
        if (currentSwitchBreaks == null)
            throw new BreakOutsideSwitchException();
        currentSwitchBreaks.add(addDummy());
    }


    @Override
    public void visit(While while_) {
        int whileBeginAddress = instructions.size();
        int jumpToEndAddress = 0;
        if (!while_.isDoWhile()) {
            while_.getCond().accept(this);
            add(new Not());
            jumpToEndAddress = addDummy();
        }
        while_.getBody().accept(this);
        if (while_.isDoWhile())
            while_.getCond().accept(this);
        else
            new True().accept(this);
        add(new Brc(whileBeginAddress));
        if (!while_.isDoWhile()) {
            int whileEndAddress = instructions.size();
            // Wir setzen den Sprung ans Ende der Schleife
            instructions.set(jumpToEndAddress, new Brc(whileEndAddress));
        }
    }

    @Override
    public void visit(Return return_) {
        return_.getExpression().accept(this);
        add(new asm.Return(frameCells));
    }

    @Override
    public void visit(EmptyStatement emptyStatement) {
    }

    /*
     * Condition
     */

    @Override
    public void visit(True true_) {
        add(new Ldi(0));
        add(new Not());
    }

    @Override
    public void visit(False false_) {
        add(new Ldi(0));
    }

    @Override
    public void visit(Comparison comparison) {
        switch (comparison.getOperator()) {
            case Equals:
                comparison.getRhs().accept(this);
                comparison.getLhs().accept(this);
                add(new Cmp(CompareType.EQ));
                break;
            case NotEquals:
                comparison.getRhs().accept(this);
                comparison.getLhs().accept(this);
                add(new Cmp(CompareType.EQ));
                add(new Not());
                break;
            case Greater:
                comparison.getLhs().accept(this);
                comparison.getRhs().accept(this);
                add(new Cmp(CompareType.LT));
                break;
            case GreaterEqual:
                comparison.getRhs().accept(this);
                comparison.getLhs().accept(this);
                add(new Cmp(CompareType.LT));
                add(new Not());
                break;
            case Less:
                comparison.getRhs().accept(this);
                comparison.getLhs().accept(this);
                add(new Cmp(CompareType.LT));
                break;
            case LessEqual:
                comparison.getLhs().accept(this);
                comparison.getRhs().accept(this);
                add(new Cmp(CompareType.LT));
                add(new Not());
                break;
        }
    }

    @Override
    public void visit(UnaryCondition unaryCondition) {
        unaryCondition.getOperand().accept(this);
        switch (unaryCondition.getOperator()) {
            case Not:
                add(new Not());
                break;
        }
    }

    @Override
    public void visit(BinaryCondition binaryCondition) {
        binaryCondition.getRhs().accept(this);
        binaryCondition.getLhs().accept(this);
        switch (binaryCondition.getOperator()) {
            case And:
                add(new And());
                break;
            case Or:
                add(new Or());
                break;
        }
    }

    /*
     * Rest
     */

    @Override
    public void visit(Declaration declaration) {
        int offset = locals.size() + 1;
        Parameter[] names = declaration.getParams();
        for (int i = 0; i < names.length; i++) {
            if (locals.containsKey(names[i].getName())) {
                throw new VariableAlreadyDefinedException(names[i].getName());
            }
            names[i].indexinmemory = offset + i;
            locals.put(names[i].getName(), names[i]);
        }

        // Beachte: Mehrere DECLs am Funktionsanfang
        // hintereinander sind ok
        add(new Decl(names.length));
    }

    @Override
    public void visit(Function function) {
        int declarations = 0;
        for (Declaration d : function.getDeclarations()) {
            declarations += d.getParams().length;
            d.accept(this);
        }
        Parameter[] parameters = function.getParameters();
        for (int i = 0; i < parameters.length; i++) {
            if (locals.containsKey(parameters[i].getName())) {
                throw new VariableAlreadyDefinedException(parameters[i].getName());
            }
            parameters[i].indexinmemory = -parameters.length + i + 1;
            locals.put(parameters[i].getName(), parameters[i]);


        }
        frameCells = parameters.length + declarations;
        for (Statement s : function.getStatements())
            s.accept(this);
    }

    @Override
    public void visit(Program program) {
        // Das Programm beginnt mit einem Sprung zur Hauptfunktion
        int ldiMainAddress = addDummy();
        add(new asm.Call(0));
        add(new Halt());
        boolean hasMain = false;
        for (Function f : program.getFunctions()) {
            int functionStartIndex = instructions.size();
            if (f.getName().equals("main")) {
                if (f.getParameters().length > 0)
                    throw new InvalidMainException();
                instructions.set(ldiMainAddress, new Ldi(functionStartIndex));
                hasMain = true;
            }
            functions.put(f.getName(), new FunctionDesc(functionStartIndex, f.getParameters().length));
            locals.clear();
            f.accept(this);
        }
        if (!hasMain)
            throw new MissingMainException();
        // Nachdem alle Funktionen assembliert wurden, müssen wir die Instruktionen
        // patchen, die
        // Funktionsadressen laden.
        for (PatchLocation pl : patchLocations) {
            FunctionDesc fDesc = functions.get(pl.functionName);
            if (fDesc == null)
                throw new UnknownFunctionException(pl.functionName);
            if (fDesc.argumentCount != pl.argumentCount)
                throw new WrongNumberOfArgumentsException();
            instructions.set(pl.ldiLocation, new Ldi(fDesc.functionIndex));
        }
    }

    @Override
    public void visit(ExpressionStatement exprStmt) {
        exprStmt.getExpression().accept(this);
        add(new asm.Pop(0));
    }


    @Override
    public void visit(ArrayIndexAssignment arrayIndexAssignment) {

        arrayIndexAssignment.getExpression().accept(this); //alloziertobject auf stack

        arrayIndexAssignment.getArray().accept(this);
        add(new Ldi(1));
        add(new Add());

        arrayIndexAssignment.getIndex().accept(this);

        add(new asm.Add());

        add(new asm.Sth());


    }

    @Override
    public void visit(ArrayLength arrayLength) {


        arrayLength.getArray().accept(this);

        //heapadresse des arrays auf stack
        add(new Lfh());
        add(new Ldi(-1));
        add(new Add());

    }

    @Override
    public void visit(ArrayAllocator arrayAllocator) {
        
        arrayAllocator.getSize().accept(this);
        add(new Ldi(1));
        add(new Add());   //size+1
        //
        arrayAllocator.getSize().accept(this);
        add(new Ldi(1));
        add(new Add());   //size+1


        add(new asm.Alloc());
        //auf dem stack jetzt: heapadresse des arrays und danach die size

        add(new asm.Pop(1));
        add(new asm.Push(1)); //rette heapadresse ohne nebenwirkung

       //: stack hat jetzt adresse 1x drauf und danach größe des arrays

        //im stack an adresse STACKADRESSE_OHNE_ALLES wird nun length gespeichert
        add(new asm.Sth());

        //stelle nun heapadresse wieder her
        add(new asm.Push(1));


    }

    @Override
    public void visit(ArrayAccess arrayAccess) {

        arrayAccess.getArray().accept(this);
        add(new Ldi(1));
        add(new Add());
        arrayAccess.getIndex().accept(this);
        add(new asm.Add());
        add(new asm.Lfh());


    }

    @Override
    public void visit(Join join) {
        join.getThreadId().accept(this);
        add(new asm.Join());
    }

    @Override
    public void visit(Synchronized synchronize) {
        synchronize.getMutex().accept(this);
        add(new asm.Lock());
        Statement[] statements=synchronize.getCriticalSection();
        for(Statement statement: statements){
            statement.accept(this);
        }
        synchronize.getMutex().accept(this);
        add(new asm.Unlock());
    }


}
