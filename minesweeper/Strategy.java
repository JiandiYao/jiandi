package object_oriented_design.minesweeper;

public interface Strategy {
	public Command getCommand();
	public void setBoard(Board b);
}
