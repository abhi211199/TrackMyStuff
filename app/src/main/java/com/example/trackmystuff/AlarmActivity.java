package com.example.trackmystuff;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AlarmActivity extends AppCompatActivity {
Button stop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        stop=(Button)findViewById(R.id.stop);
        MediaPlayer ring;
        //ring = MediaPlayer.create(AlarmActivity.this, R.raw.abcd);

        //ring.start();
    }
    public void stopmusic(View view)
    {
       // Intent intent=new Intent(AlarmActivity.this,Primary.class);
              //  startActivity(intent);
        this.finish();
       /// MediaPlayer ring;
        //ring = MediaPlayer.create(AlarmActivity.this, R.raw.abcd);
       // ring.stop();
    }

}
