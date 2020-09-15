/**
 * Block class that represents every blocks in a level's map
 * (a null in a block list represents a empty space).
 */
public abstract class Block {
    private String sprite; // representation of the block on the map

    // Constructor
    public Block(String sprite, boolean solid) {
        this.sprite = sprite;
    }

    // Getter
    public String getSprite() {
        return this.sprite;
    }

    // Setter
    public void setSprite(String sprite) {
        this.sprite = sprite;
    }
}
