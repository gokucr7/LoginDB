
package Clases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import javax.swing.JOptionPane;
import java.sql.ResultSet;



public class Cconexion {
    //atributos
    private static final String URL = "jdbc:mysql://viaduct.proxy.rlwy.net:29444/railway";
                                       //jdbc:mysql://viaduct.proxy.rlwy.net:29444/railway          
                                       //jdbc:mysql://localhost:3306/login
    private static final String USER = "root";
    private static final String PASSWORD = "153d2A1Gd3f-h5EHF6ha-FheaegA3gD3";
                                            //153d2A1Gd3f-h5EHF6ha-FheaegA3gD3
                                            //12345678
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
        // Check if the username already exists
        if (usuarioExiste(user, conexion)) {
            JOptionPane.showMessageDialog(null, "El nombre de usuario ya está registrado");
            return;
        }

        // Validate password (at least 8 characters and contains a special symbol)
        if (!validarContrasenia(password)) {
            JOptionPane.showMessageDialog(null, "La contraseña no es suficientemente segura.\nDebe tener al menos 8 caracteres y contener un símbolo especial.");
            return;
        }

        String sql = "INSERT INTO Usuarios (ingresoUsuario, ingresoContrasenia, tipo_de_usuario) VALUES (?, ?, ?)";
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

// Helper method to check if the username already exists
private boolean usuarioExiste(String user, Connection conexion) {
    String sql = "SELECT * FROM Usuarios WHERE ingresoUsuario = ?";
    try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
        pstmt.setString(1, user);
        ResultSet rs = pstmt.executeQuery();
        return rs.next(); // Returns true if the result set has at least one row (username exists)
    } catch (SQLException e) {
        System.err.println("Error al ejecutar la consulta: " + e.getMessage());
        return false; // Return false in case of an error
    }
}

// Helper method to validate password
public boolean validarContrasenia(String password) {
    // Check if the password has at least 8 characters and contains a special symbol
    return password.length() >= 8 && password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\",./<>?].*");
}





}

