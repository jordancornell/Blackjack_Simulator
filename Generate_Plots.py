import numpy as np
import matplotlib.pyplot as plt

# Python Script to visualize results of Blackjack Simulation Batches
# Reads data from text files written by Simulation class

##########################################################################################
######################### BATCH PLOTS ####################################################
# Show Average Payout Per Game as function of number of games played

# BASELINE PLOT
# No Counting, n = 5000000
input_data = np.fromfile("/home/jordan/Blackjack/baseline_5000000.txt",float,-1,";")
x = np.arange(0,len(input_data))/1000000
y=input_data*100 # multiply by 100 to convert to percent
plt.figure(1)
plt.title("Expected Return - No Card Counting")
plt.xlabel("Number of Iterations (Millions)")
plt.ylabel("Average Payout (Percent)")
plt.plot(x, y, color ="blue")
plt.xlim([0,5])
plt.ylim([-3,3])
plt.grid()

###########################################################################################
# COUNTING PLOT
# Player Implements basic card counting, n = 5000000
input_data = np.fromfile("/home/jordan/Blackjack/counting_5000000.txt",float,-1,";")
x = np.arange(0,len(input_data))/1000000
y=input_data*100 # multiply by 100 to convert to percent
plt.figure(2)
plt.title("Expected Return - Basic Card Counting")
plt.xlabel("Number of Iterations (Millions)")
plt.ylabel("Average Payout (Percent)")
plt.plot(x, y, color ="blue")
plt.xlim([0,5])
plt.ylim([-3,3])
plt.grid()

###########################################################################################
######################## SESSION PLOTS ###################################################
# Simulate number of 300 hand "sessions", evaluating average and total returns

# BASELINE PLOT
# Session of 300 hands, 20000 iterations, standard bet of $1
plt.figure(3)
arr = np.fromfile("/home/jordan/Blackjack/baseline_session_300x20000.txt",float,-1,";")
arr=arr*100 # multiply by 100 to convert to percent
plt.hist(arr,bins=30,edgecolor='white')
plt.title("Average Payout per Hand - 300 Hand Session - No Counting")
plt.xlabel("Average Payout (percent)")
plt.ylabel("Number of Occurrences")

# Add Mean and Standard Deviation
plt.axvline(np.mean(arr),color='black')
plt.axvline(np.mean(arr)+np.std(arr),color='black',linestyle='dashed')
plt.axvline(np.mean(arr)-np.std(arr),color='black',linestyle='dashed')
ymin, ymax = plt.ylim()

# Print Mean, Std values
plt.text(40*.5,ymax*.95, 'Mean = {:.3f}'.format(np.mean(arr)))
plt.text(40*.5,ymax*.9, 'Std = {:.3f}'.format(np.std(arr)))

plt.xlim([-40,40])

#############################################################################################
# BASELINE PLOT 2
# Session of 300 hands, 20000 iterations, standard bet doubled to $2

plt.figure(4)
arr = 2*np.fromfile("/home/jordan/Blackjack/baseline_session_300x20000.txt",float,-1,";")
arr=arr*100 # multiply by 100 to convert to percent
plt.hist(arr,bins=30,edgecolor='white')
plt.title("Average Payout per Hand - 300 Hand Session - No Counting")
plt.xlabel("Average Payout (percent)")
plt.ylabel("Number of Occurrences")

# Add Mean and Standard Deviation
plt.axvline(np.mean(arr),color='black')
plt.axvline(np.mean(arr)+np.std(arr),color='black',linestyle='dashed')
plt.axvline(np.mean(arr)-np.std(arr),color='black',linestyle='dashed')
ymin, ymax = plt.ylim()

# Print Mean, Std values
plt.text(40*.5,ymax*.95, 'Mean = {:.3f}'.format(np.mean(arr)))
plt.text(40*.5,ymax*.9, 'Std = {:.3f}'.format(np.std(arr)))

plt.xlim([-40,40])

##########################################################################################
# COUNTING PLOT
# Session of 300 hands, 20000 iterations, Player counts cards, varies bet accordingly

plt.figure(5)
arr = np.fromfile("/home/jordan/Blackjack/counting_session_300x20000.txt",float,-1,";")
arr=arr*100 # multiply by 100 to convert to percent
plt.hist(arr,bins=30,edgecolor='white')
plt.title("Average Payout per Hand - 300 Hand Session - With Counting")
plt.xlabel("Average Payout (percent)")
plt.ylabel("Number of Occurrences")

