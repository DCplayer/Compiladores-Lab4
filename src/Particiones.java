import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader;

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

    public ArrayList<NodoAFD> particion(ArrayList<NodoAFD> elPasado){
        HashSet<ArrayList<Integer>> listadoDeParticiones = new HashSet<>();
        for(NodoAFD nodo: elPasado){
            for(NodoAFD i: nodo.getConjuntoMinimo()){
                for(String s: alfabeto){
                    int indexParticion = 0;
                    NodoAFD NodoDondeLLega = i.getArrivals().get(i.getTransiciones().indexOf(s));
                    while(indexParticion < elPasado.size()){
                        if(elPasado.get(indexParticion).getConjuntoMinimo().contains(NodoDondeLLega)){
                            i.getParticiones().add(indexParticion);
                        }
                        indexParticion = indexParticion + 1;
                    }
                }
                listadoDeParticiones.add(i.getParticiones());
            }
        }
        ArrayList<NodoAFD>  presente = new ArrayList<>();
        for(ArrayList<Integer> particion: listadoDeParticiones){
            NodoAFD nodo = new NodoAFD(fachada);
            for (NodoAFD x: grafo){
                if(particion.equals(x.getParticiones())){
                    nodo.getConjuntoMinimo().add(x);
                }
            }
            presente.add(nodo);
        }

        for(NodoAFD g: grafo){
            g.getParticiones().clear();
        }
        return presente;
    }

    public ArrayList<NodoAFD> realizarLasParticiones(){
        ArrayList<NodoAFD> pasado = primeraParticion();
        ArrayList<NodoAFD> presente = particion(pasado);

        while (!pasado.equals(presente)){
            pasado.clear();
            pasado.addAll(presente);
            presente.clear();
            presente = particion(pasado);
        }

        return presente;
    }


}


