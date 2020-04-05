package com.jovialway.phonecall;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CALL=1;

EditText numberET,body,subject,mailid;
    ImageView callIV,mailIV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mailid=findViewById(R.id.mailid);
        subject=findViewById(R.id.subject);
        body=findViewById(R.id.body);
        numberET=findViewById(R.id.numberET);
        callIV=findViewById(R.id.callIV);
        mailIV=findViewById(R.id.mailIV);

        callIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makephoneCall();

                }
        });

        mailIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendmail();
                }
        });

    }
    private void sendmail() {
     String   mail=mailid.getText().toString();
     String   msubject=subject.getText().toString();
     String   mbody=body.getText().toString();

        Log.i("Send email", "");
        String[] TO = {mail};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Static subject "+ msubject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Static body "+ mbody);

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();

        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(MainActivity.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }}



    private void makephoneCall() {
        String number=numberET.getText().toString();
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE)!=getPackageManager().PERMISSION_GRANTED){
        ActivityCompat.requestPermissions(MainActivity.this,new String[] {Manifest.permission.CALL_PHONE},REQUEST_CALL);
        }else {
        String dial="tel:"+number;
        startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
        }
                                }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode==REQUEST_CALL){
            if (grantResults.length<0 && grantResults[0] == getPackageManager().PERMISSION_GRANTED){
                makephoneCall();
            }else {
                Toast.makeText(this, "Call Permission Denied from Phone", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
