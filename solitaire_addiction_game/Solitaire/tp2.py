# Arthur Lewis W. PAUL  Matricule : 20231416
# Naromba Conde         Matricule : 20251772
# Date : 08/12/2023

import random


'''
-------------------- Solitaire Card Game Program ----------------
 
This program implements a card shuffling game using a deck of cards.
It includes functions for shuffling cards, removing specific cards,
cheating to generate a complete deck, and finding misplaced cards.
The user interacts with the game by clicking a button to shuffle the
cards and make strategic moves. The game provides visual feedback
through an HTML-based card grid, indicating possible moves and displaying
the status of the game. The objective is to successfully organize the
shuffled cards and win the game within a limited number of shuffle attempts.
 
'''


'''-------------------- Solitaire Card Game Code -------------------- '''


'''
This function takes an input array deck (a deck of cards).
It iterates over each element of the list from the end to the beginning.
At each iteration, it chooses a random index j between 0 and the current
index i,  then swaps the elements at indices i and j.
In the end, it returns the shuffled deck of cards.
'''


def custom_shuffle(deck):
    for i in range(len(deck) - 1, 0, -1):
        j = random.randint(0, i)
        deck[i], deck[j] = deck[j], deck[i]
    return deck


'''
Function to remove Ace cards (remove_as):
This function takes a list deck as input.
It iterates through each card in the deck and replaces
any card starting with "A" with the string "absent".
It returns the modified deck of cards.
'''


def remove_as(deck):
    for i in range(len(deck)):
        if deck[i][0] == "A":
            deck[i] = "absent"
    return deck


"--------------- Unit Test Function remove_as ---------------"
# Test that remove_as correctly replaces all cards
# starting with "A" with "absent"


def test_remove_as():
    deck = ['AC', '2C', '3C', '4C', '5C', '6C',
            '7C', '8C', '9C', '10C', 'JC', 'QC', 'KC']
    modified_deck = remove_as(deck.copy())
    expected_deck = ['absent', '2C', '3C', '4C', '5C',
                     '6C', '7C', '8C', '9C', '10C', 'JC', 'QC', 'KC']
    assert modified_deck == expected_deck, \
        "Remove_as failed to replace 'A' cards"


test_remove_as()

"--------------- Unit Test Function remove_as ---------------"


'''
Cheat function (cheat):
This function generates a complete deck of cards with cards from four
suits ("C" for clubs, "D" for diamonds, "H" for hearts, "S" for spades)
and thirteen different values ("A", "2", ..., "K").
'''


def cheat():
    suits = ["C", "D", "H", "S"]
    ranks = ["A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"]

    card_deck = []
    for suit in suits:
        for rank in ranks:
            card_deck.append(rank + suit)
    return card_deck


"--------------- Unit Test Function Cheat ---------------"

# Test that cheat function returns the correct deck


def test_cheat():
    assert cheat() == ['AC', '2C', '3C', '4C', '5C', '6C', '7C',
                       '8C', '9C', '10C', 'JC', 'QC', 'KC', 'AD',
                       '2D', '3D', '4D', '5D', '6D', '7D', '8D',
                       '9D', '10D', 'JD', 'QD', 'KD', 'AH', '2H',
                       '3H', '4H', '5H', '6H', '7H', '8H', '9H',
                       '10H', 'JH', 'QH', 'KH', 'AS', '2S', '3S',
                       '4S', '5S', '6S', '7S', '8S', '9S', '10S',
                       'JS', 'QS', 'KS'], "Deck not returned correctly"


test_cheat()

"--------------- Unit Test Function Cheat ---------------"


'''
Function to generate a card deck (generate_card_deck):
This function uses the custom_shuffle and remove_as
functions to create and prepare a card deck for the game.
It returns the deck of cards ready to be displayed.
'''


def generate_card_deck():
    suits = ["C", "D", "H", "S"]
    ranks = ["A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"]

    card_deck = []
    for suit in suits:
        for rank in ranks:
            card_deck.append(rank + suit)
    shuffled_deck = custom_shuffle(card_deck)
    return remove_as(shuffled_deck)


"""
Function to identify misplaced cards (find_misplaced_cards):
This function analyzes the given card deck to find and mark any cards
that are not in their expected positions. It compares each card to its
expected position based on a predefined rank sequence and suit.
If a card is misplaced, it adds it to the list of misplaced_cards.
The function returns a list containing the misplaced cards and a
modified card deck with "absent" marking misplaced cards.
"""

shuffle_count = 3

def find_misplaced_cards(card_deck):
    misplaced_cards = []
    suite = ["2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", ""]
    figure = ""
    for i in range(len(card_deck)-1):
        index = i % 13
        if index == 0:    
            figure = card_deck[i][:2] if len(card_deck[i]) == 3 else card_deck[i][1]
        expected = suite[index] + figure
        if not (expected == card_deck[i]):
            if not (card_deck[i] == "absent"):
                misplaced_cards.append(card_deck[i])
                figure = ""
            card_deck[i] = "absent"
    return misplaced_cards


"""
Function to handle the card shuffling click event (click_shuffle):
This function is called when the user clicks the shuffle button.
It identifies and marks misplaced cards using the find_misplaced_cards
function. Additionally, it appends specific cards ("AC", "AD", "AH", "AS")
to the list of misplaced cards. The misplaced cards are then shuffled, and
the "absent" cards in the deck are replaced with the shuffled misplaced cards.
The shuffle count is decremented, and the updated card deck is displayed using
the display_grid function. The modified card deck is returned.
"""


