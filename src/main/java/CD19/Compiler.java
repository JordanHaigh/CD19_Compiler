package CD19;

import CD19.Parser.Parser;
import CD19.Parser.TreeNode;
import CD19.Scanner.CodeFileReader;
import CD19.Scanner.Scanner;
import CD19.Scanner.Token;

import java.io.*;
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
     *
     * @param filePath - Path to File
     */
    public void compile(String filePath) {
        List<Token> tokens = lexicalAnalysis(filePath);
        TreeNode tree = parse(tokens);


        printListingFileToFile();
        printTrees(tree);

    }


    /**
     * Perform Lexical Analysis on File
     * Return and print the list of tokens
     *
     * @param filePath - Path to File
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

    /**
     * Print listing to file
     */
    private void printListingFileToFile() {
        PrintWriter listingOut = null;
        try {
            listingOut = new PrintWriter("./c3256730_ProgramListing.lst");
        } catch (FileNotFoundException e) {
            File file = new File("./c3256730_ProgramListing.lst");
            try {
                file.createNewFile();
            } catch (IOException ex) {
                System.err.println("Could not create Listing File in submission location");
                System.err.println(ex.getCause());
            }
        }
        if (listingOut == null) {
            System.out.println("Couldn't print listing file");
            return;
        }
        System.out.println("\n===== Program Listing (Can also be found at \"./c3256730_ProgramListing.lst\") =====\n");
        listingFile.print(null); //print to console as well
        listingFile.print(listingOut);



    }

    /**
     * Print pretty version of tree and dans version of tree
     *
     * @param tree - Tree to print
     */
    private void printTrees(TreeNode tree) {
        System.out.println("\n=================== XML Version of Tree =========================\n");
        System.out.println(tree.prettyPrintTree());
        System.out.println("\n=================== Dans Version of Tree =========================\n");

        PrintWriter out = new PrintWriter(System.out);
        tree.danPrintTree(out, tree);
        out.flush();
    }


}
