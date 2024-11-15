package view;

import dao.ScheduleDAO;
import model.Schedule;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class MainView extends JFrame {
    private final JTextField eventNameField;
    private final JTextField eventTypeField;
    private final JTextField locationField;
    private final JSlider durationSlider;
    private final JSpinner participantCountSpinner;
    private final JTable scheduleTable;
    private final DefaultTableModel tableModel;
    private final ScheduleDAO scheduleDAO;

    public MainView() {
        setTitle("Aplikasi Jadwal Acara");
        setLayout(new BorderLayout(10, 10));
        scheduleDAO = new ScheduleDAO();

        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem exitMenuItem = new JMenuItem("Exit");

        exitMenuItem.addActionListener(_ -> System.exit(0));
        fileMenu.add(exitMenuItem);
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);

        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createTitledBorder("Form Jadwal"));

        formPanel.add(new JLabel("Nama Acara:"));
        eventNameField = new JTextField();
        formPanel.add(eventNameField);

        formPanel.add(new JLabel("Jenis Acara:"));
        eventTypeField = new JTextField();
        formPanel.add(eventTypeField);

        formPanel.add(new JLabel("Lokasi:"));
        locationField = new JTextField();
        formPanel.add(locationField);

        formPanel.add(new JLabel("Durasi (Jam):"));
        durationSlider = new JSlider(1, 10, 1);
        durationSlider.setMajorTickSpacing(1);
        durationSlider.setPaintTicks(true);
        durationSlider.setPaintLabels(true);
        formPanel.add(durationSlider);

        formPanel.add(new JLabel("Jumlah Peserta:"));
        participantCountSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
        formPanel.add(participantCountSpinner);

        String[] columnNames = {"Nama Acara", "Jenis Acara", "Lokasi", "Durasi", "Jumlah Peserta"};
        tableModel = new DefaultTableModel(columnNames, 0);
        scheduleTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(scheduleTable);
        tableScrollPane.setBorder(BorderFactory.createTitledBorder("Data Jadwal"));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        JButton tambahButton = new JButton("Tambah Jadwal");
        tambahButton.addActionListener(_ -> tambahJadwalKeTabel());
        JButton rubahButton = new JButton("Rubah Jadwal");
        rubahButton.addActionListener(_ -> rubahJadwal());
        JButton hapusButton = new JButton("Hapus Jadwal");
        hapusButton.addActionListener(_ -> hapusJadwal());

        buttonPanel.add(tambahButton);
        buttonPanel.add(rubahButton);
        buttonPanel.add(hapusButton);

        add(formPanel, BorderLayout.NORTH);
        add(tableScrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setSize(700, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void tambahJadwalKeTabel() {
        String eventName = eventNameField.getText();
        String eventType = eventTypeField.getText();
        String location = locationField.getText();
        String duration = durationSlider.getValue() + " jam";
        int participantCount = (int) participantCountSpinner.getValue();

        Object[] row = {eventName, eventType, location, duration, participantCount};
        tableModel.addRow(row);

        Schedule schedule = new Schedule(eventName, eventType, location, duration, participantCount);
        scheduleDAO.saveSchedule(schedule);

        clearForm();
    }

    private void clearForm() {
        eventNameField.setText("");
        eventTypeField.setText("");
        locationField.setText("");
        durationSlider.setValue(1);
        participantCountSpinner.setValue(1);
    }

    private void rubahJadwal() {
        int selectedRow = scheduleTable.getSelectedRow();
        if (selectedRow >= 0) {
            eventNameField.setText(tableModel.getValueAt(selectedRow, 0).toString());
            eventTypeField.setText(tableModel.getValueAt(selectedRow, 1).toString());
            locationField.setText(tableModel.getValueAt(selectedRow, 2).toString());
            durationSlider.setValue(Integer.parseInt(tableModel.getValueAt(selectedRow, 3).toString().split(" ")[0]));
            participantCountSpinner.setValue(Integer.valueOf(tableModel.getValueAt(selectedRow, 4).toString()));

            tableModel.removeRow(selectedRow);
        } else {
            JOptionPane.showMessageDialog(this, "Pilih data jadwal yang ingin dirubah", "Peringatan", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void hapusJadwal() {
        int selectedRow = scheduleTable.getSelectedRow();
        if (selectedRow >= 0) {
            tableModel.removeRow(selectedRow);
        } else {
            JOptionPane.showMessageDialog(this, "Pilih data jadwal yang ingin dihapus", "Peringatan", JOptionPane.WARNING_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainView::new);
    }
}
