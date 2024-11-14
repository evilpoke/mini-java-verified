package codegen;


import asm.HeapOverflowException;
import asm.Interpreter;
import org.junit.Test;


import static org.junit.Assert.assertEquals;

public class CodeGenArraysTest {

    public void testProgram(Program program, int expectedRetVal) {
        CodeGenerationVisitor cgv = new CodeGenerationVisitor();
        program.accept(cgv);
        int retVal = new Interpreter(cgv.getProgram()).execute();
        assertEquals(expectedRetVal, retVal);
    }

    private static void printProgram(Program prog) {
        FormatVisitor fv = new FormatVisitor();
        prog.accept(fv);
        System.out.println(fv.getFormattedCode());
    }

    @Test
    public void simplearraywithoutlength(){
        Function main = new Function("main",
                new String[]{},
                new Declaration[]{new Declaration(Type.IntArray, "a"), new Declaration(Type.Int, "result")},
                new Statement[]{
                        new Assignment("a", new ArrayAllocator(new Number(4))),
                        new ArrayIndexAssignment(new Variable("a"),new Number(0),new Number(10)),
                        new ArrayIndexAssignment(new Variable("a"),new Number(1),new Number(10)),
                        new ArrayIndexAssignment(new Variable("a"),new Number(2),new Number(10)),
                        new ArrayIndexAssignment(new Variable("a"),new Number(3),new Number(10)),
                        new Assignment("result", new ArrayAccess(new Variable("a"),new Number(2))),

                        //summation to come

                        new Return(new Variable("result"))
                });
        testProgram(new Program(new Function[]{main}),10);

    }

    @Test
    public void complexarraywithoutlength() {


        Function init = new Function(Type.IntArray, "init",
                new Parameter[]{new Parameter(Type.Int, "size")},
                new Declaration[]{new Declaration("i"), new Declaration(Type.IntArray, "array")},
                new Statement[]{
                        new Assignment("i", new Number(0)),
                        new Assignment("array", new ArrayAllocator(new Variable("size"))),
                        new While(new Comparison(new Variable("i"), Comp.Less, new Variable("size")), new Composite(new Statement[]{
                                new ArrayIndexAssignment(new Variable("array"), new Variable("i"), new Variable("i")),
                                new Assignment("i", new Binary(new Variable("i"), Binop.Plus, new Number(1)))
                        }), false),
                        new Return(new Variable("array"))
                });

        Function sum = new Function(Type.Int, "sum",
                new Parameter[]{new Parameter(Type.IntArray, "array")},
                new Declaration[]{new Declaration("i"), new Declaration("sum")},
                new Statement[]{
                        new Assignment("i", new Number(0)),
                        new Assignment("sum", new Number(0)),
                        new While(new Comparison(new Variable("i"), Comp.Less, new Number(50)), new Composite(new Statement[]{
                                new Assignment("sum", new Binary(new Variable("sum"), Binop.Plus, new ArrayAccess(new Variable("array"),
                                        new Variable("i")))),
                                new Assignment("i", new Binary(new Variable("i"), Binop.Plus, new Number(1)))
                        }), false),
                        new Return(new Variable("sum"))
                });

        Function main = new Function("main",
                new String[]{},
                new Declaration[]{new Declaration(Type.IntArray, "a")},
                new Statement[]{
                        new Assignment("a", new Call("init",
                                new Expression[]{new Number(50)})),
                        new Return(new Call("sum", new Expression[]{new Variable("a")}))
                });

        testProgram(new Program(new Function[]{init, sum, main}), 1225);

    }

    java.util.function.Function<Integer, Program> getsummationprogram = (n) -> {
        Function init = new Function(Type.IntArray, "init",
                new Parameter[]{new Parameter(Type.Int, "size")},
                new Declaration[]{new Declaration("i"), new Declaration(Type.IntArray, "array")},
                new Statement[]{
                        new Assignment("i", new Number(0)),
                        new Assignment("array", new ArrayAllocator(new Variable("size"))),
                        new While(new Comparison(new Variable("i"), Comp.Less, new Variable("size")), new Composite(new Statement[]{
                                new ArrayIndexAssignment(new Variable("array"), new Variable("i"), new Variable("i")),
                                new Assignment("i", new Binary(new Variable("i"), Binop.Plus, new Number(1)))
                        }), false),
                        new Return(new Variable("array"))
                });

        Function sum = new Function(Type.Int, "sum",
                new Parameter[]{new Parameter(Type.IntArray, "array")},
                new Declaration[]{new Declaration("i"), new Declaration("sum")},
                new Statement[]{
                        new Assignment("i", new Number(0)),
                        new Assignment("sum", new Number(0)),
                        new While(new Comparison(new Variable("i"), Comp.Less, new ArrayLength(new Variable("array"))), new Composite(new Statement[]{
                                new Assignment("sum", new Binary(new Variable("sum"), Binop.Plus, new ArrayAccess(new Variable("array"),
                                        new Variable("i")))),
                                new Assignment("i", new Binary(new Variable("i"), Binop.Plus, new Number(1)))
                        }), false),
                        new Return(new Variable("sum"))
                });

        Function main = new Function("main",
                new String[]{},
                new Declaration[]{new Declaration(Type.IntArray, "a")},
                new Statement[]{
                        new Assignment("a", new Call("init",
                                new Expression[]{new Number(n)})),
                        new Return(new Call("sum", new Expression[]{new Variable("a")}))
                });
        return new Program(new Function[]{init, sum, main});
    };

