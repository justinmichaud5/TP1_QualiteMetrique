/**
 * Item class represents items contained in monsters and chests.
 * When looted, makes sure the proper effect of the content is used.
 */
public class Item {
    private String content;

    /**
     * Item Constructor.
     *
     * @param content The content that the item is, changes it effect when looted.
     */
    public Item(String content) {
        this.content = content;
    }

    // Getter
    public String getContents() {
        return content;
    }

    /**
     * Function that uses the item on the player depending on the item's content.
     */
    public void loot() {
        switch(this.getContents()) {
            case "coeur":
                LegendOfZoe.getZoe().setRelativeHp(1);
                System.out.println("Vous trouvez un coeur!");
                break;
            case "potionvie":
                LegendOfZoe.getZoe().setHp(LegendOfZoe.ZOE_MAX_HP);
                System.out.println("Vous trouvez une potion de vie!");
                break;
            case "hexaforce":
                LegendOfZoe.setNbHexaforce(LegendOfZoe.getNbHexaforce() + 1);
                System.out.println("Vous trouvez un morceau d'Hexaforce!");
                break;
        }
    }
}
