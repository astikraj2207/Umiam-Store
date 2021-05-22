<?php

$connection = new mysqli("localhost", "root", "", "online_store_db");
$sqlCommand = $connection->prepare("insert into paid_items values (?, ?, ?, ?, ?)");
$sqlCommand->bind_param("ssiis", $_GET["email"],$_GET["room"],  $_GET["product_id"], $_GET["amount"], $_GET["brand"]);
$sqlCommand->execute();

