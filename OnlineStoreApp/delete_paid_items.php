<?php

$connection = new mysqli("localhost", "root", "", "online_store_db");
$sqlCommand = $connection->prepare("delete from paid_items where room=?");
$sqlCommand->bind_param("s", $_GET["room"]);
$sqlCommand->execute();

