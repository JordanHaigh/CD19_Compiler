public class A1 {

    public static void main(String[] args){

        System.out.println("https://www.youtube.com/watch?v=kDoUGm-g40Y");

        if(args.length > 0){
            System.out.println("Missing file argument");
            return;
        }

        String filePath = args[0];

        Compiler compiler = new Compiler();
        compiler.compile(filePath);
    }
}
