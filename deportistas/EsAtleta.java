package deportistas;

import java.util.function.Predicate;

public class EsAtleta implements Predicate<Deportista> {

	@Override
	public boolean test(Deportista t) {
		// TODO Auto-generated method stub
		return t instanceof Atleta;
	}

	// (Deportista t) -> t instanceof Atleta 
}
