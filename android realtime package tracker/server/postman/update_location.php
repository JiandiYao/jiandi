<?php
        require('dbConnection.php');
        if (!empty($_POST)) {
        
        $lon   = $_POST['lon'];
        $lat   = $_POST['lat'];
        $time  = $_POST['time'];
        $date = $_POST['date'];
        $eTime = $_POST['eTime'];
        $eDate = $date;
        $orderID = $_POST['orderID'];
        
        $lon=!empty($lon) ? "'$lon'" : "NULL";
        $lat=!empty($lat) ? "'$lat'" : "NULL";
        $time=!empty($time) ? "'$time'" : "NULL";
        $date=!empty($date) ? "'$date'" : "NULL";
        $eTime=!empty($eTime) ? "'$eTime'" : "NULL";
        $orderID=!empty($orderID) ? "'$orderID'" : "NULL";
   
        
        if($db_found){
      
        	$query = "UPDATE `PostmanLocation` 
            SET `longitude`= '$lon',`latitude`= '$lat',`time`= '$time', `date`='$date'
            WHERE `postID`= '$name'";

           /*
            $query = "UPDATE `PostmanLocation` 
            SET `longitude`= '42.293',`latitude`= '-42.293',`time`= '10:00:30', `date`='2013-12-30'
            WHERE `postID`= '1'";
            */
        $result = mysql_query($query);
          
		$num = mysql_affected_rows();
            if($num){
          
                    $query = "
                    UPDATE `Order` 
                    SET `eTime`= '$eTime',`eDate`= '$eDate' 
                    WHERE `orderID` = '$orderID'";
                    
          
                $result = mysql_query($query);
             	$num = mysql_affected_rows();
                
                    if($num){
                        $response["success"] = 1;
                          
                    } else{  
                        $response["success"] =0;   
                    }
        	
            }
            echo json_encode($response);
         }
 } else{
?>


<html>
<head>
<title>Basic Login Script</title>
</head>
<body>

<FORM NAME ="Postman Login" METHOD ="POST" ACTION ="update_location.php">

Lon: <INPUT TYPE = 'TEXT' Name ='lon'  value="<?PHP print $lon;?>" maxlength="45">
Lat: <INPUT TYPE = 'TEXT' Name ='lat'  value="<?PHP print $lat;?>" maxlength="45">
Time: <INPUT TYPE = 'TEXT' Name ='time'  value="<?PHP print $time;?>" maxlength="45">
Date: <INPUT TYPE = 'TEXT' Name ='date'  value="<?PHP print $date;?>" maxlength="45">
eTime: <INPUT TYPE = 'TEXT' Name ='eTime'  value="<?PHP print $eTime;?>" maxlength="45">
orderID: <INPUT TYPE = 'TEXT' Name ='orderID'  value="<?PHP print $orderID;?>" maxlength="45">

<P align = center>
<INPUT TYPE = "Submit" Name = "Submit"  VALUE = "Login">
</P>

</FORM>

</body>
</html>

	<?php
}

?>

