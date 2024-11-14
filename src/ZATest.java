// 6. Testklasse
class ZATest {
  public static void main(String[] argv) {
    ZAExpression<Integer> e1 = new ZAMulOp<Integer, Integer>(
        new ZAAddOp<Integer, Integer>(new ZAConst<Integer>(13), new ZAConst<Integer>(4)), new ZAConst<Integer>(2));
    System.out.println(e1.evaluate());
    assert (e1.evaluate() == 34);
    ZAExpression<Boolean> e2 = new ZAOrOp<Boolean, Boolean>(
        new ZAndOp<Boolean, Boolean>(new ZAConst<Boolean>(true),
            new ZAOrOp<Boolean, Boolean>(new ZAConst<Boolean>(true), new ZAConst<Boolean>(false))),
        new ZAConst<Boolean>(false));
    System.out.println(e2.evaluate());
    assert (e2.evaluate() == true);
    ZAExpression<Boolean> e3 = new ZAndOp<Boolean, Boolean>(
        new GTOp<Integer, Boolean>(new ZAMulOp<Integer, Integer>(new ZAConst<Integer>(7), new ZAConst<Integer>(8)),
            new ZAConst<Integer>(57)),
        new ZAConst<Boolean>(true));
    System.out.println(e3.evaluate());
    assert (e3.evaluate() == false);
    ZAExpression<Integer> e4 = new ZAMinusOp<Integer>(
        new ZASubOp<Integer, Integer>(new ZAConst<Integer>(-17), new ZAMinusOp<Integer>(new ZAConst<Integer>(16))));
    System.out.println(e4.evaluate());
    assert (e4.evaluate() == 1);
  }
}
