import java.util.ArrayList;

/**
 * Created by astable on 3/2/16.
 */
public class Prueba {

    public static void main(String args[]) {

        ArrayList<String> lista = new ArrayList<String>();
        String cadena = "hola";

        lista.add(cadena);

        String otraCadena = lista.get(0);
        otraCadena = "nueva";

        System.out.println(cadena);
    }
}
