package com.example.worker_recruitment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Custom_view_other_user extends BaseAdapter {
    private final Context context;
    String[]name,image,occupation,email,lid;
    public Custom_view_other_user(Context applicationContext,String[] name, String[] image,String[]occupation,String[]email,String[]lid) {
        this.context=applicationContext;
        this.name=name;
        this.image=image;
        this.occupation=occupation;
        this.email=email;
        this.lid=lid;

    }

    @Override
    public int getCount() {
        return name.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflator=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;
        if(convertView==null)
        {
            gridView=new View(context);
            //gridView=inflator.inflate(R.layout.customview, null);
            gridView=inflator.inflate(R.layout.custom_view_other_user,null);

        }
        else
        {
            gridView=(View)convertView;

        }
        TextView cn=(TextView)gridView.findViewById(R.id.textView4);
        ImageView ci=(ImageView) gridView.findViewById(R.id.imageView3);
        TextView ocptn=(TextView)gridView.findViewById(R.id.textView5);
        TextView ce=(TextView)gridView.findViewById(R.id.textView7);
        Button send=(Button)gridView.findViewById(R.id.button4) ;
        send.setTag(lid[position]);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String toid=v.getTag().toString();
                SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
                String hu = sh.getString("ip", "");
                String url = "http://" + hu + ":5000/send_frnd_rqst";
                RequestQueue requestQueue = Volley.newRequestQueue(context.getApplicationContext());
                StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                //  Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

                                // response
                                try {
                                    JSONObject jsonObj = new JSONObject(response);
                                    if (jsonObj.getString("status").equalsIgnoreCase("ok")) {
                                        Toast.makeText(context, "friend request sended", Toast.LENGTH_SHORT).show();




                                    } else {
                                        Toast.makeText(context.getApplicationContext(), "Not found", Toast.LENGTH_LONG).show();
                                    }

                                } catch (Exception e) {
                                    Toast.makeText(context.getApplicationContext(), "Error" + e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // error
                                Toast.makeText(context.getApplicationContext(), "eeeee" + error.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                ) {
                    @Override
                    protected Map<String, String> getParams() {
                        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
                        Map<String, String> params = new HashMap<String, String>();


                        params.put("flid", sh.getString("lid",""));
                        params.put("tlid",toid );

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
        });


        cn.setTextColor(Color.BLACK);
        ocptn.setTextColor(Color.BLACK);
        ce.setTextColor(Color.BLACK);




        cn.setText(name[position]);
        ocptn.setText(occupation[position]);
        ce.setText(email[position]);



        SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(context);
        String ip=sh.getString("ip","");

        String url="http://" + ip + ":5000"+image[position];
        Toast.makeText(context, "img url="+url, Toast.LENGTH_SHORT).show();


        Picasso.with(context).load(url). into(ci);

        return gridView;
    }
}

