package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import logica.GestorAgenda;
import modelo.Contacto;

public class PanelContacto extends JPanel {

    private GestorAgenda gestor;
    private JTextField txtNombre;
    private JTextField txtTelefono;
    private JTextField txtCorreo;
    private DefaultTableModel modeloTabla;
    private JTable tablaContactos;
    private JButton btnGuardar;
    private JButton btnModificar;
    private JButton btnEliminar;

    public PanelContacto(GestorAgenda gestor) {
        this.gestor = gestor;
        setLayout(new BorderLayout(15, 15));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel panelGrid = new JPanel(new GridLayout(3, 2, 8, 10));

        panelGrid.add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        panelGrid.add(txtNombre);

        panelGrid.add(new JLabel("Teléfono:"));
        txtTelefono = new JTextField();
        ((AbstractDocument) txtTelefono.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                if (string == null) return;
                String current = fb.getDocument().getText(0, fb.getDocument().getLength());
                int available = 10 - current.length();
                if (available > 0) {
                    String toInsert = string.length() > available ? string.substring(0, available) : string;
                    if (toInsert.matches("\\d+")) {
                        super.insertString(fb, offset, toInsert, attr);
                    }
                }
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                if (text == null) return;
                if (text.isEmpty()) {
                    super.replace(fb, offset, length, text, attrs);
                    return;
                }
                String current = fb.getDocument().getText(0, fb.getDocument().getLength());
                int available = 10 - (current.length() - length);
                if (available > 0) {
                    String toReplace = text.length() > available ? text.substring(0, available) : text;
                    if (toReplace.matches("\\d+")) {
                        super.replace(fb, offset, length, toReplace, attrs);
                    }
                }
            }
        });
        panelGrid.add(txtTelefono);

        panelGrid.add(new JLabel("Correo:"));
        txtCorreo = new JTextField();
        panelGrid.add(txtCorreo);

        JPanel panelBotones = new JPanel(new GridLayout(2, 1, 0, 8));

        btnGuardar = new JButton("Guardar Contacto");
        btnGuardar.setToolTipText("Guardar nuevo contacto");
        btnGuardar.addActionListener(e -> guardarContacto());

        JPanel panelAcciones = new JPanel(new GridLayout(1, 2, 8, 0));
        btnModificar = new JButton("Modificar");
        btnModificar.setEnabled(false);
        btnModificar.setToolTipText("Guardar cambios en el contacto seleccionado");
        btnModificar.addActionListener(e -> modificarContacto());

        btnEliminar = new JButton("Eliminar");
        btnEliminar.setEnabled(false);
        btnEliminar.setToolTipText("Eliminar el contacto seleccionado");
        btnEliminar.addActionListener(e -> eliminarContacto());

        panelAcciones.add(btnModificar);
        panelAcciones.add(btnEliminar);

        panelBotones.add(btnGuardar);
        panelBotones.add(panelAcciones);

        JPanel panelFormulario = new JPanel(new BorderLayout(0, 12));
        panelFormulario.add(panelGrid, BorderLayout.NORTH);
        panelFormulario.add(panelBotones, BorderLayout.SOUTH);

        JPanel panelIzquierdo = new JPanel(new BorderLayout());
        panelIzquierdo.setPreferredSize(new Dimension(290, 0));
        panelIzquierdo.add(panelFormulario, BorderLayout.NORTH);

        modeloTabla = new DefaultTableModel(new Object[]{"Nombre", "Teléfono", "Correo"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaContactos = new JTable(modeloTabla);
        tablaContactos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        tablaContactos.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int filaVista = tablaContactos.getSelectedRow();
                if (filaVista != -1) {
                    int fila = tablaContactos.convertRowIndexToModel(filaVista);
                    if (fila >= 0 && fila < gestor.getContactos().size()) {
                        Contacto c = gestor.getContactos().get(fila);
                        txtNombre.setText(c.getNombre());
                        txtTelefono.setText(c.getTelefono());
                        txtCorreo.setText(c.getCorreo());
                        btnModificar.setEnabled(true);
                        btnEliminar.setEnabled(true);
                        return;
                    }
                }
                btnModificar.setEnabled(false);
                btnEliminar.setEnabled(false);
            }
        });

        add(panelIzquierdo, BorderLayout.WEST);
        add(new JScrollPane(tablaContactos), BorderLayout.CENTER);

        cargarContactos();
    }

    private void guardarContacto() {
        String nombre = txtNombre.getText().trim();
        String tel = txtTelefono.getText().trim();
        String correo = txtCorreo.getText().trim();

        if (nombre.isEmpty() || tel.isEmpty() || correo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!nombre.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$")) {
            JOptionPane.showMessageDialog(this, "El nombre solo debe contener letras y espacios.", "Error en Nombre", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!tel.matches("^\\d{10}$")) {
            JOptionPane.showMessageDialog(this, "El teléfono debe contener exactamente 10 dígitos numéricos.", "Error en Teléfono", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!correo.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            JOptionPane.showMessageDialog(this, "Ingresa un formato de correo válido (ej. usuario@mail.com).", "Error en Correo", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Contacto nuevoContacto = new Contacto(nombre, tel, correo);
        gestor.agregarContacto(nuevoContacto);
        modeloTabla.addRow(new Object[]{nombre, tel, correo});

        limpiarFormulario();
        JOptionPane.showMessageDialog(this, "Contacto agregado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }

    private void modificarContacto() {
        int filaVista = tablaContactos.getSelectedRow();
        if (filaVista == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un contacto de la tabla para modificar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int fila = tablaContactos.convertRowIndexToModel(filaVista);

        String nombre = txtNombre.getText().trim();
        String tel = txtTelefono.getText().trim();
        String correo = txtCorreo.getText().trim();

        if (nombre.isEmpty() || tel.isEmpty() || correo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!nombre.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$")) {
            JOptionPane.showMessageDialog(this, "El nombre solo debe contener letras y espacios.", "Error en Nombre", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!tel.matches("^\\d{10}$")) {
            JOptionPane.showMessageDialog(this, "El teléfono debe contener exactamente 10 dígitos numéricos.", "Error en Teléfono", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!correo.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            JOptionPane.showMessageDialog(this, "Ingresa un formato de correo válido (ej. usuario@mail.com).", "Error en Correo", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Contacto contactoModificado = new Contacto(nombre, tel, correo);
        gestor.modificarContacto(fila, contactoModificado);

        modeloTabla.setValueAt(nombre, fila, 0);
        modeloTabla.setValueAt(tel, fila, 1);
        modeloTabla.setValueAt(correo, fila, 2);

        limpiarFormulario();
        JOptionPane.showMessageDialog(this, "Contacto modificado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }

    private void eliminarContacto() {
        int filaVista = tablaContactos.getSelectedRow();
        if (filaVista == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un contacto de la tabla para eliminar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int fila = tablaContactos.convertRowIndexToModel(filaVista);

        int confirmacion = JOptionPane.showConfirmDialog(
            this,
            "¿Estás seguro de que deseas eliminar este contacto?",
            "Confirmar eliminación",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );

        if (confirmacion == JOptionPane.YES_OPTION) {
            gestor.eliminarContacto(fila);
            modeloTabla.removeRow(fila);
            limpiarFormulario();
            JOptionPane.showMessageDialog(this, "Contacto eliminado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void cargarContactos() {
        modeloTabla.setRowCount(0);
        for (Contacto c : gestor.getContactos()) {
            modeloTabla.addRow(new Object[]{c.getNombre(), c.getTelefono(), c.getCorreo()});
        }
        limpiarFormulario();
    }

    private void limpiarFormulario() {
        tablaContactos.clearSelection();
        txtNombre.setText("");
        txtTelefono.setText("");
        txtCorreo.setText("");
        btnModificar.setEnabled(false);
        btnEliminar.setEnabled(false);
    }
}