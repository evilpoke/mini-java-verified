import org.junit.*;

import static org.junit.Assert.assertEquals;


public class ProdConTest {

    Worker[] workforce = new Worker[5];
    Queue<penguins> penguinsQueue;
    Queue<penguins> penguinsQueue2;
    Queue<penguins> penguinsQueue3;

    Queue<sealions> sealionsQueue;
    Queue<sealions> sealionsQueue2;
    Queue<sealions> sealionsQueue3;


    @Before
    public void inittest(){


        penguinsQueue = new Queue<>();
        sealionsQueue = new Queue<>();
        sealionsQueue2 = new Queue<>();
        penguinsQueue3 = new Queue<>();
        sealionsQueue3 = new Queue<>();
        penguinsQueue2 = new Queue<>();

        workforce[0] = new Worker(penguinsQueue,sealionsQueue,penguinssealionsFunction);
        workforce[1] = new Worker(sealionsQueue,penguinsQueue2,sealionspenguinsFunction);
        workforce[2] = new Worker(penguinsQueue2, sealionsQueue2, penguinssealionsFunction);
        workforce[3] = new Worker(sealionsQueue2,penguinsQueue3, sealionspenguinsFunction);
        workforce[4] = new Worker(penguinsQueue3,sealionsQueue3,penguinssealionsFunction);



    }

    @After
    public void tidyup(){
        workforce = new Worker[5];
        penguinsQueue = new Queue<>();
        sealionsQueue = new Queue<>();
        penguinsQueue2 = new Queue<>();
        sealionsQueue2 = new Queue<>();
        penguinsQueue3 = new Queue<>();
        sealionsQueue3 = new Queue<>();

    }

    @Test
    public void testqueues(){

        //TODO: simple test
    }

    /*@Test
    public void testsequencial() throws InterruptedException {
        penguins skipper = new penguins(10);
        penguins kowalski = new penguins(11);
        penguins rico = new penguins(6);

        try {

            penguinsQueue.enqueue(skipper);
            penguinsQueue.enqueue(kowalski);
            penguinsQueue.enqueue(rico);


            workforce[0].start();
           // workforce[0].join();
            System.out.println("survived run kickstart");
            //System.out.println("survived run kickstart");
            Thread.sleep(10000);
            workforce[0].notify();
            System.out.println("1st notify");

            Thread.sleep(200);
            penguinsQueue.testvalue= 42;

            System.out.println("2nd coming");
            workforce[0].notify();

            System.out.println("finished");

            Thread.sleep(20000);


            workforce[1].run();
            Thread.sleep(1000);
            //workforce[1].join();

            workforce[2].run();
            Thread.sleep(2000);
            //workforce[2].join();

            workforce[3].run();
            Thread.sleep(2000);
            //workforce[3].join();

            workforce[4].run();
            Thread.sleep(2000);
            //workforce[4].join();

        } catch (InterruptedException e) {
            //will (hopefully never trigger)
            e.printStackTrace();
            System.err.println("yep.. i fucked up");
        }
        assertEquals(sealionsQueue3.dequeue().getWeight(), 8);

    }
*/

    @Test
    public void prodcontest1() throws InterruptedException {

        penguins skipper = new penguins(10);
        penguins kowalski = new penguins(11);
        penguins rico = new penguins(6);

        try {

            penguinsQueue.enqueue(skipper);
            penguinsQueue.enqueue(kowalski);
            penguinsQueue.enqueue(rico);

        } catch (InterruptedException e) {
            //will (hopefully never trigger)
            e.printStackTrace();
            System.err.println("yep.. i fucked up");
        }

        for(Worker worker: workforce){
            worker.start();
        }

        Thread.sleep(2000);
        assertEquals(sealionsQueue3.dequeue().getWeight(), 3);
        assertEquals(sealionsQueue3.dequeue().getWeight(), 4);
        assertEquals(sealionsQueue3.dequeue().getWeight(), 3);
    }



    java.util.function.Function<sealions, penguins> sealionspenguinsFunction = (n) -> {

        if(n.getWeight()<=5){ //lightweight penguins are cute but not cool
            return new penguins(6);
        } else {
            return new penguins(n.getWeight()+1);
        }


    };

    java.util.function.Function<penguins, sealions> penguinssealionsFunction = (n) ->{
        return new sealions(n.getWeight()-3);
    };

    static class sealions {

        private int weight;

        public sealions(int weight){
            this.weight = weight;
        }
        public int getWeight(){
            return  weight;
        }
    }
    static class penguins {

        private int weight;

        public penguins(int weight){
            this.weight = weight;
        }
        public int getWeight(){
            return  weight;
        }
    }
}
