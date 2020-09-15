// Exit class represent the exit to the level.
public class Exit extends Block {
    // Constructor
    public Exit() {
        super("E", true);
    }

    /**
     * Function that allows the player to enter next level
     * (called only if the current level's Hexaforce has been collected).
     */
    public void exit() {
        LegendOfZoe.nextLevel();
    }
}
