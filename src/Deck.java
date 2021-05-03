import java.util.LinkedList;
import java.util.Collections;

public class Deck {
	// Represents a deck of cards, containing card objects
	// Can be a single deck or multiple, representative of "shoe" casino games
	
	// Deck represented as linked list
	private LinkedList<Card> deck = new LinkedList<>();
	// Current size of deck
	private int size = 0;
	// Running "total count" of all observed dealt cards (for card counting purposes)
	private int total_count = 0;
	
	// Constructor: Number of decks is specified by calling class
	public Deck(int numDecks) {
		// Loop through number of decks, suits, and rank
		for (int k = 0; k<numDecks; k++) {
			for (int i = 1; i <= 4; i++) {
				for (int j = 1; j <= 13; j++) {
					// Add Card object to linked list
					deck.add(new Card(i,j));
					size++;
				}
			}
		}
	}
	
	// Method to shuffle card deck
	public void shuffleDeck() {
		Collections.shuffle(deck);
	}
	
	// Method to deal single card
	public Card dealCard() {
		size--;
		//return deck.pop();
		Card card = deck.pop();
		int rank = card.getRank();
		
		// Adjust total count accordingly
		if ((rank >= 2) && (rank <= 6)) {
			total_count++;
		}
		else if ((rank >= 10) || (rank == 1)) {
			total_count--;
		}
		
		return card;
	}
	
	// Method to return true count
		public double trueCount() {
			
			return (double)total_count/((double)size/52);
		}
	
	
	// Method to return deck size
	public int deckSize() {
		return size;
	}
}
