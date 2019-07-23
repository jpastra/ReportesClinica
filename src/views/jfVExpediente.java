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
public class jfVExpediente extends javax.swing.JFrame {
ResultSet rs;
private PBvExpDHSubl   task01Inf;
private PBvExpDSubl   task02Inf;
private PBvExpCveDHSubl   task03Inf;
private PBvExpCveDSubl   task04Inf;
private PBvNoExpSubl   task05Inf;
private PBvNoExpCveSubl   task06Inf;
private PBvNoExpDHSubl   task07Inf;
private PBvNoExpDSubl   task08Inf;
private PBvNoExpCveDHSubl    task09Inf;
private PBvNoExpCveDSubl   task10Inf;
private PBvExpDHInyec   task11Inf;
private PBvExpDInyec   task12Inf;
private PBvExpCveDHInyec   task13Inf;
private PBvExpCveDInyec   task14Inf;
private PBvExpCveDHInyecDesc   task15Inf;
private PBvExpCveDInyecDesc   task16Inf;
private PBvNoExpDHInyec   task17Inf;
private PBvNoExpDInyec   task18Inf;
private PBvNoExpDHInyecDesc   task19Inf;
private PBvNoExpDInyecDesc   task20Inf;
private PBvNoExpCveDHInyec   task21Inf;
private PBvNoExpCveDInyec   task22Inf;
private PBvNoExpCveDHInyecDesc   task23Inf;
private PBvNoExpCveDInyecDesc   task24Inf;
private PBvNoExpInyec   task25Inf;
private PBvNoExpCveInyec   task26Inf;
private PBvNoExpCveInyecDesc   task27Inf;
private PBvNoExpInyecDesc   task28Inf;
private PBvCveInyecDesc   task29Inf;
private PBvCveInyec   task30Inf;
private PBvDHInyecDesc   task31Inf;
private PBvDInyecDesc   task32Inf;
    
    /**
     * Creates new form jfVacunas
     */
    public jfVExpediente() {
        initComponents();
        setLocationRelativeTo(null);
        //setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(false);
        jl_titulo.setHorizontalAlignment(JLabel.CENTER);
        jl_titulo.setForeground(Color.white);
        jl_titulo.setOpaque(true);
        jr_inyec.setSelected(true);
        jl_etiqueta.setVisible(false);
        jp_bar.setVisible(false);
    }
    
    public void vExpDHSubl() throws SQLException {

        String formato = "dd/MM/yyyy";
        Date date;
        date = jd_Desde.getDate();
        SimpleDateFormat sdfD = new SimpleDateFormat(formato);
        String strDateD = sdfD.format(date);

        Date date2 = jd_Hasta.getDate();
        SimpleDateFormat sdfH = new SimpleDateFormat(formato);
        String strDateH = sdfH.format(date2);

        rs = Conexiondb.Consulta("SELECT A.PACNEXP AS EXPEDIENTE, A.PACNOM AS NOMBRE, B.INMCANT AS CANTIDAD, B.INMADM AS TIPO, B.INMCVE  AS CLAVE, B.INMDES AS DESCRIPCION, B.INMCON AS CONCENTRACION, B.INMDOSIS AS DOSIS, TO_CHAR(B.INMFEC,'dd/MM/yyyy') AS FECHA, B.INMHOR AS HORA FROM PACCAT A, INMPAC B WHERE B.INMFEC BETWEEN TO_DATE('" + strDateD + "', 'dd/MM/yyyy') AND TO_DATE('" + strDateH + "', 'dd/MM/yyyy') AND A.PACNEXP=B.PACNEXP AND B.INMDES LIKE '%SUBL%' GROUP BY A.PACNEXP, A.PACNOM, B.INMCANT, B.INMADM, B.INMCVE, B.INMDES, B.INMCON, B.INMDOSIS, B.INMFEC, B.INMHOR ORDER BY A.PACNEXP, B.INMCVE, B.INMFEC");
        
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
                a = String.valueOf(jt_consulta_vacunas.getValueAt(fila, 2));
                b = Integer.valueOf(a);
                total = total + b;
            }

            modelo.addRow(new Object[]{"", "Total", String.valueOf(total)});

