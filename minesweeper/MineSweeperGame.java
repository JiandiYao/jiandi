package object_oriented_design.minesweeper;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Queue;

public class MineSweeperGame extends Game{
	private int col;
	private int row;
	private int numberOfMines;

	private Map<String, Board> boards;
	private Map<String,Player> players;
	private Map<String, List<Position>> moveHistory;
	private Map<String, List<Position>> checkedSafeCells;
	private Map<String, List<Position>> checkedMines;
	
	
	public MineSweeperGame() {
		try {
			FileReader reader = new FileReader("minesweeper.properties");
			Properties p = new Properties();
			p.load(reader);
			
			col = new Integer(p.getProperty(Constant.COL));
			row = new Integer(p.getProperty(Constant.ROW));
			numberOfMines = new Integer(p.getProperty(Constant.NUMBER_OF_MINES));
			
			boards = new HashMap<String, Board>();
			players = new HashMap<String, Player>();
			moveHistory = new HashMap<String, List<Position>>();
			checkedSafeCells = new HashMap<String, List<Position>>();
			
			checkedMines = new HashMap<String, List<Position>>();
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public Player setPlayer(String name) {
		Player player;
		
		if(name.toLowerCase().contains(Constant.DUMMY_COMPUTER_PLAYER))
			player =  new DummyComputerPlayer(name);
		else
			player = new SmartComputerPlayer(name);
		
		players.put(player.getPlayerName(), player);
		
		return player;
		
	}

	private Board createBoard(String playerName) {
		Board board = new Board(row, col, numberOfMines);
		boards.put(playerName, board);
		return board;
	}

	@Override
	public void startGame(String playerName) {
		// TODO Auto-generated method stub
		Board board = createBoard(playerName);
		Strategy strategy;
		Command command;
		
		command = new ClickCommand(0,0);
		command.setBoard(board);
		int steps = 0;
		
		boolean result = command.move();
		
		printBoard(board);
		
		while(result){
			steps ++;
			
			int x = command.getX();
			int y = command.getY();
			if(command instanceof ClickCommand){
				if(checkedSafeCells.containsKey(playerName))
					checkedSafeCells.get(playerName).add(new Position(x,y));
				else{
					List<Position> list = new ArrayList<Position>();
					list.add(new Position(x,y));
					checkedSafeCells.put(playerName, list);
				}
			}else if(command instanceof MarkCommand){
				if(checkedMines.containsKey(playerName))
					checkedMines.get(playerName).add(new Position(x,y));
				else{
					List<Position> list = new ArrayList<Position>();
					list.add(new Position(x,y));
					checkedMines.put(playerName, list);
				}
			}
			
			
			if(command instanceof ClickCommand)
				checkNeighborsAndSet(x, y, board);
			
			Position p = new Position(x,y);
			
			if(!moveHistory.containsKey(playerName)){
				List<Position> positions = new ArrayList<Position>();
				positions.add(p);
				moveHistory.put(playerName, positions);
			}else{
				moveHistory.get(playerName).add(p);
			}
			
			
			printBoard(board);
			
			if(playerName.contains(Constant.DUMMY_COMPUTER_PLAYER)){
				strategy = new DummyStrategy();
			}
			else{
				strategy = new SmartStrategy();
			}
			strategy.setBoard(board);
			command = strategy.getCommand();
			command.setBoard(board);
			result = command.move();
			String moveName = command.getClass().getName();
			
			
			System.out.println("Move is: "+ moveName+ " at position x="+command.getX()+" y="+command.getY());
			
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		if(steps == row*col){
			System.out.println("Congratulations, you win");
		}else{
			System.out.println("You failed");
		}
	}

	//bfs search and set cells to be checked
	//if a mine is found on this level, stopped for checking and only set lower levels to be clicked
	private void checkNeighborsAndSet(int x, int y, Board board) {
		
		Queue<Cell> queue = new LinkedList<Cell>();
		Queue<Integer> levels = new LinkedList<Integer>();
		Cell[][] cells = board.getCells();
		queue.add(cells[x][y]);
		levels.add(0);
		boolean[][] visited = new boolean[row][col];
		visited[x][y] = true;
		int level = 0;
		boolean meetAMine = false;
		
		while(!queue.isEmpty() && !meetAMine){
			Cell cell = queue.poll();
			level = levels.poll();
			int m = cell.getPosition().getX();
			int n = cell.getPosition().getY();
			
			cell.setState(CellState.NOT_MINE);
			
			if(cell.getNumberOfAdjacentMines() != -1){
				for(int i = 0; i < Constant.EIGHT_DIRECTIONS.length; i++){
					int j = m+Constant.EIGHT_DIRECTIONS[i][0];
					int k = n+Constant.EIGHT_DIRECTIONS[i][1];
					
					if(j >= 0 && j <row && k >= 0 && k<col && !visited[j][k]){
						if(cells[j][k].getNumberOfAdjacentMines() == -1){
							meetAMine = true;
							break;
						}
						queue.add(cells[j][k]);
						//System.out.println("Put x="+j+" y="+k + " into queue with level="+(level+1));
						levels.add(level+1);
						visited[j][k] = true;
					}
				}
			}
		}
		
		while(!queue.isEmpty() && levels.poll() == level+1){
			queue.poll();
		}
		
		while(!queue.isEmpty()){
			Cell cell = queue.poll();
			cell.setState(CellState.NOT_MINE);
		}
		
	}

	private void printBoard(Board board) {
		
		System.out.println();
		System.out.println("_____________________________");
		System.out.println();
		
		Cell[][] cells = board.getCells();
		for(int i = 0; i < row; i++){
			for(int j = 0; j < col; j++){
				if(cells[i][j].getState() == CellState.NOT_MINE){
					int number = cells[i][j].getNumberOfAdjacentMines();
					
					System.out.print(number + " ");//THe number of adjacent mines
					
				}else if(cells[i][j].getState() == CellState.MINE){
					System.out.print("* ");//marked as a mine
				}else{
					System.out.print("# ");//Haven't been checked
				}
			}
			System.out.println();
		}
		
		System.out.println();
		System.out.println("_____________________________");
		System.out.println();
		
	}

	@Override
	public void getCurrentState() {
		// TODO Auto-generated method stub
		
	}


	
}
