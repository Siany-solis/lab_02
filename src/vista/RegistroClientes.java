package vista;

import utilidades.ArchivoBinario;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.ArrayList;

public class RegistroClientes extends JFrame {
    private JTextField txtNombre, txtNumPersonas, txtNumMesa;
    private JRadioButton rbSi, rbNo;
    private JComboBox<String> cmbMesero, cmbEstancia;
    private JButton btnAceptar, btnCancelar;

    private final String RUTA_CLIENTES = "clientes.dat";

    public RegistroClientes() {
        setTitle("Registro Cliente");
        setSize(500, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel Datos Cliente
        JPanel panelDatos = new JPanel(new GridBagLayout());
        panelDatos.setBorder(new TitledBorder("Datos del Cliente"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0; gbc.gridy = 0;
        panelDatos.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1;
        txtNombre = new JTextField(15);
        panelDatos.add(txtNombre, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        panelDatos.add(new JLabel("Cliente habitual:"), gbc);
        rbSi = new JRadioButton("Sí");
        rbNo = new JRadioButton("No");
        ButtonGroup grupo = new ButtonGroup();
        grupo.add(rbSi);
        grupo.add(rbNo);
        JPanel panelRadio = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        panelRadio.add(rbSi);
        panelRadio.add(rbNo);
        gbc.gridx = 1;
        panelDatos.add(panelRadio, gbc);

        gbc.gridx = 2; gbc.gridy = 0;
        panelDatos.add(new JLabel("# Personas:"), gbc);
        gbc.gridx = 3;
        txtNumPersonas = new JTextField(5);
        panelDatos.add(txtNumPersonas, gbc);

        add(panelDatos, BorderLayout.NORTH);

        // Panel Mesero/Estancia
        JPanel panelMedio = new JPanel(new GridLayout(1, 4, 10, 10));
        cmbMesero = new JComboBox<>(new String[]{"Morticia", "Merlina", "Tío Cosa"});
        cmbEstancia = new JComboBox<>(new String[]{"Las Brisas", "Caribe", "El Tesero"});
        panelMedio.add(new JLabel("Mesero", SwingConstants.CENTER));
        panelMedio.add(cmbMesero);
        panelMedio.add(new JLabel("Estancia", SwingConstants.CENTER));
        panelMedio.add(cmbEstancia);
        add(panelMedio, BorderLayout.CENTER);

        // Panel Mesa y botones
        JPanel panelMesa = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelMesa.add(new JLabel("# Mesa:"));
        txtNumMesa = new JTextField(5);
        panelMesa.add(txtNumMesa);

        btnAceptar = new JButton("Aceptar");
        btnCancelar = new JButton("Cancelar");
        panelMesa.add(btnAceptar);
        panelMesa.add(btnCancelar);
        add(panelMesa, BorderLayout.SOUTH);

        btnCancelar.addActionListener(e -> dispose());

        // Aquí conectamos el botón al método
        btnAceptar.addActionListener(e -> guardarCliente());

        setVisible(true);
    }

    private void guardarCliente() {
        String nombre = txtNombre.getText().trim();
        String habitual = rbSi.isSelected() ? "Sí" : rbNo.isSelected() ? "No" : "";
        String numPersonas = txtNumPersonas.getText().trim();
        String mesero = (String) cmbMesero.getSelectedItem();
        String estancia = (String) cmbEstancia.getSelectedItem();
        String numMesa = txtNumMesa.getText().trim();

        if (nombre.isEmpty() || habitual.isEmpty() || numPersonas.isEmpty() || numMesa.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Complete todos los campos");
            return;
        }

        // Cargar clientes existentes de forma segura
        Object datos = ArchivoBinario.cargar(RUTA_CLIENTES);
        ArrayList<String[]> listaClientes;

        if (datos != null && datos instanceof ArrayList) {
            listaClientes = (ArrayList<String[]>) datos;
        } else {
            listaClientes = new ArrayList<>();
        }

        // Agregar nuevo cliente
        listaClientes.add(new String[]{nombre, habitual, numPersonas, mesero, estancia, numMesa});

        // Guardar en archivo
        ArchivoBinario.guardar(listaClientes, RUTA_CLIENTES);

        JOptionPane.showMessageDialog(this, "Cliente guardado con éxito");
        dispose();
    }
}
