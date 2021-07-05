import numpy as np
import pygame
import sys
import math

# Variabili globali

BLUE = (0, 0, 255)
BLACK = (0, 0, 0)
WHITE = (255, 255, 255)

# Inizio dichiarazione metodi utilizzati

def create_board():
    board = np.zeros((ROW_COUNT, COLUMN_COUNT))
    return board


def drop_piece(board, riga, colonna, pezzo):  # metodo per far cadere il pezzo
    board[riga][colonna] = pezzo


def is_valid_location(board, riga, colonna):  # metodo per controllare se il posto dove cade il pezzo sia libero
    return board[riga][colonna] == 0


def get_next_open_row(board, colonna):
    for r in range(ROW_COUNT):
        if board[r][colonna] == 0:
            return r


def board_control_5(board, piece, quale):
    global my_vars
    # parte orizzontale
    for x in range(ROW_COUNT):
        for y in range(COLUMN_COUNT - 4):
            if board[x][y] == piece and board[x][y + 1] == piece and board[x][y + 2] == piece and board[x][
                y + 3] == piece and board[x][y + 4] == piece:
                my_vars[quale]['punteggio'] = my_vars[quale]['punteggio'] + 50

    # parte verticale
    for y in range(COLUMN_COUNT):
        for x in range(ROW_COUNT - 4):
            if board[x][y] == piece and board[x + 1][y] == piece and board[x + 2][y] == piece and board[x + 3][
                y] == piece and board[x + 4][y] == piece:
                my_vars[quale]['punteggio'] = my_vars[quale]['punteggio'] + 50

    # parte diagonale positiva (da sx a dx) = /
    for x in range(ROW_COUNT - 4):
        for y in range(COLUMN_COUNT - 4):
            if board[x][y] == piece and board[x + 1][y + 1] == piece and board[x + 2][y + 2] == piece and board[x + 3][
                y + 3] == piece and board[x + 4][y + 4] == piece:
                my_vars[quale]['punteggio'] = my_vars[quale]['punteggio'] + 50

    # parte diagonale negativa (da dx a sx) = \
    for x in range(ROW_COUNT - 4):
        for y in range(4, COLUMN_COUNT):
            if board[x][y] == piece and board[x + 1][y - 1] == piece and board[x + 2][y - 2] == piece and board[x + 3][
                y - 3] == piece and board[x + 4][y - 4] == piece:
                my_vars[quale]['punteggio'] = my_vars[quale]['punteggio'] + 50


def board_control_4(board, piece, quale):
    global my_vars
    # parte orizzontale
    for x in range(ROW_COUNT):
        for y in range(COLUMN_COUNT - 3):
            if board[x][y] == piece and board[x][y + 1] == piece and board[x][y + 2] == piece and board[x][
                y + 3] == piece:
                my_vars[quale]['punteggio'] = my_vars[quale]['punteggio'] + 10

    # parte verticale
    for y in range(COLUMN_COUNT):
        for x in range(ROW_COUNT - 3):
            if board[x][y] == piece and board[x + 1][y] == piece and board[x + 2][y] == piece and board[x + 3][
                y] == piece:
                my_vars[quale]['punteggio'] = my_vars[quale]['punteggio'] + 10

    # parte diagonale positiva (da sx a dx) = /
    for x in range(ROW_COUNT - 3):
        for y in range(COLUMN_COUNT - 3):
            if board[x][y] == piece and board[x + 1][y + 1] == piece and board[x + 2][y + 2] == piece and board[x + 3][
                y + 3] == piece:
                my_vars[quale]['punteggio'] = my_vars[quale]['punteggio'] + 10

    # parte diagonale negativa (da dx a sx) = \
    for x in range(ROW_COUNT - 3):
        for y in range(3, COLUMN_COUNT):
            if board[x][y] == piece and board[x + 1][y - 1] == piece and board[x + 2][y - 2] == piece and board[x + 3][
                y - 3] == piece:
                my_vars[quale]['punteggio'] = my_vars[quale]['punteggio'] + 10


def board_control_3(board, piece, quale):
    global my_vars
    # parte orizzontale
    for x in range(ROW_COUNT):
        for y in range(COLUMN_COUNT - 2):
            if board[x][y] == piece and board[x][y + 1] == piece and board[x][y + 2] == piece:
                my_vars[quale]['punteggio'] = my_vars[quale]['punteggio'] + 2

    # parte verticale
    for y in range(COLUMN_COUNT):
        for x in range(ROW_COUNT - 2):
            if board[x][y] == piece and board[x + 1][y] == piece and board[x + 2][y] == piece:
                my_vars[quale]['punteggio'] = my_vars[quale]['punteggio'] + 2

    # parte diagonale positiva (da sx a dx) = \
    for x in range(ROW_COUNT - 2):
        for y in range(COLUMN_COUNT - 2):
            if board[x][y] == piece and board[x + 1][y + 1] == piece and board[x + 2][y + 2] == piece:
                my_vars[quale]['punteggio'] = my_vars[quale]['punteggio'] + 2

    # parte diagonale negativa (da dx a sx) = /
    for x in range(ROW_COUNT - 2):
        for y in range(2, COLUMN_COUNT):
            if board[x][y] == piece and board[x + 1][y - 1] == piece and board[x + 2][y - 2] == piece:
                my_vars[quale]['punteggio'] = my_vars[quale]['punteggio'] + 2


