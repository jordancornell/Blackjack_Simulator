
public class Table {
	// Table class represents a blackjack table
	// The intention is to initiate a table and simulate many games, dealing from the seme deck and reshuffling as needed
	
	// Constructor initializes table with desired game parameters
	// startNewGame() method initiates a single blackjack game at the table. 
	// playerMoves() method determines and implements the selected moves for all players at the table
	// playerScoring() method determines payout for the hand

	private Deck card_deck;
	private Hand dealer_hand;
	private BlackjackPlayer p1;
	private Hand[] otherPlayers;
	private Card Visible_card;
	private int numDecks;
	private double deckPenetration;
	private boolean counting;
	
	
	public Table(int numberDecks, double deckPen, boolean counting_set) {
		// Constructor - Create new table
		// numberDecks: How many decks?	
		// DeckPen: Penetration - what % of cards dealt before reshuffling
		// counting_set: boolean - true if card counting should be modeled in decision making
		counting = counting_set;
		deckPenetration = deckPen;
		numDecks = numberDecks;
		card_deck = new Deck(numDecks);
		card_deck.shuffleDeck();
		
	}
	
	public void startNewGame() {
		// Method to start new blackjack game
	
		//If percentage of card deck(s) used has reached the specified deck penetration threshold, time to reshuffle the deck(s)
		if (card_deck.deckSize() < 52*numDecks*(1-deckPenetration)) {
			card_deck = new Deck(numDecks);
			card_deck.shuffleDeck();
		}
		
		// Create Dealer's hand
		dealer_hand = new Hand();
		
		// Create Player
		p1 = new BlackjackPlayer();
		
		// Create new hand for every other player at table
		otherPlayers = new Hand[6];
		for (int i = 0; i < otherPlayers.length; i++) {
			otherPlayers[i] = new Hand();
		}
		
		
		//Initial Card Deal
		
		// 1st card to player
		p1.takeCard(card_deck.dealCard());
		
		// 1st card to all other players
		for (int i = 0; i < otherPlayers.length; i++) {
			otherPlayers[i].addCard(card_deck.dealCard());
		}
		
		// 1st card to dealer (this one face down)
		dealer_hand.addCard(card_deck.dealCard());
		
		// 2nd card to player
		p1.takeCard(card_deck.dealCard());
		
		//2nd card to all other players
		for (int i = 0; i < otherPlayers.length; i++) {
			otherPlayers[i].addCard(card_deck.dealCard());
		}
		
		// 2nd card to dealer (this one face up)
		Visible_card = card_deck.dealCard();
		dealer_hand.addCard(Visible_card);	
	}
	
	public void playerMoves() {
		// This method evaluates the sequences of moves selected by our player, the other players at the table, and the dealer
		
		//First check for dealer blackjack, if yes, hand over, proceed to scoring
		if (dealer_hand.sizeHand() == 2 && dealer_hand.computeValue() == 21) {
			return;
		}
		
		//Sequence of moves for Player
		
		//If player is counting cards:
		if (counting) {	
			// select bet amount based on true count
			p1.setBet(card_deck.trueCount());
		}
		// If player not counting cards, baseline bet is used
		
		
		// This loop cycles through all of the player's active hands (up to 4), selecting the optimal move
		int move = 99;
		// If player already has blackjack, skip
		if (!p1.hasBlackjack()) {
			
			while (move != 0) {
				boolean ace_flag = false;
				// Get optimal move, given player's hand and the dealer's visible card
				move = p1.getMove(Visible_card);
				// If selected move is 1 (hit), deal new card to hand
				if (move == 1) {
					p1.takeCard(card_deck.dealCard());
				}
				// If selected move is 2 (double), deal 1 additional card and end turn
				else if (move == 2) {
					p1.makeDouble();
					p1.takeCard(card_deck.dealCard());
					move = 0;
				}
				// If selected move is 3 (split), split hand method is called and a new card dealt to each hand
				else if (move == 3) {
					ace_flag = p1.splitHand(card_deck.dealCard(),card_deck.dealCard());
					if (ace_flag == true) {
					}
					// If aces were split, turn is over!
					if (ace_flag) {
						move = 0;
					}
					
				}
				
				if (move == 0) { //Breaking out of loop, check if more hands to play
					if (p1.nextHand()) {
						move = 99;
					}
					if (ace_flag) {
						move = 0;
					}
				}	
			}
		}
		
		// Other Player's moves
		// For now, following simple strategy (stand at 17 and above), same as dealer
		// Opportunity to make other players more elaborate in future
		for (int i = 0; i < otherPlayers.length; i++) {
					
			do {	
				move = Player.playTurnDealer(otherPlayers[i]);
				if (move == 1) {
					otherPlayers[i].addCard(card_deck.dealCard());
				}
			} while (move != 0 && !otherPlayers[i].isBust());	
		}
		

		// Dealer's Move
		do {	
			move = Player.playTurnDealer(dealer_hand);
			if (move == 1) {
				dealer_hand.addCard(card_deck.dealCard());
			}
		} while (move != 0 && !dealer_hand.isBust());	
	}
	
	// Method to perform scoring of player hands
	// Invokes BlackjackPlayer Class's scoring method
	public double playerScoring() {	
		double payout = p1.scoreHand(dealer_hand);		
		return payout;
	}
}
