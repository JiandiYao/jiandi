<?php

include_once('dbConnect.inc.php');


$query = "
DELETE FROM `stock_order` 
WHERE `orderNo` = '$name';";

$res = mysql_query($query);
$response = array();

if(! $res )
{
  $reponse["success"] = 2;
  
}else{
	$response["success"] = 6;
}
echo json_encode($response);
   

?>