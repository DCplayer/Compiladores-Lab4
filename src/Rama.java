import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader;

import java.util.ArrayList;
import java.util.HashSet;

public class Rama {
    private String contenido;
    private int ID;

    private boolean nullable = false;
    private HashSet<Rama> firstPos = new HashSet<>();
    private HashSet<Rama> lastPos = new HashSet<>();
    private HashSet<Rama> followPos = new HashSet<>();

    private Rama leftChild;
    private Rama rightChild;

    /*Tomaremos firstPos como el identificador de las ramas que tiene cada RAMA*/
    private ArrayList<String> transiciones = new ArrayList<>();
    private ArrayList<Rama> arrivals = new ArrayList<>();


    public Rama(String s){
        this.contenido = s;

    }

    public void setLeftChild(Rama leftChild) {
        this.leftChild = leftChild;
    }

    public void setRightChild(Rama rightChild) {
        this.rightChild = rightChild;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setNullable(boolean nullable) {
        this.nullable = nullable;
    }

    public boolean isNullable() {
        return nullable;
    }

    public String getContenido() {
        return contenido;
    }

    public Rama getLeftChild() {
        return leftChild;
    }

    public Rama getRightChild() {
        return rightChild;
    }

    public HashSet<Rama> getFirstPos() {
        return firstPos;
    }

    public HashSet<Rama> getLastPos() {
        return lastPos;
    }

    public HashSet<Rama> getFollowPos() {
        return followPos;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}

