import java.util.ArrayList;

public class Rama {
    private String contenido;
    private int ID;

    private boolean nullable = false;
    private ArrayList<Rama> firstPos = new ArrayList<>();
    private ArrayList<Rama> lastPos = new ArrayList<>();
    private ArrayList<Rama> followPos = new ArrayList<>();

    private Rama leftChild;
    private Rama rightChild;


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

    public ArrayList<Rama> getFirstPos() {
        return firstPos;
    }

    public ArrayList<Rama> getLastPos() {
        return lastPos;
    }

    public ArrayList<Rama> getFollowPos() {
        return followPos;
    }
}

