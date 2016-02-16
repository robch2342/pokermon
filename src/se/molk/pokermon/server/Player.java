package se.molk.pokermon.server;

/**
 * Created by robin on 2016-02-12.
 */
public class Player {
    ConnectionThread connection;

    public void Player(ConnectionThread connection) {
        this.connection = connection;
    }
}
