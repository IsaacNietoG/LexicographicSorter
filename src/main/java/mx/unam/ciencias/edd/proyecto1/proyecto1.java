package mx.unam.ciencias.edd.proyecto1;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import mx.unam.ciencias.edd.Lista;

/**
 *  Proyecto 1 : Ordenador lexicográfico
 *
 *  Ordena por lineas una entrada de texto recibida.
 *
 *  La entrada de texto puede ser mediante un archivo de texto o mediante la entrada estándar, en cualquiera de los dos casos
 *  el programa debe manejarlo adecuadamente.
 *  Si el programa recibe más de dos archivos como entrada, debe de juntarlos en un solo output.
 *
 *  La salida se ordena de manera lexicográfica, es decir, una generalización del orden alfabético. Para detalles acerca del orden, vease
 *  StringComparable, ya que ese será el objeto comparable que utilizaremos.
 *
 *  Banderas que puede recibir el programa:
 *
 *  -r             : Ordena de manera inversa las líneas
 *  -o "/salida/"  : El programa guarda la salida en el archivo /salida/ especificado.
 *  */
public class proyecto1 {

    /**
     *  Si ocurre algun error durante la lectura de los parámetros, usamos este método.
     *  */
    public static void uso(){
        System.err.println("Los parámetros no se han recibido de manera correcta, verifique su entrada o lea la documentación");
        System.exit(0);
    }

    /**
     *  El principal método de nuestro proyecto.
     *
     *
     *  */
    public static void main(String[] args) {
        //Estado de las banderas
        boolean invertido = false;
        boolean outputDefinido = false;
        String output = "";
        //Lista de archivos recibidos
        Lista<File> archivos = new Lista<>();

        //Verificacion de argumentos recibidos
        for(int i=0; i<args.length; i++){
            switch(args[i]){

                case "-r":
                    if(invertido) //Guard clause para cuando el usuario quiera usar la misma flag otra vez
                        uso();
                    invertido = true;
                    break;

                case "-o":
                    if(outputDefinido) //Guard clause para cuando el usuario quiera usar la misma flag otra vez
                        uso();
                    outputDefinido = true;
                    try{
                        output = args[i+1];
                        i++;
                    }catch (IndexOutOfBoundsException e){ //Si el usuario ingresa -o pero no se puede leer el argumento ingresado
                                                          //inmediatamente después entonces no tenemos una salida especificada
                                                          //adecuadamente, así q lo regresamos a su casa
                        uso();
                    }
                    break;

                default: //Si el parametro recibido no es parte de -r o -o, entonces es una ruta a un archivo para trabajar
                    try{
                        agregarFuente(args[i], archivos);
                    }catch (Exception e){
                        System.err.printf("El archvo indicado como %d parametro no pudo leerse, omitiendo...", i);
                    }
            }
        }

        //Comenzamos a reunir nuestras fuentes en una sola lista. Para esto usamos la clase LectorGeneral

        InputStreamReader entradaEstandar = new InputStreamReader(System.in);
        LectorGeneral lector = new LectorGeneral(entradaEstandar, archivos);
        lector.leerEntradaEstandar();
        lector.leerArchivos();

        Lista<StringOrdenable> listaRenglones = lector.getLista();

        listaRenglones = Lista.mergeSort(listaRenglones); //Ya que StringOrdenable es comparable, podemos usar el metodo que no pide lambda

        for(StringOrdenable e: listaRenglones){
            System.out.println(e.comparable);
        }
        System.out.println("Salida:");
        for(StringOrdenable e: listaRenglones){
            System.out.println(e);
        }
        
    }

    /**
     *  Agrega un archivo a la lista de archivos del programa.
     *
     *  Primero verifica si la ruta pasada es un archivo y si el mismo es legible.
     *  */
    public static void agregarFuente(String ruta, Lista<File> fuentes) throws IOException{
        File archivo = new File(ruta);
        if(!archivo.isFile() || !archivo.canRead()){
            System.out.println("La ruta especificada por: " + ruta + " no es legible o no es un archivo, omitiendo...");
            throw new IOException();
        }
        fuentes.agrega(archivo);
    }

}
