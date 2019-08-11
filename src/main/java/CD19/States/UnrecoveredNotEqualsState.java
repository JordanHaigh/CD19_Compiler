package CD19.States;

import CD19.CharacterClassification;

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
