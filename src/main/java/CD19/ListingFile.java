package CD19;

import CD19.Observer.*;
import CD19.Scanner.CodeFileReader;

import java.util.ArrayList;
import java.util.List;

public class ListingFile implements Observer {

    //Reproduct the input which has been scanned as a separate listing file (line numbers)
    //Can also output errors associated with any line after the line has been produced on the listing

    private CodeFileReader codeFileReader;
    private List<LineAndSource> inputFile = new ArrayList<>();
    private boolean containsErrors;

    public boolean containsErrors() { return containsErrors; }

    public ListingFile(CodeFileReader codeFileReader) {
        this.codeFileReader = codeFileReader;

        populateLinesAndSource();
    }

    private void populateLinesAndSource(){
        List<String> codeLines = codeFileReader.getCodeLines();
        int lineNumber = 1;
        for(String source : codeLines){
            inputFile.add(new LineAndSource(lineNumber, source));
            lineNumber++;
        }
    }

    public LineAndSource getLineAndSource(int lineToAddErrorsTo){
        for(LineAndSource lineAndSource : inputFile){
            if(lineToAddErrorsTo == lineAndSource.lineNumber){
                return lineAndSource;
            }
        }
        return null;
    }

    public void print(){
        for(LineAndSource lineAndSource : inputFile){
            StringBuilder sb = new StringBuilder();
            sb.append(lineAndSource.lineNumber)
                .append(": ")
                .append(lineAndSource.source);

            if(lineAndSource.errorsOnLine.size() > 0){
                sb.append("\n");
                for(int i = 0; i < lineAndSource.errorsOnLine.size(); i++){
                    CompilerErrorMessage message = lineAndSource.errorsOnLine.get(i);
                    sb.append("\t" + message.getErrorMessage())
                        .append("(").
                            append("Line ").append(message.getLine())
                            .append(", Column ").append(message.getColumn())
                        .append(")");
                    if(i != lineAndSource.errorsOnLine.size()-1)
                        sb.append("\n");
                }
            }
            System.out.println(sb.toString());
        }
    }


    @Override
    public void handleMessage(ObservableMessage message) {
        if (message instanceof CompilerErrorMessage) {
            CompilerErrorMessage compilerErrorMessage = (CompilerErrorMessage) message;

            LineAndSource lineAndSource = getLineAndSource(compilerErrorMessage.getLine());
            if(lineAndSource != null){
                lineAndSource.appendErrorsOnLine(compilerErrorMessage);
                containsErrors = true;
            }
        }

        if (message instanceof ObservableImmediateErrorMessage) {
            String newErrorMessage = ((ObservableImmediateErrorMessage) message).getErrorMessage();
            System.out.println(newErrorMessage);
        }
    }

    private class LineAndSource {
        int lineNumber;
        String source;
        List<CompilerErrorMessage> errorsOnLine = new ArrayList<>();

        public LineAndSource(int lineNumber, String source) {
            this.lineNumber = lineNumber;
            this.source = source;
        }

        public void appendErrorsOnLine(CompilerErrorMessage message){
            errorsOnLine.add(message);
        }
    }
}



