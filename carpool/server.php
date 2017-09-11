<?php

class Server {
    public function serve() {
      
        $uri = $_SERVER['REQUEST_URI'];
        $method = $_SERVER['REQUEST_METHOD'];
        $paths = explode('/', $this->paths($uri));
        array_shift($paths); // Hack; get rid of initials empty string
	$resource = array_shift($paths);
        $resource = array_shift($paths);

	/*POST*/
        if ($resource == 'login') {	
		$this->login();
	     
        }else if($resource == 'register'){
		$this->register();
	}
	else if ($resource == 'update'){
		$resource = array_shift($paths);
		if($resource == 'info')
			$this->update_info();
		else if($resource == 'password')
			$this->update_password();
       }else if ($resource == 'user'){
       		$name = array_shift($paths);
		$this->user_info($name);
       }else if ($resource == 'carpool'){
       		if($method == 'POST')
       			$this->publish();
		else if ($method == 'GET')
			$this->carpool();
       }else if ($resource == 'rating'){
       		if($method == 'POST'){
		       $this->rating();
		}else if ($method == 'GET'){
			$name = array_shift($paths);
			$this->request_rating($name);
		}
       } else if ($resource == 'search'){
      		$name = array_shift($paths);		       
		if ($name == 'cities'){
			$city1 = array_shift($paths);
			$city2 = array_shift($paths);
			$this->search_city($city1, $city2);      
		}else if ($name == 'location'){
			$location_lat = array_shift($paths);
			$location_lon = array_shift($paths);
			$radius = array_shift($paths);
			$this->search_location($location_lat, $location_lon, $radius);
		}else if ($name == 'date'){
			$date1 = array_shift($paths);
			$date2 = array_shift($paths);
			$city1 = array_shift($paths);
			$city2 = array_shift($paths);
			$this->search_date($city1, $city2, $date1, $date2);
		}
       }
	else {
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
	private function search_location($location_lat, $location_lon, $radius){
		require("search_location.php");
	}
	private function search_date($city1, $city2, $date1, $date2){
		require("search_date.php");
	}
    private function paths($url) {
        $uri = parse_url($url);
        return $uri['path'];
    }
  }

$server = new Server;
$server->serve();

?>