/**
 * Chest class represent the chest blocks in a level. Contains an item that is used when opened.
 */
public class Chest extends Block {
    private boolean looted = false;
    private Item item;

    // Constructor
    public Chest(String contents) {
        super("$", true);
        this.item = new Item(contents);
    }

    // Getter
    public boolean getLooted() {
        return this.looted;
    }

    /**
     * Function called when the player opens the chest.
     */
    public void loot() {
        this.setSprite("_");
        this.item.loot();
        this.looted = true;
    }
}
