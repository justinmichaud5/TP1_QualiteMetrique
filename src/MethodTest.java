/**
 * Test la creation de methodes et le calcul des evaluations plus avancees
 */
public class MethodTest {

    /**
     * Execute le test de la classe Method
     */
    public void testAttributes(){
        Method test = new Method("/", "class", "method", "int", 10, 5, 1 );

        assert test.getPath().compareTo("/") == 0;
        assert test.getClassName().compareTo("class") == 0;
        assert test.getName().compareTo("method") == 0;
        assert test.getArguments().compareTo("int") == 0;
        assert test.getLoc() == 10;
        assert test.getCloc() == 5;
        assert test.getCc() == 1;

        assert test.getDc() == 0.5;
        assert test.getBc() == 0.5;

        System.out.println("MethodTest passed");
    }
}
