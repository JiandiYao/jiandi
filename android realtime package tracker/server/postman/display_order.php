<?php
require('dbConnection.php');
$order = array();
$response = array();

if($db_found){
	$query = "SELECT *
	FROM  `Order` 
	WHERE  `postID` = $name ";
	
	$result = mysql_query($query);
    /*
    while($re=mysql_fetch_array($result)){
        $response[] = $re;
    }
*/
	$found = mysql_num_rows($result);
	if(!$found){
		$response["success"] = 2;
	}else{
		while($re = mysql_fetch_array($result)){
			$order[] = $re;
		}
		for($i = 0; $i< count($order); $i++){
	
            $t = $order[$i]['customerID'];
			//select from customer table
			$query = "SELECT `lastname`, `firstname`, `address`, `postcode`, `phone`
			FROM  `Customer` WHERE  `customerID` = $t";
			$c_temp = mysql_query($query);
			//customer info result
			$c_result = mysql_fetch_array($c_temp);
            
            $response[$i] = array_merge($order[$i], $c_result);
		}
	}
}else{
	//cannot connecto to server
	$response["success"]=3;
}
echo json_encode($response);

?>