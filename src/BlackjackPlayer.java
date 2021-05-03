import java.util.ArrayList;

public class BlackjackPlayer {
	// This class represents player in Blackjack game
	// Each player can have up to 4 hands in a game, depending on pairs and splitting
	// This class handles moves and scoring
	
	// List of Hands
	private ArrayList<Hand> hands = new ArrayList<Hand>();
	private int numHands = 0;
	// Which hand is "active" for play and score methods
	private int active_hand = 0;

	private double bet = 1;
	
	// Constructor - Initialize new player with one hand
	public BlackjackPlayer() {
		hands.add(new Hand());
		numHands++;
	}

	// Method to Take Card
	// Receives card, adds to active hand
	public void takeCard(Card c) {
		hands.get(active_hand).addCard(c);
	}
	
	// Method to determine if player Has Blackjack
	public boolean hasBlackjack() {
		// Single hand, 2 cards, value = 21
		if (numHands == 1 && hands.get(0).sizeHand() == 2 && hands.get(0).computeValue() == 21) {
			return true;
		}
		else {
			return false;
		}
	}
	
	// Method to Return Number of Hands that player currently has
		public int numHands() {
			return numHands;
		}
	
	// Method to Split Hand of Pairs into two hands
	public boolean splitHand(Card c1, Card c2) {
		// Break into two hands
		Card[] split_hand = hands.get(active_hand).splitHand();
	
	
		// Add new hand to player object, cards from original hand split
		hands.set(active_hand, new Hand());	
		hands.add(new Hand());
		int index2 = hands.size()-1;
		hands.get(active_hand).addCard(split_hand[0]);
		hands.get(index2).addCard(split_hand[1]);
	

		//deal additional card to each hand, post split
		hands.get(active_hand).addCard(c1);
		hands.get(index2).addCard(c2);
		
		//increment counter
		numHands++;
		
		// Return true if split hand was pair of aces
		if (split_hand[0].getRank() == 1) {
			return true;
		}
		else {
			return false;
		}
		
	}
	
	// Method to change "active" hand for play/scoring
	// Increase active hand index
	// Return true if there is another hand to play, false if not
	public boolean nextHand() {
		active_hand++;
		if (active_hand < hands.size()) {
			return true;
		}
		else {
			return false;
		}
	}
	
	
	// Method to Play turn
	public int getMove(Card Visible_card) {
		// Call method from player class, provide active hand and visible dealer card
		int move = Player.playTurn(hands.get(active_hand), Visible_card, numHands);
		return move;
	}
	
	// Method sets Double flag for active hand
	public void makeDouble() {
		hands.get(active_hand).makeDouble();
	}
	
	public void setBet(double trueCount) {
		//Set appropriate bet amount based on true count
		if (trueCount > 4) {
			bet = 8;
		}
		else if (trueCount > 3) {
			bet = 4;
		}
		else if (trueCount > 2) {
			bet = 2;
		}
		else {
			bet = 1;
		}
	}
	
	public double getBet() {
		return bet;
	}
	
	// Method performs Scoring of players hands, in relation to dealer's hand
	// Returns Payout
	public double scoreHand(Hand dealer_hand) {
		
		double payout = 0;
		boolean dealer_blackjack = false;
		if (dealer_hand.sizeHand() == 2 && dealer_hand.computeValue() == 21) {
			dealer_blackjack = true;
			
		}
		// Player has blackjack
		if (this.hasBlackjack() && !dealer_blackjack) {
			payout = 1.5;
		}
		// Blackjack Push - Both player and dealer
		else if (this.hasBlackjack() && dealer_blackjack) {
			payout = 0;
		} 
		// Dealer has blackjack
		else if (!this.hasBlackjack() && dealer_blackjack) {
			payout = -1;
		}
		else {
			

			double mult;
			int hand_value;
			int dealer_value = dealer_hand.computeValue();
			
			// Cycle through each of player's hands
			for (int i = 0; i < numHands;i++) {
				
				// Get value of hand
				hand_value = hands.get(i).computeValue();
				
				// Get Multiplier (x2 Multiplier if this hand was doubled)
				if (hands.get(i).isDouble()) {
					mult = 2;
				}
				else {
					mult = 1;
				}
				
				
				// Player Bust
				if (hand_value > 21) {
					payout -= mult;
				}
				// Dealer Bust
				else if (dealer_value > 21) {
					payout += mult;
				}
				// Dealer hand greater
				else if (hand_value < dealer_value) {
					payout -= mult;
				}
				// Player hand greater
				else if (hand_value > dealer_value) {
					payout +=mult;	
				}
				// Else, Hand totals equal (push, no payout)
			}	
		}
		//return payout;
		//System.out.println(bet);
		return payout*bet;
	}
}
