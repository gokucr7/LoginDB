/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

public class Cconexion {
    Connection conectar;
    
    String usuario="root";
    String contrasenia="12345678";
    String bd="login";
    String ip="localhost";
    String puerto="3306";
    
    String cadena = "jdbc:mysql://"+ip+":"+puerto+"/"+bd;
    
    public Connection estableceConexion(){
    
        try {
            
            Class.forName("com.mysql.jdbc.Driver");
            conectar = DriverManager.getConnection(cadena,usuario,contrasenia);
            JOptionPane.showMessageDialog(null,"Se conectó correcta a la Base de DATOS");
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Problemas en la conexion"+ e.toString());
        }
        return conectar;
    }
    
       
}
