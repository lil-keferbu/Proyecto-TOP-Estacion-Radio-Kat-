/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package gestion_ingresos_radio_kat.UI.Propietarios.Comisiones;

import gestion_ingresos_radio_kat.UI.Propietarios.Comisiones.Logica.CalculoIngresos;
import gestion_ingresos_radio_kat.UI.Propietarios.Comisiones.Logica.Modelo.AccesoDatos.Controladores_Vista_Logica.Diario;
import gestion_ingresos_radio_kat.UI.Propietarios.Comisiones.Logica.Modelo.AccesoDatos.Controladores_Vista_Logica.Semanalmente;
import gestion_ingresos_radio_kat.UI.Propietarios.Comisiones.Logica.Modelo.AccesoDatos.TurnoDAO;
import gestion_ingresos_radio_kat.UI.Propietarios.Comisiones.Logica.Modelo.AccesoDatos.UnidadDAO;
import gestion_ingresos_radio_kat.UI.Propietarios.Comisiones.Logica.Modelo.Unidad;
import gestion_ingresos_radio_kat.UI.Propietarios.GestionIngresosGUI;
import gestion_ingresos_radio_kat.UI.Propietarios.RegistroTurnoGUI;

import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.DefaultComboBoxModel;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

//Librerias que se ocupan para generar  reportes pdf 
// Importaciones necesarias
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

///Nuevas imporaciones 
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Font;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.pdf.PdfPCell;

/**
 *
 * @author User
 */
public class ReportesGUI extends javax.swing.JFrame {

    private JPanel selectedPanel = null;

    /**
     * Creates new form ReportesGUI
     */
    public ReportesGUI() {
        initComponents();
        initCustomComponents();

    }

    private void initCustomComponents() {

        // Configurar modelo de tabla
        DefaultTableModel model = new DefaultTableModel(
                new Object[]{"Fecha", "Unidad", "Propietario", "Horario registrado",
                    "Estado Unidad", "Ingreso Bruto", "Comisión (%)", "Ganancia Total"}, 0
        );
        jTableGenerarReportes.setModel(model);

        // Cargar datos en comboboxes
        cargarComboboxes();

    }

    private void cargarComboboxes() {
        try {
            UnidadDAO unidadDAO = new UnidadDAO();
            TurnoDAO turnoDAO = new TurnoDAO();

            // Propietarios
            DefaultComboBoxModel<String> modelNombres = new DefaultComboBoxModel<>();
            modelNombres.addAll(unidadDAO.obtenerNombresPropietarios());
            boxNombre.setModel(modelNombres);

            // Horarios
            DefaultComboBoxModel<String> modelHorarios = new DefaultComboBoxModel<>();
            modelHorarios.addAll(turnoDAO.obtenerHorariosRegistrados());
            boxHorarioRegistrado.setModel(modelHorarios);

            // Tipos de Unidad
            DefaultComboBoxModel<String> modelTipos = new DefaultComboBoxModel<>();
            modelTipos.addElement("Propietario único");
            modelTipos.addElement("Chofer externo");
            boxTipoUnidad.setModel(modelTipos);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error al cargar datos: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }

    }

    /* private void generarReporteDiarioPDF() {
        Document documento = new Document();
        try {
            String ruta = System.getProperty("user.home");
            PdfWriter.getInstance(documento, new FileOutputStream(ruta + "/Desktop/Reporte_Diario.pdf"));
            documento.open();

            // Crear tabla con 7 columnas (Diario)
            PdfPTable tabla = new PdfPTable(7);
            tabla.addCell("Fecha");
            tabla.addCell("Unidad");
            tabla.addCell("Propietario");
            tabla.addCell("Ingresos Brutos");
            tabla.addCell("Comisión (%)");
            tabla.addCell("Comisión Total");
            tabla.addCell("Ganancia Propietario");

            // Conexión a BD Diario
            String url = "jdbc:mysql://localhost:3310/diario";
            String user = "root";
            String password = "";

            try (Connection conn = DriverManager.getConnection(url, user, password); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery("SELECT * FROM Diario")) {

                while (rs.next()) {
                    tabla.addCell(rs.getString("fecha"));
                    tabla.addCell(rs.getString("unidad"));
                    tabla.addCell(rs.getString("propietario"));
                    tabla.addCell(String.valueOf(rs.getDouble("ingresos_brutos")));
                    tabla.addCell(String.valueOf(rs.getDouble("comision_porcentaje")));
                    tabla.addCell(String.valueOf(rs.getDouble("comision_total")));
                    tabla.addCell(String.valueOf(rs.getDouble("ganancia_propietario")));
                }

                documento.add(tabla);
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al acceder a la base de datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }

            documento.close();
            JOptionPane.showMessageDialog(this, "PDF Diario generado en el Escritorio");
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al generar PDF: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }*/
 /*private void generarReporteSemanalPDF() {
        Document documento = new Document();
        try {
            String ruta = System.getProperty("user.home");
            PdfWriter.getInstance(documento, new FileOutputStream(ruta + "/Desktop/Reporte_Semanal.pdf"));
            documento.open();

            // Crear tabla con 8 columnas (Semanalmente)
            PdfPTable tabla = new PdfPTable(8);
            tabla.addCell("Fecha");
            tabla.addCell("Unidad");
            tabla.addCell("Propietario");
            tabla.addCell("Horario Registrado");
            tabla.addCell("Estado Unidad");
            tabla.addCell("Ingresos Brutos");
            tabla.addCell("Comisión (%)");
            tabla.addCell("Ganancia Total");

            // Conexión a BD Semanalmente
            String url = "jdbc:mysql://localhost:3310/semanalmente";
            String user = "root";
            String password = "";

            try (Connection conn = DriverManager.getConnection(url, user, password); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery("SELECT * FROM Semanalmente")) {

                while (rs.next()) {
                    tabla.addCell(rs.getString("fecha"));
                    tabla.addCell(rs.getString("unidad"));
                    tabla.addCell(rs.getString("propietario"));
                    tabla.addCell(rs.getString("horario_registrado"));
                    tabla.addCell(rs.getString("estado_unidad"));
                    tabla.addCell(String.valueOf(rs.getDouble("ingresos_brutos")));
                    tabla.addCell(String.valueOf(rs.getDouble("comision_porcentaje")));
                    tabla.addCell(String.valueOf(rs.getDouble("ganancia_total")));
                }

                documento.add(tabla);
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al acceder a la base de datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }

            documento.close();
            JOptionPane.showMessageDialog(this, "PDF Semanal generado en el Escritorio");
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al generar PDF: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }*/

