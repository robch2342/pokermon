package se.molk.pokermon.server;

import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by robin on 2016-02-10.
 */
public class PokerServer extends Thread{
    private ArrayList<ConnectionThread> connections = new ArrayList<>();
    private ServerSocket serverSocket;
    private String serverName;
    private boolean running = false;
    private static PokerServer server = null;
    private Table table = new Table(8);

    private PokerServer() {}

    public static PokerServer getServer() {
        if (server == null) {
            synchronized (PokerServer.class) {
                if (server == null) {
                    server = new PokerServer();
                }
            }
        }
        return server;
    }

    public void endThread() {
        for (ConnectionThread thread : connections) {
            thread.endThread();
        }

        running = false;
        try {
            serverSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.interrupt();

    }

    public void login(ConnectionThread connection) {

    }

    public boolean validateName(String name) {
        return name.matches("[a-zA-ZåäöÅÄÖ_\\- ]{5,25}") && name.charAt(0) != ' ' && name.charAt(name.length() - 1) != ' ';
    }

    public boolean nameAvailable(String name) {
        for (ConnectionThread connection : connections) {
            if (connection.loggedIn() && name.equals(connection.getUserName())) {
                return false;
            }
        }
        return true;
    }

    public boolean isFull() {
        return getPlayerCount() >= getMaxPlayers();
    }

    public void removeConnection(ConnectionThread connection) {
        connections.remove(connection);
    }

    public int getPlayerCount() {
        int count = 0;
        for (ConnectionThread connection : connections) {
            if (connection.loggedIn()) {
                count++;
            }
        }
        return count;
    }

    public int getMaxPlayers() {
        return 8;
    }

    private void clearConnections() {
        for (ConnectionThread thread : connections) {
            if (!thread.isAlive()) {
                connections.remove(thread);
            }
        }
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public static void main(String[] args) {
        new PokerServer().start();
    }

    public void run() {
        System.out.println("Running " + getServerName());
        try {
            running = true;
            serverSocket = new ServerSocket(4223);
            while (running) {
                ConnectionThread connection = new ConnectionThread(this, serverSocket.accept());
                connections.add(connection);
                connection.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Ending " + getName());
    }


}
