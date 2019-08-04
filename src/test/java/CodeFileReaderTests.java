import CD19.CodeFileReader;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

/*
 * Jordan Haigh CD19
 * public class CodeFileReaderTests
 * Tests determine if functionality is working as intended
 * */
public class CodeFileReaderTests {

    @Test
    public void codeFileReader_emptyFile_returnBlank(){
        CodeFileReader codeFileReader = new CodeFileReader("./src/test/java/emptyFile.cd");
        List<String> lines = codeFileReader.getCodeLines();

        assertEquals(0, lines.size());
    }

    @Test
    public void codeFileReader_nonEmptyFile_returnAllLines(){
        CodeFileReader codeFileReader = new CodeFileReader("./src/test/java/nonEmptyFile.cd");
        List<String> lines = codeFileReader.getCodeLines();

        assertEquals("CD19 test", lines.get(0));
        assertEquals("main", lines.get(1));
        assertEquals("\tx : integer", lines.get(2));
        assertEquals("begin", lines.get(3));
        assertEquals("\tprintline \"Hello World\";", lines.get(4));
        assertEquals("end ", lines.get(5));
        assertEquals("CD19 test", lines.get(6));

    }

    @Test
    public void codeFileReader_readNextChar_normal(){
        List<String> codeLines = new ArrayList<>();
        codeLines.add("hello world");

        CodeFileReader codeFileReader = new CodeFileReader(codeLines);
        char nextChar = codeFileReader.readNextChar();
        assertEquals('h', nextChar);
    }

    @Test
    public void codeFileReader_readNextChar_reachEndOfLineAndTriggerArrayOutOfBounds(){
        List<String> codeLines = new ArrayList<>();
        codeLines.add("h");
        codeLines.add("e");

        CodeFileReader codeFileReader = new CodeFileReader(codeLines);
        char firstChar = codeFileReader.readNextChar(); //h
        char secondChar = codeFileReader.readNextChar(); // \n

        assertEquals(secondChar, '\n');
        assertEquals(1, codeFileReader.getColumnNumber());
        assertEquals(2, codeFileReader.getLineNumber());
    }

    @Test
    public void codeFileReader_readNextChar_reachEndOfFile(){
        List<String> codeLines = new ArrayList<>();
        codeLines.add("h");
        codeLines.add("e");

        CodeFileReader codeFileReader = new CodeFileReader(codeLines);
        char firstChar = codeFileReader.readNextChar(); //h
        char secondChar = codeFileReader.readNextChar(); // \n
        char thirdChar = codeFileReader.readNextChar(); // e
        char forthChar = codeFileReader.readNextChar(); // \n

        assertEquals(true, codeFileReader.hasReachedEOF());
    }


}
