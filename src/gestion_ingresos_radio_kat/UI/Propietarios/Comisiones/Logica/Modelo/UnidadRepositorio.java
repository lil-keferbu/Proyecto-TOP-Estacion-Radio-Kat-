/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gestion_ingresos_radio_kat.UI.Propietarios.Comisiones.Logica.Modelo;
 
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author User
 */
public class UnidadRepositorio {
     private static List<Unidad> unidades = new ArrayList<>();

    public static void agregarUnidad(Unidad unidad) {
        unidades.add(unidad);
    }

    public static List<Unidad> obtenerUnidades() {
        return unidades;
    }

    public static Unidad buscarPorPlaca(String numeroPlaca) {
        for (Unidad unidad : unidades) {
            if (unidad.getNumeroPlaca().equals(numeroPlaca)) {
                return unidad;
            }
        }
        return null;
    }
}
