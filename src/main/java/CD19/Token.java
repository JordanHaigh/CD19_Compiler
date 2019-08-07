package CD19;// COMP3290 CD19 CD19.Compiler
//
//	CD19.Token class	- constructs a token on behalf of the scanner for it to be sent to the parser.
//			- IDs/FLITs/Strings do not have their symbol table reference set in this class,
//			    this is best done within the scanner as it makes things easier in later phases,
//			    when we are dealing with things like variable scoping.
//
//    Rules of Use: The text for this class has been extracted from a working CD19 scanner.
//			  Code released via Blackboard may not be passed on to anyone outside this
//			  semester's COMP3290 class.
//			  You may not complain or expect any consideration if the code does not work
//			  the way you expect it to.
//			  It is supplied as an assistance and may be used in your project if you wish.
//
//	By COMP3290 Staff - 2019
//
//

import CD19.States.*;


/*
 * Jordan Haigh c3256730 CD19
 * public class Token
 * Code created by COMP3290 Staff. Class is used for token checking
 * */
public class Token {

    public static final int

            TEOF  =  0,	  // CD19.Token value for end of file

    // The 30 keywords

    TCD19 =  1,	TCONS = 2,	TTYPS = 3,	TIS = 4,	TARRS = 5,	TMAIN = 6,
            TBEGN =  7,	TEND  = 8,	TARAY = 9,	TOF = 10,	TFUNC = 11,	TVOID = 12,
            TCNST = 13,	TINTG = 14,	TREAL = 15,	TBOOL = 16,	TFOR  = 17,	TREPT = 18,
            TUNTL = 19,	TIFTH = 20,	TELSE = 21,	TINPT = 22,	TPRIN = 23,	TPRLN = 24,
            TRETN = 25,	TNOT  = 26,	TAND  = 27,	TOR = 28,	TXOR  = 29,	TTRUE = 30,
            TFALS = 31,

    // the operators and delimiters
    TCOMA = 32,	TLBRK = 33,	TRBRK = 34,	TLPAR = 35,	TRPAR = 36,
            TEQUL = 37,	TPLUS = 38,	TMINS = 39,	TSTAR = 40,	TDIVD = 41,	TPERC = 42,
            TCART = 43,	TLESS = 44,	TGRTR = 45,	TCOLN = 46,	TLEQL = 47,	TGEQL = 48,
            TNEQL = 49,	TEQEQ = 50,	TPLEQ = 51,	TMNEQ = 52,	TSTEQ = 53,	TDVEQ = 54,
            TPCEQ = 55,	TSEMI = 56,	TDOT = 57,

    // the tokens which need tuple values

    TIDEN = 58,	TILIT = 59,	TFLIT = 60,	TSTRG = 61,	TUNDF = 62;


    private static final String TPRINT[] = {  //  TPRINT[tokenValue] will produce the associated String
            //  e.g. TPRINT[TMAIN] will be the String "TMAIN ".
            "TEOF  ",
            "TCD19 ",	"TCONS ",	"TTYPS ",	"TIS   ",	"TARRS ",	"TMAIN ",
            "TBEGN ",	"TEND  ",	"TARAY ",	"TOF   ",	"TFUNC ",	"TVOID ",
            "TCNST ",	"TINTG ",	"TREAL ",	"TBOOL ",	"TFOR  ",	"TREPT ",
            "TUNTL ",	"TIFTH ",	"TELSE ",	"TINPT ",	"TPRIN ",	"TPRLN ",
            "TRETN ",	"TNOT  ",	"TAND  ",	"TOR   ",	"TXOR  ",	"TTRUE ",
            "TFALS ",	"TCOMA ",	"TLBRK ",	"TRBRK ",	"TLPAR ",	"TRPAR ",
            "TEQUL ",	"TPLUS ",	"TMINS ",	"TSTAR ",	"TDIVD ",	"TPERC ",
            "TCART ",	"TLESS ",	"TGRTR ",	"TCOLN ",	"TLEQL ",	"TGEQL ",
            "TNEQL ",	"TEQEQ ",	"TPLEQ ",	"TMNEQ ",	"TSTEQ ",	"TDVEQ ",
            "TPCEQ ",	"TSEMI ",	"TDOT  ",

            "TIDEN ",	"TILIT ",	"TFLIT ",	"TSTRG ",	"TUNDF "};


