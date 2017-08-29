function sellOrder(){

	
	if(bidAmount - share){
	//update the amount
		var newBalance = amount + balance;
		var newShare = bidAmount - share;
		var uri = "http://localhost/php/server.php/order/"+orderNo+"/balance/"+newBalance+"/share/"+newShare+"/accountID/"+accountID;
		
		http = new XMLHttpRequest();
	
		if(window.XMLHttpRequest){
			http = new XMLHttpRequest();
		}else{
			http= new ActiveXObject("Miscrosoft.XMLHTTP");
		}
		
		http.onreadystatechange = 
		function(){
			if(http.readyState == 4 && http.status == 200){
				
				
				var jsonobj = JSON.parse(http.responseText);
				if(jsonobj.success){
					window.location.href = "success.html";
				}
				}
		http.open("GET",uri, true);
		http.send(params);
	}else {
		//delete this piece of information
		var uri = "http://localhost/php/server.php/order/"+orderNo;
	
	
		http = new XMLHttpRequest();
		
		
		if(window.XMLHttpRequest){
			http = new XMLHttpRequest();
		}else{
			http= new ActiveXObject("Miscrosoft.XMLHTTP");
		}
		
		http.onreadystatechange = 
		function(){
			if(http.readyState == 4 && http.status == 200){
				var jsonobj = JSON.parse(http.responseText);
				if(jsonobj.success){
					window.location.href = "success.html";
				}
				}
		http.open("GET",uri, true);
		http.send(null);
	}
}

function logout(){
			window.location.href = "logout.php";
			
		}