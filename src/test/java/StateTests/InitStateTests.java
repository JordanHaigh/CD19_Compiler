package StateTests;

import CD19.States.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/*
 * Jordan Haigh CD19
 * public class InitStateTests
 * Tests determine if state transition logic is working as intended
 * */
public class InitStateTests {

    @Test
    public void InitState_SetState_IdentifierPassed_SetStateToIdentifier(){
        StateMachine sm = new StateMachine();
        sm.updateState('a');

        assertTrue(sm.getCurrentState() instanceof IdentifierState);

    }

    @Test
    public void InitState_SetState_NumberPassed_SetStateToInteger(){
        StateMachine sm = new StateMachine();
        sm.updateState('4');

        assertTrue(sm.getCurrentState() instanceof IntegerState);

    }

    @Test
    public void InitState_SetState_QuotePassed_SetStateToPossibleString(){
        StateMachine sm = new StateMachine();
        sm.updateState('"');

        assertTrue(sm.getCurrentState() instanceof PossibleStringState);

    }

    @Test
    public void InitState_SetState_LParenPassed_CompletedToken(){
        StateMachine sm = new StateMachine();
        sm.updateState('(');

        assertTrue(sm.getCurrentState() instanceof CompletedTokenState);

    }

    @Test
    public void InitState_SetState_RParenPassed_CompletedToken(){
        StateMachine sm = new StateMachine();
        sm.updateState(')');

        assertTrue(sm.getCurrentState() instanceof CompletedTokenState);

    }

    @Test
    public void InitState_SetState_LBracketPassed_CompletedToken(){
        StateMachine sm = new StateMachine();
        sm.updateState('[');

        assertTrue(sm.getCurrentState() instanceof CompletedTokenState);

    }

    @Test
    public void InitState_SetState_RBracketPassed_CompletedToken(){
        StateMachine sm = new StateMachine();
        sm.updateState(']');

        assertTrue(sm.getCurrentState() instanceof CompletedTokenState);

    }

    @Test
    public void InitState_SetState_ExponentPassed_CompletedToken(){
        StateMachine sm = new StateMachine();
        sm.updateState('^');

        assertTrue(sm.getCurrentState() instanceof CompletedTokenState);

    }

    @Test
    public void InitState_SetState_ColonPassed_CompletedToken(){
        StateMachine sm = new StateMachine();
        sm.updateState(':');

        assertTrue(sm.getCurrentState() instanceof CompletedTokenState);

    }

    @Test
    public void InitState_SetState_DotPassed_CompletedToken(){
        StateMachine sm = new StateMachine();
        sm.updateState('.');

        assertTrue(sm.getCurrentState() instanceof CompletedTokenState);

    }

    @Test
    public void InitState_SetState_SemiColonPassed_Completed(){
        StateMachine sm = new StateMachine();
        sm.updateState(';');

        assertTrue(sm.getCurrentState() instanceof CompletedTokenState);

    }

    @Test
    public void InitState_SetState_CommaPassed_CompletedToken(){
        StateMachine sm = new StateMachine();
        sm.updateState(',');

        assertTrue(sm.getCurrentState() instanceof CompletedTokenState);

    }

    @Test
    public void InitState_SetState_ExclaimationPassed_CompletedToken(){
        StateMachine sm = new StateMachine();
        sm.updateState('!');

        assertTrue(sm.getCurrentState() instanceof PossibleNotEqualsState);

    }

    @Test
    public void InitState_SetState_PlusPassed_SingleOperator(){
        StateMachine sm = new StateMachine();
        sm.updateState('+');

        assertTrue(sm.getCurrentState() instanceof SingleOperatorState);
    }

    @Test
    public void InitState_SetState_MinusPassed_SingleOperator(){
        StateMachine sm = new StateMachine();
        sm.updateState('-');

        assertTrue(sm.getCurrentState() instanceof SingleOperatorState);
    }

    @Test
    public void InitState_SetState_EqualsPassed_SingleOperator(){
        StateMachine sm = new StateMachine();
        sm.updateState('=');

        assertTrue(sm.getCurrentState() instanceof SingleOperatorState);
    }

    @Test
    public void InitState_SetState_StarPassed_SingleOperator(){
        StateMachine sm = new StateMachine();
        sm.updateState('*');

        assertTrue(sm.getCurrentState() instanceof SingleOperatorState);
    }

    @Test
    public void InitState_SetState_PercentPassed_Completed(){
        StateMachine sm = new StateMachine();
        sm.updateState('%');

        assertTrue(sm.getCurrentState() instanceof CompletedTokenState);
    }

    @Test
    public void InitState_SetState_LessThanPassed_SingleOperator(){
        StateMachine sm = new StateMachine();
        sm.updateState('<');

        assertTrue(sm.getCurrentState() instanceof SingleOperatorState);
    }

    @Test
    public void InitState_SetState_GreaterThanPassed_SingleOperator(){
        StateMachine sm = new StateMachine();
        sm.updateState('<');

        assertTrue(sm.getCurrentState() instanceof SingleOperatorState);
    }

    @Test
    public void InitState_SetState_ForwardSlash_CommendOrDivide(){
        StateMachine sm = new StateMachine();
        sm.updateState('/');

        assertTrue(sm.getCurrentState() instanceof CommentOrDivideState);
    }

    @Test
    public void InitState_SetState_UndefinedToken_Undefined(){
        StateMachine sm = new StateMachine();
        sm.updateState('#');

        assertTrue(sm.getCurrentState() instanceof UndefinedState);
    }




}
