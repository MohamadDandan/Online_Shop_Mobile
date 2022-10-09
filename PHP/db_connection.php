
<?php
    $host="localhost";
$port=3306;
$socket="";
$user="root";
$password="password";
$dbname="data";

$conn = new mysqli($host, $user, $password, $dbname);
// Check connection
if ($conn->connect_error) {
  die("Connection failed: " . $conn->connect_error);
}
?>