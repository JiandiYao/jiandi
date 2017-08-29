
	xmlhttp = new XMLHttpRequest();
	var stockSymbol = new Array();
	var bidPrice = new Array();
	var bidShare = new Array();
	var orderNo = new Array();
	var thisClientID = sessionStorage.getItem("thisClientID");
	//var thisClientID = "1";
	var balance = new Array();
	var accountID = new Array();
	
	var url = "http://localhost/php/server.php/account/"+thisClientID;
	function connect(){
		
		if(window.XMLHttpRequest){
			xmlhttp = new XMLHttpRequest();
		}else{
			xmlhttp= new ActiveXObject("Miscrosoft.XMLHTTP");
		}
		
		xmlhttp.onreadystatechange = 
		function(){
			if(xmlhttp.readyState == 4 && xmlhttp.status == 200){
				//get Json object
				var obj = JSON.parse(xmlhttp.responseText);
				//fetch the keys from the above object
				var a = new Array();
				//var array = new Array();
				var i = 0;
				for(var key in obj){
					a[i] = key;
				//	array.push([]);
					i++;
				}
				//get the id of the table
				var newTable = document.getElementById("dataTable");
				//insert new rows to the table
				for(i=i-1; i>=0; i--){
				//insert new row
				var button = new Array();
				
				
					var newRow = newTable.insertRow(-1);
				//insert new cell
					var x=newRow.insertCell(-1);
					x.innerHTML = obj[a[i]].clientID;
					
				//	array[i].push(x.innerHTML);
					
					
					var x=newRow.insertCell(-1);
				
					x.innerHTML = obj[a[i]].balance;
					//array[i].push(x.innerHTML);
					
					var x=newRow.insertCell(-1);
					x.innerHTML = obj[a[i]].stockSymbol;
					//array[i].push(x.innerHTML);
					stockSymbol[i] = obj[a[i]].stockSymbol;
					
					var x=newRow.insertCell(-1);
					x.innerHTML = obj[a[i]].share;
					bidShare[i] = obj[a[i]].share;
					
				
					orderNo[i] = obj[a[i]].orderNo;
					balance[i] = obj[a[i]].balance;
					accountID[i]=obj[a[i]].accountID;
					
					//array[i].push(x.innerHTML);
					var x=newRow.insertCell(-1);
					x.innerHTML = obj[a[i]].bidPrice;
					//array[i].push(x.innerHTML);
					bidPrice[i] = obj[a[i]].bidPrice;
					
					var x=newRow.insertCell(-1);
					x.innerHTML = obj[a[i]].bidDate;
					//array[i].push(x.innerHTML);
					
					var x=newRow.insertCell(-1);
					var buttonnode=document.createElement('input');
					buttonnode.setAttribute('type','button');
					buttonnode.setAttribute('name','sell');
					buttonnode.setAttribute('value','sell');
					buttonnode.setAttribute('id', i);
					//array[i].push(button[i]);
					x.appendChild(buttonnode);
					
					
					buttonnode.addEventListener("click",function(){
						
						var j = this.id;
						sessionStorage.setItem("stockSymbol", stockSymbol[j]);
						sessionStorage.setItem("bidPrice",bidPrice[j]);
						sessionStorage.setItem("bidShare", bidShare[j]);
						sessionStorage.setItem("orderNo", orderNo[j]);
						sessionStorage.setItem("balance", balance[j]);
						sessionStorage.setItem("accountID", accountID[j]);						
						
						window.location.href = "sell.html";

					});
					

			}
				
	
			}else{
				//document.getElementById('firstname').innerHTML = "waiting waiting";
			}
		}
		xmlhttp.open("GET",url, true);
		xmlhttp.send();
		}
window.onload =connect;

function logout(){
			window.location.href = "logout.php";
			
		}
		