package gestion_ingresos_radio_kat.UI.Propietarios.Comisiones.Logica.Modelo.AccesoDatos.Controladores_Vista_Logica;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginControlador {

    String bd = "datoslogin";
    String url = "jdbc:mysql://localhost:3310/";
    String Nombre = "root";
    String password = "";
    String driver = "com.mysql.cj.jdbc.Driver";
    Connection cx;

    public LoginControlador(String bd) {
        this.bd = bd;
    }

    public Connection conectar() {
        try {
            Class.forName(driver);

            cx = DriverManager.getConnection(url + bd, Nombre, password); // cadena de conexión
            System.out.println("SE CONECTO A LA BASE DE DATOS " + bd);
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("NO A LA BASE DE DATOS " + bd);
            Logger.getLogger(LoginControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cx;
    }

    public void desconectar() {
        try {
            if (cx != null && !cx.isClosed()) {
                cx.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(LoginControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /*public static void main(String[] agrs) {
        LoginControlador conexion = new LoginControlador("datoslogin");
        conexion.conectar();
    }*/
}
