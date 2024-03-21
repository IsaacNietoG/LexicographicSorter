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
        char[] out = new char[original.length()];
        original = Normalizer.normalize(original, Normalizer.Form.NFD);
        int j = 0;
        for (int i = 0, n = original.length(); i < n; ++i) {
            char c = original.charAt(i);
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
}
