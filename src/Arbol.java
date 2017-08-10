import java.util.ArrayList;
import java.util.Stack;

public class Arbol {
    private ArrayList<Rama> transicionesIdentificadas = new ArrayList<>();
    private ArrayList<Rama> ramasDelArbol = new ArrayList<>();

    private Stack<Rama> arboleda = new Stack<>();
    private NormalizadorDeRegex norma = new NormalizadorDeRegex();

    public Arbol(){

    }

    public void crearElArbol(String regex){
        String postfixeado = norma.PostFixYNormalizar(regex);

        int j = postfixeado.length();
        for (int i = 0; i < j; i++) {
            String x = postfixeado.substring(i, i + 1);

            if(x.equals(".") || x.equals("|")){
                Rama b = arboleda.pop();
                Rama a = arboleda.pop();
                Rama laRama = new Rama(x);

                laRama.setRightChild(b);
                laRama.setLeftChild(a);
                ramasDelArbol.add(laRama);
                arboleda.push(laRama);

            }
            else if(x.equals("+")||x.equals("*")) {
                Rama a = arboleda.pop();
                Rama laRama = new Rama(x);
                laRama.setLeftChild(a);
                laRama.setRightChild(null);

                ramasDelArbol.add(laRama);
                arboleda.push(laRama);

            }
            else{
                Rama a = new Rama(x);
                ramasDelArbol.add(a);
                transicionesIdentificadas.add(a);
                arboleda.push(a);

            }
        }

    }

}
