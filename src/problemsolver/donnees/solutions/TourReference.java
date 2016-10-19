/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package problemsolver.donnees.solutions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JPanel;

import problemsolver.donnees.Donnees;

/**
 *
 * @author Clément
 * @param <T> le type de données
 * @param <U> le type d'extraction
 */
public abstract class TourReference<T extends Donnees, U extends Donnees> extends Donnees{
    private HashMap<T, Double> moyenneTour;
    
    public TourReference(HashSet<T> l){
        super();
        moyenneTour = new HashMap<>();
        l.stream().forEach((d) -> {
            moyenneTour.put(d, 0.);
        });
    }
    
    public double getValeur(T d){
        return moyenneTour.get(d);
    }
    
    public void setValeur(T d, double v){
        moyenneTour.put(d, v);
    }
    
    public Set<T> getKeySet(){
        return moyenneTour.keySet();
    }
    
    public abstract void calculer(Collection<U> collection);
    
    @Override
	public int getSize() {
    	int taille = 0;
    	for(Donnees d:moyenneTour.keySet()){
    		taille+= d.getSize();
    	}
    	return taille;
	}
    
    public double verifier(){
    	double val = 0;
    	for(Double d:moyenneTour.values())
    		val+=d;
    	return val;
    }
}
