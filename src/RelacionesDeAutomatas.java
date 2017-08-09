/**
 * Created by Diego Castañeda on 28/07/2017.
 */
public class RelacionesDeAutomatas {

    public RelacionesDeAutomatas(){}

    public Automata or(Automata a, Automata b){
        /*Se inicia el automata declarando dos nuevos nodos, inicial y final*/
        Nodo inicial = new Nodo();
        Nodo fin = new Nodo();

        /*El inicial se conecta via epsilon a los iniciales de los automatas dados*/
        inicial.add("@", a.getNodoInicial());
        inicial.add("@", b.getNodoInicial());

        /*El nodo final de cada automata se agrega al nodo final creado al principio*/
        a.AgregarAlFinal("@", fin);
        b.AgregarAlFinal("@", fin);

        Automata x = new Automata(inicial, fin);

        return x;
    }

    public Automata and(Automata a, Automata b){
        Nodo inicial = a.getNodoInicial();
        Nodo fin = b.getNodoFinal();

        for (Nodo i : a.getHistorial()){
            for(Nodo x : i.getNodos()){
                if (x.equals(a.getNodoFinal())){
                    int elIndex = i.getNodos().indexOf(x);
                    i.getNodos().set(elIndex, b.getNodoInicial());
                }
            }
        }
        a.setNodoFinal(b.getNodoInicial());

        Automata x = new Automata(inicial, fin);
        return x;
    }

    /*PROBLEMAS AQUI, SE REPITEN LAS TRANSICIONESw*/
    public Automata kleene(Automata a){
        Nodo inicial = new Nodo();
        Nodo fin =  new Nodo();
        inicial.add("@", a.getNodoInicial());
        inicial.add("@", fin);
        a.getNodoFinal().add("@", a.getNodoInicial());
        a.getNodoFinal().add("@", fin);

        Automata x = new Automata(inicial, fin);
        return x;
    }

    public Automata sum(Automata a){
        Nodo inicial = new Nodo();
        Nodo fin =  new Nodo();
        inicial.add("@", a.getNodoInicial());
        a.getNodoFinal().add("@", a.getNodoInicial());
        a.getNodoFinal().add("@", fin);

        Automata x = new Automata(inicial, fin);
        return x;
    }
}
