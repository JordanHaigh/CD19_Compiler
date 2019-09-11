package CD19.States;

import CD19.Scanner.CharacterClassification;


/*
 * Jordan Haigh c3256730 CD19
 * public class UnrecoveredNotEqualsState.java
 * Currently there are multiple exclaimation marks.
 * Character input determines state transitions
 * */
public class UnrecoveredNotEqualsState implements State {

    @Override
    public void updateState(StateMachine sm, char c) {
        if(c == '!' || !CharacterClassification.isCharDefined(c)){
            //stay here
        }
        else if(c == '=')
            sm.setCurrentState(new InvalidStepTwoState()); //!!!!!=
        else{
            sm.setCurrentState(new InvalidStepOneState());
        }

    }
}
