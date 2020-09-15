import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class Parser {

    public static void parse (File file) {
        int classLoc = 0;
        int classCloc = 0;
        boolean inComments = false;

        try {
            //Inititialisation du Scanner
            Scanner scanner = new Scanner(file);

            // Main loop du parser
            while(scanner.hasNext()) {
                String nextLine = scanner.nextLine().trim();

                // Cas Ligne Vide || Case Commentaire Vide
                if (nextLine.equals("") || (inComments && nextLine.equals("*"))) continue;

                // Début d'un commentaire multi-lignes
                if (nextLine.startsWith("/*") && !inComments) {
                    inComments = true;
                    System.out.println("TRUE");
                }

                // Comptage
                classCloc++;
                if (!nextLine.startsWith("//") && !inComments) {
                    classLoc++;
                } else if (!inComments) {
                    System.out.println("SINGLE");
                }

                // Debug Prints
                System.out.println(
                    nextLine +
                    "\tclass_LOC = " + Integer.toString(classLoc) +
                    "\tclass_CLOC = " + Integer.toString(classCloc)
                );

                // Fin d'un commentaire multi-lignes
                if (nextLine.contains("*/")) {
                    inComments = false;
                    System.out.println("FALSE");
                }
            }
        // Fichier introuvable
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }
}
