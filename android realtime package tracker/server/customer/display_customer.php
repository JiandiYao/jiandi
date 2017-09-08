<?php
require('dbConnection.php');
$response = array();
if($db_found){
	$query = "SELECT * 
	FROM  `Order` 
	WHERE  `customerID` = '$name' ";
	
	$result = mysql_query($query) or die("MySQL error:".mysql_error());
	$found = mysql_num_rows($result);
	if($found){
		while($order = mysql_fetch_array($result)){
		  $response[] = $order;
		}
        
        /*
		$response["success"]=1;
		
		$response["orderID"]=$order['orderID'];
		$response["productName"]=$order['productName'];
		$response["status"]=$order['status'];
		$response["link"]=$order['link'];
		$response["description"]=$order['description'];
		$response["proNum"]=$order['proNum'];
		$response["customerID"]=$order['customerID'];
        */

	}else{
		$response["success"]=0;//No order information returned

	}
	
	echo json_encode($response);

}
else{
	$response["success"]=3; //cannot connect to server
	//$re["message"] = "Can not connect to database";
	$response= json_encode($response);
	echo $response;
}
?>