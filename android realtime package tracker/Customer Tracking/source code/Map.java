package com.example.packagetracker;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.Activity;
import android.app.ProgressDialog;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Map extends FragmentActivity{
	
	JSONParser jsonParser = new JSONParser();
	
	private GoogleMap mMap;
	
	ArrayList orderID = new ArrayList<String>();
	//postman longitude info
	ArrayList<Double> lon =new ArrayList<Double>();
	//postman latitude info
	ArrayList<Double> lat =new ArrayList<Double>();
	//postman location update time
	ArrayList<String> time =new ArrayList<String>();
	//package estimated arrival time
	ArrayList<String> eTime=new ArrayList<String>();
	//package estimated arrival date
	ArrayList<String> eDate=new ArrayList<String>();
	//postman location update date
	ArrayList<String> date=new ArrayList<String>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map);

		orderID = (ArrayList) getIntent().getSerializableExtra("orderID");

		FetchLocation fetchLocation = new FetchLocation();
		fetchLocation.execute();
		
		mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
		mMap.setMyLocationEnabled(true);
		
	/*	LatLng postmanStart = new LatLng(lat.get(0), lon.get(0));		
		mMap.moveCamera(CameraUpdateFactory.newLatLng(postmanStart));
		mMap.animateCamera(CameraUpdateFactory.zoomTo(16));
		mMap.addMarker(new MarkerOptions()
						.position(postmanStart)
						.title("Postman Starts Here!"));*/

		}
	
	private void setInitialLocation() {
		// TODO Auto-generated method stub
		
	}

	public void onClick_btnMapView(View v){
		mMap.animateCamera(CameraUpdateFactory.zoomTo(12));
		
		for(int i = 0; i<lat.size();i++){
		//set the center of the map
		LatLng postmanStart = new LatLng(lat.get(0),lon.get(0));
		mMap.moveCamera(CameraUpdateFactory.newLatLng(postmanStart));
		mMap.animateCamera(CameraUpdateFactory.zoomTo(12));
		mMap.addMarker(new MarkerOptions()
		.position(postmanStart)
		.title("Postman Starts Here!")
		.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
		
		LatLng latLng = new LatLng(lat.get(i),lon.get(i));
		mMap.addMarker(new MarkerOptions()
		        .position(latLng)
		        .title("Postman is here!")
		        .snippet(""+i));}
		}
		
	//set map type
	public void onClick_btnSatellite(View v){
		mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
	}
	public void onClick_btnNormal(View v){
		mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
	}
	public void onClick_btnTerrain(View v){
		mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
	}
	
class FetchLocation extends AsyncTask<String, String, ArrayList<String>> {
	

		@Override
		protected ArrayList<String> doInBackground(String... args) {
			
            try {
            	for (int i = 0; i< orderID.size(); i++){
            	String location_URL = "http://www.ece658.ciki.me/customer/server.php/orderID/"+orderID.get(i);
            	List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("orderID", (String) orderID.get(i)));

                // getting product details by making HTTP request
                JSONObject json = jsonParser.makeHttpRequest(
                       location_URL, "GET", params);
 
                	lon.add(json.getDouble("lon"));
                	lat.add(json.getDouble("lat"));
                	time.add(json.getString("time"));
                	date.add(json.getString("date"));
                	eTime.add(json.getString("eTime"));
                	eDate.add(json.getString("eDate"));

            	}
            	return null;
            } catch (JSONException e) {
                e.printStackTrace();
            }
			return null;
			
		}
		
		  protected  void onPostExecute(ArrayList<String> file_url) {
			  TextView tv = (TextView) findViewById(R.id.textView2);
			  tv.setText("");
			  for(int i = 0;i<orderID.size();i++ ){
				  tv.append("Estimated Arrival Time for order "
						  + orderID.get(i)+ ": \n"+eDate.get(i)+" "+eTime.get(i)
						  +"\nUpdated on "+date.get(i)+" "+time.get(i));

			  }
		  }
		  
	}
}
