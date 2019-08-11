package StateTests;

import CD19.States.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/*
 * Jordan Haigh CD19
 * public class UnrecoveredNotEqualsStateTests
 * Tests determine if state transition logic is working as intended
 * */
public class UnrecoveredNotEqualsStateTests {

    @Test
    public void UnrecoveredNotEqualsState_SetState_ExclaimPassed_Stay(){
        StateMachine sm = new StateMachine();
        sm.setCurrentState(new UnrecoveredNotEqualsState());
        sm.updateState('!');

        assertTrue(sm.getCurrentState() instanceof UnrecoveredNotEqualsState);

    }

    @Test
    public void UnrecoveredNotEqualsState_SetState_NdefPassed_Stay(){
        StateMachine sm = new StateMachine();
        sm.setCurrentState(new UnrecoveredNotEqualsState());
        sm.updateState('@');

        assertTrue(sm.getCurrentState() instanceof UnrecoveredNotEqualsState);

    }

    @Test
    public void UnrecoveredNotEqualsState_SetState_AlphaPassed_InvalidStepOne(){
        StateMachine sm = new StateMachine();
        sm.setCurrentState(new UnrecoveredNotEqualsState());
        sm.updateState('a');

        assertTrue(sm.getCurrentState() instanceof InvalidStepOneState);

    }


    @Test
    public void UnrecoveredNotEqualsState_SetState_NumberPassed_InvalidStepOne(){
        StateMachine sm = new StateMachine();
        sm.setCurrentState(new UnrecoveredNotEqualsState());
        sm.updateState('2');

        assertTrue(sm.getCurrentState() instanceof InvalidStepOneState);

    }

    @Test
    public void UnrecoveredNotEqualsState_SetState_SpecialNotExclaimOrNdefPassed_InvalidStepOne(){
        StateMachine sm = new StateMachine();
        sm.setCurrentState(new UnrecoveredNotEqualsState());
        sm.updateState('+');

        assertTrue(sm.getCurrentState() instanceof InvalidStepOneState);

    }


    @Test
    public void UnrecoveredNotEqualsState_SetState_EqualsPassed_InvalidStepTwo(){
        StateMachine sm = new StateMachine();
        sm.setCurrentState(new UnrecoveredNotEqualsState());
        sm.updateState('=');

        assertTrue(sm.getCurrentState() instanceof InvalidStepTwoState);

    }
}
