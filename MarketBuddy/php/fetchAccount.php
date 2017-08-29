<?php
/*
session_start();
  if(!isset($_SESSION['LOGIN_STATUS'])){
      header('location:../web/index.html');
  }
*/
include_once('dbConnect.inc.php');

$query = "SELECT * 
FROM  `account` INNER JOIN `stock_order` ON account.accountID = stock_order.accountID
WHERE  `clientID` = '$name'";
/*
$query = "SELECT * 
FROM  `account` 
WHERE  `clientID` = '5'";
*/
    $res=mysql_query($query);
    $row=mysql_num_rows($res);
    if($row ){
         
		 while($client = mysql_fetch_array( $res )){

		$data[] = $client;
		
			}
			echo json_encode($data);
		}
   
?>