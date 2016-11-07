package problemsolver.solveur;

import problemsolver.donnees.Donnees;
import problemsolver.exceptions.ErreurDonneesException;
import problemsolver.probleme.Echantillon;
import problemsolver.probleme.Probleme_Stochastique;

public class RecuitPha extends Solveur<Probleme_Stochastique>{
	Pha p;
	Solveur r;
	
	public RecuitPha(Pha mt){
		super();
		r = mt.getSolveur();
		p = mt;
	}
	
	@Override
	public Donnees resoudre(Donnees donnees, Donnees solution, boolean minimiser) throws ErreurDonneesException {
		p.setProbleme(this.getProbleme());
		r.setProbleme(this.getProbleme());
		p.resoudre(donnees, solution, minimiser);
		p.getProbleme().setUseHeuristique(true);
		p.getProbleme().setUseStochastique(false);
		return r.resoudre(minimiser);
	}
	
	@Override
	public void init() throws ErreurDonneesException {
		// TODO Auto-generated method stub
		
	}
	
    @Override
    public String toString(){
        return "Pha "+p.donneesString();
    }

	@Override
	public Donnees resoudre(Donnees donnees, Donnees solution, boolean minimiser, Echantillon echantillon)
			throws ErreurDonneesException {
		p.setProbleme(this.getProbleme());
		r.setProbleme(this.getProbleme());
		p.resoudre(donnees, solution, minimiser,echantillon);
		p.getProbleme().setUseHeuristique(true);
		p.getProbleme().setUseStochastique(false);
		return r.resoudre(minimiser);
	}

}
