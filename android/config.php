<?php
$conn = new mysqli("sql11.freemysqlhosting.net", "sql11414667", "bZP4vUa2E1", "sql11414667");

// Check connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
} 
//echo "Connected successfully";
?>
