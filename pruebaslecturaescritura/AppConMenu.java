package pruebaslecturaescritura;

import java.util.ArrayList;

import Libreria.Libreria;
import daw.com.Pantalla;

public abstract class AppConMenu {
	private ArrayList<String> opciones;
	
	public AppConMenu ()
	{
		opciones = new ArrayList<String>();
	}
	
	public AppConMenu (ArrayList<String> opciones)
	{
		this.opciones = new ArrayList<String>(opciones);
	}
	

	public ArrayList<String> getOpciones() {
		return new ArrayList<String>(opciones);
	}

	public void setOpciones(ArrayList<String> opciones) {
		this.opciones = new ArrayList<String>(opciones);
	}

	public boolean addOpcion (String opcion)
	{
		boolean exito = false;
		
		if (!opciones.contains(opcion))
		{
			opciones.add(opcion);
			exito = true;
		}
		return exito;
	}
	public void mostrarOpciones()
	{
		
		for (int i = 0; i < opciones.size(); i++)
			Pantalla.escribirString("\n" + (i+1) + ".-" +opciones.get(i));
		
		Pantalla.escribirString("\n" + (opciones.size() + 1) +".Salir" );
	}
	
	public void run ()
	{
		int opc;
		final int SALIR = opciones.size() + 1; 
		
		do
		{
			mostrarOpciones ();
			opc = Libreria.leerEntreLimites(1, SALIR, "\nElija una opción");
			evaluarOpcion(opc);
		}while (opc != SALIR);
		
	}
	
	public abstract  void evaluarOpcion (int opc);

}
