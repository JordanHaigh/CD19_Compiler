package CD19.Parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Stores symbol table records for identifiers found in the program and their data types
 * There are multiple symbol tables for types, constants and identifiers
 *
 * @author Jordan Haigh c3256730
 * @since 29/9/19
 */
public class SymbolTable {

    private Map<Integer, SymbolTableRecord> map;

    public SymbolTable(){
        map = new HashMap<>();
    }

    /**
     * Insert Record into Table
     * @param record  -Record to insert
     */
    public void insert(SymbolTableRecord record){
        map.put(record.getSymbolTableKey(), record);
    }
    /**
     * Remove Record from Table
     * @param record  - Record to remove
     */
    public void remove(SymbolTableRecord record){
        map.remove(record);
    }

    /**
     * Get record from keyId of record in table
     * @param keyId  - Record's keyID we are looking for
     */
    public SymbolTableRecord get(int keyId){
        return map.get(keyId);
    }

    /**
     * Get record from table
     * @param record  - Record we are searching for
     */
    public SymbolTableRecord get(SymbolTableRecord record){
        return map.get(record);
    }

    /**
     * Check if table contains a key id
     * @param keyId  - KeyID we are checking if in table
     * @return - true if in table, false if not
     */
    public boolean contains(int keyId){
        return map.containsKey(keyId);
    }

    /**
     * Check if table contains a record
     * @param record  - record we are checking if in table
     * @return - true if in table, false if not
     */
    public boolean contains(SymbolTableRecord record){
        return map.containsKey(record);
    }

    /**
     * Get all records as list
     * @return Records as list
     */
    public List<SymbolTableRecord> getAllRecords(){
        List<SymbolTableRecord> records = new ArrayList<>();
        records.addAll(map.values());
        return records;
    }

    public void printRecords(){
        List<SymbolTableRecord> records = getAllRecords();
        for(SymbolTableRecord s : records){
            System.out.println(s);
        }

    }


}
