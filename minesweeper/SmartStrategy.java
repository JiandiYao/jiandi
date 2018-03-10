package object_oriented_design.minesweeper;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class SmartStrategy implements Strategy {

	private Board board;
	private Cell[][] cells;
	private static Queue<Command> queue;
	
	public SmartStrategy(){
		queue = new LinkedList<Command>();
		
	}
	
	
	@Override
	public Command getCommand() {
		cells = board.getCells();
		if(!queue.isEmpty())
			return queue.poll();
		Command command = null;
		
	
		
		for(int i = 0; i < board.getRow(); i ++){
			for(int j = 0; j < board.getCol(); j++){
				
				if(cells[i][j].getState() == CellState.UNKOWN){
					command = checkCellSurroundings(i, j);
					if(command != null)
						return command;
				}
			}
		}
		
		if(command == null){
			for(int i = 0; i < board.getRow(); i ++){
				for(int j = 0; j < board.getCol(); j++){
					
					if(cells[i][j].getState() == CellState.UNKOWN){
						return new ClickCommand(i,j);
						
					}
				}
			}
		}
		return command;
	}

	private Command checkCellSurroundings(int i, int j) {
		Command command = null;
	
		for(int k = 0; k < Constant.EIGHT_DIRECTIONS.length; k++){
			int x  = Constant.EIGHT_DIRECTIONS[k][0] + i;
			int y = Constant.EIGHT_DIRECTIONS[k][1] + j;
			if(x >= 0 && x < board.getRow() && y >= 0 && y < board.getCol()){
				if(cells[x][y].getState() == CellState.NOT_MINE){
					int nbr = cells[x][y].getNumberOfAdjacentMines();
					int[] surroundings = getNumberOfMines(x, y);
					int knownMines = surroundings[0];
					int unknownCellNbr = surroundings[1];
					
					if(nbr == knownMines){
						command = new ClickCommand(i, j);
						addRestToQueue(x, y, i, j, ClickCommand.class.getName());
					
					}else if(unknownCellNbr +knownMines == nbr){
						command = new MarkCommand(i, j);
						addRestToQueue(x,y, i, j, MarkCommand.class.getName());
					}
					if(command != null)
						return command;
				}
			}
		}
		return command;
		
	}

	private void addRestToQueue(int x, int y, int i, int j, String className) {
		
		for(int m = 0; m < Constant.EIGHT_DIRECTIONS.length; m++){
			int row_nbr = x + Constant.EIGHT_DIRECTIONS[m][0];
			int col_nbr = y + Constant.EIGHT_DIRECTIONS[m][0];
			if(row_nbr >= 0 && row_nbr < board.getRow() && col_nbr >= 0 && col_nbr < board.getCol()){
				if(cells[row_nbr][col_nbr].getState() == CellState.UNKOWN){
					if(!(row_nbr == i && col_nbr == j)){
						
						if(className.equals(ClickCommand.class.getName()))
							queue.add(new ClickCommand(row_nbr, col_nbr));
						else{
							queue.add(new MarkCommand(row_nbr, col_nbr));
						}
					}
				}
			}
		}
		
	}


	private int[] getNumberOfMines(int x, int y) {
		int mines = 0;
		int unknownCellNbr = 0;
		for(int i = 0; i < Constant.EIGHT_DIRECTIONS.length; i++){
			int m = x + Constant.EIGHT_DIRECTIONS[i][0];
			int n = y + Constant.EIGHT_DIRECTIONS[i][1];
			if(m >= 0 && m < board.getRow() && n >= 0 && n < board.getCol()){
				if(cells[m][n].getState() == CellState.MINE)
					mines ++;
				else if(cells[m][n].getState() == CellState.UNKOWN)
					unknownCellNbr ++;
			}
		}
		int[] result = {mines, unknownCellNbr};
		return result;
	}

	@Override
	public void setBoard(Board b) {
		this.board = b;
	}

}
