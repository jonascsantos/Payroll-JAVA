/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import ValidaCPF.validaCPF;
import static java.lang.Float.parseFloat;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.PooledConnection;
import javax.swing.JOptionPane;
import oracle.jdbc.OraclePreparedStatement;
import oracle.jdbc.pool.OracleConnectionPoolDataSource;

/**
 *
 * @author jacks
 */
public class Editar extends javax.swing.JFrame {

    /**
     * Creates new form CadastroFu
     */
    public Editar() {
        initComponents();

    }
    
    public Editar(String CPF) {
        initComponents();
        
        ResultSet rs = null, rs2 = null, rs3 = null;
        OraclePreparedStatement ps = null;
            try{
                OracleConnectionPoolDataSource ds = new OracleConnectionPoolDataSource();
                ds.setURL("jdbc:oracle:thin:@localhost:1521:xe");
                ds.setUser("folhapagamento");
                ds.setPassword("senha");
                PooledConnection pc = ds.getPooledConnection();
                Connection conn=pc.getConnection();
                
                String SQL = "SELECT * from Setor";
                
                ps = (OraclePreparedStatement)conn.prepareStatement(SQL);
                rs = ps.executeQuery();
                
                cpfTxt.setText(CPF);
                cpfTxt.setEditable(false);
                
                if(rs.next()){
                    do{
                        setorSelect.addItem(rs.getString("Nome_S"));
                        
                    }while (rs.next());
                        
                    
                }else{
                    JOptionPane.showMessageDialog(null, "Não existem Setores cadastrados!");
                    
                }
                
                String SQL2 = "SELECT * from funcionario where pk_Cpf_F='" + CPF + "'";
                String SQL3 = "SELECT * from Funcionario_Setor where fk_pk_Cpf_F ='" + CPF + "'";
                String SQL4 = "SELECT * from Funcionario_Cargo where fk_pk_Cpf_F ='" + CPF + "'";
                
                OraclePreparedStatement ps2 = (OraclePreparedStatement)conn.prepareStatement(SQL2);
                OraclePreparedStatement ps3 = (OraclePreparedStatement)conn.prepareStatement(SQL3);
                OraclePreparedStatement ps4 = (OraclePreparedStatement)conn.prepareStatement(SQL4);
                rs2 = ps2.executeQuery();
                rs3 = ps3.executeQuery();
                ResultSet rs4 = ps4.executeQuery();
                
                if(rs2.next()){
                    nomeTxt.setText(rs2.getString("Nome_F"));
                }
                
                if(rs3.next()){
                    String cod = rs3.getString("fk_pk_Cod_S");
                    String SQL5 = "SELECT * from Setor where Cod_S ='" + cod + "'";
                    System.out.println(SQL5);
                    OraclePreparedStatement ps5 = (OraclePreparedStatement)conn.prepareStatement(SQL5);
                    ResultSet rs5 = ps5.executeQuery();
                    if(rs5.next())
                         setorSelect.setSelectedItem(rs5.getString("Nome_S")); 
                }
                if(rs4.next()){
                    String cod = rs4.getString("fk_pk_Cod_C");
                    String SQL5 = "SELECT * from Cargo where Cod_C ='" + cod + "'";
                    System.out.println(SQL5);
                    OraclePreparedStatement ps5 = (OraclePreparedStatement)conn.prepareStatement(SQL5);
                    ResultSet rs5 = ps5.executeQuery();
                    if(rs5.next()){
                        cargoTxt.setText(rs5.getString("Nome_C"));
                        cargaTxt.setText(rs5.getString("Carga_horaria"));
                        salarioTxt.setText(rs5.getString("Valor_base"));
                        vinculoSelect.setSelectedItem(rs5.getString("vinculo"));
                        valorhoraTxt.setText(rs5.getString("Valor_horaTrab"));
                    
                    }
                }
               
                
                try {
                    ps.close();
                    conn.close();
                    Thread.sleep(500);
                }
                catch(Exception e) { }
                }catch(Exception ex){
                }
   
        //this.setSize(xsize, ysize);

        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        nomeTxt = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        cpfTxt = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        cargaTxt = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        vinculoSelect = new javax.swing.JComboBox();
        jLabel6 = new javax.swing.JLabel();
        setorSelect = new javax.swing.JComboBox();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        valorhoraTxt = new javax.swing.JTextField();
        cargoTxt = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        salarioTxt = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        nomeTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nomeTxtActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setText("Nome:");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setText("CPF:");

        cpfTxt.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel3.setText("Cargo:");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel4.setText("Setor:");

        jLabel5.setFont(new java.awt.Font("Bariol Regular", 1, 24)); // NOI18N
        jLabel5.setText("Editar Cadastro de Funcionário");

        jButton4.setText("Salvar Cadastro");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        vinculoSelect.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Efetivo", "Estagiário", "Temporário" }));
        vinculoSelect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                vinculoSelectActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel6.setText("Vínculo:");

        setorSelect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                setorSelectActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel8.setText("Carga:");

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel9.setText("Valor/Hora:");

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel10.setText("Salário:");

        jButton1.setText("Alterar Adicionais");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 82, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(nomeTxt, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cargoTxt, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(valorhoraTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(salarioTxt, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jButton1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton4))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 82, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(cargaTxt, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 213, Short.MAX_VALUE)
                                    .addComponent(cpfTxt, javax.swing.GroupLayout.Alignment.TRAILING)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel4)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(setorSelect, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel6)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(vinculoSelect, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(58, 58, 58))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel10)
                                    .addComponent(jLabel9))
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel5)
                .addGap(43, 43, 43)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(nomeTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(cpfTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(cargoTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(setorSelect, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(vinculoSelect, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(cargaTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(valorhoraTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(salarioTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 54, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton4)
                    .addComponent(jButton1))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 108, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 120, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(42, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void nomeTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nomeTxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nomeTxtActionPerformed

   
    
    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        //
        ResultSet rs = null, rs2 = null;
        OraclePreparedStatement ps = null, ps2 = null, ps3 = null, ps4 = null, ps5 = null, ps6 = null, ps7 = null;
        
        String nome = nomeTxt.getText();
        String CPF = cpfTxt.getText();
        String cargo = cargoTxt.getText();
        String carga = cargaTxt.getText();
        String valorHora = valorhoraTxt.getText();
        String valorBase = salarioTxt.getText();
        String setor = (String) setorSelect.getSelectedItem();
        String vinculo = (String) vinculoSelect.getSelectedItem();
        
        if("".equals(CPF) || "".equals(nome) || "".equals(cargo) || "".equals(carga) || "".equals(valorHora)){
            JOptionPane.showMessageDialog(null, "Preencha todos os campos!");
        }else{
                
                
                
                String SQL = "UPDATE funcionario SET Nome_F = '" + nome + "' where pk_Cpf_F = '"+ CPF +"'";
                
                String SQLSearch = "select * from Funcionario_Cargo where fk_pk_Cpf_F = '"+CPF+"'";
                //if(CARGOnew == CARGOold)
                //ELSE
                //INSERT CARGO
                String SQLSearch2 = "select * from setor where nome_s = '"+setor+"'";
                
                
                

                try{
                    OracleConnectionPoolDataSource ds = new OracleConnectionPoolDataSource();
                    ds.setURL("jdbc:oracle:thin:@localhost:1521:xe");
                    ds.setUser("folhapagamento");
                    ds.setPassword("senha");
                    PooledConnection pc = ds.getPooledConnection();
                    Connection conn=pc.getConnection();

                    ps = (OraclePreparedStatement)conn.prepareStatement(SQL);
                    ps2 = (OraclePreparedStatement)conn.prepareStatement(SQLSearch);

                    ps.executeUpdate();

                    rs2 = ps2.executeQuery();
                    if(rs2.next()){
                        String SQLSearch3 = "select * from cargo where Cod_C = '"+rs2.getString("fk_pk_Cod_C")+"'";
                        ps3 = (OraclePreparedStatement)conn.prepareStatement(SQLSearch3);
                        ResultSet rs3 = ps3.executeQuery();

                        if(rs3.next()){
                                String SQL3 = "Update cargo SET Nome_C = '"+cargo+"',Valor_base = "+valorBase+" ,Valor_horaTrab = "+valorHora+" ,Carga_horaria = "+carga+", vinculo = '"+vinculo+"' WHERE Cod_C = "+rs3.getString("Cod_C")+"";
                                ps3 = (OraclePreparedStatement)conn.prepareStatement(SQL3);
                                ps3.executeUpdate();    
                        }
                    }
                    
                    ps3 = (OraclePreparedStatement)conn.prepareStatement(SQLSearch2);
                    ResultSet rs3 = ps3.executeQuery();
                    if(rs3.next()){
                        String SQL4 = "Update funcionario_setor SET fk_pk_Cod_S = '"+rs3.getString("Cod_S")+"' where fk_pk_Cpf_F = "+CPF+"";
                        ps4 = (OraclePreparedStatement)conn.prepareStatement(SQL4);
                        ps4.executeUpdate();
                    }
                    
                    
                    try {
                            ps.close();
                            conn.close();
                            Thread.sleep(500);
                        }
                        catch(Exception e) { }
                    }catch(SQLException e){
                        System.out.println("Error no sql la " + e);
                    } 


                    String[] opcoes = {"Fechar"};
                    JOptionPane.showOptionDialog(null, "Dados salvos com sucesso.", "Edição de Cadastro", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, opcoes, opcoes[0]);
                    this.dispose();
            
                
            
        }
        
      
        
    }//GEN-LAST:event_jButton4ActionPerformed

    private void vinculoSelectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_vinculoSelectActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_vinculoSelectActionPerformed

    private void setorSelectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_setorSelectActionPerformed
        
    }//GEN-LAST:event_setorSelectActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            new Adicionais(cpfTxt.getText()).setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(Editar.class.getName()).log(Level.SEVERE, null, ex);
        }
        dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Editar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Editar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Editar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Editar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Editar().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JTextField cargaTxt;
    private javax.swing.JTextField cargoTxt;
    private javax.swing.JTextField cpfTxt;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField nomeTxt;
    private javax.swing.JTextField salarioTxt;
    private javax.swing.JComboBox setorSelect;
    private javax.swing.JTextField valorhoraTxt;
    private javax.swing.JComboBox vinculoSelect;
    // End of variables declaration//GEN-END:variables
}