# Add Mean and Standard Deviation
plt.axvline(np.mean(arr),color='black')
plt.axvline(np.mean(arr)+np.std(arr),color='black',linestyle='dashed')
plt.axvline(np.mean(arr)-np.std(arr),color='black',linestyle='dashed')
ymin, ymax = plt.ylim()

# Print Mean, Std values
plt.text(40*.5,ymax*.95, 'Mean = {:.3f}'.format(np.mean(arr)))
plt.text(40*.5,ymax*.9, 'Std = {:.3f}'.format(np.std(arr)))

plt.xlim([-40,40])

##############################################################################################
################# MONTE CARLO SESSION PLOTS ##################################################
# Simulate number of 300 hand "sessions", evaluating totals

#BASELINE PLOT
# Session of 300 hands, 20000 iterations, standard bet of $1

arr = np.loadtxt("/home/jordan/Blackjack/monte_carlo_session_baseline.txt",delimiter=";")

plt.figure(6)
plt.title("Total Payout - 300 Hand Session - No Counting - $1 Bet")
plt.xlabel("Number of Hands Played")
plt.ylabel("Total Payout (dollars)")

# For each iteration...
for i in range(len(arr)):
    x = np.arange(0,len(arr[i]))
    y=arr[i]
    plt.plot(x, y)

###############################################################################################
# Histogram distribution of totals at end of sessions

totals_at_end = arr[:,len(arr[0])-1]
plt.figure(7)
plt.hist(totals_at_end,bins=20,edgecolor='white')
plt.title("Total Payout at End - 300 Hand Session - No Counting - $1 Bet")
plt.xlabel("Total Payout (Dollars)")
plt.ylabel("Number of Occurrences")

# Add Mean and Standard Deviation
plt.axvline(np.mean(totals_at_end),color='black')
plt.axvline(np.mean(totals_at_end)+np.std(totals_at_end),color='black',linestyle='dashed')
plt.axvline(np.mean(totals_at_end)-np.std(totals_at_end),color='black',linestyle='dashed')
ymin, ymax = plt.ylim()

# Print Mean and Std Values
plt.text(200*.5,ymax*.95, 'Mean($) = {:.2f}'.format(np.mean(totals_at_end)))
plt.text(200*.5,ymax*.9, 'Std($) = {:.2f}'.format(np.std(totals_at_end)))

plt.xlim([-200,200])

##################################################################################################
#BASELINE PLOT 2
# Session of 300 hands, 20000 iterations, standard bet doubled to $2

arr = 2*np.loadtxt("/home/jordan/Blackjack/monte_carlo_session_baseline.txt",delimiter=";")

plt.figure(8)
plt.title("Total Payout - 300 Hand Session - No Counting - $2 Bet")
plt.xlabel("Number of Hands Played")
plt.ylabel("Total Payout (dollars)")

# For each iteration...
for i in range(len(arr)):
    x = np.arange(0,len(arr[i]))
    y=arr[i]
    plt.plot(x, y)

###############################################################################################
# Histogram distribution of totals at end of sessions

totals_at_end = arr[:,len(arr[0])-1]
plt.figure(9)

plt.hist(totals_at_end,bins=20,edgecolor='white')
plt.title("Total Payout at End - 300 Hand Session - No Counting - $2 Bet")
plt.xlabel("Total Payout (Dollars)")
plt.ylabel("Number of Occurrences")

# Add Mean and Standard Deviation
plt.axvline(np.mean(totals_at_end),color='black')
plt.axvline(np.mean(totals_at_end)+np.std(totals_at_end),color='black',linestyle='dashed')
plt.axvline(np.mean(totals_at_end)-np.std(totals_at_end),color='black',linestyle='dashed')
ymin, ymax = plt.ylim()

# Print Mean and Std Values
plt.text(200*.5,ymax*.95, 'Mean($) = {:.2f}'.format(np.mean(totals_at_end)))
plt.text(200*.5,ymax*.9, 'Std($) = {:.2f}'.format(np.std(totals_at_end)))

plt.xlim([-200,200])

##################################################################################################
#COUNTING PLOT
# Session of 300 hands, 20000 iterations, player counts cards, varies bet accordingly

arr = np.loadtxt("/home/jordan/Blackjack/monte_carlo_session_counting.txt",delimiter=";")

