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
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import events.Exporter;
import events.formaPago;
import events.movCaja;
import java.awt.Color;
import java.io.File;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;
import javax.swing.BoundedRangeModel;
import javax.swing.DefaultListModel;
import javax.swing.SwingWorker;
import javax.swing.UIManager;


/**
 * @author Jessica Viviana Pastrana Munguia
 */
public class jfCorteCaja1 extends javax.swing.JFrame {
ResultSet rs;
private PBCorteDiaDH task01Inf;
private PBCorteDiaD task02Inf;
private PBformasPagosTDH task03Inf;
private PBformasPagosTD task04Inf;
private PBremisionesDH task05Inf;
private PBremisionesD task06Inf;
private PBfacturasDH task07Inf;
private PBfacturasD task08Inf;
private ProgressCajaInf task09Inf;
private PBformaPagoListDH task10Inf;
private PBformaPagoListD task11Inf;
private PBremisionesListDH task12Inf;
private PBremisionesListD task13Inf;
private PBfacturasListDH task14Inf;
private PBfacturasListD task15Inf;
String pattern= "'$'###,###.00";
Locale locale = new Locale("en","US");
//DecimalFormat df2 = (DecimalFormat)NumberFormat.getNumberInstance(Locale.US);
DecimalFormat df2 = (DecimalFormat)NumberFormat.getCurrencyInstance(locale);

/**
     * Creates new form jfVacunas
     */
    public jfCorteCaja1() {
        initComponents();
        setLocationRelativeTo(null);
        //setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(false);
        jr_btn_corte.setSelected(true);
        jl_cve.setEnabled(false);
        jl_cve_fp.setEnabled(false);
        jl_fp_desc.setEnabled(false);
        jlist_cve.setEnabled(false);
        jlist_desc.setEnabled(false);
        jl_cve_con.setEnabled(false);
        jlist_cve_consulta.setEnabled(false);
        jlist_desc_consulta.setEnabled(false);
        btn_obtener.setEnabled(false);
        btn_agregar.setEnabled(false);
        btn_quitar.setEnabled(false);
        btn_desahacer.setEnabled(false);
        jl_sumatoria.setVisible(false);
        jl_titulo.setForeground(Color.white);
        jl_titulo.setOpaque(true);
        jl_etiqueta.setVisible(false);
        jp_bar.setVisible(false);

    }
    
