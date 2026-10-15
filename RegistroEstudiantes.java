import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class RegistroEstudiantes extends JFrame {

    private JTextField txtCodigo, txtNombre, txtNota1, txtNota2, txtNota3;
    private JButton btnCalcular;
    private JTable tablaEstudiantes;
    private DefaultTableModel modeloTabla;

    public RegistroEstudiantes() {
        setTitle("registro de los estudiantes y sus notas");
        setSize(700, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        setLocationRelativeTo(null);

        // acá defino el panel para la parte de arriba que tiene los campos de texto y el boton para registrar y calcular
        JPanel panelNorte = new JPanel(new GridLayout(2, 1, 5, 5));

        // aqui agrego los campos de texto
        JPanel panelCampos = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        panelCampos.add(new JLabel("Código:"));
        txtCodigo = new JTextField(7);
        panelCampos.add(txtCodigo);

        panelCampos.add(new JLabel("Nombre:"));
        txtNombre = new JTextField(10);
        panelCampos.add(txtNombre);

        panelCampos.add(new JLabel("Nota 1:"));
        txtNota1 = new JTextField(3);
        panelCampos.add(txtNota1);

        panelCampos.add(new JLabel("Nota 2:"));
        txtNota2 = new JTextField(3);
        panelCampos.add(txtNota2);

        panelCampos.add(new JLabel("Nota 3:"));
        txtNota3 = new JTextField(3);
        panelCampos.add(txtNota3);

        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnCalcular = new JButton("Calcular y registrar");
        panelBoton.add(btnCalcular);

        panelNorte.add(panelCampos);
        panelNorte.add(panelBoton);

        add(panelNorte, BorderLayout.NORTH);

        modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn("Código");
        modeloTabla.addColumn("Nombre");
        modeloTabla.addColumn("Nota 1");
        modeloTabla.addColumn("Nota 2");
        modeloTabla.addColumn("Nota 3");
        modeloTabla.addColumn("Definitiva");
        modeloTabla.addColumn("Estado");

        tablaEstudiantes = new JTable(modeloTabla);
        JScrollPane scrollTabla = new JScrollPane(tablaEstudiantes);
        add(scrollTabla, BorderLayout.CENTER);

        btnCalcular.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registrarEstudiante();
            }
        });
    }

    private void registrarEstudiante() {
        try {
            String codigo = txtCodigo.getText();
            String nombre = txtNombre.getText();

            if (codigo.trim().isEmpty() || nombre.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "ponga el nombre y el código, hay algún campo que esta poniendo vacío");
                return;
            }

            // aqui miro que la cedula que se registre tenga entre 6 y 10 digitos y que solo hayan numeros
            if (!codigo.matches("^[0-9]{6,10}$")) {
                JOptionPane.showMessageDialog(this, "El código tiene que ser un número de cédula válido");
                return;
            }

            // aquí verifico que el campo de nombre solo tenga letras y espacios.
            if (!nombre.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$")) {
                JOptionPane.showMessageDialog(this, "El nombre solo puede tener letras y espacios.");
                return;
            }

            double n1 = Double.parseDouble(txtNota1.getText());
            double n2 = Double.parseDouble(txtNota2.getText());
            double n3 = Double.parseDouble(txtNota3.getText());

            // aquí verifico que las notas sean validas osea que esten en entre 0 y 5
            if (n1 < 0.0 || n1 > 5.0 || n2 < 0.0 || n2 > 5.0 || n3 < 0.0 || n3 > 5.0) {
                JOptionPane.showMessageDialog(this, "Sus notas tienen que estar entre 0.0 y 5.0");
                return;
            }

            // acá calculo el promedio y lo redondeo a 2 decimales
            double definitiva = (n1 * 0.30) + (n2 * 0.30) + (n3 * 0.40);
            definitiva = Math.round(definitiva * 100.0) / 100.0;

            String estado;
            if (definitiva >= 3.0) {
                estado = "Pasó";
            } else {
                estado = "Perdió";
            }

            Object[] fila = new Object[7];
            fila[0] = codigo;
            fila[1] = nombre;
            fila[2] = n1;
            fila[3] = n2;
            fila[4] = n3;
            fila[5] = definitiva;
            fila[6] = estado;

            modeloTabla.addRow(fila);

            limpiarCampos();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "ponga valores válidos en las notas");
        }
    }

    private void limpiarCampos() {
        txtCodigo.setText("");
        txtNombre.setText("");
        txtNota1.setText("");
        txtNota2.setText("");
        txtNota3.setText("");
        txtCodigo.requestFocus();
    }

    public static void main(String[] args) {
        RegistroEstudiantes ventana = new RegistroEstudiantes();
        ventana.setVisible(true);
    }
}
