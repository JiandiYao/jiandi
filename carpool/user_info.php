<?php
//session_start();
include_once('dbConnection.php');

$query = "SELECT * 
FROM  `user` 
WHERE  `username` = '$name'";

$res=mysql_query($query) or die ("MySQL ERROR:".mysql_error());
$checkUser=mysql_num_rows($res);
if($checkUser){
	$user = mysql_fetch_array($res);
	$re["success"]=1;
	$re["username"]=$user['username'];
	$re["name"]=$user['name'];
	$re["phone"]=$user['phone'];
	
	$re["type"]=$user['type'];
	if($user['type'] != 3)
		$re["car"] = $user['car'];
	
	$re["create_time"]=$user['create_time'];
	 
}else{
 	$re["success"]=0;
 
}
	$re = json_encode($re);
	echo $re;
?>