<?php

$connection = new mysqli("localhost", "root", "", "online_store_db");
$sqlCommand = $connection->prepare("SELECT id,name,price,amount,picture FROM paid_items INNER JOIN products ON products.id=paid_items.product_id where room=?");
$sqlCommand->bind_param("s", $_GET["room"]);
$sqlCommand->execute();

$temporderarray = array();

$sqlResult = $sqlCommand->get_result();
while ($row=$sqlResult->fetch_assoc()) {
    
    array_push($temporderarray, $row);
    
}

echo json_encode($temporderarray);


