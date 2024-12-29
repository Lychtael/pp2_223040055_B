import org.apache.hc.client5.http.async.methods.*;
import org.apache.hc.client5.http.impl.async.CloseableHttpAsyncClient;
import org.apache.hc.client5.http.impl.async.HttpAsyncClients;
import org.apache.hc.core5.concurrent.FutureCallback;
import org.apache.hc.core5.http.HttpHost;
import org.apache.hc.core5.http.message.StatusLine;
import org.apache.hc.core5.io.CloseMode;
import org.apache.hc.core5.reactor.IOReactorConfig;
import org.apache.hc.core5.util.Timeout;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class MainFrame {
    public static void main(String[] args) {
        final IOReactorConfig ioReactorConfig = IOReactorConfig.custom().setSoTimeout(Timeout.ofSeconds(5)).build();
        final CloseableHttpAsyncClient client = HttpAsyncClients.custom().setIOReactorConfig(ioReactorConfig).build();
        client.start();

        final HttpHost target = new HttpHost("672fbf9066e42ceaf15e9a9b.mockapi.io");
        final String requestUri = "/api/contacts";

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Contoh HTTP Client di Swing");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400,200);
            frame.setLayout(new BorderLayout());

            JLabel statusLabel = new JLabel("tekan tombol untuk memulai mengunduh data", JLabel.CENTER);

            JButton starButton = new JButton("Mulai");

            JProgressBar progressBar = new JProgressBar(0,100);

            frame.add(statusLabel, BorderLayout.NORTH);

            JTextArea textArea = new JTextArea();
            textArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(textArea);
            frame.add(scrollPane, BorderLayout.CENTER);

            JPanel panel = new JPanel();
            panel.add(starButton);
            panel.add(progressBar);
            panel.setLayout(new FlowLayout());
            frame.add(panel, BorderLayout.SOUTH);

            frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            frame.addWindowListener(new WindowListener() {
                @Override
                public void windowOpened(WindowEvent e) {
                }
                @Override
                public void windowClosing(WindowEvent e) {
                    client.close(CloseMode.GRACEFUL);
                    System.exit(0);
                }
                @Override
                public void windowClosed(WindowEvent e) {
                }
                @Override
                public void windowIconified(WindowEvent e){
                }
                @Override
                public void windowDeiconified(WindowEvent e){
                }
                @Override
                public void windowActivated(WindowEvent e){
                }
                @Override
                public void windowDeactivated(WindowEvent e){
                }
            });
            final SimpleHttpRequest request = SimpleRequestBuilder.get().setHttpHost(target).setPath(requestUri).build();

            starButton.addActionListener(e -> {
                progressBar.setIndeterminate(true);
                starButton.setEnabled(false);
                statusLabel.setText("Proses Berjalan...");
                textArea.setText("");
                client.execute(
                    SimpleRequestProducer.create(request),
                    SimpleResponseConsumer.create(),
                    new FutureCallback<>() {
                        @Override
                        public void completed(final SimpleHttpResponse response) {
                            System.out.println(request + "->"+new StatusLine(response));
                            System.out.println(response.getBodyText());

                            JSONParser parser = new JSONParser();
                            try {
                                JSONArray contacts = (JSONArray) parser.parse(response.getBodyText());
                                contacts.forEach(obj ->{
                                    JSONObject contact = (JSONObject) obj;
                                    String line = "Name: " + contact.get("name")  + ", Phone: " + contact.get("phone");
                                    textArea.append(line+"\n");
                                });
                            } catch (ParseException e) {
                                throw new RuntimeException(e);
                            }
                            progressBar.setIndeterminate(false);
                            starButton.setEnabled(true);
                            statusLabel.setText("Selesai");
                        }
                        @Override
                        public void failed(final Exception ex){
                            System.out.println(request + "->" + ex);
                            progressBar.setIndeterminate(false);
                            starButton.setEnabled(true);
                            statusLabel.setText("Proses Gagal");
                        }
                        @Override
                        public void cancelled(){
                            System.out.println(request + "->" + "cancelled");
                            progressBar.setIndeterminate(false);
                            starButton.setEnabled(true);
                            statusLabel.setText("Proses Dibatalkan");
                        }
                    });
            });
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
