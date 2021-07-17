<?php

$connection = new mysqli("localhost", "root", "", "online_store_db");
$sqlCommand = $connection->prepare("SELECT product_id,amount FROM invoice_details where invoice_num=?");
$sqlCommand->bind_param("i", $_GET["invoice_num"]);
$sqlCommand->execute();

$temporderarray = array();

$sqlResult = $sqlCommand->get_result();
while ($row=$sqlResult->fetch_assoc()) {
    
    array_push($temporderarray, $row);
    
}

echo json_encode($temporderarray);

