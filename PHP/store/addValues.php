<?php

include ("cars.php");

	$name = $_POST["name"];
	$price = $_POST["price"];
	$description = $_POST["description"];

	$total_row = mysql_query("SELECT * FROM cars");
	$old = mysql_num_rows($total_row);

	$add = mysql_query("INSERT INTO cars VALUES('$name','$price','$description')");

	$total_row_new = mysql_query("SELECT * FROM cars");
	$new= mysql_num_rows($total_row_new);

	if ($new > $old) {
		$response['success'] = 1;
	}else{
		$response['success'] = 0;
	}

	echo json_encode($response);

	mysql_close($db);
?>