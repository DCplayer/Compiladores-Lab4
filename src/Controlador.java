import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Stack;
/**
 * Created by Diego Castañeda on 28/07/2017.
 */
public class Controlador {

    static int ContadorDeID = 0;

    /*Atributos para la funcion de Lector de Expresiones*/
    private NormalizadorDeRegex norm = new NormalizadorDeRegex();
    private String regex;
    private RelacionesDeAutomatas rel = new RelacionesDeAutomatas();
    private Stack<Automata> stack = new Stack<Automata>();

    /*Atributos para la funcion de AlgoritmoNumeraNodosyCreaTransiciones*/
    private boolean completo = true;
    private boolean paso = true;
    private int contador = 0;
    private ArrayList<Nodo> grafo = new ArrayList<Nodo>();
    private ArrayList<Nodo> grafo2 = new ArrayList<Nodo>();
    private ArrayList<Transicion> transiciones = new ArrayList<Transicion>();
    private ArrayList<Integer> ids = new ArrayList<Integer>();

    /*Atributos para la funcion de AlgoritmoImplantaSimbolos*/
    private boolean criterio = true;
    private ArrayList<String> simbolos = new ArrayList<String>();
    private int contable = 0;


    public Controlador(String regex) {
        this.regex = regex;
    }

    public Automata LectorDeExpresiones( ) {


        String RegexNormalizado = norm.PostFixYNormalizar(regex);
        int j = RegexNormalizado.length();
        for (int i = 0; i < j; i++) {
            String x = RegexNormalizado.substring(i, i + 1);

            if (x.equals(".")) {
                Automata b = stack.pop();
                Automata a = stack.pop();
                Automata concatencion = rel.and(a,b);

                /*Se toman todos los nodos del automata A menos el ultimo*/
                ArrayList<Nodo>  listazo = new ArrayList<Nodo>();
                int numeroDeElementos = a.getHistorial().size();
                for (int num = 0; num + 1<numeroDeElementos; num++){
                    listazo.add(a.getHistorial().get(num));
                }
                listazo.addAll(b.getHistorial());
                ArrayList<Nodo> nodazo = new ArrayList<Nodo>();
                for(Nodo q: listazo){
                    nodazo.add(q);
                }
                concatencion.setHistorial(listazo);

                stack.push(concatencion);

            } else if (x.equals("|")) {
                Automata b = stack.pop();
                Automata a = stack.pop();
                Automata or = rel.or(a,b);

                /*Agregando todos los nodos del nuevo aautomata a su lista de nodos
                * asi como agregando identificacores a los nodos inicial y final del
                * nuevo automata*/
                ArrayList<Nodo> listazo = new ArrayList<Nodo>();
                or.getNodoInicial().setId(ContadorDeID);
                ContadorDeID  = ContadorDeID + 1;
                or.getNodoFinal().setId(ContadorDeID);
                ContadorDeID  = ContadorDeID + 1;
                listazo.add(or.getNodoInicial());
                listazo.addAll(a.getHistorial());
                listazo.addAll(b.getHistorial());
                listazo.add(or.getNodoFinal());
                or.setHistorial(listazo);

                stack.push(or);

            } else if (x.equals("+")) {
                Automata a = stack.pop();
                Automata kleeneSuma = rel.sum(a);

                ArrayList<Nodo> listazo = new ArrayList<Nodo>();
                kleeneSuma.getNodoInicial().setId(ContadorDeID);
                ContadorDeID = ContadorDeID + 1;
                kleeneSuma.getNodoFinal().setId(ContadorDeID);
                ContadorDeID = ContadorDeID + 1;
                listazo.add(kleeneSuma.getNodoInicial());
                listazo.addAll(a.getHistorial());
                listazo.add(kleeneSuma.getNodoFinal());
                kleeneSuma.setHistorial(listazo);

                stack.push(kleeneSuma);

            } else if (x.equals("*")) {
                Automata a = stack.pop();
                Automata kleene = rel.kleene(a);

                /*Creando una lista de nodos para el Automata Kleene y
                * dando identificadores a los nodos inicial y final del automata*/
                ArrayList<Nodo> listazo = new ArrayList<Nodo>();
                kleene.getNodoInicial().setId(ContadorDeID);
                ContadorDeID  = ContadorDeID + 1;
                kleene.getNodoFinal().setId(ContadorDeID);
                ContadorDeID  = ContadorDeID + 1;
                listazo.add(kleene.getNodoInicial());
                listazo.addAll(a.getHistorial());
                listazo.add(kleene.getNodoFinal());
                kleene.setHistorial(listazo);

                stack.push(kleene);

            } else {
                Automata y = new Automata(x);

                /*Agregando los nodos a la lista de nodos del automata
                * y nombrandolos con identificadore :D */
                ArrayList<Nodo> lista = new ArrayList<Nodo>();
                y.getNodoInicial().setId(ContadorDeID);
                ContadorDeID  = ContadorDeID + 1;
                y.getNodoFinal().setId(ContadorDeID);
                ContadorDeID  = ContadorDeID + 1;
                lista.add(y.getNodoInicial());
                lista.add(y.getNodoFinal());
                y.setHistorial(lista);

                stack.push(y);
            }
        }
        return stack.pop();
    }

    public ArrayList<Transicion> AlgoritmoCreaTransiciones(ArrayList<Nodo> a){
        for(Nodo i: a){
            int index = 0;
            while (index < i.getNodos().size()){
                Transicion t = new Transicion(i.getId(), i.getTransiciones().get(index), i.getNodos().get(index).getId());
                transiciones.add(t);
                index = index + 1;
            }

        }
        return transiciones;
    }

     public ArrayList<String> AlgoritmoImplantaSimbolos(ArrayList<Nodo> a){
        for (Nodo i : a){
            for (String s: i.getTransiciones()){
                if (!simbolos.contains(s) && !s.equals("@")){
                    simbolos.add(s);
                }
            }
        }
        return simbolos;
    }


    public ArrayList<Nodo> getGrafo() {
        return grafo;
    }

    public ArrayList<Transicion> getTransiciones() {
        return transiciones;
    }

    public ArrayList<Integer> getIds() {
        return ids;
    }

    public ArrayList<String> getSimbolos() {
        return simbolos;
    }

    public int getContador(){
        return contador;
    }
}

