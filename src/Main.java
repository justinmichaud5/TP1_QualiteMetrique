import java.io.File;
import java.util.Scanner;

public class Main {

    public static void main (String[] args) {
        // Initliasation du projet
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter relative path to the java file to scan");

        // Valider le path
        String path = scanner.nextLine();
        if (!path.endsWith(".java")) path = path + ".java";
        System.out.println("Path : " + path + "\n");


        // Parser (Partie 1)
        // S'occupe de compter les lignes ciblees
        Parser.parse(path);
    }

}
