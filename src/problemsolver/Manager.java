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
import problemsolver.donnees.Graphe_Complet;
import problemsolver.donnees.solutions.Circuit_Hamiltonien;
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
    private Probleme<Graphe_Complet, Circuit_Hamiltonien> probleme;
    private Solveur<? extends Probleme<Graphe_Complet, Circuit_Hamiltonien>> solveur;
    private boolean minimiser;
    Graphe_Complet donnees;
    
    public Manager(File fichier, Probleme<Graphe_Complet, Circuit_Hamiltonien> probleme, Solveur<Probleme<Graphe_Complet, Circuit_Hamiltonien>> solveur, boolean minimiser) {
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

    public Probleme<Graphe_Complet, Circuit_Hamiltonien> getProbleme() {
        return probleme;
    }

    public void setProbleme(Probleme<Graphe_Complet, Circuit_Hamiltonien> probleme) {
        this.probleme = probleme;
    }

    public Solveur<? extends Probleme<Graphe_Complet, Circuit_Hamiltonien>> getSolveur() {
        return solveur;
    }

    public void setSolveur(Solveur<? extends Probleme<Graphe_Complet, Circuit_Hamiltonien>> solveur) {
        this.solveur = solveur;
    }

    public boolean isMinimiser() {
        return minimiser;
    }

    public void setMinimiser(boolean minimiser) {
        this.minimiser = minimiser;
    }
    
    public Donnees getDonnees() throws FileNotFoundException, ErreurDonneesException, IOException {
    	if(donnees == null)
        	donnees = probleme.parseDonnees(fichier);
    	return donnees;
    }
}
