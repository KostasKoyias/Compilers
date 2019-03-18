import java_cup.runtime.*;
import java.io.*;

class Main {
        public static void main(String[] argv) throws Exception{
        Scanner scanner = new Scanner(new InputStreamReader(System.in));
        Parser parser = new Parser(scanner);
        parser.parse();
        System.exit(0);
    }
}

