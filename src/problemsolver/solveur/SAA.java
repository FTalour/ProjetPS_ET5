package problemsolver.solveur;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import problemsolver.donnees.Arete;
import problemsolver.donnees.Graphe_Complet;
import problemsolver.donnees.solutions.Circuit;
import problemsolver.donnees.solutions.Circuit_Hamiltonien;
import problemsolver.donnees.solutions.Circuit_TourReference;
import problemsolver.exceptions.ErreurDonneesException;
import problemsolver.probleme.DonneesScenario;
import problemsolver.probleme.Echantillon;
import problemsolver.probleme.PhiLambda;
import problemsolver.probleme.Probleme_Stochastique;

public class SAA extends Solveur<Probleme_Stochastique<Graphe_Complet, Circuit_Hamiltonien, DonneesScenario<Graphe_Complet, Arete, PhiLambda>, Circuit_TourReference>> {
	int nombreEchantillons;
	ArrayList<Echantillon> listeEchantillon;
	Echantillon referanceEchantillon = new Echantillon();
	Echantillon EchRef;
	Solveur<Probleme_Stochastique<Graphe_Complet, Circuit_Hamiltonien, DonneesScenario<Graphe_Complet, Arete, PhiLambda>, Circuit_TourReference>> solveurSecondaire;

	public SAA(int nbrS, Solveur<Probleme_Stochastique<Graphe_Complet, Circuit_Hamiltonien, DonneesScenario<Graphe_Complet, Arete, PhiLambda>, Circuit_TourReference>> secondS) {
		nombreEchantillons = nbrS;
		solveurSecondaire = secondS;

		listeEchantillon = new ArrayList<Echantillon>();
	}

	@Override
	public Circuit resoudre(Graphe_Complet donnees, Circuit_Hamiltonien solution, boolean minimiser)
			throws ErreurDonneesException {

		solveurSecondaire.setProbleme(getProbleme());
		solveurSecondaire.setAffiche(false);

		getProbleme().initialiserScenarios(20, 20, 10);
		getProbleme().initialiserTourRef(getProbleme().getDonnees(),	getProbleme().getJeu());

		// di yi ci zhu shi
		int nombreDeScenario = getProbleme().getDonnees().getScenarios().size();
		// Il faut changer 5 pour ce qu'on a entrer
		int nombreDeEchantillon = 5;

		int increment = nombreDeScenario / nombreDeEchantillon;
		Echantillon echantillon = new Echantillon();
		for (Graphe_Complet scen : (Set<Graphe_Complet>) getProbleme().getDonnees().getScenarios()) {
			referanceEchantillon.add(scen);
			if (nombreDeScenario / nombreDeEchantillon == increment) {
				echantillon = new Echantillon();
				listeEchantillon.add(echantillon);
				increment = 0;
			}
			echantillon.add(scen);
			increment++;
		}

		
		Circuit_Hamiltonien min_CR = null;
		for (int i = 0; i < listeEchantillon.size(); i++) {
			Circuit_Hamiltonien echantillonTr =  (Circuit_Hamiltonien) solveurSecondaire.resoudre(donnees,solution, minimiser, listeEchantillon.get(i));
			HashSet<Arete> hashSet = new HashSet<Arete>();
			for (int j = 0; j < echantillonTr.getParcourt().size(); j++) {
				hashSet.add(echantillonTr.getParcourt().get(i)) ;
			}
			//getProbleme().initialiserTourRefSaa(new Circuit_TourReference(hashSet, echantillonTr.getGraphe()));
			Circuit_Hamiltonien resultatRr = (Circuit_Hamiltonien) solveurSecondaire.resoudre(donnees,solution, minimiser, listeEchantillon.get(i));
			if(min_CR==null)
				min_CR = resultatRr;
			else if (getDistance(resultatRr.getParcourt()) < getDistance(resultatRr.getParcourt())) { 
				min_CR = resultatRr;
			}
		}
		//System.out.println("min_CR:"+getDistance(min_CR.getKeySet()));
		return min_CR;
	}

	private double getDistance(ArrayList<Arete> p) {
		double distance = 0;
		for (Arete a : p) {
			distance = distance + a.getPoids();
		}
		return distance;
	}
}
