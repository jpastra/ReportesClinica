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
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.Date;
import java.text.SimpleDateFormat;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;
import java.awt.Color;

/**
 * @author Jessica Viviana Pastrana Munguia
 */
public class jfInhalos extends javax.swing.JFrame {
    ResultSet rs;
    private PBinhalosC task01Inf;
    private PBinhalosCExp task02Inf;
    private PBinhalosCExpFechDH task03Inf;
    private PBinhalosCFecha task04Inf;
    private PBinhalosCInc task05Inf;
    private PBinhalosCExpCve task06Inf;
    private PBinhalosCExpCveD task07Inf;
    private PBinhalosCExpCveDH task08Inf;
    private PBinhalosCFechaD task09Inf;
    private PBinhalosCveFecD task10Inf;
    private PBinhalosCveFecDH task11Inf;

    /**
     * Creates new form jfInhalos
     */
    public jfInhalos() {
        initComponents();
        setLocationRelativeTo(null);
        //setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(false); 
        jl_titulo.setHorizontalAlignment(JLabel.CENTER);
        jl_titulo.setForeground(Color.white);
        jl_titulo.setOpaque(true);
        jl_sub_titulo.setForeground(Color.white);
        jl_sub_titulo.setOpaque(true);
        jl_etiqueta.setVisible(false);
        jp_bar.setVisible(false);

    }

//Metodo inhalo consulta general
    public void inhalosC() throws SQLException {

        //Obtener la fecha del dia de hoy
        Date date = new Date();
        SimpleDateFormat sdfT = new SimpleDateFormat("dd/MM/yyyy");
        String strDate = sdfT.format(date);

        rs = Conexiondb.Consulta("SELECT A.PACNEXP AS EXPEDIENTE, A.PACNOM AS NOMBRE, TO_CHAR(B.INHFEC,'dd/MM/yyyy') AS FECHA, B.INHHORA AS HORA, B.INHCVE AS CLAVE, B.INHMED AS TRATAMIENTO, B.INHNOM AS NOMBRE, COUNT(B.INHCVE) AS TOTAL FROM PACCAT A, INHPAC B WHERE A.PACNEXP=B.PACNEXP AND B.INHFEC = TO_DATE('" + strDate + "', 'dd/MM/yyyy') GROUP BY A.PACNEXP, A.PACNOM, B.INHFEC, B.INHHORA, B.INHCVE, B.INHMED, B.INHNOM ORDER BY B.INHFEC DESC ");

        ResultSetMetaData rsmt = rs.getMetaData();
        int numCol = rsmt.getColumnCount();

        //Creamos el objeto del modelo de la tabla
        DefaultTableModel modelo = new DefaultTableModel();
        this.jt_consulta_inhalos.setModel(modelo);

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

            for (int fila = 0; fila < jt_consulta_inhalos.getRowCount(); fila++) {
                a = String.valueOf(jt_consulta_inhalos.getValueAt(fila, 7));
                b = Integer.valueOf(a);
                total = total + b;
            }

            modelo.addRow(new Object[]{"", "", "", "", "", "","Total", String.valueOf(total)});

            rs.close();
            tt_rec();
        }
    }

