import CD19.States.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class InitStateTests {

    @Test
    public void InitState_SetState_IdentifierPassed_SetStateToIdentifier(){
        StateMachine sm = new StateMachine();
        sm.updateState('a');

        assertTrue(sm.getCurrentState() instanceof IdentifierState);

    }
}
