<?PHP
session_start();

require('dbConnection.php');


$username = "";
$password = "";
$re = array();
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
	$username = $_POST['username'];
	$password = $_POST['password'];

	$username = htmlspecialchars($username);
	$password = htmlspecialchars($password);



	if ($db_found) {

		$username = quote_smart($username, $db_handle);
		$password = quote_smart($password, $db_handle);

$query = "SELECT * 
FROM  `Customer` 
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
		  $re["user"]["customerID"]=$user['customerID'];
		  $re["user"]["firstname"]=$user['firstname'];
		  $re["user"]["lastname"] = $user['lastname'];
		  $re["user"]["address"]=$user['address'];
		  $re["user"]["postcode"]=$user['postcode'];
		  $re["user"]["phone"]=$user['phone'];
	
		  $re= json_encode($re);
		  echo $re;

			
			$_SESSION['login'] = "1";
			$_SESSION["re"]=$re;
			//header ("Location: userdata.php");
			
		}
		else {
		$re["success"]=0;
		$re["message"] = "User Not Found";
		$re = json_encode($re);
		echo $re;
		}
	}

	else {
		$re["success"]=0;
		$re["message"] = "Can not connect to database";
		$re= json_encode($re);
		echo $re;
	}
}

?>


<html>
<head>
<title>Basic Login Script</title>
</head>
<body>

<FORM NAME ="Customer Login" METHOD ="POST" ACTION ="login.php">

Username: <INPUT TYPE = 'TEXT' Name ='username'  value="<?PHP print $username;?>" maxlength="45">
Password: <INPUT TYPE = 'TEXT' Name ='password'  value="<?PHP print $password;?>" maxlength="45">

<P align = center>
<INPUT TYPE = "Submit" Name = "Submit"  VALUE = "Login">
</P>

</FORM>

</body>
</html>