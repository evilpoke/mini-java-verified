
public class SheetExamples {

  public static void main(String[] args) {
    {
      Expression<Integer> exp = new MulOp<Integer>(new Const<Integer>(3),
          new AddOp<Integer>(new Const<Integer>(1), new Const<Integer>(2)));
      System.out.println(exp);
      System.out.println(exp.evaluate());
    }
    
    System.out.println("----------------------");
    
    {
      Expression<Boolean> exp = new AndOp<Boolean>(new Const<Boolean>(true),new OrOp<Boolean>(new
          Const<Boolean>(false), new Const<Boolean>(true)));
      System.out.println(exp);
      System.out.println(exp.evaluate());
    }

    System.out.println("----------------------");

    {
      ZAExpression<Boolean> exp = new ZAEQOp<Integer, Boolean>(
          new ZAMulOp<Integer, Integer>(new ZAConst<Integer>(3),
              new ZAAddOp<Integer, Integer>(new ZAConst<Integer>(1), new ZAConst<Integer>(2))),
          new ZAConst<Integer>(9));
      System.out.println(exp);
      System.out.println(exp.evaluate());
    }
  }

}
