/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package problemsolver.probleme;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import problemsolver.donnees.Donnees;

/**
 *
 * @author Clément
 * @param <T> les données de chaque scénario (un graphe pour le TSP)
 * @param <U> les données déterministes (les arêtes déterministes pour le TSP)
 * @param <V> Le type de pénalités
 */
public class DonneesScenario<T extends Donnees, U extends Donnees, V extends Penalites> {
    private HashMap<T, V> scenarios;
    HashSet<U> donneesDeterministes;
    
    public DonneesScenario(HashMap <T, V> pScenario, HashSet<U> lArete){
        scenarios = pScenario;
        donneesDeterministes = lArete;
    }
    
	public Set<T> getScenarios(){
        return scenarios.keySet();
    }
    
    public V getPenalites(T d){
        return scenarios.get(d);
    }
    
    public HashSet<U> getDonneesDeterministes(){
        return donneesDeterministes;
    }
}
