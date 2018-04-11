package com.example.trackmystuff;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ChoosePS extends AppCompatActivity {
Button pri,sec;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_ps);
        pri=(Button)findViewById(R.id.primary);
        sec=(Button)findViewById(R.id.secondary);
    }
    public void fx(View view)
    {
        Intent intent=new Intent(ChoosePS.this,Primary.class);
        startActivity(intent);
    }
    public void fx1(View view)
    {
        Intent intent=new Intent(ChoosePS.this,Secondary.class);
        startActivity(intent);
    }
}
