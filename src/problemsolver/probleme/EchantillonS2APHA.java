package problemsolver.probleme;

import java.util.ArrayList;

import problemsolver.donnees.Donnees;
import problemsolver.donnees.solutions.TourReference;

public class EchantillonS2APHA extends ArrayList<DonneesScenario<Donnees, Donnees, Penalites<? extends TourReference<?,?>,? extends Donnees>>> {
	private TourReference tourReference;
	
	public TourReference getTr() {
		return tourReference;
	}
	
	public void setTr(TourReference a_tourRef) {
		this.tourReference = a_tourRef;
	}

}
