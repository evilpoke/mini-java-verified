package asm;

import codegen.*;
import codegen.Number;
import codegen.Program;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class InterpreterThreadTest {


  @Test
  public void testSynchronizedThreadAccess(){
    Instruction[] program = {
            new Ldi( 23),
            new Call (0),
            new Halt(),
            new Lfs (0),
            new Lock(),
            new Ldi(1),
            new Lfs (0),
            new Ldi( 1),
            new Add(),
            new Ldi(0),
            new Add(),
            new Lfh(),
            new Add(),
            new Lfs( 0),
            new Ldi( 1),
            new Add(),
            new Ldi( 0),
            new Add(),
            new Sth(),
            new Lfs (0),
            new Unlock(),
            new Ldi(42),
            new Return(1),
            new Decl(1),
            new Decl(3),
            new Ldi(1),
            new Ldi(1),
            new Add(),
            new Ldi(1),
            new Ldi(1),
            new Add(),
            new Alloc(),
            new Pop(1),
            new Push(1),
            new Sth(),
            new Push(1),
            new Sts(1),
            new Ldi(0),
            new Lfs(1),
            new Ldi(1),
            new Add(),
            new Ldi(0),
            new Add(),
            new Sth(),
            new Lfs(1),
            new Ldi(3),
            new Fork(1),
            new Sts(3),
            new Lfs(1),
            new Ldi(3),
            new Fork(1),
            new Sts(4),
            new Lfs(3),
            new Join(),
            new Sts(2),
            new Lfs(4),
            new Join(),
            new Lfs(2),
            new Add(),
            new Sts(2),
            new Lfs(1),
            new Ldi(1),
            new Add(),
            new Ldi(0),
            new Add(),
            new Lfh(),
            new Lfs(2),
            new Add(),
            new Return(4)
    };
    Interpreter intp = new Interpreter(program);
    assertEquals(86, intp.execute());
  }



  public void runProgram(Program program) {
    CodeGenerationVisitor cgv = new CodeGenerationVisitor();
    program.accept(cgv);
    int retVal = new Interpreter(cgv.getProgram()).execute();

  }

  public void testProgram(Program program, int expectedRetVal) {
    CodeGenerationVisitor cgv = new CodeGenerationVisitor();
    program.accept(cgv);
    int retVal = new Interpreter(cgv.getProgram()).execute();
    assertEquals(expectedRetVal, retVal);
  }


  @Test
  public void verysimplemultithreads() {
    Function inc = new Function(Type.Int, "inc", new Parameter[]{}, new Declaration[]{},
            new Statement[]{new codegen.Return(new codegen.Number(42))});

    Function main = new Function(Type.Int, "main", new Parameter[]{}, new Declaration[]{
            new Declaration(Type.Int, new String[]{"x", "t1", "t2"})}, new Statement[]{new
            Assignment("t1", new codegen.Call("inc", new Expression[]{}, true)), new Assignment("t2", new
            codegen.Call("inc", new Expression[]{}, true)), new Assignment("x", new codegen.Join(new
            Variable("t1"))), new Assignment("x", new Binary(new Variable("x"), Binop.Plus, new
            codegen.Join(new Variable("t2")))), new codegen.Return(new Variable("x"))});

    codegen.Program program = new codegen.Program(new Function[]{inc, main});
    testProgram(program, 84);
  }

  @Test(expected = DeadlockException.class)
  public void testDeadlock(){
    Function main = new Function(Type.Int, "main", new Parameter[] { }, new Declaration[] {
            new Declaration(Type.IntArray, new String[] { "array1", "array2" } ), new
            Declaration(Type.Int, new String[] { "back1", "back2" } ) }, new Statement[] { new
            Assignment("array1", new ArrayAllocator(new codegen.Number(5))), new Assignment("array2", new
            ArrayAllocator(new codegen.Number(3))), new Assignment("back1", new codegen.Call("firstexecuter", new
            Expression[] { new Variable("array1"), new Variable("array2") }, true)), new
            Assignment("back2", new codegen.Call("secondexecuter", new Expression[] { new Variable("array1"),
            new Variable("array2") }, true)), new Assignment("back1", new codegen.Join(new
            Variable("back1"))), new Assignment("back2", new codegen.Join(new Variable("back2"))), new
            codegen.Return(new Binary(new Variable("back1"), Binop.Plus, new Variable("back2"))) });

    Function firstexecuter = new Function(Type.Int, "firstexecuter", new Parameter[] { new
            Parameter(Type.IntArray, "lock1"), new Parameter(Type.IntArray, "lock2") }, new
            Declaration[] { new Declaration(Type.Int, "counter") }, new Statement[] { new
            Synchronized(new Variable("lock1"), new Statement[] { new Assignment("counter", new
            codegen.Number(1)), new Assignment("counter", new Binary(new Variable("counter"), Binop.Plus, new
            codegen.Number(1))), new Assignment("counter", new Binary(new Variable("counter"), Binop.Plus,
            new codegen.Number(1))), new Assignment("counter", new Binary(new Variable("counter"),
            Binop.Plus, new codegen.Number(1))), new Synchronized(new Variable("lock2"), new Statement[] {
            new Assignment("counter", new Binary(new Variable("counter"), Binop.Plus, new
                    codegen.Number(1))), new Assignment("counter", new Binary(new Variable("counter"), Binop.Plus,
            new codegen.Number(1))), new Assignment("counter", new Binary(new Variable("counter"),
            Binop.Plus, new codegen.Number(1))) }) }), new codegen.Return(new Variable("counter")) });

    Function secondexecuter = new Function(Type.Int, "secondexecuter", new Parameter[] { new
            Parameter(Type.IntArray, "lock1"), new Parameter(Type.IntArray, "lock2") }, new
            Declaration[] { new Declaration(Type.Int, "counter") }, new Statement[] { new
            Synchronized(new Variable("lock2"), new Statement[] { new Assignment("counter", new
            codegen.Number(1)), new Assignment("counter", new Binary(new Variable("counter"), Binop.Plus, new
            codegen.Number(1))), new Assignment("counter", new Binary(new Variable("counter"), Binop.Plus,
            new codegen.Number(1))), new Assignment("counter", new Binary(new Variable("counter"),
            Binop.Plus, new codegen.Number(1))), new Synchronized(new Variable("lock1"), new Statement[] {
            new Assignment("counter", new Binary(new Variable("counter"), Binop.Plus, new
                    codegen.Number(1))), new Assignment("counter", new Binary(new Variable("counter"), Binop.Plus,
            new codegen.Number(1))), new Assignment("counter", new Binary(new Variable("counter"),
            Binop.Plus, new codegen.Number(1))) }) }), new codegen.Return(new Variable("counter")) });


    codegen.Program program = new codegen.Program( new Function[] { main, firstexecuter, secondexecuter } );
    runProgram(program);

  }

  @Test
  public void testracecondition() {
    Function inc = new Function(Type.Int, "inc", new Parameter[]{new
            Parameter(Type.IntArray, "array")}, new Declaration[]{}, new Statement[]{new
            ArrayIndexAssignment(new Variable("array"), new codegen.Number(0), new Binary(new ArrayAccess(new
            Variable("array"), new codegen.Number(0)), Binop.Plus, new codegen.Number(1))), new codegen.Return(new
            codegen.Number(42))});

    Function main = new Function(Type.Int, "main", new Parameter[]{}, new Declaration[]{
            new Declaration(Type.IntArray, "array"), new Declaration(Type.Int, new String[]{"x",
            "t1", "t2"})}, new Statement[]{new Assignment("array", new ArrayAllocator(new
            codegen.Number(1))), new ArrayIndexAssignment(new Variable("array"), new codegen.Number(0), new
            codegen.Number(0)), new Assignment("t1", new codegen.Call("inc", new Expression[]{new Variable("array")
    }, true)), new Assignment("t2", new codegen.Call("inc", new Expression[]{new Variable("array")
    }, true)), new Assignment("x", new codegen.Join(new Variable("t1"))), new Assignment("x", new
            Binary(new Variable("x"), Binop.Plus, new codegen.Join(new Variable("t2")))), new codegen.Return(new
            Binary(new Variable("x"), Binop.Plus, new ArrayAccess(new Variable("array"), new
            codegen.Number(0))))});


    codegen.Program program = new codegen.Program(new Function[]{inc, main});
    testProgram(program, 85); //und nicht 86
  }


  @Test
  public void testnonracecondition() {

    Function inc = new Function(Type.Int, "inc", new Parameter[]{new
            Parameter(Type.IntArray, "array")}, new Declaration[]{}, new Statement[]{new
            Synchronized(new Variable("array"), new Statement[]{new ArrayIndexAssignment(new
            Variable("array"), new codegen.Number(0), new Binary(new ArrayAccess(new Variable("array"), new
            codegen.Number(0)), Binop.Plus, new codegen.Number(1)))}), new codegen.Return(new codegen.Number(42))});

    Function main = new Function(Type.Int, "main", new Parameter[]{}, new Declaration[]{
            new Declaration(Type.IntArray, "array"), new Declaration(Type.Int, new String[]{"x",
            "t1", "t2"})}, new Statement[]{new Assignment("array", new ArrayAllocator(new
            codegen.Number(1))), new ArrayIndexAssignment(new Variable("array"), new codegen.Number(0), new
            codegen.Number(0)), new Assignment("t1", new codegen.Call("inc", new Expression[]{new Variable("array")
    }, true)), new Assignment("t2", new codegen.Call("inc", new Expression[]{new Variable("array")
    }, true)), new Assignment("x", new codegen.Join(new Variable("t1"))), new Assignment("x", new
            Binary(new Variable("x"), Binop.Plus, new codegen.Join(new Variable("t2")))), new codegen.Return(new
            Binary(new Variable("x"), Binop.Plus, new ArrayAccess(new Variable("array"), new
            Number(0))))});


    codegen.Program program = new Program(new Function[]{inc, main});


    testProgram(program, 86);

  }







  @Test
  public void testExpr() {
    /*
     * 1.5P
     */
    Instruction[] program = {
      new Ldi(5),
      new Ldi(2),
      new Mul(),
      new Ldi(3),
      new Mod(),
      new Not(),
      new Halt()
    };
    Interpreter intp = new Interpreter(program);
    assertEquals(-4, intp.execute());
  }



  @Test
  public void testarrayassembler(){
    Instruction[] program = {
            new Decl(1),
            new Ldi(2),
            new Alloc(),
            new Sts(1),
            new Ldi(42),
            new Lfs(1),
            new Ldi(1),
            new Add(),
            new Sth(),
            new Lfs(1),
            new Ldi(1),
            new Add(),
            new Lfh(),
            new Halt()
    };
    Interpreter intp = new Interpreter(program);
    assertEquals(42, intp.execute());
  }


  @Test
  public void testVariables() {
    /*
     * 1.5P
     */
    Instruction[] program = {
      new Decl(3),
      new Ldi(5),
      new Sts(1),
      new Ldi(2),
      new Sts(2),
      new Ldi(3),
      new Sts(3),
      new Lfs(1),
      new Lfs(2),
      new Mul(),
      new Lfs(3),
      new Mod(),
      new Not(),
      new Halt()
    };
    Interpreter intp = new Interpreter(program);
    assertEquals(-4, intp.execute());
  }
  
  @Test
  public void testControlFlow() {
    /*
     * 1.5P
     */
    Instruction[] program = {
        new Decl(1),
        new Decl(1),
        new Decl(1),
        new Ldi(0),
        new Not(),
        new Ldi(7),
        new Ldi(3),
        new Cmp(CompareType.LT),
        new And(),
        new Brc(15),
        new Ldi(1),
        new Halt(),
        new Ldi(0),
        new Not(),
        new Brc(65),
        new Ldi(1),
        new Sts(1),
        new Ldi(2),
        new Sts(2),
        new Ldi(3),
        new Sts(3),
        new Ldi(1000),
        new Lfs(1),
        new Cmp(CompareType.LT),
        new Not(),
        new Brc(59),
        new Ldi(1),
        new Lfs(1),
        new Add(),
        new Sts(1),
        new Lfs(2),
        new Lfs(1),
        new Cmp(CompareType.LT),
        new Not(),
        new Not(),
        new Brc(56),
        new Ldi(1),
        new Lfs(2),
        new Add(),
        new Sts(2),
        new Lfs(3),
        new Lfs(1),
        new Cmp(CompareType.LT),
        new Not(),
        new Not(),
        new Brc(53),
        new Ldi(2),
        new Lfs(3),
        new Mul(),
        new Sts(3),
        new Ldi(0),
        new Not(),
        new Brc(40),
        new Ldi(0),
        new Not(),
        new Brc(30),
        new Ldi(0),
        new Not(),
        new Brc(21),
        new Lfs(3),
        new Lfs(2),
        new Add(),
        new Lfs(1),
        new Add(),
        new Halt()
    };
    Interpreter intp = new Interpreter(program);
    assertEquals(3537, intp.execute());
  }

  @Test
  public void testFunctionCalls() {
    /*
     * 1.5P
     */
    Instruction[] program = {
        new Ldi(26),
        new Call(0),
        new Halt(),
        new Lfs(-5),
        new Lfs(-4),
        new Lfs(-3),
        new Lfs(-2),
        new Lfs(-1),
        new Lfs(0),
        new Ldi(35),
        new Call(1),
        new Ldi(39),
        new Call(2),
        new Ldi(22),
        new Call(2),
        new Ldi(39),
        new Call(2),
        new Ldi(39),
        new Call(2),
        new Ldi(22),
        new Call(2),
        new Return(6),
        new Lfs(0),
        new Lfs(-1),
        new Add(),
        new Return(2),
        new Ldi(2),
        new Ldi(4),
        new Ldi(8),
        new Ldi(16),
        new Ldi(32),
        new Ldi(64),
        new Ldi(3),
        new Call(6),
        new Return(0),
        new Lfs(0),
        new Ldi(0),
        new Sub(),
        new Return(1),
        new Lfs(0),
        new Ldi(35),
        new Call(1),
        new Lfs(-1),
        new Add(),
        new Return(2),
    };
    Interpreter intp = new Interpreter(program);
    assertEquals(110, intp.execute());
  }
  
  @Test
  public void testExceptions() {
    /*
     * 0.25 Punkte pro Exception (korrekte Unterklasse, korrekt geworfen), max. 1P insgesamt
     */
    
    fail("Bitte Exceptions manuell prüfen!");
  }
  
  @Test
  public void testVisitor() {
    /*
     * Wichtige Punkte beim Visitor-Pattern:
     * 
     * - Genau ein accept() pro konkreter Unterklasse (-0.5 bei mehreren accepts)
     * - Keine Verwendung von instanceof, um durch die Hierarchie zu navigieren
     * - ...
     * 
     * Insg. max. 2 Punkte Abzug, max. 4 Punkte auf dem gesamten Blatt
     */
    
    fail("Bitte manuell prüfen, dass das Visitor-Pattern korrekt verwendet wurde");
  }
  
  @Test
  public void testTests() {
    /*
     * 1P Abzug ohne Tests
     */
    
    fail("Bitte Tests prüfen!");
  }

  @Test
  public void testFib() {
    /*
     * 1P Abzug, falls dieser Test scheitert (ab insg. <= 4 Punkten bei dieser Aufgabe kein Abzug mehr)
     */
    Instruction[] program = {
        new Ldi(3),
        new Call(0),
        new Halt(),
        new Ldi(30),
        new Ldi(7),
        new Call(1),
        new Return(0),
        new Decl(4),
        new Lfs(0),
        new Ldi(1),
        new Cmp(CompareType.LT),
        new Not(),
        new Not(),
        new Brc(16),
        new Lfs(0),
        new Return(5),
        new Ldi(0),
        new Sts(1),
        new Ldi(1),
        new Sts(2),
        new Ldi(1),
        new Sts(3),
        new Lfs(0),
        new Lfs(3),
        new Cmp(CompareType.LT),
        new Not(),
        new Brc(44),
        new Lfs(1),
        new Sts(4),
        new Lfs(2),
        new Lfs(1),
        new Add(),
        new Sts(1),
        new Lfs(4),
        new Not(),
        new Not(),
        new Sts(2),
        new Ldi(1),
        new Lfs(3),
        new Add(),
        new Sts(3),
        new Ldi(0),
        new Not(),
        new Brc(22),
        new Ldi(2),
        new Lfs(0),
        new Sub(),
        new Ldi(7),
        new Call(1),
        new Lfs(1),
        new Add(),
        new Return(5),
    };
    
    Interpreter intp = new Interpreter(program);
    assertEquals(832040, intp.execute());
  }

}
