# Blackjack Simulator

### Summary

The purpose of this project was to develop a simulation of a blackjack game that could be used to evaluate statistical metrics regarding the odds/edge of the game for a given set of rules. This simulation can evaluate the individual and/or cumulative outcomes of large numbers of games to draw conclusions on strategy choices and expected payouts. We begin by examining a player playing with optimal strategy, and then evaluate the efficacy of a simple card counting strategy. 

The simulation was developed with some of the following considerations:
1. Atlantic City Rules - Casino blackjack rules tend to vary across the world, but Atlantic City casinos have some of the most favorable rules for the players. Thus, a similar set of rules was selected to generate the data shown below. Additionally, this rule set has been well researched, so optimal strategies are well documented, and the model can be validated against other calculations.
2. Flexibility - The user should be able to tweak elements of the rules, game setup, or strategy to assess the impact on expected payout.
3. Scalability - We must be able to easily perform large series of simulations, as we determine the casino/player edge by analyzing the average payouts of millions of simulated games.

The simulation was implemented with the following basic rules (similar to many Atlantic City casinos):
* Blackjack payout is 3:2
* Player can split pairs (up to a total of 4 hands)
* Player can double down on any hand of 2 (including after a split)
* Aces can be split only once, and only 1 additional card dealt to each hand

As for the number of decks and frequency of reshuffling, a total of six decks were used and they were reshuffled whenever 75% of the cards had been dealt. The deck penetration (percentage of the deck dealt before reshuffling) is a very important metric for card counters, and a higher number is more desirable.

Prior to even considering the possibility of counting cards, the "player" is designed to play with optimal strategy, choosing the statistically best move, given the information available (their cards and the dealer's visible card) and the legal moves (dictated by the rule set). A large batch of games was performed with the selected rules and optimal strategy, and the average return was evaluated until convergence. This configuration yielded a house edge of **0.43%**

![Expected Return Baseline](/Plots/Expected_Return_Baseline.png)

This house edge of **0.43%** is about as favorable as a player would be able to find in a casino, and many casinos in fact are much less advantageous to the player. Casinos can increase their house edge by decreasing the Blackjack payout, limiting a player's options in regards to splits and doubles, and altering the deck size and shuffle frequency, among other rule changes. But, for the purpose of this exploration, we will stick with the Atlantic City inspired rule set defined above. If a player is playing optimally given the information available to them (their cards and the dealer's card), they can expect to see this very small house edge of **0.43%**. Thus, even a very small increase in performance should push the player over the threshold from losing money (on average), to making money. Let's start counting cards!

The card counting strategy employed here is a very simple one, and works as follows: the player observes all cards on the table as they are dealt or become visible, noting in particular high cards (10s, face cards, and aces) and low cards (2 - 6). When more low cards than high cards have been observed, the player can reason that high cards are more likely to be drawn in the coming hands, and vice versa. A higher concentration of high cards remaining in the deck equates to slightly better odds for the player (due largely to the increased likelihood of the dealer busting), and the player should increase their bet accordingly in ths scenario.

The "count" of the deck is tabulated as follows: every low card observed equates to +1 on the "total count" of the deck, and every high card equates to a -1. The total count is then divided by the number of decks remaining in the shoe in order to approximate the "true count." When the true count is high (i.e. greater than ~4), this represents a distinct advantage to the player. A player who is counting cards would likely seek to increase their bet substantially when this is the case, and keep a minimum bet (or even sit out) when the count goes very low. The true count tends to be much more indicative towards the end of a deck, before dropping back to 0 after reshuffling.

For the data generated below, the following methodology was used: minimum bet is applied in all cases where true count is less than 2. When the true count exceeds 2, the bet is doubled; when the true count exceeds 3, the bet is quadrupled; and when the true count exceeds 4, a bet of 8 times the original is applied. A larger bet spread could indeed improve the expected returns but we must consider the limitations of a casino's maximum bet, as well as the attention drawn from the casino staff, who are always on the lookout for card counters.

That said, with this simple model where the player keeps track of the true count and adjusts their bet accordingly (up to 8x the minimum), the expected return jumps notably to **2.05%** (negative 2% house edge for the casino).

![Expected Return Counting](/Plots/Expected_Return_Counting.png)

These expected return plots serve to show the merit of a simple card counting strategy on top of already optimized play, and how, in the long run, a player can reasonably expect to make money against the casino. An important consideration however, is the large variability in the gameplay and returns, which we will dive deeper into later in the statistical analysis section.

### Project Structure

This section serves as a quick overview of the structure of the project, the interaction of the classes and methods, and some key design choices that were made along the way. The majority of the project, including the design of the game and the batched simulations, were implemented in Java with an object oriented approach, whereas the data visualization portion was done with Python.

**The src/ folder in the project structure contains all java classes used to build the simulation:**

**Card.java** - Models a single playing card, has attributes suit and rank. Contains methods to retrieve suit and rank, and to print card value as text.

**Deck.java** - Models deck of cards, contains many card objects. Could be single deck or many, representative of casino "shoe" games. Basic structure is modeled as linked list, where cards are popped off the top as they are dealt. Includes methods to shuffle deck, deal a card (returning card object), and return metrics about cards dealt.

**Hand.java** - Models a "hand" of cards. Contains multiple card objects and the methods to interact with them. The dealer will have only a single hand, and thus can be modeled succinctly with a simple hand object, although since a player can have multiple hands through splits, we will later introduce the BlackjackPlayer class which offers increased functionality. Includes methods to add card to hand, calculate value, determine if hand is "soft" (i.e. hand contains Ace that is counting as 11), determine if hand has busted (gone over 21), check for pairs, and split itself into two hands.

