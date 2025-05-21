package gestion_ingresos_radio_kat.UI.Propietarios.Comisiones.Logica.Modelo.AccesoDatos.Controladores_Vista_Logica;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CrearCuentaControlador {

    String bd = "usuariosinvitados";
    String url = "jdbc:mysql://localhost:3306/";
    String usuario = "root";
    String password = "JGff404aISc";
    String driver = "com.mysql.cj.jdbc.Driver";
    Connection cx;

    public CrearCuentaControlador(String bd) {
        this.bd = bd;
    }

    public Connection conectar() {
        try {
            Class.forName(driver);
            cx = DriverManager.getConnection(url + bd, usuario, password);
            System.out.println("SE CONECTÓ A LA BASE DE DATOS " + bd);
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("NO SE CONECTÓ A LA BASE DE DATOS " + bd);
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

// Registrar usuario invitado
    public boolean registrarInvitado(String nombre, String apellido, String correo, int dia, int mes, int anio, String generoDesc, String contrasena) {
        try {
            if (existeUsuario(nombre, apellido)) {
                System.out.println("El usuario ya existe.");
                return false;
            }

            if (existeCorreo(correo)) {
                System.out.println("El correo ya está registrado.");
                return false;
            }

            int id_genero = obtenerIdGenero(generoDesc);
            if (id_genero == -1) {
                id_genero = insertarGenero(generoDesc);
            }

            int id_fecha = insertarFechaNacimiento(dia, mes, anio);

            String query = "INSERT INTO CuentasInvitados (nombre, apellido, correo, id_fecha, id_genero, password) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement st = conectar().prepareStatement(query);

            st.setString(1, nombre);
            st.setString(2, apellido);
            st.setString(3, correo);
            st.setInt(4, id_fecha);
            st.setInt(5, id_genero);
            st.setString(6, contrasena);

            int filas = st.executeUpdate();
            return filas > 0;

        } catch (SQLException e) {
            Logger.getLogger(CrearCuentaControlador.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }

    // Verificar si ya existe un correo
    public boolean existeCorreo(String correo) {
        try {
            String query = "SELECT * FROM CuentasInvitados WHERE correo = ?";
            PreparedStatement st = conectar().prepareStatement(query);
            st.setString(1, correo);
            ResultSet rs = st.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            Logger.getLogger(CrearCuentaControlador.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }

    // Verificar si el usuario ya existe
    public boolean existeUsuario(String nombre, String apellido) {
        try {
            String query = "SELECT * FROM CuentasInvitados WHERE nombre = ? AND apellido = ?";
            PreparedStatement st = conectar().prepareStatement(query);
            st.setString(1, nombre);
            st.setString(2, apellido);
            ResultSet rs = st.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            Logger.getLogger(CrearCuentaControlador.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }

    // Insertar género si no existe
    private int obtenerIdGenero(String descripcion) throws SQLException {
        String query = "SELECT id_genero FROM genero WHERE descripcion = ?";
        PreparedStatement st = conectar().prepareStatement(query);
        st.setString(1, descripcion);
        ResultSet rs = st.executeQuery();

        if (rs.next()) {
            return rs.getInt("id_genero");
        }
        return -1;
    }

    private int insertarGenero(String descripcion) throws SQLException {
        String query = "INSERT INTO genero (descripcion) VALUES (?)";
        PreparedStatement st = conectar().prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
        st.setString(1, descripcion);
        st.executeUpdate();

        ResultSet rs = st.getGeneratedKeys();
        if (rs.next()) {
            return rs.getInt(1);
        }
        return -1;
    }

    // Insertar fecha de nacimiento
    private int insertarFechaNacimiento(int dia, int mes, int anio) throws SQLException {
        String query = "INSERT INTO fecha_nacimiento (dia, mes, anio) VALUES (?, ?, ?)";
        PreparedStatement st = conectar().prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
        st.setInt(1, dia);
        st.setInt(2, mes);
        st.setInt(3, anio);
        st.executeUpdate();

        ResultSet rs = st.getGeneratedKeys();
        if (rs.next()) {
            return rs.getInt(1);
        }
        return -1;
    }

    // Mostrar todos los usuarios (para pruebas o administración)
    public void mostrarTodosLosUsuarios() {
        try {
            String query = "SELECT c.id_cuenta, c.nombre, c.apellido, f.dia, f.mes, f.anio, g.descripcion AS genero "
                    + "FROM CuentasInvitados c "
                    + "JOIN fecha_nacimiento f ON c.id_fecha = f.id_fecha "
                    + "JOIN genero g ON c.id_genero = g.id_genero";
            PreparedStatement st = conectar().prepareStatement(query);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id_cuenta")
                        + ", Nombre: " + rs.getString("nombre")
                        + ", Apellido: " + rs.getString("apellido")
                        + ", Fecha de nacimiento: " + rs.getInt("dia") + "/" + rs.getInt("mes") + "/" + rs.getInt("anio")
                        + ", Género: " + rs.getString("genero"));
            }

        } catch (SQLException e) {
            Logger.getLogger(CrearCuentaControlador.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    // Eliminar usuario por nombre y apellido
    public boolean eliminarUsuario(String nombre, String apellido) {
        try {
            String query = "DELETE FROM CuentasInvitados WHERE nombre = ? AND apellido = ?";
            PreparedStatement st = conectar().prepareStatement(query);
            st.setString(1, nombre);
            st.setString(2, apellido);
            int filas = st.executeUpdate();
            return filas > 0;
        } catch (SQLException e) {
            Logger.getLogger(CrearCuentaControlador.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }

    public boolean validarIngreso(String nombre, String apellido, String contrasena) {
        String query = "SELECT * FROM CuentasInvitados WHERE nombre = ? AND apellido = ? AND password = ?";
        try {
            PreparedStatement st = conectar().prepareStatement(query);
            st.setString(1, nombre);
            st.setString(2, apellido);
            st.setString(3, contrasena);
            ResultSet rs = st.executeQuery();

            return rs.next(); // Si hay resultado, el usuario existe y la contraseña coincide

        } catch (SQLException ex) {
            Logger.getLogger(CrearCuentaControlador.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    // Actualizar contraseña con base en el correo
    public boolean actualizarContrasenaPorCorreo(String correo, String nuevaContrasena) {
        try {
            String query = "UPDATE CuentasInvitados SET password = ? WHERE correo = ?";
            PreparedStatement st = conectar().prepareStatement(query);
            st.setString(1, nuevaContrasena);
            st.setString(2, correo);
            int filas = st.executeUpdate();
            return filas > 0;
        } catch (SQLException e) {
            Logger.getLogger(CrearCuentaControlador.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }


    /*
    public static void main(String[] agrs) {
        CrearCuentaControlador conexion = new CrearCuentaControlador("usuariosinvitados");
        conexion.conectar();
    }
    
    */
    
    
}
