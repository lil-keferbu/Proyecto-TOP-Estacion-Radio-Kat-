 package gestion_ingresos_radio_kat.UI.Propietarios.Comisiones.Logica.Modelo.AccesoDatos.Controladores_Vista_Logica;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Semanalmente {

    private static final String URL      = "jdbc:mysql://localhost:3306/semanalmente";
    private static final String USER     = "root";
    private static final String PASSWORD = "JGff404aISc";

    /**
     * Guarda (o actualiza) el registro semanal con solo dos columnas:
     * fecha (PK) y ganancia_total.
     * @param fecha yyyy-MM-dd
     * @param gananciaTotal total calculado
     * @return true si la operación fue exitosa
     */
    public boolean guardarSemanalmente(String fecha, double gananciaTotal) {
        String sql = """
                     INSERT INTO Semanalmente (fecha, ganancia_total)
                     VALUES (?, ?)
                     ON DUPLICATE KEY UPDATE ganancia_total = VALUES(ganancia_total)
                     """;

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, java.sql.Date.valueOf(fecha));
            stmt.setDouble(2, gananciaTotal);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Devuelve lista de pares [fecha, ganancia_total] para el PDF.
     */
    public List<Object[]> obtenerTodosRegistros() {
        List<Object[]> registros = new ArrayList<>();
        String sql = "SELECT fecha, ganancia_total FROM Semanalmente";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt   = conn.createStatement();
             ResultSet rs     = stmt.executeQuery(sql)) {

            while (rs.next()) {
                registros.add(new Object[]{
                        rs.getDate("fecha"),
                        rs.getDouble("ganancia_total")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return registros;
    }
}
