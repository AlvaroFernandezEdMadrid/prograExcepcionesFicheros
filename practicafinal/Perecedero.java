package practicafinal;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeParseException;

import daw.com.Teclado;

public class Perecedero extends Producto {
	private LocalDate fechaCaducidad;
	
	public Perecedero(String numRef, String nombre, float precioBase, int stock, LocalDate fechaCaducidad) {
		super(numRef, nombre, precioBase, stock);
		setFechaCaducidad(fechaCaducidad);
	}
	
	public Perecedero(String numRef) {
		super(numRef);
		setFechaCaducidad(LocalDate.now());
	}
	
	public Perecedero() {
		this("");
	}

	public LocalDate getFechaCaducidad() {
		return fechaCaducidad;
	}

	public void setFechaCaducidad(LocalDate fechaCaducidad){
		
		this.fechaCaducidad = fechaCaducidad;
	}

	@Override
	public void leerOtrosDatos() {
		boolean exito;
		
		super.leerOtrosDatos();
		
		do {
			try {
				exito=true;
				setFechaCaducidad(LocalDate.parse(Teclado.leerString("\nFecha de caducidad aaaa-mm-dd: ")));
			} catch (IllegalArgumentException | DateTimeParseException e) {
				exito=false;
				System.out.println(e.getMessage());
			}
		} while (!exito);
	}
	
	@Override
	public void escribirFichero(DataOutputStream filtro) throws IOException{
		super.escribirFichero(filtro);
		
		try {
			filtro.writeUTF(fechaCaducidad.toString());
			filtro.close();
		} catch (IOException e) {
			System.out.println(e.getStackTrace());
		}
	}
	
	@Override
	public void leerFichero(DataInputStream filtro) throws IOException{
		super.leerFichero(filtro);
		
		try {
			setFechaCaducidad(LocalDate.parse(filtro.readUTF()));
		} catch (IOException | DateTimeParseException | IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Override
	public float calcularComplemento() {
		float complemento=0;
		Period p=Period.between(fechaCaducidad, LocalDate.now());
		
		if (p.getDays()>5) {
			complemento=getPrecioBase();
		}else if (p.getDays()>=3&&p.getDays()<=5) {
			complemento=getPrecioBase()*1.5f;
		}else if (fechaCaducidad.isBefore(LocalDate.now())) {
			complemento=getPrecioBase()-getPrecioBase(); //Esta caducado, precio a 0, no se puede vender.
		}
		return complemento;
	}

}
