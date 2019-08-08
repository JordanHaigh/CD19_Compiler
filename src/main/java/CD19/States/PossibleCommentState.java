package CD19.States;

/*
 * Jordan Haigh c3256730 CD19
 * public class PossibleCommentState.java
 * Currently have /-. Need to determine the next char if it is a -. Character input determines state transitions
 * */
public class PossibleCommentState implements State{

    @Override
    public void updateState(StateMachine sm, char c) {
        if(c == '-')
            sm.setCurrentState(new AbsoluteCommentState());
        else
            sm.setCurrentState(new InvalidStepOneState());

    }
}
