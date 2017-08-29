window.onload=getBalance;

var cPrice;
var quantity;
var buyAmount = 0;
var type = new Array();
var symbol = sessionStorage.getItem("thisStockSymbol");
var balance = new Array(); 
var clientID = sessionStorage.getItem("thisClientID");
var i=0;
var tFee = 0.1 * buyAmount;
var accountType;
var share;
var eBalance;

function connect(){

	document.getElementById("symbol").innerHTML = symbol;
	//document.getElementById("balance").innerHTML = balance;
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
		
				
		}
	}
	xmlhttp.open("GET",url, true);
	xmlhttp.send();

}

setInterval(function(){connect()},1000);


function getBalance(){

	var url = "http://localhost/php/server.php/clientID/"+clientID;
	//var url = "http://localhost/php/server.php/clientID/5";

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
		
		
		var a = new Array();
				//var array = new Array();
		for(var key in jsonobj){
			a[i] = key;
			i++;
		}
		for(i=i-1; i>=0; i--){
			balance[i] = jsonobj[a[i]].balance;
			type[i] = jsonobj[a[i]].accountType;
			
		}
		if(type[0]=="traditional"){
			accountType = "traditional";
			document.getElementById("balance").innerHTML = balance[0];
			eBalance = balance[0];
		}else{
			accountType = "special";
			document.getElementById("balance").innerHTML = balance[1];
			eBalance = balance[1];
		}
		connect();
		}
	}
	xmlhttp.open("GET",url, true);
	xmlhttp.send();

}
function changeBalance(){
	selectBox = document.getElementById("account");
	var options = selectBox.options;
	var selectedValue = selectBox.options[selectBox.selectedIndex].value;
	if(selectedValue == "special"){
		accountType = "special";
		if(type[0]=="speical"){
			document.getElementById("balance").innerHTML = balance[0];
			eBalance = balance[0];
		}else{
			document.getElementById("balance").innerHTML = balance[1];
			eBalance = balance[1];
		}
		
		tFee = 0.2*buyAmount;
	}else{
		accountType = "traditional";
		if(type[0]=="traditional"){
			document.getElementById("balance").innerHTML = balance[0];
			eBalance = balance[0];
		}else{
			document.getElementById("balance").innerHTML = balance[1];
			eBalance = balance[1];
		}
		tFee = 0.1*buyAmount;
	}
}
function caculation(){

	share = document.getElementById("share").value;
	amount = document.getElementById("amount");
	
	//total amount of money 
	buyAmount  =share * cPrice;
	amount.value = buyAmount;
	changeBalance();
	var fee = document.getElementById("tFee")
	fee.value= tFee;
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

function buyOrder(){

	var uri = "http://localhost/php/server.php/client/"+clientID;
	//var uri = "http://localhost/php/server.php/client/5";
	//get date
	var currentTime = new Date();
	var month = currentTime.getMonth();
	var day =  currentTime.getDate();
	var year = currentTime.getFullYear();
	var bidDate = year+"-"+month+"-"+day;
	//get time
	var second = currentTime.getSeconds();
	var min = currentTime.getMinutes();
	var hour = currentTime.getHours();
	var bidTime = hour+":"+min+":"+second;
	
	eBalance = eBalance - buyAmount - tFee;
	if(eBalance < 0){
		alert("You do not have enough moeny!");
	}else{

	xmlhttp = new XMLHttpRequest();
	var params = "accountType="+accountType+"&stockSymbol="+symbol+"&bidDate="+bidDate+"&bidTime="+bidTime+"&share="+share+"&balance="+eBalance+"&bidPrice="+cPrice;
	
	xmlhttp.open("POST",uri, true);
	xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
	
	xmlhttp.onreadystatechange = 
	function(){
		if(xmlhttp.readyState == 4 && xmlhttp.status == 200){
		var abc = xmlhttp.responseText;
		var json = JSON.parse(xmlhttp.responseText);
		if(json.success == "8"){
					window.location.href = "success.html";
				}	
		else{
			alert("fail to place order!");
		}
		}
	}
	
	xmlhttp.send(params);
	}
}
function logout(){
			window.location.href = "logout.php";
			
		}