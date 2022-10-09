
<?php

include("db_connection.php");

  
  $sql = "SELECT * FROM `comments` WHERE PostId=".$_GET['PostId'];



$result = $conn->query($sql);

while ($row = mysqli_fetch_assoc($result)) {
				$Comment = $row['Comment'];
				
				$UserName=$row['UserName'];
				
				
				

				
				
				echo $UserName."\t: ".$Comment.",\n";
				 ;
			};
?>