    @Test
    public void beispiel_von_blatt12() {
        // printProgram(getsummationprogram.apply(50));
        testProgram(getsummationprogram.apply(50), 1225);
        testProgram(getsummationprogram.apply(20), 190);
        testProgram(getsummationprogram.apply(1000), 499500);
    }
    @Test(expected = HeapOverflowException.class)
    public void overflow(){
        testProgram(getsummationprogram.apply(10000), 49995000);

    }

    @Test
    public void beispiel_von_blatt12_mit_length(){
        java.util.function.Function<Integer, Program> getexamplecode = (n) -> {
            Function init = new Function(Type.IntArray, "init",
                    new Parameter[]{new Parameter(Type.Int, "size")},
                    new Declaration[]{new Declaration("i"), new Declaration(Type.IntArray, "array")},
                    new Statement[]{
                            new Assignment("i", new Number(0)),
                            new Assignment("array", new ArrayAllocator(new Variable("size"))),
                            new While(new Comparison(new Variable("i"), Comp.Less, new Variable("size")), new Composite(new Statement[]{
                                    new ArrayIndexAssignment(new Variable("array"), new Variable("i"), new Variable("i")),
                                    new Assignment("i", new Binary(new Variable("i"), Binop.Plus, new Number(1)))
                            }), false),
                            new Return(new Variable("array"))
                    });

            Function sum = new Function(Type.Int, "sum",
                    new Parameter[]{new Parameter(Type.IntArray, "array")},
                    new Declaration[]{new Declaration("i"), new Declaration("sum")},
                    new Statement[]{
                            new Assignment("i", new Number(0)),
                            new Assignment("sum", new Number(0)),
                            new While(new Comparison(new Variable("i"), Comp.Less, new ArrayLength(new Variable("array"))), new Composite(new Statement[]{
                                    new Assignment("sum", new Binary(new Variable("sum"), Binop.Plus, new ArrayAccess(new Variable("array"),
                                            new Variable("i")))),
                                    new Assignment("i", new Binary(new Variable("i"), Binop.Plus, new Number(1)))
                            }), false),
                            new Return(new Variable("sum"))
                    });

            Function main = new Function("main",
                    new String[]{},
                    new Declaration[]{new Declaration(Type.IntArray, "a")},
                    new Statement[]{
                            new Assignment("a", new Call("init",
                                    new Expression[]{new Number(n)})),
                            new Return(new Call("sum", new Expression[]{new Variable("a")}))
                    });

            return new Program(new Function[]{init, sum, main});
        };
        testProgram(getexamplecode.apply(50),1225);
        testProgram(getexamplecode.apply(100),4950);
        testProgram(getexamplecode.apply(200),19900);
        testProgram(getexamplecode.apply(1000),499500);

    }


    @Test
    public void simplelengthbeispiel(){
        Function main = new Function(Type.Int, "main", new Parameter[] { }, new Declaration[] {
                new Declaration(Type.IntArray, "array"), new Declaration(Type.Int, "result") }, new
                Statement[] { new Assignment("array", new ArrayAllocator(new Number(10))), new
                ArrayIndexAssignment(new Variable("array"), new Number(2), new Number(2)), new
                ArrayIndexAssignment(new Variable("array"), new Number(4), new Number(30)), new
                ArrayIndexAssignment(new Variable("array"), new Number(6), new Binary(new ArrayAccess(new
                Variable("array"), new Number(2)), Binop.Plus, new ArrayAccess(new Variable("array"), new
                Number(4)))), new Assignment("result", new Binary(new ArrayLength(new Variable("array")),
                Binop.Plus, new ArrayAccess(new Variable("array"), new Number(6)))), new Return(new
                Variable("result")) });





        Program program = new Program( new Function[] { main } );
        testProgram(program,42);
    }



