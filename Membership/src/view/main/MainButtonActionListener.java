package view.main;

import java.awt.event.*;

public class MainButtonActionListener implements ActionListener {
    private MainFrame MainFrame;
    public MainButtonActionListener(MainFrame MainFrame){
        this.MainFrame = MainFrame;
    }
    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == MainFrame.getButtonJenisMember()){
            MainFrame.showJenisMemberFrame();
        }else {
            MainFrame.showMemberFrame();
        }
    }
}
