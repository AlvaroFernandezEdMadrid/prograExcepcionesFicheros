package practicafinalprofe;

public class Cliente {
	private final String SEPARADOR=",";
	
	private String nif;
	private String nombre;
	private float saldo;

	public Cliente(String nif, String nombre, float saldo) {
		this.nif = nif;
		this.nombre = nombre;
		this.saldo = saldo;
	}

	public Cliente(String nif) {
		this(nif, "", 0);
	}

	public Cliente() {
		this("");
	}

	public String getNif() {
		return nif;
	}
	public void setNif(String nif) {
		this.nif = nif;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public float getSaldo() {
		return saldo;
	}
	public void setSaldo(float saldo) throws IllegalArgumentException{
		if (saldo<0) {
			throw new IllegalArgumentException();
		}
		this.saldo = saldo;
	}

	public String toCsv ()
	{
		String csv = "";
		
		csv += nif;
		csv += SEPARADOR;
		csv += nombre;
		csv += SEPARADOR;
		csv += saldo;
		
		return csv;
	}
	
	public void fromCSV(String csv) {
		String valores[] = csv.split(SEPARADOR);
		
		try
		{
			nif = valores[0];
			nombre = valores[1];
			setSaldo(Float.valueOf(valores[2]));
		}
		catch (NumberFormatException e) {
			setSaldo(0);
		}
	}

}
