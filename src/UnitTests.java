import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class UnitTests {

	// CARD CLASS TESTS
	
	   @Test
	   // Card initialized with correct suit
	   public void testCardSuit() {	 
		   Card card = new Card(3,9);
	      assertEquals(3,card.getSuit());
	   }
	   
	   @Test
	   // Card initialized with correct rank
	   public void testCardRank() {	 
		   Card card = new Card(2,6);
	      assertEquals(6,card.getRank());
	   }
	   
	   @Test
	   // Constructor appropriately handles invalid suit input
	   public void testCardInvalidSuitInput() {	 
		   Card card = new Card(5,1);
	      assertEquals(0,card.getSuit());
	   }
	   
	   @Test
	   // Constructor appropriately handles invalid rank input
	   public void testCardInvalidRankInput() {	 
		   Card card = new Card(3,15);
	      assertEquals(0,card.getRank());
	   }
	  
	// DECK CLASS TESTS
	   
	   @Test
	   // Single deck initialized with correct number of cards
	   public void testDeckSize1() {	 
		   Deck deck = new Deck(1);
	      assertEquals(52,deck.deckSize());
	   }
	   
	   @Test
	   // Multiple decks initialized with correct number of cards
	   public void testDeckSize2() {	 
		   Deck deck = new Deck(8);
		   int total_size = 8*52;
	      assertEquals(total_size,deck.deckSize());
	   }
	   
	   @Test
	   // Test Deck Shuffle method
	   // Splits deck into first and second halves, ensures mean rank of both are within specified threshold
	   public void testDeckShuffle() {	 
		   Deck deck = new Deck(8);
		   deck.shuffleDeck();
		   
		   int deck_size = deck.deckSize();
		   
		   // Average card rank of first half of deck
		   int sum_first_half = 0;
		   for (int i = 0; i < deck_size/2; i++) {
			   Card card = deck.dealCard();
			   sum_first_half += card.getRank();
		   }
		   double avg_first_half = (double)sum_first_half/(deck_size/2);
		   
		   // Average card rank of second half of deck
		   int sum_second_half = 0;
		   for (int i = 0; i < deck_size/2; i++) {
			   Card card = deck.dealCard();
			   sum_second_half += card.getRank();
		   }
		   double avg_second_half = (double)sum_second_half/(deck_size/2);
		   
		   double delta = Math.abs(avg_first_half - avg_second_half);
		   // Ensure delta lower than threshold
	       assertTrue(delta < 2);
	   }
	   
	   @Test
	   // Test Deck Shuffle method 2 (check suit)
	   // Splits deck into first and second halves, ensures mean suit of both are within specified threshold
	   public void testDeckShuffle2() {	 
		   Deck deck = new Deck(8);
		   deck.shuffleDeck();
		   
		   int deck_size = deck.deckSize();
		   
		   // Average card suit of first half of deck
		   int sum_first_half = 0;
		   for (int i = 0; i < deck_size/2; i++) {
			   Card card = deck.dealCard();
			   sum_first_half += card.getSuit();
		   }
		   double avg_first_half = (double)sum_first_half/(deck_size/2);
		   
		   // Average card suit of second half of deck
		   int sum_second_half = 0;
		   for (int i = 0; i < deck_size/2; i++) {
			   Card card = deck.dealCard();
			   sum_second_half += card.getSuit();
		   }
		   double avg_second_half = (double)sum_second_half/(deck_size/2);
		   
		   double delta = Math.abs(avg_first_half - avg_second_half);
		   // Ensure delta lower than threshold
	       assertTrue(delta < .5);
	   }
	 
	 // HAND CLASS TESTS
	   
	   
	   @Test
	   // Test computeValue method
	   public void testHandComputeValue() {	 
		   // Create cards for test hand
		   Card c1 = new Card(1,11); // Jack of Clubs
		   Card c2 = new Card(3,2); // 2 of Hearts
		   Card c3 = new Card(4,5); // 5 of Spades
		   
		   Hand hand = new Hand();
		   hand.addCard(c1);
		   hand.addCard(c2);
		// Compute value of 2 card hand
		   int value1 = hand.computeValue(); 
		   
		   // Add a third card and compute value again
		   hand.addCard(c3);
		   int value2 = hand.computeValue();
		      
	      assertTrue((value1==12)&&(value2==17));
	   }
	   
	   @Test
	   // Test computeValue method Again, confirming dynamic scoring of aces
	   public void testHandComputeValueAces() {	 
		   // Create cards for test hand
		   Card c1 = new Card(4,1); // Ace of Spades
		   Card c2 = new Card(2,8); // 8 of Diamonds
		   Card c3 = new Card(4,5); // 5 of Spades
		   
		   Hand hand = new Hand();
		   hand.addCard(c1);
		   hand.addCard(c2);
		// Compute value of 2 card "soft" hand (Ace value = 11)
		   int value1 = hand.computeValue(); 
		   
		   // Add a third card and compute value again (Ace value = 1 now)
		   hand.addCard(c3);
		   int value2 = hand.computeValue();
		      
	      assertTrue((value1==19)&&(value2==14));
	   }
	   
	   // Test isSoftHand method
	   // Confirm isSoftHand method returns true when ace is counting as 11 in hand, and false otherwise
	   @Test
	   public void testHandIsSoftHand() {	 
		   // Create cards for test hand
		   Card c1 = new Card(4,1); // Ace of Spades
		   Card c2 = new Card(2,8); // 8 of Diamonds
		   Card c3 = new Card(4,5); // 5 of Spades
		   
		   Hand hand = new Hand();
		   hand.addCard(c1);
		   hand.addCard(c2);
		   // Compute value of 2 card "soft" hand (Ace value = 11)
		   boolean isSoft1 = hand.isSoftHand();
		   
		   // Add a third card and compute value again (Ace value = 1 now)
		   hand.addCard(c3);
		   boolean isSoft2 = hand.isSoftHand();
		   
		   // New hand with no aces - not a "soft" hand
		   Card c4 = new Card(1,11); // Jack of Clubs
		   Card c5 = new Card(3,2); // 2 of Hearts
		   Hand hand3 = new Hand();
		   hand3.addCard(c4);
		   hand3.addCard(c5);
		   boolean isSoft3 = hand.isSoftHand();
		   
		      
	      assertTrue((isSoft1==true)&&(isSoft2==false)&&(isSoft3==false));
	   }
	   
	   
	   @Test
	// Test isBust method
	   public void testHandIsBust() {	 
		   // Create cards for test hand
		   Card c1 = new Card(1,11); // Jack of Clubs
		   Card c2 = new Card(3,8); // 8 of Hearts
		   Card c3 = new Card(4,5); // 5 of Spades
		   
		   Hand hand = new Hand();
		   hand.addCard(c1);
		   hand.addCard(c2);
		   // 2 card hand, value = 18 (<21, hand not bust)
		   boolean bust1 = hand.isBust(); 
		   
		   // Add a third card and compute value again (value now = 23, hand is bust)
		   hand.addCard(c3);
		   boolean bust2 = hand.isBust(); 
		      
	      assertTrue((bust1==false)&&(bust2==true));
	   }
	   
	   @Test
	   // Test isPair method
	   public void testHandIsPair() {	 
		  
		   // Hand 1, not a pair
		   Card c1 = new Card(1,11); // Jack of Clubs
		   Card c2 = new Card(3,8); // 8 of Hearts		   
		   Hand hand = new Hand();
		   hand.addCard(c1);
		   hand.addCard(c2);
		   int Pair1 = hand.isPair(); 
		   
		   // Hand 2, is a pair
		   Card c3 = new Card(1,8); // 8 of Clubs
		   Card c4 = new Card(3,8); // 8 of Hearts	   
		   Hand hand2 = new Hand();
		   hand2.addCard(c3);
		   hand2.addCard(c4);
		   int Pair2 = hand2.isPair(); 
		   
		   // Hand 3, pair with face cards
		   Card c5 = new Card(1,11); // Jack of Clubs
		   Card c6 = new Card(3,13); // King of Hearts	   
		   Hand hand3 = new Hand();
		   hand3.addCard(c5);
		   hand3.addCard(c6);
		   int Pair3 = hand3.isPair(); 
		   
		   // Hand 4, contains pair but >2 cards
		   Card c7 = new Card(1,11); // Jack of Clubs
		   Card c8 = new Card(3,8); // 8 of Hearts
		   Card c9 = new Card(4,8); // 8 of Spades		   
		   Hand hand4 = new Hand();
		   hand4.addCard(c7);
		   hand4.addCard(c8);
		   hand4.addCard(c9);
		   int Pair4 = hand4.isPair(); 
		      
	      assertTrue((Pair1==0)&&(Pair2==8)&&(Pair3==10)&&(Pair4==0));
	   }
	 
	  
	// BLACKJACK PLAYER CLASS TESTS

	   
	   @Test
	   // Test that hasBlackjack method correctly determines whether player was dealt blackjack
	   public void testBlackjackPlayerHasBlackjack() {	 
		   
		   // Player 1: Dealt Blackjack
		   BlackjackPlayer p1 = new BlackjackPlayer();
		   p1.takeCard(new Card(3,11)); //Jack
		   p1.takeCard(new Card(4,1)); //Ace
		   
		   // Player 2: Not Dealt Blackjack
		   BlackjackPlayer p2 = new BlackjackPlayer();
		   p2.takeCard(new Card(3,11)); //Jack
		   p2.takeCard(new Card(4,13)); //King
		   
		   
		   assertTrue((p1.hasBlackjack()==true)&&(p2.hasBlackjack()==false));
	   }
	   
	   @Test
	   // Test that splitHand method results in correct number of hands
	   public void testBlackjackPlayerSplitHand() {	 
		   
		   // Player 1: Dealt Pair
		   BlackjackPlayer p1 = new BlackjackPlayer();
		   p1.takeCard(new Card(3,11)); //Jack
		   p1.takeCard(new Card(4,13)); //King
		   
		   // Split hand, deal additional card to each
		   p1.splitHand(new Card(1,7), new Card(2,9));
		   
		   assertEquals(p1.numHands(),2);
	   }
	   
	   @Test
	   // Test that splitHand method results in correct number of hands
	   // This time, dealt multiple pairs and split to 4 hands
	   public void testBlackjackPlayerSplitHand2() {	 
		   
		   // Player 1: Dealt Pair
		   BlackjackPlayer p1 = new BlackjackPlayer();
		   p1.takeCard(new Card(3,9)); //9
		   p1.takeCard(new Card(4,9)); //9
		   
		   // Split hand, deal additional card to each
		   p1.splitHand(new Card(1,9), new Card(2,9));
		   
		   // Split each of these hands into 2 more, for 4 total
		   p1.splitHand(new Card(2,6), new Card(3,7));
		   p1.nextHand();
		   p1.splitHand(new Card(2,2), new Card(3,3));
		   
		   assertEquals(p1.numHands(),4);
	   } 
	   
}

