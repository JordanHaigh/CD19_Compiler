package StateTests;

import CD19.Scanner.States.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/*
 * Jordan Haigh CD19
 * public class UndefinedStateTests
 * Tests determine if state transition logic is working as intended
 * */
public class UndefinedStateTests {


    @Test
    public void UndefinedState_SetState_AlphaPassed_InvalidStepOneState(){
        StateMachine sm = new StateMachine();
        sm.setCurrentState(new UndefinedState());
        sm.updateState('a');

        assertTrue(sm.getCurrentState() instanceof InvalidStepOneState);

    }
    @Test
    public void UndefinedState_SetState_NumberPassed_InvalidStepOneState(){
        StateMachine sm = new StateMachine();
        sm.setCurrentState(new UndefinedState());
        sm.updateState('0');

        assertTrue(sm.getCurrentState() instanceof InvalidStepOneState);

    }

    @Test
    public void UndefinedState_SetState_SpecialNotExclaimOrUndefinedPassed_InvalidStepOneState(){
        StateMachine sm = new StateMachine();
        sm.setCurrentState(new UndefinedState());
        sm.updateState('+');

        assertTrue(sm.getCurrentState() instanceof InvalidStepOneState);

    }

    @Test
    public void UndefinedState_SetState_BracketPassed_InvalidStepOneState(){
        StateMachine sm = new StateMachine();
        sm.setCurrentState(new UndefinedState());
        sm.updateState('[');

        assertTrue(sm.getCurrentState() instanceof InvalidStepOneState);

    }


    @Test
    public void UndefinedState_SetState_QuotePassed_InvalidStepOneState(){
        StateMachine sm = new StateMachine();
        sm.setCurrentState(new UndefinedState());
        sm.updateState('\"');

        assertTrue(sm.getCurrentState() instanceof InvalidStepOneState);

    }

    @Test
    public void UndefinedState_SetState_DashPassed_InvalidStepOneState(){
        StateMachine sm = new StateMachine();
        sm.setCurrentState(new UndefinedState());
        sm.updateState('/');

        assertTrue(sm.getCurrentState() instanceof InvalidStepOneState);

    }

    @Test
    public void UndefinedState_SetState_DelimPassed_InvalidStepOneState(){
        StateMachine sm = new StateMachine();
        sm.setCurrentState(new UndefinedState());
        sm.updateState(' ');

        assertTrue(sm.getCurrentState() instanceof CompletedTokenState);

    }

    @Test
    public void UndefinedState_SetState_ExclaimationPassed_InvalidStepOneState(){
        StateMachine sm = new StateMachine();
        sm.setCurrentState(new UndefinedState());
        sm.updateState('!');

        assertTrue(sm.getCurrentState() instanceof PossibleRecoveredState);

    }

    @Test
    public void UndefinedState_SetState_UndefinedPassed_InvalidStepOneState(){
        StateMachine sm = new StateMachine();
        sm.setCurrentState(new UndefinedState());
        sm.updateState('#');

        assertTrue(sm.getCurrentState() instanceof UndefinedState);

    }





}
