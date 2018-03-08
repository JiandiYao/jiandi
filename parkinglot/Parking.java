package object_oriented_design.parkinglot;

public class Parking {

	
	//Entrance is at the middle of each Row/col
	//Next to the entrance, there are two handicaped parking
	
	
	ParkingSpot[][] parkingSpots;
	
	public Parking(){
		parkingSpots = new ParkingSpot[Constant.ROW][Constant.COL];
		for(int i = 0; i < Constant.ROW; i ++){
			for(int j = 0; j < Constant.COL; j++){
				ParkingSpot spot;
				if((i == Constant.ROW/2 || i == Constant.ROW/2+1) && (j == Constant.COL/2 || j == Constant.COL/2+1)){
					spot = new ParkingSpot(ParkingSpotType.HANDICAPED);
				}else if(i == Constant.ROW/2 || j == Constant.COL/2){
					spot = new ParkingSpot(ParkingSpotType.COMPACT);
				}else{
					spot = new ParkingSpot(ParkingSpotType.REGULAR);
				}
				parkingSpots[i][j] = spot;
				
			}
		}
	}

	public ParkingSpot[][] getParkingSpots() {
		return parkingSpots;
	}

	public void setParkingSpots(ParkingSpot[][] parking) {
		this.parkingSpots = parking;
	}
	
	
	
	
}
