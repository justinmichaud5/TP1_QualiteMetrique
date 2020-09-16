public class Method {
    private String path;
    private String className;
    private String name;
    private int loc;
    private int cloc;

    public Method(String path, String className, String name, int loc, int cloc) {
        this.path = path;
        this.className = className;
        this.name = name;
        this.loc = loc;
        this.cloc = cloc;
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
        return ((double) this.cloc) / this.loc;
    }
}
