import java.util.ArrayList;

/**
 * Monster class that represents every monsters in the game. Contains an item that is used when killed.
 */
public class Monster extends Active {
    public static boolean bonusAI = false;
    // True : if Zoe is on same tile or on 1 of 8 neighbouring tiles
    // False : if not
    private boolean zoeInRange;
    // Item carried by monster. Dropped at its death
    private Item item;

    // Constructor
    public Monster( int x, int y, String item, int currentLevel) {
        super("@", (int) Math.max(0.6 * currentLevel, 1), (int) Math.max(0.4 * currentLevel, 1), x, y);
        this.item = new Item(item);
        this.setName(generateName());
        int posXDiff = this.getPos().getX() - LegendOfZoe.getZoe().getPos().getX();
        int posYDiff = this.getPos().getY() - LegendOfZoe.getZoe().getPos().getY();
        this.checkZoeInRange();
    }

    /**
     * Function that assigns a name to each monster in the game.
     * @return String name
     */
    public String generateName() {
        String[] listNames = { "Renwil", "Guthcarne", "Isenachet", "Vell-bras", "Lafferdric", "Gar-nas",
                               "Eadbardven", "Thothormax", "Burbethgrimseph", "Ceomit", "Mawerd", "Isenachet",
                               "Vanack", "Blorgor", "Grigrel", "Rogim", "Morthos", "Brulurzgrim", "Gratlúrtz",
                               "Garselbug", "Umor", "Hargtul", "Irok", "Lug", "Grarok", "Gugash", "Tulgor", "Dra",
                               "Va-u", "Getarg", "Grimdrak", "Garneon", "Eadthor", "Crobar", "Torhubeorn" };
        return listNames[(int) Math.floor(Math.random() * listNames.length)];
    }

    // Getter
    public boolean getZoeInRange() {
        return zoeInRange;
    }
    // Setter
    public void setZoeInRange(boolean state) {
        this.zoeInRange = state;
    }

    // Methods
    /**
     * Function that checks if player Zoe is in the range of monster for an attack
     */
    public void checkZoeInRange() {
        Player zoe = LegendOfZoe.getZoe();
        int zoePosX = zoe.getPos().getX();
        int zoePosY = zoe.getPos().getY();
        int monsterPosX = this.getPos().getX();
        int monsterPosY = this.getPos().getY();

        // Calculates difference in positions between monster and the player
        int posXDiff = monsterPosX - zoePosX;
        int posYDiff = monsterPosY - zoePosY;

        // If she's around, set zoeInRange to true
        if ((posXDiff <= 1 && posXDiff >= -1) && (posYDiff <= 1 && posYDiff >= -1)) {
            this.setZoeInRange(true);
        } else { // Else, set it to false
            this.setZoeInRange(false);
        }
    }

    /**
     * Implements 'play' method in Active abstract class.
     * According to player's position, allows monster to attack it or move towards it.
     */
    public void play () {
        if (this.getAlive()) {
            Player zoe = LegendOfZoe.getZoe();
            int zoePosX = zoe.getPos().getX();
            int zoePosY = zoe.getPos().getY();
            int monsterPosX = this.getPos().getX();
            int monsterPosY = this.getPos().getY();

            // Distance between monster and the player
            int posXDiff = monsterPosX - zoePosX;
            int posYDiff = monsterPosY - zoePosY;

            // Check if player is in range
            this.checkZoeInRange();

            if (this.getZoeInRange()) { // If player is in range
                this.attack(zoe);
            } else if (bonusAI) {
                this.setPos(this.aStar(LegendOfZoe.getLevel().getBlocks()));
            } else {// Player not in range, so move towards player
                int dx = 0;
                int dy = 0;

                if (posXDiff < 0) { // Case player to right, move right
                    dx = 1;
                } else if (posXDiff > 0) { // Case player to left, move left
                    dx = -1;
                }

                if (posYDiff > 0) { // Case player up, move up
                    dy = 1;
                } else if (posYDiff < 0) { // Case player down, move down
                    dy = -1;
                }

                // Performs the move on monster
                this.move(dx, dy);
            }
            // Check if in range AFTER MOVING so we know which monsters the player can attack
            this.checkZoeInRange();
        }
    }

    /**
     * Implements 'die' method in Active abstract class.
     * When monster dies, set monster dead and player loots the item it carried.
     */
    @Override
    public void die() {
        this.setSprite("X");
        this.setAlive(false);
        this.zoeInRange = false;
        System.out.println(this.getName() + " meurt!");
        this.item.loot();
    }

