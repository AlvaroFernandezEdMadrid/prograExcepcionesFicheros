package examen;

import java.util.ArrayList;
import java.util.List;

public enum Categoria {
	CLASICOS80, CLASICOS90, MILLENIALS;
	
	public List<String> toList(){
		List<String> valores=new ArrayList<String>();
		
		for (Categoria c : Categoria.values()) {
			valores.add(c.toString());
		}
		
		return valores;
		
	}
}
