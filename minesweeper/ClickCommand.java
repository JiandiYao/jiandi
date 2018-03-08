package object_oriented_design.minesweeper;

/*
 * Click on a cell to see the result
 */
public class ClickCommand implements Command{

	private Board board;
	private int x;
	private int y;
	
	public ClickCommand(Board board){
		this.board = board;
	}
	
	public ClickCommand(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public void setBoard(Board board) {
		this.board = board;
	}

	@Override
	public boolean move(int x, int y) {
		
		board.getCells()[x][y].setState(CellState.NOT_MINE);
		if(board.getCells()[x][y].getNumberOfAdjacentMines() == -1)
			return false;
		else 
			return true;
	}

	@Override
	public boolean move() {
		
		board.getCells()[x][y].setState(CellState.NOT_MINE);
		if(board.getCells()[x][y].getNumberOfAdjacentMines() == -1)
			return false;
		else 
			return true;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	

}
