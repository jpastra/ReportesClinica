/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package events;

import javax.swing.JComboBox;
import conexion.Conexiondb;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Viviana
 */
public class movCaja {
private String precve;
private String predesc;
private String premov;
ResultSet rs;


    public movCaja() {
    }

    public movCaja(String precve, String predesc, String premov) {
        this.precve = precve;
        this.predesc = predesc;
        this.premov = premov;
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

    public String getPremov() {
        return premov;
    }

    public void setPremov(String premov) {
        this.premov = premov;
    }

 
    public void mostrarCve(JComboBox<movCaja> jcb_cve){
        try{
            rs = Conexiondb.Consulta("SELECT precve, predesc, premov FROM CONCAT WHERE PREMOV='-' ORDER BY PRECVE");
            
            while(rs.next()){
                jcb_cve.addItem(
                        new movCaja(
                                     rs.getString("precve"),
                                     rs.getString("predesc"),
                                     rs.getString("premov")
                        ));
            }
        }catch(Exception ex){
            Logger.getLogger(movCaja.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "ERROR AL MOSTRAR LAS CLAVES");
        }
    }
    
    public String toString() {
        return precve + " - " +predesc;
    }

}
