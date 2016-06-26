package com.fruitland.fruitland.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.fruitland.fruitland.R;
import com.fruitland.fruitland.adapter.Customer_Adapter;
import com.fruitland.fruitland.model.Customer_Bean;
import com.fruitland.fruitland.network.GPSTracker;
import com.fruitland.fruitland.utils.Const;
import com.fruitland.fruitland.utils.MyVolleyClass;
import com.fruitland.fruitland.utils.Parse;
import com.fruitland.fruitland.utils.Utility;
import com.fruitland.fruitland.utils.VolleyCompleteListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Admin on 4/1/2016.
 */
public class Pending_Delivery extends Activity implements View.OnClickListener ,VolleyCompleteListener {
    LinearLayout listdelivery;
    ArrayList<Double> latistart = new ArrayList<>();
    ArrayList<Double> longistart = new ArrayList<>();
    ArrayList<Double> latiend = new ArrayList<>();
    ArrayList<Double> longiend = new ArrayList<>();

    FrameLayout deliveryframe;
    LayoutInflater inflater;
    private GoogleMap mMap;
    GPSTracker mGPS;
    Button btn_ok;
    ArrayList<Customer_Bean> customer_list = new ArrayList<>();
    ListView list_customer;
    private Parse parse;
    Customer_Adapter customer_adapter;
    ImageView menu, filter;
    TextView title, maptext;
    LinearLayout mapdelivery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pendingdelivery);
        initialize();
    }

    private void initialize() {
        parse = new Parse(Pending_Delivery.this);


        title = (TextView) findViewById(R.id.title);
        title.setText("PENDING DELIVERIES");
        maptext = (TextView) findViewById(R.id.maptext);
        maptext.setVisibility(View.VISIBLE);

        filter = (ImageView) findViewById(R.id.filter);
        filter.setVisibility(View.VISIBLE);
        filter.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setAnimations();
            }
        });
        deliveryframe = (FrameLayout) findViewById(R.id.deliveryframe);
        inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        listdelivery = (LinearLayout) inflater.inflate(R.layout.layout_deliverycustlist, null);
        mapdelivery = (LinearLayout) inflater.inflate(R.layout.fragment_deliverymap, null);
        deliveryframe.addView(listdelivery);
        maptext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (maptext.getText().toString().equalsIgnoreCase("map")) {
                    maptext.setText("LIST");
                    deliveryframe.removeAllViews();
                    deliveryframe.addView(mapdelivery);
                    //   loadMap();
                } else {
                    maptext.setText("MAP");
                    deliveryframe.removeAllViews();
                    deliveryframe.addView(listdelivery);
                    //         loadList();
                }


            }
        });
        list_customer = (ListView) listdelivery.findViewById(R.id.deliverycustlist);
