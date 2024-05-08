package practicafinalprofe;

import daw.com.Pantalla;
import daw.com.Teclado;

public class Cliente {
	private String nif;
	private String nombre;
	private float saldo;
	
	public Cliente ()
	{
		this ("","",0);
	}
	public Cliente(String nif, String nombre, float saldo) {
		this.nif = nif;
		this.nombre = nombre;
		this.saldo = saldo;
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

	public void setSaldo(float saldo) throws IllegalArgumentException {
		if (saldo < 0)
			throw new IllegalArgumentException("saldo no permitido");
		this.saldo = saldo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nif == null) ? 0 : nif.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cliente other = (Cliente) obj;
		if (nif == null) {
			if (other.nif != null)
				return false;
		} else if (!nif.equals(other.nif))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Cliente [nif=" + nif + ", nombre=" + nombre + ", saldo=" + saldo + "]";
	}
	
	public void leerClave ()
	{
		nif = Teclado.leerString("\nnif: ");
	}
	
	public void leerOtrosDatos ()
	{
		boolean error;
		nombre = Teclado.leerString("\nnombre: ");
		
		do
		{
			try
			{
			error = false;
			setSaldo(Teclado.leerFloat("\nsaldo:"));
			}
			catch (IllegalArgumentException e)
			{
				error = true;
				Pantalla.escribirString("\n" + e.getMessage());
			}
		}while (error);
	}
	
	public void fromCsv (String linea)
	{
		String valores[] = linea.split(":");
		if (valores.length > 2)
		{
			nif = valores[0];
			nombre = valores[1];
			try
			{
				setSaldo (Float.valueOf(valores[2]));
			}
			catch (IllegalArgumentException e)
			{
				saldo = 0;
			}
		}
	}
	
	public String toCsv ()
	{
		String linea = "";
		
		linea += nif;
		linea += ":";
		linea += nombre;
		linea += ":";
		linea += saldo;
		
		return linea;
	}
	

}
