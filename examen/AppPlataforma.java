package examen;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.function.Predicate;

import daw.com.Teclado;

public class AppPlataforma extends AppConMenu {
	private PlataformaStreaming app;

	public AppPlataforma() 
	{
		super();	
		app=new PlataformaStreaming();
		addOpcion("Insertar nuevo material");
		addOpcion("Dar alta nuevo cliente");
		addOpcion("Realizar una escucha");
		addOpcion("Annadir catalan a todo");
	}
	
	public static void main(String[] args) {
		AppPlataforma app=new AppPlataforma();
		
		app.cargarDatos();
		app.run();
		app.salir();

	}

	@Override
	public void evaluarOpcion(int opc) {
		switch (opc) {
		case 1:
			insertarMaterial();
			break;
		case 2:
			altaCliente();
			break;
		case 3:
			realizarEscucha();
			break;
		case 4:
			anadirCatalan();
			break;
		}

	}

	private void insertarMaterial() {
		String id, tipo;
		Material m;
		boolean exito;

		try {
			do {
				id=Teclado.leerString("\nReferencia del material: ");

				if (!app.getMateriales().containsKey(id)) {
					exito=true;

					tipo=Teclado.leerString("\nTipo (Cancion | Podcast");

					if (tipo.equalsIgnoreCase("cancion")) {
						m=new Cancion(id);
					}else {
						m=new Podcast(id);
					}

					m.leerOtrosDatos();

					app.getMateriales().put(id, m);
				}else {
					exito=false;
					app.getMateriales().keySet().forEach(System.out::println);
				}
			} while (!exito);
		} catch (Exception e) {
			System.err.println("\nCliente ya existente");
		}

	}

	private void altaCliente() {
		String id;
		Cliente c;
		boolean exito;


		do {
			id=Teclado.leerString("\nNif del cliente: ");
			if (!app.getClientes().containsKey(id)) {
				exito=true;
				c=new Cliente(id);
				c.leerOtrosDatos();
			}else {
				exito=false;
				System.err.println("\nCliente ya existente");
			}
		} while (!exito);

	}

	private void realizarEscucha() {
		String nif;
		boolean exito;
		Escucha e;
		List<Material> mat;
		String cual;

		mat=new ArrayList<Material>(app.getMateriales().values());

		mat.sort(new Comparator<Material>() {

			@Override
			public int compare(Material o1, Material o2) {

				return o1.getNombre().compareTo(o2.getNombre());
			}
		});

		do {
			nif=Teclado.leerString("\nNif: ");

			if (app.getClientes().containsKey(nif)) {
				exito=true;
				for (Material m : app.getMateriales().values()) {
					if (m.getFechaEstreno().isBefore(LocalDate.now())|m.getFechaEstreno().isEqual(LocalDate.now())) {
						mat.forEach(System.out::println);
						cual=Teclado.leerString("Cual: ");
						if (app.getMateriales().containsKey(cual)) {
							app.getEscuchas().add(new Escucha(app.getMateriales().get(cual),app.getClientes().get(nif),LocalDateTime.now()));
							exito=true;
						}
					}
				}
			}else {
				exito=false;
			}

		} while (!exito);
	}

	private void anadirCatalan() {
		ArrayList<Material> podcasts;

		podcasts=new ArrayList<Material>(app.getMateriales().values());

		podcasts.removeIf(new Predicate<Material>() {

			@Override
			public boolean test(Material t) {

				return t instanceof Podcast;
			}
		}.negate());


		for (Material m : podcasts) {
			if (((Podcast)m).getIdiomas().contains("catalan")) {
				((Podcast)m).getIdiomas().add("catalan");
			}
		}
	}

	

	private void salir() {
		guardarClientes();
		guardarMateriales();
		guardarEscuchas();
	}

	private void guardarClientes() {
		try (PrintWriter filtro=new PrintWriter("clientes.csv")){
			app.getClientes().values().forEach(c->filtro.println(c.toCsv()));;
		} catch (FileNotFoundException e) {
			System.out.println("Erro escribiendo");
		}
	}

	private void guardarMateriales() {
		try (DataOutputStream filtro=new DataOutputStream(new FileOutputStream("materiales.dat"))){
			filtro.writeInt(app.getMateriales().size());
			for (Material m : app.getMateriales().values()) {
				m.toFile(filtro);
			}
			System.err.println("Exito guardando...");
		} catch (Exception e) {
			System.out.println("Error guardando");
		}
	}

	private void guardarEscuchas() {
		
	}

	private void cargarDatos() {
		cargarClientes();
		cargarMateriales();
		cargarEscuchas();
	}

	private void cargarClientes() {
		Cliente c;

		String linea;

		try (BufferedReader filtro=new BufferedReader(new FileReader("clientes.csv"))){
			while (filtro.ready()) {
				linea=filtro.readLine();

				c=new Cliente();

				c.fromCsv(linea);
				app.getClientes().put(c.getNif(), c);
			}
		} catch (IOException e) {
			System.out.println("\nError leyendo archivo");
		}
	}

	private void cargarMateriales() {
		Material m;
		
		try (DataInputStream filtro=new DataInputStream(new FileInputStream("materiales.dat"))){
			
		}catch (Exception e) {
			// TODO: handle exception
		}
	}

	private void cargarEscuchas() {
		Escucha e;

		String linea;

		try (BufferedReader filtro=new BufferedReader(new FileReader("escuchas.csv"))){
			while (filtro.ready()) {
				linea=filtro.readLine();

				e=new Escucha();

				app.escuchaFromCsv(linea);
				app.getEscuchas().add(e);
			}
		} catch (IOException e1) {
			System.out.println("\nError leyendo archivo");
		}
	}

}
