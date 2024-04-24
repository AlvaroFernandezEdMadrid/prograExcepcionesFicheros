package ejerciciocompleto;

import java.util.function.Predicate;

public class FiltroAfecto implements Predicate<Persona> {
	
	private float afecto;
	
	

	public FiltroAfecto(float afecto) {
		this.afecto = afecto;
	}


	public float getafecto() {
		return afecto;
	}

	public void setafecto(float afecto) {
		this.afecto = afecto;
	}

	@Override
	public boolean test(Persona p) {
		// TODO Auto-generated method stub
		return p.getAfecto() < afecto;
	}

}
