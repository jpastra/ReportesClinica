/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;
import conexion.Conexiondb;
import events.Export;
import java.awt.Frame;
import java.util.List;
import java.awt.print.PrinterException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import events.Exporter;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.io.File;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.UIManager;


/**
 * @author Jessica Viviana Pastrana Munguia
 */
public class jfCajaExpCve extends javax.swing.JFrame {
ResultSet rs;
private PBcDescExp task01Inf;
private PBcDescCve task02Inf;
private PBcDescExpCve task03Inf;

    
    /**
     * Creates new form jfVacunas
     */
    public jfCajaExpCve() {
        initComponents();
        setLocationRelativeTo(null);
        //setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(false);
        jl_titulo.setHorizontalAlignment(JLabel.CENTER);
        jl_titulo.setForeground(Color.white);
        jl_titulo.setOpaque(true);
        jl_etiqueta.setVisible(false);
        jp_bar.setVisible(false);
    }
    
    public void cDescExp() throws SQLException {

        rs = Conexiondb.Consulta("SELECT A.PACNEXP AS EXPEDIENTE, A.PACNOM AS NOMBRE, B.PRECVE AS CLAVE, B.DSCTO AS DESCUENTO FROM PACCAT A, DESPAC B WHERE A.PACNEXP = B.PACNEXP AND B.DSCTO > 0  AND A.PACNEXP = '"+jt_expediente.getText()+"' ORDER BY A.PACNEXP");
        
        ResultSetMetaData rsmt = rs.getMetaData();
        int numCol = rsmt.getColumnCount();

        //Creamos el objeto del modelo de la tabla
        DefaultTableModel modelo = new DefaultTableModel();
        this.jt_consulta_vacunas.setModel(modelo);

        if (rs.next() == false) {
            JOptionPane.showMessageDialog(null, "No hay datos por mostrar", "Mensage", JOptionPane.INFORMATION_MESSAGE);
            tt_rec();
        } else {

            //Agregar el nombre de las columnas
            for (int x = 1; x <= numCol; x++) {
                modelo.addColumn(rsmt.getColumnLabel(x));
            }

            do {
                //Agregar los renglones
                Object[] fila = new Object[numCol];

                for (int y = 0; y < numCol; y++) {
                    fila[y] = rs.getString(y + 1);
                }

                modelo.addRow(fila);
            } while (rs.next());

            rs.close();
            tt_rec();
        }
    }
    
    public void cDescCve() throws SQLException {

        rs = Conexiondb.Consulta("SELECT A.PACNEXP AS EXPEDIENTE, A.PACNOM AS NOMBRE, B.PRECVE AS CLAVE, B.DSCTO AS DESCUENTO FROM PACCAT A, DESPAC B WHERE  A.PACNEXP = B.PACNEXP AND PRECVE IN ("+jt_cve.getText()+") AND DSCTO > 0 ORDER BY A.PACNEXP");
        
        ResultSetMetaData rsmt = rs.getMetaData();
        int numCol = rsmt.getColumnCount();

        //Creamos el objeto del modelo de la tabla
        DefaultTableModel modelo = new DefaultTableModel();
        this.jt_consulta_vacunas.setModel(modelo);

        if (rs.next() == false) {
            JOptionPane.showMessageDialog(null, "No hay datos por mostrar", "Mensage", JOptionPane.INFORMATION_MESSAGE);
            tt_rec();
        } else {

            //Agregar el nombre de las columnas
            for (int x = 1; x <= numCol; x++) {
                modelo.addColumn(rsmt.getColumnLabel(x));
            }

            do {
                //Agregar los renglones
                Object[] fila = new Object[numCol];

                for (int y = 0; y < numCol; y++) {
                    fila[y] = rs.getString(y + 1);
                }

                modelo.addRow(fila);
            } while (rs.next());

            rs.close();
            tt_rec();
        }
    }
    
