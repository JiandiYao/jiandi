<?php

$today = date("Y-m-d H:m:s");

$temp= explode("-", $today);

$year = $temp[0];
$month = $temp[1];
$temp = explode(" ", $temp[2]);
$date = $temp[0];

$temp = $temp[1];
$temp = explode(":", $temp);
$hour = $temp[0];
$min = $temp[1];
$sec = $temp[2];

printf("%s, %s, %s", $hour, $min, $sec);

?>