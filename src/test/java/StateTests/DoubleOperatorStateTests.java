package StateTests;

import CD19.States.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/*
 * Jordan Haigh CD19
 * public class DoubleOperatorStateTests
 * Tests determine if state transition logic is working as intended
 * */
public class DoubleOperatorStateTests {

    @Test
    public void DoubleOperatorState_SetState_WhitespacePassed_Completed(){

        StateMachine sm = new StateMachine();
        sm.setCurrentState(new DoubleOperatorState());

        sm.updateState(' ');

        assertTrue(sm.getCurrentState() instanceof CompletedTokenState);

    }

    @Test
    public void DoubleOperatorState_SetState_AlphaPassed_Invalid(){

        StateMachine sm = new StateMachine();
        sm.setCurrentState(new DoubleOperatorState());

        sm.updateState('a');

        assertTrue(sm.getCurrentState() instanceof InvalidState);

    }

    @Test
    public void DoubleOperatorState_SetState_NumberPassed_Invalid(){

        StateMachine sm = new StateMachine();
        sm.setCurrentState(new DoubleOperatorState());

        sm.updateState('8');

        assertTrue(sm.getCurrentState() instanceof InvalidState);

    }

    @Test
    public void DoubleOperatorState_SetState_SpecialPassed_Invalid(){

        StateMachine sm = new StateMachine();
        sm.setCurrentState(new DoubleOperatorState());

        sm.updateState(';');

        assertTrue(sm.getCurrentState() instanceof InvalidState);

    }

    @Test
    public void DoubleOperatorState_SetState_NewLinePassed_Invalid(){

        StateMachine sm = new StateMachine();
        sm.setCurrentState(new DoubleOperatorState());

        sm.updateState('\n');

        assertTrue(sm.getCurrentState() instanceof InvalidState);

    }
}