    public void cDescExpCve() throws SQLException {

        rs = Conexiondb.Consulta("SELECT A.PACNEXP AS EXPEDIENTE, A.PACNOM AS NOMBRE, B.PRECVE AS CLAVE, B.DSCTO AS DESCUENTO FROM PACCAT A, DESPAC B WHERE  A.PACNEXP = B.PACNEXP AND A.PACNEXP = '"+jt_expediente.getText()+"' AND PRECVE IN ("+jt_cve.getText()+") AND DSCTO > 0 ORDER BY A.PACNEXP");
        
        ResultSetMetaData rsmt = rs.getMetaData();
        int numCol = rsmt.getColumnCount();

        //Creamos el objeto del modelo de la tabla
        DefaultTableModel modelo = new DefaultTableModel();
        this.jt_consulta_vacunas.setModel(modelo);

        if (rs.next() == false) {
            JOptionPane.showMessageDialog(null, "No hay datos por mostrar", "Mensage", JOptionPane.INFORMATION_MESSAGE);
            tt_rec();
        } else {

            //Agregar el nombre de las columnas
            for (int x = 1; x <= numCol; x++) {
                modelo.addColumn(rsmt.getColumnLabel(x));
            }

            do {
                //Agregar los renglones
                Object[] fila = new Object[numCol];

                for (int y = 0; y < numCol; y++) {
                    fila[y] = rs.getString(y + 1);
                }

                modelo.addRow(fila);
            } while (rs.next());

            rs.close();
            tt_rec();
        }
    }
    
          
    public void tt_rec() {
        int tt_reg = this.jt_consulta_vacunas.getRowCount();
        int ttRow = tt_reg;
        if (ttRow > 0) {
            this.jl_tt_reg.setText("Total Registros: " + ttRow);
        } else {
            int value = 0;
            this.jl_tt_reg.setText("Total Registros: " + value);
        }
    }
    
  

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        gruposiopt = new javax.swing.ButtonGroup();
        jl_titulo = new javax.swing.JLabel();
        btnConsulta = new javax.swing.JButton();
        btn_salir = new javax.swing.JButton();
        btn_imprimir = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jt_consulta_vacunas = new javax.swing.JTable();
        jl_tt_reg = new javax.swing.JLabel();
        btn_menu_principal = new javax.swing.JButton();
        btn_exportar = new javax.swing.JButton();
        btn_limpiar = new javax.swing.JButton();
        btn_reporte = new javax.swing.JButton();
        jl_expediente = new javax.swing.JLabel();
        jt_expediente = new javax.swing.JTextField();
        jl_cve = new javax.swing.JLabel();
        jt_cve = new javax.swing.JTextField();
        jl_etiqueta = new javax.swing.JLabel();
        jp_bar = new javax.swing.JProgressBar();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Reporte De Descuentos por Expediente / Clave");

