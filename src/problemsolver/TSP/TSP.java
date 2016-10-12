/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package problemsolver.TSP;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import problemsolver.donnees.Arete;
import problemsolver.donnees.Donnees;
import problemsolver.donnees.Graphe_Complet;
import problemsolver.donnees.Noeud;
import problemsolver.donnees.solutions.Circuit_Hamiltonien;
import problemsolver.donnees.solutions.Circuit_TourReference;
import problemsolver.exceptions.ErreurDonneesException;
import problemsolver.parser.Parse_Graphe;
import problemsolver.parser.Parse_Graphe_SAX;
import problemsolver.probleme.DonneesScenario;
import problemsolver.probleme.PhiLambda;
import problemsolver.probleme.Probleme_Stochastique;

/**
 *
 * @author Clément
 */
public class TSP extends Probleme_Stochastique<Graphe_Complet, Circuit_Hamiltonien, DonneesScenario<Graphe_Complet, Arete, PhiLambda>, Circuit_TourReference>{

    public TSP() {
        super(new Parse_Graphe_SAX());
    }
    
    @Override
    protected double fonctionObjectifStochastique(Graphe_Complet gC, Circuit_Hamiltonien circuit) {
    	double valeurTotale = this.fonctionObjectif(gC, circuit);
    	double valeurPhi = 0;
    	HashSet<Arete> lA= getDs().getDonneesDeterministes();
    	for(Arete a:lA){
    		if(circuit.getParcourt().contains(a)){
    			valeurTotale += (1 - this.getTr().getValeur(a)) * this.getDs().getPenalites(gC).getLambda(a);
    			valeurPhi += Math.pow((1 - this.getTr().getValeur(a)) * this.getDs().getPenalites(gC).getPhi(a), 2);
    		}else{
    			valeurTotale += (0 - this.getTr().getValeur(a)) * this.getDs().getPenalites(gC).getLambda(a);
    			valeurPhi += Math.pow((0 - this.getTr().getValeur(a)) * this.getDs().getPenalites(gC).getPhi(a), 2);
    		}
    	}
    	valeurTotale += valeurPhi/2;
    	return valeurTotale;
    }

    @Override
    protected DonneesScenario<Graphe_Complet, Arete, PhiLambda> creerScenarios(double variation, double pourcentDet, int nombre) throws ErreurDonneesException {
        HashMap<Graphe_Complet, PhiLambda> scenarios = new HashMap<Graphe_Complet, PhiLambda>();
        int nombreDet = (int) (getJeu().getListAretes().size()*(pourcentDet/100));
        HashSet<Arete> det = new HashSet<Arete>();
        for(int i = 0; i < nombreDet; i++){
        	int choix = (int) (Math.random()*getJeu().getListAretes().size());
        	while(det.contains(getJeu().getListAretes().get(choix))){
        		choix = (int) (Math.random()*getJeu().getListAretes().size());
        	}
        	det.add(getJeu().getListAretes().get(choix));
        }
    	for(int i = 0; i < nombre; i++){
    		Graphe_Complet scen = new Graphe_Complet(getJeu(), variation, det);
    		scenarios.put(scen, new PhiLambda(det));
    	}

    	return new DonneesScenario<Graphe_Complet, Arete, PhiLambda>(scenarios, det);
    }
    
    protected Circuit_TourReference creerTourRef(DonneesScenario<Graphe_Complet, Arete, PhiLambda> ar, Graphe_Complet d) throws ErreurDonneesException{
    	Circuit_TourReference ret = new Circuit_TourReference(ar.getDonneesDeterministes(), d);
		return ret;
    }


    @Override
    protected double fonctionObjectif(Graphe_Complet graphe, Circuit_Hamiltonien solution) {
    	return solution.distanceTotale();
    }

    @Override
    public Circuit_Hamiltonien solutionInitial() throws ErreurDonneesException {
        ArrayList<Noeud> parcourt = new ArrayList<Noeud>();
        for(Noeud n:getJeu().getListNoeuds()){
        	parcourt.add(n);
        }
        return new Circuit_Hamiltonien(parcourt, getJeu());
    }

    @Override
    public Circuit_Hamiltonien voisinage(Circuit_Hamiltonien solution) {
    	int a, b;
    	do{
	    	a = (int) (Math.random()*solution.getNombreNoeuds());
	    	b = (int) (Math.random()*solution.getNombreNoeuds());
    	}while(a == b);
    	
    	Circuit_Hamiltonien ret = (Circuit_Hamiltonien) solution.clone();
    	ret.swapNoeud(a, b);
    	
        return ret;
    }

	@Override
	public int getTaille() {
		return this.getJeu().getListNoeuds().size();
	}
    
}
