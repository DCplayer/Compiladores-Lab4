import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by Diego Castaneda on 05/08/2017.
 */
public class AutomataAFD {
    private ArrayList<HashSet<Nodo>> nodosInicialesViejos  = new ArrayList<>();
    private ArrayList<String> transiciones = new ArrayList<>();
    private ArrayList<HashSet<Nodo>> nodosFinalesViejos = new ArrayList<>();

    private ArrayList<NodoAFD> nodosInicialesAFD = new ArrayList<>();
    private ArrayList<NodoAFD> nodosFinalesAFD = new ArrayList<>();

    public AutomataAFD(){}


    public void add(HashSet<Nodo> inicial, String s, HashSet<Nodo> finiti){
        nodosInicialesViejos.add(inicial);
        nodosFinalesViejos.add(finiti);
        transiciones.add(s);

    }

    public void add(NodoAFD inicial, String transicion, HashSet<Nodo> finall){
        nodosInicialesAFD.add(inicial);
        transiciones.add(transicion);
        nodosFinalesViejos.add(finall);
    }

    public ArrayList<String> getTransiciones() {
        return transiciones;
    }

    public ArrayList<NodoAFD> getNodosInicialesAFD() {
        return nodosInicialesAFD;
    }

    public ArrayList<NodoAFD> getNodosFinalesAFD() {
        return nodosFinalesAFD;
    }
}
