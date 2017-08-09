/**
 * Created by Diego Castañeda on 28/07/2017.
 */
public class Transicion {
    private int nodoInicial;
    private int nodoFinal;
    private String transicion;

    public Transicion(int nodoInicial,  String transicion, int nodoFinal){
        this.nodoFinal = nodoFinal;
        this.nodoInicial = nodoInicial;
        this.transicion = transicion;

    }

    public int getNodoInicial() {
        return nodoInicial;
    }

    public int getNodoFinal() {
        return nodoFinal;
    }

    public String getTransicion() {
        return transicion;
    }

    public void setNodoInicial(int nodoInicial) {
        this.nodoInicial = nodoInicial;
    }

    public void setNodoFinal(int nodoFinal) {
        this.nodoFinal = nodoFinal;
    }

    public void setTransicion(String transicion) {
        this.transicion = transicion;
    }

    public String toString(){
        return "{" + nodoInicial + ", " + transicion + ", " + nodoFinal + "}";
    }
}
