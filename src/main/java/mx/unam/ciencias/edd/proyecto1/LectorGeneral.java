package mx.unam.ciencias.edd.proyecto1;

import java.io.InputStreamReader;
import mx.unam.ciencias.edd.Lista;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;

/**
 * LectorGeneral
 *
 * Esta clase contiene los métodos que utillizaremos para leer los archivos y la entrada estandar que recibiremos del usuario en el proyecto.
 *
 * Recibe en su constructor dos objetos, uno siendo el {@link java.io.InputStreamReader} que tiene
 * la entrada estándar del programa. (El system.in) y una lista de objetos File, que serán los archivos
 * de donde extraeremos el texto.
 *
 * De ambas fuentes, junta todo en una lista de objetos StringOrdenables compuesta dentro de la
 * instancia
 */
public class LectorGeneral {
    private Lista<File> archivos;
    private InputStreamReader standardIn;
    private Lista<StringOrdenable> renglones;

    public LectorGeneral(InputStreamReader standardIn, Lista<File> archivos){
        this.archivos = archivos;
        this.standardIn = standardIn;
        renglones = new Lista<StringOrdenable>();
    }

    public void leerEntradaEstandar(){
        try{
            if(!standardIn.ready()){
                standardIn.close();
            }
        }catch (IOException e){
            return;
        }
        BufferedReader reader = new BufferedReader(standardIn);
        String renglon;
        try{
            while((renglon = reader.readLine()) != null){
                renglones.agrega(new StringOrdenable(renglon));
            }
        reader.close();
        }catch (IOException e){
            System.err.println("El archivo o los archivos recibidos por entrada estandar no son legibles, omitiendo...");
        }
    }

    /**
     *  Lee todos los archivos que existen en la Lista archivos y vacia sus renglones en la
     *  lista de renglones
     *  */
    public void leerArchivos(){
        for(File input : archivos){
            BufferedReader reader;
            try{
                reader = new BufferedReader(new FileReader(input));
            }catch(FileNotFoundException e){
                System.err.printf("Uno de los archivos indicados como parametro no pudo leerse, omitiendo...");
                continue;
            }
            String renglon;
            try{
                while((renglon = reader.readLine()) != null){
                    renglones.agrega(new StringOrdenable(renglon));
                }
                reader.close();
            }catch (IOException e){
                System.err.println("El archivo o los archivos recibidos por argumento no son legibles, omitiendo...");
            }
        }
    }

    public Lista<StringOrdenable> getLista(){
        return this.renglones;
    }
}
