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
import problemsolver.probleme.EchantillonS2APHA;
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

	private HashMap<Donnees, Penalites<? extends TourReference<?, ?>, ? extends Donnees>> allScenarios;
	private ArrayList<EchantillonS2APHA> listeEchantillon;
	private EchantillonS2APHA EchRef;

	public S2APHA(int nbEchantillon, Solveur secondS, int var, int pDet) {
		nombreEchantillons = nbEchantillon;
		nombreScenarios = nombreEchantillons;
		tailleEchantillonRef = 4 * nombreScenarios;
		secondSolveur = secondS;
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
		HashMap<DonneesScenario<Donnees, Donnees, Penalites<? extends TourReference<?, ?>, ? extends Donnees>>, Donnees> solutionsCalculees = new HashMap<DonneesScenario<Donnees, Donnees, Penalites<? extends TourReference<?, ?>, ? extends Donnees>>, Donnees>();

		// prépartition du problème
		
		//le problème contient l'ensemble des scénarios de tous les échantillons 
		getProbleme().initialiserScenarios(variation, pourcentDet,
				nombreEchantillons * nombreScenarios + tailleEchantillonRef);
		getProbleme().initialiserTourRef(getProbleme().getDs(), getProbleme().getJeu());

		allScenarios = getProbleme().getDs().getHashMapScenarios();
		ArrayList<Donnees> allScenariosKeys = (ArrayList<Donnees>) allScenarios.keySet();
		ArrayList<Penalites<? extends TourReference<?, ?>, ? extends Donnees>> allScenariosPenalities = (ArrayList<Penalites<? extends TourReference<?, ?>, ? extends Donnees>>) allScenarios
				.values();
		HashSet<Donnees> allDataDeterministes = getProbleme().getDs().getDonneesDeterministes();
		int i = 0, j = 0;
		HashMap maHashMap;
		EchantillonS2APHA mon_echantillon;

		// mise sous forme d'echantillons
		
		//création des échantillons
		for (i = 0; i < nombreEchantillons; i++) {
			mon_echantillon = null;
			for (j = 0; j < nombreScenarios; j++) {
				maHashMap = new HashMap();
				maHashMap.put(allScenariosKeys.get(j + i * nombreEchantillons), allScenariosPenalities.get(j + i * nombreEchantillons));
				mon_echantillon.add(new DonneesScenario(maHashMap, allDataDeterministes));
			}
			listeEchantillon.add(mon_echantillon);
		}

		//création de l'échantillon de référence
		for (i++; i < nombreEchantillons * nombreScenarios + tailleEchantillonRef; i++) {
			for (j++; j < tailleEchantillonRef; j++) {
				maHashMap = new HashMap();
				maHashMap.put(allScenariosKeys.get(j + i * nombreEchantillons), allScenariosPenalities.get(j + i * nombreEchantillons));
				mon_echantillon.add(new DonneesScenario(maHashMap, allDataDeterministes));
			}
			EchRef = mon_echantillon;
		}

		// listeEchantillon contient tous les echantillons hors référence
		// EchRef contient l'echantillon référence
		
		//pour chaque echantillon on calcule une solution initiale avec le SAA
		for (EchantillonS2APHA echantillon : listeEchantillon) { 
			for (DonneesScenario<Donnees, Donnees, Penalites<? extends TourReference<?, ?>, ? extends Donnees>> donneesScenario : echantillon) {
				for(Donnees scen : donneesScenario.getScenarios()) { // resolution par echantillon
					solutionsCalculees.put(donneesScenario.getScenarios(), secondSolveur.resoudre(scen, solInit, minimiser, echantillon)); // SAA
				}
			}
		}
		
		// stockage des valeurs intiatiales
		Donnees xBest; // meilleur solution
		double vBest=0; // valeur de la meilleur solution ^v^best
		for (Donnees maSolutionInitiale : solutionsCalculees.values()) {
			double v = ((Circuit) maSolutionInitiale).distanceTotale(); // renvoi la ditance du circuit
			if (v > vBest) {
				vBest = v;
				xBest=maSolutionInitiale;
			}
		}
		
		ArrayList<ArrayList<Donnees>> solutionsEchantillons = null; // contient x^mk, les solution des échantillons à l'iteration k
		double[][] v = null; // valeurs des solutions ^v^m,k
		double[] vkbest = null; // valeurs des ^v^k best 
		
		for (Donnees maSolutionInitiale : solutionsCalculees.values()) {
			solutionsEchantillons.add((ArrayList<Donnees>) solutionsCalculees.values());
		}
		
		getProbleme().setUseStochastique(true); // le problème est stochastique
		
		double epsilon; // seuil de convergence
		double[] epsilonTab; // seuil de convergence à l'iteration k
		Donnees[] solEquilibreeTab; // solution equilibree à l'iteration k
		int k=0, kmax = 1000; // indices d'iterations
		while ((epsilonTab[k] >= epsilon || solEquilibreeTab[k] != xBest)  && k < kmax) {
			k++;
			solutionsCalculees.clear();
			
			// TODO mettre les pénalités à jour avec les classes qui vont bien
			
			
			for (i=0; i<listeEchantillon.size(); i++) {
				
				//TODO resoudre le problème à deux niveaux (s'inspirer de la classe Pha)
				solutionsCalculees.put(listeEchantillon.get(i), secondSolveur.resoudre(donnees, solInit, minimiser, listeEchantillon.get(i)));
				listeEchantillon.get(i).getTr().calculer(solutionsCalculees.values());
				
				//TODO stocker le résultat v (^v^mk) et solution (x^mk)
				
				double somme = 0;
				for (j=0; j<listeEchantillon.size(); j++) {
					//TODO avoir la valeur des solutions et pas juste la solution
					somme = Math.sqrt(Math.abs(((Circuit) solutionsEchantillons.get(j).get(k)).distanceTotale() - ((Circuit) solEquilibreeTab[k]).distanceTotale()));
				}
				epsilonTab[k] = somme; 
			}
			
			//on calcule la "vrai" meilleure solution sur N' 
			//en partant de la partie deterministe des solutions trouvées sur chaque echantillon
			for (i=0; i<listeEchantillon.size(); i++) {
				//on calcule la meilleure valeur objectif sur tout N'
				//en cherchant sur tous les scénarios de celui-ci avec la solution trouvée avant 
				for (j=0; j<EchRef.size(); j++) {
					//TODO appeler calculer sur les valeurs deterministes
					EchRef.get(j).
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
		return null;
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
