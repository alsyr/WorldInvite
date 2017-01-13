package widgas.worldinvite;

import java.util.HashMap;

/**
 * Created by trains on 11/21/2014.
 */
public class ActivitySmall extends Activity  {
    private HashMap<String,String> activity;

    public ActivitySmall(HashMap<String,String> activity){
        this.activity=activity;
    }

    @Override
    public String getTitle(){
        return this.activity.get(Connect.TAG_TITTLE);
    }

    @Override
    public Object getAllData() {
        return this.activity;
    }

    @Override
    public String getId(){
        return this.activity.get(Connect.TAG_ID);
    }

    @Override
    public String getXcord(){
        return this.activity.get(Connect.TAG_XCORD);
    }

    @Override
    public String getYcord(){
        return this.activity.get(Connect.TAG_YCORD);
    }
}
