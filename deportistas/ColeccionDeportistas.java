package deportistas;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.function.Consumer;

import daw.com.Pantalla;

public class ColeccionDeportistas  {

	
	private Map <String, Deportista> deportistas;
	
	
	public ColeccionDeportistas ()
	{
		deportistas = new TreeMap <>();
	}
	
	public Collection <Deportista> dameTodos ()
	{
		return deportistas.values();
	}
	public Iterator<Deportista> findAll ()
	{
		return deportistas.values().iterator();
	}
	
	public Deportista findByKey (String dni)
	{
		return deportistas.get(dni);
	}
	
	public boolean insert (Deportista deportista)
	{
		boolean exito = true;
		
		if (!deportistas.containsKey(deportista.getDni()))
			deportistas.put(deportista.getDni(), deportista);
		else
			exito = false;
		
		return exito;
	}
	
	public boolean delete (String dni)
	{

		return deportistas.remove(dni) != null;
	}
	
	public boolean update (Deportista deportista)
	{
		
		return deportistas.replace(deportista.getDni(), deportista) != null;
	}
	
	public void listar ()
	{
		Consumer<Entry<String,Deportista>> escribidor = new Consumer<>() {

			@Override
			public void accept(Entry<String, Deportista> t) {
				// TODO Auto-generated method stub
				t.getValue().mostrarDatos();
			}

		
		};
		
		deportistas.entrySet().forEach(escribidor);
	}
	
}


