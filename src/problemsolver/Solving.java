package problemsolver;

import java.awt.Color;

import javax.swing.SwingUtilities;

import problemsolver.donnees.Donnees;
import problemsolver.exceptions.ErreurDonneesException;
import problemsolver.solveur.Solveur;
import ui.Afficheur;
import ui.GraphFrame;

public class Solving extends Thread {
	private final Solveur solveur;
	private final boolean min;
	
	public Solving(Solveur s, boolean m) {
		solveur = s;
		min = m;
	}
	
        @Override
	public void run(){
		try {
			Donnees d =solveur.resoudre(min);
			SwingUtilities.invokeLater(() -> {
                            ProblemSolver.getMainFrame().getGFrame().showDonnees(GraphFrame.TAB_SOLUTION, d, Color.BLACK);
                            Afficheur.infoDialog("Terminé en "+ProblemSolver.getMainFrame().getClock());
                            ProblemSolver.getMainFrame().stopClock();
                        });
		} catch (ErreurDonneesException e) {
			Afficheur.erreurFataleDialog(e);
		}
	}
}
