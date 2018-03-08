package object_oriented_design.minesweeper;

public class SmartStrategy implements Strategy {

	private Board board;
	@Override
	public Command getCommand() {
		
		return null;
	}

	@Override
	public void setBoard(Board b) {
		this.board = b;
	}

}
