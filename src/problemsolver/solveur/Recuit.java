/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package problemsolver.solveur;

import javax.swing.SwingUtilities;

import problemsolver.donnees.Graphe_Complet;
import problemsolver.donnees.solutions.Circuit_Hamiltonien;
import problemsolver.exceptions.ErreurDonneesException;
import problemsolver.probleme.Probleme;

/**
 *
 * @author Clément
 */
public class Recuit extends Solveur<Probleme<Graphe_Complet, Circuit_Hamiltonien>> {
	private double T0;

	@Override
	public Circuit_Hamiltonien resoudre(Graphe_Complet graphe, Circuit_Hamiltonien solutionInitiale, boolean minimiser) throws ErreurDonneesException {
		
		long startTime;
		long endTime;
		
		int tailleProbleme 		= getProbleme().getTaille();
		
		startTime = System.nanoTime();
		T0 = calculTemperature(getProbleme().solutionInitial(), tailleProbleme);
		endTime = System.nanoTime();
		System.out.println("Duree initialistion température: " + (endTime-startTime)/1000000.0);
		
		double T 	= T0;
		double fMin = getProbleme().callFonctionObjectif(graphe, solutionInitiale);
		double deltaF = 0;
		double valeurSolutionInitiale = 0;
		double valeurSolutionInitialeSaved = 0;
		double fsolinit = 0;

		Circuit_Hamiltonien solutionInitiale_saved 	= null;
		Circuit_Hamiltonien solution_temp 			= null;
		Circuit_Hamiltonien solutionMeilleure  		= solutionInitiale;
		//valeurSolutionInitialeSaved-0.001*tailleProbleme>=valeurSolutionInitiale && valeurSolutionInitiale>=valeurSolutionInitialeSaved+0.001*tailleProbleme
		startTime = System.nanoTime();
		do{
			int i = 0;
			solutionInitiale_saved = solutionInitiale;
			valeurSolutionInitialeSaved = valeurSolutionInitiale;
			while (i < tailleProbleme * tailleProbleme) {
				long startTime1 = System.nanoTime();
				solution_temp = getProbleme().voisinage(solutionInitiale);
				long endTime1 = System.nanoTime();
				//System.out.println("\tDuree de calcul du voisinage de la boucle '" + i + "' temps: " + (endTime1-startTime1)/1000000.0);
				
				startTime1 = System.nanoTime();
				fsolinit = getProbleme().callFonctionObjectif(graphe, solutionInitiale);
				deltaF = getProbleme().callFonctionObjectif(graphe, solution_temp) - fsolinit;
				endTime1 = System.nanoTime();
				//System.out.println("\t\tDuree de calcul de deltaf : " + (endTime1-startTime1)/1000000.0);
				
				if ((deltaF < 0) == minimiser){
					solutionInitiale = solution_temp;
					valeurSolutionInitiale = fsolinit;
					
					if (valeurSolutionInitiale < fMin){
						fMin = valeurSolutionInitiale;
						solutionMeilleure = solutionInitiale;
					}
				}
				else if (Math.random() < Math.exp(-deltaF/T)){
					solutionInitiale = solution_temp;
					valeurSolutionInitiale = fsolinit;
				}
				i = i + 1;
			}
			T = 0.75 * T;
			System.out.println("valeurSolutionInitialeSaved : " + valeurSolutionInitialeSaved);
			System.out.println("valeurSolutionInitiale : " + valeurSolutionInitiale);
			boolean b = valeurSolutionInitialeSaved-0.001*tailleProbleme>=valeurSolutionInitiale;
			boolean	c = valeurSolutionInitiale>=valeurSolutionInitialeSaved+0.001*tailleProbleme;
			System.out.println("b : " + b + "    c : " + c);
		}while(T > T0/100 && (valeurSolutionInitialeSaved-0.5*tailleProbleme>=valeurSolutionInitiale || valeurSolutionInitiale>=valeurSolutionInitialeSaved+0.5*tailleProbleme));
		endTime = System.nanoTime();
		System.out.println("Duree totale de la boucle : " + (endTime-startTime)/1000000.0);
		return solutionMeilleure;
	}

	private double calculTemperature(Circuit_Hamiltonien solutionInitiale, int n){
		Circuit_Hamiltonien x = solutionInitiale;
		Circuit_Hamiltonien xPrime = null;
		boolean lastP = getProbleme().getStochastique();
		getProbleme().setUseStochastique(false);
		double df = 0;
		double a = 0, b = 0;
		double T0 = 0;
		while( a < n*n){
			xPrime = getProbleme().voisinage(x);
			double fx = getProbleme().callFonctionObjectif(getProbleme().getJeu(), x);
			double fxPrime = getProbleme().callFonctionObjectif(getProbleme().getJeu(), xPrime);
			if( fx < fxPrime){
				df = df + (fxPrime - fx);
				a = a+1;
			}

			b = b+1;
			if(b == (n*(n+1))/2){
				x = getProbleme().voisinage(x);
				b = 0;
			}
		}
		df = df/(n*n);
		T0 = -df/Math.log(0.8);
		getProbleme().setUseStochastique(lastP);
		return T0;
	}

	@SuppressWarnings("unused")
	private void criticalGraph(Circuit_Hamiltonien d, String s){
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				//ProblemSolver.getMainFrame().getGFrame().showDonnees(GraphFrame.TAB_RESOLUTION, d, Color.BLACK);
			}
		});
	}

	@Override
	public String toString(){
		return "Recuit";
	}

}

