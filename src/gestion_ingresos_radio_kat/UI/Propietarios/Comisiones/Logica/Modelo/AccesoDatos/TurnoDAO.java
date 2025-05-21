package gestion_ingresos_radio_kat.UI.Propietarios.Comisiones.Logica.Modelo.AccesoDatos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

import java.sql.*;
import java.util.ArrayList; 
import java.util.List;
import javax.swing.JOptionPane;

public class TurnoDAO {

    private final String URL = "jdbc:mysql://localhost:3306/registroturno";
    private final String USER = "root";
    private final String PASSWORD = "JGff404aISc";

    public Connection conectar() throws SQLException {
        return java.sql.DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public boolean guardarTurno(Date fecha, Date horaInicio, Date horaFin, String nombreProp, String numeroComercial) {
        String sql = "INSERT INTO Turno (fecha_turno, hora_inicio, hora_fin, nombre_propietario, numero_comercial) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            java.sql.Date fechaSQL = new java.sql.Date(fecha.getTime());
            java.sql.Time horaInicioSQL = new java.sql.Time(horaInicio.getTime());
            java.sql.Time horaFinSQL = new java.sql.Time(horaFin.getTime());

            stmt.setDate(1, fechaSQL);
            stmt.setTime(2, horaInicioSQL);
            stmt.setTime(3, horaFinSQL);
            stmt.setString(4, nombreProp);
            stmt.setString(5, numeroComercial);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al guardar turno: " + e.getMessage());
            return false;
        }
    }

    //Nuevo metodo 
    public List<String> obtenerHorariosRegistrados() {
        List<String> horarios = new ArrayList<>();
        String sql = "SELECT DATE_FORMAT(hora_inicio, '%H:%i') as inicio, "
                + "DATE_FORMAT(hora_fin, '%H:%i') as fin FROM Turno";

        try (Connection conn = conectar(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String horario = rs.getString("inicio") + " - " + rs.getString("fin");
                horarios.add(horario);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    "Error al obtener horarios: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
        return horarios;
    }

}

 