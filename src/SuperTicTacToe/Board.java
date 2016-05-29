package SuperTicTacToe;

public class Board {
	public static final char EMPTY = ' ';
	public char[][] board;
	private boolean won;
	private char winner; // ' ' -> no winner
	
	public Board(){
		board = new char[3][3];
		for (int i = 0; i < 3; i++){
			for (int j = 0; j < 3; j++){
				board[i][j] = EMPTY;
			}
		}
		won = false;
	}
	/**
	 * Attempt to place a piece into the board
	 * @param row - the row to place it in (0-2)
	 * @param col - the column to place it in (0-2)
	 * @param piece - the piece to put into that spot
	 * @return int - 1 --> cannot be placed there or  0 --> piece was placed 
	 */
	public int placePiece(int row, int col, char piece){
		if (board[row][col] != EMPTY)
			return 1;
		else{
			board[row][col] = piece;
			return 0;
		}
	}
	
	public boolean isFull(){
		boolean full = true;
		for (int i = 0; i < 3; i++){
			for (int j = 0; j < 3; j++){
				if (board[i][j] == EMPTY){
					full = false;
					break;
				}
					
			}
		}
		return full;
	}
	
	public boolean hasWin(){
		boolean win = false;
		
		// horizontal
		if (!win){
			for (int r = 0; r < 3; r++){
				if ((board[r][0] == board[r][1] && board[r][0] == board[r][2]) && board[r][0] != EMPTY){
					win = true;
					winner = board[r][0];
					break;
				}
					
			}
		}
		// vertical
		if (!win){
			for (int c = 0; c < 3; c++){
				if ((board[0][c] == board[1][c] && board[0][c] == board[2][c]) && board[0][c] != EMPTY){
					win = true;
					winner = board[0][c];
					break;
				}
					
			}
		}
		// diagonals
		if (!win){
			if (board[0][0] == board[1][1] && board[0][0] == board[2][2] && board[1][1] != EMPTY){
				win = true;
				winner = board[1][1];
			}
			else if (board[0][2] == board[1][1] && board[2][0] == board[1][1] && board[1][1] != EMPTY){
				win = true;
				winner = board[1][1];
			}
		}

		return win;
	}
	
	public char getCharAt(int row, int col){
		return board[row][col];
	}
	public void printASCIIBoard(){
		for (int i = 0; i < 3; i++){
			for (int j = 0; j < 3; j++){
				System.out.print(board[i][j] + "|");
			}
			if (i < 2)
				System.out.println("\n-------");
		}
	}
	public char getWinner(){
		return winner;
	}
	public boolean givesWin(int row, int col, char piece){
		Board b = new Board();
		for (int i = 0; i < 3; i++){
			for (int j = 0; j < 3; j++){
				b.board[i][j] = getCharAt(i,j);
			}
		}
		b.placePiece(row, col, piece);
		return b.hasWin();
	}
}
