
package Clases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import javax.swing.JOptionPane;


public class Cconexion {
    //atributos
    private static final String URL = "jdbc:mysql://localhost:3306/login";
    private static final String USER = "root";
    private static final String PASSWORD = "12345678";
    //metodo
    public static Connection estableceConexion() {
        Connection con = null;
        try {
            con = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (Exception e) {
            System.err.println("Error al establecer la conexión: " + e.getMessage());
        }
        return con;
    }
    
    public void guardarUsuario(String user, String password) {
    Connection conexion = estableceConexion();
    if (conexion != null) {
        String sql = "INSERT INTO usuarios (ingresoUsuario, ingresoContrasenia, tipo_de_usuario) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setString(1, user);
            pstmt.setString(2, password);
            pstmt.setString(3, "maestro"); // Asigna "maestro" como el tipo de usuario

            int resultado = pstmt.executeUpdate();
            if (resultado > 0) {
                JOptionPane.showMessageDialog(null, "Usuario registrado como 'maestro' correctamente");
            } else {
                JOptionPane.showMessageDialog(null, "Error al registrar el usuario");
            }
        } catch (SQLException e) {
            System.err.println("Error al ejecutar la consulta: " + e.getMessage());
        } finally {
            try {
                conexion.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
    } else {
        JOptionPane.showMessageDialog(null, "Error al establecer la conexión");
    }
}

}

