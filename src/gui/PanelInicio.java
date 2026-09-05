package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import logica.GestorAgenda;
import modelo.Nota;
import modelo.Recordatorio;

public class PanelInicio extends JPanel {

    private GestorAgenda gestor;
    private List<Recordatorio> listaRecordatorios;
    private List<Nota> ultimasNotas;
    private DefaultTableModel modeloTablaRec;
    private JPanel contenedorNotas;

    public PanelInicio(GestorAgenda gestor) {
        this.gestor = gestor;
        this.listaRecordatorios = new ArrayList<>();
        this.ultimasNotas = new ArrayList<>();

        setLayout(new BorderLayout(15, 15));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel lblTitulo = new JLabel("Panel Principal", JLabel.LEFT);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        add(lblTitulo, BorderLayout.NORTH);

        JPanel panelCuerpo = new JPanel(new GridLayout(1, 2, 15, 0));

        // --- SECCIÓN RECORDATORIOS ---
        JPanel panelRec = new JPanel(new BorderLayout(8, 8));
        panelRec.setBorder(BorderFactory.createTitledBorder("Recordatorios"));

        String[] columnasRec = {"Listo", "Asunto", "Fecha", "Hora"};
        modeloTablaRec = new DefaultTableModel(columnasRec, 0) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return columnIndex == 0 ? Boolean.class : String.class;
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 0;
            }
        };

        modeloTablaRec.addTableModelListener(e -> {
            int row = e.getFirstRow();
            int col = e.getColumn();
            if (col == 0 && row >= 0 && row < listaRecordatorios.size()) {
                boolean estado = (Boolean) modeloTablaRec.getValueAt(row, 0);
                listaRecordatorios.get(row).setCompletado(estado);
            }
        });

        JTable tablaRec = new JTable(modeloTablaRec);
        tablaRec.getColumnModel().getColumn(0).setPreferredWidth(45);
        tablaRec.getColumnModel().getColumn(0).setMaxWidth(55);

        JButton btnNuevoRec = new JButton("+ Recordatorio");
        btnNuevoRec.addActionListener(e -> agregarRecordatorioDialogo());

        panelRec.add(new JScrollPane(tablaRec), BorderLayout.CENTER);
        panelRec.add(btnNuevoRec, BorderLayout.SOUTH);

        // --- SECCIÓN ÚLTIMAS NOTAS (3 CUADROS EN CASCADA) ---
        JPanel panelNotasSeccion = new JPanel(new BorderLayout(8, 8));
        panelNotasSeccion.setBorder(BorderFactory.createTitledBorder("Últimas Notas"));

        contenedorNotas = new JPanel(new GridLayout(3, 1, 0, 10));

        panelNotasSeccion.add(contenedorNotas, BorderLayout.CENTER);

        panelCuerpo.add(panelRec);
        panelCuerpo.add(panelNotasSeccion);

        add(panelCuerpo, BorderLayout.CENTER);

        // Carga inicial
        cargarRecordatorio(new Recordatorio("Entregar proyecto", "2026-09-04", "23:59", false));
        refrescarTarjetasNotas();
    }

    private void agregarRecordatorioDialogo() {
        JTextField campoAsunto = new JTextField();
        JTextField campoFecha = new JTextField(LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE));
        JTextField campoHora = new JTextField("12:00");

        Object[] mensaje = {
            "Asunto:", campoAsunto,
            "Fecha (AAAA-MM-DD):", campoFecha,
            "Hora (HH:MM):", campoHora
        };

        int opcion = JOptionPane.showConfirmDialog(this, mensaje, "Nuevo Recordatorio Manual", JOptionPane.OK_CANCEL_OPTION);
        if (opcion == JOptionPane.OK_OPTION) {
            String asunto = campoAsunto.getText().trim();
            String fecha = campoFecha.getText().trim();
            String hora = campoHora.getText().trim();

            if (asunto.isEmpty()) {
                JOptionPane.showMessageDialog(this, "El asunto no puede quedar vacío.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (!fecha.matches("^\\d{4}-\\d{2}-\\d{2}$")) {
                JOptionPane.showMessageDialog(this, "Formato de fecha inválido. Usa AAAA-MM-DD (ej. 2026-09-04).", "Error de Formato", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!hora.matches("^(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$")) {
                JOptionPane.showMessageDialog(this, "Formato de hora inválido. Usa formato 24 horas HH:MM (ej. 14:30).", "Error de Formato", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Recordatorio nuevo = new Recordatorio(asunto, fecha, hora, false);
            cargarRecordatorio(nuevo);
        }
    }

    private void cargarRecordatorio(Recordatorio rec) {
        listaRecordatorios.add(rec);
        modeloTablaRec.addRow(new Object[]{rec.getCompletado(), rec.getAsunto(), rec.getFecha(), rec.getHora()});
    }

    public void refrescarTarjetasNotas() {
        ultimasNotas = gestor.getNotasRecientes();
        contenedorNotas.removeAll();

        // Se generan siempre exactamente 3 cuadros independientes
        for (int i = 0; i < 3; i++) {
            JPanel tarjeta = new JPanel(new BorderLayout(5, 5));
            tarjeta.setBackground(Color.WHITE);
            tarjeta.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(205, 205, 205), 1),
                    BorderFactory.createEmptyBorder(8, 10, 8, 10)
            ));

            if (i < ultimasNotas.size()) {
                Nota n = ultimasNotas.get(i);
                JLabel lblNotaTitulo = new JLabel(n.getTitulo() + "  —  [" + n.getFecha() + "]");
                lblNotaTitulo.setFont(new Font("Arial", Font.BOLD, 12));
                lblNotaTitulo.setForeground(new Color(33, 37, 41));

                JLabel lblNotaCuerpo = new JLabel("<html><body style='width: 250px;'>" + n.getContenido() + "</body></html>");
                lblNotaCuerpo.setFont(new Font("Arial", Font.PLAIN, 12));

                tarjeta.add(lblNotaTitulo, BorderLayout.NORTH);
                tarjeta.add(lblNotaCuerpo, BorderLayout.CENTER);
            } else {
                JLabel lblVacio = new JLabel("Espacio disponible", JLabel.CENTER);
                lblVacio.setFont(new Font("Arial", Font.ITALIC, 12));
                lblVacio.setForeground(Color.GRAY);
                tarjeta.add(lblVacio, BorderLayout.CENTER);
            }

            contenedorNotas.add(tarjeta);
        }

        contenedorNotas.revalidate();
        contenedorNotas.repaint();
    }
}