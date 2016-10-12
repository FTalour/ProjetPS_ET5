/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package problemsolver.solveur;

import problemsolver.donnees.Donnees;
import problemsolver.exceptions.ErreurDonneesException;
import problemsolver.probleme.Probleme;

/**
 *
 * @author Clément
 * @param <T> Les types de problème résolvables (Probleme pour tous, Probleme_Stochastique pour les problèmes stochastiques etc)
 */
public abstract class Solveur<T extends Probleme> {
    private T probleme;
    private boolean affiche;
    
    public Solveur(){
    	affiche = true;
    }
    
    public abstract Donnees resoudre(Donnees donnees, Donnees solution, boolean minimiser) throws ErreurDonneesException;
    public Donnees resoudre(Donnees d, boolean minimiser) throws ErreurDonneesException{
		return resoudre(this.getProbleme().getJeu(), d, minimiser);
	}
    public Donnees resoudre(boolean minimiser) throws ErreurDonneesException {
		return resoudre(this.getProbleme().solutionInitial(), minimiser);
	}

    public T getProbleme() {
        return probleme;
    }

    public void setProbleme(T probleme) {
        this.probleme = probleme;
    }
    
    public void setAffiche(boolean b){
    	affiche = b;
    }
    
    public boolean getAffiche(){return affiche;}
    
    public abstract void init() throws ErreurDonneesException;
}
