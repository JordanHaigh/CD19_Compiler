package CD19.Observer;

/**
 * Syntactic Error Message when the Parser finds the wrong token it was expecting from the grammar
 *
 * @author Jordan Haigh c3256730
 * @since 29/9/19
 */
public class SyntacticErrorMessage extends CompilerErrorMessage {
    public SyntacticErrorMessage(String errorMessage, int line, int column){
        super(errorMessage, line, column);
    }
}
