package examen;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import daw.com.Teclado;

public abstract class Material implements Comparable<Material>{ //La hago abstracta para que no se pueda construir objetos de ella.
	private static final Categoria CATEGORIADEFECTO=Categoria.CLASICOS80;
	private static final String SEPARADOR=";"; 
	
	private String referencia;
	private String nombre;
	private LocalDate fechaEstreno;
	private Categoria categoria;

	public Material(String referencia, String nombre, LocalDate fechaEstreno, Categoria categoria) {
		this.referencia = referencia;
		this.nombre = nombre;
		this.fechaEstreno = fechaEstreno;
		this.categoria = categoria;
	}

	public Material(String referencia) {
		this(referencia, "", LocalDate.of(1970, 01, 01), CATEGORIADEFECTO);
	}

	public Material() {
		this("");
	}

	public Material(Material og) {
		this.referencia = og.referencia;
		this.nombre = og.nombre;
		this.fechaEstreno = og.fechaEstreno;
		this.categoria = og.categoria;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public LocalDate getFechaEstreno() {
		return fechaEstreno;
	}

	public void setFechaEstreno(LocalDate fechaEstreno) throws IllegalArgumentException{
		if (fechaEstreno.isBefore(LocalDate.of(2022, 02, 03))) {
			throw new IllegalArgumentException("\nNo se permiten fechas de estreno anteriores al 3 de febrero de 2022");
		}
		this.fechaEstreno = fechaEstreno;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) throws IllegalArgumentException{
		if (!categoria.toList().contains(categoria.toString().toUpperCase())) {
			throw new IllegalArgumentException("\nCategoria erronea");
		}
		this.categoria = categoria;
	}

	@Override
	public String toString() {
		return "Material [referencia=" + referencia + ", nombre=" + nombre + ", fechaEstreno=" + fechaEstreno
				+ ", categoria=" + categoria + "]";
	}

	@Override
	public int compareTo(Material o) {
		return referencia.compareTo(o.referencia);
	}
	
	public void toFile(DataOutputStream filtro) throws IOException{
		filtro.writeUTF(referencia);
		filtro.writeUTF(nombre);
		filtro.writeUTF(fechaEstreno.toString());
		filtro.writeUTF(categoria.toString());
	}

	public void fromFile(DataInputStream filtro) throws IOException{
		setReferencia(filtro.readUTF());
		setNombre(filtro.readUTF());
		
		try {
			setFechaEstreno(LocalDate.parse(filtro.readUTF()));
		} catch (IllegalArgumentException e) {
			System.err.println(e.getMessage());
		}
		
		try {
			setCategoria(Categoria.valueOf(filtro.readUTF()));
		} catch (IllegalArgumentException e) {
			System.err.println(e.getMessage());
		}
		
	}
	
	public void leerDatos() {
		leerClave();
		leerOtrosDatos();
	}

	public void leerClave() {
		setReferencia(Teclado.leerString("\nReferencia: "));
	}

	public void leerOtrosDatos() {
		setNombre(Teclado.leerString());
		
		try {
			setFechaEstreno(LocalDate.parse(Teclado.leerString("\nFecha de estreno: ")));
		} catch (IllegalArgumentException e) {
			System.err.println(e.getMessage());
		}
		
		try {
			setCategoria(Categoria.valueOf(Teclado.leerString("\nCategoria: ")));
		} catch (IllegalArgumentException e) {
			System.err.println(e.getMessage());
		}
	}
	
	public void fromCsv(String csv) {
		String valores[]=csv.split(SEPARADOR);
		
		setReferencia(valores[0]);
		setNombre(valores[1]);
		setFechaEstreno(LocalDate.parse(valores[2]));
		setCategoria(Categoria.valueOf(valores[3]));
		
	}
	
	public String toCsv() {
		String linea="";
		
		linea+=referencia;
		linea+=SEPARADOR;
		
		linea+=nombre;
		linea+=SEPARADOR;
		
		linea+=fechaEstreno.toString();
		linea+=SEPARADOR;
		
		linea+=categoria.toString();
		
		return linea;
	}
}