//Metodo Inhalos por Expediente
    public void inhalosCExp() throws SQLException {

        rs = Conexiondb.Consulta("SELECT A.PACNEXP AS EXPEDIENTE, A.PACNOM AS NOMBRE, TO_CHAR(B.INHFEC,'dd/MM/yyyy') AS FECHA, B.INHHORA AS HORA, B.INHCVE AS CLAVE, B.INHMED AS TRATAMIENTO,B.INHNOM AS NOMBRE, COUNT(B.INHCVE) AS TOTAL FROM PACCAT A, INHPAC B WHERE A.PACNEXP=B.PACNEXP AND A.PACNEXP='" + jt_expediente.getText() + "' GROUP BY A.PACNEXP, A.PACNOM, B.INHFEC, B.INHHORA, B.INHCVE, B.INHMED, B.INHNOM ORDER BY B.INHFEC DESC");

        ResultSetMetaData rsmt = rs.getMetaData();
        int numCol = rsmt.getColumnCount();

        //Creamos el objeto del modelo de la tabla
        DefaultTableModel modelo = new DefaultTableModel();
        this.jt_consulta_inhalos.setModel(modelo);

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

            for (int fila = 0; fila < jt_consulta_inhalos.getRowCount(); fila++) {
                a = String.valueOf(jt_consulta_inhalos.getValueAt(fila, 7));
                b = Integer.valueOf(a);
                total = total + b;
            }

            modelo.addRow(new Object[]{"", "", "", "", "", "", "Total", String.valueOf(total)});

            rs.close();
            tt_rec();
        }
    }

    //Metodo Inhalos por Expediente y por fecha Desde Hasta
    public void inhalosCExpFechDH() throws SQLException {

        String formato = "dd/MM/yyyy";
        Date date;
        date = jd_Desde.getDate();
        SimpleDateFormat sdfD = new SimpleDateFormat(formato);
        String strDateD = sdfD.format(date);

        Date date2 = jd_Hasta.getDate();
        SimpleDateFormat sdfH = new SimpleDateFormat(formato);
        String strDateH = sdfH.format(date2);

        rs = Conexiondb.Consulta("SELECT A.PACNEXP AS EXPEDIENTE, A.PACNOM AS NOMBRE, TO_CHAR(B.INHFEC,'dd/MM/yyyy') AS FECHA, B.INHHORA AS HORA, B.INHCVE AS CLAVE, B.INHMED AS TRATAMIENTO,B.INHNOM AS NOMBRE, COUNT(B.INHCVE) AS TOTAL FROM PACCAT A, INHPAC B WHERE A.PACNEXP=B.PACNEXP AND A.PACNEXP='" + jt_expediente.getText() + "' AND B.INHFEC BETWEEN TO_DATE('" + strDateD + "', 'dd/MM/yyyy') AND  TO_DATE('" + strDateH + "', 'dd/MM/yyyy') GROUP BY A.PACNEXP, A.PACNOM, B.INHFEC, B.INHHORA, B.INHCVE, B.INHMED, B.INHNOM ORDER BY B.INHFEC DESC");

        ResultSetMetaData rsmt = rs.getMetaData();
        int numCol = rsmt.getColumnCount();
      
        //Creamos el objeto del modelo de la tabla
        DefaultTableModel modelo = new DefaultTableModel();
        this.jt_consulta_inhalos.setModel(modelo);

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

            for (int fila = 0; fila < jt_consulta_inhalos.getRowCount(); fila++) {
                a = String.valueOf(jt_consulta_inhalos.getValueAt(fila, 7));
                b = Integer.valueOf(a);
                total = total + b;
            }

            modelo.addRow(new Object[]{"", "", "", "", "", "", "Total", String.valueOf(total)});

            rs.close();
            tt_rec();
        }
    }

    //Metodo Inhalos por Fecha   
    public void inhalosCFecha() throws SQLException {

        String formato = "dd/MM/yyyy";
        Date date;
        date = jd_Desde.getDate();
        SimpleDateFormat sdfD = new SimpleDateFormat(formato);
        String strDateD = sdfD.format(date);

        Date date2 = jd_Hasta.getDate();
        SimpleDateFormat sdfH = new SimpleDateFormat(formato);
        String strDateH = sdfH.format(date2);

        rs = Conexiondb.Consulta("SELECT A.PACNEXP AS EXPEDIENTE, A.PACNOM AS NOMBRE, TO_CHAR(B.INHFEC,'dd/MM/yyyy') AS FECHA, B.INHHORA AS HORA, B.INHCVE AS CLAVE, B.INHMED AS TRATAMIENTO,B.INHNOM AS NOMBRE, COUNT(B.INHCVE) AS TOTAL FROM PACCAT A, INHPAC B WHERE A.PACNEXP=B.PACNEXP AND B.INHFEC BETWEEN TO_DATE('" + strDateD + "', 'dd/MM/yyyy') AND  TO_DATE('" + strDateH + "', 'dd/MM/yyyy') GROUP BY A.PACNEXP, A.PACNOM, B.INHFEC, B.INHHORA, B.INHCVE, B.INHMED,B.INHNOM  ORDER BY B.INHFEC DESC");

        ResultSetMetaData rsmt = rs.getMetaData();
        int numCol = rsmt.getColumnCount();

        //Creamos el objeto del modelo de la tabla
        DefaultTableModel modelo = new DefaultTableModel();
        this.jt_consulta_inhalos.setModel(modelo);

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

            for (int fila = 0; fila < jt_consulta_inhalos.getRowCount(); fila++) {
                a = String.valueOf(jt_consulta_inhalos.getValueAt(fila, 7));
                b = Integer.valueOf(a);
                total = total + b;
            }

            modelo.addRow(new Object[]{"", "", "", "", "", "", "Total", String.valueOf(total)});

            rs.close();
            tt_rec();
        }
    }

    //Metodos Inhalos por clave
    public void inhalosCInc() throws SQLException {

        rs = Conexiondb.Consulta("SELECT A.PACNEXP AS EXPEDIENTE, A.PACNOM AS NOMBRE, TO_CHAR(B.INHFEC,'dd/MM/yyyy') AS FECHA, B.INHHORA AS HORA, B.INHCVE AS CLAVE, B.INHMED AS TRATAMIENTO,B.INHNOM AS NOMBRE, COUNT(B.INHCVE) AS TOTAL FROM PACCAT A, INHPAC B WHERE A.PACNEXP=B.PACNEXP AND B.INHCVE LIKE '%" + jt_clave.getText().toUpperCase() + "%' GROUP BY A.PACNEXP, A.PACNOM, B.INHFEC, B.INHHORA, B.INHCVE, B.INHMED, B.INHNOM  ORDER BY B.INHFEC DESC");

        ResultSetMetaData rsmt = rs.getMetaData();
        int numCol = rsmt.getColumnCount();

        //Creamos el objeto del modelo de la tabla
        DefaultTableModel modelo = new DefaultTableModel();
        this.jt_consulta_inhalos.setModel(modelo);

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

            for (int fila = 0; fila < jt_consulta_inhalos.getRowCount(); fila++) {
                a = String.valueOf(jt_consulta_inhalos.getValueAt(fila, 7));
                b = Integer.valueOf(a);
                total = total + b;
            }

            modelo.addRow(new Object[]{"", "", "", "", "", "", "Total", String.valueOf(total)});

            rs.close();
            tt_rec();
        }
    }

    //Metodo inhalos por Expediente X Clave
    public void inhalosCExpCve() throws SQLException {

        rs = Conexiondb.Consulta("SELECT  A.PACNEXP AS EXPEDIENTE, A.PACNOM AS NOMBRE, TO_CHAR(B.INHFEC,'dd/MM/yyyy') AS FECHA, B.INHHORA AS HORA, B.INHCVE AS CLAVE, B.INHMED AS TRATAMIENTO, B.INHNOM AS NOMBRE, COUNT(B.INHCVE) AS TOTAL FROM PACCAT A, INHPAC B WHERE A.PACNEXP=B.PACNEXP AND A.PACNEXP='" + jt_expediente.getText() + "' AND B.INHCVE LIKE '%" + jt_clave.getText().toUpperCase() + "%' GROUP BY A.PACNEXP, A.PACNOM, B.INHFEC, B.INHHORA, B.INHCVE, B.INHMED, B.INHNOM  ORDER BY B.INHFEC DESC");

        ResultSetMetaData rsmt = rs.getMetaData();
        int numCol = rsmt.getColumnCount();

        //Creamos el objeto del modelo de la tabla
        DefaultTableModel modelo = new DefaultTableModel();
        this.jt_consulta_inhalos.setModel(modelo);

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

            for (int fila = 0; fila < jt_consulta_inhalos.getRowCount(); fila++) {
                a = String.valueOf(jt_consulta_inhalos.getValueAt(fila, 7));
                b = Integer.valueOf(a);
                total = total + b;
            }

            modelo.addRow(new Object[]{"", "", "", "", "", "", "Total", String.valueOf(total)});

            rs.close();
            tt_rec();
        }
    }

    //Metodo inhalos X Expediente X Clave X Fecha Desde
    public void inhalosCExpCveD() throws SQLException {

        String formato = "dd/MM/yyyy";
        Date date;
        date = jd_Desde.getDate();
        SimpleDateFormat sdfD = new SimpleDateFormat(formato);
        String strDateD = sdfD.format(date);

        rs = Conexiondb.Consulta("SELECT A.PACNEXP AS EXPEDIENTE, A.PACNOM AS NOMBRE, TO_CHAR(B.INHFEC,'dd/MM/yyyy') AS FECHA, B.INHHORA AS HORA, B.INHCVE AS CLAVE, B.INHMED AS TRATAMIENTO, B.INHNOM AS NOMBRE, COUNT(B.INHCVE) AS TOTAL FROM PACCAT A, INHPAC B WHERE A.PACNEXP=B.PACNEXP AND A.PACNEXP='" + jt_expediente.getText() + "' AND B.INHCVE LIKE '%" + jt_clave.getText().toUpperCase() + "%' AND B.INHFEC = TO_DATE('" + strDateD + ",'dd/MM/yyyy') GROUP BY A.PACNEXP, A.PACNOM, B.INHFEC, B.INHHORA, B.INHCVE, B.INHMED, B.INHNOM  ORDER BY B.INHFEC DESC");

        ResultSetMetaData rsmt = rs.getMetaData();
        int numCol = rsmt.getColumnCount();

        //Creamos el objeto del modelo de la tabla
        DefaultTableModel modelo = new DefaultTableModel();
        this.jt_consulta_inhalos.setModel(modelo);

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

            for (int fila = 0; fila < jt_consulta_inhalos.getRowCount(); fila++) {
                a = String.valueOf(jt_consulta_inhalos.getValueAt(fila, 7));
                b = Integer.valueOf(a);
                total = total + b;
            }

            modelo.addRow(new Object[]{"", "", "", "", "", "", "Total", String.valueOf(total)});

            rs.close();
            tt_rec();
        }
    }

    //Metodo inhalos X Expediente X Clave X Fecha Desde Hasta
    public void inhalosCExpCveDH() throws SQLException {

        String formato = "dd/MM/yyyy";
        Date date;
        date = jd_Desde.getDate();
        SimpleDateFormat sdfD = new SimpleDateFormat(formato);
        String strDateD = sdfD.format(date);

        Date date2 = jd_Hasta.getDate();
        SimpleDateFormat sdfH = new SimpleDateFormat(formato);
        String strDateH = sdfH.format(date2);

        rs = Conexiondb.Consulta("SELECT A.PACNEXP AS EXPEDIENTE, A.PACNOM AS NOMBRE, TO_CHAR(B.INHFEC,'dd/MM/yyyy') AS FECHA, B.INHHORA AS HORA, B.INHCVE AS CLAVE, B.INHMED AS TRATAMIENTO, B.INHNOM AS NOMBRE, COUNT(B.INHCVE) AS TOTAL FROM PACCAT A, INHPAC B WHERE A.PACNEXP=B.PACNEXP AND A.PACNEXP='" + jt_expediente.getText() + "' AND B.INHCVE LIKE '%" + jt_clave.getText().toUpperCase() + "%' AND B.INHFEC BETWEEN TO_DATE('" + strDateD + "', 'dd/MM/yyyy') AND  TO_DATE('" + strDateH + "', 'dd/MM/yyyy') GROUP BY A.PACNEXP, A.PACNOM, B.INHFEC, B.INHHORA, B.INHCVE, B.INHMED, B.INHNOM  ORDER BY B.INHFEC DESC");

        ResultSetMetaData rsmt = rs.getMetaData();
        int numCol = rsmt.getColumnCount();

        //Creamos el objeto del modelo de la tabla
        DefaultTableModel modelo = new DefaultTableModel();
        this.jt_consulta_inhalos.setModel(modelo);

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

            for (int fila = 0; fila < jt_consulta_inhalos.getRowCount(); fila++) {
                a = String.valueOf(jt_consulta_inhalos.getValueAt(fila, 7));
                b = Integer.valueOf(a);
                total = total + b;
            }

            modelo.addRow(new Object[]{"", "", "", "", "", "", "Total", String.valueOf(total)});

            rs.close();
            tt_rec();
        }
    }

    //Metodo inhalos X Expediente X Clave X Fecha Desde
    public void inhalosCFechaD() throws SQLException {

        String formato = "dd/MM/yyyy";
        Date date;
        date = jd_Desde.getDate();
        SimpleDateFormat sdfD = new SimpleDateFormat(formato);
        String strDateD = sdfD.format(date);

        rs = Conexiondb.Consulta("SELECT A.PACNEXP AS EXPEDIENTE, A.PACNOM AS NOMBRE, TO_CHAR(B.INHFEC,'dd/MM/yyyy') AS FECHA, B.INHHORA AS HORA, B.INHCVE AS CLAVE, B.INHMED AS TRATAMIENTO, B.INHNOM AS NOMBRE, COUNT(B.INHCVE) AS TOTAL FROM PACCAT A, INHPAC B WHERE A.PACNEXP=B.PACNEXP AND B.INHFEC = TO_DATE('" + strDateD + "', 'dd/MM/yyyy') GROUP BY A.PACNEXP, A.PACNOM, B.INHFEC, B.INHHORA, B.INHCVE, B.INHMED, B.INHNOM  ORDER BY B.INHFEC DESC");

        ResultSetMetaData rsmt = rs.getMetaData();
        int numCol = rsmt.getColumnCount();

        //Creamos el objeto del modelo de la tabla
        DefaultTableModel modelo = new DefaultTableModel();
        this.jt_consulta_inhalos.setModel(modelo);

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

            for (int fila = 0; fila < jt_consulta_inhalos.getRowCount(); fila++) {
                a = String.valueOf(jt_consulta_inhalos.getValueAt(fila, 7));
                b = Integer.valueOf(a);
                total = total + b;
            }

            modelo.addRow(new Object[]{"", "", "", "", "", "", "Total", String.valueOf(total)});

            rs.close();
            tt_rec();
        }
    }

    //Metodo inhalos X Clave X Fecha Desde
    public void inhalosCveFecD() throws SQLException {

        String formato = "dd/MM/yyyy";
        Date date;
        date = jd_Desde.getDate();
        SimpleDateFormat sdfD = new SimpleDateFormat(formato);
        String strDateD = sdfD.format(date);

        rs = Conexiondb.Consulta("SELECT A.PACNEXP AS EXPEDIENTE, A.PACNOM AS NOMBRE, TO_CHAR(B.INHFEC,'dd/MM/yyyy') AS FECHA, B.INHHORA AS HORA, B.INHCVE AS CLAVE, B.INHMED AS TRATAMIENTO, B.INHNOM AS NOMBRE, COUNT(B.INHCVE) AS TOTAL FROM PACCAT A, INHPAC B WHERE A.PACNEXP=B.PACNEXP AND B.INHCVE LIKE '%" + jt_clave.getText().toUpperCase() + "%' AND B.INHFEC = TO_DATE('" + strDateD + "','dd/MM/yyyy') GROUP BY A.PACNEXP, A.PACNOM, B.INHFEC, B.INHHORA, B.INHCVE, B.INHMED, B.INHNOM  ORDER BY B.INHFEC DESC");

        ResultSetMetaData rsmt = rs.getMetaData();
        int numCol = rsmt.getColumnCount();

        //Creamos el objeto del modelo de la tabla
        DefaultTableModel modelo = new DefaultTableModel();
        this.jt_consulta_inhalos.setModel(modelo);

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

            for (int fila = 0; fila < jt_consulta_inhalos.getRowCount(); fila++) {
                a = String.valueOf(jt_consulta_inhalos.getValueAt(fila, 7));
                b = Integer.valueOf(a);
                total = total + b;
            }

            modelo.addRow(new Object[]{"", "", "", "", "", "", "Total", String.valueOf(total)});

            rs.close();
            tt_rec();
        }
    }

    //Metodo inhalos X Clave X Fecha Desde Hasta
    public void inhalosCveFecDH() throws SQLException {

        String formato = "dd/MM/yyyy";
        Date date;
        date = jd_Desde.getDate();
        SimpleDateFormat sdfD = new SimpleDateFormat(formato);
        String strDateD = sdfD.format(date);

        Date date2 = jd_Hasta.getDate();
        SimpleDateFormat sdfH = new SimpleDateFormat(formato);
        String strDateH = sdfH.format(date2);

        rs = Conexiondb.Consulta("SELECT A.PACNEXP AS EXPEDIENTE, A.PACNOM AS NOMBRE, TO_CHAR(B.INHFEC,'dd/MM/yyyy') AS FECHA, B.INHHORA AS HORA, B.INHCVE AS CLAVE, B.INHMED AS TRATAMIENTO, B.INHNOM AS NOMBRE, COUNT(B.INHCVE) AS TOTAL FROM PACCAT A, INHPAC B WHERE A.PACNEXP=B.PACNEXP AND B.INHCVE LIKE '%" + jt_clave.getText().toUpperCase() + "%' AND B.INHFEC BETWEEN TO_DATE('" + strDateD + "', 'dd/MM/yyyy') AND  TO_DATE('" + strDateH + "', 'dd/MM/yyyy') GROUP BY A.PACNEXP, A.PACNOM, B.INHFEC, B.INHHORA, B.INHCVE, B.INHMED, B.INHNOM  ORDER BY B.INHFEC DESC");

        ResultSetMetaData rsmt = rs.getMetaData();
        int numCol = rsmt.getColumnCount();

        //Creamos el objeto del modelo de la tabla
        DefaultTableModel modelo = new DefaultTableModel();
        this.jt_consulta_inhalos.setModel(modelo);

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

            for (int fila = 0; fila < jt_consulta_inhalos.getRowCount(); fila++) {
                a = String.valueOf(jt_consulta_inhalos.getValueAt(fila, 7));
                b = Integer.valueOf(a);
                total = total + b;
            }

            modelo.addRow(new Object[]{"", "", "", "", "", "", "Total", String.valueOf(total)});

            rs.close();
            tt_rec();
        }
    }

    public void tt_rec() {
        int tt_reg = this.jt_consulta_inhalos.getRowCount();
        int ttRow = tt_reg - 1;
        if (ttRow > 0) {
            this.jl_tt_reg.setText("Total Registros: " + ttRow);
        } else {
            int value = 0;
            this.jl_tt_reg.setText("Total Registros: " + value);
        }
    }

    public void limpiar() {
        DefaultTableModel dTable = new DefaultTableModel();
        this.jt_consulta_inhalos.setModel(dTable);
        while (dTable.getRowCount() > 0) {
            dTable.removeRow(0);
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
        jt_consulta_inhalos = new javax.swing.JTable();
        jl_tt_reg = new javax.swing.JLabel();
        btn_menu_principal = new javax.swing.JButton();
        btn_exportar = new javax.swing.JButton();
        jd_Desde = new com.toedter.calendar.JDateChooser();
        jl_desde = new javax.swing.JLabel();
        jd_Hasta = new com.toedter.calendar.JDateChooser();
        jl_hasta = new javax.swing.JLabel();
        jl_expediente = new javax.swing.JLabel();
        jt_expediente = new javax.swing.JTextField();
        jl_excluir = new javax.swing.JLabel();
        jt_clave = new javax.swing.JTextField();
        btn_limpiar = new javax.swing.JButton();
        btn_reporte = new javax.swing.JButton();
        jl_sub_titulo = new javax.swing.JLabel();
        jp_bar = new javax.swing.JProgressBar();
        jl_etiqueta = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Reporte de Inhaloterapias por fecha / tratamiento / nombre");

        jl_titulo.setBackground(new java.awt.Color(51, 102, 255));
        jl_titulo.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jl_titulo.setForeground(new java.awt.Color(255, 255, 255));
        jl_titulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jl_titulo.setText("REPORTES DE INHALOTERAPIAS");
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

        jt_consulta_inhalos = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex){
                return false;
            }
        };
        jt_consulta_inhalos.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(jt_consulta_inhalos);

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

        jl_expediente.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jl_expediente.setText("Expediente: ");

        jt_expediente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jt_expedienteKeyPressed(evt);
            }
        });

        jl_excluir.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jl_excluir.setText("Clave");

        jt_clave.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jt_claveKeyPressed(evt);
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

        jl_sub_titulo.setBackground(new java.awt.Color(51, 102, 255));
        jl_sub_titulo.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jl_sub_titulo.setForeground(new java.awt.Color(255, 255, 255));
        jl_sub_titulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jl_sub_titulo.setText("POR FECHA / TRATAMIENTO / NOMBRE");
        jl_sub_titulo.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(0, 51, 204)));

        jl_etiqueta.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jl_etiqueta.setText("Procesando....");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jl_titulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jl_desde, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jd_Desde, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(33, 33, 33)
                                .addComponent(jl_hasta, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jd_Hasta, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jl_expediente)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jt_expediente, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jl_excluir)
                        .addGap(18, 18, 18)
                        .addComponent(jt_clave, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(btnConsulta, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_imprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn_exportar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_reporte)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_limpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn_menu_principal)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(btn_salir, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jl_tt_reg, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(28, Short.MAX_VALUE))
            .addComponent(jl_sub_titulo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addGap(15, 15, 15))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jl_etiqueta)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jp_bar, javax.swing.GroupLayout.PREFERRED_SIZE, 756, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jl_titulo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jl_sub_titulo)
                .addGap(36, 36, 36)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jl_expediente)
                    .addComponent(jt_expediente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jl_excluir)
                    .addComponent(jt_clave, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_salir, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 329, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jp_bar, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jl_etiqueta, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_salirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_salirActionPerformed
        System.exit(0);
    }//GEN-LAST:event_btn_salirActionPerformed

    private void btnConsultaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultaActionPerformed
        if (jt_expediente.getText().isEmpty() && jt_clave.getText().isEmpty() && jd_Desde.getDate() == null && jd_Hasta.getDate() == null) {
            task01Inf = new PBinhalosC();
            task01Inf.execute();
        } else if (jt_expediente.getText().length() > 0 && jt_clave.getText().isEmpty() && jd_Desde.getDate() == null && jd_Hasta.getDate() == null) {
            task02Inf = new PBinhalosCExp();
            task02Inf.execute();
        } else if (jt_expediente.getText().isEmpty() && jt_clave.getText().length() > 0 && jd_Desde.getDate() == null && jd_Hasta.getDate() == null) {
            task05Inf = new PBinhalosCInc();
            task05Inf.execute();
        } else if (jt_expediente.getText().isEmpty() && jt_clave.getText().isEmpty() && jd_Desde.getDate() != null && jd_Hasta.getDate() != null) {
            task04Inf = new PBinhalosCFecha();
            task04Inf.execute();
        } else if (jt_expediente.getText().length() > 0 && jt_clave.getText().length() > 0 && jd_Desde.getDate() == null && jd_Hasta.getDate() == null) {
            task06Inf = new PBinhalosCExpCve();
            task06Inf.execute();            
        } else if (jt_expediente.getText().length() > 0 && jt_clave.getText().length() > 0 && jd_Desde.getDate() != null && jd_Hasta.getDate() == null) {
            task07Inf = new PBinhalosCExpCveD();
            task07Inf.execute();
        }else if (jt_expediente.getText().isEmpty() && jt_clave.getText().isEmpty() && jd_Desde.getDate() != null && jd_Hasta.getDate() == null) {
                task09Inf = new PBinhalosCFechaD();
                task09Inf.execute();
        }else if (jt_expediente.getText().length() > 0 && jt_clave.getText().isEmpty() && jd_Desde.getDate() != null && jd_Hasta.getDate() != null) {
                task03Inf = new PBinhalosCExpFechDH();
                task03Inf.execute();
        }else if (jt_expediente.getText().length() > 0 && jt_clave.getText().length() > 0 && jd_Desde.getDate() != null && jd_Hasta.getDate() != null) {
                task08Inf = new PBinhalosCExpCveDH();
                task08Inf.execute();
        }else if (jt_expediente.getText().isEmpty() && jt_clave.getText().length() > 0 && jd_Desde.getDate() != null && jd_Hasta.getDate() == null) {
                task10Inf = new PBinhalosCveFecD();
                task10Inf.execute();
        }else if (jt_expediente.getText().isEmpty() && jt_clave.getText().length() > 0 && jd_Desde.getDate() != null && jd_Hasta.getDate() != null) {
                task11Inf = new PBinhalosCveFecDH();
                task11Inf.execute();
        } else if (jt_expediente.getText().length() > 0 && jt_clave.getText().length() > 0 && jd_Desde.getDate() == null && jd_Hasta.getDate() != null) {
            JOptionPane.showMessageDialog(null, "El campo fecha Desde no puede estar vacio, ingresar nuevamente ambas fechas o solo Desde");
        } else if (jt_expediente.getText().isEmpty() && jt_clave.getText().isEmpty() && jd_Desde.getDate() == null && jd_Hasta.getDate() != null) {
            JOptionPane.showMessageDialog(null, "El campo fecha Desde no puede estar vacio, ingresar nuevamente ambas fechas o solo Desde");
        }
    }//GEN-LAST:event_btnConsultaActionPerformed

    private void jt_expedienteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jt_expedienteKeyPressed

        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (jt_expediente.getText().isEmpty() && jt_clave.getText().isEmpty() && jd_Desde.getDate() == null && jd_Hasta.getDate() == null) {
                task01Inf = new PBinhalosC();
                task01Inf.execute();

            } else if (jt_expediente.getText().length() > 0 && jt_clave.getText().isEmpty() && jd_Desde.getDate() == null && jd_Hasta.getDate() == null) {
                task02Inf = new PBinhalosCExp();
                task02Inf.execute();
    
            } else if (jt_expediente.getText().isEmpty() && jt_clave.getText().length() > 0 && jd_Desde.getDate() == null && jd_Hasta.getDate() == null) {
                task05Inf = new PBinhalosCInc();
                task05Inf.execute();

            } else if (jt_expediente.getText().length() > 0 && jt_clave.getText().length() > 0 && jd_Desde.getDate() == null && jd_Hasta.getDate() == null) {
                task06Inf = new PBinhalosCExpCve();
                task06Inf.execute();
                
            } else if (jt_expediente.getText().length() > 0 && jt_clave.getText().length() > 0 && jd_Desde.getDate() != null && jd_Hasta.getDate() == null) {
                task07Inf = new PBinhalosCExpCveD();
                task07Inf.execute();
                    
            } else if (jt_expediente.getText().length() > 0 && jt_clave.getText().isEmpty() && jd_Desde.getDate() != null && jd_Hasta.getDate() != null) {
                task03Inf = new PBinhalosCExpFechDH();
                task03Inf.execute();

            } else if (jt_expediente.getText().length() > 0 && jt_clave.getText().length() > 0 && jd_Desde.getDate() != null && jd_Hasta.getDate() != null) {
                task08Inf = new PBinhalosCExpCveDH();
                task08Inf.execute();
                
            } else if (jt_expediente.getText().isEmpty() && jt_clave.getText().length() > 0 && jd_Desde.getDate() != null && jd_Hasta.getDate() == null) {
                task10Inf = new PBinhalosCveFecD();
                task10Inf.execute();
                
            } else if (jt_expediente.getText().isEmpty() && jt_clave.getText().length() > 0 && jd_Desde.getDate() != null && jd_Hasta.getDate() != null) {
                    task11Inf = new PBinhalosCveFecDH();
                    task11Inf.execute();

            } else if (jt_expediente.getText().isEmpty() && jt_clave.getText().isEmpty() && jd_Desde.getDate() != null && jd_Hasta.getDate() == null) {
                    task09Inf = new PBinhalosCFechaD();
                    task09Inf.execute();

            } else if (jt_expediente.getText().isEmpty() && jt_clave.getText().isEmpty() && jd_Desde.getDate() != null && jd_Hasta.getDate() != null) {
                    task04Inf = new PBinhalosCFecha();
                    task04Inf.execute();

            } else if (jt_expediente.getText().length() > 0 && jt_clave.getText().length() > 0 && jd_Desde.getDate() == null && jd_Hasta.getDate() != null) {
                JOptionPane.showMessageDialog(null, "El campo fecha Desde no puede estar vacio, ingresar nuevamente ambas fechas o solo Desde");
            } else if (jt_expediente.getText().isEmpty() && jt_clave.getText().isEmpty() && jd_Desde.getDate() == null && jd_Hasta.getDate() != null) {
                JOptionPane.showMessageDialog(null, "El campo fecha Desde no puede estar vacio, ingresar nuevamente ambas fechas o solo Desde");
            }
        }
    }//GEN-LAST:event_jt_expedienteKeyPressed

    private void jt_claveKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jt_claveKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (jt_expediente.getText().isEmpty() && jt_clave.getText().isEmpty() && jd_Desde.getDate() == null && jd_Hasta.getDate() == null) {
                task01Inf = new PBinhalosC();
                task01Inf.execute();

            } else if (jt_expediente.getText().length() > 0 && jt_clave.getText().isEmpty() && jd_Desde.getDate() == null && jd_Hasta.getDate() == null) {
                task02Inf = new PBinhalosCExp();
                task02Inf.execute();

            } else if (jt_expediente.getText().isEmpty() && jt_clave.getText().length() > 0 && jd_Desde.getDate() == null && jd_Hasta.getDate() == null) {
                task05Inf = new PBinhalosCInc();
                task05Inf.execute();

            } else if (jt_expediente.getText().length() > 0 && jt_clave.getText().length() > 0 && jd_Desde.getDate() == null && jd_Hasta.getDate() == null) {
                task06Inf = new PBinhalosCExpCve();
                task06Inf.execute();

            } else if (jt_expediente.getText().length() > 0 && jt_clave.getText().length() > 0 && jd_Desde.getDate() != null && jd_Hasta.getDate() == null) {
                task07Inf = new PBinhalosCExpCveD();
                task07Inf.execute();

            } else if (jt_expediente.getText().length() > 0 && jt_clave.getText().isEmpty() && jd_Desde.getDate() != null && jd_Hasta.getDate() != null) {
                task03Inf = new PBinhalosCExpFechDH();
                task03Inf.execute();

            } else if (jt_expediente.getText().length() > 0 && jt_clave.getText().length() > 0 && jd_Desde.getDate() != null && jd_Hasta.getDate() != null) {
                task08Inf = new PBinhalosCExpCveDH();
                task08Inf.execute();

            } else if (jt_expediente.getText().isEmpty() && jt_clave.getText().length() > 0 && jd_Desde.getDate() != null && jd_Hasta.getDate() == null) {
                task10Inf = new PBinhalosCveFecD();
                task10Inf.execute();
                
            } else if (jt_expediente.getText().isEmpty() && jt_clave.getText().length() > 0 && jd_Desde.getDate() != null && jd_Hasta.getDate() != null) {
                task11Inf = new PBinhalosCveFecDH();
                task11Inf.execute();

            } else if (jt_expediente.getText().isEmpty() && jt_clave.getText().isEmpty() && jd_Desde.getDate() != null && jd_Hasta.getDate() == null) {
                task09Inf = new PBinhalosCFechaD();
                task09Inf.execute();
                
            } else if (jt_expediente.getText().isEmpty() && jt_clave.getText().isEmpty() && jd_Desde.getDate() != null && jd_Hasta.getDate() != null) {
                task04Inf = new PBinhalosCFecha();
                task04Inf.execute();
                
            } else if (jt_expediente.getText().length() > 0 && jt_clave.getText().length() > 0 && jd_Desde.getDate() == null && jd_Hasta.getDate() != null) {
                JOptionPane.showMessageDialog(null, "El campo fecha Desde no puede estar vacio, ingresar nuevamente ambas fechas o solo Desde");
            } else if (jt_expediente.getText().isEmpty() && jt_clave.getText().isEmpty() && jd_Desde.getDate() == null && jd_Hasta.getDate() != null) {
                JOptionPane.showMessageDialog(null, "El campo fecha Desde no puede estar vacio, ingresar nuevamente ambas fechas o solo Desde");
            }
        }
    }//GEN-LAST:event_jt_claveKeyPressed

    private void btn_imprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_imprimirActionPerformed
        try {
            MessageFormat headerformat = new MessageFormat("Reporte Inhaloterapias"); //encabezado
            MessageFormat footerformat = new MessageFormat("Page - {0}"); //
            jt_consulta_inhalos.print(JTable.PrintMode.FIT_WIDTH, headerformat, footerformat);  //imprime la JTable
        } catch (PrinterException ex) {
            Logger.getLogger(jfInhalos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btn_imprimirActionPerformed

    private void btn_menu_principalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_menu_principalActionPerformed
        dispose();
        new MenuPrincipal().setVisible(true);
    }//GEN-LAST:event_btn_menu_principalActionPerformed

    private void btn_exportarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_exportarActionPerformed
        if (this.jt_consulta_inhalos.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "No hay datos por exportar", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        //Creacion del archivo en Excel
        int totalR = jt_consulta_inhalos.getRowCount();
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
            tb.add(jt_consulta_inhalos);
            nom.add("Reporte Inhaloterapias");
            String file = chooser.getSelectedFile().toString().concat(".xls");
            //Aqui se ejecuta el metodo
            try {
                Exporter e = new Exporter(new File(file), tb, nom);
                if (e.export()) {
                    JOptionPane.showMessageDialog(null, "Total de Registros exportados a Excel: " + totalR, "Reporte Inhaloterapias", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Hubo un error al exportar" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btn_exportarActionPerformed

    private void btn_limpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_limpiarActionPerformed
        jt_clave.setText("");
        jt_expediente.setText("");
        jd_Desde.setDate(null);
        jd_Hasta.setDate(null);
        DefaultTableModel dTable = new DefaultTableModel();
        this.jt_consulta_inhalos.setModel(dTable);
        while (dTable.getRowCount() > 0) {
            dTable.removeRow(0);
        }
        this.jl_tt_reg.setText("Total Registros: ");
    }//GEN-LAST:event_btn_limpiarActionPerformed

    private void btn_reporteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_reporteActionPerformed
        if (this.jt_consulta_inhalos.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Ningun Dato por Exportar a PDF", "Mensage", JOptionPane.INFORMATION_MESSAGE);
            return;
        } else {
            int totalR = jt_consulta_inhalos.getRowCount();
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
                tb.add(jt_consulta_inhalos);
                nom.add("Inhalos");
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
            java.util.logging.Logger.getLogger(jfInhalos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(jfInhalos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(jfInhalos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(jfInhalos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                new jfInhalos().setVisible(true);
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
    private javax.swing.JLabel jl_expediente;
    private javax.swing.JLabel jl_hasta;
    private javax.swing.JLabel jl_sub_titulo;
    private javax.swing.JLabel jl_titulo;
    private javax.swing.JLabel jl_tt_reg;
    private javax.swing.JProgressBar jp_bar;
    private javax.swing.JTextField jt_clave;
    private javax.swing.JTable jt_consulta_inhalos;
    private javax.swing.JTextField jt_expediente;
    // End of variables declaration//GEN-END:variables

    class PBinhalosC extends SwingWorker<Void, Void> {

        @Override
        protected Void doInBackground() throws Exception {
            jp_bar.setVisible(true);
            jp_bar.setIndeterminate(true);
            jl_etiqueta.setVisible(true);
            inhalosC();
            return null;
        }

        @Override
        protected void done() {
            jp_bar.setVisible(false);
            jl_etiqueta.setVisible(false);
        }

    }
    
    class PBinhalosCExp extends SwingWorker<Void, Void> {

        @Override
        protected Void doInBackground() throws Exception {
            jp_bar.setVisible(true);
            jp_bar.setIndeterminate(true);
            jl_etiqueta.setVisible(true);
            inhalosCExp();
            return null;
        }

        @Override
        protected void done() {
            jp_bar.setVisible(false);
            jl_etiqueta.setVisible(false);
        }

    }
    
    class PBinhalosCExpFechDH extends SwingWorker<Void, Void> {

        @Override
        protected Void doInBackground() throws Exception {
            jp_bar.setVisible(true);
            jp_bar.setIndeterminate(true);
            jl_etiqueta.setVisible(true);
            inhalosCExpFechDH();
            return null;
        }

        @Override
        protected void done() {
            jp_bar.setVisible(false);
            jl_etiqueta.setVisible(false);
        }

    }
    
    class PBinhalosCFecha extends SwingWorker<Void, Void> {

        @Override
        protected Void doInBackground() throws Exception {
            jp_bar.setVisible(true);
            jp_bar.setIndeterminate(true);
            jl_etiqueta.setVisible(true);
            inhalosCFecha();
            return null;
        }

        @Override
        protected void done() {
            jp_bar.setVisible(false);
            jl_etiqueta.setVisible(false);
        }

    }
    
    class PBinhalosCInc extends SwingWorker<Void, Void> {

        @Override
        protected Void doInBackground() throws Exception {
            jp_bar.setVisible(true);
            jp_bar.setIndeterminate(true);
            jl_etiqueta.setVisible(true);
            inhalosCInc();
            return null;
        }

        @Override
        protected void done() {
            jp_bar.setVisible(false);
            jl_etiqueta.setVisible(false);
        }

    }
    
    class PBinhalosCExpCve extends SwingWorker<Void, Void> {

        @Override
        protected Void doInBackground() throws Exception {
            jp_bar.setVisible(true);
            jp_bar.setIndeterminate(true);
            jl_etiqueta.setVisible(true);
            inhalosCExpCve();
            return null;
        }

        @Override
        protected void done() {
            jp_bar.setVisible(false);
            jl_etiqueta.setVisible(false);
        }

    }
    
    class PBinhalosCExpCveD extends SwingWorker<Void, Void> {

        @Override
        protected Void doInBackground() throws Exception {
            jp_bar.setVisible(true);
            jp_bar.setIndeterminate(true);
            jl_etiqueta.setVisible(true);
            inhalosCExpCveD();
            return null;
        }

        @Override
        protected void done() {
            jp_bar.setVisible(false);
            jl_etiqueta.setVisible(false);
        }

    }
    
    class PBinhalosCExpCveDH extends SwingWorker<Void, Void> {

        @Override
        protected Void doInBackground() throws Exception {
            jp_bar.setVisible(true);
            jp_bar.setIndeterminate(true);
            jl_etiqueta.setVisible(true);
            inhalosCExpCveDH();
            return null;
        }

        @Override
        protected void done() {
            jp_bar.setVisible(false);
            jl_etiqueta.setVisible(false);
        }

    }
    
    class PBinhalosCFechaD extends SwingWorker<Void, Void> {

        @Override
        protected Void doInBackground() throws Exception {
            jp_bar.setVisible(true);
            jp_bar.setIndeterminate(true);
            jl_etiqueta.setVisible(true);
            inhalosCFechaD();
            return null;
        }

        @Override
        protected void done() {
            jp_bar.setVisible(false);
            jl_etiqueta.setVisible(false);
        }

    }
    
    class PBinhalosCveFecD extends SwingWorker<Void, Void> {

        @Override
        protected Void doInBackground() throws Exception {
            jp_bar.setVisible(true);
            jp_bar.setIndeterminate(true);
            jl_etiqueta.setVisible(true);
            inhalosCveFecD();
            return null;
        }

        @Override
        protected void done() {
            jp_bar.setVisible(false);
            jl_etiqueta.setVisible(false);
        }

    }

    class PBinhalosCveFecDH extends SwingWorker<Void, Void> {

        @Override
        protected Void doInBackground() throws Exception {
            jp_bar.setVisible(true);
            jp_bar.setIndeterminate(true);
            jl_etiqueta.setVisible(true);
            inhalosCveFecDH();
            return null;
        }

        @Override
        protected void done() {
            jp_bar.setVisible(false);
            jl_etiqueta.setVisible(false);
        }

    }

}
