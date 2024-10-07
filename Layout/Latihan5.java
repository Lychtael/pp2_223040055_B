package Layout;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Latihan5 extends JFrame implements ActionListener {

    private JTextField kotakNama;
    private JTextField kotakNoHp;
    private JButton tombolSimpan;
    private JRadioButton radioLaki;
    private JRadioButton radioPerempuan;
    private JCheckBox wargaNegaraAsing;
    private ButtonGroup genderGroup;

    public Latihan5() {
        this.setTitle("Form Biodata");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(400, 300);
        this.setLayout(new GridBagLayout());  
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);  

        JLabel labelNama = new JLabel("Nama:");
        kotakNama = new JTextField(15);  

        gbc.gridx = 0; 
        gbc.gridy = 0; 
        gbc.anchor = GridBagConstraints.WEST;
        add(labelNama, gbc);

        gbc.gridx = 1; 
        add(kotakNama, gbc);

        JLabel labelNoHp = new JLabel("Nomor HP:");
        kotakNoHp = new JTextField(15);

        gbc.gridx = 0; 
        gbc.gridy = 1; 
        add(labelNoHp, gbc);

        gbc.gridx = 1; 
        add(kotakNoHp, gbc);

        JLabel labelGender = new JLabel("Jenis Kelamin:");
        radioLaki = new JRadioButton("Laki-Laki");
        radioPerempuan = new JRadioButton("Perempuan");
        genderGroup = new ButtonGroup();
        genderGroup.add(radioLaki);
        genderGroup.add(radioPerempuan);

        gbc.gridx = 0; 
        gbc.gridy = 2; 
        add(labelGender, gbc);

        gbc.gridx = 1; 
        add(radioLaki, gbc);

        gbc.gridx = 1; 
        gbc.gridy = 3; 
        add(radioPerempuan, gbc);

        wargaNegaraAsing = new JCheckBox("Warga Negara Asing");

        gbc.gridx = 1; 
        gbc.gridy = 4; 
        add(wargaNegaraAsing, gbc);

        tombolSimpan = new JButton("Simpan");
        tombolSimpan.addActionListener(this);

        gbc.gridx = 1; 
        gbc.gridy = 5; 
        gbc.anchor = GridBagConstraints.CENTER;
        add(tombolSimpan, gbc);

        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == tombolSimpan) {
            String nama = kotakNama.getText();
            String noHp = kotakNoHp.getText();
            String jenisKelamin = radioLaki.isSelected() ? "Laki-Laki" : (radioPerempuan.isSelected() ? "Perempuan" : "Tidak Dipilih");
            String wnaStatus = wargaNegaraAsing.isSelected() ? "Ya" : "Tidak";

            JOptionPane.showMessageDialog(this, "Nama: " + nama +
                    "\nNomor HP: " + noHp +
                    "\nJenis Kelamin: " + jenisKelamin +
                    "\nWarga Negara Asing: " + wnaStatus);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Latihan5::new);
    }
}
