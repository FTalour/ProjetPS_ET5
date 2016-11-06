package problemsolver.solveur;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import problemsolver.donnees.Arete;
import problemsolver.donnees.Donnees;
import problemsolver.donnees.solutions.Circuit;
import problemsolver.donnees.solutions.Circuit_TourReference;
import problemsolver.donnees.solutions.TourReference;
import problemsolver.exceptions.ErreurDonneesException;
import problemsolver.probleme.DonneesScenario;
import problemsolver.probleme.Echantillon;
import problemsolver.probleme.Probleme_Stochastique;

public class SAA extends Solveur<Probleme_Stochastique> {
	int nombreEchantillons;
	ArrayList<Echantillon> listeEchantillon;
	Echantillon EchRef;
	Solveur solveurSecondaire;
	
	public SAA(int nbrS, Solveur secondS){
        nombreEchantillons = nbrS;
        solveurSecondaire = secondS;
    }
    
	@Override
	public Donnees resoudre(Donnees donnees, Donnees solution, boolean minimiser) throws ErreurDonneesException {
		solveurSecondaire.setProbleme(getProbleme());
		solveurSecondaire.setAffiche(false);
		solveurSecondaire.init();
		//Il faut changer pour ce qu'on a entree
		getProbleme().initialiserScenarios(20, 20, 5);
		getProbleme().initialiserTourRef(getProbleme().getDs(), getProbleme().getJeu());
		int nombreDeScenario = getProbleme().getDs().getScenarios().size();
		//Il faut changer 5 pour ce qu'on a entrer
		int nombreDeEchantillon = 5;

		int increment = nombreDeScenario/nombreDeEchantillon;
		Echantillon echantillon = new Echantillon();
		for(Donnees scen: (Set<Donnees>) getProbleme().getDs().getScenarios()){

			if (nombreDeScenario/nombreDeEchantillon==increment) {
				echantillon = new Echantillon();
				listeEchantillon.add(echantillon);
				increment = 0;
			}
			echantillon.add(scen);

			increment++;

		}
		for (int i = 0; i < 1; i++) {
			listeEchantillon.get(i).setResultat((TourReference)solveurSecondaire.resoudre(donnees, solution, minimiser,listeEchantillon.get(i)));
		}

		TourReference min_CR = listeEchantillon.get(0).getResultat();

		for (int i = 1; i < listeEchantillon.size(); i++) {
			if (getDistance(listeEchantillon.get(i).getResultat().getKeySet()) <getDistance(min_CR.getKeySet())) {
				min_CR = listeEchantillon.get(i).getResultat();
			}
		}

		System.out.println(getDistance(min_CR.getKeySet()));
		
		return min_CR;
	}

	private double getDistance(Set<Arete> p) {
		double distance = 0;
		for(Arete a:p){
			distance = distance + a.getPoids();   		
		}
		return distance;
	}
	
	@Override
	public void init() throws ErreurDonneesException {
		// TODO Auto-generated method stub	
		listeEchantillon = new ArrayList<Echantillon>();
	}

	@Override
	public Donnees resoudre(Donnees dinitiales, Donnees solInit, boolean minimiser, Echantillon echantillon)
			throws ErreurDonneesException {
		// TODO Auto-generated method stub
		return null;
	}

}
