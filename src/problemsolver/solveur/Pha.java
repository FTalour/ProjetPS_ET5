/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package problemsolver.solveur;

import java.util.HashMap;
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
import problemsolver.probleme.Probleme;
import problemsolver.probleme.Probleme_Stochastique;

/**
 *
 * @author Clément
 */
public class Pha extends Solveur<Probleme_Stochastique<Graphe_Complet, Circuit_Hamiltonien, DonneesScenario<Graphe_Complet, Arete, PhiLambda>, Circuit_TourReference>>{
	private int nombreScenarios;
	private Solveur<Probleme<Graphe_Complet, Circuit_Hamiltonien>> secondSolveur;
	private int variation;
	private int pourcentDet;
	private static final int DEFVAR = 20, DEFPER = 20;
	// UTILISER PLUSIEURS THREAD POUR FAIRE LES RECUITS EN MÊME TEMPS

	public Pha(int nbrS, Solveur<Probleme<Graphe_Complet, Circuit_Hamiltonien>> secondS, int var, int pDet) {
		nombreScenarios = nbrS;
		secondSolveur = secondS;
		variation = var;
		pourcentDet = pDet;

		//création du solveur secondaire
		secondSolveur.setProbleme(getProbleme());
		secondSolveur.setAffiche(false);
	}

	public Pha(int nbrS, Solveur<Probleme<Graphe_Complet, Circuit_Hamiltonien>> secondS) {
		this(nbrS, secondS, DEFVAR, DEFPER);
	}

	@Override
	public Circuit_TourReference resoudre(Graphe_Complet dinitiales, Circuit_Hamiltonien solInit, boolean minimiser) throws ErreurDonneesException {
		double t = 0;
		double t_max = 1000;
		boolean continuer;

		// Création des scénarios
		getProbleme().initialiserScenarios(variation, pourcentDet, nombreScenarios);
		
		//Initialisation du tour de référence
		getProbleme().initialiserTourRef(getProbleme().getDs(), getProbleme().getJeu());

		// Création des scénarios avec les solutions du recuit
		HashMap<Graphe_Complet, Circuit> listSolution = new HashMap<Graphe_Complet, Circuit>();
		for(Graphe_Complet scen: (Set<Graphe_Complet>) getProbleme().getDs().getScenarios()) {
			listSolution.put(scen, secondSolveur.resoudre(scen,solInit,minimiser));
		}

		// Création et calcul du tour de référence à partir des scénarios initiés
		// Les solutions initiales sont deterministes
		getProbleme().getTr().calculer(listSolution.values());
		getProbleme().setUseStochastique(true);

		do{
			t = t + 1;
			
			// Nettoyer les solutions initiales des scénarios deterministes
			listSolution.clear();

			// Recalculer les solutions des scénarios de données deterministes avec le recuit
			for(Graphe_Complet scen: (Set<Graphe_Complet>) getProbleme().getDs().getScenarios()){
				listSolution.put(scen, secondSolveur.resoudre(scen,solInit,minimiser));		    	
			}
			
			// Création et calcul du tour de référence à partir des scénarios initiés
			// Les solutions sont calculés avec les scénarios deterministes
			getProbleme().getTr().calculer(listSolution.values());
			
			continuer = true;
			for(Graphe_Complet d:listSolution.keySet()){
				// Permet de garder b à false pour la suite de la boucle en calculant les pénalités
				continuer = (getProbleme().getDs().getPenalites(d).ajuster(getProbleme().getTr(),listSolution.get(d)) && continuer);
				// Calcul des pénalités
				getProbleme().getDs().getPenalites(d).ajuster(getProbleme().getTr(),listSolution.get(d));
			}
		}while(!continuer && t_max < 10000);
		//Afficheur.infoDialog("Terminé en "+t+" tours"); // uncomment to get annoying messages popoing up into your face
		
		// Renvoyer le tour de référence
		return getProbleme().getTr();
	}

	public Circuit_TourReference resoudre(Graphe_Complet dinitiales, Circuit_Hamiltonien solInit, boolean minimiser,Echantillon echantillon) throws ErreurDonneesException{
		double t = 0;
		boolean b;

		HashMap<Graphe_Complet, Circuit> listSolution = new HashMap<Graphe_Complet, Circuit>();
		for(Graphe_Complet scen: echantillon){
			listSolution.put(scen, secondSolveur.resoudre(scen,solInit,minimiser)); //TSP
		}
		getProbleme().getTr().calculer(listSolution.values());
		getProbleme().setUseStochastique(true);
		do{
			t = t + 1;
			listSolution.clear();

			for(Graphe_Complet scen: (Set<Graphe_Complet>) getProbleme().getDs().getScenarios()){
				listSolution.put(scen, secondSolveur.resoudre(scen,solInit,minimiser)); //TSP		    	
			}
			getProbleme().getTr().calculer(listSolution.values());
			b = true;
			for(Graphe_Complet d:listSolution.keySet()){
				b = (getProbleme().getDs().getPenalites(d).ajuster(getProbleme().getTr(),listSolution.get(d)) && b);
				getProbleme().getDs().getPenalites(d).ajuster(getProbleme().getTr(),listSolution.get(d));
			}
		}while(!b);
		// Afficheur.infoDialog("Terminé en "+t+" tours"); // uncomment to get annoying messages popoing up into your face
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

	public Solveur<Probleme<Graphe_Complet, Circuit_Hamiltonien>> getSolveur() {
		return secondSolveur;
	}
}
