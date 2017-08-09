import java.util.ArrayList;

public class Automata {
    private Nodo nodoInicial, nodoFinal;
    private ArrayList<Nodo> historial = new ArrayList<Nodo>();

    public Automata(String regex){
        nodoInicial = new Nodo();
        nodoFinal = new Nodo();
        nodoInicial.add(regex, nodoFinal);

    }

    public Automata(Nodo inicial, Nodo nfinal){
        this.nodoInicial = inicial;
        this.nodoFinal = nfinal;
    }

    public void AddTransAlFinal(String transition){
        this.getNodoFinal().addTrans(transition);
    }

    public void AddNodeAlFinal(Nodo nodo){
        this.getNodoFinal().addNode(nodo);
    }

    public void AgregarAlFinal(String t, Nodo n){
        AddTransAlFinal(t);
        AddNodeAlFinal(n);
    }

    public Nodo getNodoInicial() {
        return nodoInicial;
    }

    public Nodo getNodoFinal() {
        return nodoFinal;
    }

    public void setNodoInicial(Nodo nodoInicial) {
        this.nodoInicial = nodoInicial;
    }

    public void setNodoFinal(Nodo nodoFinal) {
        this.nodoFinal = nodoFinal;
    }

    public ArrayList<Nodo> getHistorial() {
        return historial;
    }

    public void setHistorial(ArrayList<Nodo> historial) {
        this.historial = historial;
    }

    public String toString(){
        return "Nodo Inicial: " + nodoInicial + "\nNodo Final : " + nodoFinal +"";
    }
}
