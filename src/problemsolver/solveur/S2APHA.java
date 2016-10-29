package problemsolver.solveur;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import problemsolver.donnees.Arete;
import problemsolver.donnees.Donnees;
import problemsolver.donnees.solutions.Circuit;
import problemsolver.donnees.solutions.TourReference;
import problemsolver.exceptions.ErreurDonneesException;
import problemsolver.probleme.DonneesScenario;
import problemsolver.probleme.Penalites;
import problemsolver.probleme.Probleme;
import problemsolver.probleme.Probleme_Stochastique;
import ui.Afficheur;

public class S2APHA extends Solveur<Probleme_Stochastique> {
	private Solveur<Probleme> secondSolveur;
	private int variation;
	private int pourcentDet;
	private static final int DEFVAR = 20, DEFPER = 20;

	private int nombreEchantillons;
	private int nombreScenarios;
	private int tailleEchantillonRef;
	private Solveur solveurSecondaire;

	private HashMap<Donnees, Penalites<? extends TourReference<?, ?>, ? extends Donnees>> tous_scenarios;
	private ArrayList<ArrayList<DonneesScenario<Donnees, Donnees, Penalites<? extends TourReference<?, ?>, ? extends Donnees>>>> listeEchantillon;
	private ArrayList<DonneesScenario<Donnees, Donnees, Penalites<? extends TourReference<?, ?>, ? extends Donnees>>> EchRef;

	public S2APHA(int nbEchantillon, Solveur secondS, int var, int pDet) {
		nombreEchantillons = nbEchantillon;
		nombreScenarios = nombreEchantillons;
		tailleEchantillonRef = 4 * nombreScenarios;
		solveurSecondaire = secondS;
		variation = var;
		pourcentDet = pDet;
	}

	public S2APHA(int nbrS, Solveur secondS) {
		this(nbrS, secondS, DEFVAR, DEFPER);
	}

	@Override
	public Donnees resoudre(Donnees donnees, Donnees solInit, boolean minimiser) throws ErreurDonneesException {
		double t = 0;
		boolean b;
		secondSolveur.setProbleme(getProbleme());
		secondSolveur.setAffiche(false);
		secondSolveur.init();
		HashMap<Donnees, Donnees> listSolution = new HashMap<Donnees, Donnees>();

		getProbleme().initialiserScenarios(variation, pourcentDet,
				nombreEchantillons * nombreScenarios + tailleEchantillonRef);
		getProbleme().initialiserTourRef(getProbleme().getDs(), getProbleme().getJeu());

		tous_scenarios = getProbleme().getDs().getHashMapScenarios();
		ArrayList<Donnees> keys = (ArrayList<Donnees>) tous_scenarios.keySet();
		ArrayList<Penalites<? extends TourReference<?, ?>, ? extends Donnees>> penalities = (ArrayList<Penalites<? extends TourReference<?, ?>, ? extends Donnees>>) tous_scenarios
				.values();
		HashSet<Donnees> donneesDeterministes = getProbleme().getDs().getDonneesDeterministes();
		int i = 0, j = 0;
		HashMap maHashMap;
		ArrayList<DonneesScenario<Donnees, Donnees, Penalites<? extends TourReference<?, ?>, ? extends Donnees>>> mon_echantillon = null;

		for (i = 0; i < nombreEchantillons; i++) {
			mon_echantillon = null;
			for (j = 0; j < nombreScenarios; j++) {
				maHashMap = new HashMap();
				maHashMap.put(keys.get(j + i * nombreEchantillons), penalities.get(j + i * nombreEchantillons));
				mon_echantillon.add(new DonneesScenario(maHashMap, donneesDeterministes));
			}
			listeEchantillon.add(mon_echantillon);
		}

		for (i++; i < nombreEchantillons * nombreScenarios + tailleEchantillonRef; i++) {
			for (j++; j < tailleEchantillonRef; j++) {
				maHashMap = new HashMap();
				maHashMap.put(keys.get(j + i * nombreEchantillons), penalities.get(j + i * nombreEchantillons));
				mon_echantillon.add(new DonneesScenario(maHashMap, donneesDeterministes));
			}
			EchRef = mon_echantillon;
		}

		// TODO listeEchantillon contient tous les echantillons hors référence
		// TODO EchRef contient l'echantillon référence

		// TODO verifié que le saa est appelé pour la première solution
		
		//pour chaque echantillon on calcule une solution initiale avec le SAA
		for (ArrayList<DonneesScenario<Donnees, Donnees, Penalites<? extends TourReference<?, ?>, ? extends Donnees>>> echantillon : listeEchantillon) { 
			for (DonneesScenario<Donnees, Donnees, Penalites<? extends TourReference<?, ?>, ? extends Donnees>> donneesScenario : echantillon) {
				for(Donnees scen : donneesScenario.getScenarios()) {
					listSolution.put(scen, secondSolveur.resoudre(scen, solInit, minimiser)); // SAA
				}
			}
		}
		
		getProbleme().getTr().calculer(listSolution.values());
		getProbleme().setUseStochastique(true);
		
		do {
			t++;
			listSolution.clear();
			
			//TODO la boucle principale du saapha
		} while (!b);
		
		Afficheur.infoDialog("Terminé en " + t + " tours");
		return getProbleme().getTr();
	}

	@Override
	public String toString() {
		return "Moyenne des données déterministes " + donneesString();
	}

	public String donneesString() {
		if (variation == DEFVAR && pourcentDet == DEFPER)
			return "(" + nombreEchantillons + " echantillons, avec " + secondSolveur + ")";
		else
			return "(" + nombreEchantillons + " echantillons, variation " + variation + "% et " + pourcentDet
					+ "% déterminisne, avec " + secondSolveur + ")";
	}

	public Solveur<Probleme> getSolveur() {
		return secondSolveur;
	}

	@Override
	public void init() throws ErreurDonneesException {
		// TODO Auto-generated method stub

	}

}