    @Test
    public void sorting(){
        java.util.function.Function<Integer, Program> sorting = (n) -> {
            Function main = new Function(Type.Int, "main", new Parameter[]{}, new Declaration[]{
                    new Declaration(Type.IntArray, "array"), new Declaration(Type.Int, new String[]{"i",
                    "d", "temp", "result"})}, new Statement[]{new Assignment("array", new
                    ArrayAllocator(new Number(10))), new Assignment("d", new Number(0)), new
                    Assignment("temp", new Number(0)), new ArrayIndexAssignment(new Variable("array"), new
                    Number(0), new Number(2)), new ArrayIndexAssignment(new Variable("array"), new Number(1),
                    new Number(5)), new ArrayIndexAssignment(new Variable("array"), new Number(2), new
                    Number(9)), new ArrayIndexAssignment(new Variable("array"), new Number(3), new
                    Number(8)), new ArrayIndexAssignment(new Variable("array"), new Number(4), new
                    Number(7)), new ArrayIndexAssignment(new Variable("array"), new Number(5), new
                    Number(6)), new ArrayIndexAssignment(new Variable("array"), new Number(6), new
                    Number(1)), new ArrayIndexAssignment(new Variable("array"), new Number(7), new
                    Number(3)), new ArrayIndexAssignment(new Variable("array"), new Number(8), new
                    Number(4)), new ArrayIndexAssignment(new Variable("array"), new Number(9), new
                    Number(0)), new While(new Comparison(new Variable("i"), Comp.Less, new Binary(new
                    ArrayLength(new Variable("array")), Binop.Minus, new Number(1))), new Composite(new
                    Statement[]{new Assignment("d", new Number(0)), new While(new Comparison(new
                    Variable("d"), Comp.Less, new Binary(new ArrayLength(new Variable("array")), Binop.Minus,
                    new Number(1))), new Composite(new Statement[]{new IfThen(new Comparison(new
                    ArrayAccess(new Variable("array"), new Variable("i")), Comp.Less, new ArrayAccess(new
                    Variable("array"), new Binary(new Variable("d"), Binop.Plus, new Number(1)))), new
                    Composite(new Statement[]{new Assignment("temp", new ArrayAccess(new
                    Variable("array"), new Binary(new Variable("d"), Binop.Plus, new Number(1)))), new
                    ArrayIndexAssignment(new Variable("array"), new Binary(new Variable("d"), Binop.Plus, new
                    Number(1)), new ArrayAccess(new Variable("array"), new Variable("i"))), new
                    ArrayIndexAssignment(new Variable("array"), new Variable("i"), new Variable("temp"))}
            )), new Assignment("d", new Binary(new Variable("d"), Binop.Plus, new Number(1)))}),
                    false), new Assignment("i", new Binary(new Variable("i"), Binop.Plus, new Number(1)))}
            ), false), new Assignment("result", new ArrayAccess(new Variable("array"), new Number(n))),
                    new Return(new Variable("result"))});


           return new Program(new Function[]{main});
        };
        testProgram(sorting.apply(1),1);
        testProgram(sorting.apply(2),2);
        testProgram(sorting.apply(3),3);
        testProgram(sorting.apply(4),4);
        testProgram(sorting.apply(5),5);
        testProgram(sorting.apply(6),6);
        testProgram(sorting.apply(7),7);
        testProgram(sorting.apply(8),8);
    }

    @Test
    public void testRecursion(){
        Function main = new Function(Type.Int, "main", new Parameter[] { }, new Declaration[] {
                new Declaration(Type.IntArray, "array"), new Declaration(Type.Int, new String[] {
                "result", "a", "b", "counter" } ) }, new Statement[] { new Assignment("array", new
                ArrayAllocator(new Number(10))), new Assignment("counter", new Number(10)), new
                ArrayIndexAssignment(new Variable("array"), new Number(2), new Number(5)), new
                ArrayIndexAssignment(new Variable("array"), new Number(4), new Number(30)), new
                ArrayIndexAssignment(new Variable("array"), new Number(6), new Binary(new ArrayAccess(new
                Variable("array"), new Number(2)), Binop.Plus, new ArrayAccess(new Variable("array"), new
                Number(4)))), new ArrayIndexAssignment(new Variable("array"), new Number(0), new
                Binary(new ArrayAccess(new Variable("array"), new Number(6)), Binop.Plus, new Binary(new
                Number(2), Binop.MultiplicationOperator, new ArrayAccess(new Variable("array"), new
                Number(6))))), new Assignment("result", new Binary(new ArrayLength(new
                Variable("array")), Binop.Plus, new ArrayAccess(new Variable("array"), new Number(6)))),
                new Assignment("array", new Call("dosomestuff", new Expression[] { new Variable("array")
                } )), new Assignment("result", new ArrayAccess(new Variable("array"), new Number(0))),
                new Return(new Variable("result")) });

        Function dosomestuff = new Function(Type.IntArray, "dosomestuff", new Parameter[] { new
                Parameter(Type.IntArray, "input") }, new Declaration[] { }, new Statement[] { new
                IfThenElse(new Comparison(new ArrayAccess(new Variable("input"), new Number(2)),
                Comp.Equals, new Number(0)), new Return(new Variable("input")), new Composite( new
                Statement[] { new ArrayIndexAssignment(new Variable("input"), new Number(2), new
                Binary(new ArrayAccess(new Variable("input"), new Number(2)), Binop.Minus, new
                Number(1))), new Return(new Call("dosomestuff", new Expression[] { new Variable("input")
        } )) } )) });


        Program program = new Program( new Function[] { main, dosomestuff } );
        testProgram(program,105);
    }

