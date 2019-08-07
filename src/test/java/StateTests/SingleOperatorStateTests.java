package StateTests;

import CD19.States.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/*
 * Jordan Haigh CD19
 * public class SingleOperatorStateTests
 * Tests determine if state transition logic is working as intended
 * */
public class SingleOperatorStateTests {

    @Test
    public void SingleOperatorState_SetState_EqualsPassed_CompletedToken(){

        StateMachine sm = new StateMachine();
        sm.setCurrentState(new SingleOperatorState());

        sm.updateState('=');

        assertTrue(sm.getCurrentState() instanceof CompletedTokenState);

    }

    @Test
    public void SingleOperatorState_SetState_WhitespacePassed_Completed(){

        StateMachine sm = new StateMachine();
        sm.setCurrentState(new SingleOperatorState());

        sm.updateState(' ');

        assertTrue(sm.getCurrentState() instanceof CompletedTokenState);

    }

    @Test
    public void SingleOperatorState_SetState_AlphaPassed_Invalid(){

        StateMachine sm = new StateMachine();
        sm.setCurrentState(new SingleOperatorState());

        sm.updateState('a');

        assertTrue(sm.getCurrentState() instanceof InvalidState);
    }

    @Test
    public void SingleOperatorState_SetState_NumberPassed_Invalid(){

        StateMachine sm = new StateMachine();
        sm.setCurrentState(new SingleOperatorState());

        sm.updateState('1');

        assertTrue(sm.getCurrentState() instanceof InvalidState);
    }

    @Test
    public void SingleOperatorState_SetState_SpecialNotEqualsPassed_Invalid(){

        StateMachine sm = new StateMachine();
        sm.setCurrentState(new SingleOperatorState());

        sm.updateState('@');

        assertTrue(sm.getCurrentState() instanceof InvalidState);
    }
    @Test
    public void SingleOperatorState_SetState_NewLinePassed_Invalid(){

        StateMachine sm = new StateMachine();
        sm.setCurrentState(new SingleOperatorState());

        sm.updateState('\n');

        assertTrue(sm.getCurrentState() instanceof InvalidState);
    }

}
