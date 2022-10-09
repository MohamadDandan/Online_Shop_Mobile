<?php

class DataBaseConfig
{
    public $servername;
    public $username;
    public $password;
    public $databasename;

    public function __construct()
    {
//127.0.0.1
        $this->servername = 'localhost';
        $this->username = 'root';
        $this->password = 'password';
        $this->databasename = 'data';

    }
}

?>
