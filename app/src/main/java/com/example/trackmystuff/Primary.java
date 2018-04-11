package com.example.trackmystuff;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class Primary extends AppCompatActivity implements LocationListener {
    LocationManager locationManager;
    Location location;
    EditText no;
    TextView locprim, locsec;
    Button map1, map2;
   // Double primlat, primlong;
    String seclat, seclong;
    // private Uri  pathh1,pathh2;
    static final int READ_BLOCK_SIZE = 100;
    // StorageReference mStorageRef;
    String text,text1;
    private StorageReference mStorageRef;
   public String mobile;
  private Uri filePath;
    double primlatitude,primlongitude;
    double primlatitude1,primlongitude1;
    Integer flag=0,flag1=0;
    Integer flg=0;
    Timer t=new Timer();
  // MediaPlayer ring= MediaPlayer.create(Primary.this,R.raw.abcd);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_primary);
        no = (EditText) findViewById(R.id.rmn);
        locprim = (TextView) findViewById(R.id.primarylocinfo);
        locsec = (TextView) findViewById(R.id.secondarylocinfo);
        map1 = (Button) findViewById(R.id.mapprimary);
        map2 = (Button) findViewById(R.id.mapsecondary);
      //  Uri filepath;
      //mobile=no.getText().toString();
        //Toast.makeText(getBaseContext(),"successs",Toast.LENGTH_SHORT).show();

        mStorageRef = FirebaseStorage.getInstance().getReference();


            String mobno = no.getText().toString();
            if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 101);

            }

            registerReceiver(onComplete,new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));

        Toast.makeText(getBaseContext(),"getting primary location",Toast.LENGTH_SHORT).show();
            getLocation();




    }



    void getLocation() {
        try {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 5, this);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }


    public void onLocationChanged(Location location) {
        locprim.setText("Latitude: " + location.getLatitude() + "\n" + "Longitude: " + location.getLongitude());
        primlatitude = location.getLatitude();
        primlongitude = location.getLongitude();
        try {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            locprim.setText(locprim.getText() + "\n" + addresses.get(0).getAddressLine(0) + ", " +
                    addresses.get(0).getAddressLine(1) + ", " + addresses.get(0).getAddressLine(2));
        } catch (Exception e) {

        }
 primlatitude=location.getLatitude();
        primlongitude=location.getLongitude();
    }


    public void onProviderDisabled(String provider) {
        Toast.makeText(Primary.this, "Please Enable GPS and Internet", Toast.LENGTH_SHORT).show();
    }


    public void onStatusChanged(String provider, int status, Bundle extras) {

    }


    public void onProviderEnabled(String provider) {

    }

    public void mapprim(View view)//plots map
    {
        Toast.makeText(getBaseContext(),"map primary",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
        intent.putExtra("lat", primlatitude);
        intent.putExtra("long", primlongitude);
        startActivity(intent);
    }
    public void mapprim1(View view)//plots map
    {
        Toast.makeText(getBaseContext(),"map secondary",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
        intent.putExtra("lat", primlatitude1);
        intent.putExtra("long", primlongitude1);
        startActivity(intent);
    }

    public void download(final String name)//download files from firebase-sec mobile lat&long dwnld
    {
       // Toast.makeText(getBaseContext(),"download called",Toast.LENGTH_SHORT).show();
        mStorageRef = FirebaseStorage.getInstance().getReference();


        mStorageRef.child(name).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                DownloadManager.Request request = new DownloadManager.Request(uri);
                request.setTitle("File download from FIREBASE");
                request.setDescription("File is being downloaded....");
                request.allowScanningByMediaScanner();
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, name);
                DownloadManager manager = (DownloadManager) getApplicationContext().getSystemService(Context.DOWNLOAD_SERVICE);
                manager.enqueue(request);

        }}).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

    }
   public void write1(String content)//writes files in downloads
{
    FileOutputStream fop = null;
    File file;
    //String content = "This is the text content";
    mobile=no.getText().toString();
String Filename=("id"+mobile+".txt");

   // Toast.makeText(getBaseContext(),Filename,Toast.LENGTH_SHORT).show();
    try {

        file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),Filename);
        fop = new FileOutputStream(file);

        // if file doesnt exists, then create it
        if (!file.exists()) {
            file.createNewFile();
        }

        // get the content in bytes
        byte[] contentInBytes = content.getBytes();

        fop.write(contentInBytes);
        fop.flush();
        fop.close();

        System.out.println("Done");

    } catch (IOException e) {
        e.printStackTrace();
    } finally {
        try {
            if (fop != null) {
                fop.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

public void ring()//rings alarm
{ //MediaPlayer ring= MediaPlayer.create(Primary.this,R.raw.abcd);
    /*if(fla==0)
    {

    ring.start();}
    else if(fla==1)
    {  ring.stop();
    ring.release();}*/

    Toast.makeText(getBaseContext(),"alert",Toast.LENGTH_SHORT).show();
    Intent intent=new Intent(Primary.this,AlarmActivity.class);
    startActivity(intent);

}

public void rem(String abc)//deletes dwnld files from DOWNLOAD
{
   File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),abc);
   file.delete();
      }

    public void rem1(String abc)//deletes dwnld files from firebase
    {
        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        StorageReference deleteRef = storageRef.child(abc);
        deleteRef.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
            }
        });
    }

