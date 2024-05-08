package deportistas;

import java.time.Duration;

import daw.com.Teclado;


public class Tiempo {
	
	public static String tiempoToString (Duration tiempo)
	{
		String tiempoString = "No hay datos";
		int valor;
		
		
		
		if (tiempo != null)
		{
			
			tiempoString="";
			valor = (int) tiempo.toDays();
			
			if (valor > 0)
			{
				tiempoString += valor + "d " ;
				tiempo = tiempo.minusDays(valor);
			}
			
			valor = (int) tiempo.toHours();
			if (valor > 0)
			{
				tiempoString += valor + "h " ;
				tiempo = tiempo.minusHours(valor);
			}
			
			valor = (int) tiempo.toMinutes();
			
			if (valor > 0)
			{
				tiempoString += valor + "m " ;
				tiempo = tiempo.minusMinutes(valor);
			}
			
			valor = (int) tiempo.getSeconds();
			if (valor > 0)
				tiempoString += valor + "s" ;
			
		}
		
		
		return tiempoString;
	}
	
	public static Duration leerDuration () {
		
		Duration tiempo ;
		
		int valor;
		
		do
		{
			valor = Teclado.leerInt("\nhoras");
		}while (valor < 0);
		
		tiempo = Duration.ofHours(valor);
		
		valor = Libreria.leerEntreLimites(0, 59, "minutos");
		
		tiempo = tiempo.plusMinutes(valor);
		
		
		valor = Libreria.leerEntreLimites(0, 59, "segundos");
		
		tiempo = tiempo.plusSeconds(valor);
		
		return tiempo;
	}

}
