package StateTests;

import CD19.States.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/*
 * Jordan Haigh c3256730 CD19
 * public class AbsoluteCommentStateTests
 * Tests determine if state transition logic is working as intended
 * */
public class AbsoluteCommentStateTests {

    @Test
    public void AbsoluteCommentState_SetState_AlphaPassed_NoStateChange(){

        StateMachine sm = new StateMachine();
        sm.setCurrentState(new AbsoluteCommentState());

        sm.updateState('a');

        assertTrue(sm.getCurrentState() instanceof AbsoluteCommentState);

    }

    @Test
    public void AbsoluteCommentState_SetState_NumberPassed_NoStateChange(){

        StateMachine sm = new StateMachine();
        sm.setCurrentState(new AbsoluteCommentState());

        sm.updateState('6');

        assertTrue(sm.getCurrentState() instanceof AbsoluteCommentState);

    }

    @Test
    public void AbsoluteCommentState_SetState_SpecialPassed_NoStateChange(){

        StateMachine sm = new StateMachine();
        sm.setCurrentState(new AbsoluteCommentState());

        sm.updateState('/');

        assertTrue(sm.getCurrentState() instanceof AbsoluteCommentState);

    }

    @Test
    public void AbsoluteCommentState_SetState_WhitespacePassed_NoStateChange(){

        StateMachine sm = new StateMachine();
        sm.setCurrentState(new AbsoluteCommentState());

        sm.updateState(' ');

        assertTrue(sm.getCurrentState() instanceof AbsoluteCommentState);

    }


    @Test
    public void AbsoluteCommentState_SetState_NewLinePassed_CompletedCommentState(){

        StateMachine sm = new StateMachine();
        sm.setCurrentState(new AbsoluteCommentState());

        sm.updateState('\n');

        assertTrue(sm.getCurrentState() instanceof CompletedCommentTokenState);

    }
}