def draw_board(board):      # Disegna l'interfaccia grafica iniziale
    for c in range(COLUMN_COUNT):
        for r in range(ROW_COUNT):
            pygame.draw.rect(screen, BLUE, (c * SQUARESIZE, r * SQUARESIZE + SQUARESIZE, SQUARESIZE, SQUARESIZE))
            if board[r][c] == 0:
                pygame.draw.circle(screen, BLACK, (
                    int(c * SQUARESIZE + SQUARESIZE / 2), int(r * SQUARESIZE + SQUARESIZE + SQUARESIZE / 2)), RADIUS)


def draw_board_2(board):
    global my_vars
    for c in range(COLUMN_COUNT):
        for r in range(ROW_COUNT):
            pygame.draw.rect(screen, BLUE, (c * SQUARESIZE, r * SQUARESIZE + SQUARESIZE, SQUARESIZE, SQUARESIZE))
            if board[r][c] == 0:
                pygame.draw.circle(screen, BLACK, (
                int(c * SQUARESIZE + SQUARESIZE / 2), int(r * SQUARESIZE + SQUARESIZE + SQUARESIZE / 2)), RADIUS)
            else:
                for a in my_vars:
                    if board[r][c] == my_vars[a]["pedina"]:
                        pygame.draw.circle(screen, my_vars[a]["colour"], (
                            int(c * SQUARESIZE + SQUARESIZE / 2), int(r * SQUARESIZE + SQUARESIZE + SQUARESIZE / 2)),
                                           RADIUS)
    pygame.display.update()

# Inizio programma

my_vars = {}
ROW_COUNT = int(input("Specifica la dimensione della tabella: "))
COLUMN_COUNT = ROW_COUNT
board = create_board()
print(board)
gameOver = False
PLAYER = int(input("Quanti giocatori partecipano? "))
for i in (number + 1 for number in range(PLAYER)):      # Non va da 0 a Player-1, ma va da 1 a Player
    my_vars[i] = {'name': "Giocatore %d" % i, 'pedina': i, 'punteggio': 0, 'score_attuale': 0,
                  'colour': ""}
    color_choice = input("Di che colore vuoi la pedina " + my_vars[i]['name'] + "? (colore da inserire in inglese) ")
    my_vars[i]['colour'] = color_choice

# Fine fase inizializzazione e inizio gioco vero e proprio

pygame.init()

SQUARESIZE = 50
tot_caselle = 0
width = COLUMN_COUNT * SQUARESIZE
height = (ROW_COUNT + 1) * SQUARESIZE
size = (width, height)
RADIUS = int(SQUARESIZE / 2 - 5)
max = 0

screen = pygame.display.set_mode(size)
draw_board(board)
pygame.display.update()

while not gameOver:
    for x in my_vars:
        print("Tocca al giocatore: " + my_vars[x]['name'])
        has_clicked = False
        while not has_clicked:
            for event in pygame.event.get():
                if event.type == pygame.QUIT: # Equivalente di clicca sulla x per terminare il gioco
                    sys.exit()
                if event.type == pygame.MOUSEBUTTONDOWN and event.button == 1: # Equivalente di se il giocatore clicca il tasto sx del mouse
                    posx = event.pos[0]
                    posy = event.pos[1]
                    colonna = int(math.floor(posx / SQUARESIZE))
                    riga = int(math.floor((posy - 50) / SQUARESIZE))
                    if not is_valid_location(board, riga, colonna):
                        print("Ops! Devi scegliere un'altra posizione")
                        print(" ")
                        continue
                    drop_piece(board, riga, colonna, my_vars[x]['pedina'])
                    tot_caselle = tot_caselle + 1
                    has_clicked = True
                    print(board)
                    board_control_3(board, my_vars[x]['pedina'], x)
                    board_control_4(board, my_vars[x]['pedina'], x)
                    board_control_5(board, my_vars[x]['pedina'], x)
                    if my_vars[x]['punteggio'] >= 50:
                        print("Congratulazioni, " + str(my_vars[x]['name']) + "! Hai vinto!")
                        gameOver = True
                        sys.exit()
                    elif tot_caselle == (ROW_COUNT * COLUMN_COUNT):
                        print("Purtroppo nessuno è arrivato a 50: gioco terminato!")
                        gameOver = True
                        sys.exit()
                    my_vars[x]['score_attuale'] = my_vars[x]['punteggio']
                    my_vars[x]['punteggio'] = 0
                    draw_board_2(board)
                    for ciclo in my_vars:
                        print("Il punteggio del " + str(my_vars[ciclo]['name']) + " e' " + str(
                            my_vars[ciclo]['score_attuale']))
                    print(" ")
