# Blackjack Simulator

### Summary

The purpose of project was to develop a simulation of a blackjack game that could be used to evaluate statistical metrics regarding the odds/edge of the game for a given set of rules. This simulation can evaluate the individual and/or cumulative outcomes of large numbers of games to draw conclusions on strategy choices and expected payouts. We begin by examining a player playing with optimal strategy, and then evaluate the efficacy of a simple card counting strategy. 

The simulation was developed with some of the following considerations:

Atlantic City Rules - Casino blackjack rules tend to vary across the world, but Atlantic City casinos have some of the most favorable rules for the players. Thus, a similar set of rules was selected to generate the data shown below. Additionally, this rule set has been well researched, so optimal strategies are well documented, and the model could be validated against other's clculations.

Flexibility is an important consideration, the user should be able to tweak elements of the rules, game setup, or strategy to assess the impact on expected payout.
Scalability is also an important consideration, as we determine the casino/player edge over via the averge payouts of millions of simulated games

The simulation was implemented with the following basic rules (similar to many Atlantic City casinos)
Blackjack payout is 3:2
Player can split pairs (up to a total of 4 hands)
Player can Double Down on any hand of 2 (including after a split)
Aces can be split only once, and only 1 additional card dealt to each hand

As for the number of decks and frequency of reshuffling, a total of six decks were used and they were reshuffled whenever 75% of the cards had been dealt. The deck penatration (percentage of the deck dealt before reshuffling) is a very important metric for card counters, and a higher number is more desirable.

Prior to even considering the possibility of counting cards, the "player" is designed to play with optimal strategy, choosing the statistically best move, given the information available (their cards, and the dealer's visible card) and the legal moves (dictated by the rule set). A large batch of games was performed with the selected rules and optimal strategy, and the average return was evaluated until convergence. This configuration yielded a house edge of **0.43%**

![Expected Return Baseline](/Plots/Expected_Return_Baseline.png)

This is 

![Expected Return Counting](/Plots/Expected_Return_Counting.png)

### Project Structure

**src/ folder in the project structure contains all java classes used to build the simulation:**

**Card.java** - Models a single playing card, has attributes suit and rank. Contains methods to retrieve suit and rank, and to print card value as text.

**Deck.java** - Models deck of cards, contains many card objects. Could be single deck or many, representative of casino "shoe" games. Basic structure is modeled as linked list, where cards are popped off the top as they are dealt. Includes methods to shuffle deck, deal a card (returning card object), and return metrics about cards dealt.

**Hand.java** - Models a "hand" of cards. Contains multiple card objects and the methods to interact with them. The dealer will have only a single hand, and thus can be modeled succinctly with a simple hand object, although since a player can have multiple hands through splits, we will later introduce the BlackjackPlayer class which offers increased functionality. Includes methods to add card to hand, calculate value, determine if hand is "soft" (i.e. hand contains Ace that is counting as 11), determine if hand has busted (gone over 21), check for pairs, and split itself into two hands.

**BlackjackPlayer.java** - This class is used to model our main player in the blackjack game. This class wraps an additional layer of functionality around the Hand class, allowing a player to have up to the alowed 4 hands(depending on pairs and splitting), and providing mechanisms to perform moves and scoring. The BlackjackPlayer objects consists of a list of initialized at size 1 and increasing as necessary, and maintains an "active" index to determine which hand to interact with. Includes methods to manipulate hands (add card, perform splits, etc), method to perform scoring and payout calculation relative to the dealer's hand, and method to determine bet size based on true count (when counting cards).

**Player.java** - Player class contains static methods that hold logic of gameplay strategies. The playTurn() method contains an optimal strategy for the defined rule set, given the player's hand(s) and the dealer's visible card. It returns one of the following moves: Stand, Hit, Double or Split. This class also contains a simpler method to handle the dealer's moves. As is the rule of the game, the dealer must hit on any total less than 17, and stand at 17 or above.

**Table.java** - Table class represents a blackjack table, at which one or many games can be played. When playing multiple games, cards are dealt from the same deck and the deck is reshuffed as needed. The Table is initialized with the desired game parameters and includes methods to start a new game, allow players and dealer to select their moves and bets, and perform scoring, determining player's payout for the game.
	
**Simulation.java** - Simulation class allows large batches of blackjack games to be performed in order to collect statistical metrics. The different methods allow for a variety of batch types to be performed (ex, large series of single games, series of sessions of certain number of games, etc.) These methods perform the series of games as specified, and write the output data to text files that are later used for plotting and analysis.

**UnitTests.java** - Unit tests for methods of Card, Deck, Hand and BlackjackPlayer classes.


**Generate_Plots.py** - Python script using numpy and matplotlib to analyze and plot data from large output files. This script was used to generate all plots shown in this document



Testing

### Statistical Analysis

![Hist Session Baseline](/Plots/Histogram_Session_Baseline.png)

![Hist Session Baseline Double](/Plots/Histogram_Session_Baseline_Double_Bet.png)

![Hist Session Counting](/Plots/Histogram_Session_Counting.png)

| Monte Carlo						 | Histogram								 |
|--------------------------------------------------------|-----------------------------------------------------------------------|
|![Monte Carlo Baseline](/Plots/Monte_Carlo_Baseline.png)|![Monte Carlo Baseline Hist](/Plots/Monte_Carlo_Baseline_Histogram.png)|

![Monte Carlo Baseline_Double](/Plots/Monte_Carlo_Baseline_Double_Bet.png)

![Monte Carlo Baseline Double Hist](/Plots/Monte_Carlo_Baseline_Double_Bet_Histogram.png)

![Monte Carlo Counting](/Plots/Monte_Carlo_Counting.png)

![Monte Carlo Counting Hist](/Plots/Monte_Carlo_Counting_Histogram.png)



![True Count Distributions](/Plots/True_Count_Distributions.png)

![True Count vs Decks Remaining](/Plots/True_Count_vs_Decks_Remaining.png)
