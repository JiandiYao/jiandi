package object_oriented_design.minesweeper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Board {

	private int row;
	private int col;
	private int numberOfMines;
	
	private Cell[][] cells;
	
	private List<Position> mines;
	
	public Board(int row, int col, int numberOfMines) {
		this.row = row;
		this.col = col;
		this.numberOfMines = numberOfMines;
		cells = new Cell[row][col];
		mines = new ArrayList<Position>();
		
		init();
		
		generateMines();
		//printBoard();
		calculateAdjacentMineNbr();
		printBoard();
	}

	private void calculateAdjacentMineNbr() {
		int[][] directions = {{0,1},{0,-1}, {1,0},{-1,0},{1,1}, {1,-1}, {-1,1}, {-1,-1}};
		for(int i = 0; i < row; i++){
			for(int j = 0; j < col; j++){
				if(cells[i][j].getNumberOfAdjacentMines() != -1){
					int count = 0;
					for(int k = 0; k < 8; k++){
						if(i+directions[k][0] >=0 && i+directions[k][0] < row 
								&& j+directions[k][1]>= 0 && j + directions[k][1] < col){
							if(cells[i+directions[k][0]][j+directions[k][1]].getNumberOfAdjacentMines() == -1)
								count ++;
						}
					}
					cells[i][j].setNumberOfAdjacentMines(count);
				}
			}
		}
		
	}

	private void init() {
		for(int i = 0; i < row; i++){
			for(int j = 0; j < col; j++){
				Cell cell = new Cell(i, j);
				cells[i][j] = cell;
			}
		}
	}

	private void generateMines() {
		Random ran = new Random();
		for(int i = 0; i < numberOfMines; ){
			int x = ran.nextInt(row);
			int y = ran.nextInt(col);
			if(cells[x][y].getNumberOfAdjacentMines() == 0){
				cells[x][y].setNumberOfAdjacentMines(-1);
				//System.out.println("Set mine at x="+x+" y="+y);
				i++;
				mines.add(new Position(x,y));
			}
		}
		
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}

	public int getNumberOfMines() {
		return numberOfMines;
	}

	public Cell[][] getCells() {
		return cells;
	}

	public List<Position> getMines() {
		return mines;
	}

	private void printBoard(){
		System.out.println();
		System.out.println("_____________________________");
		System.out.println();
		
		for(int i = 0; i< row; i++){
			for(int j = 0; j < col; j++){
				System.out.print(cells[i][j].getNumberOfAdjacentMines()+" ");
				
			}
			System.out.println();
		}
		
		System.out.println();
		System.out.println("_____________________________");
		System.out.println();
		
	}
}
