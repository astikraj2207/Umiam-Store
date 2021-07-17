<?php

$connection = new mysqli("localhost", "root", "", "online_store_db");
$sqlCommand = $connection->prepare("SELECT id,name,price,amount,brand,picture FROM temporary_place_order INNER JOIN products ON products.id=temporary_place_order.product_id where email=? and brand=?");
$sqlCommand->bind_param("ss", $_GET["email"], $_GET["brand"]);
$sqlCommand->execute();

$temporderarray = array();

$sqlResult = $sqlCommand->get_result();
while ($row=$sqlResult->fetch_assoc()) {
    
    array_push($temporderarray, $row);
    
}

echo json_encode($temporderarray);

