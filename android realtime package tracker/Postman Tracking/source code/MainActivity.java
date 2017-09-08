package com.postman_tracking;


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
	private Button mSubmit;
	
	 // Progress Dialog
    private ProgressDialog pDialog;
    
    private static String KEY_SUCCESS = "success";
    private static String KEY_UID = "postID";
    private static String KEY_USERNAME = "username";
    private static String KEY_FIRSTNAME = "firstname";
    private static String KEY_LASTNAME = "lastname";
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
    private static final String LOGIN_URL = "http://www.ece658.ciki.me/postman/login.php";
    
  //testing from a real server:
    //private static final String LOGIN_URL = "http://www.yourdomain.com/webservice/login.php";
    
    //JSON element ids from repsonse of php script:
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		
		//setup input fields
		user = (EditText)findViewById(R.id.username);
		pass = (EditText)findViewById(R.id.password);
		
		//setup buttons
		mSubmit = (Button)findViewById(R.id.login);
	
		
		//register listeners
		mSubmit.setOnClickListener(this);
		
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.login:
				new AttemptLogin().execute();
			break;

		default:
			break;
		}
	}
	
	class AttemptLogin extends AsyncTask<String, String, JSONObject> {

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
                params.add(new BasicNameValuePair("uname", username));
                params.add(new BasicNameValuePair("pword", password));
 
               // Log.d("request!", "starting");
                // getting product details by making HTTP request
                JSONObject jsonObj = jsonParser.makeHttpRequest(
                       LOGIN_URL, "POST", params);
                    
 
                // json success tag
                success = jsonObj.getInt(TAG_SUCCESS);
                if(success == 2){
                	Log.d("Login Failure!", "Wrong username");
                	return null;
                }
                else if (success == 1) {
                	//Log.d("Login Successful!", json.toString());
                	
                	if (pDialog != null) {
            	        pDialog.dismiss();
            	        pDialog = null;
            	    }
                
                	String firstname = jsonObj.getString(KEY_FIRSTNAME);
                	String lastname = jsonObj.getString(KEY_LASTNAME);
                	String phone = jsonObj.getString(KEY_PHONE);
                
                	String postID = jsonObj.getString(KEY_UID);
                	ArrayList<String> postmanInfo = new ArrayList<String>();
                	postmanInfo.add(username);
                	postmanInfo.add(firstname);
                	postmanInfo.add(lastname);
                	postmanInfo.add(phone);
                	postmanInfo.add(postID);
                	
                	
                	Intent i = new Intent(MainActivity.this, DisplayOrder.class);
                	i.putExtra("postman", postmanInfo);
                	
                	
                
                
                	    
                	finish();
    				startActivity(i);
                	return jsonObj;
                }else{
                	Log.d("Login Failure!", "Wrong password");
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


