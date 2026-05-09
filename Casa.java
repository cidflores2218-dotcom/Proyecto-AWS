package modelo;

public class Casa extends Inmueble {
    private int numPisos;
    private boolean tieneJardin;

    public Casa(String direccion, double area, double precio, String tipoOperacion, int numPisos, boolean tieneJardin) {
        super(direccion, area, precio, tipoOperacion);
        this.numPisos = numPisos;
        this.tieneJardin = tieneJardin;
    }

    public int getNumPisos() { return numPisos; }
    public void setNumPisos(int numPisos) { this.numPisos = numPisos; }

    public boolean isTieneJardin() { return tieneJardin; }
    public void setTieneJardin(boolean tieneJardin) { this.tieneJardin = tieneJardin; }

    @Override
    public String getTipoInmueble() {
        return "Casa";
    }

    @Override
public String mostrarInfo() {
    // Usamos super para traer lo que el padre ya sabe decir...
    // ¡Y le pegamos lo nuevo de la clase hija!
    return super.mostrarInfo() + " | Pisos: " + numPisos + " | Jardin: " + (tieneJardin ? "Si" : "No");
}

    @Override
    public String toString() {
        return super.toString() + ";" + numPisos + ";" + tieneJardin;
    }
}