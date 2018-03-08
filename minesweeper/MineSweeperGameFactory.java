package object_oriented_design.minesweeper;

import java.util.Properties;

public class MineSweeperGameFactory implements AbstractGameFactory{

	public Game createGame(){
		
		return new MineSweeperGame();
	}
}
