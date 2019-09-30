package CD19.Parser.Nodes;

import CD19.Parser.Parser;
import CD19.Parser.SymbolTableRecord;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;

public class NAlistNode implements Node {

    //NASGNS	<alist>	::=	<id><asgnstat> <alistTail>
    //<alistTail>	::=	eps | , <id><asgnstat> <alistTail>

    NAsgnStatNode nAsgnStatNode;

    public NAlistNode() {
        this(NAsgnStatNode.INSTANCE());
    }

    public NAlistNode(NAsgnStatNode nAsgnStatNode) {
        this.nAsgnStatNode = nAsgnStatNode;
    }

    private static NAlistNode instance;

    public static NAlistNode INSTANCE() {
        if (instance == null) {
            instance = new NAlistNode();
        }
        return instance;
    }

    @Override
    public TreeNode make(Parser parser) {
        Token token = parser.peek();

        if (!parser.peekAndConsume(Token.TIDEN)) {
            //Token is not a TIDEN, throw syntactic error and recover if possible
            parser.syntacticError("<alist> - Expected an Identifier", token);
            return new TreeNode(); //todo error recovery here
        }

        //So we have seen an TIDEN

        TreeNode asgnStat = nAsgnStatNode.make(parser);

        TreeNode tail = tail(parser);

        if (tail == null) {
            //tail is null, so we only have one asgnstat
            SymbolTableRecord record = new SymbolTableRecord(token.getStr(), asgnStat.getRight().getType(), token.getStr() + "_" + parser.getScope());
            parser.insertIdentifierRecord(record);
            asgnStat.setSymbol(record);

            //todo data types come later

            return asgnStat;
        } else {
            //insert SymbolTable Record for the AList
            SymbolTableRecord record = new SymbolTableRecord(token.getStr(), asgnStat.getRight().getType(), token.getStr() + "_" + parser.getScope());
            parser.insertIdentifierRecord(record);

            asgnStat.setSymbol(record);

            return new TreeNode(TreeNode.NASGNS, asgnStat, tail);
        }

    }

    private TreeNode tail(Parser parser) {
        //	//<alistTail>	::=	eps | , <id><asgnstat> <alistTail>

        //Add additional Assignments if seen in the token stream
        if (!parser.peekAndConsume(Token.TCOMA)) //eps transition
            return null;

        //More tokens to be added
        Token token = parser.peek();

        if (!parser.peekAndConsume(Token.TIDEN)) {
            parser.syntacticError("<alist> - Expected an Identifier", token);
            return new TreeNode(TreeNode.NUNDEF); //todo error recovery here
        }

        TreeNode asgnStat = nAsgnStatNode.make(parser);
        TreeNode tail = tail(parser);

        if (tail == null) {
            return asgnStat;
        }
        return new TreeNode(TreeNode.NASGNS, asgnStat, tail);

    }
}
