<?php
/*
session_start();
  if(!isset($_SESSION['LOGIN_STATUS'])){
      header('location:../web/index.html');
  }
*/
include_once('dbConnect.inc.php');

$query = "SELECT * 
FROM  `client` 
WHERE  `username` = '$name'";

    $res=mysql_query($query);
    $row=mysql_num_rows($res);
    if($row ){
         
		while( $client = mysql_fetch_array( $res )){
	
			$response=$client;
			$response=json_encode($response);
			echo $response;
			}
   }
?>