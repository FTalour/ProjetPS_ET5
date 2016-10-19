/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package problemsolver.solveur;

import java.util.ArrayList;
import problemsolver.donnees.Graphe;
import problemsolver.donnees.solutions.TourReference;
import java.util.HashMap;
import java.util.Set;

import problemsolver.donnees.Arete;
import problemsolver.donnees.Donnees;
import problemsolver.exceptions.ErreurDonneesException;
import problemsolver.probleme.Probleme;
import problemsolver.probleme.Probleme_Stochastique;
import problemsolver.donnees.solutions.TourReference;
import ui.Afficheur;

/**
 *
 * @author Clément
 */
public class Pha extends Solveur<Probleme_Stochastique>{
    private int nombreScenarios;
    private Solveur<Probleme> secondSolveur;
    private int variation;
    private int pourcentDet;
    private static final int DEFVAR = 20, DEFPER = 20;
    // UTILISER PLUSIEURS THREAD POUR FAIRE LES RECUITS EN MÊME TEMPS
    
    public Pha(int nbrS, Solveur secondS, int var, int pDet){
        nombreScenarios = nbrS;
        secondSolveur = secondS;
        variation = var;
        pourcentDet = pDet;
    }
    
    public Pha(int nbrS, Solveur secondS) {
        this(nbrS, secondS, DEFVAR, DEFPER);
    }

    @SuppressWarnings("unchecked")
    @Override

	public Donnees resoudre(Donnees dinitiales, Donnees solInit, boolean minimiser) throws ErreurDonneesException{
			double t = 0;
		    boolean b;
		    secondSolveur.setProbleme(getProbleme());
		    secondSolveur.setAffiche(false);
		    secondSolveur.init();
		    HashMap<Donnees, Donnees> listSolution = new HashMap<Donnees, Donnees>();
		    getProbleme().initialiserScenarios(variation, pourcentDet, nombreScenarios);
		    getProbleme().initialiserTourRef(getProbleme().getDs(), getProbleme().getJeu());
		    for(Donnees scen: (Set<Donnees>) getProbleme().getDs().getScenarios()){
		    	
		    	listSolution.put(scen, secondSolveur.resoudre(scen,solInit,minimiser)); //TSP
		    }
		    getProbleme().getTr().calculer(listSolution.values());
		    getProbleme().setUseStochastique(true);
			do{
				t = t + 1;
				listSolution.clear();
				
				for(Donnees scen: (Set<Donnees>) getProbleme().getDs().getScenarios()){
			    	listSolution .put(scen, secondSolveur.resoudre(scen,solInit,minimiser)); //TSP		    	
			    }
				getProbleme().getTr().calculer(listSolution.values());
				b = true;
				for(Donnees d:listSolution.keySet()){
					b = (getProbleme().getDs().getPenalites(d).ajuster(getProbleme().getTr(),listSolution.get(d)) && b);
					getProbleme().getDs().getPenalites(d).ajuster(getProbleme().getTr(),listSolution.get(d));
				}
			}while(!b);
			Afficheur.infoDialog("Terminé en "+t+" tours");
			return getProbleme().getTr();
	}

    
    @Override
    public String toString(){
        return "Moyenne des données déterministes "+donneesString();
    }
    
    public String donneesString(){
        if(variation == DEFVAR && pourcentDet == DEFPER)
                return "("+nombreScenarios+" scenarios, avec "+secondSolveur+")";
        else
            return "("+nombreScenarios+" scenarios, variation "+variation+"% et "+pourcentDet+"% déterminisne, avec "+secondSolveur+")";
    }

    public Solveur<Probleme> getSolveur() {
        return secondSolveur;
    }

	@Override
	public void init() throws ErreurDonneesException {
		// TODO Auto-generated method stub
		
	}
}
