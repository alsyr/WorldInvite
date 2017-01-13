package widgas.worldinvite;

import org.apache.http.NameValuePair;

import java.util.List;

/**
 * Created by trains on 11/21/2014.
 */
public class ActivityFull extends Activity {

    private List<NameValuePair> activity;

    public ActivityFull(List<NameValuePair> activity){
        this.activity=activity;
    }

    @Override
    public String getTitle(){
        return this.activity.get(1).getValue();
    }

    @Override
    public Object getAllData() {
        return this.activity;
    }

    @Override
    public String getData(){
        return this.activity.get(2).getValue();
    }

    @Override
    public String getXcord(){
        return this.activity.get(3).getValue();
    }

    @Override
    public String getYcord(){
        return this.activity.get(4).getValue();
    }

    @Override
    public String getCreatedAt(){
        return this.activity.get(5).getValue();
    }
}
