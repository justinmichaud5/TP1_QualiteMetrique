import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ParserTest {
    public void testBasicParse(){
        String javaClass = "/**\n" +
                " * Chest class represent the chest blocks in a level. Contains an item that is used when opened.\n" +
                " */\n" +
                "public class Chest extends Block {\n" +
                "    private boolean looted = false;\n" +
                "    private Item item;\n" +
                "\n" +
                "    // Constructor\n" +
                "    public Chest(String contents) {\n" +
                "        super(\"$\", true);\n" +
                "        this.item = new Item(contents);\n" +
                "    }\n" +
                "\n" +
                "    // Getter\n" +
                "    public boolean getLooted() {\n" +
                "        return this.looted;\n" +
                "    }\n" +
                "\n" +
                "    /**\n" +
                "     * Function called when the player opens the chest.\n" +
                "     */\n" +
                "    public void loot() {\n" +
                "        this.setSprite(\"_\");\n" +
                "        this.item.loot();\n" +
                "        this.looted = true;\n" +
                "    }\n" +
                "}";

        try{
            FileWriter myWriter = new FileWriter("Chest.java");
            myWriter.write(javaClass);
            myWriter.close();
        } catch (IOException e){
            e.printStackTrace();
        }

        Parser test = new Parser();
        File file = new File("Chest.java");
        ParsedClass class1 = test.parse(file);

        assert class1.getClassName().compareTo("Chest") == 0;
        assert class1.getClassLoc() == 24;
        assert class1.getClassCloc() == 8;
        assert class1.getClassWmc() == 3;
        assert class1.getMethods().size() == 3;

        Method loot = class1.getMethods().get(2);
        assert loot.getName().compareTo("loot") == 0;
        assert loot.getLoc() == 8;
        assert loot.getCloc() == 3;
        assert loot.getCc() == 1;

        System.out.println("ParserTest passed");
        System.gc();
        file.delete();
    }
}
