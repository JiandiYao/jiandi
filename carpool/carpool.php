<?php
//session_start();
include_once('dbConnection.php');

$query = "SELECT * 
FROM  `carpool` 
WHERE  `date` >= NOW()";

$res=mysql_query($query) or die ("MySQL ERROR:".mysql_error());
$check=mysql_num_rows($res);
if($check){
	$re["success"]=1;	
	while($car = mysql_fetch_array($res))
		$re[] = $car;
	

}else{
 	$re["success"]=0;
 
}
	$re = json_encode($re);
	echo $re;
?>