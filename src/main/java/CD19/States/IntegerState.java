package CD19.States;

import CD19.CharacterClassification;

/*
 * Jordan Haigh c3256730 CD19
 * public class IntegerState.java
 * Building an Integer literal token. Character inputs determine state transitions
 * */
public class IntegerState implements State {

    @Override
    public void updateState(StateMachine sm, char c) {
        if(CharacterClassification.isCharNumerical(c))
            return;
        else if(c == '.'){
            sm.setCurrentState(new PossibleFloatState());
        }
        else if(CharacterClassification.isCharDelimiter(c)){
            sm.setCurrentState(new CompletedTokenState());
        }
        else{
            sm.setCurrentState(new InvalidStepOneState());
        }

    }
}
