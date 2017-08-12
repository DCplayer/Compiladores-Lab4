import sun.plugin.javascript.navig.AnchorArray;
import sun.plugin.javascript.navig.Array;

import javax.net.ssl.SSLEngineResult;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public class Main {
    public static void main(String args[]){
        NormalizadorDeRegex norm = new NormalizadorDeRegex();
        Scanner sc = new Scanner(System.in);

        System.out.println("Ingrese la expresion regular de su automata: ");
        String regex = sc.nextLine();
        Controlador control = new Controlador(regex);
        ConvertidorAfnAfd convert = new ConvertidorAfnAfd();

        long  tiempoInicialAFD = System.nanoTime();
        Automata AutomataFinal = control.LectorDeExpresiones();
        long tiempoFinalAFD = System.nanoTime();
        double  tiempoAFD = (tiempoFinalAFD - tiempoInicialAFD) / 1000000.0;


        /*------------------------------------------AFN------------------------------------------------------*/
        ArrayList<Nodo> grafo = AutomataFinal.getHistorial();
        /*------------------------------------------AFN------------------------------------------------------*/

        /*Obtener las transiciones del grafo*/
        ArrayList<Transicion> t = control.AlgoritmoCreaTransiciones(grafo);


        /*Obtener la Numeracion de ID de nodos*/
        ArrayList<Integer> ids = new ArrayList<Integer>();
        for(Nodo i: grafo){
            ids.add(i.getId());
        }

        /*Obtener los simbolos del grafo*/
        ArrayList<String> s = control.AlgoritmoImplantaSimbolos(grafo);

        /*Obtener el Nodo Inicial*/
        int IdInicial = AutomataFinal.getNodoInicial().getId();

        /*Obtener el Nodo Final*/
        int IdFinal = AutomataFinal.getNodoFinal().getId();

        HashSet<Nodo> inicial = new HashSet<>();
        inicial.add(AutomataFinal.getNodoInicial());

        long tiempoInicialConvertirAFNAFD = System.nanoTime();
        ArrayList<NodoAFD> noIdentificado = convert.ConvertirAfnAfd(inicial, s);
        long tiempoFinalConvertirAFNAFD = System.nanoTime();
        double tiempoConvertirAFNAFD = (tiempoFinalConvertirAFNAFD - tiempoInicialConvertirAFNAFD) / 1000000.0;
        convert.aceptacionEnAFD(AutomataFinal,noIdentificado);

        /*------------------------------------------AFD en base a un AFN--------------------------------------*/
        ArrayList<NodoAFD> AFD = convert.NombrarNodosDelAFD(noIdentificado);
        /*------------------------------------------AFD en base a un AFN--------------------------------------*/



        System.out.println("Tiempo de Creacion del AFN");
        System.out.println("" + tiempoAFD + " milisegundos");
        System.out.println("Tiempo de convertir el AFN a un ADF");
        System.out.println("" + tiempoConvertirAFNAFD + " milisegundos");

        boolean seguimos = true;
        while (seguimos){

            System.out.println("\nIngrese la cadena que desea simular : ");
            String simulacion = sc.nextLine();

            long tiempoInicialSimulacionAFN = System.nanoTime();
            boolean simulacionAFN = convert.simularAFN(AutomataFinal, simulacion);
            long tiempoFinalSimulacionAFN = System.nanoTime();
            double tiempoSimulacionAFN = (tiempoFinalSimulacionAFN-tiempoInicialSimulacionAFN )/1000000.0;

            System.out.println("---------------------------AFN---------------------------");
            if (simulacionAFN){
                System.out.println("          Cadena: Aceptada");
            }
            else{
                System.out.println("          Cadena: No Aceptada");
            }
            System.out.println("          tiempo: " + tiempoSimulacionAFN + " milisegundos");

            long tiempoInicialSimulacionAFD = System.nanoTime();
            boolean simulacionAFD = convert.simularAFD(AFD, simulacion);
            long tiempoFinalSimulacionAFD = System.nanoTime();
            double tiempoSimulacionAFD = (tiempoFinalSimulacionAFD - tiempoInicialSimulacionAFD)/1000000.0;

            System.out.println("---------------------------AFD---------------------------");
            if (simulacionAFD){
                System.out.println("          Cadena: Aceptada");
            }
            else{
                System.out.println("          Cadena: No Aceptada");
            }
            System.out.println("          tiempo: " + tiempoSimulacionAFD + " milisegundos");


            System.out.println("\nDesea seguir simulando cadenas (0 NO/1 SI) : ");
            String respuesta = sc.nextLine();
            if (respuesta.equals("0")){
                seguimos = false;
            }
        }

        Arbol elArbol = new Arbol();
        ArrayList<NodosRamas> directo = elArbol.CrearElAFDDirecto(regex);
        System.out.println("*************************************************************");
        for (NodosRamas r : directo){
            System.out.println("-------------------------------------");
            boolean seraFinal = false;
            for(Rama q: r.getConjunto()){
                System.out.println(q.getID());
                if(q.getID() == 6){
                    seraFinal = true;
                }
            }
            if(seraFinal){
                System.out.println("Soy de Aceptacion");
            }
            if(!seraFinal){
                System.out.println("NO soy de Aceptacion");
            }

            int losStrings = 0;
            while(losStrings < r.getTransiciones().size()){
                System.out.println("Yo voy a " + r.getArrivals().get(losStrings) + " con la trans: " + r.getTransiciones().get(losStrings));
                losStrings = losStrings + 1;
            }


        }



    }
}

