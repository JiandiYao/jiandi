package object_oriented_design.parkinglot;

public class ParkingSpot {

	private ParkingSpotType type;
	private boolean isVacant;
	
	public ParkingSpot(ParkingSpotType type){
		this.type = type;
		isVacant = true;
	}

	public ParkingSpotType getType() {
		return type;
	}

	public void setType(ParkingSpotType type) {
		this.type = type;
	}

	public boolean isVacant() {
		return isVacant;
	}

	public void setVacant(boolean isVacant) {
		this.isVacant = isVacant;
	}
	
	
}
