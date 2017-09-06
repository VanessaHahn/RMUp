package mi.ur.de.android.runnersmeetup;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class SplashScreenActivity extends AppCompatActivity {

    private ProgressBar circularProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        SharedPreferences prefs = getSharedPreferences("RunCondition",MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("run", false);
        editor.commit();

        prefs = this.getSharedPreferences("LoginData",MODE_PRIVATE);
        int userId = prefs.getInt(Constants.KEY_ID, -1);
        Constants.setId(userId);
        String userName = prefs.getString(Constants.KEY_NAME, null);
        Constants.setName(userName);

        circularProgress = (ProgressBar) findViewById(R.id.progressBar);
        circularProgress.setVisibility(View.VISIBLE);
        Animation anim = AnimationUtils.loadAnimation(this,R.anim.move);
        ImageView imageView = (ImageView) findViewById(R.id.imageRunner);
        imageView.setAnimation(anim);



        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(Constants.getId() != -1 && Constants.getName() != null) {
                    startActivity(new Intent(SplashScreenActivity.this, NavigationDrawer.class));
                } else {
                    startActivity(new Intent(SplashScreenActivity.this, LoginActivity.class));
                    finish();
                }
            }
        },4000);
    }
}
