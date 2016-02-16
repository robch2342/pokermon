package se.molk.pokermon.poker;

/**
 * Created by robin on 2016-02-12.
 */
public class Card {
    private Suit suit = null;
    private int value = 0;

    public Card(Suit suit, int value) {
        this.suit = suit;
        this.value = value;
    }

    public Card(String card) {
        if (card.equals("X")) {
            return;
        }
        switch (card.charAt(0)) {
            case 'C': suit = Suit.CLUBS;
                break;
            case 'D': suit = Suit.DIAMONDS;
                break;
            case 'H': suit = Suit.HEARTS;
                break;
            case 'S': suit = Suit.SPADES;
        }
        value = Integer.valueOf(card.substring(1));
    }

    public boolean eq(Card card) {
        return value == card.value;
    }
    public boolean gt(Card card) {
        return value > card.value;
    }

    public boolean sequential(Card card) {
        return value + 1 == card.value || (value == 14 && card.value == 2);
    }

    public Suit getSuit() {
        return suit;
    }

    public int getValue() {
        return value;
    }

    public String printName() {
        String name = "";
        switch (value) {
            case 11:
                name = "Jack";
                break;
            case 12:
                name = "Queen";
                break;
            case 13:
                name = "King";
                break;
            case 14:
                name = "Ace";
                break;
            default:
                name += value;
        }
        switch (suit) {
            case CLUBS: name += " of Clubs";
                break;
            case DIAMONDS: name += " of Diamonds";
                break;
            case HEARTS: name += " of Hearts";
                break;
            case SPADES: name += " of Spades";
        }
        return name;
    }

    public String toString() {
        String name = "";
        switch (suit) {
            case CLUBS:
                name = "♣";
                break;
            case DIAMONDS:
                name = "♦";
                break;
            case HEARTS:
                name = "♥";
                break;
            case SPADES:
                name = "♠";
        }
        switch (value) {
            case 11:
                name += "Jack";
                break;
            case 12:
                name += "Queen";
                break;
            case 13:
                name += "King";
                break;
            case 14:
                name += "Ace";
                break;
            default:
                name += value;
        }
        return name;
    }

    public enum Suit {
        CLUBS, DIAMONDS, HEARTS, SPADES;
    }
}
