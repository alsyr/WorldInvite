package widgas.worldinvite;

import android.util.Log;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Weston Newby on 11/3/2014.
 */
public class Connect {

    public static final String TAG_SUCCESS = "success";
    public static final String TAG_ACTIVITIES = "activities";
    public static final String TAG_ACTIVITY = "activity";
    public static final String TAG_ID = "id";
    public static final String TAG_TITTLE = "tittle";
    public static final String TAG_DATA = "data";
    public static final String TAG_POST_TIME = "post_time";
    public static final String TAG_XCORD = "xcord";
    public static final String TAG_YCORD = "ycord";
    public static final String TAG_NAME = "name";


    /* creates a new activity for a given user and adds activity to users list, ID is auto created
     * NOTE: user can have a max of 5 activities
     *
     *@param userName       user name of user creating activity
     *@param tittle         tittle of activity, max characters 200, tittle must not already exist
     *@param data           post data, max characters 800
     *@param xcord          xcordinate for map(as a string), must be no longer than XxX.XxXxXxX (3.7)
     *@param ycord          ycordinate for map(as a string), must be no longer than XxX.XxXxXxX (3.7)
     *@return              true if success, false otherwise
     */
    public static boolean newActivity(String userName, String tittle, String data, String xcord, String ycord){
        JSONParser jsonParser = new JSONParser();
        String url_create_activity = "http://104.53.222.3/group_project/create_activity.php";

        //NOTE TO SELF: edit php to not need id, autoincrement on database

        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("name", userName));
        params.add(new BasicNameValuePair("tittle", tittle));
        params.add(new BasicNameValuePair("data", data));
        params.add(new BasicNameValuePair("xcord", xcord));
        params.add(new BasicNameValuePair("ycord", ycord));

        // getting JSON Object
        // Note that create product url accepts POST method
        JSONObject json = jsonParser.makeHttpRequest(url_create_activity,
                "POST", params);

        // check log cat for response
        Log.d("Create Response", json.toString());

