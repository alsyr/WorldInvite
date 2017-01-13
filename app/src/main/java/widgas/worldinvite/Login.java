package widgas.worldinvite;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;


public class Login extends Activity {
    Button btnGo;
    Button btnNew;
    CheckBox checkBox;
    private static String TAG_NAME = "name";
    EditText txtName;
    EditText txtPassword;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        setTitle("World Invite");
        checkBox = (CheckBox) findViewById(R.id.checkBoxRemember);
        btnGo = (Button) findViewById(R.id.btnLogin);
        final SaveAndLoad sal = new SaveAndLoad(getApplicationContext());
        btnNew = (Button) findViewById(R.id.buttonNew);
        String username = sal.load();
        if (username!=null){
            txtName = (EditText) findViewById(R.id.editName);
            txtName.setText(username);
        }

        // view user activities click event
        btnGo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Launching All products Activity
                txtName = (EditText) findViewById(R.id.editName);
                txtPassword = (EditText) findViewById(R.id.editPassword);
                if (!txtName.getText().toString().equals("")&&!txtPassword.getText().toString().equals("")) {
                    if (checkBox.isChecked()) {
                        sal.saveFile(txtName.getText().toString());
                    }

                    new LoginTask().execute();
                }

            }
        });
        btnNew.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                showNewUserAlert();
            }
        });
    }

    private void showAlert(){
        new AlertDialog.Builder(this)
                .setTitle("oops!")
                .setMessage("Wrong password, or this name is already taken!")
                .setNeutralButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
    private void showNewUserAlert(){
        new AlertDialog.Builder(this)
                .setTitle("Welcome!")
                .setMessage("To register, just type your desired name and password into the login fields")
                .setNeutralButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
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

    class LoginTask extends AsyncTask<String, Integer, Integer> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Login.this);
            pDialog.setMessage("Logging in...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        /**
         * getting All products from url
         * */
        protected Integer doInBackground(String... args) {
            // Building Parameters
            int x= Connect.login(txtName.getText().toString(),txtPassword.getText().toString());
            if (x==1) {
                finish();
                Log.d("x=1:", "x="+x);
                Intent i = new Intent(getApplicationContext(), MainMenu.class);
                i.putExtra(TAG_NAME, txtName.getText().toString());
                startActivity(i);
            }
            if(x==0) {
                finish();
                Log.d("x=0:", "x="+x);
                boolean b= Connect.newUser(txtName.getText().toString(),txtPassword.getText().toString());
                Log.e("look--------->", Boolean.toString(b));
                Intent i = new Intent(getApplicationContext(), MainMenu.class);
                i.putExtra(TAG_NAME, txtName.getText().toString());
                startActivity(i);
            }
            return x;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(Integer x) {
            // dismiss the dialog after getting all products
            pDialog.dismiss();
            if(x==2) {
                showAlert();
                Log.d("showing box", "box!");
            }

        }

    }
}
