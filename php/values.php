<?php
require "conn.php";
$distanceAvg = $_POST["distance"];
$timeAvg = $_POST["time"];
$velocityAvg = $_POST["velocity"];
$mysql_qry = "insert into Runners (StreckeDs, ZeitDs, GeschwindigkeitDs) values('$distanceAvg', '$timeAvg', '$velocityAvg')";
$result = mysql_query($conn, $mysql_qry);
if($conn->query($mysql_qry) === TRUE) {
echo "Daten aktualisiert";
}
else {
echo "Error: " . $mysql_qry . "<br>" . $conn->error;
}
$conn->close();
?>