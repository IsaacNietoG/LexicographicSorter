package mx.unam.ciencias.edd.proyecto1;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

import mx.unam.ciencias.edd.IteradorLista;
import mx.unam.ciencias.edd.Lista;
/**
 * La clase que se encarga de la parte de la salida de este proyecto.
 *
 * Recibe en la construcción de la instancia los parámetros que necesitamos para la funcionalidad de la clase, siendo:
 *
 * - La lista de los objetos que volcaremos a nuestras salidas.
 * -(Opcional)Ruta del archivo de volcado
 *
 * Lo hice con genéricos para no perder generalidad y posible reutilización del código
 *
 */
public class EscritorGeneral<T> {

    IteradorLista<T> iteradorSource;
    String output;
    boolean esVacia;

    /**
     *  Constructor para el caso donde usaremos la funcionalidad de volcar a archivo
     *
     *  @param source    Una lista de genéricos donde tendremos los objetos que queremos volcar
     *         salida    La ruta del archivo al que volcaremos nuestra salida
     *
     *  */
    public EscritorGeneral(Lista<T> source, String salida){
        iteradorSource = source.iteradorLista();
        this.output = salida;

    }

    /**
     *  Constructor para el caso donde no usaremos la funcionalidad de volcar a archivo
     *
     *  @param source   Una lista de genéricos donde tendremos los objetos que queremos volcar
     *
     *  */
    public EscritorGeneral(Lista<T> source){
        iteradorSource = source.iteradorLista();
        this.output = null;
    }

    /**
     *  Recorre la lista vinculada a la instancia y efectúa la lambda proporcionada
     *
     *  @param accion   Una lambda donde especificaremos la acción a realizar
     *
     *  */
    public void recorridoNormal(AccionLista<T> accion){
        while(iteradorSource.hasNext())
            accion.actua(iteradorSource.next());

    }

    /**
     *  Recorre la lista vinculada a la instancia, pero desde el final y hasta el inicio, efectuando
     *  la lambda proporcionada
     *
     *  @param accion   Una lambda donde especificaremos la acción a realizar
     *  */
    public void recorridoReversa(AccionLista<T> accion){
        iteradorSource.end();
        while(iteradorSource.hasPrevious())
            accion.actua(iteradorSource.previous());
    }

    /**
     *  Volca los contenidos de la lista al archivo especificado.
     *  Si no se especificó archivo durante el instanciado del objeto, no hace nada.
     *
     *  @param invertido   Para indicar si queremos que se vuelquen en orden inverso o no.
     *
     *  */
    public void volcarATexto(boolean invertido){
        if(output == null){ // Guard clause para que no haga nada si no se especifica archivo
            return;
        }

        PrintWriter escritor=null;
        try{
            escritor = new PrintWriter(output);
        }catch(FileNotFoundException e){
            return;
        }

        if(!invertido)
            recorridoNormal((a) -> escritor.println(a));
        else
            recorridoNormal((a) -> escritor.println(a));

        escritor.close();
    }

    /**
     *  Imprime en consola los contenidos de la lista vinculada.
     *
     *  @param invertido    Para indicar si queremos que se impriman en orden inverso o no.
     *
     *  */
    public void volcarAConsola(boolean invertido){
        if(!invertido)
            recorridoNormal((a) -> System.out.println(a));
        else
            recorridoReversa((a) -> System.out.println(a));

    }


}
