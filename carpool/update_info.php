<?php
include_once('dbConnection.php');

$username = $_POST['username'];

$name = $_POST['name'];
$phone = $_POST['phone'];
$type = $_POST['type'];
if($_POST['type'] != 3 || $_POST['type'] != NULL)
	$car = $_POST['car'];
else
	$car = "NULL";

$re=array();

$query1="UPDATE `user` SET  `name` = '$name', 
 `phone` =  '$phone', `car` =  '$car', `type` =  '$type' 
 WHERE   `username` =  '$username'";

$res=mysql_query($query1) or die ("MySQL ERROR:".mysql_error());

if(!$res){
	$re["success"]=0;
}else{
	$re["success"]=1;

}
	$re = json_encode($re);
    	echo $re;

?>