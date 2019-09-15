package CD19.Scanner.States;

import CD19.Scanner.CharacterClassification;

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
        else if(c == '!')
            sm.setCurrentState(new UnrecoveredNotEqualsState()); //!!!!!!!=
        else if(CharacterClassification.isCharDefined(c))
            sm.setCurrentState(new InvalidStepOneState());
        else
            sm.setCurrentState(new UndefinedState());
    }
}