 /*
    
        try {
            // Cargar nombres de propietarios
            UnidadDAO unidadDAO = new UnidadDAO();
            DefaultComboBoxModel<String> modelNombres = new DefaultComboBoxModel<>();
            modelNombres.addAll(unidadDAO.obtenerNombresPropietarios());
            boxNombre.setModel(modelNombres);

            // Cargar horarios
            TurnoDAO turnoDAO = new TurnoDAO();
            DefaultComboBoxModel<String> modelHorarios = new DefaultComboBoxModel<>();
            modelHorarios.addAll(turnoDAO.obtenerHorariosRegistrados());
            boxHorarioRegistrado.setModel(modelHorarios);

            // Cargar tipos de unidad
            DefaultComboBoxModel<String> modelTipos = new DefaultComboBoxModel<>();
            modelTipos.addElement("Propietario único");
            modelTipos.addElement("Chofer externo");
            boxTipoUnidad.setModel(modelTipos);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error al cargar datos: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    
    
    
     */
    // Método que suma todas las ganancias de la tabla de ReportesGUI
    public double calcularGananciaTotalEstacion() {
        double gananciaTotal = 0.0;
        DefaultTableModel model = (DefaultTableModel) jTableGenerarReportes.getModel();
        int columnaGanancia = 7; // Columna "Ganancia Total"

        for (int i = 0; i < model.getRowCount(); i++) {
            Object valorCelda = model.getValueAt(i, columnaGanancia);
            if (valorCelda != null) {
                try {
                    gananciaTotal += Double.parseDouble(valorCelda.toString());
                } catch (NumberFormatException e) {
                    // Ignorar si hay errores de formato
                }
            }
        }
        return gananciaTotal;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        JpGestionIngresos = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        JpRegistroTurno = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        JpReportes = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        btnRegresar = new javax.swing.JButton();
        btnAdelante = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jSDiaMesAnio = new javax.swing.JSpinner();
        jLabel8 = new javax.swing.JLabel();
        boxTipoUnidad = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        boxTipoReporte = new javax.swing.JComboBox<>();
        jPanel5 = new javax.swing.JPanel();
        btnAddTableReporte = new javax.swing.JButton();
        btnLimpiarFormulario = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnGuardarBDExportarPDF = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableGenerarReportes = new javax.swing.JTable();
        jLabel10 = new javax.swing.JLabel();
        boxNombre = new javax.swing.JComboBox<>();
        btnBuscar = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        txtBuscar = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        boxHorarioRegistrado = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setMinimumSize(new java.awt.Dimension(1271, 751));
        jPanel2.setPreferredSize(new java.awt.Dimension(1271, 751));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(29, 54, 88));
        jPanel1.setPreferredSize(new java.awt.Dimension(270, 640));

        JpGestionIngresos.setBackground(new java.awt.Color(29, 54, 88));
        JpGestionIngresos.setPreferredSize(new java.awt.Dimension(231, 66));
        JpGestionIngresos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JpGestionIngresosMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                JpGestionIngresosMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                JpGestionIngresosMouseExited(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Gestion Ingresos ");

        javax.swing.GroupLayout JpGestionIngresosLayout = new javax.swing.GroupLayout(JpGestionIngresos);
        JpGestionIngresos.setLayout(JpGestionIngresosLayout);
        JpGestionIngresosLayout.setHorizontalGroup(
            JpGestionIngresosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JpGestionIngresosLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        JpGestionIngresosLayout.setVerticalGroup(
            JpGestionIngresosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JpGestionIngresosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 54, Short.MAX_VALUE)
                .addContainerGap())
        );

