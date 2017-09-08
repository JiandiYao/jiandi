

package com.postman_tracking;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import android.R.integer;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public class GoogleMap_Android extends FragmentActivity {
	
	//use for the server
	ArrayList originalPosition = new ArrayList();
	ArrayList originalLat = new ArrayList();
	ArrayList originalLng = new ArrayList();
	
	ArrayList orderIDSequence=new ArrayList();
	ArrayList orderDeliveryTime=new ArrayList();
	
	//get the information of orders
	ArrayList <Double> orderLat = new ArrayList<Double>();
	ArrayList <Double> orderLng = new ArrayList<Double>();
	ArrayList <String> orderID = new ArrayList<String>();
	ArrayList <String> firstname = new ArrayList<String>();
	ArrayList <String> lastname = new ArrayList<String>();
	ArrayList <String> address = new ArrayList<String>();
	ArrayList <String> postcode = new ArrayList<String>();
	ArrayList <String> phone = new ArrayList<String>();
	
	ArrayList orderSequence = new ArrayList();
	ArrayList orderLocationMarker = new ArrayList();
	ArrayList deliverySequenceLat = new ArrayList();
	ArrayList deliverySequenceLng = new ArrayList();
	//internal variables
	int count=0;
	int route;
	int takeTime;
	private GoogleMap mMap;
	LocationManager locationManager;
	Location myOriginalLocation;
	
	JSONParser jsonParser = new JSONParser();

	private String postID;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.google_map);
		postID = getIntent().getStringExtra("postID");
		mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.the_map)).getMap();
		setUpMapIfNeeded();
		new FetchOrder().execute();
		new GeoDecode().execute();
		//new BeeperControl().beepForAnHour();
	}
		
	private void setUpMapIfNeeded() {
		// TODO Auto-generated method stub
		if (mMap==null){
			mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.the_map)).getMap();
		}
		if(mMap!=null){
			setUpMap();
		}
	}

	private void setUpMap() {
		//Enable my location layer
		mMap.setMyLocationEnabled(true);
		//Get location manager object
		locationManager=(LocationManager) getSystemService(LOCATION_SERVICE);
		//Create a criteria to retrieve provider
		Criteria criteria = new Criteria();
		//Get the name of the best provider
		String provider = locationManager.getBestProvider(criteria, true);
		//get current location
		myOriginalLocation = locationManager.getLastKnownLocation(provider);
		
		if(myOriginalLocation != null){
		//Get the latitude of my current location
		double latitude = myOriginalLocation.getLatitude();
		originalLat.add(latitude);
		//Get the longitude of my current location
		double longitude = myOriginalLocation.getLongitude();
		originalLng.add(longitude);
		//Create a LatLng object for the current location
		LatLng latLng = new LatLng(latitude, longitude);
		//Show the current location
		mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
		mMap.animateCamera(CameraUpdateFactory.zoomTo(20));
		mMap.addMarker(new MarkerOptions()
						.position(new LatLng(latitude, longitude))
						.title("Start here!"));
		}
		LocationListener listener = new LocationListener(){

			@Override
			public void onLocationChanged(Location arg0) {
				//this.mylocation=arg0;
				LocationManager locationManager=(LocationManager) getSystemService(LOCATION_SERVICE);
				Criteria criteria = new Criteria();
				String provider = locationManager.getBestProvider(criteria, true);
				Location myNewLocation = locationManager.getLastKnownLocation(provider);
				//if(myNewLocation!=null){
				double oriLat = myNewLocation.getLatitude();
				originalLat.add(oriLat);
				double oriLng = myNewLocation.getLongitude();
				originalLng.add(oriLng);
				LatLng oriPosition = new LatLng(oriLat,oriLng);
				originalPosition.add(oriPosition);
				mMap.moveCamera(CameraUpdateFactory.newLatLng(oriPosition));
				mMap.addMarker(new MarkerOptions()
								.position(new LatLng(oriLat, oriLng))
								.title("You are here!")
								.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
								);
				
			}

			@Override
			public void onProviderDisabled(String provider) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onProviderEnabled(String provider) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onStatusChanged(String provider, int status,
					Bundle extras) {
				// TODO Auto-generated method stub
			}
		};
		locationManager.requestLocationUpdates(provider, 5, 1, listener);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
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
	
	//postman push button to get the list from the server
	public void onClick_btnGetList(View v){
		mMap.animateCamera(CameraUpdateFactory.zoomTo(12));
		
		for(int i=0;i<orderID.size();i++){
			LatLng orderLocation = new LatLng(orderLat.get(i), orderLng.get(i));
			Marker orderLocationM = mMap.addMarker(new MarkerOptions()
									.position(orderLocation)
									.title("OrderID"+orderID.get(i)
										+","+firstname.get(i)+lastname.get(i)
										+","+address.get(i)
										+","+postcode.get(i)
										+","+phone.get(i))
									.snippet(""+orderID.get(i)));
			orderLocationMarker.add(orderLocationM);
		}
		                   
		mMap.setOnMarkerClickListener(new OnMarkerClickListener(){

			@Override
			public boolean onMarkerClick(Marker marker) {
				count = count+1;
			    for	(int i=0; i<orderLocationMarker.size(); i++){
				if (marker.equals(orderLocationMarker.get(i)))
			    	{
						String orderIDs = ((Marker) orderLocationMarker.get(i)).getSnippet();
						//delivery sequence
						orderIDSequence.add(orderIDs);
						//delivery relative order sequence
						orderSequence.add(i);
			    		((Marker) orderLocationMarker.get(i)).setSnippet("stop"+count);
			    		LatLng thisOrderLocation = (LatLng) ((Marker) orderLocationMarker.get(i)).getPosition();
			    		deliverySequenceLat.add(thisOrderLocation.latitude);
			    		deliverySequenceLng.add(thisOrderLocation.longitude);
			    	}
			    }
				return false;
			}
			});
		
		Button button= (Button) findViewById(R.id.btnStart);
		button.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				LocationManager locationManager=(LocationManager) getSystemService(LOCATION_SERVICE);
				Criteria criteria = new Criteria();
				String provider = locationManager.getBestProvider(criteria, true);
				Location myLocation = locationManager.getLastKnownLocation(provider);
				// TODO Auto-generated method stub
				if(deliverySequenceLat.size() == 1){
					double endLocationLat = (Double) deliverySequenceLat.get(0);
					double endLocationLng = (Double) deliverySequenceLng.get(0);
					findDirections(myLocation.getLatitude(),myLocation.getLongitude(),
							endLocationLat, endLocationLng, GMapV2Direction.MODE_DRIVING );
					findDurations(myLocation.getLatitude(),myLocation.getLongitude(),
				    		endLocationLat, endLocationLng, GMapV2Direction.MODE_DRIVING );
					new UpdateLocation().execute();
				}
				else if(deliverySequenceLat.size()>1)
				{
					double endLocationLatIni = (Double) deliverySequenceLat.get(0);
					double endLocationLngIni = (Double) deliverySequenceLng.get(0);
					findDirections(myLocation.getLatitude(),myLocation.getLongitude(),
							endLocationLatIni, endLocationLngIni, GMapV2Direction.MODE_DRIVING );
					findDurations(myLocation.getLatitude(),myLocation.getLongitude(),
				    		endLocationLatIni, endLocationLngIni, GMapV2Direction.MODE_DRIVING );
					for(int i = 0; i < (deliverySequenceLat.size()-1);i++){
					double startLocationLat = (Double)deliverySequenceLat.get(i);
					double startLocationLng = (Double)deliverySequenceLng.get(i);
					double endLocationLat = (Double)deliverySequenceLat.get(i+1);
					double endLocationLng = (Double)deliverySequenceLng.get(i+1);
					
					findDirections(startLocationLat,startLocationLng,
				    		endLocationLat, endLocationLng, GMapV2Direction.MODE_DRIVING );
					findDurations(startLocationLat,startLocationLng,
				    		endLocationLat, endLocationLng, GMapV2Direction.MODE_DRIVING );
					}
					new UpdateLocation().execute();
				}
				else
					System.err.println("error");
			}
		});
	}

	public int handleGetDurationResult(ArrayList durationTimes)
	{
		int s = 0;
	    return s;
	}
	
	//send request and get data from the server, save the returning data.
	public class GMapV2Direction {
	    public final static String MODE_DRIVING = "driving";
	    public GMapV2Direction() { }
	    public Document getDocument(LatLng start, LatLng end, String mode) {
	        String url = "http://maps.googleapis.com/maps/api/directions/xml?"
	                + "origin=" + start.latitude + "," + start.longitude
	                + "&destination=" + end.latitude + "," + end.longitude
	                + "&sensor=true&units=metric&mode=driving";
//http://maps.googleapis.com/maps/api/directions/xml?origin=Waterloo&destination=Toronto&sensor=true&units=metric&mode=driving
	        try {
	            HttpClient httpClient = new DefaultHttpClient();
	            HttpContext localContext = new BasicHttpContext();
	            HttpPost httpPost = new HttpPost(url);
	            HttpResponse response = httpClient.execute(httpPost, localContext);
	            InputStream in = response.getEntity().getContent();
	            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
	            Document doc = builder.parse(in);
	            return doc;
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return null;
	    }
	   
	    //get time duration
	    public String getDurationText (Document doc) {
	        NodeList nl1 = doc.getElementsByTagName("duration");
	        Node node1 = nl1.item(0);
	        NodeList nl2 = node1.getChildNodes();
	        Node node2 = nl2.item(getNodeIndex(nl2, "text"));
	        Log.i("DurationText", node2.getTextContent());
	        return node2.getTextContent();
	    }
	 
	    public int getDurationValue (Document doc) {
	        NodeList nl1 = doc.getElementsByTagName("duration");
	        Node node1 = nl1.item(0);
	        NodeList nl2 = node1.getChildNodes();
	        Node node2 = nl2.item(getNodeIndex(nl2, "value"));
	        Log.i("DurationValue", node2.getTextContent());
	        return Integer.parseInt(node2.getTextContent());
	    }
	 
	    public String getDistanceText (Document doc) {
	        NodeList nl1 = doc.getElementsByTagName("distance");
	        Node node1 = nl1.item(0);
	        NodeList nl2 = node1.getChildNodes();
	        Node node2 = nl2.item(getNodeIndex(nl2, "text"));
	        Log.i("DistanceText", node2.getTextContent());
	        System.err.println(node2.getTextContent()+"");
	        return node2.getTextContent();
	    }
	 
	    public int getDistanceValue (Document doc) {
	        NodeList nl1 = doc.getElementsByTagName("distance");
	        Node node1 = nl1.item(0);
	        NodeList nl2 = node1.getChildNodes();
	        Node node2 = nl2.item(getNodeIndex(nl2, "value"));
	        Log.i("DistanceValue", node2.getTextContent());
	        return Integer.parseInt(node2.getTextContent());
	    }
	 
	    public String getStartAddress (Document doc) {
	        NodeList nl1 = doc.getElementsByTagName("start_address");
	        Node node1 = nl1.item(0);
	        Log.i("StartAddress", node1.getTextContent());
	        return node1.getTextContent();
	    }
	 
	    public String getEndAddress (Document doc) {
	        NodeList nl1 = doc.getElementsByTagName("end_address");
	        Node node1 = nl1.item(0);
	        Log.i("StartAddress", node1.getTextContent());
	        return node1.getTextContent();
	    }
	 
	    public String getCopyRights (Document doc) {
	        NodeList nl1 = doc.getElementsByTagName("copyrights");
	        Node node1 = nl1.item(0);
	        Log.i("CopyRights", node1.getTextContent());
	        return node1.getTextContent();
	    }
	 
	    //get the time document
		public ArrayList getDuration (Document doc) {
	        NodeList nl1, nl2, nl3;
	        ArrayList listTimes = new ArrayList();
	        nl1 = doc.getElementsByTagName("step");
	        if (nl1.getLength() > 0) {
	            for (int i = 0; i < nl1.getLength(); i++) {
	            	Node node1 = nl1.item(i);
	                nl2 = node1.getChildNodes();
	 
	                Node locationNode = nl2.item(getNodeIndex(nl2, "duration"));
	                nl3 = locationNode.getChildNodes();
	                Node latNode = nl3.item(getNodeIndex(nl3, "value"));
	                int estimateTime = Integer.parseInt(latNode.getTextContent());
	                listTimes.add(estimateTime);
	            }
	            int time = 0;
	    	    for(int i = 0 ; i < listTimes.size() ; i++)
	    	    {
	    	         time = time+(Integer) listTimes.get(i);
	    	    }
	    	    takeTime=takeTime+time;
	    	    orderDeliveryTime.add(takeTime);
	        }
	        return listTimes;
	    }
		
		public ArrayList getDirection (Document doc) {
	        NodeList nl1, nl2, nl3;
	        ArrayList listGeopoints = new ArrayList();
	        nl1 = doc.getElementsByTagName("step");
	        if (nl1.getLength() > 0) {
	            for (int i = 0; i < nl1.getLength(); i++) {
	                Node node1 = nl1.item(i);
	                nl2 = node1.getChildNodes();
	 
	                Node locationNode = nl2.item(getNodeIndex(nl2, "start_location"));
	                nl3 = locationNode.getChildNodes();
	                Node latNode = nl3.item(getNodeIndex(nl3, "lat"));
	                double lat = Double.parseDouble(latNode.getTextContent());
	                Node lngNode = nl3.item(getNodeIndex(nl3, "lng"));
	                double lng = Double.parseDouble(lngNode.getTextContent());
	                listGeopoints.add(new LatLng(lat, lng));
	 
	                locationNode = nl2.item(getNodeIndex(nl2, "polyline"));
	                nl3 = locationNode.getChildNodes();
	                latNode = nl3.item(getNodeIndex(nl3, "points"));
	                ArrayList arr = decodePoly(latNode.getTextContent());
	                for(int j = 0 ; j < arr.size() ; j++) {
	                    listGeopoints.add((LatLng)arr.get(j));
	                    
	                }
	 
	                locationNode = nl2.item(getNodeIndex(nl2, "end_location"));
	                nl3 = locationNode.getChildNodes();
	                latNode = nl3.item(getNodeIndex(nl3, "lat"));
	                lat = Double.parseDouble(latNode.getTextContent());
	                lngNode = nl3.item(getNodeIndex(nl3, "lng"));
	                lng = Double.parseDouble(lngNode.getTextContent());
	                listGeopoints.add(new LatLng(lat, lng));
	            }
	        }
	 
	        return listGeopoints;
	    }
	 
	    private int getNodeIndex(NodeList nl, String nodename) {
	        for(int i = 0 ; i < nl.getLength() ; i++) {
	            if(nl.item(i).getNodeName().equals(nodename))
	                return i;
	        }
	        return -1;
	    }
	 
	    @SuppressWarnings({ "rawtypes", "unchecked" })
		private ArrayList decodePoly(String encoded) {
	        ArrayList poly = new ArrayList();
	        int index = 0, len = encoded.length();
	        int lat = 0, lng = 0;
	        while (index < len) {
	            int b, shift = 0, result = 0;
	            do {
	                b = encoded.charAt(index++) - 63;
	                result |= (b & 0x1f) << shift;                 shift += 5;             } while (b >= 0x20);
	            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
	            lat += dlat;
	            shift = 0;
	            result = 0;
	            do {
	                b = encoded.charAt(index++) - 63;
	                result |= (b & 0x1f) << shift;                 shift += 5;             } while (b >= 0x20);
	            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
	            lng += dlng;
	 
	            LatLng position = new LatLng((double) lat / 1E5, (double) lng / 1E5);
	            poly.add(position);
	        }
	        return poly;
	    }
	}
	
	@SuppressWarnings("rawtypes")
	public class GetDirectionsAsyncTask extends AsyncTask<Map<String, String>, Object, ArrayList>
	{
	    public static final String USER_CURRENT_LAT = "user_current_lat";
	    public static final String USER_CURRENT_LONG = "user_current_long";
	    public static final String DESTINATION_LAT = "destination_lat";
	    public static final String DESTINATION_LONG = "destination_long";
	    public static final String DIRECTIONS_MODE = "directions_mode";
	    private GoogleMap_Android activity;
	    private Exception exception;
	    private ProgressDialog progressDialog;
	 
	    public GetDirectionsAsyncTask(GoogleMap_Android activity)
	    {
	        super();
	        this.activity = activity;
	    }
	 
	    public void onPreExecute()
	    {
	        progressDialog = new ProgressDialog(activity);
	        progressDialog.setMessage("Calculating directions");
	        progressDialog.show();
	    }
	 
	    @Override
	    public void onPostExecute(ArrayList result)
	    {
	    	progressDialog.dismiss();
	        if (exception == null)
	        {
	            activity.handleGetDirectionsResult(result);
	        }
	        else
	        {
	            processException();
	        }
	    }
	 
	    @Override
	    protected ArrayList doInBackground(Map<String, String>... params)
	    {
	        Map<String, String> paramMap = params[0];
	        try
	        {
	            LatLng fromPosition = new LatLng(Double.valueOf(paramMap.get(USER_CURRENT_LAT)) , Double.valueOf(paramMap.get(USER_CURRENT_LONG)));
	            LatLng toPosition = new LatLng(Double.valueOf(paramMap.get(DESTINATION_LAT)) , Double.valueOf(paramMap.get(DESTINATION_LONG)));
	            GMapV2Direction md = new GMapV2Direction();
	            Document doc = md.getDocument(fromPosition, toPosition, paramMap.get(DIRECTIONS_MODE));
	            ArrayList directionPoints = md.getDirection(doc);
	            return directionPoints;
	        }
	        catch (Exception e)
	        {
	            exception = e;
	            return null;
	        }
	    }
	    
	    @SuppressLint("ShowToast")
		private void processException()
	    {
	        Toast.makeText(activity, activity.getString(R.string.action_settings), 3000).show();
	    }
	}
	
	//find route main function
	@SuppressWarnings("unchecked")
	public void findDirections(double fromPositionDoubleLat, double fromPositionDoubleLong, double toPositionDoubleLat, double toPositionDoubleLong, String mode)
	{	
		Map<String, String> map = new HashMap<String, String>();
	    map.put(GetDirectionsAsyncTask.USER_CURRENT_LAT, String.valueOf(fromPositionDoubleLat));
	    map.put(GetDirectionsAsyncTask.USER_CURRENT_LONG, String.valueOf(fromPositionDoubleLong));
	    map.put(GetDirectionsAsyncTask.DESTINATION_LAT, String.valueOf(toPositionDoubleLat));
	    map.put(GetDirectionsAsyncTask.DESTINATION_LONG, String.valueOf(toPositionDoubleLong));
	    map.put(GetDirectionsAsyncTask.DIRECTIONS_MODE, mode);
	    
	    GetDirectionsAsyncTask asyncTask = new GetDirectionsAsyncTask(this);
	    asyncTask.execute(map);
	}
	
	
	//get time AsyncTask
	public class GetDurationAsyncTask extends AsyncTask<Map<String, String>, Object, ArrayList>
	{
	    public static final String USER_CURRENT_LAT = "user_current_lat";
	    public static final String USER_CURRENT_LONG = "user_current_long";
	    public static final String DESTINATION_LAT = "destination_lat";
	    public static final String DESTINATION_LONG = "destination_long";
	    public static final String DIRECTIONS_MODE = "directions_mode";
	    private GoogleMap_Android activity;
	    private Exception exception;
	    private ProgressDialog progressDialog;
	 
	    public GetDurationAsyncTask(GoogleMap_Android activity)
	    {
	        super();
	    	this.activity = activity;
	    }
	 
	    public void onPreExecute()
	    {
	        progressDialog = new ProgressDialog(activity);
	        progressDialog.setMessage("Calculating directions");
	        progressDialog.show();
	    }
	 
	    @Override
	    public void onPostExecute(ArrayList result)
	    {
	    	progressDialog.dismiss();
	        if (exception == null)
	        {
	            activity.handleGetDurationResult(result);
	        }
	        else
	        {
	        	processException();
	        }
	    }
	 
	    @Override
	    protected ArrayList doInBackground(Map<String, String>... params)
	    {
	        Map<String, String> paramMap = params[0];
	        try
	        {
	            LatLng fromPosition = new LatLng(Double.valueOf(paramMap.get(USER_CURRENT_LAT)) , Double.valueOf(paramMap.get(USER_CURRENT_LONG)));
	            LatLng toPosition = new LatLng(Double.valueOf(paramMap.get(DESTINATION_LAT)) , Double.valueOf(paramMap.get(DESTINATION_LONG)));
	            GMapV2Direction md = new GMapV2Direction();
	            Document doc = md.getDocument(fromPosition, toPosition, paramMap.get(DIRECTIONS_MODE));
	            ArrayList durationPoints = md.getDuration(doc);
	            return durationPoints;
	        }
	        catch (Exception e)
	        {
	            exception = e;
	            return null;
	        }
	    }
	    @SuppressLint("ShowToast")
		private void processException()
	    {
	        Toast.makeText(activity, activity.getString(R.string.action_settings), 3000).show();
	    }
	}
	
	//find the duration time main function
	@SuppressWarnings("unchecked")
	public int findDurations(double fromPositionDoubleLat, double fromPositionDoubleLong, double toPositionDoubleLat, double toPositionDoubleLong, String mode)
	{
		
		Map<String, String> map = new HashMap<String, String>();
	    map.put(GetDurationAsyncTask.USER_CURRENT_LAT, String.valueOf(fromPositionDoubleLat));
	    map.put(GetDurationAsyncTask.USER_CURRENT_LONG, String.valueOf(fromPositionDoubleLong));
	    map.put(GetDurationAsyncTask.DESTINATION_LAT, String.valueOf(toPositionDoubleLat));
	    map.put(GetDurationAsyncTask.DESTINATION_LONG, String.valueOf(toPositionDoubleLong));
	    map.put(GetDurationAsyncTask.DIRECTIONS_MODE, mode);
	    
	    GetDurationAsyncTask asyncTask = new GetDurationAsyncTask(this);
	    asyncTask.execute(map);
	    return takeTime;
	}
	
	//draw on the map
	public void handleGetDirectionsResult(ArrayList directionPoints)
	{
	    PolylineOptions rectLine = new PolylineOptions().width(3).color(Color.BLUE);
	    for(int i = 0 ; i < directionPoints.size() ; i++)
	    {
	        rectLine.add((LatLng) directionPoints.get(i));
	    }
	    mMap.addPolyline(rectLine);
	}
	
	
//upload info to server	
class UpdateLocation extends AsyncTask<String, String, String> {

	protected String doInBackground(String... args) {
			// TODO Auto-generated method stub
			 // Check for success tag
          int success;
          String url = "http://www.ece658.ciki.me/postman/server.php/postID/"+postID;
          for(int i = 0; i < orderIDSequence.size(); i++){
          //get the interval
        	  int interval = (Integer) orderDeliveryTime.get(i);
        	  int hours = interval/3600;
        	  int minutes = (interval%3600)/60;
        	  int seconds = (interval%3600)%60;
        	  Calendar cal = Calendar.getInstance();
        	  int month = cal.get(Calendar.MONTH)+1;
        	  //get current time
        	  String current_date = cal.get(Calendar.YEAR)+"-"+month+"-"+cal.get(Calendar.DATE);
        	  String current_time = cal.get(Calendar.HOUR_OF_DAY)+":"+cal.get(Calendar.MINUTE)+":"+cal.get(Calendar.SECOND);        
        	  cal.add(Calendar.SECOND, seconds);
        	  cal.add(Calendar.MINUTE, minutes);
        	  cal.add(Calendar.HOUR_OF_DAY, hours);  
        	  //get the estimated time
        	  hours = cal.get(Calendar.HOUR_OF_DAY);
        	  minutes = cal.get(Calendar.MINUTE);
        	  seconds = cal.get(Calendar.SECOND);
          
        	  String eTime = hours + ":" + minutes + ":" + seconds;
        	  try {
        		  // Building Parameters
        		  List<NameValuePair> params = new ArrayList<NameValuePair>();
        		  params.add(new BasicNameValuePair("orderID",(String) orderIDSequence.get(i)));
        		  params.add(new BasicNameValuePair("time", current_time));
        		  params.add(new BasicNameValuePair("date", current_date));
        		  //the location of postman
        		  int j = originalLat.size();
        		  String lat = Double.toString((Double) originalLat.get(j-1));
        		  String lng = Double.toString((Double) originalLng.get(j-1));
        		  params.add(new BasicNameValuePair("lon", lng));
        		  params.add(new BasicNameValuePair("lat", lat));
        		  params.add(new BasicNameValuePair("eTime", eTime));

					JSONObject json = jsonParser.makeHttpRequest(
					       url, "POST", params);
					int a = json.getInt("success");	
					//return null;
        	  }	catch (JSONException e) {
        		e.printStackTrace();
          }
		}
          return null;  
    }
}


	
class GeoDecode extends AsyncTask<String, String, String>{
	protected String doInBackground(String... args) {

		 for(int i = 0 ; i< postcode.size();i++){
		 String uri = "http://maps.googleapis.com/maps/api/geocode/json?address="+postcode.get(i)+"&sensor=true";
		
        JSONObject json = jsonParser.makeHttpRequest(
			       uri, "GET", null);
        JSONArray jsonArray = null;
        JSONObject geo = null;

        try {
        	jsonArray = json.getJSONArray("results");
        	geo = jsonArray.getJSONObject(0);
        	JSONObject temp1 = geo.getJSONObject("geometry");
        	JSONObject temp = temp1.getJSONObject("location");
        
        
			orderLat.add(temp.getDouble("lat"));
			orderLng.add(temp.getDouble("lng"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		 return null;
	}
	
}
/*
 class BeeperControl {
    	    private final ScheduledExecutorService scheduler =
    	       Executors.newScheduledThreadPool(1);

    	    public void beepForAnHour() {
    	        final Runnable beeper = new Runnable() {
    	                public void run() {
    	                	new UpdateLocation().execute();}
    	            };
    	        final ScheduledFuture<?> beeperHandle =
    	            scheduler.scheduleAtFixedRate(beeper, 40, 40, TimeUnit.SECONDS);
    	        scheduler.schedule(new Runnable() {
    	                public void run() { beeperHandle.cancel(true); }
    	            }, 120 * 60, TimeUnit.SECONDS);
    	    }
    	 }
*/

class FetchOrder extends AsyncTask<String, String, ArrayList<String>> {

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
            for(int i=0; i<jsonArray.length();i++){
            	JSONObject json = jsonArray.getJSONObject(i);
          	
    			orderID.add((String) json.getString("orderID"));
    			firstname.add((String) json.getString("firstname"));
    			lastname.add((String) json.getString("lastname"));
    			address.add((String) json.getString("address"));
    			postcode.add((String) json.getString("postcode"));
    			phone.add((String) json.getString("phone"));

            }
            return null;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
	}
}    
}


