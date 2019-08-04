package CD19.States;

import CD19.CharacterClassification;

public class IdentifierState implements State {

    @Override
    public void updateState(StateMachine sm, char c) {
        if(c == ' ' || c == '\n')
            sm.setCurrentState(new CompletedTokenState());

        if(CharacterClassification.isCharSpecial(c))
            sm.setCurrentState(new InvalidState());
    }
}

