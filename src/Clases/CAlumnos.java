
package Clases;

import java.awt.HeadlessException;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.sql.CallableStatement;
import java.sql.Statement;
import javax.swing.JComboBox;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;


public class CAlumnos {
    int codigo;
    String nombreALumnos;
    String apellidosAlumnos;
    double promedio;
    String carrera;

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNombreALumnos() {
        return nombreALumnos;
    }

    public void setNombreALumnos(String nombreALumnos) {
        this.nombreALumnos = nombreALumnos;
    }

    public String getApellidosAlumnos() {
        return apellidosAlumnos;
    }

    public void setApellidosAlumnos(String apellidosAlumnos) {
        this.apellidosAlumnos = apellidosAlumnos;
    }
    
    public double getPromedio() {
    return promedio;
}

    public void setPromedio(double promedio) {
        this.promedio = promedio;
    }

    public String getCarrera() {


    return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }
    
    public void InsertarAlumno (JTextField paramNombres, JTextField paramApellidos, JSpinner paramPromedio, JComboBox<String> paramCarrera) {
        
    setNombreALumnos(paramNombres.getText());
    setApellidosAlumnos(paramApellidos.getText());
    setPromedio((double) paramPromedio.getValue());
    setCarrera(paramCarrera.getSelectedItem().toString());

    String consulta = "INSERT INTO Alumnos (nombres, apellidos, promedio, carrera) VALUES (?, ?, ?, ?);";

    try {
        CallableStatement cs = Cconexion.estableceConexion().prepareCall(consulta);
        cs.setString(1, getNombreALumnos());
        cs.setString(2, getApellidosAlumnos());
        cs.setDouble(3, getPromedio());
        cs.setString(4, getCarrera());

        cs.execute();

        JOptionPane.showMessageDialog(null, "Se insertó correctamente el alumno");
    } catch (HeadlessException | SQLException e) {
        JOptionPane.showMessageDialog(null, "No se insertó correctamente el alumno, error: " + e.toString());
    }
        
    }
    
    
    public void MostrarAlumnos (JTable paramTablaTotalAlumnos) {
    DefaultTableModel modelo = new DefaultTableModel();
    TableRowSorter<TableModel> OrdenarTabla = new TableRowSorter<>(modelo);
    
    paramTablaTotalAlumnos.setRowSorter(OrdenarTabla);

    // Defino las columnas de la tabla
    modelo.addColumn("Id");
    modelo.addColumn("Nombres");
    modelo.addColumn("Apellidos");
    modelo.addColumn("Promedio"); 
    modelo.addColumn("Carrera"); 

    paramTablaTotalAlumnos.setModel(modelo);

    String sql = "select * from Alumnos;";
    String[] datos = new String[5]; 

    Statement st;
    try {
        st = Cconexion.estableceConexion().createStatement();
        ResultSet rs = st.executeQuery(sql);

        while (rs.next()) {
            datos[0] = rs.getString(1);
            datos[1] = rs.getString(2);
            datos[2] = rs.getString(3);
            datos[3] = rs.getString(4); // Promedio
            datos[4] = rs.getString(5); // Carrera

            modelo.addRow(datos);
        }

        paramTablaTotalAlumnos.setModel(modelo);

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "No se pudo mostrar los registros, error: " + e.toString());
    }
    
    


}

        public void SeleccionarAlumno(JTable paramTablaAlumnos, JTextField paramId, JTextField paramNombres, JTextField paramApellidos, JSpinner paramPromedio, JComboBox<String> paramCarrera) {
    try {
        int fila = paramTablaAlumnos.getSelectedRow();
        if (fila >= 0) {
            paramId.setText(paramTablaAlumnos.getValueAt(fila, 0).toString());
            paramNombres.setText(paramTablaAlumnos.getValueAt(fila, 1).toString());
            paramApellidos.setText(paramTablaAlumnos.getValueAt(fila, 2).toString());

            
            String promedioStr = paramTablaAlumnos.getValueAt(fila, 3).toString();
            double promedio = Double.parseDouble(promedioStr);
            paramPromedio.setValue(promedio);

            // Para establecer el valor seleccionado en el JComboBox
            String carrera = paramTablaAlumnos.getValueAt(fila, 4).toString();
            paramCarrera.setSelectedItem(carrera);
        } else {
            JOptionPane.showMessageDialog(null, "Fila no seleccionada");
        }
    } catch (HeadlessException | NumberFormatException e) {
        JOptionPane.showMessageDialog(null, "Error de selección, error: " + e.toString());
    }
}

        
    public void ModificarAlumnos(JTextField paramCodigo, JTextField paramNombres, JTextField paramApellidos, JSpinner paramPromedio, JComboBox<String> paramCarrera) {
    
    setCodigo(Integer.parseInt(paramCodigo.getText()));
    setNombreALumnos(paramNombres.getText());
    setApellidosAlumnos(paramApellidos.getText());
    setPromedio((double) paramPromedio.getValue());
    setCarrera(paramCarrera.getSelectedItem().toString());

    String consulta = "UPDATE Alumnos SET nombres = ?, apellidos = ?, promedio = ?, carrera = ? WHERE id = ?;";
    
    try {
        CallableStatement cs = Cconexion.estableceConexion().prepareCall(consulta);
        
        cs.setString(1, getNombreALumnos());
        cs.setString(2, getApellidosAlumnos());
        cs.setDouble(3, getPromedio());
        cs.setString(4, getCarrera());
        cs.setInt(5, getCodigo());

        cs.execute();

        JOptionPane.showMessageDialog(null, "Modificación exitosa");
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "No se pudo modificar, error: " + e.toString());
    }
}
        
        
        public void EliminarAlumnos(JTextField paramCodigo) {
            setCodigo(Integer.parseInt(paramCodigo.getText()));
            
            
            String consulta = "DELETE from Alumnos WHERE id = ?;";
            try {
                CallableStatement cs  = Cconexion.estableceConexion().prepareCall(consulta);
                
                cs.setInt(1, getCodigo());
                cs.execute();
                
                JOptionPane.showMessageDialog(null, "Modificacion exitosa ");
                
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "   No se modifico, error:  ");
            }
        }

        public void MostrarAlumnosIngenieriaSistemas(JTable paramTabla) {
        DefaultTableModel modelo = new DefaultTableModel();

        // Defino las columnas de la tabla
        modelo.addColumn("Id");
        modelo.addColumn("Nombres");
        modelo.addColumn("Apellidos");
        modelo.addColumn("Promedio");
        modelo.addColumn("Carrera");

        paramTabla.setModel(modelo);

        String sql = "SELECT * FROM Alumnos WHERE carrera = 'Ingenieria de sistemas';";
        String[] datos = new String[5];

        Statement st;
        try {
            st = Cconexion.estableceConexion().createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
                datos[2] = rs.getString(3);
                datos[3] = rs.getString(4);
                datos[4] = rs.getString(5);

                modelo.addRow(datos);
            }

            paramTabla.setModel(modelo);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al mostrar los registros de Ingeniería de Sistemas: " + e.getMessage());
        }
    }
    
    public void MostrarAlumnosIngenieriaElectronica(JTable paramTabla) {
        DefaultTableModel modelo = new DefaultTableModel();

        // Define las columnas de la tabla
        modelo.addColumn("Id");
        modelo.addColumn("Nombres");
        modelo.addColumn("Apellidos");
        modelo.addColumn("Promedio");
        modelo.addColumn("Carrera");

        paramTabla.setModel(modelo);

        String sql = "SELECT * FROM Alumnos WHERE carrera = 'Ingenieria electronica';";
        String[] datos = new String[5];

        Statement st;
        try {
            st = Cconexion.estableceConexion().createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
                datos[2] = rs.getString(3);
                datos[3] = rs.getString(4);
                datos[4] = rs.getString(5);

                modelo.addRow(datos);
            }

            paramTabla.setModel(modelo);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al mostrar los registros de Ingeniería Electronica: " + e.getMessage());
        }
    }
    
    public void MostrarAlumnosAdministracionEmpresas(JTable paramTabla) {
        DefaultTableModel modelo = new DefaultTableModel();

        // Define las columnas de la tabla
        modelo.addColumn("Id");
        modelo.addColumn("Nombres");
        modelo.addColumn("Apellidos");
        modelo.addColumn("Promedio");
        modelo.addColumn("Carrera");

        paramTabla.setModel(modelo);

        String sql = "SELECT * FROM Alumnos WHERE carrera = 'Administracion de empresas';";
        String[] datos = new String[5];

        Statement st;
        try {
            st = Cconexion.estableceConexion().createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
                datos[2] = rs.getString(3);
                datos[3] = rs.getString(4);
                datos[4] = rs.getString(5);

                modelo.addRow(datos);
            }

            paramTabla.setModel(modelo);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al mostrar los registros de Ingeniería de Sistemas: " + e.getMessage());
        }
    }
    
     public List<Double> getPromediosIngenieriaSistemas() {
        List<Double> promedios = new ArrayList<>();
        Connection conexion = null;

        try {
            conexion = Cconexion.estableceConexion(); // Establece la conexión usando la clase Cconexion.

            String consulta = "SELECT promedio FROM Alumnos WHERE carrera = 'Ingeniería de Sistemas'";
            try (PreparedStatement statement = conexion.prepareStatement(consulta);
                 ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    double promedio = resultSet.getDouble("promedio");
                    promedios.add(promedio);
                }
            }
        } catch (SQLException e) {
        } finally {
            if (conexion != null) {
                try {
                    conexion.close();
                } catch (SQLException e) {
                }
            }
        }

        return promedios;
    }
    
    public List<Double> getPromediosIngenieriaElectronica() {
        List<Double> promedios = new ArrayList<>();
        Connection conexion = null;

        try {
            conexion = Cconexion.estableceConexion(); // Establece la conexión usando la clase Cconexion.

            String consulta = "SELECT promedio FROM Alumnos WHERE carrera = 'Ingeniería electronica'";
            try (PreparedStatement statement = conexion.prepareStatement(consulta);
                 ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    double promedio = resultSet.getDouble("promedio");
                    promedios.add(promedio);
                }
            }
        } catch (SQLException e) {
        } finally {
            if (conexion != null) {
                try {
                    conexion.close();
                } catch (SQLException e) {
                }
            }
        }

        return promedios;
    }
    
    public List<Double> getPromediosAdministracionEmpresas() {
        List<Double> promedios = new ArrayList<>();
        Connection conexion = null;

        try {
            conexion = Cconexion.estableceConexion(); // Establece la conexión usando la clase Cconexion.

            String consulta = "SELECT promedio FROM Alumnos WHERE carrera = 'Administracion de empresas'";
            try (PreparedStatement statement = conexion.prepareStatement(consulta);
                 ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    double promedio = resultSet.getDouble("promedio");
                    promedios.add(promedio);
                }
            }
        } catch (SQLException e) {
        } finally {
            if (conexion != null) {
                try {
                    conexion.close();
                } catch (SQLException e) {
                }
            }
        }

        return promedios;
    }
  

    public static double calcularMedia(List<Double> datos) {
        return datos.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
    }

    public static double calcularModa(List<Double> datos) {
    if (datos.isEmpty()) {
        return 0.0; // Si no hay datos, la moda es 0.
    }

    // Crear una lista para almacenar los valores modales.
    List<Double> modas = new ArrayList<>();

    // Crear un mapa para contar la frecuencia de cada valor.
    Map<Double, Integer> frecuencias = new HashMap<>();

    // Encontrar la frecuencia máxima.
    int frecuenciaMaxima = 1;
    for (double dato : datos) {
        int frecuencia = frecuencias.getOrDefault(dato, 0) + 1;
        frecuencias.put(dato, frecuencia);
        frecuenciaMaxima = Math.max(frecuenciaMaxima, frecuencia);
    }

    // Encontrar los valores con frecuencia máxima.
    for (Map.Entry<Double, Integer> entry : frecuencias.entrySet()) {
        if (entry.getValue() == frecuenciaMaxima) {
            modas.add(entry.getKey());
        }
    }

    if (modas.size() == 1) {
        return modas.get(0); // Si solo hay una moda, retornarla.
    } else {
        return 0.0; // Si hay múltiples modas, retornar 0 o manejarlo según tus necesidades.
    }
}


    public static double calcularMediana(List<Double> datos) {
        Collections.sort(datos);
        int n = datos.size();
        if (n % 2 == 0) {
            return (datos.get(n / 2 - 1) + datos.get(n / 2)) / 2.0;
        } else {
            return datos.get(n / 2);
        }
    }

    public static double calcularCuartil1(List<Double> datos) {
        Collections.sort(datos);
        int n = datos.size();
        int pos = (int) Math.ceil(0.25 * (n + 1)) - 1;
        return datos.get(pos);
    }

    public static double calcularCuartil3(List<Double> datos) {
        Collections.sort(datos);
        int n = datos.size();
        int pos = (int) Math.ceil(0.75 * (n + 1)) - 1;
        return datos.get(pos);
    }

    public static double calcularDesviacionMedia(List<Double> datos) {
        double media = calcularMedia(datos);
        double sum = 0.0;
        for (double dato : datos) {
            sum += Math.abs(dato - media);
        }
        return sum / datos.size();
    }

    public static double calcularVarianza(List<Double> datos) {
        DescriptiveStatistics stats = new DescriptiveStatistics();
        for (double dato : datos) {
            stats.addValue(dato);
        }
        return stats.getVariance();
    }

    public static double calcularDesviacionTipica(List<Double> datos) {
        return Math.sqrt(calcularVarianza(datos));
    }

    public static double calcularCoeficienteVariacion(List<Double> datos) {
        double media = calcularMedia(datos);
        double desviacionTipica = calcularDesviacionTipica(datos);
        return (desviacionTipica / media) * 100.0;
    }

    public static double calcularAsimetria(List<Double> datos) {
        DescriptiveStatistics stats = new DescriptiveStatistics();
        for (double dato : datos) {
            stats.addValue(dato);
        }
        return stats.getSkewness();
    }

    public static double calcularCurtosis(List<Double> datos) {
        DescriptiveStatistics stats = new DescriptiveStatistics();
        for (double dato : datos) {
            stats.addValue(dato);
        }
        return stats.getKurtosis();
    }

    

        }
        
    
        