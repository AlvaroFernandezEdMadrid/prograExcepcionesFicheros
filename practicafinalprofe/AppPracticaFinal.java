package practicafinalprofe;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Consumer;
import java.util.function.Predicate;

import daw.com.Pantalla;
import daw.com.Teclado;


public class AppPracticaFinal extends AppConMenu {

	private Map<String, Producto> productos;
	private Map<String, Cliente> clientes;
	private List<Cesta> cestas;

	
	
	public AppPracticaFinal() {
		super();
		addOpcion("Añadir producto");
		addOpcion("Modificar stock");
		addOpcion("Servir pedido");
		addOpcion("Mostrar lista precios");
		addOpcion("Eliminar caducados");
		
		productos = new HashMap<>();
		clientes = new HashMap<>();
		cestas = new ArrayList<>();
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		AppPracticaFinal app = new AppPracticaFinal();
		app.leerDatos ();
		app.run();
		app.guardarDatos();

	}
	
	private void guardarDatos() {
		// TODO Auto-generated method stub
		guardarProductos();
		guardarClientes();
		guardarCestas ();
		
	}
	
	private void guardarCestas() {
		// TODO Auto-generated method stub
		try (PrintWriter filtro = new PrintWriter("cestas.csv"))
		{
			cestasToCsv().forEach(filtro::println);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			Pantalla.escribirString("\nerror escribiendo");
		}
		
	}

	private void guardarProductos ()
	{
		try (DataOutputStream filtro = new DataOutputStream (new FileOutputStream("productos.dat")))
		{
			filtro.writeInt(productos.size());
			
			for (Producto p : productos.values())
			{
				//filtro.writeUTF(p.getClass().getName());
				if (p instanceof Perecedero)
					filtro.writeUTF("Perecedero");
				else
					filtro.writeUTF("NoPerecedero");
				
				p.escribirFichero(filtro);
			}
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Pantalla.escribirString("\nerror guardando datos");
		}
		
	}

	private void guardarClientes () 
	{
		try (PrintWriter filtro = new PrintWriter ("clientes.csv"))
		{
			clientes.values().
					forEach(c -> filtro.println(c.toCsv()));
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			Pantalla.escribirString("\nError escribiendo fichero..");
		}
		
	}
	private void leerDatos() {
		leerProductos ();
		leerClientes ();
		leerCestas ();
		
	}
	
	private void leerCestas() {
		// TODO Auto-generated method stub
		String linea;
		Cesta cesta;
		try (BufferedReader filtro = new BufferedReader (new FileReader("cestas.csv")))
		{
			while (filtro.ready())
			{
				linea = filtro.readLine();
				cesta = leerCesta (linea);
				cestas.add(cesta);
			}
			
		}  catch (IOException e) {
			// TODO Auto-generated catch block
			Pantalla.escribirString("\nerror leyendo fichero");
		}
		
	}

	private Cesta leerCesta(String linea) {
		// TODO Auto-generated method stub
		
		int cuantos;
		Producto p;
		int cantidad;
		Cesta cesta;
		String valores[] = linea.split(":");
		
		cesta = new Cesta (clientes.get(valores[0]));
		
		cuantos = Integer.valueOf(valores[1]);
		
		for (int i = 0; i < cuantos; i++)
		{
			p = productos.get(valores[i+2]);
			cantidad = Integer.valueOf(valores[i+3]);
			cesta.insertarProducto(p, cantidad);
		}
		
		return cesta;
	}

	private void leerProductos ()
	{
		// TODO Auto-generated method stub
		String tipoProducto;
		Producto p;
		try(DataInputStream filtro = new DataInputStream (new FileInputStream("productos.dat")))
		{
			int cuantos = filtro.readInt();
			
			for (int i = 0; i < cuantos; i++)
			{
				try
				{
					tipoProducto = filtro.readUTF();
					p = FactoriaProducto.build(tipoProducto);
		
					p.leerFichero(filtro);
				
					productos.put(p.getReferencia(), p);
				}
				catch (IllegalArgumentException e)
				{
					Pantalla.escribirString("\nTipo de producto sin identificar en lectura de fichero");
				}
			}
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Pantalla.escribirString("\nNo hay datos guardados");
		}
		
	}
	
	private void leerClientes ()
	{
		Cliente cliente;
		String linea;
		
		try(BufferedReader filtro = new BufferedReader (new FileReader("clientes.csv")))
		{
			while (filtro.ready())
			{
				linea = filtro.readLine();
				cliente = new Cliente ();
				cliente.fromCsv(linea);
				clientes.put(cliente.getNif(), cliente);
			}
			
		}  catch (IOException e) {
			// TODO Auto-generated catch block
			Pantalla.escribirString("\nNo se ha podido leer fichero");
		}
		
	}

	@Override
	public void evaluarOpcion(int arg0) {
		// TODO Auto-generated method stub
		
		switch (arg0)
		{
			case 1:
				insertar();
				break;
			case 2:
				modificarStock();
				break;
			case 3:
				hacerPedido();
				break;
			case 4:
				mostrarPrecios();
				break;
			case 5:
				eliminarCaducados();
				break;
			case 6:
				salir();
				break;
						
		}

	}

