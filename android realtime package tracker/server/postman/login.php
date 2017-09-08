<?PHP
session_start();
require('dbConnection.php');

$username = "";
$password = "";

//==========================================
//	ESCAPE DANGEROUS SQL CHARACTERS
//==========================================
function quote_smart($value, $handle) {

   if (get_magic_quotes_gpc()) {
       $value = stripslashes($value);
   }

   if (!is_numeric($value)) {
       $value = "'" . mysql_real_escape_string($value, $handle) . "'";
   }
   return $value;
}

if ($_SERVER['REQUEST_METHOD'] == 'POST'){
	$username = $_POST['uname'];
	$password = $_POST['pword'];

	$username = htmlspecialchars($username);
	$password = htmlspecialchars($password);



	if ($db_found) {

		$username = quote_smart($username, $db_handle);
		$password = quote_smart($password, $db_handle);
		
		$query = $query = "SELECT * 
		FROM  `Postman` 
		WHERE  `username` = $username";
		
		$info = mysql_query($query);
		$found = mysql_num_rows($info);
		if(!$found){
			//user not found
			$response["success"] = 2;
			echo json_encode($response);
		}else{

			$query = "SELECT * 
			FROM  `Postman` 
			WHERE  `username` = $username  AND `password`= $password ";
		
			$info = mysql_query($query) or die("MySQL ERROR: ".mysql_error());
			$found = mysql_num_rows($info);
			//====================================================
			//	CHECK TO SEE IF THE $result VARIABLE IS TRUE
			//====================================================

			if ($found) {
				$user = mysql_fetch_array( $info );
	
				$re["success"]=1;
  
				//add parameters that will return to android
				$re["postID"]=$user['postID'];
				$re["firstname"]=$user['firstname'];
				$re["lastname"] = $user['lastname'];
				$re["phone"]=$user['phone'];
			
				$_SESSION['log'] = true;
				$_SESSION["re"]=$re;
				
				$re= json_encode($re);
				echo $re;	
			}
			else {
				//wrong password
				$re["success"]=3;
				$re = json_encode($re);
				echo $re;
			}
		}
	}
}

?>


<html>
<head>
<title>Basic Login Script</title>
</head>
<body>

<FORM NAME ="Postman Login" METHOD ="POST" ACTION ="login.php">

Username: <INPUT TYPE = 'TEXT' Name ='uname'  value="<?PHP print $uname;?>" maxlength="11">
Password: <INPUT TYPE = 'TEXT' Name ='pword'  value="<?PHP print $pword;?>" maxlength="45">

<P align = center>
<INPUT TYPE = "Submit" Name = "Submit"  VALUE = "Login">
</P>

</FORM>

</body>
</html>