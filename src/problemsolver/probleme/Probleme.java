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
    
    public Probleme(Parser<T> p){
        parser = p;
        useStochastique = false;
    }
    
    public double callFonctionObjectif(T donnees, U solution){
    	return fonctionObjectif(donnees, solution);
    }
    protected abstract double fonctionObjectif(T donnees, U solution);
    public abstract U solutionInitial() throws ErreurDonneesException;
    public abstract U voisinage(U solution);
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
}
