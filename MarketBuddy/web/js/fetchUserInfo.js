
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
				document.getElementById('clientID').innerHTML = jsonobj.clientID;
				document.getElementById('firstname').innerHTML = jsonobj.firstname;
				document.getElementById('lastname').innerHTML = jsonobj.lastname;
				document.getElementById('address').innerHTML = jsonobj.address;
				document.getElementById('phone').innerHTML = jsonobj.phone;
				document.getElementById('email').innerHTML = jsonobj.email;
				
				sessionStorage.setItem("thisClientID", jsonobj.clientID);
			}else{
				//document.getElementById('firstname').innerHTML = "waiting waiting";
			}
		}
		
		
		xmlhttp.open("GET","http://localhost/php/server.php/username", true);
		xmlhttp.send();
		}
		function logout(){
			window.location.href = "logout.php";
			
		}
