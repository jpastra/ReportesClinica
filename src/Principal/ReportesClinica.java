/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Principal;

//import conexion.Conexiondb;
//import javax.swing.JOptionPane;
import views.MenuPrincipal;
import views.jfPassword;

/**
 *
 * @author Viviana
 */
public class ReportesClinica {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        /*Conexiondb c = new Conexiondb();
        
        if(c.Init_conn() != null)
            JOptionPane.showMessageDialog(null, "Conexion Correcta");
        else
            JOptionPane.showMessageDialog(null, "Conexion Erronea");*/
        
        //new MenuPrincipal().setVisible(true);
        new jfPassword().setVisible(true);
        
    }
    
}
