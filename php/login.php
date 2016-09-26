<?php 
require "conn.php";
$user_name = $_POST["user_name"];
$password = $_POST["password"];
$mysql_qry = "SELECT * FROM Runners WHERE Name LIKE '$user_name' AND Passwort LIKE '$password';";
$result = mysqli_query($conn, $mysql_qry);
if($result === FALSE){
	echo "ERROR";
} else {
	if(mysqli_num_rows($result) == 1){
		$singleRow = mysqli_fetch_array($result);
		$id = $singleRow['ID'];
		$gesch = $singleRow['Geschwindigkeit'];
		$mobil = $singleRow['Handynummer'];
		echo "true/$id/$gesch/$mobil";
	} else {
		echo "false";
	}
}

?>