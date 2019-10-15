package CD19.Parser;

//	COMP3290 CD19 Compiler
//		Syntax Tree Node Class - Builds a syntax tree node
//
//	By COMP3290 Staff - 2019
//

/**
 * Code from Dan
 * Defines TreeNode used in Parsing section
 *
 * @author Jordan Haigh c3256730
 * @since 29/9/19
 */
import CD19.Parser.Nodes.NodeDataTypes;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TreeNode {

    // SYNTAX TREE NODE VALUES
    // ***********************
    public static final int NUNDEF = 0,
            NPROG = 1,		NGLOB = 2,		NILIST = 3,		NINIT = 4,		NFUNCS = 5,
            NMAIN = 6,		NSDLST = 7,		NTYPEL = 8,		NRTYPE = 9,		NATYPE = 10,
            NFLIST = 11,	NSDECL = 12,	NALIST = 13,	NARRD = 14,		NFUND = 15,
            NPLIST = 16,	NSIMP = 17,		NARRP = 18,		NARRC = 19,		NDLIST = 20,
            NSTATS = 21,	NFOR = 22,		NREPT = 23,		NASGNS = 24,	NIFTH = 25,
            NIFTE = 26,		NASGN = 27,		NPLEQ = 28,		NMNEQ = 29,		NSTEQ = 30,
            NDVEQ = 31,		NINPUT = 32,	NPRINT = 33,	NPRLN = 34,		NCALL = 35,
            NRETN = 36,		NVLIST = 37,	NSIMV = 38,		NARRV = 39,		NEXPL = 40,
            NBOOL = 41,		NNOT = 42,		NAND = 43,		NOR = 44,		NXOR = 45,
            NEQL = 46,		NNEQ = 47,		NGRT = 48,		NLSS = 49,		NLEQ = 50,
            NADD = 51,		NSUB = 52,		NMUL = 53,		NDIV = 54,		NMOD = 55,
            NPOW = 56,		NILIT = 57,		NFLIT = 58,		NTRUE = 59,		NFALS = 60,
            NFCALL = 61,	NPRLST = 62,	NSTRG = 63,		NGEQ = 64;

    private static final String PRINTNODE[] = {  	//  PRINTNODE[TreeNode Value] will produce the associated String
            //  e.g. PRINTNODE[NPROG] will be the String "NPROG".
            "NUNDEF",
            "NPROG ",	"NGLOB ",	"NILIST",	"NINIT ",	"NFUNCS",
            "NMAIN ",	"NSDLST",	"NTYPEL",	"NRTYPE",	"NATYPE",
            "NFLIST",	"NSDECL",	"NALIST",	"NARRD ",	"NFUND ",
            "NPLIST",	"NSIMP ",	"NARRP ",	"NARRC ",	"NDLIST",
            "NSTATS",	"NFOR  ",	"NREPT ",	"NASGNS",	"NIFTH ",
            "NIFTE ",	"NASGN ",	"NPLEQ ",	"NMNEQ ",	"NSTEQ ",
            "NDVEQ ",	"NINPUT",	"NPRINT",	"NPRLN ",	"NCALL ",
            "NRETN ",	"NVLIST",	"NSIMV ",	"NARRV ",	"NEXPL ",
            "NBOOL ",	"NNOT  ",	"NAND  ",	"NOR   ",	"NXOR  ",
            "NEQL  ",	"NNEQ  ",	"NGRT  ",	"NLSS  ",	"NLEQ  ",
            "NADD  ",	"NSUB  ",	"NMUL  ",	"NDIV  ",	"NMOD  ",
            "NPOW  ",	"NILIT ",	"NFLIT ",	"NTRUE ",	"NFALS ",
            "NFCALL",	"NPRLST",	"NSTRG ",	"NGEQ  " };


    private static int count = 0;

    private int nodeValue;
    private String nodeValueString;
    private TreeNode left,middle,right;
    private SymbolTableRecord symbol;
    private String dataType;

    public TreeNode(){
        this(TreeNode.NUNDEF);
    }

    public TreeNode (int value) {
        nodeValue = value;
        nodeValueString = PRINTNODE[value];
        left = null;
        middle = null;
        right = null;
        symbol = null;
        dataType = null;
    }

    public TreeNode (int value, SymbolTableRecord st) {
        this(value);
        symbol = st;
        if(st != null)
            dataType = st.getDataType();
    }

    public TreeNode (int value, TreeNode l, TreeNode r) {
        this(value);
        left = l;
        right = r;
    }

    public TreeNode (int value, TreeNode l, TreeNode m, TreeNode r) {
        this(value,l,r);
        middle = m;
    }

    public int getValue() { return nodeValue; }

    public TreeNode getLeft() { return left; }

    public TreeNode getMiddle() { return middle; }

    public TreeNode getRight() { return right; }

    public SymbolTableRecord getSymbol() { return symbol; }

    public String getType() { return dataType; }

    public void setValue(int value) { nodeValue = value;
        nodeValueString = PRINTNODE[value];
    }

    public void setLeft(TreeNode l) { left = l; }

    public void setMiddle(TreeNode m) { middle = m; }

    public void setRight(TreeNode r) { right = r; }

    public void setSymbol(SymbolTableRecord st) { symbol = st; }

    public void setType(String st) {
        dataType = st;
        NodeDataTypes.addDataType(st);
    }

    //
    // Call is: TreeNode.danPrintTree(outfile, rootOfTree);
    //	-> prints tree pre-order as a flat 7 values per line
    //
    //   I am used to this type of print - if you cannot use
    //	it then you are free to implement your own XML or
    //	whatever you like tree output routine.
    //

    public static void danPrintTree(PrintWriter out, TreeNode tr) {
        if (tr.nodeValue == NPROG) count = 0;
        out.print(PRINTNODE[tr.nodeValue]+" ");
        count++;
        if (count%10 == 0){
            out.println();
            count = 0;
        }
        if (tr.symbol != null) {
            int lexemeLength = tr.getSymbol().getLexeme().length();
            int numberOfSpacesLexemeTakesUp =(int)Math.ceil(lexemeLength / 7.0);
            int numberOfPads = numberOfSpacesLexemeTakesUp * 7 - lexemeLength;

            out.print(tr.symbol.getLexeme() + " ");

            for(int i = 0; i < numberOfPads;i++){
                out.print(" ");
            }

            count += numberOfSpacesLexemeTakesUp;


            if (count%10 == 0 || count > 10) {
                out.println();
                count = 0;
            }
        }
//        if (tr.type   != null) {
//            out.print(  tr.type.getName() + " ");
//            count++;
//            if (count%7 == 0) out.println();
//        }
        if (tr.left   != null) { danPrintTree(out,tr.left);   }
        if (tr.middle != null) { danPrintTree(out,tr.middle); }
        if (tr.right  != null) { danPrintTree(out,tr.right);  }
        if (tr.nodeValue == NPROG && count%10 != 0) {
            out.println();
            count = 0;
        }
    }

    //pretty version
    public String prettyPrintTree(){
        return prettyPrintTree("", true);
    }

    private String prettyPrintTree(String prefix, boolean isTail){

        String result = prefix + (isTail ? "\\--- " : "|--- ") + toString() + "\n";

        TreeNode l = getLeft();
        TreeNode m = getMiddle();
        TreeNode r = getRight();
        List<TreeNode> children = new ArrayList<TreeNode>();

        if(l != null) children.add(l);
        if(m != null) children.add(m);
        if(r != null) children.add(r);

        for (int i = 0; i < children.size() - 1; i++) {
            result += children.get(i).prettyPrintTree(prefix + (isTail ? "     " : "|    "), false);
        }
        if(children.size() > 0) {
            result += children.get(children.size() - 1).prettyPrintTree(prefix + (isTail ? "     " : "|    "), true);
        }

        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("TreeNode{ ")
                .append(nodeValue)
                .append(" ")
                .append(nodeValueString);
        if(symbol !=null){
            sb.append("(").append(symbol.getLexeme()).append(")");
            sb.append("Scope: " + symbol.getScope());
        }
        sb.append("}");

        return sb.toString();
    }

    public void updateType(String firstType, String secondType){
        if(firstType == null && secondType != null){
            //weird left factoring edge case. just set to left and hope it doesnt come back to bite you in the ass
            this.setType(secondType);
            return;
        }
        if(secondType == null && firstType != null){
            this.setType(firstType);
            return;
        }
        boolean typePromotion = (firstType.equals("Integer") && secondType.equals("Real")) || //if one is int and one is real, promote to real
                (firstType.equals("Real") && secondType.equals("Integer"));

//        if(firstType.equals("Boolean")){
//            this.setType("Boolean");
//        }
        if(typePromotion){
            this.setType("Real");
        }
        else if(!firstType.equals(secondType)){ //edge case for when you have int a = 5^5^true. the true part wouldn't be picked up at the end so this should hopefully get it
            this.setType("Mixed");
        }
        else if(firstType.equals("Boolean") || secondType.equals("Boolean")){
            this.setType("Boolean");
        }
        else{
            //same types all good
            this.setType(firstType);
        }

    }
}
