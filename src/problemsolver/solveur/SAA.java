package problemsolver.solveur;

import java.util.ArrayList;

import problemsolver.donnees.Donnees;
import problemsolver.donnees.solutions.TourReference;
import problemsolver.exceptions.ErreurDonneesException;
import problemsolver.probleme.DonneesScenario;
import problemsolver.probleme.Penalites;
import problemsolver.probleme.Probleme;
import problemsolver.probleme.Probleme_Stochastique;

public class SAA extends Solveur<Probleme_Stochastique> {
	int nombreEchantillons;
	private ArrayList<ArrayList<DonneesScenario<Donnees, Donnees, Penalites<? extends TourReference<?,?>,? extends Donnees>>>> listeEchantillon;
	private ArrayList<DonneesScenario<Donnees, Donnees, Penalites<? extends TourReference<?,?>,? extends Donnees>>> EchRef;
	Solveur<Probleme<?,?>> solveurSecondaire;
	
	@Override
	public Donnees resoudre(Donnees donnees, Donnees solution, boolean minimiser) throws ErreurDonneesException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void init() throws ErreurDonneesException {
		// TODO Auto-generated method stub
		
	}

}
