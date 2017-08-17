import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader;
import sun.plugin.javascript.navig.AnchorArray;
import sun.plugin.javascript.navig.Array;

import javax.net.ssl.SSLEngineResult;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
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



        long  tiempoInicialAFN = System.nanoTime();
        Automata AutomataFinal = control.LectorDeExpresiones();
        long tiempoFinalAFN = System.nanoTime();
        double  tiempoAFN = (tiempoFinalAFN - tiempoInicialAFN) / 1000000.0;


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


        BufferedWriter bw2 = null;
        FileWriter fw2 = null;


        try {

            PrintWriter writer = new PrintWriter("Descripcion AFN.txt");
            writer.println("AFN:\nNodos: " +ids + "\nNodo Inicial: " + IdInicial+ "\nNodo Final: " + IdFinal+ "\nSimbolos: " + s + "\nTransiciones: " + t + "\nTiempo de Creacion: " + tiempoAFN + " milisegundos");
            writer.close();

        } catch (Exception e) {

            e.printStackTrace();

        }

        long tiempoInicialConvertirAFNAFD = System.nanoTime();
        ArrayList<NodoAFD> noIdentificado = convert.ConvertirAfnAfd(inicial, s);
        long tiempoFinalConvertirAFNAFD = System.nanoTime();
        double tiempoConvertirAFNAFD = (tiempoFinalConvertirAFNAFD - tiempoInicialConvertirAFNAFD) / 1000000.0;
        convert.aceptacionEnAFD(AutomataFinal,noIdentificado);

        /*------------------------------------------AFD en base a un AFN--------------------------------------*/
        ArrayList<NodoAFD> AFD = convert.NombrarNodosDelAFD(noIdentificado);
        /*------------------------------------------AFD en base a un AFN--------------------------------------*/

        ArrayList<String> simbolos = convert.getSimbolos(AFD);
        ArrayList<Transicion> trensi = convert.getTransiciones(AFD);
        int inicio = convert.getNodoInicial(AFD);
        int finiti = convert.getNodoFinal(AFD);

        ArrayList<Integer> iDs = new ArrayList<Integer>();
        for(NodoAFD i: AFD){
            iDs.add(i.getId());
        }



        BufferedWriter bw3 = null;
        FileWriter fw3 = null;


        try {

            PrintWriter writer = new PrintWriter("Descripcion AFN-AFD.txt");
            writer.println("AFN-AFD:\nNodos: " +iDs + "\nNodo Inicial: " + inicio+ "\nNodo Final: " + finiti+ "\nSimbolos: " + simbolos+ "\nTransiciones: " + trensi+ "\nTiempo de Creacion: " + tiempoConvertirAFNAFD+ " milisegundos");
            writer.close();

        } catch (Exception e) {

            e.printStackTrace();

        }

