package modelo;

import java.io.Serializable;

public abstract class Inmueble implements Serializable {
    // Encapsulamiento: atributos privados
    private static final long serialVersionUID = 1L;
    private String direccion;
    private double area;
    private double precio;
    private String tipoOperacion; // "venta" o "alquiler"

    public Inmueble(String direccion, double area, double precio, String tipoOperacion) {
        this.direccion = direccion;
        this.area = area;
        this.precio = precio;
        this.tipoOperacion = tipoOperacion;
    }

    // Getters y Setters
    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public double getArea() { return area; }
    public void setArea(double area) { this.area = area; }

    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }

    public String getTipoOperacion() { return tipoOperacion; }
    public void setTipoOperacion(String tipoOperacion) { this.tipoOperacion = tipoOperacion; }

    // Método que será sobrescrito (Polimorfismo)
    public abstract String getTipoInmueble();

    // Método para mostrar información (polimorfismo)
    public String mostrarInfo() {
    return getTipoInmueble() + " - " + direccion + " | " + area + " m² | $" + precio + " | " + tipoOperacion;
}

    @Override
    public String toString() {
        return getTipoInmueble() + ";" + direccion + ";" + area + ";" + precio + ";" + tipoOperacion;
    }
}