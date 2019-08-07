package StateTests;

import CD19.States.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/*
 * Jordan Haigh CD19
 * public class AbsoluteNotEqualsStateTests
 * Tests determine if state transition logic is working as intended
 * */
public class AbsoluteNotEqualsStateTests {

    @Test
    public void AbsoluteNotEqualsState_SetState_WhitespacePassed_CompletedToken(){

        StateMachine sm = new StateMachine();
        sm.setCurrentState(new AbsoluteNotEqualsState());

        sm.updateState(' ');

        assertTrue(sm.getCurrentState() instanceof CompletedTokenState);

    }

    @Test
    public void AbsoluteNotEqualsState_SetState_AlphaPassed_Invalid(){

        StateMachine sm = new StateMachine();
        sm.setCurrentState(new AbsoluteNotEqualsState());

        sm.updateState('d');

        assertTrue(sm.getCurrentState() instanceof InvalidState);

    }

    @Test
    public void AbsoluteNotEqualsState_SetState_NumberPassed_Invalid(){

        StateMachine sm = new StateMachine();
        sm.setCurrentState(new AbsoluteNotEqualsState());

        sm.updateState('0');

        assertTrue(sm.getCurrentState() instanceof InvalidState);

    }

    @Test
    public void AbsoluteNotEqualsState_SetState_SpecialPassed_Invalid(){

        StateMachine sm = new StateMachine();
        sm.setCurrentState(new AbsoluteNotEqualsState());

        sm.updateState('!');

        assertTrue(sm.getCurrentState() instanceof InvalidState);

    }

    @Test
    public void AbsoluteNotEqualsState_SetState_NewLine_Invalid(){

        StateMachine sm = new StateMachine();
        sm.setCurrentState(new AbsoluteNotEqualsState());

        sm.updateState('\n');

        assertTrue(sm.getCurrentState() instanceof InvalidState);

    }


}
