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
    public void semanticError_badID_prog_idsdifferent() {
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
    public void semanticError_badID_type_structiddoesntexist() {
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
        assertEquals(true, parser.getSemanticErrors().get(0).getErrorMessage().contains("blah"));

        assertEquals(true, parser.isSyntacticallyValid());
        assertEquals(false, parser.isSemanticallyValid());

    }


    @Test
    public void semanticError_badID_paramtypetail_typedoesntexist() {
        List<String> code = new ArrayList<>();

        code.add("CD19 Prog");
        code.add("function myfunction(a : fake) : void"); //error here
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
        assertEquals(true, parser.getSemanticErrors().get(0).getErrorMessage().contains("fake"));

        assertEquals(true, parser.isSyntacticallyValid());
        assertEquals(false, parser.isSemanticallyValid());

    }

    @Test
    public void semanticError_badID_asgnstatorcallstat_asgnstat_iddoesntexist() {
        List<String> code = new ArrayList<>();

        code.add("CD19 Prog");
        code.add("main");
        code.add("a : integer");
        code.add("begin");
        code.add("b = 5;"); //error ere
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
        assertEquals(true, parser.getSemanticErrors().get(0).getErrorMessage().contains("b"));

        assertEquals(true, parser.isSyntacticallyValid());
        assertEquals(false, parser.isSemanticallyValid());

    }

    @Test
    public void semanticError_badID_asgnstatorcallstat_callstat_iddoesntexist() {
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
        assertEquals(true, parser.getSemanticErrors().get(0).getErrorMessage().contains("Function name b"));

        assertEquals(true, parser.isSyntacticallyValid());
        assertEquals(false, parser.isSemanticallyValid());

    }

    @Test
    public void semanticError_badID_alist_iddoesntexist() {
        List<String> code = new ArrayList<>();

        code.add("CD19 Prog");
        code.add("main");
        code.add("a : integer");
        code.add("begin");
        code.add("repeat(b = 5) a = 5; until TRUE;"); //error onb
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
        assertEquals(true, parser.getSemanticErrors().get(0).getErrorMessage().contains("Variable b"));

        assertEquals(true, parser.isSyntacticallyValid());
        assertEquals(false, parser.isSemanticallyValid());

    }

    @Test
    public void semanticError_badID_alist_twoiddoesntexist() {
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
        assertEquals(true, parser.getSemanticErrors().get(0).getErrorMessage().contains("Variable b"));
        assertEquals(true, parser.getSemanticErrors().get(1).getErrorMessage().contains("Variable c"));

        assertEquals(true, parser.isSyntacticallyValid());
        assertEquals(false, parser.isSemanticallyValid());

    }


    @Test
    public void semanticError_badID_var_iddoesntexist() {
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
        assertEquals(true, parser.getSemanticErrors().get(0).getErrorMessage().contains("Variable b"));

        assertEquals(true, parser.isSyntacticallyValid());
        assertEquals(false, parser.isSemanticallyValid());

    }

    @Test
    public void semanticError_goodid_vartail_idexists() {
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

        assertEquals(0, parser.getSemanticErrors().size());

        assertEquals(true, parser.isSyntacticallyValid());
        assertEquals(true, parser.isSemanticallyValid());

    }

    @Test
    public void semanticError_badid_vartail_iddoesntexists() {
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
        code.add("programarray[0].b = 5;");
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
        assertEquals(true, parser.getSemanticErrors().get(0).getErrorMessage().contains("variable b"));
        assertEquals(true, parser.isSyntacticallyValid());
        assertEquals(false, parser.isSemanticallyValid());

    }


    @Test
    public void semanticError_badID_exponent_varorfncalliddoesntexist_var() {
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
        assertEquals(true, parser.getSemanticErrors().get(0).getErrorMessage().contains("Variable b"));

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

    @Test
    public void semanticError_arraysize_arraysizedoesntexist() {
        List<String> code = new ArrayList<>();

        code.add("CD19 Prog");
//        code.add("constants");
//        code.add("mynum =10");
        code.add("types");
        code.add("mystruct is");
        code.add("a : integer");
        code.add("end");
        code.add("myarray is array[mynum] of mystruct");
        code.add("arrays");
        code.add("programarray : myarray");
        code.add("main");
        code.add("a : integer");
        code.add("begin");
        code.add("a =5;");
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
        assertEquals(true, parser.getSemanticErrors().get(0).getErrorMessage().contains("mynum"));

        assertEquals(true, parser.isSyntacticallyValid());
        assertEquals(false, parser.isSemanticallyValid());

    }

    @Test
    public void semanticError_arraysize_arraysizedoesntexist_usingprogramname() {
        List<String> code = new ArrayList<>();

        code.add("CD19 Prog");
//        code.add("constants");
//        code.add("mynum =10");
        code.add("types");
        code.add("mystruct is");
        code.add("a : integer");
        code.add("end");
        code.add("myarray is array[Prog] of mystruct");
        code.add("arrays");
        code.add("programarray : myarray");
        code.add("main");
        code.add("a : integer");
        code.add("begin");
        code.add("a =5;");
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
        assertEquals(true, parser.getSemanticErrors().get(0).getErrorMessage().contains("Prog"));

        assertEquals(true, parser.isSyntacticallyValid());
        assertEquals(false, parser.isSemanticallyValid());

    }


    @Test
    public void semanticError_strongtyping_fact_good() {
        List<String> code = new ArrayList<>();

        code.add("CD19 Prog");
        code.add("main");
        code.add("a : integer");
        code.add("begin");
        code.add("a = 5^5;");
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

        assertEquals(0, parser.getSemanticErrors().size());
        //assertEquals(true, parser.getSemanticErrors().get(0).getErrorMessage().contains("Bad typing"));

        assertEquals(true, parser.isSyntacticallyValid());
        assertEquals(true, parser.isSemanticallyValid());

    }

    @Test
    public void semanticError_strongtyping_facts_front() {
        List<String> code = new ArrayList<>();

        code.add("CD19 Prog");
        code.add("main");
        code.add("a : integer");
        code.add("begin");
        code.add("a = true^5^5;");
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

        assertEquals(2, parser.getSemanticErrors().size());
        assertEquals(true, parser.getSemanticErrors().get(0).getErrorMessage().contains("Mixed"));

        assertEquals(true, parser.isSyntacticallyValid());
        assertEquals(false, parser.isSemanticallyValid());

    }

    @Test
    public void semanticError_strongtyping_facts_mid() {
        List<String> code = new ArrayList<>();

        code.add("CD19 Prog");
        code.add("main");
        code.add("a : integer");
        code.add("begin");
        code.add("a = 5^true^5;");
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

        assertEquals(2, parser.getSemanticErrors().size());
        assertEquals(true, parser.getSemanticErrors().get(0).getErrorMessage().contains("Mixed"));

        assertEquals(true, parser.isSyntacticallyValid());
        assertEquals(false, parser.isSemanticallyValid());

    }

    @Test
    public void semanticError_strongtyping_facts_end() {
        List<String> code = new ArrayList<>();

        code.add("CD19 Prog");
        code.add("main");
        code.add("a : integer,");
        code.add("b : integer");
        code.add("begin");
        code.add("a = b^5^true;");
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

        assertEquals(2, parser.getSemanticErrors().size());
        assertEquals(true, parser.getSemanticErrors().get(0).getErrorMessage().contains("Mixed"));

        assertEquals(true, parser.isSyntacticallyValid());
        assertEquals(false, parser.isSemanticallyValid());

    }


    @Test
    public void semanticError_strongtyping_bools_facts() {
        List<String> code = new ArrayList<>();

        code.add("CD19 Prog");
        code.add("main");
        code.add("a : integer");
        code.add("begin");
        code.add("a = true^true;");
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

        assertEquals(2, parser.getSemanticErrors().size());
        assertEquals(true, parser.getSemanticErrors().get(0).getErrorMessage().contains("booleans"));

        assertEquals(true, parser.isSyntacticallyValid());
        assertEquals(false, parser.isSemanticallyValid());

    }

    @Test
    public void semanticError_strongtyping_bools_facts3() {
        List<String> code = new ArrayList<>();

        code.add("CD19 Prog");
        code.add("main");
        code.add("a : integer");
        code.add("begin");
        code.add("a = true^true^true;");
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

        assertEquals(2, parser.getSemanticErrors().size());
        assertEquals(true, parser.getSemanticErrors().get(0).getErrorMessage().contains("booleans"));

        assertEquals(true, parser.isSyntacticallyValid());
        assertEquals(false, parser.isSemanticallyValid());

    }

    @Test
    public void semanticError_strongtyping_typepromote() {
        List<String> code = new ArrayList<>();

        code.add("CD19 Prog");
        code.add("main");
        code.add("a : integer");
        code.add("begin");
        code.add("a = 5^5.0;");
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
//        assertEquals(true, parser.getSemanticErrors().get(0).getErrorMessage().contains("booleans"));

        assertEquals(true, parser.isSyntacticallyValid());
        assertEquals(false, parser.isSemanticallyValid());

    }

    @Test
    public void semanticError_strongtyping_typepromote_inttofloat() {
        List<String> code = new ArrayList<>();

        code.add("CD19 Prog");
        code.add("main");
        code.add("a : real");
        code.add("begin");
        code.add("a = 5^5.0;");
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

        assertEquals(0, parser.getSemanticErrors().size());
//        assertEquals(true, parser.getSemanticErrors().get(0).getErrorMessage().contains("booleans"));

        assertEquals(true, parser.isSyntacticallyValid());
        assertEquals(true, parser.isSemanticallyValid());

    }

    @Test
    public void semanticError_strongtyping_term_good() {
        List<String> code = new ArrayList<>();

        code.add("CD19 Prog");
        code.add("main");
        code.add("a : integer");
        code.add("begin");
        code.add("a = 5*5;");
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

        assertEquals(0, parser.getSemanticErrors().size());
//        assertEquals(true, parser.getSemanticErrors().get(0).getErrorMessage().contains("booleans"));

        assertEquals(true, parser.isSyntacticallyValid());
        assertEquals(true, parser.isSemanticallyValid());

    }

    @Test
    public void semanticError_strongtyping_term_badfront() {
        List<String> code = new ArrayList<>();

        code.add("CD19 Prog");
        code.add("main");
        code.add("a : integer");
        code.add("begin");
        code.add("a = true*5*5;");
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

        assertEquals(2, parser.getSemanticErrors().size());
        assertEquals(true, parser.getSemanticErrors().get(0).getErrorMessage().contains("Mixed"));

        assertEquals(true, parser.isSyntacticallyValid());
        assertEquals(false, parser.isSemanticallyValid());

    }

    @Test
    public void semanticError_strongtyping_term_badmid() {
        List<String> code = new ArrayList<>();

        code.add("CD19 Prog");
        code.add("main");
        code.add("a : integer");
        code.add("begin");
        code.add("a = 5*true*5;");
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

        assertEquals(2, parser.getSemanticErrors().size());
        assertEquals(true, parser.getSemanticErrors().get(0).getErrorMessage().contains("Mixed"));

        assertEquals(true, parser.isSyntacticallyValid());
        assertEquals(false, parser.isSemanticallyValid());

    }

    @Test
    public void semanticError_strongtyping_term_badend() {
        List<String> code = new ArrayList<>();

        code.add("CD19 Prog");
        code.add("main");
        code.add("a : integer");
        code.add("begin");
        code.add("a = 5*5*true;");
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

        assertEquals(2, parser.getSemanticErrors().size());
        assertEquals(true, parser.getSemanticErrors().get(0).getErrorMessage().contains("Mixed"));

        assertEquals(true, parser.isSyntacticallyValid());
        assertEquals(false, parser.isSemanticallyValid());

    }

    @Test
    public void semanticError_strongtyping_term_allbadtrues() {
        List<String> code = new ArrayList<>();

        code.add("CD19 Prog");
        code.add("main");
        code.add("a : integer");
        code.add("begin");
        code.add("a = true/true*true;");
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

        assertEquals(2, parser.getSemanticErrors().size());
        assertEquals(true, parser.getSemanticErrors().get(0).getErrorMessage().contains("booleans"));

        assertEquals(true, parser.isSyntacticallyValid());
        assertEquals(false, parser.isSemanticallyValid());

    }

    @Test
    public void semanticError_strongtyping_term_typepromo() {
        List<String> code = new ArrayList<>();

        code.add("CD19 Prog");
        code.add("main");
        code.add("a : real");
        code.add("begin");
        code.add("a = 5.5 * 5;");
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

        assertEquals(0, parser.getSemanticErrors().size());
//        assertEquals(true, parser.getSemanticErrors().get(0).getErrorMessage().contains("booleans"));

        assertEquals(true, parser.isSyntacticallyValid());
        assertEquals(true, parser.isSemanticallyValid());

    }


    @Test
    public void semanticError_strongtyping_expr_badfront() {
        List<String> code = new ArrayList<>();

        code.add("CD19 Prog");
        code.add("main");
        code.add("a : integer");
        code.add("begin");
        code.add("a = true+5+5;");
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

        assertEquals(2, parser.getSemanticErrors().size());
        assertEquals(true, parser.getSemanticErrors().get(0).getErrorMessage().contains("Mixed"));

        assertEquals(true, parser.isSyntacticallyValid());
        assertEquals(false, parser.isSemanticallyValid());

    }

    @Test
    public void semanticError_strongtyping_expr_badmid() {
        List<String> code = new ArrayList<>();

        code.add("CD19 Prog");
        code.add("main");
        code.add("a : integer");
        code.add("begin");
        code.add("a = 5+true+5;");
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

        assertEquals(2, parser.getSemanticErrors().size());
        assertEquals(true, parser.getSemanticErrors().get(0).getErrorMessage().contains("Mixed"));

        assertEquals(true, parser.isSyntacticallyValid());
        assertEquals(false, parser.isSemanticallyValid());

    }

    @Test
    public void semanticError_strongtyping_expr_badend() {
        List<String> code = new ArrayList<>();

        code.add("CD19 Prog");
        code.add("main");
        code.add("a : integer");
        code.add("begin");
        code.add("a = 5+5+true;");
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

        assertEquals(2, parser.getSemanticErrors().size());
        assertEquals(true, parser.getSemanticErrors().get(0).getErrorMessage().contains("Mixed"));

        assertEquals(true, parser.isSyntacticallyValid());
        assertEquals(false, parser.isSemanticallyValid());

    }

    @Test
    public void semanticError_strongtyping_expr_allbad() {
        List<String> code = new ArrayList<>();

        code.add("CD19 Prog");
        code.add("main");
        code.add("a : integer");
        code.add("begin");
        code.add("a = true+true+true;");
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

        assertEquals(2, parser.getSemanticErrors().size());
        assertEquals(true, parser.getSemanticErrors().get(0).getErrorMessage().contains("booleans"));

        assertEquals(true, parser.isSyntacticallyValid());
        assertEquals(false, parser.isSemanticallyValid());

    }

    @Test
    public void semanticError_strongtyping_withvariable_good() {
        List<String> code = new ArrayList<>();

        code.add("CD19 Prog");
        code.add("main");
        code.add("a : integer,");
        code.add("b : integer,");
        code.add("c : integer,");
        code.add("d : integer");
        code.add("begin");
        code.add("a = b+c+d;");
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

        assertEquals(0, parser.getSemanticErrors().size());
//        assertEquals(true, parser.getSemanticErrors().get(0).getErrorMessage().contains("booleans"));

        assertEquals(true, parser.isSyntacticallyValid());
        assertEquals(true, parser.isSemanticallyValid());

    }

    @Test
    public void semanticError_strongtyping_withvariable_bad_floatsassignedtoint() {
        List<String> code = new ArrayList<>();

        code.add("CD19 Prog");
        code.add("main");
        code.add("a : integer,");
        code.add("b : real,");
        code.add("c : real,");
        code.add("d : integer");
        code.add("begin");
        code.add("a = b+c+d;");
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
        assertEquals(true, parser.getSemanticErrors().get(0).getErrorMessage().contains("assign"));

        assertEquals(true, parser.isSyntacticallyValid());
        assertEquals(false, parser.isSemanticallyValid());

    }

    @Test
    public void semanticError_strongtyping_assignjustbooltoint() {
        List<String> code = new ArrayList<>();

        code.add("CD19 Prog");
        code.add("main");
        code.add("a : integer");
        code.add("begin");
        code.add("a = true;");
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
        assertEquals(true, parser.getSemanticErrors().get(0).getErrorMessage().contains("assign"));

        assertEquals(true, parser.isSyntacticallyValid());
        assertEquals(false, parser.isSemanticallyValid());

    }

    //////////////////////////////////////////////////
    @Test
    public void semanticError_strongtyping_asgn_b_as_int() {
        List<String> code = new ArrayList<>();

        code.add("CD19 Prog");
        code.add("main");
        code.add("a : integer");
        code.add("begin");
        code.add("a=5;");
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

        assertEquals(0, parser.getSemanticErrors().size());
//        assertEquals(true, parser.getSemanticErrors().get(0).getErrorMessage().contains("booleans"));

        assertEquals(true, parser.isSyntacticallyValid());
        assertEquals(true, parser.isSemanticallyValid());
    }

    @Test
    public void semanticError_strongtyping_asgn_a_as_true() {
        List<String> code = new ArrayList<>();

        code.add("CD19 Prog");
        code.add("main");
        code.add("a : integer");
        code.add("begin");
        code.add("a=true;");
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
        assertEquals(true, parser.getSemanticErrors().get(0).getErrorMessage().contains("assign"));

        assertEquals(true, parser.isSyntacticallyValid());
        assertEquals(false, parser.isSemanticallyValid());
    }

    @Test
    public void semanticError_strongtyping_asgn_a_as_brackets() {
        List<String> code = new ArrayList<>();

        code.add("CD19 Prog");
        code.add("main");
        code.add("a : integer");
        code.add("begin");
        code.add("a=(5+5)-5;");
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

        assertEquals(0, parser.getSemanticErrors().size());
//        assertEquals(false, parser.getSemanticErrors().get(0).getErrorMessage().contains("assign"));

        assertEquals(true, parser.isSyntacticallyValid());
        assertEquals(true, parser.isSemanticallyValid());
    }

    @Test
    public void semanticError_strongtyping_asgn_a_as_double() {
        List<String> code = new ArrayList<>();

        code.add("CD19 Prog");
        code.add("main");
        code.add("a : integer");
        code.add("begin");
        code.add("a=5.5;");
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
        assertEquals(true, parser.getSemanticErrors().get(0).getErrorMessage().contains("assign"));

        assertEquals(true, parser.isSyntacticallyValid());
        assertEquals(false, parser.isSemanticallyValid());
    }

    @Test
    public void semanticError_strongtyping_asgn_a_as_typepromotion() {
        List<String> code = new ArrayList<>();

        code.add("CD19 Prog");
        code.add("main");
        code.add("a : integer");
        code.add("begin");
        code.add("a=5+5.5;");
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
        assertEquals(true, parser.getSemanticErrors().get(0).getErrorMessage().contains("assign"));

        assertEquals(true, parser.isSyntacticallyValid());
        assertEquals(false, parser.isSemanticallyValid());
    }

    @Test
    public void semanticError_strongtyping_asgn_b_withgoodvar() {
        List<String> code = new ArrayList<>();

        code.add("CD19 Prog");
        code.add("main");
        code.add("a : integer,");
        code.add("b : integer");
        code.add("begin");
        code.add("a=5;");
        code.add("b=a+5;");
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

        assertEquals(0, parser.getSemanticErrors().size());
        //assertEquals(true, parser.getSemanticErrors().get(0).getErrorMessage().contains("assign"));

        assertEquals(true, parser.isSyntacticallyValid());
        assertEquals(true, parser.isSemanticallyValid());
    }

    @Test
    public void semanticError_strongtyping_asgn_b_withbooleanvar() {
        List<String> code = new ArrayList<>();

        code.add("CD19 Prog");
        code.add("main");
        code.add("a : boolean,");
        code.add("b : integer");
        code.add("begin");
        code.add("a=true;");
        code.add("b=a+5;");
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

        assertEquals(2, parser.getSemanticErrors().size());
//        assertEquals(true, parser.getSemanticErrors().get(0).getErrorMessage().contains("assign"));

        assertEquals(true, parser.isSyntacticallyValid());
        assertEquals(false, parser.isSemanticallyValid());
    }

    @Test
    public void semanticError_strongtyping_asgn_b_withbooleanvar2() {
        List<String> code = new ArrayList<>();

        code.add("CD19 Prog");
        code.add("main");
        code.add("a : boolean,");
        code.add("b : integer");
        code.add("begin");
        code.add("a=true;");
        code.add("b=5+5+a-5;");
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

        assertEquals(2, parser.getSemanticErrors().size());
//        assertEquals(true, parser.getSemanticErrors().get(0).getErrorMessage().contains("assign"));

        assertEquals(true, parser.isSyntacticallyValid());
        assertEquals(false, parser.isSemanticallyValid());
    }

    @Test
    public void semanticError_strongtyping_constant() {
        List<String> code = new ArrayList<>();

        code.add("CD19 Prog");
        code.add("constants");
        code.add("COUNT = 10");
        code.add("main");
        code.add("a : real,");
        code.add("b : integer");
        code.add("begin");
        code.add("b=5;");
        code.add("a= COUNT + b + COUNT;");
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

        assertEquals(0, parser.getSemanticErrors().size());
        assertEquals(true, parser.isSyntacticallyValid());
        assertEquals(true, parser.isSemanticallyValid());
    }

    @Test
    public void semanticError_strongtyping_constantboolean() {
        List<String> code = new ArrayList<>();

        code.add("CD19 Prog");
        code.add("constants");
        code.add("MYBOOL = true");
        code.add("main");
        code.add("a : boolean");
        code.add("begin");
        code.add("a= MYBOOL + true;");
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
        assertEquals(true, parser.isSyntacticallyValid());
        assertEquals(false, parser.isSemanticallyValid());
    }

    @Test
    public void semanticError_strongtyping_constantboolean2() {
        List<String> code = new ArrayList<>();

        code.add("CD19 Prog");
        code.add("constants");
        code.add("MYBOOL = true");
        code.add("main");
        code.add("a : boolean");
        code.add("begin");
        code.add("a= MYBOOL;");
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

        assertEquals(0, parser.getSemanticErrors().size());
        assertEquals(true, parser.isSyntacticallyValid());
        assertEquals(true, parser.isSemanticallyValid());
    }


    @Test
    public void semanticError_strongtyping_repeatasgnlist() {
        List<String> code = new ArrayList<>();

        code.add("CD19 Prog");
        code.add("main");
        code.add("a : integer,");
        code.add("b : integer");
        code.add("begin");
        code.add("repeat(a = 5,b = 1)");
        code.add("b = b + 1;");
        code.add("until true;");
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

        assertEquals(0, parser.getSemanticErrors().size());
        assertEquals(true, parser.isSyntacticallyValid());
        assertEquals(true, parser.isSemanticallyValid());
    }

    @Test
    public void semanticError_strongtyping_repeatasgnlist_a_assgntobool() {
        List<String> code = new ArrayList<>();

        code.add("CD19 Prog");
        code.add("main");
        code.add("a : integer,");
        code.add("b : integer");
        code.add("begin");
        code.add("repeat(a = true)");
        code.add("b = b + 1;");
        code.add("until true;");
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
        assertEquals(true, parser.isSyntacticallyValid());
        assertEquals(false, parser.isSemanticallyValid());
    }

    @Test
    public void semanticError_strongtyping_functionexistswithargs() {
        List<String> code = new ArrayList<>();

        code.add("CD19 Prog");
        code.add("function myfunc(x : integer) : void");
        code.add("begin");
        code.add("x = 5;");
        code.add("end");

        code.add("main");
        code.add("a : integer,");
        code.add("b : integer");
        code.add("begin");
        code.add("myfunc(a);");
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

        assertEquals(0, parser.getSemanticErrors().size());
        assertEquals(true, parser.isSyntacticallyValid());
        assertEquals(true, parser.isSemanticallyValid());
    }

    @Test
    public void semanticError_strongtyping_functiondoesntexistswithargs() {
        List<String> code = new ArrayList<>();

        code.add("CD19 Prog");
        code.add("function myfunc(x : integer) : void");
        code.add("begin");
        code.add("x = 5;");
        code.add("end");

        code.add("main");
        code.add("a : integer,");
        code.add("b : integer");
        code.add("begin");
        code.add("AAAHHHHINTERNALSCREAMING(a);");
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
        assertEquals(true, parser.isSyntacticallyValid());
        assertEquals(false, parser.isSemanticallyValid());
    }

    @Test
    public void semanticError_strongtyping_functionargdoesntexist() {
        List<String> code = new ArrayList<>();

        code.add("CD19 Prog");
        code.add("function myfunc(x : integer) : void");
        code.add("begin");
        code.add("x = 5;");
        code.add("end");

        code.add("main");
        code.add("a : integer");
        code.add("begin");
        code.add("myfunc(b);");
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
        assertEquals(true, parser.isSyntacticallyValid());
        assertEquals(false, parser.isSemanticallyValid());
    }

    @Test
    public void semanticError_strongtyping_iostat() {
        List<String> code = new ArrayList<>();

        code.add("CD19 Prog");
        code.add("main");
        code.add("a : integer");
        code.add("begin");
        code.add("printline 5+5+a;");
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

        assertEquals(0, parser.getSemanticErrors().size());
        assertEquals(true, parser.isSyntacticallyValid());
        assertEquals(true, parser.isSemanticallyValid());
    }

    @Test
    public void semanticError_strongtyping_iostat_bad() {
        List<String> code = new ArrayList<>();

        code.add("CD19 Prog");
        code.add("main");
        code.add("a : integer");
        code.add("begin");
        code.add("printline 5+5+b;");
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
        assertEquals(true, parser.isSyntacticallyValid());
        assertEquals(false, parser.isSemanticallyValid());
    }

    @Test
    public void semanticError_strongtyping_iostat_bad2() {
        List<String> code = new ArrayList<>();

        code.add("CD19 Prog");
        code.add("main");
        code.add("a : integer");
        code.add("begin");
        code.add("printline 5+b+5;");
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
        assertEquals(true, parser.isSyntacticallyValid());
        assertEquals(false, parser.isSemanticallyValid());
    }

    @Test
    public void semanticError_strongtyping_iostat_STRING() {
        List<String> code = new ArrayList<>();

        code.add("CD19 Prog");
        code.add("main");
        code.add("a : integer");
        code.add("begin");
        code.add("printline \"WHY IS THERE SO MUCH WORK TO THIS\";");
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

        assertEquals(0, parser.getSemanticErrors().size());
        assertEquals(true, parser.isSyntacticallyValid());
        assertEquals(true, parser.isSemanticallyValid());
    }

    @Test
    public void semanticError_strongtyping_input_variabledoesntexist() {
        List<String> code = new ArrayList<>();

        code.add("CD19 Prog");
        code.add("main");
        code.add("a : integer");
        code.add("begin");
        code.add("input b,c;");
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

        assertEquals(2, parser.getSemanticErrors().size());
        assertEquals(true, parser.isSyntacticallyValid());
        assertEquals(false, parser.isSemanticallyValid());
    }

    @Test
    public void semanticError_strongtyping_input_variableexists() {
        List<String> code = new ArrayList<>();

        code.add("CD19 Prog");
        code.add("main");
        code.add("a : integer,");
        code.add("b : integer");
        code.add("begin");
        code.add("input a,b;");
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

        assertEquals(0, parser.getSemanticErrors().size());
        assertEquals(true, parser.isSyntacticallyValid());
        assertEquals(true, parser.isSemanticallyValid());
    }



}
