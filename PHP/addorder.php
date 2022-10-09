<?php

include('db_connection.php');
	$quntity = $_GET['quantity'];  
    $address = $_GET['address']; 
	$postid = $_GET['postid'];  
    $userid = $_GET['userid'];  

$sql = "INSERT INTO tests (Qun, Address,createdAt,updatedAt, PostId,UserId)
VALUES ('$quntity', '$address','2021-12-18 09:55:56','2021-12-18 09:55:56', '$postid','$userid')";

if ($conn->query($sql) === TRUE) {
  echo "New record created successfully";
} else {
  echo "Error: " . $sql . "<br>" . $conn->error;
}





?>