<?php

$connection = new mysqli("localhost", "root", "", "online_store_db");
$sqlCommand = $connection->prepare("delete from temporary_place_order where email=? and product_id=?");
$sqlCommand->bind_param("si", $_GET["email"], $_GET["product_id"]);
$sqlCommand->execute();


