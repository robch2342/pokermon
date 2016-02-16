package se.molk.pokermon.poker;

import java.util.ArrayList;

/**
 * Created by robin on 2016-02-13.
 */
public class Hand {
    private Card[] cards = new Card[5];
    /**
     * The value of this type of hand. Straight flush = 8, Four of a kind = 7, ..., One pair = 1, and High card = 0.
     */
    private int value = -1;

    public Hand(Card card1, Card card2, Card card3, Card card4, Card card5) {
        cards[0] = card1;
        cards[1] = card2;
        cards[2] = card3;
        cards[3] = card4;
        cards[4] = card5;
        calculateValue();
    }

    public Hand() {

    }

    public void addCard(Card card) {
        for (int i = 0; i < 5; i++) {
            if (cards[i] == null) {
                cards[i] = card;
                return;
            }
        }
    }

    public boolean gt(Hand hand) {
        if (value > hand.getValue()){
            return true;
        } else if (value == hand.getValue()) {
            if (value == 8 || value == 4) {
                return getStraightHighCard().gt(hand.getStraightHighCard());
            } else {
                //System.out.println("card by card compare. (" + print() + ") vs (" + hand.print() + ")");
                for (int i = 4; i >= 0; i--) {
                    if (cards[i].gt(hand.cards[i])) {
                        //System.out.println(cards[i].toString() + " beat " + hand.cards[i].toString());
                        return true;
                    } else if (hand.cards[i].gt(cards[i])) {
                        return false;
                    }
                }
            }
        }
        return false;
    }

    public int getValue(){
        return value;
    }

    public boolean eq(Hand hand) {
        if (value != hand.value) {
            return false;
        }
        for (int i = 0; i < 5; i++) {
            if (!cards[i].eq(hand.cards[i])) {
                return false;
            }
        }
        return true;
    }

    public void calculateValue() {
        sort();
        boolean flush = checkFlush();
        boolean straight = checkStraight();
        //Check for straight flush.
        if (flush && straight) {
            value = 8;
            return;
        } else if (checkFourOfAKind()) {
            value = 7;
            return;
        }
        boolean toak = checkThreeOfAKind();
        int pairs = countPairs(toak);
        //Check for full house.
        if (toak && pairs == 1) {
            value = 6;
        } else if (flush) {
            value = 5;
        } else if (straight) {
            value = 4;
        } else if (toak) {
            value = 3;
        } else if (pairs == 2) {
            value = 2;
        } else if (pairs == 1) {
            value = 1;
        } else {
            value = 0;
        }
    }

    private boolean checkThreeOfAKind() {
        for (int i = 0; i < 3; i++) {
            if (cards[i].eq(cards[i + 1]) && cards[i].eq(cards[i + 2])) {
                //Move ToaK to the back.
                if (i == 1) {
                    Card c = cards[4];
                    cards[4] = cards[1];
                    cards[1] = c;
                } else if (i == 0) {
                    Card c = cards[0];
                    cards[0] = cards[3];
                    cards[3] = cards[1];
                    cards[1] = cards[4];
                    cards[4] = c;
                }
                return true;
            }
        }
        return false;
    }

    private int countPairs(boolean toak) {
        //Check for pair that is part of full house.
        if (toak) {
            if (cards[0].eq(cards[1])) {
                return 1;
            }
            return 0;
        }
        //Check for two or one pair.
        int pairs = 0;
        for (int i = 4; i > 0; i--) {
            if (cards[i].eq(cards[i - 1])) {
                //Move pairs to the back.
                if (pairs == 1 && i == 1) {
                    //Move the second pair.
                    Card c = cards[2];
                    cards[2] = cards[0];
                    cards[0] = c;
                } else if (pairs == 0 && i != 4) {
                    //Move the first pair.
                    int j = i;
                    while (j < 4) {
                        Card c = cards[4];
                        for (int k = 4; k > j; k--) {
                            cards[k] = cards[k - 1];
                        }
                        cards[j] = c;
                        j++;
                    }
                }
                pairs++;
            }
        }
        return pairs;
    }

    private boolean checkFourOfAKind()  {
        if (cards[0].getValue() == cards[1].getValue() && cards[1].getValue() == cards[2].getValue() && cards[2].getValue() == cards[3].getValue()) {
            //Move kicker to the first position.
            Card c = cards[4];
            cards[4] = cards[0];
            cards[0] = c;
            return true;
        } else if (cards[1].getValue() == cards[2].getValue() && cards[2].getValue() == cards[3].getValue() && cards[3].getValue() == cards[4].getValue()) {
            return true;
        }
        return false;
    }

    private boolean checkFlush() {
        for (int i = 1; i < 5; i++) {
            if (cards[0].getSuit() != cards[i].getSuit()) {
                //System.out.println(print() + " not a flush");
                return false;
            }
        }
        return true;
    }

    private boolean checkStraight() {
        boolean s = true;
        for (int i = 0; i < 4; i++) {
            if (!cards[i].sequential(cards[i + 1])) {
                //System.out.println(print() + " not a straight");
                s = false;
                break;
            }
        }
        //Check for straight beginning with Ace.
        if (!s && cards[4].getValue() == 14 && cards[0].getValue() == 2 && cards[1].getValue() == 3 &&
                cards[2].getValue() == 4 && cards[3].getValue() == 5) {
            s = true;
        }
        return s;
    }

    private Card getStraightHighCard() {
        if (cards[4].getValue() == 14) {
            return cards[3];
        }
        return cards[4];
    }

    private void sort() {
        boolean done = false;
        while (!done) {
            done = true;
            for (int i = 0; i < 4; i++) {
                if (cards[i].gt(cards[i + 1])) {
                    Card c = cards[i];
                    cards[i] = cards[i + 1];
                    cards[i + 1] = c;
                    done = false;
                }
            }
        }
    }

    public String print() {
        String result = "";
        for (int i = 4; i > 0; i--) {
            result += cards[i].toString() + ", ";
        }
        return result + cards[0];
    }

    public static Hand findBestHand(Card[] sevenCards) {
        Hand bestHand = new Hand();
        int perms = 0;
        for (int skip1 = 0; skip1 < 7; skip1++) {
            for (int skip2 = 0; skip2 <7; skip2++) {
                if (skip1 != skip2) {
                    //Generate new permutation
                    Hand newHand = new Hand();
                    for (int i = 0; i < 7; i++) {
                        if (i != skip1 && i != skip2) {
                            newHand.addCard(sevenCards[i]);
                        }
                    }
                    //Check it against old hand;
                    newHand.calculateValue();
                    if (newHand.gt(bestHand)) {
                        bestHand = newHand;
                    }
                    perms++;
                }
            }
        }
        System.out.println("Permutations: " + perms);
        return bestHand;
    }
}