    @Test
    public void handleEmptyArray(){
        Function main = new Function(Type.Int, "main", new Parameter[] { }, new Declaration[] {
                new Declaration(Type.IntArray, "onesexyarray"), new Declaration(Type.Int, "result") },
                new Statement[] { new Assignment("result", new Number(15)), new
                        Assignment("onesexyarray", new ArrayAllocator(new Number(0))), new Assignment("result",
                        new ArrayLength(new Variable("onesexyarray"))), new Return(new Variable("result")) });


        Program program = new Program( new Function[] { main } );
        testProgram(program, 0);
    }

    @Test
    public void testmultiplearrays(){
        Function main = new Function(Type.Int, "main", new Parameter[] { }, new Declaration[] {
                new Declaration(Type.IntArray, new String[] { "firstarray", "secondarray", "thirdarray" }
                ), new Declaration(Type.Int, "result") }, new Statement[] { new Assignment("firstarray",
                new ArrayAllocator(new Number(15))), new Assignment("secondarray", new ArrayAllocator(new
                Number(5))), new Assignment("thirdarray", new ArrayAllocator(new Number(20))), new
                ArrayIndexAssignment(new Variable("firstarray"), new Number(5), new Number(2)), new
                ArrayIndexAssignment(new Variable("firstarray"), new Number(2), new Number(2)), new
                ArrayIndexAssignment(new Variable("firstarray"), new Number(1), new Number(2)), new
                ArrayIndexAssignment(new Variable("secondarray"), new Number(5), new Number(9)), new
                ArrayIndexAssignment(new Variable("secondarray"), new Number(2), new Number(9)), new
                ArrayIndexAssignment(new Variable("secondarray"), new Number(1), new Number(9)), new
                ArrayIndexAssignment(new Variable("thirdarray"), new Number(5), new Number(19)), new
                ArrayIndexAssignment(new Variable("thirdarray"), new Number(2), new Number(19)), new
                ArrayIndexAssignment(new Variable("thirdarray"), new Number(1), new Number(19)), new
                Assignment("result", new ArrayAccess(new Variable("firstarray"), new Number(5))), new
                Assignment("result", new Binary(new Variable("result"), Binop.Plus, new ArrayAccess(new
                Variable("firstarray"), new Number(2)))), new Assignment("result", new Binary(new
                Variable("result"), Binop.Plus, new ArrayAccess(new Variable("firstarray"), new
                Number(1)))), new Assignment("result", new Binary(new Variable("result"), Binop.Plus, new
                ArrayAccess(new Variable("secondarray"), new Number(5)))), new Assignment("result", new
                Binary(new Variable("result"), Binop.Plus, new ArrayAccess(new Variable("secondarray"),
                new Number(2)))), new Assignment("result", new Binary(new Variable("result"), Binop.Plus,
                new ArrayAccess(new Variable("secondarray"), new Number(1)))), new Assignment("result",
                new Binary(new Variable("result"), Binop.Plus, new ArrayAccess(new
                        Variable("thirdarray"), new Number(5)))), new Assignment("result", new Binary(new
                Variable("result"), Binop.Plus, new ArrayAccess(new Variable("thirdarray"), new
                Number(2)))), new Assignment("result", new Binary(new Variable("result"), Binop.Plus, new
                ArrayAccess(new Variable("thirdarray"), new Number(1)))), new Assignment("result", new
                Binary(new Variable("result"), Binop.Plus, new ArrayLength(new Variable("firstarray")))),
                new Assignment("result", new Binary(new Variable("result"), Binop.Plus, new
                        ArrayLength(new Variable("secondarray")))), new Assignment("result", new Binary(new
                Variable("result"), Binop.Plus, new ArrayLength(new Variable("thirdarray")))), new
                Return(new Variable("result")) });


        Program program = new Program( new Function[] { main } );
        testProgram(program, 118);
    }





}
