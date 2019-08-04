import java.util.List;

public class Compiler {

    public void compile(String filePath){
        lexicalAnalysis(filePath);

        //todo more at a later date

    }

    public void lexicalAnalysis(String filePath){
        CodeFileReader codeFileReader = new CodeFileReader();
        List<String> codeLines = codeFileReader.readFile(filePath);



    }

    public void parse(){
        //todo at a later date
    }





}
