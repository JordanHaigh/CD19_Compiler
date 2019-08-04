import CD19.CodeFileReader;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
}
