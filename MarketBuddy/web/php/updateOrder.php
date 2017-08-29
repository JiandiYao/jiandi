<?php

include_once('dbConnect.inc.php');
$balance = $_POST['newBalance'];
$share = $_POST['newShare'];
$accountID = $_POST['newAccountID'];

$response=array();

$query = "
UPDATE  `stock_order` SET  `share` =  '$share' WHERE  `orderNo` =  '$name'";

$sql = "
UPDATE  `account` SET  `balance` =  '$balance' WHERE  `accountID` =  '$accountID'";

$result = mysql_query($sql);

    $res=mysql_query($query);
  
	
    if($result && $res){
			$response["success"] = 7;
		
			}
	else{
		$response["success"] = 3;
	}
			echo json_encode($response);
   
?>