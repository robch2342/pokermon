package se.molk.pokermon.client;

import se.molk.pokermon.server.PokerServer;

import javax.swing.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import java.awt.*;
import java.awt.event.*;

public class ServerDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JButton stopServerButton;
    private JTextField serverName;
    private JLabel statusLabel;
    private PokerGUI gui;


    public ServerDialog(PokerGUI gui, String name, boolean running) {
        this.gui = gui;
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

// call onCancel() when cross is clicked
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

// call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        serverName.setText(name);

        if (running) {
            statusLabel.setText("Server is online!");
            statusLabel.setForeground(Color.GREEN);
        } else {
            stopServerButton.setEnabled(false);
        }

        serverName.addCaretListener(new CaretListener() {
            @Override
            public void caretUpdate(CaretEvent e) {
                serverNameChanged();
            }
        });

        stopServerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stopServer();
            }
        });

        pack();
        setVisible(true);
    }

    private void stopServer() {
        gui.stopServer();
        stopServerButton.setEnabled(false);
        statusLabel.setText("Server is offline");
        statusLabel.setForeground(Color.RED);
    }

    private void onOK() {
        gui.startServer(serverName.getText());
        dispose();
    }

    private void onCancel() {
        dispose();
    }

    private void serverNameChanged() {
        System.out.println(serverName.getText());
        if (serverName.getText().length() > 0) {
            buttonOK.setEnabled(true);
        } else {
            buttonOK.setEnabled(false);
        }
    }
}
