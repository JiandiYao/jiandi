<?php
include_once('dbConnection.php');

/*coarse evaluation to simplify database search*/
$perimeter = 40000;//km
$degree = 360*$radius/$perimeter;

$max_lat = $location_lat+$degree;
if($max_lat > 90){
	$max_lat = 180-$max_lat;
}
$min_lat = $location_lat-$degree;
if ($min_lat < -90){
	$min_lat = -180-$min_lat;
}

$max_lon = $location_lon+$degree;
if($max_lon > 180){
	$max_lon = -360+$max_lon;
}
$min_lon = $location_lon-$degree;

if ($min_lon < -180){
	$min_lon = 360+$min_lon;
}

$query = "SELECT * 
FROM  `carpool` 
WHERE `pick_la` <= '$max_lat' AND `pick_la` >= '$min_lat' AND
`pick_lo` <= '$max_lon' AND `pick_lo` >= '$min_lon' AND `date` >= NOW() ";



$res=mysql_query($query) or die ("MySQL ERROR:".mysql_error());
$check=mysql_num_rows($res);
if($check){
	
	while($car = mysql_fetch_array($res))
		$resource[] = $car;
	$j = 0;
	for($i = 0; $i < count($resource); $i++){
		if(getDistance($location_lat, $location_lon, $resource[$i]['pick_la'], $resource[$i]['pick_lo']) <= $radius){
			$re[$j] = $resource[$i];
			$j++;
		}
	}	
	if(count($re)>0)
		$re["success"]=1;
	else
		$re["success"]=0;	
			
}else{
 	$re["success"]=0;
 
}
$re = json_encode($re);
echo $re;
	
	
function getDistance($latitude1, $longitude1, $latitude2, $longitude2) {  
    $earth_radius = 6371;  
      
    $dLat = deg2rad($latitude2 - $latitude1);  
    $dLon = deg2rad($longitude2 - $longitude1);  
      
    $a = sin($dLat/2) * sin($dLat/2) + cos(deg2rad($latitude1)) * cos(deg2rad($latitude2)) * sin($dLon/2) * sin($dLon/2);  
    $c = 2 * asin(sqrt($a));  
    $d = $earth_radius * $c;  
      
    return $d;  
}
?>