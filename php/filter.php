<?php
require "conn.php";
$id = $_POST["id"];
$velocityAvg = $_POST["velocity"];
$locationLongitude = $_POST["longitude"];
$locationLatitude = $_POST["latitude"];
$mysql_qry = "select Name, StreckeDs, ZeitDs, GeschwindigkeitDs, Telefon(mobil) from Runners where (Id is not like $id) and (GeschwindigkeitDs >= ('$velocityAvg' - 1)) and (GeschwindigkeitDs <= ('$velocityAvg' + 1)) and (abs(LocationLongitude - '$locationLongitude')<=0.03) and (abs(LocationLatitude - '$locationLatitude')<=0.03);";
$result = mysql_query($mysql_qry);
if(mysql_num_rows($result) == 0) {
echo "Keine Ergebnisse. Versuchen Sie es später noch einmal.";
} else {
while($dsatz = mysql_fetch_assoc($result)){
echo $dSatz["Name"] . ", "
   . $dSatz["GeschwindigkeitDs"] . ", "
   . $dSatz["StreckeDs"] . ", "
   . $dSatz["ZeitDs"] . ", "
   . $dSatz["Telefon(mobil)"] . "<br />";
}
}
?>		