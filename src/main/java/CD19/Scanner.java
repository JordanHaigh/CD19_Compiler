package CD19;

import CD19.States.*;

import java.util.ArrayList;
import java.util.List;

/*
 * Jordan Haigh c3256730 CD19
 * public class Scanner
 * Used for lexical analysis and token generation when scanning a file
 * */
public class Scanner {

    CodeFileReader codeFileReader;
    private StateMachine stateMachine; //state pattern -  https://sourcemaking.com/design_patterns/state

    public Scanner(CodeFileReader codeFileReader) {
        this.codeFileReader = codeFileReader;
        this.stateMachine = new StateMachine();
    }

    public List<Token> getAllTokens() {
        //parse all lines in the code lines
        //for each line, get the tokens from the line

        List<Token> allTokens = new ArrayList<>();

        while (!codeFileReader.hasReachedEOF()) {
            Token token = getNextToken();
            allTokens.add(token);
        }

        return allTokens;
    }

    public Token getNextToken() {
        //search char by char, building the token and updating state where necessary
        StringBuilder lexemeBuffer = new StringBuilder();
        while (true) {
            char nextChar = codeFileReader.readNextChar();

            if (codeFileReader.hasReachedEOF()) {
                //return new eof token
                return new Token(Token.TEOF, codeFileReader.getLineNumber(), codeFileReader.getColumnNumber(), "\0");
            }

            lexemeBuffer.append(nextChar); //we append here and may take characters away when we are building the token.
            // the final state will determine how many characters to remove from the lexeme
            stateMachine.updateState(nextChar);

            if (stateMachine.getCurrentState() instanceof CompletedTokenState) {
                return createTokenFromState(lexemeBuffer.toString(), 0);

            }
            if (stateMachine.getCurrentState() instanceof InvalidStepOneState) {
                return createTokenFromState(lexemeBuffer.toString(), 1);

            }
            if (stateMachine.getCurrentState() instanceof InvalidStepTwoState) {
                return createTokenFromState(lexemeBuffer.toString(), 2);
            }
        }
    }

    private Token createTokenFromState(String lexeme, int numberOfSteps) {
        String lexemeSubString = lexeme.substring(0, lexeme.length() - numberOfSteps); //todo this may or may not work. this is in the event of /-a or 100.a that it needs to go back and parse ONLY the first bit
        //clean substring of delimiters
        lexemeSubString = cleanLexeme(lexemeSubString);

        int tokenId = Token.findTokenId(lexemeSubString, stateMachine.getPreviousState());
        int tokenColumn = codeFileReader.getColumnNumber() - lexeme.length();
        int tokenLine = codeFileReader.getLineNumber();

        stateMachine.reset(); //send it back to the init state for next parse
        codeFileReader.moveColumnPosition(numberOfSteps); //move it back one spot so we aren't forgetting about the current read char

        return new Token(tokenId, tokenLine, tokenColumn, lexemeSubString);

    }

    private String cleanLexeme(String lexeme) {
        lexeme = lexeme.replaceAll(" ", "");
        lexeme = lexeme.replaceAll("\t", "");
        lexeme = lexeme.replaceAll("\n", "");

//        //if just a semi, dont delete it - because that will be its own token (its the one outlier case that makes this all gross looking)
//        if(!lexeme.equals(";"))
//            lexeme = lexeme.replaceAll(";","");

        return lexeme;
    }
}
