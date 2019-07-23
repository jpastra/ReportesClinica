/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import conexion.Conexiondb;
import static conexion.Conexiondb.Init_conn;
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
import events.Validar;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.SimpleFormatter;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.table.TableModel;

/**
 * @author Jessica Viviana Pastrana Munguia
 */
public class jfAplicarDescuento extends javax.swing.JFrame {

    private static final Logger LOG = Logger.getLogger(jfAplicarDescuento.class.getName());

    ResultSet rs;
    private PBsDescExp task01Inf;
    private PBsDescCve task02Inf;
    private PBsDescExpCveEsp task03Inf;
    private PBsDescExpCveInit task04Inf;
    private PBsDescExpCveEnds task05Inf;
    Validar v = new Validar();

    /**
     * Creates new form jfVacunas
     */
    public jfAplicarDescuento() {
        initComponents();
        setLocationRelativeTo(null);
        //setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(false);
        jl_titulo.setHorizontalAlignment(JLabel.CENTER);
        jl_titulo.setForeground(Color.white);
        jl_titulo.setOpaque(true);
        jl_etiqueta.setVisible(false);
        //jl_mensaje.setVisible(false);
        jp_bar.setVisible(false);
        jt_expediente.grabFocus();
        jt_expediente.setNextFocusableComponent(jt_cve_esp);
        jl_nombre_pac.setVisible(false);
        jl_paciente_nombre.setVisible(false);
        v.validarSoloNumeros(jtf_desc);
        v.validaLongitud(jtf_desc);
        jt_cve_esp.setNextFocusableComponent(jt_cve_init);
        jt_cve_init.setNextFocusableComponent(jt_cve_ends);
        btn_descuento.setNextFocusableComponent(btn_imprimir);
        jtf_expediente.setEditable(false);
        jtf_cve.setEditable(false);
        
    }

    public void nomPac() throws SQLException {
        rs = Conexiondb.Consulta("SELECT PACNOM FROM PACCAT WHERE PACNEXP='" + jt_expediente.getText() + "'");

        //String user;
        while (rs.next()) {
            jl_paciente_nombre.setText(rs.getString("PACNOM"));
        }

        rs.close();

    }

