package widgas.worldinvite;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class AllUserActivities extends ListActivity {

    // Progress Dialog
    private ProgressDialog pDialog;


    ArrayList<HashMap<String, String>> activitiesList;

    // JSON Node names
    private static final String TAG_NAME = "name";
    private static final String TAG_ID = "id";
    private static final String TAG_TITTLE = "tittle";

    // products JSONArray
    JSONArray activities = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_user_activities);
        setTitle("World Invite");

        // Hashmap for ListView
        activitiesList = new ArrayList<HashMap<String, String>>();

        // Loading products in Background Thread
        new LoadAllActivities().execute();

        // Get listview
        ListView lv = getListView();

        // on seleting single product
        // launching Edit Product Screen
        lv.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // getting values from selected ListItem
                String ID = ((TextView) view.findViewById(R.id.id)).getText()
                        .toString();

                // Starting new intent
                Intent in = new Intent(getApplicationContext(),
                        ViewActivity.class);
                // sending id to next activity
                in.putExtra(TAG_ID, ID);
                startActivity(in);
                // starting new activity and expecting some response back

            }
        });

    }


    /**
     * Background Async Task to Load all product by making HTTP Request
     * */
    class LoadAllActivities extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(AllUserActivities.this);
            pDialog.setMessage("Loading activities. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        /**
         * getting All products from url
         * */
        protected String doInBackground(String... args) {
            Intent i = getIntent();

            // getting activity id (id) from intent
            String name = i.getStringExtra(TAG_NAME);

            activitiesList=Connect.getUserActivities(name);
            if(activitiesList==null){
                Intent in = new Intent(getApplicationContext(),
                    MainMenu.class);
                //Closing all previous activities
                in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(in);
            }


            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after getting all products
            pDialog.dismiss();
            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                public void run() {
                    /**
                     * Updating parsed JSON data into ListView
                     * */
                    ListAdapter adapter = new SimpleAdapter(
                            AllUserActivities.this, activitiesList,
                            R.layout.list_item_group, new String[] { TAG_ID,
                            TAG_TITTLE},
                            new int[] { R.id.id, R.id.tittle, });
                    // updating listview
                    setListAdapter(adapter);
                }
            });

        }

    }

}