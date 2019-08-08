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
        if (CharacterClassification.isCharAlphabetical(c) ||
                CharacterClassification.isCharNumerical(c) ||
                CharacterClassification.isCharSingleOperator(c) ||
                CharacterClassification.isCharAssignmentOrRelationalOperator(c) ||
                c == '\"' || c == '/')
        {
            sm.setCurrentState(new InvalidStepOneState());
        }
        else if(CharacterClassification.isCharDelimiter(c))
            sm.setCurrentState(new CompletedTokenState());
        else if(c == '!')
            sm.setCurrentState(new PossibleRecoveredState());
        else
            return; //keep consuming unidentified characters

    }
}
