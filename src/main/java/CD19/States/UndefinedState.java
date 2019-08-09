package CD19.States;

import CD19.CharacterClassification;
/*
 * Jordan Haigh c3256730 CD19
 * public class UndefinedState.java
 * State when an invalid character such as # @ or ? is passed. Character input determines state transitions
 * */
public class UndefinedState implements State {
    @Override
    public void updateState(StateMachine sm, char c) {
        if(CharacterClassification.isCharDelimiter(c))
            sm.setCurrentState(new CompletedTokenState());
        else if(c == '!')
            sm.setCurrentState(new PossibleRecoveredState());
        else if (CharacterClassification.isCharDefined(c))
            sm.setCurrentState(new InvalidStepOneState());
        else
            return; //keep consuming unidentified characters

    }
}
