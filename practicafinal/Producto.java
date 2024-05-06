package practicafinal;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Objects;

import daw.com.Teclado;

public abstract class Producto implements Comparable<Producto>{
	private String numRef;
	private String nombre;
	private float precioBase;
	private int stock;
	
	public Producto(String numRef, String nombre, float precioBase, int stock) {
		this.numRef = numRef;
		this.nombre = nombre;
		setPrecioBase(precioBase);
		setStock(stock);
	}
	
	public Producto(String numRef) {
		this(numRef,"",0,0);
	}

	public Producto() {
		this("");
	}

	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public float getPrecioBase() {
		return precioBase;
	}
	
	public void setPrecioBase(float precioBase) throws IllegalArgumentException {
		if (precioBase<0) {
			throw new IllegalArgumentException("\nNo se permiten precioBases negativos.");
		}
		this.precioBase = precioBase;
	}
	
	public int getStock() {
		return stock;
	}
	
	public void setStock(int stock)  throws IllegalArgumentException{
		if (stock<0) {
			throw new IllegalArgumentException("\nNo se permiten stocks negativos.");
		}
		this.stock = stock;
	}
	
	public String getNumRef() {
		return numRef;
	}

	@Override
	public int hashCode() {
		return Objects.hash(numRef);
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
		return Objects.equals(numRef, other.numRef);
	}
	
	@Override
	public String toString() {
		return "Producto Referencia=" + numRef + ", nombre=" + nombre + ", precioBase=" + precioBase + ", stock=" + stock;
	}

	@Override
	public int compareTo(Producto o) {
		return numRef.compareTo(o.numRef);
	}
	
	public void leerClave() {
		numRef=Teclado.leerString("\nNumero de referencia: ");
	}
	
	public void leerOtrosDatos() {
		boolean exito;
		
		setNombre(Teclado.leerString("\nNombre: "));
		
		do {
			try {
				exito=true;
				setPrecioBase(Teclado.leerInt("\nPrecio Base: "));
				setStock(Teclado.leerInt("\nStock: "));
			} catch (IllegalArgumentException e) {
				exito=false;
				System.out.println(e.getMessage());
				
			}
		} while (!exito);
		
		do {
			try {
				exito=true;
				setStock(Teclado.leerInt("\nStock: "));
			} catch (IllegalArgumentException e) {
				exito=false;
				System.out.println(e.getMessage());
				
			}
		} while (!exito);
	}
	
	public void escribirFichero(DataOutputStream filtro) throws IOException {
		try {
			filtro.writeUTF(numRef);
			filtro.writeUTF(nombre);
			filtro.writeFloat(precioBase);
			filtro.writeInt(stock);
		} catch (IOException e) {
			System.out.println(e.getStackTrace());
		}
	}
	
	public void leerFichero(DataInputStream filtro) throws IOException{
		try {
			numRef=filtro.readUTF();
			nombre=filtro.readUTF();
			try {
				setPrecioBase(filtro.readFloat());
				setStock(filtro.readInt());
			} catch (IOException | IllegalArgumentException e) {
				System.out.println(e.getMessage());
			}
		} catch (IOException e) {
			e.getStackTrace();
		}
	}
	
	public float getPrecioFinal() {
		return precioBase+calcularComplemento();
	}
	
	public abstract float calcularComplemento();
	
}
