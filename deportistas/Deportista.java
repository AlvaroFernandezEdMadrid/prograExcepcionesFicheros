package deportistas;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import daw.com.Pantalla;
import daw.com.Teclado;


public abstract class Deportista  {
	
	private static final String SEPARADOR=":";
	private static final DateTimeFormatter FORMATOFECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	private String dni;
	private String nombre;
	private LocalDate fechaNacimiento;
	
	
	public Deportista ()
	{
		this ("");
	}
	
	public Deportista (String dni)
	{
		this.dni = dni;
		this.nombre = "";
		this.fechaNacimiento = null;
	}
	
	public Deportista(String dni, String nombre, LocalDate fechaNacimiento) {
		
		this.dni = dni;
		this.nombre = nombre;
		setFechaNacimiento  (fechaNacimiento);
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public LocalDate getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(LocalDate fechaNacimiento) {
		
		if (fechaNacimiento == null)
			throw new IllegalArgumentException("fecha sin datos");
		if (fechaNacimiento.plusYears(18).isAfter(LocalDate.now()))
			throw new IllegalArgumentException ("deportista menor de edad");
		
		this.fechaNacimiento = fechaNacimiento;
	}
	
	public void leerDatos ()
	{
		nombre = Teclado.leerString("nombre:");
		leerFecha ();
		
	}

	public void leerFecha ()
	{
		
		boolean repetir;
		LocalDate fecha;
		String textoFecha;
		
		do
		{
			repetir = false;
			try
			{
				textoFecha = Teclado.leerString("fecha de nacimiento");

				fecha = LocalDate.parse(textoFecha, FORMATOFECHA);
				setFechaNacimiento (fecha);
			}
			catch (IllegalArgumentException | DateTimeParseException e )
			{
				Pantalla.escribirString("\nFecha errónea" );
				repetir = true;
			}
		}while (repetir);
	}
	
	public void mostrarDatos ()
	{
		
		Pantalla.escribirString("\ndni: " + dni);
		Pantalla.escribirString("\nNombre: " + nombre);
		Pantalla.escribirString("\nFecha Nacimiento: " + escribirFecha());
		
	}
	
	public String toCSV ()
	{
		String csv = "";
		
		csv += dni;
		csv += SEPARADOR;
		
		csv += nombre;
		csv += SEPARADOR;
		
				
		csv += escribirFecha ();

		return csv;
		
	}
	
	private String escribirFecha ()
	{
		String fecha ;
		if (fechaNacimiento == null)
			fecha = "sin datos";
		else
			fecha = fechaNacimiento.format(FORMATOFECHA);
		
		return fecha;
	}
	
	public String  fromCsv (String linea)
	{
		
		String valores[] = linea.split(SEPARADOR);
		
		dni = valores[0];
		nombre = valores[1];
		
		
		try
		{
	
			setFechaNacimiento(LocalDate.parse(valores[2], FORMATOFECHA));
		}
		catch (IllegalArgumentException | DateTimeParseException e)
		{
			fechaNacimiento = null;
		}
		
		for (int i = 0; i < 3; i++)
			linea = linea.substring(linea.indexOf(SEPARADOR) + 1);
		
		
		return linea;
	}
	
	public void leerFichero (DataInputStream fichero) throws IOException
	{
		dni = fichero.readUTF();
		nombre = fichero.readUTF();
		
		try
		{
	
			setFechaNacimiento(LocalDate.parse(fichero.readUTF()));
		}
		catch (IllegalArgumentException | DateTimeParseException e)
		{
			fechaNacimiento = null;
		}
		
	}
	
	public void escribirFichero (DataOutputStream fichero) throws IOException
	{
		fichero.writeUTF(dni);
		fichero.writeUTF(nombre);
		
		fichero.writeUTF (fechaNacimiento != null?
								fechaNacimiento.toString():
								"sin datos");
		
		/*
		try
		{
			fichero.writeUTF (fechaNacimiento.toString());
		}
		catch (NullPointerException e)
		{
			fichero.writeUTF("sin datos");
		}
		*/
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dni == null) ? 0 : dni.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Deportista other = (Deportista) obj;
		if (dni == null) {
			if (other.dni != null)
				return false;
		} else if (!dni.equals(other.dni))
			return false;
		return true;
	}
	
	
}