    // ***************** Read BONUS.txt *****************
    public Position aStar(Block[][] map) {
        Player zoe = LegendOfZoe.getZoe();
        Node startNode = new Node(null, this.getPos());
        Node endNode = new Node(null, zoe.getPos());

        ArrayList<Node> closedNodes = new ArrayList<>(); // Already tested nodes
        ArrayList<Node> openNodes = new ArrayList<>(); // Potential path nodes

        openNodes.add(startNode);

        while(openNodes.size() > 0) {

            // Visual representation of pathfinding
            String[][] mapAStar = new String[LevelGenerator.HAUTEUR][LevelGenerator.LARGEUR];

            for (int y = 0; y < LevelGenerator.HAUTEUR; y++) {
                for (int x = 0; x < LevelGenerator.LARGEUR; x++) {
                    if (map[y][x] == null) {
                        mapAStar[y][x] = " ";
                    } else {
                        mapAStar[y][x] = map[y][x].getSprite();
                    }
                }
            }

            mapAStar[endNode.position.getY()][endNode.position.getX()] = "D";
            mapAStar[startNode.position.getY()][startNode.position.getX()] = "S";


            for (Node n : closedNodes) {
                mapAStar[n.position.getY()][n.position.getX()] = "c";
            }
            for (Node n : openNodes) {
                mapAStar[n.position.getY()][n.position.getX()] = "o";
            }

            // Uncomment to show algorithm progression
            /*
            for (String[] line : mapAStar) {
                for (String sprite : line) {
                    System.out.print(sprite);
                }
                System.out.println("");
            }
            System.out.println("------------------------------");
            */
            // End of visual

            // Find the lowest "f" value in openNodes
            int indexNode = 0;
            int minF = (int) Double.POSITIVE_INFINITY;
            for (int i = 0; i < openNodes.size(); i++) {
                if (openNodes.get(i).f < minF) {
                    minF = openNodes.get(i).f;
                    indexNode = i;
                }
            }

            // The lowest "f" node is our currentNode
            Node currentNode = openNodes.get(indexNode);
            openNodes.remove(indexNode); // We remove it from openNodes
            closedNodes.add(currentNode); // And add it to closedNodes

            // Check if the currentNode is at the end of the path
            if (currentNode.equalPos(endNode)) {
                currentNode = currentNode.parent;
                while(currentNode.parent.parent != null) {
                    // Uncomment to show Path (Part 1/2)
                    // mapAStar[currentNode.position.getY()][currentNode.position.getX()] = "P";
                    currentNode = currentNode.parent;
                }
                // Uncomment to show Path (Part 2/2)
                 /* for (String[] line : mapAStar) {
                    for (String sprite : line) {
                        System.out.print(sprite);
                    }
                    System.out.println("");
                } */
                return currentNode.position;
            }

            // We get all surrounding nodes to the current node
            int[][] surrPos = {{0,1},{1,1},{1,0},{1,-1},{0,-1},{-1,-1},{-1,0},{-1,1}};
            ArrayList<Node> children = new ArrayList<>();

            for (int[] pos : surrPos) {
                Position childrenPos = new Position(
                        currentNode.position.getX() + pos[0], currentNode.position.getY() + pos[1]
                );
                // If out of bounds
                boolean outOfBounds = false;
                if ((childrenPos.getX() < 0 || childrenPos.getX() >= LevelGenerator.LARGEUR) ||
                        (childrenPos.getY() < 0 || childrenPos.getY() >= LevelGenerator.HAUTEUR)) {
                    outOfBounds = true;
                }

                // If walkable space
                if (!outOfBounds){
                    if ((mapAStar[childrenPos.getY()][childrenPos.getX()].equals(" ")) ||
                        (mapAStar[childrenPos.getY()][childrenPos.getX()].equals("D")) ||
                        (mapAStar[childrenPos.getY()][childrenPos.getX()].equals("S"))) {
                            children.add(new Node(currentNode, childrenPos));
                    }
                }
            }

            // Generate Children nodes
            for (Node child : children) {
                boolean isInClose = false;
                boolean isInOpen = false;
                // Check if the current node has already been closed before
                for (Node closedNode : closedNodes) {
                    if (child.equalPos(closedNode)) {
                        isInClose = true;
                    }
                }

                // If it is closed, we can skip to the next child
                if (isInClose) {
                    continue;
                }

                // Initialise his g value
                child.g = currentNode.g + 1;

                // Check if the node has already been opened before
                for (Node openNode : openNodes) {
                    // If it is already in the open nodes and has a higher g value (Number of movement cost)
                    // We can still add it to the openNode list since it is more efficient
                    if (child.equalPos(openNode) && child.g > openNode.g) {
                        isInOpen = true;
                    }
                }

                if (isInOpen) {
                    continue;
                }

                int dx = child.position.getX() - endNode.position.getX();
                int dy = child.position.getY() - endNode.position.getY();

                child.h = dx * dx + dy * dy;
                child.f = child.g + child.h;

                openNodes.add(child);
            }
        }
        // If we don't find any path, we just return the monster's position
        return this.getPos();
    }
}
