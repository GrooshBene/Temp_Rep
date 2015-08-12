package kr.applepi.phonecall;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by user on 2015-08-09.
 */
public class Splash extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedlnstanceState) {
        super.onCreate(savedlnstanceState);
        setContentView(R.layout.splash);

        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {@Override public void run() {finish();}},3000);
    }
}
