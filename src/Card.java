
public class Card {
	// Represents a single playing card
	// Each card has attributes, Suit (1-4 inclusive) and Rank (1-13 inclusive)
	private int suit;
	private int rank;
	
	// Constructor, sets suit and rank if they are in acceptable bounds
	public Card(int card_suit, int card_rank) {
		if (card_suit < 1 || card_suit > 4 || card_rank < 1 || card_rank > 13) {
			return;
		}
		suit = card_suit;
		rank = card_rank;
	}
	
	// Method to return suit
	public int getSuit() {
		return suit;	
	}
	
	// Method to return rank
	public int getRank() {
		return rank;	
	}
	
	// Method to print suit and rank as text output	
	public void printCard() {
		String[] suits = {"NULL", "Clubs", "Diamonds","Hearts","Spades"};
		String[] values = {"NULL", "Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King"};
		System.out.println(values[rank] + " of " + suits[suit]);
	}		
}
