package CD19.Scanner.States;

import CD19.Scanner.CharacterClassification;

/*
 * Jordan Haigh c3256730 CD19
 * public class IdentifierState.java
 * Building an Identifier token. Character inputs determine state transitions
 * */
public class IdentifierState implements State {

    @Override
    public void updateState(StateMachine sm, char c) {
        if(CharacterClassification.isCharNumerical(c) || CharacterClassification.isCharAlphabetical(c)){
            return; //no need to update state
        }

        if(CharacterClassification.isCharDelimiter(c))
            sm.setCurrentState(new CompletedTokenState());
        else if(c == '_') //underscores in this state is fine.
            return;
        else
            sm.setCurrentState(new InvalidStepOneState());

    }
}

