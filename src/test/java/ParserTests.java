import CD19.Observer.SemanticErrorMessage;
import CD19.Parser.Parser;
import CD19.Parser.TreeNode;
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

        tokens.add(new Token(Token.TPRLN,1,1,null));
        tokens.add(new Token(Token.TSTRG,1,1,"\"heres a string\""));

        //tokens.add(new Token(Token.TSEMI, 1, 1, null)); //fail

        tokens.add(new Token(Token.TIDEN, 1, 1, "a"));
        tokens.add(new Token(Token.TEQUL, 1, 1, null));
        tokens.add(new Token(Token.TINTG, 1, 1, null));

        //tokens.add(new Token(Token.TSEMI, 1, 1, null)); //fail

        tokens.add(new Token(Token.TIDEN, 1, 1, "n"));
        tokens.add(new Token(Token.TEQUL, 1, 1, null));
        tokens.add(new Token(Token.TINTG, 1, 1, null));

        tokens.add(new Token(Token.TSEMI, 1, 1, null)); //consume up to here


        tokens.add(new Token(Token.TEND,1,1,null));

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
        List<Token> tokens = new ArrayList<Token>();

        tokens.add(new Token(Token.TCD19, 1, 1, null));
        tokens.add(new Token(Token.TIDEN, 1, 1, "Prog"));

        tokens.add(new Token(Token.TMAIN, 1, 1, null));

        tokens.add(new Token(Token.TIDEN, 1, 1, "a"));
        tokens.add(new Token(Token.TCOLN, 1, 1, null));
        tokens.add(new Token(Token.TINTG, 1, 1, null));

        tokens.add(new Token(Token.TBEGN, 1, 1, null));

        tokens.add(new Token(Token.TIDEN, 1, 1, "a"));
        tokens.add(new Token(Token.TEQUL, 1, 1, null));
        tokens.add(new Token(Token.TILIT, 1, 1, null));
        tokens.add(new Token(Token.TSEMI, 1, 1, null));

        tokens.add(new Token(Token.TEND,1,1,null));

        tokens.add(new Token(Token.TCD19, 1, 1, null));
        tokens.add(new Token(Token.TIDEN, 1, 1, "Flinging"));
        tokens.add(new Token(Token.TEOF, 1, 1, null));


        Parser parser = new Parser(tokens);
        TreeNode tree = parser.parse();

        assertEquals(true, parser.getSemanticErrors().size() == 1);
        assertEquals(true, parser.getSemanticErrors().get(0).getErrorMessage().contains("Start and End"));

        assertEquals(true, parser.isSyntacticallyValid());
        assertEquals(false, parser.isSemanticallyValid());

    }

    @Test
    public void semanticError_type_structiddoesntexist() {
        /**
         CD19 Prog

         main
         a : integer
         begin
         a = 5;
         end

         CD19 Prog
         */
        List<Token> tokens = new ArrayList<Token>();

        tokens.add(new Token(Token.TCD19, 1, 1, null));
        tokens.add(new Token(Token.TIDEN, 1, 1, "Prog"));

        tokens.add(new Token(Token.TIDEN, 1, 1, "mystruct"));
        tokens.add(new Token(Token.TIS, 1, 1, null));
        tokens.add(new Token(Token.TIDEN, 1, 1, "a"));
        tokens.add(new Token(Token.TCOLN, 1, 1, null));
        tokens.add(new Token(Token.TINTG, 1, 1, null));
        tokens.add(new Token(Token.TEND, 1, 1, null));



        tokens.add(new Token(Token.TMAIN, 1, 1, null));

        tokens.add(new Token(Token.TIDEN, 1, 1, "a"));
        tokens.add(new Token(Token.TCOLN, 1, 1, null));
        tokens.add(new Token(Token.TINTG, 1, 1, null));

        tokens.add(new Token(Token.TBEGN, 1, 1, null));

        tokens.add(new Token(Token.TIDEN, 1, 1, "a"));
        tokens.add(new Token(Token.TEQUL, 1, 1, null));
        tokens.add(new Token(Token.TILIT, 1, 1, null));
        tokens.add(new Token(Token.TSEMI, 1, 1, null));

        tokens.add(new Token(Token.TEND,1,1,null));

        tokens.add(new Token(Token.TCD19, 1, 1, null));
        tokens.add(new Token(Token.TIDEN, 1, 1, "Prog"));
        tokens.add(new Token(Token.TEOF, 1, 1, null));


        Parser parser = new Parser(tokens);
        TreeNode tree = parser.parse();

        assertEquals(true, parser.getSemanticErrors().size() == 1);
        assertEquals(true, parser.getSemanticErrors().get(0).getErrorMessage().contains("Start and End"));

        assertEquals(true, parser.isSyntacticallyValid());
        assertEquals(false, parser.isSemanticallyValid());

    }
}
