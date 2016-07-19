## TicTacToe (Coding Exercise)

This is a coding exercise to create a simple tic-tac-toe player, with 
limited learning capabilities.

## Usage

Tic-tac-toe is played via command line. The computer will ask for desired grid 
size and whether you would like to play as X or O. Each move is entered as 
coordinates, such as "1,1" for the top-left corner. Multiple games can be played 
in the same session.

## Features

- **Learning A.I.** - the app uses a very simple learning algorithm to improve 
its play over time. Each time it loses, it will remember the last move it made, 
and avoid it in the future. It will also avoid moves which are equivalent (the
same move, but rotated/mirrored/transposed)
- **Selectable grid size** - Tic-tac-toe can be played on any size grid over 2.
(With size 2, the first player always wins!)

## Code Notes

- Grid storage: the tic-tac-toe grid is stored in a custom matrix class, that
implements all required matrix transformations.
- Win detection: wins and draws are detected by assigning X and O values of 1 
and -1, and then keeping track of the total value of each row/column/diagonal.
In this way, less computation needs to be done after each move.
- A.I. Memory: the collection of losing move the the computer learns is stored
in a file-based database, using SQLite.

## Contributors

Steven Noto (https://github.com/stevennoto/)

## License

Public Domain
