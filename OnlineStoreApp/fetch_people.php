<?php

$connection = new mysqli("localhost", "root", "", "online_store_db");
$selectBrandsCommand = $connection->prepare("select distinct room from paid_items");
$selectBrandsCommand->execute();

$brandsResult = $selectBrandsCommand->get_result();

$brands = array();

while ($row = $brandsResult->fetch_assoc()) {
    
    array_push($brands, $row);
    
}

echo json_encode($brands);