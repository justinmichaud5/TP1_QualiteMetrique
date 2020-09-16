import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {

    // On suppose que les brackets ouverte de la méthode est dans la même ligne
    public static void parse (String path) {
        boolean test = true;
        File file = new File(path);
        Pattern p = Pattern.compile("^(private|protected|public)");

        ArrayList<Method> methods = new ArrayList<>();
        boolean inComments = false;

        String className = path.substring(path.lastIndexOf("/") + 1, path.lastIndexOf("."));
        int classLoc = 0;
        int classCloc = 0;

        String methodName = "";
        int methodLoc = 0;
        int methodCloc = 0;

        try {
            //Inititialisation du Scanner
            Scanner scanner = new Scanner(file);

            // Main loop du parser
            while(scanner.hasNext()) {
                String nextLine = scanner.nextLine().trim();
                nextLine.replaceAll("\\s+[(]","(");

                // Cas Ligne Vide || Case Commentaire Vide
                if (nextLine.equals("") || (inComments && nextLine.equals("*"))) continue;

                // Début d'un commentaire multi-lignes
                if (nextLine.startsWith("/*") && !inComments) {
                    inComments = true;
                }

                // Comptage
                if (!nextLine.startsWith("//") && !inComments) {
                    classLoc++;
                } else {
                    classCloc++;
                    methodCloc++;
                }

                // Fin d'un commentaire multi-lignes
                if (nextLine.contains("*/")) {
                    inComments = false;
                }
            }

            methods.add(new Method(methodName, methodLoc-1, methodCloc));

            System.out.println(className + "_LOC : " + Integer.toString(classLoc));
            System.out.println(className + "_CLOC : " + Integer.toString(classCloc));
            System.out.println(className + "_DC : " + Double.toString(((double)classCloc) / classLoc));

            for (Method m : methods) {
                System.out.println(m.getName() + "_LOC : " + Integer.toString(m.getLoc()));
                System.out.println(m.getName() + "_CLOC : " + Integer.toString(m.getCloc()));
                System.out.println(m.getName() + "_DC : " + Double.toString(m.getDc()));
            }

        // Fichier introuvable
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }
}
