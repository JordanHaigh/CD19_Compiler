package CD19.Scanner;

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

    private List<String> codeLines;
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

    /**
     * Reads a file from argument file path
     * @param filePath - File to read
     * @return - List of Strings of each line in the file
     */
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

    /**
     * Reads the next character in the fileLines
     * Updates the Column and Row Number respectively
     * If a new line is hit, it will trigger a flag
     * @return - Next char found in the code file
     */
    public char readNextChar() {
        if (hitNewLineOnLastPass) { //if we previously hit a new line
            columnNumber = 0;

            lineNumber++;
            if (lineNumber == codeLines.size())
                reachedEOF = true;

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

    /**
     * @param steps - Number of steps to move
     */
    public void moveColumnPosition(int steps) {
        for (int i = 0; i < steps; i++)
            columnNumber--;

        if (columnNumber < 0)
            columnNumber = 0;
    }



    public List<String> getCodeLines() { return codeLines; }
    public int getLineNumber() { return lineNumber + 1; }
    public int getColumnNumber() { return columnNumber + 1; }
    public boolean hasReachedEOF() { return reachedEOF; }


}

