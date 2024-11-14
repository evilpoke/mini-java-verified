package asm;

import codegen.NegativeArraySizeException;

import java.util.Set;

public class InterpreterThread implements AsmVisitor {

    private int id;


    private int[] stack = new int[128];
    private int[] heap;             //sync
    private int stackPointer = -1;
    private int programCounter = 0;
    private int framePointer = -1;
    private Instruction[] program;  //sync
    private boolean halted;
    private int[] registers = new int[2];
    private  Integer currentheapindex;   //sync
    private Integer lastarraylength;    //sync
    private Integer maxheapindex;       //sync


    public int instructionfirings = Interpreter.k;


    public int state;  //0:ready    1:joining   2:finished  3: blocked

    public int waiting_for;  //hmm

    public int blockedbyheapadress;

    public InterpreterThread[] threads;  //sync

    private boolean declAllowed = true;

    public Set blockedheapadresses;     //sync

    public int returnvalue_for_future_askers;

    //public Map<Integer,Integer> heapadress_to_threadreturnvalue = new HashMap<>();

    public static int true_() {
        return -1;
    }

    public static int false_() {
        return 0;
    }

  /*  public InterpreterThread(){ //TODO: to be removed...or not?

    }*/


    public InterpreterThread(int id, Instruction[] program, int[] heap, InterpreterThread[] threads, Set blockedheapadresses, Integer currentheapindex, Integer lastarraylength, Integer maxheapindex) {
        this.program = program;
        this.heap = heap;
        this.threads = threads;
        this.id = id;
        this.blockedheapadresses = blockedheapadresses;

        this.maxheapindex = maxheapindex;
        this.currentheapindex = currentheapindex;
        this.lastarraylength = lastarraylength;

    }


    public int pop() {
        if (stackPointer < 0)
            throw new StackUnderflowException();
        return stack[stackPointer--];
    }

    public void push(int value) {
        stackPointer++;
        if (stackPointer >= stack.length)
            throw new StackOverflowException();
        stack[stackPointer] = value;
    }

    /**
     * Accesses heap at given index
     *
     * @param i
     * @return
     */
    private int accessheapatadress(int i) {
        if (i < 0 || i >= heap.length || i > maxheapindex-1) {
            throw new IllegalHeapAccessException(i);
        }
        return heap[i];
    }


    public int execute() {
        while (!halted) {
            if (instructionfirings == 0) {
                instructionfirings = Interpreter.k;
                return 42;
            }

            Instruction next = program[programCounter];
            programCounter++;
            instructionfirings--;
            next.accept(this);
            //if(state )

            if (state == 2 && id != 0) { //not mainthread


                return 42;
            }


            if (state == 2 && id == 0) {
                return stack[stackPointer];
            }

            if (state != 0) {
                instructionfirings = Interpreter.k;
                return 42;
            }


        }


        return stack[stackPointer];

    }


    //******************************************************************************************************************

    @Override
    public void visit(Add add) {
        int a = pop();
        int b = pop();
        push(a + b);
        declAllowed = false;
    }

    @Override
    public void visit(Decl decl) {
        if (!declAllowed)
            throw new InvalidDeclarationException();
        if (decl.getCount() < 0)
            throw new InvalidStackAllocationException();
        stackPointer += decl.getCount();
    }

    @Override
    public void visit(And and) {
        int a = pop();
        int b = pop();
        push(a & b);
        declAllowed = false;
    }

    @Override
    public void visit(Brc brc) {
        if (brc.getTarget() < 0 || brc.getTarget() > program.length)
            throw new InvalidJumpTargetException();
        int cond = pop();
        if (cond == -1)
            programCounter = brc.getTarget();
        declAllowed = false;
    }

    @Override
    public void visit(Call call) {
        if (call.getArgCount() < 0)
            throw new InvalidNumberOfMethodParametersException();
        int functionAddress = pop();
        if (functionAddress < 0 || functionAddress > program.length)
            throw new InvalidMethodAddressException();
        int[] arguments = new int[call.getArgCount()];
        for (int i = 0; i < arguments.length; i++)
            arguments[i] = pop();
        push(framePointer);
        push(programCounter);
        for (int i = 0; i < arguments.length; i++)
            push(arguments[arguments.length - 1 - i]);
        framePointer = stackPointer;
        programCounter = functionAddress;
        declAllowed = true;
    }

