package problemsolver.solveur;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import problemsolver.donnees.Arete;
import problemsolver.donnees.Donnees;
import problemsolver.donnees.solutions.Circuit;
import problemsolver.donnees.solutions.Circuit_Hamiltonien;
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
		boolean b;
		secondSolveur.setProbleme(getProbleme());
		secondSolveur.setAffiche(false);
		secondSolveur.init();
		HashMap<DonneesScenario<Donnees, Donnees, Penalites<? extends TourReference<?, ?>, ? extends Donnees>>, Donnees> listSolution = new HashMap<DonneesScenario<Donnees, Donnees, Penalites<? extends TourReference<?, ?>, ? extends Donnees>>, Donnees>();

		// prépartition du problème
		
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

		// mise sous forme d'echantillons
		
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

		// listeEchantillon contient tous les echantillons hors référence
		// EchRef contient l'echantillon référence
		
		//pour chaque echantillon on calcule une solution initiale avec le SAA
		for (ArrayList<DonneesScenario<Donnees, Donnees, Penalites<? extends TourReference<?, ?>, ? extends Donnees>>> echantillon : listeEchantillon) { 
			for (DonneesScenario<Donnees, Donnees, Penalites<? extends TourReference<?, ?>, ? extends Donnees>> donneesScenario : echantillon) {
				for(Donnees scen : donneesScenario.getScenarios()) { // resolution par echantillon
					listSolution.put(donneesScenario.getScenarios(), secondSolveur.resoudre(donneesScenario.getScenarios(), solInit, minimiser)); // SAA
				}
			}
		}
		
		// stockage des valeurs intiatiales
		Donnees xBest; // meilleur solution
		double vBest=0; // valeur de la meilleur solution ^v^best
		for (Donnees maSolutionInitiale : listSolution.values()) {
			double v = ((Circuit) maSolutionInitiale).distanceTotale(); // renvoi la ditance du circuit
			if (v > vBest) {
				vBest = v;
				xBest=maSolutionInitiale;
			}
		}
		
		
		ArrayList<ArrayList<Donnees>> solution = null; // contient x^mk, les solution des échantillons à l'iteration k
		double[][] v = null; // valeurs des solutions ^v^m,k
		double[] vkbest = null; // valeurs des ^v^k best 
		
		for (Donnees maSolutionInitiale : listSolution.values()) {
			solution.add((ArrayList<Donnees>) listSolution.values());
		}
		
		getProbleme().setUseStochastique(true); // le problème est stochastique
		
		double epsilon; // seuil de convergence
		double[] epsilonTab; // seuil de convergence à l'iteration k
		Donnees[] solEquilibreeTab; // solution equilibree à l'iteration k
		int k=0, kmax = 1000; // indices d'iterations
		while ((epsilonTab[k] >= epsilon || solEquilibreeTab[k] != xBest)  && k < kmax) {
			k++;
			listSolution.clear();
			
			// TODO mettre les pénalités à jour avec les classes qui vont bien
			
			
			for (i=0; i<listeEchantillon.size(); i++) {
				
				//TODO resoudre le problème à deux niveaux 
				//faire un truc comme ça ==> getProbleme().getTr().calculer(listSolution.values());
				
				//TODO stocker le résultat v (^v^mk) et solution (x^mk)
				
				double somme = 0;
				for (j=0; j<listeEchantillon.size(); j++) {
					//TODO avoir la valeur des solutions et pas juste la solution
					somme = Math.sqrt(Math.abs(solution.get(j).get(k) - solEquilibreeTab[k]));
				}
				epsilonTab[k] = somme; 
			}
			
			//on calcule la "vrai" meilleure solution sur N' 
			//en partant des solutions trouvées sur chaque echantillon
			for (i=0; i<listeEchantillon.size(); i++) {
				//on calcule la meilleure valeur objectif sur tout N'
				//en cherchant sur tous les scénarios de celui-ci avec la solution trouvée avant 
				for (j=0; j<EchRef.size(); j++) {
					
				}
			}
			
			// recherche de la meilleur solution du tour
			vkbest[k]=v[0][k];
			for (i=1; i<listeEchantillon.size(); i++) {
				if (v[i][k] < vkbest[k]) {
					vkbest[k]=v[i][k];
				}
				
			}
			
			// recherche de la meilleur solution sur tous les tours 
			if (vkbest[k] < vBest) {
				vBest = vkbest[k];
				for (i=0; i<listeEchantillon.size(); i++) {
					//xBest = /* x corespondant au vBest */;
				}
			}
		}
		
		Afficheur.infoDialog("Terminé en " + k + " tours");
		
		//x^S2APHA
		return getProbleme().initialiserTourRef(donS, typD);
		
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
