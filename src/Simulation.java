import java.io.FileWriter;
import java.io.IOException;

public class Simulation {
	// Simulation class allows large batches of blackjack games to be performed in order to collect statistical metrics
	// The static methods allow for a variety of different batch types to be performed
	// Main method contains the specific inputs and method calls used to generate plots and analytics
	
	
	
	// SIMPLE BATCH
	// Simulate Large Number of Single Blackjack Games
	// Return average/expected payout as sample size grows, write output to text file
	public static void simple_batch(int numDecks, double deckPen, boolean counting, int iterations, String filepath) throws IOException {
	
		// Bookkeeping vars
		double sum = 0;
		int total = 0;
		double payout;
		
		// New Table
		Table game = new Table(numDecks,deckPen,counting);
		
		FileWriter writer = new FileWriter(filepath);
		
		// Loop through number of games/iterations
		for (int i = 0; i< iterations; i++) {
			// New game
			game.startNewGame();
			// Assess player moves
			game.playerMoves();
			// Get payout
			payout = game.playerScoring();

			sum+=payout;
			total++;

			//Write average payout to file
			writer.write(Double.toString(sum/total));
			writer.write(";\n");
			
		}
		
		// Print final value at end
		System.out.print("Average payout: ");
		System.out.println(sum/total);
		
		writer.close();
	}
	
	// SESSION BATCH
	// Simulate a series of sessions
	// Ex. A Blackjack Player at a busy table may expect to play ~300 hands in a 4 hour session
	// Simulate large number of these sessions, returning average/expected payout per each session
	// Write output to text file
	public static void session_batch(int numDecks, double deckPen, boolean counting, int iterations, int session_size, String filepath) throws IOException {
		
		FileWriter writer = new FileWriter(filepath);
		
		// Bookkeeping vars
		double sum;
		int total;
		double payout;
		
		// New Table
		Table game;
		
		// Loop through number of games/iterations
		for (int j = 0; j< iterations; j++) {
		
			sum = 0;
			total = 0;
			game = new Table(numDecks,deckPen,counting);
		
			for (int i = 0; i< session_size; i++) {
				// New game
				game.startNewGame();
				// Assess player moves
				game.playerMoves();
				// Get payout
				payout = game.playerScoring();

				sum+=payout;
				total++;						
			}

			//Write average payout to file
			writer.write(Double.toString(sum/total));
			writer.write(";\n");
		}
		
		writer.close();
		System.out.println("done");
		
	}
		
	// Monte Carlo Plots
	// Total Return after session
	// Performed for series of sessions, output written to text file
	public static void monte_carlo_session_batch(int numDecks, double deckPen, boolean counting, int iterations, int session_size, String filepath) throws IOException {
		
		FileWriter writer = new FileWriter(filepath);
		
		// Bookkeeping vars
		double sum;
		double payout;
		
		// New table
		Table game;
		
		// Loop through number of games/iterations
		for (int j = 0; j< iterations; j++) {
		
			sum = 0;
			game = new Table(numDecks,deckPen,counting);
		
			for (int i = 0; i< session_size; i++) {
				// New game
				game.startNewGame();
				// Assess player moves
				game.playerMoves();
				// Get payout
				payout = game.playerScoring();

				sum+=payout;
				
				writer.write(Double.toString(sum));
				if (i<session_size-1) {
				writer.write(";");
				}		
			}

			writer.write("\n");
		}
		
		writer.close();
		System.out.println("done");
		
	}
	
	
	// Total count vs card deck penetration
	// Monte Carlo plots - X num  of simulations
	// Independent of Game
	public static void deck_penetration(int numDecks, int iterations, String filepath) throws IOException {
		
		FileWriter writer = new FileWriter(filepath);

		Deck card_deck;
		
		// For each iteration
		for (int j = 0; j< iterations; j++) {
		
			// New card deck, and shuffle
			card_deck = new Deck(numDecks);
			card_deck.shuffleDeck();
		
			// While cards remain in deck...
			while (card_deck.deckSize()>0) {
				
				// deal new card
				card_deck.dealCard();
				
				// write true count to file
				writer.write(Double.toString(card_deck.trueCount()));
				
				if (card_deck.deckSize()>0) {
				writer.write(";");
				}		
			}
			writer.write("\n");
		}
		
		writer.close();		
	}
	
	
	// Main Method
	public static void main(String[] args) throws IOException {
		
		// Define variables		
		int numDecks = 6;
		double deckPen = .75;
		int session_size = 300;
		boolean counting;
		int iterations;
		String filepath;
		
		
		// Perform batch of games, optimal strategy but no card counting
		counting = false;
		iterations = 5000000;
		filepath = "/home/jordan/Blackjack/baseline_5000000.txt";
		simple_batch(numDecks, deckPen, counting, iterations, filepath);
		

		// Perform batch of games, optimal strategy with card counting
		counting = true;
		iterations = 5000000;
		filepath = "/home/jordan/Blackjack/counting_5000000.txt";
		simple_batch(numDecks, deckPen, counting, iterations, filepath);
		
		// Perform batch of ~4 hour "sessions" - optimal strategy, no card counting
		// Assessing expected return per hand
		counting = false;
		iterations = 20000;
		filepath = "/home/jordan/Blackjack/baseline_session_300x20000.txt";
		session_batch(numDecks, deckPen, counting, iterations, session_size, filepath);
		
		//Perform batch of ~4 hour "sessions" - optimal strategy, with card counting
		// Assessing expected return per hand
		counting = true;
		iterations = 20000;
		filepath = "/home/jordan/Blackjack/counting_session_300x20000.txt";
		session_batch(numDecks, deckPen, counting, iterations, session_size, filepath);
		
		// Perform batch of ~4 hour "sessions" - optimal strategy, no card counting
		// Assessing total return of session
		counting = false;
		iterations = 2000;
		filepath = "/home/jordan/Blackjack/monte_carlo_session_baseline.txt";
		monte_carlo_session_batch(numDecks, deckPen, counting, iterations, session_size, filepath);
		
		//Perform batch of ~4 hour "sessions" - optimal strategy, with card counting
		// Assessing total return of session
		counting = true;
		iterations = 2000;
		filepath = "/home/jordan/Blackjack/monte_carlo_session_counting.txt";
		monte_carlo_session_batch(numDecks, deckPen, counting, iterations, session_size, filepath);
		
		// Assess "total count" vs percentage of deck used/remaining, run batch of x iterations
		iterations = 5000;
		filepath = "/home/jordan/Blackjack/deck_penetration_percentage.txt";
		deck_penetration(numDecks, iterations, filepath);
			
		
	}
	
	
	
	
}
