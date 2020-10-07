import java.util.ArrayList;

public class Method {
    private String path;
    private String className;
    private String name;
    private String arguments;
    private int loc;
    private int cloc;
    private int cc;
    private double dc;

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
