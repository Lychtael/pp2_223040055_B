package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.*;
import model.*;
import org.apache.ibatis.session.SqlSession;
import view.UserPdf;
import view.UserView;

public class UserController {
    private UserView view;
    private UserMapper mapper;
    private UserPdf pdf;

    public UserController(UserView view, UserMapper mapper, UserPdf pdf) {
        this.view = view;
        this.mapper = mapper;
        this.pdf = pdf;

        this.view.addAddUserListener(new AddUserListener());
        this.view.addRefreshListener(new RefreshListener());
        this.view.addExportListener(new ExportListener());
    }

    class AddUserListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = view.getNameInput();
            String email = view.getEmailInput();
            if (!name.isEmpty() && !email.isEmpty()) {
                SwingWorker<Void, Integer> worker = new SwingWorker<Void, Integer>() {
                    @Override
                    protected Void doInBackground() throws Exception {
                        for (int i = 1; i <= 100; i++) {
                            String newName = name + i;
                            String newEmail = email + i + "@gmail.com";

                            addUser(newName, newEmail);

                            publish(i); 
                        }
                        return null;
                    }

                    @Override
                    protected void process(List<Integer> chunks) {
                        int progress = chunks.get(chunks.size() - 1); 
                        view.updateProgress(progress); 
                    }

                    @Override
                    protected void done() {
                        try {
                            get(); 
                            JOptionPane.showMessageDialog(view, "All users added successfully!");
                            refreshUserList();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(view, "An error occurred: " + ex.getMessage());
                        }
                    }
                };

                worker.execute();
            } else {
                JOptionPane.showMessageDialog(view, "Please fill in all fields.");
            }
        }

        private void addUser(String name, String email) {
            SqlSession session = MyBatisUtil.getSqlSession();
            try {
                UserMapper mapper = session.getMapper(UserMapper.class);
                User user = new User(name, email);
                mapper.insertUser(user); 
                session.commit(); 
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(view, "Failed to add user: " + ex.getMessage());
            } finally {
                session.close();
            }
        }

        private void refreshUserList() {
            List<User> users = mapper.getAllUsers();
            String[] userArray = users.stream()
                .map(u -> u.getName() + " (" + u.getEmail() + ")")
                .toArray(String[]::new);
            view.setUserList(userArray); 
        }
    }

    class RefreshListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            refreshUserList();
        }

        private void refreshUserList() {
            List<User> users = mapper.getAllUsers();
            String[] userArray = users.stream()
                .map(u -> u.getName() + " (" + u.getEmail() + ")")
                .toArray(String[]::new);
            view.setUserList(userArray); 
        }
    }

    class ExportListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            List<User> users = mapper.getAllUsers();
            pdf.exportPdf(users);
            JOptionPane.showMessageDialog(view, "User data exported to PDF.");
        }
    }
}
