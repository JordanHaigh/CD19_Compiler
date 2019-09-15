package CD19.Scanner;

import CD19.ErrorHandler;
import CD19.Observer.LexicalErrorMessage;
import CD19.Observer.ObservableMessage;
import CD19.Observer.Observer;
import CD19.Observer.Subject;
import CD19.Scanner.States.*;

import java.util.ArrayList;
import java.util.List;

/*
 * Jordan Haigh c3256730 CD19
 * public class Scanner
 * Used for lexical analysis and token generation when scanning a file
 * */
public class Scanner implements Subject {

    private CodeFileReader codeFileReader;
    private StateMachine stateMachine; //state pattern -  https://sourcemaking.com/design_patterns/state

    private List<Observer> observers = new ArrayList<>();

    public Scanner(CodeFileReader codeFileReader) {

        this.codeFileReader = codeFileReader;
        this.stateMachine = new StateMachine();
    }

    /**
     * Get all tokens from a .CD File
     * @return - List containing all tokens from .CD File
     */
    public List<Token> getAllTokens() {
        //parse all lines in the code lines
        //for each line, get the tokens from the line

        List<Token> allTokens = new ArrayList<>();

        while (!codeFileReader.hasReachedEOF()) {
            Token token = getNextToken();
            if(token.isUndefined()){
                //add to listing
                notifyObservers(new LexicalErrorMessage("lexical error: " + token.getStr() + " [" + token.getCol() + ":" + token.getLine() + "]"));
            }
            allTokens.add(token);
        }

        return allTokens;
    }

    /**
     * Get Next Token from Code File Reader
     * Keeps building upon a string builder (greedy left to right)
     * Uses state machine to determine if the state is in a completed state
     * @return - Completed Token
     */
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
            if(lexemeBuffer.toString().equals("\n"))
                lexemeBuffer = new StringBuilder(); //reset lexeme buffer otherwise it will mess with column numbers
            stateMachine.updateState(nextChar);


            if(stateMachine.getCurrentState() instanceof  CompletedCommentTokenState){
                stateMachine.reset(); //comments are not tokens and are ignored by the program
                lexemeBuffer = new StringBuilder();
            }
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

    /**
     * Given a lexeme, it will clean the lexeme, and find the associated token string/id.
     * @param lexeme - Lexeme string
     * @param numberOfSteps - Number of steps to move the code file reader column back
     * @return - Token containing tid, column, row and lexeme (where applicable)
     */
    private Token createTokenFromState(String lexeme, int numberOfSteps) {
        String lexemeSubString = lexeme.substring(0, lexeme.length() - numberOfSteps);
        //clean substring of delimiters

        String cleanedLexeme = cleanLexeme(lexemeSubString);

        int tokenId = Token.findTokenId(cleanedLexeme, stateMachine.getPreviousState());

        //now if the token is a string we need to undo the cleaning. so we need to readd tabs and spaces
        if(tokenId == Token.TSTRG){
            cleanedLexeme = lexemeSubString;
        }

        int tokenColumn = codeFileReader.getColumnNumber() - lexeme.length();
        int tokenLine = codeFileReader.getLineNumber();

        stateMachine.reset(); //send it back to the init state for next parse
        codeFileReader.moveColumnPosition(numberOfSteps); //move it back one spot so we aren't forgetting about the current read char

        return new Token(tokenId, tokenLine, tokenColumn, cleanedLexeme);

    }

    /**
     * Clean lexeme of spaces,new lines and tabs
     * @param lexeme - Lexeme to clean
     * @return - Cleaned lexeme
     */
    private String cleanLexeme(String lexeme) {
        if(!(stateMachine.getPreviousState() instanceof PossibleStringState)){ //dont clean if it is a failed string
            lexeme = lexeme.replaceAll(" ", "");
        }

        lexeme = lexeme.replaceAll("\t", "");
        lexeme = lexeme.replaceAll("\n", "");

        return lexeme;
    }

    //observer methods
    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(ObservableMessage message) {
        for(Observer observer : observers){
            observer.handleMessage(message);
        }
    }
}
