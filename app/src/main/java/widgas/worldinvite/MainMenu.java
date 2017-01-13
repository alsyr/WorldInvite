package widgas.worldinvite;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;


public class MainMenu extends Activity {

    Button btnViewActivities;
    Button btnViewMap;
    Button btnNewActivity;
    EditText txtName;
    private static String TAG_NAME = "name";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main_menu);
        setTitle("World Invite");
        txtName = (EditText) findViewById(R.id.nameField);

        // Buttons
        btnViewActivities = (Button) findViewById(R.id.UserActivitiesButton);
        btnNewActivity = (Button) findViewById(R.id.NewActivityButton);
        btnViewMap = (Button) findViewById(R.id.MapButton);

        Intent in = getIntent();
        final String name = in.getStringExtra(TAG_NAME);
        txtName.setText(name);

        // view user activities click event
        btnViewActivities.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Launching All products Activity
                Intent i = new Intent(getApplicationContext(), AllUserActivities.class);
                i.putExtra(TAG_NAME, txtName.getText().toString());
                startActivity(i);

            }
        });

        // new activity click event
        btnNewActivity.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Launching create new product activity
                Intent i = new Intent(getApplicationContext(), NewActivity.class);
                i.putExtra(TAG_NAME, txtName.getText().toString());
                startActivity(i);

            }
        });

        //map click event
        btnViewMap.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Launching create new product activity
               Intent i = new Intent(getApplicationContext(), Maps.class);
               startActivity(i);

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
