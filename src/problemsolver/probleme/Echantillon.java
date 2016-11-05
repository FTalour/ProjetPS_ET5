package problemsolver.probleme;

import java.util.ArrayList;

import problemsolver.donnees.Donnees;
import problemsolver.donnees.solutions.Circuit_TourReference;
import problemsolver.donnees.solutions.TourReference;

public class Echantillon extends ArrayList<Donnees>{
	private TourReference resultat;

	public TourReference getResultat() {
		return resultat;
	}

	public void setResultat(TourReference resultat) {
		this.resultat = resultat;
	}

	
}
