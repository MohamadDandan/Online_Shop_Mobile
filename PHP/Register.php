<?php

include('db_connection.php');
	$UserName = $_GET['UN'];  
    $Email = $_GET['E']; 
	$Password = $_GET['P'];
    $Gender = $_GET['G'];
	$Country = $_GET['C'];  
    $DOB = $_GET['DOB']; 
	  
	$timestamp = date('Y-m-d H:i:s');
	
	

	$hash=password_hash($Password, PASSWORD_DEFAULT);
	$hashjs=str_replace("$2y$","$2b$",$hash);

$sql = "INSERT INTO users (UserName, Email, Password, Gender, Country, DOB, createdAt, updatedAt, UserTypeId,State)
VALUES ('$UserName', '$Email','$hashjs', '$Gender','$Country', '$DOB','$timestamp','$timestamp', '1','Active')";

if ($conn->query($sql) === TRUE) {
  echo true;
} else {
  echo "Error: " . $sql . "<br>" . $conn->error;
}
?>