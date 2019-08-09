import CD19.CodeFileReader;
import CD19.Scanner;
import CD19.Token;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ScannerTests {

    @Test
    public void Scanner_getNextToken_TCD19(){
        List<String> code = new ArrayList<>();
        code.add("CD19");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        Token token = scanner.getNextToken();
        assertEquals(Token.TCD19, token.value());

    }

    @Test
    public void Scanner_getNextToken_TCNST(){
        List<String> code = new ArrayList<>();
        code.add("const");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        Token token = scanner.getNextToken();
        assertEquals(Token.TCNST, token.value());

    }

    @Test
    public void Scanner_getNextToken_TTYPS(){
        List<String> code = new ArrayList<>();
        code.add("types");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        Token token = scanner.getNextToken();
        assertEquals(Token.TTYPS, token.value());

    }

    @Test
    public void Scanner_getNextToken_TARRS(){
        List<String> code = new ArrayList<>();
        code.add("arrays");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        Token token = scanner.getNextToken();
        assertEquals(Token.TARRS, token.value());

    }

    @Test
    public void Scanner_getNextToken_TMAIN(){
        List<String> code = new ArrayList<>();
        code.add("main");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        Token token = scanner.getNextToken();
        assertEquals(Token.TMAIN, token.value());

    }

    @Test
    public void Scanner_getNextToken_TBEGN(){
        List<String> code = new ArrayList<>();
        code.add("begin");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        Token token = scanner.getNextToken();
        assertEquals(Token.TBEGN, token.value());

    }

    @Test
    public void Scanner_getNextToken_TEND(){
        List<String> code = new ArrayList<>();
        code.add("end");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        Token token = scanner.getNextToken();
        assertEquals(Token.TEND, token.value());

    }

    @Test
    public void Scanner_getNextToken_TIS(){
        List<String> code = new ArrayList<>();
        code.add("is");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        Token token = scanner.getNextToken();
        assertEquals(Token.TIS, token.value());

    }

    @Test
    public void Scanner_getNextToken_TARAY(){
        List<String> code = new ArrayList<>();
        code.add("array");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        Token token = scanner.getNextToken();
        assertEquals(Token.TARAY, token.value());

    }

    @Test
    public void Scanner_getNextToken_TOF(){
        List<String> code = new ArrayList<>();
        code.add("of");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        Token token = scanner.getNextToken();
        assertEquals(Token.TOF, token.value());

    }

    @Test
    public void Scanner_getNextToken_TFUNC(){
        List<String> code = new ArrayList<>();
        code.add("func");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        Token token = scanner.getNextToken();
        assertEquals(Token.TFUNC, token.value());

    }

    @Test
    public void Scanner_getNextToken_TVOID(){
        List<String> code = new ArrayList<>();
        code.add("void");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        Token token = scanner.getNextToken();
        assertEquals(Token.TVOID, token.value());

    }

    @Test
    public void Scanner_getNextToken_TINTG(){
        List<String> code = new ArrayList<>();
        code.add("integer");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        Token token = scanner.getNextToken();
        assertEquals(Token.TINTG, token.value());

    }

    @Test
    public void Scanner_getNextToken_TREAL(){
        List<String> code = new ArrayList<>();
        code.add("real");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        Token token = scanner.getNextToken();
        assertEquals(Token.TREAL, token.value());

    }

    @Test
    public void Scanner_getNextToken_TBOOL(){
        List<String> code = new ArrayList<>();
        code.add("boolean");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        Token token = scanner.getNextToken();
        assertEquals(Token.TBOOL, token.value());

    }

    @Test
    public void Scanner_getNextToken_TFOR(){
        List<String> code = new ArrayList<>();
        code.add("for");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        Token token = scanner.getNextToken();
        assertEquals(Token.TFOR, token.value());

    }

    @Test
    public void Scanner_getNextToken_TREPT(){
        List<String> code = new ArrayList<>();
        code.add("repeat");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        Token token = scanner.getNextToken();
        assertEquals(Token.TREPT, token.value());

    }

    @Test
    public void Scanner_getNextToken_TUNTL(){
        List<String> code = new ArrayList<>();
        code.add("until");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        Token token = scanner.getNextToken();
        assertEquals(Token.TUNTL, token.value());

    }

    @Test
    public void Scanner_getNextToken_TIFTH(){
        List<String> code = new ArrayList<>();
        code.add("if");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        Token token = scanner.getNextToken();
        assertEquals(Token.TIFTH, token.value());

    }

    @Test
    public void Scanner_getNextToken_TELSE(){
        List<String> code = new ArrayList<>();
        code.add("else");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        Token token = scanner.getNextToken();
        assertEquals(Token.TELSE, token.value());

    }

    @Test
    public void Scanner_getNextToken_TINPT(){
        List<String> code = new ArrayList<>();
        code.add("input");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        Token token = scanner.getNextToken();
        assertEquals(Token.TINPT, token.value());

    }

    @Test
    public void Scanner_getNextToken_TPRIN(){
        List<String> code = new ArrayList<>();
        code.add("print");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        Token token = scanner.getNextToken();
        assertEquals(Token.TPRIN, token.value());

    }

    @Test
    public void Scanner_getNextToken_TPRLN(){
        List<String> code = new ArrayList<>();
        code.add("printline");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        Token token = scanner.getNextToken();
        assertEquals(Token.TPRLN, token.value());

    }

    @Test
    public void Scanner_getNextToken_TRETN(){
        List<String> code = new ArrayList<>();
        code.add("return");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        Token token = scanner.getNextToken();
        assertEquals(Token.TRETN, token.value());

    }

    @Test
    public void Scanner_getNextToken_TEQUL(){
        List<String> code = new ArrayList<>();
        code.add("=");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        Token token = scanner.getNextToken();
        assertEquals(Token.TEQUL, token.value());

    }

    @Test
    public void Scanner_getNextToken_TPLEQ(){
        List<String> code = new ArrayList<>();
        code.add("+=");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        Token token = scanner.getNextToken();
        assertEquals(Token.TPLEQ, token.value());

    }

    @Test
    public void Scanner_getNextToken_TMNEQ(){
        List<String> code = new ArrayList<>();
        code.add("-=");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        Token token = scanner.getNextToken();
        assertEquals(Token.TMNEQ, token.value());

    }

    @Test
    public void Scanner_getNextToken_TSTEQ(){
        List<String> code = new ArrayList<>();
        code.add("*=");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        Token token = scanner.getNextToken();
        assertEquals(Token.TSTEQ, token.value());

    }

    @Test
    public void Scanner_getNextToken_TDVEQ(){
        List<String> code = new ArrayList<>();
        code.add("/=");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        Token token = scanner.getNextToken();
        assertEquals(Token.TDVEQ, token.value());

    }

    @Test
    public void Scanner_getNextToken_TNOT(){
        List<String> code = new ArrayList<>();
        code.add("not");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        Token token = scanner.getNextToken();
        assertEquals(Token.TNOT, token.value());

    }

    @Test
    public void Scanner_getNextToken_TAND(){
        List<String> code = new ArrayList<>();
        code.add("and");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        Token token = scanner.getNextToken();
        assertEquals(Token.TAND, token.value());

    }

    @Test
    public void Scanner_getNextToken_TOR(){
        List<String> code = new ArrayList<>();
        code.add("or");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        Token token = scanner.getNextToken();
        assertEquals(Token.TOR, token.value());
    }

    @Test
    public void Scanner_getNextToken_TXOR(){
        List<String> code = new ArrayList<>();
        code.add("xor");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        Token token = scanner.getNextToken();
        assertEquals(Token.TXOR, token.value());
    }

    @Test
    public void Scanner_getNextToken_TEQEQ(){
        List<String> code = new ArrayList<>();
        code.add("==");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        Token token = scanner.getNextToken();
        assertEquals(Token.TEQEQ, token.value());
    }


    @Test
    public void Scanner_getNextToken_TGRTR(){
        List<String> code = new ArrayList<>();
        code.add(">");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        Token token = scanner.getNextToken();
        assertEquals(Token.TGRTR, token.value());
    }

    @Test
    public void Scanner_getNextToken_TGEQL(){
        List<String> code = new ArrayList<>();
        code.add(">=");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        Token token = scanner.getNextToken();
        assertEquals(Token.TGEQL, token.value());
    }

    @Test
    public void Scanner_getNextToken_TLESS(){
        List<String> code = new ArrayList<>();
        code.add("<");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        Token token = scanner.getNextToken();
        assertEquals(Token.TLESS, token.value());
    }

    @Test
    public void Scanner_getNextToken_TLEQL(){
        List<String> code = new ArrayList<>();
        code.add("<=");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        Token token = scanner.getNextToken();
        assertEquals(Token.TLEQL, token.value());
    }

    @Test
    public void Scanner_getNextToken_TPLUS(){
        List<String> code = new ArrayList<>();
        code.add("+");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        Token token = scanner.getNextToken();
        assertEquals(Token.TPLUS, token.value());
    }

    @Test
    public void Scanner_getNextToken_TMINS(){
        List<String> code = new ArrayList<>();
        code.add("-");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        Token token = scanner.getNextToken();
        assertEquals(Token.TMINS, token.value());
    }

    @Test
    public void Scanner_getNextToken_TSTAR(){
        List<String> code = new ArrayList<>();
        code.add("*");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        Token token = scanner.getNextToken();
        assertEquals(Token.TSTAR, token.value());
    }

    @Test
    public void Scanner_getNextToken_TDIVD(){
        List<String> code = new ArrayList<>();
        code.add("/");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        Token token = scanner.getNextToken();
        assertEquals(Token.TDIVD, token.value());
    }

    @Test
    public void Scanner_getNextToken_TCART(){
        List<String> code = new ArrayList<>();
        code.add("^");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        Token token = scanner.getNextToken();
        assertEquals(Token.TCART, token.value());
    }

    @Test
    public void Scanner_getNextToken_TPERC(){
        List<String> code = new ArrayList<>();
        code.add("%");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        Token token = scanner.getNextToken();
        assertEquals(Token.TPERC, token.value());
    }

    @Test
    public void Scanner_getNextToken_TTRUE(){
        List<String> code = new ArrayList<>();
        code.add("true");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        Token token = scanner.getNextToken();
        assertEquals(Token.TTRUE, token.value());
    }

    @Test
    public void Scanner_getNextToken_TFALS(){
        List<String> code = new ArrayList<>();
        code.add("false");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        Token token = scanner.getNextToken();
        assertEquals(Token.TFALS, token.value());
    }

    @Test
    public void Scanner_getNextToken_TLBRK(){
        List<String> code = new ArrayList<>();
        code.add("[");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        Token token = scanner.getNextToken();
        assertEquals(Token.TLBRK, token.value());
    }

    @Test
    public void Scanner_getNextToken_TRBRK(){
        List<String> code = new ArrayList<>();
        code.add("]");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        Token token = scanner.getNextToken();
        assertEquals(Token.TRBRK, token.value());
    }

    @Test
    public void Scanner_getNextToken_TCOLN(){
        List<String> code = new ArrayList<>();
        code.add(":");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        Token token = scanner.getNextToken();
        assertEquals(Token.TCOLN, token.value());
    }

    @Test
    public void Scanner_getNextToken_TDOT(){
        List<String> code = new ArrayList<>();
        code.add(".");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        Token token = scanner.getNextToken();
        assertEquals(Token.TDOT, token.value());
    }

    @Test
    public void Scanner_getNextToken_TSEMI(){
        List<String> code = new ArrayList<>();
        code.add(";");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        Token token = scanner.getNextToken();
        assertEquals(Token.TSEMI, token.value());
    }

    @Test
    public void Scanner_getNextToken_TLPAR(){
        List<String> code = new ArrayList<>();
        code.add("(");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        Token token = scanner.getNextToken();
        assertEquals(Token.TLPAR, token.value());
    }

    @Test
    public void Scanner_getNextToken_TRPAR(){
        List<String> code = new ArrayList<>();
        code.add(")");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        Token token = scanner.getNextToken();
        assertEquals(Token.TRPAR, token.value());
    }

    @Test
    public void Scanner_getNextToken_TCOMA(){
        List<String> code = new ArrayList<>();
        code.add(",");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        Token token = scanner.getNextToken();
        assertEquals(Token.TCOMA, token.value());
    }

    //NOW FOR THE FUN STUFF OOOOOOOOOOO
    @Test
    public void Scanner_getAllTokens_Iden_Iden(){
        List<String> code = new ArrayList<>();
        code.add("aa aa");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        List<Token> tokens = scanner.getAllTokens();
        assertEquals(Token.TIDEN, tokens.get(0).value());
        assertEquals(Token.TIDEN, tokens.get(1).value());
        assertEquals(Token.TEOF, tokens.get(2).value());
    }




}
