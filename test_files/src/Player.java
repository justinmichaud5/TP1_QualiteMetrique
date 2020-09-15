/**
 * Player class that represents the player's character in the game.
 */
public class Player extends Active {

    // Constructor
    public Player () {
        super("&", LegendOfZoe.ZOE_MAX_HP, LegendOfZoe.ZOE_DAMAGE);
        this.setName("Zoe");
    }

    // Methods
    /**
     * Implements 'play' method in Active abstract class.
     * Function that allows the player to play (i.e. perform the desired action).
     */
    @Override
    public void play() {
        // Reads command entered by user (1 by 1)
        String action = LegendOfZoe.getInputs().substring(0, 1);
        // According to command entered, perform certain action on player
        switch (action) {
            case "w": // Move up
                this.move(0, 1);
                break;
            case "s": // Move down
                this.move(0, -1);
                break;
            case "a": // Move left
                this.move(-1, 0);
                break;
            case "d": // Move right
                this.move(1, 0);
                break;
            case "o": // Open chest
                this.open();
                break;
            case "c": // Break wall
                this.dig();
                break;
            case "x": // Attack monster
                for (Monster m : LegendOfZoe.getLevel().getMonsters()) {
                    // Attack every monster in range
                    if (m.getZoeInRange()) {
                        this.attack(m);
                    }
                }
                break;
            case "q": // Exit game
                System.exit(0);
                break;
        }
        // Go to next input
        LegendOfZoe.setInputs(LegendOfZoe.getInputs().substring(1));
    }
    /**
     * Implements 'die' method in Active abstract class
     */
    @Override
    public void die() {
        this.setAlive(false);
    }

    /**
     * Function that allows the player to dig.
     * Retrieves all walls in the player's surroundings and removes wall where dig action is performed.
     */
    public void dig () {
        // Position of player
        int posX = this.getPos().getX();
        int posY = this.getPos().getY();

        int minY = Math.max(0, posY - 1);
        int maxY = Math.min(posY + 1, LevelGenerator.HAUTEUR - 1);
        int minX = Math.max(0, posX - 1);
        int maxX = Math.min(posX + 1, LevelGenerator.LARGEUR - 1);

        Block[][] blocks = LegendOfZoe.getLevel().getBlocks();

        for (int dy = minY; dy <= maxY; dy++) {
            for (int dx = minX; dx <= maxX; dx++) {
                if (blocks[dy][dx] instanceof Wall) { blocks[dy][dx] = null; }
            }
        }
    }

    /**
     * Function that allows the player to open chests.
     * Retrieves all chests in the player's surroundings and loot their contents.
     */
    public void open () {
        int posX = this.getPos().getX();
        int posY = this.getPos().getY();

        int minY = Math.max(0, posY - 1);
        int maxY = Math.min(posY + 1, LevelGenerator.HAUTEUR - 1);
        int minX = Math.max(0, posX - 1);
        int maxX = Math.min(posX + 1, LevelGenerator.LARGEUR - 1);

        Block[][] blocks = LegendOfZoe.getLevel().getBlocks();

        for (int dy = minY; dy <= maxY; dy++) {
            for (int dx = minX; dx <= maxX; dx++) {
                if (blocks[dy][dx] instanceof Chest && !((Chest)blocks[dy][dx]).getLooted()) {
                    ((Chest) blocks[dy][dx]).loot();
                }
            }
        }
    }

    /**
     * Function that allows the player to exit a level.
     */
    public void exit () {
        int posX = this.getPos().getX();
        int posY = this.getPos().getY();

        int minY = Math.max(0, posY - 1);
        int maxY = Math.min(posY + 1, LevelGenerator.HAUTEUR - 1);
        int minX = Math.max(0, posX - 1);
        int maxX = Math.min(posX + 1, LevelGenerator.LARGEUR - 1);

        Block[][] blocks = LegendOfZoe.getLevel().getBlocks();

        for (int dy = minY; dy <= maxY; dy++) {
            for (int dx = minX; dx <= maxX; dx++) {
                if (blocks[dy][dx] instanceof Exit) {
                    ((Exit) blocks[dy][dx]).exit();
                }
            }
        }
    }
}
