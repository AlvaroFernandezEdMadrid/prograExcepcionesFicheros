package primerarchivo;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import daw.com.Teclado;

public class EscribirFicheroTextoPlano {
	public static void main(String[] args) {
		String linea;
		
		//Sobreescribe
		/*try (PrintWriter salida=new PrintWriter("miFichero.txt")){
			do {
				linea=Teclado.leerString("\nLinea: ");
				salida.println(linea);
			} while (!linea.isEmpty());
		} catch (FileNotFoundException e) {
			System.out.println("Fichero no encontrado.");
		}*/
		
		//Modo append, annade.
		try (PrintWriter salida=new PrintWriter(new FileWriter("miFichero.txt",true))){
			do {
				linea=Teclado.leerString("\nLinea: ");
				salida.println(linea);
			} while (!linea.isEmpty());
		} catch (FileNotFoundException e) {
			System.out.println("Fichero no encontrado.");
		} catch (IOException e1) {
			System.out.println("Fichero no encontrado.");
		}
	}
}
