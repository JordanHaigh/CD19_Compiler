import CD19.States.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/*
 * Jordan Haigh CD19
 * public class AbsoluteStringStateTests
 * Tests determine if state transition logic is working as intended
 * */

public class AbsoluteStringStateTests {

    @Test
    public void PossibleStringState_SetState_AlphaPassed_InvalidState(){

        StateMachine sm = new StateMachine();
        sm.setCurrentState(new AbsoluteStringState());

        sm.updateState('g');

        assertTrue(sm.getCurrentState() instanceof InvalidState);

    }

    @Test
    public void PossibleStringState_SetState_NumberPassed_InvalidState(){

        StateMachine sm = new StateMachine();
        sm.setCurrentState(new AbsoluteStringState());

        sm.updateState('9');

        assertTrue(sm.getCurrentState() instanceof InvalidState);

    }

    @Test
    public void PossibleStringState_SetState_SpecialPassed_InvalidState(){

        StateMachine sm = new StateMachine();
        sm.setCurrentState(new AbsoluteStringState());

        sm.updateState('^');

        assertTrue(sm.getCurrentState() instanceof InvalidState);

    }

    @Test
    public void PossibleStringState_SetState_WhitespacePassed_CompletedToken(){

        StateMachine sm = new StateMachine();
        sm.setCurrentState(new AbsoluteStringState());

        sm.updateState(' ');

        assertTrue(sm.getCurrentState() instanceof CompletedTokenState);

    }

    @Test
    public void PossibleStringState_SetState_NewLinePassed_CompletedToken(){

        StateMachine sm = new StateMachine();
        sm.setCurrentState(new AbsoluteStringState());

        sm.updateState('\n');

        assertTrue(sm.getCurrentState() instanceof CompletedTokenState);

    }

}
