# Blackjack Simulator

The purpose of project was to develop a simulation of a blackjack game that could be used to evaluate statistical metrics regarding the odds/edge of the game for a given set of rules. This simulation can evaluate the individual and/or cumulative outcomes of large numbers of games to draw conclusions on strategy choices and expected payouts. We begin by examining a player playing with optimal strategy, and then evaluate the efficacy of a simple card counting strategy. 

The simulation was developed with some of the following considerations:

Atlantic City Rules - Casino blackjack rules tend to vary across the world, but Atlantic City casinos have some of the most favorable rules for the players. Thus, a similar set of rules was selected to generate the data shown below. Additionally, this rule set has been well researched, so optimal strategies are well documented, and the model could be validated against other's clculations.

Flexibility
Scalability is an important consideration, as we determine the casino/player edge over via the averge payouts of millions of simulated games

The simulation was implemented with the following basic rules (similar to many Atlantic City casinos)
Blackjack payout is 3:2
Player can split pairs (up to a total of 4 hands)
Player can Double Down on any hand of 2 (including after a split)
Aces can be split only once, and only 1 additional card dealt to each hand

As for the number of decks and frequency of reshuffling, a total of six decks were used and they were reshuffled whenever 75% of the cards had been dealt. The deck penatration (percentage of the deck dealt before reshuffling) is a very important metric for card counters, and a higher number is more desirable.

Prior to even considering the possibility of counting cards, the "player" is designed to play with optimal strategy, choosing the statistically best move, given the information available (their cards, and the dealer's visible card) and the legal moves (dictated by the rule set). A large batch of games was performed with the selected rules and optimal strategy, and the average return was evaluated until convergence. This configuration yielded a house edge of **0.43%**



![Expected Return Baseline](/Plots/Expected_Return_Baseline.png)

![Expected Return Counting](/Plots/Expected_Return_Counting.png)



![Hist Session Baseline](/Plots/Histogram_Session_Baseline.png)

![Hist Session Baseline Double](/Plots/Histogram_Session_Baseline_Double_Bet.png)

![Hist Session Counting](/Plots/Histogram_Session_Counting.png)



![Monte Carlo Baseline](/Plots/Monte_Carlo_Baseline.png)

![Monte Carlo Baseline Hist](/Plots/Monte_Carlo_Baseline_Histogram.png)

![Monte Carlo Baseline_Double](/Plots/Monte_Carlo_Baseline_Double_Bet.png)

![Monte Carlo Baseline Double Hist](/Plots/Monte_Carlo_Baseline_Double_Bet_Histogram.png)

![Monte Carlo Counting](/Plots/Monte_Carlo_Counting.png)

![Monte Carlo Counting Hist](/Plots/Monte_Carlo_Counting_Histogram.png)



![True Count Distributions](/Plots/True_Count_Distributions.png)

![True Count vs Decks Remaining](/Plots/True_Count_vs_Decks_Remaining.png)
