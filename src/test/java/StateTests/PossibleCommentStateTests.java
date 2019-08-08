package StateTests;

import CD19.States.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/*
 * Jordan Haigh c3256730 CD19
 * public class PossibleCommentStateTests
 * Tests determine if state transition logic is working as intended
 * */
public class PossibleCommentStateTests {

    @Test
    public void PossibleCommentState_SetState_DashPassed_AbsoluteComment(){

        StateMachine sm = new StateMachine();
        sm.setCurrentState(new PossibleCommentState());

        sm.updateState('-');

        assertTrue(sm.getCurrentState() instanceof AbsoluteCommentState);

    }

    @Test
    public void PossibleCommentState_SetState_AlphaPassed_Invalid(){

        StateMachine sm = new StateMachine();
        sm.setCurrentState(new PossibleCommentState());

        sm.updateState('a');

        assertTrue(sm.getCurrentState() instanceof InvalidStepOneState);

    }

    @Test
    public void PossibleCommentState_SetState_NumberPassed_Invalid(){

        StateMachine sm = new StateMachine();
        sm.setCurrentState(new PossibleCommentState());

        sm.updateState('3');

        assertTrue(sm.getCurrentState() instanceof InvalidStepOneState);

    }

    @Test
    public void PossibleCommentState_SetState_SpecialNotDashPassed_Invalid(){

        StateMachine sm = new StateMachine();
        sm.setCurrentState(new PossibleCommentState());

        sm.updateState(')');

        assertTrue(sm.getCurrentState() instanceof InvalidStepOneState);

    }

    @Test
    public void PossibleCommentState_SetState_WhitespacePassed_Invalid(){

        StateMachine sm = new StateMachine();
        sm.setCurrentState(new PossibleCommentState());

        sm.updateState(' ');

        assertTrue(sm.getCurrentState() instanceof InvalidStepOneState);

    }

    @Test
    public void PossibleCommentState_SetState_NewLinePassed_Invalid(){

        StateMachine sm = new StateMachine();
        sm.setCurrentState(new PossibleCommentState());

        sm.updateState('\n');

        assertTrue(sm.getCurrentState() instanceof InvalidStepOneState);

    }
}
