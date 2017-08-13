import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by Diego Castaneda on 12/08/2017.
 */
public class Particiones {
    private ArrayList<NodoAFD> grafo;
    private HashSet<Nodo> fachada = new HashSet<>();
    private ArrayList<String> alfabeto = new ArrayList<>();

    public Particiones(ArrayList<NodoAFD> grafo){
        this.grafo = grafo;
    }

    public ArrayList<NodoAFD> primeraParticion(){
        /*Obteniendo el alfabeto*/
        alfabeto.addAll(grafo.get(0).getTransiciones());

        NodoAFD uno = new NodoAFD(fachada);
        NodoAFD dos = new NodoAFD(fachada);
        for (NodoAFD nodos: grafo){
            if(nodos.isFinal()){
                uno.getConjuntoMinimo().add(nodos);
            }
            else{
                dos.getConjuntoMinimo().add(nodos);
            }
        }
        ArrayList<NodoAFD> pasado = new ArrayList<>();
        pasado.add(uno);
        pasado.add(dos);
        return pasado;
    }

    public ArrayList<NodoAFD> particion(){
        ArrayList<NodoAFD> pasado = primeraParticion();
        for(NodoAFD nodo: pasado){
            for(NodoAFD i: nodo.getConjuntoMinimo()){
                ArrayList<Integer> lasParticiones = new ArrayList<>();
                for(String s: alfabeto){
                    
                }
            }
        }
    }


}


