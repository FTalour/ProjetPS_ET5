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
public class Recuit extends Solveur<Probleme<Graphe_Complet, Circuit_Hamiltonien>>{
	private double T0;
	
	@Override
    public Circuit_Hamiltonien resoudre(Graphe_Complet d, Circuit_Hamiltonien x, boolean minimiser) throws ErreurDonneesException {
		Circuit_Hamiltonien xLast = null;
    	int n = getProbleme().getTaille();
    	double T = T0;
    	Circuit_Hamiltonien xMeilleur = x;
    	double fMin = getProbleme().callFonctionObjectif(d, x);
    	while(T > T0/100 && x!=xLast){
    		int i = 0;
    		xLast = x;
    		while(i<n*n){
    			Circuit_Hamiltonien xPrime = getProbleme().voisinage(x);
    			double deltaF = getProbleme().callFonctionObjectif(d, xPrime) - getProbleme().callFonctionObjectif(d, x);
    			if ((deltaF < 0) == minimiser){
    				x = xPrime;
    				if (getProbleme().callFonctionObjectif(d, x) < fMin){
    					fMin = getProbleme().callFonctionObjectif(d, x);
    					xMeilleur = x;
    				}
    			}
    			else if (Math.random() < Math.exp(-deltaF/T)){
    				x = xPrime;
    			}
    			i = i + 1;
    		}
        	T = 0.85 * T;
    	}
    	return xMeilleur;
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

	@Override
	public void init() throws ErreurDonneesException {
		int n = getProbleme().getTaille();
    	T0 = calculTemperature(getProbleme().solutionInitial(), n);
	}
}
