
window.onload=connect;
	xmlhttp = new XMLHttpRequest();

		function connect(){
		
		if(window.XMLHttpRequest){
			xmlhttp = new XMLHttpRequest();
		}else{
			xmlhttp= new ActiveXObject("Miscrosoft.XMLHTTP");
		}
		
		xmlhttp.onreadystatechange = 
		function(){
			if(xmlhttp.readyState == 4 && xmlhttp.status == 200){
				
				
				var jsonobj = JSON.parse(xmlhttp.responseText);
				
				sessionStorage.setItem("thisClientID", jsonobj.clientID);
				
				document.getElementById('clientName').innerHTML = jsonobj.firstname+" "+ jsonobj.lastname;
			}
		}
		
		
		xmlhttp.open("GET","http://localhost/php/server.php/username", true);
		xmlhttp.send();
		}

		function logout(){
			window.location.href = "logout.php";
			
		}