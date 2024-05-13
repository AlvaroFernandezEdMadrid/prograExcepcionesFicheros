package pruebaslecturaescritura;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import daw.com.Pantalla;

public class AppTienda extends AppConMenu{
	private Tienda tienda;

	public AppTienda() {
		super();
		tienda=new Tienda();
		addOpcion("Annadir Cliente");
		addOpcion("Annadir Producto");
		addOpcion("Quitar Cliente");
		addOpcion("Quitar Producto");
		addOpcion("Listar Clientes");
		addOpcion("Listar Productos");
	}
	
	public static void main(String[] args) {
		AppTienda app=new AppTienda();
		app.cargarDatos();
		app.run();
		app.guardarDatos();
	}


	private void cargarDatos() {
		clientesFromFile();
		productosFromFile();
	}

	private void guardarDatos() {
		clientesToFile();
		productosToFile();
	}

	@Override
	public void evaluarOpcion(int opc) {

		switch (opc) {
		case 1:
			tienda.addCliente();
			break;
		case 2:
			tienda.addProducto();
			break;
		case 3:
			tienda.removeCliente();
			break;
		case 4:
			tienda.removeProducto();
			break;
		case 5:
			listarClientes();
			break;
		case 6:
			listarProductos();
			break;
		}
	}

	public void listarProductos() {
		tienda.getProductos().entrySet().forEach(System.out::println);
		
	}

	public void listarClientes() {
		tienda.getClientes().entrySet().forEach(System.out::println);
	}
	
	public void clientesToFile() {

		try (DataOutputStream filtro=new DataOutputStream(new FileOutputStream("clientes.dat"))){
			filtro.writeInt(tienda.getClientes().size());

			for (Cliente c : tienda.getClientes().values()) {
				c.escribirFichero(filtro);
			}
			System.out.println("Exito guardando");
		} catch (IOException e) {
			System.err.println("Error escribiendo archivo");
		}	
	}
	
	public void productosToFile() {

		try (DataOutputStream filtro=new DataOutputStream(new FileOutputStream("productos.dat"))){
			filtro.writeInt(tienda.getProductos().size());

			for (Producto p : tienda.getProductos().values()) {
				p.escribirArchivo(filtro);;
			}

			System.out.println("Exito guardando");
		} catch (IOException e) {
			System.err.println("Error escribiendo archivo");
		}	
	}
	
	private void clientesFromFile() {
		int cuantos;
		Cliente c;
		
		try(DataInputStream filtro = new DataInputStream (new FileInputStream("clientes.dat"))){
			cuantos=filtro.readInt();
			
			for (int i = 0; i < cuantos; i++) {
				c=new Cliente();
				c.leerFichero(filtro);
				tienda.getClientes().put(c.getNif(), c);
			}
			
		}catch (IOException e) {
			Pantalla.escribirString("\nNo hay datos guardados");
		}
	}

	private void productosFromFile() {
		int cuantos;
		Producto p;
		
		try(DataInputStream filtro = new DataInputStream (new FileInputStream("productos.dat"))){
			cuantos=filtro.readInt();
			
			for (int i = 0; i < cuantos; i++) {
				p=new Producto();
				p.leerArchivo(filtro);
				tienda.getProductos().put(p.getId(), p);
			}
			
		}catch (IOException e) {
			Pantalla.escribirString("\nNo hay datos guardados");
		}
	}
}