    private int tid;	// token number - for token classification
    private int line;	// line number on listing
    private int pos;	// character position within line
    private String str;	// lexeme - actual character string from scanner for TIDEN/TILIT/TFLIT/TSTRG
    //private StRec symbol;	// symbol table entry - set by Parser in Part 3 - not used in Part 1 todo uncomment
    // This does require a "stub" class for StRec, however........

    public Token(int t, int ln, int p, String s) {  //Constructor takes in token number, line, column & lexeme
        tid = t;
        line = ln;
        pos = p;
        str = s;		// This string is expected to be non-empty only if lexeme has been recognised
        //   as an ID, Integer or Real Literal, String Literal, or an Error (TUNDF).
        // For all other tokens the lexeme has already supplied all information
        //   necessary and so is expected to be passed in as an empty string.
        if (tid == TIDEN) {				// Identifier lexeme could be a reserved keyword
            int v = checkKeywords(s);		// 	(match is case-insensitive)
            if (v > 0) { tid = v; str = null; }	// if keyword, alter token type and set lexeme to null
        }
        //symbol = null;	// initially null, SymTab lookup is done by Parser for TIDEN/TILIT/TFLIT/TSTRG todo uncomment
    }

    public int value() { return tid; }

    public int getLn() { return line; }

    public int getPos() { return pos; }

    public String getStr() { return str; }

    //public StRec getSymbol() { return symbol; } todo uncomment

    //public void setSymbol(StRec x) {symbol = x; }		// Used by the Parser to set the ST Ref field of the CD19.Token tuple todo uncomment

    public String toString() {				// This does NOT produce output for the CD19.Scanner Phase	   *****
        String s = TPRINT[tid]+" " + line + " " + pos;	// It is meant to be used for diagnostic printing only	   *****
        if (str == null) return s;			// It may give you some ideas wrt reporting lexical errors *****
        if (tid != TUNDF)
            s += " " + str;
        else {
            s += " ";
            for (int i=0; i<str.length(); i++) { // output non-printables as ascii codes
                char ch = str.charAt(i);
                int j = (int)ch;
                if (j <= 31 || j >= 127) s += "\\" +j; else s += ch;
            }
        }
        return s;
    }

    public String shortString() {		// This produces a string which may be useful for output in the CD19.Scanner Phase	*****
        String s = TPRINT[tid];		// CD19.Token as a string
        if (str == null) return s;	// If that is all - return
        if (tid != TUNDF) {		// For IDs, ILITS and FLITs - add the lexeme
            s += str + " ";
            int j = (6 - s.length()%6) % 6;
            for (int i=0; i<j; i++)
                s += " ";	// right-fill with spaces
            return s;		// return ID/ILIT/FLIT
        }
        s = "\n" + s;
        for (int i=0; i<str.length(); i++) { // output non-printables as ascii codes
            char ch = str.charAt(i);
            int j = (int)ch;
            if (j <= 31 || j >= 127) s += "\\" +j; else s += ch;
        }
        s += "\n";
        return s;
    }

