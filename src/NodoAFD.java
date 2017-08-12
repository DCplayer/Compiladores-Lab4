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
}
