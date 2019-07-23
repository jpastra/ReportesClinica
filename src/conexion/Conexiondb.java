/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Viviana
 */
public class Conexiondb {
    
    /* Constructor */
    public Conexiondb(){
        
    }
    
    static Connection conn = null;
    
    private static String user="testdb";
    private static String pass="testmx";
    private static String url="jdbc:odbc:OracleXE";

    
  
    /* Metodo de conexion */
    public static Connection Init_conn()
    {
        
        try{
            
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            
            conn = DriverManager.getConnection(url, user, pass);
            
            if(conn != null)
                return null;
            
        }
        catch(ClassNotFoundException ex)
        {
            JOptionPane.showMessageDialog(null, ex, "Error en Conexion a la DB" + ex.getMessage(), JOptionPane.ERROR_MESSAGE);
        }
        catch(SQLException ex)
        {
            JOptionPane.showMessageDialog(null, ex, "Error en Conexion a la BD" + ex.getMessage(), JOptionPane.ERROR_MESSAGE);
        }
        catch(Exception ex)
        {
            JOptionPane.showMessageDialog(null, ex, "Error en Conexion a la BD" + ex.getMessage(), JOptionPane.ERROR_MESSAGE);
        }
        finally{
            return conn;
        }
    }
    
    public static ResultSet Consulta(String consulta)
    {
       Connection conn = Init_conn();
       Statement st;
       
       try{
           st = conn.createStatement();
           ResultSet rs = st.executeQuery(consulta);
           return rs;
       }
       catch(SQLException ex){
           Logger.getLogger(Conexiondb.class.getName()).log(Level.SEVERE, null, ex);
           return null;
       }
    }
    
    public void Actualiza(int desc, String pacexp, String cve) {
        String sql = "UPDATE DESPAC SET DSCTO = ? "
                + "WHERE PACNEXP = ?  AND PRECVE = ?";

        try {
            Connection conn = Init_conn();
            PreparedStatement ps = null;
            conn.setAutoCommit(false);
            ps = conn.prepareStatement(sql);
            ps.setInt(1, desc);
            ps.setString(2, pacexp);
            ps.setString(3, cve);
            ps.executeUpdate();
            conn.commit();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    } 
    
}
