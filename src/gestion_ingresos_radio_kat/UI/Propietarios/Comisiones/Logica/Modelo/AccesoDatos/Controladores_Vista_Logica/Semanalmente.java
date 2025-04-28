/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
public class Semanalmente {

    private final String URL = "jdbc:mysql://localhost:3310/semanalmente";
    private final String USER = "root";
    private final String PASSWORD = "";

    public boolean guardarSemanalmente(String fecha, String unidad, String propietario, String horarioRegistrado, String estadoUnidad, double ingresosBrutos, double comisionPorcentaje, double gananciaTotal) {
        String sql = "INSERT INTO Semanalmente (fecha, unidad, propietario, horario_registrado, estado_unidad, ingresos_brutos, comision_porcentaje, ganancia_total) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, java.sql.Date.valueOf(fecha)); // formato yyyy-MM-dd
            stmt.setString(2, unidad);
            stmt.setString(3, propietario);
            stmt.setString(4, horarioRegistrado);
            stmt.setString(5, estadoUnidad);
            stmt.setDouble(6, ingresosBrutos);
            stmt.setDouble(7, comisionPorcentaje);
            stmt.setDouble(8, gananciaTotal);

            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Nuevo método para obtener todos los registros
    public List<Object[]> obtenerTodosRegistros() {
        List<Object[]> registros = new ArrayList<>();
        String sql = "SELECT * FROM Semanalmente";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Object[] fila = {
                    rs.getDate("fecha"),
                    rs.getString("unidad"),
                    rs.getString("propietario"),
                    rs.getString("horario_registrado"),
                    rs.getString("estado_unidad"),
                    rs.getDouble("ingresos_brutos"),
                    rs.getDouble("comision_porcentaje"),
                    rs.getDouble("ganancia_total")
                };
                registros.add(fila);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return registros;
    }

}
