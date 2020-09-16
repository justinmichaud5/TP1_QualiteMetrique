public class Method {
    private String name;
    private int loc;
    private int cloc;

    public Method(String name, int loc, int cloc) {
        this.name = name;
        this.loc = loc;
        this.cloc = cloc;
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
