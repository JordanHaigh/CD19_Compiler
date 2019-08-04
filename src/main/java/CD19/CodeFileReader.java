package CD19;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/*
 * Jordan Haigh CD19
 * public class CD19.CodeFileReader
 * Takes file path as argument and returns a list of strings of the contained text in the file.
 * */
public class CodeFileReader {

    List<String> codeLines;
    private int lineNumber, columnNumber;
    private boolean reachedEOF;

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
        String currentLine = codeLines.get(lineNumber);
        char nextChar;

        try {
            nextChar = currentLine.charAt(++columnNumber);
        } catch (ArrayIndexOutOfBoundsException e) {
            //column number has gone too far
            columnNumber = 0;

            lineNumber++;
            if(lineNumber == codeLines.size())
                reachedEOF = true;

            return '\n';
        }

        return nextChar;
    }

    public void moveColumnPosition() {
        columnNumber--;
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

