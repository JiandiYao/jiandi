<?php
include_once('dbConnection.php');

$driver = $_POST['driver'];
$passenger = $_POST['passenger'];
$rating = $_POST['rating'];

$time = date("Y-m-d H:m:s");

$query ="INSERT INTO `rating`( `driver`, `passenger`, `rating`, `time`) 
	VALUES ('$driver', '$passenger', '$rating', '$time')";
	
$res=mysql_query($query) or die ("MySQL ERROR:".mysql_error());

if($res){
	$re["success"]=1;
}else{
	$re["success"]=0;
}

	$re = json_encode($re);
    	echo $re;

?>