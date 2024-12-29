import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MainFrame {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Contoh Konkurensi di Swing");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 200);
            frame.setLayout(new BorderLayout());
            
            JLabel statusLabel = new JLabel("Tekan tombol untuk mulai tugas berat", JLabel.CENTER);

            JButton startButton = new JButton("Mulai");

            JProgressBar progressBar = new JProgressBar(0,60);
            progressBar.setStringPainted(true);
            progressBar.setValue(0);

            frame.add(progressBar, BorderLayout.NORTH);
            frame.add(statusLabel, BorderLayout.CENTER);
            frame.add(startButton, BorderLayout.SOUTH);

            startButton.addActionListener(e -> {
                startButton.setEnabled(false);
                statusLabel.setText("Tugas berat sedang berjalan");

                SwingWorker<Void, Integer> worker = new SwingWorker<>() {
                    @Override
                    protected Void doInBackground() throws Exception {
                        for (int i = 0; i <= 100; i++) {
                            Thread.sleep(50);
                            publish(i);
                        }
                        return null;
                    }
                };
                worker.execute();
            });
            frame.setVisible(true);
            frame.setLocationRelativeTo(null);
        });
    }
}
