package com.example.worker_recruitment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity implements View.OnClickListener {
    EditText uname;
    EditText pass;
    Button login;
    TextView reg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        uname = (EditText) findViewById(R.id.username);
        pass = (EditText) findViewById(R.id.password);
        reg = (TextView) findViewById(R.id.textView9);
        login = (Button) findViewById(R.id.login);
        login.setOnClickListener(this);
        reg.setOnClickListener(this);
        uname.setText("azra629@gmail.com");
        pass.setText("1234");
    }

    @Override
    public void onClick(View v) {


//        Toast.makeText(this, "welcome python..................", Toast.LENGTH_SHORT).show();


        if (v == login) {

            final String unam = uname.getText().toString();
            final String password22 = pass.getText().toString();

            if (unam.equalsIgnoreCase("")) {
                uname.setError("Missing");
            } else if (password22.equalsIgnoreCase("")) {
                pass.setError("Missing");
            } else {


                final String pas = pass.getText().toString();
                SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                String hu = sh.getString("ip", "");
                String url = "http://" + hu + ":5000/and_login";


                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                  Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

                                // response
                                try {
                                    JSONObject jsonObj = new JSONObject(response);
                                    if (jsonObj.getString("status").equalsIgnoreCase("ok")) {

                                        String lid = jsonObj.getString("lid");
                                        Toast.makeText(Login.this, "lid", Toast.LENGTH_SHORT).show();
                                        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                        SharedPreferences.Editor ed = sh.edit();
                                        ed.putString("lid", lid);
                                        ed.commit();

                                        Intent ii = new Intent(getApplicationContext(), LocationService.class);
                                        startService(ii);


                                        Intent ij = new Intent(getApplicationContext(), Home.class);
                                        startActivity(ij);
                                    }
                                    else if (jsonObj.getString("status").equalsIgnoreCase("blocked")) {
                                        Toast.makeText(getApplicationContext(), "Your account has been blocked", Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Not found", Toast.LENGTH_LONG).show();
                                    }

                                } catch (Exception e) {
                                    Toast.makeText(getApplicationContext(), "Error" + e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // error
                                Toast.makeText(getApplicationContext(), "eeeee" + error.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                ) {
                    @Override
                    protected Map<String, String> getParams() {
                        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        Map<String, String> params = new HashMap<String, String>();


                        params.put("username", unam);
                        params.put("pass", pas);

                        return params;
                    }
                };

                int MY_SOCKET_TIMEOUT_MS = 100000;

                postRequest.setRetryPolicy(new DefaultRetryPolicy(
                        MY_SOCKET_TIMEOUT_MS,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                requestQueue.add(postRequest);
            }
        }



        else
            {
                startActivity(new Intent(getApplicationContext(), registration.class));

            }

        }

    }

