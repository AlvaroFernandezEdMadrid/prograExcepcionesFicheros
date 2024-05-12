package deportistas;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

import daw.com.Pantalla;

public class PruebaStream {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<Deportista> lista;
		Stream <Deportista> stream;
		
		lista = cargarDatosBinario();
		
		
		stream = lista.stream();
		
		long cuantos =stream.
			filter((d) -> d.getFechaNacimiento().isAfter(LocalDate.of(1960, 1, 1))).
			map(Deportista::getNombre).
			filter (s -> s.startsWith("m")).count();
		
		System.out.println(cuantos);
		
		
		stream = lista.stream();
		
		Function <Deportista,Integer> edad = d -> Period.between(d.getFechaNacimiento(),LocalDate.now()).getYears(); 
		stream.map(edad).forEach(System.out::println);
		
		stream = lista.stream();
		double media = stream.mapToInt(d -> Period.between(d.getFechaNacimiento(),LocalDate.now()).getYears()).
		average().getAsDouble();
		System.out.println(media);
		
		
		stream = lista.stream();
		stream.map(Deportista::getNombre).distinct().forEachOrdered(System.out::println);
		
		stream = lista.stream();
		stream.map(Deportista::getNombre).limit(2).forEach(System.out::println);
		
		stream = lista.stream();
		stream.map(Deportista::getNombre).skip(2).forEach(System.out::println);
		
		stream = lista.stream();
		stream.map(Deportista::getNombre).
			sorted((s1,s2) -> s1.length()-s2.length()).
			forEach(System.out::println);
		
		stream = lista.stream();
		
	}
	
	@SuppressWarnings("deprecation")
	public static List<Deportista> cargarDatosBinario ()
	{
		String tipo;
		Deportista deportista;
		int cuantos;
		List <Deportista> lista = new ArrayList<>();
		
		File fichero = new File ("deportistas.dat");
		
		if (fichero.exists())
		{
			try (FileInputStream bruto = new FileInputStream (fichero);
					DataInputStream filtro = new DataInputStream (bruto)) 
			{
				
				cuantos = filtro.readInt();
				
				for (int i = 0; i < cuantos; i++ )
				{
					tipo = filtro.readUTF();
					
					deportista = (Deportista) Class.forName(tipo).newInstance();
					deportista.leerFichero(filtro);
					lista.add(deportista);
					
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				Pantalla.escribirString("\nError accediendo al fichero...");
			} catch (InstantiationException | 
					IllegalAccessException |
					ClassNotFoundException e) {
					
				Pantalla.escribirString("\nNo se pueden cargar los datos ...");
				
			}
			
		}
		
		return lista;
	}

}
