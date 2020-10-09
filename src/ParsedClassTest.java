import java.util.ArrayList;

/**
 * Test la creation de classes et le calcul des evaluations plus avancees
 */
public class ParsedClassTest {

    /**
     * Execute le test de la classe ParsedClass
     */
    public void testAttributes(){
        Method method_test = new Method("/", "class", "method", "int", 10, 5, 1 );
        ArrayList<Method> method_array = new ArrayList<>();
        method_array.add(method_test);
        ParsedClass test = new ParsedClass("/", method_array, "class", 20, 10);

        assert test.getPath().compareTo("/") == 0;
        assert test.getMethods().get(0).getName().compareTo("method") == 0;
        assert test.getClassName().compareTo("class") == 0;
        assert test.getClassLoc() == 20;
        assert test.getClassCloc() == 10;
        assert test.getClassWmc() == 1;
        assert test.getClassDc() == 0.5;
        assert test.getClassBc() == 0.5;

        System.out.println("ParsedClassTest passed");
    }
}
