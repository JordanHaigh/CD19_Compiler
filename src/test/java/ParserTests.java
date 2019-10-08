import CD19.Observer.SemanticErrorMessage;
import CD19.Observer.SyntacticErrorMessage;
import CD19.Parser.Parser;
import CD19.Parser.TreeNode;
import CD19.Scanner.CodeFileReader;
import CD19.Scanner.Scanner;
import CD19.Scanner.Token;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import CD19.Compiler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ParserTests {

    @Test
    public void syntactic_errorrecover_missingendkeywordinstrstat() {
        String code = "CD19 FlingingFM\n" +
                "main\n" +
                "    a :integer,\n" +
                "    n : integer\n" +
                "begin\n" +
                "    /--printline \"heres a rhyme\"\n" +
                "    /--a = 5\n" +
                "    /--n = 5;\n" +
                "\n" +
                "    if(TRUE)\n" +
                "\n" +
                "\n" +
                "end CD19 FlingingFM";

        List<Token> tokens = new ArrayList<Token>();

        tokens.add(new Token(Token.TCD19, 1, 1, null));
        tokens.add(new Token(Token.TIDEN, 1, 1, "Flinging"));

        tokens.add(new Token(Token.TMAIN, 1, 1, null));

        tokens.add(new Token(Token.TIDEN, 1, 1, "a"));
        tokens.add(new Token(Token.TCOLN, 1, 1, null));
        tokens.add(new Token(Token.TINTG, 1, 1, null));

        tokens.add(new Token(Token.TCOMA, 1, 1, null));

        tokens.add(new Token(Token.TIDEN, 1, 1, "n"));
        tokens.add(new Token(Token.TCOLN, 1, 1, null));
        tokens.add(new Token(Token.TINTG, 1, 1, null));


        tokens.add(new Token(Token.TBEGN, 1, 1, null));

        tokens.add(new Token(Token.TIFTH, 1, 1, null));
        tokens.add(new Token(Token.TLPAR, 1, 1, null));
        tokens.add(new Token(Token.TTRUE, 1, 1, null));
        tokens.add(new Token(Token.TRPAR, 1, 1, null));
        //tokens.add(new Token(Token.TEND,1,1,null)); //missing end - so it will consume the end that matches to the begin

        tokens.add(new Token(Token.TEND, 1, 1, null));

        tokens.add(new Token(Token.TCD19, 1, 1, null));
        tokens.add(new Token(Token.TIDEN, 1, 1, "Flinging"));
        tokens.add(new Token(Token.TEOF, 1, 1, null));


        Parser parser = new Parser(tokens);
        parser.parse();
        assertEquals(false, parser.isSyntacticallyValid());
    }

    @Test
    public void syntactic_errorrecover_missingendkeywordinstrstat_readeof() {
        String code = "CD19 FlingingFM\n" +
                "main\n" +
                "    a :integer,\n" +
                "    n : integer\n" +
                "begin\n" +
                "    /--printline \"heres a rhyme\"\n" +
                "    /--a = 5\n" +
                "    /--n = 5;\n" +
                "\n" +
                "    if(TRUE)\n" +
                "\n" +
                "\n" +
                "CD19 FlingingFM";

        List<Token> tokens = new ArrayList<>();

        tokens.add(new Token(Token.TCD19, 1, 1, null));
        tokens.add(new Token(Token.TIDEN, 1, 1, "Flinging"));

        tokens.add(new Token(Token.TMAIN, 1, 1, null));

        tokens.add(new Token(Token.TIDEN, 1, 1, "a"));
        tokens.add(new Token(Token.TCOLN, 1, 1, null));
        tokens.add(new Token(Token.TINTG, 1, 1, null));

        tokens.add(new Token(Token.TCOMA, 1, 1, null));

        tokens.add(new Token(Token.TIDEN, 1, 1, "n"));
        tokens.add(new Token(Token.TCOLN, 1, 1, null));
        tokens.add(new Token(Token.TINTG, 1, 1, null));

        tokens.add(new Token(Token.TBEGN, 1, 1, null));

        tokens.add(new Token(Token.TIFTH, 1, 1, null));
        tokens.add(new Token(Token.TLPAR, 1, 1, null));
        tokens.add(new Token(Token.TTRUE, 1, 1, null));
        tokens.add(new Token(Token.TRPAR, 1, 1, null));
        //tokens.add(new Token(Token.TEND,1,1,null)); //missing end - so it will consume the end that matches to the begin

        //tokens.add(new Token(Token.TEND,1,1,null));

        tokens.add(new Token(Token.TCD19, 1, 1, null));
        tokens.add(new Token(Token.TIDEN, 1, 1, "Flinging"));
        tokens.add(new Token(Token.TEOF, 1, 1, null));


        Parser parser = new Parser(tokens);
        parser.parse();

        assertEquals(false, parser.isSyntacticallyValid());
    }


    @Test
    public void syntactic_errorrecover_missingsemiinstat() {
        String code = "CD19 FlingingFM\n" +
                "main\n" +
                "    a :integer,\n" +
                "    n : integer\n" +
                "begin\n" +
                "    printline \"heres a rhyme\"\n" +
                "    a = 5\n" +
                "    n = 5;\n" +
                "end CD19 FlingingFM";

        List<Token> tokens = new ArrayList<Token>();

        tokens.add(new Token(Token.TCD19, 1, 1, null));
        tokens.add(new Token(Token.TIDEN, 1, 1, "Flinging"));

        tokens.add(new Token(Token.TMAIN, 1, 1, null));

        tokens.add(new Token(Token.TIDEN, 1, 1, "a"));
        tokens.add(new Token(Token.TCOLN, 1, 1, null));
        tokens.add(new Token(Token.TINTG, 1, 1, null));

        tokens.add(new Token(Token.TCOMA, 1, 1, null));

        tokens.add(new Token(Token.TIDEN, 1, 1, "n"));
        tokens.add(new Token(Token.TCOLN, 1, 1, null));
        tokens.add(new Token(Token.TINTG, 1, 1, null));

        tokens.add(new Token(Token.TBEGN, 1, 1, null));

        tokens.add(new Token(Token.TPRLN, 1, 1, null));
        tokens.add(new Token(Token.TSTRG, 1, 1, "\"heres a string\""));

        //tokens.add(new Token(Token.TSEMI, 1, 1, null)); //fail

        tokens.add(new Token(Token.TIDEN, 1, 1, "a"));
        tokens.add(new Token(Token.TEQUL, 1, 1, null));
        tokens.add(new Token(Token.TINTG, 1, 1, null));

        //tokens.add(new Token(Token.TSEMI, 1, 1, null)); //fail

        tokens.add(new Token(Token.TIDEN, 1, 1, "n"));
        tokens.add(new Token(Token.TEQUL, 1, 1, null));
        tokens.add(new Token(Token.TINTG, 1, 1, null));

        tokens.add(new Token(Token.TSEMI, 1, 1, null)); //consume up to here


        tokens.add(new Token(Token.TEND, 1, 1, null));

        tokens.add(new Token(Token.TCD19, 1, 1, null));
        tokens.add(new Token(Token.TIDEN, 1, 1, "Flinging"));
        tokens.add(new Token(Token.TEOF, 1, 1, null));


        Parser parser = new Parser(tokens);
        TreeNode tree = parser.parse();

        assertEquals(false, parser.isSyntacticallyValid());
        assertEquals(TreeNode.NPROG, tree.getValue());
        assertEquals(TreeNode.NGLOB, tree.getLeft().getValue());
        assertEquals(TreeNode.NMAIN, tree.getRight().getValue());

        assertEquals(TreeNode.NSDLST, tree.getRight().getLeft().getValue());
        assertEquals(TreeNode.NPRLN, tree.getRight().getRight().getValue());

    }

    @Test
    public void semanticError_prog_idsdifferent() {
        /**
         CD19 Prog

         main
         a : integer
         begin
         a = 5;
         end

         CD19 Flinging
         */

        List<String> code = new ArrayList<>();
        code.add("CD19 Prog");
        code.add("main");
        code.add("a : integer");
        code.add("begin");
        code.add("a = 5;");
        code.add("end");
        code.add("CD19 blag");

        Scanner scanner = new Scanner(new CodeFileReader(code));
        List<Token> tokens = scanner.getAllTokens();

        Parser parser = new Parser(tokens);
        TreeNode tree = parser.parse();

        System.out.println(tree.prettyPrintTree());
        for (SemanticErrorMessage message : parser.getSemanticErrors())
            System.out.println(message.printAll());

        for (SyntacticErrorMessage message : parser.getSyntacticErrors())
            System.out.println(message.printAll());

        assertEquals(true, parser.getSemanticErrors().size() == 1);
        assertEquals(true, parser.getSemanticErrors().get(0).getErrorMessage().contains("Start and End"));

        assertEquals(true, parser.isSyntacticallyValid());
        assertEquals(false, parser.isSemanticallyValid());

    }

    @Test
    public void semanticError_type_structiddoesntexist() {
        /**
         CD19 Prog

         constants
         length = 10

         types

         mystruct is
         a : integer
         end

         myarray is array[length] of blah

         main
         a : integer
         begin
         a = 5;
         end

         CD19 Prog
         */
        List<String> code = new ArrayList<>();
        code.add("CD19 Prog");
        code.add("constants");
        code.add("length = 10");
        code.add("types");
        code.add("mystruct is");
        code.add("a : integer");
        code.add("end");
        code.add("myarray is array[length] of blah");

        code.add("main");
        code.add("a : integer");
        code.add("begin");
        code.add("a = 5;");
        code.add("end");
        code.add("CD19 Prog");

        Scanner scanner = new Scanner(new CodeFileReader(code));
        List<Token> tokens = scanner.getAllTokens();

        Parser parser = new Parser(tokens);
        TreeNode tree = parser.parse();

        for (SemanticErrorMessage message : parser.getSemanticErrors())
            System.out.println(message.getErrorMessage());

        assertEquals(true, parser.getSemanticErrors().size() == 1);
        assertEquals(true, parser.getSemanticErrors().get(0).getErrorMessage().contains("Struct"));

        assertEquals(true, parser.isSyntacticallyValid());
        assertEquals(false, parser.isSemanticallyValid());

    }


    @Test
    public void semanticError_paramtypetail_arrtypedoesntexist() {
        List<String> code = new ArrayList<>();

        code.add("CD19 Prog");
        code.add("function myfunction(a : fake) : void");
        code.add("begin");
        code.add("print a;");
        code.add("end");

        code.add("main");
        code.add("a : integer");
        code.add("begin");
        code.add("a = 5;");
        code.add("end");
        code.add("CD19 Prog");

        Scanner scanner = new Scanner(new CodeFileReader(code));
        List<Token> tokens = scanner.getAllTokens();

        Parser parser = new Parser(tokens);
        TreeNode tree = parser.parse();

        for (SemanticErrorMessage message : parser.getSemanticErrors())
            System.out.println(message.printAll());

        for (SyntacticErrorMessage message : parser.getSyntacticErrors())
            System.out.println(message.printAll());

        assertEquals(true, parser.getSemanticErrors().size() == 1);
        assertEquals(true, parser.getSemanticErrors().get(0).getErrorMessage().contains("Array"));

        assertEquals(true, parser.isSyntacticallyValid());
        assertEquals(false, parser.isSemanticallyValid());

    }

    @Test
    public void semanticError_asgnstatorcallstat_asgnstat_iddoesntexist() {
        List<String> code = new ArrayList<>();

        code.add("CD19 Prog");
        code.add("main");
        code.add("a : integer");
        code.add("begin");
        code.add("b = 5;");
        code.add("end");
        code.add("CD19 Prog");

        Scanner scanner = new Scanner(new CodeFileReader(code));
        List<Token> tokens = scanner.getAllTokens();

        Parser parser = new Parser(tokens);
        TreeNode tree = parser.parse();

        for (SemanticErrorMessage message : parser.getSemanticErrors())
            System.out.println(message.printAll());

        for (SyntacticErrorMessage message : parser.getSyntacticErrors())
            System.out.println(message.printAll());

        assertEquals(true, parser.getSemanticErrors().size() == 1);
        assertEquals(true, parser.getSemanticErrors().get(0).getErrorMessage().contains("Variable"));

        assertEquals(true, parser.isSyntacticallyValid());
        assertEquals(false, parser.isSemanticallyValid());

    }

    @Test
    public void semanticError_asgnstatorcallstat_callstat_iddoesntexist() {
        List<String> code = new ArrayList<>();

        code.add("CD19 Prog");
        code.add("main");
        code.add("a : integer");
        code.add("begin");
        code.add("b();");
        code.add("end");
        code.add("CD19 Prog");

        Scanner scanner = new Scanner(new CodeFileReader(code));
        List<Token> tokens = scanner.getAllTokens();

        Parser parser = new Parser(tokens);
        TreeNode tree = parser.parse();

        for (SemanticErrorMessage message : parser.getSemanticErrors())
            System.out.println(message.printAll());

        for (SyntacticErrorMessage message : parser.getSyntacticErrors())
            System.out.println(message.printAll());

        assertEquals(true, parser.getSemanticErrors().size() == 1);
        assertEquals(true, parser.getSemanticErrors().get(0).getErrorMessage().contains("Function"));

        assertEquals(true, parser.isSyntacticallyValid());
        assertEquals(false, parser.isSemanticallyValid());

    }

    @Test
    public void semanticError_alist_iddoesntexist() {
        List<String> code = new ArrayList<>();

        code.add("CD19 Prog");
        code.add("main");
        code.add("a : integer");
        code.add("begin");
        code.add("repeat(b = 5) a = 5; until TRUE;");
        code.add("end");
        code.add("CD19 Prog");

        Scanner scanner = new Scanner(new CodeFileReader(code));
        List<Token> tokens = scanner.getAllTokens();

        Parser parser = new Parser(tokens);
        TreeNode tree = parser.parse();

        for (SemanticErrorMessage message : parser.getSemanticErrors())
            System.out.println(message.printAll());

        for (SyntacticErrorMessage message : parser.getSyntacticErrors())
            System.out.println(message.printAll());

        assertEquals(true, parser.getSemanticErrors().size() == 1);
        assertEquals(true, parser.getSemanticErrors().get(0).getErrorMessage().contains("Variable"));

        assertEquals(true, parser.isSyntacticallyValid());
        assertEquals(false, parser.isSemanticallyValid());

    }

    @Test
    public void semanticError_alist_twoiddoesntexist() {
        List<String> code = new ArrayList<>();

        code.add("CD19 Prog");
        code.add("main");
        code.add("a : integer");
        code.add("begin");
        code.add("repeat(b = 5, c=5) a = 5; until TRUE;");
        code.add("end");
        code.add("CD19 Prog");

        Scanner scanner = new Scanner(new CodeFileReader(code));
        List<Token> tokens = scanner.getAllTokens();

        Parser parser = new Parser(tokens);
        TreeNode tree = parser.parse();

        for (SemanticErrorMessage message : parser.getSemanticErrors())
            System.out.println(message.printAll());

        for (SyntacticErrorMessage message : parser.getSyntacticErrors())
            System.out.println(message.printAll());

        assertEquals(2, parser.getSemanticErrors().size());
        assertEquals(true, parser.getSemanticErrors().get(0).getErrorMessage().contains("Variable"));
        assertEquals(true, parser.getSemanticErrors().get(1).getErrorMessage().contains("Variable"));

        assertEquals(true, parser.isSyntacticallyValid());
        assertEquals(false, parser.isSemanticallyValid());

    }


    @Test
    public void semanticError_var_iddoesntexist() {
        List<String> code = new ArrayList<>();

        code.add("CD19 Prog");
        code.add("main");
        code.add("a : integer");
        code.add("begin");
        code.add("input b;");
        code.add("end");
        code.add("CD19 Prog");

        Scanner scanner = new Scanner(new CodeFileReader(code));
        List<Token> tokens = scanner.getAllTokens();

        Parser parser = new Parser(tokens);
        TreeNode tree = parser.parse();

        for (SemanticErrorMessage message : parser.getSemanticErrors())
            System.out.println(message.printAll());

        for (SyntacticErrorMessage message : parser.getSyntacticErrors())
            System.out.println(message.printAll());

        assertEquals(1, parser.getSemanticErrors().size());
        assertEquals(true, parser.getSemanticErrors().get(0).getErrorMessage().contains("Variable"));

        assertEquals(true, parser.isSyntacticallyValid());
        assertEquals(false, parser.isSemanticallyValid());

    }

    @Test
    public void semanticError_vartail_iddoesntexist() {
        List<String> code = new ArrayList<>();

        code.add("CD19 Prog");
        code.add("types");
        code.add("mystruct is");
        code.add("a : integer");
        code.add("end");
        code.add("myarray is array[10] of mystruct");
        code.add("arrays");
        code.add("programarray : myarray");
        code.add("main");
        code.add("b : integer");
        code.add("begin");
        code.add("programarray[0].a = 5;");
        code.add("end");
        code.add("CD19 Prog");

        Scanner scanner = new Scanner(new CodeFileReader(code));
        List<Token> tokens = scanner.getAllTokens();

        Parser parser = new Parser(tokens);
        TreeNode tree = parser.parse();
        System.out.println(tree.prettyPrintTree());
        for (SemanticErrorMessage message : parser.getSemanticErrors())
            System.out.println(message.printAll());

        for (SyntacticErrorMessage message : parser.getSyntacticErrors())
            System.out.println(message.printAll());

        assertEquals(1, parser.getSemanticErrors().size());
        assertEquals(true, parser.getSemanticErrors().get(0).getErrorMessage().contains("Struct"));

        assertEquals(true, parser.isSyntacticallyValid());
        assertEquals(false, parser.isSemanticallyValid());

    }


    @Test
    public void semanticError_vexponent_varorfncalliddoesntexist_var() {
        List<String> code = new ArrayList<>();

        code.add("CD19 Prog");
        code.add("main");
        code.add("a : integer");
        code.add("begin");
        code.add("a = b;");
        code.add("end");
        code.add("CD19 Prog");

        Scanner scanner = new Scanner(new CodeFileReader(code));
        List<Token> tokens = scanner.getAllTokens();

        Parser parser = new Parser(tokens);
        TreeNode tree = parser.parse();
        System.out.println(tree.prettyPrintTree());
        for (SemanticErrorMessage message : parser.getSemanticErrors())
            System.out.println(message.printAll());

        for (SyntacticErrorMessage message : parser.getSyntacticErrors())
            System.out.println(message.printAll());

        assertEquals(1, parser.getSemanticErrors().size());
        assertEquals(true, parser.getSemanticErrors().get(0).getErrorMessage().contains("b"));

        assertEquals(true, parser.isSyntacticallyValid());
        assertEquals(false, parser.isSemanticallyValid());

    }


    @Test
    public void semanticError_exponent_varorfncalliddoesntexist_fncall() {
        List<String> code = new ArrayList<>();

        code.add("CD19 Prog");
        code.add("main");
        code.add("a : integer");
        code.add("begin");
        code.add("a = b();");
        code.add("end");
        code.add("CD19 Prog");

        Scanner scanner = new Scanner(new CodeFileReader(code));
        List<Token> tokens = scanner.getAllTokens();

        Parser parser = new Parser(tokens);
        TreeNode tree = parser.parse();
        System.out.println(tree.prettyPrintTree());
        for (SemanticErrorMessage message : parser.getSemanticErrors())
            System.out.println(message.printAll());

        for (SyntacticErrorMessage message : parser.getSyntacticErrors())
            System.out.println(message.printAll());

        assertEquals(1, parser.getSemanticErrors().size());
        assertEquals(true, parser.getSemanticErrors().get(0).getErrorMessage().contains("Function b"));

        assertEquals(true, parser.isSyntacticallyValid());
        assertEquals(false, parser.isSemanticallyValid());

    }

}
