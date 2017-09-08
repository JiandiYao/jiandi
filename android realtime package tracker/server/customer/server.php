<?php

class Server {
    public function serve() {
      
        $uri = $_SERVER['REQUEST_URI'];
        $method = $_SERVER['REQUEST_METHOD'];
        $paths = explode('/', $this->paths($uri));
        array_shift($paths); // Hack; get rid of initials empty string
		$resource = array_shift($paths);
        $resource = array_shift($paths);
		$resource = array_shift($paths);
      
        if ($resource == 'customerID') {
		if($method == 'GET'){
            $name = array_shift($paths);
			$this->display_customer($name);
	     }     
        }else if($resource == 'orderID'){
		if($method == 'GET'){
			$name = array_shift($paths);
			$this->display_location($name);
		}
		}else if($resource == 'login'){
			$this->login();
		}else if($resource == 'logout'){
			$this->logout();
		}
		else {
            // We only handle resources under 'clients'
     
        } 
    }
//get stock up-to-date info from Yahoo
    private function display_customer($name) {
		require("display_customer.php");
	}
	//fetch postman location and 
	private function display_location($name){
		require("display_location.php");
	}
	private function login(){
		require("login.php");
	}
	
	private function logout(){
		require("logout.php");
	}
	
    private function paths($url) {
        $uri = parse_url($url);
        return $uri['path'];
    }
  }

$server = new Server;
$server->serve();

?>