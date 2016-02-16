package se.molk.pokermon.poker;

import org.junit.Test;
import se.molk.pokermon.poker.Card;
import se.molk.pokermon.poker.Deck;

import static org.junit.Assert.*;

/**
 * Created by robin on 2016-02-13.
 */
public class DeckTest {

    @Test
    public void testShuffle() throws Exception {
        Deck deck = new Deck();
        Card[] deck1 = deck.getCards().clone();
        deck.shuffle();
        Card[] deck2 = deck.getCards();
        double diff = diff(deck1, deck2);
        for (int i = 0; i < 999; i++) {
                deck1 = deck2.clone();
                deck.shuffle();
                deck2 = deck.getCards();
                diff += diff(deck1, deck2);
                diff /= 2;
        }
        System.out.println("Total: " + diff);
        assertTrue("Randomness is at least 95%", diff > 95);
    }

    public double diff(Card[] deck1, Card[] deck2) {
        double diff = 0;
        for (int i = 0; i < 52; i++) {
            if (!deck1[i].toString().equals(deck2[i].toString())) {
                diff += 1;
            }
        }
        return diff / .52;
    }
}