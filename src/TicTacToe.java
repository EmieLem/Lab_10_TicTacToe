import java.util.Scanner;

/** A two-player console Tic-Tac-Toe game. */
public class TicTacToe
{
    private static final int ROWS = 3;
    private static final int COLS = 3;
    private static final String[][] board = new String[ROWS][COLS];
    private static int moveCount;

    /*
     * Pseudocode:
     * 1. Clear the board and start with X.
     * 2. Display the board and read a row and column from 1 through 3.
     * 3. Convert coordinates to array indexes and repeat until the square is empty.
     * 4. Place the mark, count the move, and check for a win or tie.
     * 5. Announce a result or switch players; ask to play another game.
     */
    public static void main(String[] args)
    {
        Scanner console = new Scanner(System.in);
        boolean playAgain;

        do
        {
            clearBoard();
            String currentPlayer = "X";
            boolean gameOver = false;
            System.out.println("\nTic-Tac-Toe: enter row and column numbers from 1 to 3.");

            while(!gameOver)
            {
                System.out.println("\nPlayer " + currentPlayer + "'s turn.");
                display();
                int row = SafeInput.getRangedInt(console, "Choose a row", 1, ROWS) - 1;
                int col = SafeInput.getRangedInt(console, "Choose a column", 1, COLS) - 1;

                while(!isValidMove(row, col))
                {
                    System.out.println("That square is already occupied. Player " + currentPlayer + " must pick an empty square.");
                    display();
                    row = SafeInput.getRangedInt(console, "Choose a row", 1, ROWS) - 1;
                    col = SafeInput.getRangedInt(console, "Choose a column", 1, COLS) - 1;
                }

                board[row][col] = currentPlayer;
                moveCount++;

                if(isWin(currentPlayer))
                {
                    display();
                    System.out.println("Player " + currentPlayer + " wins!");
                    gameOver = true;
                }
                else if(isTie())
                {
                    display();
                    System.out.println("The game is a tie.");
                    gameOver = true;
                }
                else
                {
                    currentPlayer = nextPlayer(currentPlayer);
                }
            }

            playAgain = SafeInput.getYNConfirm(console, "Would you like to play again?");
        }
        while(playAgain);

        System.out.println("Thanks for playing!");
    }

    /** Returns the other player's symbol after one legal, nonterminal move. */
    private static String nextPlayer(String currentPlayer)
    {
        return "X".equals(currentPlayer) ? "O" : "X";
    }

    /** Resets every square for a new game. */
    private static void clearBoard()
    {
        moveCount = 0;
        for(int row = 0; row < ROWS; row++)
        {
            for(int col = 0; col < COLS; col++)
            {
                board[row][col] = " ";
            }
        }
    }

    /** Displays the board with 1-based row and column labels for player input. */
    private static void display()
    {
        System.out.print("\n    ");
        for(int col = 0; col < COLS; col++)
        {
            System.out.print(" " + (col + 1) + "  ");
        }
        System.out.println();

        for(int row = 0; row < ROWS; row++)
        {
            System.out.print((row + 1) + "   ");
            for(int col = 0; col < COLS; col++)
            {
                System.out.print(board[row][col]);
                if(col < COLS - 1)
                {
                    System.out.print(" | ");
                }
            }
            System.out.println();
            if(row < ROWS - 1)
            {
                System.out.println("   ---+---+---");
            }
        }
        System.out.println();
    }

    /** Returns true only when the requested square is still empty. */
    private static boolean isValidMove(int row, int col)
    {
        return row >= 0 && row < ROWS && col >= 0 && col < COLS
                && " ".equals(board[row][col]);
    }

    /** Checks all three possible directions for a winning line. */
    private static boolean isWin(String player)
    {
        return isRowWin(player) || isColWin(player) || isDiagonalWin(player);
    }

    private static boolean isRowWin(String player)
    {
        for(int row = 0; row < ROWS; row++)
        {
            boolean completeRow = true;
            for(int col = 0; col < COLS; col++)
            {
                if(!player.equals(board[row][col]))
                {
                    completeRow = false;
                    break;
                }
            }
            if(completeRow)
            {
                return true;
            }
        }
        return false;
    }

    private static boolean isColWin(String player)
    {
        for(int col = 0; col < COLS; col++)
        {
            boolean completeCol = true;
            for(int row = 0; row < ROWS; row++)
            {
                if(!player.equals(board[row][col]))
                {
                    completeCol = false;
                    break;
                }
            }
            if(completeCol)
            {
                return true;
            }
        }
        return false;
    }

    private static boolean isDiagonalWin(String player)
    {
        boolean topLeftToBottomRight = true;
        boolean topRightToBottomLeft = true;
        for(int index = 0; index < ROWS; index++)
        {
            if(!player.equals(board[index][index]))
            {
                topLeftToBottomRight = false;
            }
            if(!player.equals(board[index][COLS - 1 - index]))
            {
                topRightToBottomLeft = false;
            }
        }
        return topLeftToBottomRight || topRightToBottomLeft;
    }

    /** A tie occurs when the board is full or every winning line is blocked. */
    private static boolean isTie()
    {
        if(moveCount == ROWS * COLS)
        {
            return true;
        }

        // A remaining winning line must still be possible for at least one player.
        for(int row = 0; row < ROWS; row++)
        {
            boolean hasX = false;
            boolean hasO = false;
            for(int col = 0; col < COLS; col++)
            {
                hasX |= "X".equals(board[row][col]);
                hasO |= "O".equals(board[row][col]);
            }
            if(!hasX || !hasO)
            {
                return false;
            }
        }

        for(int col = 0; col < COLS; col++)
        {
            boolean hasX = false;
            boolean hasO = false;
            for(int row = 0; row < ROWS; row++)
            {
                hasX |= "X".equals(board[row][col]);
                hasO |= "O".equals(board[row][col]);
            }
            if(!hasX || !hasO)
            {
                return false;
            }
        }

        boolean mainHasX = false;
        boolean mainHasO = false;
        boolean reverseHasX = false;
        boolean reverseHasO = false;
        for(int index = 0; index < ROWS; index++)
        {
            mainHasX |= "X".equals(board[index][index]);
            mainHasO |= "O".equals(board[index][index]);
            reverseHasX |= "X".equals(board[index][COLS - 1 - index]);
            reverseHasO |= "O".equals(board[index][COLS - 1 - index]);
        }

        return mainHasX && mainHasO && reverseHasX && reverseHasO;
    }
}
