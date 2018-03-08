package object_oriented_design.minesweeper;

/*
 * Mark a cell as a mine
 */
public class MarkCommand implements Command{
	private Board board;
	private int x;
	private int y;
	
	public MarkCommand(Board board){
		this.board = board;
	}
	
	public MarkCommand(int x, int y){
		this.x = x;
		this.y = y;
	}

	@Override
	public boolean move(int x, int y) {
		board.getCells()[x][y].setState(CellState.MINE);
		return true;
	}

	@Override
	public boolean move() {
		board.getCells()[x][y].setState(CellState.MINE);
		return true;
	}

	@Override
	public void setBoard(Board board) {
		this.board = board;
		
	}

	@Override
	public int getX() {
		// TODO Auto-generated method stub
		return this.x;
	}

	@Override
	public int getY() {
		// TODO Auto-generated method stub
		return this.y;
	}

}
