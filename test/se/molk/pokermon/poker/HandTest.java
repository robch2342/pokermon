package se.molk.pokermon.poker;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * Created by robin on 2016-02-13.
 */
public class HandTest {
    HashMap<String, Hand> hands;
    ArrayList<String> handNames;

    @Before
    public void setUp() {
        handNames = new ArrayList<>();
        handNames.add("Straight Flush Ace High");
        handNames.add("Straight Flush Six High");
        handNames.add("Four Aces");
        handNames.add("Four Eights");
        handNames.add("Sevens Over Kings");
        handNames.add("Sevens Over Fives");
        handNames.add("Twos Over Aces");
        handNames.add("Flush Ace High");
        handNames.add("Flush Seven High");
        handNames.add("Straight Ace High");
        handNames.add("Straight 8 High");
        handNames.add("Straight 5 High");
        handNames.add("Three Aces");
        handNames.add("Three 2s, Ace Kicker");
        handNames.add("Three 2s");
        handNames.add("Two Pairs, 2s and Aces");
        handNames.add("Two Pairs, 3s and Kings");
        handNames.add("Two Pairs, 9s and 4s, 10 Kicker");
        handNames.add("Two Pairs, 9s and 4s");
        handNames.add("Pair Of Aces");
        handNames.add("Pair Of 9s, King Kicker");
        handNames.add("Pair Of 9s");
        handNames.add("Pair Of 4s");
        //handNames.add("");
        hands = new HashMap<>();

        hands.put("Straight Flush Ace High", new Hand(new Card("H13"), new Card("H14"), new Card("H12"), new Card("H11"), new Card("H10")));
        hands.put("Straight Flush Six High", new Hand(new Card("D2"), new Card("D6"), new Card("D4"), new Card("D5"), new Card("D3")));
        hands.put("Four Aces", new Hand(new Card("D14"), new Card("H14"), new Card("H12"), new Card("C14"), new Card("S14")));
        hands.put("Four Eights", new Hand(new Card("D2"), new Card("H8"), new Card("D8"), new Card("C8"), new Card("S8")));
        hands.put("Sevens Over Kings", new Hand(new Card("H13"), new Card("D7"), new Card("H7"), new Card("S13"), new Card("C7")));
        hands.put("Sevens Over Fives", new Hand(new Card("S7"), new Card("D7"), new Card("H5"), new Card("C7"), new Card("C5")));
        hands.put("Twos Over Aces", new Hand(new Card("H2"), new Card("H14"), new Card("D14"), new Card("C2"), new Card("S2")));
        hands.put("Flush Ace High", new Hand(new Card("H2"), new Card("H14"), new Card("H12"), new Card("H5"), new Card("H10")));
        hands.put("Flush Seven High", new Hand(new Card("H2"), new Card("H7"), new Card("H5"), new Card("H3"), new Card("H4")));
        hands.put("Straight Ace High", new Hand(new Card("D10"), new Card("H14"), new Card("C12"), new Card("S11"), new Card("H13")));
        hands.put("Straight 8 High", new Hand(new Card("H8"), new Card("D4"), new Card("H7"), new Card("C5"), new Card("S6")));
        hands.put("Straight 5 High", new Hand(new Card("C3"), new Card("H5"), new Card("D2"), new Card("H14"), new Card("S4")));
        hands.put("Three Aces", new Hand(new Card("D2"), new Card("S14"), new Card("D12"), new Card("C14"), new Card("S14")));
        hands.put("Three 2s, Ace Kicker", new Hand(new Card("D2"), new Card("S2"), new Card("D14"), new Card("C2"), new Card("S9")));
        hands.put("Three 2s", new Hand(new Card("D2"), new Card("S2"), new Card("D12"), new Card("C2"), new Card("S9")));
        hands.put("Two Pairs, 2s and Aces", new Hand(new Card("H2"), new Card("H14"), new Card("D2"), new Card("H5"), new Card("C14")));
        hands.put("Two Pairs, 3s and Kings", new Hand(new Card("H3"), new Card("D3"), new Card("H12"), new Card("S13"), new Card("H13")));
        hands.put("Two Pairs, 9s and 4s, 10 Kicker", new Hand(new Card("H9"), new Card("C4"), new Card("S9"), new Card("H4"), new Card("H10")));
        hands.put("Two Pairs, 9s and 4s", new Hand(new Card("H9"), new Card("C4"), new Card("S9"), new Card("H4"), new Card("H7")));
        hands.put("Pair Of Aces", new Hand(new Card("H2"), new Card("D14"), new Card("H12"), new Card("H14"), new Card("S10")));
        hands.put("Pair Of 9s, King Kicker", new Hand(new Card("H2"), new Card("D9"), new Card("H12"), new Card("H13"), new Card("S9")));
        hands.put("Pair Of 9s", new Hand(new Card("H2"), new Card("D9"), new Card("H12"), new Card("H11"), new Card("S9")));
        hands.put("Pair Of 4s", new Hand(new Card("H2"), new Card("D4"), new Card("H12"), new Card("H4"), new Card("S10")));
    }


    @Test
    public void testGT() {
        for (int i = 0; i < handNames.size(); i++) {
            String hand1Name = handNames.get(i);
            Hand hand1 = hands.get(hand1Name);
            for (int j = 0; j < i; j++) {
                String hand2Name = handNames.get(j);
                Hand hand2 = hands.get(hand2Name);
                assertTrue(hand1Name + " (" + hand1.print() + ") should not beat " + hand2Name + " (" + hand2.print() + ")", !hand1.gt(hand2));
            }
            assertTrue(hand1Name + " equals " + hand1Name + " (" + hand1.print() + ")", hand1.eq(hand1));
            for (int j = i + 1; j < handNames.size(); j++) {
                String hand2Name = handNames.get(j);
                Hand hand2 = hands.get(hand2Name);
                //System.out.println(hand2Name + ", value = " + hand2.getValue());
                assertTrue(hand1Name + " (" + hand1.print() + ") should beat " + hand2Name + " (" + hand2.print() + ")", hand1.gt(hand2));
            }
        }
    }

    @Test
    public void testFindBestHand() {
        Card[] sevenCards = {new Card("S14"), new Card("H14"), new Card("H8"), new Card("H2"), new Card("H7"), new Card("H6"), new Card("H3")};
        Hand bestHand = Hand.findBestHand(sevenCards);
        System.out.println(bestHand.print());
    }

}