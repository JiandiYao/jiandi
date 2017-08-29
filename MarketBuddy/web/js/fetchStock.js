//	window.onload=setInterval;

	window.onload=fetchUserStock;

	var stock = new Array();
	//var thisClientID = sessionStorage.getItem("thisClientID");
	var k;
	var num;
		
	var price = new Array();
	var change = new Array();
	var volume = new Array();
	var average = new Array();
	var buy = new Array();
	
	var jsonobj;
	var uri;
	
	var newTable;
		
	function fetchUserStock(){

		//var url = "http://localhost/php/server.php/userStock/"+thisClientID;
		var url  = "http://localhost/php/server.php/userStock/1";
		http = new XMLHttpRequest();
		http.onreadystatechange = function(){
			if(http.readyState == 4 && http.status == 200){
				var obj = JSON.parse(http.responseText);
				var a = new Array();
				var i = 0;
				for(var key in obj){
					a[i] = key;
					i++;
				}
				num = i;
				stock[0] = obj[a[0]].stockSymbol;
			
				for(i=i-1; i>=0; i--){
					//stock in hold for this client				
					stock[i]=obj[a[i]].stockSymbol;
				}
				createTable();
				//connect();
				//addTableValue();
			}
		}
		http.open("GET",url, true);
		http.send();
	}

function createTable(){
	var m ,n;
	newTable = document.getElementById("dataTable");
	
	for ( m=0; m<num; m++){
		var newRow = newTable.insertRow(-1);
	
		var x=newRow.insertCell(-1);
		var checkbox = document.createElement('input');
		checkbox.setAttribute('type','checkbox');
		checkbox.setAttribute('id', m);
		x.appendChild(checkbox);
		
		var x=newRow.insertCell(-1);
		x.innerHTML = m+1;
			
		var x=newRow.insertCell(-1);
		x.innerHTML = stock[m];
			

		for(n=0; n<5; n++){
			var x=newRow.insertCell(-1);
			x.innerHTML = "0";
		}
		
		x=newRow.insertCell(-1);
		var buttonnode=document.createElement('input');
		buttonnode.setAttribute('type','button');
		buttonnode.setAttribute('name','buy');
		buttonnode.setAttribute('value','buy');
		buttonnode.setAttribute('id', m);
		x.appendChild(buttonnode);
		
		buttonnode.addEventListener("click",function(){
						
			var j = this.id;
			sessionStorage.setItem("thisStockSymbol", stock[j]);
			window.location.href = "buy.html";

			});

	}
}
	
setInterval(function(){myTimer()},1000);
	
function myTimer(){
	for(k=0; k<num; k++){
		uri = "http://localhost/php/server.php/stock/"+stock[k];
		
		connect();
		
		price[k] = jsonobj.price;
		change[k] = jsonobj.change;
		volume[k] = jsonobj.volume;
		average[k] = jsonobj.avg_daily_volume;
		//add system time.
		var d = new Date();
		var time = d.toLocaleTimeString();

		var button = new Array();
		var myTable = document.getElementById("dataTable");

		var x=myTable.rows[k+1].cells[3];
		x.innerHTML = price[k];
			
		var x=myTable.rows[k+1].cells[4];
		x.innerHTML = change[k];
			
		var x=myTable.rows[k+1].cells[5];
		x.innerHTML = volume[k];
			
		var x=myTable.rows[k+1].cells[6];
		x.innerHTML = average[k];
			
		var x=myTable.rows[k+1].cells[7];
		x.innerHTML = time;
	}
}

	//get newest stock info
	function connect(){
		
		xmlhttp = new XMLHttpRequest();
		xmlhttp.onreadystatechange = function(){
			if(xmlhttp.readyState == 4 && xmlhttp.status == 200){
				
				
				jsonobj = JSON.parse(xmlhttp.responseText);
	
			}
		}
	
		xmlhttp.open("GET",uri, false);
		xmlhttp.send();
		
	}
	function logout(){
			window.location.href = "logout.php";
			
		}