def click_shuffle(card_deck):
    #print("avant shuffle\n",card_deck)
    global shuffle_count
    misplaced_cards = find_misplaced_cards(card_deck)
    misplaced_cards.append("AC")
    misplaced_cards.append("AD")
    misplaced_cards.append("AH")
    misplaced_cards.append("AS")
    misplaced_cards = custom_shuffle(misplaced_cards)
    index = 0

    for i in range(len(card_deck)-1):
        if card_deck[i] == "absent":
            card_deck[i] = misplaced_cards[index]
            index += 1

    shuffle_count -= 1
    card_deck = remove_as(card_deck)
    display_grid(card_deck)
    #print("avant shuffle",card_deck)
    return card_deck


'''
Function to display the card grid (display_grid):
This function generates an HTML representation of a card grid from
the provided card deck. It uses HTML tags such as <table>, <tr>,
<td>, and <img> to create the HTML structure. It also applies a
green background color to cells corresponding to possible moves.
It uses the find_moves function to obtain possible moves.
'''


def display_grid(card_deck):
    global shuffle_count
    card_grid_html = "<table>"
    for row in range(4):
        card_grid_html += "<tr>"
        for col in range(13):
            index = row * 13 + col
            card_path = "cards/" + card_deck[index] + ".svg"
            card_grid_html += '<td id="case' + \
                str(index) + '"><img src="' + card_path + \
                '" alt="' + card_deck[index] + '" /></td>'
        card_grid_html += "</tr>"
    card_grid_html += "</table>"

    misplaced_cards = find_misplaced_cards(card_deck.copy())
    test, test2 = find_moves(card_deck)
    if len(misplaced_cards) == 0:
        card_grid_html += "Vous avez réussi!  Bravo!"

    if shuffle_count > 0 and len(test) == 0:
        card_grid_html += "<br> Vous devez \
        <button id ='button_shuffle_card' onclick='click_shuffle(card_deck)'>\
        brasser les cartes </button><br> "

    elif shuffle_count > 0:
        card_grid_html += "<br> Vous pouvez encore \
        <button id ='button_shuffle_card' onclick='click_shuffle(card_deck)'>\
        brasser les cartes </button> " + str(shuffle_count)+" fois <br>"

    else:
        if len(test) == 0:
            card_grid_html += "<br><p>Vous avez perdu</p>"
        else:
            card_grid_html += "<br> <p>Vous ne pouvez plus brasser les \
            cartes</p>"

    card_grid_html += "<br> <button onclick='init()'>Nouvelle partie</button>"
    jeu = document.querySelector("#jeu")
    jeu.innerHTML = card_grid_html

    for t in range(len(test)):
        case = document.querySelector("#case"+str(card_deck.index(test[t])))
        case.setAttribute("style", "background-color: lime")
        aba = str(test2[t]) + "," + str(deck_ori.index(test[t]))
        case.setAttribute("onclick", 'test(' + aba + ')')


"""
Function to handle the user's move (test):
This function updates the card deck based on the user's move
by replacing an "absent" card with the specified card value.
It then calls the display_grid function to update the display.
"""


def test(pos, val):
    card_deck[card_deck.index(deck_ori[val])] = "absent"
    card_deck[int(pos)] = deck_ori[val]
    display_grid(card_deck)


"""
Function to find possible moves (find_moves):
This function identifies possible moves in the card deck.
It returns two lists - moves (possible card values) and
move_pos (positions where the moves can be made).
"""


def find_moves(card_deck):
    moves = []
    move_pos = []

    for i in range(len(card_deck)):
        if card_deck[i] == "absent":
            if i in [0, 13, 26, 39]:
                for suit in ["C", "D", "H", "S"]:
                    moves.append("2" + suit)
                    move_pos.append(i)
            else:
                left_card = card_deck[i-1]
                if left_card == "absent":
                    continue
                elif left_card[0] == "J":
                    next_card = "Q" + left_card[-1]
                elif left_card[0] == "Q":
                    next_card = "K" + left_card[-1]
                elif left_card[0] == "1":
                    next_card = "J" + left_card[-1]
                elif left_card[0] == "K":
                    continue
                else:
                    next_card = str(int(left_card[:-1]) + 1) + left_card[-1]
                moves.append(next_card)
                move_pos.append(i)
    return [moves, move_pos]



"""
Main function to start the game (game):
This function initializes the game by generating a cheat deck,
creating and shuffling a game deck, and then displaying the grid
using the display_grid function.
"""


def game():
    global deck_ori
    global card_deck
    deck_ori = cheat()
    card_deck = generate_card_deck()
    display_grid(card_deck)


"""
Function to initialize a new game (init):
This function resets the game by setting up the HTML structure
and calling the game function to start a new game.
"""


def init():
    racine = document.querySelector("#cb-body")
    racine.innerHTML = (
        "<style>"
        "#jeu table { float:none; margin:3px 3px; }"
        "#jeu table td { border:0; padding:1px 2px; height:auto; width:auto; }"
        "#jeu table td img { height:140px; }"
        "</style>"
        "<div id='jeu'>"
        "<br>"
        "<div>"

    )

    global shuffle_count
    shuffle_count = 3

    game()


'''-------------------- Solitaire Card Game Code -------------------- '''
