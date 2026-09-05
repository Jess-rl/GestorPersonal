package gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import logica.GestorAgenda;

public class VentanaPrincipal extends JFrame {

    private GestorAgenda gestor;
    private JPanel panelContenido;
    private CardLayout cardLayout;

    public VentanaPrincipal() {
        setTitle("Gestor de Agenda");
        setSize(850, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        gestor = new GestorAgenda();

        // 1. Contenedor central CardLayout
        cardLayout = new CardLayout();
        panelContenido = new JPanel(cardLayout);
        add(panelContenido, BorderLayout.CENTER);

        // Se instancia primero PanelInicio para entregarlo a PanelNotas
        PanelInicio panelInicio = new PanelInicio(gestor);
        PanelContacto panelContacto = new PanelContacto(gestor);
        PanelNotas panelNotas = new PanelNotas(gestor, panelInicio);

        panelContenido.add(panelInicio, "inicio");
        panelContenido.add(panelContacto, "contactos");
        panelContenido.add(panelNotas, "notas");

        // 2. Barra superior de navegación
        JPanel panelMenu = new JPanel(new GridLayout(1, 3, 10, 0));
        panelMenu.setPreferredSize(new Dimension(0, 45));
        panelMenu.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));

        JButton btnInicio = new JButton("Inicio");
        JButton btnContactos = new JButton("Contactos");
        JButton btnNotas = new JButton("Notas");

        btnInicio.addActionListener(e -> {
            panelInicio.refrescarTarjetasNotas();
            cardLayout.show(panelContenido, "inicio");
        });
        btnContactos.addActionListener(e -> {
            panelContacto.cargarContactos();
            cardLayout.show(panelContenido, "contactos");
        });
        btnNotas.addActionListener(e -> {
            panelNotas.cargarNotas();
            cardLayout.show(panelContenido, "notas");
        });

        panelMenu.add(btnInicio);
        panelMenu.add(btnContactos);
        panelMenu.add(btnNotas);

        add(panelMenu, BorderLayout.NORTH);
    }
}