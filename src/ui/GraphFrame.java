/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.HeadlessException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import problemsolver.ProblemSolver;
import problemsolver.donnees.Donnees;
import problemsolver.exceptions.IGotItException;

/**
 *
 * @author clement
 */
public class GraphFrame extends javax.swing.JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int TAB_DONNEES = 0, TAB_SOLUTION = 1;
    
    /**
     * Creates new form GraphFrame
     */
    public GraphFrame() {
    	initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        boutonImage = new javax.swing.JButton();
        boutonFermer = new javax.swing.JButton();
        panel = new javax.swing.JTabbedPane();
        panelDonnees = new javax.swing.JSplitPane();
        graphDonnees = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        textAreaDonnees = new javax.swing.JTextArea();
        panelSolution = new javax.swing.JSplitPane();
        graphSolution = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        textAreaSolution = new javax.swing.JTextArea();
        boutonTexte = new javax.swing.JButton();

        setTitle("Visionneur de données");

        boutonImage.setText("Image");
        boutonImage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boutonImageActionPerformed(evt);
            }
        });

        boutonFermer.setText("Fermer");
        boutonFermer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boutonFermerActionPerformed(evt);
            }
        });

        panelDonnees.setDividerLocation(200);
        panelDonnees.setDividerSize(3);
        panelDonnees.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        panelDonnees.setResizeWeight(1.0);
        panelDonnees.setContinuousLayout(true);
        panelDonnees.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        graphDonnees.setBackground(new java.awt.Color(255, 255, 255));
        graphDonnees.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                graphDonneesComponentResized(evt);
            }
        });

        javax.swing.GroupLayout graphDonneesLayout = new javax.swing.GroupLayout(graphDonnees);
        graphDonnees.setLayout(graphDonneesLayout);
        graphDonneesLayout.setHorizontalGroup(
            graphDonneesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        graphDonneesLayout.setVerticalGroup(
            graphDonneesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        panelDonnees.setTopComponent(graphDonnees);

        textAreaDonnees.setEditable(false);
        textAreaDonnees.setColumns(20);
        textAreaDonnees.setLineWrap(true);
        textAreaDonnees.setRows(4);
        jScrollPane1.setViewportView(textAreaDonnees);

        panelDonnees.setBottomComponent(jScrollPane1);

        panel.addTab("Données", panelDonnees);

        panelSolution.setDividerLocation(200);
        panelSolution.setDividerSize(3);
        panelSolution.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        panelSolution.setResizeWeight(1.0);
        panelSolution.setContinuousLayout(true);

        graphSolution.setBackground(new java.awt.Color(255, 255, 255));
        graphSolution.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                graphSolutionComponentResized(evt);
            }
        });

        javax.swing.GroupLayout graphSolutionLayout = new javax.swing.GroupLayout(graphSolution);
        graphSolution.setLayout(graphSolutionLayout);
        graphSolutionLayout.setHorizontalGroup(
            graphSolutionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 399, Short.MAX_VALUE)
        );
        graphSolutionLayout.setVerticalGroup(
            graphSolutionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 199, Short.MAX_VALUE)
        );

        panelSolution.setLeftComponent(graphSolution);

        textAreaSolution.setEditable(false);
        textAreaSolution.setColumns(20);
        textAreaSolution.setLineWrap(true);
        textAreaSolution.setRows(4);
        jScrollPane3.setViewportView(textAreaSolution);

        panelSolution.setRightComponent(jScrollPane3);

        panel.addTab("Solution", panelSolution);

        boutonTexte.setText("Texte");
        boutonTexte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boutonTexteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panel)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(boutonImage)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(boutonTexte)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(boutonFermer)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panel, javax.swing.GroupLayout.DEFAULT_SIZE, 342, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(boutonImage)
                    .addComponent(boutonFermer)
                    .addComponent(boutonTexte))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void boutonFermerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boutonFermerActionPerformed
        this.setVisible(false);
    }//GEN-LAST:event_boutonFermerActionPerformed
    
    private void graphComponentResized(JPanel j){
        Dimension d = j.getSize();
        for(Component c:j.getComponents()){
            c.setSize(d);
        }
        repaint();
    }
        
    private void boutonImageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boutonImageActionPerformed
        try{
            JFileChooser jfc;
            jfc = new JFileChooser();
            File managerFile = ProblemSolver.getMainFrame().getManager().getFichier();
            
            if(managerFile != null && managerFile.isFile()){
                String nom = managerFile.getAbsolutePath();
                if(Afficheur.getExtension(nom)!="")
                    nom = nom.substring(0, nom.lastIndexOf("."));
                jfc.setSelectedFile(new File(nom+".png"));
            }
            
            File output;
            
            if(jfc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION){
                output = jfc.getSelectedFile();
                
                if(!output.getName().endsWith(".png")){
                    output = new File(output.getAbsolutePath()+".png");
                }
                
                if(output.exists()){
                    if(JOptionPane.showConfirmDialog(this, "Le fichier "+output.getAbsolutePath()+" existe déjà.\nVoulez vous le remplacer?", "Sauvegarder", JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION)
                        return;
                }
                
                JPanel graph = getGraph(panel.getSelectedIndex());
                Container parent = graph.getParent();
                parent.remove(graph);
                JPanel fond = new JPanel();
                fond.setBackground(Color.white);
                fond.add(graph);
                Dimension lastSize = graph.getSize();
                fond.setSize(new Dimension(1240, 1080));
                graph.setSize(new Dimension(1240, 1080));
                graphComponentResized(graph);
                BufferedImage bimg = createImage(fond);
                fond.remove(graph);
                parent.add(graph);
                graph.setSize(lastSize);
                graphComponentResized(graph);
                ImageIO.write(bimg, "png", output);
            }
        }
        catch(HeadlessException | IOException e){
            Afficheur.erreurFataleDialog(e);
        } catch (IGotItException ex) {}
        
    }//GEN-LAST:event_boutonImageActionPerformed

    private void boutonTexteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boutonTexteActionPerformed
        try{
            JFileChooser jfc;
            jfc = new JFileChooser();
            File managerFile = ProblemSolver.getMainFrame().getManager().getFichier();
            
            if(managerFile != null && managerFile.isFile()){
                String nom = managerFile.getAbsolutePath();
                if(!Afficheur.getExtension(nom).equals(""))
                    nom = nom.substring(0, nom.lastIndexOf("."));
                jfc.setSelectedFile(new File(nom+".txt"));
            }
            File output;
            
            if(jfc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION){
                output = jfc.getSelectedFile();
                
                if(!output.getName().endsWith(".txt")){
                    output = new File(output.getAbsolutePath()+".txt");
                }
                
                if(output.exists()){
                    if(JOptionPane.showConfirmDialog(this, "Le fichier "+output.getAbsolutePath()+" existe déjà.\nVoulez vous le remplacer?", "Sauvegarder", JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION)
                        return;
                }
                
                String text = getText(panel.getSelectedIndex()).getText();
                try (PrintWriter out = new PrintWriter(output)) {
                    out.print(text);
                }
            }
        }
        catch(HeadlessException | IOException e){
            Afficheur.erreurFataleDialog(e);
        } catch (IGotItException ex) {}
    }//GEN-LAST:event_boutonTexteActionPerformed

    private void graphSolutionComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_graphSolutionComponentResized
        graphComponentResized(graphSolution);
    }//GEN-LAST:event_graphSolutionComponentResized

    private void graphDonneesComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_graphDonneesComponentResized
        graphComponentResized(graphDonnees);
    }//GEN-LAST:event_graphDonneesComponentResized

    public BufferedImage createImage(JPanel panel) {

    int w = panel.getWidth();
    int h = panel.getHeight();
    BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
    Graphics2D g = bi.createGraphics();
    panel.paint(g);
    return bi;
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton boutonFermer;
    private javax.swing.JButton boutonImage;
    private javax.swing.JButton boutonTexte;
    private javax.swing.JPanel graphDonnees;
    private javax.swing.JPanel graphSolution;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane panel;
    private javax.swing.JSplitPane panelDonnees;
    private javax.swing.JSplitPane panelSolution;
    private javax.swing.JTextArea textAreaDonnees;
    private javax.swing.JTextArea textAreaSolution;
    // End of variables declaration//GEN-END:variables

    public void setPane(int index){
        panel.setSelectedIndex(index);
    }
    
    public void showDonnees(int index, Donnees d, Color c){
        refresh(index);
        if(!isVisible()){
            setLocationRelativeTo(null);
            setVisible(true);
        }
        // d est soit un Circuit soit un Graphe_Complet
        setText(index, d.toString());
        panel.setSelectedIndex(index);
        if(d.getSize()>=ProblemSolver.MAX_SIZE)
        	return;
		
		d.affiche(getGraph(index), 1, 1, 0, 0, c);
    }
    
    public void addShowDonnees(int index, Donnees d, Color c){
        if(!isVisible()){
            setLocationRelativeTo(null);
            setVisible(true);
        }
        // d est soit un Circuit soit un Graphe_Complet
        setText(index, d.toString());
        panel.setSelectedIndex(index);
        if(d.getSize()>=ProblemSolver.MAX_SIZE)
        	return;
		
		d.affiche(getGraph(index), 1, 1, 0, 0, c);
    }
    
    public JPanel getGraph(int index){
        switch(index){
            case(TAB_DONNEES):
                return graphDonnees;
            case(TAB_SOLUTION):
                return graphSolution;
            default:
                return null;
        }
    }
    
    public JTextArea getText(int index){
        switch(index){
            case(TAB_DONNEES):
                return textAreaDonnees;
            case(TAB_SOLUTION):
                return textAreaSolution;
            default:
                return null;
        }
    }
    
    public void setText(int index, String str){
        getText(index).setText(str);
    }

	public void refresh(int index){
        getGraph(index).removeAll();
        setText(index, "");
    }
    
    public void resetAll(){
        for(int i = 0; i < 3; i++){
            refresh(i);
        }
    }
}
