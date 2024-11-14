import java.util.function.Function;

public class Worker<T, U> extends Thread {

    private Function<T, U> operator;
    private Queue income, outcome;

    public Worker(Queue<T> workincome, Queue<U> workoutcome, Function<T, U> function) {
        operator = function;
        income = workincome;
        outcome = workoutcome;
    }

    public void run() {
        try {
           while (true) {

                T subject = (T) income.dequeue();
               // income.notify();
                U talkback = operator.apply(subject);
                outcome.enqueue(talkback);
               // outcome.notify();

            }


        } catch (InterruptedException e) {
            System.out.println("hi");
            //exit function
        }

    }




}
