package practicafinal;

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


	public void setProcedencia(String procedencia) {
		this.procedencia = procedencia;
	}


	@Override
	public float calcularComplemento() {
		float complemento=0;
		
		
		
		return complemento;
	}

}
