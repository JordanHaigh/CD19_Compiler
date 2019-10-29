package CD19;

import CD19.CodeGen.CodeGenerator;
import CD19.Parser.Parser;
import CD19.Parser.TreeNode;
import CD19.Scanner.CodeFileReader;
import CD19.Scanner.Scanner;
import CD19.Scanner.Token;

import java.io.*;
import java.util.List;

/*
 * Jordan Haigh c3256730 CD19
 * public class Compiler
 * Class houses the main sections of the complete assignment (lexical analysis, parsing, compiling)
 * */
public class Compiler {

    ListingFile listingFile;
    Scanner scanner;
    Parser parser;
    CodeGenerator codeGenerator;

    /**
     * Compile source file
     *
     * @param file - File
     */
    public void compile(File file) {
        List<Token> tokens = lexicalAnalysis(file);
        TreeNode tree = parse(tokens);


        printListingFileToFile("./c3256730_ProgramListing.lst");
        printErrorsToConsole();
        printTree(tree);
        System.out.println();

        codeGeneration(tree);

    }


    /**
     * Perform Lexical Analysis on File
     * Return and print the list of tokens
     *
     * @param file - File
     */
    public List<Token> lexicalAnalysis(File file) {
        listingFile = new ListingFile(new CodeFileReader(file));

        scanner = new Scanner(new CodeFileReader(file));
        scanner.addObserver(listingFile);
        return scanner.getAllTokens();
    }

    public TreeNode parse(List<Token> tokens) {
        parser = new Parser(tokens);
        parser.addObserver(listingFile);
        return parser.parse();
    }

    public void codeGeneration(TreeNode tree){
        codeGenerator = new CodeGenerator(tree,parser.getConstants(), parser.getIdentifiers());
        codeGenerator.run();
        codeGenerator.getProgram().printMatrix(false);
    }

    /**
     * Print listing to file
     */
    private void printListingFileToFile(String filename) {
        PrintWriter listingOut = null;
        try {
            listingOut = new PrintWriter(filename);
        } catch (FileNotFoundException e) {
            File file = new File(filename);
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

        listingFile.print(null); //todo comment before submission
        listingFile.print(listingOut);
    }

    private void printErrorsToConsole() {
        System.out.println(listingFile.printErrors());
    }

    /**
     * Print pretty version of tree and dans version of tree
     *
     * @param tree - Tree to print
     */
    private void printTree(TreeNode tree) {
        System.out.println("\n=================== XML Version of Tree =========================\n");
        System.out.println(tree.prettyPrintTree());
//        System.out.println("\n=================== Dans Version of Tree =========================\n");
//
//        PrintWriter out = new PrintWriter(System.out);
//        tree.danPrintTree(out, tree);
//        out.flush();
    }


}
