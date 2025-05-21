package gestion_ingresos_radio_kat.UI.Propietarios.Comisiones.Logica.Modelo.AccesoDatos;

import java.sql.*;
import java.text.SimpleDateFormat;

public class RegistroIngresosDAO {

    private final String URL = "jdbc:mysql://localhost:3306/gestioningresos";
    private final String USER = "root";
    private final String PASSWORD = "JGff404aISc";

        public Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public boolean guardarRegistro(String fecha, String placaUnidad, double ingresosBrutos,
            double comisionTotal, double gananciaPropietario) {
        String sql = "INSERT INTO RegistroIngresos (fecha,placa_unidad  , ingresos_brutos, comision_total, ganancia_propietario) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Convertir fecha de String a Date
            java.util.Date fechaUtil = new SimpleDateFormat("dd/MM/yyyy").parse(fecha);
            java.sql.Date fechaSQL = new java.sql.Date(fechaUtil.getTime());

            stmt.setDate(1, fechaSQL);
            stmt.setString(2, placaUnidad);
            stmt.setDouble(3, ingresosBrutos);
            stmt.setDouble(4, comisionTotal);
            stmt.setDouble(5, gananciaPropietario);

            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            System.err.println("Error al guardar registro: " + e.getMessage());
            return false;
        }
    }

}
