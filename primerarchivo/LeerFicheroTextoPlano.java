package primerarchivo;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

public class LeerFicheroTextoPlano {

	public static void main(String[] args) {
		String linea;
		int nPalabras=0;
		Set<String> distintas;

		//trycatch con recursos, se cierran solos los ficheros
		try (BufferedReader buffer=new BufferedReader(new FileReader("quijote.txt"))){
			distintas=new TreeSet<String>();
			while (buffer.ready()) {
				linea=buffer.readLine();
				nPalabras+=linea.split(" ").length;
				
				Arrays.asList(linea.split(" ")).forEach(p->distintas.add(p));
				
				System.out.println(linea);
			}

			System.out.println(nPalabras);
			System.out.println(distintas.size());
			
			distintas.forEach(System.out::println);

		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} 



	}

}