/*------------------------------------------------------Minimizacion de AFD-------------------------------------------------------------------------------*/

        long tiempoInicialMini = System.nanoTime();
        MinimizadorDeAFD minimizador = new MinimizadorDeAFD(AFD.get(0).getTransiciones());
        minimizador.crearLasParejasEIdentificarlas(AFD);
        minimizador.movimientoDeParejas();
        long tiempoFinalMini = System.nanoTime();
        double tiempoMini = (tiempoFinalMini- tiempoInicialMini) / 1000000.0;

        ArrayList<Dstate> nodazos = minimizador.nodosDelAFD();

        int contador = 0;
        for(Dstate i: nodazos){
            i.setID(contador);
            contador = contador + 1;
        }

        ArrayList<Integer> idsMini = new ArrayList<Integer>();
        for(Dstate i: nodazos){
            idsMini.add(i.getID());
        }

        ArrayList<Transicion> transiDeMini = minimizador.getTransiciones(nodazos);
        int inicioMini = minimizador.getNodoInicial(nodazos);
        int finalMini = minimizador.getNodoFinal(nodazos);
        ArrayList<String> simbDeMini = minimizador.getSimbolos(nodazos);

        BufferedWriter bw6 = null;
        FileWriter fw6 = null;


        try {

            PrintWriter writer = new PrintWriter("Descripcion AFDMinimizado.txt");
            writer.println("AFD:\nNodos: " +idsMini + "\nNodo Inicial: " + +inicioMini+ "\nNodo Final: " + finalMini+ "\nSimbolos: " + simbDeMini+ "\nTransiciones: " + transiDeMini+ "\nTiempo de Creacion: " + tiempoMini+ " milisegundos" );
            writer.close();

        } catch (Exception e) {

            e.printStackTrace();

        }






        boolean seguimos = true;
        while (seguimos){



            System.out.println("\nIngrese la cadena que desea simular : ");
            String simulacion = sc.nextLine();

            long tiempoInicialSimulacionAFN = System.nanoTime();
            boolean simulacionAFN = convert.simularAFN(AutomataFinal, simulacion);
            long tiempoFinalSimulacionAFN = System.nanoTime();
            double tiempoSimulacionAFN = (tiempoFinalSimulacionAFN-tiempoInicialSimulacionAFN )/1000000.0;

            BufferedWriter bw4 = null;
            FileWriter fw4 = null;


            try {

                PrintWriter writer = new PrintWriter("Descripcion SimulacionAFN.txt");
                if(simulacionAFN){
                    writer.println("Cadena: Aceptada\n" + "tiempo: " + tiempoSimulacionAFN + " milisegundos");
                }
                else{
                    writer.println("Cadena: No Aceptada\n" + "tiempo: " + tiempoSimulacionAFN + " milisegundos");
                }

                writer.close();

            } catch (Exception e) {

                e.printStackTrace();

            }

            long tiempoInicialSimulacionAFD = System.nanoTime();
            boolean simulacionAFD = convert.simularAFD(AFD, simulacion);
            long tiempoFinalSimulacionAFD = System.nanoTime();
            double tiempoSimulacionAFD = (tiempoFinalSimulacionAFD - tiempoInicialSimulacionAFD)/1000000.0;

            BufferedWriter bw5 = null;
            FileWriter fw5 = null;


            try {

                PrintWriter writer = new PrintWriter("Descripcion SimulacionAFD.txt");
                if(simulacionAFN){
                    writer.println("Cadena: Aceptada\n" + "tiempo: " + tiempoSimulacionAFD+ " milisegundos");
                }
                else{
                    writer.println("Cadena: No Aceptada\n" + "tiempo: " + tiempoSimulacionAFD + " milisegundos");
                }

                writer.close();

            } catch (Exception e) {

                e.printStackTrace();

            }
            System.out.println("\nDesea seguir simulando cadenas (0 NO/1 SI) : ");
            String respuesta = sc.nextLine();
            if (respuesta.equals("0")){
                seguimos = false;
            }
        }

/*---------------------------------------------CreacionDirecta--------------------------------------------------------------*/
        Arbol elArbol = new Arbol();

        long tiempoInicialCrearDirectoElAFD = System.nanoTime();
        ArrayList<NodosRamas> directo = elArbol.CrearElAFDDirecto(regex);
        long tiempoFinalCrearDirectoElAFD = System.nanoTime();
        double tiempoCrearDirectoElAFD = (tiempoFinalCrearDirectoElAFD - tiempoInicialCrearDirectoElAFD) / 1000000.0;

        /*Identificar los nodos del AFD*/
        int numeroParaElID = 0;
        for(NodosRamas nodoRama: directo){
            nodoRama.setId(numeroParaElID);
            numeroParaElID = numeroParaElID + 1;
        }


        System.out.println("\n***********************CreacionDirectaDelAFD**************************************");
        System.out.println("Se necesito de " + tiempoCrearDirectoElAFD + " milisegundos");
        for (NodosRamas r : directo){
            System.out.println("-------------------------------------");
            System.out.println("Mi ID es: " + r.getId());
            boolean seraFinal = false;
            for(Rama q: r.getConjunto()){
                System.out.println(q.getID());
                if(q.getContenido().equals("#")){
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
                System.out.println("Yo voy a " + Ramas(r.getArrivals().get(losStrings)) + " con la trans: " + r.getTransiciones().get(losStrings));
                losStrings = losStrings + 1;
            }


        }




    }

    public static String Ramas(NodosRamas noEsR){
        String casual  ="{";
        for (Rama i: noEsR.getConjunto()){
            casual = casual + ""  + i.getID() + ", ";
        }
        casual = casual + "}";
        return  casual;
    }
}

