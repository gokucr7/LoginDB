
package Clases;

import java.sql.Connection;
import java.sql.DriverManager;


public class Cconexion {
    private static final String URL = "jdbc:mysql://localhost:3306/login";
    private static final String USER = "root";
    private static final String PASSWORD = "12345678";

    public static Connection estableceConexion() {
        Connection con = null;
        try {
            con = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (Exception e) {
            System.err.println("Error al establecer la conexión: " + e.getMessage());
        }
        return con;
    }
    
       
}
