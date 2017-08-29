window.onload=connect;
var cPrice;
var sellAmount;
var quantity;
var net;
var symbol = sessionStorage.getItem("stockSymbol");
var orderNo = sessionStorage.getItem("orderNo");
var balance = sessionStorage.getItem("balance");
var accountID = sessionStorage.getItem("accountID");
var bidPrice = sessionStorage.getItem("bidPrice");
var bidShare = sessionStorage.getItem("bidShare");

function connect(){

	document.getElementById("symbol").innerHTML = symbol;
	document.getElementById("bidPrice").innerHTML = bidPrice;
	document.getElementById("bidAmount").innerHTML = bidShare;
	var url = "http://localhost/php/server.php/stock/"+symbol;
	document.getElementById("cPrice").innerHTML = cPrice;
	
	xmlhttp = new XMLHttpRequest();
	if(window.XMLHttpRequest){
		xmlhttp = new XMLHttpRequest();
	}else{
		xmlhttp= new ActiveXObject("Miscrosoft.XMLHTTP");
	}
		
	xmlhttp.onreadystatechange = 
	function(){
		if(xmlhttp.readyState == 4 && xmlhttp.status == 200){
		
		var jsonobj = JSON.parse(xmlhttp.responseText);
		cPrice = jsonobj.price;
		document.getElementById("cPrice").innerHTML = cPrice;
				
		}else{
				//document.getElementById('firstname').innerHTML = "waiting waiting";
		}
	}
	xmlhttp.open("GET",url, true);
	xmlhttp.send();

}
setInterval(function(){connect()},1000);
function caculation(){

	quantity = document.getElementById("quantity").value;
	amount = document.getElementById("amount");
	
	
	//total amount of money 
	sellAmount  =quantity * cPrice;
	amount.value = sellAmount;
	net = document.getElementById("net");
	oPrice = cPrice - bidPrice;
	net.value = oPrice*quantity;
	
	sessionStorage.setItem("thisShare", quantity);
	
	/*
	// See if the input value contains any text
	if (inputField.value.length == 0) {
	// The data is invalid, so set the help message
	if (helpText != null)
		helpText.innerHTML = "Please enter a number.";
		
	}else if(inputField.value > bidAmount){
		if (helpText != null)
			helpText.innerHTML = "Max share is "+bidAmount;
			
		}else if(isNaN(inputField.value)){
			helpText.innerHTML = "Please input an integer";
		}
	else {
		// The data is OK, so clear the help message
		if (helpText != null)
		helpText.innerHTML = "";
		
	}*/
}

function sellOrder(){
	//update the amount
		var newBalance = sellAmount + balance;
		sessionStorage.setItem("balance", newBalance);
		var newShare = bidShare - quantity;
	
	
if(bidShare - quantity){
	var uri = "http://localhost/php/server.php/ord/"+orderNo;

	xmlhttp = new XMLHttpRequest();

	var params = "newBalance="+newBalance+"&newShare="+newShare+"&newAccountID="+accountID;
	
	xmlhttp.open("POST",uri, false);
	xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
	
	xmlhttp.onreadystatechange = 
	function(){
		if(xmlhttp.readyState == 4 && xmlhttp.status == 200){
		
		var json = JSON.parse(xmlhttp.responseText);
		if(json.success == "7"){
					window.location.href = "success.html";
				}	
		else{
			alert("fail to sell order!");
		}
		}
	}
	xmlhttp.send(params);
}else{
	var uri = "http://localhost/php/server.php/order/"+orderNo;
	xmlhttp = new XMLHttpRequest();
		
		xmlhttp.onreadystatechange = 
	function(){
		if(xmlhttp.readyState == 4 && xmlhttp.status == 200){
		
		var json = JSON.parse(xmlhttp.responseText);
		if(json.success == "6"){
					window.location.href = "success.html";
				}	
		}else{
				alert("fail to sell!");
		}
	}
	xmlhttp.open("GET",uri, false);
	xmlhttp.send();

	}
}

function logout(){
			window.location.href = "logout.php";
}
