package CD19.States;

import CD19.CharacterClassification;

/*
 * Jordan Haigh c3256730 CD19
 * public class InitState.java
 * Starting node of the state machine. Character input determines state transitions
 * */
public class InitState implements State {
    @Override
    public void updateState(StateMachine sm, char c) {
        if (CharacterClassification.isCharDelimiter(c))
            return;

        if (CharacterClassification.isCharAlphabetical(c) || c == '_')
            sm.setCurrentState(new IdentifierState());
        else if (CharacterClassification.isCharNumerical(c))
            sm.setCurrentState(new IntegerState());
        else if (c == '\"')
            sm.setCurrentState(new PossibleStringState());
        else if (CharacterClassification.isCharSingleOperator(c))
            sm.setCurrentState(new CompletedTokenState());
        else if (c == '!')
            sm.setCurrentState(new PossibleNotEqualsState());
        else if (CharacterClassification.isCharAssignmentOrRelationalOperator(c))
            sm.setCurrentState(new SingleOperatorState());
        else if (c == '/')
            sm.setCurrentState(new CommentOrDivideState());
        else
            sm.setCurrentState(new UndefinedState());
    }
}


