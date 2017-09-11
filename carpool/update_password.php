<?php
include_once('dbConnection.php');

$username = $_POST['username'];
$password = $_POST['password'];

$re=array();

$query1="UPDATE `user` SET  `password` = '$password'
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