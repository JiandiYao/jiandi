<?php
session_start();
/*
  if(!isset($_SESSION['LOGIN_STATUS'])){
      header('location:../web/index.html');
  }*/
class Server {
    public function serve() {
      
        $uri = $_SERVER['REQUEST_URI'];
        $method = $_SERVER['REQUEST_METHOD'];
        $paths = explode('/', $this->paths($uri));
        array_shift($paths); // Hack; get rid of initials empty string
		$resource = array_shift($paths);
        $resource = array_shift($paths);
		 $resource = array_shift($paths);
 
        if ($resource == 'stock') {
		if($method == "GET"){
            $name = array_shift($paths);
			
			$this->display_stock($name);
	     }     
        }else if($resource == 'username'){
		if($method == "GET"){
			$name = $_SESSION['USERNAME'];
			$this->display_client($name);

		}}else if($resource == 'account'){
			if($method = "GET"){
				$name = array_shift($paths);
				$this->display_account($name);
			}
		}else if($resource == 'order'){
			$name = array_shift($paths);
			if($method = 'GET'){
				$this->delete_order($name);
				
			}
		}else if ($resource == 'ord'){
		if($method = 'POST'){
			$name = array_shift($paths);
			$this->update_order($name);			
			}
		}
		else if($resource == 'userStock'){
		if($method = 'GET'){
			$name = array_shift($paths);
			$this->fetchUserStock($name);
			}
		}else if($resource == 'clientID'){
			if($method='GET'){
				$name = array_shift($paths);
				$this->fetchBalance($name);
			}
		}else if($resource == 'client'){
				$clientID = array_shift($paths);
				
				$this->addOrder($clientID);
			}
		else {
            // We only handle resources under 'clients'
            header('Location:index.html');
        } 
    }
//get stock up-to-date info from Yahoo
    private function display_stock($name) {
		require("stockAPI.php");
	}
	//fetch user information from database
    private function display_client($name){
		require('fetchUserInfo.php');
	
	}
	//fetch account and order inforamtion for a client
	private function display_account($name){
		require('fetchAccount.php');
	}
	//delete an order
	private function delete_order($name){
		require('deleteOrder.php');
	}
	//update an order and account
	private function update_order($name){
		require('updateOrder.php');
	}
	//fetch client stock interest(stock in hold)
	private function fetchUserStock($name){
		require('fetchUserStock.php');
	}
	
	//fetch balance by clientID
	private function fetchBalance($name){
		require('fetchBalance.php');
	}
	
	//add new order
	private function addOrder($clientID){
		require('addOrder.php');
	}

    private function paths($url) {
        $uri = parse_url($url);
        return $uri['path'];
    }
  }

$server = new Server;
$server->serve();

?>