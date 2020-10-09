import java.util.ArrayList;

/**
 *  Structure de donnees qui contient tous les informations perninantes aux classes trouvee dans les fichiers java
 */
public class ParsedClass {
    private String path;
    private ArrayList<Method> methods;
    private String className;
    private int classLoc;
    private int classCloc;
    private double classDc;
    private int classWmc;

    /**
     * Constructeur d'une class parsee
     *
     * @param path Chemin vers le fichier contenant la classe
     * @param methods Un liste de tous les methodes contenu dans la classe
     * @param className Le nom de la classe
     * @param classLoc Le nombre de ligne dans la classe
     * @param classCloc Le nombre de ligne de commentaire dans la classe
     */
    public ParsedClass(String path, ArrayList<Method> methods, String className, int classLoc, int classCloc) {
        this.path = path;
        this.methods = methods;
        this.className = className;
        this.classLoc = classLoc;
        this.classCloc = classCloc;
        this.classDc = ((double) this.classCloc) / this.classLoc;
        this.classWmc = 0;

        for (Method m : this.methods){
            this.classWmc += m.getCc();
        }
    }

    public String getPath() {
        return path;
    }

    public ArrayList<Method> getMethods() {
        return methods;
    }

    public String getClassName() {
        return className;
    }

    public int getClassLoc() {
        return classLoc;
    }

    public int getClassCloc() {
        return classCloc;
    }

    public double getClassDc() {
        return classDc;
    }

    public int getClassWmc() {
        return classWmc;
    }

    public double getClassBc() {
        return classDc/classWmc;
    }
}
