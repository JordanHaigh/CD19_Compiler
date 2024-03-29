package CD19.Scanner.States;

import CD19.Scanner.CharacterClassification;

/*
 * Jordan Haigh c3256730 CD19
 * public class SingleOperatorState.java
 * Building either a single or double operator (+ or +=) Character input determines state transitions
 * */
public class SingleOperatorState implements State{

    @Override
    public void updateState(StateMachine sm, char c) {
        if(c == '=' || CharacterClassification.isCharDelimiter(c))
            sm.setCurrentState(new CompletedTokenState()); //double operator
        else
            sm.setCurrentState(new InvalidStepOneState());
    }
}
