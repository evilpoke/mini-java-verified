package codegen;

public class FormatVisitor implements ProgramVisitor {
    private int level = 0;

    private StringBuilder resultBuilder = new StringBuilder();

    private void indent() {
        for (int i = 0; i < level; i++)
            resultBuilder.append("  ");
    }

    public String getFormattedCode() {
        return resultBuilder.toString();
    }

    @Override
    public void visit(Number number) {
        resultBuilder.append(number.getValue());
    }

    @Override
    public void visit(Variable nameExpression) {
        resultBuilder.append(nameExpression.getName());
    }

    @Override
    public void visit(Unary unary) {
        switch (unary.getOperator()) {
            case Minus:
                resultBuilder.append("-");
                break;
        }
        unary.getOperand().accept(this);
    }

    @Override
    public void visit(Binary binary) {
        resultBuilder.append('(');
        binary.getLhs().accept(this);
        switch (binary.getOperator()) {
            case DivisionOperator:
                resultBuilder.append(" / ");
                break;
            case Minus:
                resultBuilder.append(" - ");
                break;
            case Modulo:
                resultBuilder.append(" % ");
                break;
            case MultiplicationOperator:
                resultBuilder.append(" * ");
                break;
            case Plus:
                resultBuilder.append(" + ");
                break;
        }
        binary.getRhs().accept(this);
        resultBuilder.append(')');
    }

    @Override
    public void visit(Function function) {
        if (function.getReturntype() == Type.Int) {
            resultBuilder.append("int ");
        } else {
            resultBuilder.append("int[] ");
        }

        resultBuilder.append(function.getName());
        resultBuilder.append("(");
        Parameter[] params = function.getParameters();
        for (int i = 0; i < params.length; i++) {
            if (i > 0)
                resultBuilder.append(", ");
            if (params[i].getType() == Type.Int) {
                resultBuilder.append("int ");
            } else {
                resultBuilder.append("int[] ");
            }
            resultBuilder.append(params[i].getName());
        }
        resultBuilder.append(") {\n");
        level++;
        for (Declaration decl : function.getDeclarations())
            decl.accept(this);
        for (Statement stmt : function.getStatements())
            stmt.accept(this);
        level--;
        resultBuilder.append("}");
    }

    @Override
    public void visit(Declaration declaration) {
        indent();
        Parameter[] parameters = declaration.getParams();
        if (declaration.getTypeofallparameters() == Type.Int) {
            resultBuilder.append("int ");
        } else {
            resultBuilder.append("int[] ");
        }
        for (int i = 0; i < parameters.length; i++) {
            if (i > 0) {
                resultBuilder.append(", ");
            }
            resultBuilder.append(parameters[i].getName());
        }
        resultBuilder.append(";\n");
    }

    @Override
    public void visit(Read read) {
        resultBuilder.append("read()");
    }

    @Override
    public void visit(Assignment assignment) {
        indent();
        resultBuilder.append(assignment.getName());
        resultBuilder.append(" = ");
        assignment.getExpression().accept(this);
        resultBuilder.append(";\n");
    }

    @Override
    public void visit(Composite composite) {
//    indent();
//    resultBuilder.append('{');
//    level++;
        for (Statement stmt : composite.getStatements())
            stmt.accept(this);
//    indent();
//    level--;
//    resultBuilder.append('}');
    }

    @Override
    public void visit(Comparison comp) {
        resultBuilder.append('(');
        comp.getLhs().accept(this);
        switch (comp.getOperator()) {
            case Equals:
                resultBuilder.append(" == ");
                break;
            case Greater:
                resultBuilder.append(" > ");
                break;
            case GreaterEqual:
                resultBuilder.append(" >= ");
                break;
            case Less:
                resultBuilder.append(" < ");
                break;
            case LessEqual:
                resultBuilder.append(" <= ");
                break;
            case NotEquals:
                resultBuilder.append(" != ");
                break;
        }
        comp.getRhs().accept(this);
        resultBuilder.append(')');
    }

    @Override
    public void visit(While while_) {
        indent();
        if (while_.isDoWhile())
            resultBuilder.append("do {\n");
        else {
            resultBuilder.append("while(");
            while_.getCond().accept(this);
            resultBuilder.append(") {\n");
        }
        level++;
        while_.getBody().accept(this);
        level--;
        indent();
        if (while_.isDoWhile()) {
            resultBuilder.append("} while(");
            while_.getCond().accept(this);
            resultBuilder.append(");\n");
        } else
            resultBuilder.append("}\n");
    }

    @Override
    public void visit(True true_) {
        resultBuilder.append("true");
    }

    @Override
    public void visit(False false_) {
        resultBuilder.append("false");

    }

    @Override
    public void visit(UnaryCondition unary) {
        switch (unary.getOperator()) {
            case Not:
                resultBuilder.append("!");
                break;
        }
        unary.getOperand().accept(this);
    }

