package widgas.worldinvite;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class NewActivity extends Activity {

    private String name;
    private Button button;
    private EditText txtTittle;
    private EditText txtTime;
    private EditText txtDescription;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_activity);
        button = (Button) findViewById(R.id.buttonDone);
        setTitle("World Invite");
        Intent i = getIntent();

        // getting username from intent
        name = i.getStringExtra(Connect.TAG_NAME);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                txtTittle = (EditText) findViewById(R.id.tittleText);
                txtTime = (EditText) findViewById(R.id.timeText);
                txtDescription = (EditText) findViewById(R.id.descriptionText);
                String data = "[" + txtTime.getText().toString() + "]" + txtDescription.getText().toString();
                String tittle = txtTittle.getText().toString();
                if(tittle!=null&&data!=null) {
                    Intent i = new Intent(getApplicationContext(), LocationSelect.class);
                    i.putExtra(Connect.TAG_TITTLE, tittle);
                    i.putExtra(Connect.TAG_DATA, data);
                    i.putExtra(Connect.TAG_NAME, name);
                    finish();
                    startActivity(i);
                }
            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.neww, menu);
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