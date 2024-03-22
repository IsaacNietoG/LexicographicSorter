package mx.unam.ciencias.edd.proyecto1;

import java.text.Normalizer;

/**
 * Una string ordenable.
 *
 * Para acomodarnos a los requisitos de la practica, esta clase comparable implementa los metodos
 * necesarios para comparar la string que se recibe en el constructor con otra.
 *
 * Para acomodarse a estos requisitos, realizamos las siguientes acciones en las strings que
 * realmente vamos a comparar.
 *
 * - Convertimos a minusculas
 * - Eliminamos espacios exteriores
 * - Eliminamos espacios y caracteres distintos a letras
 * - Convierte acentos y dieresis a sus versiones vocales
 * - Convierte las eñes a enes
 *
 */
public class StringOrdenable implements Comparable<StringOrdenable>{
    String original;
    String comparable;

	public StringOrdenable(String string){
        original = string;
        comparable = arreglarOriginal();
    }

    /**
     *  Normaliza el texto original y lo guarda en una string que será la que compararemos
     *
     *  */
    private String arreglarOriginal(){
        String normalizar = Normalizer.normalize(original.toLowerCase().trim()
                                                 .replaceAll("[^a-zA-Z0-9]", ""),
                                                 Normalizer.Form.NFD);
        char[] out = new char[normalizar.length()];
        int j = 0;
        for (int i = 0, n = normalizar.length(); i < n; ++i) {
            char c = normalizar.charAt(i);
            if (c <= '\u007F') out[j++] = c;
        }
        return new String(out);
    }

    /**
     *  Utiliza el compareTo de la clase String para comparar otro objeto {@link StringOrdenable}
     *
     *  Para esto comparamos las strings comparables, ya que por el metodo arreglarOriginal, estas
     *  Strings ahora están "limpias".
     *  */
    @Override
    public int compareTo(StringOrdenable arg0) {
        return comparable.compareTo(arg0.comparable);
    }

    /**
     *  Para asegurarnos que todo funciona bien, la representacion toString de este objeto es la
     *  string original
     *  */
    @Override
    public String toString() {
        return original;
    }
}
