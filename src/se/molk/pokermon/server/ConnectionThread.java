package se.molk.pokermon.server;

import java.io.*;
import java.net.Socket;

/**
 * Created by robin on 2016-02-10.
 */
public class ConnectionThread extends Thread {
    private PokerServer server;
    private Socket socket;
    private BufferedReader in;
    private BufferedWriter out;
    private boolean running = false;
    private String userName = null;

    public ConnectionThread(PokerServer server, Socket socket) {
        this.server = server;
        this.socket = socket;
    }

    public boolean loggedIn() {
        return userName != null;
    }

    public String getUserName() {
        return userName;
    }

    public void endThread() {
        running = false;
        try {
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.interrupt();
    }

    public void run() {
        running = true;
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            doStuff();
        } catch (Exception e) {
            e.printStackTrace();
        }
        PokerServer.getServer().removeConnection(this);
    }

    /**
     * Send data to the client.
     * @param output the data to output.
     */
    private void send(String output) {
        try {
            out.write(output + "\n");
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void doStuff() throws Exception {
        PokerServer server = PokerServer.getServer();
        send("WELCOME " + server.getPlayerCount() + "/" + server.getMaxPlayers() + ":" + server.getServerName());
        if (server.isFull()) {
            endThread();
            return;
        }
        String nextline;
        for (int i = 0; i < 10; i++) {
            if (in.ready()) {
                nextline = in.readLine();
                if (nextline.matches("LOGIN .+")) {
                    String name = nextline.substring(7);
                    synchronized (server) {

                        //userName = server.attemptLogin(name);
                        break;
                    }
                }
            }
            Thread.sleep(1000);
        }
        if (!loggedIn()) {
            endThread();
            return;
        }
        while ((nextline = in.readLine())!=null) {
            System.out.println(nextline);
        }
    }
}
