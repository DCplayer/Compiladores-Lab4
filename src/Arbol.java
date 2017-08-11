import java.util.ArrayList;
import java.util.Stack;

public class Arbol {
    private ArrayList<Rama> transicionesIdentificadas = new ArrayList<>();
    private ArrayList<Rama> ramasDelArbol = new ArrayList<>();

    private Stack<Rama> arboleda = new Stack<>();
    private NormalizadorDeRegex norma = new NormalizadorDeRegex();

    public Arbol(){

    }

    public void crearElArbol(String regex){
        String conNumeralAlfinal = "("+ regex +")#";
        String postfixeado = norma.PostFixYNormalizar(conNumeralAlfinal);


        int j = postfixeado.length();
        for (int i = 0; i < j; i++) {
            String x = postfixeado.substring(i, i + 1);

            if(x.equals(".") || x.equals("|")){
                Rama b = arboleda.pop();
                Rama a = arboleda.pop();
                Rama laRama = new Rama(x);

                laRama.setRightChild(b);
                laRama.setLeftChild(a);
                ramasDelArbol.add(laRama);
                arboleda.push(laRama);

            }
            else if(x.equals("+")||x.equals("*")) {
                Rama a = arboleda.pop();
                Rama laRama = new Rama(x);
                laRama.setLeftChild(a);
                laRama.setRightChild(null);

                ramasDelArbol.add(laRama);
                arboleda.push(laRama);

            }
            else{
                Rama a = new Rama(x);
                ramasDelArbol.add(a);
                transicionesIdentificadas.add(a);
                arboleda.push(a);

            }
        }

    }

    public void nombrarListas(){
        int contador = 1;
        for (Rama r: transicionesIdentificadas){
            r.setID(contador);
            contador = contador + 1;
        }
    }

    public void nullable() {
        for (Rama r : ramasDelArbol) {
            if(r.getContenido().equals("@")){
                r.setNullable(true);
            }
            else if(r.getContenido().equals("*")){
                r.setNullable(true);
            }
            else if(r.getContenido().equals("|")){
                if(r.getLeftChild().isNullable() || r.getRightChild().isNullable()){
                    r.setNullable(true);
                }
            }
            else if(r.getContenido().equals(".")){
                if(r.getLeftChild().isNullable() && r.getRightChild().isNullable()){
                    r.setNullable(true);
                }

            }
        }
    }

    public void firstPos(){
        for (Rama i: transicionesIdentificadas){
            if (!i.getContenido().equals("@")){
                i.getFirstPos().add(i);
            }
        }

        for (Rama r: ramasDelArbol){
            if(r.getContenido().equals("*")){
                r.getFirstPos().addAll(r.getLeftChild().getFirstPos());
            }
            else if(r.getContenido().equals("|")){
                r.getFirstPos().addAll(r.getLeftChild().getFirstPos());
                r.getFirstPos().addAll(r.getRightChild().getFirstPos());
            }
            else if(r.getContenido().equals(".")){
                if(r.getLeftChild().isNullable()){
                    r.getFirstPos().addAll(r.getLeftChild().getFirstPos());
                    r.getFirstPos().addAll(r.getRightChild().getFirstPos());
                }
                else{
                    r.getFirstPos().addAll(r.getLeftChild().getFirstPos());
                }
            }
        }
    }

    public void lastPos(){
        for (Rama i: transicionesIdentificadas){
            if(!i.getContenido().equals("@")){
                i.getLastPos().add(i);
            }
        }

        for (Rama r: ramasDelArbol){
            if(r.getContenido().equals("*")){
                r.getLastPos().addAll(r.getLeftChild().getLastPos());
            }
            else if(r.getContenido().equals("|")){
                r.getLastPos().addAll(r.getLeftChild().getLastPos());
                r.getLastPos().addAll(r.getRightChild().getLastPos());
            }
            else if(r.getContenido().equals(".")){
                if(r.getRightChild().isNullable()){
                    r.getLastPos().addAll(r.getLeftChild().getLastPos());
                    r.getLastPos().addAll(r.getRightChild().getLastPos());
                }
                else{
                    r.getLastPos().addAll(r.getRightChild().getLastPos());
                }

            }
        }
    }

    /*REVISAR: Si el arqueotipo de followPos de concatenacion funciona tambien para el Or*/
    public void followPos(){
        for (Rama r: ramasDelArbol){
            if(r.getContenido().equals(".") || r.getContenido().equals("|")){
                for(Rama x :r.getLeftChild().getLastPos()){
                    x.getFollowPos().addAll(r.getRightChild().getFirstPos());
                }

            }
            else if(r.getContenido().equals("*")){
                for (Rama y: r.getLastPos()){
                    y.getFollowPos().addAll(r.getFirstPos()); 
                }
            }
        }
    }



}
