package CD19.States;

import CD19.CharacterClassification;

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
        else if(c == '=' || CharacterClassification.isCharDelimiter(c))
            sm.setCurrentState(new CompletedTokenState()); // /= or /
        else
            sm.setCurrentState(new InvalidStepOneState());
    }
}
