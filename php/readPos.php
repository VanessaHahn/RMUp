<?php 
require "conn.php";
$id = $_POST["id"];
$mysql_qry = "SELECT * FROM position WHERE  ID <> '$id';";
$result = mysqli_query($conn, $mysql_qry);
$counter = 0;
if($result === FALSE){
	echo "ERROR";
} else {
	if(mysqli_num_rows($result) > 0){
		while ($row = $result->fetch_array()) {
			if($counter != 0){
				echo "-";
			}
			$long = $row['Longitude'];
			$lat = $row['Lateral'];
			$uID = $row['ID'];
			echo "true/$uID/$long/$lat";
			$counter = $counter + 1;
		}
	} else {
		echo "false";
	}
}
?>