    public void sDescExp() throws SQLException {

        rs = Conexiondb.Consulta("SELECT PACNEXP AS EXPEDIENTE, PRECVE AS CLAVE, DSCTO AS DESCUENTO FROM DESPAC WHERE PACNEXP = '" + jt_expediente.getText() + "' AND PRECVE IN (SELECT PRECVE FROM CONCAT WHERE PREMOV='+') ORDER BY PRECVE");

        ResultSetMetaData rsmt = rs.getMetaData();
        int numCol = rsmt.getColumnCount();

        //Creamos el objeto del modelo de la tabla
        DefaultTableModel modelo = new DefaultTableModel();
        this.jt_consulta_desct.setModel(modelo);

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

    public void sDescCve() throws SQLException {

        rs = Conexiondb.Consulta("SELECT A.PACNEXP AS EXPEDIENTE, B.PACNOM AS NOMBRE, A.PRECVE AS CLAVE, A.DSCTO AS DESCUENTO FROM DESPAC A, PACCAT B WHERE A.PACNEXP = B.PACNEXP AND A.PRECVE = '" + jt_cve_esp.getText() + "' AND A.PRECVE IN (SELECT PRECVE FROM CONCAT WHERE PREMOV='+') ORDER BY A.PACNEXP");

        ResultSetMetaData rsmt = rs.getMetaData();
        int numCol = rsmt.getColumnCount();

        //Creamos el objeto del modelo de la tabla
        DefaultTableModel modelo = new DefaultTableModel();
        this.jt_consulta_desct.setModel(modelo);

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

    public void sDescExpCveEsp() throws SQLException {

        rs = Conexiondb.Consulta("SELECT PACNEXP AS EXPEDIENTE, PRECVE AS CLAVE, DSCTO AS DESCUENTO FROM DESPAC WHERE PACNEXP='" + jt_expediente.getText() + "' AND PRECVE = '" + jt_cve_esp.getText() + "' AND PRECVE IN (SELECT PRECVE FROM CONCAT WHERE PREMOV='+') ORDER BY PRECVE");

        ResultSetMetaData rsmt = rs.getMetaData();
        int numCol = rsmt.getColumnCount();

        //Creamos el objeto del modelo de la tabla
        DefaultTableModel modelo = new DefaultTableModel();
        this.jt_consulta_desct.setModel(modelo);

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

    public void sDescExpCveInit() throws SQLException {

        rs = Conexiondb.Consulta("SELECT PACNEXP AS EXPEDIENTE, PRECVE AS CLAVE, DSCTO AS DESCUENTO FROM DESPAC WHERE PACNEXP='" + jt_expediente.getText() + "' AND PRECVE LIKE '" + jt_cve_init.getText() + "%' AND PRECVE IN (SELECT PRECVE FROM CONCAT WHERE PREMOV='+') ORDER BY PRECVE");

        ResultSetMetaData rsmt = rs.getMetaData();
        int numCol = rsmt.getColumnCount();

        //Creamos el objeto del modelo de la tabla
        DefaultTableModel modelo = new DefaultTableModel();
        this.jt_consulta_desct.setModel(modelo);

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

    public void sDescExpCveEnds() throws SQLException {

        rs = Conexiondb.Consulta("SELECT PACNEXP AS EXPEDIENTE, PRECVE AS CLAVE, DSCTO AS DESCUENTO FROM DESPAC WHERE PACNEXP='" + jt_expediente.getText() + "' AND (PRECVE LIKE '%" + jt_cve_ends.getText() + "_' OR PRECVE LIKE '%"+jt_cve_ends.getText()+"') AND PRECVE IN (SELECT PRECVE FROM CONCAT WHERE PREMOV='+') ORDER BY PRECVE");

        ResultSetMetaData rsmt = rs.getMetaData();
        int numCol = rsmt.getColumnCount();

        //Creamos el objeto del modelo de la tabla
        DefaultTableModel modelo = new DefaultTableModel();
        this.jt_consulta_desct.setModel(modelo);

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
        int tt_reg = this.jt_consulta_desct.getRowCount();
        int ttRow = tt_reg;
        if (ttRow > 0) {
            this.jl_tt_reg.setText("Total Registros: " + ttRow);
        } else {
            int value = 0;
            this.jl_tt_reg.setText("Total Registros: " + value);
        }
    }

    public void muestraGrid() {
        int colData = jt_consulta_desct.getColumnCount();

        if (colData == 3) {

            int index = jt_consulta_desct.getSelectedRow();

            TableModel model = jt_consulta_desct.getModel();

            String expediente = model.getValueAt(index, 0).toString();
            String clave = model.getValueAt(index, 1).toString();
            String descuento = model.getValueAt(index, 2).toString();

            jtf_expediente.setText(expediente);
            jtf_cve.setText(clave);
            jtf_desc.setText(descuento);

        } else if (colData == 4) {
            int index = jt_consulta_desct.getSelectedRow();

            TableModel model = jt_consulta_desct.getModel();

            String expediente = model.getValueAt(index, 0).toString();
            String clave = model.getValueAt(index, 2).toString();
            String descuento = model.getValueAt(index, 3).toString();

            jtf_expediente.setText(expediente);
            jtf_cve.setText(clave);
            jtf_desc.setText(descuento);
        }
    }

    public void refresh() {
        if (jt_expediente.getText().length() > 0 && jt_cve_esp.getText().isEmpty() && jt_cve_init.getText().isEmpty() && jt_cve_ends.getText().isEmpty()) {
            task01Inf = new PBsDescExp();
            task01Inf.execute();
            jl_mensaje.setVisible(true);
            jl_mensaje.setText("Registro Actualizado");
            //System.out.println("Se ejecuta la tarea 1");

        } else if (jt_expediente.getText().isEmpty() && jt_cve_esp.getText().length() > 0 && jrb_cve_esp.isSelected()) {
            task02Inf = new PBsDescCve();
            task02Inf.execute();
            jl_mensaje.setVisible(true);
            jl_mensaje.setText("Registro Actualizado");
            //System.out.println("Se ejecuta la tarea 2");

        } else if (jt_expediente.getText().length() > 0 && jt_cve_esp.getText().length() > 0 && jrb_cve_esp.isSelected()) {

            task03Inf = new PBsDescExpCveEsp();
            task03Inf.execute();
            jl_mensaje.setVisible(true);
            jl_mensaje.setText("Registro Actualizado");
            //System.out.println("Se ejecuta la tarea 3");

        } else if (jt_expediente.getText().length() > 0 && jrb_cve_init.isSelected() && jt_cve_init.getText().length() > 0 && jt_cve_esp.getText().isEmpty()) {

            task04Inf = new PBsDescExpCveInit();
            task04Inf.execute();
            jl_mensaje.setVisible(true);
            jl_mensaje.setText("Registro Actualizado");
            //System.out.println("Se ejecuta la tarea 4");

        } else if (jt_expediente.getText().length() > 0 &&  jrb_cve_ends.isSelected() && jt_cve_ends.getText().length() > 0 && jt_cve_esp.getText().isEmpty()) {

            task05Inf = new PBsDescExpCveEnds();
            task05Inf.execute();
            jl_mensaje.setVisible(true);
            jl_mensaje.setText("Registro Actualizado");
            //System.out.println("Se ejecuta la tarea 5");
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

        btnGrupo = new javax.swing.ButtonGroup();
        jl_titulo = new javax.swing.JLabel();
        btnConsulta = new javax.swing.JButton();
        btn_salir = new javax.swing.JButton();
        btn_imprimir = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jt_consulta_desct = new javax.swing.JTable();
        jl_tt_reg = new javax.swing.JLabel();
        btn_menu_principal = new javax.swing.JButton();
        btn_exportar = new javax.swing.JButton();
        btn_limpiar = new javax.swing.JButton();
        btn_reporte = new javax.swing.JButton();
        jl_etiqueta = new javax.swing.JLabel();
        jp_bar = new javax.swing.JProgressBar();
        jl_expediente = new javax.swing.JLabel();
        jt_expediente = new javax.swing.JTextField();
        jt_cve_esp = new javax.swing.JTextField();
        jl_clave_texto = new javax.swing.JLabel();
        jt_cve_init = new javax.swing.JTextField();
        jt_cve_ends = new javax.swing.JTextField();
        jrb_cve_esp = new javax.swing.JRadioButton();
        jrb_cve_init = new javax.swing.JRadioButton();
        jrb_cve_ends = new javax.swing.JRadioButton();
        jl_nombre_pac = new javax.swing.JLabel();
        jl_paciente_nombre = new javax.swing.JLabel();
        panel1 = new java.awt.Panel();
        jl_expediente_label = new javax.swing.JLabel();
        jl_cve_label = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jtf_expediente = new javax.swing.JTextField();
        jtf_cve = new javax.swing.JTextField();
        jtf_desc = new javax.swing.JTextField();
        jl_datos_pacdesc = new javax.swing.JLabel();
        btn_descuento = new javax.swing.JButton();
        jl_mensaje = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Aplicar Descuentos");

        jl_titulo.setBackground(new java.awt.Color(51, 102, 255));
        jl_titulo.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jl_titulo.setForeground(new java.awt.Color(255, 255, 255));
        jl_titulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jl_titulo.setText("APLICAR DESCUENTOS");
        jl_titulo.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(0, 51, 255)));

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

        jt_consulta_desct = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex){
                return false;
            }
        };
        jt_consulta_desct.setModel(new javax.swing.table.DefaultTableModel(
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
        jt_consulta_desct.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jt_consulta_desctMouseClicked(evt);
            }
        });
        jt_consulta_desct.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jt_consulta_desctKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(jt_consulta_desct);

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

        jl_etiqueta.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jl_etiqueta.setText("Procesando....");

        jl_expediente.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jl_expediente.setText("Expediente:");

        jt_expediente.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jt_expediente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jt_expedienteKeyPressed(evt);
            }
        });

        jt_cve_esp.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jt_cve_esp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jt_cve_espFocusGained(evt);
            }
        });
        jt_cve_esp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jt_cve_espKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jt_cve_espKeyTyped(evt);
            }
        });

        jl_clave_texto.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jl_clave_texto.setText("Clave");

        jt_cve_init.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jt_cve_init.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jt_cve_initFocusGained(evt);
            }
        });
        jt_cve_init.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jt_cve_initKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jt_cve_initKeyTyped(evt);
            }
        });

        jt_cve_ends.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jt_cve_endsFocusGained(evt);
            }
        });
        jt_cve_ends.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jt_cve_endsKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jt_cve_endsKeyTyped(evt);
            }
        });

        btnGrupo.add(jrb_cve_esp);
        jrb_cve_esp.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jrb_cve_esp.setText("Por Clave Especifica:");
        jrb_cve_esp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrb_cve_espActionPerformed(evt);
            }
        });

        btnGrupo.add(jrb_cve_init);
        jrb_cve_init.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jrb_cve_init.setText("Comienza con letra:");
        jrb_cve_init.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrb_cve_initActionPerformed(evt);
            }
        });

        btnGrupo.add(jrb_cve_ends);
        jrb_cve_ends.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jrb_cve_ends.setText("Termina con letra:");
        jrb_cve_ends.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrb_cve_endsActionPerformed(evt);
            }
        });

        jl_nombre_pac.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jl_nombre_pac.setForeground(new java.awt.Color(0, 51, 153));
        jl_nombre_pac.setText("NOMBRE PACIENTE:");

        jl_paciente_nombre.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jl_paciente_nombre.setForeground(new java.awt.Color(0, 51, 51));

        panel1.setBackground(new java.awt.Color(0, 153, 255));

        jl_expediente_label.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jl_expediente_label.setText("EXPEDIENTE:");

        jl_cve_label.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jl_cve_label.setText("CLAVE:");

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("DESCUENTO:");

        jtf_expediente.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        jtf_cve.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        jtf_desc.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jtf_desc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtf_descKeyTyped(evt);
            }
        });

        jl_datos_pacdesc.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jl_datos_pacdesc.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jl_datos_pacdesc.setText("DATOS A ACTUALIZAR");

        btn_descuento.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btn_descuento.setText("Aplicar Desc");
        btn_descuento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_descuentoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel1Layout = new javax.swing.GroupLayout(panel1);
        panel1.setLayout(panel1Layout);
        panel1Layout.setHorizontalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel1Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addComponent(jl_cve_label)
                    .addComponent(jl_expediente_label))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jtf_expediente)
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jtf_desc, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtf_cve, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 24, Short.MAX_VALUE)))
                .addContainerGap())
            .addComponent(jl_datos_pacdesc, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_descuento, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(68, 68, 68))
        );
        panel1Layout.setVerticalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel1Layout.createSequentialGroup()
                .addComponent(jl_datos_pacdesc, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17)
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtf_expediente, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jl_expediente_label))
                .addGap(18, 18, 18)
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jl_cve_label)
                    .addComponent(jtf_cve, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jtf_desc, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btn_descuento, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jl_mensaje.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jl_titulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jl_clave_texto)
                            .addComponent(jl_expediente, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(28, 28, 28)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jrb_cve_esp)
                                    .addComponent(jrb_cve_init)
                                    .addComponent(jrb_cve_ends))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jt_cve_init, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
                                    .addComponent(jt_cve_esp)
                                    .addComponent(jt_cve_ends)))
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jt_expediente, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 105, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addComponent(btn_imprimir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGap(18, 18, 18)
                                    .addComponent(btn_limpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(btn_exportar, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btn_reporte))
                                    .addGap(18, 18, 18)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(btn_salir, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btn_menu_principal, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addComponent(jl_tt_reg, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(27, 27, 27))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(panel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jl_mensaje, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(107, 107, 107)
                                .addComponent(btnConsulta, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jl_nombre_pac, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jl_paciente_nombre, javax.swing.GroupLayout.PREFERRED_SIZE, 286, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(53, 53, 53)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jl_etiqueta)
                                        .addGap(18, 18, 18)
                                        .addComponent(jp_bar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 497, Short.MAX_VALUE))))))
                .addGap(18, 18, 18))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jl_titulo, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jl_expediente)
                            .addComponent(jt_expediente, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jrb_cve_esp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jt_cve_esp))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jt_cve_init, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jrb_cve_init, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(57, 57, 57)
                                .addComponent(jl_clave_texto))))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btn_imprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_limpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btn_menu_principal, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_exportar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btn_reporte, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_salir, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jl_tt_reg, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jl_paciente_nombre, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jl_nombre_pac, javax.swing.GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jrb_cve_ends)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jt_cve_ends, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(2, 2, 2)))
                        .addGap(15, 15, 15)
                        .addComponent(btnConsulta, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jl_mensaje, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(panel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jp_bar, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jl_etiqueta))
                .addContainerGap())
        );

        getAccessibleContext().setAccessibleName("Reporte Descuentos");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_salirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_salirActionPerformed
        System.exit(0);
    }//GEN-LAST:event_btn_salirActionPerformed

    private void btnConsultaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultaActionPerformed
        if (jt_expediente.getText().isEmpty() && jt_cve_esp.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "La consulta debe de ser por Expediente / Clave o Ambas");
        } else if (jt_expediente.getText().length() > 0 && jt_cve_esp.getText().isEmpty() && jt_cve_init.getText().isEmpty() && jt_cve_ends.getText().isEmpty()) {

            task01Inf = new PBsDescExp();
            task01Inf.execute();
            jl_mensaje.setVisible(false);
            jtf_expediente.setText("");
            jtf_cve.setText("");
            jtf_desc.setText("");
            //System.out.println("Se ejecuta la tarea 1");
        } else if (jt_expediente.getText().isEmpty() && jt_cve_esp.getText().length() > 0 && jrb_cve_esp.isSelected()) {

            task02Inf = new PBsDescCve();
            task02Inf.execute();
            jl_mensaje.setVisible(false);
            jtf_expediente.setText("");
            jtf_cve.setText("");
            jtf_desc.setText("");
            //System.out.println("Se ejecuta la tarea 2");

        } else if (jt_expediente.getText().length() > 0 && jt_cve_esp.getText().length() > 0 && jrb_cve_esp.isSelected()) {

            task03Inf = new PBsDescExpCveEsp();
            task03Inf.execute();
            jl_mensaje.setVisible(false);
            jtf_expediente.setText("");
            jtf_cve.setText("");
            jtf_desc.setText("");
            //System.out.println("Se ejecuta la tarea 3");

        } else if (jt_expediente.getText().length() > 0 && jrb_cve_init.isSelected() && jt_cve_init.getText().length() > 0 && jt_cve_esp.getText().isEmpty()) {

            task04Inf = new PBsDescExpCveInit();
            task04Inf.execute();
            jl_mensaje.setVisible(false);
            jtf_expediente.setText("");
            jtf_cve.setText("");
            jtf_desc.setText("");
            //System.out.println("Se ejecuta la tarea 4");

        } else if (jt_expediente.getText().length() > 0 && jt_cve_ends.getText().length() > 0 && jrb_cve_ends.isSelected()) {

            task05Inf = new PBsDescExpCveEnds();
            task05Inf.execute();
            jl_mensaje.setVisible(false);
            jtf_expediente.setText("");
            jtf_cve.setText("");
            jtf_desc.setText("");
            //System.out.println("Se ejecuta la tarea 5");
        }
    }//GEN-LAST:event_btnConsultaActionPerformed

    private void btn_imprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_imprimirActionPerformed

        try {
            MessageFormat headerformat = new MessageFormat("Reporte Aplicar Descuentos"); //encabezado
            MessageFormat footerformat = new MessageFormat("Page - {0}"); //
            jt_consulta_desct.print(JTable.PrintMode.FIT_WIDTH, headerformat, footerformat);  //imprime la JTable
        } catch (PrinterException ex) {
            Logger.getLogger(jfAplicarDescuento.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btn_imprimirActionPerformed

    private void btn_menu_principalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_menu_principalActionPerformed
        dispose();
        new MenuPrincipal().setVisible(true);
    }//GEN-LAST:event_btn_menu_principalActionPerformed

    private void btn_exportarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_exportarActionPerformed
        if (this.jt_consulta_desct.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "No hay datos por exportar", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        //Creacion del archivo en Excel
        int totalR = jt_consulta_desct.getRowCount();
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos de Excel", ".xls");
        File route = new File("C:/Reportescap/ReportesExcel");
        chooser.setFileFilter(filter);
        chooser.setDialogTitle("Salvar Archivo");
        chooser.setMultiSelectionEnabled(false);
        chooser.setAcceptAllFileFilterUsed(false);
        chooser.setCurrentDirectory(route);
        if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            List<JTable> tb = new ArrayList<>();
            List<String> nom = new ArrayList<>();
            tb.add(jt_consulta_desct);
            nom.add("Reporte Aplicar Descuentos");
            String file = chooser.getSelectedFile().toString().concat(".xls");
            //Aqui se ejecuta el metodo
            try {
                Exporter e = new Exporter(new File(file), tb, nom);
                if (e.export()) {
                    JOptionPane.showMessageDialog(null, "Total de Registros exportados a Excel: " + totalR, "Reporte Aplicar Descuentos", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Hubo un error al exportar" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btn_exportarActionPerformed

    private void btn_limpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_limpiarActionPerformed
        DefaultTableModel dTable = new DefaultTableModel();
        jt_expediente.setText("");
        jt_cve_esp.setText("");
        jt_cve_init.setText("");
        jt_cve_ends.setText("");
        jl_nombre_pac.setVisible(false);
        jl_paciente_nombre.setVisible(false);
        btnGrupo.clearSelection();
        jtf_expediente.setText("");
        jtf_cve.setText("");
        jtf_desc.setText("");
        jl_mensaje.setVisible(false);
        this.jt_consulta_desct.setModel(dTable);
        while (dTable.getRowCount() > 0) {
            dTable.removeRow(0);
        }
        this.jl_tt_reg.setText("Total Registros: ");
    }//GEN-LAST:event_btn_limpiarActionPerformed

    private void btn_reporteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_reporteActionPerformed
        if (this.jt_consulta_desct.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Ningun Dato por Exportar a PDF", "Mensage", JOptionPane.INFORMATION_MESSAGE);
            return;
        } else {
            int totalR = jt_consulta_desct.getRowCount();
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
                tb.add(jt_consulta_desct);
                nom.add("AplicarDescuentos");
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

    private void jrb_cve_espActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jrb_cve_espActionPerformed
        if (jrb_cve_esp.isSelected()) {
            jt_cve_init.setText("");
            jt_cve_ends.setText("");
        }
    }//GEN-LAST:event_jrb_cve_espActionPerformed

    private void jrb_cve_initActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jrb_cve_initActionPerformed
        if (jrb_cve_init.isSelected()) {
            jt_cve_esp.setText("");
            jt_cve_ends.setText("");
        }
    }//GEN-LAST:event_jrb_cve_initActionPerformed

    private void jrb_cve_endsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jrb_cve_endsActionPerformed
        if (jrb_cve_ends.isSelected()) {
            jt_cve_esp.setText("");
            jt_cve_init.setText("");
            jtf_expediente.setText("");
            jtf_cve.setText("");
            jtf_desc.setText("");
        }
    }//GEN-LAST:event_jrb_cve_endsActionPerformed

    private void jt_expedienteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jt_expedienteKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (jt_expediente.getText().isEmpty() && jt_cve_esp.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "La consulta debe de ser por Expediente / Clave o Ambas");
            } else if (jt_expediente.getText().length() > 0 && jt_cve_esp.getText().isEmpty() && jt_cve_init.getText().isEmpty() && jt_cve_ends.getText().isEmpty()) {

                task01Inf = new PBsDescExp();
                task01Inf.execute();
                jl_mensaje.setVisible(false);
                jtf_expediente.setText("");
                jtf_cve.setText("");
                jtf_desc.setText("");
                //System.out.println("Se ejecuta la tarea 1");
            } else if (jt_expediente.getText().isEmpty() && jt_cve_esp.getText().length() > 0 && jrb_cve_esp.isSelected()) {

                task02Inf = new PBsDescCve();
                task02Inf.execute();
                jl_mensaje.setVisible(false);
                jtf_expediente.setText("");
                jtf_cve.setText("");
                jtf_desc.setText("");
                //System.out.println("Se ejecuta la tarea 2");

            } else if (jt_expediente.getText().length() > 0 && jt_cve_esp.getText().length() > 0 && jrb_cve_esp.isSelected()) {

                task03Inf = new PBsDescExpCveEsp();
                task03Inf.execute();
                jl_mensaje.setVisible(false);
                jtf_expediente.setText("");
                jtf_cve.setText("");
                jtf_desc.setText("");
                //System.out.println("Se ejecuta la tarea 3");

            } else if (jt_expediente.getText().length() > 0 && jrb_cve_init.isSelected() && jt_cve_init.getText().length() > 0 && jt_cve_esp.getText().isEmpty()) {

                task04Inf = new PBsDescExpCveInit();
                task04Inf.execute();
                jl_mensaje.setVisible(false);
                jtf_expediente.setText("");
                jtf_cve.setText("");
                jtf_desc.setText("");
                //System.out.println("Se ejecuta la tarea 4");

            } else if (jt_expediente.getText().length() > 0 && jt_cve_ends.getText().length() > 0 && jrb_cve_ends.isSelected()) {

                task05Inf = new PBsDescExpCveEnds();
                task05Inf.execute();
                jl_mensaje.setVisible(false);
                jtf_expediente.setText("");
                jtf_cve.setText("");
                jtf_desc.setText("");
                //System.out.println("Se ejecuta la tarea 5");
            }
        }
    }//GEN-LAST:event_jt_expedienteKeyPressed

    private void jt_cve_espKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jt_cve_espKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (jt_expediente.getText().isEmpty() && jt_cve_esp.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "La consulta debe de ser por Expediente / Clave o Ambas");
            } else if (jt_expediente.getText().length() > 0 && jt_cve_esp.getText().isEmpty() && jt_cve_init.getText().isEmpty() && jt_cve_ends.getText().isEmpty()) {

                task01Inf = new PBsDescExp();
                task01Inf.execute();
                jl_mensaje.setVisible(false);
                jtf_expediente.setText("");
                jtf_cve.setText("");
                jtf_desc.setText("");
                //System.out.println("Se ejecuta la tarea 1");
            } else if (jt_expediente.getText().isEmpty() && jt_cve_esp.getText().length() > 0 && jrb_cve_esp.isSelected()) {

                task02Inf = new PBsDescCve();
                task02Inf.execute();
                jl_mensaje.setVisible(false);
                jtf_expediente.setText("");
                jtf_cve.setText("");
                jtf_desc.setText("");
                //System.out.println("Se ejecuta la tarea 2");

            } else if (jt_expediente.getText().length() > 0 && jt_cve_esp.getText().length() > 0 && jrb_cve_esp.isSelected()) {

                task03Inf = new PBsDescExpCveEsp();
                task03Inf.execute();
                jl_mensaje.setVisible(false);
                jtf_expediente.setText("");
                jtf_cve.setText("");
                jtf_desc.setText("");
                //System.out.println("Se ejecuta la tarea 3");

            } else if (jt_expediente.getText().length() > 0 && jrb_cve_init.isSelected() && jt_cve_init.getText().length() > 0 && jt_cve_esp.getText().isEmpty()) {

                task04Inf = new PBsDescExpCveInit();
                task04Inf.execute();
                jl_mensaje.setVisible(false);
                jtf_expediente.setText("");
                jtf_cve.setText("");
                jtf_desc.setText("");
                //System.out.println("Se ejecuta la tarea 4");

            } else if (jt_expediente.getText().length() > 0 && jt_cve_ends.getText().length() > 0 && jrb_cve_ends.isSelected()) {

                task05Inf = new PBsDescExpCveEnds();
                task05Inf.execute();
                jl_mensaje.setVisible(false);
                jtf_expediente.setText("");
                jtf_cve.setText("");
                jtf_desc.setText("");
                //System.out.println("Se ejecuta la tarea 5");
            }
        }
    }//GEN-LAST:event_jt_cve_espKeyPressed

    private void jt_cve_initKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jt_cve_initKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (jt_expediente.getText().isEmpty() && jt_cve_esp.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "La consulta debe de ser por Expediente / Clave o Ambas");
            } else if (jt_expediente.getText().length() > 0 && jt_cve_esp.getText().isEmpty() && jt_cve_init.getText().isEmpty() && jt_cve_ends.getText().isEmpty()) {

                task01Inf = new PBsDescExp();
                task01Inf.execute();
                jl_mensaje.setVisible(false);
                jtf_expediente.setText("");
                jtf_cve.setText("");
                jtf_desc.setText("");
                //System.out.println("Se ejecuta la tarea 1");
            } else if (jt_expediente.getText().isEmpty() && jt_cve_esp.getText().length() > 0 && jrb_cve_esp.isSelected()) {

                task02Inf = new PBsDescCve();
                task02Inf.execute();
                jl_mensaje.setVisible(false);
                jtf_expediente.setText("");
                jtf_cve.setText("");
                jtf_desc.setText("");
                //System.out.println("Se ejecuta la tarea 2");

            } else if (jt_expediente.getText().length() > 0 && jt_cve_esp.getText().length() > 0 && jrb_cve_esp.isSelected()) {

                task03Inf = new PBsDescExpCveEsp();
                task03Inf.execute();
                jl_mensaje.setVisible(false);
                jtf_expediente.setText("");
                jtf_cve.setText("");
                jtf_desc.setText("");
                //System.out.println("Se ejecuta la tarea 3");

            } else if (jt_expediente.getText().length() > 0 && jrb_cve_init.isSelected() && jt_cve_init.getText().length() > 0 && jt_cve_esp.getText().isEmpty()) {

                task04Inf = new PBsDescExpCveInit();
                task04Inf.execute();
                jl_mensaje.setVisible(false);
                jtf_expediente.setText("");
                jtf_cve.setText("");
                jtf_desc.setText("");
                //System.out.println("Se ejecuta la tarea 4");

            } else if (jt_expediente.getText().length() > 0 && jt_cve_ends.getText().length() > 0 && jrb_cve_ends.isSelected()) {

                task05Inf = new PBsDescExpCveEnds();
                task05Inf.execute();
                jl_mensaje.setVisible(false);
                jtf_expediente.setText("");
                jtf_cve.setText("");
                jtf_desc.setText("");
                //System.out.println("Se ejecuta la tarea 5");
            }
        }
    }//GEN-LAST:event_jt_cve_initKeyPressed

    private void jt_cve_endsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jt_cve_endsKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (jt_expediente.getText().isEmpty() && jt_cve_esp.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "La consulta debe de ser por Expediente / Clave o Ambas");
            } else if (jt_expediente.getText().length() > 0 && jt_cve_esp.getText().isEmpty() && jt_cve_init.getText().isEmpty() && jt_cve_ends.getText().isEmpty()) {

                task01Inf = new PBsDescExp();
                task01Inf.execute();
                jl_mensaje.setVisible(false);
                jtf_expediente.setText("");
                jtf_cve.setText("");
                jtf_desc.setText("");
                //System.out.println("Se ejecuta la tarea 1");

            } else if (jt_expediente.getText().isEmpty() && jt_cve_esp.getText().length() > 0 && jrb_cve_esp.isSelected()) {

                task02Inf = new PBsDescCve();
                task02Inf.execute();
                jl_mensaje.setVisible(false);
                jtf_expediente.setText("");
                jtf_cve.setText("");
                jtf_desc.setText("");
                //System.out.println("Se ejecuta la tarea 2");

            } else if (jt_expediente.getText().length() > 0 && jt_cve_esp.getText().length() > 0 && jrb_cve_esp.isSelected()) {

                task03Inf = new PBsDescExpCveEsp();
                task03Inf.execute();
                jl_mensaje.setVisible(false);
                jtf_expediente.setText("");
                jtf_cve.setText("");
                jtf_desc.setText("");
                //System.out.println("Se ejecuta la tarea 3");

            } else if (jt_expediente.getText().length() > 0 && jrb_cve_init.isSelected() && jt_cve_init.getText().length() > 0 && jt_cve_esp.getText().isEmpty()) {

                task04Inf = new PBsDescExpCveInit();
                task04Inf.execute();
                jl_mensaje.setVisible(false);
                jtf_expediente.setText("");
                jtf_cve.setText("");
                jtf_desc.setText("");
                //System.out.println("Se ejecuta la tarea 4");

            } else if (jt_expediente.getText().length() > 0 && jt_cve_ends.getText().length() > 0 && jrb_cve_ends.isSelected()) {

                task05Inf = new PBsDescExpCveEnds();
                task05Inf.execute();
                jl_mensaje.setVisible(false);
                jtf_expediente.setText("");
                jtf_cve.setText("");
                jtf_desc.setText("");
                //System.out.println("Se ejecuta la tarea 5");
            }
        }
    }//GEN-LAST:event_jt_cve_endsKeyPressed

   
    private void btn_descuentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_descuentoActionPerformed
        
        int valdsct = Integer.parseInt(jtf_desc.getText().trim());
        //Float ftdsct = Float.parseFloat(jtf_desc.getText().trim());
        
        if (jtf_desc.getText().isEmpty() || jtf_cve.getText().isEmpty() || jtf_expediente.getText().isEmpty()) 
        {
            JOptionPane.showMessageDialog(null, "No hay registros por actualizar");
        }else if(valdsct > 100){
            JOptionPane.showMessageDialog(null, "Descuento Mayor a 100");
            jtf_desc.grabFocus();
        }else if (jtf_desc.getText().length()>0 ) {

            FileHandler fileHandler = null;
            try {
                fileHandler = new FileHandler("C:/Reportescap/Logs/LogDate.txt");
            } catch (IOException ex) {
                Logger.getLogger(jfAplicarDescuento.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SecurityException ex) {
                Logger.getLogger(jfAplicarDescuento.class.getName()).log(Level.SEVERE, null, ex);
            }
            SimpleFormatter formatter = new SimpleFormatter();
            fileHandler.setFormatter(formatter);
            LOG.addHandler(fileHandler);
            fileHandler.setLevel(Level.ALL);
                
            int valorA;
            String pacExp;
            String cve;
            
            valorA = Integer.parseInt(jtf_desc.getText().trim());
            pacExp = jtf_expediente.getText().trim();
            cve = jtf_cve.getText().trim();
            
            //String sql = "UPDATE DESPAC SET DSCTO = ? WHERE PACNEXP='?' AND PRECVE='?' ";
            String sql = "UPDATE DESPAC SET DSCTO="+valorA+" WHERE PACNEXP='"+pacExp+"' AND PRECVE='"+cve+"'";

            try {
                Connection conn = Init_conn();
                Statement stmt = conn.createStatement();
                //PreparedStatement ps = null;
                conn.setAutoCommit(false);
                //ps = conn.prepareStatement(sql);
                
                //Integer valueA = Integer.parseInt(jtf_desc.getText());
                //Float valueA = Float.parseFloat(jtf_desc.getText());
                //ps.setInt(1, valorA);
                //ps.setString(2, pacExp);
                //ps.setString(3, cve);
                //ps.executeUpdate();
                stmt.executeUpdate(sql);
                conn.commit();
                refresh();
                jl_mensaje.setVisible(true);
                jl_mensaje.setText("Registro Actualizado");

                LOG.log(Level.INFO, sql);
                
            }catch (SQLException ex) {
                /*ex.printStackTrace();*/
                //JOptionPane.showMessageDialog(null, ex.getMessage() +" "+ ex.getErrorCode()+" "+ex.getSQLState());
                //Logger.getLogger(jfAplicarDescuento.class.getName()).log(Level.INFO, null, ex);
                LOG.log(Level.INFO, ex.getSQLState()+" "+ex.getErrorCode() +" " +ex.getCause() + "Error de SQL " + sql +" "+ ex.getMessage());
                JOptionPane.showMessageDialog(this, ex.getSQLState() +" " +ex.getErrorCode()+" "+ ex.getCause()+"\n Error de SQL " + sql +"\n "+ ex.getMessage());
                
            }catch(SecurityException e){
                JOptionPane.showMessageDialog(null, "No hay permisos para ejecutar el update" + e.getMessage() + "Error de Seguridad");
                
            }catch (Exception es){
                LOG.log(Level.INFO, es.getMessage() +" Causa del error: "+es.getCause() + " "+ sql + "Error de Excepcion" + jtf_desc.getText());
                JOptionPane.showMessageDialog(this, es.getMessage()+" " +es.getCause()+ " "+ sql + "\n Error de Excepcion "+ jtf_desc.getText());
            }/*finally{
                LOG.log(Level.INFO, "Verificar el query "+ sql);
            }*/
        }
    }//GEN-LAST:event_btn_descuentoActionPerformed

    private void jt_cve_espFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jt_cve_espFocusGained
        jrb_cve_esp.setSelected(true);
        jt_cve_init.setText("");
        jt_cve_ends.setText("");
    }//GEN-LAST:event_jt_cve_espFocusGained

    private void jt_cve_initFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jt_cve_initFocusGained
        jrb_cve_init.setSelected(true);
        jt_cve_esp.setText("");
        jt_cve_ends.setText("");
    }//GEN-LAST:event_jt_cve_initFocusGained

    private void jt_cve_endsFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jt_cve_endsFocusGained
        jrb_cve_ends.setSelected(true);
        jt_cve_esp.setText("");
        jt_cve_init.setText("");
    }//GEN-LAST:event_jt_cve_endsFocusGained

    private void jt_cve_espKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jt_cve_espKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLowerCase(c)) {
            String cad = ("" + c).toUpperCase();
            c = cad.charAt(0);
            evt.setKeyChar(c);
        }
    }//GEN-LAST:event_jt_cve_espKeyTyped

    private void jt_cve_initKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jt_cve_initKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLowerCase(c)) {
            String cad = ("" + c).toUpperCase();
            c = cad.charAt(0);
            evt.setKeyChar(c);
        }
    }//GEN-LAST:event_jt_cve_initKeyTyped

    private void jt_cve_endsKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jt_cve_endsKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLowerCase(c)) {
            String cad = ("" + c).toUpperCase();
            c = cad.charAt(0);
            evt.setKeyChar(c);
        }
    }//GEN-LAST:event_jt_cve_endsKeyTyped

    private void jt_consulta_desctMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jt_consulta_desctMouseClicked
        muestraGrid();
    }//GEN-LAST:event_jt_consulta_desctMouseClicked

    private void jt_consulta_desctKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jt_consulta_desctKeyReleased
        muestraGrid();
    }//GEN-LAST:event_jt_consulta_desctKeyReleased

    private void jtf_descKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtf_descKeyTyped
        Character cv = evt.getKeyChar();
        if(cv == '.'){
            JOptionPane.showMessageDialog(null, "No se permiten Decimales");
            evt.consume();
        }
     
    }//GEN-LAST:event_jtf_descKeyTyped

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(jfAplicarDescuento.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(jfAplicarDescuento.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(jfAplicarDescuento.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(jfAplicarDescuento.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                new jfAplicarDescuento().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnConsulta;
    private javax.swing.ButtonGroup btnGrupo;
    private javax.swing.JButton btn_descuento;
    private javax.swing.JButton btn_exportar;
    private javax.swing.JButton btn_imprimir;
    private javax.swing.JButton btn_limpiar;
    private javax.swing.JButton btn_menu_principal;
    private javax.swing.JButton btn_reporte;
    private javax.swing.JButton btn_salir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel jl_clave_texto;
    private javax.swing.JLabel jl_cve_label;
    private javax.swing.JLabel jl_datos_pacdesc;
    private javax.swing.JLabel jl_etiqueta;
    private javax.swing.JLabel jl_expediente;
    private javax.swing.JLabel jl_expediente_label;
    private javax.swing.JLabel jl_mensaje;
    private javax.swing.JLabel jl_nombre_pac;
    private javax.swing.JLabel jl_paciente_nombre;
    private javax.swing.JLabel jl_titulo;
    private javax.swing.JLabel jl_tt_reg;
    private javax.swing.JProgressBar jp_bar;
    private javax.swing.JRadioButton jrb_cve_ends;
    private javax.swing.JRadioButton jrb_cve_esp;
    private javax.swing.JRadioButton jrb_cve_init;
    private javax.swing.JTable jt_consulta_desct;
    private javax.swing.JTextField jt_cve_ends;
    private javax.swing.JTextField jt_cve_esp;
    private javax.swing.JTextField jt_cve_init;
    private javax.swing.JTextField jt_expediente;
    private javax.swing.JTextField jtf_cve;
    private javax.swing.JTextField jtf_desc;
    private javax.swing.JTextField jtf_expediente;
    private java.awt.Panel panel1;
    // End of variables declaration//GEN-END:variables

    class PBsDescExp extends SwingWorker<Void, Void> {

        @Override
        protected Void doInBackground() throws Exception {
            jp_bar.setVisible(true);
            jp_bar.setIndeterminate(true);
            jl_etiqueta.setVisible(true);
            jl_nombre_pac.setVisible(true);
            jl_paciente_nombre.setVisible(true);
            nomPac();
            sDescExp();
            return null;
        }

        @Override
        protected void done() {
            jp_bar.setVisible(false);
            jl_etiqueta.setVisible(false);
        }
    }

    class PBsDescCve extends SwingWorker<Void, Void> {

        @Override
        protected Void doInBackground() throws Exception {
            jp_bar.setVisible(true);
            jp_bar.setIndeterminate(true);
            jl_etiqueta.setVisible(true);
            jl_nombre_pac.setVisible(false);
            jl_paciente_nombre.setVisible(false);
            sDescCve();
            return null;
        }

        @Override
        protected void done() {
            jp_bar.setVisible(false);
            jl_etiqueta.setVisible(false);
        }
    }

    class PBsDescExpCveEsp extends SwingWorker<Void, Void> {

        @Override
        protected Void doInBackground() throws Exception {
            jp_bar.setVisible(true);
            jp_bar.setIndeterminate(true);
            jl_etiqueta.setVisible(true);
            jl_nombre_pac.setVisible(true);
            jl_paciente_nombre.setVisible(true);
            nomPac();
            sDescExpCveEsp();
            return null;
        }

        @Override
        protected void done() {
            jp_bar.setVisible(false);
            jl_etiqueta.setVisible(false);
        }
    }

    class PBsDescExpCveInit extends SwingWorker<Void, Void> {

        @Override
        protected Void doInBackground() throws Exception {
            jp_bar.setVisible(true);
            jp_bar.setIndeterminate(true);
            jl_etiqueta.setVisible(true);
            jl_nombre_pac.setVisible(true);
            jl_paciente_nombre.setVisible(true);
            nomPac();
            sDescExpCveInit();
            return null;
        }

        @Override
        protected void done() {
            jp_bar.setVisible(false);
            jl_etiqueta.setVisible(false);
        }
    }

    class PBsDescExpCveEnds extends SwingWorker<Void, Void> {

        @Override
        protected Void doInBackground() throws Exception {
            jp_bar.setVisible(true);
            jp_bar.setIndeterminate(true);
            jl_etiqueta.setVisible(true);
            jl_nombre_pac.setVisible(true);
            jl_paciente_nombre.setVisible(true);
            nomPac();
            sDescExpCveEnds();
            return null;
        }

        @Override
        protected void done() {
            jp_bar.setVisible(false);
            jl_etiqueta.setVisible(false);
        }
    }
}
