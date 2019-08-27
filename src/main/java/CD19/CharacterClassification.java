package CD19;


/*
 * Jordan Haigh c3256730 CD19
 * public class CharacterClassification
 * Static methods determine if a char is within a certain range of the ascii table
 * */
public class CharacterClassification{
    public static boolean isCharAlphabetical(char c){
        return (65 <= c && c <= 90) || (97 <= c && c <= 122); //if char is a capital or lowercase
    }

    public static boolean isCharNumerical(char c){
        return (48 <= c && c <= 57);
    } //0-9

    public static boolean isCharAssignmentOrRelationalOperator(char c){
        return c == '=' || c == '+' || c == '-' || c == '*' || c == '<' || c == '>';
    }

    public static boolean isCharSingleOperator(char c){
        return c == '(' || c == ')' || c == '[' || c == ']' || c == '^' || c == ':' || c == '.' || c == ',' || c == '%' || c==';';
    }

    public static boolean isCharDelimiter(char c){
        return c == ' ' || c == '\n' || c == '\t';

    }

    //dont forget quote and forward slash!

    public static boolean isCharDefined(char c){
        return isCharAlphabetical(c) || isCharNumerical(c) ||
                isCharAssignmentOrRelationalOperator(c) ||
                isCharSingleOperator(c) || isCharDelimiter(c) ||
                c == '\"' || c == '/' ;

    }


}
