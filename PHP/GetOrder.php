<?php

include('db_connection.php');
	 
    
	
	
	

$sql = "SELECT * FROM otests WHERE UserId=".$_GET['id']." order by id desc ";

$result = $conn->query($sql);
$count = mysqli_num_rows($result);
while ($row = mysqli_fetch_assoc($result)) {
				$id = $row['id'];
				$status = $row['status'];
				$comment=$row['comment'];
				$Address=$row['Address'];
				$createdAt = $row['createdAt'];
				

				echo $id."id".$status."status".$comment."comment".$Address."Address".$createdAt.",";
			};
			echo "Count".$count;
?>