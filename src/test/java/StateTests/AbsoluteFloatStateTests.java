package StateTests;

import CD19.States.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/*
 * Jordan Haigh c3256730 CD19
 * public class AbsoluteFloatStateTests
 * Tests determine if state transition logic is working as intended
 * */
public class AbsoluteFloatStateTests {
    @Test
    public void AbsoluteFloatState_SetState_NumberPassed_NoUpdate(){

        StateMachine sm = new StateMachine();
        sm.setCurrentState(new AbsoluteFloatState());

        sm.updateState('1');

        assertTrue(sm.getCurrentState() instanceof AbsoluteFloatState);

    }

    @Test
    public void AbsoluteFloatState_SetState_AlphaPassed_InvalidState(){

        StateMachine sm = new StateMachine();
        sm.setCurrentState(new AbsoluteFloatState());

        sm.updateState('a');

        assertTrue(sm.getCurrentState() instanceof InvalidStepOneState);

    }


    @Test
    public void AbsoluteFloatState_SetState_SpecialPassed_InvalidState(){

        StateMachine sm = new StateMachine();
        sm.setCurrentState(new AbsoluteFloatState());

        sm.updateState('$');

        assertTrue(sm.getCurrentState() instanceof InvalidStepOneState);

    }

    @Test
    public void AbsoluteFloatState_SetState_WhitespacePassed_CompletedTokenState(){

        StateMachine sm = new StateMachine();
        sm.setCurrentState(new AbsoluteFloatState());

        sm.updateState(' ');

        assertTrue(sm.getCurrentState() instanceof CompletedTokenState);

    }

    @Test
    public void AbsoluteFloatState_SetState_NewLinePassed_CompletedTokenState(){

        StateMachine sm = new StateMachine();
        sm.setCurrentState(new AbsoluteFloatState());

        sm.updateState('\n');

        assertTrue(sm.getCurrentState() instanceof CompletedTokenState);

    }
}
