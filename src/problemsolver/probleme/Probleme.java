/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package problemsolver.probleme;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import problemsolver.donnees.Donnees;
import problemsolver.donnees.solutions.TourReference;
import problemsolver.exceptions.ErreurDonneesException;
import problemsolver.parser.Parser;

/**
 *
 * @author Clément
 * @param <T>
 */
public abstract class Probleme<T extends Donnees, U extends Donnees>{
    T jeu;
    Parser<T> parser;
    private boolean useStochastique;
    private boolean useHeuristique;
    
    public Probleme(Parser<T> p){
        parser = p;
        useStochastique = false;
        useHeuristique = false; 
    }
    
    public double callFonctionObjectif(T donnees, U solution){
    	return fonctionObjectif(donnees, solution);
    }
    protected abstract double fonctionObjectif(T donnees, U solution);
    public U solutionInitial() throws ErreurDonneesException{
    	return solutionInitialBase();
    }
    public U voisinage(U solution){
    	if(useHeuristique)
    		return voisinageHeur(solution);
    	else
    		return voisinageBase(solution);
    }
    public abstract U solutionInitialHeur(TourReference tr) throws ErreurDonneesException;
    public abstract U voisinageHeur(U solution);
    public abstract U solutionInitialBase() throws ErreurDonneesException;
    public abstract U voisinageBase(U solution);
    public abstract int getTaille();

    public T getJeu() {
        return jeu;
    }

    public void setJeu(T jeu) {
        this.jeu = jeu;
    }
    
    public T parseDonnees(File f) throws ErreurDonneesException, FileNotFoundException, IOException{
        return parser.Parse(f);
    }
    
    public void setUseStochastique(boolean b){
    	useStochastique = b;
    }
    
    public boolean getStochastique(){
    	return useStochastique;
    }
    
    public void setUseHeuristique(boolean b){
    	useHeuristique = b;
    }
    
    public boolean getHeuristique(){
    	return useHeuristique;
    }
}
