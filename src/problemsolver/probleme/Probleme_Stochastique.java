/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package problemsolver.probleme;

import problemsolver.donnees.Arete;
import problemsolver.donnees.Graphe_Complet;
import problemsolver.donnees.solutions.Circuit;
import problemsolver.donnees.solutions.Circuit_Hamiltonien;
import problemsolver.donnees.solutions.TourReference;
import problemsolver.exceptions.ErreurDonneesException;
import problemsolver.parser.Parser;

/**
 *
 * @author Clément
 * @param <T> Les données à utiliser
 * @param <U> Le type de solution
 * @param <V> Le type de DonneesScenario
 * @param <W> Le tour de référence
 */
public abstract class Probleme_Stochastique<T extends Graphe_Complet, U extends Circuit_Hamiltonien, V extends DonneesScenario<? extends Graphe_Complet, ? extends Arete, ? extends PhiLambda>, W extends TourReference<? extends Arete, ? extends Circuit>> extends Probleme<T,U>{
    private V ds;
    private W tr;

    public Probleme_Stochastique(Parser<T> p) {
        super(p);
    }
    
    @Override public double callFonctionObjectif(T donnees, U solution){
    	if(getStochastique()){
    		return fonctionObjectifStochastique(donnees, solution);
    	}else{
    		return fonctionObjectif(donnees, solution);
    	}
    }
    
    protected abstract double fonctionObjectifStochastique(T donnees, U solution);
    
    public void initialiserScenarios(double variation, double pourcentDet, int nombre) throws ErreurDonneesException{
        ds = (V) creerScenarios(variation, pourcentDet, nombre);
    }
    
    protected abstract V creerScenarios(double variation, double pourcentDet, int nombre) throws ErreurDonneesException;

    public void initialiserTourRef(DonneesScenario<Graphe_Complet, Arete, PhiLambda> donneesScenario, Graphe_Complet graphe_Complet) throws ErreurDonneesException{
        tr = (W) creerTourRef(donneesScenario, graphe_Complet);
    }
    
    public void initialiserTourRefSaa(W trNew) throws ErreurDonneesException{
        tr = trNew;
    }
    
    protected abstract W creerTourRef(DonneesScenario<Graphe_Complet, Arete, PhiLambda> donneesScenario, Graphe_Complet graphe_Complet) throws ErreurDonneesException;

    public V getDs() {
        return ds;
    }
    
    public W getTr() {
        return tr;
    }
    
    public U solutionInitial() throws ErreurDonneesException{
    	if(getHeuristique() && getTr() != null)
    		return (U) solutionInitialHeur(getTr());
    	else
    		return solutionInitialBase();
    }
    
}
