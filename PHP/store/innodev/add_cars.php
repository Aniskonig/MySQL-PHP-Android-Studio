<?php

// Information vendeur


require("Controller_cars.php") ;
include("config.php");
$controller= new Controller_cars($pdo);

    $name= $_GET["name"];
    $price= $_GET["price"];
    $description= $_GET["description"];
	
	echo $name.' '.$price.'  '.$description ; 

	
    echo json_encode($controller->add_cars($name,$price,$description));

	
	$data = array(
        'message'      => 'Ajout réussi',
    );
	
    $json = json_encode($data);


