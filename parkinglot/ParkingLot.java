package object_oriented_design.parkinglot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

//Design a system for a parking lot where drivers can also have memberships 
//(but also support guest drivers).
//The parking lot has counter screens on each row.


public class ParkingLot {

	private MembershipManager manager;
	private Map<Integer, Row> rows;
	
	public ParkingLot(){
		manager = new MembershipManager();
		rows = new HashMap<Integer, Row>();
	}
	
	public void addMembership(Vehcile v, MembershipType type){
		Membership m = new Membership(v, type);
		manager.addMembership(m);
	}
	
	public void removeMembership(Vehcile v){
		manager.removeMembership(v);
	}
	
	public boolean hasParkingSpots(){
		for(Row r: rows.values()){
			if(r.getVacancy() != 0)
				return true;
		}
		return false;
	}
	
	public int getVacancyForRow(int rowId){
		return rows.get(rowId).getVacancy();
	}
	
	public void parkACar(int rowId, int spotId){
		
	}
}

class MembershipManager{
	//private ParkingLot parkingLot;
	private List<Membership> memberships;
	
	public MembershipManager(){
		//this.parkingLot = parkingLot;
		memberships = new ArrayList<Membership>();
		
	}

	public List<Membership> getMemberships() {
		return memberships;
	}

	public void addMembership(Membership membership) {
		memberships.add(membership);
	}
	
	public void removeMembership(Membership membership){
		memberships.remove(membership);
	}
	
	public void removeMembership(Vehcile v){
		Iterator<Membership> it = memberships.iterator();
		while(it.hasNext()){
			if(it.next().getV().getPlate().equals(v.getPlate())){
				memberships.remove(it.next());
				return;
			}
		}
	}
	
}
class Row{
	private int rowId;
	private List<Spot> spots;

	private int vacancy;
	
	public Row(int rowId, int vacancy){
		this.rowId = rowId;
		this.vacancy = vacancy;
		spots = new ArrayList<Spot>();
		
	}
	public int getRowId() {
		return rowId;
	}

	public List<Spot> getSpots() {
		return spots;
	}

	
	public void addSpot(Spot spot){
		spots.add(spot);
		if(spot.isVacant())
			this.vacancy ++;
	}
	
	public int getVacancy() {
		return vacancy;
	}
	
	public void setSpotVacant(int spotId){
		for(Spot spot: spots){
			if(spot.getSpotId() == spotId){
				spot.setVacant(true);
				vacancy ++;
			}
		}
	}
	
	public void setSpotOccupied(int spotId){
		for(Spot spot: spots){
			if(spot.getSpotId() == spotId){
				spot.setVacant(false);
				vacancy --;
			}
		}
	}
	
}
class Spot{
	private int spotId;
	
	private Type type;
	private boolean isVacant;
	
	
	public int getSpotId() {
		return spotId;
	}
	public void setSpotId(int spotId) {
		this.spotId = spotId;
	}
	public Type getType() {
		return type;
	}
	public void setType(Type type) {
		this.type = type;
	}
	public boolean isVacant() {
		return isVacant;
	}
	public void setVacant(boolean isVacant) {
		this.isVacant = isVacant;
	}
	
}
enum Type{
	BIG_SPOT,
	WHEELCHAIR_SPOT
	
}

class Vehcile {
	private String plate;
	private int size;

	public Vehcile(String plate, int size){
		this.plate = plate;
		this.size = size;
	}

	public String getPlate() {
		return plate;
	}

	public void setPlate(String plate) {
		this.plate = plate;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
	
}

class Membership{
	private String memberId;
	private MembershipType type;
	private Vehcile v;
	public Membership(Vehcile v, MembershipType type){
		this.v = v;
		this.type = type;
		this.memberId = v.getPlate();
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public MembershipType getType() {
		return type;
	}
	public void setType(MembershipType type) {
		this.type = type;
	}
	public Vehcile getV() {
		return v;
	}
	public void setV(Vehcile v) {
		this.v = v;
	}
	
	
	
}

enum MembershipType{
	GOLD, PLATUIUM;
	
	private String name;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	private double price;
	
	
}

