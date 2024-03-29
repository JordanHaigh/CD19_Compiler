package CD19.Scanner.States;

import CD19.Scanner.CharacterClassification;

/*
 * Jordan Haigh c3256730 CD19
 * public class AbsoluteFloatState.java
 * Building an absolute Float (<integer>.<integer>)token. Character inputs determine state transitions
 * */
public class AbsoluteFloatState implements State {

    @Override
    public void updateState(StateMachine sm, char c) {
        if(CharacterClassification.isCharNumerical(c))
            return; //no state update
        else if(CharacterClassification.isCharDelimiter(c))
            sm.setCurrentState(new CompletedTokenState());
        else
            sm.setCurrentState(new InvalidStepOneState());
    }
}