    @Override
    public void visit(Cmp cmp) {
        int a = pop();
        int b = pop();
        switch (cmp.getCompareType()) {
            case EQ:
                push(a == b ? true_() : false_());
                break;
            case LT:
                push(a < b ? true_() : false_());
        }
        declAllowed = false;
    }

    @Override
    public void visit(Div div) {
        int a = pop();
        int b = pop();
        push(a / b);
        declAllowed = false;
    }


    @Override
    public void visit(Halt halt) {
        if (id != 0) {
            throw new HaltOutsideMainThreadException();
        }

        int found = -1;

        for (InterpreterThread interpreterThread : threads) {
            if (interpreterThread != null && interpreterThread.id != id && interpreterThread.state == 0) {
                found = interpreterThread.id;
                break;
            }
        }
        if (found != -1) {
            //halt must'nt be executed, there's still (heaven knows why) another thread up and running
              /*  programCounter--; //next time this thread is executed, it is again checked
                state = 1;
                waiting_for= found;*/ //nope: i'm gonna throw up instead

            throw new PendingThreadException();

        } else {

            halted = true;
            state = 2; //finished
        }
    }

    @Override
    public void visit(Ldi ldi) {
        push(ldi.getValue());
        declAllowed = false;
    }

    @Override
    public void visit(Lfs lds) {
        int stackAddress = framePointer + lds.getIndex();
        if (stackAddress < 0 || stackAddress >= stack.length)
            throw new InvalidStackAccessException();
        push(stack[stackAddress]);
        declAllowed = false;
    }

    @Override
    public void visit(Mod mod) {
        int a = pop();
        int b = pop();
        push(a % b);
        declAllowed = false;
    }

    @Override
    public void visit(Mul mul) {
        int a = pop();
        int b = pop();
        push(a * b);
        declAllowed = false;
    }

    @Override
    public void visit(Nop nop) {
        declAllowed = false;
    }

    @Override
    public void visit(Not not) {
        int a = pop();
        push(~a);
        declAllowed = false;
    }

    @Override
    public void visit(Or or) {
        int a = pop();
        int b = pop();
        push(a | b);
        declAllowed = false;
    }

    @Override
    public void visit(Pop pop) {
        int a = pop();
        registers[pop.getRegister()] = a;
        declAllowed = false;
    }

    @Override
    public void visit(Push push) {
        push(registers[push.getRegister()]);
        declAllowed = false;
    }

    @Override
    public void visit(In read) {
        int value = Terminal.askInt("Zahl eingeben: ");
        push(value);
        declAllowed = false;
    }

    @Override
    public void visit(Sts sts) {
        int stackAddress = framePointer + sts.getIndex();
        if (stackAddress < 0 || stackAddress >= stack.length)
            throw new InvalidStackAccessException();
        int value = pop();
        stack[stackAddress] = value;
        declAllowed = false;
    }

    @Override
    public void visit(Sub sub) {
        int a = pop();
        int b = pop();
        push(a - b);
        declAllowed = false;
    }

    @Override
    public void visit(Out write) {
        int value = pop();
        System.out.println(value);
        declAllowed = false;
    }

    @Override
    public void visit(Alloc alloc) {

        int arraysize = pop();
        if (arraysize < 0) {
            throw new NegativeArraySizeException();
        }

        currentheapindex += lastarraylength;
        lastarraylength = arraysize;
        if (currentheapindex + arraysize >= heap.length) {
            throw new HeapOverflowException();
        }
        maxheapindex = currentheapindex + arraysize;
        push(currentheapindex);
//    stackPointer++;

    }

    @Override
    public void visit(Lfh lfh) {
        int index = pop();
        int data = accessheapatadress(index);
        push(data);
    }

    @Override
    public void visit(Sth sth) {
        int addresstostore = pop();
        int value = pop();
        accessheapatadress(addresstostore);  //check if the array actually exists
        heap[addresstostore] = value;
        //return nothing
    }

