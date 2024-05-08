package practicafinalprofe;

public class FactoriaProducto {
	public static Producto build (String tipo) throws IllegalArgumentException
	{
		Producto p;
		
		tipo.toLowerCase();
		
		switch (tipo)
		{
			case "perecedero" :
				p = new Perecedero ();
				break;
			case "noperecedero":
				p = new NoPerecedero ();
				break;
			default:
				throw new IllegalArgumentException("tipo de producto no permitido");
		}
				
		return p;
	}
	

	public static Producto build2 (String tipo) throws IllegalArgumentException
	{
		Producto p;
		

			try {
				p = (Producto) Class.forName(tipo).
							getDeclaredConstructor().
							newInstance();
				
			} 
			catch (Exception e) {
				// TODO Auto-generated catch block
				throw new IllegalArgumentException("tipo de producto no permitido");
			}

	
				
		return p;
	}

}
