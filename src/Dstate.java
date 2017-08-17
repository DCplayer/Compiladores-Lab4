import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by Diego Castaneda on 16/08/2017.
 */
public class Dstate {
    private int ID = 0;

    private HashSet<NodoAFD> contenido = new HashSet<>();
    private HashSet<Dstate> parejasDependientes = new HashSet<>();

    private ArrayList<String> transiciones = new ArrayList<>();
    private ArrayList<Dstate> arrivals = new ArrayList<>();

    public Dstate(HashSet<NodoAFD> parejaInicial){
        this.contenido = parejaInicial;

    }

    public HashSet<NodoAFD> getContenido() {
        return contenido;
    }

    public void setContenido(HashSet<NodoAFD> contenido) {
        this.contenido = contenido;
    }

    public HashSet<Dstate> getParejasDependientes() {
        return parejasDependientes;
    }

    public void setParejasDependientes(HashSet<Dstate> parejasDependientes) {
        this.parejasDependientes = parejasDependientes;
    }

    public ArrayList<String> getTransiciones() {
        return transiciones;
    }

    public ArrayList<Dstate> getArrivals() {
        return arrivals;
    }

    public void setTransiciones(ArrayList<String> transiciones) {
        this.transiciones = transiciones;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getID() {
        return ID;
    }
}
