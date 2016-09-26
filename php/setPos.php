<?php 
require "conn.php";
$id = $_POST["id"];
$long = $_POST["long"];
$lat = $_POST["lat"];
$mysql_qry = "SELECT * FROM position WHERE ID LIKE '$id';";
$result = mysqli_query($conn, $mysql_qry);
if($result === FALSE){
	echo "ERROR";
} else {
	if(mysqli_num_rows($result) == 1){
		$mysql_qry = "UPDATE position SET Longitude = '$long' WHERE ID = '$id';";
		$result = mysqli_query($conn, $mysql_qry);
		$mysql_qry = "UPDATE position SET Lateral = '$lat' WHERE ID = '$id';";
		$result = mysqli_query($conn, $mysql_qry);		
		echo "true1";
	} else {
		$mysql_qry = "INSERT INTO position (ID, Longitude, Lateral) values('$id', '$long', '$lat');";
        $result = mysqli_query($conn, $mysql_qry);		
		echo "true2";
	}
}
?>	