<?php


Class Controller_cars
{

    private $_db;

    function __construct($db)
    {
        $this->_db = $db;
    }


    // AddCars
    function add_cars($name,$price,$description){
        try {
            $statement = $this->_db->prepare('insert into cars(name,price, description)
          values (?,?,?)');
            $statement->execute(array($name,$price,$description));
            $id = $this->_db->lastInsertId();
            $row = array("id"=>$id);
            return $row;
        }catch (Exception $e){
            $this->_db->rollback();
            throw $e;
        }
    }




}