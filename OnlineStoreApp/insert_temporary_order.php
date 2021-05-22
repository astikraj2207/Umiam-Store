<?php

$connection = new mysqli("localhost", "root", "", "online_store_db");
$sqlCommand = $connection->prepare("insert into temporary_place_order values (?, ?, ?)");
$sqlCommand->bind_param("sii", $_GET["email"],  $_GET["product_id"], $_GET["amount"]);
$sqlCommand->execute();

