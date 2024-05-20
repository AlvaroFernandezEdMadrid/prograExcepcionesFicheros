package examen;

import java.util.Objects;
import daw.com.Teclado;


public class Cliente implements Comparable<Cliente>{
	private static final String SEPARADOR=";";  
	
	private String nif;
	private String nombre;
	private float cuota;

	public Cliente(String nif, String nombre, float cuota) {
		this.nif = nif;
		this.nombre = nombre;
		setCuota(cuota);
		
	}

	public Cliente(String nif) {
		this(nif, "", 0);
	}

	public Cliente() {
		this("");
	}
	
	public Cliente(Cliente og) {
		this.nif = og.nif;
		this.nombre = og.nombre;
		this.cuota = og.cuota;
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

	public float getCuota(){
		return cuota;
	}

	public void setCuota(float cuota) throws IllegalArgumentException{
		if (cuota<0) {
			throw new IllegalArgumentException("\nCuota no valida");
		}
		this.cuota = cuota;
	}

	@Override
	public String toString() {
		return "Cliente [nif=" + nif + ", nombre=" + nombre + ", cuota=" + cuota + "]";
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

	public void fromCsv(String csv) {
		String valores[]=csv.split(SEPARADOR);
		
		setNif(valores[0]);
		setNif(valores[1]);
		try {
			setCuota(Float.valueOf(valores[2]));
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public String toCsv() {
		String linea="";
		
		linea+=nif;
		linea+=SEPARADOR;
		
		linea+=nombre;
		linea+=SEPARADOR;
		
		linea+=cuota;
		
		return linea;
	}

	@Override
	public int compareTo(Cliente o) {
		return nif.compareTo(o.nif);
	}
	
	public void leerDatos() {
		leerClave();
		leerOtrosDatos();
	}

	public void leerClave() {
		setNif(Teclado.leerString("\nNif: "));
	}

	public void leerOtrosDatos() {
		setNombre(Teclado.leerString("\nNombre: "));
		setCuota(Teclado.leerFloat("\nCuota: "));
	}
	
	public void mostrarDatos() {
		System.out.println(this);
	}

}
