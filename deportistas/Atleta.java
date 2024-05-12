package deportistas;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import daw.com.Pantalla;
import daw.com.Teclado;


public class Atleta extends Deportista {
	

	private final static DateTimeFormatter FORMATOHORA=DateTimeFormatter.ofPattern("HH:mm:ss");
	
	private String lugar;
	private int distancia;
	private Duration marca;

	public Atleta ()
	{
		this ("");
	}
	
	public Atleta(String dni,String nombre,LocalDate fechaNacimiento,
			String lugar,int distancia,Duration marca) 
	{
		super(dni,nombre,fechaNacimiento);
		
		this.lugar = lugar;
		setDistancia (distancia);
		this.marca = marca;
	}
	
	public Atleta(String dni) {
		super(dni);
		this.lugar="";
		this.distancia = 0;
		this.marca = null;
	}

	public String getLugar() {
		return lugar;
	}

	public void setLugar(String lugar) {
		this.lugar = lugar;
	}

	public int getDistancia() {
		return distancia;
	}

	public void setDistancia(int distancia) {
		
		if(distancia < 0) {
			throw new IllegalArgumentException("\nValor no permitido");
		}
		
		this.distancia = distancia;
	}

	public Duration getMarca() {
		return marca;
	}

	public void setMarca(Duration marca) {
		this.marca = marca.abs();
	}
	

	public void leerDatos() {
		boolean correcto ;
		
		super.leerDatos();
		
		lugar=Teclado.leerString("\nLugar");
		do
		{
			correcto = true;
			int distancia = Teclado.leerInt("\nDistancia");
			try {
			setDistancia(distancia);
			}
			catch (IllegalArgumentException e)
			{
				Pantalla.escribirString(e.getMessage());
				correcto = false;
			}
		}while (!correcto);
		
		marca = Tiempo.leerDuration ();
	}

	
	
	public void mostrarDatos ()
	{
		
		super.mostrarDatos();
		
		Pantalla.escribirString("\nLugar: "+lugar);
		Pantalla.escribirString("\nDistancia: "+ distancia);
		Pantalla.escribirString("\nMarca: "+ Tiempo.tiempoToString (marca));
		
	}
	
	
	
	public String toCSV ()
	{
		String csv =  super.toCSV() + ":";
		
		csv += lugar;
		csv += ":";
		
		csv += distancia;
		csv += ":";
		
				
		csv += marca.getSeconds(); // se guarda marca como segundos

		return csv;
		
	}
	
	
	
	public String  fromCsv (String linea)
	{
		linea = super.fromCsv(linea);
		
		String valores[] = linea.split(":");
		
		lugar = valores[0];
		
		try
		{
			setDistancia (Integer.parseInt(valores[1]));
		}
		catch (IllegalArgumentException e)
		{
			distancia = 0;
		}
		
		try
		{
			// se lee marca como segundos
			setMarca (Duration.ofSeconds(Long.parseLong(valores[2])));
		}
		catch (NumberFormatException e)
		{
			marca = null;
		}
		
		linea = ""; // no hay m�s datos que tratar 
		
		return linea;
	}
	
	public void leerFichero (DataInputStream fichero) throws IOException
	{
		long segundos;
		
		super.leerFichero(fichero);
		
	
		lugar = fichero.readUTF();
		
		
		try
		{
			setDistancia (fichero.readInt());
		}
		catch (IllegalArgumentException e)
		{
			distancia = 0;
		}
		
		
		
		segundos = fichero.readLong();
		setMarca (segundos > 0? Duration.ofSeconds(segundos): null);
		
		
	}
	
	public void escribirFichero (DataOutputStream fichero) throws IOException
	{
		super.escribirFichero(fichero);
		
		fichero.writeUTF(lugar) ;
		fichero.writeInt(distancia);
		
		fichero.writeLong (marca!= null ?marca.getSeconds():0);	
	}
	
}
