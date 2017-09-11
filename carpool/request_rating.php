<?php
include_once('dbConnection.php');

$query1 = "SELECT * from `rating` 
	WHERE  `driver` = '$name'";
$query ="SELECT avg(rating) as avg_rating from `rating` 
	WHERE  `driver` = '$name'";
	
$res=mysql_query($query1) or die ("MySQL ERROR:".mysql_error());
$checkUser=mysql_num_rows($res);

if($checkUser){
	$res=mysql_query($query) or die ("MySQL ERROR:".mysql_error());
	$rate = mysql_fetch_array($res);
	$re["success"]=1;
	$re["rating"]=$rate['avg_rating'];
	$re["driver"]=$name;
}else{
	$re["success"]=0;
}

	$re = json_encode($re);
    	echo $re;

?>