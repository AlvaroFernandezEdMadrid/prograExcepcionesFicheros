package pruebaslecturaescritura;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

import daw.com.Teclado;

public class Tienda {
	private String nombre;
	private Map<String, Cliente> clientes;
	private Map<String, Producto> productos;

	public Tienda(String nombre, Map<String, Cliente> clientes, Map<String, Producto> productos) {
		this.nombre = nombre;
		setClientes(clientes);
		setProductos(productos);
	}

	public Tienda(String nombre) {
		this(nombre, new TreeMap<String, Cliente>(), new TreeMap<String, Producto>());
	}

	public Tienda() {
		this("");
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public Map<String, Cliente> getClientes() {
		return clientes;
	}
	
	public void setClientes(Map<String, Cliente> clientes) {
		if (clientes==null) {
			clientes=new TreeMap<String, Cliente>();
		}
		this.clientes = clientes;
	}
	
	public Map<String, Producto> getProductos() {
		return productos;
	}
	
	public void setProductos(Map<String, Producto> productos) {
		if (productos==null) {
			productos=new TreeMap<String, Producto>();
		}
		this.productos = productos;
	}


	public boolean addProducto() {
		boolean exito;
		Producto p;
		
		p=new Producto();
		
		do {
			p.leerClave();
			
			if (!productos.containsKey(p.getId())) {
				exito=true;
				productos.put(nombre, p);
			}else
				exito=false;
				System.out.println("\nEl producto ya existe");
		} while (!exito);
		
		return exito;
	}
	
	public boolean removeProducto() {
		boolean exito;
		String cual;
		
		cual=Teclado.leerString("\nId del producto a eliminar: ");
		
		do {
			try {
				exito=true;
				productos.remove(cual);
			} catch (Exception e) {
				exito=false;
				System.err.println("\nError eliminando...");
			}
		} while (!exito);
		
		return exito;
	}
	
	public void productosFromCsv() {
		String linea;
		Producto p;
		
		try (BufferedReader buffer=new BufferedReader(new FileReader ("productos.csv"))) {
			while (buffer.ready()) {
				
			}
		} catch (IOException e) {
			// TODO: handle exception
		}
	}

}
