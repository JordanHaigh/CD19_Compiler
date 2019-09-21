import CD19.Compiler;
import CD19.Parser.Parser;

import java.io.File;

/*
* Jordan Haigh c3256730 CD19
* public class A3
* Used as main entry point for first assignment submission
* */
public class A3 {

    public static void main(String[] args){

        if(args.length != 1){
            System.out.println("Error: Incorrect number of program arguments");
            return;
        }

        String filePath = args[0];

        File file = new File(filePath);
        if(!file.exists()){
            System.out.println("Error: File doesn't exist");
            return;
        }

        Compiler compiler = new Compiler();
        compiler.compile(filePath);
    }
}
