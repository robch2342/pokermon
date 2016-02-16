package se.molk.pokermon.client;

import se.molk.pokermon.server.PokerServer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by robin on 2016-02-10.
 */
public class PokerGUI implements ActionListener{
    private JPanel panel;
    private JTextPane textPane1;
    private JButton serverStartButton;
    private JPanel tablePanel;
    private JTextField textField1;
    private PokerClient client;

    public PokerGUI() {
        serverStartButton.addActionListener(this);
    }

    public static void main(String[] args) {
        JFrame jFrame = new JFrame("Pokermon - Gotta bet it all!");
        PokerGUI pokerGUI = new PokerGUI();
        jFrame.setContentPane(pokerGUI.panel);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.pack();
        jFrame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("server start")) {

            PokerServer server = PokerServer.getServer();
            new ServerDialog(this, server != null && server.getServerName() != null ? server.getServerName() : "Gotta bet it all!", server != null && server.isAlive());
        }
    }

    public void stopServer() {
        PokerServer.getServer().endThread();
    }

    public void startServer(String serverName) {
        PokerServer server = PokerServer.getServer();
        if (server == null || !server.isAlive()) {
            server.setServerName(serverName);
            server.start();
        }
    }

}
