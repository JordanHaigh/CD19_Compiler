package CD19.Parser.Nodes;

import java.util.ArrayList;
import java.util.List;

/**
 * Node DataTypes
 *
 * @author Jordan Haigh c3256730
 * @since 29/9/19
 */
public class NodeDataTypes {
    static List<String> dataTypes = new ArrayList<>();

    public static void instantiate(){
        dataTypes.add("Undefined");
        dataTypes.add("Integer");
        dataTypes.add("Boolean");
        dataTypes.add("Real");
        //dataTypes.add("Struct");
        //dataTypes.add("Array");
        dataTypes.add("String");
        dataTypes.add("Void");
    }

    public static void addDataType(String dataType){
        if(!dataTypes.contains(dataType))
            dataTypes.add(dataType);
    }

    public static boolean dataTypeExists(String dataType){
        return dataType.contains(dataType);
    }
}
