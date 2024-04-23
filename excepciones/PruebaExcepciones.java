package excepciones;

import java.util.Arrays;

import Libreria.Libreria;

public class PruebaExcepciones {
	public static void main(String[] args) {
		String nombre="";
		int[] arr1=new int[5];
		
		Arrays.fill(arr1, Libreria.generarAleatorio(0, 110));
		
		try {
			nombre.concat("alvaro");
			arr1[7]++;
		} catch (NullPointerException e) {
			nombre="alvaro";
		}catch (IndexOutOfBoundsException e) {
			System.out.println("te has pasao");
		}
		
		System.out.println(nombre);
		
	}
}
