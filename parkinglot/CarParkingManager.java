package object_oriented_design.parkinglot;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class CarParkingManager {

	Parking p;
	Vechicle[][] parkedVehicle;
	ConcurrentMap<String, int[][]> mapedVehicle;
	
	
	public CarParkingManager(Parking p){
		this.p = p;
		parkedVehicle = new Vechicle[Constant.ROW][Constant.COL];
		mapedVehicle = new ConcurrentHashMap<String, int[][]>();
	}
	
	
	public boolean canPark(Vechicle v, ParkingSpot spot){
		if(v.isHandicaped()){
			return spot.getType().getSize() >= v.getSize();
		}else{
			return !spot.getType().getSpotType().equals(Constant.HANDICAPED) && spot.getType().getSize() >= v.getSize();
		}
	}
	
	public void leaveParking(Vechicle v){
		int[][] location = mapedVehicle.get(v.getPlate());
		mapedVehicle.remove(v.getPlate());
		parkedVehicle[location[0][0]][location[0][1]] = null;
		p.getParkingSpots()[location[0][0]][location[0][1]].setVacant(true);
	}
	
	public int[][] parkACar(Vechicle v, int[][] entrance){
		
		ParkingSpot[][] spots = p.getParkingSpots();
		
		int[][] directions = {{1,0}, {0,1}, {-1,0}, {0,-1}};
		
		return bfs(v, spots, entrance, directions);
	}
	
	public int[][] bfs(Vechicle v, ParkingSpot[][] spots, int[][] entrance, int[][] directions){
		Queue<int[][]> queue = new LinkedList<int[][]>();
		queue.add(entrance);
		boolean[][] visited =  new boolean[Constant.ROW][Constant.COL];
		
		while(!queue.isEmpty()){
			int[][] location = queue.poll();
			visited[location[0][0]][location[0][1]] = true;
			
			for(int i = 0; i < directions.length; i++){
				int x = location[0][0] + directions[i][0];
				int y = location[0][1] + directions[i][1];
				int[][] result = new int[1][2];
				result[0][0] = x;
				result[0][1] = y;
				
				if(!visited[x][y] &&  x >= 0 && x < Constant.ROW && y >= 0  && y < Constant.COL){
					synchronized(spots[x][y]){
						if(canPark(v, spots[x][y])){
							parkedVehicle[x][y] = v;
							spots[x][y].setVacant(false);
							mapedVehicle.put(v.getPlate(), result);
							return result;
						}
						queue.add(result);
					}
					
				}
			}
		}
		return null;
	}
}
