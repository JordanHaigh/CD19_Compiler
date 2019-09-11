package CD19.Parser;

import CD19.Scanner.Token;

import java.util.Objects;

public class SymbolTableRecord {
    private int recordKey;
    private int tokenId;
    private String lexeme;
    private String scope; //todo add later in phase

    public SymbolTableRecord(){
        this(Token.TUNDF, "");

    }

    public SymbolTableRecord(int tokenId, String lexeme){
        this.tokenId = tokenId;
        this.lexeme = lexeme;
    }

    public SymbolTableRecord(Token token){
        this.tokenId = token.getTokenID();
        this.lexeme = token.getStr();
    }


    //todo test later
    public SymbolTableRecord(int tokenId, String lexeme, String scope){
       this(tokenId,lexeme);
       this.scope = scope;
       recordKey = hashCode();
    }

    public SymbolTableRecord(Token token,  String scope){
        this(token);
        this.scope = scope;
    }

    public int getRecordKey() {return recordKey; }
    public int getTokenId() { return tokenId; }
    public String getLexeme() { return lexeme; }
    public String getScope() { return scope; }

    public void setRecordKey(int recordKey) {this.recordKey = recordKey; }
    public void setTokenId(int tokenId) { this.tokenId = tokenId; }
    public void setLexeme(String lexeme) { this.lexeme = lexeme; }
    public void setScope(String scope) { this.scope = scope; }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SymbolTableRecord that = (SymbolTableRecord) o;
        return tokenId == that.tokenId &&
                Objects.equals(lexeme, that.lexeme) &&
                Objects.equals(scope, that.scope);
    }

    @Override
    public int hashCode() {
        return Objects.hash(scope, lexeme);
    }


}
