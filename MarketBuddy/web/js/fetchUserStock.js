window.onload=fetchUserStock;
	
		
		
		function fetchUserStock(){
		
		var stock = new Array();
		var thisClientID = sessionStorage.getItem("thisClientID");
		var url = "http://localhost/php/server.php/userStock/"+thisClientID;
		//var url  = "http://localhost/php/server.php/userStock/1";
		xmlhttp = new XMLHttpRequest();
		xmlhttp.onreadystatechange = function(){
			if(xmlhttp.readyState == 4 && xmlhttp.status == 200){
				
				
				var obj = JSON.parse(xmlhttp.responseText);
				
								
				var a = new Array();
				//var array = new Array();
				var i = 0;
				for(var key in obj){
					a[i] = key;
				//	array.push([]);
					i++;
				}
				stock[0] = obj[a[0]].stockSymbol;
			
				for(i=i-1; i>=0; i--){
				//stock in hold for this client
				
					stock[i]=obj[a[i]].stockSymbol;
					
				}
	
				//return stock[i];
			
			}else{
				//document.getElementById('firstname').innerHTML = "waiting waiting";
			}
		}
		
		xmlhttp.open("GET",url, true);
		xmlhttp.send();
		}
		function logout(){
			window.location.href = "logout.php";
			
		}