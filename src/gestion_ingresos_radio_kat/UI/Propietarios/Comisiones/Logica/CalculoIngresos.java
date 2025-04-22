package gestion_ingresos_radio_kat.UI.Propietarios.Comisiones.Logica;

public class CalculoIngresos {

    // Método para calcular la comisión total según el tipo de propietario
    public static double calcularComisionTotal(double ingresosBrutos, String tipoPropietario) {
        double porcentajeComision;

        // Definir porcentajes según el tipo de propietario
        switch (tipoPropietario.toLowerCase()) {
            case "propietario unico":
                porcentajeComision = 0.10; // 10%
                break;
            case "chofer externo":
                porcentajeComision = 0.05;  // 5%
                break;
            default:
                throw new IllegalArgumentException("Tipo de propietario no válido: " + tipoPropietario);
        }

        return ingresosBrutos * porcentajeComision;
    }

    // Método para calcular la ganancia del propietario (Ingresos Brutos + Comisión Total)
    public static double calcularGananciaPropietario(double ingresosBrutos, double comisionTotal) {
        return ingresosBrutos - comisionTotal;
    }

}
 