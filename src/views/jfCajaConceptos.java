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
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;
import javax.swing.SwingWorker;
import javax.swing.UIManager;



/**
 * @author Jessica Viviana Pastrana Munguia
 */
public class jfCajaConceptos extends javax.swing.JFrame {
ResultSet rs;
private PBcjaConFecDH task01Inf;
private PBcjaConFecD task02Inf;
private PBcjaConExpFecDH task03Inf;
private PBcjaConExpFecD task04Inf;
private PBcjaConExp task05Inf;
private PBcjaPagadoDH task06Inf;
private PBcjaPagadoD task07Inf;
private PBcjaNoPagoDH task08Inf;
private PBcjaNoPagoD task09Inf;
String pattern= "###.00";
Locale locale = new Locale("en","US");
DecimalFormat df2 = (DecimalFormat)NumberFormat.getNumberInstance(Locale.US);


    /**
     * Creates new form jfVacunas
     */
    public jfCajaConceptos() {
        initComponents();
        setLocationRelativeTo(null);
        //setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(false);
        jl_titulo.setHorizontalAlignment(JLabel.CENTER);
        jl_titulo.setForeground(Color.white);
        jl_titulo.setOpaque(true);
        jl_etiqueta.setVisible(false);
        jp_bar.setVisible(false);
        jr_btn_todos.setSelected(true);
        jl_expediente.setVisible(false);
        jt_expediente.setVisible(false);
    }
    
