package object_oriented_design.minesweeper;

public interface Command {
	
	public boolean move(int x, int y);
	
	public boolean move();
	public void setBoard(Board board);
	public int getX();

	public int getY() ;
	
	

}
