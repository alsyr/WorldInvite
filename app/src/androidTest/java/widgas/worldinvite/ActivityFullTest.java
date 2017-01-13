package widgas.worldinvite;

import junit.framework.TestCase;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.*;

public class ActivityFullTest extends TestCase {
    List<NameValuePair> theActivity = new ArrayList<NameValuePair>();
    ActivityFull theActivityFull;

    public ActivityFullTest()
    {
        theActivity.add(new BasicNameValuePair("0", "nothing"));
        theActivity.add(new BasicNameValuePair("1", "theTitle"));
        theActivity.add(new BasicNameValuePair("2", "theData"));
        theActivity.add(new BasicNameValuePair("3", "theLongitude"));
        theActivity.add(new BasicNameValuePair("4", "theLatitude"));
        theActivity.add(new BasicNameValuePair("5", "theDate"));

        theActivityFull = new ActivityFull(theActivity);
    }

    public void testGetTitle() throws Exception {
        assertEquals("theTitle", theActivityFull.getTitle());
    }

    public void testGetAllData() throws Exception {
        assertEquals(theActivity, theActivityFull.getAllData());
    }

    public void testGetData() throws Exception {
        assertEquals("theData", theActivityFull.getData());
    }

    public void testGetId() throws Exception {
        assertEquals(null, theActivityFull.getId());
    }

    public void testGetXcord() throws Exception {
        assertEquals("theLongitude", theActivityFull.getXcord());
    }

    public void testGetYcord() throws Exception {
        assertEquals("theLatitude", theActivityFull.getYcord());
    }

    public void testGetCreatedAt() throws Exception {
        assertEquals("theDate", theActivityFull.getCreatedAt());
    }
}