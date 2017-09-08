package com.example.packagetracker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class RetrieveAccount extends Activity implements OnClickListener{
	
	String customer_URL;
	private ProgressDialog pDialog;
	String orderID;
	private String  status, productName, link, description, proNum, orderStatus;
	private String customerID;
	
	//int status,foo;
	// JSON parser class
    JSONParser jsonParser = new JSONParser();
    ArrayList<String> orderInfo = new ArrayList<String>();
    ArrayList<String> odrID = new ArrayList<String>();
    private Button button1, button2;
    
    private int k;
    
    
    
    String KEY_ORDERID = "orderID";
    String KEY_PRODUCTNAME = "productName";
    String KEY_LINK = "link";
    String KEY_STATUS = "status";
    String KEY_DESCRIPTION = "description";
    String KEY_PRONUM = "proNum";

	 @Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.customer_view);
		
	
		Bundle extras = getIntent().getExtras();
	
		String name = extras.getString("name");	
		customerID =  extras.getString("customerID");
		customer_URL = "http://www.ece658.ciki.me/customer/server.php/customerID/"+customerID;
		
		EditText et= (EditText) findViewById(R.id.firstname);
		
		et.setText(name);
		
		
		FetchOrder fetchOrder = new FetchOrder();
		fetchOrder.execute();
		
		//setup buttons
		button1 = (Button)findViewById(R.id.button1);
		button2 = (Button)findViewById(R.id.button2);
		
		button1.setOnClickListener(this);
		button2.setOnClickListener(this);

	}
	 public void onClick(View view){
			switch (view.getId()) {
			case R.id.button1:
				Intent i = new Intent(RetrieveAccount.this, Map.class);
				
					i.putExtra("orderID", odrID);
				
				startActivity(i);
				break;
			case R.id.button2:
				Intent intent = new Intent(RetrieveAccount.this, UpdateCustomerInfo.class);
				intent.putExtra("customerInfo", orderInfo);
				startActivity(intent);
				break;

			default:
				break;
			}
	 }
	 
	 
	 class FetchOrder extends AsyncTask<String, String, ArrayList<String>> {

		
		boolean failure = false;
		
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(RetrieveAccount.this);
            pDialog.setMessage("Getting order information...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
		@Override
		protected ArrayList<String> doInBackground(String... args) {
			// TODO Auto-generated method stub
      
            try {
                // Building Parameters
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("customerID", customerID));

               // Log.d("request!", "starting");
                // getting product details by making HTTP request
                JSONArray jsonArray = null;
				try {
					jsonArray = jsonParser.makeHttpGETRequest(
					       customer_URL, "GET", params);
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                
                k = jsonArray.length();
                for(int i=0; i<jsonArray.length();i++){
                	JSONObject json = jsonArray.getJSONObject(i);
                	
                	orderID = json.getString(KEY_ORDERID);
                	productName = json.getString(KEY_PRODUCTNAME);
                	status = json.getString(KEY_STATUS);
                	link= json.getString(KEY_LINK);
                	description= json.getString(KEY_DESCRIPTION);
                	proNum= json.getString(KEY_PRONUM);
                	
                	orderInfo.add(orderID );
                	orderInfo.add(productName);
                	orderInfo.add(status);
                	orderInfo.add(link);
                	orderInfo.add(description);
                	orderInfo.add(proNum);
                	odrID.add(orderID);
                }
                return orderInfo;
                         
            } catch (JSONException e) {
                e.printStackTrace();
            }
 
            return null;
			
		}
		
        protected  void onPostExecute(ArrayList<String> file_url) {
            // dismiss the dialog once product deleted
        
        	String orderStatus;
            pDialog.dismiss();
            if (file_url != null){
            	TextView tv = (TextView) findViewById(R.id.textView3);
				if(file_url.get(2)=="1"){
					orderStatus = "Order Delivered";
				}else if (file_url.get(2)=="2"){
					orderStatus = "Not delivered";
				}else {
					orderStatus = "Delivering";
				}
				
				tv.setText("");
				for (int i = 0; i<k*6; i=i+6){
				tv.append("Order ID: "+file_url.get(i)+"\n");
				tv.append("Product Name: "+file_url.get(i+1)+"\n");
				tv.append("Status: "+orderStatus+"\n");
				tv.append("Link: "+file_url.get(i+3)+"\n");
				tv.append("Description: "+file_url.get(i+4)+"\n");
				tv.append("Number of the product: " + file_url.get(i+5)+"\n\n");
				}
            }
 
        }
}
}