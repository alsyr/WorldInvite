package widgas.worldinvite;

import junit.framework.TestCase;
import java.util.HashMap;

public class ActivitySmallTest extends TestCase {
    HashMap<String,String> theActivity = new HashMap<String, String>();
    ActivitySmall theActivitySmall;

    public ActivitySmallTest()
    {
        theActivity.put("tittle","theTitle");
        theActivity.put("id","theId");
        theActivity.put("xcord", "theLongitude");
        theActivity.put("ycord", "theLatitude");

        theActivitySmall = new ActivitySmall(theActivity);
    }

    public void testGetTitle() throws Exception {
        assertEquals("theTitle", theActivitySmall.getTitle());
    }

    public void testGetAllData() throws Exception {
        assertEquals(theActivity, theActivitySmall.getAllData());
    }

    public void testGetData() throws Exception {
        assertEquals(null, theActivitySmall.getData());
    }

    public void testGetId() throws Exception {
        assertEquals("theId", theActivitySmall.getId());
    }

    public void testGetXcord() throws Exception {
        assertEquals("theLongitude", theActivitySmall.getXcord());
    }

    public void testGetYcord() throws Exception {
        assertEquals("theLatitude", theActivitySmall.getYcord());
    }

    public void testGetCreatedAt() throws Exception {
        assertEquals(null, theActivitySmall.getCreatedAt());
    }
}