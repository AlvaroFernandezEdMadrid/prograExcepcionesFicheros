package examen;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import daw.com.Teclado;

public class Cancion extends Material {
	private static final String SEPARADOR=";"; 
	
	private String interprete;

	public Cancion(String referencia, String nombre, LocalDate fechaEstreno, Categoria categoria, String interprete) {
		super(referencia, nombre, fechaEstreno, categoria);
		this.interprete = interprete;
	}

	public Cancion(String referencia) {
		this(referencia, "", LocalDate.now(), Categoria.CLASICOS80, "");
	}

	public Cancion() {
		this("");
	}

	public Cancion(Cancion og) {
		super(og.getReferencia(), og.getNombre(), og.getFechaEstreno(), og.getCategoria());
		this.interprete = og.interprete;
	}

	public String getInterprete() {
		return interprete;
	}

	public void setInterprete(String interprete) {
		this.interprete = interprete;
	}

	@Override
	public String toString() {
		return "Cancion [interprete=" + interprete + ", toString()=" + super.toString() + "]";
	}
	
	@Override
	public void leerOtrosDatos() {
		super.leerOtrosDatos();
		setInterprete(Teclado.leerString("\nInterprete: "));
	}
	
	@Override
	public void fromFile(DataInputStream filtro) throws IOException{
		super.fromFile(filtro);
		interprete=filtro.readUTF();
	}
	
	@Override
	public void toFile(DataOutputStream filtro) throws IOException{
		super.toFile(filtro);
		filtro.writeUTF(SEPARADOR);
		filtro.writeUTF("cancion");
		filtro.writeUTF(SEPARADOR);
		filtro.writeUTF(interprete);
	}
	
	@Override
	public String toCsv() {
		String linea="";
		
		
		linea+=super.toCsv();
		linea+=SEPARADOR;
		
		linea+=interprete;
				
		return linea;
	}
	
	@Override
	public void fromCsv(String csv) {
		String valores[]=csv.split(SEPARADOR);
		
		
		setReferencia(valores[0]);
		setNombre(valores[1]);
		setFechaEstreno(LocalDate.parse(valores[2]));
		setCategoria(Categoria.valueOf(valores[3]));
		setInterprete(valores[4]);
	}
}
