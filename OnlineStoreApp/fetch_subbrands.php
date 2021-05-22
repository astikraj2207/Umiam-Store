<?php

$connection = new mysqli("localhost", "root", "", "online_store_db");
$fetchProductsCommand = $connection->prepare("select * from products where brand=?");
$fetchProductsCommand->bind_param("s", $_GET["brand"]);
$fetchProductsCommand->execute();

$pResults = $fetchProductsCommand->get_result();

$pArray = array();

while ($row = $pResults->fetch_assoc()) {
    
    array_push($pArray, $row);
    
    
}

echo json_encode($pArray);
