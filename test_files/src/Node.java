public class Node {
    public Node parent;
    public Position position;
    public int g;
    public int f;
    public int h;

    public Node (Node parent, Position position) {
        this.parent = parent;
        this.position = position;

        this.g = 0;
        this.f = 0;
        this.h = 0;
    }

    public boolean equalPos(Node targetPos) {
        if (this.position.getX() == targetPos.position.getX() &&
            this.position.getY() == targetPos.position.getY()) {
            return true;
        }
        return false;
    }
}
