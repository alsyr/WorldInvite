package widgas.worldinvite;

import android.content.Context;
import android.util.Log;
import java.io.*;


/**
 * Created by trains on 12/2/2014.
 */
public class SaveAndLoad implements Serializable {

    private transient Context context;
    private String name;

    private static final long serialVersionUID = 0L;

    SaveAndLoad(Context c){
        context=c;
    }
    /*
        @param mytext   text to be saved
        @return         true if success, false otherwise
     */
    public boolean saveFile(String mytext){
        Log.i("TESTE", "SAVE");
        this.name=mytext;
        try {
            FileOutputStream fos = context.openFileOutput("username"+".txt",Context.MODE_PRIVATE);
            Writer out = new OutputStreamWriter(fos);
            out.write(mytext);
            out.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    /*
        @return     String of found username, or null if none found
     */
    public String load(){
        Log.i("TESTE", "FILE");
        try {
            FileInputStream fis = context.openFileInput("username"+".txt");
            BufferedReader r = new BufferedReader(new InputStreamReader(fis));
            String line= r.readLine();
            r.close();
            return line;
        } catch (IOException e) {
            e.printStackTrace();
            Log.i("TESTE", "FILE - false");
            return null;
        }
    }

    private void validateName(String c){
        if(c==null||c.equals("")) throw new IllegalArgumentException("name must not be null/empty");
    }
    private void validateState(){
        validateName(this.name);
    }

    private void readObject( ObjectInputStream aInputStream) throws ClassNotFoundException, IOException {
        aInputStream.defaultReadObject();
        this.name=new String(name);
        validateState();
    }
    private void writeObject(ObjectOutputStream aObjectOutputStream) throws IOException{
        aObjectOutputStream.defaultWriteObject();
    }
}
