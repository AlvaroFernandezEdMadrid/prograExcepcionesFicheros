package examen;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class PlataformaStreaming {
	private static final String SEPARADOR=":";

	private Map<String, Cliente> clientes;
	private Map<String, Material> materiales;
	private List<Escucha> escuchas;

	public PlataformaStreaming(Map<String, Cliente> clientes, Map<String, Material> materiales,
			List<Escucha> escuchas) {
		setClientes(clientes);
		setMateriales(materiales);
		setEscuchas(escuchas);
	}

	public PlataformaStreaming() {
		this(null, null, null);
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

	public Map<String, Material> getMateriales() {
		return materiales;
	}

	public void setMateriales(Map<String, Material> materiales) {
		if (materiales==null) {
			materiales=new TreeMap<String, Material>();
		}
		this.materiales = materiales;
	}

	public List<Escucha> getEscuchas() {
		return escuchas;
	}

	public void setEscuchas(List<Escucha> escuchas) {
		if (escuchas==null) {
			escuchas=new ArrayList<Escucha>();
		}
		this.escuchas = escuchas;
	}

	public String escuchasToCsv() {
		String linea="";

		for (Escucha e : escuchas) {
			linea+=e.getMaterial().getReferencia();
			linea+=SEPARADOR;

			linea+=e.getCliente().getNif();
			linea+=SEPARADOR;

			linea+=e.getFechaHora().toString();
			linea+="\n";
		}

		return linea;
	}

	public void escuchaFromCsv(String csv) {
		String valores[]=csv.split(csv);

		Escucha es=new Escucha(materiales.get(valores[0]),clientes.get(valores[1]),LocalDateTime.parse(valores[2]));

		try {
			escuchas.add(es);
		} catch (Exception e) {
			System.err.println(e.getStackTrace());
		}

	}

}