            rs.close();
            tt_rec();
        }
    }
    
    public void vExpDSubl() throws SQLException {

        String formato = "dd/MM/yyyy";
        Date date;
        date = jd_Desde.getDate();
        SimpleDateFormat sdfD = new SimpleDateFormat(formato);
        String strDateD = sdfD.format(date);

        rs = Conexiondb.Consulta("SELECT A.PACNEXP AS EXPEDIENTE, A.PACNOM AS NOMBRE, B.INMCANT AS CANTIDAD, B.INMADM AS TIPO, B.INMCVE  AS CLAVE, B.INMDES AS DESCRIPCION, B.INMCON AS CONCENTRACION, B.INMDOSIS AS DOSIS, TO_CHAR(B.INMFEC,'dd/MM/yyyy') AS FECHA, B.INMHOR AS HORA FROM PACCAT A, INMPAC B WHERE B.INMFEC = TO_DATE('" + strDateD + "', 'dd/MM/yyyy') AND A.PACNEXP=B.PACNEXP AND B.INMDES LIKE '%SUBL%' GROUP BY A.PACNEXP, A.PACNOM, B.INMCANT, B.INMADM, B.INMCVE, B.INMDES, B.INMCON, B.INMDOSIS, B.INMFEC, B.INMHOR ORDER BY A.PACNEXP, B.INMCVE, B.INMFEC");
        
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
                a = String.valueOf(jt_consulta_vacunas.getValueAt(fila, 2));
                b = Integer.valueOf(a);
                total = total + b;
            }

            modelo.addRow(new Object[]{"", "Total", String.valueOf(total)});

            rs.close();
            tt_rec();
        }
    }
    
    public void vExpCveDHSubl() throws SQLException {

        String formato = "dd/MM/yyyy";
        Date date;
        date = jd_Desde.getDate();
        SimpleDateFormat sdfD = new SimpleDateFormat(formato);
        String strDateD = sdfD.format(date);

        Date date2 = jd_Hasta.getDate();
        SimpleDateFormat sdfH = new SimpleDateFormat(formato);
        String strDateH = sdfH.format(date2);

        rs = Conexiondb.Consulta("SELECT A.PACNEXP AS EXPEDIENTE, A.PACNOM AS NOMBRE, B.INMCANT AS CANTIDAD, B.INMADM AS TIPO, B.INMCVE  AS CLAVE, B.INMDES AS DESCRIPCION, B.INMCON AS CONCENTRACION, B.INMDOSIS AS DOSIS, TO_CHAR(B.INMFEC,'dd/MM/yyyy') AS FECHA, B.INMHOR AS HORA FROM PACCAT A, INMPAC B WHERE B.INMFEC BETWEEN TO_DATE('" + strDateD + "', 'dd/MM/yyyy') AND TO_DATE('" + strDateH + "', 'dd/MM/yyyy') AND B.INMCVE IN (" + this.jt_cve.getText() + ") AND A.PACNEXP=B.PACNEXP AND B.INMDES LIKE '%SUBL%' GROUP BY A.PACNEXP, A.PACNOM, B.INMCANT, B.INMADM, B.INMCVE, B.INMDES, B.INMCON, B.INMDOSIS, B.INMFEC, B.INMHOR ORDER BY A.PACNEXP, B.INMCVE, B.INMFEC");

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
                a = String.valueOf(jt_consulta_vacunas.getValueAt(fila, 2));
                b = Integer.valueOf(a);
                total = total + b;
            }

            modelo.addRow(new Object[]{"", "Total", String.valueOf(total)});

            rs.close();
            tt_rec();
        }
    }

    
    public void vExpCveDSubl() throws SQLException {

        String formato = "dd/MM/yyyy";
        Date date;
        date = jd_Desde.getDate();
        SimpleDateFormat sdfD = new SimpleDateFormat(formato);
        String strDateD = sdfD.format(date);

        rs = Conexiondb.Consulta("SELECT A.PACNEXP AS EXPEDIENTE, A.PACNOM AS NOMBRE, B.INMCANT AS CANTIDAD, B.INMADM AS TIPO, B.INMCVE  AS CLAVE, B.INMDES AS DESCRIPCION, B.INMCON AS CONCENTRACION, B.INMDOSIS AS DOSIS, TO_CHAR(B.INMFEC,'dd/MM/yyyy') AS FECHA, B.INMHOR AS HORA FROM PACCAT A, INMPAC B WHERE B.INMFEC = TO_DATE('" + strDateD + "', 'dd/MM/yyyy') AND B.INMCVE IN (" + this.jt_cve.getText() + ") AND A.PACNEXP=B.PACNEXP AND B.INMDES LIKE '%SUBL%' GROUP BY A.PACNEXP, A.PACNOM, B.INMCANT, B.INMADM, B.INMCVE, B.INMDES, B.INMCON, B.INMDOSIS, B.INMFEC, B.INMHOR ORDER BY A.PACNEXP, B.INMCVE, B.INMFEC");

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
                a = String.valueOf(jt_consulta_vacunas.getValueAt(fila, 2));
                b = Integer.valueOf(a);
                total = total + b;
            }

            modelo.addRow(new Object[]{"", "Total", String.valueOf(total)});

            rs.close();
            tt_rec();
        }
    }

    public void vNoExpSubl() throws SQLException {

        rs = Conexiondb.Consulta("SELECT A.PACNEXP AS EXPEDIENTE, A.PACNOM AS NOMBRE, B.INMCANT AS CANTIDAD, B.INMADM AS TIPO, B.INMCVE  AS CLAVE, B.INMDES AS DESCRIPCION, B.INMCON AS CONCENTRACION, B.INMDOSIS AS DOSIS, TO_CHAR(B.INMFEC,'dd/MM/yyyy') AS FECHA, B.INMHOR AS HORA FROM PACCAT A, INMPAC B WHERE B.PACNEXP='" + this.jt_expediente.getText() + "' AND A.PACNEXP=B.PACNEXP AND B.INMDES LIKE '%SUBL%' GROUP BY A.PACNEXP, A.PACNOM, B.INMCANT, B.INMADM, B.INMCVE, B.INMDES, B.INMCON, B.INMDOSIS, B.INMFEC, B.INMHOR ORDER BY A.PACNEXP, B.INMCVE, B.INMFEC");

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
                a = String.valueOf(jt_consulta_vacunas.getValueAt(fila, 2));
                b = Integer.valueOf(a);
                total = total + b;
            }

            modelo.addRow(new Object[]{"", "Total", String.valueOf(total)});

            rs.close();
            tt_rec();
        }
    }
    
        
    public void vNoExpCveSubl() throws SQLException {

        rs = Conexiondb.Consulta("SELECT A.PACNEXP AS EXPEDIENTE, A.PACNOM AS NOMBRE, B.INMCANT AS CANTIDAD, B.INMADM AS TIPO, B.INMCVE  AS CLAVE, B.INMDES AS DESCRIPCION, B.INMCON AS CONCENTRACION, B.INMDOSIS AS DOSIS, TO_CHAR(B.INMFEC,'dd/MM/yyyy') AS FECHA, B.INMHOR AS HORA FROM PACCAT A, INMPAC B WHERE B.PACNEXP='" + this.jt_expediente.getText() + "' AND B.INMCVE IN (" + this.jt_cve.getText() + ") AND A.PACNEXP=B.PACNEXP AND B.INMDES LIKE '%SUBL%' GROUP BY A.PACNEXP, A.PACNOM, B.INMCANT, B.INMADM, B.INMCVE, B.INMDES, B.INMCON, B.INMDOSIS, B.INMFEC, B.INMHOR ORDER BY A.PACNEXP, B.INMCVE, B.INMFEC");

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
                a = String.valueOf(jt_consulta_vacunas.getValueAt(fila, 2));
                b = Integer.valueOf(a);
                total = total + b;
            }

            modelo.addRow(new Object[]{"", "Total", String.valueOf(total)});

            rs.close();
            tt_rec();
        }
    }
    
    public void vNoExpDHSubl() throws SQLException {

        String formato = "dd/MM/yyyy";
        Date date;
        date = jd_Desde.getDate();
        SimpleDateFormat sdfD = new SimpleDateFormat(formato);
        String strDateD = sdfD.format(date);

        Date date2 = jd_Hasta.getDate();
        SimpleDateFormat sdfH = new SimpleDateFormat(formato);
        String strDateH = sdfH.format(date2);

        rs = Conexiondb.Consulta("SELECT A.PACNEXP AS EXPEDIENTE, A.PACNOM AS NOMBRE, B.INMCANT AS CANTIDAD, B.INMADM AS TIPO, B.INMCVE  AS CLAVE, B.INMDES AS DESCRIPCION, B.INMCON AS CONCENTRACION, B.INMDOSIS AS DOSIS, TO_CHAR(B.INMFEC,'dd/MM/yyyy') AS FECHA, B.INMHOR AS HORA FROM PACCAT A, INMPAC B WHERE B.INMFEC BETWEEN TO_DATE('" + strDateD + "', 'dd/MM/yyyy') AND TO_DATE('" + strDateH + "', 'dd/MM/yyyy') AND B.PACNEXP='" + this.jt_expediente.getText() + "' AND A.PACNEXP=B.PACNEXP AND B.INMDES LIKE '%SUBL%' GROUP BY A.PACNEXP, A.PACNOM, B.INMCANT, B.INMADM, B.INMCVE, B.INMDES, B.INMCON, B.INMDOSIS, B.INMFEC, B.INMHOR ORDER BY A.PACNEXP, B.INMCVE, B.INMFEC");

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
                a = String.valueOf(jt_consulta_vacunas.getValueAt(fila, 2));
                b = Integer.valueOf(a);
                total = total + b;
            }

            modelo.addRow(new Object[]{"", "Total", String.valueOf(total)});

            rs.close();
            tt_rec();
        }
    }
     
    public void vNoExpDSubl() throws SQLException {

        String formato = "dd/MM/yyyy";
        Date date;
        date = jd_Desde.getDate();
        SimpleDateFormat sdfD = new SimpleDateFormat(formato);
        String strDateD = sdfD.format(date);

        rs = Conexiondb.Consulta("SELECT A.PACNEXP AS EXPEDIENTE, A.PACNOM AS NOMBRE, B.INMCANT AS CANTIDAD, B.INMADM AS TIPO, B.INMCVE  AS CLAVE, B.INMDES AS DESCRIPCION, B.INMCON AS CONCENTRACION, B.INMDOSIS AS DOSIS, TO_CHAR(B.INMFEC,'dd/MM/yyyy') AS FECHA, B.INMHOR AS HORA FROM PACCAT A, INMPAC B WHERE B.INMFEC = TO_DATE('" + strDateD + "', 'dd/MM/yyyy') AND B.PACNEXP='" + this.jt_expediente.getText() + "' AND A.PACNEXP=B.PACNEXP AND B.INMDES LIKE '%SUBL%' GROUP BY A.PACNEXP, A.PACNOM, B.INMCANT, B.INMADM, B.INMCVE, B.INMDES, B.INMCON, B.INMDOSIS, B.INMFEC, B.INMHOR ORDER BY A.PACNEXP, B.INMCVE, B.INMFEC");

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
                a = String.valueOf(jt_consulta_vacunas.getValueAt(fila, 2));
                b = Integer.valueOf(a);
                total = total + b;
            }

            modelo.addRow(new Object[]{"", "Total", String.valueOf(total)});

            rs.close();
            tt_rec();
        }
    }
  
        
    public void vNoExpCveDHSubl() throws SQLException {

        String formato = "dd/MM/yyyy";
        Date date;
        date = jd_Desde.getDate();
        SimpleDateFormat sdfD = new SimpleDateFormat(formato);
        String strDateD = sdfD.format(date);

        Date date2 = jd_Hasta.getDate();
        SimpleDateFormat sdfH = new SimpleDateFormat(formato);
        String strDateH = sdfH.format(date2);

        rs = Conexiondb.Consulta("SELECT A.PACNEXP AS EXPEDIENTE, A.PACNOM AS NOMBRE, B.INMCANT AS CANTIDAD, B.INMADM AS TIPO, B.INMCVE  AS CLAVE, B.INMDES AS DESCRIPCION, B.INMCON AS CONCENTRACION, B.INMDOSIS AS DOSIS, TO_CHAR(B.INMFEC,'dd/MM/yyyy') AS FECHA, B.INMHOR AS HORA FROM PACCAT A, INMPAC B WHERE B.INMFEC BETWEEN TO_DATE('" + strDateD + "', 'dd/MM/yyyy') AND TO_DATE('" + strDateH + "', 'dd/MM/yyyy') AND B.PACNEXP='" + this.jt_expediente.getText() + "'  AND B.INMCVE IN (" + this.jt_cve.getText() + ") AND A.PACNEXP=B.PACNEXP AND B.INMDES LIKE '%SUBL%' GROUP BY A.PACNEXP, A.PACNOM, B.INMCANT, B.INMADM, B.INMCVE, B.INMDES, B.INMCON, B.INMDOSIS, B.INMFEC, B.INMHOR ORDER BY A.PACNEXP, B.INMCVE, B.INMFEC");

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
                a = String.valueOf(jt_consulta_vacunas.getValueAt(fila, 2));
                b = Integer.valueOf(a);
                total = total + b;
            }

            modelo.addRow(new Object[]{"", "Total", String.valueOf(total)});

            rs.close();
            tt_rec();
        }
    }

    public void vNoExpCveDSubl() throws SQLException {

        String formato = "dd/MM/yyyy";
        Date date;
        date = jd_Desde.getDate();
        SimpleDateFormat sdfD = new SimpleDateFormat(formato);
        String strDateD = sdfD.format(date);

        rs = Conexiondb.Consulta("SELECT A.PACNEXP AS EXPEDIENTE, A.PACNOM AS NOMBRE, B.INMCANT AS CANTIDAD, B.INMADM AS TIPO, B.INMCVE  AS CLAVE, B.INMDES AS DESCRIPCION, B.INMCON AS CONCENTRACION, B.INMDOSIS AS DOSIS, TO_CHAR(B.INMFEC,'dd/MM/yyyy') AS FECHA, B.INMHOR AS HORA FROM PACCAT A, INMPAC B WHERE B.INMFEC = TO_DATE('" + strDateD + "', 'dd/MM/yyyy') AND B.PACNEXP='" + this.jt_expediente.getText() + "'  AND B.INMCVE IN (" + this.jt_cve.getText() + ") AND A.PACNEXP=B.PACNEXP AND B.INMDES LIKE '%SUBL%' GROUP BY A.PACNEXP, A.PACNOM, B.INMCANT, B.INMADM, B.INMCVE, B.INMDES, B.INMCON, B.INMDOSIS, B.INMFEC, B.INMHOR ORDER BY A.PACNEXP, B.INMCVE, B.INMFEC");

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
                a = String.valueOf(jt_consulta_vacunas.getValueAt(fila, 2));
                b = Integer.valueOf(a);
                total = total + b;
            }

            modelo.addRow(new Object[]{"", "Total", String.valueOf(total)});

            rs.close();
            tt_rec();
        }
    }

    /**
     * Aqui comienzan los metodos para las consultas de vacunas inyectadas
     * 
     */
    public void vExpDHInyec() throws SQLException {

        String formato = "dd/MM/yyyy";
        Date date;
        date = jd_Desde.getDate();
        SimpleDateFormat sdfD = new SimpleDateFormat(formato);
        String strDateD = sdfD.format(date);

        Date date2 = jd_Hasta.getDate();
        SimpleDateFormat sdfH = new SimpleDateFormat(formato);
        String strDateH = sdfH.format(date2);

        rs = Conexiondb.Consulta("SELECT A.PACNEXP AS EXPEDIENTE, A.PACNOM AS NOMBRE, B.INMCANT AS CANTIDAD, B.INMADM AS TIPO, B.INMCVE  AS CLAVE, B.INMDES AS DESCRIPCION, B.INMCON AS CONCENTRACION, B.INMDOSIS AS DOSIS, TO_CHAR(B.INMFEC,'dd/MM/yyyy') AS FECHA, B.INMHOR AS HORA FROM PACCAT A, INMPAC B WHERE B.INMFEC BETWEEN TO_DATE('" + strDateD + "', 'dd/MM/yyyy') AND TO_DATE('" + strDateH + "', 'dd/MM/yyyy') AND A.PACNEXP=B.PACNEXP AND B.INMDES NOT LIKE '%SUBL%' GROUP BY A.PACNEXP, A.PACNOM, B.INMCANT, B.INMADM, B.INMCVE, B.INMDES, B.INMCON, B.INMDOSIS, B.INMFEC, B.INMHOR ORDER BY A.PACNEXP, B.INMCVE, B.INMFEC");

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
                a = String.valueOf(jt_consulta_vacunas.getValueAt(fila, 2));
                b = Integer.valueOf(a);
                total = total + b;
            }

            modelo.addRow(new Object[]{"", "Total", String.valueOf(total)});

            rs.close();
            tt_rec();
        }
    }
    
    public void vExpDInyec() throws SQLException {

        String formato = "dd/MM/yyyy";
        Date date;
        date = jd_Desde.getDate();
        SimpleDateFormat sdfD = new SimpleDateFormat(formato);
        String strDateD = sdfD.format(date);

        rs = Conexiondb.Consulta("SELECT A.PACNEXP AS EXPEDIENTE, A.PACNOM AS NOMBRE, B.INMCANT AS CANTIDAD, B.INMADM AS TIPO, B.INMCVE  AS CLAVE, B.INMDES AS DESCRIPCION, B.INMCON AS CONCENTRACION, B.INMDOSIS AS DOSIS, TO_CHAR(B.INMFEC,'dd/MM/yyyy') AS FECHA, B.INMHOR AS HORA FROM PACCAT A, INMPAC B WHERE B.INMFEC = TO_DATE('" + strDateD + "', 'dd/MM/yyyy') AND A.PACNEXP=B.PACNEXP AND B.INMDES NOT LIKE '%SUBL%' GROUP BY A.PACNEXP, A.PACNOM, B.INMCANT, B.INMADM, B.INMCVE, B.INMDES, B.INMCON, B.INMDOSIS, B.INMFEC, B.INMHOR ORDER BY A.PACNEXP, B.INMCVE, B.INMFEC");

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
                a = String.valueOf(jt_consulta_vacunas.getValueAt(fila, 2));
                b = Integer.valueOf(a);
                total = total + b;
            }

            modelo.addRow(new Object[]{"", "Total", String.valueOf(total)});

            rs.close();
            tt_rec();
        }
    }

    public void vExpCveDHInyec() throws SQLException {

        String formato = "dd/MM/yyyy";
        Date date;
        date = jd_Desde.getDate();
        SimpleDateFormat sdfD = new SimpleDateFormat(formato);
        String strDateD = sdfD.format(date);

        Date date2 = jd_Hasta.getDate();
        SimpleDateFormat sdfH = new SimpleDateFormat(formato);
        String strDateH = sdfH.format(date2);

        rs = Conexiondb.Consulta("SELECT A.PACNEXP AS EXPEDIENTE, A.PACNOM AS NOMBRE, B.INMCANT AS CANTIDAD, B.INMADM AS TIPO, B.INMCVE  AS CLAVE, B.INMDES AS DESCRIPCION, B.INMCON AS CONCENTRACION, B.INMDOSIS AS DOSIS, TO_CHAR(B.INMFEC,'dd/MM/yyyy') AS FECHA, B.INMHOR AS HORA FROM PACCAT A, INMPAC B WHERE B.INMFEC BETWEEN TO_DATE('" + strDateD + "', 'dd/MM/yyyy') AND TO_DATE('" + strDateH + "', 'dd/MM/yyyy') AND B.INMCVE IN (" + this.jt_cve.getText() + ") AND A.PACNEXP=B.PACNEXP AND B.INMDES NOT LIKE '%SUBL%' GROUP BY A.PACNEXP, A.PACNOM, B.INMCANT, B.INMADM, B.INMCVE, B.INMDES, B.INMCON, B.INMDOSIS, B.INMFEC, B.INMHOR ORDER BY A.PACNEXP, B.INMCVE, B.INMFEC");

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
                a = String.valueOf(jt_consulta_vacunas.getValueAt(fila, 2));
                b = Integer.valueOf(a);
                total = total + b;
            }

            modelo.addRow(new Object[]{"", "Total", String.valueOf(total)});

            rs.close();
            tt_rec();
        }
    }

    
    public void vExpCveDInyec() throws SQLException {

        String formato = "dd/MM/yyyy";
        Date date;
        date = jd_Desde.getDate();
        SimpleDateFormat sdfD = new SimpleDateFormat(formato);
        String strDateD = sdfD.format(date);

        rs = Conexiondb.Consulta("SELECT A.PACNEXP AS EXPEDIENTE, A.PACNOM AS NOMBRE, B.INMCANT AS CANTIDAD, B.INMADM AS TIPO, B.INMCVE  AS CLAVE, B.INMDES AS DESCRIPCION, B.INMCON AS CONCENTRACION, B.INMDOSIS AS DOSIS, TO_CHAR(B.INMFEC,'dd/MM/yyyy') AS FECHA, B.INMHOR AS HORA FROM PACCAT A, INMPAC B WHERE B.INMFEC = TO_DATE('" + strDateD + "', 'dd/MM/yyyy') AND B.INMCVE IN (" + this.jt_cve.getText() + ") AND A.PACNEXP=B.PACNEXP AND B.INMDES NOT LIKE '%SUBL%' GROUP BY A.PACNEXP, A.PACNOM, B.INMCANT, B.INMADM, B.INMCVE, B.INMDES, B.INMCON, B.INMDOSIS, B.INMFEC, B.INMHOR ORDER BY A.PACNEXP, B.INMCVE, B.INMFEC");

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
                a = String.valueOf(jt_consulta_vacunas.getValueAt(fila, 2));
                b = Integer.valueOf(a);
                total = total + b;
            }

            modelo.addRow(new Object[]{"", "Total", String.valueOf(total)});

            rs.close();
            tt_rec();
        }
    }

    public void vExpCveDHInyecDesc() throws SQLException {

        String formato = "dd/MM/yyyy";
        Date date;
        date = jd_Desde.getDate();
        SimpleDateFormat sdfD = new SimpleDateFormat(formato);
        String strDateD = sdfD.format(date);

        Date date2 = jd_Hasta.getDate();
        SimpleDateFormat sdfH = new SimpleDateFormat(formato);
        String strDateH = sdfH.format(date2);

        rs = Conexiondb.Consulta("SELECT A.PACNEXP AS EXPEDIENTE, A.PACNOM AS NOMBRE, B.INMCANT AS CANTIDAD, B.INMADM AS TIPO, B.INMCVE  AS CLAVE, B.INMDES AS DESCRIPCION, B.INMCON AS CONCENTRACION, B.INMDOSIS AS DOSIS, TO_CHAR(B.INMFEC,'dd/MM/yyyy') AS FECHA, B.INMHOR AS HORA FROM PACCAT A, INMPAC B WHERE B.INMFEC BETWEEN TO_DATE('" + strDateD + "', 'dd/MM/yyyy') AND TO_DATE('" + strDateH + "', 'dd/MM/yyyy') AND B.INMCVE IN (" + this.jt_cve.getText() + ") AND A.PACNEXP=B.PACNEXP AND B.INMDES NOT LIKE '%SUBL%' AND B.INMDES LIKE '%" + this.jt_descripcion.getText() + "%' GROUP BY A.PACNEXP, A.PACNOM, B.INMCANT, B.INMADM, B.INMCVE, B.INMDES, B.INMCON, B.INMDOSIS, B.INMFEC, B.INMHOR ORDER BY A.PACNEXP, B.INMCVE, B.INMFEC");

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
                a = String.valueOf(jt_consulta_vacunas.getValueAt(fila, 2));
                b = Integer.valueOf(a);
                total = total + b;
            }

            modelo.addRow(new Object[]{"", "Total", String.valueOf(total)});

            rs.close();
            tt_rec();
        }
    }

    public void vExpCveDInyecDesc() throws SQLException {

        String formato = "dd/MM/yyyy";
        Date date;
        date = jd_Desde.getDate();
        SimpleDateFormat sdfD = new SimpleDateFormat(formato);
        String strDateD = sdfD.format(date);

        rs = Conexiondb.Consulta("SELECT A.PACNEXP AS EXPEDIENTE, A.PACNOM AS NOMBRE, B.INMCANT AS CANTIDAD, B.INMADM AS TIPO, B.INMCVE  AS CLAVE, B.INMDES AS DESCRIPCION, B.INMCON AS CONCENTRACION, B.INMDOSIS AS DOSIS, TO_CHAR(B.INMFEC,'dd/MM/yyyy') AS FECHA, B.INMHOR AS HORA FROM PACCAT A, INMPAC B WHERE B.INMFEC = TO_DATE('" + strDateD + "', 'dd/MM/yyyy') AND B.INMCVE IN (" + this.jt_cve.getText() + ") AND A.PACNEXP=B.PACNEXP AND B.INMDES NOT LIKE '%SUBL%' AND B.INMDES LIKE '%" + this.jt_descripcion.getText() + "%' GROUP BY A.PACNEXP, A.PACNOM, B.INMCANT, B.INMADM, B.INMCVE, B.INMDES, B.INMCON, B.INMDOSIS, B.INMFEC, B.INMHOR ORDER BY A.PACNEXP, B.INMCVE, B.INMFEC");

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
                a = String.valueOf(jt_consulta_vacunas.getValueAt(fila, 2));
                b = Integer.valueOf(a);
                total = total + b;
            }

            modelo.addRow(new Object[]{"", "Total", String.valueOf(total)});

            rs.close();
            tt_rec();
        }
    }

    public void vNoExpDHInyec() throws SQLException {

        String formato = "dd/MM/yyyy";
        Date date;
        date = jd_Desde.getDate();
        SimpleDateFormat sdfD = new SimpleDateFormat(formato);
        String strDateD = sdfD.format(date);

        Date date2 = jd_Hasta.getDate();
        SimpleDateFormat sdfH = new SimpleDateFormat(formato);
        String strDateH = sdfH.format(date2);

        rs = Conexiondb.Consulta("SELECT A.PACNEXP AS EXPEDIENTE, A.PACNOM AS NOMBRE, B.INMCANT AS CANTIDAD, B.INMADM AS TIPO, B.INMCVE  AS CLAVE, B.INMDES AS DESCRIPCION, B.INMCON AS CONCENTRACION, B.INMDOSIS AS DOSIS, TO_CHAR(B.INMFEC,'dd/MM/yyyy') AS FECHA, B.INMHOR AS HORA FROM PACCAT A, INMPAC B WHERE B.INMFEC BETWEEN TO_DATE('" + strDateD + "', 'dd/MM/yyyy') AND TO_DATE('" + strDateH + "', 'dd/MM/yyyy') AND B.PACNEXP='" + this.jt_expediente.getText() + "' AND A.PACNEXP=B.PACNEXP AND B.INMDES NOT LIKE '%SUBL%' GROUP BY A.PACNEXP, A.PACNOM, B.INMCANT, B.INMADM, B.INMCVE, B.INMDES, B.INMCON, B.INMDOSIS, B.INMFEC, B.INMHOR ORDER BY A.PACNEXP, B.INMFEC");

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
                a = String.valueOf(jt_consulta_vacunas.getValueAt(fila, 2));
                b = Integer.valueOf(a);
                total = total + b;
            }

            modelo.addRow(new Object[]{"", "Total", String.valueOf(total)});

            rs.close();
            tt_rec();
        }
    }
     
    public void vNoExpDInyec() throws SQLException {

        String formato = "dd/MM/yyyy";
        Date date;
        date = jd_Desde.getDate();
        SimpleDateFormat sdfD = new SimpleDateFormat(formato);
        String strDateD = sdfD.format(date);

        rs = Conexiondb.Consulta("SELECT A.PACNEXP AS EXPEDIENTE, A.PACNOM AS NOMBRE, B.INMCANT AS CANTIDAD, B.INMADM AS TIPO, B.INMCVE  AS CLAVE, B.INMDES AS DESCRIPCION, B.INMCON AS CONCENTRACION, B.INMDOSIS AS DOSIS, TO_CHAR(B.INMFEC,'dd/MM/yyyy') AS FECHA, B.INMHOR AS HORA FROM PACCAT A, INMPAC B WHERE B.INMFEC = TO_DATE('" + strDateD + "', 'dd/MM/yyyy') AND B.PACNEXP='" + this.jt_expediente.getText() + "' AND A.PACNEXP=B.PACNEXP AND B.INMDES NOT LIKE '%SUBL%' GROUP BY A.PACNEXP, A.PACNOM, B.INMCANT, B.INMADM, B.INMCVE, B.INMDES, B.INMCON, B.INMDOSIS, B.INMFEC, B.INMHOR ORDER BY A.PACNEXP, B.INMCVE, B.INMFEC");

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
                a = String.valueOf(jt_consulta_vacunas.getValueAt(fila, 2));
                b = Integer.valueOf(a);
                total = total + b;
            }

            modelo.addRow(new Object[]{"", "Total", String.valueOf(total)});

            rs.close();
            tt_rec();
        }
    }
    
    public void vNoExpDHInyecDesc() throws SQLException {

        String formato = "dd/MM/yyyy";
        Date date;
        date = jd_Desde.getDate();
        SimpleDateFormat sdfD = new SimpleDateFormat(formato);
        String strDateD = sdfD.format(date);

        Date date2 = jd_Hasta.getDate();
        SimpleDateFormat sdfH = new SimpleDateFormat(formato);
        String strDateH = sdfH.format(date2);

        rs = Conexiondb.Consulta("SELECT A.PACNEXP AS EXPEDIENTE, A.PACNOM AS NOMBRE, B.INMCANT AS CANTIDAD, B.INMADM AS TIPO, B.INMCVE  AS CLAVE, B.INMDES AS DESCRIPCION, B.INMCON AS CONCENTRACION, B.INMDOSIS AS DOSIS, TO_CHAR(B.INMFEC,'dd/MM/yyyy') AS FECHA, B.INMHOR AS HORA FROM PACCAT A, INMPAC B WHERE B.INMFEC BETWEEN TO_DATE('" + strDateD + "', 'dd/MM/yyyy') AND TO_DATE('" + strDateH + "', 'dd/MM/yyyy') AND B.PACNEXP='" + this.jt_expediente.getText() + "' AND A.PACNEXP=B.PACNEXP AND B.INMDES NOT LIKE '%SUBL%' AND B.INMDES LIKE '%" + this.jt_descripcion.getText() + "%' GROUP BY A.PACNEXP, A.PACNOM, B.INMCANT, B.INMADM, B.INMCVE, B.INMDES, B.INMCON, B.INMDOSIS, B.INMFEC, B.INMHOR ORDER BY A.PACNEXP, B.INMCVE, B.INMFEC");

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
                a = String.valueOf(jt_consulta_vacunas.getValueAt(fila, 2));
                b = Integer.valueOf(a);
                total = total + b;
            }

            modelo.addRow(new Object[]{"", "Total", String.valueOf(total)});

            rs.close();
            tt_rec();
        }
    }
     
    public void vNoExpDInyecDesc() throws SQLException {

        String formato = "dd/MM/yyyy";
        Date date;
        date = jd_Desde.getDate();
        SimpleDateFormat sdfD = new SimpleDateFormat(formato);
        String strDateD = sdfD.format(date);

        rs = Conexiondb.Consulta("SELECT A.PACNEXP AS EXPEDIENTE, A.PACNOM AS NOMBRE, B.INMCANT AS CANTIDAD, B.INMADM AS TIPO, B.INMCVE  AS CLAVE, B.INMDES AS DESCRIPCION, B.INMCON AS CONCENTRACION, B.INMDOSIS AS DOSIS, TO_CHAR(B.INMFEC,'dd/MM/yyyy') AS FECHA, B.INMHOR AS HORA FROM PACCAT A, INMPAC B WHERE B.INMFEC = TO_DATE('" + strDateD + "', 'dd/MM/yyyy') AND B.PACNEXP='" + this.jt_expediente.getText() + "' AND A.PACNEXP=B.PACNEXP AND B.INMDES NOT LIKE '%SUBL%' AND B.INMDES LIKE '%" + this.jt_descripcion.getText() + "%' GROUP BY A.PACNEXP, A.PACNOM, B.INMCANT, B.INMADM, B.INMCVE, B.INMDES, B.INMCON, B.INMDOSIS, B.INMFEC, B.INMHOR ORDER BY A.PACNEXP, B.INMCVE, B.INMFEC");

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
                a = String.valueOf(jt_consulta_vacunas.getValueAt(fila, 2));
                b = Integer.valueOf(a);
                total = total + b;
            }

            modelo.addRow(new Object[]{"", "Total", String.valueOf(total)});

            rs.close();
            tt_rec();
        }
    }

    public void vNoExpCveDHInyec() throws SQLException {

        String formato = "dd/MM/yyyy";
        Date date;
        date = jd_Desde.getDate();
        SimpleDateFormat sdfD = new SimpleDateFormat(formato);
        String strDateD = sdfD.format(date);

        Date date2 = jd_Hasta.getDate();
        SimpleDateFormat sdfH = new SimpleDateFormat(formato);
        String strDateH = sdfH.format(date2);

        rs = Conexiondb.Consulta("SELECT A.PACNEXP AS EXPEDIENTE, A.PACNOM AS NOMBRE, B.INMCANT AS CANTIDAD, B.INMADM AS TIPO, B.INMCVE  AS CLAVE, B.INMDES AS DESCRIPCION, B.INMCON AS CONCENTRACION, B.INMDOSIS AS DOSIS, TO_CHAR(B.INMFEC,'dd/MM/yyyy') AS FECHA, B.INMHOR AS HORA FROM PACCAT A, INMPAC B WHERE B.INMFEC BETWEEN TO_DATE('" + strDateD + "', 'dd/MM/yyyy') AND TO_DATE('" + strDateH + "', 'dd/MM/yyyy') AND B.PACNEXP='" + this.jt_expediente.getText() + "'  AND B.INMCVE IN (" + this.jt_cve.getText() + ") AND A.PACNEXP=B.PACNEXP AND B.INMDES NOT LIKE '%SUBL%' GROUP BY A.PACNEXP, A.PACNOM, B.INMCANT, B.INMADM, B.INMCVE, B.INMDES, B.INMCON, B.INMDOSIS, B.INMFEC, B.INMHOR ORDER BY A.PACNEXP, B.INMCVE, B.INMFEC");

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
                a = String.valueOf(jt_consulta_vacunas.getValueAt(fila, 2));
                b = Integer.valueOf(a);
                total = total + b;
            }

            modelo.addRow(new Object[]{"", "Total", String.valueOf(total)});

            rs.close();
            tt_rec();
        }
    }

    public void vNoExpCveDInyec() throws SQLException {

        String formato = "dd/MM/yyyy";
        Date date;
        date = jd_Desde.getDate();
        SimpleDateFormat sdfD = new SimpleDateFormat(formato);
        String strDateD = sdfD.format(date);

        rs = Conexiondb.Consulta("SELECT A.PACNEXP AS EXPEDIENTE, A.PACNOM AS NOMBRE, B.INMCANT AS CANTIDAD, B.INMADM AS TIPO, B.INMCVE  AS CLAVE, B.INMDES AS DESCRIPCION, B.INMCON AS CONCENTRACION, B.INMDOSIS AS DOSIS, TO_CHAR(B.INMFEC,'dd/MM/yyyy') AS FECHA, B.INMHOR AS HORA FROM PACCAT A, INMPAC B WHERE B.INMFEC = TO_DATE('" + strDateD + "', 'dd/MM/yyyy') AND B.PACNEXP='" + this.jt_expediente.getText() + "'  AND B.INMCVE IN (" + this.jt_cve.getText() + ") AND A.PACNEXP=B.PACNEXP AND B.INMDES NOT LIKE '%SUBL%' GROUP BY A.PACNEXP, A.PACNOM, B.INMCANT, B.INMADM, B.INMCVE, B.INMDES, B.INMCON, B.INMDOSIS, B.INMFEC, B.INMHOR ORDER BY A.PACNEXP, B.INMCVE, B.INMFEC");

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
                a = String.valueOf(jt_consulta_vacunas.getValueAt(fila, 2));
                b = Integer.valueOf(a);
                total = total + b;
            }

            modelo.addRow(new Object[]{"", "Total", String.valueOf(total)});

            rs.close();
            tt_rec();
        }
    }

    public void vNoExpCveDHInyecDesc() throws SQLException {

        String formato = "dd/MM/yyyy";
        Date date;
        date = jd_Desde.getDate();
        SimpleDateFormat sdfD = new SimpleDateFormat(formato);
        String strDateD = sdfD.format(date);

        Date date2 = jd_Hasta.getDate();
        SimpleDateFormat sdfH = new SimpleDateFormat(formato);
        String strDateH = sdfH.format(date2);

        rs = Conexiondb.Consulta("SELECT A.PACNEXP AS EXPEDIENTE, A.PACNOM AS NOMBRE, B.INMCANT AS CANTIDAD, B.INMADM AS TIPO, B.INMCVE  AS CLAVE, B.INMDES AS DESCRIPCION, B.INMCON AS CONCENTRACION, B.INMDOSIS AS DOSIS, TO_CHAR(B.INMFEC,'dd/MM/yyyy') AS FECHA, B.INMHOR AS HORA FROM PACCAT A, INMPAC B WHERE B.INMFEC BETWEEN TO_DATE('" + strDateD + "', 'dd/MM/yyyy') AND TO_DATE('" + strDateH + "', 'dd/MM/yyyy') AND B.PACNEXP='" + this.jt_expediente.getText() + "'  AND B.INMCVE IN (" + this.jt_cve.getText() + ") AND A.PACNEXP=B.PACNEXP AND B.INMDES NOT LIKE '%SUBL%' AND B.INMDES LIKE '%" + this.jt_descripcion.getText() + "%' GROUP BY A.PACNEXP, A.PACNOM, B.INMCANT, B.INMADM, B.INMCVE, B.INMDES, B.INMCON, B.INMDOSIS, B.INMFEC, B.INMHOR ORDER BY A.PACNEXP, B.INMCVE, B.INMFEC");

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
                a = String.valueOf(jt_consulta_vacunas.getValueAt(fila, 2));
                b = Integer.valueOf(a);
                total = total + b;
            }

            modelo.addRow(new Object[]{"", "Total", String.valueOf(total)});

            rs.close();
            tt_rec();
        }
    }

    public void vNoExpCveDInyecDesc() throws SQLException {

        String formato = "dd/MM/yyyy";
        Date date;
        date = jd_Desde.getDate();
        SimpleDateFormat sdfD = new SimpleDateFormat(formato);
        String strDateD = sdfD.format(date);

        rs = Conexiondb.Consulta("SELECT A.PACNEXP AS EXPEDIENTE, A.PACNOM AS NOMBRE, B.INMCANT AS CANTIDAD, B.INMADM AS TIPO, B.INMCVE  AS CLAVE, B.INMDES AS DESCRIPCION, B.INMCON AS CONCENTRACION, B.INMDOSIS AS DOSIS, TO_CHAR(B.INMFEC,'dd/MM/yyyy') AS FECHA, B.INMHOR AS HORA FROM PACCAT A, INMPAC B WHERE B.INMFEC = TO_DATE('" + strDateD + "', 'dd/MM/yyyy') AND B.PACNEXP='" + this.jt_expediente.getText() + "'  AND B.INMCVE IN (" + this.jt_cve.getText() + ") AND A.PACNEXP=B.PACNEXP AND B.INMDES NOT LIKE '%SUBL%' AND B.INMDES LIKE '%\" + this.jt_descripcion.getText() + \"%' GROUP BY A.PACNEXP, A.PACNOM, B.INMCANT, B.INMADM, B.INMCVE, B.INMDES, B.INMCON, B.INMDOSIS, B.INMFEC, B.INMHOR ORDER BY A.PACNEXP, B.INMCVE, B.INMFEC");

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
                a = String.valueOf(jt_consulta_vacunas.getValueAt(fila, 2));
                b = Integer.valueOf(a);
                total = total + b;
            }

            modelo.addRow(new Object[]{"", "Total", String.valueOf(total)});

            rs.close();
            tt_rec();
        }
    }
    
    public void vNoExpInyec() throws SQLException {

        rs = Conexiondb.Consulta("SELECT A.PACNEXP AS EXPEDIENTE, A.PACNOM AS NOMBRE, B.INMCANT AS CANTIDAD, B.INMADM AS TIPO, B.INMCVE  AS CLAVE, B.INMDES AS DESCRIPCION, B.INMCON AS CONCENTRACION, B.INMDOSIS AS DOSIS, TO_CHAR(B.INMFEC,'dd/MM/yyyy') AS FECHA, B.INMHOR AS HORA FROM PACCAT A, INMPAC B WHERE B.PACNEXP='" + this.jt_expediente.getText() + "' AND A.PACNEXP=B.PACNEXP AND B.INMDES NOT LIKE '%SUBL%' GROUP BY A.PACNEXP, A.PACNOM, B.INMCANT, B.INMADM, B.INMCVE, B.INMDES, B.INMCON, B.INMDOSIS, B.INMFEC, B.INMHOR ORDER BY A.PACNEXP, B.INMCVE, B.INMFEC");

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
                a = String.valueOf(jt_consulta_vacunas.getValueAt(fila, 2));
                b = Integer.valueOf(a);
                total = total + b;
            }

            modelo.addRow(new Object[]{"", "Total", String.valueOf(total)});

            rs.close();
            tt_rec();
        }
    }

    public void vNoExpCveInyec() throws SQLException {

        rs = Conexiondb.Consulta("SELECT A.PACNEXP AS EXPEDIENTE, A.PACNOM AS NOMBRE, B.INMCANT AS CANTIDAD, B.INMADM AS TIPO, B.INMCVE  AS CLAVE, B.INMDES AS DESCRIPCION, B.INMCON AS CONCENTRACION, B.INMDOSIS AS DOSIS, TO_CHAR(B.INMFEC,'dd/MM/yyyy') AS FECHA, B.INMHOR AS HORA FROM PACCAT A, INMPAC B WHERE B.PACNEXP='" + this.jt_expediente.getText() + "' AND B.INMCVE IN (" + this.jt_cve.getText() + ") AND A.PACNEXP=B.PACNEXP AND B.INMDES NOT LIKE '%SUBL%' GROUP BY A.PACNEXP, A.PACNOM, B.INMCANT, B.INMADM, B.INMCVE, B.INMDES, B.INMCON, B.INMDOSIS, B.INMFEC, B.INMHOR ORDER BY A.PACNEXP, B.INMCVE, B.INMFEC");

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
                a = String.valueOf(jt_consulta_vacunas.getValueAt(fila, 2));
                b = Integer.valueOf(a);
                total = total + b;
            }

            modelo.addRow(new Object[]{"", "Total", String.valueOf(total)});

            rs.close();
            tt_rec();
        }
    }
    
    public void vNoExpCveInyecDesc() throws SQLException {

        rs = Conexiondb.Consulta("SELECT A.PACNEXP AS EXPEDIENTE, A.PACNOM AS NOMBRE, B.INMCANT AS CANTIDAD, B.INMADM AS TIPO, B.INMCVE  AS CLAVE, B.INMDES AS DESCRIPCION, B.INMCON AS CONCENTRACION, B.INMDOSIS AS DOSIS, TO_CHAR(B.INMFEC,'dd/MM/yyyy') AS FECHA, B.INMHOR AS HORA FROM PACCAT A, INMPAC B WHERE B.PACNEXP='" + this.jt_expediente.getText() + "' AND A.PACNEXP=B.PACNEXP AND B.INMCVE IN (" + this.jt_cve.getText() + ") AND B.INMDES NOT LIKE '%SUBL%' AND B.INMDES LIKE '%" + this.jt_descripcion.getText() + "%' GROUP BY A.PACNEXP, A.PACNOM, B.INMCANT, B.INMADM, B.INMCVE, B.INMDES, B.INMCON, B.INMDOSIS, B.INMFEC, B.INMHOR ORDER BY A.PACNEXP, B.INMCVE, B.INMFEC");

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
                a = String.valueOf(jt_consulta_vacunas.getValueAt(fila, 2));
                b = Integer.valueOf(a);
                total = total + b;
            }

            modelo.addRow(new Object[]{"", "Total", String.valueOf(total)});

            rs.close();
            tt_rec();
        }
    }
    
    public void vNoExpInyecDesc() throws SQLException {

        rs = Conexiondb.Consulta("SELECT A.PACNEXP AS EXPEDIENTE, A.PACNOM AS NOMBRE, B.INMCANT AS CANTIDAD, B.INMADM AS TIPO, B.INMCVE  AS CLAVE, B.INMDES AS DESCRIPCION, B.INMCON AS CONCENTRACION, B.INMDOSIS AS DOSIS, TO_CHAR(B.INMFEC,'dd/MM/yyyy') AS FECHA, B.INMHOR AS HORA FROM PACCAT A, INMPAC B WHERE B.PACNEXP='" + this.jt_expediente.getText() + "' AND A.PACNEXP=B.PACNEXP AND B.INMDES NOT LIKE '%SUBL%' AND B.INMDES LIKE '%" + this.jt_descripcion.getText() + "%' GROUP BY A.PACNEXP, A.PACNOM, B.INMCANT, B.INMADM, B.INMCVE, B.INMDES, B.INMCON, B.INMDOSIS, B.INMFEC, B.INMHOR ORDER BY A.PACNEXP, B.INMCVE, B.INMFEC");

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
                a = String.valueOf(jt_consulta_vacunas.getValueAt(fila, 2));
                b = Integer.valueOf(a);
                total = total + b;
            }

            modelo.addRow(new Object[]{"", "Total", String.valueOf(total)});

            rs.close();
            tt_rec();
        }
    }
    
    public void vCveInyecDesc() throws SQLException {

        rs = Conexiondb.Consulta("SELECT A.PACNEXP AS EXPEDIENTE, A.PACNOM AS NOMBRE, B.INMCANT AS CANTIDAD, B.INMADM AS TIPO, B.INMCVE  AS CLAVE, B.INMDES AS DESCRIPCION, B.INMCON AS CONCENTRACION, B.INMDOSIS AS DOSIS, TO_CHAR(B.INMFEC,'dd/MM/yyyy') AS FECHA, B.INMHOR AS HORA FROM PACCAT A, INMPAC B WHERE A.PACNEXP=B.PACNEXP AND B.INMCVE IN (" + this.jt_cve.getText() + ") AND B.INMDES NOT LIKE '%SUBL%' AND B.INMDES LIKE '%" + this.jt_descripcion.getText() + "%' GROUP BY A.PACNEXP, A.PACNOM, B.INMCANT, B.INMADM, B.INMCVE, B.INMDES, B.INMCON, B.INMDOSIS, B.INMFEC, B.INMHOR ORDER BY A.PACNEXP, B.INMCVE, B.INMFEC");

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
                a = String.valueOf(jt_consulta_vacunas.getValueAt(fila, 2));
                b = Integer.valueOf(a);
                total = total + b;
            }

            modelo.addRow(new Object[]{"", "Total", String.valueOf(total)});

            rs.close();
            tt_rec();
        }
    }
    
    public void vCveInyec() throws SQLException {

        rs = Conexiondb.Consulta("SELECT A.PACNEXP AS EXPEDIENTE, A.PACNOM AS NOMBRE, B.INMCANT AS CANTIDAD, B.INMADM AS TIPO, B.INMCVE  AS CLAVE, B.INMDES AS DESCRIPCION, B.INMCON AS CONCENTRACION, B.INMDOSIS AS DOSIS, TO_CHAR(B.INMFEC,'dd/MM/yyyy') AS FECHA, B.INMHOR AS HORA FROM PACCAT A, INMPAC B WHERE A.PACNEXP=B.PACNEXP AND B.INMCVE IN (" + this.jt_cve.getText() + ") AND B.INMDES NOT LIKE '%SUBL%' GROUP BY A.PACNEXP, A.PACNOM, B.INMCANT, B.INMADM, B.INMCVE, B.INMDES, B.INMCON, B.INMDOSIS, B.INMFEC, B.INMHOR ORDER BY A.PACNEXP, B.INMCVE, B.INMFEC");

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
                a = String.valueOf(jt_consulta_vacunas.getValueAt(fila, 2));
                b = Integer.valueOf(a);
                total = total + b;
            }

            modelo.addRow(new Object[]{"", "Total", String.valueOf(total)});

            rs.close();
            tt_rec();
        }
    }
    
    public void vDHInyecDesc() throws SQLException {

        String formato = "dd/MM/yyyy";
        Date date;
        date = jd_Desde.getDate();
        SimpleDateFormat sdfD = new SimpleDateFormat(formato);
        String strDateD = sdfD.format(date);

        Date date2 = jd_Hasta.getDate();
        SimpleDateFormat sdfH = new SimpleDateFormat(formato);
        String strDateH = sdfH.format(date2);

        rs = Conexiondb.Consulta("SELECT A.PACNEXP AS EXPEDIENTE, A.PACNOM AS NOMBRE, B.INMCANT AS CANTIDAD, B.INMADM AS TIPO, B.INMCVE  AS CLAVE, B.INMDES AS DESCRIPCION, B.INMCON AS CONCENTRACION, B.INMDOSIS AS DOSIS, TO_CHAR(B.INMFEC,'dd/MM/yyyy') AS FECHA, B.INMHOR AS HORA FROM PACCAT A, INMPAC B WHERE B.INMFEC BETWEEN TO_DATE('" + strDateD + "', 'dd/MM/yyyy') AND TO_DATE('" + strDateH + "', 'dd/MM/yyyy') AND A.PACNEXP=B.PACNEXP AND B.INMDES NOT LIKE '%SUBL%' AND B.INMDES LIKE '%" + this.jt_descripcion.getText() + "%' GROUP BY A.PACNEXP, A.PACNOM, B.INMCANT, B.INMADM, B.INMCVE, B.INMDES, B.INMCON, B.INMDOSIS, B.INMFEC, B.INMHOR ORDER BY A.PACNEXP, B.INMCVE, B.INMFEC");

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
                a = String.valueOf(jt_consulta_vacunas.getValueAt(fila, 2));
                b = Integer.valueOf(a);
                total = total + b;
            }

            modelo.addRow(new Object[]{"", "Total", String.valueOf(total)});

            rs.close();
            tt_rec();
        }
    }

    public void vDInyecDesc() throws SQLException {

        String formato = "dd/MM/yyyy";
        Date date;
        date = jd_Desde.getDate();
        SimpleDateFormat sdfD = new SimpleDateFormat(formato);
        String strDateD = sdfD.format(date);

        rs = Conexiondb.Consulta("SELECT A.PACNEXP AS EXPEDIENTE, A.PACNOM AS NOMBRE, B.INMCANT AS CANTIDAD, B.INMADM AS TIPO, B.INMCVE  AS CLAVE, B.INMDES AS DESCRIPCION, B.INMCON AS CONCENTRACION, B.INMDOSIS AS DOSIS, TO_CHAR(B.INMFEC,'dd/MM/yyyy') AS FECHA, B.INMHOR AS HORA FROM PACCAT A, INMPAC B WHERE B.INMFEC = TO_DATE('" + strDateD + "', 'dd/MM/yyyy') AND A.PACNEXP=B.PACNEXP AND B.INMDES NOT LIKE '%SUBL%' AND B.INMDES LIKE '%" + this.jt_descripcion.getText() + "%' GROUP BY A.PACNEXP, A.PACNOM, B.INMCANT, B.INMADM, B.INMCVE, B.INMDES, B.INMCON, B.INMDOSIS, B.INMFEC, B.INMHOR ORDER BY A.PACNEXP, B.INMCVE, B.INMFEC");

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
                a = String.valueOf(jt_consulta_vacunas.getValueAt(fila, 2));
                b = Integer.valueOf(a);
                total = total + b;
            }

            modelo.addRow(new Object[]{"", "Total", String.valueOf(total)});

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
        jd_Desde = new com.toedter.calendar.JDateChooser();
        jl_desde = new javax.swing.JLabel();
        jd_Hasta = new com.toedter.calendar.JDateChooser();
        jl_hasta = new javax.swing.JLabel();
        jl_descripcion = new javax.swing.JLabel();
        jt_descripcion = new javax.swing.JTextField();
        btn_limpiar = new javax.swing.JButton();
        btn_reporte = new javax.swing.JButton();
        jl_expediente = new javax.swing.JLabel();
        jt_expediente = new javax.swing.JTextField();
        jl_cve = new javax.swing.JLabel();
        jt_cve = new javax.swing.JTextField();
        jr_subl = new javax.swing.JRadioButton();
        jr_inyec = new javax.swing.JRadioButton();
        jl_etiqueta = new javax.swing.JLabel();
        jp_bar = new javax.swing.JProgressBar();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Reporte De Vacunas Por Expediente");

        jl_titulo.setBackground(new java.awt.Color(51, 102, 255));
        jl_titulo.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jl_titulo.setForeground(new java.awt.Color(255, 255, 255));
        jl_titulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jl_titulo.setText("REPORTE DE VACUNAS POR EXPEDIENTE");
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

        jl_descripcion.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jl_descripcion.setText("Descripcion:");

        jt_descripcion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jt_descripcionKeyPressed(evt);
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
        jl_cve.setText("Clave");

        jt_cve.setPreferredSize(new java.awt.Dimension(6, 30));
        jt_cve.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jt_cveKeyPressed(evt);
            }
        });

        gruposiopt.add(jr_subl);
        jr_subl.setText("Sublinguales");
        jr_subl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jr_sublActionPerformed(evt);
            }
        });

        gruposiopt.add(jr_inyec);
        jr_inyec.setText("Inyectadas");
        jr_inyec.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jr_inyecActionPerformed(evt);
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
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jl_descripcion)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jt_descripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jl_desde, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jd_Desde, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(33, 33, 33)
                                .addComponent(jl_hasta, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jd_Hasta, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(457, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addComponent(jl_cve)
                                        .addGap(18, 18, 18)
                                        .addComponent(jt_cve, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jl_expediente)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jt_expediente, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(73, 73, 73)
                                .addComponent(jr_subl)
                                .addGap(58, 58, 58)
                                .addComponent(jr_inyec))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnConsulta, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(btn_imprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btn_exportar)
                                .addGap(14, 14, 14)
                                .addComponent(btn_reporte)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btn_limpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btn_menu_principal)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btn_salir, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(24, 24, 24))
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jl_tt_reg, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane1)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jl_etiqueta)
                                .addGap(18, 18, 18)
                                .addComponent(jp_bar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(16, 16, 16))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jl_titulo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jl_expediente, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jt_expediente, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(19, 19, 19)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jl_cve)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jt_cve, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(1, 1, 1)))
                        .addGap(19, 19, 19))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(65, 65, 65)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jr_subl)
                                .addComponent(jr_inyec))
                            .addComponent(btn_salir, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jl_descripcion)
                    .addComponent(jt_descripcion, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jl_desde, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jd_Desde, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jd_Hasta, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jl_hasta, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 252, Short.MAX_VALUE)
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
            MessageFormat headerformat = new MessageFormat("Reporte Vac por Exped"); //encabezado
            MessageFormat footerformat = new MessageFormat("Page - {0}"); //
            jt_consulta_vacunas.print(JTable.PrintMode.FIT_WIDTH, headerformat, footerformat);  //imprime la JTable
        } catch (PrinterException ex) {
        Logger.getLogger(jfVExpediente.class.getName()).log(Level.SEVERE, null, ex);
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
           nom.add("Reporte Vac por Exped");
           String file = chooser.getSelectedFile().toString().concat(".xls");
           //Aqui se ejecuta el metodo
           try{
               Exporter e = new Exporter(new File(file),tb,nom);
               if(e.export())
               {
                   JOptionPane.showMessageDialog(null, "Total de Registros exportados a Excel: " + totalR, "Reporte Vac por Exped", JOptionPane.INFORMATION_MESSAGE);
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
        jt_descripcion.setText("");
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
                nom.add("VacunasExpedientes");
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
        if(jt_expediente.getText().isEmpty() && jt_cve.getText().isEmpty() && jd_Desde.getDate() != null && jd_Hasta.getDate() != null && jr_subl.isSelected()){
               task01Inf = new PBvExpDHSubl();
               task01Inf.execute();
               
        }else if(jt_expediente.getText().isEmpty() && jt_cve.getText().isEmpty() && jd_Desde.getDate() != null && jd_Hasta.getDate() == null  && jr_subl.isSelected())
        {
               task02Inf = new PBvExpDSubl();
               task02Inf.execute();
               
        }else if(jt_expediente.getText().isEmpty() && jt_cve.getText().length() > 0 && jd_Desde.getDate() != null && jd_Hasta.getDate() != null && jr_subl.isSelected())
        {
               task03Inf = new PBvExpCveDHSubl();
               task03Inf.execute();
               
        }else if(jt_expediente.getText().isEmpty() && jt_cve.getText().length() > 0 && jd_Desde.getDate() != null && jd_Hasta.getDate() == null && jr_subl.isSelected())
        {
               task04Inf = new PBvExpCveDSubl();
               task04Inf.execute();
               
        }else if(jt_expediente.getText().length() > 0 && jt_cve.getText().isEmpty() && jd_Desde.getDate() != null && jd_Hasta.getDate() == null && jr_subl.isSelected())
        {
               task08Inf = new PBvNoExpDSubl();
               task08Inf.execute();
               
        }else if(jt_expediente.getText().length() > 0 && jt_cve.getText().isEmpty() && jd_Desde.getDate() != null && jd_Hasta.getDate() != null && jr_subl.isSelected())
        {
            
               task07Inf = new PBvNoExpDHSubl();
               task07Inf.execute();
               
        }else if(jt_expediente.getText().length() > 0 && jt_cve.getText().length() > 0 && jd_Desde.getDate() != null && jd_Hasta.getDate() != null && jr_subl.isSelected())
        {
               task09Inf = new PBvNoExpCveDHSubl();
               task09Inf.execute();
               
        }else if(jt_expediente.getText().length() > 0 && jt_cve.getText().length() > 0 && jd_Desde.getDate() != null && jd_Hasta.getDate() == null && jr_subl.isSelected())
        {
            
               task10Inf = new PBvNoExpCveDSubl();
               task10Inf.execute();
               
        }else if(jt_expediente.getText().length() > 0 && jt_cve.getText().isEmpty() && jd_Desde.getDate() == null && jd_Hasta.getDate() == null && jr_subl.isSelected())
        {
            
               task05Inf = new PBvNoExpSubl();
               task05Inf.execute();
               
        }else if(jt_expediente.getText().length() > 0 && jt_cve.getText().length() > 0 && jd_Desde.getDate() == null && jd_Hasta.getDate() == null && jr_subl.isSelected())
        {

               task06Inf = new PBvNoExpCveSubl();
               task06Inf.execute();
               
        }else if(jt_expediente.getText().isEmpty() && jt_cve.getText().isEmpty() && jd_Desde.getDate() == null && jd_Hasta.getDate() == null && jr_subl.isSelected())
        {
            JOptionPane.showMessageDialog(null, "Ingresa un Expediente, una Clave \n o una Fecha Desde - Hasta");
        }else if(jt_expediente.getText().isEmpty() && jt_cve.getText().isEmpty() && jd_Desde.getDate() == null && jd_Hasta.getDate() != null && jr_subl.isSelected())
        {
            JOptionPane.showMessageDialog(null, "Ingresa Desde que fecha es la consulta");
        }else if(jt_expediente.getText().isEmpty() && jt_cve.getText().length() > 0 && jd_Desde.getDate() == null && jd_Hasta.getDate() != null && jr_subl.isSelected())
        {
            JOptionPane.showMessageDialog(null, "Ingresa Desde que fecha es la consulta");
        }else if(jt_expediente.getText().length() > 0 && jt_cve.getText().isEmpty() && jd_Desde.getDate() == null && jd_Hasta.getDate() != null && jr_subl.isSelected())
        {
            JOptionPane.showMessageDialog(null, "Ingresa Desde que fecha es la consulta");
        }else if(jt_expediente.getText().length() > 0 && jt_cve.getText().length() > 0 && jd_Desde.getDate() == null && jd_Hasta.getDate() != null && jr_subl.isSelected())
        {
            JOptionPane.showMessageDialog(null, "Ingresa Desde que fecha es la consulta");
        }else if(jt_expediente.getText().isEmpty() && jt_cve.getText().length() > 0 && jd_Desde.getDate() == null && jd_Hasta.getDate() == null && jr_subl.isSelected())
        {
            JOptionPane.showMessageDialog(null, "Ingresa Un numero de Expediente o una fecha para la consulta");
        }else if(jt_expediente.getText().isEmpty() && jt_cve.getText().isEmpty() && jt_descripcion.getText().isEmpty() && jd_Desde.getDate() == null && jd_Hasta.getDate() == null  && jr_inyec.isSelected()){
            JOptionPane.showMessageDialog(null, "Ingresa un Expediente, una Clave, una Fecha Desde - Hasta o una Descripcion");
        }else if(jt_expediente.getText().isEmpty() && jt_cve.getText().isEmpty() && jt_descripcion.getText().isEmpty() && jd_Desde.getDate() != null && jd_Hasta.getDate() != null  && jr_inyec.isSelected()){
            
               task11Inf = new PBvExpDHInyec();
               task11Inf.execute();
               
        }else if(jt_expediente.getText().isEmpty() && jt_cve.getText().isEmpty() && jt_descripcion.getText().isEmpty() && jd_Desde.getDate() != null && jd_Hasta.getDate() == null  && jr_inyec.isSelected())
        {
               task12Inf = new PBvExpDInyec();
               task12Inf.execute();
               
        }else if(jt_expediente.getText().isEmpty() && jt_cve.getText().length() > 0 && jt_descripcion.getText().isEmpty() && jd_Desde.getDate() != null && jd_Hasta.getDate() != null  && jr_inyec.isSelected()){
            
               task13Inf = new PBvExpCveDHInyec();
               task13Inf.execute();
                              
        }else if(jt_expediente.getText().isEmpty() && jt_cve.getText().length() > 0 && jt_descripcion.getText().isEmpty() && jd_Desde.getDate() != null && jd_Hasta.getDate() == null  && jr_inyec.isSelected()){
            
               task14Inf = new PBvExpCveDInyec();
               task14Inf.execute();
               
        }else if(jt_expediente.getText().isEmpty() && jt_cve.getText().length() > 0 && jt_descripcion.getText().length() > 0 && jd_Desde.getDate() != null && jd_Hasta.getDate() != null  && jr_inyec.isSelected())
        {
               task15Inf = new PBvExpCveDHInyecDesc();
               task15Inf.execute();
               
        }else if(jt_expediente.getText().isEmpty() && jt_cve.getText().length() > 0 && jt_descripcion.getText().length() > 0 && jd_Desde.getDate() != null && jd_Hasta.getDate() == null  && jr_inyec.isSelected())
        {
               task16Inf = new PBvExpCveDInyecDesc();
               task16Inf.execute();
               
        }else if(jt_expediente.getText().length() > 0 && jt_cve.getText().isEmpty() && jt_descripcion.getText().isEmpty() && jd_Desde.getDate() != null && jd_Hasta.getDate() != null  && jr_inyec.isSelected())
        {
           
               task17Inf = new PBvNoExpDHInyec();
               task17Inf.execute();
               
        }else if(jt_expediente.getText().length() > 0 && jt_cve.getText().isEmpty() && jt_descripcion.getText().isEmpty() && jd_Desde.getDate() != null && jd_Hasta.getDate() == null  && jr_inyec.isSelected())
        {
               task18Inf = new PBvNoExpDInyec();
               task18Inf.execute();
               
        }else if(jt_expediente.getText().length() > 0 && jt_cve.getText().isEmpty() && jt_descripcion.getText().length() > 0 && jd_Desde.getDate() != null && jd_Hasta.getDate() == null  && jr_inyec.isSelected())
        {
               task20Inf = new PBvNoExpDInyecDesc();
               task20Inf.execute();
               
        }else if(jt_expediente.getText().length() > 0 && jt_cve.getText().isEmpty() && jt_descripcion.getText().length() > 0 && jd_Desde.getDate() != null && jd_Hasta.getDate() != null  && jr_inyec.isSelected())
        {
                task19Inf = new PBvNoExpDHInyecDesc();
                task19Inf.execute();
                
        }else if(jt_expediente.getText().length() > 0 && jt_cve.getText().length() > 0 && jt_descripcion.getText().isEmpty() && jd_Desde.getDate() != null && jd_Hasta.getDate() != null  && jr_inyec.isSelected())
        {
               task21Inf = new PBvNoExpCveDHInyec();
               task21Inf.execute();
               
        }else if(jt_expediente.getText().length() > 0 && jt_cve.getText().length() > 0 && jt_descripcion.getText().isEmpty() && jd_Desde.getDate() != null && jd_Hasta.getDate() == null  && jr_inyec.isSelected())
        {
               task22Inf = new PBvNoExpCveDInyec();
               task22Inf.execute();
               
        }else if(jt_expediente.getText().length() > 0 && jt_cve.getText().length() > 0 && jt_descripcion.getText().length() > 0 && jd_Desde.getDate() != null && jd_Hasta.getDate() != null  && jr_inyec.isSelected())
        {
               task23Inf = new PBvNoExpCveDHInyecDesc();
               task23Inf.execute();
             
        }else if(jt_expediente.getText().length() > 0 && jt_cve.getText().length() > 0 && jt_descripcion.getText().length() > 0 && jd_Desde.getDate() != null && jd_Hasta.getDate() == null  && jr_inyec.isSelected())
        {
               task24Inf = new PBvNoExpCveDInyecDesc();
               task24Inf.execute();
                              
        }else if(jt_expediente.getText().length() > 0 && jt_cve.getText().isEmpty() && jt_descripcion.getText().isEmpty() && jd_Desde.getDate() == null && jd_Hasta.getDate() == null  && jr_inyec.isSelected())
        {
               task25Inf = new PBvNoExpInyec();
               task25Inf.execute();
               
        }else if(jt_expediente.getText().length() > 0 && jt_cve.getText().length() > 0 && jt_descripcion.getText().isEmpty() && jd_Desde.getDate() == null && jd_Hasta.getDate() == null  && jr_inyec.isSelected())
        {
               task26Inf = new PBvNoExpCveInyec();
               task26Inf.execute();
               
        }else if(jt_expediente.getText().length() > 0 && jt_cve.getText().length() > 0 && jt_descripcion.getText().length() > 0 && jd_Desde.getDate() == null && jd_Hasta.getDate() == null  && jr_inyec.isSelected())
        {
               task27Inf = new PBvNoExpCveInyecDesc();
               task27Inf.execute();
               
        }else if(jt_expediente.getText().length() > 0 && jt_cve.getText().isEmpty() && jt_descripcion.getText().length() > 0 && jd_Desde.getDate() == null && jd_Hasta.getDate() == null  && jr_inyec.isSelected())
        {
               task28Inf = new PBvNoExpInyecDesc();
               task28Inf.execute();
               
        }else if(jt_expediente.getText().isEmpty() && jt_cve.getText().length() > 0 && jt_descripcion.getText().length() > 0 && jd_Desde.getDate() == null && jd_Hasta.getDate() == null  && jr_inyec.isSelected())
        {
               task29Inf = new PBvCveInyecDesc();
               task29Inf.execute();
               
        }else if(jt_expediente.getText().isEmpty() && jt_cve.getText().length() > 0 && jt_descripcion.getText().isEmpty() && jd_Desde.getDate() == null && jd_Hasta.getDate() == null  && jr_inyec.isSelected())
        {
               task30Inf = new PBvCveInyec();
               task30Inf.execute();
               
        }else if(jt_expediente.getText().isEmpty() && jt_cve.getText().isEmpty() && jt_descripcion.getText().length() > 0 && jd_Desde.getDate() != null && jd_Hasta.getDate() != null  && jr_inyec.isSelected())
        {
               task31Inf = new PBvDHInyecDesc();
               task31Inf.execute();
               
        }else if(jt_expediente.getText().isEmpty() && jt_cve.getText().isEmpty() && jt_descripcion.getText().length() > 0 && jd_Desde.getDate() != null && jd_Hasta.getDate()== null  && jr_inyec.isSelected())
        {
               task32Inf = new PBvDInyecDesc();
               task32Inf.execute();
        }else if(jt_expediente.getText().isEmpty() && jt_cve.getText().isEmpty() && jt_descripcion.getText().isEmpty() && jd_Desde.getDate() == null && jd_Hasta.getDate() != null  && jr_inyec.isSelected()){
            JOptionPane.showMessageDialog(null, "Ingresa Desde que fecha es la consulta");
        }else if(jt_expediente.getText().isEmpty() && jt_cve.getText().length() > 0 && jt_descripcion.getText().isEmpty() && jd_Desde.getDate() == null && jd_Hasta.getDate() != null  && jr_inyec.isSelected()){
            JOptionPane.showMessageDialog(null, "Ingresa Desde que fecha es la consulta");
        }else if(jt_expediente.getText().isEmpty() && jt_cve.getText().length() > 0 && jt_descripcion.getText().length() > 0 && jd_Desde.getDate() == null && jd_Hasta.getDate() != null  && jr_inyec.isSelected()){
            JOptionPane.showMessageDialog(null, "Ingresa Desde que fecha es la consulta");
        }else if(jt_expediente.getText().length() > 0 && jt_cve.getText().isEmpty() && jt_descripcion.getText().isEmpty() && jd_Desde.getDate() == null && jd_Hasta.getDate() != null  && jr_inyec.isSelected()){
            JOptionPane.showMessageDialog(null, "Ingresa Desde que fecha es la consulta");
        }else if(jt_expediente.getText().length() > 0 && jt_cve.getText().isEmpty() && jt_descripcion.getText().length() > 0 && jd_Desde.getDate() == null && jd_Hasta.getDate() != null  && jr_inyec.isSelected()){
            JOptionPane.showMessageDialog(null, "Ingresa Desde que fecha es la consulta");
        }else if(jt_expediente.getText().length() > 0 && jt_cve.getText().length() > 0 && jt_descripcion.getText().isEmpty() && jd_Desde.getDate() == null && jd_Hasta.getDate() != null  && jr_inyec.isSelected()){
            JOptionPane.showMessageDialog(null, "Ingresa Desde que fecha es la consulta");
        }else if(jt_expediente.getText().length() > 0 && jt_cve.getText().length() > 0 && jt_descripcion.getText().length() > 0 && jd_Desde.getDate() == null && jd_Hasta.getDate() != null  && jr_inyec.isSelected()){
            JOptionPane.showMessageDialog(null, "Ingresa Desde que fecha es la consulta");
        }else if(jt_expediente.getText().isEmpty() && jt_cve.getText().isEmpty() && jt_descripcion.getText().length() > 0 && jd_Desde.getDate() == null && jd_Hasta.getDate() != null  && jr_inyec.isSelected()){
            JOptionPane.showMessageDialog(null, "Ingresa Desde que fecha es la consulta");
        }else if(jt_expediente.getText().isEmpty() && jt_cve.getText().isEmpty() && jt_descripcion.getText().length() > 0 && jd_Desde.getDate() == null && jd_Hasta.getDate() == null  && jr_inyec.isSelected()){
            JOptionPane.showMessageDialog(null, "Ingresa Un Expediente, la clave o Desde que fecha es la consulta");
        }
    }//GEN-LAST:event_btnConsultaActionPerformed

    private void jr_sublActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jr_sublActionPerformed
            if(jr_subl.isSelected()){
                jt_descripcion.setVisible(false);
                jl_descripcion.setVisible(false);
            }
    }//GEN-LAST:event_jr_sublActionPerformed

    private void jr_inyecActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jr_inyecActionPerformed
        if(jr_inyec.isSelected()){
            jt_descripcion.setVisible(true);
            jl_descripcion.setVisible(true);
        }
    }//GEN-LAST:event_jr_inyecActionPerformed

    private void jt_expedienteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jt_expedienteKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (jt_expediente.getText().isEmpty() && jt_cve.getText().isEmpty() && jd_Desde.getDate() != null && jd_Hasta.getDate() != null && jr_subl.isSelected()) {
                task01Inf = new PBvExpDHSubl();
                task01Inf.execute();

            } else if (jt_expediente.getText().isEmpty() && jt_cve.getText().isEmpty() && jd_Desde.getDate() != null && jd_Hasta.getDate() == null && jr_subl.isSelected()) {
                task02Inf = new PBvExpDSubl();
                task02Inf.execute();

            } else if (jt_expediente.getText().isEmpty() && jt_cve.getText().length() > 0 && jd_Desde.getDate() != null && jd_Hasta.getDate() != null && jr_subl.isSelected()) {
                task03Inf = new PBvExpCveDHSubl();
                task03Inf.execute();

            } else if (jt_expediente.getText().isEmpty() && jt_cve.getText().length() > 0 && jd_Desde.getDate() != null && jd_Hasta.getDate() == null && jr_subl.isSelected()) {
                task04Inf = new PBvExpCveDSubl();
                task04Inf.execute();

            } else if (jt_expediente.getText().length() > 0 && jt_cve.getText().isEmpty() && jd_Desde.getDate() != null && jd_Hasta.getDate() == null && jr_subl.isSelected()) {
                task08Inf = new PBvNoExpDSubl();
                task08Inf.execute();

            } else if (jt_expediente.getText().length() > 0 && jt_cve.getText().isEmpty() && jd_Desde.getDate() != null && jd_Hasta.getDate() != null && jr_subl.isSelected()) {

                task07Inf = new PBvNoExpDHSubl();
                task07Inf.execute();

            } else if (jt_expediente.getText().length() > 0 && jt_cve.getText().length() > 0 && jd_Desde.getDate() != null && jd_Hasta.getDate() != null && jr_subl.isSelected()) {
                task09Inf = new PBvNoExpCveDHSubl();
                task09Inf.execute();

            } else if (jt_expediente.getText().length() > 0 && jt_cve.getText().length() > 0 && jd_Desde.getDate() != null && jd_Hasta.getDate() == null && jr_subl.isSelected()) {

                task10Inf = new PBvNoExpCveDSubl();
                task10Inf.execute();

            } else if (jt_expediente.getText().length() > 0 && jt_cve.getText().isEmpty() && jd_Desde.getDate() == null && jd_Hasta.getDate() == null && jr_subl.isSelected()) {

                task05Inf = new PBvNoExpSubl();
                task05Inf.execute();

            } else if (jt_expediente.getText().length() > 0 && jt_cve.getText().length() > 0 && jd_Desde.getDate() == null && jd_Hasta.getDate() == null && jr_subl.isSelected()) {

                task06Inf = new PBvNoExpCveSubl();
                task06Inf.execute();

            } else if (jt_expediente.getText().isEmpty() && jt_cve.getText().isEmpty() && jd_Desde.getDate() == null && jd_Hasta.getDate() == null && jr_subl.isSelected()) {
                JOptionPane.showMessageDialog(null, "Ingresa un Expediente, una Clave \n o una Fecha Desde - Hasta");
            } else if (jt_expediente.getText().isEmpty() && jt_cve.getText().isEmpty() && jd_Desde.getDate() == null && jd_Hasta.getDate() != null && jr_subl.isSelected()) {
                JOptionPane.showMessageDialog(null, "Ingresa Desde que fecha es la consulta");
            } else if (jt_expediente.getText().isEmpty() && jt_cve.getText().length() > 0 && jd_Desde.getDate() == null && jd_Hasta.getDate() != null && jr_subl.isSelected()) {
                JOptionPane.showMessageDialog(null, "Ingresa Desde que fecha es la consulta");
            } else if (jt_expediente.getText().length() > 0 && jt_cve.getText().isEmpty() && jd_Desde.getDate() == null && jd_Hasta.getDate() != null && jr_subl.isSelected()) {
                JOptionPane.showMessageDialog(null, "Ingresa Desde que fecha es la consulta");
            } else if (jt_expediente.getText().length() > 0 && jt_cve.getText().length() > 0 && jd_Desde.getDate() == null && jd_Hasta.getDate() != null && jr_subl.isSelected()) {
                JOptionPane.showMessageDialog(null, "Ingresa Desde que fecha es la consulta");
            } else if (jt_expediente.getText().isEmpty() && jt_cve.getText().length() > 0 && jd_Desde.getDate() == null && jd_Hasta.getDate() == null && jr_subl.isSelected()) {
                JOptionPane.showMessageDialog(null, "Ingresa Un numero de Expediente o una fecha para la consulta");
            } else if (jt_expediente.getText().isEmpty() && jt_cve.getText().isEmpty() && jt_descripcion.getText().isEmpty() && jd_Desde.getDate() == null && jd_Hasta.getDate() == null && jr_inyec.isSelected()) {
                JOptionPane.showMessageDialog(null, "Ingresa un Expediente, una Clave, una Fecha Desde - Hasta o una Descripcion");
            } else if (jt_expediente.getText().isEmpty() && jt_cve.getText().isEmpty() && jt_descripcion.getText().isEmpty() && jd_Desde.getDate() != null && jd_Hasta.getDate() != null && jr_inyec.isSelected()) {

                task11Inf = new PBvExpDHInyec();
                task11Inf.execute();

            } else if (jt_expediente.getText().isEmpty() && jt_cve.getText().isEmpty() && jt_descripcion.getText().isEmpty() && jd_Desde.getDate() != null && jd_Hasta.getDate() == null && jr_inyec.isSelected()) {
                task12Inf = new PBvExpDInyec();
                task12Inf.execute();

            } else if (jt_expediente.getText().isEmpty() && jt_cve.getText().length() > 0 && jt_descripcion.getText().isEmpty() && jd_Desde.getDate() != null && jd_Hasta.getDate() != null && jr_inyec.isSelected()) {

                task13Inf = new PBvExpCveDHInyec();
                task13Inf.execute();

            } else if (jt_expediente.getText().isEmpty() && jt_cve.getText().length() > 0 && jt_descripcion.getText().isEmpty() && jd_Desde.getDate() != null && jd_Hasta.getDate() == null && jr_inyec.isSelected()) {

                task14Inf = new PBvExpCveDInyec();
                task14Inf.execute();

            } else if (jt_expediente.getText().isEmpty() && jt_cve.getText().length() > 0 && jt_descripcion.getText().length() > 0 && jd_Desde.getDate() != null && jd_Hasta.getDate() != null && jr_inyec.isSelected()) {
                task15Inf = new PBvExpCveDHInyecDesc();
                task15Inf.execute();

            } else if (jt_expediente.getText().isEmpty() && jt_cve.getText().length() > 0 && jt_descripcion.getText().length() > 0 && jd_Desde.getDate() != null && jd_Hasta.getDate() == null && jr_inyec.isSelected()) {
                task16Inf = new PBvExpCveDInyecDesc();
                task16Inf.execute();

            } else if (jt_expediente.getText().length() > 0 && jt_cve.getText().isEmpty() && jt_descripcion.getText().isEmpty() && jd_Desde.getDate() != null && jd_Hasta.getDate() != null && jr_inyec.isSelected()) {

                task17Inf = new PBvNoExpDHInyec();
                task17Inf.execute();

            } else if (jt_expediente.getText().length() > 0 && jt_cve.getText().isEmpty() && jt_descripcion.getText().isEmpty() && jd_Desde.getDate() != null && jd_Hasta.getDate() == null && jr_inyec.isSelected()) {
                task18Inf = new PBvNoExpDInyec();
                task18Inf.execute();

            } else if (jt_expediente.getText().length() > 0 && jt_cve.getText().isEmpty() && jt_descripcion.getText().length() > 0 && jd_Desde.getDate() != null && jd_Hasta.getDate() == null && jr_inyec.isSelected()) {
                task20Inf = new PBvNoExpDInyecDesc();
                task20Inf.execute();

            } else if (jt_expediente.getText().length() > 0 && jt_cve.getText().isEmpty() && jt_descripcion.getText().length() > 0 && jd_Desde.getDate() != null && jd_Hasta.getDate() != null && jr_inyec.isSelected()) {
                task19Inf = new PBvNoExpDHInyecDesc();
                task19Inf.execute();

            } else if (jt_expediente.getText().length() > 0 && jt_cve.getText().length() > 0 && jt_descripcion.getText().isEmpty() && jd_Desde.getDate() != null && jd_Hasta.getDate() != null && jr_inyec.isSelected()) {
                task21Inf = new PBvNoExpCveDHInyec();
                task21Inf.execute();

            } else if (jt_expediente.getText().length() > 0 && jt_cve.getText().length() > 0 && jt_descripcion.getText().isEmpty() && jd_Desde.getDate() != null && jd_Hasta.getDate() == null && jr_inyec.isSelected()) {
                task22Inf = new PBvNoExpCveDInyec();
                task22Inf.execute();

            } else if (jt_expediente.getText().length() > 0 && jt_cve.getText().length() > 0 && jt_descripcion.getText().length() > 0 && jd_Desde.getDate() != null && jd_Hasta.getDate() != null && jr_inyec.isSelected()) {
                task23Inf = new PBvNoExpCveDHInyecDesc();
                task23Inf.execute();

            } else if (jt_expediente.getText().length() > 0 && jt_cve.getText().length() > 0 && jt_descripcion.getText().length() > 0 && jd_Desde.getDate() != null && jd_Hasta.getDate() == null && jr_inyec.isSelected()) {
                task24Inf = new PBvNoExpCveDInyecDesc();
                task24Inf.execute();

            } else if (jt_expediente.getText().length() > 0 && jt_cve.getText().isEmpty() && jt_descripcion.getText().isEmpty() && jd_Desde.getDate() == null && jd_Hasta.getDate() == null && jr_inyec.isSelected()) {
                task25Inf = new PBvNoExpInyec();
                task25Inf.execute();

            } else if (jt_expediente.getText().length() > 0 && jt_cve.getText().length() > 0 && jt_descripcion.getText().isEmpty() && jd_Desde.getDate() == null && jd_Hasta.getDate() == null && jr_inyec.isSelected()) {
                task26Inf = new PBvNoExpCveInyec();
                task26Inf.execute();

            } else if (jt_expediente.getText().length() > 0 && jt_cve.getText().length() > 0 && jt_descripcion.getText().length() > 0 && jd_Desde.getDate() == null && jd_Hasta.getDate() == null && jr_inyec.isSelected()) {
                task27Inf = new PBvNoExpCveInyecDesc();
                task27Inf.execute();

            } else if (jt_expediente.getText().length() > 0 && jt_cve.getText().isEmpty() && jt_descripcion.getText().length() > 0 && jd_Desde.getDate() == null && jd_Hasta.getDate() == null && jr_inyec.isSelected()) {
                task28Inf = new PBvNoExpInyecDesc();
                task28Inf.execute();

            } else if (jt_expediente.getText().isEmpty() && jt_cve.getText().length() > 0 && jt_descripcion.getText().length() > 0 && jd_Desde.getDate() == null && jd_Hasta.getDate() == null && jr_inyec.isSelected()) {
                task29Inf = new PBvCveInyecDesc();
                task29Inf.execute();

            } else if (jt_expediente.getText().isEmpty() && jt_cve.getText().length() > 0 && jt_descripcion.getText().isEmpty() && jd_Desde.getDate() == null && jd_Hasta.getDate() == null && jr_inyec.isSelected()) {
                task30Inf = new PBvCveInyec();
                task30Inf.execute();

            } else if (jt_expediente.getText().isEmpty() && jt_cve.getText().isEmpty() && jt_descripcion.getText().length() > 0 && jd_Desde.getDate() != null && jd_Hasta.getDate() != null && jr_inyec.isSelected()) {
                task31Inf = new PBvDHInyecDesc();
                task31Inf.execute();

            } else if (jt_expediente.getText().isEmpty() && jt_cve.getText().isEmpty() && jt_descripcion.getText().length() > 0 && jd_Desde.getDate() != null && jd_Hasta.getDate() == null && jr_inyec.isSelected()) {
                task32Inf = new PBvDInyecDesc();
                task32Inf.execute();
            } else if (jt_expediente.getText().isEmpty() && jt_cve.getText().isEmpty() && jt_descripcion.getText().isEmpty() && jd_Desde.getDate() == null && jd_Hasta.getDate() != null && jr_inyec.isSelected()) {
                JOptionPane.showMessageDialog(null, "Ingresa Desde que fecha es la consulta");
            } else if (jt_expediente.getText().isEmpty() && jt_cve.getText().length() > 0 && jt_descripcion.getText().isEmpty() && jd_Desde.getDate() == null && jd_Hasta.getDate() != null && jr_inyec.isSelected()) {
                JOptionPane.showMessageDialog(null, "Ingresa Desde que fecha es la consulta");
            } else if (jt_expediente.getText().isEmpty() && jt_cve.getText().length() > 0 && jt_descripcion.getText().length() > 0 && jd_Desde.getDate() == null && jd_Hasta.getDate() != null && jr_inyec.isSelected()) {
                JOptionPane.showMessageDialog(null, "Ingresa Desde que fecha es la consulta");
            } else if (jt_expediente.getText().length() > 0 && jt_cve.getText().isEmpty() && jt_descripcion.getText().isEmpty() && jd_Desde.getDate() == null && jd_Hasta.getDate() != null && jr_inyec.isSelected()) {
                JOptionPane.showMessageDialog(null, "Ingresa Desde que fecha es la consulta");
            } else if (jt_expediente.getText().length() > 0 && jt_cve.getText().isEmpty() && jt_descripcion.getText().length() > 0 && jd_Desde.getDate() == null && jd_Hasta.getDate() != null && jr_inyec.isSelected()) {
                JOptionPane.showMessageDialog(null, "Ingresa Desde que fecha es la consulta");
            } else if (jt_expediente.getText().length() > 0 && jt_cve.getText().length() > 0 && jt_descripcion.getText().isEmpty() && jd_Desde.getDate() == null && jd_Hasta.getDate() != null && jr_inyec.isSelected()) {
                JOptionPane.showMessageDialog(null, "Ingresa Desde que fecha es la consulta");
            } else if (jt_expediente.getText().length() > 0 && jt_cve.getText().length() > 0 && jt_descripcion.getText().length() > 0 && jd_Desde.getDate() == null && jd_Hasta.getDate() != null && jr_inyec.isSelected()) {
                JOptionPane.showMessageDialog(null, "Ingresa Desde que fecha es la consulta");
            } else if (jt_expediente.getText().isEmpty() && jt_cve.getText().isEmpty() && jt_descripcion.getText().length() > 0 && jd_Desde.getDate() == null && jd_Hasta.getDate() != null && jr_inyec.isSelected()) {
                JOptionPane.showMessageDialog(null, "Ingresa Desde que fecha es la consulta");
            } else if (jt_expediente.getText().isEmpty() && jt_cve.getText().isEmpty() && jt_descripcion.getText().length() > 0 && jd_Desde.getDate() == null && jd_Hasta.getDate() == null && jr_inyec.isSelected()) {
                JOptionPane.showMessageDialog(null, "Ingresa Un Expediente, la clave o Desde que fecha es la consulta");
            }
        }
    }//GEN-LAST:event_jt_expedienteKeyPressed

    private void jt_cveKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jt_cveKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (jt_expediente.getText().isEmpty() && jt_cve.getText().isEmpty() && jd_Desde.getDate() != null && jd_Hasta.getDate() != null && jr_subl.isSelected()) {
                task01Inf = new PBvExpDHSubl();
                task01Inf.execute();

            } else if (jt_expediente.getText().isEmpty() && jt_cve.getText().isEmpty() && jd_Desde.getDate() != null && jd_Hasta.getDate() == null && jr_subl.isSelected()) {
                task02Inf = new PBvExpDSubl();
                task02Inf.execute();

            } else if (jt_expediente.getText().isEmpty() && jt_cve.getText().length() > 0 && jd_Desde.getDate() != null && jd_Hasta.getDate() != null && jr_subl.isSelected()) {
                task03Inf = new PBvExpCveDHSubl();
                task03Inf.execute();

            } else if (jt_expediente.getText().isEmpty() && jt_cve.getText().length() > 0 && jd_Desde.getDate() != null && jd_Hasta.getDate() == null && jr_subl.isSelected()) {
                task04Inf = new PBvExpCveDSubl();
                task04Inf.execute();

            } else if (jt_expediente.getText().length() > 0 && jt_cve.getText().isEmpty() && jd_Desde.getDate() != null && jd_Hasta.getDate() == null && jr_subl.isSelected()) {
                task08Inf = new PBvNoExpDSubl();
                task08Inf.execute();

            } else if (jt_expediente.getText().length() > 0 && jt_cve.getText().isEmpty() && jd_Desde.getDate() != null && jd_Hasta.getDate() != null && jr_subl.isSelected()) {

                task07Inf = new PBvNoExpDHSubl();
                task07Inf.execute();

            } else if (jt_expediente.getText().length() > 0 && jt_cve.getText().length() > 0 && jd_Desde.getDate() != null && jd_Hasta.getDate() != null && jr_subl.isSelected()) {
                task09Inf = new PBvNoExpCveDHSubl();
                task09Inf.execute();

            } else if (jt_expediente.getText().length() > 0 && jt_cve.getText().length() > 0 && jd_Desde.getDate() != null && jd_Hasta.getDate() == null && jr_subl.isSelected()) {

                task10Inf = new PBvNoExpCveDSubl();
                task10Inf.execute();

            } else if (jt_expediente.getText().length() > 0 && jt_cve.getText().isEmpty() && jd_Desde.getDate() == null && jd_Hasta.getDate() == null && jr_subl.isSelected()) {

                task05Inf = new PBvNoExpSubl();
                task05Inf.execute();

            } else if (jt_expediente.getText().length() > 0 && jt_cve.getText().length() > 0 && jd_Desde.getDate() == null && jd_Hasta.getDate() == null && jr_subl.isSelected()) {

                task06Inf = new PBvNoExpCveSubl();
                task06Inf.execute();

            } else if (jt_expediente.getText().isEmpty() && jt_cve.getText().isEmpty() && jd_Desde.getDate() == null && jd_Hasta.getDate() == null && jr_subl.isSelected()) {
                JOptionPane.showMessageDialog(null, "Ingresa un Expediente, una Clave \n o una Fecha Desde - Hasta");
            } else if (jt_expediente.getText().isEmpty() && jt_cve.getText().isEmpty() && jd_Desde.getDate() == null && jd_Hasta.getDate() != null && jr_subl.isSelected()) {
                JOptionPane.showMessageDialog(null, "Ingresa Desde que fecha es la consulta");
            } else if (jt_expediente.getText().isEmpty() && jt_cve.getText().length() > 0 && jd_Desde.getDate() == null && jd_Hasta.getDate() != null && jr_subl.isSelected()) {
                JOptionPane.showMessageDialog(null, "Ingresa Desde que fecha es la consulta");
            } else if (jt_expediente.getText().length() > 0 && jt_cve.getText().isEmpty() && jd_Desde.getDate() == null && jd_Hasta.getDate() != null && jr_subl.isSelected()) {
                JOptionPane.showMessageDialog(null, "Ingresa Desde que fecha es la consulta");
            } else if (jt_expediente.getText().length() > 0 && jt_cve.getText().length() > 0 && jd_Desde.getDate() == null && jd_Hasta.getDate() != null && jr_subl.isSelected()) {
                JOptionPane.showMessageDialog(null, "Ingresa Desde que fecha es la consulta");
            } else if (jt_expediente.getText().isEmpty() && jt_cve.getText().length() > 0 && jd_Desde.getDate() == null && jd_Hasta.getDate() == null && jr_subl.isSelected()) {
                JOptionPane.showMessageDialog(null, "Ingresa Un numero de Expediente o una fecha para la consulta");
            } else if (jt_expediente.getText().isEmpty() && jt_cve.getText().isEmpty() && jt_descripcion.getText().isEmpty() && jd_Desde.getDate() == null && jd_Hasta.getDate() == null && jr_inyec.isSelected()) {
                JOptionPane.showMessageDialog(null, "Ingresa un Expediente, una Clave, una Fecha Desde - Hasta o una Descripcion");
            } else if (jt_expediente.getText().isEmpty() && jt_cve.getText().isEmpty() && jt_descripcion.getText().isEmpty() && jd_Desde.getDate() != null && jd_Hasta.getDate() != null && jr_inyec.isSelected()) {

                task11Inf = new PBvExpDHInyec();
                task11Inf.execute();

            } else if (jt_expediente.getText().isEmpty() && jt_cve.getText().isEmpty() && jt_descripcion.getText().isEmpty() && jd_Desde.getDate() != null && jd_Hasta.getDate() == null && jr_inyec.isSelected()) {
                task12Inf = new PBvExpDInyec();
                task12Inf.execute();

            } else if (jt_expediente.getText().isEmpty() && jt_cve.getText().length() > 0 && jt_descripcion.getText().isEmpty() && jd_Desde.getDate() != null && jd_Hasta.getDate() != null && jr_inyec.isSelected()) {

                task13Inf = new PBvExpCveDHInyec();
                task13Inf.execute();

            } else if (jt_expediente.getText().isEmpty() && jt_cve.getText().length() > 0 && jt_descripcion.getText().isEmpty() && jd_Desde.getDate() != null && jd_Hasta.getDate() == null && jr_inyec.isSelected()) {

                task14Inf = new PBvExpCveDInyec();
                task14Inf.execute();

            } else if (jt_expediente.getText().isEmpty() && jt_cve.getText().length() > 0 && jt_descripcion.getText().length() > 0 && jd_Desde.getDate() != null && jd_Hasta.getDate() != null && jr_inyec.isSelected()) {
                task15Inf = new PBvExpCveDHInyecDesc();
                task15Inf.execute();

            } else if (jt_expediente.getText().isEmpty() && jt_cve.getText().length() > 0 && jt_descripcion.getText().length() > 0 && jd_Desde.getDate() != null && jd_Hasta.getDate() == null && jr_inyec.isSelected()) {
                task16Inf = new PBvExpCveDInyecDesc();
                task16Inf.execute();

            } else if (jt_expediente.getText().length() > 0 && jt_cve.getText().isEmpty() && jt_descripcion.getText().isEmpty() && jd_Desde.getDate() != null && jd_Hasta.getDate() != null && jr_inyec.isSelected()) {

                task17Inf = new PBvNoExpDHInyec();
                task17Inf.execute();

            } else if (jt_expediente.getText().length() > 0 && jt_cve.getText().isEmpty() && jt_descripcion.getText().isEmpty() && jd_Desde.getDate() != null && jd_Hasta.getDate() == null && jr_inyec.isSelected()) {
                task18Inf = new PBvNoExpDInyec();
                task18Inf.execute();

            } else if (jt_expediente.getText().length() > 0 && jt_cve.getText().isEmpty() && jt_descripcion.getText().length() > 0 && jd_Desde.getDate() != null && jd_Hasta.getDate() == null && jr_inyec.isSelected()) {
                task20Inf = new PBvNoExpDInyecDesc();
                task20Inf.execute();

            } else if (jt_expediente.getText().length() > 0 && jt_cve.getText().isEmpty() && jt_descripcion.getText().length() > 0 && jd_Desde.getDate() != null && jd_Hasta.getDate() != null && jr_inyec.isSelected()) {
                task19Inf = new PBvNoExpDHInyecDesc();
                task19Inf.execute();

            } else if (jt_expediente.getText().length() > 0 && jt_cve.getText().length() > 0 && jt_descripcion.getText().isEmpty() && jd_Desde.getDate() != null && jd_Hasta.getDate() != null && jr_inyec.isSelected()) {
                task21Inf = new PBvNoExpCveDHInyec();
                task21Inf.execute();

            } else if (jt_expediente.getText().length() > 0 && jt_cve.getText().length() > 0 && jt_descripcion.getText().isEmpty() && jd_Desde.getDate() != null && jd_Hasta.getDate() == null && jr_inyec.isSelected()) {
                task22Inf = new PBvNoExpCveDInyec();
                task22Inf.execute();

            } else if (jt_expediente.getText().length() > 0 && jt_cve.getText().length() > 0 && jt_descripcion.getText().length() > 0 && jd_Desde.getDate() != null && jd_Hasta.getDate() != null && jr_inyec.isSelected()) {
                task23Inf = new PBvNoExpCveDHInyecDesc();
                task23Inf.execute();

            } else if (jt_expediente.getText().length() > 0 && jt_cve.getText().length() > 0 && jt_descripcion.getText().length() > 0 && jd_Desde.getDate() != null && jd_Hasta.getDate() == null && jr_inyec.isSelected()) {
                task24Inf = new PBvNoExpCveDInyecDesc();
                task24Inf.execute();

            } else if (jt_expediente.getText().length() > 0 && jt_cve.getText().isEmpty() && jt_descripcion.getText().isEmpty() && jd_Desde.getDate() == null && jd_Hasta.getDate() == null && jr_inyec.isSelected()) {
                task25Inf = new PBvNoExpInyec();
                task25Inf.execute();

            } else if (jt_expediente.getText().length() > 0 && jt_cve.getText().length() > 0 && jt_descripcion.getText().isEmpty() && jd_Desde.getDate() == null && jd_Hasta.getDate() == null && jr_inyec.isSelected()) {
                task26Inf = new PBvNoExpCveInyec();
                task26Inf.execute();

            } else if (jt_expediente.getText().length() > 0 && jt_cve.getText().length() > 0 && jt_descripcion.getText().length() > 0 && jd_Desde.getDate() == null && jd_Hasta.getDate() == null && jr_inyec.isSelected()) {
                task27Inf = new PBvNoExpCveInyecDesc();
                task27Inf.execute();

            } else if (jt_expediente.getText().length() > 0 && jt_cve.getText().isEmpty() && jt_descripcion.getText().length() > 0 && jd_Desde.getDate() == null && jd_Hasta.getDate() == null && jr_inyec.isSelected()) {
                task28Inf = new PBvNoExpInyecDesc();
                task28Inf.execute();

            } else if (jt_expediente.getText().isEmpty() && jt_cve.getText().length() > 0 && jt_descripcion.getText().length() > 0 && jd_Desde.getDate() == null && jd_Hasta.getDate() == null && jr_inyec.isSelected()) {
                task29Inf = new PBvCveInyecDesc();
                task29Inf.execute();

            } else if (jt_expediente.getText().isEmpty() && jt_cve.getText().length() > 0 && jt_descripcion.getText().isEmpty() && jd_Desde.getDate() == null && jd_Hasta.getDate() == null && jr_inyec.isSelected()) {
                task30Inf = new PBvCveInyec();
                task30Inf.execute();

            } else if (jt_expediente.getText().isEmpty() && jt_cve.getText().isEmpty() && jt_descripcion.getText().length() > 0 && jd_Desde.getDate() != null && jd_Hasta.getDate() != null && jr_inyec.isSelected()) {
                task31Inf = new PBvDHInyecDesc();
                task31Inf.execute();

            } else if (jt_expediente.getText().isEmpty() && jt_cve.getText().isEmpty() && jt_descripcion.getText().length() > 0 && jd_Desde.getDate() != null && jd_Hasta.getDate() == null && jr_inyec.isSelected()) {
                task32Inf = new PBvDInyecDesc();
                task32Inf.execute();
            } else if (jt_expediente.getText().isEmpty() && jt_cve.getText().isEmpty() && jt_descripcion.getText().isEmpty() && jd_Desde.getDate() == null && jd_Hasta.getDate() != null && jr_inyec.isSelected()) {
                JOptionPane.showMessageDialog(null, "Ingresa Desde que fecha es la consulta");
            } else if (jt_expediente.getText().isEmpty() && jt_cve.getText().length() > 0 && jt_descripcion.getText().isEmpty() && jd_Desde.getDate() == null && jd_Hasta.getDate() != null && jr_inyec.isSelected()) {
                JOptionPane.showMessageDialog(null, "Ingresa Desde que fecha es la consulta");
            } else if (jt_expediente.getText().isEmpty() && jt_cve.getText().length() > 0 && jt_descripcion.getText().length() > 0 && jd_Desde.getDate() == null && jd_Hasta.getDate() != null && jr_inyec.isSelected()) {
                JOptionPane.showMessageDialog(null, "Ingresa Desde que fecha es la consulta");
            } else if (jt_expediente.getText().length() > 0 && jt_cve.getText().isEmpty() && jt_descripcion.getText().isEmpty() && jd_Desde.getDate() == null && jd_Hasta.getDate() != null && jr_inyec.isSelected()) {
                JOptionPane.showMessageDialog(null, "Ingresa Desde que fecha es la consulta");
            } else if (jt_expediente.getText().length() > 0 && jt_cve.getText().isEmpty() && jt_descripcion.getText().length() > 0 && jd_Desde.getDate() == null && jd_Hasta.getDate() != null && jr_inyec.isSelected()) {
                JOptionPane.showMessageDialog(null, "Ingresa Desde que fecha es la consulta");
            } else if (jt_expediente.getText().length() > 0 && jt_cve.getText().length() > 0 && jt_descripcion.getText().isEmpty() && jd_Desde.getDate() == null && jd_Hasta.getDate() != null && jr_inyec.isSelected()) {
                JOptionPane.showMessageDialog(null, "Ingresa Desde que fecha es la consulta");
            } else if (jt_expediente.getText().length() > 0 && jt_cve.getText().length() > 0 && jt_descripcion.getText().length() > 0 && jd_Desde.getDate() == null && jd_Hasta.getDate() != null && jr_inyec.isSelected()) {
                JOptionPane.showMessageDialog(null, "Ingresa Desde que fecha es la consulta");
            } else if (jt_expediente.getText().isEmpty() && jt_cve.getText().isEmpty() && jt_descripcion.getText().length() > 0 && jd_Desde.getDate() == null && jd_Hasta.getDate() != null && jr_inyec.isSelected()) {
                JOptionPane.showMessageDialog(null, "Ingresa Desde que fecha es la consulta");
            } else if (jt_expediente.getText().isEmpty() && jt_cve.getText().isEmpty() && jt_descripcion.getText().length() > 0 && jd_Desde.getDate() == null && jd_Hasta.getDate() == null && jr_inyec.isSelected()) {
                JOptionPane.showMessageDialog(null, "Ingresa Un Expediente, la clave o Desde que fecha es la consulta");
            }
        }
    }//GEN-LAST:event_jt_cveKeyPressed

    private void jt_descripcionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jt_descripcionKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (jt_expediente.getText().isEmpty() && jt_cve.getText().isEmpty() && jd_Desde.getDate() != null && jd_Hasta.getDate() != null && jr_subl.isSelected()) {
                task01Inf = new PBvExpDHSubl();
                task01Inf.execute();

            } else if (jt_expediente.getText().isEmpty() && jt_cve.getText().isEmpty() && jd_Desde.getDate() != null && jd_Hasta.getDate() == null && jr_subl.isSelected()) {
                task02Inf = new PBvExpDSubl();
                task02Inf.execute();

            } else if (jt_expediente.getText().isEmpty() && jt_cve.getText().length() > 0 && jd_Desde.getDate() != null && jd_Hasta.getDate() != null && jr_subl.isSelected()) {
                task03Inf = new PBvExpCveDHSubl();
                task03Inf.execute();

            } else if (jt_expediente.getText().isEmpty() && jt_cve.getText().length() > 0 && jd_Desde.getDate() != null && jd_Hasta.getDate() == null && jr_subl.isSelected()) {
                task04Inf = new PBvExpCveDSubl();
                task04Inf.execute();

            } else if (jt_expediente.getText().length() > 0 && jt_cve.getText().isEmpty() && jd_Desde.getDate() != null && jd_Hasta.getDate() == null && jr_subl.isSelected()) {
                task08Inf = new PBvNoExpDSubl();
                task08Inf.execute();

            } else if (jt_expediente.getText().length() > 0 && jt_cve.getText().isEmpty() && jd_Desde.getDate() != null && jd_Hasta.getDate() != null && jr_subl.isSelected()) {

                task07Inf = new PBvNoExpDHSubl();
                task07Inf.execute();

            } else if (jt_expediente.getText().length() > 0 && jt_cve.getText().length() > 0 && jd_Desde.getDate() != null && jd_Hasta.getDate() != null && jr_subl.isSelected()) {
                task09Inf = new PBvNoExpCveDHSubl();
                task09Inf.execute();

            } else if (jt_expediente.getText().length() > 0 && jt_cve.getText().length() > 0 && jd_Desde.getDate() != null && jd_Hasta.getDate() == null && jr_subl.isSelected()) {

                task10Inf = new PBvNoExpCveDSubl();
                task10Inf.execute();

            } else if (jt_expediente.getText().length() > 0 && jt_cve.getText().isEmpty() && jd_Desde.getDate() == null && jd_Hasta.getDate() == null && jr_subl.isSelected()) {

                task05Inf = new PBvNoExpSubl();
                task05Inf.execute();

            } else if (jt_expediente.getText().length() > 0 && jt_cve.getText().length() > 0 && jd_Desde.getDate() == null && jd_Hasta.getDate() == null && jr_subl.isSelected()) {

                task06Inf = new PBvNoExpCveSubl();
                task06Inf.execute();

            } else if (jt_expediente.getText().isEmpty() && jt_cve.getText().isEmpty() && jd_Desde.getDate() == null && jd_Hasta.getDate() == null && jr_subl.isSelected()) {
                JOptionPane.showMessageDialog(null, "Ingresa un Expediente, una Clave \n o una Fecha Desde - Hasta");
            } else if (jt_expediente.getText().isEmpty() && jt_cve.getText().isEmpty() && jd_Desde.getDate() == null && jd_Hasta.getDate() != null && jr_subl.isSelected()) {
                JOptionPane.showMessageDialog(null, "Ingresa Desde que fecha es la consulta");
            } else if (jt_expediente.getText().isEmpty() && jt_cve.getText().length() > 0 && jd_Desde.getDate() == null && jd_Hasta.getDate() != null && jr_subl.isSelected()) {
                JOptionPane.showMessageDialog(null, "Ingresa Desde que fecha es la consulta");
            } else if (jt_expediente.getText().length() > 0 && jt_cve.getText().isEmpty() && jd_Desde.getDate() == null && jd_Hasta.getDate() != null && jr_subl.isSelected()) {
                JOptionPane.showMessageDialog(null, "Ingresa Desde que fecha es la consulta");
            } else if (jt_expediente.getText().length() > 0 && jt_cve.getText().length() > 0 && jd_Desde.getDate() == null && jd_Hasta.getDate() != null && jr_subl.isSelected()) {
                JOptionPane.showMessageDialog(null, "Ingresa Desde que fecha es la consulta");
            } else if (jt_expediente.getText().isEmpty() && jt_cve.getText().length() > 0 && jd_Desde.getDate() == null && jd_Hasta.getDate() == null && jr_subl.isSelected()) {
                JOptionPane.showMessageDialog(null, "Ingresa Un numero de Expediente o una fecha para la consulta");
            } else if (jt_expediente.getText().isEmpty() && jt_cve.getText().isEmpty() && jt_descripcion.getText().isEmpty() && jd_Desde.getDate() == null && jd_Hasta.getDate() == null && jr_inyec.isSelected()) {
                JOptionPane.showMessageDialog(null, "Ingresa un Expediente, una Clave, una Fecha Desde - Hasta o una Descripcion");
            } else if (jt_expediente.getText().isEmpty() && jt_cve.getText().isEmpty() && jt_descripcion.getText().isEmpty() && jd_Desde.getDate() != null && jd_Hasta.getDate() != null && jr_inyec.isSelected()) {

                task11Inf = new PBvExpDHInyec();
                task11Inf.execute();

            } else if (jt_expediente.getText().isEmpty() && jt_cve.getText().isEmpty() && jt_descripcion.getText().isEmpty() && jd_Desde.getDate() != null && jd_Hasta.getDate() == null && jr_inyec.isSelected()) {
                task12Inf = new PBvExpDInyec();
                task12Inf.execute();

            } else if (jt_expediente.getText().isEmpty() && jt_cve.getText().length() > 0 && jt_descripcion.getText().isEmpty() && jd_Desde.getDate() != null && jd_Hasta.getDate() != null && jr_inyec.isSelected()) {

                task13Inf = new PBvExpCveDHInyec();
                task13Inf.execute();

            } else if (jt_expediente.getText().isEmpty() && jt_cve.getText().length() > 0 && jt_descripcion.getText().isEmpty() && jd_Desde.getDate() != null && jd_Hasta.getDate() == null && jr_inyec.isSelected()) {

                task14Inf = new PBvExpCveDInyec();
                task14Inf.execute();

            } else if (jt_expediente.getText().isEmpty() && jt_cve.getText().length() > 0 && jt_descripcion.getText().length() > 0 && jd_Desde.getDate() != null && jd_Hasta.getDate() != null && jr_inyec.isSelected()) {
                task15Inf = new PBvExpCveDHInyecDesc();
                task15Inf.execute();

            } else if (jt_expediente.getText().isEmpty() && jt_cve.getText().length() > 0 && jt_descripcion.getText().length() > 0 && jd_Desde.getDate() != null && jd_Hasta.getDate() == null && jr_inyec.isSelected()) {
                task16Inf = new PBvExpCveDInyecDesc();
                task16Inf.execute();

            } else if (jt_expediente.getText().length() > 0 && jt_cve.getText().isEmpty() && jt_descripcion.getText().isEmpty() && jd_Desde.getDate() != null && jd_Hasta.getDate() != null && jr_inyec.isSelected()) {

                task17Inf = new PBvNoExpDHInyec();
                task17Inf.execute();

            } else if (jt_expediente.getText().length() > 0 && jt_cve.getText().isEmpty() && jt_descripcion.getText().isEmpty() && jd_Desde.getDate() != null && jd_Hasta.getDate() == null && jr_inyec.isSelected()) {
                task18Inf = new PBvNoExpDInyec();
                task18Inf.execute();

            } else if (jt_expediente.getText().length() > 0 && jt_cve.getText().isEmpty() && jt_descripcion.getText().length() > 0 && jd_Desde.getDate() != null && jd_Hasta.getDate() == null && jr_inyec.isSelected()) {
                task20Inf = new PBvNoExpDInyecDesc();
                task20Inf.execute();

            } else if (jt_expediente.getText().length() > 0 && jt_cve.getText().isEmpty() && jt_descripcion.getText().length() > 0 && jd_Desde.getDate() != null && jd_Hasta.getDate() != null && jr_inyec.isSelected()) {
                task19Inf = new PBvNoExpDHInyecDesc();
                task19Inf.execute();

            } else if (jt_expediente.getText().length() > 0 && jt_cve.getText().length() > 0 && jt_descripcion.getText().isEmpty() && jd_Desde.getDate() != null && jd_Hasta.getDate() != null && jr_inyec.isSelected()) {
                task21Inf = new PBvNoExpCveDHInyec();
                task21Inf.execute();

            } else if (jt_expediente.getText().length() > 0 && jt_cve.getText().length() > 0 && jt_descripcion.getText().isEmpty() && jd_Desde.getDate() != null && jd_Hasta.getDate() == null && jr_inyec.isSelected()) {
                task22Inf = new PBvNoExpCveDInyec();
                task22Inf.execute();

            } else if (jt_expediente.getText().length() > 0 && jt_cve.getText().length() > 0 && jt_descripcion.getText().length() > 0 && jd_Desde.getDate() != null && jd_Hasta.getDate() != null && jr_inyec.isSelected()) {
                task23Inf = new PBvNoExpCveDHInyecDesc();
                task23Inf.execute();

            } else if (jt_expediente.getText().length() > 0 && jt_cve.getText().length() > 0 && jt_descripcion.getText().length() > 0 && jd_Desde.getDate() != null && jd_Hasta.getDate() == null && jr_inyec.isSelected()) {
                task24Inf = new PBvNoExpCveDInyecDesc();
                task24Inf.execute();

            } else if (jt_expediente.getText().length() > 0 && jt_cve.getText().isEmpty() && jt_descripcion.getText().isEmpty() && jd_Desde.getDate() == null && jd_Hasta.getDate() == null && jr_inyec.isSelected()) {
                task25Inf = new PBvNoExpInyec();
                task25Inf.execute();

            } else if (jt_expediente.getText().length() > 0 && jt_cve.getText().length() > 0 && jt_descripcion.getText().isEmpty() && jd_Desde.getDate() == null && jd_Hasta.getDate() == null && jr_inyec.isSelected()) {
                task26Inf = new PBvNoExpCveInyec();
                task26Inf.execute();

            } else if (jt_expediente.getText().length() > 0 && jt_cve.getText().length() > 0 && jt_descripcion.getText().length() > 0 && jd_Desde.getDate() == null && jd_Hasta.getDate() == null && jr_inyec.isSelected()) {
                task27Inf = new PBvNoExpCveInyecDesc();
                task27Inf.execute();

            } else if (jt_expediente.getText().length() > 0 && jt_cve.getText().isEmpty() && jt_descripcion.getText().length() > 0 && jd_Desde.getDate() == null && jd_Hasta.getDate() == null && jr_inyec.isSelected()) {
                task28Inf = new PBvNoExpInyecDesc();
                task28Inf.execute();

            } else if (jt_expediente.getText().isEmpty() && jt_cve.getText().length() > 0 && jt_descripcion.getText().length() > 0 && jd_Desde.getDate() == null && jd_Hasta.getDate() == null && jr_inyec.isSelected()) {
                task29Inf = new PBvCveInyecDesc();
                task29Inf.execute();

            } else if (jt_expediente.getText().isEmpty() && jt_cve.getText().length() > 0 && jt_descripcion.getText().isEmpty() && jd_Desde.getDate() == null && jd_Hasta.getDate() == null && jr_inyec.isSelected()) {
                task30Inf = new PBvCveInyec();
                task30Inf.execute();

            } else if (jt_expediente.getText().isEmpty() && jt_cve.getText().isEmpty() && jt_descripcion.getText().length() > 0 && jd_Desde.getDate() != null && jd_Hasta.getDate() != null && jr_inyec.isSelected()) {
                task31Inf = new PBvDHInyecDesc();
                task31Inf.execute();

            } else if (jt_expediente.getText().isEmpty() && jt_cve.getText().isEmpty() && jt_descripcion.getText().length() > 0 && jd_Desde.getDate() != null && jd_Hasta.getDate() == null && jr_inyec.isSelected()) {
                task32Inf = new PBvDInyecDesc();
                task32Inf.execute();
            } else if (jt_expediente.getText().isEmpty() && jt_cve.getText().isEmpty() && jt_descripcion.getText().isEmpty() && jd_Desde.getDate() == null && jd_Hasta.getDate() != null && jr_inyec.isSelected()) {
                JOptionPane.showMessageDialog(null, "Ingresa Desde que fecha es la consulta");
            } else if (jt_expediente.getText().isEmpty() && jt_cve.getText().length() > 0 && jt_descripcion.getText().isEmpty() && jd_Desde.getDate() == null && jd_Hasta.getDate() != null && jr_inyec.isSelected()) {
                JOptionPane.showMessageDialog(null, "Ingresa Desde que fecha es la consulta");
            } else if (jt_expediente.getText().isEmpty() && jt_cve.getText().length() > 0 && jt_descripcion.getText().length() > 0 && jd_Desde.getDate() == null && jd_Hasta.getDate() != null && jr_inyec.isSelected()) {
                JOptionPane.showMessageDialog(null, "Ingresa Desde que fecha es la consulta");
            } else if (jt_expediente.getText().length() > 0 && jt_cve.getText().isEmpty() && jt_descripcion.getText().isEmpty() && jd_Desde.getDate() == null && jd_Hasta.getDate() != null && jr_inyec.isSelected()) {
                JOptionPane.showMessageDialog(null, "Ingresa Desde que fecha es la consulta");
            } else if (jt_expediente.getText().length() > 0 && jt_cve.getText().isEmpty() && jt_descripcion.getText().length() > 0 && jd_Desde.getDate() == null && jd_Hasta.getDate() != null && jr_inyec.isSelected()) {
                JOptionPane.showMessageDialog(null, "Ingresa Desde que fecha es la consulta");
            } else if (jt_expediente.getText().length() > 0 && jt_cve.getText().length() > 0 && jt_descripcion.getText().isEmpty() && jd_Desde.getDate() == null && jd_Hasta.getDate() != null && jr_inyec.isSelected()) {
                JOptionPane.showMessageDialog(null, "Ingresa Desde que fecha es la consulta");
            } else if (jt_expediente.getText().length() > 0 && jt_cve.getText().length() > 0 && jt_descripcion.getText().length() > 0 && jd_Desde.getDate() == null && jd_Hasta.getDate() != null && jr_inyec.isSelected()) {
                JOptionPane.showMessageDialog(null, "Ingresa Desde que fecha es la consulta");
            } else if (jt_expediente.getText().isEmpty() && jt_cve.getText().isEmpty() && jt_descripcion.getText().length() > 0 && jd_Desde.getDate() == null && jd_Hasta.getDate() != null && jr_inyec.isSelected()) {
                JOptionPane.showMessageDialog(null, "Ingresa Desde que fecha es la consulta");
            } else if (jt_expediente.getText().isEmpty() && jt_cve.getText().isEmpty() && jt_descripcion.getText().length() > 0 && jd_Desde.getDate() == null && jd_Hasta.getDate() == null && jr_inyec.isSelected()) {
                JOptionPane.showMessageDialog(null, "Ingresa Un Expediente, la clave o Desde que fecha es la consulta");
            }
        }
    }//GEN-LAST:event_jt_descripcionKeyPressed

    
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
            java.util.logging.Logger.getLogger(jfVExpediente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(jfVExpediente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(jfVExpediente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(jfVExpediente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
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
                new jfVExpediente().setVisible(true);
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
    private com.toedter.calendar.JDateChooser jd_Desde;
    private com.toedter.calendar.JDateChooser jd_Hasta;
    private javax.swing.JLabel jl_cve;
    private javax.swing.JLabel jl_descripcion;
    private javax.swing.JLabel jl_desde;
    private javax.swing.JLabel jl_etiqueta;
    private javax.swing.JLabel jl_expediente;
    private javax.swing.JLabel jl_hasta;
    private javax.swing.JLabel jl_titulo;
    private javax.swing.JLabel jl_tt_reg;
    private javax.swing.JProgressBar jp_bar;
    private javax.swing.JRadioButton jr_inyec;
    private javax.swing.JRadioButton jr_subl;
    private javax.swing.JTable jt_consulta_vacunas;
    private javax.swing.JTextField jt_cve;
    private javax.swing.JTextField jt_descripcion;
    private javax.swing.JTextField jt_expediente;
    // End of variables declaration//GEN-END:variables

    class PBvExpDHSubl extends SwingWorker<Void, Void> {

        @Override
        protected Void doInBackground() throws Exception {
            jp_bar.setVisible(true);
            jp_bar.setIndeterminate(true);
            jl_etiqueta.setVisible(true);
            vExpDHSubl();
            return null;
        }

        @Override
        protected void done() {
            jp_bar.setVisible(false);
            jl_etiqueta.setVisible(false);
        }

    }
    
    class PBvExpDSubl extends SwingWorker<Void, Void> {

        @Override
        protected Void doInBackground() throws Exception {
            jp_bar.setVisible(true);
            jp_bar.setIndeterminate(true);
            jl_etiqueta.setVisible(true);
            vExpDSubl();
            return null;
        }

        @Override
        protected void done() {
            jp_bar.setVisible(false);
            jl_etiqueta.setVisible(false);
        }
    }
    
    class PBvExpCveDHSubl extends SwingWorker<Void, Void> {

        @Override
        protected Void doInBackground() throws Exception {
            jp_bar.setVisible(true);
            jp_bar.setIndeterminate(true);
            jl_etiqueta.setVisible(true);
            vExpCveDHSubl();
            return null;
        }

        @Override
        protected void done() {
            jp_bar.setVisible(false);
            jl_etiqueta.setVisible(false);
        }
    }
    
    class PBvExpCveDSubl extends SwingWorker<Void, Void> {

        @Override
        protected Void doInBackground() throws Exception {
            jp_bar.setVisible(true);
            jp_bar.setIndeterminate(true);
            jl_etiqueta.setVisible(true);
            vExpCveDSubl();
            return null;
        }

        @Override
        protected void done() {
            jp_bar.setVisible(false);
            jl_etiqueta.setVisible(false);
        }
    }

    class PBvNoExpSubl extends SwingWorker<Void, Void> {

        @Override
        protected Void doInBackground() throws Exception {
            jp_bar.setVisible(true);
            jp_bar.setIndeterminate(true);
            jl_etiqueta.setVisible(true);
            vNoExpSubl();
            return null;
        }

        @Override
        protected void done() {
            jp_bar.setVisible(false);
            jl_etiqueta.setVisible(false);
        }
    }
    
    class PBvNoExpCveSubl extends SwingWorker<Void, Void> {

        @Override
        protected Void doInBackground() throws Exception {
            jp_bar.setVisible(true);
            jp_bar.setIndeterminate(true);
            jl_etiqueta.setVisible(true);
            vNoExpCveSubl();
            return null;
        }

        @Override
        protected void done() {
            jp_bar.setVisible(false);
            jl_etiqueta.setVisible(false);
        }
    }
    
    class PBvNoExpDHSubl extends SwingWorker<Void, Void> {

        @Override
        protected Void doInBackground() throws Exception {
            jp_bar.setVisible(true);
            jp_bar.setIndeterminate(true);
            jl_etiqueta.setVisible(true);
            vNoExpDHSubl();
            return null;
        }

        @Override
        protected void done() {
            jp_bar.setVisible(false);
            jl_etiqueta.setVisible(false);
        }
    }
    
    class PBvNoExpDSubl extends SwingWorker<Void, Void> {

        @Override
        protected Void doInBackground() throws Exception {
            jp_bar.setVisible(true);
            jp_bar.setIndeterminate(true);
            jl_etiqueta.setVisible(true);
            vNoExpDSubl();
            return null;
        }

        @Override
        protected void done() {
            jp_bar.setVisible(false);
            jl_etiqueta.setVisible(false);
        }
    }
    
    class PBvNoExpCveDHSubl extends SwingWorker<Void, Void> {

        @Override
        protected Void doInBackground() throws Exception {
            jp_bar.setVisible(true);
            jp_bar.setIndeterminate(true);
            jl_etiqueta.setVisible(true);
            vNoExpCveDHSubl();
            return null;
        }

        @Override
        protected void done() {
            jp_bar.setVisible(false);
            jl_etiqueta.setVisible(false);
        }
    }
    
    class PBvNoExpCveDSubl extends SwingWorker<Void, Void> {

        @Override
        protected Void doInBackground() throws Exception {
            jp_bar.setVisible(true);
            jp_bar.setIndeterminate(true);
            jl_etiqueta.setVisible(true);
            vNoExpCveDSubl();
            return null;
        }

        @Override
        protected void done() {
            jp_bar.setVisible(false);
            jl_etiqueta.setVisible(false);
        }
    }
    
    class PBvExpDHInyec extends SwingWorker<Void, Void> {

        @Override
        protected Void doInBackground() throws Exception {
            jp_bar.setVisible(true);
            jp_bar.setIndeterminate(true);
            jl_etiqueta.setVisible(true);
            vExpDHInyec();
            return null;
        }

        @Override
        protected void done() {
            jp_bar.setVisible(false);
            jl_etiqueta.setVisible(false);
        }
    }
    
    class PBvExpDInyec extends SwingWorker<Void, Void> {

        @Override
        protected Void doInBackground() throws Exception {
            jp_bar.setVisible(true);
            jp_bar.setIndeterminate(true);
            jl_etiqueta.setVisible(true);
            vExpDInyec();
            return null;
        }

        @Override
        protected void done() {
            jp_bar.setVisible(false);
            jl_etiqueta.setVisible(false);
        }
    }
    
    class PBvExpCveDHInyec extends SwingWorker<Void, Void> {

        @Override
        protected Void doInBackground() throws Exception {
            jp_bar.setVisible(true);
            jp_bar.setIndeterminate(true);
            jl_etiqueta.setVisible(true);
            vExpCveDHInyec();
            return null;
        }

        @Override
        protected void done() {
            jp_bar.setVisible(false);
            jl_etiqueta.setVisible(false);
        }
    }
    
    class PBvExpCveDInyec extends SwingWorker<Void, Void> {

        @Override
        protected Void doInBackground() throws Exception {
            jp_bar.setVisible(true);
            jp_bar.setIndeterminate(true);
            jl_etiqueta.setVisible(true);
            vExpCveDInyec();
            return null;
        }

        @Override
        protected void done() {
            jp_bar.setVisible(false);
            jl_etiqueta.setVisible(false);
        }
    }
    
    class PBvExpCveDHInyecDesc extends SwingWorker<Void, Void> {

        @Override
        protected Void doInBackground() throws Exception {
            jp_bar.setVisible(true);
            jp_bar.setIndeterminate(true);
            jl_etiqueta.setVisible(true);
            vExpCveDHInyecDesc();
            return null;
        }

        @Override
        protected void done() {
            jp_bar.setVisible(false);
            jl_etiqueta.setVisible(false);
        }
    }
    
    class PBvExpCveDInyecDesc extends SwingWorker<Void, Void> {

        @Override
        protected Void doInBackground() throws Exception {
            jp_bar.setVisible(true);
            jp_bar.setIndeterminate(true);
            jl_etiqueta.setVisible(true);
            vExpCveDInyecDesc();
            return null;
        }

        @Override
        protected void done() {
            jp_bar.setVisible(false);
            jl_etiqueta.setVisible(false);
        }
    }
    
    class PBvNoExpDHInyec extends SwingWorker<Void, Void> {

        @Override
        protected Void doInBackground() throws Exception {
            jp_bar.setVisible(true);
            jp_bar.setIndeterminate(true);
            jl_etiqueta.setVisible(true);
            vNoExpDHInyec();
            return null;
        }

        @Override
        protected void done() {
            jp_bar.setVisible(false);
            jl_etiqueta.setVisible(false);
        }
    }
    
    class PBvNoExpDInyec extends SwingWorker<Void, Void> {

        @Override
        protected Void doInBackground() throws Exception {
            jp_bar.setVisible(true);
            jp_bar.setIndeterminate(true);
            jl_etiqueta.setVisible(true);
            vNoExpDInyec();
            return null;
        }

        @Override
        protected void done() {
            jp_bar.setVisible(false);
            jl_etiqueta.setVisible(false);
        }
    }
    
    class PBvNoExpDHInyecDesc extends SwingWorker<Void, Void> {

        @Override
        protected Void doInBackground() throws Exception {
            jp_bar.setVisible(true);
            jp_bar.setIndeterminate(true);
            jl_etiqueta.setVisible(true);
            vNoExpDHInyecDesc();
            return null;
        }

        @Override
        protected void done() {
            jp_bar.setVisible(false);
            jl_etiqueta.setVisible(false);
        }
    }
    
    class PBvNoExpDInyecDesc extends SwingWorker<Void, Void> {

        @Override
        protected Void doInBackground() throws Exception {
            jp_bar.setVisible(true);
            jp_bar.setIndeterminate(true);
            jl_etiqueta.setVisible(true);
            vNoExpDInyecDesc();
            return null;
        }

        @Override
        protected void done() {
            jp_bar.setVisible(false);
            jl_etiqueta.setVisible(false);
        }
    }
    
    class PBvNoExpCveDHInyec extends SwingWorker<Void, Void> {

        @Override
        protected Void doInBackground() throws Exception {
            jp_bar.setVisible(true);
            jp_bar.setIndeterminate(true);
            jl_etiqueta.setVisible(true);
            vNoExpCveDHInyec();
            return null;
        }

        @Override
        protected void done() {
            jp_bar.setVisible(false);
            jl_etiqueta.setVisible(false);
        }
    }
    
    class PBvNoExpCveDInyec extends SwingWorker<Void, Void> {

        @Override
        protected Void doInBackground() throws Exception {
            jp_bar.setVisible(true);
            jp_bar.setIndeterminate(true);
            jl_etiqueta.setVisible(true);
            vNoExpCveDInyec();
            return null;
        }

        @Override
        protected void done() {
            jp_bar.setVisible(false);
            jl_etiqueta.setVisible(false);
        }
    }

    class PBvNoExpCveDHInyecDesc extends SwingWorker<Void, Void> {

        @Override
        protected Void doInBackground() throws Exception {
            jp_bar.setVisible(true);
            jp_bar.setIndeterminate(true);
            jl_etiqueta.setVisible(true);
            vNoExpCveDHInyecDesc();
            return null;
        }

        @Override
        protected void done() {
            jp_bar.setVisible(false);
            jl_etiqueta.setVisible(false);
        }
    }
    
    class PBvNoExpCveDInyecDesc extends SwingWorker<Void, Void> {

        @Override
        protected Void doInBackground() throws Exception {
            jp_bar.setVisible(true);
            jp_bar.setIndeterminate(true);
            jl_etiqueta.setVisible(true);
            vNoExpCveDInyecDesc();
            return null;
        }

        @Override
        protected void done() {
            jp_bar.setVisible(false);
            jl_etiqueta.setVisible(false);
        }
    }
    
    class PBvNoExpInyec extends SwingWorker<Void, Void> {

        @Override
        protected Void doInBackground() throws Exception {
            jp_bar.setVisible(true);
            jp_bar.setIndeterminate(true);
            jl_etiqueta.setVisible(true);
            vNoExpInyec();
            return null;
        }

        @Override
        protected void done() {
            jp_bar.setVisible(false);
            jl_etiqueta.setVisible(false);
        }
    }
    
    class PBvNoExpCveInyec extends SwingWorker<Void, Void> {

        @Override
        protected Void doInBackground() throws Exception {
            jp_bar.setVisible(true);
            jp_bar.setIndeterminate(true);
            jl_etiqueta.setVisible(true);
            vNoExpCveInyec();
            return null;
        }

        @Override
        protected void done() {
            jp_bar.setVisible(false);
            jl_etiqueta.setVisible(false);
        }
    }
    
    class PBvNoExpCveInyecDesc extends SwingWorker<Void, Void> {

        @Override
        protected Void doInBackground() throws Exception {
            jp_bar.setVisible(true);
            jp_bar.setIndeterminate(true);
            jl_etiqueta.setVisible(true);
            vNoExpCveInyecDesc();
            return null;
        }

        @Override
        protected void done() {
            jp_bar.setVisible(false);
            jl_etiqueta.setVisible(false);
        }
    }
    
    class PBvNoExpInyecDesc extends SwingWorker<Void, Void> {

        @Override
        protected Void doInBackground() throws Exception {
            jp_bar.setVisible(true);
            jp_bar.setIndeterminate(true);
            jl_etiqueta.setVisible(true);
            vNoExpInyecDesc();
            return null;
        }

        @Override
        protected void done() {
            jp_bar.setVisible(false);
            jl_etiqueta.setVisible(false);
        }
    }
    
    class PBvCveInyecDesc extends SwingWorker<Void, Void> {

        @Override
        protected Void doInBackground() throws Exception {
            jp_bar.setVisible(true);
            jp_bar.setIndeterminate(true);
            jl_etiqueta.setVisible(true);
            vCveInyecDesc();
            return null;
        }

        @Override
        protected void done() {
            jp_bar.setVisible(false);
            jl_etiqueta.setVisible(false);
        }
    }
    
    class PBvCveInyec extends SwingWorker<Void, Void> {

        @Override
        protected Void doInBackground() throws Exception {
            jp_bar.setVisible(true);
            jp_bar.setIndeterminate(true);
            jl_etiqueta.setVisible(true);
            vCveInyec();
            return null;
        }

        @Override
        protected void done() {
            jp_bar.setVisible(false);
            jl_etiqueta.setVisible(false);
        }
    }
    
    class PBvDHInyecDesc extends SwingWorker<Void, Void> {

        @Override
        protected Void doInBackground() throws Exception {
            jp_bar.setVisible(true);
            jp_bar.setIndeterminate(true);
            jl_etiqueta.setVisible(true);
            vDHInyecDesc();
            return null;
        }

        @Override
        protected void done() {
            jp_bar.setVisible(false);
            jl_etiqueta.setVisible(false);
        }
    }
    
    class PBvDInyecDesc extends SwingWorker<Void, Void> {

        @Override
        protected Void doInBackground() throws Exception {
            jp_bar.setVisible(true);
            jp_bar.setIndeterminate(true);
            jl_etiqueta.setVisible(true);
            vDInyecDesc();
            return null;
        }

        @Override
        protected void done() {
            jp_bar.setVisible(false);
            jl_etiqueta.setVisible(false);
        }
    }
}