plt.figure(10)
plt.title("Total Payout - 300 Hand Session - With Counting")
plt.xlabel("Number of Hands Played")
plt.ylabel("Total Payout (dollars)")

# For each iteration...
for i in range(len(arr)):
    x = np.arange(0,len(arr[i]))
    y=arr[i]
    plt.plot(x, y)

####################################################################################
# Histogram distribution of totals at end of sessions

totals_at_end = arr[:,len(arr[0])-1]
plt.figure(11)

plt.hist(totals_at_end,bins=20,edgecolor='white')
plt.title("Total Payout at End - 300 Hand Session - With Counting")
plt.xlabel("Total Payout (Dollars)")
plt.ylabel("Number of Occurrences")

# Add Mean and Standard Deviation
plt.axvline(np.mean(totals_at_end),color='black')
plt.axvline(np.mean(totals_at_end)+np.std(totals_at_end),color='black',linestyle='dashed')
plt.axvline(np.mean(totals_at_end)-np.std(totals_at_end),color='black',linestyle='dashed')
ymin, ymax = plt.ylim()

# Print Mean and Std Values
plt.text(200*.5,ymax*.95, 'Mean($) = {:.2f}'.format(np.mean(totals_at_end)))
plt.text(200*.5,ymax*.9, 'Std($) = {:.2f}'.format(np.std(totals_at_end)))

plt.xlim([-200,200])

####################################################################################################
#################### DECK PENETRATION / TRUE COUNT PLOTS ###########################################

# True Count Observed vs Percentage of Cards Dealt

arr = np.loadtxt("/home/jordan/Blackjack/deck_penetration_percentage.txt",delimiter=";")
plt.figure(12)
plt.title("True Count vs Cards Remaining")
plt.xlabel("Decks Remaining in Shoe")
plt.ylabel("True Count")

# For each iteration...
for i in range(len(arr)):
    x = np.arange(0,len(arr[i]))/52
    y=arr[i]
    plt.plot(x, y)

########################################################################################
# Histogram Distributions of True Counts at Various Deck Penetrations
# Contains 6 subplots showing data distributions at different slices

# Create and Title Figure
fig, ax = plt.subplots(2, 3)
fig.suptitle("True Count Distribution at Various Deck Penetrations")

# Slice #1
plt.subplot(2, 3, 1)
slice25 = arr[:,78]
plt.hist(slice25,bins=20,edgecolor='white')
plt.title("25% Cards Dealt")
plt.gca().axes.xaxis.set_ticklabels([])
plt.ylabel("Number of Occurrences")
plt.xlim([-20,20])
plt.ylim([0,1000])

# Slice #2
plt.subplot(2, 3, 2)
slice375 = arr[:,117]
plt.hist(slice375,bins=20,edgecolor='white')
plt.title("37.5% Cards Dealt")
plt.gca().axes.xaxis.set_ticklabels([])
plt.gca().axes.yaxis.set_ticklabels([])
plt.xlim([-20,20])
plt.ylim([0,1000])

# Slice #3
plt.subplot(2, 3, 3)
slice50 = arr[:,156]
plt.hist(slice50,bins=20,edgecolor='white')
plt.title("50% Cards Dealt")
plt.gca().axes.xaxis.set_ticklabels([])
plt.gca().axes.yaxis.set_ticklabels([])
plt.xlim([-20,20])
plt.ylim([0,1000])

# Slice #4
plt.subplot(2, 3, 4)
slice625 = arr[:,195]
plt.hist(slice625,bins=20,edgecolor='white')
plt.title("62.5% Cards Dealt")
plt.xlabel("True Count")
plt.ylabel("Number of Occurrences")
plt.xlim([-20,20])
plt.ylim([0,1000])

# Slice #5
plt.subplot(2, 3, 5)
slice75 = arr[:,234]
plt.hist(slice75,bins=20,edgecolor='white')
plt.title("75% Cards Dealt")
plt.xlabel("True Count")
plt.gca().axes.yaxis.set_ticklabels([])
plt.xlim([-20,20])
plt.ylim([0,1000])

# Slice #6
plt.subplot(2, 3, 6)
slice875 = arr[:,273]
plt.hist(slice875,bins=20,edgecolor='white')
plt.title("87.5% Cards Dealt")
plt.xlabel("True Count")
plt.gca().axes.yaxis.set_ticklabels([])
plt.xlim([-20,20])
plt.ylim([0,1000])


# Display all Plots
plt.show()

