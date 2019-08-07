package StateTests;

import CD19.States.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/*
 * Jordan Haigh CD19
 * public class PossibleNotEqualsStateTests
 * Tests determine if state transition logic is working as intended
 * */
public class PossibleNotEqualsStateTests {

    @Test
    public void PossibleNotEqualsState_SetState_AlphaPassed_InvalidState(){

        StateMachine sm = new StateMachine();
        sm.setCurrentState(new PossibleNotEqualsState());

        sm.updateState('g');

        assertTrue(sm.getCurrentState() instanceof InvalidState);

    }

    @Test
    public void PossibleNotEqualsState_SetState_NumberPassed_InvalidState(){

        StateMachine sm = new StateMachine();
        sm.setCurrentState(new PossibleNotEqualsState());

        sm.updateState('3');

        assertTrue(sm.getCurrentState() instanceof InvalidState);

    }

    @Test
    public void PossibleNotEqualsState_SetState_SpecialNotEqualsPassed_InvalidState(){

        StateMachine sm = new StateMachine();
        sm.setCurrentState(new PossibleNotEqualsState());

        sm.updateState('?');

        assertTrue(sm.getCurrentState() instanceof InvalidState);

    }

    @Test
    public void PossibleNotEqualsState_SetState_WhitespacePassed_Invalid(){

        StateMachine sm = new StateMachine();
        sm.setCurrentState(new PossibleNotEqualsState());

        sm.updateState(' ');

        assertTrue(sm.getCurrentState() instanceof InvalidState);

    }

    @Test
    public void PossibleNotEqualsState_SetState_NewlinePassed_Invalid(){

        StateMachine sm = new StateMachine();
        sm.setCurrentState(new PossibleNotEqualsState());

        sm.updateState('\n');

        assertTrue(sm.getCurrentState() instanceof InvalidState);

    }

    @Test
    public void PossibleNotEqualsState_SetState_EqualsPassed_Completed(){

        StateMachine sm = new StateMachine();
        sm.setCurrentState(new PossibleNotEqualsState());

        sm.updateState('=');

        assertTrue(sm.getCurrentState() instanceof CompletedTokenState);

    }
}
