package StateTests;

import CD19.States.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/*
 * Jordan Haigh CD19
 * public class IntegerStateTests
 * Tests determine if state transition logic is working as intended
 * */
public class IntegerStateTests {

    @Test
    public void IntegerState_SetState_NumberPassed_NoStateUpdate(){

        StateMachine sm = new StateMachine();
        sm.setCurrentState(new IntegerState());

        sm.updateState('1');

        assertTrue(sm.getCurrentState() instanceof IntegerState);

    }

    @Test
    public void IntegerState_SetState_DotPassed_PossibleFloatState(){

        StateMachine sm = new StateMachine();
        sm.setCurrentState(new IntegerState());

        sm.updateState('.');

        assertTrue(sm.getCurrentState() instanceof PossibleFloatState);

    }

    @Test
    public void IntegerState_SetState_AlphaPassed_InvalidState(){

        StateMachine sm = new StateMachine();
        sm.setCurrentState(new IntegerState());

        sm.updateState('a');

        assertTrue(sm.getCurrentState() instanceof InvalidState);

    }

    @Test
    public void IntegerState_SetState_SpecialPassed_InvalidState(){

        StateMachine sm = new StateMachine();
        sm.setCurrentState(new IntegerState());

        sm.updateState('%');

        assertTrue(sm.getCurrentState() instanceof InvalidState);

    }

    @Test
    public void IntegerState_SetState_WhitespacePassed_CompletedTokenState(){

        StateMachine sm = new StateMachine();
        sm.setCurrentState(new IntegerState());

        sm.updateState(' ');

        assertTrue(sm.getCurrentState() instanceof CompletedTokenState);

    }

    @Test
    public void IntegerState_SetState_NewLinePassed_CompletedTokenState(){

        StateMachine sm = new StateMachine();
        sm.setCurrentState(new IntegerState());

        sm.updateState('\n');

        assertTrue(sm.getCurrentState() instanceof CompletedTokenState);

    }
}
