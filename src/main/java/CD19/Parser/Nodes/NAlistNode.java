package CD19.Parser.Nodes;

import CD19.Parser.Parser;
import CD19.Parser.SymbolTableRecord;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;

public class NAlistNode implements Node{

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
        if(token.getTokenID() == Token.TIDEN){
            parser.consume();
            TreeNode asgnStat = nAsgnStatNode.make(parser);
            TreeNode tail = tail(parser);
            if(tail == null){
                SymbolTableRecord record = new SymbolTableRecord(token.getStr(), asgnStat.getRight().getType(),"");//todo fix scope
                parser.insertIdentifierRecord(record);
                //todo data types come later
                asgnStat.setSymbol(record);

                return asgnStat;
            }
            else{
                SymbolTableRecord record = new SymbolTableRecord(token.getStr(), asgnStat.getRight().getType(),"");//todo fix scope
                parser.insertIdentifierRecord(record);

                asgnStat.setSymbol(record);

                return new TreeNode(TreeNode.NASGNS, asgnStat, tail);
            }

        }
        else
            return new TreeNode(TreeNode.NUNDEF); //todo error here

    }

    private TreeNode tail(Parser parser){
        if(parser.peekAndConsume(Token.TCOMA)){
            parser.peekAndConsume(Token.TIDEN);
            TreeNode asgnStat = nAsgnStatNode.make(parser);
            TreeNode tail = tail(parser);
            if(tail == null){
                return asgnStat;
            }
            else{
                return new TreeNode(TreeNode.NASGNS, asgnStat, tail); //todo what to do with id???
            }

        }
        return null; //eps trans

    }
}
