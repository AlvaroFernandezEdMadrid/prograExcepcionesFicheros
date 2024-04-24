package tpp;

import daw.com.Teclado;

public class AppBonoTransporte {
	public static void main(String[] args) {
		BonoTransporte bono;
		int cuantos;

		bono=new BonoTransporte();

		bono.leerDatos();

		cuantos=Teclado.leerInt("Cuantos: ");


		try {
			for (int i = 0; i < cuantos; i++) {
				bono.picar();
				System.out.println("\nBono picado, saldo actual: "+bono.getSaldo());
			}
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}


		bono.recargar(Teclado.leerInt("Cuanto recargo?"));

		try {
			for (int i = 0; i < cuantos; i++) {
				bono.picar();
				System.out.println("\nBono picado, saldo actual: "+bono.getSaldo());
			}
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}

		System.out.println(bono.toString());

	}
}
