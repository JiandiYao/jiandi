<?php

$accountType = $_POST['accountType'];
$stockSymbol = $_POST['stockSymbol'];
$bidDate = $_POST['bidDate'];
$bidTime = $_POST['bidTime'];
$share = $_POST['share'];
$balance = $_POST['balance'];
$bidPrice = $_POST['bidPrice'];

include_once('dbConnect.inc.php');
$response=array();

$query = "
UPDATE  `account` SET  `balance` =  '$balance' WHERE  `clientID` =  '$clientID' AND  `accountType` =  '$accountType'";

$mysql = "
SELECT `accountID` FROM `account` WHERE `clientID`='$clientID' AND `accountType`= '$accountType'";

/*
$query = "
UPDATE  `account` SET  `balance` =  '3112' WHERE  `clientID` =  '1' AND  `accountType` =  'traditional'";

$mysql = "
SELECT `accountID` FROM `account` WHERE `clientID`='1' AND `accountType`= 'traditional'";
*/
$result = mysql_query($query);

$rest = mysql_query($mysql);
$temp = mysql_fetch_array($rest);
$accountID = $temp['accountID'];

//echo $accountID;

$sql = "
INSERT INTO `stock_order`( `accountID`, `share`, `bidPrice`, `bidTime`, `bidDate`, `stockSymbol`) 
VALUES ('$accountID', '$share', '$bidPrice', '$bidTime', '$bidDate', '$stockSymbol')";
/*
$sql = "
INSERT INTO `stock_order`( `accountID`, `share`, `bidPrice`, `bidTime`, `bidDate`, `stockSymbol`) 
VALUES ('$accountID', '12303', '1230.9', '12:00:00', '2013-10-03', 'GOOG')";
 */ 
$res=mysql_query($sql);

  if($result && $res){
			$response["success"] = 8;
		
			}
	else{
		$response["success"] = 1;
	}
			echo json_encode($response);
 
?>