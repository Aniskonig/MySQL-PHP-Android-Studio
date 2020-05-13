<?php

$servername = "192.168.1.122";
$username = "root";
$password = "";
$dbname = "store";

$charset = 'utf8';

$dsn = "mysql:host=$servername;dbname=$dbname;charset=$charset";
$opt = [
    PDO::ATTR_ERRMODE            => PDO::ERRMODE_EXCEPTION,
    PDO::ATTR_DEFAULT_FETCH_MODE => PDO::FETCH_ASSOC,
    PDO::ATTR_EMULATE_PREPARES   => false,
];
$pdo= new PDO($dsn, $username, $password, $opt);

?>