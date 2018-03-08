package object_oriented_design.minesweeper;

public class DummyStrategy implements Strategy {

	private Board board;
	public DummyStrategy(Board board){
		this.board = board;
	}
	
	public DummyStrategy(){
		
	}
	
	public void setBoard(Board b){
		this.board =b;
	}
	@Override
	public Command getCommand() {
		int col = board.getCol();
		int row = board.getRow();
		for(int i = 0; i < row; i ++){
			for(int j = 0; j < col; j++){
				if(board.getCells()[i][j].getState() == CellState.UNKOWN){
					return new ClickCommand(i, j);
				}
			}
		}
		return null;
	}

	
}