	private void insertar() {
		// TODO Auto-generated method stub
		String referencia, tipoProducto;
		Producto p;
		
		referencia = Teclado.leerString("\nreferencia ");
		if (!productos.containsKey(referencia))
		{
			try
			{
				tipoProducto = Teclado.leerString("\ntipo producto ");
				p = FactoriaProducto.build(tipoProducto);
				
				p.setReferencia(referencia);
				p.leerOtrosDatos();
				productos.put(p.getReferencia(), p);
			}
			catch (IllegalArgumentException e)
			{
				Pantalla.escribirString("\n" + e.getMessage());
			}
			
		}
		else
			Pantalla.escribirString("\nProducto ya existente");
	}

	private void modificarStock() {
		// TODO Auto-generated method stub
		String referencia;
		Producto p;
		int stock;
		
		referencia = Teclado.leerString("\nreferencia ");
		p = productos.get(referencia);
		if (p != null)
		{
			stock = Libreria.leerEnteroPositivo("\ncantidad a añadir ");
			stock += p.getStock();
			p.setStock(stock);
		}
		else
			Pantalla.escribirString("\nProducto no existente");
	}

	private void hacerPedido() {
		// TODO Auto-generated method stub
		String referencia;
		Cesta cesta;
		int unidades,stock;
		Producto p;
		String nif;
		Cliente cliente;
		
		nif = Teclado.leerString("\nnif del cliente ");
		cliente = clientes.get(nif);
		
		if (cliente != null)
		{
			cesta = new Cesta(cliente);
		
			do
			{
				referencia = Teclado.leerString("\nreferencia ");
				p = productos.get(referencia);
				if (p != null)
				{
					unidades = Libreria.leerEnteroPositivo("\nunidades ");
					stock = p.getStock();

					if (stock >= unidades && 
							cliente.getSaldo() > cesta.getTotal() + (unidades * p.getPrecioVenta()))
					{
						cesta.insertarProducto(p, unidades);
						// Actualizar stock
						p.setStock(p.getStock() - unidades);
					}
					else
						Pantalla.escribirString("\nProducto no añadido al pedido por falta de stock o de saldo");

				}
				else
					Pantalla.escribirString("\nNo existe producto con esa referencia");

			}while (Teclado.leerString("\nseguir?(s/n)").equals("s"));


			for (Entry<Producto,Integer> e : cesta.getCesta().entrySet())
			{
				Pantalla.escribirString("\n" + e.getKey().getNombre() + "," 
						+ e.getValue());
			}
			Pantalla.escribirString("\nTotal pedido :" + cesta.getTotal());
			cliente.setSaldo(cliente.getSaldo() - cesta.getTotal());
			Pantalla.escribirString("\nEl saldo actual del cliente es " + cliente.getSaldo());
			// Añadir la cesta al conjunto de todas las cestas
			cestas.add(cesta);
		}
		else
			Pantalla.escribirString("\nNo existe el cliente");
		
	}

	private void mostrarPrecios() {
		// TODO Auto-generated method stub
		Comparator <Producto> comparador;
		Consumer<Producto> consumidor;
		List<Producto> lista;
		
		/*
		comparador = new Comparator<Producto>() {

			@Override
			public int compare(Producto o1, Producto o2) {
				// TODO Auto-generated method stub
				return Float.compare(o1.getPrecioVenta(), o2.getPrecioVenta());
			}
		};
		*/
		comparador = Comparator.comparing(Producto::getPrecioVenta);
		
		/*
		consumidor = new Consumer<Producto>() {

			@Override
			public void accept(Producto t) {
				// TODO Auto-generated method stub
				Pantalla.escribirString("\n" + t.getNombre() + "," 
								+ t.getReferencia() + "," 
								+ t.getPrecioVenta());
			}

		};
		*/
		consumidor = t -> Pantalla.escribirString("\n" + t.getNombre() + "," 
				+ t.getReferencia() + "," 
				+ t.getPrecioVenta());
		
		lista = new ArrayList<> (productos.values());
		
		lista.sort(comparador);
		lista.forEach(consumidor);
	}

	private void eliminarCaducados() {
		// TODO Auto-generated method stub
		Predicate <Producto> predicado;
		List<Producto> lista;
		float total = 0, subtotal;
		
		lista = new ArrayList<> (productos.values());
		
		predicado = new Predicate<Producto> () {
			@Override
			public boolean test(Producto t) {
				return t instanceof Perecedero &&
						((Perecedero)t).estaCaducado();
			}
		};		
		
		lista.removeIf(predicado.negate());
		
		for (Producto p: lista)
		{
			subtotal = p.getPrecio() * p.getStock();
			Pantalla.escribirString("\n" + p.getReferencia() + "," 
					+ p.getNombre() + "," 
					+ subtotal);
			
			// eliminar del map de productos
			productos.remove(p.getReferencia());
			
			// acumular
			total+= subtotal;
		}
		
		Pantalla.escribirString("\nTotal dinero por productos caducados " + total);
		
	}

	private void salir() {
		// TODO Auto-generated method stub
		Pantalla.escribirString("\nChao Bambino");
	}
	

	public List<String> cestasToCsv() {

		List<String> lineas = new ArrayList<String>();

		for (Cesta c : cestas) 
		{

			String linea = "";
			linea += c.getCliente().getNif();
			linea += ":";
			linea += c.getCesta().values().size();

			for (Entry<Producto, Integer> e : c.getCesta().entrySet()) 
			{
				linea += ":";
				// producto
				linea += e.getKey().getReferencia();
				linea += ":";
				// cantidad
				linea += e.getValue().toString();
			}

			lineas.add(linea);
		}

		return lineas;

	}



}
