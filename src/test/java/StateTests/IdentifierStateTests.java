package StateTests;

import CD19.States.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


/*
 * Jordan Haigh CD19
 * public class IdentifierStateTests
 * Tests determine if state transition logic is working as intended
 * */
public class IdentifierStateTests {

    @Test
    public void IdentifierState_SetState_IdentifierPassed_NoStateUpdate() {
        StateMachine sm = new StateMachine();
        sm.setCurrentState(new IdentifierState());

        sm.updateState('a');

        assertTrue(sm.getCurrentState() instanceof IdentifierState);
    }

    @Test
    public void IdentifierState_SetState_NumberPassed_NoStateUpdate() {
        StateMachine sm = new StateMachine();
        sm.setCurrentState(new IdentifierState());

        sm.updateState('5');

        assertTrue(sm.getCurrentState() instanceof IdentifierState);
    }

    @Test
    public void IdentifierState_SetState_SpecialPassed_InvalidState() {
        StateMachine sm = new StateMachine();
        sm.setCurrentState(new IdentifierState());

        sm.updateState('+');

        assertTrue(sm.getCurrentState() instanceof InvalidStepOneState);
    }

    @Test
    public void IdentifierState_SetState_WhitespacePassed_CompletedState() {
        StateMachine sm = new StateMachine();
        sm.setCurrentState(new IdentifierState());

        sm.updateState(' ');

        assertTrue(sm.getCurrentState() instanceof CompletedTokenState);
    }

    @Test
    public void IdentifierState_SetState_NewLinePassed_CompletedState() {
        StateMachine sm = new StateMachine();
        sm.setCurrentState(new IdentifierState());

        sm.updateState('\n');

        assertTrue(sm.getCurrentState() instanceof CompletedTokenState);
    }

    @Test
    public void IdentifierState_SetState_Underscore_NoStateChange() {
        StateMachine sm = new StateMachine();
        sm.setCurrentState(new IdentifierState());

        sm.updateState('_');

        assertTrue(sm.getCurrentState() instanceof IdentifierState);
    }
}
