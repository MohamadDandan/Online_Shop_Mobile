
<?php      
    include('db_connection.php');  
    $username = $_GET['user'];  
    $password = $_GET['pass'];  
      
        //to prevent from mysqli injection  
        $username = stripcslashes($username);  
        $password = stripcslashes($password);  
        $username = mysqli_real_escape_string($conn, $username);  
        $password = mysqli_real_escape_string($conn, $password);  
      

        $sql = "select * from users where UserName = '$username' ";  
        $result = mysqli_query($conn, $sql);  
        $row = mysqli_fetch_array($result, MYSQLI_ASSOC);  
        $count = mysqli_num_rows($result);  
          $hash=$row["Password"];
		  $ID=$row['id'];
		  if (password_verify($password , $hash) && $count == 1) {
			echo $ID;
		} else {
			echo 'Invalid';
			}
            
?>