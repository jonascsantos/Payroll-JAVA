/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Toolkit;
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
 * @author jonas
 */
public class Principal extends javax.swing.JFrame {

    
    
    
    /**
     * Creates new form Principal
     */
    public Principal() {
        //this.setUndecorated(true);
        //this.setAlwaysOnTop(true);
        this.setResizable(false);
        this.setVisible(true);
        initComponents();
        Toolkit tk = Toolkit.getDefaultToolkit();
        int xsize = (int) tk.getScreenSize().getWidth();
        int ysize = (int) tk.getScreenSize().getHeight();
        //this.setSize(xsize, ysize);

    }
    
    
    
    
   public class FuncionariobyCPF{
        ResultSet rs = null;
        OraclePreparedStatement ps = null;
        public ResultSet find(String s, Connection conn){
            try{
     
                String SQL = "select * from funcionario where PK_CPF_F = ?";
                
                ps = (OraclePreparedStatement)conn.prepareStatement(SQL);                             
                ps.setString(1, s);
                rs = ps.executeQuery();
                
               
            } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
            } catch (Exception e) {
            }
            
                
            
            return rs;
        }
    }
    
    public class PagamentobyCPF{
        ResultSet rs = null;
        OraclePreparedStatement ps = null;
        public ResultSet find(String s, Connection conn){
            try{
                

                String SQL = "select * from pagamento where FK_PK_CPF_F = ?";
                
                ps = (OraclePreparedStatement)conn.prepareStatement(SQL);                             
                ps.setString(1, s);
                rs = ps.executeQuery();
                
                
            } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
            } catch (Exception e) {
                System.out.println(e);
            }
            
            return rs;
        } 
    }
    
     public class FuncCargobyCPF{
        ResultSet rs = null;
        OraclePreparedStatement ps = null;
        public ResultSet find(String s, Connection conn){
            try{
                

                String SQL = "select * from Funcionario_Cargo where FK_PK_CPF_F = ? and DATA_FIM is null";
                
                ps = (OraclePreparedStatement)conn.prepareStatement(SQL);                             
                ps.setString(1, s);
                rs = ps.executeQuery();
                
                
            } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
            } catch (Exception e) {
            }
            return rs;
        } 
    }
    
    public class CargobyID{
        ResultSet rs = null;
        OraclePreparedStatement ps = null;
        public ResultSet find(String s, Connection conn){
            try{
              

                String SQL = "select * from Cargo where Cod_C = ?";
                
                ps = (OraclePreparedStatement)conn.prepareStatement(SQL);                             
                ps.setString(1, s);
                rs = ps.executeQuery();
                
                 
            } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
            } catch (Exception e) {
            }
            return rs;
        } 
       
    }
    
   public float INSS(Float SalarioBruto){
        float totalInss;
        
        if(SalarioBruto <= 1556.94)
            totalInss = 0.08f * SalarioBruto;
        else if(SalarioBruto <= 2594.92)
            totalInss = 0.09f * SalarioBruto;
        else if(SalarioBruto <= 5189.82)
            totalInss = 0.11f * SalarioBruto;
        else
            totalInss = 570.88f;
        return totalInss;
    }
    
    public float FGTS(float SalarioBruto){
        return SalarioBruto*0.08f;
    }
    
    public void calcImpostos(String CPF) throws SQLException{
        String cod = null, horatrab = null, AdComissao_P = null, AdInsalubridade_P = null, AdPericulosidade_P = null;
        Float SalarioBase = 0f, ValorHora = 0f, HoraExtraReais = 0f, inss = 0f, fgts = 0f, SalarioBruto = 0f;
        ResultSet rs = null, rs2 = null, rs3 = null, rs4 = null;
        
        FuncionariobyCPF f = new FuncionariobyCPF();
        PagamentobyCPF f2 = new PagamentobyCPF();
        FuncCargobyCPF f3 = new FuncCargobyCPF();
        CargobyID f4 = new CargobyID();
        
        OracleConnectionPoolDataSource ds = new OracleConnectionPoolDataSource();
                ds.setURL("jdbc:oracle:thin:@localhost:1521:xe");
                ds.setUser("folhapagamento");
                ds.setPassword("senha");
                PooledConnection pc = ds.getPooledConnection();
                Connection conn=pc.getConnection();
        
        
        rs = f.find(CPF, conn);
        rs2 = f2.find(CPF, conn);
        rs3 = f3.find(CPF, conn);
        try{
            if(rs2.next()){
                horatrab = rs2.getString("HORASEXTRATRABALHADAS");
                AdComissao_P = rs2.getString("AdComissao_P");
                AdInsalubridade_P = rs2.getString("AdInsalubridade_P");
                AdPericulosidade_P = rs2.getString("AdPericulosidade_P");
            }
            if(rs3.next()){
                cod = rs3.getString("fk_pk_Cod_C");
                rs4 = f4.find(cod, conn); 
            }
            if(rs4.next()){
                    SalarioBase= parseFloat(rs4.getString("Valor_base"));
                    ValorHora = parseFloat(rs4.getString("Valor_horaTrab"));   
             }
            
            if(horatrab != null){
                  HoraExtraReais = (ValorHora + ValorHora * 0.5f) * parseFloat(horatrab) ;  //adicional de 50% do valor da hora extra.
                  System.out.println(HoraExtraReais);
            }
            
            SalarioBruto = SalarioBase + HoraExtraReais;
            
            if(AdComissao_P != null){
                SalarioBruto = SalarioBruto + parseFloat(AdComissao_P);
            }
            if(AdPericulosidade_P != null){
                SalarioBruto = SalarioBruto + parseFloat(AdPericulosidade_P);
            }
            if(AdInsalubridade_P != null){
                SalarioBruto = SalarioBruto + parseFloat(AdInsalubridade_P);
            }
            if(AdComissao_P != null){
                SalarioBruto = SalarioBruto + parseFloat(AdComissao_P);
            }
            
            inss = INSS(SalarioBruto);
            fgts = FGTS(SalarioBruto);
            System.out.println(SalarioBruto);
            System.out.println(inss);
            System.out.println(fgts);
            
            
           
            
            updatePagamento("Fgts_P", CPF, fgts, conn);
            updatePagamento("INSS_P", CPF, inss, conn);
            updatePagamento("AdHoraExtra_P", CPF, HoraExtraReais, conn);
            
                 try {
                    rs.close();
                    pc.close();
                    conn.close();
                    Thread.sleep(500);
                }
                catch(Exception e) { }
            
            
            
            /*resultSetClose(rs);
            resultSetClose(rs2);
            resultSetClose(rs3);
            resultSetClose(rs4);
           */
            
            //JOptionPane.showMessageDialog(null, "Salario: "+SalarioBase +" Valor hora: "+ ValorHora );
        }catch(Exception ex){
        }
        
        
        
    
    }
    
    public void updatePagamento(String Campo, String CPF, Float Dado, Connection conn) throws SQLException{
        ResultSet rs = null;
        OraclePreparedStatement ps = null;
        try{                 
            String SQL = "UPDATE pagamento SET " + Campo + " = ? where FK_PK_CPF_F ='" + CPF + "'";

            ps = (OraclePreparedStatement)conn.prepareStatement(SQL);  
            ps.setFloat(1, Dado);
            rs = ps.executeQuery();
            
            
            
        } catch (SQLException e) {
        System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        } 
        finally {
            if (rs != null)
              rs.close ();
            if (ps != null)
              ps.close ();
         }
        
        
        
        
    }
    
    
    public boolean SearchCPF(String CPF) {
        ResultSet rs = null;
        OraclePreparedStatement ps = null;
            try{
                OracleConnectionPoolDataSource ds = new OracleConnectionPoolDataSource();
                ds.setURL("jdbc:oracle:thin:@localhost:1521:xe");
                ds.setUser("folhapagamento");
                ds.setPassword("senha");
                PooledConnection pc = ds.getPooledConnection();
                Connection conn=pc.getConnection();
                
                String SQL = "SELECT * from pagamento where FK_PK_CPF_F ='" + CPF + "'";
                
                ps = (OraclePreparedStatement)conn.prepareStatement(SQL);
                rs = ps.executeQuery();
                if(rs.next()){
                    //JOptionPane.showMessageDialog(null, "CPF encontrado");
                    return true;
                }else{
                    JOptionPane.showMessageDialog(null, "Não existe folha de pagamento referente ao CPF informado");
                }
                 try {
                    ps.close();
                    conn.close();
                    Thread.sleep(500);
                }
                catch(Exception e) { }
                
            } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
            } catch (Exception e) {
            }
            return false;
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnDesconto = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        btnAdicional = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        btnDesconto1 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        btnDesconto2 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        btnDesconto3 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        btnDesconto4 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        btnDesconto.setText("Pagamento INSS/FGTS");
        btnDesconto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDescontoActionPerformed(evt);
            }
        });

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/logo-inss-previdencia-social2.png"))); // NOI18N

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/adicional.png"))); // NOI18N

        btnAdicional.setText("Bônus");
        btnAdicional.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdicionalActionPerformed(evt);
            }
        });

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/funcionario.png"))); // NOI18N

        btnDesconto1.setText("Cadastro de Funcionários");
        btnDesconto1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDesconto1ActionPerformed(evt);
            }
        });

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/setor.png"))); // NOI18N

        btnDesconto2.setText("Cadastro de Setores");
        btnDesconto2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDesconto2ActionPerformed(evt);
            }
        });

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/contracheque.png"))); // NOI18N

        btnDesconto3.setText("Contracheque");
        btnDesconto3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDesconto3ActionPerformed(evt);
            }
        });

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/RelatorioSetor.png"))); // NOI18N

        btnDesconto4.setText("Relatório de Pagamento por Setor");
        btnDesconto4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDesconto4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(249, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(55, 55, 55)
                                .addComponent(btnDesconto1)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(88, 88, 88)
                                .addComponent(btnDesconto2))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(28, 28, 28)
                                .addComponent(jLabel4)))
                        .addGap(11, 11, 11)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jLabel5))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(101, 101, 101)
                                .addComponent(btnDesconto3))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(55, 55, 55)
                                .addComponent(btnDesconto)))
                        .addGap(37, 37, 37)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(103, 103, 103)
                                .addComponent(btnAdicional)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jLabel6))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(45, 45, 45)
                                .addComponent(btnDesconto4)))))
                .addGap(242, 242, 242))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(159, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel3)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(btnDesconto1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(241, 241, 241)
                                    .addComponent(btnDesconto2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnDesconto3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(80, 80, 80)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnAdicional, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnDesconto, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnDesconto4, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(78, 78, 78))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnAdicionalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdicionalActionPerformed
        // TODO add your handling code here:
        String CPF = null;
            do {
                CPF = JOptionPane.showInputDialog( "Informe o CPF");
                if (CPF == null) {
                    JOptionPane.showMessageDialog(null, "Ação cancelada");
                    break;
                } else {
                    if (validaCPF.isCPF(CPF) == true){
                        if(SearchCPF(CPF)){
                            Adicionais s;
                            try {
                                s = new Adicionais(CPF);
                                s.setVisible(true);
                            } catch (SQLException ex) {
                                Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            
                            dispose();
                        }
                    }else 
                        JOptionPane.showMessageDialog(null, "Erro, CPF invalido !!!");
       
                }   
            }while (validaCPF.isCPF(CPF) == false);
        
        
    }//GEN-LAST:event_btnAdicionalActionPerformed

    private void btnDescontoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDescontoActionPerformed
        // TODO add your handling code here:

            String CPF = null;
            do {
                CPF = JOptionPane.showInputDialog( "Informe o CPF");
                if (CPF == null) {
                    JOptionPane.showMessageDialog(null, "Ação cancelada");
                    break;
                } else {
                    if (validaCPF.isCPF(CPF) == true){
                        if(SearchCPF(CPF)){
                            pagamento s;
                            try {
                                s = new pagamento(CPF);
                                s.setVisible(true);

                            } catch (SQLException ex) {
                                Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            dispose();
                        }
                    }else 
                        JOptionPane.showMessageDialog(null, "Erro, CPF invalido !!!");
       
                }   
            }while (validaCPF.isCPF(CPF) == false);

    }//GEN-LAST:event_btnDescontoActionPerformed

    private void btnDesconto1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDesconto1ActionPerformed
        new cadastroFuncionario().setVisible(true);
    }//GEN-LAST:event_btnDesconto1ActionPerformed

    private void btnDesconto2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDesconto2ActionPerformed
        new CadastroSetor().setVisible(true);

    }//GEN-LAST:event_btnDesconto2ActionPerformed

    private void btnDesconto3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDesconto3ActionPerformed
        String CPF = null;
            do {
                CPF = JOptionPane.showInputDialog( "Informe o CPF");
                if (CPF == null) {
                    JOptionPane.showMessageDialog(null, "Ação cancelada");
                    break;
                } else {
                    if (validaCPF.isCPF(CPF) == true){
                        if(SearchCPF(CPF)){
                            Adicionais s;
                            new Contracheque(CPF).setVisible(true);
                            //dispose();
                        }
                    }else 
                        JOptionPane.showMessageDialog(null, "Erro, CPF invalido !!!");
       
                }   
            }while (validaCPF.isCPF(CPF) == false);
        
        
        
        // TODO add your handling code here:
    }//GEN-LAST:event_btnDesconto3ActionPerformed

    private void btnDesconto4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDesconto4ActionPerformed
        new Relatorio().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_btnDesconto4ActionPerformed

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
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Principal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdicional;
    private javax.swing.JButton btnDesconto;
    private javax.swing.JButton btnDesconto1;
    private javax.swing.JButton btnDesconto2;
    private javax.swing.JButton btnDesconto3;
    private javax.swing.JButton btnDesconto4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    // End of variables declaration//GEN-END:variables
}
