package practicafinalprofe;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;


public class Cesta {
	private Map<Producto,Integer> cesta;
	private Cliente cliente;
	
	public Cesta ()
	{
		this (new Cliente());
	}
	
	public Cesta (Cliente cliente)
	{
		this.cliente = cliente;
		cesta = new HashMap <>();
	}

	public Map<Producto, Integer> getCesta() {
		return cesta;
	}

	public void setCesta(Map<Producto, Integer> cesta) {
		this.cesta = cesta;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	@Override
	public String toString() {
		return "Cesta [cesta=" + cesta + ", cliente=" + cliente + "]";
	}

	public float getTotal ()
	{
		float total = 0;
		
		for (Entry<Producto,Integer> e : cesta.entrySet())
			total += (e.getKey().getPrecioVenta() * e.getValue());
		
		return total;
	}
	
	public void insertarProducto (Producto p, int unidades)
	{
		if (cesta.containsKey(p))
			cesta.put(p, cesta.get(p)+ unidades);
		else 
			cesta.put(p, unidades);
	}
	
}
