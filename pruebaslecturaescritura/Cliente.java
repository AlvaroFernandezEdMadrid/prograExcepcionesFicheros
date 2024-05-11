package pruebaslecturaescritura;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Objects;

import daw.com.Teclado;

public class Cliente implements Comparable<Cliente>{
	private static final String SEPARADOR=",";
	
	private String nif;
	private String nombre;
	private LocalDate fecha;

	public Cliente(String nif, String nombre, LocalDate fecha) {
		this.nif = nif;
		this.nombre = nombre;
		setFecha(fecha);
	}

	public Cliente(String nif) {
		this(nif,"",LocalDate.now());
	}

	public Cliente() {
		this("");
	}
	
	public Cliente(Cliente og) {
		nif=og.nif;
		nombre=og.nombre;
		fecha=og.fecha;
	}

	public String getNif() {
		return nif;
	}
	
	public void setNif(String nif) {
		this.nif = nif;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public LocalDate getFecha() {
		return fecha;
	}
	
	public void setFecha(LocalDate fecha) throws IllegalArgumentException{
		if (fecha.isAfter(LocalDate.now())) {
			throw new IllegalArgumentException();
		}
		this.fecha = fecha;
	}

	@Override
	public String toString() {
		return "Cliente [nif=" + nif + ", nombre=" + nombre + ", fecha=" + fecha + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(nif);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cliente other = (Cliente) obj;
		return Objects.equals(nif, other.nif);
	}

	@Override
	public int compareTo(Cliente o) {
		return nif.compareTo(o.nif);
	}

	public String toCsv() {
		String valores="";
		
		valores+=nif;
		valores+=SEPARADOR;
		
		valores+=nombre;
		valores+=SEPARADOR;
		
		valores+=fecha.toString();
		
		return valores;
	}
	
	public void fromCsv(String linea) {
		String valores[];
		
		valores=linea.split(SEPARADOR);
		
		nif=valores[0];
		nombre=valores[1];
		
		try {
			fecha=LocalDate.parse(valores[2]);
		} catch (DateTimeParseException e) {
			System.err.println("\nFecha invalida");
		}
		
	}
	
	public void leerFichero(DataInputStream filtro) throws IOException{
		nif=filtro.readUTF();
		nombre=filtro.readUTF();
		
		try {
			setFecha(LocalDate.parse(filtro.readUTF()));
		} catch (DateTimeParseException e) {
			System.err.println("\nFecha invalida");
		}
		
	}
	
	public void escribirFichero(DataOutputStream filtro) throws IOException{
		filtro.writeUTF(nif);
		filtro.writeUTF(nombre);
		filtro.writeUTF(fecha.toString());
	}
	
	public void leerDatos() {
		leerClave();
		leerOtrosDatos();
	}
	
	public void leerOtrosDatos() {
		boolean exito;
		
		setNombre(Teclado.leerString("\nNombre: "));
		do {
			try {
				exito=true;
				setFecha(LocalDate.parse(Teclado.leerString("\nFecha aaaa-mm-dd: ")));
			} catch (IllegalArgumentException e) {
				exito=false;
			}
		} while (!exito);
	}
	
	public void leerClave() {
		setNif(Teclado.leerString("\nNIF: "));
	}
	
	public void mostrarDatos() {
		System.out.println(this);
	}
	
}
