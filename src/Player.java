
public class Player {
	// This class contains methods that return best moves, given information and options availible to player
	

	// Static Method, containing strategy for optimal blackjack play
	// Takes following inputs:
		// Player's hand (active hand if there are multiple)
		// Dealer's card that is visible to player
		// Number of hands player currently has (to determine if split is allowed)
	// Returns int, corresponding to optimal move
		// 0: Stand
		// 1: Hit
		// 2: Double
		// 3: Split
	
	public static int playTurn(Hand my_hand, Card dealer_card, int numHands) {
	
		// Rank of visible dealer card
		int dealer_rank = dealer_card.getRank();
		// Current value of player hand:
		int value = my_hand.computeValue();
		
		// Check if hand is pair
		int pair = my_hand.isPair();

		// If hand is pair and player does not yet have max # of hands, split:
		if (pair > 0 && numHands < 4) {
			
			// Pair of aces. Can split but limitations apply	
			if (pair == 1) {
				if (dealer_rank != 1) {		
					// Cannot re-split, and 1 card only
					return 3; //split
				}
				else {
					return 1; //hit
				}
			}
			// Pair of 2s or 3s
			else if (pair == 2 || pair == 3) {
				if (dealer_rank == 1 || dealer_rank > 7) {		
					return 1; //hit
				}
				else {
					return 3; //split
				}
			}
			// Pair of 4s
			else if (pair == 4) {
				if (dealer_rank == 5 || dealer_rank == 6) {		
					return 3; //split
				}
				else {
					return 1; //hit
				}				
			}
			// Pair of 5s
			else if (pair == 5) {
				if (dealer_rank == 1 || dealer_rank > 9) {		
					return 1; //hit
				}
				else {
					return 2; //double
				}
			}
			// Pair of 6s
			else if (pair == 6) {
				if (dealer_rank == 1 || dealer_rank > 6) {		
					return 1; //hit
				}
				else {
					return 3; //split
				}		
			}
			// Pair of 7s
			else if (pair == 7) {
				if (dealer_rank == 1 || dealer_rank > 7) {		
					return 1; //hit
				}
				else {
					return 3; //split
				}	
			}
			// Pair of 8s
			else if (pair == 8) {
				return 3; //split
			}
			// Pair of 9s
			else if (pair == 9) {
				if (dealer_rank == 1 || dealer_rank == 7 || dealer_rank == 10) {		
					return 0; //stand
				}
				else {
					return 3; //split
				}	
			}
			//Pair of 10s
			else if (pair == 10) {
				//Stand
				return 0;
			}									
		}
		
		// Soft hand - Includes Ace Currently Counting as 11
		else if (my_hand.isSoftHand()) {
			// Compute total of other card(s) in hand
			int total_minus_ace = my_hand.computeValue()-11;
			
			if (total_minus_ace == 2 || total_minus_ace == 3) {	
				if ((dealer_rank == 5 || dealer_rank == 6) && (my_hand.sizeHand()==2)) {
					return 2; //double
				}
				else {
					return 1; //hit
				}
			}
			if (total_minus_ace == 4 || total_minus_ace == 5) {
				if ((dealer_rank > 3 && dealer_rank < 7)&& (my_hand.sizeHand()==2)) {			
					return 2; //double
				}
				else {
					return 1; //hit
				}
			}
			if (total_minus_ace == 6) {
				if ((dealer_rank > 1 && dealer_rank < 7)&& (my_hand.sizeHand()==2)) {						
					return 2; //double
				}
				else {
					return 1; //hit
				}
			}
			if (total_minus_ace == 7) {
				if ((dealer_rank > 1 && dealer_rank < 7)&& (my_hand.sizeHand()==2)) {			
					return 2; //double
				}
				else if (dealer_rank == 7 || dealer_rank == 8) {
					return 0; //stand
				}
				else {
					return 1; //hit
				}
			}
			if (total_minus_ace == 8 || total_minus_ace == 9) {
				return 0; //stand
			}	
		}
		// Regular hand, not pair or soft
		// Choose optimal move based on total and dealer card
		else {
			
			if (value < 9) {
				return 1; //hit
			}
			else if (value == 9) {	
				if ((dealer_rank > 2 && dealer_rank < 7)&& (my_hand.sizeHand()==2)) {					
					return 2; //double
				}
				else {
					return 1; //hit
				}
			}
			else if (value == 10) {
				if ((dealer_rank > 1 && dealer_rank < 10)&& (my_hand.sizeHand()==2)) {	
					return 2; //double
				}
				else {
					return 1; //hit
				}
			}
			else if (value == 11) {		
				if ((dealer_rank != 1)&& (my_hand.sizeHand()==2)) {			
					return 2; //double
				}
				else {
					return 1; //hit
				}
			}
			else if (value == 12) {
				if (dealer_rank > 3 && dealer_rank < 7) {		
					return 0; //stand
				}
				else {
					return 1; //hit
				}
			}
			else if (value < 17) {
				if (dealer_rank > 1 && dealer_rank < 7) {		
					return 0; //stand
				}
				else {
					return 1; //hit
				}
			}			
			else {
				return 0; //stand
			}	
		}
		return 0;
	}
	
	
	// Static Method, Dealer's Move/Play
	// Dealer Hits on totals below 17
	// Stands on totals of 17 or greater
	// Requires Dealer's Hand as input
	// Returns 0(stand) or 1(hit)
	public static int playTurnDealer(Hand dealer_hand) {
		// value of hand
		int value = dealer_hand.computeValue();
		if (value >=17) {
			return 0;
		}
		else {
			return 1;
		}
		
	}
}
