package se.molk.pokermon.poker;

import java.util.ArrayList;

/**
 * Created by robin on 2016-02-12.
 */
public class Deck {
    Card[] cards = new Card[52];
    int index = 51;

    public Card[] getCards() {
        return cards;
    }

    public Deck() {
        int i = 0;
        for (Card.Suit suit : Card.Suit.values()) {
            for (int value = 2; value <= 14; value++) {
                cards[i] = new Card(suit, value);
                i++;
            }
        }
    }

    public void shuffle() {
        for (int i = 0; i < 52; i++) {
            Card card = cards[i];
            int j = (int) (Math.random() * 52);
            cards[i] = cards[j];
            cards[j] = card;
        }
    }

    public String print() {
        String string = "";
        for (int i = 0; i < 51; i++) {
            string += cards[i].toString() + ", ";
        }
        return string + cards[51];
    }
}
