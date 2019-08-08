package CD19.States;

/*
 * Jordan Haigh c3256730 CD19
 * public class PossibleStringState.java
 * Building an string literal. Character inputs determine state transitions
 * */
public class PossibleStringState implements State{

    @Override
    public void updateState(StateMachine sm, char c) {
        if(c == '\n')
            sm.setCurrentState(new InvalidStepOneState());
        else if(c == '\"')
            sm.setCurrentState(new CompletedTokenState());


        //alphas, special or numbers dont change anything
    }
}
