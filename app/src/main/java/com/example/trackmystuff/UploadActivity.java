package com.example.trackmystuff;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class UploadActivity extends AppCompatActivity {
    private StorageReference mStorageRef;
    private Uri filePath;
    Button upload;
    String downloadlink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        mStorageRef = FirebaseStorage.getInstance().getReference();
        upload=(Button)findViewById(R.id.upload);
      //fx();
        Intent intent=new Intent(this,Primary.class);
        startActivity(intent);

    }

    public void fx(View view) {
Intent intent=getIntent();
//Integer no=intent.getIntExtra("no",0);
//Integer mobile=no;
        String mobile=intent.getStringExtra("no");

        Toast.makeText(getBaseContext(),mobile,Toast.LENGTH_SHORT).show();
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
                          //    downloadlink = riversRef.getDownloadUrl();

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


}