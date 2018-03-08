package object_oriented_design.minesweeper;

public class DummyComputerPlayer implements Player{

	private String playerName;
	public DummyComputerPlayer(String playerName){
		this.playerName = playerName;
	}
	@Override
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
	@Override
	public String getPlayerName() {
		return this.playerName;
	}

}
