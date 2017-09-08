package com.postman_tracking;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;


public class DisplayOrder extends Activity {
	
	String postID;
	JSONParser jsonParser= new JSONParser();

	private ProgressDialog pDialog;
	ListView list;
	CustomAdapter adapter;
	public  DisplayOrder CustomListView = null;
	public  ArrayList<ListModel> CustomListViewValuesArr = new ArrayList<ListModel>();
	ArrayList<String> orderInfo = new ArrayList<String>();
	private int k;
	
	private String orderID, productName, status,  customerID, cFirstname, cLastname, cAddress, cPostcode, cPhone;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_order);
		
		
		ArrayList arrayList = new ArrayList<String>();
		
		arrayList = (ArrayList) getIntent().getSerializableExtra("postman");
				
		postID =  (String) arrayList.get(4);
		
		
		EditText et= (EditText) findViewById(R.id.editText1);
		et.setText((CharSequence) arrayList.get(1)+" "+ arrayList.get(2));
		
		Button button = (Button) findViewById(R.id.button1);
		button.setOnClickListener(new OnClickListener() {
		    public void onClick(View v) {
		    
		        Intent i = new Intent(DisplayOrder.this, GoogleMap_Android.class);
		        i.putExtra("postID", postID);
		        
		        startActivity(i);
		    }
		});

	
        CustomListView = this;
   	       
  
		FetchOrder fetchOrder = new FetchOrder();
		fetchOrder.execute();
		
		
		
	}
	
	
class FetchOrder extends AsyncTask<String, String, ArrayList<String>> {

		
		boolean failure = false;
		
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(DisplayOrder.this);
            pDialog.setMessage("Getting order information...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
		@Override
		protected ArrayList<String> doInBackground(String... args) {
			// TODO Auto-generated method stub
			 // Check for success tag
            int success;
            String url = "http://www.ece658.ciki.me/postman/server.php/postID/"+postID;
           
           try {
                // Building Parameters
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("postID", postID));
               
                
 
               // Log.d("request!", "starting");
                // getting product details by making HTTP request
                JSONArray jsonArray = null;
				try {
					jsonArray = jsonParser.makeHttpGETRequest(
					       url, "GET", params);
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                
                k = jsonArray.length();
                if (pDialog != null) {
        	        pDialog.dismiss();
        	        pDialog = null;
        	    }
                            
                for(int i=0; i<jsonArray.length();i++){
                	JSONObject json = jsonArray.getJSONObject(i);
              
                	orderID= json.getString("orderID");
                	productName = json.getString("productName");
                	status= json.getString("status");
                	customerID= json.getString("customerID");
                	cFirstname = json.getString("firstname");
                	cLastname= json.getString("lastname");
                	cAddress = json.getString("address");
                	cPostcode= json.getString("postcode");
                	cPhone= json.getString("phone");
                	
                	orderInfo.add(orderID);
                	orderInfo.add(productName);
                	orderInfo.add(status);
                    orderInfo.add(customerID);
                    orderInfo.add(cFirstname);
                    orderInfo.add(cLastname);
                    orderInfo.add(cAddress);
                    orderInfo.add(cPostcode);
                    orderInfo.add(cPhone);
  
 	
                }
                orderInfo.add(postID);
                return orderInfo;
               
            } catch (JSONException e) {
                e.printStackTrace();
            }
 
            return null;
			
		}
		
       
		protected  void onPostExecute(ArrayList<String> arrayList) {
            // dismiss the dialog once product deleted
        
            Resources res =getResources();

            list=(ListView)findViewById(R.id.list);
            /**************** Create Custom Adapter *********/
            adapter=new CustomAdapter(CustomListView, CustomListViewValuesArr,res);
            list.setAdapter(adapter);
            
            for (int i = 0; i < k*9; i=i+9) {
    			
    			final ListModel sched = new ListModel();
    			    
    			  /******* Firstly take data in model object ******/
    			
    			   sched.setCompanyName("orderID: "+arrayList.get(i));
    			   sched.setImage("order2");
    			   sched.setUrl("Name: "+arrayList.get(i+1));
			   
    			/******** Take Model Object in ArrayList **********/
    			CustomListViewValuesArr.add(sched);
    		
    		}
            
            }
 
        }

	public void onItemClick(int mPosition)
	{
		mPosition = mPosition*9;
		Toast.makeText(CustomListView, 
				 "Customer Name: "+orderInfo.get(mPosition+4)+" "+orderInfo.get(mPosition+5)
				 +"\nAddress: "+orderInfo.get(mPosition+6)+"\nPostcode: "+orderInfo.get(mPosition+7)
				 +"\nPhone: "+orderInfo.get(mPosition+8), 
				Toast.LENGTH_LONG)
		.show();
	}

}
