package CD19.Parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SymbolTable {

    private Map<Integer, SymbolTableRecord> map;

    public SymbolTable(){
        map = new HashMap<>();
    }

    public void insert(SymbolTableRecord record){
        map.put(record.getRecordKey(), record);
    }

    public void remove(SymbolTableRecord record){
        map.remove(record);
    }

    public SymbolTableRecord get(int keyId){
        return map.get(keyId);
    }

    public SymbolTableRecord get(SymbolTableRecord record){
        return map.get(record);
    }

    public boolean contains(int keyId){
        return map.containsKey(keyId);
    }

    public boolean contains(SymbolTableRecord record){
        return map.containsKey(record);
    }
    public List<SymbolTableRecord> getAllRecords(){
        List<SymbolTableRecord> records = new ArrayList<>();
        records.addAll(map.values());
        return records;
    }


}
