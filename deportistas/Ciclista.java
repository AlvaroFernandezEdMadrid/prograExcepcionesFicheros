package deportistas;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDate;

import daw.com.Pantalla;
import daw.com.Teclado;

public class Ciclista extends Deportista {
	
	private String nombrePrueba;
	private int etapasPrueba;
	private int puesto;
	private int etapasGanadas;

	
	public Ciclista ()
	{
		this ("");
	}
	
	
	public Ciclista(String dni) {
		super(dni);
		
		this.nombrePrueba = "";
		this.etapasPrueba = 0;
		this.puesto = 0;
		this.etapasGanadas = 0;
		
	}


	public Ciclista(String dni,String nombre,LocalDate fechaNacimiento,
			String nombrePrueba, int etapasPrueba, int puesto, int etapasGanadas) {
		super(dni,nombre,fechaNacimiento);
		this.nombrePrueba = nombrePrueba;
		setEtapasPrueba (etapasPrueba);
		setPuesto (puesto);
		setEtapasGanadas (etapasGanadas);
	}


	public String getNombrePrueba() {
		return nombrePrueba;
	}


	public void setNombrePrueba(String nombrePrueba) {
		this.nombrePrueba = nombrePrueba;
	}


	public int getEtapasPrueba() {
		
		return etapasPrueba;
	}


	public void setEtapasPrueba(int etapasPrueba) {
		
		if(etapasPrueba < 0) {
			throw new IllegalArgumentException("\nValor no permitido");
		}
		
		this.etapasPrueba = etapasPrueba;
	}


	public int getPuesto() {
		return puesto;
	}


	public void setPuesto(int puesto) {
		
		if(puesto < 0) {
			throw new IllegalArgumentException("\nValor no permitido");
		}
		
		this.puesto = puesto;
	}


	public int getEtapasGanadas() {
		return etapasGanadas;
	}


	public void setEtapasGanadas(int etapasGanadas) {
		
		if(etapasGanadas < 0 || etapasPrueba < etapasGanadas) {
			throw new IllegalArgumentException("\nValor no permitido");
		}
		
		
		this.etapasGanadas = etapasGanadas;
	}
	
	public void leerDatos ()
	{
		boolean correcto;
		super.leerDatos();
		
		this.nombrePrueba = Teclado.leerString("Prueba ");
		
		do
		{
			try {
				correcto = true;
				setEtapasPrueba(Teclado.leerInt("\nEtapas de la prueba "));
			}
			catch (IllegalArgumentException e)
			{
				Pantalla.escribirString("\n" + e.getMessage());
				correcto = false;
			}
			
		}while (!correcto);
		
		do
		{
			try {
				correcto = true;
				setPuesto(Teclado.leerInt("\nPuesto en la prueba "));
			}
			catch (IllegalArgumentException e)
			{
				Pantalla.escribirString("\n" + e.getMessage());
				correcto = false;
			}
			
		}while (!correcto);
		
		do
		{
			try {
				correcto = true;
				setEtapasGanadas(Teclado.leerInt("\nnº de etapas ganadas en de la prueba"));
			}
			catch (IllegalArgumentException e)
			{
				Pantalla.escribirString("\n" + e.getMessage());
				correcto = false;
			}
			
		}while (!correcto);
		
		
		/*
		leerAtributo ("etapasPrueba", "nº de etapas de la prueba ");
		leerAtributo ("puesto", "Puesto en la carrera ");
		leerAtributo ("etapasGanadas", "nº de etapas ganadas ");
		*/
	}
	
	
	
	private void leerAtributo (String atributo, String mensaje)
	{
		Object valor;
		boolean correcto ;
		String nombreMetodo = "set";
		
		// Construir nombre del método set
		nombreMetodo += atributo.substring(0, 1).toUpperCase();
		nombreMetodo += atributo.substring(1);
		
		
		do
		{
			correcto = true;
			try {
				
				// Obtener atributo a leer
				Field field = getClass().getDeclaredField(atributo);
				
				// Crear modificador (set) del atributo a leer
				Method metodo = getClass().getMethod(nombreMetodo, field.getType());
			
				
				// leer atributo del teclado
				switch (field.getType().getSimpleName())
				{
					case "int" :
						valor = Teclado.leerInt(mensaje);
						break;
					case "float" :
						valor = Teclado.leerFloat(mensaje);
						break;
					default :
						valor = Teclado.leerString("\nvalor");
				}
				
				
				// Llamar al método set				
				metodo.invoke(this, valor);
				
	

			} catch (NoSuchFieldException | 
					SecurityException | 
					IllegalArgumentException | 
					IllegalAccessException | 
					NoSuchMethodException | 
					InvocationTargetException e) {
				//e.printStackTrace();
				Pantalla.escribirString("\nError leyendo.. " + e.getMessage());
				correcto = false;
			}
			
		}while (!correcto);
		
	}
	
	
	public void mostrarDatos ()
	{
		super.mostrarDatos();
		
		Pantalla.escribirString("\nPrueba " + nombrePrueba);

		Pantalla.escribirString("\nEtapas Prueba " + etapasPrueba);
		Pantalla.escribirString("\nPuesto en la Prueba " + puesto);
		Pantalla.escribirString("\nEtapas ganadas " + etapasGanadas);

		
	}
	
	
	public String toCSV ()
	{
		String csv =  super.toCSV() + ":";
		
		csv += nombrePrueba;
		csv += ":";
		
		csv += etapasPrueba;
		csv += ":";
		
		csv += puesto;
		csv += ":";
				
		csv += etapasGanadas;

		return csv;
		
	}
	
	
	
	public String  fromCsv (String linea)
	{
		linea = super.fromCsv(linea);
		
		String valores[] = linea.split(":");
		
		nombrePrueba = valores[0];
		
		try
		{
			setEtapasPrueba (Integer.parseInt(valores[1]));
		}
		catch (IllegalArgumentException e)
		{
			etapasPrueba = 0;
		}
		
		
		try
		{
			setPuesto (Integer.parseInt(valores[2]));
		}
		catch (IllegalArgumentException e)
		{
			puesto = 0;
		}
		
		
		try
		{
			setEtapasGanadas (Integer.parseInt(valores[3]));
		}
		catch (IllegalArgumentException e)
		{
			etapasGanadas = 0;
		}
		
		
		
		linea = ""; // no hay m�s datos que tratar 
		
		return linea;
	}
	
	public void leerFichero (DataInputStream fichero) throws IOException
	{
		super.leerFichero(fichero);
		
		nombrePrueba = fichero.readUTF();
		
				
		try
		{
			setEtapasPrueba (fichero.readInt());
		}
		catch (IllegalArgumentException e)
		{
			etapasPrueba = 0;
		}
		
		
		try
		{
			setPuesto (fichero.readInt());
		}
		catch (IllegalArgumentException e)
		{
			puesto = 0;
		}
		
		
		try
		{
			setEtapasGanadas (fichero.readInt());
		}
		catch (IllegalArgumentException e)
		{
			etapasGanadas = 0;
		}
		
	}
	
	public void escribirFichero (DataOutputStream fichero) throws IOException
	{
		super.escribirFichero(fichero);
		
		fichero.writeUTF(nombrePrueba);
		
		fichero.writeInt(etapasPrueba);
		fichero.writeInt(puesto);
		fichero.writeInt(etapasGanadas);
	}
	
	
	
}
