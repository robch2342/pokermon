package se.molk.pokermon.server;

import java.net.ServerSocket;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by robin on 2016-02-10.
 */
public class PokerServer extends Thread{
    private ArrayList<ConnectionThread> connections = new ArrayList<>();
    private HashMap<String, ConnectionThread> players = new HashMap<>();
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

    public synchronized void login(ConnectionThread connection) {
        players.put(connection.getUserName(), connection);
        connections.remove(connection);
    }

    public boolean validateName(String name) {
        return name.matches("[a-zA-ZåäöÅÄÖ_\\- ]{5,25}") && name.charAt(0) != ' ' && name.charAt(name.length() - 1) != ' ';
    }

    public boolean nameAvailable(String name) {
        return !players.keySet().contains(name);
    }

    public boolean isFull() {
        return getPlayerCount() >= getMaxPlayers();
    }

    public void removeConnection(ConnectionThread connection) {
        if (connection.loggedIn()) {
            players.remove(connection.getUserName());
            System.out.println("Removed player " + connection.getUserName());
        } else {
            connections.remove(connection);
            System.out.println("Removed connection");
        }
    }

    public int getPlayerCount() {
        return players.size();
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
                ConnectionThread connection = new ConnectionThread(serverSocket.accept());
                connections.add(connection);
                connection.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Ending " + getName());
    }


}
