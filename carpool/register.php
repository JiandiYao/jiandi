<?php
include_once('dbConnection.php');

$username = $_POST['username'];
$password = $_POST['password'];
$name = $_POST['name'];
$phone = $_POST['phone'];
$type = $_POST['type'];
if($_POST['type'] != 3 || $_POST['type'] == NULL)
	$car = $_POST['car'];
else
	$car = "NULL";

$response=array();

$query1="SELECT * 
FROM `user` 
WHERE `username`='$username'";
$res=mysql_query($query1) or die ("MySQL ERROR:".mysql_error());
$checkUser=mysql_num_rows($res);

if($checkUser){
	$re["success"]=0;
}else{

	$today = date("Y-m-d H:m:s");
	$query2 ="
	INSERT INTO `user`( `username`, `password`, `name`, `phone`, `type`, `car`, `create_time`) 
	VALUES ('$username', '$password', '$name', '$phone', '$type', '$car', '$today')";
	
	$rest=mysql_query($query2);
	if($rest)
		$re["success"]=1;
	else
		$re["success"]=2;
}

	$re = json_encode($re);
    	echo $re;

?>