package deportistas;

import java.util.function.Predicate;

public class EsCiclista implements Predicate<Deportista> {

	@Override
	public boolean test(Deportista t) {
		// TODO Auto-generated method stub
		return t instanceof Ciclista;
	}

}
