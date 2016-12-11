package ticTacToe;

import java.util.ArrayList;

import apcs.Window;

public class TicTacToe {
	
	//State of the board
	int[][] board;
	int player;
	
	public TicTacToe()
	{
		board = new int[3][3];
		player = 1;
	}
	
	public TicTacToe(TicTacToe other)
	{
		board = new int[3][3];
		player = other.player;
		
		// Copy the board over
		for (int x = 0; x < 3; x++)
		{
			for (int y = 0; y < 3; y++)
			{
				board[x][y] = other.board[x][y];
			}
		}
	}
	
	
	
	
	public void move(int x, int y)
	{
		board[x][y] = player;
		player = 1 + player%2;
	}
	
	public void move(Move m)
	{
		move(m.x, m.y);
	}
	
	public int getWinner()
	{
		for (int i = 0; i < 3; i++)
		{
			if (board[i][0] > 0 &&
				board[i][0] == board[i][1] &&
				board[i][0] == board[i][2])
			{
				return board[i][0];
			}
			if (board[0][i] > 0 &&
				board[0][i] == board[1][i] &&
				board[0][i] == board[2][i])
			{
				return board[0][i];
			}
		}
		
		if (board[0][0] > 0 &&
			board[0][0] == board[1][1] &&
			board[0][0] == board[2][2])
		{
			return board[0][0];
		}
		if (board[0][2] > 0 &&
			board[0][2] == board[1][1] &&
			board[0][2] == board[2][0])
		{
			return board[0][2];
		}
		return 0;
	}
	
	public boolean isDraw()
	{
		for (int x = 0; x < 3; x++)
		{
			for (int y = 0; y < 3; y++)
			{
				if (board[x][y] == 0)
					return false;
			}
		}
		return true;
	}
	
	public ArrayList <Move> getMoves() 
	{
		ArrayList <Move> moves = new ArrayList <Move> ();
		for (int x = 0; x < 3; x++)
		{
			for (int y = 0; y < 3; y++)
			{
				if (board[x][y] == 0)
				{
					moves.add(new Move(x, y));
				}
			}
		}
		return moves;
	}
	
	public Move getHumanMove()
	{
		Move move = null;
		while (move == null)
		{
			Window.mouse.waitForClick();
			
			int x = (Window.mouse.getX() - 25) / 150;
			int y = (Window.mouse.getY() - 25) /150;
			
			if (x >= 0 && x < 3 && y >= 0 && y < 3 && board[x][y] == 0)
			{
				move = new Move(x, y);
			}
			
			Window.mouse.waitForRelease();
		}
		return move;
	}
	
	public Move getBestMove()
	{
		ArrayList <Move> moves = getMoves();
		Move best = null;
		int bestValue = -2;
		
		for (Move move : moves)
		{
			// Make a new state of the board that's played that move
			TicTacToe next = new TicTacToe(this);
			next.move(move);
			
			int value = minimax(next, false, player);
			
			if (value > bestValue)
			{
				bestValue = value;
				best = move;
			}
		}
		return best;
	}
	/**
	 * 
	 * @param state - the state of the board
	 * @param player - which player we're finding the best move for
	 * @return
	 */
	public int minimax(TicTacToe state, boolean findBest, int player)
	{
		if (state.isDraw())
		{
			return 0;
		}
		int winner = state.getWinner();
		
		if (winner > 0)
		{
			if (winner == player)
				return 1;
			else
				return -1;
		}
		
		//state.draw();
		int best = -2;
		if (! findBest)
		{
			best = 2;
		}
		
		
		for (Move move: state.getMoves())
		{
			TicTacToe nextState = new TicTacToe(state);
			nextState.move(move);
			
			int value = minimax(nextState, !findBest, player);
			if (findBest) {
				if (value > best) 
				{
					best = value;
				}
			}
			else
			{
				if (value < best)
				{
					best = value;
				}
			}
		}
		return best;
	}
	
	public void draw()
	{
		Window.out.background("white");
		
		//Draw the four lines
		Window.out.color("gray");
		Window.out.rectangle(250, 175, 450, 5);
		Window.out.rectangle(250, 325, 450, 5);
		Window.out.rectangle(175, 250, 5, 450);
		Window.out.rectangle(325, 250, 5, 450);
		
		// Draw the Xs and Os
		
		Window.out.font("Courier", 150);
		for (int x = 0; x < 3; x++)
		{
			for (int y = 0; y < 3; y++)
			{
				if (board[x][y] == 1)
				{
					Window.out.color("blue");
					Window.out.print("X", 50 + x * 150, 150 + y*150);
				}
				if (board[x][y] == 2)
				{
					Window.out.color("red");
					Window.out.print("O", 50 + x*150, 150 + y*150);
				}
			}
		}
		Window.frame();
	}
	
}
