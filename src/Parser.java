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
        boolean rightAfterDoc = false;
        String className = file.getName().substring(0, file.getName().indexOf("."));
        int classLoc = 0;
        int classCloc = 0;

        String methodName = "";
        String methodArgs = "";
        int methodLoc = 0;
        int methodCloc = 0;
        int methodCC = 1;

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

                // Si la ligne n'est pas vide, c'est une ligne de code de la classe
                classLoc++;

                // Identification du debut d'une methode
                if (!inComments && m.find() && nextLine.contains("(") && nextLine.contains(")")
                        && nextLine.charAt(nextLine.length() - 1) != ';') {
                    String[] splits = nextLine.split("[(]");
                    splits[1] = splits[1].replaceAll(",\\s+",",");
                    String[] argsSplit = splits[1].split(",");

                    if(splits[1].charAt(0) != ')') {
                        for (int i = 0; i < argsSplit.length; i++) {
                            methodArgs += argsSplit[i].split(" ")[0];
                            if (i != argsSplit.length - 1) {
                                methodArgs += "_";
                            }
                        }
                    }

                    inMethod = true;
                    methodName = splits[0].substring(splits[0].lastIndexOf(" ") + 1);
                    methodCloc = multilineCommentCount;
                    methodLoc = multilineCommentCount;
                    methodCC = 1;
                    multilineCommentCount = 0;
                }

                // La ligne est dans une méthode
                if (inMethod) {
                    methodLoc++;

                    for (int i = 0; i < nextLine.length(); i++) {
                        char c = nextLine.charAt(i);
                        if (c == '{') curlyBracketCount++;
                        else if (c == '}') {
                            curlyBracketCount--;
                            if (curlyBracketCount == 0) {
                                methods.add(new Method(file.getPath(), className, methodName, methodArgs, methodLoc, methodCloc, methodCC));
                                methodArgs = "";
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
                // Si la ligne est une ligne de commentaire
                if (nextLine.contains("//") || inComments){
                    if (inMethod) {
                        methodCloc++;
                    }
                    classCloc++;
                }
                /*if (!nextLine.contains("//") && !inComments) {
                    if (inMethod){
                        methodLoc++;
                    }
                    classLoc++;
                } else { // Si nous sommes en commentaires
                    if (inMethod) {
                        methodCloc++;
                    }
                    classCloc++;
                }*/

                // Cas d'un bloc de commentaire javadoc et calcul de CC
                if (inComments){
                    multilineCommentCount++;
                } else if (inMethod){
                    nextLine = nextLine.replaceAll("\\s+", "");

                    if (nextLine.startsWith("if(") || nextLine.contains("elseif(") || nextLine.contains("while(") ||
                            nextLine.contains("for(") ||(nextLine.startsWith("case") && nextLine.contains(":"))) {
                        methodCC++;
                    }
                }

                // Reinitialise le compte de la javadoc lorsqu'on passe aux autres lignes
                if(rightAfterDoc && !nextLine.startsWith("@")){
                    multilineCommentCount = 0;
                    rightAfterDoc = false;
                }

                // Fin d'un commentaire multi-lignes
                if (nextLine.contains("*/")) {
                    inComments = false;
                    rightAfterDoc = true;
                }
            }

            /*
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
             */



        // Fichier introuvable
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }

        return new ParsedClass(file.getPath(), methods, className, classLoc, classCloc);
    }
}