    public void corteDiaDH() throws SQLException {
 
        String formato = "dd/MM/yyyy";
        Date date;
        date = jd_Desde.getDate();
        SimpleDateFormat sdfD = new SimpleDateFormat(formato);
        String strDateD = sdfD.format(date);

        Date date2 = jd_Hasta.getDate();
        SimpleDateFormat sdfH = new SimpleDateFormat(formato);
        String strDateH = sdfH.format(date2);

        rs = Conexiondb.Consulta("SELECT A.PACNEXP AS EXPEDIENTE, B.PACNOM AS NOMBRE, TO_CHAR(A.CAJFEC,'dd/MM/yyyy') AS FECHA, A.CAJNUM, A.CAJFOL AS FOLIO, A. USUCVE AS USUARIO, A.CAJTUR AS TURNO, C.PRECVE AS FORMA_PAGO, D.PREDESC AS DESCRIPCION, C.CAJTOTN AS PAGADO FROM CAJENC A, PACCAT B, CAJDET C, CONCAT D WHERE A.PACNEXP = B.PACNEXP AND B.PACNEXP = C.PACNEXP AND A.CAJNUM = C.CAJNUM AND C.PRECVE = D.PRECVE AND D.PREMOV = '-' AND A.CAJFEC BETWEEN TO_DATE('" + strDateD + "', 'dd/MM/yyyy') AND TO_DATE('" + strDateH + "', 'dd/MM/yyyy') AND C.CAJFEC BETWEEN TO_DATE('" + strDateD + "', 'dd/MM/yyyy') AND TO_DATE('" + strDateH + "', 'dd/MM/yyyy') ORDER BY A.PACNEXP, A.CAJFEC");
        
        ResultSetMetaData rsmt = rs.getMetaData();
        int numCol = rsmt.getColumnCount();

        //Creamos el objeto del modelo de la tabla
        DefaultTableModel modelo = new DefaultTableModel();
        this.jt_consulta_corte.setModel(modelo);

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
            double b = 0;
            double total = 0;

            for (int fila = 0; fila < jt_consulta_corte.getRowCount(); fila++) {
                a = String.valueOf(jt_consulta_corte.getValueAt(fila, 9));
                b = Double.parseDouble(a);
                total = total + b;
            }

            modelo.addRow(new Object[]{"","","","","","","","","Total", df2.format(total)});
            
            jl_sumatoria.setVisible(true);
            this.jl_sumatoria.setText("Total del dia: " +df2.format(total));

            rs.close();
            tt_rec();
        }

    }

    public void corteDiaD() throws SQLException {
        
        String formato = "dd/MM/yyyy";
        Date date;
        date = jd_Desde.getDate();
        SimpleDateFormat sdfD = new SimpleDateFormat(formato);
        String strDateD = sdfD.format(date);

        rs = Conexiondb.Consulta("SELECT A.PACNEXP AS EXPEDIENTE, B.PACNOM AS NOMBRE, TO_CHAR(A.CAJFEC,'dd/MM/yyyy') AS FECHA, A.CAJNUM, A.CAJFOL AS FOLIO, A. USUCVE AS USUARIO, A.CAJTUR AS TURNO, C.PRECVE AS FORMA_PAGO, D.PREDESC AS DESCRIPCION, C.CAJTOTN AS PAGADO FROM CAJENC A, PACCAT B, CAJDET C, CONCAT D WHERE A.PACNEXP = B.PACNEXP AND B.PACNEXP = C.PACNEXP AND A.CAJNUM = C.CAJNUM AND C.PRECVE = D.PRECVE AND D.PREMOV = '-' AND A.CAJFEC = TO_DATE('" + strDateD + "', 'dd/MM/yyyy') AND C.CAJFEC = TO_DATE('" + strDateD + "', 'dd/MM/yyyy') ORDER BY A.PACNEXP, A.CAJFEC");
        
        ResultSetMetaData rsmt = rs.getMetaData();
        int numCol = rsmt.getColumnCount();

        //Creamos el objeto del modelo de la tabla
        DefaultTableModel modelo = new DefaultTableModel();
        this.jt_consulta_corte.setModel(modelo);

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
            double b = 0;
            double total = 0;

            for (int fila = 0; fila < jt_consulta_corte.getRowCount(); fila++) {
                a = String.valueOf(jt_consulta_corte.getValueAt(fila, 9));
                b = Double.parseDouble(a);
                total = total + b;
            }

            modelo.addRow(new Object[]{"","","","","","","","","Total", df2.format(total)});
            
            jl_sumatoria.setVisible(true);
            this.jl_sumatoria.setText("Total del dia: " +df2.format(total));

            rs.close();
            tt_rec();
        }

    }
    
   
    
    public void formasPagosTDH() throws SQLException {
    
        String formato = "dd/MM/yyyy";
        Date date;
        date = jd_Desde.getDate();
        SimpleDateFormat sdfD = new SimpleDateFormat(formato);
        String strDateD = sdfD.format(date);

        Date date2 = jd_Hasta.getDate();
        SimpleDateFormat sdfH = new SimpleDateFormat(formato);
        String strDateH = sdfH.format(date2);

        rs = Conexiondb.Consulta("SELECT A.PACNEXP AS EXPEDIENTE, B.PACNOM AS NOMBRE, TO_CHAR(A.CAJFEC,'dd/MM/yyyy') AS FECHA, C.PRECVE AS FORMA_PAGO, D.PREDESC AS DESCRIPCION, A.CAJNUM, A.CAJFOL AS FOLIO, A.USUCVE AS USUARIO, A.CAJTUR AS TURNO, C.CAJTOTN AS PAGADO FROM CAJENC A, PACCAT B, CAJDET C, CONCAT D WHERE A.PACNEXP = B.PACNEXP AND B.PACNEXP = C.PACNEXP AND A.CAJNUM = C.CAJNUM AND C.PRECVE = D.PRECVE AND D.PREMOV='-' AND A.CAJFEC BETWEEN TO_DATE('" + strDateD + "', 'dd/MM/yyyy') AND TO_DATE('" + strDateH + "', 'dd/MM/yyyy') AND C.CAJFEC BETWEEN TO_DATE('" + strDateD + "', 'dd/MM/yyyy') AND TO_DATE('" + strDateH + "', 'dd/MM/yyyy') ORDER BY EXPEDIENTE");
        
        ResultSetMetaData rsmt = rs.getMetaData();
        int numCol = rsmt.getColumnCount();

        //Creamos el objeto del modelo de la tabla
        DefaultTableModel modelo = new DefaultTableModel();
        this.jt_consulta_corte.setModel(modelo);

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
            double b = 0;
            double total = 0;

            for (int fila = 0; fila < jt_consulta_corte.getRowCount(); fila++) {
                a = String.valueOf(jt_consulta_corte.getValueAt(fila, 9));
                b = Double.parseDouble(a);
                total = total + b;
            }

            modelo.addRow(new Object[]{"", "", "", "", "", "", "", "", "Total", df2.format(total)});

            jl_sumatoria.setVisible(true);
            this.jl_sumatoria.setText("Total del dia: " + df2.format(total));

            rs.close();
            tt_rec();
        }
    }
    
    public void formasPagosTD() throws SQLException {
        
        String formato = "dd/MM/yyyy";
        Date date;
        date = jd_Desde.getDate();
        SimpleDateFormat sdfD = new SimpleDateFormat(formato);
        String strDateD = sdfD.format(date);

        rs = Conexiondb.Consulta("SELECT A.PACNEXP AS EXPEDIENTE, B.PACNOM AS NOMBRE, TO_CHAR(A.CAJFEC,'dd/MM/yyyy') AS FECHA, C.PRECVE AS FORMA_PAGO, D.PREDESC AS DESCRIPCION, A.CAJNUM, A.CAJFOL AS FOLIO, A.USUCVE AS USUARIO, A.CAJTUR AS TURNO, C.CAJTOTN AS PAGADO FROM CAJENC A, PACCAT B, CAJDET C, CONCAT D WHERE A.PACNEXP = B.PACNEXP AND B.PACNEXP = C.PACNEXP AND A.CAJNUM = C.CAJNUM AND C.PRECVE = D.PRECVE AND D.PREMOV='-' AND A.CAJFEC = TO_DATE('" + strDateD + "', 'dd/MM/yyyy') AND C.CAJFEC = TO_DATE('" + strDateD + "', 'dd/MM/yyyy') ORDER BY EXPEDIENTE");
        
        ResultSetMetaData rsmt = rs.getMetaData();
        int numCol = rsmt.getColumnCount();

        //Creamos el objeto del modelo de la tabla
        DefaultTableModel modelo = new DefaultTableModel();
        this.jt_consulta_corte.setModel(modelo);

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
            double b = 0;
            double total = 0;

            for (int fila = 0; fila < jt_consulta_corte.getRowCount(); fila++) {
                a = String.valueOf(jt_consulta_corte.getValueAt(fila, 9));
                b = Double.parseDouble(a);
                total = total + b;
            }

            modelo.addRow(new Object[]{"", "", "", "", "", "", "", "", "Total", df2.format(total)});

            jl_sumatoria.setVisible(true);
            this.jl_sumatoria.setText("Total del dia: " + df2.format(total));

            rs.close();
            tt_rec();
        }
    }

    
    public void remisionesDH() throws SQLException {
           
        String formato = "dd/MM/yyyy";
        Date date;
        date = jd_Desde.getDate();
        SimpleDateFormat sdfD = new SimpleDateFormat(formato);
        String strDateD = sdfD.format(date);

        Date date2 = jd_Hasta.getDate();
        SimpleDateFormat sdfH = new SimpleDateFormat(formato);
        String strDateH = sdfH.format(date2);

        rs = Conexiondb.Consulta("SELECT A.PACNEXP AS EXPEDIENTE, B.PACNOM AS NOMBRE, TO_CHAR(A.CAJFEC,'dd/MM/yyyy') AS FECHA, C.PRECVE AS FORMA_PAGO, D.PREDESC AS DESCRIPCION, A.CAJNUM, A.CAJFOL AS FOLIO, A.USUCVE AS USUARIO, A.CAJTUR AS TURNO, C.CAJTOTN AS PAGADO FROM CAJENC A, PACCAT B, CAJDET C, CONCAT D WHERE A.PACNEXP = B.PACNEXP AND B.PACNEXP = C.PACNEXP AND A.CAJNUM = C.CAJNUM AND C.PRECVE = D.PRECVE AND D.PREMOV='-' AND A.CAJFEC BETWEEN TO_DATE('" + strDateD + "', 'dd/MM/yyyy') AND TO_DATE('" + strDateH + "', 'dd/MM/yyyy') AND C.CAJFEC BETWEEN TO_DATE('" + strDateD + "', 'dd/MM/yyyy') AND TO_DATE('" + strDateH + "', 'dd/MM/yyyy') AND A.CAJFOL = 0 ORDER BY EXPEDIENTE");
        
        ResultSetMetaData rsmt = rs.getMetaData();
        int numCol = rsmt.getColumnCount();

        //Creamos el objeto del modelo de la tabla
        DefaultTableModel modelo = new DefaultTableModel();
        this.jt_consulta_corte.setModel(modelo);

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
            double b = 0;
            double total = 0;

            for (int fila = 0; fila < jt_consulta_corte.getRowCount(); fila++) {
                a = String.valueOf(jt_consulta_corte.getValueAt(fila, 9));
                b = Double.parseDouble(a);
                total = total + b;
            }

            modelo.addRow(new Object[]{"", "", "", "", "", "", "", "", "Total", df2.format(total)});

            jl_sumatoria.setVisible(true);
            this.jl_sumatoria.setText("Total del dia: " + df2.format(total));

            rs.close();
            tt_rec();
        }
    }
    
    public void remisionesD() throws SQLException {
        
        String formato = "dd/MM/yyyy";
        Date date;
        date = jd_Desde.getDate();
        SimpleDateFormat sdfD = new SimpleDateFormat(formato);
        String strDateD = sdfD.format(date);

        rs = Conexiondb.Consulta("SELECT A.PACNEXP AS EXPEDIENTE, B.PACNOM AS NOMBRE, TO_CHAR(A.CAJFEC,'dd/MM/yyyy') AS FECHA, C.PRECVE AS FORMA_PAGO, D.PREDESC AS DESCRIPCION, A.CAJNUM, A.CAJFOL AS FOLIO, A.USUCVE AS USUARIO, A.CAJTUR AS TURNO, C.CAJTOTN AS PAGADO FROM CAJENC A, PACCAT B, CAJDET C, CONCAT D WHERE A.PACNEXP = B.PACNEXP AND B.PACNEXP = C.PACNEXP AND A.CAJNUM = C.CAJNUM AND C.PRECVE = D.PRECVE AND D.PREMOV='-' AND A.CAJFEC = TO_DATE('" + strDateD + "', 'dd/MM/yyyy') AND C.CAJFEC = TO_DATE('" + strDateD + "', 'dd/MM/yyyy') AND A.CAJFOL = 0 ORDER BY EXPEDIENTE");
        
        ResultSetMetaData rsmt = rs.getMetaData();
        int numCol = rsmt.getColumnCount();

        //Creamos el objeto del modelo de la tabla
        DefaultTableModel modelo = new DefaultTableModel();
        this.jt_consulta_corte.setModel(modelo);

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
            double b = 0;
            double total = 0;

            for (int fila = 0; fila < jt_consulta_corte.getRowCount(); fila++) {
                a = String.valueOf(jt_consulta_corte.getValueAt(fila, 9));
                b = Double.parseDouble(a);
                total = total + b;
            }

            modelo.addRow(new Object[]{"", "", "", "", "", "", "", "", "Total", df2.format(total)});

            jl_sumatoria.setVisible(true);
            this.jl_sumatoria.setText("Total del dia: " + df2.format(total));

            rs.close();
            tt_rec();
        }
    }
   
    public void facturasDH() throws SQLException {
        
        String formato = "dd/MM/yyyy";
        Date date;
        date = jd_Desde.getDate();
        SimpleDateFormat sdfD = new SimpleDateFormat(formato);
        String strDateD = sdfD.format(date);

        Date date2 = jd_Hasta.getDate();
        SimpleDateFormat sdfH = new SimpleDateFormat(formato);
        String strDateH = sdfH.format(date2);

        rs = Conexiondb.Consulta("SELECT A.PACNEXP AS EXPEDIENTE, B.PACNOM AS NOMBRE, TO_CHAR(A.CAJFEC,'dd/MM/yyyy') AS FECHA, C.PRECVE AS FORMA_PAGO, D.PREDESC AS DESCRIPCION, A.CAJNUM, A.CAJFOL AS FOLIO, A.USUCVE AS USUARIO, A.CAJTUR AS TURNO, C.CAJTOTN AS PAGADO FROM CAJENC A, PACCAT B, CAJDET C, CONCAT D WHERE A.PACNEXP = B.PACNEXP AND B.PACNEXP = C.PACNEXP AND A.CAJNUM = C.CAJNUM AND C.PRECVE = D.PRECVE AND D.PREMOV='-' AND A.CAJFEC BETWEEN TO_DATE('" + strDateD + "', 'dd/MM/yyyy') AND TO_DATE('" + strDateH + "', 'dd/MM/yyyy') AND C.CAJFEC BETWEEN TO_DATE('" + strDateD + "', 'dd/MM/yyyy') AND TO_DATE('" + strDateH + "', 'dd/MM/yyyy') AND A.CAJFOL > 0 ORDER BY EXPEDIENTE");
        
        ResultSetMetaData rsmt = rs.getMetaData();
        int numCol = rsmt.getColumnCount();

        //Creamos el objeto del modelo de la tabla
        DefaultTableModel modelo = new DefaultTableModel();
        this.jt_consulta_corte.setModel(modelo);

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
            double b = 0;
            double total = 0;

            for (int fila = 0; fila < jt_consulta_corte.getRowCount(); fila++) {
                a = String.valueOf(jt_consulta_corte.getValueAt(fila, 9));
                b = Double.parseDouble(a);
                total = total + b;
            }

            modelo.addRow(new Object[]{"", "", "", "", "", "", "", "", "Total", df2.format(total)});

            jl_sumatoria.setVisible(true);
            this.jl_sumatoria.setText("Total del dia: " + df2.format(total));

            rs.close();
            tt_rec();
        }
    }
    
    public void facturasD() throws SQLException {
        
        String formato = "dd/MM/yyyy";
        Date date;
        date = jd_Desde.getDate();
        SimpleDateFormat sdfD = new SimpleDateFormat(formato);
        String strDateD = sdfD.format(date);

        rs = Conexiondb.Consulta("SELECT A.PACNEXP AS EXPEDIENTE, B.PACNOM AS NOMBRE, TO_CHAR(A.CAJFEC,'dd/MM/yyyy') AS FECHA, C.PRECVE AS FORMA_PAGO, D.PREDESC AS DESCRIPCION, A.CAJNUM, A.CAJFOL AS FOLIO, A.USUCVE AS USUARIO, A.CAJTUR AS TURNO, C.CAJTOTN AS PAGADO FROM CAJENC A, PACCAT B, CAJDET C, CONCAT D WHERE A.PACNEXP = B.PACNEXP AND B.PACNEXP = C.PACNEXP AND A.CAJNUM = C.CAJNUM AND C.PRECVE = D.PRECVE AND D.PREMOV='-' AND A.CAJFEC = TO_DATE('" + strDateD + "', 'dd/MM/yyyy') AND C.CAJFEC = TO_DATE('" + strDateD + "', 'dd/MM/yyyy') AND A.CAJFOL > 0 ORDER BY EXPEDIENTE");
        
        ResultSetMetaData rsmt = rs.getMetaData();
        int numCol = rsmt.getColumnCount();

        //Creamos el objeto del modelo de la tabla
        DefaultTableModel modelo = new DefaultTableModel();
        this.jt_consulta_corte.setModel(modelo);

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
            double b = 0;
            double total = 0;

            for (int fila = 0; fila < jt_consulta_corte.getRowCount(); fila++) {
                a = String.valueOf(jt_consulta_corte.getValueAt(fila, 9));
                b = Double.parseDouble(a);
                total = total + b;
            }

            modelo.addRow(new Object[]{"", "", "", "", "", "", "", "", "Total", df2.format(total)});

            jl_sumatoria.setVisible(true);
            this.jl_sumatoria.setText("Total del dia: " + df2.format(total));

            rs.close();
            tt_rec();
        }
    }
    
    //private String cveChar;
    
    public void obtenCve() {

        int var1 = 1;
        String vChar="";
        String sChar="";
        
        for (int i = 0; i < jlist_cve_consulta.getModel().getSize(); i++) {

            if (var1 >= jlist_cve_consulta.getModel().getSize()) {
                //System.out.print("'" + jlist_cve_consulta.getModel().getElementAt(i).toString() + "'");
                vChar = "'" + jlist_cve_consulta.getModel().getElementAt(i).toString() + "'";

            } else {
                //System.out.print("'" + jlist_cve_consulta.getModel().getElementAt(i).toString() + "',");
                vChar = "'" + jlist_cve_consulta.getModel().getElementAt(i).toString() + "',";
            }
            var1++;
            sChar = sChar+vChar;
        }

        String cveChar = sChar;
        //System.out.println(sChar);
        //JOptionPane.showMessageDialog(this, cveChar.toString());

    }
    
    public void formaPagoListDH() throws SQLException{
        
        String formato = "dd/MM/yyyy";
        Date date;
        date = jd_Desde.getDate();
        SimpleDateFormat sdfD = new SimpleDateFormat(formato);
        String strDateD = sdfD.format(date);

        Date date2 = jd_Hasta.getDate();
        SimpleDateFormat sdfH = new SimpleDateFormat(formato);
        String strDateH = sdfH.format(date2);
        
        int var1 = 1;
        String vChar="";
        String sChar="";
        
        for (int i = 0; i < jlist_cve_consulta.getModel().getSize(); i++) {

            if (var1 >= jlist_cve_consulta.getModel().getSize()) {
                //System.out.print("'" + jlist_cve_consulta.getModel().getElementAt(i).toString() + "'");
                vChar = "'" + jlist_cve_consulta.getModel().getElementAt(i).toString() + "'";

            } else {
                //System.out.print("'" + jlist_cve_consulta.getModel().getElementAt(i).toString() + "',");
                vChar = "'" + jlist_cve_consulta.getModel().getElementAt(i).toString() + "',";
            }
            var1++;
            sChar = sChar+vChar;
        }

        String cveChar = sChar;
         
        rs = Conexiondb.Consulta("SELECT A.PACNEXP AS EXPEDIENTE, B.PACNOM AS NOMBRE, TO_CHAR(A.CAJFEC,'dd/MM/yyyy') AS FECHA, C.PRECVE AS FORMA_PAGO, D.PREDESC AS DESCRIPCION, A.CAJNUM, A.CAJFOL AS FOLIO, A.USUCVE AS USUARIO, A.CAJTUR AS TURNO, C.CAJTOTN AS PAGADO FROM CAJENC A, PACCAT B, CAJDET C, CONCAT D WHERE A.PACNEXP = B.PACNEXP AND B.PACNEXP = C.PACNEXP AND A.CAJNUM = C.CAJNUM AND C.PRECVE IN ("+cveChar.toString()+") AND C.PRECVE = D.PRECVE AND D.PREMOV='-' AND A.CAJFEC BETWEEN TO_DATE('" + strDateD + "', 'dd/MM/yyyy') AND TO_DATE('" + strDateH + "', 'dd/MM/yyyy') AND C.CAJFEC BETWEEN TO_DATE('" + strDateD + "', 'dd/MM/yyyy') AND TO_DATE('" + strDateH + "', 'dd/MM/yyyy') ORDER BY EXPEDIENTE");
        
        ResultSetMetaData rsmt = rs.getMetaData();
        int numCol = rsmt.getColumnCount();

        //Creamos el objeto del modelo de la tabla
        DefaultTableModel modelo = new DefaultTableModel();
        this.jt_consulta_corte.setModel(modelo);

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
            double b = 0;
            double total = 0;

            for (int fila = 0; fila < jt_consulta_corte.getRowCount(); fila++) {
                a = String.valueOf(jt_consulta_corte.getValueAt(fila, 9));
                b = Double.parseDouble(a);
                total = total + b;
            }

            modelo.addRow(new Object[]{"", "", "", "", "", "", "", "", "Total", df2.format(total)});

            jl_sumatoria.setVisible(true);
            this.jl_sumatoria.setText("Total del dia: " + df2.format(total));

            rs.close();
            tt_rec();
        }
    }
    
       public void formaPagoListD() throws SQLException {
           
        String formato = "dd/MM/yyyy";
        Date date;
        date = jd_Desde.getDate();
        SimpleDateFormat sdfD = new SimpleDateFormat(formato);
        String strDateD = sdfD.format(date);
        
        int var1 = 1;
        String vChar="";
        String sChar="";
        
        for (int i = 0; i < jlist_cve_consulta.getModel().getSize(); i++) {

            if (var1 >= jlist_cve_consulta.getModel().getSize()) {
                //System.out.print("'" + jlist_cve_consulta.getModel().getElementAt(i).toString() + "'");
                vChar = "'" + jlist_cve_consulta.getModel().getElementAt(i).toString() + "'";

            } else {
                //System.out.print("'" + jlist_cve_consulta.getModel().getElementAt(i).toString() + "',");
                vChar = "'" + jlist_cve_consulta.getModel().getElementAt(i).toString() + "',";
            }
            var1++;
            sChar = sChar+vChar;
        }

        String cveChar = sChar;

        rs = Conexiondb.Consulta("SELECT A.PACNEXP AS EXPEDIENTE, B.PACNOM AS NOMBRE, TO_CHAR(A.CAJFEC,'dd/MM/yyyy') AS FECHA, C.PRECVE AS FORMA_PAGO, D.PREDESC AS DESCRIPCION, A.CAJNUM, A.CAJFOL AS FOLIO, A.USUCVE AS USUARIO, A.CAJTUR AS TURNO, C.CAJTOTN AS PAGADO FROM CAJENC A, PACCAT B, CAJDET C, CONCAT D WHERE A.PACNEXP = B.PACNEXP AND B.PACNEXP = C.PACNEXP AND A.CAJNUM = C.CAJNUM AND C.PRECVE IN ("+cveChar.toString()+") AND C.PRECVE = D.PRECVE AND D.PREMOV='-' AND A.CAJFEC = TO_DATE('" + strDateD + "', 'dd/MM/yyyy') AND C.CAJFEC = TO_DATE('" + strDateD + "', 'dd/MM/yyyy') ORDER BY EXPEDIENTE");
        
        ResultSetMetaData rsmt = rs.getMetaData();
        int numCol = rsmt.getColumnCount();

        //Creamos el objeto del modelo de la tabla
        DefaultTableModel modelo = new DefaultTableModel();
        this.jt_consulta_corte.setModel(modelo);

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
            double b = 0;
            double total = 0;

            for (int fila = 0; fila < jt_consulta_corte.getRowCount(); fila++) {
                a = String.valueOf(jt_consulta_corte.getValueAt(fila, 9));
                b = Double.parseDouble(a);
                total = total + b;
            }

            modelo.addRow(new Object[]{"", "", "", "", "", "", "", "", "Total", df2.format(total)});

            jl_sumatoria.setVisible(true);
            this.jl_sumatoria.setText("Total del dia: " + df2.format(total));

            rs.close();
            tt_rec();
        }
    }
       
    public void remisionesListDH() throws SQLException {
        
        String formato = "dd/MM/yyyy";
        Date date;
        date = jd_Desde.getDate();
        SimpleDateFormat sdfD = new SimpleDateFormat(formato);
        String strDateD = sdfD.format(date);

        Date date2 = jd_Hasta.getDate();
        SimpleDateFormat sdfH = new SimpleDateFormat(formato);
        String strDateH = sdfH.format(date2);
        
        int var1 = 1;
        String vChar="";
        String sChar="";
        
        for (int i = 0; i < jlist_cve_consulta.getModel().getSize(); i++) {

            if (var1 >= jlist_cve_consulta.getModel().getSize()) {
                //System.out.print("'" + jlist_cve_consulta.getModel().getElementAt(i).toString() + "'");
                vChar = "'" + jlist_cve_consulta.getModel().getElementAt(i).toString() + "'";

            } else {
                //System.out.print("'" + jlist_cve_consulta.getModel().getElementAt(i).toString() + "',");
                vChar = "'" + jlist_cve_consulta.getModel().getElementAt(i).toString() + "',";
            }
            var1++;
            sChar = sChar+vChar;
        }

        String cveChar = sChar;

        rs = Conexiondb.Consulta("SELECT A.PACNEXP AS EXPEDIENTE, B.PACNOM AS NOMBRE, TO_CHAR(A.CAJFEC,'dd/MM/yyyy') AS FECHA, C.PRECVE AS FORMA_PAGO, D.PREDESC AS DESCRIPCION, A.CAJNUM, A.CAJFOL AS FOLIO, A.USUCVE AS USUARIO, A.CAJTUR AS TURNO, C.CAJTOTN AS PAGADO FROM CAJENC A, PACCAT B, CAJDET C, CONCAT D WHERE A.PACNEXP = B.PACNEXP AND B.PACNEXP = C.PACNEXP AND A.CAJNUM = C.CAJNUM AND C.PRECVE = D.PRECVE AND C.PRECVE IN ("+cveChar.toString()+") AND D.PREMOV='-' AND A.CAJFEC BETWEEN TO_DATE('" + strDateD + "', 'dd/MM/yyyy') AND TO_DATE('" + strDateH + "', 'dd/MM/yyyy') AND C.CAJFEC BETWEEN TO_DATE('" + strDateD + "', 'dd/MM/yyyy') AND TO_DATE('" + strDateH + "', 'dd/MM/yyyy') AND A.CAJFOL = 0 ORDER BY EXPEDIENTE");
        
        ResultSetMetaData rsmt = rs.getMetaData();
        int numCol = rsmt.getColumnCount();

        //Creamos el objeto del modelo de la tabla
        DefaultTableModel modelo = new DefaultTableModel();
        this.jt_consulta_corte.setModel(modelo);

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
            double b = 0;
            double total = 0;

            for (int fila = 0; fila < jt_consulta_corte.getRowCount(); fila++) {
                a = String.valueOf(jt_consulta_corte.getValueAt(fila, 9));
                b = Double.parseDouble(a);
                total = total + b;
            }

            modelo.addRow(new Object[]{"", "", "", "", "", "", "", "", "Total", df2.format(total)});

            jl_sumatoria.setVisible(true);
            this.jl_sumatoria.setText("Total del dia: " + df2.format(total));

            rs.close();
            tt_rec();
        }
    }

    public void remisionesListD() throws SQLException {
        
        String formato = "dd/MM/yyyy";
        Date date;
        date = jd_Desde.getDate();
        SimpleDateFormat sdfD = new SimpleDateFormat(formato);
        String strDateD = sdfD.format(date);

        int var1 = 1;
        String vChar = "";
        String sChar = "";

        for (int i = 0; i < jlist_cve_consulta.getModel().getSize(); i++) {

            if (var1 >= jlist_cve_consulta.getModel().getSize()) {
                //System.out.print("'" + jlist_cve_consulta.getModel().getElementAt(i).toString() + "'");
                vChar = "'" + jlist_cve_consulta.getModel().getElementAt(i).toString() + "'";

            } else {
                //System.out.print("'" + jlist_cve_consulta.getModel().getElementAt(i).toString() + "',");
                vChar = "'" + jlist_cve_consulta.getModel().getElementAt(i).toString() + "',";
            }
            var1++;
            sChar = sChar + vChar;
        }

        String cveChar = sChar;

        rs = Conexiondb.Consulta("SELECT A.PACNEXP AS EXPEDIENTE, B.PACNOM AS NOMBRE, TO_CHAR(A.CAJFEC,'dd/MM/yyyy') AS FECHA, C.PRECVE AS FORMA_PAGO, D.PREDESC AS DESCRIPCION, A.CAJNUM, A.CAJFOL AS FOLIO, A.USUCVE AS USUARIO, A.CAJTUR AS TURNO, C.CAJTOTN AS PAGADO FROM CAJENC A, PACCAT B, CAJDET C, CONCAT D WHERE A.PACNEXP = B.PACNEXP AND B.PACNEXP = C.PACNEXP AND A.CAJNUM = C.CAJNUM AND C.PRECVE = D.PRECVE AND C.PRECVE IN (" + cveChar.toString() + ") AND D.PREMOV='-' AND A.CAJFEC = TO_DATE('" + strDateD + "', 'dd/MM/yyyy') AND C.CAJFEC = TO_DATE('" + strDateD + "', 'dd/MM/yyyy') AND A.CAJFOL = 0 ORDER BY EXPEDIENTE");
        
        ResultSetMetaData rsmt = rs.getMetaData();
        int numCol = rsmt.getColumnCount();

        //Creamos el objeto del modelo de la tabla
        DefaultTableModel modelo = new DefaultTableModel();
        this.jt_consulta_corte.setModel(modelo);

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
            double b = 0;
            double total = 0;

            for (int fila = 0; fila < jt_consulta_corte.getRowCount(); fila++) {
                a = String.valueOf(jt_consulta_corte.getValueAt(fila, 9));
                b = Double.parseDouble(a);
                total = total + b;
            }

            modelo.addRow(new Object[]{"", "", "", "", "", "", "", "", "Total", df2.format(total)});

            jl_sumatoria.setVisible(true);
            this.jl_sumatoria.setText("Total del dia: " + df2.format(total));

            rs.close();
            tt_rec();
        }
    }

    
    public void facturasListDH() throws SQLException {
        
        String formato = "dd/MM/yyyy";
        Date date;
        date = jd_Desde.getDate();
        SimpleDateFormat sdfD = new SimpleDateFormat(formato);
        String strDateD = sdfD.format(date);

        Date date2 = jd_Hasta.getDate();
        SimpleDateFormat sdfH = new SimpleDateFormat(formato);
        String strDateH = sdfH.format(date2);
        
        int var1 = 1;
        String vChar = "";
        String sChar = "";

        for (int i = 0; i < jlist_cve_consulta.getModel().getSize(); i++) {

            if (var1 >= jlist_cve_consulta.getModel().getSize()) {
                //System.out.print("'" + jlist_cve_consulta.getModel().getElementAt(i).toString() + "'");
                vChar = "'" + jlist_cve_consulta.getModel().getElementAt(i).toString() + "'";

            } else {
                //System.out.print("'" + jlist_cve_consulta.getModel().getElementAt(i).toString() + "',");
                vChar = "'" + jlist_cve_consulta.getModel().getElementAt(i).toString() + "',";
            }
            var1++;
            sChar = sChar + vChar;
        }

        String cveChar = sChar;


        rs = Conexiondb.Consulta("SELECT A.PACNEXP AS EXPEDIENTE, B.PACNOM AS NOMBRE, TO_CHAR(A.CAJFEC,'dd/MM/yyyy') AS FECHA, C.PRECVE AS FORMA_PAGO, D.PREDESC AS DESCRIPCION, A.CAJNUM, A.CAJFOL AS FOLIO, A.USUCVE AS USUARIO, A.CAJTUR AS TURNO, C.CAJTOTN AS PAGADO FROM CAJENC A, PACCAT B, CAJDET C, CONCAT D WHERE A.PACNEXP = B.PACNEXP AND B.PACNEXP = C.PACNEXP AND A.CAJNUM = C.CAJNUM AND C.PRECVE = D.PRECVE AND C.PRECVE IN ("+cveChar.toString()+") AND D.PREMOV='-' AND A.CAJFEC BETWEEN TO_DATE('" + strDateD + "', 'dd/MM/yyyy') AND TO_DATE('" + strDateH + "', 'dd/MM/yyyy') AND C.CAJFEC BETWEEN TO_DATE('" + strDateD + "', 'dd/MM/yyyy') AND TO_DATE('" + strDateH + "', 'dd/MM/yyyy') AND A.CAJFOL > 0 ORDER BY EXPEDIENTE");
        
        ResultSetMetaData rsmt = rs.getMetaData();
        int numCol = rsmt.getColumnCount();

        //Creamos el objeto del modelo de la tabla
        DefaultTableModel modelo = new DefaultTableModel();
        this.jt_consulta_corte.setModel(modelo);

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
            double b = 0;
            double total = 0;

            for (int fila = 0; fila < jt_consulta_corte.getRowCount(); fila++) {
                a = String.valueOf(jt_consulta_corte.getValueAt(fila, 9));
                b = Double.parseDouble(a);
                total = total + b;
            }

            modelo.addRow(new Object[]{"", "", "", "", "", "", "", "", "Total", df2.format(total)});

            jl_sumatoria.setVisible(true);
            this.jl_sumatoria.setText("Total del dia: " + df2.format(total));

            rs.close();
            tt_rec();
        }
    }

    public void facturasListD() throws SQLException {
        
        String formato = "dd/MM/yyyy";
        Date date;
        date = jd_Desde.getDate();
        SimpleDateFormat sdfD = new SimpleDateFormat(formato);
        String strDateD = sdfD.format(date);

        int var1 = 1;
        String vChar = "";
        String sChar = "";

        for (int i = 0; i < jlist_cve_consulta.getModel().getSize(); i++) {

            if (var1 >= jlist_cve_consulta.getModel().getSize()) {
                //System.out.print("'" + jlist_cve_consulta.getModel().getElementAt(i).toString() + "'");
                vChar = "'" + jlist_cve_consulta.getModel().getElementAt(i).toString() + "'";

            } else {
                //System.out.print("'" + jlist_cve_consulta.getModel().getElementAt(i).toString() + "',");
                vChar = "'" + jlist_cve_consulta.getModel().getElementAt(i).toString() + "',";
            }
            var1++;
            sChar = sChar + vChar;
        }

        String cveChar = sChar;


        rs = Conexiondb.Consulta("SELECT A.PACNEXP AS EXPEDIENTE, B.PACNOM AS NOMBRE, TO_CHAR(A.CAJFEC,'dd/MM/yyyy') AS FECHA, C.PRECVE AS FORMA_PAGO, D.PREDESC AS DESCRIPCION, A.CAJNUM, A.CAJFOL AS FOLIO, A.USUCVE AS USUARIO, A.CAJTUR AS TURNO, C.CAJTOTN AS PAGADO FROM CAJENC A, PACCAT B, CAJDET C, CONCAT D WHERE A.PACNEXP = B.PACNEXP AND B.PACNEXP = C.PACNEXP AND A.CAJNUM = C.CAJNUM AND C.PRECVE = D.PRECVE AND C.PRECVE IN ("+cveChar.toString()+") AND D.PREMOV='-' AND A.CAJFEC = TO_DATE('" + strDateD + "', 'dd/MM/yyyy') AND C.CAJFEC = TO_DATE('" + strDateD + "', 'dd/MM/yyyy') AND A.CAJFOL > 0 ORDER BY EXPEDIENTE");
        
        ResultSetMetaData rsmt = rs.getMetaData();
        int numCol = rsmt.getColumnCount();

        //Creamos el objeto del modelo de la tabla
        DefaultTableModel modelo = new DefaultTableModel();
        this.jt_consulta_corte.setModel(modelo);

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
            double b = 0;
            double total = 0;

            for (int fila = 0; fila < jt_consulta_corte.getRowCount(); fila++) {
                a = String.valueOf(jt_consulta_corte.getValueAt(fila, 9));
                b = Double.parseDouble(a);
                total = total + b;
            }

            modelo.addRow(new Object[]{"", "", "", "", "", "", "", "", "Total", df2.format(total)});

            jl_sumatoria.setVisible(true);
            this.jl_sumatoria.setText("Total del dia: " + df2.format(total));

            rs.close();
            tt_rec();
        }
    }


    public void cambioEstatus(){
        jl_cve.setEnabled(false);
        jl_cve.setVisible(true);
        jl_cve_fp.setEnabled(false);
        jl_cve_fp.setVisible(true);
        jl_fp_desc.setEnabled(false);
        jl_fp_desc.setVisible(true);
        jlist_cve.setEnabled(false);
        jlist_cve.setVisible(true);
        jScrollPane4.setVisible(true);
        jlist_desc.setEnabled(false);
        jlist_desc.setVisible(true);
        jScrollPane2.setVisible(true);
        jl_cve_con.setEnabled(false);
        jl_cve_con.setVisible(true);
        jlist_cve_consulta.setEnabled(false);
        jlist_cve_consulta.setVisible(true);
        jScrollPane5.setVisible(true);
        jlist_desc_consulta.setEnabled(false);
        jlist_desc_consulta.setVisible(true);
        jScrollPane3.setVisible(true);
        btn_obtener.setEnabled(false);
        btn_obtener.setVisible(true);
        btn_agregar.setEnabled(false);
        btn_agregar.setVisible(true);
        btn_quitar.setEnabled(false);
        btn_quitar.setVisible(true);
        btn_desahacer.setEnabled(false);
        btn_desahacer.setVisible(true);
        jr_btn_fp.setSelected(false);
    }
    public void cambioEstatusPago() {
        jl_cve.setEnabled(true);
        jl_cve.setVisible(true);
        jl_cve_fp.setEnabled(true);
        jl_cve_fp.setVisible(true);
        jl_fp_desc.setEnabled(true);
        jl_fp_desc.setVisible(true);
        jlist_cve.setEnabled(true);
        jlist_cve.setVisible(true);
        jScrollPane4.setVisible(true);
        jlist_desc.setEnabled(true);
        jlist_desc.setVisible(true);
        jScrollPane2.setVisible(true);
        jl_cve_con.setEnabled(true);
        jl_cve_con.setVisible(true);
        jlist_cve_consulta.setEnabled(true);
        jlist_cve_consulta.setVisible(true);
        jScrollPane5.setVisible(true);
        jlist_desc_consulta.setEnabled(true);
        jlist_desc_consulta.setVisible(true);
        jScrollPane3.setVisible(true);
        btn_obtener.setEnabled(true);
        btn_obtener.setVisible(true);
        btn_agregar.setEnabled(true);
        btn_agregar.setVisible(true);
        btn_quitar.setEnabled(true);
        btn_quitar.setVisible(true);
        btn_desahacer.setEnabled(true);
        btn_desahacer.setVisible(true);
    }
   
    public void tt_rec() {
        int tt_reg = this.jt_consulta_corte.getRowCount();
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

        rbtnGroupCaja = new javax.swing.ButtonGroup();
        jl_titulo = new javax.swing.JLabel();
        btnConsulta = new javax.swing.JButton();
        btn_salir = new javax.swing.JButton();
        btn_imprimir = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jt_consulta_corte = new javax.swing.JTable();
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
        jl_cve = new javax.swing.JLabel();
        jr_btn_fpago = new javax.swing.JRadioButton();
        jr_btn_corte = new javax.swing.JRadioButton();
        jr_btn_facturas = new javax.swing.JRadioButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jlist_desc = new javax.swing.JList<movCaja>();
        jr_btn_fp = new javax.swing.JRadioButton();
        jr_btn_remision = new javax.swing.JRadioButton();
        jl_sumatoria = new javax.swing.JLabel();
        btn_obtener = new javax.swing.JButton();
        btn_agregar = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jlist_desc_consulta = new javax.swing.JList();
        btn_quitar = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        jlist_cve = new javax.swing.JList();
        jScrollPane5 = new javax.swing.JScrollPane();
        jlist_cve_consulta = new javax.swing.JList();
        jl_cve_fp = new javax.swing.JLabel();
        jl_fp_desc = new javax.swing.JLabel();
        jl_cve_con = new javax.swing.JLabel();
        btn_desahacer = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Reporte De Corte de Caja");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jl_titulo.setBackground(new java.awt.Color(51, 102, 255));
        jl_titulo.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jl_titulo.setForeground(new java.awt.Color(255, 255, 255));
        jl_titulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jl_titulo.setText("REPORTE DE CORTE DE CAJA");
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

        jt_consulta_corte = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex){
                return false;
            }
        };
        jt_consulta_corte.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(jt_consulta_corte);

        jl_tt_reg.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
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

        jl_cve.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jl_cve.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jl_cve.setText("Formas de Pago");

        rbtnGroupCaja.add(jr_btn_fpago);
        jr_btn_fpago.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jr_btn_fpago.setText("Forma de Pago");
        jr_btn_fpago.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jr_btn_fpagoActionPerformed(evt);
            }
        });

        rbtnGroupCaja.add(jr_btn_corte);
        jr_btn_corte.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jr_btn_corte.setText("Corte del Dia");
        jr_btn_corte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jr_btn_corteActionPerformed(evt);
            }
        });

        rbtnGroupCaja.add(jr_btn_facturas);
        jr_btn_facturas.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jr_btn_facturas.setText("Facturas");
        jr_btn_facturas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jr_btn_facturasActionPerformed(evt);
            }
        });

        jlist_desc.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane2.setViewportView(jlist_desc);

        jr_btn_fp.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jr_btn_fp.setText("y Formas Pago");
        jr_btn_fp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jr_btn_fpActionPerformed(evt);
            }
        });

        rbtnGroupCaja.add(jr_btn_remision);
        jr_btn_remision.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jr_btn_remision.setText("Remisiones");
        jr_btn_remision.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jr_btn_remisionActionPerformed(evt);
            }
        });

        jl_sumatoria.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jl_sumatoria.setText("Total del dia:");

        btn_obtener.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btn_obtener.setText("Obtener");
        btn_obtener.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_obtenerActionPerformed(evt);
            }
        });

        btn_agregar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btn_agregar.setText(">>");
        btn_agregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_agregarActionPerformed(evt);
            }
        });

        jlist_desc_consulta.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        jScrollPane3.setViewportView(jlist_desc_consulta);

        btn_quitar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btn_quitar.setText("<<");
        btn_quitar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_quitarActionPerformed(evt);
            }
        });

        jlist_cve.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jlist_cve.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
                jlist_cveAncestorMoved(evt);
            }
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        jlist_cve.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jlist_cveValueChanged(evt);
            }
        });
        jScrollPane4.setViewportView(jlist_cve);

        jlist_cve_consulta.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jlist_cve_consulta.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
                jlist_cve_consultaAncestorMoved(evt);
            }
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        jScrollPane5.setViewportView(jlist_cve_consulta);

        jl_cve_fp.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jl_cve_fp.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jl_cve_fp.setText("CVE");

        jl_fp_desc.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jl_fp_desc.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jl_fp_desc.setText("DESCRIPCION/FORMA PAGO");

        jl_cve_con.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jl_cve_con.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jl_cve_con.setText("FORMAS PAGO A CONSULTAR");

        btn_desahacer.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btn_desahacer.setText("Deshacer");
        btn_desahacer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_desahacerActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jl_titulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(22, 22, 22)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jr_btn_corte)
                                    .addComponent(jr_btn_fpago)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                            .addComponent(jl_hasta, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(18, 18, 18)
                                            .addComponent(jd_Hasta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                            .addComponent(jl_desde, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(18, 18, 18)
                                            .addComponent(jd_Desde, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jr_btn_remision)
                                            .addComponent(jr_btn_facturas))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jr_btn_fp)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(27, 27, 27)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                    .addComponent(jl_cve_fp, javax.swing.GroupLayout.DEFAULT_SIZE, 56, Short.MAX_VALUE)
                                                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                                                .addGap(10, 10, 10)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 186, Short.MAX_VALUE)
                                                    .addComponent(jl_fp_desc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                            .addComponent(jl_sumatoria, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(116, 116, 116)
                                                .addComponent(jl_cve_con, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(btn_obtener, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(btn_agregar, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(btn_quitar, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(btn_desahacer, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(btnConsulta, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(226, 226, 226)
                                        .addComponent(jl_tt_reg, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(21, 21, 21)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(btn_reporte, javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(btn_exportar, javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(btn_limpiar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btn_menu_principal, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btn_salir, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(btn_imprimir, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(423, 423, 423)
                                .addComponent(jl_cve, javax.swing.GroupLayout.PREFERRED_SIZE, 324, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 14, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jl_etiqueta, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jp_bar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jl_titulo, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jl_cve, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jr_btn_corte)
                                .addGap(24, 24, 24)
                                .addComponent(jr_btn_fpago)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jr_btn_remision, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(15, 15, 15)
                                        .addComponent(jr_btn_facturas)
                                        .addGap(18, 18, 18))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(jr_btn_fp)
                                        .addGap(35, 35, 35)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jl_desde, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jd_Desde, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(21, 21, 21)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jd_Hasta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jl_hasta, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jl_cve_fp)
                                    .addComponent(jl_fp_desc)
                                    .addComponent(jl_cve_con))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(btn_obtener, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(btn_agregar)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(btn_quitar, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(btn_desahacer, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnConsulta, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jl_sumatoria, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btn_imprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_reporte, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_exportar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_limpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_menu_principal, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btn_salir, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jl_tt_reg, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(12, 12, 12)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 257, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jp_bar, javax.swing.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE)
                    .addComponent(jl_etiqueta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_salirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_salirActionPerformed
        System.exit(0);
    }//GEN-LAST:event_btn_salirActionPerformed

    private void btnConsultaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultaActionPerformed
        if (jd_Desde.getDate() != null && jd_Hasta.getDate() != null && jr_btn_corte.isSelected()) {
            task01Inf = new PBCorteDiaDH();
            task01Inf.execute();
        } else if (jd_Desde.getDate() != null && jd_Hasta.getDate() == null && jr_btn_corte.isSelected()) {
            task02Inf = new PBCorteDiaD();
            task02Inf.execute();
        } else if (jd_Desde.getDate() == null && jd_Hasta.getDate() != null && jr_btn_corte.isSelected()) {
            JOptionPane.showMessageDialog(null, "Ingresa Desde que fecha es la consulta");
        } else if (jd_Desde.getDate() == null && jd_Hasta.getDate() == null && jr_btn_corte.isSelected()) {
            JOptionPane.showMessageDialog(null, "Ingresa Desde - Hasta que fecha es la consulta");
        } else if (jd_Desde.getDate() == null && jd_Hasta.getDate() != null && jr_btn_remision.isSelected()) {
            JOptionPane.showMessageDialog(null, "Ingresa Desde que fecha es la consulta");
        } else if (jd_Desde.getDate() == null && jd_Hasta.getDate() == null && jr_btn_remision.isSelected()) {
            JOptionPane.showMessageDialog(null, "Ingresa Desde - Hasta que fecha es la consulta");
        } else if (jd_Desde.getDate() != null && jd_Hasta.getDate() != null && jr_btn_remision.isSelected() && jr_btn_fp.isSelected() == false) {
            task05Inf = new PBremisionesDH();
            task05Inf.execute();
            //JOptionPane.showMessageDialog(this, "Estoy ejecutando esta tarea 05 Remisiones DH");
        } else if (jd_Desde.getDate() != null && jd_Hasta.getDate() == null && jr_btn_remision.isSelected() && jr_btn_fp.isSelected() == false) {
            task06Inf = new PBremisionesD();
            task06Inf.execute();
            //JOptionPane.showMessageDialog(this, "Estoy ejecutando esta tarea 06 Remisiones D");
        } else if (jd_Desde.getDate() == null && jd_Hasta.getDate() != null && jr_btn_facturas.isSelected()) {
            JOptionPane.showMessageDialog(null, "Ingresa Desde que fecha es la consulta");
        } else if (jd_Desde.getDate() == null && jd_Hasta.getDate() == null && jr_btn_facturas.isSelected()) {
            JOptionPane.showMessageDialog(null, "Ingresa Desde - Hasta que fecha es la consulta");
        } else if (jd_Desde.getDate() != null && jd_Hasta.getDate() != null && jr_btn_facturas.isSelected() && jr_btn_fp.isSelected() == false) {
            task07Inf = new PBfacturasDH();
            task07Inf.execute();
            //JOptionPane.showMessageDialog(this, "Estoy ejecutando Facturas DH");
        }else if (jd_Desde.getDate() != null && jd_Hasta.getDate() == null && jr_btn_facturas.isSelected() && jr_btn_fp.isSelected() == false) {
            task08Inf = new PBfacturasD();
            task08Inf.execute();
            //JOptionPane.showMessageDialog(this, "Estoy ejecutnado Facturas D");
        }else if (jd_Desde.getDate() != null && jd_Hasta.getDate() != null && jr_btn_fpago.isSelected() && jlist_cve.getModel().getSize() == 0 && jlist_desc.getModel().getSize() == 0 && jlist_cve_consulta.getModel().getSize() == 0 && jlist_desc_consulta.getModel().getSize() == 0) {
            task03Inf = new PBformasPagosTDH();
            task03Inf.execute();
            //JOptionPane.showMessageDialog(this, "Ejecuto el PBformasPagosTDH - 1");
        }else if (jd_Desde.getDate() != null && jd_Hasta.getDate() != null && jr_btn_fpago.isSelected() && jlist_cve.getModel().getSize() > 0 && jlist_desc.getModel().getSize() > 0 && jlist_cve_consulta.getModel().getSize() == 0 && jlist_desc_consulta.getModel().getSize() == 0) {
            task03Inf = new PBformasPagosTDH();
            task03Inf.execute();
            //JOptionPane.showMessageDialog(this, "Ejecuto el PBformasPagosTDH - 2");
        }else if (jd_Desde.getDate() != null && jd_Hasta.getDate() == null && jr_btn_fpago.isSelected() && jlist_cve.getModel().getSize() == 0 && jlist_desc.getModel().getSize() == 0 && jlist_cve_consulta.getModel().getSize() == 0 && jlist_desc_consulta.getModel().getSize() == 0) {
            task04Inf = new PBformasPagosTD();
            task04Inf.execute();
            //JOptionPane.showMessageDialog(this, "Ejecuto el PBformasPagosTDH - 3");
        }else if (jd_Desde.getDate() != null && jd_Hasta.getDate() == null && jr_btn_fpago.isSelected() && jlist_cve.getModel().getSize() > 0 && jlist_desc.getModel().getSize() > 0 && jlist_cve_consulta.getModel().getSize() == 0 && jlist_desc_consulta.getModel().getSize() == 0) {
            task04Inf = new PBformasPagosTD();
            task04Inf.execute();
            //JOptionPane.showMessageDialog(this, "Ejecuto el PBformasPagosTDH - 4");
        }else if (jd_Desde.getDate() == null && jd_Hasta.getDate() == null && jr_btn_fpago.isSelected()) {
            JOptionPane.showMessageDialog(null, "Ingresa Desde - Hasta que fecha es la consulta");
        }else if (jd_Desde.getDate() == null && jd_Hasta.getDate() != null && jr_btn_fpago.isSelected()) {
            JOptionPane.showMessageDialog(null, "Ingresa Desde que fecha es la consulta");
        }else if (jd_Desde.getDate() != null && jd_Hasta.getDate() != null && jr_btn_fpago.isSelected() && jlist_cve.getModel().getSize() > 0 && jlist_desc.getModel().getSize() > 0 && jlist_cve_consulta.getModel().getSize() > 0 && jlist_desc_consulta.getModel().getSize() > 0) {
            task10Inf = new PBformaPagoListDH();
            task10Inf.execute();
            //JOptionPane.showMessageDialog(this, "Ejecuto el PBformasPagosTDH - 5");
        }else if (jd_Desde.getDate() == null && jd_Hasta.getDate() == null && jr_btn_fpago.isSelected() && jlist_desc_consulta.getModel().getSize() > 0) {
            JOptionPane.showMessageDialog(null, "Ingresa Desde - Hasta que fecha es la consulta");
        }else if (jd_Desde.getDate() == null && jd_Hasta.getDate() != null && jr_btn_fpago.isSelected() && jlist_desc_consulta.getModel().getSize() > 0) {
            JOptionPane.showMessageDialog(null, "Ingresa Desde que fecha es la consulta");
        }else if (jd_Desde.getDate() != null && jd_Hasta.getDate() == null && jr_btn_fpago.isSelected() && jlist_cve.getModel().getSize() > 0 && jlist_desc.getModel().getSize() > 0 && jlist_cve_consulta.getModel().getSize() > 0 && jlist_desc_consulta.getModel().getSize() > 0) {
            task11Inf = new PBformaPagoListD();
            task11Inf.execute();
            //JOptionPane.showMessageDialog(this, "Ejecuto el PBformasPagosTDH - 5");
        }else if(jd_Desde.getDate() != null && jd_Hasta.getDate() != null && jr_btn_remision.isSelected() && jr_btn_fp.isSelected() && jlist_cve.getModel().getSize() == 0){
            task05Inf = new PBremisionesDH();
            task05Inf.execute();
            //JOptionPane.showMessageDialog(this, "Ejecutando opcion 7 Remisiones");
        }else if(jd_Desde.getDate() != null && jd_Hasta.getDate() != null && jr_btn_remision.isSelected() && jr_btn_fp.isSelected() && jlist_cve.getModel().getSize() > 0 && jlist_desc.getModel().getSize() > 0 && jlist_cve_consulta.getModel().getSize() == 0 && jlist_desc_consulta.getModel().getSize() == 0 ){
            task05Inf = new PBremisionesDH();
            task05Inf.execute();
            //JOptionPane.showMessageDialog(this, "Ejecuto opcion 8 Remisiones");
        }else if(jd_Desde.getDate() != null && jd_Hasta.getDate() == null && jr_btn_remision.isSelected() && jr_btn_fp.isSelected() && jlist_cve.getModel().getSize() == 0 && jlist_desc.getModel().getSize() == 0 && jlist_cve_consulta.getModel().getSize() == 0 && jlist_desc_consulta.getModel().getSize() == 0 ){
            task06Inf = new PBremisionesD();
            task06Inf.execute();
            //JOptionPane.showMessageDialog(this, "Ejecuto opcion 9 Remisiones");
        }else if(jd_Desde.getDate() != null && jd_Hasta.getDate() == null && jr_btn_remision.isSelected() && jr_btn_fp.isSelected() && jlist_cve.getModel().getSize() > 0 && jlist_desc.getModel().getSize() > 0 && jlist_cve_consulta.getModel().getSize() == 0 && jlist_desc_consulta.getModel().getSize() == 0 ){
            task06Inf = new PBremisionesD();
            task06Inf.execute();
            //JOptionPane.showMessageDialog(this, "Ejecuto opcion 10 Remisiones");
        }else if(jd_Desde.getDate() != null && jd_Hasta.getDate() != null && jr_btn_remision.isSelected() && jr_btn_fp.isSelected() && jlist_cve.getModel().getSize() > 0 && jlist_desc.getModel().getSize() > 0 && jlist_cve_consulta.getModel().getSize() > 0 && jlist_desc_consulta.getModel().getSize() > 0 ){
            task12Inf = new PBremisionesListDH();
            task12Inf.execute();
            //JOptionPane.showMessageDialog(this, "Ejecuto opcion 11 Remisiones");
        }else if(jd_Desde.getDate() != null && jd_Hasta.getDate() == null && jr_btn_remision.isSelected() && jr_btn_fp.isSelected() && jlist_cve.getModel().getSize() > 0 && jlist_desc.getModel().getSize() > 0 && jlist_cve_consulta.getModel().getSize() > 0 && jlist_desc_consulta.getModel().getSize() > 0 ){
            task13Inf = new PBremisionesListD();
            task13Inf.execute();
            //JOptionPane.showMessageDialog(this, "Ejecuto opcion 12 Remisiones");
        }else if (jd_Desde.getDate() != null && jd_Hasta.getDate() != null && jr_btn_facturas.isSelected() && jr_btn_fp.isSelected() && jlist_cve.getModel().getSize() == 0 && jlist_desc.getModel().getSize() == 0 && jlist_cve_consulta.getModel().getSize() == 0 && jlist_desc_consulta.getModel().getSize() == 0 ) {
            task07Inf = new PBfacturasDH();
            task07Inf.execute();
            //JOptionPane.showMessageDialog(this, "Ejecuto opcion 13 Facturas");
        }else if (jd_Desde.getDate() != null && jd_Hasta.getDate() != null && jr_btn_facturas.isSelected() && jr_btn_fp.isSelected() && jlist_cve.getModel().getSize() > 0 && jlist_desc.getModel().getSize() > 0 && jlist_cve_consulta.getModel().getSize() == 0 && jlist_desc_consulta.getModel().getSize() == 0 ) {
            task07Inf = new PBfacturasDH();
            task07Inf.execute();
            //JOptionPane.showMessageDialog(this, "Ejecuto opcion 14 Facturas");
        }else if (jd_Desde.getDate() != null && jd_Hasta.getDate() == null && jr_btn_facturas.isSelected() && jr_btn_fp.isSelected() && jlist_cve.getModel().getSize() == 0 && jlist_desc.getModel().getSize() == 0 && jlist_cve_consulta.getModel().getSize() == 0 && jlist_desc_consulta.getModel().getSize() == 0 ) {
            task08Inf = new PBfacturasD();
            task08Inf.execute();
            //JOptionPane.showMessageDialog(this, "Ejecuto opcion 15 Facturas");
        }else if (jd_Desde.getDate() != null && jd_Hasta.getDate() == null && jr_btn_facturas.isSelected() && jr_btn_fp.isSelected() && jlist_cve.getModel().getSize() > 0 && jlist_desc.getModel().getSize() > 0 && jlist_cve_consulta.getModel().getSize() == 0 && jlist_desc_consulta.getModel().getSize() == 0 ) {
            task08Inf = new PBfacturasD();
            task08Inf.execute();
            //JOptionPane.showMessageDialog(this, "Ejecuto opcion 16 Facturas");
        }else if (jd_Desde.getDate() != null && jd_Hasta.getDate() != null && jr_btn_facturas.isSelected() && jr_btn_fp.isSelected() && jlist_cve.getModel().getSize() > 0 && jlist_desc.getModel().getSize() > 0 && jlist_cve_consulta.getModel().getSize() > 0 && jlist_desc_consulta.getModel().getSize() > 0 ) {
            task14Inf = new PBfacturasListDH();
            task14Inf.execute();
            //JOptionPane.showMessageDialog(this, "Ejecuto opcion 17 Facturas");
        }else if (jd_Desde.getDate() != null && jd_Hasta.getDate() == null && jr_btn_facturas.isSelected() && jr_btn_fp.isSelected() && jlist_cve.getModel().getSize() > 0 && jlist_desc.getModel().getSize() > 0 && jlist_cve_consulta.getModel().getSize() > 0 && jlist_desc_consulta.getModel().getSize() > 0 ) {
            task15Inf = new PBfacturasListD();
            task15Inf.execute();
            //JOptionPane.showMessageDialog(this, "Ejecuto opcion 18 Facturas");
        }
    }//GEN-LAST:event_btnConsultaActionPerformed

    private void btn_imprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_imprimirActionPerformed

        try{
            MessageFormat headerformat = new MessageFormat("Reporte Corte Caja"); //encabezado
            MessageFormat footerformat = new MessageFormat("Page - {0}"); //
            jt_consulta_corte.print(JTable.PrintMode.FIT_WIDTH, headerformat, footerformat);  //imprime la JTable
        } catch (PrinterException ex) {
        Logger.getLogger(jfCorteCaja1.class.getName()).log(Level.SEVERE, null, ex);
    }
    }//GEN-LAST:event_btn_imprimirActionPerformed

    private void btn_menu_principalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_menu_principalActionPerformed
        dispose();
        new MenuPrincipal().setVisible(true);
    }//GEN-LAST:event_btn_menu_principalActionPerformed

    private void btn_exportarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_exportarActionPerformed
       if(this.jt_consulta_corte.getRowCount() == 0)
       {
           JOptionPane.showMessageDialog(null, "No hay datos por exportar", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
           return;
       }
       
       //Creacion del archivo en Excel
       int totalR = jt_consulta_corte.getRowCount();
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
           tb.add(jt_consulta_corte);
           nom.add("Reporte Corte Caja");
           String file = chooser.getSelectedFile().toString().concat(".xls");
           //Aqui se ejecuta el metodo
           try{
               Exporter e = new Exporter(new File(file),tb,nom);
               if(e.export())
               {
                   JOptionPane.showMessageDialog(null, "Total de Registros exportados a Excel: " + totalR, "Reporte Corte Caja", JOptionPane.INFORMATION_MESSAGE);
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
        DefaultTableModel dTable = new DefaultTableModel();
        this.jt_consulta_corte.setModel(dTable);
        while(dTable.getRowCount()>0){
            dTable.removeRow(0);
        }
        this.jl_tt_reg.setText("Total Registros: ");
        jl_sumatoria.setVisible(false);
        ((DefaultListModel)jlist_cve.getModel()).clear();
        ((DefaultListModel)jlist_desc.getModel()).clear();
        ((DefaultListModel)jlist_cve_consulta.getModel()).clear();
        ((DefaultListModel)jlist_desc_consulta.getModel()).clear();

    }//GEN-LAST:event_btn_limpiarActionPerformed

    private void btn_reporteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_reporteActionPerformed
        if (this.jt_consulta_corte.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Ningun Dato por Exportar a PDF", "Mensage", JOptionPane.INFORMATION_MESSAGE);
            return;
        } else {
            int totalR = jt_consulta_corte.getRowCount();
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
                tb.add(jt_consulta_corte);
                nom.add("Corte Caja");
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

    private void jr_btn_fpagoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jr_btn_fpagoActionPerformed
        if (jr_btn_fpago.isSelected()) {
            cambioEstatusPago();
        }
    }//GEN-LAST:event_jr_btn_fpagoActionPerformed

    private void jr_btn_remisionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jr_btn_remisionActionPerformed
        if (jr_btn_remision.isSelected()) {
            cambioEstatus();
        }
    }//GEN-LAST:event_jr_btn_remisionActionPerformed

    private void jr_btn_fpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jr_btn_fpActionPerformed
        if(jr_btn_fp.isSelected())
        {
            cambioEstatusPago();
        }else if(jr_btn_fp.isSelected() == false){
            cambioEstatus();
        }
    }//GEN-LAST:event_jr_btn_fpActionPerformed

    private void jr_btn_facturasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jr_btn_facturasActionPerformed
        if(jr_btn_facturas.isSelected()){
            cambioEstatus();
        }
    }//GEN-LAST:event_jr_btn_facturasActionPerformed

    private void btn_obtenerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_obtenerActionPerformed
        task09Inf = new ProgressCajaInf();
        task09Inf.execute();
        
    }//GEN-LAST:event_btn_obtenerActionPerformed

    DefaultListModel modelList2 = new DefaultListModel();
    DefaultListModel modelList3 = new DefaultListModel();
    private void btn_agregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_agregarActionPerformed
        
        int indix = jlist_cve.getSelectedIndex();
        int indixx = jlist_desc.getSelectedIndex();
        
        if (indix >= 0 || indixx >= 0){
            int indice;
            indice = jlist_desc.getSelectedIndex();
            jlist_cve.setSelectedIndex(indice);
            String idvalor = (String) jlist_cve.getSelectedValue();
            
            
            modelList2.addElement(idvalor);
            jlist_cve_consulta.setModel(modelList2);
            
            modelList3.addElement(jlist_desc.getSelectedValue());
            jlist_desc_consulta.setModel(modelList3);
            
                       
        }else{
            JOptionPane.showMessageDialog(null, "Debe seleccionar un registro", "ERROR", JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_btn_agregarActionPerformed

    private void btn_quitarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_quitarActionPerformed
        int indixc = jlist_cve_consulta.getSelectedIndex();
        int indice = jlist_desc_consulta.getSelectedIndex();
        if(indixc >= 0){
            modelList2.removeElementAt(indixc);
            modelList3.removeElementAt(indixc);
        }else if(indice >=0){
            modelList2.removeElementAt(indice);
            modelList3.removeElementAt(indice);
        }else{
           JOptionPane.showMessageDialog(null, "Debe seleccionar un registro");
        }
    }//GEN-LAST:event_btn_quitarActionPerformed

    private void btn_desahacerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_desahacerActionPerformed
        ((DefaultListModel)jlist_cve.getModel()).clear();
        ((DefaultListModel)jlist_desc.getModel()).clear();
        ((DefaultListModel)jlist_cve_consulta.getModel()).clear();
        ((DefaultListModel)jlist_desc_consulta.getModel()).clear();
    }//GEN-LAST:event_btn_desahacerActionPerformed

    private void jlist_cveValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jlist_cveValueChanged
        int indice;
        indice = jlist_cve.getSelectedIndex();
        jlist_desc.setSelectedIndex(indice);
        
    }//GEN-LAST:event_jlist_cveValueChanged

    private void jlist_cveAncestorMoved(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_jlist_cveAncestorMoved
        BoundedRangeModel modelin = jScrollPane4.getVerticalScrollBar().getModel();
        jScrollPane2.getVerticalScrollBar().setModel(modelin);
    }//GEN-LAST:event_jlist_cveAncestorMoved

    private void jlist_cve_consultaAncestorMoved(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_jlist_cve_consultaAncestorMoved
        BoundedRangeModel modelin = jScrollPane5.getVerticalScrollBar().getModel();
        jScrollPane3.getVerticalScrollBar().setModel(modelin);
    }//GEN-LAST:event_jlist_cve_consultaAncestorMoved

    private void jr_btn_corteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jr_btn_corteActionPerformed
        if (jr_btn_corte.isSelected()) {
            cambioEstatus();
        }
    }//GEN-LAST:event_jr_btn_corteActionPerformed

    
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
                if ("Metal".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(jfCorteCaja1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(jfCorteCaja1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(jfCorteCaja1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(jfCorteCaja1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try{
                    UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
                }catch(Exception e){
                    e.printStackTrace();
                }
                new jfCorteCaja1().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnConsulta;
    private javax.swing.JButton btn_agregar;
    private javax.swing.JButton btn_desahacer;
    private javax.swing.JButton btn_exportar;
    private javax.swing.JButton btn_imprimir;
    private javax.swing.JButton btn_limpiar;
    private javax.swing.JButton btn_menu_principal;
    private javax.swing.JButton btn_obtener;
    private javax.swing.JButton btn_quitar;
    private javax.swing.JButton btn_reporte;
    private javax.swing.JButton btn_salir;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private com.toedter.calendar.JDateChooser jd_Desde;
    private com.toedter.calendar.JDateChooser jd_Hasta;
    private javax.swing.JLabel jl_cve;
    private javax.swing.JLabel jl_cve_con;
    private javax.swing.JLabel jl_cve_fp;
    private javax.swing.JLabel jl_desde;
    private javax.swing.JLabel jl_etiqueta;
    private javax.swing.JLabel jl_fp_desc;
    private javax.swing.JLabel jl_hasta;
    private javax.swing.JLabel jl_sumatoria;
    private javax.swing.JLabel jl_titulo;
    private javax.swing.JLabel jl_tt_reg;
    private javax.swing.JList jlist_cve;
    private javax.swing.JList jlist_cve_consulta;
    private javax.swing.JList<movCaja> jlist_desc;
    private javax.swing.JList jlist_desc_consulta;
    private javax.swing.JProgressBar jp_bar;
    private javax.swing.JRadioButton jr_btn_corte;
    private javax.swing.JRadioButton jr_btn_facturas;
    private javax.swing.JRadioButton jr_btn_fp;
    private javax.swing.JRadioButton jr_btn_fpago;
    private javax.swing.JRadioButton jr_btn_remision;
    private javax.swing.JTable jt_consulta_corte;
    private javax.swing.ButtonGroup rbtnGroupCaja;
    // End of variables declaration//GEN-END:variables
    
    class PBCorteDiaDH extends SwingWorker<Void, Void> {

        @Override
        protected Void doInBackground() throws Exception {
            jp_bar.setVisible(true);
            jp_bar.setIndeterminate(true);
            jl_etiqueta.setVisible(true);
            corteDiaDH();
            return null;
        }

        @Override
        protected void done() {
            jp_bar.setVisible(false);
            jl_etiqueta.setVisible(false);
        }

    }
    
    class PBCorteDiaD extends SwingWorker<Void, Void> {

        @Override
        protected Void doInBackground() throws Exception {
            jp_bar.setVisible(true);
            jp_bar.setIndeterminate(true);
            jl_etiqueta.setVisible(true);
            corteDiaD();
            return null;
        }

        @Override
        protected void done() {
            jp_bar.setVisible(false);
            jl_etiqueta.setVisible(false);
        }

    }
    
    class PBformasPagosTDH extends SwingWorker<Void, Void> {

        @Override
        protected Void doInBackground() throws Exception {
            jp_bar.setVisible(true);
            jp_bar.setIndeterminate(true);
            jl_etiqueta.setVisible(true);
            formasPagosTDH();
            //JOptionPane.showMessageDialog(null, "Ejecutandose Metodo de la primera forma de pago");
            return null;
        }

        @Override
        protected void done() {
            jp_bar.setVisible(false);
            jl_etiqueta.setVisible(false);
        }

    }
    
    class PBformasPagosTD extends SwingWorker<Void, Void> {

        @Override
        protected Void doInBackground() throws Exception {
            jp_bar.setVisible(true);
            jp_bar.setIndeterminate(true);
            jl_etiqueta.setVisible(true);
            formasPagosTD();
            //JOptionPane.showMessageDialog(null, "Ejecutandose Metodo de la primera forma de pago");
            return null;
        }

        @Override
        protected void done() {
            jp_bar.setVisible(false);
            jl_etiqueta.setVisible(false);
        }

    }
    
    class PBremisionesDH extends SwingWorker<Void, Void> {

        @Override
        protected Void doInBackground() throws Exception {
            jp_bar.setVisible(true);
            jp_bar.setIndeterminate(true);
            jl_etiqueta.setVisible(true);
            remisionesDH();
            //JOptionPane.showMessageDialog(null, "Ejecutandose Metodo de la primera forma de pago");
            return null;
        }

        @Override
        protected void done() {
            jp_bar.setVisible(false);
            jl_etiqueta.setVisible(false);
        }

    }
    
    class PBremisionesD extends SwingWorker<Void, Void> {

        @Override
        protected Void doInBackground() throws Exception {
            jp_bar.setVisible(true);
            jp_bar.setIndeterminate(true);
            jl_etiqueta.setVisible(true);
            remisionesD();
            //JOptionPane.showMessageDialog(null, "Ejecutandose Metodo de la primera forma de pago");
            return null;
        }

        @Override
        protected void done() {
            jp_bar.setVisible(false);
            jl_etiqueta.setVisible(false);
        }

    }
    
    class PBfacturasDH extends SwingWorker<Void, Void> {

        @Override
        protected Void doInBackground() throws Exception {
            jp_bar.setVisible(true);
            jp_bar.setIndeterminate(true);
            jl_etiqueta.setVisible(true);
            facturasDH();
            //JOptionPane.showMessageDialog(null, "Ejecutandose Metodo de la primera forma de pago");
            return null;
        }

        @Override
        protected void done() {
            jp_bar.setVisible(false);
            jl_etiqueta.setVisible(false);
        }

    }
    
    
    class PBfacturasD extends SwingWorker<Void, Void> {

        @Override
        protected Void doInBackground() throws Exception {
            jp_bar.setVisible(true);
            jp_bar.setIndeterminate(true);
            jl_etiqueta.setVisible(true);
            facturasD();
            //JOptionPane.showMessageDialog(null, "Ejecutandose Metodo de la primera forma de pago");
            return null;
        }

        @Override
        protected void done() {
            jp_bar.setVisible(false);
            jl_etiqueta.setVisible(false);
        }

    }
    
    class ProgressCajaInf extends SwingWorker<Void, Void> {

        @Override
        protected Void doInBackground() throws Exception {
            barraprogreso barra = new barraprogreso();
            barra.setVisible(true);
            formaPago fpmethod = new formaPago();
            fpmethod.llenarprecve();
            fpmethod.llenarprecve();
            jlist_cve.setModel(fpmethod.llenarprecve());
            jlist_desc.setModel(fpmethod.llenarpredesc());
            barra.setVisible(false);
            return null;
        }

        @Override
        protected void done() {

        }

    }
    
    class PBformaPagoListDH extends SwingWorker<Void, Void> {

        @Override
        protected Void doInBackground() throws Exception {
            jp_bar.setVisible(true);
            jp_bar.setIndeterminate(true);
            jl_etiqueta.setVisible(true);
            //obtenCve();
            formaPagoListDH();
            //JOptionPane.showMessageDialog(null, "Ejecutandose Metodo de la primera forma de pago");
            return null;
        }

        @Override
        protected void done() {
            jp_bar.setVisible(false);
            jl_etiqueta.setVisible(false);
        }

    }
    
    class PBformaPagoListD extends SwingWorker<Void, Void> {

        @Override
        protected Void doInBackground() throws Exception {
            jp_bar.setVisible(true);
            jp_bar.setIndeterminate(true);
            jl_etiqueta.setVisible(true);
            //obtenCve();
            formaPagoListD();
            //JOptionPane.showMessageDialog(null, "Ejecutandose Metodo de la primera forma de pago");
            return null;
        }

        @Override
        protected void done() {
            jp_bar.setVisible(false);
            jl_etiqueta.setVisible(false);
        }

    }
    
    class PBremisionesListDH extends SwingWorker<Void, Void> {

        @Override
        protected Void doInBackground() throws Exception {
            jp_bar.setVisible(true);
            jp_bar.setIndeterminate(true);
            jl_etiqueta.setVisible(true);
            //obtenCve();
            remisionesListDH();
            //JOptionPane.showMessageDialog(null, "Ejecutandose Metodo de la primera forma de pago");
            return null;
        }

        @Override
        protected void done() {
            jp_bar.setVisible(false);
            jl_etiqueta.setVisible(false);
        }

    }
    
    class PBremisionesListD extends SwingWorker<Void, Void> {

        @Override
        protected Void doInBackground() throws Exception {
            jp_bar.setVisible(true);
            jp_bar.setIndeterminate(true);
            jl_etiqueta.setVisible(true);
            //obtenCve();
            remisionesListD();
            //JOptionPane.showMessageDialog(null, "Ejecutandose Metodo de la primera forma de pago");
            return null;
        }

        @Override
        protected void done() {
            jp_bar.setVisible(false);
            jl_etiqueta.setVisible(false);
        }

    }

    class PBfacturasListDH extends SwingWorker<Void, Void> {

        @Override
        protected Void doInBackground() throws Exception {
            jp_bar.setVisible(true);
            jp_bar.setIndeterminate(true);
            jl_etiqueta.setVisible(true);
            //obtenCve();
            facturasListDH();
            //JOptionPane.showMessageDialog(null, "Ejecutandose Metodo de la primera forma de pago");
            return null;
        }

        @Override
        protected void done() {
            jp_bar.setVisible(false);
            jl_etiqueta.setVisible(false);
        }

    }

    class PBfacturasListD extends SwingWorker<Void, Void> {

        @Override
        protected Void doInBackground() throws Exception {
            jp_bar.setVisible(true);
            jp_bar.setIndeterminate(true);
            jl_etiqueta.setVisible(true);
            //obtenCve();
            facturasListD();
            //JOptionPane.showMessageDialog(null, "Ejecutandose Metodo de la primera forma de pago");
            return null;
        }

        @Override
        protected void done() {
            jp_bar.setVisible(false);
            jl_etiqueta.setVisible(false);
        }

    }

    
}
