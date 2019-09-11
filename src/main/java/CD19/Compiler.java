package CD19;

import CD19.Parser.Parser;
import CD19.Scanner.CodeFileReader;
import CD19.Scanner.Scanner;
import CD19.Scanner.Token;

import java.util.ArrayList;
import java.util.List;

/*
 * Jordan Haigh c3256730 CD19
 * public class Compiler
 * Class houses the main sections of the complete assignment (lexical analysis, parsing, compiling)
 * */
public class Compiler {

    ErrorHandler errorHandler;

    public Compiler(){
        errorHandler = new ErrorHandler();
    }
    /**
     * Compile source file
     * @param filePath  - Path to File
     */
    public void compile(String filePath) {
        List<Token> tokens = lexicalAnalysis(filePath);
        parse(tokens);
        //todo more at a later date

    }

    /**
     * Perform Lexical Analysis on File
     * Return and print the list of tokens
     * @param filePath  - Path to File
     */
    public List<Token> lexicalAnalysis(String filePath) {
        Scanner scanner = new Scanner(new CodeFileReader(filePath));
        scanner.addObserver(errorHandler);
        List<Token> tokens = scanner.getAllTokens();
        //printTokens(allTokens);
        return tokens;
    }

    public void parse(List<Token> tokens) {
        Parser parser = new Parser(tokens);
        parser.addObserver(errorHandler);
        parser.parse();

    }


}
