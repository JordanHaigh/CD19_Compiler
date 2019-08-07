package CD19.States;

/*
 * Jordan Haigh c3256730 CD19
 * public class CommentOrDivideState.java
 * First Character is a /. Need to determine if its a comment or a divide.
 * Character input determines state transitions
 * */
public class CommentOrDivideState implements State {

    @Override
    public void updateState(StateMachine sm, char c) {
        if(c == '-')
            sm.setCurrentState(new PossibleCommentState());
        else if(c == '=')
            sm.setCurrentState(new CompletedTokenState()); // /=
        else if(c == ' ')
            sm.setCurrentState(new CompletedTokenState()); // /
        else
            sm.setCurrentState(new InvalidState());
    }
}
