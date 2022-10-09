<?php

include('db_connection.php');
	 
    $PostId = $_GET['PID']; 
	$OrderId = $_GET['OID'];
	
	$Quntity = $_GET['qun'];
	$timestamp = date('Y-m-d H:i:s');
	
	

$sql = "INSERT INTO tests (Qun, createdAt, updatedAt, OTestId, PostId)
VALUES ('$Quntity','$timestamp','$timestamp','$OrderId', '$PostId')";

if ($conn->query($sql) === TRUE) {
  echo "You have added new item ";
} else {
  echo "Error: " . $sql . "<br>" . $conn->error;
}
?>