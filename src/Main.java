import java.io.File;
import java.util.Scanner;

public class Main {

    public static void main (String[] args) {
        // Initliasation du projet
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter relative path to the java file to scan");

        // Valider le path
        String path = scanner.nextLine();
        System.out.println("Path : " + path + "\n");

        File[] fileArray = new File("test_files").listFiles();
        for(File f : fileArray) {
            if(f.getName().endsWith(".java")) // to deal with the .txt files.
            {
               Parser.parse(f); // to read the files
            }
        }
        // Parser (Partie 1)
        // S'occupe de compter les lignes ciblees
    }

}
