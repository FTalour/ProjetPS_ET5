package problemsolver.probleme;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import problemsolver.donnees.Arete;
import problemsolver.donnees.Graphe;

public class ScenarioTSP extends DonneesScenario<Graphe, Arete, PhiLambda> {

	HashMap<Graphe, PhiLambda> scénarios;
	ArrayList<Arete> listeAretes;
	
	public ScenarioTSP(HashMap<Graphe, PhiLambda> pScenario, HashSet<Arete> lArete) {
		super(pScenario, lArete);
		// TODO Auto-generated constructor stub
	}
	
	
}