/*
        for (int i = 0; i < 10; i++) {
            Customer_Bean customer_bean = new Customer_Bean();
            customer_list.add(customer_bean);
        }

        customer_adapter = new Customer_Adapter(Pending_Delivery.this, customer_list);
        list_customer.setAdapter(customer_adapter);*/
        list_customer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), Delivery_CustomerDetails.class);
                intent.putExtra("deliverydata", customer_list.get(position));
                startActivity(intent);
            }
        });

        menu = (ImageView) findViewById(R.id.expanded_menu);
        menu.setImageDrawable(getResources().getDrawable(R.drawable.back));
        menu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
        btn_ok = (Button) findViewById(R.id.btn_ok);
        btn_ok.setOnClickListener(this);
        getCustomersDelivery();

    }

    private void loadList() {
   /*     list_customer = (ListView) listdelivery.findViewById(R.id.deliverycustlist);

        for (int i = 0; i < 10; i++) {
            Customer_Bean customer_bean = new Customer_Bean();
            customer_list.add(customer_bean);
        }
        */

        customer_adapter = new Customer_Adapter(Pending_Delivery.this, customer_list);
        list_customer.setAdapter(customer_adapter);
        list_customer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), Delivery_CustomerDetails.class);
                intent.putExtra("deliverydata", customer_list.get(position));
                startActivity(intent);
            }
        });
    }

    private void loadMap() {
        mGPS = new GPSTracker(this);
        if (mGPS.canGetLocation()) {
            double latitude = mGPS.getLatitude();
            double longitude = mGPS.getLongitude();

        }


        if (mMap == null) {

            mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.mapfrag)).getMap();
            if (mMap != null) {
                if (mGPS.canGetLocation()) {

                    mMap.setMyLocationEnabled(true);

                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                            new LatLng(mGPS.getLatitude(), mGPS.getLongitude()), 11));

                    //       mMap.animateCamera(CameraUpdateFactory.zoomTo(10));
                    mMap.getUiSettings().setZoomControlsEnabled(true);

                    double latitude = Double.valueOf("18.4750340");
                    double longitude = Double.valueOf("73.8203160");

                    LatLng latLng = new LatLng(latitude, longitude);
                    MarkerOptions markerOptions = new MarkerOptions();
                    //    markerOptions.snippet("" + i);


                    for (int i = 0; i < customer_list.size(); i++) {
                        double latitude1 = Double.valueOf(customer_list.get(i).getLat());
                        double longitude1 = Double.valueOf(customer_list.get(i).getLng());

                        LatLng latLng1 = new LatLng(latitude1, longitude1);
                        Marker marker = mMap.addMarker(markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker))
                                .position(latLng1).title(customer_list.get(i).getName()));
                        if (customer_list.get(i).getPackages().equals("The Essentials")) {

                            marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.marker_red));
                        } else if (customer_list.get(i).getPackages().equals("Grand Medley")) {
                            marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.marker));

                        } else if (customer_list.get(i).getPackages().equals("Exotica")) {
                            marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.marker_yellow));

                        }

                    }
               /*     Marker marker = mMap.addMarker(markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker))
                            .position(latLng).title("Customer 1"));
                    Marker marker1 = mMap.addMarker(markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_red))
                            .position(new LatLng(18.5475034, 73.7603160)).title("Customer 2"));
                    Marker marker2 = mMap.addMarker(markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_yellow))
                            .position(new LatLng(18.56750340, 73.7203160)).title("Customer 3"));
                    Marker marker3 = mMap.addMarker(markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker))
                            .position(new LatLng(18.44750340, 73.9203160)).title("Customer 4"));
                    Marker marker4 = mMap.addMarker(markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_red))
                            .position(new LatLng(18.36750340, 73.7503160)).title("Customer 5"));
                    Marker marker5 = mMap.addMarker(markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_yellow))
                            .position(new LatLng(18.4175034, 73.7903160)).title("Customer 6"));
                    Marker marker6 = mMap.addMarker(markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker))
                            .position(new LatLng(18.45750340, 73.8403160)).title("Customer 7"));
                    Marker marker7 = mMap.addMarker(markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_red))
                            .position(new LatLng(18.35750340, 73.7003160)).title("Customer 8"));
*/
                    //   hashMarker.put("" + i, hm);


                    CreateLink();
                } else {
                    mGPS.showSettingsAlert();
                }
            }

        }
    }

    private void setAnimations() {

        final LinearLayout mLayoutTab = (LinearLayout) findViewById(R.id.filterlay);

        if (mLayoutTab.getVisibility() == View.GONE) {
            mLayoutTab.animate()
                    .translationY(0).alpha(1.0f)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                            super.onAnimationStart(animation);
                            mLayoutTab.setVisibility(View.VISIBLE);
                            mLayoutTab.setAlpha(0.0f);
                        }
                    });
        } else {
            mLayoutTab.animate()
                    .translationY(0).alpha(0.0f)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            mLayoutTab.setVisibility(View.GONE);
                        }
                    });
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_ok:
                // setAnimations();
                getCustomersDelivery();
                break;
        }
    }

    private void getCustomersDelivery() {
        if (!Utility.isNetworkAvailable(this)) {
            Utility.showToast(
                    getResources().getString(R.string.dialog_no_inter_message),
                    this);
            return;
        }
        Utility.showSimpleProgressDialog(Pending_Delivery.this, null, "Please Wait...", false);
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(Const.URL, Const.ServiceType.PENDING_DELIVERY);
        map.put(Const.Params.REGIONID, "1");
        map.put(Const.Params.WEEK, "5");
        map.put(Const.Params.MONTH, "6");
        new MyVolleyClass(Pending_Delivery.this, map, Const.ServiceCode.PENDING_DELIVERY, this);
    }


    @Override
    public void onTaskCompleted(String response, int serviceCode) {
        switch (serviceCode) {

            case Const.ServiceCode.PENDING_DELIVERY:
                Utility.removeSimpleProgressDialog();
                Log.e("%%%%%%%%%", response);
                if (parse.isSuccess(response)) {

                    try {
                        JSONObject obj = new JSONObject(response);
                        JSONArray jsonArray = obj.getJSONArray("data");
                        customer_list = new ArrayList<>();
                        customer_list.clear();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            Customer_Bean customer_bean = new Customer_Bean();
                            JSONObject objdata = jsonArray.getJSONObject(i);
                            customer_bean.setName(objdata.getString("name"));
                            customer_bean.setContact(objdata.getString("phone"));
                            customer_bean.setAddress(objdata.getString("address"));

                            if (objdata.getString("lat").equals("")) {
                                customer_bean.setLat("0.0");
                            } else {
                                customer_bean.setLat(objdata.getString("lat"));
                            }
                            if (objdata.getString("lat").equals("")) {
                                customer_bean.setLng("0.0");
                            } else {
                                customer_bean.setLng(objdata.getString("lng"));
                            }

                            customer_bean.setPackages(objdata.getString("package"));
                            customer_bean.setFruits_avoided(objdata.getString("fruit_avoided"));
                            customer_bean.setDeliveyid(objdata.getString("delivery_id"));
                            customer_list.add(customer_bean);

                        }
                        //  customer_adapter = new Customer_Adapter(Pending_Delivery.this, customer_list);
                        // list_customer.setAdapter(customer_adapter);
                        loadList();
                        loadMap();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                break;

            case Const.ServiceCode.CREATE_LINK:
                Utility.removeSimpleProgressDialog();
                parseJSON(response);
                for (int i = 0; i < latiend.size(); i++) {
                    String url = getDirectionsUrl(new LatLng(latistart.get(i), longistart.get(i)), new LatLng(latiend.get(i), longiend.get(i)));
                    DownloadTask downloadTask = new DownloadTask();
                    // Start downloading json data from Google Directions API
                    downloadTask.execute(url);
                }

                break;
        }
    }

    public void parseJSON(String response) {

        try {
            JSONObject main = new JSONObject(response);
            JSONArray routes = main.getJSONArray("routes");
            JSONObject routesobj = (JSONObject) routes.get(0);
            JSONArray legs = routesobj.getJSONArray("legs");
            JSONObject obj = (JSONObject) legs.get(0);
            JSONArray steps = obj.getJSONArray("steps");
            for (int i = 0; i < steps.length(); i++) {
                JSONObject mainobj = (JSONObject) steps.get(i);
                JSONObject start_location = mainobj.getJSONObject("start_location");
                latistart.add(start_location.getDouble("lat"));
                longistart.add(start_location.getDouble("lng"));
                JSONObject end_location = mainobj.getJSONObject("end_location");
                latiend.add(end_location.getDouble("lat"));
                longiend.add(end_location.getDouble("lng"));
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void CreateLink() {

        if (!Utility.isNetworkAvailable(Pending_Delivery.this)) {
            Utility.showToast(
                    getResources().getString(R.string.toast_no_internet),
                    Pending_Delivery.this);
            return;
        }
        Utility.showSimpleProgressDialog(Pending_Delivery.this, "", "loading..", false);

        HashMap<String, String> map = new HashMap<String, String>();
        String link=createMapLink();
        //map.put(Const.URL, "https://maps.googleapis.com/maps/api/directions/json?origin=18.500565%2C73.859054&destination=18.510812%2C73.843616&waypoints=via:18.504407%2C73.854802%7Cvia:18.508431%2C73.849656%7Cvia:18.510812%2C73.843616%7Cvia:18.510812%2C73.846008&key=AIzaSyAVSEkSWq5rLVXxXa_bUc5mnULQu-BNWlQ");
        map.put(Const.URL, link);
        new MyVolleyClass(this, map, Const.ServiceCode.CREATE_LINK, this);
    }


    private String createMapLink(){
        String link="https://maps.googleapis.com/maps/api/directions/json?origin=";

     //  " https://maps.googleapis.com/maps/api/directions/json?origin=18.500565%2C73.859054&destination=18.510812%2C73.843616&waypoints=via:18.504407%2C73.854802%7Cvia:18.508431%2C73.849656%7Cvia:18.510812%2C73.843616%7Cvia:18.510812%2C73.846008&key=AIzaSyAVSEkSWq5rLVXxXa_bUc5mnULQu-BNWlQ"

        link=link+customer_list.get(0).getLat()+"%2C"+customer_list.get(0).getLng()+"&destination="+customer_list.get(customer_list.size()-1).getLat()+"%2C"+customer_list.get(customer_list.size()-1).getLng()+"&waypoints=" ;
               for(int i=1;i<customer_list.size()-1;i++){

                   if(i==customer_list.size()-2){
                       link+="via:"+customer_list.get(i).getLat()+"%2C"+customer_list.get(i).getLng()+"&key=AIzaSyALOeny8DdE1mIu5YEup9PGPq_OcBC9yUU";
                   }else{
                       link+="via:"+customer_list.get(i).getLat()+"%2C"+customer_list.get(i).getLng()+"%7C";
                   }

               }

        return link;
    }

    private String getDirectionsUrl(LatLng origin, LatLng dest) {

        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;

        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;

        // Sensor enabled
        String sensor = "sensor=false";

        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + sensor;

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;

        return url;
    }

    /**
     * A method to download json data from url
     */
    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);

            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        } catch (Exception e) {

        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

    // Fetches data from url passed
    private class DownloadTask extends AsyncTask<String, Void, String> {

        // Downloading data in non-ui thread
        @Override
        protected String doInBackground(String... url) {

            // For storing data from web service
            String data = "";

            try {
                // Fetching the data from web service
                data = downloadUrl(url[0]);
            } catch (Exception e) {

            }
            return data;
        }

        // Executes in UI thread, after the execution of
        // doInBackground()
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            ParserTask parserTask = new ParserTask();

            // Invokes the thread for parsing the JSON data
            parserTask.execute(result);
        }
    }

    /**
     * A class to parse the Google Places in JSON format
     */
    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {

        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try {
                jObject = new JSONObject(jsonData[0]);
                DirectionsJSONParser parser = new DirectionsJSONParser();

                // Starts parsing data
                routes = parser.parse(jObject);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return routes;
        }

        // Executes in UI thread, after the parsing process
        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList<LatLng> points = null;
            PolylineOptions lineOptions = null;
            MarkerOptions markerOptions = new MarkerOptions();

            // Traversing through all the routes
            for (int i = 0; i < result.size(); i++) {
                points = new ArrayList<LatLng>();
                lineOptions = new PolylineOptions();

                // Fetching i-th route
                List<HashMap<String, String>> path = result.get(i);

                // Fetching all the points in i-th route
                for (int j = 0; j < path.size(); j++) {
                    HashMap<String, String> point = path.get(j);

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }

                // Adding all the points in the route to LineOptions
                lineOptions.addAll(points);
                lineOptions.width(5);
                lineOptions.color(Color.RED);
            }

            // Drawing polyline in the Google Map for the i-th route
            try {
                mMap.addPolyline(lineOptions);
            } catch (Exception e) {

            }
        }
    }
}
