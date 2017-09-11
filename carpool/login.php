<?php
//session_start();
include_once('dbConnection.php');
$message=array();
if(isset($_POST['username']) && !empty($_POST['username'])){
    $username=mysql_real_escape_string($_POST['username']);
}else{
    $message[]='Please enter username';
}

if(isset($_POST['password']) && !empty($_POST['password'])){
    $password=mysql_real_escape_string($_POST['password']);
}else{
    $message[]='Please enter password';
}

$countError=count($message);

if($countError > 0){
     for($i=0;$i<$countError;$i++){
              echo ucwords($message[$i]).'<br/><br/>';
     }
}else{
    $query = "SELECT * 
	FROM  `user` 
	WHERE  `username` = '$username' and `password`='$password'";

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
}
?>