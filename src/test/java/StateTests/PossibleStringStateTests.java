package StateTests;

import CD19.States.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/*
 * Jordan Haigh CD19
 * public class PossibleStringStateTests
 * Tests determine if state transition logic is working as intended
 * */

public class PossibleStringStateTests {
    @Test
    public void PossibleStringState_SetState_AlphaPassed_NoStateUpdate(){

        StateMachine sm = new StateMachine();
        sm.setCurrentState(new PossibleStringState());

        sm.updateState('g');

        assertTrue(sm.getCurrentState() instanceof PossibleStringState);

    }

    @Test
    public void PossibleStringState_SetState_NumberPassed_NoStateUpdate(){

        StateMachine sm = new StateMachine();
        sm.setCurrentState(new PossibleStringState());

        sm.updateState('8');

        assertTrue(sm.getCurrentState() instanceof PossibleStringState);

    }

    @Test
    public void PossibleStringState_SetState_SpecialNotQuotePassed_NoStateUpdate(){

        StateMachine sm = new StateMachine();
        sm.setCurrentState(new PossibleStringState());

        sm.updateState('%');

        assertTrue(sm.getCurrentState() instanceof PossibleStringState);

    }

    @Test
    public void PossibleStringState_SetState_QuotePassed_AbsoluteString(){

        StateMachine sm = new StateMachine();
        sm.setCurrentState(new PossibleStringState());

        sm.updateState('\"');

        assertTrue(sm.getCurrentState() instanceof CompletedTokenState);

    }

    @Test
    public void PossibleStringState_SetState_WhitespacePassed_NoStateUpdate(){

        StateMachine sm = new StateMachine();
        sm.setCurrentState(new PossibleStringState());

        sm.updateState(' ');

        assertTrue(sm.getCurrentState() instanceof PossibleStringState);

    }

    @Test
    public void PossibleStringState_SetState_NewLinePassed_InvalidState(){

        StateMachine sm = new StateMachine();
        sm.setCurrentState(new PossibleStringState());

        sm.updateState('\n');

        assertTrue(sm.getCurrentState() instanceof InvalidStepOneState);

    }
}
