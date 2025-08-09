package vista;

import javax.swing.*;

public class MenuPrincipal extends JFrame {

    public MenuPrincipal() {
        setTitle("La Casa del Pirata");
        setSize(500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JMenuBar barra = new JMenuBar();

        JMenu menu1 = new JMenu("Clientes");
        JMenuItem item1 = new JMenuItem("Registrar Cliente");
        item1.addActionListener(e -> new RegistroClientes());
        menu1.add(item1);

        JMenu menu2 = new JMenu("Comandas");
        JMenuItem item2 = new JMenuItem("Registrar Comanda");
        item2.addActionListener(e -> new RegistroComanda());
        menu2.add(item2);

        JMenu menu3 = new JMenu("Facturación");
        JMenuItem item3 = new JMenuItem("Facturar");
        item3.addActionListener(e -> new Facturacion());
        menu3.add(item3);

        barra.add(menu1);
        barra.add(menu2);
        barra.add(menu3);
        setJMenuBar(barra);

        setVisible(true);
    }
}
