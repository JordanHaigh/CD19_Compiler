package CD19.States;

/*
 * Jordan Haigh c3256730 CD19
 * public class AbsoluteeCommentState.java
 * Currently have /--. A new line will complete the token. Anything else is consumed.
 * Character input determines state transitions
 * */
public class AbsoluteCommentState implements State {

    @Override
    public void updateState(StateMachine sm, char c) {
        if(c == '\n')
            sm.setCurrentState(new CompletedCommentTokenState()); // this state won't generate a token for the comment

        //everything else is consumed by the comment line
    }
}
