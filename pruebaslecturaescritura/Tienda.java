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

			if (!clientes.containsKey(p.getId())) {
				exito=true;
				p.leerOtrosDatos();
				productos.put(p.getId(), p);
			}else {
				exito=false;
				System.out.println("\nEl producto ya existe");
			}

		} while (!exito);

		return exito;
	}

	public boolean removeProducto() {
		boolean exito;

		if (productos.remove(Teclado.leerString("ID del Producto a eliminar: ")) != null) {
			exito=true;
			System.out.println("Producto eliminado con exito");
		}else{
			exito=false;
			System.out.println("Error eliminando producto");
		}

		return exito;
	}

	public void productosFromCsv() {
		String linea;
		Producto p;

		try (BufferedReader buffer=new BufferedReader(new FileReader ("productos.csv"))) {
			while (buffer.ready()) {
				p=new Producto();
				linea=buffer.readLine();
				p.fromCsv(linea);
				if (!productos.containsKey(p.getId())) {
					productos.put(p.getId(), p);
				}
			}
		} catch (IOException e) {
			System.out.println("\nError leyendo archivo...");
		}
	}

	public void clientesFromCsv() {
		String linea;
		Cliente c;

		try (BufferedReader buffer=new BufferedReader(new FileReader ("clientes.csv"))) {
			while (buffer.ready()) {
				c=new Cliente();
				linea=buffer.readLine();
				c.fromCsv(linea);
				if (!clientes.containsKey(c.getNif())) {
					clientes.put(c.getNif(), c);
				}
			}
		} catch (IOException e) {
			System.out.println("\nError leyendo archivo...");
		}
	}

	public boolean addCliente() {
		boolean exito;
		Cliente c;

		c=new Cliente();

		do {
			c.leerClave();

			if (!clientes.containsKey(c.getNif())) {
				exito=true;
				c.leerOtrosDatos();
				clientes.put(c.getNif(), c);
			}else {
				exito=false;
				System.out.println("\nEl cliente ya existe");
			}

		} while (!exito);

		return exito;
	}

	public boolean removeCliente() {
		boolean exito;

		if (clientes.remove(Teclado.leerString("ID del cliente a eliminar: ")) != null) {
			exito=true;
			System.out.println("Cliente eliminado con exito");
		}else {
			exito=false;
			System.out.println("Error eliminando cliente");
		}
			

		return exito;
	}

	

}
