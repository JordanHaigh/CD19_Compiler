package CD19;

import java.util.List;

/*
 * Jordan Haigh c3256730 CD19
 * public class Compiler
 * Class houses the main sections of the complete assignment (lexical analysis, parsing, compiling)
 * */
public class Compiler {

    public void compile(String filePath){
        lexicalAnalysis(filePath);

        //todo more at a later date

    }

    public void lexicalAnalysis(String filePath){
       Scanner scanner = new Scanner(new CodeFileReader(filePath));
       List<Token> allTokens = scanner.getAllTokens();

    }

    public void parse(){
        //todo at a later date
    }





}
