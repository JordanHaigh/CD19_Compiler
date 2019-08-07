import CD19.Compiler;

/*
* Jordan Haigh CD19
* public class A1
* Used as main entry point for first assignment submission
* */
public class A1 {

    public static void main(String[] args){


        int a = 10;
        a %= 3;
        System.out.println(a);

        if(args.length != 1){
            System.out.println("Incorrect number of program arguments");
            return;
        }

        String filePath = args[0];

        Compiler compiler = new Compiler();
        compiler.compile(filePath);
    }
}
