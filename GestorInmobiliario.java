package modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GestorInmobiliario {
    private List<Inmueble> inmuebles;

    public GestorInmobiliario() {
        inmuebles = new ArrayList<>();
    }

    public void setInmuebles(List<Inmueble> listaCargada) {
        this.inmuebles = listaCargada;
    }

    public void agregarInmueble(Inmueble i) {
        inmuebles.add(i);
    }

    public boolean eliminarInmueble(String direccion) {
        return inmuebles.removeIf(i -> i.getDireccion().equalsIgnoreCase(direccion));
    }

    public List<Inmueble> getInmuebles() {
        return new ArrayList<>(inmuebles);
    }

    public List<Inmueble> filtrarPorTipo(String tipo) {
        return inmuebles.stream()
                .filter(i -> i.getTipoInmueble().equalsIgnoreCase(tipo))
                .collect(Collectors.toList());
    }

    public List<Inmueble> filtrarPorOperacion(String operacion) {
        return inmuebles.stream()
                .filter(i -> i.getTipoOperacion().equalsIgnoreCase(operacion))
                .collect(Collectors.toList());
    }
}
