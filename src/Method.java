import java.util.ArrayList;

/**
 *  Structure de donnees qui contient tous les informations perninantes aux methodes trouvee dans les fichiers java
 */
public class Method {
    private String path;
    private String className;
    private String name;
    private String arguments;
    private int loc;
    private int cloc;
    private int cc;
    private double dc;

    /**
     * Constructeur d'une nouvelle methode
     *
     * @param path Chemin vers le fichier contenant la methode
     * @param className Nom de la class auquelle la methode appartient
     * @param name Nom de la methode
     * @param arguments Le(s) type(s) de tous les arguments separes par des '_'
     * @param loc Le nombre de ligne dans la methode
     * @param cloc Le nombre de ligne de commentaire dans la methode
     * @param cc La valeur de la complexite cyclomatique
     */
    public Method(String path, String className, String name, String arguments, int loc, int cloc, int cc) {
        this.path = path;
        this.className = className;
        this.name = name;
        this.arguments = arguments;
        this.loc = loc;
        this.cloc = cloc;
        this.cc = cc;
        this.dc = ((double) this.cloc) / this.loc;
    }

    public String getPath() {
        return path;
    }

    public String getClassName() {
        return className;
    }

    public String getName() {
        return name;
    }

    public int getLoc() {
        return loc;
    }

    public int getCloc() {
        return cloc;
    }

    public double getDc() {
        return dc;
    }

    public String getArguments() {
        return arguments;
    }

    public int getCc() {
        return cc;
    }

    public double getBc() {
        return dc/cc;
    }
}
