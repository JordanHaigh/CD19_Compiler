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
    }

//    public static boolean isCharSpecial(char c){
//        if(c == 33) // !
//            return true;
//        if(35 <= c && c <= 46)
//            return true; // # $ % & ' ( ) * + , - .
//        if(58 <= c && c <= 64)// : ; < = > ? @
//            return true;
//        if(91 <= c && c <= 96)// [ \ ] ^ _ `
//            return true;
//        if(123 <= c && c <= 126)// { | } ~
//            return true;
//
//        return false;
//    }

    public static boolean isCharAssignmentOrRelationalOperator(char c){
        return c == '=' || c == '+' || c == '-' || c == '*' || c == '%' || c == '<' || c == '>';
    }

    public static boolean isCharSingleOperator(char c){
        return c == '(' || c == ')' || c == '[' || c == ']' || c == '^' || c == ':' || c == '.' || c == ',';
    }

    public static boolean isCharDelimiter(char c){
        return c == ' ' || c == '\n' || c == '\t' || c == ';';

    }

    //dont forget quote and forward slash!


}
