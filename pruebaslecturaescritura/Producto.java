package pruebaslecturaescritura;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Objects;

import daw.com.Teclado;

public class Producto implements Comparable<Producto>{
	private static final String SEPARADOR=",";
	private String id, nombre;

	public Producto(String id, String nombre) {
		this.id = id;
		this.nombre = nombre;
	}

	public Producto(String id) {
		this(id,"");
	}

	public Producto() {
		this("");
	}
	
	public Producto(Producto og) {
		id=og.id;
		nombre=og.nombre;
	}
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public String toString() {
		return "Producto [id=" + id + ", nombre=" + nombre + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Producto other = (Producto) obj;
		return Objects.equals(id, other.id);
	}

	public String toCsv() {
		String linea="";

		linea+=id;
		linea+=SEPARADOR;
		linea+=nombre;

		return linea;
	}

	public void fromCsv(String linea) {
		String valores[];

		valores=linea.split(SEPARADOR);

		id=valores[0];
		nombre=valores[1];

	}

	public void leerArchivo(DataInputStream filtro) throws IOException{
		id=filtro.readUTF();
		nombre=filtro.readUTF();	
	}

	public void escribirArchivo(DataOutputStream filtro)  throws IOException{
		filtro.writeUTF(id);
		filtro.writeUTF(nombre);
	}

	@Override
	public int compareTo(Producto o) {
		return id.compareTo(o.id);
	}
	
	public void leerDatos() {
		leerClave();
		leerOtrosDatos();
	}
	
	public void leerClave() {
		id=Teclado.leerString();
	}
	
	public void leerOtrosDatos() {
		nombre=Teclado.leerString();
	}
	
	public void mostrarDatos() {
		System.out.println(this);
	}
}
