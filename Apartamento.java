package modelo;

public class Apartamento extends Inmueble {
    private int piso;
    private boolean tieneAscensor;

    public Apartamento(String direccion, double area, double precio, String tipoOperacion, int piso, boolean tieneAscensor) {
        super(direccion, area, precio, tipoOperacion);
        this.piso = piso;
        this.tieneAscensor = tieneAscensor;
    }

    public int getPiso() { return piso; }
    public void setPiso(int piso) { this.piso = piso; }

    public boolean isTieneAscensor() { return tieneAscensor; }
    public void setTieneAscensor(boolean tieneAscensor) { this.tieneAscensor = tieneAscensor; }

    @Override
    public String getTipoInmueble() {
        return "Apartamento";
    }
    @Override
public String mostrarInfo() {
    // Llamamos a lo que el padre ya sabe decir y le pegamos lo del apartamento
    return super.mostrarInfo() + " | Piso: " + piso + " | Ascensor: " + (tieneAscensor ? "Sí" : "No");
}
    @Override
    public String toString() {
        return super.toString() + ";" + piso + ";" + tieneAscensor;
    }
}