package ui;

import modelo.*;
import persistencia.ArchivoUtil;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class VentanaPrincipal extends JFrame {
    private GestorInmobiliario gestor;
    private DefaultTableModel modeloTabla;
    private JTextField txtDir, txtArea, txtPrecio, txtDatoEspecial, txtEliminarDir;
    private JCheckBox chkOpcional;
    private JComboBox<String> cbTipo, cbOp, fTipo, fOp;
    private JLabel lblEspecial;

    public VentanaPrincipal() {
        gestor = new GestorInmobiliario();
        
        // CORRECCIÓN DE PERSISTENCIA: 
        // Se asigna la lista cargada directamente al gestor para evitar copias vacías
        gestor.setInmuebles(ArchivoUtil.cargar());
        
        inicializarGUI();
    }

    private void inicializarGUI() {
        setTitle("Gestión Inmobiliaria Inteligente v2.0");
        setSize(1100, 750);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(15, 15));

        // --- FORMULARIO DINÁMICO (NORTE) ---
        JPanel pForm = new JPanel(new GridLayout(0, 4, 12, 12));
        pForm.setBorder(BorderFactory.createTitledBorder("Registro de Propiedades"));

        cbTipo = new JComboBox<>(new String[]{"Casa", "Apartamento", "LocalComercial"});
        cbOp = new JComboBox<>(new String[]{"venta", "alquiler"});
        txtDir = new JTextField(); 
        txtArea = new JTextField(); 
        txtPrecio = new JTextField();
        txtDatoEspecial = new JTextField();
        lblEspecial = new JLabel("N° Pisos:");
        chkOpcional = new JCheckBox("¿Tiene Jardín?");

        cbTipo.addActionListener(e -> actualizarLabels());

        pForm.add(new JLabel("Tipo:")); pForm.add(cbTipo);
        pForm.add(new JLabel("Operación:")); pForm.add(cbOp);
        pForm.add(new JLabel("Dirección:")); pForm.add(txtDir);
        pForm.add(new JLabel("Área (m²):")); pForm.add(txtArea);
        pForm.add(new JLabel("Precio ($):")); pForm.add(txtPrecio);
        pForm.add(lblEspecial); pForm.add(txtDatoEspecial);
        pForm.add(new JLabel("Adicional:")); pForm.add(chkOpcional);

        JButton btnAdd = new JButton("Registrar Inmueble");
        btnAdd.setFont(new Font("SansSerif", Font.BOLD, 12));
        btnAdd.addActionListener(e -> registrarInmueble());
        pForm.add(btnAdd);

        // --- TABLA DE DATOS (CENTRO) ---
        modeloTabla = new DefaultTableModel(new String[]{"Inmueble", "Ubicación", "Precio", "Uso", "Atributos Especiales"}, 0);
        JTable tabla = new JTable(modeloTabla);
        tabla.setRowHeight(30);
        tabla.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 12));

        // --- PANEL SUR: FILTROS Y ELIMINACIÓN ---
        JPanel pControl = new JPanel(new GridLayout(2, 1, 5, 5));
        
        JPanel pFiltros = new JPanel(new FlowLayout(FlowLayout.LEFT));
        fTipo = new JComboBox<>(new String[]{"Todos", "Casa", "Apartamento", "LocalComercial"});
        fOp = new JComboBox<>(new String[]{"Todos", "venta", "alquiler"});
        JButton btnFiltro = new JButton("Filtrar Lista");
        btnFiltro.addActionListener(e -> refrescarTabla());
        pFiltros.add(new JLabel("Filtrar por:")); pFiltros.add(fTipo); pFiltros.add(fOp); pFiltros.add(btnFiltro);

        JPanel pEliminar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        txtEliminarDir = new JTextField(25);
        JButton btnDelete = new JButton("Eliminar por Dirección");
        btnDelete.setForeground(Color.RED);
        btnDelete.addActionListener(e -> accionEliminar());
        pEliminar.add(new JLabel("Dirección exacta:")); pEliminar.add(txtEliminarDir); pEliminar.add(btnDelete);

        pControl.add(pFiltros);
        pControl.add(pEliminar);

        add(pForm, BorderLayout.NORTH);
        add(new JScrollPane(tabla), BorderLayout.CENTER);
        add(pControl, BorderLayout.SOUTH);

        refrescarTabla();
    }

    private void actualizarLabels() {
        String tipo = (String) cbTipo.getSelectedItem();
        switch (tipo) {
            case "Casa" -> { lblEspecial.setText("N° Pisos:"); chkOpcional.setText("¿Tiene Jardín?"); }
            case "Apartamento" -> { lblEspecial.setText("Piso N°:"); chkOpcional.setText("¿Tiene Ascensor?"); }
            case "LocalComercial" -> { lblEspecial.setText("Tipo Negocio:"); chkOpcional.setText("¿Tiene Vitrina?"); }
        }
    }

    private void registrarInmueble() {
        try {
            if (txtDir.getText().isEmpty() || txtArea.getText().isEmpty() || txtPrecio.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, llene todos los campos obligatorios.");
                return;
            }

            String precioLimpio = txtPrecio.getText().replace(",", "").trim();
            double precio = Double.parseDouble(precioLimpio);
            double area = Double.parseDouble(txtArea.getText().trim());
            
            String tipo = (String) cbTipo.getSelectedItem();
            String operacion = (String) cbOp.getSelectedItem();
            String direccion = txtDir.getText();
            String especial = txtDatoEspecial.getText().trim();

            Inmueble nuevo = null;

            switch (tipo) {
                case "Casa" -> {
                    int pisos = especial.isEmpty() ? 0 : Integer.parseInt(especial);
                    nuevo = new Casa(direccion, area, precio, operacion, pisos, chkOpcional.isSelected());
                }
                case "Apartamento" -> {
                    int numPiso = especial.isEmpty() ? 0 : Integer.parseInt(especial);
                    nuevo = new Apartamento(direccion, area, precio, operacion, numPiso, chkOpcional.isSelected());
                }
                case "LocalComercial" -> {
                    nuevo = new LocalComercial(direccion, area, precio, operacion, especial, chkOpcional.isSelected());
                }
            }

            if (nuevo != null) {
                gestor.agregarInmueble(nuevo);
                ArchivoUtil.guardar(gestor.getInmuebles());
                refrescarTabla();
                limpiar();
                JOptionPane.showMessageDialog(this, "¡Propiedad registrada con éxito!");
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Error: Área, Precio y Pisos deben ser números.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error inesperado: " + e.getMessage());
        }
    }

    private void refrescarTabla() {
        modeloTabla.setRowCount(0);
        String selT = (String) fTipo.getSelectedItem();
        String selO = (String) fOp.getSelectedItem();

        for (Inmueble i : gestor.getInmuebles()) {
            if (!selT.equals("Todos") && !i.getTipoInmueble().equals(selT)) continue;
            if (!selO.equals("Todos") && !i.getTipoOperacion().equals(selO)) continue;

            String precioFormateado = String.format("%,.2f", i.getPrecio());
            String detalles = obtenerSoloAtributosEspeciales(i);

            modeloTabla.addRow(new Object[]{
                i.getTipoInmueble(), i.getDireccion(), 
                "$" + precioFormateado, i.getTipoOperacion(), detalles
            });
        }
    }

    private String obtenerSoloAtributosEspeciales(Inmueble i) {
        if (i instanceof Casa c) 
            return c.getNumPisos() + " pisos | Jardín: " + (c.isTieneJardin() ? "Sí" : "No");
        if (i instanceof Apartamento a) 
            return "Piso " + a.getPiso() + " | Ascensor: " + (a.isTieneAscensor() ? "Sí" : "No");
        if (i instanceof LocalComercial l) 
            return "Giro: " + l.getTipoNegocioRecomendado() + " | Vitrina: " + (l.isTieneVitrina() ? "Sí" : "No");
        return "";
    }

    private void accionEliminar() {
        if (gestor.eliminarInmueble(txtEliminarDir.getText())) {
            ArchivoUtil.guardar(gestor.getInmuebles());
            refrescarTabla();
            txtEliminarDir.setText("");
            JOptionPane.showMessageDialog(this, "Inmueble eliminado.");
        } else {
            JOptionPane.showMessageDialog(this, "Dirección no encontrada.");
        }
    }

    private void limpiar() {
        txtDir.setText(""); 
        txtArea.setText(""); 
        txtPrecio.setText(""); 
        txtDatoEspecial.setText("");
        chkOpcional.setSelected(false);
    }
}