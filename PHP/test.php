
<?php

include('db_connection.php');


$sql="SELECT tests.id,tests.Qun,tests.createdAt,posts.PostName,posts.PostPrice  FROM tests  INNER JOIN posts ON tests.PostId=posts.id where OTestId=".$_GET['id'];
$result = $conn->query($sql);
$count = mysqli_num_rows($result);
while ($row = mysqli_fetch_assoc($result)) {
				$id = $row['id'];
				$Qun = $row['Qun'];
				
				$createdAt = $row['createdAt'];
				$PostName = $row['PostName'];
				$PostPrice=$row['PostPrice'];
				

				 echo $id."ID".$Qun."Qun".$PostName."PostName".$PostPrice."PostPrice".$createdAt.",";
			};
			echo "Count".$count;
?>
