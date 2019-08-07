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
        if(c == ' ' || c == '\n')
            return;

        if(CharacterClassification.isCharAlphabetical(c))
            sm.setCurrentState(new IdentifierState());
        else if(CharacterClassification.isCharNumerical(c))
            sm.setCurrentState(new IntegerState());
        else if(CharacterClassification.isCharSingleOperator(c))
            sm.setCurrentState(new CompletedTokenState());
        else if(c == '!')
            sm.setCurrentState(new PossibleNotEqualsState());
//        else if(CharacterClassification.isCharSpecial(c))
//            sm.setCurrentState(new SpecialState());
//        else if(CharacterClassification.isCharPossibleComment(c))
//            sm.setCurrentState(new PossibleCommentState());
        else if(c == '\"')
            sm.setCurrentState(new PossibleStringState());
        else{
            System.out.println("unknown char. char is " + (int)c);
            System.exit(-1);
        }
    }
}


