package testingstuff;

import org.junit.*;

import javax.print.DocFlavor;
import java.awt.event.ItemListener;


public class   MapTest {

    static private Fun<Integer, String> f;
    static private Integer[] a;
    static private String[] b;
    static private String[] b_ok;

    @Before
    public void reset() {
        f = new IntToString();
        a = new Integer[42];
        for (int i = 0; i < a.length; ++i)
            a[i] = i;
        b = new String[a.length];
        b_ok = new String[a.length];
        for (int i = 0; i < b_ok.length; ++i)
            b_ok[i] = Integer.toString(i);
    }


    @After
    public void tidyUp() {
        f = null;
        a = null;
        b = null;
        b_ok = null;
    }

    @BeforeClass
    @AfterClass
    public static void checkIfEmpty() {
        Assert.assertNull(f);
        Assert.assertNull(a);
        Assert.assertNull(b);
        Assert.assertNull(b_ok);
    }


    @Test(expected=IllegalArgumentException.class)
    public void testerror1() throws InterruptedException{
        testingstuff.Map.map(null,null,  null, -2);
    }

    @Test(expected = IllegalArgumentException.class,timeout = 10)
    public void testerr2() throws InterruptedException{
        testingstuff.Map.map(f,a,null,500000000);
    }
    @Test(expected = IllegalArgumentException.class)
    public void meantests() throws InterruptedException{
        testingstuff.Map.map(f,a,null,Integer.MIN_VALUE);
        testingstuff.Map.map(f,a,null,Integer.MAX_VALUE);
        testingstuff.Map.map(f,a,null,new Integer(5));
    }
        //map(Fun<T, R> f, T[] a, R[] b, int n)

    @Test(expected = IllegalArgumentException.class)
    public void FUCKTHEPOLICE() throws InterruptedException{
        testingstuff.Map.map(null, null, b, -5000000);
    }

    @Test(expected = IllegalArgumentException.class)
    public void KIRREKACKE() throws  InterruptedException{
        String[] bla = null;
        testingstuff.Map.map(f,null,new String[]{" ", "something"}, 5);
        testingstuff.Map.map(f,null,bla, 5);
    }

    @Test
    public void nominaltest() throws InterruptedException {

        for (int i = 1; i <= a.length; ++i) {
            testingstuff.Map.map(f, a, b, i);
            Assert.assertArrayEquals(b, b_ok);
            reset();
        }

    }

    @Test
    public void KREBS_test() throws InterruptedException{
        for(int i = 10; i < 100; i++){
            String[] asdf = new String[i];
            testingstuff.Map.map(f,a,asdf,i);
        }
    }








}
