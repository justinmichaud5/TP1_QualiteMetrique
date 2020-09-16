import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {
    // On suppose que les brackets ouverte de la méthode est dans la même ligne
    public static ParsedClass parse(File file) {
        boolean test = true;
        Pattern p = Pattern.compile("^(private|protected|public)");

        ArrayList<Method> methods = new ArrayList<>();
        boolean inComments = false;
        boolean inMethod = false;
        String className = file.getName().substring(0, file.getName().indexOf("."));
        int classLoc = 0;
        int classCloc = 0;

        String methodName = "";
        int methodLoc = 0;
        int methodCloc = 0;

        int curlyBracketCount = 0;
        int multilineCommentCount = 0;

        try {
            //Inititialisation du Scanner
            Scanner scanner = new Scanner(file);

            // Main loop du parser
            while(scanner.hasNext()) {
                String nextLine = scanner.nextLine().trim();
                //System.out.println(nextLine);
                nextLine = nextLine.replaceAll("\\s+[(]","(");
                Matcher m = p.matcher(nextLine);

                // Cas Ligne Vide || Case Commentaire Vide
                if (nextLine.equals("") || (inComments && nextLine.equals("*"))) continue;

                if (!inComments && m.find() && nextLine.contains("(") && nextLine.contains(")")
                        && nextLine.charAt(nextLine.length() - 1) != ';') {
                    String[] splits = nextLine.split("[(]");

                    inMethod = true;
                    methodName = splits[0].substring(splits[0].lastIndexOf(" ") + 1);
                    methodLoc = 1;
                    methodCloc = multilineCommentCount;
                }

                if (inMethod) {
                    for (int i = 0; i < nextLine.length(); i++) {
                        char c = nextLine.charAt(i);
                        if (c == '{') curlyBracketCount++;
                        else if (c == '}') {
                            curlyBracketCount--;
                            if (curlyBracketCount == 0) {
                                methods.add(new Method(file.getPath(), className, methodName, methodLoc, methodCloc));
                                inMethod = false;
                                multilineCommentCount = 0;
                            }
                        }
                    }
                }

                // Début d'un commentaire multi-lignes
                if (nextLine.contains("/*") && !inComments) {
                    inComments = true;
                    multilineCommentCount = 0;
                }

                // Comptage
                if (!nextLine.contains("//") && !inComments) {
                    if (inMethod) methodLoc++;
                    classLoc++;
                } else {
                    if (inMethod) methodCloc++;
                    classCloc++;
                }

                if (inComments) multilineCommentCount++;

                // Fin d'un commentaire multi-lignes
                if (nextLine.contains("*/")) {
                    inComments = false;
                }
            }

            System.out.println("---  CLASS  ---");
            System.out.println(className + "_LOC : " + Integer.toString(classLoc));
            System.out.println(className + "_CLOC : " + Integer.toString(classCloc));
            System.out.println(className + "_DC : " + Double.toString(((double)classCloc) / classLoc) + "\n");

            System.out.println("--- METHODS ---");
            for (Method m : methods) {
                System.out.println(m.getName() + "_LOC : " + Integer.toString(m.getLoc()));
                System.out.println(m.getName() + "_CLOC : " + Integer.toString(m.getCloc()));
                System.out.println(m.getName() + "_DC : " + Double.toString(m.getDc()) + "\n");
            }



        // Fichier introuvable
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }

        return new ParsedClass(file.getPath(), methods, className, classLoc, classCloc);
    }
}
