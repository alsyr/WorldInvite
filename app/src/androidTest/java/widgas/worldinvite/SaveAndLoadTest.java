package widgas.worldinvite;

import android.content.Context;
import android.content.Intent;
import android.test.AndroidTestCase;
import android.test.mock.MockContext;


/**
 * Created by trains on 12/2/2014.
 */
public class SaveAndLoadTest extends AndroidTestCase {
    Login login;
    SaveAndLoad sal;
    String username = "bob";

    @Override
    public void setUp() throws Exception{
        super.setUp();
        login = new Login();
        sal = new SaveAndLoad(getContext());
    }

    public void testSave(){
        assertEquals(true,sal.saveFile(username));
    }
    public void testLoad(){
        sal.saveFile(username);
        assertEquals(username, sal.load());
    }


}
