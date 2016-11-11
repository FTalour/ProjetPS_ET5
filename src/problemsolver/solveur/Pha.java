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
	
	// dinittiales ne sert jamais, c'est pas normal, on ne devrait pas le modifié, il contient les données parser, on devrait modifier une copie
	// Pour le SAA il vaudrait mieux que resoudre prenne directement une liste de scenarios
	@Override
	public Circuit_TourReference resoudre(Graphe_Complet dinitiales, Circuit_Hamiltonien solInit, boolean minimiser) throws ErreurDonneesException {
		double t = 0;
		double t_max = 10000;
		boolean continuer;
		
		// Timer
		long startTime;
		long endTime;
		
		// Configurer le problème comme stochastique
		getProbleme().setUseStochastique(true);

		// Création des scénarios et transformation du graphe actuel en stochastique avec les variations indiquées
		getProbleme().initialiserScenarios(variation, pourcentDet, nombreScenarios);
		
		//Initialisation du tour de référence
		getProbleme().initialiserTourRef(getProbleme().getDonnees(), getProbleme().getJeu());

		startTime = System.nanoTime();
		
		Circuit meilleurSolution = solInit;
		// Création des scénarios avec les solutions du recuit
		HashMap<Graphe_Complet, Circuit> listSolution = new HashMap<Graphe_Complet, Circuit>();
		for(Graphe_Complet scen: (Set<Graphe_Complet>) getProbleme().getDonnees().getScenarios()) {
			long startTime1 = System.nanoTime();
			meilleurSolution = secondSolveur.resoudre(scen,(Circuit_Hamiltonien) meilleurSolution,minimiser);
			long endTime1 = System.nanoTime();
			System.out.println("Duree resolution d'un scénario: " + (endTime1-startTime1)/1000000.0);
			
			listSolution.put(scen, meilleurSolution);
		}
		endTime = System.nanoTime();
		System.out.println("Duree initialistion des solutions des '" + getProbleme().getDonnees().getScenarios().size() + "' scenarios, temps: " + (endTime-startTime)/1000000.0);
		
		// Création et calcul du tour de référence à partir des scénarios initiés
		getProbleme().getTourRef().calculer(listSolution.values());
		
		long startTimeBoucle = System.nanoTime();
		do{
			t = t + 1;
			
			// Nettoyer les solutions initiales des scénarios
			listSolution.clear();

			// Recalculer les solutions des scénarios de données avec le recuit
			startTime = System.nanoTime();
			for(Graphe_Complet scen: (Set<Graphe_Complet>) getProbleme().getDonnees().getScenarios()){
				meilleurSolution = secondSolveur.resoudre(scen,(Circuit_Hamiltonien) meilleurSolution,minimiser);
				listSolution.put(scen, meilleurSolution);		    	
			}
			endTime = System.nanoTime();
			System.out.println("Duree calcul des solutions de la boucle '" + t +"' : temps: " + (endTime-startTime)/1000000.0);
			
			// Création et calcul du tour de référence à partir des scénarios initiés
			getProbleme().getTourRef().calculer(listSolution.values());
			
			continuer = true;
			for(Graphe_Complet d:listSolution.keySet()){
				// Permet de garder b à false pour la suite de la boucle en calculant les pénalités
				continuer = (getProbleme().getDonnees().getPenalites(d).ajuster(getProbleme().getTourRef(),listSolution.get(d)) && continuer);
				// Calcul des pénalités
				// getProbleme().getDonnees().getPenalites(d).ajuster(getProbleme().getTourRef(),listSolution.get(d));
			}
		}while(!continuer && t < t_max);
		long endTimeBoucle = System.nanoTime();
		System.out.println("Duree de la boucle principale du Pha: " + (endTimeBoucle-startTimeBoucle)/1000000.0);
		
		//Afficheur.infoDialog("Terminé en "+t+" tours"); // uncomment to get annoying messages popoing up into your face
		
		// Renvoyer le tour de référence
		return getProbleme().getTourRef();
	}

	// n'est jamais utilisé ...
	public Circuit_TourReference resoudre(Graphe_Complet dinitiales, Circuit_Hamiltonien solInit, boolean minimiser,Echantillon echantillon) throws ErreurDonneesException{
		double t = 0;
		boolean b;

		getProbleme().setUseStochastique(true);
		
		HashMap<Graphe_Complet, Circuit> listSolution = new HashMap<Graphe_Complet, Circuit>();
		for(Graphe_Complet scen: echantillon){
			listSolution.put(scen, secondSolveur.resoudre(scen,solInit,minimiser)); //TSP
		}
		getProbleme().getTourRef().calculer(listSolution.values());
		
		do{
			t = t + 1;
			listSolution.clear();

			for(Graphe_Complet scen: (Set<Graphe_Complet>) getProbleme().getDonnees().getScenarios()){
				listSolution.put(scen, secondSolveur.resoudre(scen,solInit,minimiser)); //TSP		    	
			}
			getProbleme().getTourRef().calculer(listSolution.values());
			b = true;
			for(Graphe_Complet d:listSolution.keySet()){
				b = (getProbleme().getDonnees().getPenalites(d).ajuster(getProbleme().getTourRef(),listSolution.get(d)) && b);
				getProbleme().getDonnees().getPenalites(d).ajuster(getProbleme().getTourRef(),listSolution.get(d));
			}
		}while(!b);

		// Afficheur.infoDialog("Terminé en "+t+" tours"); // uncomment to get annoying messages popoing up into your face
		return getProbleme().getTourRef();
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
