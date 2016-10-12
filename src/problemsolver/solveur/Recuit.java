/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package problemsolver.solveur;

import java.awt.Color;

import javax.swing.SwingUtilities;

import problemsolver.ProblemSolver;
import problemsolver.donnees.Donnees;
import problemsolver.exceptions.ErreurDonneesException;
import problemsolver.probleme.Probleme;
import ui.Afficheur;
import ui.GraphFrame;

/**
 *
 * @author Clément
 */
public class Recuit extends Solveur<Probleme>{
	private double T0;
	
	
	@Override
    public Donnees resoudre(Donnees d, Donnees x, boolean minimiser) throws ErreurDonneesException {
    	Donnees xLast = null;
    	int n = getProbleme().getTaille();
    	double T = T0;
    	Donnees xMeilleur = x;
    	double fMin = getProbleme().callFonctionObjectif(d, x);
    	while(T > T0/100 && x!=xLast){
    		int i = 0;
    		xLast = x;
    		while(i<n*n){
    			Donnees xPrime = getProbleme().voisinage(x);
    			double deltaF = getProbleme().callFonctionObjectif(d, xPrime) - getProbleme().callFonctionObjectif(d, x);
    			if (deltaF < 0 == minimiser){
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
    		if(getAffiche())
    			criticalGraph(x, "Temperature: "+T+"\nTour optimal: "+xMeilleur);
        	T = 0.85 * T;
    	}
    	return xMeilleur;
    }
    
    private double calculTemperature(Donnees solutionInitiale, int n){
    	Donnees x = solutionInitiale;
    	Donnees xPrime = null;
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
    
    private void criticalGraph(Donnees d, String s){
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
