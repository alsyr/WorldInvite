package widgas.worldinvite;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.socialize.ActionBarUtils;
import com.socialize.CommentUtils;
import com.socialize.Socialize;
import com.socialize.entity.Entity;
import com.socialize.ui.actionbar.ActionBarOptions;
import com.socialize.ui.actionbar.ActionBarView;

/**
 * Created by lche on 11/23/14.
 */
public class Comment extends Activity {

    Entity entity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        Intent i = getIntent();
        entity = (Entity)i.getSerializableExtra("Entity");

        // Call Socialize in onCreate
        Socialize.onCreate(this, savedInstanceState);

        // Your entity key. May be passed as a Bundle parameter to your activity
        String entityKey = "http://r.getsocialize.com/a/555159";

        //Entity entity = Entity.newInstance("http://myentity.com", "My Name");

        // Show the comment list view
        // The "this" argument refers to the current Activity
                CommentUtils.showCommentView(this, entity);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Call Socialize in onPause
        Socialize.onPause(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Call Socialize in onResume
        Socialize.onResume(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Call Socialize in onStart
        Socialize.onStart(this);
    }

    @Override
    protected void onStop() {
        super.onStop();

        // Call Socialize in onStop
        Socialize.onStop(this);
    }

    @Override
    protected void onDestroy() {
        // Call Socialize in onDestroy before the activity is destroyed
        Socialize.onDestroy(this);

        super.onDestroy();
    }

}