<?php

$connection = new mysqli("localhost", "root", "", "online_store_db");
$sqlCommand = $connection->prepare("SELECT room from app_users_table where email=?");
$sqlCommand->bind_param("s", $_GET["email"]);
$sqlCommand->execute();
$roomResult = $sqlCommand->get_result();

while($row = $roomResult->fetch_assoc()) 
{
    echo $row["room"];
}