public void starttrack(View view) throws InterruptedException {

    mobile=no.getText().toString();
    write1("1");
      fx1();
    t.scheduleAtFixedRate(new TimerTask() {

        @Override
        public void run() {
           flag=1;
           rem("lat"+mobile+".txt");
            download("lat" + mobile + ".txt");
            rem("long"+mobile+".txt");
            download("long" + mobile + ".txt");
           // Toast.makeText(getBaseContext(),"downloaded at interval",Toast.LENGTH_SHORT).show();
        }


    },0,60000);



}
    public void read()//read files from Downloads
    {
        mobile=no.getText().toString();
        String Filename=("lat"+mobile+".txt");
        String Filename1=("long"+mobile+".txt");
        //Toast.makeText(getBaseContext(),mobile,Toast.LENGTH_SHORT).show();
        File file=new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),Filename);
        File file1=new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),Filename1);

        try{
            int length=(int) file.length();
            byte[] bytes = new  byte[length];
            FileInputStream fileInputStream = new FileInputStream(file);
            fileInputStream.read(bytes);
            text=new String(bytes);
          //  primlatitude1=Integer.parseInt(text);
primlatitude1=Double.parseDouble(text);

        }catch (FileNotFoundException e)
        {
            e.printStackTrace();


        }catch (IOException e){
            e.printStackTrace();

        }
        //////////////////////////
        try{
            int length=(int) file1.length();
            byte[] bytes1 = new  byte[length];
            FileInputStream fileInputStream = new FileInputStream(file1);
            fileInputStream.read(bytes1);
            text1=new String(bytes1);
          //  primlongitude1=Integer.parseInt(text1);
            primlongitude1=Double.parseDouble(text1);

        }catch (FileNotFoundException e)
        {
            e.printStackTrace();


        }catch (IOException e){
            e.printStackTrace();

        }

      String msg=Double.toString(primlatitude1);
        String msg1=Double.toString(primlongitude1);

locsec.setText(msg+"\n"+msg1);
    }
public void registeruser(View view)
{
    mobile=no.getText().toString();
    write1("0");
    fx1();
   }

public void stoptrack(View view)
{
    mobile=no.getText().toString();
    flag=1;
    t.cancel();
    rem1("id"+mobile+".txt");
    rem1("lat"+mobile+".txt");
    rem("long"+mobile+".txt");
    rem("lat"+mobile+".txt");
    rem1("long"+mobile+".txt");
    write1("0");
       fx1();
    rem("id"+mobile+".txt");
    Toast.makeText(getBaseContext(),"cancel",Toast.LENGTH_SHORT).show();


}

    public void fx1() {

        filePath = Uri.parse("file:///storage/emulated/0/Download/"+"id"+mobile+".txt");
        //if there is a file to upload
        //String file = getCacheDir().toString() + "/mytextfile.txt";
        if (filePath != null) {
            //displaying a progress dialog while upload is going on
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading");
            progressDialog.show();

            final StorageReference riversRef = mStorageRef.child("id"+mobile+".txt");
            riversRef.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //if the upload is successfull
                            //hiding the progress dialog
                            progressDialog.dismiss();
                            //  downloadlink = riversRef.getDownloadUrl();

                            //and displaying a success toast
                            Toast.makeText(getApplicationContext(), "File Uploaded \n" + mStorageRef.getDownloadUrl().toString(), Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            //if the upload is not successfull
                            //hiding the progress dialog
                            progressDialog.dismiss();

                            //and displaying error message
                            Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            //calculating progress percentage
                            double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();

                            //displaying percentage in progress dialog
                            progressDialog.setMessage("Uploaded " + ((int) progress) + "%...");
                        }
                    });

        }
        //if there is not any file

    }

BroadcastReceiver onComplete = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(Primary.this,"Recieved",Toast.LENGTH_SHORT).show();
            while(primlongitude1==0||primlatitude1==0) {
                read();
            }
            if(((Math.abs(primlatitude-primlatitude1)>0.0001)||(Math.abs(primlongitude-primlongitude1)>0.0001))&&(primlongitude1!=0)&&(primlatitude1!=0)&&(primlongitude!=0&&primlatitude!=0)&&flag==0)
                ring();
           // if(primlong!=primlongitude1&&flag==0)

            flag=0;
        }
            };





}


