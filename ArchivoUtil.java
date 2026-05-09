package persistencia;

import modelo.Inmueble;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ArchivoUtil {
    private static final String ARCHIVO = "inmuebles.dat";

    // Guarda la lista completa de objetos en un solo paso
    public static void guardar(List<Inmueble> inmuebles) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARCHIVO))) {
            oos.writeObject(inmuebles);
        } catch (IOException e) {
            System.err.println("Error al guardar: " + e.getMessage());
        }
    }

    // Carga la lista completa y maneja errores de archivo inexistente
    @SuppressWarnings("unchecked")
    public static List<Inmueble> cargar() {
        File file = new File(ARCHIVO);
        if (!file.exists()) return new ArrayList<>();

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (List<Inmueble>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error al cargar: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}