        JpRegistroTurno.setBackground(new java.awt.Color(29, 54, 88));
        JpRegistroTurno.setPreferredSize(new java.awt.Dimension(231, 66));
        JpRegistroTurno.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JpRegistroTurnoMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                JpRegistroTurnoMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                JpRegistroTurnoMouseExited(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Registro Turno");

        javax.swing.GroupLayout JpRegistroTurnoLayout = new javax.swing.GroupLayout(JpRegistroTurno);
        JpRegistroTurno.setLayout(JpRegistroTurnoLayout);
        JpRegistroTurnoLayout.setHorizontalGroup(
            JpRegistroTurnoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JpRegistroTurnoLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        JpRegistroTurnoLayout.setVerticalGroup(
            JpRegistroTurnoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JpRegistroTurnoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        JpReportes.setBackground(new java.awt.Color(29, 54, 88));
        JpReportes.setPreferredSize(new java.awt.Dimension(231, 66));
        JpReportes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JpReportesMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                JpReportesMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                JpReportesMouseExited(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Reportes");

        javax.swing.GroupLayout JpReportesLayout = new javax.swing.GroupLayout(JpReportes);
        JpReportes.setLayout(JpReportesLayout);
        JpReportesLayout.setHorizontalGroup(
            JpReportesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JpReportesLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(60, Short.MAX_VALUE))
        );
        JpReportesLayout.setVerticalGroup(
            JpReportesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JpReportesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 54, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel7.setBackground(new java.awt.Color(29, 54, 88));

        btnRegresar.setBackground(new java.awt.Color(29, 54, 88));
        btnRegresar.setForeground(new java.awt.Color(29, 54, 88));
        btnRegresar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gestion_ingresos_radio_kat/Imegenes/Iconos/Regresar.png"))); // NOI18N
        btnRegresar.setMaximumSize(new java.awt.Dimension(64, 24));
        btnRegresar.setMinimumSize(new java.awt.Dimension(64, 24));
        btnRegresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegresarActionPerformed(evt);
            }
        });

        btnAdelante.setBackground(new java.awt.Color(29, 54, 88));
        btnAdelante.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gestion_ingresos_radio_kat/Imegenes/Iconos/adelante.png"))); // NOI18N

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(btnRegresar, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 102, Short.MAX_VALUE)
                .addComponent(btnAdelante, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(24, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnAdelante, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnRegresar, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10))
        );

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gestion_ingresos_radio_kat/Imegenes/Iconos/edit_note_24dp_E3E3E3_FILL0_wght400_GRAD0_opsz24.png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(31, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(JpGestionIngresos, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 279, Short.MAX_VALUE)
                        .addComponent(JpRegistroTurno, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 279, Short.MAX_VALUE)
                        .addComponent(JpReportes, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 279, Short.MAX_VALUE)
                        .addComponent(jPanel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jLabel4)
                .addGap(74, 74, 74)
                .addComponent(JpGestionIngresos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addComponent(JpRegistroTurno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(47, 47, 47)
                .addComponent(JpReportes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(166, Short.MAX_VALUE))
        );

        jPanel2.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(-40, -10, 310, 780));

        jPanel3.setBackground(new java.awt.Color(68, 122, 156));
        jPanel3.setPreferredSize(new java.awt.Dimension(1030, 240));

        jLabel14.setBackground(new java.awt.Color(255, 255, 255));
        jLabel14.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 24)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("Reportes");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(323, 323, 323)
                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 445, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(352, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(43, Short.MAX_VALUE)
                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34))
        );

        jPanel2.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 0, 1120, 180));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel5.setFont(new java.awt.Font("Arial Black", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Fecha reporte ");
        jPanel4.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 170, 40));

        jSDiaMesAnio.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jSDiaMesAnio.setModel(new javax.swing.SpinnerDateModel(new java.util.Date(1743733765360L), null, null, java.util.Calendar.MONTH));
        jPanel4.add(jSDiaMesAnio, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 172, -1));

        jLabel8.setFont(new java.awt.Font("Arial Black", 0, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 0));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Horario registrado");
        jLabel8.setToolTipText("Unidades Registradas");
        jPanel4.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 30, 160, 40));

        boxTipoUnidad.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " " }));
        boxTipoUnidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boxTipoUnidadActionPerformed(evt);
            }
        });
        jPanel4.add(boxTipoUnidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 70, 160, 30));

        jLabel9.setFont(new java.awt.Font("Arial Black", 0, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 0, 0));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Tipo reporte");
        jPanel4.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 30, 110, 40));

        boxTipoReporte.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Diario", "Semanalmente" }));
        boxTipoReporte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boxTipoReporteActionPerformed(evt);
            }
        });
        jPanel4.add(boxTipoReporte, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 70, 110, 30));

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        btnAddTableReporte.setBackground(new java.awt.Color(255, 255, 255));
        btnAddTableReporte.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        btnAddTableReporte.setForeground(new java.awt.Color(255, 255, 255));
        btnAddTableReporte.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gestion_ingresos_radio_kat/Imegenes/Iconos/GenerarReporte.png"))); // NOI18N
        btnAddTableReporte.setToolTipText("Generar reporte en la Tabla Reportes ");
        btnAddTableReporte.setBorder(null);
        btnAddTableReporte.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAddTableReporte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddTableReporteActionPerformed(evt);
            }
        });

        btnLimpiarFormulario.setBackground(new java.awt.Color(255, 255, 255));
        btnLimpiarFormulario.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        btnLimpiarFormulario.setForeground(new java.awt.Color(255, 255, 255));
        btnLimpiarFormulario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gestion_ingresos_radio_kat/Imegenes/Iconos/LimpiarFormulario.png"))); // NOI18N
        btnLimpiarFormulario.setToolTipText("Limpiar Flitros");
        btnLimpiarFormulario.setBorder(null);
        btnLimpiarFormulario.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnLimpiarFormulario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarFormularioActionPerformed(evt);
            }
        });

        btnCancelar.setBackground(new java.awt.Color(255, 255, 255));
        btnCancelar.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        btnCancelar.setForeground(new java.awt.Color(255, 255, 255));
        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gestion_ingresos_radio_kat/Imegenes/Iconos/Cancelar.png"))); // NOI18N
        btnCancelar.setToolTipText("Cancelar");
        btnCancelar.setBorder(null);
        btnCancelar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        btnGuardarBDExportarPDF.setBackground(new java.awt.Color(255, 255, 255));
        btnGuardarBDExportarPDF.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        btnGuardarBDExportarPDF.setForeground(new java.awt.Color(255, 255, 255));
        btnGuardarBDExportarPDF.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gestion_ingresos_radio_kat/Imegenes/Iconos/ExportarPDF.png"))); // NOI18N
        btnGuardarBDExportarPDF.setToolTipText("Exportar PDF");
        btnGuardarBDExportarPDF.setBorder(null);
        btnGuardarBDExportarPDF.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnGuardarBDExportarPDF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarBDExportarPDFActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnAddTableReporte)
                .addGap(37, 37, 37)
                .addComponent(btnLimpiarFormulario)
                .addGap(45, 45, 45)
                .addComponent(btnGuardarBDExportarPDF)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 600, Short.MAX_VALUE)
                .addComponent(btnCancelar)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGap(0, 8, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnGuardarBDExportarPDF, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLimpiarFormulario, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAddTableReporte, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel4.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 500, 950, 70));

        jTableGenerarReportes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Fecha", "Unidad", "Propietario", "Horiario  registrado", "Estado unidad", "Ingreso bruto", "Comision (%)", "Ganancia Total"
            }
        ));
        jScrollPane1.setViewportView(jTableGenerarReportes);

        jPanel4.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 290, 940, 190));

        jLabel10.setFont(new java.awt.Font("Arial Black", 0, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 0, 0));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("Nombre Propietario");
        jLabel10.setToolTipText("");
        jPanel4.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(379, 30, 170, 40));

        boxNombre.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " " }));
        boxNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boxNombreActionPerformed(evt);
            }
        });
        jPanel4.add(boxNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 70, 170, 30));

        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });
        jPanel4.add(btnBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 150, 80, 30));
        jPanel4.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 180, 290, 30));

        txtBuscar.setText(" ");
        txtBuscar.setBorder(null);
        txtBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBuscarActionPerformed(evt);
            }
        });
        jPanel4.add(txtBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 150, 290, 30));

        jLabel11.setFont(new java.awt.Font("Arial Black", 0, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 0, 0));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("Tipo  unidad");
        jLabel11.setToolTipText("Unidades Registradas");
        jPanel4.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 30, 160, 40));

        boxHorarioRegistrado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " " }));
        boxHorarioRegistrado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boxHorarioRegistradoActionPerformed(evt);
            }
        });
        jPanel4.add(boxHorarioRegistrado, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 70, 160, 30));

        jButton1.setText("Borrar");
        jPanel4.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 150, 80, 30));

        jPanel2.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 180, 1000, 570));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 1230, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 751, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnRegresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegresarActionPerformed
        // TODO add your handling code here:
        // TODO add your handling code here:
        GestionIngresosGUI registro = new GestionIngresosGUI();
        registro.setVisible(true);

        // Cerrar la ventana actual
        this.dispose();


    }//GEN-LAST:event_btnRegresarActionPerformed

    private void btnAddTableReporteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddTableReporteActionPerformed

        try {
            // Validación de campos
            if (jSDiaMesAnio.getValue() == null
                    || boxNombre.getSelectedItem() == null
                    || boxTipoUnidad.getSelectedItem() == null
                    || boxHorarioRegistrado.getSelectedItem() == null) {

                JOptionPane.showMessageDialog(this,
                        "Todos los campos son obligatorios",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Obtener valores
            Date fecha = (Date) jSDiaMesAnio.getValue();
            String nombre = boxNombre.getSelectedItem().toString();
            String tipoUnidad = boxTipoUnidad.getSelectedItem().toString().replace("ú", "u"); // Normalizar
            String horario = boxHorarioRegistrado.getSelectedItem().toString();

            // Obtener datos
            UnidadDAO unidadDAO = new UnidadDAO();
            String estadoUnidad = unidadDAO.obtenerEstadoUnidadPorPropietario(nombre);
            double ingresoBruto = unidadDAO.obtenerIngresoBrutoPorPropietario(nombre);

            // Calcular valores
            //double comisionTotal = CalculoIngresos.calcularComisionTotal(ingresoBruto, tipoUnidad);
            //double gananciaPropietario = ingresoBruto - comisionTotal;
            // Dentro de btnAddTableReporteActionPerformed
            double comisionTotal = CalculoIngresos.calcularComisionTotal(ingresoBruto, tipoUnidad);
            double gananciaPropietario = CalculoIngresos.calcularGananciaPropietario(ingresoBruto, comisionTotal);

            // Formatear y agregar fila
            String fechaFormateada = new SimpleDateFormat("dd/MM/yyyy").format(fecha);
            DefaultTableModel model = (DefaultTableModel) jTableGenerarReportes.getModel();
            model.addRow(new Object[]{
                fechaFormateada,
                tipoUnidad,
                nombre,
                horario,
                estadoUnidad,
                ingresoBruto,
                comisionTotal,
                gananciaPropietario
            });

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnAddTableReporteActionPerformed

    /*
    
      try {
        // Validación de campos
        if (jSDiaMesAnio.getValue() == null
                || boxNombre.getSelectedItem() == null
                || boxTipoUnidad.getSelectedItem() == null
                || boxHorarioRegistrado.getSelectedItem() == null) {

            JOptionPane.showMessageDialog(this,
                    "Todos los campos son obligatorios",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Obtener valores
        Date fecha = (Date) jSDiaMesAnio.getValue();
        String nombre = boxNombre.getSelectedItem().toString();
        String tipoUnidad = boxTipoUnidad.getSelectedItem().toString().replace("ú", "u"); // Normalizar
        String horario = boxHorarioRegistrado.getSelectedItem().toString();

        // Obtener datos
        UnidadDAO unidadDAO = new UnidadDAO();
        String estadoUnidad = unidadDAO.obtenerEstadoUnidadPorPropietario(nombre);
        double ingresoBruto = unidadDAO.obtenerIngresoBrutoPorPropietario(nombre);

        if (ingresoBruto <= 0.0) {
            JOptionPane.showMessageDialog(this, "No se encontraron ingresos para el propietario: " + nombre, "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Calcular valores
        double comisionTotal = CalculoIngresos.calcularComisionTotal(ingresoBruto, tipoUnidad);
        double gananciaPropietario = ingresoBruto - comisionTotal;

        // Formatear y agregar fila
        String fechaFormateada = new SimpleDateFormat("dd/MM/yyyy").format(fecha);
        DefaultTableModel model = (DefaultTableModel) jTableGenerarReportes.getModel();
        model.addRow(new Object[]{
            fechaFormateada,       // Fecha
            tipoUnidad,            // Unidad
            nombre,                // Propietario
            horario,               // Horario registrado
            estadoUnidad,          // Estado Unidad
            String.format("%.2f", ingresoBruto), // Ingreso Bruto
            String.format("%.2f", comisionTotal), // Comisión
            String.format("%.2f", gananciaPropietario) // Ganancia
        });

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this,
                "Error: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
    }
    
    
    
     */
 /*
        
    
    
    
     */
    //Otra actualizacion 
    /*
    
    
       private void btnAddTableReporteActionPerformed(java.awt.event.ActionEvent evt) {                                                   
        try {
            if (jSDiaMesAnio.getValue() == null
                    || boxNombre.getSelectedItem() == null
                    || boxTipoUnidad.getSelectedItem() == null
                    || boxHorarioRegistrado.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(this,
                        "Todos los campos son obligatorios",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            Date fecha = (Date) jSDiaMesAnio.getValue();
            String nombre = boxNombre.getSelectedItem().toString();
            String tipoUnidad = boxTipoUnidad.getSelectedItem().toString();
            String horario = boxHorarioRegistrado.getSelectedItem().toString();

            UnidadDAO unidadDAO = new UnidadDAO();
            String estadoUnidad = unidadDAO.obtenerEstadoUnidadPorPropietario(nombre);

            // Aquí deberías obtener el ingreso bruto real, de momento pondré un ejemplo de 1000
            double ingresoBruto = unidadDAO.obtenerIngresoBrutoPorPropietario(nombre);

            double comisionPorcentaje = tipoUnidad.equals("Propietario único") ? 10.0 : 15.0;
            double comisionDecimal = comisionPorcentaje / 100.0;
            double gananciaTotal = ingresoBruto * (1 - comisionDecimal); // Fórmula: ingreso - comisión

            String fechaFormateada = new SimpleDateFormat("dd/MM/yyyy").format(fecha);

            DefaultTableModel model = (DefaultTableModel) jTableGenerarReportes.getModel();

            model.addRow(new Object[]{
                fechaFormateada,
                tipoUnidad,
                nombre,
                horario,
                estadoUnidad,
                ingresoBruto,
                comisionPorcentaje,
                gananciaTotal
            });

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }                    
    
     */
    //Implementacion que funciona 
    /*
    rivate void btnAddTableReporteActionPerformed(java.awt.event.ActionEvent evt) {                                                   
        try {
            // Validar campos requeridos
            if (jSDiaMesAnio.getValue() == null
                    || boxNombre.getSelectedItem() == null
                    || boxTipoUnidad.getSelectedItem() == null
                    || boxHorarioRegistrado.getSelectedItem() == null) {

                JOptionPane.showMessageDialog(this,
                        "Todos los campos son obligatorios",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Obtener valores
            Date fecha = (Date) jSDiaMesAnio.getValue();
            String nombre = boxNombre.getSelectedItem().toString();
            String tipoUnidad = boxTipoUnidad.getSelectedItem().toString();
            String horario = boxHorarioRegistrado.getSelectedItem().toString();

            // Obtener datos adicionales
            UnidadDAO unidadDAO = new UnidadDAO();
            String estadoUnidad = unidadDAO.obtenerEstadoUnidadPorPropietario(nombre);

            // Calcular comisión (ejemplo)
            double comision = tipoUnidad.equals("Propietario único") ? 10.0 : 15.0;

            // Formatear fecha
            String fechaFormateada = new SimpleDateFormat("dd/MM/yyyy").format(fecha);

            // Obtener modelo de tabla
            DefaultTableModel model = (DefaultTableModel) jTableGenerarReportes.getModel();

            // Agregar nueva fila
            model.addRow(new Object[]{
                fechaFormateada,
                tipoUnidad,
                nombre,
                horario,
                estadoUnidad,
                0.0, // Ingreso Bruto (debes implementar esta lógica)
                comision,
                0.0 // Ganancia Total (calcular basado en comisión e ingreso)
            });

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }

    }                                                  

     */

    private void btnLimpiarFormularioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarFormularioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnLimpiarFormularioActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        System.exit(0);
    }//GEN-LAST:event_btnCancelarActionPerformed
    private String convertirFecha(String fechaOriginal) {
        try {
            java.util.Date fecha = new SimpleDateFormat("dd/MM/yyyy").parse(fechaOriginal);
            return new SimpleDateFormat("yyyy-MM-dd").format(fecha);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void btnGuardarBDExportarPDFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarBDExportarPDFActionPerformed

        String tipoReporte = boxTipoReporte.getSelectedItem().toString();
        boolean guardadoCorrectamente = false;
        boolean pdfGenerado = false;

        try {
            if (tipoReporte.equalsIgnoreCase("Diario")) {
                guardadoCorrectamente = guardarEnDiario();
                if (guardadoCorrectamente) {
                    pdfGenerado = generarReporteDiarioPDF();
                }
            } else if (tipoReporte.equalsIgnoreCase("Semanalmente")) {
                guardadoCorrectamente = guardarEnSemanalmente();
                if (guardadoCorrectamente) {
                    pdfGenerado = generarReporteSemanalPDF();
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error general: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }

        mostrarResultadoFinal(guardadoCorrectamente, pdfGenerado);
    }//GEN-LAST:event_btnGuardarBDExportarPDFActionPerformed

    /*String tipoReporte = boxTipoReporte.getSelectedItem().toString();

        if (tipoReporte.equalsIgnoreCase("Diario")) {
            // Guardar en Diario
            Diario diarioDAO = new Diario();
            DefaultTableModel model = (DefaultTableModel) jTableGenerarReportes.getModel();
            boolean guardadoCorrectamente = false;

            for (int i = 0; i < model.getRowCount(); i++) {
                try {
                    String fecha = convertirFecha(model.getValueAt(i, 0).toString());
                    String unidad = model.getValueAt(i, 1).toString();
                    String propietario = model.getValueAt(i, 2).toString();
                    double ingresosBrutos = Double.parseDouble(model.getValueAt(i, 5).toString());
                    double comisionPorcentaje = Double.parseDouble(model.getValueAt(i, 6).toString());
                    double gananciaPropietario = Double.parseDouble(model.getValueAt(i, 7).toString());

                    boolean resultado = diarioDAO.guardarDiario(fecha, unidad, propietario, ingresosBrutos, comisionPorcentaje, (ingresosBrutos * (comisionPorcentaje / 100)), gananciaPropietario);
                    if (resultado) {
                        guardadoCorrectamente = true;
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            if (guardadoCorrectamente) {
                javax.swing.JOptionPane.showMessageDialog(this, "Los datos se han guardado correctamente en el Diario.");
            } else {
                javax.swing.JOptionPane.showMessageDialog(this, "Hubo un error al guardar los datos en el Diario.");
            }

        } else if (tipoReporte.equalsIgnoreCase("Semanalmente")) {
            // Guardar en Semanalmente
            Semanalmente semanalmenteDAO = new Semanalmente();
            DefaultTableModel model = (DefaultTableModel) jTableGenerarReportes.getModel();
            boolean guardadoCorrectamente = false;

            for (int i = 0; i < model.getRowCount(); i++) {
                try {
                    String fecha = convertirFecha(model.getValueAt(i, 0).toString());
                    String unidad = model.getValueAt(i, 1).toString();
                    String propietario = model.getValueAt(i, 2).toString();
                    String horarioRegistrado = model.getValueAt(i, 3).toString();
                    String estadoUnidad = model.getValueAt(i, 4).toString();
                    double ingresosBrutos = Double.parseDouble(model.getValueAt(i, 5).toString());
                    double comisionPorcentaje = Double.parseDouble(model.getValueAt(i, 6).toString());
                    double gananciaTotal = Double.parseDouble(model.getValueAt(i, 7).toString());

                    boolean resultado = semanalmenteDAO.guardarSemanalmente(fecha, unidad, propietario, horarioRegistrado, estadoUnidad, ingresosBrutos, comisionPorcentaje, gananciaTotal);
                    if (resultado) {
                        guardadoCorrectamente = true;
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            if (guardadoCorrectamente) {
                javax.swing.JOptionPane.showMessageDialog(this, "Los datos se han guardado correctamente en el reporte Semanal.");
            } else {
                javax.swing.JOptionPane.showMessageDialog(this, "Hubo un error al guardar los datos en el reporte Semanal.");
            }
        }*/
    private boolean guardarEnDiario() {
        Diario diarioDAO = new Diario();
        DefaultTableModel model = (DefaultTableModel) jTableGenerarReportes.getModel();
        boolean resultado = false;

        for (int i = 0; i < model.getRowCount(); i++) {
            try {
                String fecha = convertirFecha(model.getValueAt(i, 0).toString());
                String unidad = model.getValueAt(i, 1).toString();
                String propietario = model.getValueAt(i, 2).toString();
                double ingresosBrutos = Double.parseDouble(model.getValueAt(i, 5).toString());
                double comisionPorcentaje = Double.parseDouble(model.getValueAt(i, 6).toString());
                double gananciaPropietario = Double.parseDouble(model.getValueAt(i, 7).toString());

                resultado = diarioDAO.guardarDiario(
                        fecha, unidad, propietario,
                        ingresosBrutos, comisionPorcentaje,
                        (ingresosBrutos * (comisionPorcentaje / 100)),
                        gananciaPropietario
                );
            } catch (Exception ex) {
                ex.printStackTrace();
                return false;
            }
        }
        return resultado;
    }

    private boolean guardarEnSemanalmente() {
        Semanalmente semanalDAO = new Semanalmente();
        DefaultTableModel model = (DefaultTableModel) jTableGenerarReportes.getModel();
        boolean resultado = false;

        for (int i = 0; i < model.getRowCount(); i++) {
            try {
                String fecha = convertirFecha(model.getValueAt(i, 0).toString());
                String unidad = model.getValueAt(i, 1).toString();
                String propietario = model.getValueAt(i, 2).toString();
                String horarioRegistrado = model.getValueAt(i, 3).toString();
                String estadoUnidad = model.getValueAt(i, 4).toString();
                double ingresosBrutos = Double.parseDouble(model.getValueAt(i, 5).toString());
                double comisionPorcentaje = Double.parseDouble(model.getValueAt(i, 6).toString());
                double gananciaTotal = Double.parseDouble(model.getValueAt(i, 7).toString());

                resultado = semanalDAO.guardarSemanalmente(
                        fecha, unidad, propietario,
                        horarioRegistrado, estadoUnidad,
                        ingresosBrutos, comisionPorcentaje,
                        gananciaTotal
                );
            } catch (Exception ex) {
                ex.printStackTrace();
                return false;
            }
        }
        return resultado;
    }

    private boolean generarReporteDiarioPDF() {
        String nombreArchivo = "Reporte_Diario.pdf";
        String rutaCarpeta = System.getProperty("user.home") + "/OneDrive/Documentos/Escritorio";
        String rutaCompleta = rutaCarpeta + "/" + nombreArchivo;

        try {
            // Verificar si la carpeta existe
            File carpeta = new File(rutaCarpeta);
            if (!carpeta.exists()) {
                carpeta.mkdirs();
            }

            Document documento = new Document();
            PdfWriter.getInstance(documento, new FileOutputStream(rutaCompleta));
            documento.open();

            // TÍTULO
            Font fontTitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 24, BaseColor.BLUE);
            Paragraph titulo = new Paragraph("Reporte Diario de Ingresos", fontTitulo);
            titulo.setAlignment(Element.ALIGN_CENTER);
            documento.add(titulo);

            // SUBTÍTULO
            Font fontSubtitulo = FontFactory.getFont(FontFactory.HELVETICA, 16, BaseColor.DARK_GRAY);
            Paragraph subtitulo = new Paragraph("Detalle de ingresos, comisiones y ganancias registrados.", fontSubtitulo);
            subtitulo.setAlignment(Element.ALIGN_CENTER);
            subtitulo.setSpacingAfter(20);
            documento.add(subtitulo);

            // Tabla
            PdfPTable tabla = new PdfPTable(7);
            tabla.setWidthPercentage(100);
            agregarEncabezadosDiarioConEstilo(tabla);

            Diario diarioDAO = new Diario();
            List<Object[]> registros = diarioDAO.obtenerTodosRegistros();

            for (Object[] registro : registros) {
                for (Object dato : registro) {
                    PdfPCell cell = new PdfPCell(new Phrase(String.valueOf(dato)));
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setPadding(5);
                    tabla.addCell(cell);
                }
            }

            documento.add(tabla);
            documento.close();

            return verificarPDFGenerado(rutaCompleta, "Diario");

        } catch (DocumentException | IOException e) {
            manejarErrorPDF(e);
            return false;
        }
        /*String nombreArchivo = "Reporte_Diario.pdf";
        String rutaCarpeta = System.getProperty("user.home") + "/OneDrive/Documentos/Escritorio";
        String rutaCompleta = rutaCarpeta + "/" + nombreArchivo;

        try {
            // Verificar si la carpeta existe
            File carpeta = new File(rutaCarpeta);
            if (!carpeta.exists()) {
                carpeta.mkdirs();
            }

            Document documento = new Document();
            PdfWriter.getInstance(documento, new FileOutputStream(rutaCompleta));
            documento.open();

            PdfPTable tabla = new PdfPTable(7); // 7 columnas para Diario
            agregarEncabezadosDiario(tabla);

            Diario diarioDAO = new Diario();
            List<Object[]> registros = diarioDAO.obtenerTodosRegistros();

            for (Object[] registro : registros) {
                for (Object dato : registro) {
                    tabla.addCell(String.valueOf(dato));
                }
            }

            documento.add(tabla);
            documento.close();

            return verificarPDFGenerado(rutaCompleta, "Diario");

        } catch (DocumentException | IOException e) {
            manejarErrorPDF(e);
            return false;
        }*/

    }

    /* String nombreArchivo = "Reporte_Diario.pdf";
        String rutaCompleta = System.getProperty("user.home") + "/Desktop/" + nombreArchivo;

        try {
            Document documento = new Document();
            PdfWriter.getInstance(documento, new FileOutputStream(rutaCompleta));
            documento.open();

            PdfPTable tabla = new PdfPTable(7);
            agregarEncabezadosDiario(tabla);

            Diario diarioDAO = new Diario();
            List<Object[]> registros = diarioDAO.obtenerTodosRegistros();

            for (Object[] registro : registros) {
                for (Object dato : registro) {
                    tabla.addCell(dato.toString());
                }
            }

            documento.add(tabla);
            documento.close();

            return verificarPDFGenerado(rutaCompleta, "Diario");

        } catch (DocumentException | IOException e) {
            manejarErrorPDF(e);
            return false;
        }*/
    //Implementacion funcional
    /*
    
    
     */
    private boolean generarReporteSemanalPDF() {
        String nombreArchivo = "Reporte_Semanal.pdf";
        String rutaCarpeta = System.getProperty("user.home") + "/OneDrive/Documentos/Escritorio";
        String rutaCompleta = rutaCarpeta + "/" + nombreArchivo;

        try {
            // Verificar si la carpeta existe
            File carpeta = new File(rutaCarpeta);
            if (!carpeta.exists()) {
                carpeta.mkdirs();
            }

            Document documento = new Document();
            PdfWriter.getInstance(documento, new FileOutputStream(rutaCompleta));
            documento.open();

            // TÍTULO
            Font fontTitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 24, BaseColor.BLUE);
            Paragraph titulo = new Paragraph("Reporte Semanal de Ingresos", fontTitulo);
            titulo.setAlignment(Element.ALIGN_CENTER);
            documento.add(titulo);

            // SUBTÍTULO
            Font fontSubtitulo = FontFactory.getFont(FontFactory.HELVETICA, 16, BaseColor.DARK_GRAY);
            Paragraph subtitulo = new Paragraph("Resumen semanal de turnos, unidades y ganancias.", fontSubtitulo);
            subtitulo.setAlignment(Element.ALIGN_CENTER);
            subtitulo.setSpacingAfter(20);
            documento.add(subtitulo);

            // Tabla
            PdfPTable tabla = new PdfPTable(8);
            tabla.setWidthPercentage(100);
            agregarEncabezadosSemanalConEstilo(tabla);

            Semanalmente semanalmenteDAO = new Semanalmente();
            List<Object[]> registros = semanalmenteDAO.obtenerTodosRegistros();

            for (Object[] registro : registros) {
                for (Object dato : registro) {
                    PdfPCell cell = new PdfPCell(new Phrase(String.valueOf(dato)));
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setPadding(5);
                    tabla.addCell(cell);
                }
            }

            documento.add(tabla);
            documento.close();

            return verificarPDFGenerado(rutaCompleta, "Semanal");

        } catch (DocumentException | IOException e) {
            manejarErrorPDF(e);
            return false;
        }

        /*String nombreArchivo = "Reporte_Semanal.pdf";
        String rutaCarpeta = System.getProperty("user.home") + "/OneDrive/Documentos/Escritorio";
        String rutaCompleta = rutaCarpeta + "/" + nombreArchivo;

        try {
            // Verificar si la carpeta existe
            File carpeta = new File(rutaCarpeta);
            if (!carpeta.exists()) {
                carpeta.mkdirs();
            }

            Document documento = new Document();
            PdfWriter.getInstance(documento, new FileOutputStream(rutaCompleta));
            documento.open();

            PdfPTable tabla = new PdfPTable(8); // 8 columnas para Semanalmente
            agregarEncabezadosSemanal(tabla);

            Semanalmente semanalmenteDAO = new Semanalmente();
            List<Object[]> registros = semanalmenteDAO.obtenerTodosRegistros();

            for (Object[] registro : registros) {
                for (Object dato : registro) {
                    tabla.addCell(String.valueOf(dato));
                }
            }

            documento.add(tabla);
            documento.close();

            return verificarPDFGenerado(rutaCompleta, "Semanal");

        } catch (DocumentException | IOException e) {
            manejarErrorPDF(e);
            return false;
        }*/
    }

    /*String nombreArchivo = "Reporte_Semanal.pdf";
        String rutaCompleta = System.getProperty("user.home") + "/Desktop/" + nombreArchivo;

        try {
            Document documento = new Document();
            PdfWriter.getInstance(documento, new FileOutputStream(rutaCompleta));
            documento.open();

            PdfPTable tabla = new PdfPTable(8);
            agregarEncabezadosSemanal(tabla);

            Semanalmente semanalDAO = new Semanalmente();
            List<Object[]> registros = semanalDAO.obtenerTodosRegistros();

            for (Object[] registro : registros) {
                for (Object dato : registro) {
                    tabla.addCell(dato.toString());
                }
            }

            documento.add(tabla);
            documento.close();

            return verificarPDFGenerado(rutaCompleta, "Semanal");

        } catch (DocumentException | IOException e) {
            manejarErrorPDF(e);
            return false;
        }*/
    //Implementacion que funciona 
    /*
    
     */
    private void agregarEncabezadosDiario(PdfPTable tabla) {
        tabla.addCell("Fecha");
        tabla.addCell("Unidad");
        tabla.addCell("Propietario");
        tabla.addCell("Ingresos Brutos");
        tabla.addCell("Comisión (%)");
        tabla.addCell("Comisión Total");
        tabla.addCell("Ganancia Propietario");
    }

    private void agregarEncabezadosSemanal(PdfPTable tabla) {
        tabla.addCell("Fecha");
        tabla.addCell("Unidad");
        tabla.addCell("Propietario");
        tabla.addCell("Horario Registrado");
        tabla.addCell("Estado Unidad");
        tabla.addCell("Ingresos Brutos");
        tabla.addCell("Comisión (%)");
        tabla.addCell("Ganancia Total");
    }
    //String ruta, String tipo
    /// String rutaArchivo, String tipoReporte

    private boolean verificarPDFGenerado(String rutaArchivo, String tipoReporte) {
        File archivo = new File(rutaArchivo);
        if (archivo.exists()) {
            System.out.println("El reporte " + tipoReporte + " fue generado exitosamente en: " + rutaArchivo);
            return true;
        } else {
            System.out.println("Error: no se pudo generar el reporte " + tipoReporte + ".");
            return false;
        }

        /*File archivo = new File(ruta);
        if (archivo.exists() && archivo.length() > 0) {
            try {
                Desktop.getDesktop().open(archivo);
                JOptionPane.showMessageDialog(this,
                        "PDF " + tipo + " generado con éxito!\n"
                        + "Ubicación: " + ruta,
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE);
                return true;
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this,
                        "PDF generado pero no se pudo abrir:\n" + e.getMessage(),
                        "Advertencia",
                        JOptionPane.WARNING_MESSAGE);
                return true;
            }
        }
        return false;*/
    }

    private void agregarEncabezadosDiarioConEstilo(PdfPTable tabla) {
        String[] encabezados = {"Fecha", "Unidad", "Propietario", "Ingresos Brutos", "Comisión %", "Comisión Total", "Ganancia Propietario"};
        for (String encabezado : encabezados) {
            PdfPCell header = new PdfPCell();
            header.setBackgroundColor(new BaseColor(70, 130, 180)); // Azul clarito
            header.setPhrase(new Phrase(encabezado, FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14, BaseColor.WHITE)));
            header.setHorizontalAlignment(Element.ALIGN_CENTER);
            header.setPadding(8);
            tabla.addCell(header);
        }
    }

    private void agregarEncabezadosSemanalConEstilo(PdfPTable tabla) {
        String[] encabezados = {"Fecha", "Unidad", "Propietario", "Horario", "Estado Unidad", "Ingresos Brutos", "Comisión %", "Ganancia Total"};
        for (String encabezado : encabezados) {
            PdfPCell header = new PdfPCell();
            header.setBackgroundColor(new BaseColor(70, 130, 180)); // Azul clarito
            header.setPhrase(new Phrase(encabezado, FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14, BaseColor.WHITE)));
            header.setHorizontalAlignment(Element.ALIGN_CENTER);
            header.setPadding(8);
            tabla.addCell(header);
        }
    }

    private void manejarErrorPDF(Exception e) {
        JOptionPane.showMessageDialog(this,
                "Error al generar PDF: " + e.getMessage()
                + "\nRevise:\n1. Permisos de escritura\n2. Conexión a BD\n3. Formato de datos",
                "Error Crítico",
                JOptionPane.ERROR_MESSAGE);
    }

    private void mostrarResultadoFinal(boolean guardado, boolean pdfGenerado) {
        if (guardado && pdfGenerado) {
            JOptionPane.showMessageDialog(this,
                    "Operación completada con éxito!\n"
                    + "Datos guardados y PDF generado correctamente.",
                    "Éxito Total",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            String mensaje = "Resultado parcial:\n";
            if (guardado) {
                mensaje += "✓ Datos guardados\n✗ PDF no generado";
            } else if (pdfGenerado) {
                mensaje += "✗ Datos no guardados\n✓ PDF generado";
            } else {
                mensaje += "✗ Ambos procesos fallaron";
            }

            JOptionPane.showMessageDialog(this,
                    mensaje,
                    "Resultado Parcial",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    //Implementacion que funciona 
    /*
    
     String tipoReporte = boxTipoReporte.getSelectedItem().toString();

        if (tipoReporte.equalsIgnoreCase("Diario")) {
            // Guardar en Diario
            Diario diarioDAO = new Diario();
            DefaultTableModel model = (DefaultTableModel) jTableGenerarReportes.getModel();
            boolean guardadoCorrectamente = false;

            for (int i = 0; i < model.getRowCount(); i++) {
                try {
                    String fecha = convertirFecha(model.getValueAt(i, 0).toString());
                    String unidad = model.getValueAt(i, 1).toString();
                    String propietario = model.getValueAt(i, 2).toString();
                    double ingresosBrutos = Double.parseDouble(model.getValueAt(i, 5).toString());
                    double comisionPorcentaje = Double.parseDouble(model.getValueAt(i, 6).toString());
                    double gananciaPropietario = Double.parseDouble(model.getValueAt(i, 7).toString());

                    boolean resultado = diarioDAO.guardarDiario(fecha, unidad, propietario, ingresosBrutos, comisionPorcentaje, (ingresosBrutos * (comisionPorcentaje / 100)), gananciaPropietario);
                    if (resultado) {
                        guardadoCorrectamente = true;
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            if (guardadoCorrectamente) {
                javax.swing.JOptionPane.showMessageDialog(this, "Los datos se han guardado correctamente en el Diario.");
            } else {
                javax.swing.JOptionPane.showMessageDialog(this, "Hubo un error al guardar los datos en el Diario.");
            }

        } else if (tipoReporte.equalsIgnoreCase("Semanalmente")) {
            // Guardar en Semanalmente
            Semanalmente semanalmenteDAO = new Semanalmente();
            DefaultTableModel model = (DefaultTableModel) jTableGenerarReportes.getModel();
            boolean guardadoCorrectamente = false;

            for (int i = 0; i < model.getRowCount(); i++) {
                try {
                    String fecha = convertirFecha(model.getValueAt(i, 0).toString());
                    String unidad = model.getValueAt(i, 1).toString();
                    String propietario = model.getValueAt(i, 2).toString();
                    String horarioRegistrado = model.getValueAt(i, 3).toString();
                    String estadoUnidad = model.getValueAt(i, 4).toString();
                    double ingresosBrutos = Double.parseDouble(model.getValueAt(i, 5).toString());
                    double comisionPorcentaje = Double.parseDouble(model.getValueAt(i, 6).toString());
                    double gananciaTotal = Double.parseDouble(model.getValueAt(i, 7).toString());

                    boolean resultado = semanalmenteDAO.guardarSemanalmente(fecha, unidad, propietario, horarioRegistrado, estadoUnidad, ingresosBrutos, comisionPorcentaje, gananciaTotal);
                    if (resultado) {
                        guardadoCorrectamente = true;
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            if (guardadoCorrectamente) {
                javax.swing.JOptionPane.showMessageDialog(this, "Los datos se han guardado correctamente en el reporte Semanal.");
            } else {
                javax.swing.JOptionPane.showMessageDialog(this, "Hubo un error al guardar los datos en el reporte Semanal.");
            }
        }

    
    
    
    
     */

    private void JpGestionIngresosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JpGestionIngresosMouseClicked
        GestionIngresosGUI ingresos = new GestionIngresosGUI();
        ingresos.setVisible(true);

        if (selectedPanel != null) {
            selectedPanel.setBackground(new Color(44, 90, 120)); // Color original al deseleccionar
        }
        selectedPanel = JpGestionIngresos;
        JpGestionIngresos.setBackground(new Color(68, 122, 156));
        JOptionPane.showMessageDialog(this, "Gestión de ingresos seleccionada.", "Información", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_JpGestionIngresosMouseClicked

    private void JpGestionIngresosMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JpGestionIngresosMouseEntered
        if (selectedPanel != JpGestionIngresos) {
            JpGestionIngresos.setBackground(new Color(68, 122, 156));
        }
    }//GEN-LAST:event_JpGestionIngresosMouseEntered
    private void JpGestionIngresosMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JpGestionIngresosMouseExited
        if (selectedPanel != JpGestionIngresos) {
            JpGestionIngresos.setBackground(new Color(44, 90, 120));
        }
    }//GEN-LAST:event_JpGestionIngresosMouseExited

    private void JpRegistroTurnoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JpRegistroTurnoMouseClicked
        RegistroTurnoGUI ingresos = new RegistroTurnoGUI();
        ingresos.setVisible(true);

        if (selectedPanel != null) {
            selectedPanel.setBackground(new Color(44, 90, 120));
        }
        selectedPanel = JpRegistroTurno;
        JpRegistroTurno.setBackground(new Color(68, 122, 156));
        JOptionPane.showMessageDialog(this, "Registro de turno seleccionado.", "Información", JOptionPane.INFORMATION_MESSAGE);

    }//GEN-LAST:event_JpRegistroTurnoMouseClicked

    private void JpRegistroTurnoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JpRegistroTurnoMouseEntered
        if (selectedPanel != JpRegistroTurno) {
            JpRegistroTurno.setBackground(new Color(68, 122, 156));
        }
    }//GEN-LAST:event_JpRegistroTurnoMouseEntered

    private void JpRegistroTurnoMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JpRegistroTurnoMouseExited
        // TODO add your handling code here:
        if (selectedPanel != JpRegistroTurno) {
            JpRegistroTurno.setBackground(new Color(44, 90, 120));
        }
    }//GEN-LAST:event_JpRegistroTurnoMouseExited

    private void JpReportesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JpReportesMouseClicked
        // TODO add your handling code here:
        ReportesGUI rep = new ReportesGUI();
        rep.setVisible(true);

        if (selectedPanel != null) {
            selectedPanel.setBackground(new Color(44, 90, 120));
        }
        selectedPanel = JpReportes;
        JpReportes.setBackground(new Color(68, 122, 156));
        JOptionPane.showMessageDialog(this, "Reporte generado.", "Información", JOptionPane.INFORMATION_MESSAGE);

    }//GEN-LAST:event_JpReportesMouseClicked

    private void JpReportesMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JpReportesMouseEntered
        // TODO add your handling code here:
        if (selectedPanel != JpReportes) {
            JpReportes.setBackground(new Color(68, 122, 156));
        }
    }//GEN-LAST:event_JpReportesMouseEntered

    private void JpReportesMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JpReportesMouseExited
        // TODO add your handling code here:
        if (selectedPanel != JpReportes) {
            JpReportes.setBackground(new Color(68, 122, 156));
        }
    }//GEN-LAST:event_JpReportesMouseExited

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        // TODO add your handling code here:

        try {
            String nombreBuscado = boxNombre.getSelectedItem().toString().trim();

            DefaultTableModel model = (DefaultTableModel) jTableGenerarReportes.getModel();
            DefaultTableModel modeloFiltrado = new DefaultTableModel(
                    new Object[]{"Fecha", "Unidad", "Propietario", "Horario registrado", "Estado Unidad", "Ingreso Bruto", "Comisión (%)", "Ganancia Total"}, 0
            );

            for (int i = 0; i < model.getRowCount(); i++) {
                String propietario = model.getValueAt(i, 2).toString().trim();
                if (propietario.equalsIgnoreCase(nombreBuscado)) {
                    // Copiar la fila completa si coincide el nombre
                    Object[] fila = new Object[model.getColumnCount()];
                    for (int j = 0; j < model.getColumnCount(); j++) {
                        fila[j] = model.getValueAt(i, j);
                    }
                    modeloFiltrado.addRow(fila);
                }
            }

            jTableGenerarReportes.setModel(modeloFiltrado); // Mostrar solo resultados encontrados

        } catch (Exception e) {
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(this, "Error al buscar: " + e.getMessage());
        }
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void boxHorarioRegistradoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boxHorarioRegistradoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_boxHorarioRegistradoActionPerformed

    private void txtBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBuscarActionPerformed

    private void boxTipoUnidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boxTipoUnidadActionPerformed

    }//GEN-LAST:event_boxTipoUnidadActionPerformed

    private void boxNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boxNombreActionPerformed

    }//GEN-LAST:event_boxNombreActionPerformed

    private void boxTipoReporteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boxTipoReporteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_boxTipoReporteActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ReportesGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ReportesGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ReportesGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ReportesGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ReportesGUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel JpGestionIngresos;
    private javax.swing.JPanel JpRegistroTurno;
    private javax.swing.JPanel JpReportes;
    private javax.swing.JComboBox<String> boxHorarioRegistrado;
    private javax.swing.JComboBox<String> boxNombre;
    private javax.swing.JComboBox<String> boxTipoReporte;
    private javax.swing.JComboBox<String> boxTipoUnidad;
    private javax.swing.JButton btnAddTableReporte;
    private javax.swing.JButton btnAdelante;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnGuardarBDExportarPDF;
    private javax.swing.JButton btnLimpiarFormulario;
    private javax.swing.JButton btnRegresar;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JSpinner jSDiaMesAnio;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable jTableGenerarReportes;
    private javax.swing.JTextField txtBuscar;
    // End of variables declaration//GEN-END:variables
}
