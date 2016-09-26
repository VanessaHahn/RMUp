<?php
require "conn.php";
$name = $_POST["name"];
$password = $_POST["password"];
$mobile = $_POST["mobile"];
$mysql_qry = "insert into Runners (Name, Passwort, Telefon(mobil)) values('$name', '$password', '$mobile')";
$result = mysql_query($conn, $mysql_qry);
if($conn->query($mysql_qry) === TRUE) {
mysql_fetch_assoc($dsatz);
echo "Insert Successful / " 
   . $dSatz["Id"] . "<br />";
} else {
echo "Error: " . $mysql_qry . "<br>" . $conn->error;
}
$conn->close();
?>