    @Override
    public void visit(Fork fork) {

        int adressofnewthread = pop();
        int[] parameters = new int[fork.getArgCount()];
        for (int i : parameters) {
            parameters[i] = pop();
        }
        int next = ++Interpreter.threadidcounter;
        push(next);

        if (next == 2) {
            System.getProperties().toString();
        }

        InterpreterThread interpreterThread = new InterpreterThread(next, program, heap, this.threads, blockedheapadresses,currentheapindex,lastarraylength,maxheapindex);
        threads[next] = interpreterThread;
        interpreterThread.push(-1);
        interpreterThread.push(-1);

        for (int i = parameters.length - 1; i >= 0; i--) {
            interpreterThread.push(parameters[i]);
        }
        interpreterThread.framePointer = interpreterThread.stackPointer;
        interpreterThread.programCounter = adressofnewthread;
        interpreterThread.returnvalue_for_future_askers = 42; //is doesn't matter
        interpreterThread.state = 0;


    }

    @Override
    public void visit(Join join) {
        int joinedthreadid = pop();
        if (joinedthreadid == 0 || joinedthreadid == id) {
            throw new IllegalJoinStatement();
        }
        boolean found = false;
        boolean doublefound = false;
        for (InterpreterThread interpreterThread : threads) {
            if (interpreterThread.id == joinedthreadid) {
                found = true;
                if (interpreterThread.state == 2) {
                    doublefound = true;
                    push(interpreterThread.returnvalue_for_future_askers);

                }


                break;
            }
        }
        if (!found) {
            throw new ThreadDoesNotExistException();
        }


        if (!doublefound) {
            state = 1;
            waiting_for = joinedthreadid;
        }

    }

    @Override
    public void visit(Lock lock) {
        int heapobj_tolock = pop();

        if (blockedheapadresses.contains(heapobj_tolock)) {

            blockedbyheapadress = heapobj_tolock;
            state = 3; //blocked

        } else {

            blockedheapadresses.add(heapobj_tolock);

        }


    }

    @Override
    public void visit(Unlock unlock) {
        int heapobj_unlock = pop();

        if (!blockedheapadresses.contains(heapobj_unlock)) {
            throw new IllegalUnlockException();
        } else {
            blockedheapadresses.remove(heapobj_unlock);


            for (InterpreterThread interpreterThread : threads) {
                if (interpreterThread != null && interpreterThread.state == 3 && interpreterThread.blockedbyheapadress == heapobj_unlock) {
                    interpreterThread.state = 0;
                    blockedheapadresses.add(heapobj_unlock);
                    break;
                    //no break cause there could be more than one threads blocked by the same heapaddr  -NO there SHOULD be a break
                }
            }


        }


    }

    @Override
    public void visit(Return ret) {
        if (ret.getCells() < 0)
            throw new InvalidStackFrameSizeException();
        int retVal = pop();
        for (int i = 0; i < ret.getCells(); i++)
            pop();
        programCounter = pop();
        if (programCounter >= program.length) {
            throw new InvalidReturnAddressException();
        }
        if (id != 0) {  //we are in a subthread, leaving a method

            if (programCounter < 0) { //we are indeed jumping back to a father thread
                state = 2;

                for (InterpreterThread interpreterThread : threads) { //for those who already asked
                    if (interpreterThread != null && interpreterThread.state == 1 && interpreterThread.waiting_for == id) {
                        interpreterThread.push(retVal);
                        interpreterThread.state = 0;
                    }
                }
                //and now for those theads who haven't asked for the value yet:
                returnvalue_for_future_askers = retVal;

                try {
                    while (true) {
                        pop();
                    }
                } catch (StackUnderflowException ignored) {
                }
                //This thread's stack is now destroyed as its not needed anymore (state = 2)


            } else {  //we are jumping back..yes.. but not to another thread

                framePointer = pop();
                if (framePointer >= stack.length)
                    throw new InvalidFramePointerException();
                push(retVal);
                declAllowed = false;
                //state stays up and running
            }

        } else { //we are in the main thread leaving a method:
            if (programCounter < 0) {  //it's the mainmethod we are leaving

                push(retVal);
                state = 2;
                //killing the mainthread and simply returning the value

            } else { //it's a submethod we are leaving

                framePointer = pop();
                if (framePointer >= stack.length)
                    throw new InvalidFramePointerException();
                push(retVal);
                declAllowed = false;
                //state up and running
            }

        }


    }


}

