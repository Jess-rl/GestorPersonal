package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import logica.GestorAgenda;
import modelo.Nota;

public class PanelNotas extends JPanel {

    private GestorAgenda gestor;
    private PanelInicio panelInicio;
    private JTextField txtTitulo;
    private JTextArea txtContenido;
    private DefaultTableModel modeloTabla;
    private JTable tablaNotas;
    private JButton btnGuardar;
    private JButton btnModificar;
    private JButton btnEliminar;

    public PanelNotas(GestorAgenda gestor) {
        this(gestor, null);
    }

    public PanelNotas(GestorAgenda gestor, PanelInicio panelInicio) {
        this.gestor = gestor;
        this.panelInicio = panelInicio;
        setLayout(new BorderLayout(15, 15));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel panelFormulario = new JPanel();
        panelFormulario.setLayout(new BoxLayout(panelFormulario, BoxLayout.Y_AXIS));
        panelFormulario.setPreferredSize(new Dimension(280, 0));

        JLabel lblTitulo = new JLabel("Título de la Nota:");
        lblTitulo.setAlignmentX(LEFT_ALIGNMENT);
        txtTitulo = new JTextField();
        txtTitulo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        txtTitulo.setAlignmentX(LEFT_ALIGNMENT);

        JLabel lblContenido = new JLabel("Contenido:");
        lblContenido.setAlignmentX(LEFT_ALIGNMENT);
        txtContenido = new JTextArea();
        txtContenido.setLineWrap(true);
        txtContenido.setWrapStyleWord(true);
        JScrollPane scrollContenido = new JScrollPane(txtContenido);
        scrollContenido.setPreferredSize(new Dimension(280, 200));
        scrollContenido.setMaximumSize(new Dimension(Integer.MAX_VALUE, 220));
        scrollContenido.setAlignmentX(LEFT_ALIGNMENT);

        btnGuardar = new JButton("Guardar Nota");
        btnGuardar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 32));
        btnGuardar.setAlignmentX(LEFT_ALIGNMENT);
        btnGuardar.setToolTipText("Crear y guardar una nueva nota");
        btnGuardar.addActionListener(e -> guardarNota());

        JPanel panelAcciones = new JPanel(new GridLayout(1, 2, 8, 0));
        panelAcciones.setMaximumSize(new Dimension(Integer.MAX_VALUE, 32));
        panelAcciones.setAlignmentX(LEFT_ALIGNMENT);

        btnModificar = new JButton("Modificar");
        btnModificar.setEnabled(false);
        btnModificar.setToolTipText("Guardar cambios en la nota seleccionada");
        btnModificar.addActionListener(e -> modificarNota());

        btnEliminar = new JButton("Eliminar");
        btnEliminar.setEnabled(false);
        btnEliminar.setToolTipText("Eliminar la nota seleccionada");
        btnEliminar.addActionListener(e -> eliminarNota());

        panelAcciones.add(btnModificar);
        panelAcciones.add(btnEliminar);

        panelFormulario.add(lblTitulo);
        panelFormulario.add(Box.createRigidArea(new Dimension(0, 5)));
        panelFormulario.add(txtTitulo);
        panelFormulario.add(Box.createRigidArea(new Dimension(0, 10)));
        panelFormulario.add(lblContenido);
        panelFormulario.add(Box.createRigidArea(new Dimension(0, 5)));
        panelFormulario.add(scrollContenido);
        panelFormulario.add(Box.createRigidArea(new Dimension(0, 12)));
        panelFormulario.add(btnGuardar);
        panelFormulario.add(Box.createRigidArea(new Dimension(0, 8)));
        panelFormulario.add(panelAcciones);
        panelFormulario.add(Box.createVerticalGlue());

        modeloTabla = new DefaultTableModel(new Object[]{"Título", "Contenido", "Fecha"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaNotas = new JTable(modeloTabla);
        tablaNotas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        tablaNotas.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int filaVista = tablaNotas.getSelectedRow();
                if (filaVista != -1) {
                    int fila = tablaNotas.convertRowIndexToModel(filaVista);
                    if (fila >= 0 && fila < gestor.getNotas().size()) {
                        Nota n = gestor.getNotas().get(fila);
                        txtTitulo.setText(n.getTitulo());
                        txtContenido.setText(n.getContenido());
                        btnModificar.setEnabled(true);
                        btnEliminar.setEnabled(true);
                        return;
                    }
                }
                btnModificar.setEnabled(false);
                btnEliminar.setEnabled(false);
            }
        });

        add(panelFormulario, BorderLayout.WEST);
        add(new JScrollPane(tablaNotas), BorderLayout.CENTER);

        cargarNotas();
    }

    public void setPanelInicio(PanelInicio panelInicio) {
        this.panelInicio = panelInicio;
    }

    private void guardarNota() {
        String titulo = txtTitulo.getText().trim();
        String contenido = txtContenido.getText().trim();

        if (titulo.isEmpty() || contenido.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Completa todos los campos.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (titulo.length() < 3) {
            JOptionPane.showMessageDialog(this, "El título debe tener al menos 3 caracteres.", "Error en Título", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (titulo.length() > 60) {
            JOptionPane.showMessageDialog(this, "El título es demasiado largo (máximo 60 caracteres).", "Error en Título", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (contenido.length() > 1000) {
            JOptionPane.showMessageDialog(this, "El contenido excede el límite permitido (máximo 1000 caracteres).", "Error en Contenido", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String fechaActual = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE);
        Nota nuevaNota = new Nota(titulo, contenido, fechaActual);

        gestor.agregarNota(nuevaNota);
        modeloTabla.addRow(new Object[]{titulo, contenido, fechaActual});

        if (panelInicio != null) {
            panelInicio.refrescarTarjetasNotas();
        }

        limpiarFormulario();
        JOptionPane.showMessageDialog(this, "Nota agregada con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }

    private void modificarNota() {
        int filaVista = tablaNotas.getSelectedRow();
        if (filaVista == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona una nota de la tabla para modificar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int fila = tablaNotas.convertRowIndexToModel(filaVista);

        String titulo = txtTitulo.getText().trim();
        String contenido = txtContenido.getText().trim();

        if (titulo.isEmpty() || contenido.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Completa todos los campos.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (titulo.length() < 3) {
            JOptionPane.showMessageDialog(this, "El título debe tener al menos 3 caracteres.", "Error en Título", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (titulo.length() > 60) {
            JOptionPane.showMessageDialog(this, "El título es demasiado largo (máximo 60 caracteres).", "Error en Título", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (contenido.length() > 1000) {
            JOptionPane.showMessageDialog(this, "El contenido excede el límite permitido (máximo 1000 caracteres).", "Error en Contenido", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String fechaActual = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE);
        Nota notaModificada = new Nota(titulo, contenido, fechaActual);

        gestor.modificarNota(fila, notaModificada);

        modeloTabla.setValueAt(titulo, fila, 0);
        modeloTabla.setValueAt(contenido, fila, 1);
        modeloTabla.setValueAt(fechaActual, fila, 2);

        if (panelInicio != null) {
            panelInicio.refrescarTarjetasNotas();
        }

        limpiarFormulario();
        JOptionPane.showMessageDialog(this, "Nota modificada con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }

    private void eliminarNota() {
        int filaVista = tablaNotas.getSelectedRow();
        if (filaVista == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona una nota de la tabla para eliminar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int fila = tablaNotas.convertRowIndexToModel(filaVista);

        int confirmacion = JOptionPane.showConfirmDialog(
            this,
            "¿Estás seguro de que deseas eliminar esta nota?",
            "Confirmar eliminación",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );

        if (confirmacion == JOptionPane.YES_OPTION) {
            gestor.eliminarNota(fila);
            modeloTabla.removeRow(fila);

            if (panelInicio != null) {
                panelInicio.refrescarTarjetasNotas();
            }

            limpiarFormulario();
            JOptionPane.showMessageDialog(this, "Nota eliminada con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void cargarNotas() {
        modeloTabla.setRowCount(0);
        for (Nota n : gestor.getNotas()) {
            modeloTabla.addRow(new Object[]{n.getTitulo(), n.getContenido(), n.getFecha()});
        }
        limpiarFormulario();
    }

    private void limpiarFormulario() {
        tablaNotas.clearSelection();
        txtTitulo.setText("");
        txtContenido.setText("");
        btnModificar.setEnabled(false);
        btnEliminar.setEnabled(false);
    }
}