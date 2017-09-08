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
      
        if ($resource == 'postID') {
		if($method == 'GET'){
            $name = array_shift($paths);
			$this->display_order($name);
	     } else if($method = 'POST'){
	       $name = array_shift($paths);
           $this->update_location($name);
	     }  
        }else if($resource == 'login'){
			$this->login();
		}
        else if($resource == 'logout'){
			$this->logout();
		}
     
		else {
		  echo ("do nothing");

        } 
    }
//get stock up-to-date info from Yahoo
    private function display_order($name) {
		require("display_order.php");
	}
    
    //update postman location and order estimated arrival time
    private function update_location($name){
        require("update_location.php");
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