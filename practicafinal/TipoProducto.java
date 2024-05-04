package practicafinal;

import java.util.ArrayList;

public enum TipoProducto {
	COMESTIBLE, LIMPIEZA, BELLEZA;
	
	public static ArrayList<String> toList(){
		ArrayList<String> tipos;
		
		tipos=new ArrayList<String>();
		
		for (TipoProducto s : TipoProducto.values()) {
			tipos.add(s.toString());
		}
		
		return tipos;
	}
}


