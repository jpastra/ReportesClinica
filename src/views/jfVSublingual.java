/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;
import conexion.Conexiondb;
import events.Export;
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
import java.util.Date;
import java.text.SimpleDateFormat;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.UIManager;


/**
 * @author Jessica Viviana Pastrana Munguia
 */
public class jfVSublingual extends javax.swing.JFrame {
ResultSet rs;
private PBvSublingual task01Inf;
private PBvSublingualCveDH task02Inf;
private PBvSublingualCveD task03Inf;
private PBvSublingualD task04Inf;
    
    /**
     * Creates new form jfVacunas
     */
    public jfVSublingual() {
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
    
    public void vSublingual() throws SQLException {

        String formato = "dd/MM/yyyy";
        Date date;
        date = jd_Desde.getDate();
        SimpleDateFormat sdfD = new SimpleDateFormat(formato);
        String strDateD = sdfD.format(date);

        Date date2 = jd_Hasta.getDate();
        SimpleDateFormat sdfH = new SimpleDateFormat(formato);
        String strDateH = sdfH.format(date2);

        rs = Conexiondb.Consulta("SELECT INMCVE  AS CLAVE, INMDES AS DESCRIPCION, TO_CHAR(INMFEC,'dd/MM/yyyy') AS FECHA, SUM (INMCANT) AS TOTAL FROM INMPAC WHERE INMFEC BETWEEN TO_DATE('" + strDateD + "','dd/MM/yyyy') AND TO_DATE('" + strDateH + "','dd/MM/yyyy') AND INMDES LIKE '%SUBLINGUAL%' GROUP BY INMCVE, INMDES, INMFEC ORDER BY CLAVE");
        
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

            String a = "";
            int total = 0;
            int b = 0;

            for (int fila = 0; fila < jt_consulta_vacunas.getRowCount(); fila++) {
                a = String.valueOf(jt_consulta_vacunas.getValueAt(fila, 3));
                b = Integer.valueOf(a);
                total = total + b;
            }

            modelo.addRow(new Object[]{"", "", "Total", String.valueOf(total)});

            rs.close();
            tt_rec();
        }
    }
    
