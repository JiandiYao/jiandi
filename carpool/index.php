<?php

class Server {
    public function serve() {
      
        $uri = $_SERVER['REQUEST_URI'];
        $method = $_SERVER['REQUEST_METHOD'];
        $paths = explode('/', $this->paths($uri));
        array_shift($paths); // Hack; get rid of initials empty string
	$resource = array_shift($paths);
        $resource = array_shift($paths);

	switch($resource){
	
	case 'login':
		$this->login();
		break;
	case 'register':
		$this->register();
		break;
	case 'update':
		$resource = array_shift($paths);
		if($resource == 'info')
			$this->update_info();
		else if($resource == 'password')
			$this->update_password();
		break;
	case 'user':
		$name = array_shift($paths);
		$this->user_info($name);
		break;
	case 'carpool':
		if($method == 'POST')
			$this->publish();
		else if ($method == 'GET')
			$this->carpool();
		break;
	case 'rating':
		if($method == 'POST'){
			$this->rating();
		}else if ($method == 'GET'){
			$name = array_shift($paths);
			$this->request_rating($name);
		}
		break;
	case 'search':
		$city1 = array_shift($paths);
		$city2 = array_shift($paths);
		$this->search_city($city1, $city2);
		echo $city1;	
		break;
	deafult:
		echo ("do nothing");
	}
    }

	private function login(){
		require("login.php");
	}
	
	private function register(){
		require("register.php");
	}

	private function update_info(){
		require("update_info.php");
	}
	private function update_password(){
		require("update_password.php");
	}
	private function user_info($name){
		require("user_info.php");
	}
	private function publish(){
		require("publish.php");
	}
	private function carpool(){
		require("carpool.php");
	}
	private function rating(){
		require("rating.php");
	}
	private function request_rating($name){
		require("request_rating.php");
	}
	private function search_city($city1, $city2){
		require("search_city.php");
	}
    private function paths($url) {
        $uri = parse_url($url);
        return $uri['path'];
    }
  }

$server = new Server;
$server->serve();

?>