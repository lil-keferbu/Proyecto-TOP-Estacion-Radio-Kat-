package gestion_ingresos_radio_kat.UI.Propietarios.Comisiones.Logica.Modelo.AccesoDatos.Controladores_Vista_Logica;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author User
 */
public class Diario {

    private final String URL = "jdbc:mysql://localhost:3306/diario";
    private final String USER = "root";
    private final String PASSWORD = "JGff404aISc";

    public boolean guardarDiario(String fecha, String unidad, String propietario, double ingresosBrutos, double comisionPorcentaje, double comisionTotal, double gananciaPropietario) {
        String sql = "INSERT INTO Diario (fecha, unidad, propietario, ingresos_brutos, comision_porcentaje, comision_total, ganancia_propietario) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, java.sql.Date.valueOf(fecha)); // formato yyyy-MM-dd
            stmt.setString(2, unidad);
            stmt.setString(3, propietario);
            stmt.setDouble(4, ingresosBrutos);
            stmt.setDouble(5, comisionPorcentaje);
            stmt.setDouble(6, comisionTotal);
            stmt.setDouble(7, gananciaPropietario);

            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

 
    
    
    // Nuevo método para obtener todos los registros
    public List<Object[]> obtenerTodosRegistros() {
        List<Object[]> registros = new ArrayList<>();
        String sql = "SELECT * FROM Diario";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Object[] fila = {
                    rs.getDate("fecha"),
                    rs.getString("unidad"),
                    rs.getString("propietario"),
                    rs.getDouble("ingresos_brutos"),
                    rs.getDouble("comision_porcentaje"),
                    rs.getDouble("comision_total"),
                    rs.getDouble("ganancia_propietario")
                };
                registros.add(fila);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return registros;
    }
    
 
    
    

    // En Diario.java
    public double obtenerSumaComisionesPorSemana(Date inicio, Date fin) {
        String sql = "SELECT SUM(comision_total) AS total FROM Diario WHERE fecha BETWEEN ? AND ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, new java.sql.Date(inicio.getTime()));
            stmt.setDate(2, new java.sql.Date(fin.getTime()));
            ResultSet rs = stmt.executeQuery();

            return rs.next() ? rs.getDouble("total") : 0.0;

        } catch (SQLException e) {
            e.printStackTrace();
            return 0.0;
        }
    }

    public List<Object[]> obtenerRegistrosPorSemana(Date inicio, Date fin) {
        List<Object[]> registros = new ArrayList<>();
        String sql = "SELECT fecha, unidad, propietario, horario_registrado, estado_unidad, "
                + "ingresos_brutos, comision_porcentaje, ganancia_propietario "
                + "FROM Diario WHERE fecha BETWEEN ? AND ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, new java.sql.Date(inicio.getTime()));
            stmt.setDate(2, new java.sql.Date(fin.getTime()));
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Object[] fila = {
                    rs.getDate("fecha"),
                    rs.getString("unidad"),
                    rs.getString("propietario"),
                    rs.getString("horario_registrado"),
                    rs.getString("estado_unidad"),
                    rs.getDouble("ingresos_brutos"),
                    rs.getDouble("comision_porcentaje"),
                    rs.getDouble("ganancia_propietario")
                };
                registros.add(fila);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return registros;
    }

}
