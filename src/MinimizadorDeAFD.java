import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Stack;

/**
 * Created by DiegoCastaneda on 16/08/2017.
 */
public class MinimizadorDeAFD {
    private HashSet<HashSet<NodoAFD>> noMarcados = new HashSet<>();
    private HashSet<HashSet<NodoAFD>> Marcados = new HashSet<>();
    private ArrayList<String> alfabeto = new ArrayList<>();
    private HashSet<Dstate> noMarcadosDstate = new HashSet<>();
    private HashSet<Dstate> marcadosDstate = new HashSet<>();
    private HashSet<NodoAFD> automaton = new HashSet<>();
    private Stack<String> invertido = new Stack<>();


    public MinimizadorDeAFD(ArrayList<String> alfa){
        this.alfabeto = alfa;
    }

    public ArrayList<NodoAFD> ordenar(ArrayList<NodoAFD> automata){
        this.automaton.addAll(automata);
        ArrayList<NodoAFD> ordenado = new ArrayList<>();
        for(NodoAFD n: automata){
            if(n.isInitial()){
                ordenado.add(n);
            }
        }
        for(NodoAFD nn : automata){
            if(!nn.isInitial() && !nn.isFinal()){
                ordenado.add(nn);
            }
        }
        for(NodoAFD nnn: automata){
            if(nnn.isFinal()){
                ordenado.add(nnn);
            }
        }
        return ordenado;
    }

    public void crearLasParejasEIdentificarlas(ArrayList<NodoAFD> automataAFD){
        ArrayList<NodoAFD> ordenado = ordenar(automataAFD);



        for(NodoAFD nodo: ordenado){
            for(NodoAFD secundario: ordenado){
                if((ordenado.indexOf(nodo) < ordenado.indexOf(secundario)) && !nodo.isFinal()){
                    HashSet<NodoAFD> pair = new HashSet<>();
                    pair.add(nodo);
                    pair.add(secundario);
                    if(secundario.isFinal()){
                        Marcados.add(pair);
                    }
                    else{
                        noMarcados.add(pair);
                    }
                }


            }
        }

        for(HashSet<NodoAFD> n: Marcados){
            Dstate nodo = new Dstate(n);
            marcadosDstate.add(nodo);
        }
        for(HashSet<NodoAFD> m: noMarcados){
            Dstate mm = new Dstate(m);
            noMarcadosDstate.add(mm);
        }




    }

    public Dstate moveMore(Dstate state, String s){
        HashSet<NodoAFD> pareja = state.getContenido();
        HashSet<NodoAFD> resul = new HashSet<>();
        for(NodoAFD n: pareja){
            int index = 0;
            while(index < n.getTransiciones().size()){
                if(n.getTransiciones().get(index).equals(s)){
                    resul.add(n.getArrivals().get(index));
                }
                index = index + 1;
            }
        }
        Dstate resultado = new Dstate(resul);
        return resultado;
    }

    public boolean distinguible(HashSet<Dstate> movimiento){


        for(Dstate pareja: movimiento){
            ArrayList<NodoAFD> comprobar = new ArrayList<>();
            for(NodoAFD n: pareja.getContenido()){
                comprobar.add(n);
            }
            if(comprobar.size() > 1){
                if(comprobar.get(0).isFinal() || comprobar.get(1).isFinal()){
                    return true;
            }

            }
        }
        return false;

    }

    public void pendientes(){
        boolean continuar = true;
        HashSet<Dstate> nM = new HashSet<>();
        nM.addAll(noMarcadosDstate);
        HashSet<Dstate> M = new HashSet<>();
        M.addAll(marcadosDstate);



        while(continuar){
            ArrayList<Dstate> MeterMasTarde = new ArrayList<>();
            for(Dstate n: noMarcadosDstate){
                for(Dstate x: n.getParejasDependientes()){
                    for(Dstate y: marcadosDstate){
                        if(x.getContenido().equals(y.getContenido())){
                            MeterMasTarde.add(n);
                        }
                    }
                }
            }
            marcadosDstate.addAll(MeterMasTarde);
            for(Dstate nnn: marcadosDstate){
                if(noMarcadosDstate.contains(nnn)){
                    noMarcadosDstate.remove(nnn);
                }
            }

            if(!M.equals(marcadosDstate)){
                M.clear();
                M.addAll(marcadosDstate);
            }
            else if(M.equals(marcadosDstate)){
                continuar = false;
            }
        }

    }

