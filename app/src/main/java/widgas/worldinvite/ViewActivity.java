package widgas.worldinvite;

import java.util.List;

import org.apache.http.NameValuePair;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.socialize.entity.Entity;

public class ViewActivity extends Activity {

    List<NameValuePair> values;
    EditText txtTittle;
    EditText txtData;
    String Xcord;
    String Ycord;
    EditText txtCreatedAt;
    Button btnMap;
    Button btnComment;
    Entity entity;
    //Button btnDelete;

    String id;

    // Progress Dialog
    private ProgressDialog pDialog;

    // single product url
    private static final String url_product_detials = "http://104.53.222.3/group_project/get_activity_details.php";

    // url to update product
    //private static final String url_update_product = "http://10.0.2.2/android_connect/update_product.php";

    // url to delete activity
    private static final String url_delete_product = "http://10.0.2.2/android_connect/delete_activity.php";

    // JSON Node names
    private static final String TAG_ID = "id";
    private static final String TAG_XCORD = "xcord";
    private static final String TAG_YCORD = "ycord";
    private ActivityInt act;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        setTitle("World Invite");

        btnMap = (Button) findViewById(R.id.btnViewOnMap);
        btnComment = (Button) findViewById(R.id.btnViewComments);



        Intent i = getIntent();

        // getting activity id (id) from intent
        id = i.getStringExtra(TAG_ID);

        // Getting complete activity details in background thread
        new GetActivityDetails().execute();

        btnMap.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                 //starting background task to update product
                Intent in = new Intent(getApplicationContext(),
                        Maps.class);
                // sending id to next activity
                in.putExtra(Connect.TAG_XCORD,Xcord);
                in.putExtra(Connect.TAG_YCORD,Ycord);
                startActivity(in);

            }
        });

        btnComment.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                //starting background task to update product
                Intent in = new Intent(getApplicationContext(),
                        Comment.class);
                in.putExtra("Entity", entity);
                startActivity(in);
            }
        });


    }

    /**
     * Background Async Task to Get complete product details
     * */
    public class GetActivityDetails extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ViewActivity.this);
            pDialog.setMessage("Loading activity details. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Getting product details in background thread
         * */
        protected String doInBackground(String... params) {
            values = Connect.getActivityDetails(id);

            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                public void run() {
                    // Check for success tag
                    int success;
                    try {

                        if (values!=null){
                            act=new ActivityFull(values);
                            // product with this pid found
                            // Edit Text
                            txtTittle = (EditText) findViewById(R.id.inputTittle);
                            txtData = (EditText) findViewById(R.id.inputData);
                            txtCreatedAt = (EditText) findViewById(R.id.inputCreatedAt);
                            txtTittle.setKeyListener(null);
                            txtData.setKeyListener(null);
                            txtCreatedAt.setKeyListener(null);
                            // display product data in EditText
                            txtTittle.setText(act.getTitle());
                            txtData.setText(act.getData());
                            Xcord=(act.getXcord());
                            Ycord=(act.getYcord());
                            txtCreatedAt.setText(act.getCreatedAt());

                            // For Socialize
                            entity = Entity.newInstance(id, act.getTitle());

                        }else{
                            finish();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            return null;
        }


        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once got all details
            pDialog.dismiss();
        }
    }
}
