package vista;

import utilidades.ArchivoBinario;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class RegistroComanda extends JFrame {
    private JComboBox<String> cmbEstancia;
    private JTextField txtNumMesa, txtCliente, txtProducto, txtCantidad;
    private JTable tabla;
    private JButton btnAgregar, btnAceptar;

    private final String RUTA_COMANDAS = "comandas.dat";

    public RegistroComanda() {
        setTitle("Registrar Comanda");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel datos mesa
        JPanel panelMesa = new JPanel(new GridBagLayout());
        panelMesa.setBorder(new TitledBorder("Datos de la Mesa"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);

        gbc.gridx = 0; gbc.gridy = 0;
        panelMesa.add(new JLabel("Estancia:"), gbc);
        gbc.gridx = 1;
        cmbEstancia = new JComboBox<>(new String[]{"Las Brisas", "Caribe", "El Tesero"});
        panelMesa.add(cmbEstancia, gbc);

        gbc.gridx = 2;
        panelMesa.add(new JLabel("# Mesa:"), gbc);
        gbc.gridx = 3;
        txtNumMesa = new JTextField(5);
        panelMesa.add(txtNumMesa, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        panelMesa.add(new JLabel("Cliente:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 3;
        txtCliente = new JTextField(15);
        panelMesa.add(txtCliente, gbc);

        add(panelMesa, BorderLayout.NORTH);

        // Panel pedido
        JPanel panelPedido = new JPanel(new GridBagLayout());
        panelPedido.setBorder(new TitledBorder("Datos del Pedido"));
        gbc.gridwidth = 1;
        gbc.gridx = 0; gbc.gridy = 0;
        panelPedido.add(new JLabel("Producto:"), gbc);
        gbc.gridx = 1;
        txtProducto = new JTextField(12);
        panelPedido.add(txtProducto, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        panelPedido.add(new JLabel("Cantidad:"), gbc);
        gbc.gridx = 1;
        txtCantidad = new JTextField(5);
        panelPedido.add(txtCantidad, gbc);

        btnAgregar = new JButton("Agregar");
        gbc.gridx = 2; gbc.gridy = 1;
        panelPedido.add(btnAgregar, gbc);

        // Tabla
        String[] columnas = {"Cantidad", "Descripción"};
        tabla = new JTable(new DefaultTableModel(columnas, 0));
        JScrollPane scroll = new JScrollPane(tabla);

        JPanel centro = new JPanel(new BorderLayout());
        centro.add(panelPedido, BorderLayout.NORTH);
        centro.add(scroll, BorderLayout.CENTER);
        add(centro, BorderLayout.CENTER);

        // Botón aceptar
        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnAceptar = new JButton("Aceptar");
        panelBoton.add(btnAceptar);
        add(panelBoton, BorderLayout.SOUTH);

        // Eventos
        btnAgregar.addActionListener(e -> {
            String producto = txtProducto.getText().trim();
            String cantidad = txtCantidad.getText().trim();
            if (!producto.isEmpty() && cantidad.matches("\\d+")) {
                ((DefaultTableModel) tabla.getModel()).addRow(new Object[]{cantidad, producto});
                txtProducto.setText("");
                txtCantidad.setText("");
            }
        });

        btnAceptar.addActionListener(e -> guardarComanda());

        setVisible(true);
    }

    private void guardarComanda() {
        String estancia = (String) cmbEstancia.getSelectedItem();
        String mesa = txtNumMesa.getText().trim();
        String cliente = txtCliente.getText().trim();

        if (mesa.isEmpty() || cliente.isEmpty() || tabla.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Complete todos los campos y agregue productos");
            return;
        }

        // Cargar archivo
        Object datos = ArchivoBinario.cargar(RUTA_COMANDAS);
        ArrayList<Object[]> listaComandas;
        if (datos != null && datos instanceof ArrayList) {
            listaComandas = (ArrayList<Object[]>) datos;
        } else {
            listaComandas = new ArrayList<>();
        }

        // Guardar pedidos
        ArrayList<String[]> pedidos = new ArrayList<>();
        for (int i = 0; i < tabla.getRowCount(); i++) {
            String cant = tabla.getValueAt(i, 0).toString();
            String prod = tabla.getValueAt(i, 1).toString();
            pedidos.add(new String[]{cant, prod});
        }

        // Estructura: estancia, mesa, cliente, pedidos
        listaComandas.add(new Object[]{estancia, mesa, cliente, pedidos});

        ArchivoBinario.guardar(listaComandas, RUTA_COMANDAS);

        JOptionPane.showMessageDialog(this, "Comanda guardada con éxito");
        dispose();
    }
}
