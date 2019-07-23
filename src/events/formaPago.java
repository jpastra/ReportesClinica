/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package events;

import conexion.Conexiondb;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;

/**
 *
 * @author Viviana
 */
public class formaPago {
private String precve;
private String predesc;
ResultSet rs;

    public formaPago() {
    }

    public formaPago(String precve, String predesc, String premov) {
        this.precve = precve;
        this.predesc = predesc;
    }

    public String getPrecve() {
        return precve;
    }

    public void setPrecve(String precve) {
        this.precve = precve;
    }

    public String getPredesc() {
        return predesc;
    }

    public void setPredesc(String predesc) {
        this.predesc = predesc;
    }

    public DefaultListModel llenarprecve() {
        DefaultListModel modelo = new DefaultListModel();
        try {
            rs = Conexiondb.Consulta("SELECT PRECVE, PREDESC FROM CONCAT WHERE PREMOV='-' ORDER BY PRECVE");

            while (rs.next()) {
                String value = rs.getString(1);
                String value1 = rs.getString(2);
                //String value3 = value + " - " + value1;
                modelo.addElement(value);
                //modelo1.addElement(values2);

            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return modelo;
    }
    
    public DefaultListModel llenarpredesc() {
        DefaultListModel modelo = new DefaultListModel();
        try {
            rs = Conexiondb.Consulta("SELECT PRECVE, PREDESC FROM CONCAT WHERE PREMOV='-' ORDER BY PRECVE");

            while (rs.next()) {
                String value = rs.getString(1);
                String value1 = rs.getString(2);
                //String value3 = value + " - " + value1;
                modelo.addElement(value1);
                //modelo1.addElement(values2);

            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return modelo;
    }
    
    public String toString() {
        return precve + " - " +predesc;
    }
    
}
