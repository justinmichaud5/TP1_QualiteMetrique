import java.util.ArrayList;

public class ParsedClass {
    private String path;
    private ArrayList<Method> methods;
    private String className;
    private int classLoc = 0;
    private int classCloc = 0;

    public ParsedClass(String path, ArrayList<Method> methods, String className, int classLoc, int classCloc) {
        this.path = path;
        this.methods = methods;
        this.className = className;
        this.classLoc = classLoc;
        this.classCloc = classCloc;
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

    public double getDc() {
        return ((double) this.classCloc) / this.classLoc;
    }
}
