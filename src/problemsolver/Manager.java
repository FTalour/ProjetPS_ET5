/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package problemsolver;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import problemsolver.donnees.Donnees;
import problemsolver.exceptions.ErreurDonneesException;
import problemsolver.exceptions.IGotItException;
import problemsolver.probleme.Probleme;
import problemsolver.solveur.Solveur;
import ui.Afficheur;

/**
 *
 * @author Clément
 */
public class Manager{
    private File fichier;
    private Probleme probleme;
    private Solveur solveur;
    private boolean minimiser;
    Donnees donnees;
    
    public Manager(File fichier, Probleme probleme, Solveur solveur, boolean minimiser){
        this.fichier = fichier;
        this.probleme = probleme;
        this.solveur = solveur;
        this.minimiser = minimiser;
        this.donnees = null;
    }
    
    public void demarrer() throws ErreurDonneesException
    {
    	
        try{
            if(donnees == null){
            	if(fichier == null){
            		Afficheur.erreurDialog("Veuillez choisir un fichier valide.");
            		return;
            	}
            	else
            		donnees = probleme.parseDonnees(fichier);
            }
        	probleme.setJeu(donnees);
        }catch(Exception e){
        	Afficheur.erreurFataleDialog(e);
        }
		probleme.setUseHeuristique(false);
		probleme.setUseStochastique(false);
        solveur.setProbleme(probleme);
        solveur.init();
        
        new Solving(solveur, minimiser).run();
        
    }
    
    public File getFichier() throws IGotItException {
        return fichier;
    }

    public void setFichier(File fichier) {
        this.fichier = fichier;
        donnees = null;
    }

    public Probleme getProbleme() {
        return probleme;
    }

    public void setProbleme(Probleme probleme) {
        this.probleme = probleme;
    }

    public Solveur getSolveur() {
        return solveur;
    }

    public void setSolveur(Solveur solveur) {
        this.solveur = solveur;
    }

    public boolean isMinimiser() {
        return minimiser;
    }

    public void setMinimiser(boolean minimiser) {
        this.minimiser = minimiser;
    }
    
    public Donnees getDonnees() throws FileNotFoundException, ErreurDonneesException, IOException{
    	if(donnees == null)
        	donnees = probleme.parseDonnees(fichier);
    	return donnees;
    }
}