**BlackjackPlayer.java** - Models our main player in the blackjack game. This class wraps an additional layer of functionality around the Hand class, allowing a player to have up to the allowed 4 hands (depending on pairs and splitting), and providing mechanisms to perform moves and scoring. The BlackjackPlayer object consists of a list of hands initialized at size 1 and increasing as necessary, and maintains an "active" index to determine which hand to interact with. Includes methods to manipulate hands (add card, perform splits, etc), method to perform scoring and payout calculation relative to the dealer's hand, and method to determine bet size based on true count (when counting cards).

**Player.java** - Player class contains static methods that hold logic of gameplay strategies. The playTurn() method contains an optimal strategy for the defined rule set, given the player's hand(s) and the dealer's visible card. It returns one of the following moves: Stand, Hit, Double or Split. This class also contains a simpler method to handle the dealer's moves. As is the rule of the game, the dealer must hit on any total less than 17, and stand at 17 or above.

**Table.java** - Table class represents a blackjack table, at which one or many games can be played. When playing multiple games, cards are dealt from the same deck and the deck is reshuffed as needed. The Table is initialized with the desired game parameters and includes methods to start a new game, allow players and dealer to select their moves and bets, and perform scoring, determining player's payout for the game.
	
**Simulation.java** - Simulation class allows large batches of blackjack games to be performed in order to collect statistical metrics. The different methods allow for a variety of batch types to be performed (ex, large series of single games, series of sessions of certain number of games, etc.) These methods perform the series of games as specified, and write the output data to text files that are later used for plotting and analysis.

**UnitTests.java** - Unit tests for methods of Card, Deck, Hand, and BlackjackPlayer classes.


**Generate_Plots.py** - Python script using numpy and matplotlib to analyze and plot data from large output files. This script was used to generate all plots shown in this document.


### Statistical Analysis

This section serves to explore some further detail on the results of the simulations. In the summary section, we looked at the expected payout for the baseline optimal strategy case and the card counting case, as it converged over millions of iterations. To make this analysis more practical however, it is important to look at more reasonable numbers of games and consider the expected returns and variability. For this purpose, we will consider a "session" of 300 games, which is approximately the number of games a player might expect to complete when sitting down at a blackjack table for 4 hours. 20000 iterations of 300 game "sessions" were performed for both the non-counting and counting cases to evaluate the returns in a more practical context.


The following cases are considered over the course 

* Baseline: This is the simple case where the player employs optimal strategy but does not count cards. The bet is held constant at $1 (although this amount is arbitrary and the results could be scaled to represent any bet size).

* Baseline with Double Bet: Same as baseline case, but bet is now $2 for the purpose of better matching the distribution of the card counting case.

* Card Counting Case: Optimal strategy, bet varies from 1-8 depending on the true count of the deck.

The figures below show the distributions of the average payout of these 300 hand sessions. The means are as we would expect, the same as the ones calculated above, but the standard deviations should be noted. Even though in the card counting case we can expect an average payout per hand of +2% of the bet, the standard deviation of 15.5% expresses extreme variability, which should be of note to the player.

|Baseline|Baseline with Double Bet|Counting|
|--------|------------------------|--------|
|![Hist Session Baseline](/Plots/Histogram_Session_Baseline.png)|![Hist Session Baseline Double](/Plots/Histogram_Session_Baseline_Double_Bet.png)|![Hist Session Counting](/Plots/Histogram_Session_Counting.png)|

How does this variability propagate through a session at the blackjack table? The previous figures examined the average return of the session, but the following ones will look at the cumulative return. The figures on the left show the total payout as the number of games increases, in this case for 2000 iterations of the 300 game session (# iterations decreased to make the plots easier to observe). The figures on the right show the distribution of total payouts at the end of each session. Again, the standard bet amounts are arbitrary (and lower than most casino minimums) and can be scaled as necessary. Similarly, the takeaway of these plots should be the large variability observed in all cases, even when the card counting drives the expected return positive.


| Monte Carlo Plots - Total Payout vs # Games	| Histograms - Total Payout at end of session					 |
|--------------------------------------------------------|-----------------------------------------------------------------------|
|![Monte Carlo Baseline](/Plots/Monte_Carlo_Baseline.png)|![Monte Carlo Baseline Hist](/Plots/Monte_Carlo_Baseline_Histogram.png)|
|![Monte Carlo Baseline_Double](/Plots/Monte_Carlo_Baseline_Double_Bet.png)|![Monte Carlo Baseline Double Hist](/Plots/Monte_Carlo_Baseline_Double_Bet_Histogram.png)|
|![Monte Carlo Counting](/Plots/Monte_Carlo_Counting.png)|![Monte Carlo Counting Hist](/Plots/Monte_Carlo_Counting_Histogram.png)|

Lastly, the following plots show some quick analysis on the deck penetration and the true counts observed. This first plot shows the true count as a function of the number of decks remaining in the shoe. Note how the magnitude of the true count rises sharply as the end of the deck is approached. For this reason, games with fewer decks are advantageous to the player, as well as games where a larger percent of the deck is dealt before a reshuffle, as both cases allow for more decisive information on true count to be available for a larger percentage of hands.

![True Count vs Decks Remaining](/Plots/True_Count_vs_Decks_Remaining.png)

Similarly, this last figure shows the distribution of true counts at a variety of slices for the 6 deck case. Note how as the percentage of cards dealt increases, the distribution widens and ultimately separates into two distinct nodes. As we approach the end of the deck, there is a much greater likelihood of a largely positive true count, which is very actionable information for the card counting player.

![True Count Distributions](/Plots/True_Count_Distributions.png)

