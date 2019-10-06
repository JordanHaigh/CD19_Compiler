package CD19.Observer;

/**
 * Lexical Error Message when the Scanner finds an invalid token
 *
 * @author Jordan Haigh c3256730
 * @since 29/9/19
 */
public class LexicalErrorMessage extends CompilerErrorMessage {
    public LexicalErrorMessage(String errorMessage, int line, int column){
        super(errorMessage, line, column);
    }
}
