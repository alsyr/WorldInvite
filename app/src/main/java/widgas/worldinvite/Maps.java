package widgas.worldinvite;

import android.app.ProgressDialog;
import android.content.*;
import android.location.*;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.*;

import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ExecutionException;

public class Maps extends FragmentActivity implements LocationListener, GoogleMap.OnMapClickListener{

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    // private MenuActivity theMenu;
    private ArrayList<ActivityInt> acts = new ArrayList<ActivityInt>();

    private ProgressDialog pDialog;
    private Double xcord;
    private Double ycord;
    private Location location;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent i = getIntent();

        // getting activity id (id) from intent
        if(i.getStringExtra(Connect.TAG_XCORD)!=null&&i.getStringExtra(Connect.TAG_YCORD)!=null) {
            xcord = Double.parseDouble(i.getStringExtra(Connect.TAG_XCORD));
            ycord = Double.parseDouble(i.getStringExtra(Connect.TAG_YCORD));
        }


        LocationManager locationManager;

        //UiSettings mapSettings;
        //mapSettings = mMap.getUiSettings();
        //mapSettings.setZoomControlsEnabled(true);

        // Get the LocationManager object from the System Service LOCATION_SERVICE
        locationManager = (LocationManager)getSystemService(LOCATION_SERVICE);

        // Create a criteria object needed to retrieve the provider
        Criteria criteria = new Criteria();

        // Get the name of the best available provider
        String provider = locationManager.getBestProvider(criteria, true);

        // We can use the provider immediately to get the last known location
        location = locationManager.getLastKnownLocation(provider);

        // request that the provider send this activity GPS updates every 5 minutes
        locationManager.requestLocationUpdates(provider, 300000, 0, this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        setTitle("World Invite");
        try {
            Integer result = new MapTask().execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        setUpMapIfNeeded();
        mMap.setOnMapClickListener(this);
        //EditText searchAddress = (EditText) findViewById(R.id.btnSearch);


        //mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {


        UiSettings theSettings = mMap.getUiSettings();
        theSettings.setCompassEnabled(false);

        if(acts!=null) {
            for(ActivityInt actInt : acts){
                ActivitySmall act = (ActivitySmall)actInt;
                Log.e("placing", act.getTitle());
                mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(Double.parseDouble(act.getYcord()),Double.parseDouble(act.getXcord())))
                        .title(act.getTitle())
                        .snippet(act.getId())
                        .visible(true));
            }
        } else {
            Log.e("null!!","acts is null");
        }

        if (xcord!=null){
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(ycord, xcord), 16));
        } else {
            if(location!=null) {
                LatLng currentPosition = new LatLng(location.getLatitude(), location.getLongitude());
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentPosition, 11));
            }
        }



        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                String id =marker.getSnippet();
                Intent in = new Intent(getApplicationContext(),
                        ViewActivity.class);
                // sending id to next activity
                in.putExtra(Connect.TAG_ID, id);
                startActivity(in);
            }
        });
        // getting product details from intent
    }

    @Override
    public void onLocationChanged(Location location) {
        if (mMap != null)
        {
            drawMarker(location);
        }
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    private void drawMarker(Location location){
        //mMap.clear();

        //  convert the location object to a LatLng object that can be used by the map API
        LatLng currentPosition = new LatLng(location.getLatitude(), location.getLongitude());

        // zoom to the current location by using Java Reflection Classes
        // instead of using simply the following code
        //mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentPosition, 16));
        try {
            Method m = GoogleMap.class.getDeclaredMethod("animateCamera", CameraUpdate.class);
            try {
                m.invoke(mMap, CameraUpdateFactory.newLatLngZoom(currentPosition,16));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        // add a marker to the map indicating our current position
        mMap.addMarker(new MarkerOptions()
                .position(currentPosition)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.green_marker)));
    }


    public void onClick_Search(View v)
    {
        EditText searchAddress = (EditText) findViewById(R.id.edtSearch);
        String check = searchAddress.getText().toString();
        if(!check.isEmpty()) {
            Geocoder theLocation = new Geocoder(this);

            List<Address> theAddresses = null;
            try {
                theAddresses = theLocation.getFromLocationName(check, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }

            if(!theAddresses.isEmpty())
            {
                Address address = theAddresses.get(0);

                double lat = address.getLatitude();
                double lng = address.getLongitude();

                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng), 16));
            }
        }
    }


    @Override
    public void onMapClick(LatLng latLng) {


    }

    class MapTask extends AsyncTask<String, Integer, Integer> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialog = new ProgressDialog(Maps.this);
            pDialog.setMessage("Loading activities...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();

        }
        /**
         * getting All products from url
         * */
        protected Integer doInBackground(String... args) {
            // Building Parameters
            ArrayList<HashMap<String, String>> x =Connect.getAllActivitiesEssentials();
            for(HashMap<String,String> act : x){
                acts.add(new ActivitySmall(act));
            }
            return null;

        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(Integer x) {
            pDialog.dismiss();

        }

    }

}
