package vista;

import utilidades.ArchivoBinario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class Facturacion extends JFrame {
    private JComboBox<String> cmbComandas;
    private JTable tabla;
    private JTextField txtCliente, txtSubtotal, txtPropina, txtTotal;
    private final String RUTA_COMANDAS = "comandas.dat";

    public Facturacion() {
        setTitle("Facturación");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Selección de comanda
        JPanel panelTop = new JPanel(new FlowLayout());
        panelTop.add(new JLabel("Seleccionar comanda:"));
        cmbComandas = new JComboBox<>();
        panelTop.add(cmbComandas);
        JButton btnCargar = new JButton("Cargar");
        panelTop.add(btnCargar);
        add(panelTop, BorderLayout.NORTH);

        // Tabla
        String[] columnas = {"Cantidad", "Descripción", "Precio Unit", "Subtotal"};
        tabla = new JTable(new DefaultTableModel(columnas, 0));
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        // Totales
        JPanel panelBottom = new JPanel(new GridLayout(4, 2, 5, 5));
        panelBottom.add(new JLabel("Cliente:"));
        txtCliente = new JTextField(); panelBottom.add(txtCliente);
        panelBottom.add(new JLabel("Subtotal:"));
        txtSubtotal = new JTextField(); panelBottom.add(txtSubtotal);
        panelBottom.add(new JLabel("Propina (10%):"));
        txtPropina = new JTextField(); panelBottom.add(txtPropina);
        panelBottom.add(new JLabel("Total:"));
        txtTotal = new JTextField(); panelBottom.add(txtTotal);

        add(panelBottom, BorderLayout.SOUTH);

        // Cargar lista en combo
        cargarListaComandas();

        // Evento para cargar datos
        btnCargar.addActionListener(e -> cargarComandaSeleccionada());

        setVisible(true);
    }

    private void cargarListaComandas() {
        Object datos = ArchivoBinario.cargar(RUTA_COMANDAS);
        if (datos != null && datos instanceof ArrayList) {
            ArrayList<Object[]> lista = (ArrayList<Object[]>) datos;
            for (Object[] comanda : lista) {
                String estancia = (String) comanda[0];
                String mesa = (String) comanda[1];
                cmbComandas.addItem("Estancia: " + estancia + " | Mesa: " + mesa);
            }
        }
    }

    private void cargarComandaSeleccionada() {
        int index = cmbComandas.getSelectedIndex();
        if (index == -1) return;

        Object datos = ArchivoBinario.cargar(RUTA_COMANDAS);
        ArrayList<Object[]> lista = (ArrayList<Object[]>) datos;
        Object[] comanda = lista.get(index);

        String cliente = (String) comanda[2];
        ArrayList<String[]> pedidos = (ArrayList<String[]>) comanda[3];

        txtCliente.setText(cliente);

        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
        modelo.setRowCount(0);

        double subtotal = 0;
        for (String[] p : pedidos) {
            int cantidad = Integer.parseInt(p[0]);
            double precio = 10.0; // precio fijo
            double sub = cantidad * precio;
            subtotal += sub;
            modelo.addRow(new Object[]{p[0], p[1], precio, sub});
        }

        double propina = subtotal * 0.10;
        double total = subtotal + propina;

        txtSubtotal.setText(String.format("%.2f", subtotal));
        txtPropina.setText(String.format("%.2f", propina));
        txtTotal.setText(String.format("%.2f", total));
    }
}
