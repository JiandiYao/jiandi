<?php
session_start();
include_once('dbConnect.inc.php');
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
FROM  `client` 
WHERE  `username` = '$username' and `password`='$password'";

    $res=mysql_query($query);
    $checkUser=mysql_num_rows($res);
    if($checkUser){
         $_SESSION['LOGIN_STATUS']=true;
         $_SESSION['USERNAME']=$username;
         //echo 'correct';
		 header('Location: ../web/welcome.html');
    }else{
         echo ucwords('please enter correct user details');
    }
}
?>