package CD19;

import CD19.States.*;

import java.util.ArrayList;
import java.util.List;

/*
 * Jordan Haigh CD19
 * public class CD19.Scanner
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

            if(codeFileReader.hasReachedEOF()){
                return new Token(Token.TEOF, codeFileReader.getLineNumber(), codeFileReader.getColumnNumber(), ""); //todo do we want nothing for the lexeme here?
            }

            stateMachine.updateState(nextChar);

            if (stateMachine.getCurrentState() instanceof InvalidState) {
                return createTokenFromInvalidState(lexemeBuffer);
            }
            if (stateMachine.getCurrentState() instanceof CompletedTokenState) {
                return createTokenFromCompletedState(lexemeBuffer);
            }

            lexemeBuffer.append(nextChar);

        }
    }

    private Token createTokenFromInvalidState(StringBuilder lexemeBuffer){
        //attempt to salvage a token before we hit the invalid character

        int tokenId = Token.findTokenId(lexemeBuffer.toString(), stateMachine.getPreviousState());
        int tokenColumn = codeFileReader.getColumnNumber() - lexemeBuffer.length();
        int tokenLine = codeFileReader.getLineNumber();

        stateMachine.reset(); //send it back to the init state for next parse
        codeFileReader.moveColumnPosition(); //move it back one spot so we aren't forgetting about the current read char

        return new Token(tokenId, tokenLine, tokenColumn, lexemeBuffer.toString());

    }

    private Token createTokenFromCompletedState(StringBuilder lexemeBuffer){
        int tokenId = Token.findTokenId(lexemeBuffer.toString(), stateMachine.getPreviousState());
        int tokenColumn = codeFileReader.getColumnNumber() - lexemeBuffer.length();
        int tokenLine = codeFileReader.getLineNumber();

        stateMachine.reset(); //send it back to the init state for next parse

        return new Token(tokenId, tokenLine, tokenColumn, lexemeBuffer.toString());

    }
}