    public void cjaConFecDH() throws SQLException {

        String formato = "dd/MM/yyyy";
        Date date;
        date = jd_Desde.getDate();
        SimpleDateFormat sdfD = new SimpleDateFormat(formato);
        String strDateD = sdfD.format(date);

        Date date2 = jd_Hasta.getDate();
        SimpleDateFormat sdfH = new SimpleDateFormat(formato);
        String strDateH = sdfH.format(date2);

        rs = Conexiondb.Consulta("SELECT A.PACNEXP AS EXPEDIENTE, A.PACNOM AS NOMBRE, B.PRECVE AS CLAVE, TO_CHAR(B.CAJTOTC, '9999.99') AS CAJTOTC, B.CAJNUM AS CAJNUM, C.CAJFOL AS CAJFOL, TO_CHAR(B.CAJFEC,'dd/MM/yyyy') AS FECHA, SUM(B.CAJCANT) AS TOTAL_DE_TRATAMIENTOS, C.USUCVE AS USUCVE FROM PACCAT A, CAJDET B, CAJENC C WHERE A.PACNEXP=B.PACNEXP AND A.PACNEXP = C.PACNEXP AND B.PACNEXP=C.PACNEXP AND B.CAJNUM = C.CAJNUM AND B.CAJFEC BETWEEN TO_DATE('"+ strDateD +"','dd/MM/yyyy') AND TO_DATE('" + strDateH + "','dd/MM/yyyy') AND C.CAJFEC BETWEEN TO_DATE('"+ strDateD +"','dd/MM/yyyy') AND TO_DATE('" + strDateH + "','dd/MM/yyyy') AND B.CAJFEC = C.CAJFEC GROUP BY A.PACNEXP, A.PACNOM, B.PRECVE, B.CAJTOTC, B.CAJNUM, B.CAJFEC, C.CAJFOL, C.USUCVE UNION SELECT A.PACNEXP AS EXPEDIENTE, A.PACNOM AS NOMBRE, B.PRECVE AS CLAVE, TO_CHAR(B.CAJTOTC, '9999.99') AS CAJTOTC, B.CAJNUM AS CAJNUM, TO_NUMBER(0) AS CAJFOL, TO_CHAR(B.CAJFEC,'dd/MM/yyyy') AS FECHA, SUM(B.CAJCANT) AS TOTAL_DE_TRATAMIENTOS, TO_CHAR('') AS USUCVE FROM PACCAT A, CAJDET B WHERE A.PACNEXP=B.PACNEXP AND B.CAJFEC BETWEEN TO_DATE('"+ strDateD +"','dd/MM/yyyy') AND TO_DATE('" + strDateH + "','dd/MM/yyyy') AND A.PACNEXP NOT IN (SELECT PACNEXP FROM CAJENC WHERE CAJFEC BETWEEN TO_DATE('"+ strDateD +"','dd/MM/yyyy') AND TO_DATE('" + strDateH + "','dd/MM/yyyy')) GROUP BY A.PACNEXP, A.PACNOM, B.PRECVE, B.CAJTOTC, B.CAJNUM, B.CAJFEC ORDER BY EXPEDIENTE, CAJNUM");

        ResultSetMetaData rsmt = rs.getMetaData();
        int numCol = rsmt.getColumnCount();

        //Creamos el objeto del modelo de la tabla
        DefaultTableModel modelo = new DefaultTableModel();
        this.jt_consulta_conceptos.setModel(modelo);

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

    public void cjaConFecD() throws SQLException {

        String formato = "dd/MM/yyyy";
        Date date;
        date = jd_Desde.getDate();
        SimpleDateFormat sdfD = new SimpleDateFormat(formato);
        String strDateD = sdfD.format(date);

        rs = Conexiondb.Consulta("SELECT A.PACNEXP AS EXPEDIENTE, A.PACNOM AS NOMBRE, B.PRECVE AS CLAVE, TO_CHAR(B.CAJTOTC, '9999.99') AS CAJTOTC, B.CAJNUM AS CAJNUM, C.CAJFOL AS CAJFOL, TO_CHAR(B.CAJFEC,'dd/MM/yyyy') AS FECHA, SUM(B.CAJCANT) AS TOTAL_DE_TRATAMIENTOS, C.USUCVE AS USUCVE FROM PACCAT A, CAJDET B, CAJENC C WHERE A.PACNEXP=B.PACNEXP AND A.PACNEXP = C.PACNEXP AND B.PACNEXP=C.PACNEXP AND B.CAJNUM = C.CAJNUM AND B.CAJFEC = TO_DATE('"+ strDateD +"','dd/MM/yyyy') AND C.CAJFEC = TO_DATE('"+ strDateD +"','dd/MM/yyyy') AND B.CAJFEC = C.CAJFEC GROUP BY A.PACNEXP, A.PACNOM, B.PRECVE, B.CAJTOTC, B.CAJNUM, B.CAJFEC, C.CAJFOL, C.USUCVE UNION SELECT A.PACNEXP AS EXPEDIENTE, A.PACNOM AS NOMBRE, B.PRECVE AS CLAVE, TO_CHAR(B.CAJTOTC, '9999.99') AS CAJTOTC, B.CAJNUM AS CAJNUM, TO_NUMBER(0) AS CAJFOL, TO_CHAR(B.CAJFEC,'dd/MM/yyyy') AS FECHA, SUM(B.CAJCANT) AS TOTAL_DE_TRATAMIENTOS, TO_CHAR('') AS USUCVE FROM PACCAT A, CAJDET B WHERE A.PACNEXP=B.PACNEXP AND B.CAJFEC = TO_DATE('"+ strDateD +"','dd/MM/yyyy') AND A.PACNEXP NOT IN (SELECT PACNEXP FROM CAJENC WHERE CAJFEC = TO_DATE('"+ strDateD +"','dd/MM/yyyy')) GROUP BY A.PACNEXP, A.PACNOM, B.PRECVE, B.CAJTOTC, B.CAJNUM, B.CAJFEC ORDER BY EXPEDIENTE, CAJNUM");

        ResultSetMetaData rsmt = rs.getMetaData();
        int numCol = rsmt.getColumnCount();

        //Creamos el objeto del modelo de la tabla
        DefaultTableModel modelo = new DefaultTableModel();
        this.jt_consulta_conceptos.setModel(modelo);

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

    public void cjaConExpFecDH() throws SQLException {

        String formato = "dd/MM/yyyy";
        Date date;
        date = jd_Desde.getDate();
        SimpleDateFormat sdfD = new SimpleDateFormat(formato);
        String strDateD = sdfD.format(date);

        Date date2 = jd_Hasta.getDate();
        SimpleDateFormat sdfH = new SimpleDateFormat(formato);
        String strDateH = sdfH.format(date2);

        rs = Conexiondb.Consulta("SELECT A.PACNEXP AS EXPEDIENTE, A.PACNOM AS NOMBRE, B.PRECVE AS CLAVE, TO_CHAR(B.CAJTOTC, '9999.99') AS CAJTOTC, B.CAJNUM AS CAJNUM, C.CAJFOL AS CAJFOL, TO_CHAR(B.CAJFEC,'dd/MM/yyyy') AS FECHA, SUM(B.CAJCANT) AS TOTAL_DE_TRATAMIENTOS, C.USUCVE AS USUCVE FROM PACCAT A, CAJDET B, CAJENC C WHERE A.PACNEXP=B.PACNEXP AND A.PACNEXP = C.PACNEXP AND B.PACNEXP=C.PACNEXP AND B.CAJNUM = C.CAJNUM AND B.CAJFEC BETWEEN TO_DATE('"+ strDateD +"','dd/MM/yyyy') AND TO_DATE('" + strDateH + "','dd/MM/yyyy') AND C.CAJFEC BETWEEN TO_DATE('"+ strDateD +"','dd/MM/yyyy') AND TO_DATE('" + strDateH + "','dd/MM/yyyy') AND B.CAJFEC = C.CAJFEC AND A.PACNEXP='"+jt_expediente.getText()+"' GROUP BY A.PACNEXP, A.PACNOM, B.PRECVE, B.CAJTOTC, B.CAJNUM, B.CAJFEC, C.CAJFOL, C.USUCVE UNION SELECT A.PACNEXP AS EXPEDIENTE, A.PACNOM AS NOMBRE, B.PRECVE AS CLAVE, TO_CHAR(B.CAJTOTC, '9999.99') AS CAJTOTC, B.CAJNUM AS CAJNUM, TO_NUMBER(0) AS CAJFOL, TO_CHAR(B.CAJFEC,'dd/MM/yyyy') AS FECHA, SUM(B.CAJCANT) AS TOTAL_DE_TRATAMIENTOS, TO_CHAR('') AS USUCVE FROM PACCAT A, CAJDET B WHERE A.PACNEXP=B.PACNEXP AND B.CAJFEC BETWEEN TO_DATE('"+ strDateD +"','dd/MM/yyyy') AND TO_DATE('" + strDateH + "','dd/MM/yyyy') AND A.PACNEXP NOT IN (SELECT PACNEXP FROM CAJENC WHERE CAJFEC BETWEEN TO_DATE('"+ strDateD +"','dd/MM/yyyy') AND TO_DATE('" + strDateH + "','dd/MM/yyyy')) AND A.PACNEXP='"+jt_expediente.getText()+"' GROUP BY A.PACNEXP, A.PACNOM, B.PRECVE, B.CAJTOTC, B.CAJNUM, B.CAJFEC ORDER BY EXPEDIENTE, CAJNUM");
 
        ResultSetMetaData rsmt = rs.getMetaData();
        int numCol = rsmt.getColumnCount();

        //Creamos el objeto del modelo de la tabla
        DefaultTableModel modelo = new DefaultTableModel();
        this.jt_consulta_conceptos.setModel(modelo);

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

    public void cjaConExpFecD() throws SQLException {

        String formato = "dd/MM/yyyy";
        Date date;
        date = jd_Desde.getDate();
        SimpleDateFormat sdfD = new SimpleDateFormat(formato);
        String strDateD = sdfD.format(date);

        rs = Conexiondb.Consulta("SELECT A.PACNEXP AS EXPEDIENTE, A.PACNOM AS NOMBRE, B.PRECVE AS CLAVE, TO_CHAR(B.CAJTOTC, '9999.99') AS CAJTOTC, B.CAJNUM AS CAJNUM, C.CAJFOL AS CAJFOL, TO_CHAR(B.CAJFEC,'dd/MM/yyyy') AS FECHA, SUM(B.CAJCANT) AS TOTAL_DE_TRATAMIENTOS, C.USUCVE AS USUCVE FROM PACCAT A, CAJDET B, CAJENC C WHERE A.PACNEXP=B.PACNEXP AND A.PACNEXP = C.PACNEXP AND B.PACNEXP=C.PACNEXP AND B.CAJNUM = C.CAJNUM AND B.CAJFEC = TO_DATE('"+ strDateD +"','dd/MM/yyyy') AND C.CAJFEC = TO_DATE('"+ strDateD +"','dd/MM/yyyy') AND B.CAJFEC = C.CAJFEC AND A.PACNEXP='"+jt_expediente.getText()+"' GROUP BY A.PACNEXP, A.PACNOM, B.PRECVE, B.CAJTOTC, B.CAJNUM, B.CAJFEC, C.CAJFOL, C.USUCVE UNION SELECT A.PACNEXP AS EXPEDIENTE, A.PACNOM AS NOMBRE, B.PRECVE AS CLAVE, TO_CHAR(B.CAJTOTC, '9999.99') AS CAJTOTC, B.CAJNUM AS CAJNUM, TO_NUMBER(0) AS CAJFOL, TO_CHAR(B.CAJFEC,'dd/MM/yyyy') AS FECHA, SUM(B.CAJCANT) AS TOTAL_DE_TRATAMIENTOS, TO_CHAR('') AS USUCVE FROM PACCAT A, CAJDET B WHERE A.PACNEXP=B.PACNEXP AND B.CAJFEC = TO_DATE('"+ strDateD +"','dd/MM/yyyy') AND A.PACNEXP NOT IN (SELECT PACNEXP FROM CAJENC WHERE CAJFEC = TO_DATE('"+ strDateD +"','dd/MM/yyyy')) AND A.PACNEXP='"+jt_expediente.getText()+"' GROUP BY A.PACNEXP, A.PACNOM, B.PRECVE, B.CAJTOTC, B.CAJNUM, B.CAJFEC ORDER BY EXPEDIENTE, CAJNUM");
 
        ResultSetMetaData rsmt = rs.getMetaData();
        int numCol = rsmt.getColumnCount();

        //Creamos el objeto del modelo de la tabla
        DefaultTableModel modelo = new DefaultTableModel();
        this.jt_consulta_conceptos.setModel(modelo);

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
    
    public void cjaConExp() throws SQLException {

        rs = Conexiondb.Consulta("SELECT A.PACNEXP AS EXPEDIENTE, A.PACNOM AS NOMBRE, B.PRECVE AS CLAVE, TO_CHAR(B.CAJTOTC, '9999.99') AS CAJTOTC, B.CAJNUM AS CAJNUM, C.CAJFOL AS CAJFOL, B.CAJFEC AS FECHA, SUM(B.CAJCANT) AS TOTAL_DE_TRATAMIENTOS, C.USUCVE AS USUCVE FROM PACCAT A, CAJDET B, CAJENC C WHERE A.PACNEXP=B.PACNEXP AND A.PACNEXP = C.PACNEXP AND B.PACNEXP=C.PACNEXP AND B.CAJNUM = C.CAJNUM AND B.CAJFEC = C.CAJFEC AND A.PACNEXP='"+jt_expediente.getText()+"' GROUP BY A.PACNEXP, A.PACNOM, B.PRECVE, B.CAJTOTC, B.CAJNUM, B.CAJFEC, C.CAJFOL, C.USUCVE UNION SELECT A.PACNEXP AS EXPEDIENTE, A.PACNOM AS NOMBRE, B.PRECVE AS CLAVE, TO_CHAR(B.CAJTOTC, '9999.99') AS CAJTOTC, B.CAJNUM AS CAJNUM, TO_NUMBER(0) AS CAJFOL, B.CAJFEC AS FECHA, SUM(B.CAJCANT) AS TOTAL_DE_TRATAMIENTOS, TO_CHAR('') AS USUCVE FROM PACCAT A, CAJDET B WHERE A.PACNEXP = B.PACNEXP AND B.CAJFEC NOT IN (SELECT D.CAJFEC FROM CAJDET C, CAJENC D WHERE C.PACNEXP = '"+jt_expediente.getText()+"' AND C.PACNEXP = D.PACNEXP AND C.CAJFEC = D.CAJFEC) AND A.PACNEXP = '"+jt_expediente.getText()+"' GROUP BY A.PACNEXP, A.PACNOM, B.PRECVE, B.CAJTOTC, B.CAJNUM, B.CAJFEC ORDER BY EXPEDIENTE, CAJNUM, FECHA DESC");

        ResultSetMetaData rsmt = rs.getMetaData();
        int numCol = rsmt.getColumnCount();

        //Creamos el objeto del modelo de la tabla
        DefaultTableModel modelo = new DefaultTableModel();
        this.jt_consulta_conceptos.setModel(modelo);

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

    public void cjaPagadoDH() throws SQLException {

        String formato = "dd/MM/yyyy";
        Date date;
        date = jd_Desde.getDate();
        SimpleDateFormat sdfD = new SimpleDateFormat(formato);
        String strDateD = sdfD.format(date);

        Date date2 = jd_Hasta.getDate();
        SimpleDateFormat sdfH = new SimpleDateFormat(formato);
        String strDateH = sdfH.format(date2);

        rs = Conexiondb.Consulta("SELECT A.PACNEXP AS EXPEDIENTE, A.PACNOM AS NOMBRE, B.PRECVE AS CLAVE, TO_CHAR(B.CAJTOTC, '9999.99') AS CAJTOTC, B.CAJNUM AS CAJNUM, C.CAJFOL, TO_CHAR(B.CAJFEC,'dd/MM/yyyy') AS FECHA, SUM (CAJCANT) AS TOTAL_DE_TRATAMIENTOS, C.USUCVE FROM PACCAT A, CAJDET B, CAJENC C WHERE A.PACNEXP=B.PACNEXP AND A.PACNEXP = C.PACNEXP AND B.PACNEXP=C.PACNEXP AND B.CAJNUM = C.CAJNUM AND B.CAJFEC = C.CAJFEC AND B.CAJFEC BETWEEN TO_DATE('"+strDateD+"', 'dd/MM/yyyy') AND TO_DATE('"+strDateH+"', 'dd/MM/yyyy') AND C.CAJFEC BETWEEN TO_DATE('"+strDateD+"', 'dd/MM/yyyy') AND TO_DATE('"+strDateH+"', 'dd/MM/yyyy') AND A.PACNEXP IN (SELECT DISTINCT(PACNEXP) FROM CAJDET WHERE CAJFEC BETWEEN TO_DATE('"+strDateD+"', 'dd/MM/yyyy') AND TO_DATE('"+strDateH+"', 'dd/MM/yyyy') AND PRECVE IN (SELECT PRECVE FROM CONCAT WHERE PREMOV='-')) GROUP BY A.PACNEXP, A.PACNOM, B.PRECVE, B.CAJTOTC, B.CAJNUM, C.CAJFOL, B.CAJFEC, C.USUCVE ORDER BY EXPEDIENTE, CAJNUM");
        
        ResultSetMetaData rsmt = rs.getMetaData();
        int numCol = rsmt.getColumnCount();

        //Creamos el objeto del modelo de la tabla
        DefaultTableModel modelo = new DefaultTableModel();
        this.jt_consulta_conceptos.setModel(modelo);

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

    public void cjaPagadoD() throws SQLException {

        String formato = "dd/MM/yyyy";
        Date date;
        date = jd_Desde.getDate();
        SimpleDateFormat sdfD = new SimpleDateFormat(formato);
        String strDateD = sdfD.format(date);

        rs = Conexiondb.Consulta("SELECT A.PACNEXP AS EXPEDIENTE, A.PACNOM AS NOMBRE, B.PRECVE AS CLAVE, TO_CHAR(B.CAJTOTC, '9999.99') AS CAJTOTC, B.CAJNUM AS CAJNUM, C.CAJFOL, TO_CHAR(B.CAJFEC,'dd/MM/yyyy') AS FECHA, SUM (CAJCANT) AS TOTAL_DE_TRATAMIENTOS, C.USUCVE FROM PACCAT A, CAJDET B, CAJENC C WHERE A.PACNEXP=B.PACNEXP AND A.PACNEXP = C.PACNEXP AND B.PACNEXP=C.PACNEXP AND B.CAJNUM = C.CAJNUM AND B.CAJFEC = C.CAJFEC AND B.CAJFEC = TO_DATE('"+strDateD+"', 'dd/MM/yyyy') AND C.CAJFEC = TO_DATE('"+strDateD+"', 'dd/MM/yyyy') AND A.PACNEXP IN (SELECT DISTINCT(PACNEXP) FROM CAJDET WHERE CAJFEC = TO_DATE('"+strDateD+"', 'dd/MM/yyyy') AND PRECVE IN (SELECT PRECVE FROM CONCAT WHERE PREMOV='-')) GROUP BY A.PACNEXP, A.PACNOM, B.PRECVE, B.CAJTOTC, B.CAJNUM, C.CAJFOL, B.CAJFEC, C.USUCVE ORDER BY EXPEDIENTE, CAJNUM");
        

        ResultSetMetaData rsmt = rs.getMetaData();
        int numCol = rsmt.getColumnCount();

        //Creamos el objeto del modelo de la tabla
        DefaultTableModel modelo = new DefaultTableModel();
        this.jt_consulta_conceptos.setModel(modelo);

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
    
    public void cjaNoPagoDH() throws SQLException {

        String formato = "dd/MM/yyyy";
        Date date;
        date = jd_Desde.getDate();
        SimpleDateFormat sdfD = new SimpleDateFormat(formato);
        String strDateD = sdfD.format(date);

        Date date2 = jd_Hasta.getDate();
        SimpleDateFormat sdfH = new SimpleDateFormat(formato);
        String strDateH = sdfH.format(date2);

        rs = Conexiondb.Consulta("SELECT A.PACNEXP AS EXPEDIENTE, A.PACNOM AS NOMBRE, B.PRECVE AS CLAVE, TO_CHAR(B.CAJTOTC, '9999.99') AS CAJTOTC, B.CAJNUM AS CAJNUM, C.CAJFOL, TO_CHAR(B.CAJFEC,'dd/MM/yyyy') AS FECHA, SUM (CAJCANT) AS TOTAL_DE_TRATAMIENTOS, C.USUCVE FROM PACCAT A, CAJDET B, CAJENC C WHERE A.PACNEXP=B.PACNEXP AND A.PACNEXP = C.PACNEXP AND B.PACNEXP = C.PACNEXP AND B.CAJNUM = C.CAJNUM AND B.CAJFEC = C.CAJFEC AND B.CAJFEC BETWEEN TO_DATE('" + strDateD + "', 'dd/MM/yyyy') AND TO_DATE('" + strDateH + "', 'dd/MM/yyyy') AND C.CAJFEC BETWEEN TO_DATE('" + strDateD + "', 'dd/MM/yyyy') AND TO_DATE('" + strDateH + "', 'dd/MM/yyyy') AND A.PACNEXP NOT IN (SELECT DISTINCT(PACNEXP) FROM CAJDET WHERE CAJFEC BETWEEN TO_DATE('" + strDateD + "', 'dd/MM/yyyy') AND TO_DATE('" + strDateH + "', 'dd/MM/yyyy') AND PRECVE IN (SELECT PRECVE FROM CONCAT WHERE PREMOV='-')) GROUP BY A.PACNEXP, A.PACNOM, B.PRECVE, B.CAJTOTC, B.CAJNUM, C.CAJFOL, B.CAJFEC, C.USUCVE UNION SELECT A.PACNEXP AS EXPEDIENTE, A.PACNOM AS NOMBRE, B.PRECVE AS CLAVE, TO_CHAR(B.CAJTOTC, '9999.99') AS CAJTOTC, B.CAJNUM AS CAJNUM, C.CAJFOL, TO_CHAR(B.CAJFEC,'dd/MM/yyyy') AS FECHA, SUM (CAJCANT) AS TOTAL_DE_TRATAMIENTOS, C.USUCVE FROM PACCAT A, CAJDET B, CAJENC C WHERE A.PACNEXP=B.PACNEXP AND A.PACNEXP = C.PACNEXP AND B.PACNEXP = C.PACNEXP  AND B.CAJNUM = C.CAJNUM  AND B.CAJFEC = C.CAJFEC AND B.CAJTOTC=0  AND B.CAJFEC BETWEEN TO_DATE('" + strDateD + "', 'dd/MM/yyyy') AND TO_DATE('" + strDateH + "', 'dd/MM/yyyy') AND C.CAJFEC BETWEEN TO_DATE('" + strDateD + "', 'dd/MM/yyyy') AND TO_DATE('" + strDateH + "', 'dd/MM/yyyy') AND B.PRECVE IN (SELECT PRECVE FROM CONCAT WHERE PREMOV='+')  AND A.PACNEXP NOT IN (SELECT DISTINCT(PACNEXP) FROM CAJDET WHERE CAJFEC BETWEEN TO_DATE('" + strDateD + "', 'dd/MM/yyyy') AND TO_DATE('" + strDateH + "', 'dd/MM/yyyy') AND PRECVE IN (SELECT PRECVE FROM CONCAT WHERE PREMOV='-') AND CAJCANT=0) GROUP BY A.PACNEXP, A.PACNOM, B.PRECVE, B.CAJTOTC, B.CAJNUM, C.CAJFOL, B.CAJFEC, C.USUCVE ORDER BY EXPEDIENTE, CAJNUM");
        
        ResultSetMetaData rsmt = rs.getMetaData();
        int numCol = rsmt.getColumnCount();

        //Creamos el objeto del modelo de la tabla
        DefaultTableModel modelo = new DefaultTableModel();
        this.jt_consulta_conceptos.setModel(modelo);

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

    public void cjaNoPagoD() throws SQLException {

        String formato = "dd/MM/yyyy";
        Date date;
        date = jd_Desde.getDate();
        SimpleDateFormat sdfD = new SimpleDateFormat(formato);
        String strDateD = sdfD.format(date);

        rs = Conexiondb.Consulta("SELECT A.PACNEXP AS EXPEDIENTE, A.PACNOM AS NOMBRE, B.PRECVE AS CLAVE, TO_CHAR(B.CAJTOTC, '9999.99') AS CAJTOTC, B.CAJNUM AS CAJNUM, C.CAJFOL, TO_CHAR(B.CAJFEC,'dd/MM/yyyy') AS FECHA, SUM (CAJCANT) AS TOTAL_DE_TRATAMIENTOS, C.USUCVE FROM PACCAT A, CAJDET B, CAJENC C WHERE A.PACNEXP=B.PACNEXP AND A.PACNEXP = C.PACNEXP AND B.PACNEXP = C.PACNEXP AND B.CAJNUM = C.CAJNUM AND B.CAJFEC = C.CAJFEC AND B.CAJFEC = TO_DATE('" + strDateD + "', 'dd/MM/yyyy') AND C.CAJFEC = TO_DATE('" + strDateD + "', 'dd/MM/yyyy') AND A.PACNEXP NOT IN (SELECT DISTINCT(PACNEXP) FROM CAJDET WHERE CAJFEC = TO_DATE('" + strDateD + "', 'dd/MM/yyyy') AND PRECVE IN (SELECT PRECVE FROM CONCAT WHERE PREMOV='-')) GROUP BY A.PACNEXP, A.PACNOM, B.PRECVE, B.CAJTOTC, B.CAJNUM, C.CAJFOL, B.CAJFEC, C.USUCVE UNION SELECT A.PACNEXP AS EXPEDIENTE, A.PACNOM AS NOMBRE, B.PRECVE AS CLAVE, B.CAJTOTC, B.CAJNUM AS CAJNUM, C.CAJFOL, TO_CHAR(B.CAJFEC,'dd/MM/yyyy') AS FECHA, SUM (CAJCANT) AS TOTAL_DE_TRATAMIENTOS, C.USUCVE FROM PACCAT A, CAJDET B, CAJENC C WHERE A.PACNEXP=B.PACNEXP AND A.PACNEXP = C.PACNEXP AND B.PACNEXP = C.PACNEXP  AND B.CAJNUM = C.CAJNUM  AND B.CAJFEC = C.CAJFEC AND B.CAJTOTC=0 AND B.CAJFEC = TO_DATE('" + strDateD + "', 'dd/MM/yyyy') AND C.CAJFEC = TO_DATE('" + strDateD + "', 'dd/MM/yyyy') AND B.PRECVE IN (SELECT PRECVE FROM CONCAT WHERE PREMOV='+')  AND A.PACNEXP NOT IN (SELECT DISTINCT(PACNEXP) FROM CAJDET WHERE CAJFEC = TO_DATE('" + strDateD + "', 'dd/MM/yyyy') AND PRECVE IN (SELECT PRECVE FROM CONCAT WHERE PREMOV='-') AND CAJCANT=0) GROUP BY A.PACNEXP, A.PACNOM, B.PRECVE, B.CAJTOTC, B.CAJNUM, C.CAJFOL, B.CAJFEC, C.USUCVE ORDER BY EXPEDIENTE, CAJNUM");
        
        ResultSetMetaData rsmt = rs.getMetaData();
        int numCol = rsmt.getColumnCount();

        //Creamos el objeto del modelo de la tabla
        DefaultTableModel modelo = new DefaultTableModel();
        this.jt_consulta_conceptos.setModel(modelo);

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
        int tt_reg = this.jt_consulta_conceptos.getRowCount();
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

        btn_grp_caja = new javax.swing.ButtonGroup();
        jl_titulo = new javax.swing.JLabel();
        btnConsulta = new javax.swing.JButton();
        btn_salir = new javax.swing.JButton();
        btn_imprimir = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jt_consulta_conceptos = new javax.swing.JTable();
        jl_tt_reg = new javax.swing.JLabel();
        btn_menu_principal = new javax.swing.JButton();
        btn_exportar = new javax.swing.JButton();
        jd_Desde = new com.toedter.calendar.JDateChooser();
        jl_desde = new javax.swing.JLabel();
        jd_Hasta = new com.toedter.calendar.JDateChooser();
        jl_hasta = new javax.swing.JLabel();
        btn_limpiar = new javax.swing.JButton();
        btn_reporte = new javax.swing.JButton();
        jl_etiqueta = new javax.swing.JLabel();
        jp_bar = new javax.swing.JProgressBar();
        jl_expediente = new javax.swing.JLabel();
        jt_expediente = new javax.swing.JTextField();
        jr_btn_todos = new javax.swing.JRadioButton();
        jr_btn_exp = new javax.swing.JRadioButton();
        jr_btn_pago = new javax.swing.JRadioButton();
        jr_btn_no_pago = new javax.swing.JRadioButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Reporte De Caja Conceptos");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jl_titulo.setBackground(new java.awt.Color(51, 102, 255));
        jl_titulo.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jl_titulo.setForeground(new java.awt.Color(255, 255, 255));
        jl_titulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jl_titulo.setText("REPORTE DE CAJA CONCEPTOS");
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

        jt_consulta_conceptos = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex){
                return false;
            }
        };
        jt_consulta_conceptos.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(jt_consulta_conceptos);

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

        jt_expediente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jt_expedienteKeyPressed(evt);
            }
        });

        btn_grp_caja.add(jr_btn_todos);
        jr_btn_todos.setText("Todos por Fecha");
        jr_btn_todos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jr_btn_todosActionPerformed(evt);
            }
        });

        btn_grp_caja.add(jr_btn_exp);
        jr_btn_exp.setText("Por Expediente");
        jr_btn_exp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jr_btn_expActionPerformed(evt);
            }
        });

        btn_grp_caja.add(jr_btn_pago);
        jr_btn_pago.setText("Pagaron");
        jr_btn_pago.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jr_btn_pagoActionPerformed(evt);
            }
        });

        btn_grp_caja.add(jr_btn_no_pago);
        jr_btn_no_pago.setText("No pagaron");
        jr_btn_no_pago.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jr_btn_no_pagoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jl_titulo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(btnConsulta, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn_imprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn_exportar)
                        .addGap(18, 18, 18)
                        .addComponent(btn_reporte)
                        .addGap(18, 18, 18)
                        .addComponent(btn_limpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn_menu_principal)
                        .addGap(11, 11, 11)
                        .addComponent(jl_tt_reg, javax.swing.GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE))
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jl_etiqueta)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jp_bar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jl_desde, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jd_Desde, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jl_expediente)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jt_expediente)))
                        .addGap(54, 54, 54)
                        .addComponent(jl_hasta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(85, 85, 85)
                        .addComponent(jd_Hasta, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_salir, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(165, 165, 165)
                        .addComponent(jr_btn_todos)
                        .addGap(65, 65, 65)
                        .addComponent(jr_btn_exp)
                        .addGap(67, 67, 67)
                        .addComponent(jr_btn_pago)
                        .addGap(82, 82, 82)
                        .addComponent(jr_btn_no_pago)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(18, 18, 18))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jl_titulo, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jr_btn_exp)
                        .addComponent(jr_btn_pago)
                        .addComponent(jr_btn_no_pago))
                    .addComponent(jr_btn_todos, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jl_expediente, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jt_expediente, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_salir, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jl_hasta, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jd_Hasta, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jd_Desde, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jl_desde, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnConsulta, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_imprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_exportar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_reporte, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_limpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_menu_principal, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jl_tt_reg, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 307, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jp_bar, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jl_etiqueta))
                .addGap(17, 17, 17))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_salirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_salirActionPerformed
        System.exit(0);
    }//GEN-LAST:event_btn_salirActionPerformed

    private void btnConsultaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultaActionPerformed
    
        if(jd_Desde.getDate() == null && jd_Hasta.getDate() == null && jr_btn_todos.isSelected())
        {
            JOptionPane.showMessageDialog(null, "Ingresa un rango de fecha que deseas hacer la consulta");
        } else if(jd_Desde.getDate() != null && jd_Hasta.getDate() != null && jr_btn_todos.isSelected())
        {
            
                task01Inf = new PBcjaConFecDH();
                task01Inf.execute();
            
        }else if(jd_Desde.getDate() != null && jd_Hasta.getDate() == null && jr_btn_todos.isSelected())
        {
            
                task02Inf = new PBcjaConFecD();
                task02Inf.execute();
            
        }else if(jd_Desde.getDate() == null && jd_Hasta.getDate() != null && jr_btn_todos.isSelected()){
            JOptionPane.showMessageDialog(null, "El campo Desde no puede estar vacio, ingresa un rango de Fecha Desde - Hasta");
        }if(jt_expediente.getText().isEmpty() && jd_Desde.getDate() == null && jd_Hasta.getDate() == null && jr_btn_exp.isSelected())
        {
            JOptionPane.showMessageDialog(null, "Ingresa el Num de Expediente o un rango de fecha que deseas hacer la consulta");
        } else if(jt_expediente.getText().length() >0 && jd_Desde.getDate() != null && jd_Hasta.getDate() != null && jr_btn_exp.isSelected())
        {
                task03Inf = new PBcjaConExpFecDH();
                task03Inf.execute();
            
        }else if(jt_expediente.getText().length() > 0 && jd_Desde.getDate() != null && jd_Hasta.getDate() == null && jr_btn_exp.isSelected())
        {
            
                task04Inf = new PBcjaConExpFecD();
                task04Inf.execute();
            
        }else if(jt_expediente.getText().length() > 0 && jd_Desde.getDate() == null && jd_Hasta.getDate() == null && jr_btn_exp.isSelected())
        {
            
                task05Inf = new PBcjaConExp();
                task05Inf.execute();
            
        }else if(jt_expediente.getText().isEmpty() && jd_Desde.getDate() == null && jd_Hasta.getDate() != null && jr_btn_exp.isSelected()){
            JOptionPane.showMessageDialog(null, "El campo Desde no puede estar vacio, ingresa un rango de Fecha Desde - Hasta");
        }else if(jt_expediente.getText().length() > 0 && jd_Desde.getDate() == null && jd_Hasta.getDate() != null && jr_btn_exp.isSelected()){
            JOptionPane.showMessageDialog(null, "El campo Desde no puede estar vacio, ingresa un rango de Fecha Desde - Hasta");
        }else if(jt_expediente.getText().isEmpty() && jd_Desde.getDate() != null && jd_Hasta.getDate() != null && jr_btn_exp.isSelected()){
            JOptionPane.showMessageDialog(null, "Ingresa un Numero de Expediente no puede estar vacio");
        }else if(jt_expediente.getText().isEmpty() && jd_Desde.getDate() != null && jd_Hasta.getDate() == null && jr_btn_exp.isSelected()){
            JOptionPane.showMessageDialog(null, "Ingresa un Numero de Expediente no puede estar vacio");
        } if(jd_Desde.getDate() == null && jd_Hasta.getDate() == null && jr_btn_pago.isSelected())
        {
            JOptionPane.showMessageDialog(null, "Ingresa un rango de fecha que deseas hacer la consulta");
        } else if(jd_Desde.getDate() != null && jd_Hasta.getDate() != null && jr_btn_pago.isSelected())
        {
            
                task06Inf = new PBcjaPagadoDH();
                task06Inf.execute();
            
        }else if(jd_Desde.getDate() != null && jd_Hasta.getDate() == null && jr_btn_pago.isSelected())
        {
            
                task07Inf = new PBcjaPagadoD();
                task07Inf.execute();
            
        }else if(jd_Desde.getDate() == null && jd_Hasta.getDate() != null && jr_btn_pago.isSelected()){
            JOptionPane.showMessageDialog(null, "El campo Desde no puede estar vacio, ingresa un rango de Fecha Desde - Hasta");
        } if(jd_Desde.getDate() == null && jd_Hasta.getDate() == null && jr_btn_no_pago.isSelected())
        {
            JOptionPane.showMessageDialog(null, "Ingresa un rango de fecha que deseas hacer la consulta");
        } else if(jd_Desde.getDate() != null && jd_Hasta.getDate() != null && jr_btn_no_pago.isSelected())
        {
            
                task08Inf = new PBcjaNoPagoDH();
                task08Inf.execute();
            
        }else if(jd_Desde.getDate() != null && jd_Hasta.getDate() == null && jr_btn_no_pago.isSelected())
        {
            
                task09Inf = new PBcjaNoPagoD();
                task09Inf.execute();
            
        }else if(jd_Desde.getDate() == null && jd_Hasta.getDate() != null && jr_btn_no_pago.isSelected()){
            JOptionPane.showMessageDialog(null, "El campo Desde no puede estar vacio, ingresa un rango de Fecha Desde - Hasta");
        }
    }//GEN-LAST:event_btnConsultaActionPerformed

    private void btn_imprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_imprimirActionPerformed

        try{
            MessageFormat headerformat = new MessageFormat("Reporte Caja Conceptos"); //encabezado
            MessageFormat footerformat = new MessageFormat("Page - {0}"); //
            jt_consulta_conceptos.print(JTable.PrintMode.FIT_WIDTH, headerformat, footerformat);  //imprime la JTable
        } catch (PrinterException ex) {
        Logger.getLogger(jfCajaConceptos.class.getName()).log(Level.SEVERE, null, ex);
    }
    }//GEN-LAST:event_btn_imprimirActionPerformed

    private void btn_menu_principalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_menu_principalActionPerformed
        dispose();
        new MenuPrincipal().setVisible(true);
    }//GEN-LAST:event_btn_menu_principalActionPerformed

    private void btn_exportarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_exportarActionPerformed
       if(this.jt_consulta_conceptos.getRowCount() == 0)
       {
           JOptionPane.showMessageDialog(null, "No hay datos por exportar", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
           return;
       }
       
       //Creacion del archivo en Excel
       int totalR = jt_consulta_conceptos.getRowCount();
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
           tb.add(jt_consulta_conceptos);
           nom.add("Reporte Caja Conceptos");
           String file = chooser.getSelectedFile().toString().concat(".xls");
           //Aqui se ejecuta el metodo
           try{
               Exporter e = new Exporter(new File(file),tb,nom);
               if(e.export())
               {
                   JOptionPane.showMessageDialog(null, "Total de Registros exportados a Excel: " + totalR, "Reporte Caja Conceptos", JOptionPane.INFORMATION_MESSAGE);
               }
           }
           catch(Exception e)
           {
             JOptionPane.showMessageDialog(null, "Hubo un error al exportar" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
           }
       }
    }//GEN-LAST:event_btn_exportarActionPerformed

    private void btn_limpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_limpiarActionPerformed
        jd_Desde.setDate(null);
        jd_Hasta.setDate(null);
        jt_expediente.setText("");
        DefaultTableModel dTable = new DefaultTableModel();
        this.jt_consulta_conceptos.setModel(dTable);
        while(dTable.getRowCount()>0){
            dTable.removeRow(0);
        }
        this.jl_tt_reg.setText("Total Registros: ");
    }//GEN-LAST:event_btn_limpiarActionPerformed

    private void btn_reporteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_reporteActionPerformed
        if (this.jt_consulta_conceptos.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Ningun Dato por Exportar a PDF", "Mensage", JOptionPane.INFORMATION_MESSAGE);
            return;
        } else {
            int totalR = jt_consulta_conceptos.getRowCount();
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
                tb.add(jt_consulta_conceptos);
                nom.add("Caja Conceptos");
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

    private void jr_btn_expActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jr_btn_expActionPerformed
        if(jr_btn_exp.isSelected()){
            jt_expediente.setVisible(true);
            jl_expediente.setVisible(true);
        }
    }//GEN-LAST:event_jr_btn_expActionPerformed

    private void jr_btn_pagoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jr_btn_pagoActionPerformed
        if(jr_btn_pago.isSelected()){
            jt_expediente.setVisible(false);
            jl_expediente.setVisible(false);
        }
    }//GEN-LAST:event_jr_btn_pagoActionPerformed

    private void jr_btn_no_pagoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jr_btn_no_pagoActionPerformed
        if(jr_btn_no_pago.isSelected()){
            jt_expediente.setVisible(false);
            jl_expediente.setVisible(false);
        }
    }//GEN-LAST:event_jr_btn_no_pagoActionPerformed

    private void jt_expedienteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jt_expedienteKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (jt_expediente.getText().isEmpty() && jd_Desde.getDate() == null && jd_Hasta.getDate() == null && jr_btn_exp.isSelected()) {
                JOptionPane.showMessageDialog(null, "Ingresa el Num de Expediente o un rango de fecha que deseas hacer la consulta");
            } else if (jt_expediente.getText().length() > 0 && jd_Desde.getDate() != null && jd_Hasta.getDate() != null && jr_btn_exp.isSelected()) {
                task03Inf = new PBcjaConExpFecDH();
                task03Inf.execute();

            } else if (jt_expediente.getText().length() > 0 && jd_Desde.getDate() != null && jd_Hasta.getDate() == null && jr_btn_exp.isSelected()) {

                task04Inf = new PBcjaConExpFecD();
                task04Inf.execute();

            } else if (jt_expediente.getText().length() > 0 && jd_Desde.getDate() == null && jd_Hasta.getDate() == null && jr_btn_exp.isSelected()) {

                task05Inf = new PBcjaConExp();
                task05Inf.execute();

            } else if (jt_expediente.getText().isEmpty() && jd_Desde.getDate() == null && jd_Hasta.getDate() != null && jr_btn_exp.isSelected()) {
                JOptionPane.showMessageDialog(null, "El campo Desde no puede estar vacio, ingresa un rango de Fecha Desde - Hasta");
            } else if (jt_expediente.getText().length() > 0 && jd_Desde.getDate() == null && jd_Hasta.getDate() != null && jr_btn_exp.isSelected()) {
                JOptionPane.showMessageDialog(null, "El campo Desde no puede estar vacio, ingresa un rango de Fecha Desde - Hasta");
            } else if (jt_expediente.getText().isEmpty() && jd_Desde.getDate() != null && jd_Hasta.getDate() != null && jr_btn_exp.isSelected()) {
                JOptionPane.showMessageDialog(null, "Ingresa un Numero de Expediente no puede estar vacio");
            } else if (jt_expediente.getText().isEmpty() && jd_Desde.getDate() != null && jd_Hasta.getDate() == null && jr_btn_exp.isSelected()) {
                JOptionPane.showMessageDialog(null, "Ingresa un Numero de Expediente no puede estar vacio");
            }
        }
    }//GEN-LAST:event_jt_expedienteKeyPressed

    private void jr_btn_todosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jr_btn_todosActionPerformed
        if(jr_btn_todos.isSelected()){
            jl_expediente.setVisible(false);
            jt_expediente.setVisible(false);
        }
    }//GEN-LAST:event_jr_btn_todosActionPerformed

    
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
            java.util.logging.Logger.getLogger(jfCajaConceptos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(jfCajaConceptos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(jfCajaConceptos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(jfCajaConceptos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try{
                    UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
                }catch(Exception e){
                    e.printStackTrace();
                }
                new jfCajaConceptos().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnConsulta;
    private javax.swing.JButton btn_exportar;
    private javax.swing.ButtonGroup btn_grp_caja;
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
    private javax.swing.JLabel jl_expediente;
    private javax.swing.JLabel jl_hasta;
    private javax.swing.JLabel jl_titulo;
    private javax.swing.JLabel jl_tt_reg;
    private javax.swing.JProgressBar jp_bar;
    private javax.swing.JRadioButton jr_btn_exp;
    private javax.swing.JRadioButton jr_btn_no_pago;
    private javax.swing.JRadioButton jr_btn_pago;
    private javax.swing.JRadioButton jr_btn_todos;
    private javax.swing.JTable jt_consulta_conceptos;
    private javax.swing.JTextField jt_expediente;
    // End of variables declaration//GEN-END:variables

    class PBcjaConFecDH extends SwingWorker<Void, Void> {

        @Override
        protected Void doInBackground() throws Exception {
            jp_bar.setVisible(true);
            jp_bar.setIndeterminate(true);
            jl_etiqueta.setVisible(true);
            cjaConFecDH();
            return null;
        }

        @Override
        protected void done() {
            jp_bar.setVisible(false);
            jl_etiqueta.setVisible(false);
        }
    }

    class PBcjaConFecD extends SwingWorker<Void, Void> {

        @Override
        protected Void doInBackground() throws Exception {
            jp_bar.setVisible(true);
            jp_bar.setIndeterminate(true);
            jl_etiqueta.setVisible(true);
            cjaConFecD();
            return null;
        }

        @Override
        protected void done() {
            jp_bar.setVisible(false);
            jl_etiqueta.setVisible(false);
        }
    }
    
        class PBcjaConExpFecDH extends SwingWorker<Void, Void> {

        @Override
        protected Void doInBackground() throws Exception {
            jp_bar.setVisible(true);
            jp_bar.setIndeterminate(true);
            jl_etiqueta.setVisible(true);
            cjaConExpFecDH();
            return null;
        }

        @Override
        protected void done() {
            jp_bar.setVisible(false);
            jl_etiqueta.setVisible(false);
        }
    }

    class PBcjaConExpFecD extends SwingWorker<Void, Void> {

        @Override
        protected Void doInBackground() throws Exception {
            jp_bar.setVisible(true);
            jp_bar.setIndeterminate(true);
            jl_etiqueta.setVisible(true);
            cjaConExpFecD();
            return null;
        }

        @Override
        protected void done() {
            jp_bar.setVisible(false);
            jl_etiqueta.setVisible(false);
        }
    }
    
    class PBcjaConExp extends SwingWorker<Void, Void> {

        @Override
        protected Void doInBackground() throws Exception {
            jp_bar.setVisible(true);
            jp_bar.setIndeterminate(true);
            jl_etiqueta.setVisible(true);
            cjaConExp();
            return null;
        }

        @Override
        protected void done() {
            jp_bar.setVisible(false);
            jl_etiqueta.setVisible(false);
        }
    }
    
    class PBcjaPagadoDH extends SwingWorker<Void, Void> {

        @Override
        protected Void doInBackground() throws Exception {
            jp_bar.setVisible(true);
            jp_bar.setIndeterminate(true);
            jl_etiqueta.setVisible(true);
            cjaPagadoDH();
            return null;
        }

        @Override
        protected void done() {
            jp_bar.setVisible(false);
            jl_etiqueta.setVisible(false);
        }
    }

    class PBcjaPagadoD extends SwingWorker<Void, Void> {

        @Override
        protected Void doInBackground() throws Exception {
            jp_bar.setVisible(true);
            jp_bar.setIndeterminate(true);
            jl_etiqueta.setVisible(true);
            cjaPagadoD();
            return null;
        }

        @Override
        protected void done() {
            jp_bar.setVisible(false);
            jl_etiqueta.setVisible(false);
        }
    }

    class PBcjaNoPagoDH extends SwingWorker<Void, Void> {

        @Override
        protected Void doInBackground() throws Exception {
            jp_bar.setVisible(true);
            jp_bar.setIndeterminate(true);
            jl_etiqueta.setVisible(true);
            cjaNoPagoDH();
            return null;
        }

        @Override
        protected void done() {
            jp_bar.setVisible(false);
            jl_etiqueta.setVisible(false);
        }
    }

    class PBcjaNoPagoD extends SwingWorker<Void, Void> {

        @Override
        protected Void doInBackground() throws Exception {
            jp_bar.setVisible(true);
            jp_bar.setIndeterminate(true);
            jl_etiqueta.setVisible(true);
            cjaNoPagoD();
            return null;
        }

        @Override
        protected void done() {
            jp_bar.setVisible(false);
            jl_etiqueta.setVisible(false);
        }
    }
    

}
