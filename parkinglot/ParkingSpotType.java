package object_oriented_design.parkinglot;

public enum ParkingSpotType {
	
	REGULAR(5), HANDICAPED(5), COMPACT(3);
	
	private int size;
	
	ParkingSpotType(int size){
		this.size = size;
	}
	public int getSize() {
		return size;
	}
	
	public String getSpotType(){
		if(this == REGULAR){
			return Constant.REGULAR;
		}
		else if(this == HANDICAPED)
			return Constant.HANDICAPED;
		else
			return Constant.COMPACT;
	}
}
