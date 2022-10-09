<?php

include("db_connection.php");

  
  $sql = "SELECT * FROM `posts` WHERE id=".$_GET['id'];



$result = $conn->query($sql);

while ($row = mysqli_fetch_assoc($result)) {
				$PostName = $row['PostName'];
				$PostPrice = $row['PostPrice'];
				$PostDetail=$row['PostDetail'];
				
				
				
				
				 $data = array("PostName"=>$PostName, "PostPrice"=>$PostPrice,"PostDetail"=>$PostDetail);
				 
				 echo json_encode($data);
			};
?>