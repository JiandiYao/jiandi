<?php

include_once('dbConnect.inc.php');

$query = "SELECT  `stockSymbol`
FROM  `stockinhold` 
WHERE  `clientID` = '$name'";

    $res=mysql_query($query);
 
    $row=mysql_num_rows($res);
 
    if($row ){
         
		 while($client = mysql_fetch_array( $res )){
			$data[] = $client;

		 }
		 echo json_encode($data);
   }
?>