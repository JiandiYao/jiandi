<?php
$mysql_db_hostname = "localhost";
$mysql_db_user = "root";
$mysql_db_password = "";
$mysql_db_database = "mydatabase";
/*
$mysql_db_hostname = "mysql.2freehosting.com";
$mysql_db_user = "u805838818_rack";
$mysql_db_password = "123456";
$mysql_db_database = "u805838818_750";
*/
$con = mysql_connect($mysql_db_hostname, $mysql_db_user, $mysql_db_password) or die("Could not connect database");
$dbfound=mysql_select_db($mysql_db_database, $con) or die("Could not select database");

//if($dbfound){echo "success";}
?>