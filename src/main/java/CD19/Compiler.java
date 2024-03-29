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
    PrintStream stdout = System.out;

    /**
     * Compile source file
     *
     * @param file - File
     */
    public void compile(File file) {
        List<Token> tokens = lexicalAnalysis(file);
        TreeNode tree = parse(tokens);

        printListingFileToFile("./"+getFileNameWithoutExtension(file.getName())+".lst");
        printErrorsToConsole();
        printTree(tree);

        if(parser.isSyntacticallyValid() && parser.isSemanticallyValid()){
            codeGeneration(tree,file);
        }
        else{
            System.out.println("Fix program errors before Code Gen");
        }

    }

    private String getFileNameWithoutExtension(String fileName){
        int pos = fileName.lastIndexOf(".");
        if (pos > 0 && pos < (fileName.length() - 1)) { // If '.' is not the first or last character.
            fileName = fileName.substring(0, pos);
        }

        return fileName;
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

    public void codeGeneration(TreeNode tree, File file){
        codeGenerator = new CodeGenerator(tree,parser.getConstants(), parser.getIdentifiers());
        codeGenerator.run();
        if(codeGenerator.hasCompilerError()){
            return;
        }
        else{
            printModToFile(getFileNameWithoutExtension(file.getName()));
            System.out.println("\n" + getFileNameWithoutExtension(file.getName()) +" compiled successfully!");

        }
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

        listingFile.print(null);
        listingFile.print(listingOut);
    }

    /**
     * Print listing to file
     */
    private void printModToFile(String filenameWithoutExtension) {
        PrintWriter modOut = null;

        try {
            modOut = new PrintWriter(filenameWithoutExtension+".mod");
        } catch (FileNotFoundException e) {
            File file = new File(filenameWithoutExtension+".mod");
            try {
                file.createNewFile();
            } catch (IOException ex) {
                System.err.println("Could not create mod File at location");
                System.err.println(ex.getCause());
            }
        }
        if (modOut == null) {
            System.out.println("Couldn't print mod file");
            return;
        }
        codeGenerator.printMatrix(false,modOut);
        codeGenerator.printMatrix(false,null);
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
        System.out.println();
//
//        PrintWriter out = new PrintWriter(System.out);
//        tree.danPrintTree(out, tree);
//        out.flush();
    }


}
