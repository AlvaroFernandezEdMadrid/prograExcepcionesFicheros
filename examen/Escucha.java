package examen;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class Escucha {
	
	private Material material;
	private Cliente cliente;
	private LocalDateTime fechaHora;
	
	public Escucha(Material material, Cliente cliente, LocalDateTime fechaHora) {
		this.material = material;
		this.cliente = cliente;
		this.fechaHora = fechaHora;
	}

	public Escucha() {
		this(null, null, null);
	}
	
	public Material getMaterial() {
		return material;
	}
	
	public void setMaterial(Material material) {
		this.material = material;
	}
	
	public Cliente getCliente() {
		return cliente;
	}
	
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	public LocalDateTime getFechaHora() {
		return fechaHora;
	}
	
	public void setFechaHora(LocalDateTime fechaHora) throws IllegalArgumentException{
		LocalDateTime l=LocalDateTime.of(material.getFechaEstreno(), LocalTime.of(0, 0)); //Chapuza, lo siento
		
		if (fechaHora.isBefore(l)) {
			throw new IllegalArgumentException();
		}
		
		this.fechaHora = fechaHora;
	}
	
	/*public void fromCsv(String csv) {
		String valores[]=csv.split(SEPARADOR);
		
		setMaterial(material);
		
		setMaterial(material.fromCsv(valores[0]));
	}*/
	
	/*public String toCsv() {
		String linea="";
		
		linea+=material.toCsv();
		linea+=SEPARADOR;
		
		linea+=cliente.toCsv();
		linea+=SEPARADOR;
		
		linea+=fechaHora.toString();
		
		return linea;
	}*/
	
	
}
