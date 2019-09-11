package StateTests;

import CD19.Scanner.States.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/*
 * Jordan Haigh CD19
 * public class PossibleRecoveredStateTests
 * Tests determine if state transition logic is working as intended
 * */
public class PossibleRecoveredStateTests {

    @Test
    public void PossibleRecoveredState_SetState_EqualsPassed_InvalidStepTwo(){
        StateMachine sm = new StateMachine();
        sm.setCurrentState(new PossibleRecoveredState());
        sm.updateState('=');

        assertTrue(sm.getCurrentState() instanceof InvalidStepTwoState);

    }

    @Test
    public void PossibleRecoveredState_SetState_DelimPassed_Completed(){
        StateMachine sm = new StateMachine();
        sm.setCurrentState(new PossibleRecoveredState());
        sm.updateState(' ');

        assertTrue(sm.getCurrentState() instanceof CompletedTokenState);

    }

    @Test
    public void PossibleRecoveredState_SetState_AlphaPassed_Undefined(){
        StateMachine sm = new StateMachine();
        sm.setCurrentState(new PossibleRecoveredState());
        sm.updateState('a');

        assertTrue(sm.getCurrentState() instanceof UndefinedState);

    }

    @Test
    public void PossibleRecoveredState_SetState_NumberPassed_Undefined(){
        StateMachine sm = new StateMachine();
        sm.setCurrentState(new PossibleRecoveredState());
        sm.updateState('9');

        assertTrue(sm.getCurrentState() instanceof UndefinedState);

    }

    @Test
    public void PossibleRecoveredState_SetState_UndefinedPassed_Undefined(){
        StateMachine sm = new StateMachine();
        sm.setCurrentState(new PossibleRecoveredState());
        sm.updateState('#');

        assertTrue(sm.getCurrentState() instanceof UndefinedState);

    }

    @Test
    public void PossibleRecoveredState_SetState_ExclaimPassed_UnrecoveredNotEquals(){
        StateMachine sm = new StateMachine();
        sm.setCurrentState(new PossibleRecoveredState());
        sm.updateState('!');

        assertTrue(sm.getCurrentState() instanceof UnrecoveredNotEqualsState);

    }
}