    private static int checkKeywords(String s) {	// Takes a lexeme recognised as an ID
        // Returns the correct keyword CD19.Token number
        s = s.toLowerCase();		// change to lower case before checking
        if ( s.equals("cd19")      )	return TCD19;
        if ( s.equals("constants") )	return TCONS;
        if ( s.equals("types")     )	return TTYPS;
        if ( s.equals("is")        )	return TIS;
        if ( s.equals("arrays")    )	return TARRS;

        if ( s.equals("main")      )	return TMAIN;
        if ( s.equals("begin")     )	return TBEGN;
        if ( s.equals("end")       )	return TEND;
        if ( s.equals("array")     )	return TARAY;
        if ( s.equals("of")        )	return TOF;
        if ( s.equals("func")      )	return TFUNC;
        if ( s.equals("void")      )	return TVOID;
        if ( s.equals("const")     )	return TCNST;

        if ( s.equals("integer")   )	return TINTG;
        if ( s.equals("real")      )	return TREAL;
        if ( s.equals("boolean")   )	return TBOOL;

        if ( s.equals("for")       )	return TFOR;
        if ( s.equals("repeat")    )	return TREPT;
        if ( s.equals("until")     )	return TUNTL;
        if ( s.equals("if")        )	return TIFTH;
        if ( s.equals("else")      )	return TELSE;

        if ( s.equals("input")     )	return TINPT;
        if ( s.equals("print")     )	return TPRIN;
        if ( s.equals("printline") )	return TPRLN;
        if ( s.equals("return")    )	return TRETN;

        if ( s.equals("and")       )	return TAND;
        if ( s.equals("or")        )	return TOR;
        if ( s.equals("xor")       )	return TXOR;
        if ( s.equals("not")       )	return TNOT;
        if ( s.equals("true")      )	return TTRUE;
        if ( s.equals("false")     )	return TFALS;

        return -1;		// not a Keyword
    }


    //////////////////////////////////////////////
    //extra methods

    public static int checkOperatorsAndDelimiters(String s){
        if ( s.equals(",")  )	return TCOMA;
        if ( s.equals("[")  )	return TLBRK;
        if ( s.equals("]")  )	return TRBRK;
        if ( s.equals("(")  )	return TLPAR;
        if ( s.equals(")")  )	return TRPAR;
        if ( s.equals("=")  )	return TEQUL;
        if ( s.equals("+")  )	return TPLUS;
        if ( s.equals("-")  )	return TMINS;
        if ( s.equals("*")  )	return TSTAR;
        if ( s.equals("/")  )	return TDIVD;
        if ( s.equals("%")  )	return TPERC;
        if ( s.equals("^")  )	return TCART;
        if ( s.equals("<")  )	return TLESS;
        if ( s.equals(">")  )	return TGRTR;
        if ( s.equals(":")  )	return TCOLN;
        if ( s.equals("<=") )	return TLEQL;
        if ( s.equals(">=") )	return TGRTR;
        if ( s.equals("!=") )	return TNEQL;
        if ( s.equals("==") )	return TEQEQ;
        if ( s.equals("+=") )	return TPLEQ;
        if ( s.equals("-=") )	return TMNEQ;
        if ( s.equals("*=") )	return TSTEQ;
        if ( s.equals("/=") )	return TDVEQ;
        if ( s.equals("%=") )	return TPCEQ;
        if ( s.equals(";")  )	return TSEMI;
        if ( s.equals(".")  )	return TDOT;

        return -1;
    }

    public static int findTokenId(String tokenString, State previousState){
        int tokenId;

        tokenId = checkKeywords(tokenString);
        if(tokenId != -1)
            return tokenId;

        //so its not a keyword, check operators and delimiters
        tokenId = checkOperatorsAndDelimiters(tokenString);
        if(tokenId != -1)
            return tokenId;

        //if not keyword and not operator, then it could be:
        //identifier, number, string, undefined token,
        if(previousState instanceof IdentifierState)
            return TIDEN;
        else if(previousState instanceof IntegerState)
            return TILIT;
        else if(previousState instanceof AbsoluteFloatState)
            return TFLIT;
        else if(previousState instanceof StringState)
            return TSTRG;
        else
            return TUNDF; //todo do we want to error handle this?
    }
}
