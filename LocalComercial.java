package modelo;

public class LocalComercial extends Inmueble {
    private String tipoNegocioRecomendado;
    private boolean tieneVitrina;

    public LocalComercial(String direccion, double area, double precio, String tipoOperacion, String tipoNegocioRecomendado, boolean tieneVitrina) {
        super(direccion, area, precio, tipoOperacion);
        this.tipoNegocioRecomendado = tipoNegocioRecomendado;
        this.tieneVitrina = tieneVitrina;
    }

    public String getTipoNegocioRecomendado() { return tipoNegocioRecomendado; }
    public void setTipoNegocioRecomendado(String tipoNegocioRecomendado) { this.tipoNegocioRecomendado = tipoNegocioRecomendado; }

    public boolean isTieneVitrina() { return tieneVitrina; }
    public void setTieneVitrina(boolean tieneVitrina) { this.tieneVitrina = tieneVitrina; }

    @Override
    public String getTipoInmueble() {
        return "LocalComercial";
    }
    @Override
public String mostrarInfo() {
    return super.mostrarInfo() + " | Negocio: " + tipoNegocioRecomendado + " | Vitrina: " + (tieneVitrina ? "Sí" : "No");
}
    @Override
    public String toString() {
        return super.toString() + ";" + tipoNegocioRecomendado + ";" + tieneVitrina;
    }
}