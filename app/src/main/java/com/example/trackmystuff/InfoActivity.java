//contains iformation abt creators and a button which laumches the main app//future work-add animation in this class
package com.example.trackmystuff;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

public class InfoActivity extends AppCompatActivity {
CheckBox check;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        check=(CheckBox)findViewById(R.id.accept);

    }
public void fx(View view)
{
    Boolean status=check.isChecked();
    if(status==true)
    {
        Intent intent=new Intent(InfoActivity.this,ChoosePS.class);
        Toast.makeText(getBaseContext(),"TERMS and CONDITIONS accepted",Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }
    else if(status==false)
    {
        Toast.makeText(getBaseContext(),"TERMS and CONDITIONS not accepted.TrackMyStuff was closed",Toast.LENGTH_SHORT).show();
        finishAffinity();
           }
}

}
