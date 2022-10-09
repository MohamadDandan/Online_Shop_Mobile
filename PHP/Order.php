<?php

include('db_connection.php');
	 
    $UserId = $_GET['UID']; 
	$timestamp = date('Y-m-d H:i:s');
	$address = $_GET['address']; 
	
	

$sql = "INSERT INTO otests (status, createdAt, updatedAt, UserId,Address)
VALUES ('in progress','$timestamp','$timestamp', '$UserId','$address')";

if ($conn->query($sql) === TRUE) {
	
	$last_id = $conn->insert_id;

  echo $last_id;
} else {
  echo "Error: " . $sql . "<br>" . $conn->error;
}
?>