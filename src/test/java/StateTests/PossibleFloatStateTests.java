package StateTests;

import CD19.States.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/*
 * Jordan Haigh CD19
 * public class PossibleFloatStateTests
 * Tests determine if state transition logic is working as intended
 * */
public class PossibleFloatStateTests {

    @Test
    public void PossibleFloatState_SetState_NumberPassed_AbsoluteFloat(){

        StateMachine sm = new StateMachine();
        sm.setCurrentState(new PossibleFloatState());

        sm.updateState('1');

        assertTrue(sm.getCurrentState() instanceof AbsoluteFloatState);

    }

    @Test
    public void PossibleFloatState_SetState_AlphaPassed_Invalid(){

        StateMachine sm = new StateMachine();
        sm.setCurrentState(new PossibleFloatState());

        sm.updateState('a');

        assertTrue(sm.getCurrentState() instanceof InvalidStepTwoState);
    }

    @Test
    public void PossibleFloatState_SetState_SpecialPassed_Invalid(){

        StateMachine sm = new StateMachine();
        sm.setCurrentState(new PossibleFloatState());

        sm.updateState('.');

        assertTrue(sm.getCurrentState() instanceof InvalidStepTwoState);
    }

    @Test
    public void PossibleFloatState_SetState_WhitespacePassed_Invalid(){

        StateMachine sm = new StateMachine();
        sm.setCurrentState(new PossibleFloatState());

        sm.updateState(' ');

        assertTrue(sm.getCurrentState() instanceof InvalidStepTwoState);
    }

    @Test
    public void PossibleFloatState_SetState_NewLinePassed_Invalid(){

        StateMachine sm = new StateMachine();
        sm.setCurrentState(new PossibleFloatState());

        sm.updateState('\n');

        assertTrue(sm.getCurrentState() instanceof InvalidStepTwoState);
    }

}
