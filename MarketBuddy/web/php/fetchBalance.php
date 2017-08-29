<?php

include_once('dbConnect.inc.php');

$query = "SELECT * 
FROM  `account` 
WHERE  `clientID` = '$name'";

$response = array();
    $res=mysql_query($query);
    $row=mysql_num_rows($res);
    if($row ){
         
		while( $client = mysql_fetch_array( $res )){
	
			$response[]=$client;
			
			}
			echo json_encode($response);
   }
?>