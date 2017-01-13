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
import java.util.*;

public class LocationSelect extends FragmentActivity implements LocationListener, GoogleMap.OnMapClickListener{

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    // private MenuActivity theMenu;
    private String name;
    private String tittle;
    private String data;
    private String xcord;
    private String ycord;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

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
        Location location = locationManager.getLastKnownLocation(provider);

        // request that the provider send this activity GPS updates every 20 seconds
        locationManager.requestLocationUpdates(provider, 100000, 0, this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_select);
        setTitle("World Invite");

        //EditText searchAddress = (EditText) findViewById(R.id.btnSearch);
        setUpMapIfNeeded();
        mMap.setOnMapClickListener(this);

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

        // getting product details from intent
        Intent i = getIntent();

        // getting activity id (id) from intent
        this.name=i.getStringExtra(Connect.TAG_NAME);
        this.tittle = i.getStringExtra(Connect.TAG_TITTLE);
        this.data = i.getStringExtra(Connect.TAG_DATA);

        UiSettings theSettings = mMap.getUiSettings();
        theSettings.setCompassEnabled(false);

    }

    @Override
    public void onLocationChanged(Location location) {

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

        // zoom to the current location
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentPosition, 16));

        // add a marker to the map indicating our current position
        mMap.addMarker(new MarkerOptions()
                .position(currentPosition)
                .snippet("Lat:" + location.getLatitude() + "Lng:"+ location.getLongitude()));

    }

    @Override
    public void onMapClick(LatLng latLng) {

        Context theContext = getApplicationContext();
        Geocoder theLocation = new Geocoder(this);
        xcord = String.valueOf(latLng.longitude);
        ycord = String.valueOf(latLng.latitude);
        new NewActivityTask().execute();


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


    class NewActivityTask extends AsyncTask<Void, Boolean, Boolean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(LocationSelect.this);
            pDialog.setMessage("Creating activity...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }
        /**
         * getting All products from url
         * */
        protected Boolean doInBackground(Void... args) {
            // Building Parameters
            Boolean x= Connect.newActivity(name,tittle,data,xcord,ycord);
            Log.e("true = success", String.valueOf(x));
            return x;

        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(Boolean x) {
            pDialog.dismiss();
            if(x){

                Intent i = new Intent(getApplicationContext(), MainMenu.class);
                i.putExtra(Connect.TAG_NAME,name);
                finish();
                startActivity(i);
            }
        }

    }
}
