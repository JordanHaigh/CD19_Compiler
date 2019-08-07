package CD19.States;

import CD19.CharacterClassification;

/*
 * Jordan Haigh c3256730 CD19
 * public class PossibleFloatState.java
 * Building a possible Float token. Character inputs determine state transitions
 * */
public class PossibleFloatState implements State{

    @Override
    public void updateState(StateMachine sm, char c) {
        if(CharacterClassification.isCharNumerical(c)){
            sm.setCurrentState(new AbsoluteFloatState());
        } else{
            sm.setCurrentState(new InvalidState());
        }

    }
}
