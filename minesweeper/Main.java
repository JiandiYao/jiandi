package object_oriented_design.minesweeper;

public class Main {

	public static void main(String[] args) {
		Game game = new MineSweeperGame();
		String player = "dummy";
		game.startGame(player);
		
	}

}
