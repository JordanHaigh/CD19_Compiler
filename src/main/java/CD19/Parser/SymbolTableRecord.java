package CD19.Parser;

import CD19.Parser.Nodes.NodeDataTypes;
import CD19.Scanner.Token;

import java.util.Objects;

public class SymbolTableRecord {
    private int symbolTableKey;
    private String lexeme;
    private NodeDataTypes dataType;



    private String scope;

    public SymbolTableRecord(){
        this("",null, "");
    }

    public SymbolTableRecord(String lexeme, NodeDataTypes dataType, String scope){
        this.lexeme = lexeme;
        this.dataType = dataType;
        this.scope = scope;
        symbolTableKey = hashCode();
    }

    public int getSymbolTableKey() { return symbolTableKey; }
    public String getLexeme() { return lexeme; }
    public NodeDataTypes getDataType() { return dataType; }
    public String getScope() { return scope; }


    public void setLexeme(String lexeme) { this.lexeme = lexeme; }
    public void setDataType(NodeDataTypes dataType) { this.dataType = dataType; }
    public void setScope(String scope) { this.scope = scope; }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SymbolTableRecord that = (SymbolTableRecord) o;
        return dataType == that.dataType &&
                Objects.equals(lexeme, that.lexeme);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lexeme, scope);
    }

}
