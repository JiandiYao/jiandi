package com.example.packagetracker;

import java.util.ArrayList;
import java.util.HashMap;

import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Movie;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener{
	
	public static JSONObject jsonObject_user = null;
	
	

	private EditText pass, user;
	private Button mSubmit, mRegister;
	
	 // Progress Dialog
    private ProgressDialog pDialog;
    
    private static String KEY_SUCCESS = "success";
    private static String KEY_UID = "customerID";
    private static String KEY_USERNAME = "username";
    private static String KEY_FIRSTNAME = "firstname";
    private static String KEY_LASTNAME = "lastname";
    private static String KEY_ADDRESS = "address";
    private static String KEY_POSTCODE = "postcode";
    private static String KEY_PHONE = "phone";
    private static String KEY_USER = "user";
 
    // JSON parser class
    JSONParser jsonParser = new JSONParser();
    
    //php login script location:
    
    //localhost :  
    //testing on your device
    //put your local ip instead,  on windows, run CMD > ipconfig
    //or in mac's terminal type ifconfig and look for the ip under en0 or en1
   // private static final String LOGIN_URL = "http://xxx.xxx.x.x:1234/webservice/login.php";
    
    //testing on Emulator:
    private static final String LOGIN_URL = "http://ece658.ciki.me/customer/login.php";
    
  //testing from a real server:
    //private static final String LOGIN_URL = "http://www.yourdomain.com/webservice/login.php";
    
    //JSON element ids from repsonse of php script:
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//setup input fields
		user = (EditText)findViewById(R.id.username);
		pass = (EditText)findViewById(R.id.password);
		
		//setup buttons
		mSubmit = (Button)findViewById(R.id.login);
		mRegister = (Button)findViewById(R.id.register);
		
		//register listeners
		mSubmit.setOnClickListener(this);
		mRegister.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.login:
				new AttemptLogin().execute();
			break;
		case R.id.register:
				Intent i = new Intent(this, Register.class);
				startActivity(i);
			break;

		default:
			break;
		}
	}
	
	class AttemptLogin extends AsyncTask<String, String, JSONObject> {

		 /**
         * Before starting background thread Show Progress Dialog
         * */
		boolean failure = false;
		
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Attempting login...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
		@Override
		protected JSONObject doInBackground(String... args) {
			// TODO Auto-generated method stub
			 // Check for success tag
            int success;
            String username = user.getText().toString();
            String password = pass.getText().toString();
            try {
                // Building Parameters
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("username", username));
                params.add(new BasicNameValuePair("password", password));
 
               // Log.d("request!", "starting");
                // getting product details by making HTTP request
                JSONObject json = jsonParser.makeHttpRequest(
                       LOGIN_URL, "POST", params);
 
                // json success tag
                success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                	//Log.d("Login Successful!", json.toString());
                	JSONObject customer = json.getJSONObject("user");
                	               	
                	
                	String firstname = customer.getString(KEY_FIRSTNAME);
                	String lastname = customer.getString(KEY_LASTNAME);
                	String phone = customer.getString(KEY_PHONE);
                	String address = customer.getString(KEY_ADDRESS);
                	String postcode = customer.getString(KEY_POSTCODE);
                	String customerID = customer.getString(KEY_UID);
                	ArrayList<String> customerInfo = new ArrayList<String>();
                	customerInfo.add(username);
                	customerInfo.add(firstname);
                	customerInfo.add(lastname);
                	customerInfo.add(address);
                	customerInfo.add(postcode);
                	customerInfo.add(phone);
                	customerInfo.add(customerID);
                	
                	
                	Intent i = new Intent(MainActivity.this, RetrieveAccount.class);
                	i.putExtra("customerID", customerID);
                	i.putExtra("name", firstname+" "+lastname);
                	
                	
                	if (pDialog != null) {
            	        pDialog.dismiss();
            	        pDialog = null;
            	    }
                
                	    
                	finish();
    				startActivity(i);
                	return json;
                }else{
                	Log.d("Login Failure!", json.getString(TAG_MESSAGE));
                	return null;
                	
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
 
            return null;
			
		}
		/**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once product deleted
            pDialog.dismiss();
            if (file_url != null){
            	Toast.makeText(MainActivity.this, file_url, Toast.LENGTH_LONG).show();
            }
 
        }
		
	}

}


