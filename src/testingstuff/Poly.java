package testingstuff;





public class Poly {

    static class A  {
        protected A a;

        public A(A a) {
            this.a = a;
        }

        public void f(A a) {
            System.out.println("A.f(A)");
            this.a.f(a);
        }

        public void d(A a){
            System.out.println("do stuff...");

        }

        public void f(B<A> a) {
            System.out.println("A.f(B)");
            a.f(this.a);
        }
    }

    static class B<T> extends A {
        public T t;

        public B(Object o) {
            super(null);
        }

        public B() {
            super(null);
            a = new C<B<T>>(this);
        }
       /* @Override
        public void f(A a){
            System.out.println("test");
        }*/

        public void f(B<A> a) {
            System.out.println("B.f(B<A>)");
        }
    }

    static class C<T extends A> extends B<T> {
        public T t;

        public C(T t) {
            super(null);
            a = this;
            this.t = t;
        }

        public void f(A a) {
            System.out.println("C.f(A)");
        }


        public void f(B<A> a) {
            System.out.println("C.f(B<A>)");
            a.f(t);
        }
    }

    public static void main(String[] args) {
        B<Integer> b1 = new B<Integer>();
        B<A> b2 = new B<A>();
        A a = new A(b1);
        C<A> c = new C<A>(a);

       //a.f(b1);
       //a.f(b2);
        B<C<A>> b3 = new B<C<A>>();
        B<A> b4;
        //b2 = b3;

      //  (b2 = b3).f(a);

  //      (b4 = c).t.f(a);


    }
}