        public void vSublingualD() throws SQLException {

        String formato = "dd/MM/yyyy";
        Date date;
        date = jd_Desde.getDate();
        SimpleDateFormat sdfD = new SimpleDateFormat(formato);
        String strDateD = sdfD.format(date);

        rs = Conexiondb.Consulta("SELECT INMCVE  AS CLAVE, INMDES AS DESCRIPCION, TO_CHAR(INMFEC,'dd/MM/yyyy') AS FECHA, SUM (INMCANT) AS TOTAL FROM INMPAC WHERE INMFEC = TO_DATE('" + strDateD + "','dd/MM/yyyy') AND INMDES LIKE '%SUBLINGUAL%' GROUP BY INMCVE, INMDES, INMFEC ORDER BY CLAVE");
        
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

            String a = "";
            int total = 0;
            int b = 0;

            for (int fila = 0; fila < jt_consulta_vacunas.getRowCount(); fila++) {
                a = String.valueOf(jt_consulta_vacunas.getValueAt(fila, 3));
                b = Integer.valueOf(a);
                total = total + b;
            }

            modelo.addRow(new Object[]{"", "", "Total", String.valueOf(total)});

            rs.close();
            tt_rec();
        }
    }

    public void vSublingualCveDH() throws SQLException {

        String formato = "dd/MM/yyyy";
        Date date;
        date = jd_Desde.getDate();
        SimpleDateFormat sdfD = new SimpleDateFormat(formato);
        String strDateD = sdfD.format(date);

        Date date2 = jd_Hasta.getDate();
        SimpleDateFormat sdfH = new SimpleDateFormat(formato);
        String strDateH = sdfH.format(date2);

        rs = Conexiondb.Consulta("SELECT INMCVE  AS CLAVE, INMDES AS DESCRIPCION, TO_CHAR(INMFEC,'dd/MM/yyyy') AS FECHA, SUM (INMCANT) AS TOTAL FROM INMPAC WHERE INMFEC BETWEEN TO_DATE('" + strDateD + "', 'dd/MM/yyyy') AND TO_DATE('" + strDateH + "', 'dd/MM/yyyy') AND INMCVE IN (" + jt_cve.getText() + ") AND INMDES LIKE '%SUBLINGUAL%' GROUP BY INMCVE, INMDES, INMFEC ORDER BY CLAVE");
        
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

            String a = "";
            int total = 0;
            int b = 0;

            for (int fila = 0; fila < jt_consulta_vacunas.getRowCount(); fila++) {
                a = String.valueOf(jt_consulta_vacunas.getValueAt(fila, 3));
                b = Integer.valueOf(a);
                total = total + b;
            }

            modelo.addRow(new Object[]{"", "", "Total", String.valueOf(total)});

            rs.close();
            tt_rec();
        }
    }
    
    public void vSublingualCveD() throws SQLException {

        String formato = "dd/MM/yyyy";
        Date date;
        date = jd_Desde.getDate();
        SimpleDateFormat sdfD = new SimpleDateFormat(formato);
        String strDateD = sdfD.format(date);

        rs = Conexiondb.Consulta("SELECT INMCVE  AS CLAVE, INMDES AS DESCRIPCION, TO_CHAR(INMFEC,'dd/MM/yyyy') AS FECHA, SUM (INMCANT) AS TOTAL FROM INMPAC WHERE INMFEC = TO_DATE('" + strDateD + "', 'dd/MM/yyyy') AND INMCVE IN (" + jt_cve.getText() + ") AND INMDES LIKE '%SUBLINGUAL%' GROUP BY INMCVE, INMDES, INMFEC ORDER BY CLAVE");
        
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

            String a = "";
            int total = 0;
            int b = 0;

            for (int fila = 0; fila < jt_consulta_vacunas.getRowCount(); fila++) {
                a = String.valueOf(jt_consulta_vacunas.getValueAt(fila, 3));
                b = Integer.valueOf(a);
                total = total + b;
            }

            modelo.addRow(new Object[]{"", "", "Total", String.valueOf(total)});

            rs.close();
            tt_rec();
        }
    }

    public void tt_rec() {
        int tt_reg = this.jt_consulta_vacunas.getRowCount();
        int ttRow = tt_reg - 1;
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

        jl_titulo = new javax.swing.JLabel();
        btnConsulta = new javax.swing.JButton();
        btn_salir = new javax.swing.JButton();
        btn_imprimir = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jt_consulta_vacunas = new javax.swing.JTable();
        jl_tt_reg = new javax.swing.JLabel();
        btn_menu_principal = new javax.swing.JButton();
        btn_exportar = new javax.swing.JButton();
        jd_Desde = new com.toedter.calendar.JDateChooser();
        jl_desde = new javax.swing.JLabel();
        jd_Hasta = new com.toedter.calendar.JDateChooser();
        jl_hasta = new javax.swing.JLabel();
        jl_excluir = new javax.swing.JLabel();
        jt_cve = new javax.swing.JTextField();
        btn_limpiar = new javax.swing.JButton();
        btn_reporte = new javax.swing.JButton();
        jl_etiqueta = new javax.swing.JLabel();
        jp_bar = new javax.swing.JProgressBar();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Reporte De Totales de Vacunas Sublinguales");

        jl_titulo.setBackground(new java.awt.Color(51, 102, 255));
        jl_titulo.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jl_titulo.setForeground(new java.awt.Color(255, 255, 255));
        jl_titulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jl_titulo.setText("REPORTE DE TOTALES DE VACUNAS SUBLINGUALES");
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

        jd_Desde.setDateFormatString("dd/MM/yyyy");

        jl_desde.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jl_desde.setText("Desde:");
        jl_desde.setPreferredSize(new java.awt.Dimension(47, 18));

        jd_Hasta.setDateFormatString("dd/MM/yyyy");

        jl_hasta.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jl_hasta.setText("Hasta:");
        jl_hasta.setPreferredSize(new java.awt.Dimension(45, 18));

        jl_excluir.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jl_excluir.setText("Clave:");

        jt_cve.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jt_cveKeyPressed(evt);
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

        jl_etiqueta.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jl_etiqueta.setText("Procesando....");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jl_titulo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(jl_desde, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jd_Desde, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(65, 65, 65)
                .addComponent(jl_hasta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jd_Hasta, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnConsulta, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btn_imprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btn_exportar)
                                .addGap(16, 16, 16)
                                .addComponent(btn_reporte)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btn_limpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(14, 14, 14)
                                .addComponent(btn_menu_principal)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jl_tt_reg, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jl_etiqueta)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jp_bar, javax.swing.GroupLayout.DEFAULT_SIZE, 751, Short.MAX_VALUE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(jScrollPane1))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addComponent(jl_excluir)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jt_cve, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_salir, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(28, 28, 28))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jl_titulo, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(55, 55, 55)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jl_excluir)
                    .addComponent(jt_cve, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_salir, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jl_desde, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 7, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jl_hasta, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jd_Desde, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jd_Hasta, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jl_tt_reg, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnConsulta, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_imprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_exportar, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_menu_principal, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_reporte, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_limpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 297, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jp_bar, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jl_etiqueta))
                .addGap(22, 22, 22))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_salirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_salirActionPerformed
        System.exit(0);
    }//GEN-LAST:event_btn_salirActionPerformed

    private void btnConsultaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultaActionPerformed
    
        if(jt_cve.getText().isEmpty() && jd_Desde.getDate() == null && jd_Hasta.getDate() == null)
        {
            JOptionPane.showMessageDialog(null, "Ingresa la descripción o desde que fecha hasta que fecha \ndeseas hacer la consulta de las Vacunas Sublinguales");
        } else if(jt_cve.getText().isEmpty() && jd_Desde.getDate() != null && jd_Hasta.getDate() != null)
        {
            
                task01Inf = new PBvSublingual();
                task01Inf.execute();
                
        }else if(jt_cve.getText().length() > 0 && jd_Desde.getDate() != null && jd_Hasta.getDate() != null){
            
                task02Inf = new PBvSublingualCveDH();
                task02Inf.execute();
                
        }else if(jt_cve.getText().length() > 0 && jd_Desde.getDate() != null && jd_Hasta.getDate() == null){
            
                task03Inf = new PBvSublingualCveD();
                task03Inf.execute();
                
        }else if(jt_cve.getText().isEmpty() && jd_Desde.getDate() != null && jd_Hasta.getDate() == null){
            
                task04Inf = new PBvSublingualD();
                task04Inf.execute();
                
        }else if(jt_cve.getText().length() > 0 && jd_Desde.getDate() == null && jd_Hasta.getDate() == null){
            JOptionPane.showMessageDialog(null, "La consulta debe de contar con la Fecha Desde \n       o un rango de Fecha Desde - Hasta");
        }else if(jt_cve.getText().length() > 0 && jd_Desde.getDate() == null && jd_Hasta.getDate() != null){
            JOptionPane.showMessageDialog(null, "La consulta desde de ser con el campo de Fecha Desde \n               o un rango de Fecha Desde - Hasta");
        }
    }//GEN-LAST:event_btnConsultaActionPerformed

    private void btn_imprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_imprimirActionPerformed

        try{
            MessageFormat headerformat = new MessageFormat("Reporte Vacunas Sublinguales"); //encabezado
            MessageFormat footerformat = new MessageFormat("Page - {0}"); //
            jt_consulta_vacunas.print(JTable.PrintMode.FIT_WIDTH, headerformat, footerformat);  //imprime la JTable
        } catch (PrinterException ex) {
        Logger.getLogger(jfVSublingual.class.getName()).log(Level.SEVERE, null, ex);
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
       File route = new File("C:/Reportescap/ReportesExcel");
       chooser.setFileFilter(filter);
       chooser.setDialogTitle("Salvar Archivo");
       chooser.setMultiSelectionEnabled(false);
       chooser.setAcceptAllFileFilterUsed(false);
       chooser.setCurrentDirectory(route);
       if(chooser.showSaveDialog(null)==JFileChooser.APPROVE_OPTION){
           List<JTable> tb = new ArrayList<>();
           List<String>nom = new ArrayList<>();
           tb.add(jt_consulta_vacunas);
           nom.add("Reporte Vacunas Sublinguales");
           String file = chooser.getSelectedFile().toString().concat(".xls");
           //Aqui se ejecuta el metodo
           try{
               Exporter e = new Exporter(new File(file),tb,nom);
               if(e.export())
               {
                   JOptionPane.showMessageDialog(null, "Total de Registros exportados a Excel: " + totalR, "Reporte Vacunas Sublingual", JOptionPane.INFORMATION_MESSAGE);
               }
           }
           catch(Exception e)
           {
             JOptionPane.showMessageDialog(null, "Hubo un error al exportar" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
           }
       }
    }//GEN-LAST:event_btn_exportarActionPerformed

    private void btn_limpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_limpiarActionPerformed
        jt_cve.setText("");
        jd_Desde.setDate(null);
        jd_Hasta.setDate(null);
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
                nom.add("VacunasSublinguales");
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

    private void jt_cveKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jt_cveKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (jt_cve.getText().isEmpty() && jd_Desde.getDate() == null && jd_Hasta.getDate() == null) {
                JOptionPane.showMessageDialog(null, "Ingresa la descripción o desde que fecha hasta que fecha \ndeseas hacer la consulta de las Vacunas Sublinguales");
            } else if (jt_cve.getText().length() > 0 && jd_Desde.getDate() != null && jd_Hasta.getDate() != null) {
                
                    task01Inf = new PBvSublingual();
                    task01Inf.execute();

            } else if (jt_cve.getText().length() > 0 && jd_Desde.getDate() != null && jd_Hasta.getDate() != null) {
                
                    task02Inf = new PBvSublingualCveDH();
                    task02Inf.execute();
                    
            } else if (jt_cve.getText().length() > 0 && jd_Desde.getDate() != null && jd_Hasta.getDate() == null) {
                
                    task03Inf = new PBvSublingualCveD();
                    task03Inf.execute();
                    
            }else if(jt_cve.getText().isEmpty() && jd_Desde.getDate() != null && jd_Hasta.getDate() == null){
            
                task04Inf = new PBvSublingualD();
                task04Inf.execute();
                
            }else if (jt_cve.getText().length() > 0 && jd_Desde.getDate() == null && jd_Hasta.getDate() == null) {
                JOptionPane.showMessageDialog(null, "La consulta debe de contar con la Fecha Desde \n       o un rango de Fecha Desde - Hasta");
            } else if (jt_cve.getText().length() > 0 && jd_Desde.getDate() == null && jd_Hasta.getDate() != null) {
                JOptionPane.showMessageDialog(null, "La consulta desde de ser con el campo de Fecha Desde \n               o un rango de Fecha Desde - Hasta");
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
            java.util.logging.Logger.getLogger(jfVSublingual.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(jfVSublingual.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(jfVSublingual.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(jfVSublingual.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try{
                    UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
                }catch(Exception e){
                    e.printStackTrace();
                }
                new jfVSublingual().setVisible(true);
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
    private javax.swing.JScrollPane jScrollPane1;
    private com.toedter.calendar.JDateChooser jd_Desde;
    private com.toedter.calendar.JDateChooser jd_Hasta;
    private javax.swing.JLabel jl_desde;
    private javax.swing.JLabel jl_etiqueta;
    private javax.swing.JLabel jl_excluir;
    private javax.swing.JLabel jl_hasta;
    private javax.swing.JLabel jl_titulo;
    private javax.swing.JLabel jl_tt_reg;
    private javax.swing.JProgressBar jp_bar;
    private javax.swing.JTable jt_consulta_vacunas;
    private javax.swing.JTextField jt_cve;
    // End of variables declaration//GEN-END:variables

    class PBvSublingual extends SwingWorker<Void, Void> {
        @Override
        protected Void doInBackground() throws Exception {
            jp_bar.setVisible(true);
            jp_bar.setIndeterminate(true);
            jl_etiqueta.setVisible(true);
            vSublingual();
            return null;
        }

        @Override
        protected void done() {
            jp_bar.setVisible(false);
            jl_etiqueta.setVisible(false);
        }
    }
    
    class PBvSublingualCveDH extends SwingWorker<Void, Void> {

        @Override
        protected Void doInBackground() throws Exception {
            jp_bar.setVisible(true);
            jp_bar.setIndeterminate(true);
            jl_etiqueta.setVisible(true);
            vSublingualCveDH();
            return null;
        }

        @Override
        protected void done() {
            jp_bar.setVisible(false);
            jl_etiqueta.setVisible(false);
        }
    }
    
    class PBvSublingualCveD extends SwingWorker<Void, Void> {

        @Override
        protected Void doInBackground() throws Exception {
            jp_bar.setVisible(true);
            jp_bar.setIndeterminate(true);
            jl_etiqueta.setVisible(true);
            vSublingualCveD();
            return null;
        }

        @Override
        protected void done() {
            jp_bar.setVisible(false);
            jl_etiqueta.setVisible(false);
        }
    }
    
    class PBvSublingualD extends SwingWorker<Void, Void> {

        @Override
        protected Void doInBackground() throws Exception {
            jp_bar.setVisible(true);
            jp_bar.setIndeterminate(true);
            jl_etiqueta.setVisible(true);
            vSublingualD();
            return null;
        }

        @Override
        protected void done() {
            jp_bar.setVisible(false);
            jl_etiqueta.setVisible(false);
        }
    }
}
