package problemsolver.probleme;

import java.util.ArrayList;

import problemsolver.donnees.Donnees;
import problemsolver.donnees.solutions.Circuit_TourReference;
import problemsolver.donnees.solutions.TourReference;

public class Echantillon extends ArrayList<Donnees>{
	private TourReference tourReference;

	public TourReference getResultat() {
		return tourReference;
	}

	public void setResultat(TourReference a_tourRef) {
		this.tourReference = a_tourRef;
	}
	
	public TourReference getTr() {
		return tourReference;
	}
	
	public void setTr(TourReference a_tourRef) {
		this.tourReference = a_tourRef;
	}

	
}
