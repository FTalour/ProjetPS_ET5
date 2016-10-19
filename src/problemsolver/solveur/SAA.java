package problemsolver.solveur;

import java.util.ArrayList;

import problemsolver.donnees.Donnees;
import problemsolver.exceptions.ErreurDonneesException;
import problemsolver.probleme.Probleme_Stochastique;

public class SAA extends Solveur<Probleme_Stochastique> {
	int nombreEchantillons;
	ArrayList<Echantillon> listeEchantillon;
	Echantillon EchRef;
	Solveur solveurSecondaire;
	
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
