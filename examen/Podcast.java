package examen;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Podcast extends Material {
	private static final String SEPARADOR=";"; 

	private int numCapitulo;
	private List<String> idiomas;

	public Podcast(String referencia, String nombre, LocalDate fechaEstreno, Categoria categoria, int numCapitulo,
			List<String> idiomas) {
		super(referencia, nombre, fechaEstreno, categoria);
		this.numCapitulo = numCapitulo;
		this.idiomas = idiomas;
	}

	public Podcast(String referencia) {
		this(referencia, "", LocalDate.now(), Categoria.CLASICOS80, 1, new ArrayList<String>());
	}

	public Podcast() {
		this("");
	}

	public Podcast(Podcast og) {
		super(og.getReferencia(), og.getNombre(), og.getFechaEstreno(), og.getCategoria());
		setNumCapitulo(og.numCapitulo);
		setIdiomas(og.idiomas);
	}

	public int getNumCapitulo() {
		return numCapitulo;
	}

	public void setNumCapitulo(int numCapitulo) throws IllegalArgumentException{
		if (numCapitulo<0) {
			throw new IllegalArgumentException();
		}
		this.numCapitulo = numCapitulo;
	}

	public List<String> getIdiomas() {
		return idiomas;
	}

	public void setIdiomas(List<String> idiomas){
		if (idiomas==null) {
			idiomas=new ArrayList<String>();
		}
		this.idiomas = idiomas;
	}

	@Override
	public String toString() {
		return "Podcast [numCapitulo=" + numCapitulo + ", idiomas=" + idiomas + ", toString()=" + super.toString()
		+ "]";
	}

	@Override
	public void fromFile(DataInputStream filtro) throws IOException{
		int cuantos;

		super.fromFile(filtro);

		try {
			setNumCapitulo(filtro.readInt());
		} catch (IllegalArgumentException e) {
			System.err.println(e.getMessage());
		}

		cuantos=filtro.readInt();

		for (int i = 0; i < cuantos; i++) {
			try {
				idiomas.add(filtro.readUTF());
			} catch (Exception e) {
				System.out.println(e.getStackTrace());
			}
		}
	}

	@Override
	public void toFile(DataOutputStream filtro) throws IOException{

		super.toFile(filtro);

		filtro.writeUTF("podcast");
		filtro.writeUTF(SEPARADOR);
		filtro.writeInt(numCapitulo);
		filtro.writeInt(idiomas.size());
		for (String s : idiomas) {
			filtro.writeUTF(s);
		}
	}

	@Override
	public void fromCsv(String csv) {
		String valores[]=csv.split(SEPARADOR);
		int cuantos;
		
		setReferencia(valores[0]);
		setNombre(valores[1]);
		setFechaEstreno(LocalDate.parse(valores[2]));
		setCategoria(Categoria.valueOf(valores[3]));
		setNumCapitulo(Integer.valueOf(valores[4]));
		cuantos=Integer.valueOf(valores[5]);
		
		for (int i = 6; i < cuantos; i++) {
			try {
				idiomas.add(valores[i]);
			} catch (Exception e) {
				System.err.println(e.getStackTrace());
			}
		}
	}

}
