package com.example.trackmystuff;
import android.*;
import android.Manifest;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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

import static android.widget.Toast.LENGTH_SHORT;

public class Secondary extends AppCompatActivity implements LocationListener {

    EditText phone;
    TextView textView;
    Button register;
    LocationManager locationManager;
    Double lat,lon;
    public String number;
   public String text;
   public String mobile;
    private  Uri filePath;
    private StorageReference mStorageRef;
    Timer t=new Timer();
    String latit,longit;
public String idstat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // getSupportActionBar().hide();
        setContentView(R.layout.activity_secondary);
        phone=(EditText)findViewById(R.id.editText2);
        register=(Button)findViewById(R.id.register);
        mStorageRef = FirebaseStorage.getInstance().getReference();

        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 101);

        }


       getLocation();

       // getInput();
        registerReceiver(onComplete,new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
       // write1(lat.toString(),lon.toString());
    }
    void getLocation() {
        try {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 5, this);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        try {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            //.makeText(MainActivity.this,addresses.g)
        }catch(Exception e)
        {
        }

        lat = location.getLatitude();
        lon = location.getLongitude();
latit=Double.toString(lat);
longit=Double.toString(lon);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(Secondary.this, "Please Enable GPS and Internet", LENGTH_SHORT).show();
    }

public void wr(View view)
{mobile=phone.getText().toString();
download("id"+mobile+".txt");
  //  fx1("lat"+mobile+".txt");
   // fx1("long"+mobile+".txt");
}


    public void write1(String content,String content1)//writes files in downloads
    {
        String mobile=phone.getText().toString();
        FileOutputStream fop = null;
        File file;
        //String content = "This is the text content";
        mobile=phone.getText().toString();
        String Filename=("lat"+mobile+".txt");
        String Filename1=("long"+mobile+".txt");
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
        //////////////////////////////////////////
        try {

            file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),Filename1);
            fop = new FileOutputStream(file);

            // if file doesnt exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }

            // get the content in bytes
            byte[] contentInBytes1 = content1.getBytes();

            fop.write(contentInBytes1);
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

    public void read()//read files from Downloads
    {
        mobile=phone.getText().toString();
               String Filename=("id"+mobile+".txt");
        //Toast.makeText(getBaseContext(),mobile,Toast.LENGTH_SHORT).show();
        File file=new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),Filename);

        try{
            int length=(int) file.length();
            byte[] bytes = new  byte[length];
            FileInputStream fileInputStream = new FileInputStream(file);
            fileInputStream.read(bytes);
            text=new String(bytes);
            idstat=text;
            Toast.makeText(getBaseContext(),idstat,Toast.LENGTH_SHORT).show();
            //  primlatitude1=Integer.parseInt(text);
            //primlatitude1=Double.parseDouble(text);

        }catch (FileNotFoundException e)
        {
            e.printStackTrace();


        }catch (IOException e){
            e.printStackTrace();

        }

    }

    public void fx1(String abc) {
mobile=phone.getText().toString();
        filePath = Uri.parse("file:///storage/emulated/0/Download/"+abc);


        if (filePath != null) {
            //displaying a progress dialog while upload is going on
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading");
            progressDialog.show();

            final StorageReference riversRef = mStorageRef.child(abc);
            Toast.makeText(getBaseContext(),"successs",Toast.LENGTH_SHORT).show();
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
    public void download(final String name)//download files from firebase-sec mobile lat&long dwnld
    {
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

    public void start()
    {
        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Toast.makeText(getBaseContext(),"downloading",Toast.LENGTH_SHORT).show();
download("id.txt");
            }
        },0,60000);
    }


    BroadcastReceiver onComplete = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(Secondary.this, "Recieved"+idstat, Toast.LENGTH_SHORT).show();
            read();
            if(idstat==null)
                read();
            if (Integer.parseInt(idstat) == 1) {
                Toast.makeText(getBaseContext(),"Track Started",Toast.LENGTH_SHORT).show();
                mobile=phone.getText().toString();
write1(latit,longit);
                fx1("lat" + mobile + ".txt");
                fx1("long" + mobile + ".txt");
                Toast.makeText(getBaseContext(),"uploaded",Toast.LENGTH_SHORT).show();
            }
        }
    };

}
