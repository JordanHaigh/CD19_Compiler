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
    public void InitState_SetState_NumberPassed_SetStateToIdentifier(){
        StateMachine sm = new StateMachine();
        sm.updateState('4');

        assertTrue(sm.getCurrentState() instanceof IntegerState);

    }
}
