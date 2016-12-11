package ticTacToe;

import apcs.Window;

public class TicTacToeGame {

	public static void main(String[] args) {
		Window.frame();
		TicTacToe game = new TicTacToe();
		
		while (true)
		{
			game.draw();
			
			if (game.player == 1)
			{
				game.move(game.getHumanMove());
			}
			else
			{
				game.move(game.getBestMove());
			}
			
			if (game.isDraw() || game.getWinner() > 0)
			{
				game.draw();
				Window.sleep(3000);
				game = new TicTacToe();
			}
		}
	}

}
