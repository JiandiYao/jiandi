<?php
include_once('dbConnection.php');

$username = $_POST['username'];

$dep_city = $_POST['dep_city'];
$des_city = $_POST['des_city'];

$pickup = $_POST['pickup'];
$pick_la = $_POST['pick_la'];
$pick_lo = $_POST['pick_lo'];

$dropoff = $_POST['dropoff'];
$drop_la = $_POST['drop_la'];
$drop_lo = $_POST['drop_lo'];

$price = $_POST['price'];
$date = $_POST['time'];

/*split time into year, month, date, hour, min*/
/*
$temp= explode("-", $time);

$year = $temp[0];
$month = $temp[1];
$temp = explode(" ", $temp[2]);
$date = $temp[0];

$temp = $temp[1];
$temp = explode(":", $temp);
$hour = $temp[0];
$minute = $temp[1];
*/
/*
$query ="INSERT INTO `carpool`( `dep_city`, `des_city`, `pickup`, 
	`pick_la`, `pick_lo`, `dropoff`, `drop_la`, `drop_lo`, 
	`price`, `year`, `month`,`date`, `hour`, `minute`, `driver`) 
	
	VALUES ('$dep_city', '$des_city', '$pickup', '$pick_la', 
	'$pick_lo', '$dropoff', '$drop_la', '$drop_lo', '$price', 
	'$year', '$month', '$date', '$hour', '$minute', '$username')";	
*/
$query ="INSERT INTO `carpool`( `dep_city`, `des_city`, `pickup`, 
	`pick_la`, `pick_lo`, `dropoff`, `drop_la`, `drop_lo`, 
	`price`, `date`, `driver`) 	
	VALUES ('$dep_city', '$des_city', '$pickup', '$pick_la', 
	'$pick_lo', '$dropoff', '$drop_la', '$drop_lo', '$price', 
	'$date', '$username')";

$res=mysql_query($query) or die ("MySQL ERROR:".mysql_error());

if($res){
	$re["success"]=1;
}else{
	$re["success"]=0;
}

	$re = json_encode($re);
    	echo $re;

?>