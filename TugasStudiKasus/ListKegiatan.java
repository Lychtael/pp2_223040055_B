package TugasStudiKasus;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ListKegiatan extends JFrame implements ActionListener {
    private JTextField kotakNamaKegiatan;
    private JTextArea kotakDeskripsiKegiatan;
    private JButton tombolAdd, tombolUpdate, tombolDelete;
    private JSpinner spinnerTanggal;
    private JTable itemTable;
    private DefaultTableModel tableModel;
    private ArrayList<String[]> dataList;

    private JRadioButton radioPersonal, radioWork;
    private JCheckBox checkBoxSelesai;
    private JComboBox<String> comboPrioritas;
    private JList<String> ApakahAkanDilakukan;
    private DefaultListModel<String> listModel;
    private JSlider sliderPrioritas;
    private JMenuBar menuBar;
    private JMenu menuFile;

    public ListKegiatan() {
        setTitle("Latihan Belajar");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 600);
        setLayout(new BorderLayout());

        // Menu
        menuBar = new JMenuBar();
        menuFile = new JMenu("File");
        JMenuItem menuExit = new JMenuItem("Exit");
        menuFile.add(menuExit);
        menuBar.add(menuFile);
        setJMenuBar(menuBar);

        // Input fields
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Padding

        JLabel labelNamaKegiatan = new JLabel("Nama Kegiatan:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(labelNamaKegiatan, gbc);

        kotakNamaKegiatan = new JTextField(20);
        gbc.gridx = 1;
        inputPanel.add(kotakNamaKegiatan, gbc);

        JLabel labelDeskripsiKegiatan = new JLabel("Deskripsi Kegiatan:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(labelDeskripsiKegiatan, gbc);

        kotakDeskripsiKegiatan = new JTextArea(3, 20);
        gbc.gridx = 1;
        inputPanel.add(new JScrollPane(kotakDeskripsiKegiatan), gbc);

        JLabel labelPrioritasKegiatan = new JLabel("Skala Prioritas:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        inputPanel.add(labelPrioritasKegiatan, gbc);

        String[] prioritasOptions = {"Rendah", "Sedang", "Tinggi"};
        comboPrioritas = new JComboBox<>(prioritasOptions);
        gbc.gridx = 1;
        inputPanel.add(comboPrioritas, gbc);

        // JRadioButton for kategori
        radioPersonal = new JRadioButton("Personal");
        radioWork = new JRadioButton("Work");
        ButtonGroup group = new ButtonGroup();
        group.add(radioPersonal);
        group.add(radioWork);
        
        gbc.gridx = 0;
        gbc.gridy = 3;
        inputPanel.add(radioPersonal, gbc);
        
        gbc.gridx = 1;
        inputPanel.add(radioWork, gbc);

        // JList Akan Dilakukan
        JLabel AkanDilakukan = new JLabel("Akan Dilakukan?");
        gbc.gridx = 0;
        gbc.gridy = 4;
        inputPanel.add(AkanDilakukan, gbc);
        
        AkanDilakukan.setBounds(15, 280, 100, 30);
        listModel = new DefaultListModel<>();
        listModel.addElement("Tentu Saja");
        listModel.addElement("Malas");
        ApakahAkanDilakukan = new JList<>(listModel);
        ApakahAkanDilakukan.setBounds(150, 306, 200, 50); // Reduced height from 75 to 50
        ApakahAkanDilakukan.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        ApakahAkanDilakukan.setVisibleRowCount(2);
        
        gbc.gridx = 1;
        inputPanel.add(new JScrollPane(ApakahAkanDilakukan), gbc);

        // JCheckBox for completed task
        checkBoxSelesai = new JCheckBox("Kegiatan Selesai");
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        inputPanel.add(checkBoxSelesai, gbc);

        // JSlider for priority level
        sliderPrioritas = new JSlider(1, 10);
        sliderPrioritas.setMajorTickSpacing(1);
        sliderPrioritas.setPaintTicks(true);
        sliderPrioritas.setPaintLabels(true);
        gbc.gridy = 6;
        inputPanel.add(sliderPrioritas, gbc);

        // Date picker
        JLabel labelTanggal = new JLabel("Tanggal:");
        gbc.gridx = 0;
        gbc.gridy = 7;
        inputPanel.add(labelTanggal, gbc);
        
        spinnerTanggal = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(spinnerTanggal, "dd-MM-yyyy");
        spinnerTanggal.setEditor(dateEditor);
        gbc.gridx = 1;
        inputPanel.add(spinnerTanggal, gbc);

        // Table for displaying added items
        String[] columnNames = {"Nama Kegiatan", "Deskripsi", "Prioritas", "Tanggal", "Dilakukan", "Selesai"};
        tableModel = new DefaultTableModel(columnNames, 0);
        itemTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(itemTable);
        
        // Buttons
        JPanel buttonPanel = new JPanel();
        tombolAdd = new JButton("Add");
        tombolAdd.addActionListener(this);
        tombolUpdate = new JButton("Update");
        tombolUpdate.addActionListener(this);
        tombolDelete = new JButton("Delete");
        tombolDelete.addActionListener(this);
        
        buttonPanel.add(tombolAdd);
        buttonPanel.add(tombolUpdate);
        buttonPanel.add(tombolDelete);

        // Data List
        dataList = new ArrayList<>();

        // Add components to frame
        add(inputPanel, BorderLayout.NORTH);
        add(tableScrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == tombolAdd) {
            addDataToTable();
        } else if (e.getSource() == tombolUpdate) {
            updateDataInTable();
        } else if (e.getSource() == tombolDelete) {
            deleteDataFromTable();
        }
    }

    private void addDataToTable() {
        String nama = kotakNamaKegiatan.getText();
        String deskripsi = kotakDeskripsiKegiatan.getText();
        String prioritas = (String) comboPrioritas.getSelectedItem();
        String tanggal = new SimpleDateFormat("dd-MM-yyyy").format((Date) spinnerTanggal.getValue());
        String selesai = checkBoxSelesai.isSelected() ? "Ya" : "Tidak";
        String dataDilakukan = ApakahAkanDilakukan.getSelectedValue() != null ? ApakahAkanDilakukan.getSelectedValue() : "Tidak akan dilakukan?";

        String[] data = {nama, deskripsi, prioritas, tanggal, dataDilakukan, selesai};
        dataList.add(data);
        tableModel.addRow(data);
    }

    private void updateDataInTable() {
        int selectedRow = itemTable.getSelectedRow();
        if (selectedRow != -1) {
            String nama = kotakNamaKegiatan.getText();
            String deskripsi = kotakDeskripsiKegiatan.getText();
            String prioritas = (String) comboPrioritas.getSelectedItem();
            String tanggal = new SimpleDateFormat("dd-MM-yyyy").format((Date) spinnerTanggal.getValue());
            String selesai = checkBoxSelesai.isSelected() ? "Ya" : "Tidak";
            String dataDilakukan = ApakahAkanDilakukan.getSelectedValue() != null ? ApakahAkanDilakukan.getSelectedValue() : "Tidak akan dilakukan?";

            String[] updatedData = {nama, deskripsi, prioritas, tanggal, dataDilakukan, selesai};
            dataList.set(selectedRow, updatedData);
            for (int i = 0; i < updatedData.length; i++) {
                tableModel.setValueAt(updatedData[i], selectedRow, i);
            }
        }
    }

    private void deleteDataFromTable() {
        int selectedRow = itemTable.getSelectedRow();
        if (selectedRow != -1) {
            dataList.remove(selectedRow);
            tableModel.removeRow(selectedRow);
        }
    }

    public static void main(String[] args) {
        new ListKegiatan();
    }
}
