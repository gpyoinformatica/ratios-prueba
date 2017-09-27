package gpyo.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.Charset;

public final class FileUtil{
	
	/**
     * Método constructor de la clase
     * @deprecated  En desuso debido a que esta clase no tiene sentido realizar instancias nuevas
     */
    @Deprecated
	private FileUtil(){}
	
	public static int countLines(InputStream is) throws IOException {
	    try {
	        byte[] c = new byte[1024];
	        int count = 0;
	        int readChars = 0;
	        boolean empty = true;
	        while ((readChars = is.read(c)) != -1) {
	            empty = false;
	            for (int i = 0; i < readChars; ++i) {
	                if (c[i] == '\n')
	                    ++count;
	            }
	        }
	        return (count == 0 && !empty) ? 1 : count;
	    } finally {
	        is.close();
	    }
	}
	
	public static int countLines(String filename) throws IOException {
		InputStream is = new BufferedInputStream(new FileInputStream(filename));
		int lines = countLines(is);
		return lines;
	}
	
	/**
     * Constante que define el conjunto de caracteres predefinido en el sistema
     */
    public static Charset DEFAULT = Charset.defaultCharset();
    
    /**
     * Conjunto decaracteres UTF-8
     */
    public static Charset UTF_8 = Charset.forName("UTF-8");
    
    /**
     * <p>
     * Obtiene el arreglo de tipo {@code byte} tomando como fuente un objeto de tipo
     * {@code InputStream}
     * </p>
     *
     * @param inputStream   Fuente de lectura para obtener los bytes del archivo
     * 
     * @return      Retorna un arreglo de tipo {@code byte} que representa el contenido del archivo leído
     *
     * @throws IOException  Lanzada en caso de presentarse algún problema en tiempo de ejecución
     *                                              relacionado con la fuente de lectura
     */
    public static byte[] readBytes (InputStream inputStream)
                    throws IOException {
            byte[] archivoByte = new byte[inputStream.available ()];
            inputStream.read (archivoByte);
            inputStream.close ();
            return archivoByte;
    }
    
    /**
     * <p>
     * Obtiene el contenido en un arreglo de tipo {@code byte} tomando como fuente un objeto de tipo
     * {@code File}
     * </p>
     * 
     * @param fileToRead    Objeto que encapsula la informacion del archivo del cual se obtiene la el
     *                              contenido representado en un arreglo de tipo {@code byte}
     *
     * @return      Retorna un arreglo de tipo {@code byte} que represena el contenido del archivo leido
     *
     * @throws IOException  Lanzada en caso de presentarse algun problema en tiempo de ejecucion
     *                                              relacionado con la fuente de lectura
     */
    public static byte[] readBytes (File fileToRead)
                    throws IOException {
            return readBytes (new FileInputStream (fileToRead));
    }
    
    /**
     * <p>
     * Obtiene el contenido en un arreglo de tipo {@code byte} tomando como fuente un objeto de tipo
     * {@code String}
     * </p>
     * 
     * @param content
     * @param charset
     * @return
     */
    public static byte[] readBytes (String content, Charset charset) {
            return content.getBytes (charset);
    }
    
    /**
     * <p>
     * Toma el contenido del parámetro {@code content} en la dirección que se define en la ubicación 
     * del párametro {@code fileToWrite}
     * </p>
     * 
     * @param content       Define el contenido del archivo que se escribe en el disco duro
     * @param fileToWrite   Define la ubicación del archivo que se genera
     * @param charset       Indica el conjunto de caracteres con el que se escribe el archivo
     *
     * @throws IOException  Lanzada cuando ocurre un error en tiempo de ejecución referente a la
     *                                              escritura del archivo
     */
    public static void writeFile (String content, File fileToWrite, Charset charset)
                    throws IOException {
            writeFile (content.getBytes (charset), fileToWrite, charset);
    }
    
    /**
     * <p>
     * Escribe un archivo en disco duro el contenido del parámetro {@code content} en la ubicación 
     * definida por el parámetro {@code fileToWrite} con el conjunto de caracteres definido por el 
     * parámetro charset.
     * </p>
     *
     * @param content       Define e contenido del archivo que se escribe e disco duro representado en
     *                                      un arreglo de bytes que corresponde a contenido binario del archivo a
     *                                      escribir
     * @param fileToWrite   Define la ubicacion del archivo que se genera
     * @param charset       Indica el conjunto de caracteres con el que se escribe el archivo
     *
     * @throws IOException  Lanzada cuando ocurre un error en tiempo de ejecucion referente a la
     *                                              escritura del archivo
     */
    public static void writeFile (byte[] content, File fileToWrite, Charset charset)
                    throws IOException {
            FileOutputStream fos = new FileOutputStream (fileToWrite);
            fos.write (content);
            fos.close ();
    }
}