import nz.ac.waikato.modeljunit.*;
import nz.ac.waikato.modeljunit.coverage.ActionCoverage;
import nz.ac.waikato.modeljunit.coverage.StateCoverage;
import nz.ac.waikato.modeljunit.coverage.TransitionCoverage;

import java.io.FileNotFoundException;

/**
 *  Very first version of the FSM.
 *  Automaton focusing on the card authentication tries, abstracting the bills and money (no withdraw operation)
 */
public class SampleFSM implements FsmModel
{
    /** Variable representing the current state */
    private int state;
    private boolean validTransaction;

    /** Variable representing the current state */
    private ATMAdapter adapter;

    private boolean connect = false;

    /**
     *  Constructor. Initializes the data.
     */
    public SampleFSM()
    {
        state = 0;
        validTransaction = false;
        adapter = new ATMAdapter();
    }

    /**
     *  Inherited from the FsmModel interface.
     *  Provides a Comparable object that characterizes the current state.
     */
    public String getState()
    {
        return String.valueOf(state)+validTransaction;
    }

    /**
     *  Inherited from the FsmModel interface.
     *  Provides a Comparable object that characterizes the current state.
     */
    public void reset(boolean testing)
    {
        state = 0;
        validTransaction = false;
        adapter = new ATMAdapter();
    }


    public boolean insertCardValidGuard() { return state == 0; }

    @Action
    public void insertCardValid()
    {
        // evolution of the state
        state = 1;
        // realizes the transition on the System Under Test
        if (connect) adapter.insertCardValid();
    }


    public boolean insertCardBlockedGuard() { return state == 0; }

    @Action
    public void insertCardBlocked()
    {
        // evolution of the state
        state = 2;
        // realizes the transition on the System Under Test
        if (connect) adapter.insertCardBlocked();
    }



//    public boolean cancelGuard() { return state == 1; }
//
//    @Action
//    public void cancel()
//    {
//        // evolution of the state
//        state = 0;
//        // transmits the operation to the System Under Test
//        if (connect) adapter.cancel();
//    }


    public boolean inputPinValidGuard() { return state == 1; }

    @Action
    public void inputPinValid()
    {
        // evolution of the state
        state = 3;
        // realizes the transition on the System Under Test
        if (connect) adapter.inputPinValid();
    }


    public boolean inputPinNoValidGuard() { return state == 1; }

    @Action
    public void inputPinNoValid()
    {
        // evolution of the state
        state = 1;
        // realizes the transition on the System Under Test
        if (connect) adapter.inputPinNoValid();
    }


    public boolean inputPinBlockedGuard() { return state == 1; }

    @Action
    public void inputPinBlocked()
    {
        // evolution of the state
        state = 2;
        // realizes the transition on the System Under Test
        if (connect) adapter.inputPinBlocked();
    }


    public boolean chooseAmountValidGuard() { return state == 3; }

    @Action
    public void chooseAmountValid()
    {
        // evolution of the state
        state = 2;
        validTransaction = true;
        // realizes the transition on the System Under Test
        if (connect) adapter.chooseAmountValid();
    }

    public boolean chooseAmountNoValidGuard() { return state == 3; }

    @Action
    public void chooseAmountNoValid()
    {
        // evolution of the state
        state = 3;
        // realizes the transition on the System Under Test
        if (connect) adapter.chooseAmountNoValid();

    }


    public boolean takeCardGuard() { return state == 2; }

    @Action
    public void takeCard()
    {
        state = 4;
        if (connect) adapter.takeCard();
    }


    public boolean wait6SecondsTakingCardGuard() { return state == 2; }

    @Action
    public void wait6SecondsTakingCard()
    {
        state = 5;
        if (connect) adapter.wait6SecondsTakingCard();
    }


    public boolean takeBillsGuard() { return (state == 4 && validTransaction); }

    @Action
    public void takeBills()
    {
        state = 6;
        if (connect) adapter.takeBills();
    }


    public boolean wait6SecondsTakingBillsGuard() { return (state == 4 && validTransaction); }

    @Action
    public void wait6SecondsTakingBills()
    {
        state = 7;
        if (connect) adapter.wait6SecondsTakingBills();
    }

    /***
     * Main program
     */
    public static void main(String[] argv) {

        // initialization of the model
        SampleFSM model = new SampleFSM();

        /**
         * Test a system by making random walks through an EFSM model of the system.
         */
        //Tester tester = new RandomTester(model);

        /**
         * Test a system by making greedy walks through an EFSM model of the system.
         * A greedy random walk gives preference to transitions that have never been taken before.
         * Once all transitions out of a state have been taken, it behaves the same as a random walk.
         */
        Tester tester = new GreedyTester(model);

        /**
         * Creates a GreedyTester that will terminate each test sequence after getLoopTolerance() visits to a state.
         */
        //AllRoundTester tester = new AllRoundTester(model);
        //tester.setLoopTolerance(3);

        /**
         * A test generator that looks N-levels ahead in the graph. It chooses the highest-valued
         * transition (action) that is enabled in the current state.
         * DEPTH = How far should we look ahead?
         * NEW_ACTION = How worthwhile is it to use a completely new action?
         * NEW_TRANS = How worthwhile is it to explore a new transition?
         */
        //LookaheadTester tester = new LookaheadTester(model);
        //tester.setDepth(10);
        //tester.setNewActionValue(50);
        //tester.setNewTransValue(100);  // priority on new transitions w.r.t. new actions

        // computes the graph to get coverage measure information
        try {
            tester.buildGraph().printGraphDot("graph.dot");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        model.connect = true;

        // usual paramaterization
        tester.addListener(new VerboseListener());
        tester.addListener(new StopOnFailureListener());
        tester.addCoverageMetric(new TransitionCoverage());
        tester.addCoverageMetric(new StateCoverage() {
            @Override
            public String getName() {
                return "Total state coverage";
            }
        });
        tester.addCoverageMetric(new ActionCoverage());

        // run the test generation (10 steps)  <-- CAN BE INCREASED TO PRODUCE MORE TESTS!
        tester.generate(80);

        // prints the coverage and quits the execution
        tester.printCoverage();
    }
}
