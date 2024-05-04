package practicafinal;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import daw.com.Teclado;

public class NoPerecedero extends Producto {
	private String procedencia;
	private TipoProducto tipo;

	public NoPerecedero(String numRef, String nombre, float precioBase, int stock, String procedencia, TipoProducto tipo) {
		super(numRef, nombre, precioBase, stock);
		setProcedencia(procedencia);

	}



	public NoPerecedero(String numRef) {
		super(numRef);
		setProcedencia(procedencia);
	}

	public NoPerecedero() {
		this("");
	}


	public String getProcedencia() {
		return procedencia;
	}


	public void setProcedencia(String procedencia){
		this.procedencia = procedencia;
	}

	public TipoProducto getTipo() {
		return tipo;
	}

	public void setTipo(TipoProducto tipo) throws IllegalArgumentException {
		if (!TipoProducto.toList().contains(tipo.toString())) {
			throw new IllegalArgumentException("\nTipo invalido");
		}
		this.tipo = tipo;
	}

	@Override
	public void leerOtrosDatos() {
		boolean exito;
		
		super.leerOtrosDatos();
		
		setProcedencia(Teclado.leerString("\nPais de Procedencia:"));

		do {
			try {
				exito=true;
				for (String s : TipoProducto.toList()) {
					System.out.println(s);
				}
				setTipo(TipoProducto.valueOf(Teclado.leerString("\nTipo de producto: ").toUpperCase()));
			} catch (IllegalArgumentException e) {
				exito=false;
				System.out.println(e.getMessage());
			}
		} while (!exito);
	}
	
	@Override
	public void leerFichero(DataInputStream filtro) throws IOException{
		super.leerFichero(filtro);
		
		try {
			setProcedencia(filtro.readUTF());
			setTipo(TipoProducto.valueOf(filtro.readUTF().toUpperCase()));
		} catch (IOException | IllegalArgumentException e ) {
			e.printStackTrace();
		}
	}

	@Override
	public void escribirFichero(DataOutputStream filtro) throws IOException{
		super.escribirFichero(filtro);
		
		try {
			filtro.writeUTF(procedencia);
			filtro.writeUTF(tipo.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public float calcularComplemento() {
		float complemento=0;

		if (tipo.equals(TipoProducto.BELLEZA)|
				tipo.equals(TipoProducto.LIMPIEZA)&&
				procedencia.equalsIgnoreCase("españa")) {
			complemento=getPrecioBase(); //Mas el doble
		}else if (tipo.equals(TipoProducto.COMESTIBLE)) {
			complemento=getPrecioBase()*0.4f; //Mas el 40%
		}
		
		return complemento;
	}

}
