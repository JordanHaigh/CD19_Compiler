package CD19;

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
        lexicalAnalysis(filePath);

        //todo more at a later date

    }

    /**
     * Perform Lexical Analysis on File
     * Return and print the list of tokens
     * @param filePath  - Path to File
     */
    public void lexicalAnalysis(String filePath) {
        Scanner scanner = new Scanner(new CodeFileReader(filePath),errorHandler);
        List<Token> allTokens = scanner.getAllTokens();
        printTokens(allTokens);
    }

    /**
     * Printing method for Assignment 1
     * @param allTokens - Tokens to be printed
     */
    public void printTokens(List<Token> allTokens){
        //build lines to print tokens
        List<String> lines = new ArrayList<>();
        StringBuilder currentLine = new StringBuilder();

        for(Token token : allTokens){
            if(token.isUndefined()){
                if(currentLine.length() > 0){
                    lines.add(currentLine.toString()); //add current line
                }
                lines.add(token.getTokenIDAsString());
                lines.add("lexical error " + token.getStr());
                currentLine =  new StringBuilder(); //reset
            }
            else{
                //defined token
                if(currentLine.length() >= 60){ //as per spec, if the current line is over 60 chars long, new line
                    lines.add(currentLine.toString());
                    currentLine =  new StringBuilder();
                }

                String tokenString = token.shortString(); //maybe keyword, operator or literal
                currentLine.append(tokenString);
            }

        }
        if(currentLine.length() > 0) //if theres anything left over in the buffer
            lines.add(currentLine.toString());


        //print out lines
        for(String line : lines){
            System.out.println(line);
        }

        //print out error listing
        System.out.println("Error Handler Messages:");
        errorHandler.printErrorMessages();
    }
    public void parse() {
        //todo at a later date
    }


}