    public void movimientoDeParejas(){
        for(Dstate pareja: noMarcadosDstate){
            HashSet<Dstate> resul = new HashSet<>();
            for(String s: alfabeto){
                Dstate x = moveMore(pareja, s);
                resul.add(x);
            }
            if(distinguible(resul)){
                /*Dejar para despues la eliminacion de cads nodo que este en marcado en No marcado */
                /*noMarcados.remove(pareja);*/
                marcadosDstate.add(pareja);
            }
            else{
                pareja.setParejasDependientes(resul);
                            }
        }
        for(Dstate listo: marcadosDstate){
            if(noMarcadosDstate.contains(listo)){
                noMarcadosDstate.remove(listo);
            }
        }
        pendientes();
    }

    public boolean equivalentes(Dstate pareja1, Dstate pareja2){
        for(NodoAFD x: pareja1.getContenido()){
            if(pareja2.getContenido().contains(x)){
                return true;
            }
        }
        return false;
    }

    public ArrayList<Dstate> CreandoLosNodosMinimizados(){
        ArrayList<Dstate> resultante = new ArrayList<>();
        HashSet<Dstate> listaParaBorrarDeNoMarcados = new HashSet<>();

        boolean cambios = true;
        while(cambios){
            if(noMarcadosDstate.size() == 0){
                cambios = false;
                break;
            }
            for(Dstate x: noMarcadosDstate){

                Dstate D = new Dstate(x.getContenido());


                for(Dstate s: noMarcadosDstate){
                    boolean booleano = equivalentes(x, s);
                    if(booleano){
                        D.getContenido().addAll(s.getContenido());
                        listaParaBorrarDeNoMarcados.add(s);
                    }

                }
                resultante.add(D);
                if(!listaParaBorrarDeNoMarcados.isEmpty()){
                    listaParaBorrarDeNoMarcados.add(x);
                    for(Dstate t: listaParaBorrarDeNoMarcados){
                        if(noMarcadosDstate.contains(t)){
                            noMarcadosDstate.remove(t);
                        }
                    }
                    break;

                }
                else{
                    cambios = false;
                    break;
                }


            }

        }
        for(Dstate nodo: resultante){
            for(NodoAFD NNN: nodo.getContenido()){
                if(automaton.contains(NNN)){
                    automaton.remove(NNN);
                }
            }
        }
        return resultante;
    }

    public ArrayList<Dstate> nodosDelAFD(){
        ArrayList<Dstate> parcialito =  CreandoLosNodosMinimizados();
        ArrayList<Dstate> finalizado = new ArrayList<>();
        for(NodoAFD n: automaton){
            HashSet<NodoAFD> inicio = new HashSet<>();
            inicio.add(n);
            Dstate incoming = new Dstate(inicio);
            finalizado.add(incoming);

        }
        for(Dstate i : parcialito){
            finalizado.add(i);
        }

        for(Dstate D: finalizado){
            for(NodoAFD n: D.getContenido()){
                int index = 0;
                System.out.println(alfabeto);
                while(index < alfabeto.size()){
                    int indexirino = n.getTransiciones().indexOf(alfabeto.get(index));
                    System.out.println(n.getTransiciones());
                    D.getTransiciones().add(n.getTransiciones().get(indexirino));
                    for(Dstate DD: finalizado){
                        if(DD.getContenido().contains(n.getArrivals().get(index))){
                            D.getArrivals().add(DD);
                            break;
                        }
                    }
                    index = index + 1;

                }
            }

        }


        return finalizado;
    }



}
