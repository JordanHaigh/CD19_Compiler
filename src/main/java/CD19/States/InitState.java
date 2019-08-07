package CD19.States;

import CD19.CharacterClassification;

public class InitState implements State {
    @Override
    public void updateState(StateMachine sm, char c) {
        if(c == ' ' || c == '\n')
            return;

        if(CharacterClassification.isCharAlphabetical(c))
            sm.setCurrentState(new IdentifierState());
        else if(CharacterClassification.isCharNumerical(c))
            sm.setCurrentState(new IntegerState());
//        else if(CharacterClassification.isCharSpecial(c))
//            sm.setCurrentState(new SpecialState());
//        else if(CharacterClassification.isCharPossibleComment(c))
//            sm.setCurrentState(new PossibleCommentState());
        else if(CharacterClassification.isCharPossibleString(c))
            sm.setCurrentState(new StringState());
        else{
            System.out.println("unknown char. char is " + c);
            System.exit(-1);
        }
    }
}


