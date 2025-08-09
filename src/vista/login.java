package vista;

import javax.swing.*;
import java.awt.*;

public class login extends JFrame {
    private JTextField txtUsuario;
    private JPasswordField txtContra;
    private JButton btnAceptar;
    private JButton btnCancelar;

    public login() {
        setTitle("Autenticación de usuarios");
        setSize(300, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Icono
        JLabel lblIcono = new JLabel("\uD83D\uDC64", SwingConstants.CENTER);
        lblIcono.setFont(new Font("SansSerif", Font.PLAIN, 40));
        add(lblIcono, BorderLayout.NORTH);

        // Panel central
        JPanel panelCampos = new JPanel(new GridLayout(2, 2, 5, 5));
        panelCampos.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelCampos.add(new JLabel("Usuario:"));
        txtUsuario = new JTextField();
        panelCampos.add(txtUsuario);
        panelCampos.add(new JLabel("Contraseña:"));
        txtContra = new JPasswordField();
        panelCampos.add(txtContra);
        add(panelCampos, BorderLayout.CENTER);

        // Panel botones
        JPanel panelBotones = new JPanel(new FlowLayout());
        btnAceptar = new JButton("Aceptar");
        btnCancelar = new JButton("Cancelar");
        panelBotones.add(btnAceptar);
        panelBotones.add(btnCancelar);
        add(panelBotones, BorderLayout.SOUTH);

        // Eventos
        btnAceptar.addActionListener(e -> autenticar());
        btnCancelar.addActionListener(e -> System.exit(0));

        setVisible(true);
    }

    private void autenticar() {
        String usuario = txtUsuario.getText();
        String contra = new String(txtContra.getPassword());

        if (usuario.equals("admin") && contra.equals("secreta")) {
            dispose();
            new MenuPrincipal();
        } else {
            JOptionPane.showMessageDialog(this, "Credenciales incorrectas");
        }
    }
}
