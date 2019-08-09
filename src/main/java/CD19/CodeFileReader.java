package CD19;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/*
 * Jordan Haigh c3256730 CD19
 * public class CodeFileReader
 * Takes file path as argument and returns a list of strings of the contained text in the file.
 * */
public class CodeFileReader {

    List<String> codeLines;
    private int lineNumber, columnNumber;
    private boolean reachedEOF;
    private boolean hitNewLineOnLastPass;

    public CodeFileReader(List<String> codeLines) {
        this.codeLines = codeLines;
        lineNumber = columnNumber = 0;
    }

    public CodeFileReader(String filePath) {
        this.codeLines = readFile(filePath);
        lineNumber = columnNumber = 0;
    }

    public List<String> readFile(String filePath) {
        List<String> lines = new ArrayList<>();

        //https://stackoverflow.com/a/5868528/8566833
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return lines;
    }

    public char readNextChar() {
        if (hitNewLineOnLastPass) { //if we previously hit a new line
           incrementLineNumber();
            hitNewLineOnLastPass = false;
        }

        if(reachedEOF){ //check if eof before updating the current line (otherwise exception!)
            return '\0';
        }

        String currentLine = codeLines.get(lineNumber);
        char nextChar;

        try {
            nextChar = currentLine.charAt(columnNumber++);
        } catch (StringIndexOutOfBoundsException e) {
            //column number has gone too far
            hitNewLineOnLastPass = true;
            return '\n';
        }

        if(nextChar == '\n')
            hitNewLineOnLastPass = true;

        return nextChar;
    }

    private void incrementLineNumber(){
        columnNumber = 0;

        lineNumber++;
        if (lineNumber == codeLines.size())
            reachedEOF = true;
    }

    public void moveColumnPosition() {
        columnNumber--;
    }

    public void moveColumnPosition(int steps) {
        for (int i = 0; i < steps; i++)
            columnNumber--;

        if (columnNumber < 0)
            columnNumber = 0; //todo danger danger be careful with this
    }


    public List<String> getCodeLines() {
        return codeLines;
    }

    public int getLineNumber() {
        return lineNumber + 1;
    }

    public int getColumnNumber() {
        return columnNumber + 1;
    }

    public boolean hasReachedEOF() {
        return reachedEOF;
    }


}

