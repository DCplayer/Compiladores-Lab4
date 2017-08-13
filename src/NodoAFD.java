import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by Diego Castaneda on 05/08/2017.
 */
public class NodoAFD {
    private int id;
    private HashSet<Nodo> conjunto;
    private HashSet<NodoAFD> conjuntoMinimo;

    private ArrayList<String> transiciones = new ArrayList<>();
    private ArrayList<NodoAFD> arrivals = new ArrayList<>();

    /*Esta ultima lista servira para saber a que particion pertenece dependiendo de la cantidad de elementos en el
    * alfabeto y ademas a si se tiene que ese Nodo, con esa transicion llega o no a a un estado de Aceptacion
    *
    * Se queria hacer un ArrayList de booleanos pero es mejor de numero para identificar cada una de las particiones
    * que se esta haciendo :D .*/
    private ArrayList<Integer> particiones = new ArrayList<>();

    private boolean isInitial = false;
    private boolean isFinal = false;

    public NodoAFD(HashSet<Nodo> propiedad) {
        this.conjunto= propiedad;

    }

    public void add(String movimiento, NodoAFD llegada){
        transiciones.add(movimiento);
        arrivals.add(llegada);
    }

    public HashSet<Nodo> getConjunto() {
        return conjunto;
    }

    public ArrayList<String> getTransiciones() {
        return transiciones;
    }

    public ArrayList<NodoAFD> getArrivals() {
        return arrivals;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setInitial(boolean initial) {
        isInitial = initial;
    }

    public void setFinal(boolean aFinal) {
        isFinal = aFinal;
    }

    public boolean isInitial() {
        return isInitial;
    }

    public boolean isFinal() {
        return isFinal;
    }

    public HashSet<NodoAFD> getConjuntoMinimo() {
        return conjuntoMinimo;
    }

    public void setConjuntoMinimo(HashSet<NodoAFD> conjuntoMinimo) {
        this.conjuntoMinimo = conjuntoMinimo;
    }
}
