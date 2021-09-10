package com.example.worker_recruitment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import androidx.appcompat.app.AppCompatActivity;

import android.preference.PreferenceManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class view_profile extends AppCompatActivity  {
    ImageView img;
    String name, phone_no,place,post,occupation,email,skill;
    Button edit;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);
        ImageView ir= (ImageView) findViewById(R.id.imageView8);
        TextView nam = (TextView) findViewById(R.id.textView13);
        TextView pn= (TextView) findViewById(R.id.textView14);
        TextView plc = (TextView) findViewById(R.id.textView15);
        TextView ps= (TextView) findViewById(R.id.textView16);
        TextView ocptn= (TextView) findViewById(R.id.textView17);
        TextView emal = (TextView) findViewById(R.id.textView18);
        TextView skil=(TextView) findViewById(R.id.textView19);
        edit=(Button) findViewById(R.id.button5);



        final SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        String hu = sh.getString("ip", "");
        String url = "http://" + hu + ":5000/view_profile";

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest postRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                // response
                try {
                    JSONObject jsonObj = new JSONObject(response);
                    if (jsonObj.getString("status").equalsIgnoreCase("ok")) {

                        name=jsonObj.getString("name");
                        nam.setText(name);
                        phone_no=jsonObj.getString("phone_no");
                        pn.setText(phone_no);
                        place=jsonObj.getString("place");
                        plc.setText(place);
                        post=jsonObj.getString("post");
                        ps.setText(post);
                        occupation=jsonObj.getString("occupation");
                        ocptn.setText(occupation);
                        email=jsonObj.getString("email");
                        emal.setText(email);
                        skill=jsonObj.getString("skill");
                        skil.setText(skill);

//
                        String image=jsonObj.getString("image");
                        SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        String ip=sh.getString("ip","");

                        String url="http://" + ip + ":5000"+image;
                        Toast.makeText(getApplicationContext(), "img url="+url, Toast.LENGTH_SHORT).show();


                        Picasso.with(getApplicationContext()).load(url). into(ir);


                    }


                    // }
                    else {
                        Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_LONG).show();
                    }

                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Error" + e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }
            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // error
                Toast.makeText(getApplicationContext(), "eeeee" + error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                Map<String, String> params = new HashMap<String, String>();
                String id = sh.getString("lid", "");
                params.put("lid", id);
//                params.put("dob", dobb);
//                params.put("email", emaill);
//                params.put("phone", phon);
//                params.put("gender", gen);
//                params.put("image",attach );
//                params.put("pin", pinn);
//                params.put("experience", exper);
//                params.put("quali", qualific);
//                params.put("spec", speci);
//                params.put("adhar", adhr);
//                params.put("password", passwrd);
//                        params.put("image", attach);


                return params;
            }
        };

        int MY_SOCKET_TIMEOUT_MS = 100000;

        postRequest.setRetryPolicy(new DefaultRetryPolicy(MY_SOCKET_TIMEOUT_MS, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(postRequest);



    edit.setOnClickListener(new View.OnClickListener()
    {
        @Override
        public void onClick(View view) {
        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();

        Intent ij=new Intent(getApplicationContext(), edit_profile.class);
        startActivity(ij);
    }
    });






}}




