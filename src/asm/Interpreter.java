package asm;


import java.util.HashSet;
import java.util.Set;

public class Interpreter {

    public static int threadidcounter = 0;
    public static final int k = 5;


    private InterpreterThread currentThread = null;
    private InterpreterThread[] interpreterThreads;
    private Instruction[] instructions;

    private Set blockedheapadresses;


    int iter = 0;

    public Interpreter(Instruction[] instructions) {
        this.instructions = instructions;
        interpreterThreads = new InterpreterThread[100];
        blockedheapadresses = new HashSet<Integer>(maxnumberofblocks());
    }

    public int maxnumberofblocks(){
        int counter = 1;
        for (Instruction instruction : instructions) {
            if(instruction instanceof Lock){
            //if (instruction.encode()[0] == Opcode.LOCK.encode()) {
                counter++;
            }
        }
        return counter;
    }


    /**
     * Scans the instructions for the maximum number of threads needed
     *
     * @return
     */
    public int maxnumberofthreads() {
        int counter = 1;
        for (Instruction instruction : instructions) {
           if(instruction instanceof Fork){
            // if (instruction.encode()[0] == Opcode.FORK.encode()) {
                counter++;
            }
        }
        return counter;
    }


    public int execute() {

        interpreterThreads[0] = new InterpreterThread(0, instructions,new int[1024], interpreterThreads,blockedheapadresses,0,0,0);
        interpreterThreads[0].state = 0;


        currentThread = interpreterThreads[0];


        int possiblemainthreadreturn;
        while (true) {

            possiblemainthreadreturn = currentThread.execute();

            if (interpreterThreads[0].state == 2) {
                break;
            }

            switchtonextthread();


        }

        checkforstillopenthreads();
        if(!blockedheapadresses.isEmpty()){
            throw new UnresolvedBlocksException();
        }
            return possiblemainthreadreturn;

    }


    public void checkforstillopenthreads() {
        for (InterpreterThread interpreterThread : interpreterThreads) {
            if (interpreterThread != null && interpreterThread.state != 2) {
                throw new PendingThreadException();
            }
        }
    }




    public void switchtonextthread() {

        boolean found = false;
        int startpoint = iter + 1;
        testing:
        {
            for (int i = startpoint; i < interpreterThreads.length; i++) {
                if (interpreterThreads[i] != null && interpreterThreads[i].state == 0) {
                    iter = i;
                    found = true;
                    break testing;
                }
            }
            for (int i = 0; i < interpreterThreads.length; i++) {
                if (interpreterThreads[i] != null && interpreterThreads[i].state == 0) {
                    iter = i;
                    found =true;
                    break testing;
                }
            }
        }

        if(!found){
            throw new DeadlockException();
        }



        currentThread = interpreterThreads[iter];


    }


}
