<?php
include("db_connection.php");


$sql = "SELECT * FROM posts ";

$result = $conn->query($sql);
$count = mysqli_num_rows($result);
while ($row = mysqli_fetch_assoc($result)) {
				$PostName = $row['PostName'];
				$PostPrice = $row['PostPrice'];
				$ID=$row['id'];
				
				
				 echo $ID.";".$PostName.": ".$PostPrice.",";
			};echo "Count".$count;
?>
