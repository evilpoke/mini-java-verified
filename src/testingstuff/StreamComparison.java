package testingstuff;

import java.util.stream.IntStream;

public class StreamComparison {


    static long printlnComparison(final int numPrintStatements) {
        final long nonParallelStart = System.currentTimeMillis();
        IntStream.range(1, numPrintStatements).forEach(i ->
                System.out.println(i + " besitzt " + Thread.currentThread().getName())
        );
        final long nonParallelEnd = System.currentTimeMillis();

        IntStream.range(1, numPrintStatements).parallel().forEach(i ->
                System.out.println(i + ": " + Thread.currentThread().getName())
        );
        final long parallelEnd = System.currentTimeMillis();


        return (nonParallelEnd-nonParallelStart) - (parallelEnd -nonParallelEnd);
    }

    static long calcComparisation(final int numCalculations){

        final long nonParallelStart = System.currentTimeMillis();
        IntStream.range(1,numCalculations).forEach( DEATH_IS_COMING ->{
           double somecalc =  Math.pow(Math.log(DEATH_IS_COMING),Math.sqrt(DEATH_IS_COMING));
        });
        final long endnonParallel = System.currentTimeMillis();
        IntStream.range(1,numCalculations).parallel().forEach( DEATH_IS_COMING ->{
            double somecalc =  Math.pow(Math.log(DEATH_IS_COMING),Math.sqrt(DEATH_IS_COMING));
        });
        final long endParallel = System.currentTimeMillis();

        return (endnonParallel-nonParallelStart) - (endParallel - endnonParallel);

    }

    public static void main(String[] args){

        long printdif = printlnComparison(100000);
        long calcdif = calcComparisation(10000);

        System.out.println("***Result***:");
        System.out.println(printdif);
        System.out.println(calcdif);

        /*IntStream.range(0,10000).boxed().mapToDouble(
                Math::sqrt
        ).*/

    }


}