    @Override
    public void visit(BinaryCondition binary) {
        resultBuilder.append('(');
        binary.getLhs().accept(this);
        switch (binary.getOperator()) {
            case And:
                resultBuilder.append(" && ");
                break;
            case Or:
                resultBuilder.append(" || ");
                break;
        }
        binary.getRhs().accept(this);
        resultBuilder.append(')');

    }

    @Override
    public void visit(IfThenElse ifThenElse) {
        indent();
        resultBuilder.append("if(");
        ifThenElse.getCond().accept(this);
        resultBuilder.append(") {\n");
        level++;
        ifThenElse.getThenBranch().accept(this);
        level--;
        indent();
        resultBuilder.append("} else {\n");
        level++;
        ifThenElse.getElseBranch().accept(this);
        level--;
        indent();
        resultBuilder.append("}\n");
    }

    @Override
    public void visit(Program program) {
        Function[] functions = program.getFunctions();
        for (int i = 0; i < functions.length; i++) {
            if (i > 0)
                resultBuilder.append("\n\n");
            functions[i].accept(this);
        }

    }

    @Override
    public void visit(IfThen ifThen) {
        indent();
        resultBuilder.append("if(");
        ifThen.getCond().accept(this);
        resultBuilder.append(") {\n");
        level++;
        ifThen.getThenBranch().accept(this);
        level--;
        indent();
        resultBuilder.append("}\n");
    }

    @Override
    public void visit(Call call) {
        if (!call.isFork()) {

            resultBuilder.append(call.getFunctionName());
            resultBuilder.append("(");
            Expression[] args = call.getArguments();
            for (int i = 0; i < args.length; i++) {
                if (i > 0)
                    resultBuilder.append(", ");
                args[i].accept(this);
            }
            resultBuilder.append(")");
        } else {
            resultBuilder.append("fork:").append(call.getFunctionName()).append("(");
            Expression[] args = call.getArguments();
            for (int i = 0; i < args.length; i++) {
                if (i > 0)
                    resultBuilder.append(", ");
                args[i].accept(this);
            }
            resultBuilder.append(")");

        }
    }

    @Override
    public void visit(Return return_) {
        indent();
        resultBuilder.append("return ");
        return_.getExpression().accept(this);
        resultBuilder.append(";\n");
    }

    @Override
    public void visit(Write write) {
        resultBuilder.append("write(");
        write.getExpression().accept(this);
        resultBuilder.append(")");
    }

    @Override
    public void visit(EmptyStatement emptyStatement) {
        indent();
        resultBuilder.append(";\n");
    }

    @Override
    public void visit(ExpressionStatement exprStmt) {
        indent();
        exprStmt.getExpression().accept(this);
        resultBuilder.append(";\n");
    }

    @Override
    public void visit(Switch sw) {
        indent();
        resultBuilder.append("switch(");
        sw.getSwitchExpression().accept(this);
        resultBuilder.append(") {\n");
        level++;
        SwitchCase cases[] = sw.getCases();
        for (int i = 0; i < cases.length; i++) {
            indent();
            resultBuilder.append("case ");
            cases[i].getNumber().accept(this);
            resultBuilder.append(":\n");
            level++;
            cases[i].getCaseStatement().accept(this);
            level--;
        }
        if (sw.getDefault() != null) {
            indent();
            resultBuilder.append("default:\n");
            level++;
            sw.getDefault().accept(this);
            level--;
        }
        level--;
        indent();
        resultBuilder.append("}\n");
    }

    @Override
    public void visit(Break br) {
        indent();
        resultBuilder.append("break;\n");
    }

    @Override
    public void visit(ArrayIndexAssignment arrayIndexAssignment) {
        indent();
        arrayIndexAssignment.getArray().accept(this);
        resultBuilder.append("[");
        arrayIndexAssignment.getIndex().accept(this);
        resultBuilder.append("] = ");
        arrayIndexAssignment.getExpression().accept(this);
        resultBuilder.append(";\n");
    }

    @Override
    public void visit(ArrayLength arrayLength) {

        resultBuilder.append("length(");
        arrayLength.getArray().accept(this);
        resultBuilder.append(")");

    }

    @Override
    public void visit(ArrayAllocator arrayAllocator) {
        resultBuilder.append("new int[");
        arrayAllocator.getSize().accept(this);
        resultBuilder.append("]");
    }

    @Override
    public void visit(ArrayAccess arrayAcces) {
        arrayAcces.getArray().accept(this);
        resultBuilder.append("[");
        arrayAcces.getIndex().accept(this);
        resultBuilder.append("]");
    }

    @Override
    public void visit(Join join) {
        resultBuilder.append("join(");
        join.getThreadId().accept(this);
        resultBuilder.append(")");
    }

    @Override
    public void visit(Synchronized synchronize) {
        indent();
        resultBuilder.append("synchronized(");
        synchronize.getMutex().accept(this);
        resultBuilder.append(")  {\n");
        level++;
        for (int i = 0; i < synchronize.getCriticalSection().length; i++) {
            synchronize.getCriticalSection()[i].accept(this);
        }
        level--;
        indent();
        resultBuilder.append("}\n");
    }

}
