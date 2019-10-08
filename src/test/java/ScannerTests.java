import CD19.Scanner.CodeFileReader;
import CD19.Scanner.Scanner;
import CD19.Scanner.Token;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ScannerTests {

    @Test
    public void Scanner_getNextToken_TCD19(){
        List<String> code = new ArrayList<>();
        code.add("CD19");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        Token token = scanner.getNextToken();
        assertEquals(Token.TCD19, token.getTokenID());

    }

    @Test
    public void Scanner_getNextToken_TCONS(){
        List<String> code = new ArrayList<>();
        code.add("constants");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        Token token = scanner.getNextToken();
        assertEquals(Token.TCONS, token.getTokenID());

    }

    @Test
    public void Scanner_getNextToken_TCNST(){
        List<String> code = new ArrayList<>();
        code.add("const");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        Token token = scanner.getNextToken();
        assertEquals(Token.TCNST, token.getTokenID());

    }

    @Test
    public void Scanner_getNextToken_TTYPS(){
        List<String> code = new ArrayList<>();
        code.add("types");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        Token token = scanner.getNextToken();
        assertEquals(Token.TTYPS, token.getTokenID());

    }

    @Test
    public void Scanner_getNextToken_TARRS(){
        List<String> code = new ArrayList<>();
        code.add("arrays");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        Token token = scanner.getNextToken();
        assertEquals(Token.TARRS, token.getTokenID());

    }

    @Test
    public void Scanner_getNextToken_TMAIN(){
        List<String> code = new ArrayList<>();
        code.add("main");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        Token token = scanner.getNextToken();
        assertEquals(Token.TMAIN, token.getTokenID());

    }

    @Test
    public void Scanner_getNextToken_TBEGN(){
        List<String> code = new ArrayList<>();
        code.add("begin");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        Token token = scanner.getNextToken();
        assertEquals(Token.TBEGN, token.getTokenID());

    }

    @Test
    public void Scanner_getNextToken_TEND(){
        List<String> code = new ArrayList<>();
        code.add("end");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        Token token = scanner.getNextToken();
        assertEquals(Token.TEND, token.getTokenID());

    }

    @Test
    public void Scanner_getNextToken_TIS(){
        List<String> code = new ArrayList<>();
        code.add("is");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        Token token = scanner.getNextToken();
        assertEquals(Token.TIS, token.getTokenID());

    }

    @Test
    public void Scanner_getNextToken_TARAY(){
        List<String> code = new ArrayList<>();
        code.add("array");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        Token token = scanner.getNextToken();
        assertEquals(Token.TARAY, token.getTokenID());

    }

    @Test
    public void Scanner_getNextToken_TOF(){
        List<String> code = new ArrayList<>();
        code.add("of");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        Token token = scanner.getNextToken();
        assertEquals(Token.TOF, token.getTokenID());

    }

    @Test
    public void Scanner_getNextToken_TFUNC(){
        List<String> code = new ArrayList<>();
        code.add("function");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        Token token = scanner.getNextToken();
        assertEquals(Token.TFUNC, token.getTokenID());

    }

    @Test
    public void Scanner_getNextToken_TVOID(){
        List<String> code = new ArrayList<>();
        code.add("void");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        Token token = scanner.getNextToken();
        assertEquals(Token.TVOID, token.getTokenID());

    }

    @Test
    public void Scanner_getNextToken_TINTG(){
        List<String> code = new ArrayList<>();
        code.add("integer");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        Token token = scanner.getNextToken();
        assertEquals(Token.TINTG, token.getTokenID());

    }

    @Test
    public void Scanner_getNextToken_TREAL(){
        List<String> code = new ArrayList<>();
        code.add("real");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        Token token = scanner.getNextToken();
        assertEquals(Token.TREAL, token.getTokenID());

    }

    @Test
    public void Scanner_getNextToken_TBOOL(){
        List<String> code = new ArrayList<>();
        code.add("boolean");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        Token token = scanner.getNextToken();
        assertEquals(Token.TBOOL, token.getTokenID());

    }

    @Test
    public void Scanner_getNextToken_TFOR(){
        List<String> code = new ArrayList<>();
        code.add("for");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        Token token = scanner.getNextToken();
        assertEquals(Token.TFOR, token.getTokenID());

    }

    @Test
    public void Scanner_getNextToken_TREPT(){
        List<String> code = new ArrayList<>();
        code.add("repeat");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        Token token = scanner.getNextToken();
        assertEquals(Token.TREPT, token.getTokenID());

    }

    @Test
    public void Scanner_getNextToken_TUNTL(){
        List<String> code = new ArrayList<>();
        code.add("until");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        Token token = scanner.getNextToken();
        assertEquals(Token.TUNTL, token.getTokenID());

    }

    @Test
    public void Scanner_getNextToken_TIFTH(){
        List<String> code = new ArrayList<>();
        code.add("if");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        Token token = scanner.getNextToken();
        assertEquals(Token.TIFTH, token.getTokenID());

    }

    @Test
    public void Scanner_getNextToken_TELSE(){
        List<String> code = new ArrayList<>();
        code.add("else");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        Token token = scanner.getNextToken();
        assertEquals(Token.TELSE, token.getTokenID());

    }

    @Test
    public void Scanner_getNextToken_TINPT(){
        List<String> code = new ArrayList<>();
        code.add("input");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        Token token = scanner.getNextToken();
        assertEquals(Token.TINPT, token.getTokenID());

    }

    @Test
    public void Scanner_getNextToken_TPRIN(){
        List<String> code = new ArrayList<>();
        code.add("print");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        Token token = scanner.getNextToken();
        assertEquals(Token.TPRIN, token.getTokenID());

    }

    @Test
    public void Scanner_getNextToken_TPRLN(){
        List<String> code = new ArrayList<>();
        code.add("printline");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        Token token = scanner.getNextToken();
        assertEquals(Token.TPRLN, token.getTokenID());

    }

    @Test
    public void Scanner_getNextToken_TRETN(){
        List<String> code = new ArrayList<>();
        code.add("return");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        Token token = scanner.getNextToken();
        assertEquals(Token.TRETN, token.getTokenID());

    }

    @Test
    public void Scanner_getNextToken_TEQUL(){
        List<String> code = new ArrayList<>();
        code.add("=");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        Token token = scanner.getNextToken();
        assertEquals(Token.TEQUL, token.getTokenID());

    }

    @Test
    public void Scanner_getNextToken_TPLEQ(){
        List<String> code = new ArrayList<>();
        code.add("+=");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        Token token = scanner.getNextToken();
        assertEquals(Token.TPLEQ, token.getTokenID());

    }

    @Test
    public void Scanner_getNextToken_TMNEQ(){
        List<String> code = new ArrayList<>();
        code.add("-=");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        Token token = scanner.getNextToken();
        assertEquals(Token.TMNEQ, token.getTokenID());

    }

    @Test
    public void Scanner_getNextToken_TSTEQ(){
        List<String> code = new ArrayList<>();
        code.add("*=");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        Token token = scanner.getNextToken();
        assertEquals(Token.TSTEQ, token.getTokenID());

    }

    @Test
    public void Scanner_getNextToken_TDVEQ(){
        List<String> code = new ArrayList<>();
        code.add("/=");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        Token token = scanner.getNextToken();
        assertEquals(Token.TDVEQ, token.getTokenID());

    }

    @Test
    public void Scanner_getNextToken_TNOT(){
        List<String> code = new ArrayList<>();
        code.add("not");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        Token token = scanner.getNextToken();
        assertEquals(Token.TNOT, token.getTokenID());

    }

    @Test
    public void Scanner_getNextToken_TAND(){
        List<String> code = new ArrayList<>();
        code.add("and");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        Token token = scanner.getNextToken();
        assertEquals(Token.TAND, token.getTokenID());

    }

    @Test
    public void Scanner_getNextToken_TOR(){
        List<String> code = new ArrayList<>();
        code.add("or");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        Token token = scanner.getNextToken();
        assertEquals(Token.TOR, token.getTokenID());
    }

    @Test
    public void Scanner_getNextToken_TXOR(){
        List<String> code = new ArrayList<>();
        code.add("xor");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        Token token = scanner.getNextToken();
        assertEquals(Token.TXOR, token.getTokenID());
    }

    @Test
    public void Scanner_getNextToken_TEQEQ(){
        List<String> code = new ArrayList<>();
        code.add("==");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        Token token = scanner.getNextToken();
        assertEquals(Token.TEQEQ, token.getTokenID());
    }


    @Test
    public void Scanner_getNextToken_TGRTR(){
        List<String> code = new ArrayList<>();
        code.add(">");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        Token token = scanner.getNextToken();
        assertEquals(Token.TGRTR, token.getTokenID());
    }

    @Test
    public void Scanner_getNextToken_TGEQL(){
        List<String> code = new ArrayList<>();
        code.add(">=");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        Token token = scanner.getNextToken();
        assertEquals(Token.TGEQL, token.getTokenID());
    }

    @Test
    public void Scanner_getNextToken_TLESS(){
        List<String> code = new ArrayList<>();
        code.add("<");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        Token token = scanner.getNextToken();
        assertEquals(Token.TLESS, token.getTokenID());
    }

    @Test
    public void Scanner_getNextToken_TLEQL(){
        List<String> code = new ArrayList<>();
        code.add("<=");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        Token token = scanner.getNextToken();
        assertEquals(Token.TLEQL, token.getTokenID());
    }

    @Test
    public void Scanner_getNextToken_TPLUS(){
        List<String> code = new ArrayList<>();
        code.add("+");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        Token token = scanner.getNextToken();
        assertEquals(Token.TPLUS, token.getTokenID());
    }

    @Test
    public void Scanner_getNextToken_TMINS(){
        List<String> code = new ArrayList<>();
        code.add("-");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        Token token = scanner.getNextToken();
        assertEquals(Token.TMINS, token.getTokenID());
    }

    @Test
    public void Scanner_getNextToken_TSTAR(){
        List<String> code = new ArrayList<>();
        code.add("*");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        Token token = scanner.getNextToken();
        assertEquals(Token.TSTAR, token.getTokenID());
    }

    @Test
    public void Scanner_getNextToken_TDIVD(){
        List<String> code = new ArrayList<>();
        code.add("/");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        Token token = scanner.getNextToken();
        assertEquals(Token.TDIVD, token.getTokenID());
    }

    @Test
    public void Scanner_getNextToken_TCART(){
        List<String> code = new ArrayList<>();
        code.add("^");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        Token token = scanner.getNextToken();
        assertEquals(Token.TCART, token.getTokenID());
    }

    @Test
    public void Scanner_getNextToken_TPERC(){
        List<String> code = new ArrayList<>();
        code.add("%");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        Token token = scanner.getNextToken();
        assertEquals(Token.TPERC, token.getTokenID());
    }

    @Test
    public void Scanner_getNextToken_TTRUE(){
        List<String> code = new ArrayList<>();
        code.add("true");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        Token token = scanner.getNextToken();
        assertEquals(Token.TTRUE, token.getTokenID());
    }

    @Test
    public void Scanner_getNextToken_TFALS(){
        List<String> code = new ArrayList<>();
        code.add("false");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        Token token = scanner.getNextToken();
        assertEquals(Token.TFALS, token.getTokenID());
    }

    @Test
    public void Scanner_getNextToken_TLBRK(){
        List<String> code = new ArrayList<>();
        code.add("[");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        Token token = scanner.getNextToken();
        assertEquals(Token.TLBRK, token.getTokenID());
    }

    @Test
    public void Scanner_getNextToken_TRBRK(){
        List<String> code = new ArrayList<>();
        code.add("]");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        Token token = scanner.getNextToken();
        assertEquals(Token.TRBRK, token.getTokenID());
    }

    @Test
    public void Scanner_getNextToken_TCOLN(){
        List<String> code = new ArrayList<>();
        code.add(":");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        Token token = scanner.getNextToken();
        assertEquals(Token.TCOLN, token.getTokenID());
    }

    @Test
    public void Scanner_getNextToken_TDOT(){
        List<String> code = new ArrayList<>();
        code.add(".");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        Token token = scanner.getNextToken();
        assertEquals(Token.TDOT, token.getTokenID());
    }

    @Test
    public void Scanner_getNextToken_TSEMI(){
        List<String> code = new ArrayList<>();
        code.add(";");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        Token token = scanner.getNextToken();
        assertEquals(Token.TSEMI, token.getTokenID());
    }

    @Test
    public void Scanner_getNextToken_TLPAR(){
        List<String> code = new ArrayList<>();
        code.add("(");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        Token token = scanner.getNextToken();
        assertEquals(Token.TLPAR, token.getTokenID());
    }

    @Test
    public void Scanner_getNextToken_TRPAR(){
        List<String> code = new ArrayList<>();
        code.add(")");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        Token token = scanner.getNextToken();
        assertEquals(Token.TRPAR, token.getTokenID());
    }

    @Test
    public void Scanner_getNextToken_TCOMA(){
        List<String> code = new ArrayList<>();
        code.add(",");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        Token token = scanner.getNextToken();
        assertEquals(Token.TCOMA, token.getTokenID());
    }

    @Test
    public void Scanner_getNextToken_TEOF(){
        List<String> code = new ArrayList<>();
        code.add("");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        Token token = scanner.getNextToken();
        assertEquals(Token.TEOF, token.getTokenID());
    }

    //NOW FOR THE FUN STUFF OOOOOOOOOOO
    @Test
    public void Scanner_getAllTokens_Iden_Iden(){
        List<String> code = new ArrayList<>();
        code.add("aa aa");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        List<Token> tokens = scanner.getAllTokens();
        assertEquals(Token.TIDEN, tokens.get(0).getTokenID());
        assertEquals(Token.TIDEN, tokens.get(1).getTokenID());
        assertEquals(Token.TEOF, tokens.get(2).getTokenID());
    }

    @Test
    public void Scanner_getAllTokens_int(){
        List<String> code = new ArrayList<>();
        code.add("124");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        List<Token> tokens = scanner.getAllTokens();
        assertEquals(Token.TILIT, tokens.get(0).getTokenID());
        assertEquals(Token.TEOF, tokens.get(1).getTokenID());
    }

    @Test
    public void Scanner_getAllTokens_float(){
        List<String> code = new ArrayList<>();
        code.add("124.123");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        List<Token> tokens = scanner.getAllTokens();
        assertEquals(Token.TFLIT, tokens.get(0).getTokenID());
        assertEquals(Token.TEOF, tokens.get(1).getTokenID());
    }


    @Test
    public void Scanner_getAllTokens_Iden_UndefinedChar(){
        List<String> code = new ArrayList<>();
        code.add("aa##");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        List<Token> tokens = scanner.getAllTokens();
        assertEquals(Token.TIDEN, tokens.get(0).getTokenID());
        assertEquals(Token.TUNDF, tokens.get(1).getTokenID());
        assertEquals(Token.TEOF, tokens.get(2).getTokenID());

    }
    @Test
    public void Scanner_getAllTokens_Iden_Slash(){
        List<String> code = new ArrayList<>();
        code.add("aa/-=a");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        List<Token> tokens = scanner.getAllTokens();
        assertEquals(Token.TIDEN, tokens.get(0).getTokenID());
        assertEquals(Token.TDIVD, tokens.get(1).getTokenID());
        assertEquals(Token.TMNEQ, tokens.get(2).getTokenID());
        assertEquals(Token.TIDEN, tokens.get(3).getTokenID());
        assertEquals(Token.TEOF, tokens.get(4).getTokenID());

    }

    @Test
    public void Scanner_getAllTokens_Iden_Underscore(){
        List<String> code = new ArrayList<>();
        code.add("aa_a");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        List<Token> tokens = scanner.getAllTokens();
        assertEquals(Token.TIDEN, tokens.get(0).getTokenID());
        assertEquals(Token.TEOF, tokens.get(1).getTokenID());

    }

    @Test
    public void Scanner_getAllTokens_Iden_UnderscoreAtEnd(){
        List<String> code = new ArrayList<>();
        code.add("aa_");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        List<Token> tokens = scanner.getAllTokens();
        assertEquals(Token.TIDEN, tokens.get(0).getTokenID());
        assertEquals(Token.TEOF, tokens.get(1).getTokenID());

    }

    @Test
    public void Scanner_getAllTokens_SemiAfterIden(){
        List<String> code = new ArrayList<>();
        code.add("aa; 100");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        List<Token> tokens = scanner.getAllTokens();
        assertEquals(Token.TIDEN, tokens.get(0).getTokenID());
        assertEquals(Token.TSEMI, tokens.get(1).getTokenID());
        assertEquals(Token.TILIT, tokens.get(2).getTokenID());
        assertEquals(Token.TEOF, tokens.get(3).getTokenID());

    }

    @Test
    public void Scanner_getAllTokens_NegativeInt(){
        List<String> code = new ArrayList<>();
        code.add("-100");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        List<Token> tokens = scanner.getAllTokens();
        assertEquals(Token.TMINS, tokens.get(0).getTokenID());
        assertEquals(Token.TILIT, tokens.get(1).getTokenID());
        assertEquals(Token.TEOF, tokens.get(2).getTokenID());

    }

    @Test
    public void Scanner_getAllTokens_LeftPaddedZeroInt(){
        List<String> code = new ArrayList<>();
        code.add("0000000100");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        List<Token> tokens = scanner.getAllTokens();
        assertEquals(Token.TILIT, tokens.get(0).getTokenID());
        assertEquals(Token.TEOF, tokens.get(1).getTokenID());

    }

    @Test
    public void Scanner_getAllTokens_EqualsSpaceEquals(){
        List<String> code = new ArrayList<>();
        code.add("= =");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        List<Token> tokens = scanner.getAllTokens();
        assertEquals(Token.TEQUL, tokens.get(0).getTokenID());
        assertEquals(Token.TEQUL, tokens.get(1).getTokenID());
        assertEquals(Token.TEOF, tokens.get(2).getTokenID());

    }

    @Test
    public void Scanner_getAllTokens_UnsuccessfulFloatDueToUndefined(){
        List<String> code = new ArrayList<>();
        code.add("100.#");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        List<Token> tokens = scanner.getAllTokens();
        assertEquals(Token.TILIT, tokens.get(0).getTokenID());
        assertEquals(Token.TDOT, tokens.get(1).getTokenID());
        assertEquals(Token.TUNDF, tokens.get(2).getTokenID());
        assertEquals(Token.TEOF, tokens.get(3).getTokenID());

    }

    @Test
    public void Scanner_getAllTokens_UnsuccessfulFloatDueToSpecial(){
        List<String> code = new ArrayList<>();
        code.add("100./");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        List<Token> tokens = scanner.getAllTokens();
        assertEquals(Token.TILIT, tokens.get(0).getTokenID());
        assertEquals(Token.TDOT, tokens.get(1).getTokenID());
        assertEquals(Token.TDIVD, tokens.get(2).getTokenID());
        assertEquals(Token.TEOF, tokens.get(3).getTokenID());

    }

    @Test
    public void Scanner_getAllTokens_UnsuccessfulFloatDueToAlpha(){
        List<String> code = new ArrayList<>();
        code.add("100.a");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        List<Token> tokens = scanner.getAllTokens();
        assertEquals(Token.TILIT, tokens.get(0).getTokenID());
        assertEquals(Token.TDOT, tokens.get(1).getTokenID());
        assertEquals(Token.TIDEN, tokens.get(2).getTokenID());
        assertEquals(Token.TEOF, tokens.get(3).getTokenID());

    }

    @Test
    public void Scanner_getAllTokens_SemiStopsFloat(){
        List<String> code = new ArrayList<>();
        code.add("100.;");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        List<Token> tokens = scanner.getAllTokens();
        assertEquals(Token.TILIT, tokens.get(0).getTokenID());
        assertEquals(Token.TDOT, tokens.get(1).getTokenID());
        assertEquals(Token.TSEMI, tokens.get(2).getTokenID());
        assertEquals(Token.TEOF, tokens.get(3).getTokenID());

    }

    @Test
    public void Scanner_getAllTokens_SemiStopsComment(){
        List<String> code = new ArrayList<>();
        code.add("/-;");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        List<Token> tokens = scanner.getAllTokens();
        assertEquals(Token.TDIVD, tokens.get(0).getTokenID());
        assertEquals(Token.TMINS, tokens.get(1).getTokenID());
        assertEquals(Token.TSEMI, tokens.get(2).getTokenID());
        assertEquals(Token.TEOF, tokens.get(3).getTokenID());

    }

    @Test
    public void Scanner_getAllTokens_BadCommentIden(){
        List<String> code = new ArrayList<>();
        code.add("/-a");  //la dash ah
        Scanner scanner = new Scanner(new CodeFileReader(code));

        List<Token> tokens = scanner.getAllTokens();
        assertEquals(Token.TDIVD, tokens.get(0).getTokenID());
        assertEquals(Token.TMINS, tokens.get(1).getTokenID());
        assertEquals(Token.TIDEN, tokens.get(2).getTokenID());
        assertEquals(Token.TEOF, tokens.get(3).getTokenID());

    }

    @Test
    public void Scanner_getAllTokens_CompleteComment(){
        List<String> code = new ArrayList<>();
        code.add("/--haha u cant see meee");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        List<Token> tokens = scanner.getAllTokens();
        assertEquals(Token.TEOF, tokens.get(0).getTokenID());

    }

    @Test
    public void Scanner_getAllTokens_InlineCompleteComment(){
        List<String> code = new ArrayList<>();
        code.add("iden 100; /--haha u cant see meee");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        List<Token> tokens = scanner.getAllTokens();
        assertEquals(Token.TIDEN, tokens.get(0).getTokenID());
        assertEquals(Token.TILIT, tokens.get(1).getTokenID());
        assertEquals(Token.TSEMI, tokens.get(2).getTokenID());
        assertEquals(Token.TEOF, tokens.get(3).getTokenID());

    }

    @Test
    public void Scanner_getAllTokens_Exclaim_Undefined(){
        List<String> code = new ArrayList<>();
        code.add("!");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        List<Token> tokens = scanner.getAllTokens();
        assertEquals(Token.TUNDF, tokens.get(0).getTokenID());
        assertEquals(Token.TEOF, tokens.get(1).getTokenID());
    }

    @Test
    public void Scanner_getAllTokens_GroupedUndefined_ExclaimAtStart(){
        List<String> code = new ArrayList<>();
        code.add("!##");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        List<Token> tokens = scanner.getAllTokens();
        assertEquals(Token.TUNDF, tokens.get(0).getTokenID());
        assertEquals(Token.TEOF, tokens.get(1).getTokenID());

    }

    @Test
    public void Scanner_getAllTokens_GroupedUndefined_ExclaimMiddle(){
        List<String> code = new ArrayList<>();
        code.add("@!# %");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        List<Token> tokens = scanner.getAllTokens();
        assertEquals(Token.TUNDF, tokens.get(0).getTokenID());
        assertEquals(Token.TPERC, tokens.get(1).getTokenID());
        assertEquals(Token.TEOF, tokens.get(2).getTokenID());

    }

    @Test
    public void Scanner_getAllTokens_GroupedUndefined_ExclaimEnd(){
        List<String> code = new ArrayList<>();
        code.add("@#! ##");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        List<Token> tokens = scanner.getAllTokens();
        assertEquals(Token.TUNDF, tokens.get(0).getTokenID());
        assertEquals(Token.TUNDF, tokens.get(1).getTokenID());
        assertEquals(Token.TEOF, tokens.get(2).getTokenID());

    }

    @Test
    public void Scanner_getAllTokens_GroupedUndefined_NotEqualBetweenUndefined(){
        List<String> code = new ArrayList<>();
        code.add("@!=#! #a");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        List<Token> tokens = scanner.getAllTokens();
        assertEquals(Token.TUNDF, tokens.get(0).getTokenID());
        assertEquals(Token.TNEQL, tokens.get(1).getTokenID());
        assertEquals(Token.TUNDF, tokens.get(2).getTokenID());
        assertEquals(Token.TUNDF, tokens.get(3).getTokenID());
        assertEquals(Token.TIDEN, tokens.get(4).getTokenID());
        assertEquals(Token.TEOF, tokens.get(5).getTokenID());

    }

    @Test
    public void Scanner_getAllTokens_GroupedUndefined_NotEqualAtEnd(){
        List<String> code = new ArrayList<>();
        code.add("@!= #a");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        List<Token> tokens = scanner.getAllTokens();
        assertEquals(Token.TUNDF, tokens.get(0).getTokenID());
        assertEquals(Token.TNEQL, tokens.get(1).getTokenID());
        assertEquals(Token.TUNDF, tokens.get(2).getTokenID());
        assertEquals(Token.TIDEN, tokens.get(3).getTokenID());
        assertEquals(Token.TEOF, tokens.get(4).getTokenID());

    }

    @Test
    public void Scanner_getAllTokens_GroupedUndefined_FailedFloatSpaceIden(){
        List<String> code = new ArrayList<>();
        code.add("100. a");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        List<Token> tokens = scanner.getAllTokens();
        assertEquals(Token.TILIT, tokens.get(0).getTokenID());
        assertEquals(Token.TDOT, tokens.get(1).getTokenID());
        assertEquals(Token.TIDEN, tokens.get(2).getTokenID());
        assertEquals(Token.TEOF, tokens.get(3).getTokenID());

    }


    @Test
    public void Scanner_getAllTokens_TwoDotFloat(){
        List<String> code = new ArrayList<>();
        code.add("10..0");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        List<Token> tokens = scanner.getAllTokens();
        assertEquals(Token.TILIT, tokens.get(0).getTokenID());
        assertEquals(Token.TDOT, tokens.get(1).getTokenID());
        assertEquals(Token.TDOT, tokens.get(2).getTokenID());
        assertEquals(Token.TILIT, tokens.get(3).getTokenID());
        assertEquals(Token.TEOF, tokens.get(4).getTokenID());

    }


    @Test
    public void Scanner_getAllTokens_GroupedUndefined_FailedCommentTurnedIntoMinusEquals(){
        List<String> code = new ArrayList<>();
        code.add("/-=a");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        List<Token> tokens = scanner.getAllTokens();
        assertEquals(Token.TDIVD, tokens.get(0).getTokenID());
        assertEquals(Token.TMNEQ, tokens.get(1).getTokenID());
        assertEquals(Token.TIDEN, tokens.get(2).getTokenID());
        assertEquals(Token.TEOF, tokens.get(3).getTokenID());

    }


    @Test
    public void Scanner_getAllTokens_CompleteString(){
        List<String> code = new ArrayList<>();
        code.add("\"this is text ina string  ###?!!= <= \"");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        List<Token> tokens = scanner.getAllTokens();
        assertEquals(Token.TSTRG, tokens.get(0).getTokenID());
        assertEquals(Token.TEOF, tokens.get(1).getTokenID());
    }

    @Test
    public void Scanner_getAllTokens_FailedString(){
        List<String> code = new ArrayList<>();
        code.add("\"this string wraps around a line so ");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        List<Token> tokens = scanner.getAllTokens();
        assertEquals(Token.TUNDF, tokens.get(0).getTokenID());
        assertEquals(Token.TEOF, tokens.get(1).getTokenID());
    }

    @Test
    public void Scanner_getAllTokens_UppercaseAndLowerCase(){
        List<String> code = new ArrayList<>();
        code.add("Cd19 BeGiN");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        List<Token> tokens = scanner.getAllTokens();
        assertEquals(Token.TCD19, tokens.get(0).getTokenID());
        assertEquals(Token.TBEGN, tokens.get(1).getTokenID());
        assertEquals(Token.TEOF, tokens.get(2).getTokenID());
    }

    @Test
    public void Scanner_getAllTokens_SpaceIden(){
        List<String> code = new ArrayList<>();
        code.add(" a");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        List<Token> tokens = scanner.getAllTokens();
        assertEquals(Token.TIDEN, tokens.get(0).getTokenID());
        assertEquals(Token.TEOF, tokens.get(1).getTokenID());
    }

    @Test
    public void Scanner_getAllTokens_Multiline(){
        List<String> code = new ArrayList<>();
        code.add(" a");
        code.add("123.1020");
        code.add("###");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        List<Token> tokens = scanner.getAllTokens();
        assertEquals(Token.TIDEN, tokens.get(0).getTokenID());
        assertEquals(Token.TFLIT, tokens.get(1).getTokenID());
        assertEquals(Token.TUNDF, tokens.get(2).getTokenID());
        assertEquals(Token.TEOF, tokens.get(3).getTokenID());
    }


    @Test
    public void Scanner_getAllTokens_UnderscoreIden(){
        List<String> code = new ArrayList<>();
        code.add("_dan");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        List<Token> tokens = scanner.getAllTokens();
        assertEquals(Token.TUNDF, tokens.get(0).getTokenID());
        assertEquals(Token.TIDEN, tokens.get(1).getTokenID());
        assertEquals(Token.TEOF, tokens.get(2).getTokenID());
    }

    @Test
    public void Scanner_getAllTokens_UndefDoubleExclaimEquals(){
        List<String> code = new ArrayList<>();
        code.add("@!!=");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        List<Token> tokens = scanner.getAllTokens();
        assertEquals(Token.TUNDF, tokens.get(0).getTokenID());
        assertEquals(Token.TNEQL, tokens.get(1).getTokenID());
        assertEquals(Token.TEOF, tokens.get(2).getTokenID());
    }

    @Test
    public void Scanner_getAllTokens_UndefMultiExclaimEquals(){
        List<String> code = new ArrayList<>();
        code.add("@!!!!!!=");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        List<Token> tokens = scanner.getAllTokens();
        assertEquals(Token.TUNDF, tokens.get(0).getTokenID());
        assertEquals(Token.TNEQL, tokens.get(1).getTokenID());
        assertEquals(Token.TEOF, tokens.get(2).getTokenID());
    }

    @Test
    public void Scanner_getAllTokens_LessLessEqEq(){
        List<String> code = new ArrayList<>();
        code.add("<<==");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        List<Token> tokens = scanner.getAllTokens();
        assertEquals(Token.TLESS, tokens.get(0).getTokenID());
        assertEquals(Token.TLEQL, tokens.get(1).getTokenID());
        assertEquals(Token.TEQUL, tokens.get(2).getTokenID());
        assertEquals(Token.TEOF, tokens.get(3).getTokenID());
    }


    @Test
    public void Scanner_getAllTokens_EmptyString(){
        List<String> code = new ArrayList<>();
        code.add("\"\"");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        List<Token> tokens = scanner.getAllTokens();
        assertEquals(Token.TSTRG, tokens.get(0).getTokenID());
        assertEquals(Token.TEOF, tokens.get(1).getTokenID());
    }

    @Test
    public void Scanner_getAllTokens_SpaceString(){
        List<String> code = new ArrayList<>();
        code.add("\" \"");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        List<Token> tokens = scanner.getAllTokens();
        assertEquals(Token.TSTRG, tokens.get(0).getTokenID());
        assertEquals(Token.TEOF, tokens.get(1).getTokenID());
    }


    @Test
    public void Scanner_getAllTokens_ExclaimQuote(){
        List<String> code = new ArrayList<>();
        code.add("#!!#\"  \n");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        List<Token> tokens = scanner.getAllTokens();
        assertEquals(Token.TUNDF, tokens.get(0).getTokenID());
        assertEquals(Token.TUNDF, tokens.get(1).getTokenID());
        assertEquals(Token.TEOF, tokens.get(2).getTokenID());
    }

    @Test
    public void Scanner_getAllTokens_UnderscoreInUndefined(){
        List<String> code = new ArrayList<>();
        code.add("#_##");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        List<Token> tokens = scanner.getAllTokens();
        assertEquals(Token.TUNDF, tokens.get(0).getTokenID());
        assertEquals(Token.TEOF, tokens.get(1).getTokenID());
    }

    @Test
    public void Scanner_getAllTokens_TwoUnderscores(){
        List<String> code = new ArrayList<>();
        code.add("__");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        List<Token> tokens = scanner.getAllTokens();
        assertEquals(Token.TUNDF, tokens.get(0).getTokenID());
        assertEquals(Token.TEOF, tokens.get(1).getTokenID());
    }

    @Test
    public void Scanner_getAllTokens_PercentEquals(){
        List<String> code = new ArrayList<>();
        code.add("%=");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        List<Token> tokens = scanner.getAllTokens();
        assertEquals(Token.TPERC, tokens.get(0).getTokenID());
        assertEquals(Token.TEQUL, tokens.get(1).getTokenID());
        assertEquals(Token.TEOF, tokens.get(2).getTokenID());
    }

    @Test
    public void Scanner_getAllTokens_TabsInStrings(){
        List<String> code = new ArrayList<>();
        code.add("\"this is string \t more text \"");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        List<Token> tokens = scanner.getAllTokens();
        assertEquals(Token.TSTRG, tokens.get(0).getTokenID());
        assertEquals(Token.TEOF, tokens.get(1).getTokenID());
    }

    @Test
    public void Scanner_getAllTokens_IdenWithNumbers(){
        List<String> code = new ArrayList<>();
        code.add("abc123");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        List<Token> tokens = scanner.getAllTokens();
        assertEquals(Token.TIDEN, tokens.get(0).getTokenID());
        assertEquals(Token.TEOF, tokens.get(1).getTokenID());
    }

    @Test
    public void Scanner_getAllTokens_NotNotEqEq(){
        List<String> code = new ArrayList<>();
        code.add("!!==");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        List<Token> tokens = scanner.getAllTokens();
        assertEquals(Token.TUNDF, tokens.get(0).getTokenID());
        assertEquals(Token.TNEQL, tokens.get(1).getTokenID());
        assertEquals(Token.TEQUL, tokens.get(2).getTokenID());
        assertEquals(Token.TEOF, tokens.get(3).getTokenID());
    }

    @Test
    public void Scanner_getAllTokens_DansFunkyOne(){
        List<String> code = new ArrayList<>();
        code.add("?@!-$$@#");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        List<Token> tokens = scanner.getAllTokens();
        assertEquals(Token.TUNDF, tokens.get(0).getTokenID());
        assertEquals(Token.TMINS, tokens.get(1).getTokenID());
        assertEquals(Token.TUNDF, tokens.get(2).getTokenID());
        assertEquals(Token.TEOF, tokens.get(3).getTokenID());
    }

    @Test
    public void Scanner_getAllTokens_array(){
        List<String> code = new ArrayList<>();
        code.add("array[]");
        Scanner scanner = new Scanner(new CodeFileReader(code));

        List<Token> tokens = scanner.getAllTokens();
        assertEquals(Token.TARAY, tokens.get(0).getTokenID());
        assertEquals(Token.TLBRK, tokens.get(1).getTokenID());
        assertEquals(Token.TRBRK, tokens.get(2).getTokenID());
        assertEquals(Token.TEOF, tokens.get(3).getTokenID());
    }


}
