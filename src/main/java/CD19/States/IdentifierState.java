package CD19.States;

import CD19.CharacterClassification;

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

        if(c == ' ' || c == '\n')
            sm.setCurrentState(new CompletedTokenState());

        if(CharacterClassification.isCharSpecial(c))
            sm.setCurrentState(new InvalidState());

    }
}
