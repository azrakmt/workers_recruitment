package com.example.worker_recruitment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
EditText edip;
Button b1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edip=(EditText)findViewById(R.id.edip);
        b1=(Button) findViewById(R.id.btip);
        b1.setOnClickListener(this);
        edip.setText("192.168.43.3");
    }

    @Override
    public void onClick(View v) {
        String ip=edip.getText().toString();
        if(ip.equalsIgnoreCase("")){
            edip.setError("Missing");
        }
        else {


            final String ipval = edip.getText().toString();
            SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            SharedPreferences.Editor ed = sh.edit();
            ed.putString("ip", ipval);
            ed.commit();
            Toast.makeText(this, "Moving.................!!!!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), Login.class));
        }

    }
}