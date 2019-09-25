package CD19;

import CD19.Parser.Parser;
import CD19.Parser.TreeNode;
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

    ListingFile listingFile;

    /**
     * Compile source file
     * @param filePath  - Path to File
     */
    public void compile(String filePath) {
        List<Token> tokens = lexicalAnalysis(filePath);

        if(!listingFile.containsErrors()){
            //parse(tokens);
        }

        listingFile.print();
    }

    /**
     * Perform Lexical Analysis on File
     * Return and print the list of tokens
     * @param filePath  - Path to File
     */
    public List<Token> lexicalAnalysis(String filePath) {
        listingFile = new ListingFile(new CodeFileReader(filePath));

        Scanner scanner = new Scanner(new CodeFileReader(filePath));
        scanner.addObserver(listingFile);
        return scanner.getAllTokens();
    }

    public TreeNode parse(List<Token> tokens) {
        Parser parser = new Parser(tokens);
        parser.addObserver(listingFile);
        return parser.parse();

    }


}
