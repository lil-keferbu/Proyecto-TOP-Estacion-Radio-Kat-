package gestion_ingresos_radio_kat.UI.Propietarios.Comisiones.Logica.Modelo.AccesoDatos;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class UnidadDAO {

    private final String URL = "jdbc:mysql://localhost:3310/registrounidades";
    private final String USER = "root";
    private final String PASSWORD = "";

    public Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    /**
     * Registra una unidad en el sistema. Se inserta el propietario y luego la
     * unidad con los IDs correspondientes de los catálogos.
     */
    public boolean registrarUnidad(String placa, String nombreProp, String numComercial, String numTelefono, String tipoUnidad, String estadoUnidad) {
        String sqlInsertPropietario = "INSERT INTO Propietario (nombre, telefono) VALUES (?, ?)";
        String sqlSelectTipo = "SELECT id_tipo_unidad FROM TipoUnidad WHERE nombre_tipo = ?";
        String sqlSelectEstado = "SELECT id_estado_unidad FROM EstadoUnidad WHERE nombre_estado = ?";
        String sqlInsertUnidad = "INSERT INTO Unidad (placa, id_propietario, numero_comercial, id_tipo_unidad, id_estado_unidad) VALUES (?, ?, ?, ?, ?)";

        Connection conn = null;
        try {
            conn = conectar();
            // Iniciar transacción
            conn.setAutoCommit(false);

            // 1. Insertar al Propietario
            int idPropietario;
            try (PreparedStatement stmtProp = conn.prepareStatement(sqlInsertPropietario, Statement.RETURN_GENERATED_KEYS)) {
                stmtProp.setString(1, nombreProp);
                stmtProp.setString(2, numTelefono);
                int filas = stmtProp.executeUpdate();
                if (filas == 0) {
                    conn.rollback();
                    return false;
                }
                try (ResultSet rs = stmtProp.getGeneratedKeys()) {
                    if (rs.next()) {
                        idPropietario = rs.getInt(1);
                    } else {
                        conn.rollback();
                        return false;
                    }
                }
            }

            // 2. Obtener el ID del Tipo de Unidad
            int idTipo;
            try (PreparedStatement stmtTipo = conn.prepareStatement(sqlSelectTipo)) {
                stmtTipo.setString(1, tipoUnidad);
                try (ResultSet rs = stmtTipo.executeQuery()) {
                    if (rs.next()) {
                        idTipo = rs.getInt("id_tipo_unidad");
                    } else {
                        // Si no se encuentra, se podría insertar o devolver error
                        conn.rollback();
                        System.err.println("Tipo de unidad no encontrado: " + tipoUnidad);
                        return false;
                    }
                }
            }

            // 3. Obtener el ID del Estado de Unidad
            int idEstado;
            try (PreparedStatement stmtEstado = conn.prepareStatement(sqlSelectEstado)) {
                stmtEstado.setString(1, estadoUnidad);
                try (ResultSet rs = stmtEstado.executeQuery()) {
                    if (rs.next()) {
                        idEstado = rs.getInt("id_estado_unidad");
                    } else {
                        conn.rollback();
                        System.err.println("Estado de unidad no encontrado: " + estadoUnidad);
                        return false;
                    }
                }
            }

            // 4. Insertar la Unidad
            try (PreparedStatement stmtUnidad = conn.prepareStatement(sqlInsertUnidad)) {
                stmtUnidad.setString(1, placa);
                stmtUnidad.setInt(2, idPropietario);
                stmtUnidad.setString(3, numComercial);
                stmtUnidad.setInt(4, idTipo);
                stmtUnidad.setInt(5, idEstado);

                int filasInsertadas = stmtUnidad.executeUpdate();
                if (filasInsertadas > 0) {
                    conn.commit();  // Confirmar la transacción
                    return true;
                } else {
                    conn.rollback();
                    return false;
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al registrar unidad: " + e.getMessage());
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException ex) {
                System.err.println("Error al hacer rollback: " + ex.getMessage());
            }
            return false;
        } finally {
            try {
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }

    /**
     * Obtiene la lista de unidades registradas, realizando join con los
     * catálogos y propietarios para mostrar la información completa.
     */
    public List<Object[]> obtenerUnidades() {
        List<Object[]> lista = new ArrayList<>();
        String sql = "SELECT u.placa, p.nombre AS nombre_propietario, u.numero_comercial, p.telefono AS numero_telefono, "
                + "t.nombre_tipo AS tipo_unidad, e.nombre_estado AS estado_unidad "
                + "FROM Unidad u "
                + "JOIN Propietario p ON u.id_propietario = p.id_propietario "
                + "JOIN TipoUnidad t ON u.id_tipo_unidad = t.id_tipo_unidad "
                + "JOIN EstadoUnidad e ON u.id_estado_unidad = e.id_estado_unidad";

        try (Connection conn = conectar(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Object[] fila = new Object[6];
                fila[0] = rs.getString("placa");
                fila[1] = rs.getString("nombre_propietario");
                fila[2] = rs.getString("numero_comercial");
                fila[3] = rs.getString("numero_telefono");
                fila[4] = rs.getString("tipo_unidad");
                fila[5] = rs.getString("estado_unidad");
                lista.add(fila);
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener unidades: " + e.getMessage());
        }

        return lista;
    }

    public String obtenerPlacaPorPropietario(String nombrePropietario) {
        String sql = "SELECT u.placa FROM Unidad u "
                + "JOIN Propietario p ON u.id_propietario = p.id_propietario "
                + "WHERE p.nombre = ?";

        try (Connection conn = conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nombrePropietario.trim()); // Eliminar espacios extras
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("placa");
                } else {
                    JOptionPane.showMessageDialog(null,
                            "No se encontró la placa para el propietario: " + nombrePropietario,
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                    return "";
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener placa: " + e.getMessage());
            return "";

        }
    }

    //nuevos metodos 
    public List<String> obtenerNombresPropietarios() {
        List<String> nombres = new ArrayList<>();
        String sql = "SELECT nombre FROM Propietario";

        try (Connection conn = conectar(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                nombres.add(rs.getString("nombre"));
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al obtener nombres de propietarios: " + ex.getMessage());
        }

        return nombres;
    }

    public List<String> obtenerNumerosComerciales() {
        List<String> numeros = new ArrayList<>();
        String sql = "SELECT numero_comercial FROM Unidad";

        try (Connection conn = conectar(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                numeros.add(rs.getString("numero_comercial"));
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al obtener números comerciales: " + ex.getMessage());
        }

        return numeros;
    }

}
