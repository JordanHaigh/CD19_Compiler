package CD19.Parser;

import CD19.Parser.Nodes.NodeDataTypes;
import CD19.Scanner.Token;

import java.util.Objects;

public class SymbolTableRecord {
    private int symbolTableKey;
    private String lexeme;
    private NodeDataTypes dataType;

    public SymbolTableRecord(){
        this("",NodeDataTypes.Undefined);
    }

    public SymbolTableRecord(String lexeme, NodeDataTypes dataType){
        this.dataType = dataType;
        this.lexeme = lexeme;
        symbolTableKey = hashCode();
    }

    public int getSymbolTableKey() { return symbolTableKey; }
    public String getLexeme() { return lexeme; }
    public NodeDataTypes getDataType() { return dataType; }

    public void setLexeme(String lexeme) { this.lexeme = lexeme; }
    public void setDataType(NodeDataTypes dataType) { this.dataType = dataType; }

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
        return Objects.hash(dataType, lexeme);
    }

}
