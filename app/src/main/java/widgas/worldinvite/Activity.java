package widgas.worldinvite;

/**
 * Created by trains on 12/1/2014.
 */
public abstract class Activity implements ActivityInt {
    private Object activity;
    public String getTitle(){
        return null;
    }

    public Object getAllData() {
        return activity;
    }

    public String getData() {
        return null;
    }

    public String getId(){
        return null;
    }

    public String getXcord(){
        return null;
    }

    public String getYcord(){
        return null;
    }

    public String getCreatedAt() {
        return null;
    }
}
