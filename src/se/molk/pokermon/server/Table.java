package se.molk.pokermon.server;

import se.molk.pokermon.poker.Card;

import java.util.Iterator;

/**
 * Created by robin on 2016-02-12.
 */
public class Table implements Iterable<Player>{
    private Player[] players;
    private int[] seats;
    private Card[] board = new Card[5];
    private int dealer = -1;

    public int size() {
        return seats.length;
    }

    public Player getPlayer(int i) {
        return players[i];
    }

    public Player getNextPlayer(int i) {
        if (i >= seats.length || i < 0) {
            return null;
        }
        int j = i + 1;
        while (true) {
            if (j >= seats.length) {
                j = 0;
            }
            if (j == i) {
                return null;
            }
            Player player = getSeat(i);
            if (player != null) {
                return player;
            }
            j++;
        }
    }
    public Table(int capacity) {
        players = new Player[capacity];
        seats = new int[capacity];
        for (int i = 0; i < capacity; i++) {
            seats[i] = -1;
        }
    }

    public Player getSeat(int i) {
        if (seats[i] != -1) {
            return players[seats[i]];
        }
        return null;
    }

    public boolean sit(int seat, Player player) {
        if (seats[seat] == -1) {
            for (int i = 0; i < players.length; i++) {
                if (players[i] == player) {
                    seats[seat] = i;
                    return true;
                }
            }
        }
        return false;
    }


    @Override
    public Iterator<Player> iterator() {
        return new PlayerIterator();
    }

    public class PlayerIterator implements Iterator<Player> {
        int index = 0;

        @Override
        public boolean hasNext() {
            for (int i = index; i < players.length; i++) {
                if (players[i] != null) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public Player next() {
            for (int i = index; i < players.length; i++) {
                if (players[i] != null) {
                    index = i + 1;
                    return players[i];
                }
            }
            return null;
        }

        @Override
        public void remove() {
            //players[index - 1] = null;
        }
    }
}