        // check for success tag
        try {
            int success = json.getInt(TAG_SUCCESS);

            if (success == 1) {
                // successfully created product
                return true;

            } else {
                // failed to create product
                return false;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }


    /*  Creates a new user, username must not already exits
     *  @param name     username of user to create (username must not already exist, and must be less than ___ characters)
     *  @param password password of user to create (must be less than ____ characters)
     *  @return         true if success, false otherwise
     */
    public static boolean newUser(String name, String password){

        JSONParser jsonParser = new JSONParser();
        String url_create_user = "http://104.53.222.3/group_project/create_user.php";

        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("name", name));
        params.add(new BasicNameValuePair("password", password));

        // getting JSON Object
        // Note that create product url accepts POST method
        JSONObject json = jsonParser.makeHttpRequest(url_create_user,
                "POST", params);

        // check log cat fro response
        Log.d("Create Response", json.toString());

        // check for success tag
        try {
            int success = json.getInt(TAG_SUCCESS);

            if (success == 1) {
                // successfully created product
                return true;

            } else {
                return false;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }


    /* gets all activity details for a given activity, each name value pair conatins the name of the data and the data (ie. "id", 12345)
     * @param   id     activity id to find
     * @return         list of name value pairs containing activity details, including timestamp of post time
     */
    public static List<NameValuePair> getActivityDetails(String id){
        JSONParser jsonParser = new JSONParser();
        String url_activity_details = "http://104.53.222.3/group_project/get_activity_details.php";

        List<NameValuePair> activityValues = new ArrayList<NameValuePair>();

        int success;
        try {
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("id", id));

            // getting product details by making HTTP request
            // Note that product details url will use GET request
            JSONObject json = jsonParser.makeHttpRequest(
                    url_activity_details, "GET", params);

            // check your log for json response
            Log.e("Single Activity Details", json.toString());

            // json success tag
            success = json.getInt(TAG_SUCCESS);
            if (success == 1) {
                // successfully received activity details
                JSONArray activityObj = json
                        .getJSONArray(TAG_ACTIVITY); // JSON Array

                // get first activity object from JSON Array
                JSONObject product = activityObj.getJSONObject(0);

                // add activity values to list
                activityValues.add(new BasicNameValuePair("id",product.getString(TAG_ID)));
                activityValues.add(new BasicNameValuePair("tittle",product.getString(TAG_TITTLE)));
                activityValues.add(new BasicNameValuePair("data", product.getString(TAG_DATA)));
                activityValues.add(new BasicNameValuePair("xcord", product.getString(TAG_XCORD)));
                activityValues.add(new BasicNameValuePair("ycord", product.getString(TAG_YCORD)));
                activityValues.add(new BasicNameValuePair("post_time", product.getString(TAG_POST_TIME)));

                return activityValues;

            }else{
                return null;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


    /*  Gets all activities essentails (ID, Tittle, xcord, ycord) all in string format, each activity is a hash map in the arraylist
     *
     * @return ArrayList containging a hashmap for each activity, hashmap contains activity details
     */
    public static ArrayList<HashMap<String, String>> getAllActivitiesEssentials(){
        // Creating JSON Parser object
        JSONParser jParser = new JSONParser();

        ArrayList<HashMap<String, String>> activitiesList;

        // url to get all activity essentials list
        String url_all_activities = "http://104.53.222.3/group_project/get_all_activity_essentials.php";

        activitiesList = new ArrayList<HashMap<String, String>>();

        // activities JSONArray
        JSONArray activities = null;
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        // getting JSON string from URL
        JSONObject json = jParser.makeHttpRequest(url_all_activities, "GET", params);

        // Check your log cat for JSON response
        Log.d("All Activities: ", json.toString());

        try {
            // Checking for SUCCESS TAG
            int success = json.getInt(TAG_SUCCESS);

            if (success == 1) {
                // activities found
                // Getting Array of Products
                activities = json.getJSONArray(TAG_ACTIVITIES);

                // looping through All activities
                for (int i = 0; i < activities.length(); i++) {
                    JSONObject c = activities.getJSONObject(i);

                    // Storing each json item in variable
                    String id = c.getString(TAG_ID);
                    String tittle = c.getString(TAG_TITTLE);
                    String xcord = c.getString(TAG_XCORD);
                    String ycord = c.getString(TAG_YCORD);

                    // creating new HashMap
                    HashMap<String, String> map = new HashMap<String, String>();

                    // adding each child node to HashMap key => value
                    map.put(TAG_ID, id);
                    map.put(TAG_TITTLE, tittle);
                    map.put(TAG_XCORD, xcord);
                    map.put(TAG_YCORD, ycord);

                    // adding HashMap to ArrayList
                    activitiesList.add(map);
                }
                return activitiesList;
            } else {
                return null;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


    /* gets all activities posted by a user
     * @param username      user to get activities
     * @return              Arraylist of activity ID & tittle, or null if no activities/invalid user
     */
    public static ArrayList<HashMap<String, String>> getUserActivities(String username) {
        JSONParser jsonParser = new JSONParser();
        String url_user_activities = "http://104.53.222.3/group_project/get_user_activities.php";

        ArrayList<HashMap<String,String>> activitiesList = new ArrayList<HashMap<String, String>>();
        JSONArray activities = null;

        int success;
        try {
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("name", username));

            // getting product details by making HTTP request
            // Note that product details url will use GET request
            JSONObject json = jsonParser.makeHttpRequest(
                    url_user_activities, "GET", params);

            // check your log for json response
            Log.d("All User Activities", json.toString());

            // json success tag
            success = json.getInt(TAG_SUCCESS);


            if (success == 1) {
                // successfully received activity details
                activities = json.getJSONArray(TAG_ACTIVITIES);

                // looping through All activities
                for (int i = 0; i < activities.length(); i++) {
                    JSONObject c = activities.getJSONObject(i);

                    // Storing each json item in variable
                    String id = c.getString(TAG_ID);
                    String tittle = c.getString(TAG_TITTLE);

                    // creating new HashMap
                    HashMap<String, String> map = new HashMap<String, String>();

                    // adding each child node to HashMap key => value
                    map.put(TAG_ID, id);
                    map.put(TAG_TITTLE, tittle);

                    // adding HashList to ArrayList
                    activitiesList.add(map);
                }
                return activitiesList;

            }else{
                return null;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


    /*  deletes a user and all associated activities
     *  @param      username to be deleted
     *  @return     true if success, false if failed
     */
    public static boolean deleteUser(String username){
        int success;
        JSONParser jsonParser = new JSONParser();
        String url_delete_user = "http://104.53.222.3/group_project/delete_user.php";

        try {
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("name", username));

            // getting activity details by making HTTP request
            JSONObject json = jsonParser.makeHttpRequest(
                    url_delete_user, "POST", params);

            // check your log for json response
            Log.d("Delete user", json.toString());

            // json success tag
            success = json.getInt(TAG_SUCCESS);
            if (success == 1) {
                // user successfully deleted
                return true;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }


    /* deletes an activity
     * @param id        activity id to delete
     * @return          true if success, false if otherwise
     */
    public static boolean deleteActivity(String id){
        int success;
        JSONParser jsonParser = new JSONParser();
        String url_delete_activity = "http://104.53.222.3/group_project/delete_activity.php";

        try {
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("id", id));

            // getting activity details by making HTTP request
            JSONObject json = jsonParser.makeHttpRequest(
                    url_delete_activity, "POST", params);

            // check your log for json response
            Log.d("Delete activity", json.toString());

            // json success tag
            success = json.getInt(TAG_SUCCESS);
            if (success == 1) {
                // activity successfully deleted
                return true;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static int login(String name, String password){
        int success;
        JSONParser jsonParser = new JSONParser();
        String url_login = "http://104.53.222.3/group_project/login.php";

        try {
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("name", name));
            params.add(new BasicNameValuePair("password", password));

            // getting activity details by making HTTP request
            JSONObject json = jsonParser.makeHttpRequest(
                    url_login, "POST", params);

            // check your log for json response
            Log.d("Login", json.toString());

            // json success tag
            success = json.getInt(TAG_SUCCESS);
            if (success == 1) {
                //user info correct
                return 1;
            }
            if (success==2) {
                //user exists, but password is wrong
                return 2;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }


}

