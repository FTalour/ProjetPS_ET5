/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import problemsolver.solveur.SAA;
import problemsolver.solveur.Pha;
import problemsolver.solveur.Recuit;
import problemsolver.solveur.RecuitPha;
import problemsolver.solveur.Solveur;

@SuppressWarnings("serial")
public class SaaOptions extends javax.swing.JDialog {
    private boolean OKpressed;
    /**
     * Creates new form NewJFrame
     * @param parent
     */
    public SaaOptions(java.awt.Frame parent) {
        super(parent, true);
        initComponents();
        OKpressed = false;
        spinnerEchantillon.setValue(5);
        pack();
    }
    
    public SaaOptions(javax.swing.JDialog parent) {
        super(parent, true);
        initComponents();
        OKpressed = false;
        spinnerEchantillon.setValue(5);
        pack();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        boutonOK = new javax.swing.JButton();
        boutonAnnuler = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        spinnerEchantillon = new javax.swing.JSpinner();
        jLabel1 = new javax.swing.JLabel();
        sliderEchantillon = new javax.swing.JSlider();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel2 = new javax.swing.JLabel();
        comboBoxSolveurs = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        boutonOK.setText("OK");
        boutonOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boutonOKActionPerformed(evt);
            }
        });

        boutonAnnuler.setText("Annuler");
        boutonAnnuler.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boutonAnnulerActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        spinnerEchantillon.setModel(new javax.swing.SpinnerNumberModel(5, 1, 500, 1));
        spinnerEchantillon.setRequestFocusEnabled(false);

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, sliderEchantillon, org.jdesktop.beansbinding.ELProperty.create("${value}"), spinnerEchantillon, org.jdesktop.beansbinding.BeanProperty.create("value"));
        bindingGroup.addBinding(binding);

        jLabel1.setText("Quantité d'échantillons:");

        sliderEchantillon.setMajorTickSpacing(100);
        sliderEchantillon.setMaximum(500);
        sliderEchantillon.setMinimum(1);
        sliderEchantillon.setMinorTickSpacing(25);
        sliderEchantillon.setPaintTicks(true);
        sliderEchantillon.setToolTipText("");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, spinnerEchantillon, org.jdesktop.beansbinding.ELProperty.create("${value}"), sliderEchantillon, org.jdesktop.beansbinding.BeanProperty.create("value"));
        bindingGroup.addBinding(binding);

        jLabel2.setText("Solveur secondaire à utiliser:");

        comboBoxSolveurs.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "recuit", "PHA" }));
        comboBoxSolveurs.setSelectedItem("Recuit");
        comboBoxSolveurs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboBoxSolveursActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(0, 24, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(sliderEchantillon, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(spinnerEchantillon))
                        .addContainerGap())
                    .addComponent(comboBoxSolveurs, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(spinnerEchantillon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sliderEchantillon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(comboBoxSolveurs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(boutonOK)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(boutonAnnuler)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(boutonOK)
                    .addComponent(boutonAnnuler))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        bindingGroup.bind();

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void boutonOKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boutonOKActionPerformed
        OKpressed = true;
        setVisible(false);
    }//GEN-LAST:event_boutonOKActionPerformed

    private void boutonAnnulerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boutonAnnulerActionPerformed
        OKpressed = false;
        setVisible(false);
    }//GEN-LAST:event_boutonAnnulerActionPerformed

    private void comboBoxSolveursActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboBoxSolveursActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboBoxSolveursActionPerformed
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton boutonAnnuler;
    private javax.swing.JButton boutonOK;
    private javax.swing.JComboBox<String> comboBoxSolveurs;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSlider sliderEchantillon;
    private javax.swing.JSpinner spinnerEchantillon;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    public SAA getSaa() {
        if(OKpressed){
            Solveur second = null;
            switch(comboBoxSolveurs.getItemAt(comboBoxSolveurs.getSelectedIndex())){
                case "recuit":
                    second = new Recuit();
                    break;
                case "PHA":
                    java.awt.event.ActionEvent evt;
                    PhaOptions optPha = new PhaOptions(this);
                    optPha.setLocationRelativeTo(null);
                    optPha.setVisible(true);
                    second = new RecuitPha(optPha.getPha());
                    break;
                default:
                    second = null;
                    break;
            }
            if(second == null){
                Afficheur.erreurDialog("Solveur secondaire incompatible");
                return null;
            }else{
                return new SAA(sliderEchantillon.getValue(), second);
            }
        }
        return null;
    }
}