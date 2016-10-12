/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.Thread.State;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import problemsolver.Manager;
import problemsolver.TSP.TSP;
import problemsolver.donnees.Donnees;
import problemsolver.exceptions.ErreurDonneesException;
import problemsolver.exceptions.IGotItException;
import problemsolver.solveur.*;

/**
 *
 * @author Clément
 */
public class MainFrame extends javax.swing.JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Manager manager;
    private String lastFile = "";
    private GraphFrame gFrame;
    private Timer telapsed;
    long startTime;
    
    /**
     * Creates new form NewJFrame
     * @param m Le manager
     */
    public MainFrame(Manager m) {
        manager = m;
        gFrame = new GraphFrame();
        gFrame.setLocationRelativeTo(null);
        gFrame.setVisible(false);
        initComponents();
        startTime = 0;
        telapsed = new Timer(100, new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				long temps = (System.currentTimeMillis() - startTime)/1000;
				String tps = ""+temps/3600;
				tps += ":"+(temps%3600)/60;
				tps += ":"+(temps%3600)%60;
				setTitle(tps);
			}
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        progressBar = new javax.swing.JProgressBar();
        jPanel1 = new javax.swing.JPanel();
        labelTitreFichier = new javax.swing.JLabel();
        textFieldFichier = new javax.swing.JTextField();
        labelTitreSolveur = new javax.swing.JLabel();
        labelTitreProbleme = new javax.swing.JLabel();
        labelSolveur = new javax.swing.JLabel();
        labelProbleme = new javax.swing.JLabel();
        boutonDemarrer = new javax.swing.JButton();
        boutonParser = new javax.swing.JButton();
        menuBar = new javax.swing.JMenuBar();
        menuFichiers = new javax.swing.JMenu();
        menuItemCharger = new javax.swing.JMenuItem();
        menuItemVisionneuse = new javax.swing.JMenuItem();
        menuSolveur = new javax.swing.JMenu();
        menuItemRecuit = new javax.swing.JMenuItem();
        menuItemPHA = new javax.swing.JMenuItem();
        menuMinMax = new javax.swing.JMenu();
        menuItemMin = new javax.swing.JCheckBoxMenuItem();
        menuItemMax = new javax.swing.JCheckBoxMenuItem();
        menuProbleme = new javax.swing.JMenu();
        menuItemTSP = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Problem Solveur");

        progressBar.setToolTipText("");
        progressBar.setEnabled(false);

        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        labelTitreFichier.setText("Fichier:");

        textFieldFichier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textFieldFichierActionPerformed(evt);
            }
        });

        labelTitreSolveur.setText("Solveur:");

        labelTitreProbleme.setText("Problème:");

        labelSolveur.setText("minimiser avec: recuit");

        labelProbleme.setText("TSP");

        boutonDemarrer.setText("Démarrer");
        boutonDemarrer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
					boutonDemarrerActionPerformed(evt);
				} catch (ErreurDonneesException e) {
					Afficheur.erreurFataleDialog(e);
				}
            }
        });

        boutonParser.setText("Parser les données");
        boutonParser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boutonParserActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(labelTitreFichier)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textFieldFichier, javax.swing.GroupLayout.DEFAULT_SIZE, 328, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(labelTitreSolveur)
                                .addGap(18, 18, 18)
                                .addComponent(labelSolveur))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(labelTitreProbleme)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(labelProbleme))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(boutonDemarrer)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(boutonParser)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelTitreFichier)
                    .addComponent(textFieldFichier, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelTitreSolveur)
                    .addComponent(labelSolveur))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelTitreProbleme)
                    .addComponent(labelProbleme))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(boutonDemarrer)
                    .addComponent(boutonParser))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        menuFichiers.setText("Fichiers");

        menuItemCharger.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        menuItemCharger.setText("Charger");
        menuItemCharger.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemChargerActionPerformed(evt);
            }
        });
        menuFichiers.add(menuItemCharger);

        menuItemVisionneuse.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        menuItemVisionneuse.setText("Visionneuse");
        menuItemVisionneuse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemVisionneuseActionPerformed(evt);
            }
        });
        menuFichiers.add(menuItemVisionneuse);

        menuBar.add(menuFichiers);

        menuSolveur.setText("Solveur");

        menuItemRecuit.setText("Recuit");
        menuItemRecuit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemRecuitActionPerformed(evt);
            }
        });
        menuSolveur.add(menuItemRecuit);

        menuItemPHA.setText("PHA");
        menuItemPHA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemPHAActionPerformed(evt);
            }
        });
        menuSolveur.add(menuItemPHA);

        menuMinMax.setText("Minimiser/Maximiser");

        menuItemMin.setSelected(true);
        menuItemMin.setText("Minimiser");
        menuItemMin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemMinActionPerformed(evt);
            }
        });
        menuMinMax.add(menuItemMin);

        menuItemMax.setText("Maximiser");
        menuItemMax.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemMaxActionPerformed(evt);
            }
        });
        menuMinMax.add(menuItemMax);

        menuSolveur.add(menuMinMax);

        menuBar.add(menuSolveur);

        menuProbleme.setText("Problème");

        menuItemTSP.setText("TSP");
        menuItemTSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemTSPActionPerformed(evt);
            }
        });
        menuProbleme.add(menuItemTSP);

        menuBar.add(menuProbleme);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(progressBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void menuItemChargerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemChargerActionPerformed
        try{
            JFileChooser jfc;
            if(manager.getFichier() != null && manager.getFichier().exists())
                jfc = new JFileChooser(manager.getFichier());
            else
                jfc = new JFileChooser();
            
            if(jfc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION){
                updateFichier(jfc.getSelectedFile());
            }
        }catch(IGotItException e){}
        catch(Exception e){
            Afficheur.erreurFataleDialog(e);
        }
    }//GEN-LAST:event_menuItemChargerActionPerformed

    private void menuItemRecuitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemRecuitActionPerformed
        manager.setSolveur(new Recuit());
        updateLabelSolveur();
    }//GEN-LAST:event_menuItemRecuitActionPerformed

    private void menuItemMaxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemMaxActionPerformed
        manager.setMinimiser(false);
        menuItemMin.setSelected(false);
        updateLabelSolveur();
    }//GEN-LAST:event_menuItemMaxActionPerformed

    private void textFieldFichierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textFieldFichierActionPerformed
        if(textFieldFichier.getText().isEmpty())
            Afficheur.erreurDialog("Veuillez entrer le chemin vers un fichier de données.");
        else
            updateFichier(new File(textFieldFichier.getText()));
    }//GEN-LAST:event_textFieldFichierActionPerformed

    private void boutonDemarrerActionPerformed(java.awt.event.ActionEvent evt) throws ErreurDonneesException {//GEN-FIRST:event_boutonDemarrerActionPerformed
    	startClock();
    	new Thread(){
    		public void run(){
    			bolocks();
    		}
    	}.start();
    	new Thread(){
    		public void run(){
    			try {
					manager.demarrer();
				} catch (ErreurDonneesException e) {
					Afficheur.erreurDialog("Erreur dans les données");
				}
    		}
    	}.start();
    }//GEN-LAST:event_boutonDemarrerActionPerformed

    public synchronized void startClock(){
    	setTitle("0:0:0");
    	startTime = System.currentTimeMillis();
    	telapsed.start();
    }
    
    public String getClock(){
    	long temps = (System.currentTimeMillis() - startTime)/1000;
		String tps = ""+temps/3600;
		tps += ":"+(temps%3600)/60;
		tps += ":"+(temps%3600)%60;
		return tps;
    }
    
    public synchronized void stopClock(){
    	setTitle("");
    	telapsed.stop();
    }
    
    private void menuItemMinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemMinActionPerformed
        manager.setMinimiser(true);
        menuItemMax.setSelected(false);
        updateLabelSolveur();
    }//GEN-LAST:event_menuItemMinActionPerformed

    private void menuItemPHAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemPHAActionPerformed
        PhaOptions optPha = new PhaOptions(this);
        optPha.setLocationRelativeTo(null);
        optPha.setVisible(true);
        Pha pha = optPha.getPha();
        if(pha != null){
            manager.setSolveur(pha);
        }
        updateLabelSolveur();
    }//GEN-LAST:event_menuItemPHAActionPerformed

    private void menuItemTSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemTSPActionPerformed
        manager.setProbleme(new TSP());
    }//GEN-LAST:event_menuItemTSPActionPerformed

    private void boutonParserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boutonParserActionPerformed
    	bolocks();
    }//GEN-LAST:event_boutonParserActionPerformed

    private void bolocks(){
		new Thread(new Runnable(){

			@Override
			public void run() {
				try{
					Donnees d = manager.getDonnees();
					SwingUtilities.invokeLater(new Runnable(){
						@Override
						public void run() {
							gFrame.showDonnees(GraphFrame.TAB_DONNEES, d, Color.black);
						}
					});
				} catch (FileNotFoundException | NullPointerException e) {
					Afficheur.erreurDialog("Fichier introuvable");
				} catch (ErreurDonneesException e) {
					Afficheur.erreurDialog("Erreur dans les données");
					e.printStackTrace();
				} catch (IOException e) {
					Afficheur.erreurDialog("Erreur de lecture du fichier");
					e.printStackTrace();
				} catch (Exception e){
					Afficheur.erreurFataleDialog(e);
				}
			}
			
		}).start();
    }
    
    private void menuItemVisionneuseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemVisionneuseActionPerformed
        gFrame.setLocationRelativeTo(null);
        gFrame.setVisible(true);
    }//GEN-LAST:event_menuItemVisionneuseActionPerformed

    private void updateLabelSolveur(){
        String txt = (manager.isMinimiser())?"minimiser":"maximiser";
        txt += " avec: ";
        txt+= manager.getSolveur().toString();
        labelSolveur.setText(txt);
    }
    
    private void updateFichier(File fichier){
        if(fichier == null){
            Afficheur.erreurDialog("Le fichier est introuvable.");
            return;
        }
        if(fichier.isFile()){
            manager.setFichier(fichier);
            lastFile = fichier.getAbsolutePath();
        }else{
            Afficheur.erreurDialog("Le fichier "+fichier.getAbsolutePath()+" est introuvable.");
        }
        textFieldFichier.setText(lastFile);
    }
    
    public Manager getManager(){
        return manager;
    }
    
    public GraphFrame getGFrame(){
    	return gFrame;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton boutonDemarrer;
    private javax.swing.JButton boutonParser;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel labelProbleme;
    private javax.swing.JLabel labelSolveur;
    private javax.swing.JLabel labelTitreFichier;
    private javax.swing.JLabel labelTitreProbleme;
    private javax.swing.JLabel labelTitreSolveur;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenu menuFichiers;
    private javax.swing.JMenuItem menuItemCharger;
    private javax.swing.JCheckBoxMenuItem menuItemMax;
    private javax.swing.JCheckBoxMenuItem menuItemMin;
    private javax.swing.JMenuItem menuItemPHA;
    private javax.swing.JMenuItem menuItemRecuit;
    private javax.swing.JMenuItem menuItemTSP;
    private javax.swing.JMenuItem menuItemVisionneuse;
    private javax.swing.JMenu menuMinMax;
    private javax.swing.JMenu menuProbleme;
    private javax.swing.JMenu menuSolveur;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JTextField textFieldFichier;
    // End of variables declaration//GEN-END:variables

}
