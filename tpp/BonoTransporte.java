package tpp;

import daw.com.Teclado;

public class BonoTransporte {
	/*
	 * +nombrePersona
	 * +saldo
	 * 
	 * picar()
	 * recargarSaldo(cuanto)
	 * 
	 * leerDatos()
	 * 
	 * App que crea un bonoTransporte, lee datos y pregunta cuantos van y pica.
	 * Recarga el bono.
	 * Vuelve a picar.
	 * Muestra el bono.
	 * */

	private String titular;
	private int saldo;

	public BonoTransporte(String titular, int saldo) {
		this.titular = titular;
		setSaldo(saldo);
	}

	public BonoTransporte(String titular) {
		this(titular, 0);
	}

	public BonoTransporte() {
		this("");
	}

	public String getTitular() {
		return titular;
	}

	public void setTitular(String titular) {
		this.titular = titular;
	}

	public int getSaldo() {
		return saldo;
	}

	public void setSaldo(int saldo) throws IllegalArgumentException{
		if (saldo<0) {
			throw new IllegalArgumentException("Saldo menor de 0");
		}
		this.saldo = saldo;
	}

	public void picar() throws IllegalArgumentException{
		if (saldo<=0) {
			throw new IllegalArgumentException("No hay suficiente saldo.");
		}
		saldo--;

	}

	public void recargar(int cuanto)  throws IllegalArgumentException{

		if (cuanto<0) {
			throw new IllegalArgumentException("No se pueden recargar valores negativos.");
		}

		saldo+=cuanto;

	}

	public void leerDatos() {
		setTitular(Teclado.leerString("Nombre titular: "));

		do {
			try {
				setSaldo(Teclado.leerInt("Saldo: "));
			} catch (IllegalArgumentException e) {
				System.out.println("error, valor no permitido.");
			}
		} while (saldo<0);
	}

	@Override
	public String toString() {
		return "BonoTransporte [titular=" + titular + ", saldo=" + saldo + "]";
	}


}
