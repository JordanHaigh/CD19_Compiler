package StateTests;

import CD19.States.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/*
 * Jordan Haigh c3256730 CD19
 * public class CommentOrDivideTests
 * Tests determine if state transition logic is working as intended
 * */
public class CommentOrDivideTests {

    @Test
    public void CommentOrDivideState_SetState_EqualsPassed_Completed(){

        StateMachine sm = new StateMachine();
        sm.setCurrentState(new CommentOrDivideState());

        sm.updateState('=');

        assertTrue(sm.getCurrentState() instanceof CompletedTokenState);

    }

    @Test
    public void CommentOrDivideState_SetState_WhitespacePassed_Completed(){

        StateMachine sm = new StateMachine();
        sm.setCurrentState(new CommentOrDivideState());

        sm.updateState(' ');

        assertTrue(sm.getCurrentState() instanceof CompletedTokenState);

    }

    @Test
    public void CommentOrDivideState_SetState_DashPassed_PossibleComment(){

        StateMachine sm = new StateMachine();
        sm.setCurrentState(new CommentOrDivideState());

        sm.updateState('-');

        assertTrue(sm.getCurrentState() instanceof PossibleCommentState);

    }

    @Test
    public void CommentOrDivideState_SetState_AlphaPassed_Invalid(){

        StateMachine sm = new StateMachine();
        sm.setCurrentState(new CommentOrDivideState());

        sm.updateState('Z');

        assertTrue(sm.getCurrentState() instanceof InvalidStepOneState);

    }

    @Test
    public void CommentOrDivideState_SetState_NumberPassed_Invalid(){

        StateMachine sm = new StateMachine();
        sm.setCurrentState(new CommentOrDivideState());

        sm.updateState('8');

        assertTrue(sm.getCurrentState() instanceof InvalidStepOneState);

    }
    @Test
    public void CommentOrDivideState_SetState_SpecialNotDashOrEqualsPassed_Invalid(){

        StateMachine sm = new StateMachine();
        sm.setCurrentState(new CommentOrDivideState());

        sm.updateState('&');

        assertTrue(sm.getCurrentState() instanceof InvalidStepOneState);

    }

    @Test
    public void CommentOrDivideState_SetState_NewLinePassed_Invalid(){

        StateMachine sm = new StateMachine();
        sm.setCurrentState(new CommentOrDivideState());

        sm.updateState('\n');

        assertTrue(sm.getCurrentState() instanceof InvalidStepOneState);

    }
}
