<?php
require('dbConnection.php');
if($db_found){
	$query = "SELECT * 
	FROM  `Order` 
	WHERE  `orderID` =  '$name'";
	
	$result = mysql_query($query) or die("MySQL error:".mysql_error());

	$temp = mysql_fetch_array($result);

    $postID = $temp['postID'];

	$mysql = "SELECT * FROM `PostmanLocation` WHERE `postID`= '$postID'";
	$re = mysql_query($mysql)  or die("MySQL error:".mysql_error());

	$found1 = mysql_num_rows($re); 
 
	$found2 = mysql_num_rows($result);
  
  if($found1 && $found2){
    
	$location = mysql_fetch_array($re);
 	$response["success"]=1;
	$response["estimatedTime"] = $temp['estimatedArrivalTime'];
	$response["longitude"] = $location['longitude'];
	$response["latitude"] = $location['latitude'];
	$response["time"] = $location['time'];

    
  }else{
    $response["success"]=0;//No order information returned

  }
	echo json_encode($response);
}
?>