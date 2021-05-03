import java.util.ArrayList;

public class Hand {
	// Class to represent hand of cards
	// Contains number of card objects and methods to interact with them
	
	// Hand represented as linked list
	private ArrayList<Card> cards = new ArrayList<Card>();
	// Flag true if player has doubled hand
	private boolean isDouble = false;

	
	// Method Add Card to Hand
	public void addCard(Card c) {
		cards.add(c);
	}
	
	// Method to Calculate Value of Hand
	public int computeValue() {
		
		int total_count = 0;
		Card cur_card;
		int value;
		int rank;
		int ace_count = 0;
		
		// For each card in hand
		for (int i = 0; i < cards.size(); i++) {
			
			// Get rank of card
			cur_card = cards.get(i);
			rank = cur_card.getRank();
		
			// For numbered cards 2-10, value is same as rank
			if (rank > 1 && rank <= 10) {
				value = rank;
			}
			
			// All face cards have value of 10
			else if (rank > 10) {
				value = 10;
			}
			
			// Ace value is 1 or 11, depending on total count
			else {
				value = 1;
				ace_count++;
			}
			// Add card's value to total value of hand
			total_count+=value;	
		}
		// Now consider scoring/value of aces
		if (ace_count > 0) {
			for (int i = 0; i <= ace_count; i++) {
				if (total_count<=11) {
					total_count+=10;
				}
			}	
		}
		
		return total_count;
	}
	
	// Method to determine if hand is a "soft hand"
	// Is an ace currently counting as 11?
	public boolean isSoftHand() {
		boolean soft_flag = false;
		int total_count = 0;
		Card cur_card;
		int value;
		int rank;
		int ace_count = 0;
		
		// For each card in hand
		for (int i = 0; i < cards.size(); i++) {
			
			// Get rank of card
			cur_card = cards.get(i);
			rank = cur_card.getRank();
		
			// For numbered cards 2-10, value is same as rank
			if (rank > 1 && rank <= 10) {
				value = rank;
			}
			
			// All face cards have value of 10
			else if (rank > 10) {
				value = 10;
			}
			
			// Ace value is 1 or 11, depending on total count
			else {
				value = 1;
				ace_count++;
			}
			// Add card's value to total value of hand	
			total_count+=value;
			
		}
		// Now consider scoring/value of aces
		if (ace_count > 0) {
			for (int i = 0; i < ace_count; i++) {
				if (total_count<=11) {
					total_count+=10;
					soft_flag = true;
				}
			}
		}		
		return soft_flag;	
	}
	
	// Method returns true if hand has "busted" (gone above value of 21)
	public boolean isBust() {
		if (this.computeValue()>21) {
			return true;
		}
		else {
			return false;
		}
	}
	
	// Method to "split" blackjack hand
	// Player has option to split when hand contains a pair
	public Card[] splitHand() {
		Card[] return_array = {cards.get(0),cards.get(1)};
		return return_array;
	}

	// Method sets makeDouble flag to true
	public void makeDouble() {
		isDouble = true;
	}
	
	// Method checks value of makeDouble flag
	public boolean isDouble() {
		if (isDouble) {
			return true;
		}
		else {
			return false;
		}
	}
	
	// Method returns size of hand
	public int sizeHand() {
		return cards.size();
	}
	
	// Method checks if hand is a pair (and is eligible for split)
	// returns value of pair if pair exists, returns 0 otherwise
	public int isPair() {
		// Confirm size is 2
		if (cards.size() == 2) {
			// Get value of each card
			int value1 = cards.get(0).getRank();
			int value2 = cards.get(1).getRank();
			// Check if equal
			if ((value1 == value2)||(value1>9 && value2>9)) {
				// Return value
				if (value1>9) {
					//All face cards have value 10, return 10
					return 10;
				}
				else {
					return value1;
				}
			}		
		}
		// Not a pair, return 0
		return 0;
	}
	
	// Method to print out cards in hand as text output
	public void printHand() {
		for (int i = 0; i< cards.size(); i++) {
			cards.get(i).printCard();
		}
	}
}