        jl_titulo.setBackground(new java.awt.Color(51, 102, 255));
        jl_titulo.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jl_titulo.setForeground(new java.awt.Color(255, 255, 255));
        jl_titulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jl_titulo.setText("REPORTE DE DESCUENTOS POR EXPEDIENTE / CLAVE");
        jl_titulo.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(0, 51, 204)));

        btnConsulta.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnConsulta.setMnemonic('o');
        btnConsulta.setText("Consulta");
        btnConsulta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConsultaActionPerformed(evt);
            }
        });

        btn_salir.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btn_salir.setMnemonic('s');
        btn_salir.setText("Salir");
        btn_salir.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_salir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_salirActionPerformed(evt);
            }
        });

        btn_imprimir.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btn_imprimir.setMnemonic('i');
        btn_imprimir.setText("Imprimir");
        btn_imprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_imprimirActionPerformed(evt);
            }
        });

        jt_consulta_vacunas = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex){
                return false;
            }
        };
        jt_consulta_vacunas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                ""
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jt_consulta_vacunas);

        jl_tt_reg.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jl_tt_reg.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jl_tt_reg.setText("Total Registros :");

        btn_menu_principal.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btn_menu_principal.setMnemonic('m');
        btn_menu_principal.setText("Menu Principal");
        btn_menu_principal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_menu_principalActionPerformed(evt);
            }
        });

        btn_exportar.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btn_exportar.setMnemonic('e');
        btn_exportar.setText("Exportar a Excel");
        btn_exportar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_exportarActionPerformed(evt);
            }
        });

        btn_limpiar.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btn_limpiar.setMnemonic('l');
        btn_limpiar.setText("Limpiar");
        btn_limpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_limpiarActionPerformed(evt);
            }
        });

        btn_reporte.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btn_reporte.setMnemonic('g');
        btn_reporte.setText("Generar Reporte");
        btn_reporte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_reporteActionPerformed(evt);
            }
        });

        jl_expediente.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jl_expediente.setText("Expediente: ");

        jt_expediente.setMinimumSize(new java.awt.Dimension(6, 25));
        jt_expediente.setPreferredSize(new java.awt.Dimension(6, 30));
        jt_expediente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jt_expedienteKeyPressed(evt);
            }
        });

        jl_cve.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jl_cve.setText("Clave:");

        jt_cve.setPreferredSize(new java.awt.Dimension(6, 30));
        jt_cve.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jt_cveKeyPressed(evt);
            }
        });

        jl_etiqueta.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jl_etiqueta.setText("Procesando.....");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jl_titulo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnConsulta, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_imprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_exportar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_reporte)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_limpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_menu_principal)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jl_tt_reg, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane1)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jl_etiqueta)
                                .addGap(18, 18, 18)
                                .addComponent(jp_bar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(16, 16, 16))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jl_expediente)
                                .addGap(18, 18, 18)
                                .addComponent(jt_expediente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jl_cve)
                                .addGap(18, 18, 18)
                                .addComponent(jt_cve, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(518, 518, 518)
                        .addComponent(btn_salir, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(24, 24, 24))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jl_titulo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(65, 65, 65)
                        .addComponent(btn_salir, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jl_expediente)
                            .addComponent(jt_expediente, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(26, 26, 26)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jt_cve, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jl_cve))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnConsulta, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_imprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_exportar, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_reporte, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_limpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_menu_principal, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jl_tt_reg, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(16, 16, 16)))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 326, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jp_bar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jl_etiqueta))
                .addGap(21, 21, 21))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_salirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_salirActionPerformed
        System.exit(0);
    }//GEN-LAST:event_btn_salirActionPerformed

    private void btn_imprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_imprimirActionPerformed

        try{
            MessageFormat headerformat = new MessageFormat("Reporte de Dsc Exped Cve"); //encabezado
            MessageFormat footerformat = new MessageFormat("Page - {0}"); //
            jt_consulta_vacunas.print(JTable.PrintMode.FIT_WIDTH, headerformat, footerformat);  //imprime la JTable
        } catch (PrinterException ex) {
        Logger.getLogger(jfCajaExpCve.class.getName()).log(Level.SEVERE, null, ex);
    }
    }//GEN-LAST:event_btn_imprimirActionPerformed

    private void btn_menu_principalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_menu_principalActionPerformed
        dispose();
        new MenuPrincipal().setVisible(true);
    }//GEN-LAST:event_btn_menu_principalActionPerformed

    private void btn_exportarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_exportarActionPerformed
       if(this.jt_consulta_vacunas.getRowCount() == 0)
       {
           JOptionPane.showMessageDialog(null, "No hay datos por exportar", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
           return;
       }
       
       //Creacion del archivo en Excel
       int totalR = jt_consulta_vacunas.getRowCount();
       JFileChooser chooser = new JFileChooser();
       FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos de Excel", ".xls");
       File route = new File("C:/Reportescap/Reportesexcel");
       chooser.setFileFilter(filter);
       chooser.setDialogTitle("Salvar Archivo");
       chooser.setMultiSelectionEnabled(false);
       chooser.setAcceptAllFileFilterUsed(false);
       chooser.setCurrentDirectory(route);
       if(chooser.showSaveDialog(null)==JFileChooser.APPROVE_OPTION){
           List<JTable> tb = new ArrayList<>();
           List<String>nom = new ArrayList<>();
           tb.add(jt_consulta_vacunas);
           nom.add("Reporte de Dsc Exped Cve");
           String file = chooser.getSelectedFile().toString().concat(".xls");
           //Aqui se ejecuta el metodo
           try{
               Exporter e = new Exporter(new File(file),tb,nom);
               if(e.export())
               {
                   JOptionPane.showMessageDialog(null, "Total de Registros exportados a Excel: " + totalR, "Reporte de Dsc Exped Cve", JOptionPane.INFORMATION_MESSAGE);
               }
           }
           catch(Exception e)
           {
             JOptionPane.showMessageDialog(null, "Hubo un error al exportar" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
           }
       }
    }//GEN-LAST:event_btn_exportarActionPerformed

    private void btn_limpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_limpiarActionPerformed
        jt_expediente.setText("");
        jt_cve.setText("");
        DefaultTableModel dTable = new DefaultTableModel();
        this.jt_consulta_vacunas.setModel(dTable);
        while(dTable.getRowCount()>0){
            dTable.removeRow(0);
        }
        this.jl_tt_reg.setText("Total Registros: ");
    }//GEN-LAST:event_btn_limpiarActionPerformed

    private void btn_reporteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_reporteActionPerformed
        if (this.jt_consulta_vacunas.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Ningun Dato por Exportar a PDF", "Mensage", JOptionPane.INFORMATION_MESSAGE);
            return;
        } else {
            int totalR = jt_consulta_vacunas.getRowCount();
            JOptionPane.showMessageDialog(null, "Creando un archivo PDF con " + totalR + " Registros", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
            JFileChooser chooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("PDF Files", ".pdf");
            File route = new File("C:/Reportescap/Reportespdf");
            chooser.setFileFilter(filter);
            chooser.setDialogTitle("Salvar Archivo");
            chooser.setMultiSelectionEnabled(false);
            chooser.setAcceptAllFileFilterUsed(false);
            chooser.setCurrentDirectory(route);
            if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                List<JTable> tb = new ArrayList<>();
                List<String> nom = new ArrayList<>();
                tb.add(jt_consulta_vacunas);
                nom.add("DescExpedCve");
                String file = chooser.getSelectedFile().toString().concat(".pdf");

                //Aqui se ejecuta el metodo
                try {
                    Export ex = new Export(new File(file), tb, nom);
                    if (ex.export()) {
                        JOptionPane.showMessageDialog(null, "Archivo Generado");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }//GEN-LAST:event_btn_reporteActionPerformed

    private void btnConsultaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultaActionPerformed
        if(jt_expediente.getText().isEmpty() && jt_cve.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "Ingresa un numero de Expediente, una Clave o ambos");
        }else if(jt_expediente.getText().length() > 0 && jt_cve.getText().isEmpty()){
            task01Inf = new PBcDescExp();
             task01Inf.execute();
        }else if(jt_expediente.getText().isEmpty() && jt_cve.getText().length() > 0){
            task02Inf = new PBcDescCve();
             task02Inf.execute();
        }else if(jt_expediente.getText().length() > 0 && jt_cve.getText().length() > 0){
            task03Inf = new PBcDescExpCve();
            task03Inf.execute();
        }
    }//GEN-LAST:event_btnConsultaActionPerformed

    private void jt_expedienteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jt_expedienteKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (jt_expediente.getText().isEmpty() && jt_cve.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Ingresa un numero de Expediente, una Clave o ambos");
            } else if (jt_expediente.getText().length() > 0 && jt_cve.getText().isEmpty()) {
                task01Inf = new PBcDescExp();
                task01Inf.execute();
            } else if (jt_expediente.getText().isEmpty() && jt_cve.getText().length() > 0) {
                task02Inf = new PBcDescCve();
                task02Inf.execute();
            } else if (jt_expediente.getText().length() > 0 && jt_cve.getText().length() > 0) {
                task03Inf = new PBcDescExpCve();
                task03Inf.execute();
            }
        }
    }//GEN-LAST:event_jt_expedienteKeyPressed

    private void jt_cveKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jt_cveKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (jt_expediente.getText().isEmpty() && jt_cve.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Ingresa un numero de Expediente, una Clave o ambos");
            } else if (jt_expediente.getText().length() > 0 && jt_cve.getText().isEmpty()) {
                task01Inf = new PBcDescExp();
                task01Inf.execute();
            } else if (jt_expediente.getText().isEmpty() && jt_cve.getText().length() > 0) {
                task02Inf = new PBcDescCve();
                task02Inf.execute();
            } else if (jt_expediente.getText().length() > 0 && jt_cve.getText().length() > 0) {
                task03Inf = new PBcDescExpCve();
                task03Inf.execute();
            }
        }
    }//GEN-LAST:event_jt_cveKeyPressed

    
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
            java.util.logging.Logger.getLogger(jfCajaExpCve.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(jfCajaExpCve.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(jfCajaExpCve.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(jfCajaExpCve.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                new jfCajaExpCve().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnConsulta;
    private javax.swing.JButton btn_exportar;
    private javax.swing.JButton btn_imprimir;
    private javax.swing.JButton btn_limpiar;
    private javax.swing.JButton btn_menu_principal;
    private javax.swing.JButton btn_reporte;
    private javax.swing.JButton btn_salir;
    private javax.swing.ButtonGroup gruposiopt;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel jl_cve;
    private javax.swing.JLabel jl_etiqueta;
    private javax.swing.JLabel jl_expediente;
    private javax.swing.JLabel jl_titulo;
    private javax.swing.JLabel jl_tt_reg;
    private javax.swing.JProgressBar jp_bar;
    private javax.swing.JTable jt_consulta_vacunas;
    private javax.swing.JTextField jt_cve;
    private javax.swing.JTextField jt_expediente;
    // End of variables declaration//GEN-END:variables

    class PBcDescExp extends SwingWorker<Void, Void> {

        @Override
        protected Void doInBackground() throws Exception {
            jp_bar.setVisible(true);
            jp_bar.setIndeterminate(true);
            jl_etiqueta.setVisible(true);
            cDescExp();
            return null;
        }

        @Override
        protected void done() {
            jp_bar.setVisible(false);
            jl_etiqueta.setVisible(false);
        }

    }
    
    class PBcDescCve extends SwingWorker<Void, Void> {

        @Override
        protected Void doInBackground() throws Exception {
            jp_bar.setVisible(true);
            jp_bar.setIndeterminate(true);
            jl_etiqueta.setVisible(true);
            cDescCve();
            return null;
        }

        @Override
        protected void done() {
            jp_bar.setVisible(false);
            jl_etiqueta.setVisible(false);
        }
    }
   
    class PBcDescExpCve extends SwingWorker<Void, Void> {

        @Override
        protected Void doInBackground() throws Exception {
            jp_bar.setVisible(true);
            jp_bar.setIndeterminate(true);
            jl_etiqueta.setVisible(true);
            cDescExpCve();
            return null;
        }

        @Override
        protected void done() {
            jp_bar.setVisible(false);
            jl_etiqueta.setVisible(false);
        }
    }
 
}
