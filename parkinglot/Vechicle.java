package object_oriented_design.parkinglot;

public abstract class Vechicle {
	protected int size;
	protected boolean isHandicaped;
	protected String plate;
	
	public Vechicle(String plate, int size, boolean isHandicaped){
		this.plate = plate;
		this.size = size;
		this.isHandicaped = isHandicaped;
	}
	public int getSize(){
		return this.size;
	}
	public boolean isHandicaped() {
		return isHandicaped;
	}
	public String getPlate() {
		return plate;
	}
	
	
}
