package CD19.Scanner.States;


import CD19.Scanner.CharacterClassification;

/*
 * Jordan Haigh c3256730 CD19
 * public class PossibleNotEqualsState.java
 * Building a not equals (!=) token. Character input determines state transitions
 * */
public class PossibleNotEqualsState implements State {

    @Override
    public void updateState(StateMachine sm, char c) {
        if(c == '=')
            sm.setCurrentState(new CompletedTokenState()); //absolute not equals
        else if(c == '!')
            sm.setCurrentState(new UnrecoveredNotEqualsState());
        else if(!CharacterClassification.isCharDefined(c))
            sm.setCurrentState(new UndefinedState());
        else
            sm.setCurrentState(new InvalidStepOneState());
    }
}
