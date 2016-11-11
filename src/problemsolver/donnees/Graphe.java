/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package problemsolver.donnees;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import problemsolver.Graphismes.GraphicComponent;
import ui.Afficheur;

/**
 *
 * @author Clément
 */
public class Graphe extends Donnees{
    private final ArrayList<Noeud> listNoeuds;
    private final HashMap<Integer, Arete> mapAretes;
    private double[][] poidsArretes;
    private boolean DAinit;
    
    private double maxX;
    private double maxY;
    private double minX;
    private double minY;
    private static final int GRAPHICSIZE = 8;
    
    @SuppressWarnings("unchecked")
	public Graphe(ArrayList<Noeud> ln, HashMap<Integer, Arete> ma){
        super();
        listNoeuds = (ArrayList<Noeud>) ln.clone();
        mapAretes = (HashMap<Integer, Arete>) ma.clone();
        maxX = Double.MIN_VALUE;
        maxY = Double.MIN_VALUE;
        minX = Double.MAX_VALUE;
        minY = Double.MAX_VALUE;
        for(Noeud n : listNoeuds){
            if(n.getX() > maxX)
                maxX = n.getX();
            if(n.getY() > maxY)
                maxY = n.getY();
            if(n.getX() < minX)
                minX = n.getX();
            if(n.getY() < minY)
                minY = n.getY();
        }
        poidsArretes = new double[listNoeuds.size()][listNoeuds.size()];
        DAinit = false;
    }

	@SuppressWarnings("unchecked")
	public Graphe(Graphe g, double variation, HashSet<Arete> valDet) {
        super();
        listNoeuds =  (ArrayList<Noeud>) g.listNoeuds.clone();
        mapAretes = (HashMap<Integer, Arete>) g.mapAretes.clone();
        for(Arete a:g.mapAretes.values()){
        	if(!valDet.contains(a))
        		a.setPoids(a.getPoids() + a.getPoids() * ((Math.random()*variation*2-variation)/100));
        }
        maxX = g.maxX;
        maxY = g.maxY;
        minX = g.minX;
        minY = g.minY;
        poidsArretes = new double[listNoeuds.size()][listNoeuds.size()];
        DAinit = false;
    }
    
    /*public Graphe(double[][] DA) {
        super();
        listNoeuds = new ArrayList();
        mapAretes = new HashMap();
        maxX = Double.MIN_VALUE;
        maxY = Double.MIN_VALUE;
        minX = Double.MAX_VALUE;
        minY = Double.MAX_VALUE;
        DAinit = true;
        doubleArray = DA;
        for(int i = 0; i < DA.length; i ++){
            listNoeuds.add(new Noeud(0, 0, Integer.toString(i)));
        }
        for(int i = 0; i < DA.length; i++){
            for(int j = 0; j < DA[i].length; j++){
                if(DA[i][j] != 0.){
                    Arete a = new Arete(listNoeuds.get(i), listNoeuds.get(j), DA[i][j]);
                    mapAretes.put(a.hashCode(), a);
                }
            }
        }
    }*/
    
    public double[][] getDoubleArray(){
        if(DAinit){
            return poidsArretes;
        }
        DAinit = true;
        int i = 0;
        for(Noeud n:listNoeuds){
            int j = 0;
            for(Noeud m:listNoeuds){
                if(n == m){
                    poidsArretes[i][j] = 0.;
                }else{
                    Arete a = getArete(n, m);
                    if(a == null)
                        poidsArretes[i][j] = Double.MAX_VALUE;
                    else
                    	poidsArretes[i][j] = a.getPoids();
                }
                j++;
            }
            i++;
        }
        return poidsArretes;
    }
    
    public void setCoordonnees(double coord[][]){
        try{
            maxX = Double.MIN_VALUE;
            maxY = Double.MIN_VALUE;
            minX = Double.MAX_VALUE;
            minY = Double.MAX_VALUE;
            for(int i = 0; i < listNoeuds.size(); i++){
                Noeud n = listNoeuds.get(i);
                n.setX(coord[0][i]);
                n.setY(coord[1][i]);
                if(coord[0][i] > maxX)
                    maxX = coord[0][i];
                if(coord[1][i] > maxY)
                    maxY = coord[1][i];
                if(coord[0][i] < minX)
                    minX = coord[0][i];
                if(coord[1][i] < minY)
                    minY = coord[1][i];
            }
        }catch(IndexOutOfBoundsException e){
            Afficheur.erreurFataleDialog(e);
        }catch(Exception e){
            Afficheur.erreurFataleDialog(e);
        }
    }
    
    public Arete getArete(Noeud a, Noeud b){
        return mapAretes.get(Arete.getHashCode(a, b));
    }

    public ArrayList<Noeud> getListNoeuds() {
        return listNoeuds;
    }

    public ArrayList<Arete> getListAretes() {
    	ArrayList<Arete> ret = new ArrayList<Arete>();
    	ret.addAll(mapAretes.values());
        return ret;
    }
    
    public HashMap<Integer, Arete> getMapAretes(){
        return mapAretes;
    }
    
    public double getMaxX() {
        return maxX;
    }

    public double getMaxY() {
        return maxY;
    }

    public double getMinX() {
        return minX;
    }

    public double getMinY() {
        return minY;
    }
   
    @SuppressWarnings("serial")
	@Override
    public void affiche(JPanel j, double echelleLargeur, double echelleHauteur, double translationX, double translationY, Color c) {
    	double MX = Math.abs(minX);
        double MY = Math.abs(minY);
    	double EL = (j.getWidth()/(maxX+MX))*echelleLargeur;
        double EH = (j.getHeight()/(maxY+MY))*echelleHauteur;
        GraphicComponent pan = new GraphicComponent(){
            
            @Override
            protected void affiche(Graphics g) {
            	Graphics2D g2d = (Graphics2D)g;
                g2d.setColor(c);
                for(Noeud n:listNoeuds){
                	g2d.fillOval((int) (((n.getX()+MX)*EL*getResizeX())-GRAPHICSIZE/2), (int) (((n.getY()+MY)*EH*getResizeY())-GRAPHICSIZE/2), GRAPHICSIZE, GRAPHICSIZE);
                }
                for(Arete a:mapAretes.values()){
                	g2d.drawLine((int)((a.getNoeudA().getX()+MX)*EL*getResizeX()), (int)((a.getNoeudA().getY()+MY)*EH*getResizeY()), (int)((a.getNoeudB().getX()+MX)*EL*getResizeX()), (int)((a.getNoeudB().getY()+MY)*EH*getResizeY()));
                }
                
            }
        };
        pan.setBackground(new Color(255, 255, 255, 0));
        pan.setInitSize(j.getSize());
        SwingUtilities.invokeLater(new Runnable(){

			@Override
			public void run() {
				j.add(pan);
				j.getParent().revalidate();
		        j.getParent().repaint();
			}
        	
        });
        
    }
    
    @Override
    public String toString(){
        String ret="";
        for(Arete a: mapAretes.values()){
            ret+= a+"\n";
        }
        return ret;
    }

	@Override
	public int getSize() {
		return listNoeuds.size()+mapAretes.size()*2;
	}
    
    
}
