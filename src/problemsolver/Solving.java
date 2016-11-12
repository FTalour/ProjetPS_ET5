package problemsolver;

import java.awt.Color;
import java.io.File;

import javax.swing.SwingUtilities;

import problemsolver.donnees.Graphe_Complet;
import problemsolver.donnees.solutions.Circuit;
import problemsolver.donnees.solutions.Circuit_Hamiltonien;
import problemsolver.exceptions.ErreurDonneesException;
import problemsolver.probleme.Probleme;
import problemsolver.solveur.Solveur;
import ui.Afficheur;
import ui.GraphFrame;

public class Solving extends Thread {
	private final Solveur<? extends Probleme<Graphe_Complet, Circuit_Hamiltonien>> solveur;
	private final boolean min;
	private File file;

	public Solving(File fichier, Solveur<? extends Probleme<Graphe_Complet, Circuit_Hamiltonien>> solveur2, boolean m) {
		solveur = solveur2;
		min = m;
		file = fichier;
	}

	@Override
	public void run(){
		try {
			Circuit d = solveur.resoudre(min);
			SwingUtilities.invokeLater(() -> {
				//parseByFile(file);
				ProblemSolver.getMainFrame().getGFrame().showDonnees(GraphFrame.TAB_SOLUTION, d, Color.BLACK);
				//ProblemSolver.getMainFrame().getGFrame().setText(GraphFrame.TAB_SOLUTION, "TonTexte");
				Afficheur.infoDialog("Terminé en "+ProblemSolver.getMainFrame().getClock());
				ProblemSolver.getMainFrame().stopClock();
			});
		} catch (ErreurDonneesException e) {
			Afficheur.erreurFataleDialog(e);
		}
	}
	/*
	private double parseByFile(File file) {
		if(solveur.toString() == "recuit") return -1;
		String filename = file.getName();
		double coeff = switchSolveur(solveur.toString());
		double valeur=0;
		switch (filename) {
		case "br17":
			valeur = 39;
			break;
		case "a280":
			valeur = 2579;
			break;
		default:
			break;
		}
		return valeur * coeff;
	}
	
	private double switchSolveur(String solveurName){
		double valeur=0;
		switch (solveurName) {
		case "pha":
			valeur = 1.1;
			break;
		case "saa":
			valeur = 1.05;
			break;
		default:
			break;
		}
		return valeur;
	}*/
}
