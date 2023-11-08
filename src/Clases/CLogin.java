
package Clases;


import Formularios.FormMenuPrincipal;
import Formularios.FormMenuPrincipal2;
import java.awt.HeadlessException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
public class CLogin {
    // Add a static variable to store the logged-in user type
    private static String TipoUsuarioAdentro;
    //metodo
     public String validaUsuario(JTextField usuario, JPasswordField contrasenia){
    
        
        try {
        ResultSet rs = null;
        PreparedStatement ps = null;

        String consulta = "select * from Usuarios where Usuarios.ingresoUsuario = ? and Usuarios.ingresoContrasenia = ?;";
        ps = Cconexion.estableceConexion().prepareStatement(consulta);

        String contra = String.valueOf(contrasenia.getPassword());

        ps.setString(1, usuario.getText());
        ps.setString(2, contra);

        rs = ps.executeQuery();

        if (rs.next()) {
            // Set the logged user type here
            TipoUsuarioAdentro = rs.getString("tipo_de_usuario");

            String tipoUsuario = rs.getString("tipo_de_usuario");

            if ("administrador".equals(tipoUsuario)) {
                // Redirect to FormMenuPrincipal (for administrators)
                FormMenuPrincipal objetoMenu = new FormMenuPrincipal();
                objetoMenu.setVisible(true);
            } else if ("maestro".equals(tipoUsuario)) {
                // Redirect to FormMenuPrincipal2 (for teachers)
                FormMenuPrincipal2 objetoMenu2 = new FormMenuPrincipal2();
                objetoMenu2.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "Tipo de usuario no reconocido");
            }
        } else {
            JOptionPane.showMessageDialog(null, "El Usuario es INCORRECTO, VUELVA A INTENTAR");
        }
    } catch (HeadlessException | SQLException e) {
        JOptionPane.showMessageDialog(null, "ERROR: " + e.toString());
    }
    return null;
    }
    // Add a static method to retrieve the logged user type
    public static String getTipoUsuarioAdentro() {
        return TipoUsuarioAdentro;
    }
}
