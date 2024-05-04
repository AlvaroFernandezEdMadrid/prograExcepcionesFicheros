package practicafinal;

import java.util.Map;
import java.util.TreeMap;

import Libreria.Libreria;
import daw.com.Teclado;

public class Almacen {
	private Map<String, Producto> almacen;

	public Almacen(Map<String, Producto> almacen) {
		if (almacen==null) {
			almacen=new TreeMap<String, Producto>();
		}
		this.almacen = almacen;
	}
	
	public Almacen() {
		this(null);
	}

	public Map<String, Producto> getAlmacen() {
		return almacen;
	}

	public void setAlmacen(Map<String, Producto> almacen) {
		this.almacen = almacen;
	}
	
	public boolean addProducto() {
		Producto p;
		String tipo;
		boolean exito=false;
		
		do {
			
			do {
				tipo=Teclado.leerString("\nPerecedero(1) o No Perecedero(2): ");
			} while (!(tipo.equalsIgnoreCase("1")|tipo.equalsIgnoreCase("2")));
			if (tipo.equalsIgnoreCase("1")) {
				p=new Perecedero();
			}else
				p=new NoPerecedero();
			
			do {
				p.leerClave();
			} while (almacen.containsKey(p.getNumRef()));
			
			p.leerOtrosDatos();
			
			try {
				almacen.put(p.getNumRef(), p);
				exito=true;
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		} while (Teclado.leerString("\nSigo s/n ?").equalsIgnoreCase("s"));
		
		return exito;
	}
	
	public boolean modStock() {
		String id;
		boolean exito;
		
		do {
			id=Teclado.leerString("\nReferencia del producto: ");
		} while (!almacen.containsKey(id));
		
		try {
			almacen.get(id).setStock(almacen.get(id).getStock()+Libreria.leerEnteroPositivo("\nCantidad a annadir al stock: "));
			exito=true;
		} catch (Exception e) {
			exito=false;
			System.out.println(e.getStackTrace());
		}
		
		return exito;
	}
}
