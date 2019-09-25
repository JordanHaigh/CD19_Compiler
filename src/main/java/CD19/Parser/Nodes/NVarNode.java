package CD19.Parser.Nodes;

import CD19.Parser.Parser;
import CD19.Parser.SymbolTableRecord;
import CD19.Parser.TreeNode;
import CD19.Scanner.Token;

public class NVarNode implements Node{

    //NSIMV | NARRV	<var>	::=	<id> <varTail>
    //	<varTail>	::=	ε | [<expr>] . <id>

    NVarTailNode nVarTailNode;

    public NVarNode() {
        this(NVarTailNode.INSTANCE());
    }

    public NVarNode(NVarTailNode nVarTailNode) {
        this.nVarTailNode = nVarTailNode;
    }

    private static NVarNode instance;
    public static NVarNode INSTANCE() {
        if (instance == null) {
            instance = new NVarNode();
        }
        return instance;
    }

    @Override
    public TreeNode make(Parser parser) {
        Token id = parser.peek();
        if(id.getTokenID() == Token.TIDEN){
            parser.consume();
            TreeNode tail = nVarTailNode.make(parser);

            if(tail.getValue() == TreeNode.NARRV){
                //look up types symbol table with id
                //get strec
                //now we have strec of struct
                //now i need to get the members of the struct
                //use the name of the struct (scope) to get the member

                return tail;
            }
            else{
                SymbolTableRecord record = new SymbolTableRecord(id.getStr(),NodeDataTypes.String,parser.getScope()); //todo this var is called by vlist and vlist is only called in the "input". the inputs will be made as strings BUT YOU MUST PARSE THEM TO THE RESPECTIVE DATA TYPE
                parser.insertIdentifierRecord(record);// todo remember to parse to appropriate data type.
                tail.setSymbol(record);
                return tail;
            }

        }
        else
            return new TreeNode(TreeNode.NUNDEF); //error detect
    }


}
