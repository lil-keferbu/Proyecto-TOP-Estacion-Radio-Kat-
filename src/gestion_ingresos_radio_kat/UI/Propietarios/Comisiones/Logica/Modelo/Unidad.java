/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gestion_ingresos_radio_kat.UI.Propietarios.Comisiones.Logica.Modelo;

/**
 *
 * @author User
 */
public class Unidad {

    private String numeroPlaca;
    private String nombrePropietario;
    private String tipoUnidad;

    public Unidad(String numeroPlaca, String nombrePropietario, String tipoUnidad) {
        this.numeroPlaca = numeroPlaca;
        this.nombrePropietario = nombrePropietario;
        this.tipoUnidad = tipoUnidad;
    }

    public String getNumeroPlaca() {
        return numeroPlaca;
    }

    public String getNombrePropietario() {
        return nombrePropietario;
    }

    public String getTipoUnidad() {
        return tipoUnidad;
    }
}
