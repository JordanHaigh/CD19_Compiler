package CD19.States;

import CD19.CharacterClassification;

/*
 * Jordan Haigh c3256730 CD19
 * public class PossibleRecoveredState.java
 * Currently the lexeme buffer contains undefined characters and an exclaimation mark.
 * Character input determines state transitions
 * */
public class PossibleRecoveredState implements State {
    @Override
    public void updateState(StateMachine sm, char c) {
        if(c == '=')
            sm.setCurrentState(new InvalidStepTwoState());
        else if(CharacterClassification.isCharDelimiter(c))
            sm.setCurrentState(new CompletedTokenState());
        else
            sm.setCurrentState(new UndefinedState());
    }
}
