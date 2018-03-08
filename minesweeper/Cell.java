package object_oriented_design.minesweeper;

public class Cell {

	private CellState state;
	private Position position;
	private int numberOfAdjacentMines;
	
	public Cell(int x, int y){
		state = CellState.UNKOWN;
		position = new Position(x,y);
		numberOfAdjacentMines = 0;
	}

	public int getNumberOfAdjacentMines() {
		return numberOfAdjacentMines;
	}



	public void setNumberOfAdjacentMines(int numberOfAdjacentMines) {
		this.numberOfAdjacentMines = numberOfAdjacentMines;
	}



	public CellState getState() {
		return state;
	}

	public void setState(CellState state) {
		this.state = state;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}
	
	
}
