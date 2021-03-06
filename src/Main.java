import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * Classe qui initialise le programe et demare les algorithmes d'evaluation
 */
public class Main {

    /**
     * Ce programme cherche a trouver les cas ou les developpeurs ont cree du code complique sans un
     * niveau de documentation adequat.
     *
     * @param args les arguments en entree, particulierement utile pour faire rouler les tests unitaires avec "-ea"
     */
    public static void main (String[] args) {
        for (String arg : args) {
            if (arg.compareTo("-ea") == 0) {
                MethodTest test = new MethodTest();
                test.testAttributes();

                ParsedClassTest test2 = new ParsedClassTest();
                test2.testAttributes();

                ParserTest test3 = new ParserTest();
                test3.testBasicParse();
                break;
            }
        }

        String path;
        // Initialisation du projet
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter relative path to the java file to scan");

        // Input du path cible
        path = scanner.nextLine();

        System.out.println("Path : " + path + "\n");

        try {
            // Creation des parsers
            FileWriter classCsvWriter = new FileWriter("classes.csv");
            FileWriter methodCsvWriter = new FileWriter("methodes.csv");

            // Titre des colonnes
            classCsvWriter.append("chemin,class,classe_LOC,classe_CLOC,classe_DC,WMC,classe_BC\n");
            methodCsvWriter.append("chemin,class,methode,methode_LOC,methode_CLOC,methode_DC,CC,methode_BC\n");

            // Premiere appel a l'algo recursif
            parse(classCsvWriter, methodCsvWriter, path);

            // Finalisation des fichier csv
            classCsvWriter.flush();
            classCsvWriter.close();
            methodCsvWriter.flush();
            methodCsvWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Algo recursif qui parse les fichiers .java et construit les csv de class et de methode pour les FileWriter en
     * entree.
     *
     * @param classCsvWriter Un file writer qui va contenir les informations des classes
     * @param methodCsvWriter Un file writer qui va contenir les informations des methodes de chaque classe
     * @param path Le chemin du fichier cible de l'algorithme
     */
    public static void parse(FileWriter classCsvWriter, FileWriter methodCsvWriter, String path) {
        try {
            File[] fileArray = new File(path).listFiles();
            if (fileArray != null) {
                for (File f : fileArray) {
                    if (f.getName().endsWith(".java")) { // to deal with the .txt files.
                        ParsedClass pc = Parser.parse(f); // to read the files

                        System.out.println("Parsing class : " + pc.getClassName() + " ...");

                        classCsvWriter.append(pc.getPath());
                        classCsvWriter.append(",");
                        classCsvWriter.append(pc.getClassName());
                        classCsvWriter.append(",");
                        classCsvWriter.append(Integer.toString(pc.getClassLoc()));
                        classCsvWriter.append(",");
                        classCsvWriter.append(Integer.toString(pc.getClassCloc()));
                        classCsvWriter.append(",");
                        classCsvWriter.append(Double.toString(pc.getClassDc()));
                        classCsvWriter.append(",");
                        classCsvWriter.append(Integer.toString(pc.getClassWmc()));
                        classCsvWriter.append(",");
                        classCsvWriter.append(Double.toString(pc.getClassBc()));
                        classCsvWriter.append("\n");

                        for (Method m : pc.getMethods()) {
                            System.out.println("Parsing method : " + m.getName() + " ...");
                            methodCsvWriter.append(m.getPath());
                            methodCsvWriter.append(",");
                            methodCsvWriter.append(pc.getClassName());
                            methodCsvWriter.append(",");
                            methodCsvWriter.append(m.getName());
                            if (m.getArguments().compareTo("") != 0) {
                                methodCsvWriter.append("_");
                                methodCsvWriter.append(m.getArguments());
                            }
                            methodCsvWriter.append(",");
                            methodCsvWriter.append(Integer.toString(m.getLoc()));
                            methodCsvWriter.append(",");
                            methodCsvWriter.append(Integer.toString(m.getCloc()));
                            methodCsvWriter.append(",");
                            methodCsvWriter.append(Double.toString(m.getDc()));
                            methodCsvWriter.append(",");
                            methodCsvWriter.append(Integer.toString(m.getCc()));
                            methodCsvWriter.append(",");
                            methodCsvWriter.append(Double.toString(m.getBc()));
                            methodCsvWriter.append("\n");
                        }
                    } else if (!f.getName().contains(".")) {
                        System.out.println("File found : " + f.getName() + " ...");
                        parse(classCsvWriter, methodCsvWriter, path + "/" + f.getName());
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
