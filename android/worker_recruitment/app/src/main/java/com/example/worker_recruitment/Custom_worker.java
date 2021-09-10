package com.example.worker_recruitment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Custom_worker extends BaseAdapter {

    private android.content.Context Context;
    String[] name,email,phone,pic,lid,rr;


    public Custom_worker(android.content.Context applicationContext, String[] name, String[] email,String[] phone, String[] pic, String[] lid, String[] rr) {

        this.Context=applicationContext;
        this.name=name;
        this.email=email;
        this.phone=phone;
        this.pic=pic;
        this.lid=lid;
        this.rr=rr;


    }

    @Override
    public int getCount() {

        return name.length;
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertview, ViewGroup parent) {


        LayoutInflater inflator=(LayoutInflater)Context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;
        if(convertview==null)
        {
            gridView=new View(Context);
            gridView=inflator.inflate(R.layout.custom_worker, null);

        }
        else
        {
            gridView=(View)convertview;

        }

        TextView tv1=(TextView)gridView.findViewById(R.id.name);
        TextView tv2=(TextView)gridView.findViewById(R.id.email);
        TextView tv3=(TextView)gridView.findViewById(R.id.phone);
        ImageView img=(ImageView) gridView.findViewById(R.id.imageView2);
        final RatingBar r1=(RatingBar) gridView.findViewById(R.id.ratingBar4);
        final TextView b2=(TextView) gridView.findViewById(R.id.re);

        b2.setTag(position);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos=(int)view.getTag();
                SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(Context);
                String hu = sh.getString("ip", "");
                String url = "http://" + hu + ":5000/user_send_work_req";
                RequestQueue requestQueue = Volley.newRequestQueue(Context);
                StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Toast.makeText(Context, response, Toast.LENGTH_LONG).show();

                                // response
                                try {
                                    JSONObject jsonObj = new JSONObject(response);
                                    if (jsonObj.getString("status").equalsIgnoreCase("ok")) {
                                        Toast.makeText(Context, "Request Sent", Toast.LENGTH_SHORT).show();
                                        Intent ij=new Intent(Context, view_sent_work_req.class);
                                        ij.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        Context.startActivity(ij);
                                    }


                                    // }
                                    else {
                                        Toast.makeText(Context, "Already sent", Toast.LENGTH_LONG).show();
                                    }

                                } catch (Exception e) {
                                    Toast.makeText(Context, "Error" + e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // error
                                Toast.makeText(Context, "eeeee" + error.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                ) {
                    @Override
                    protected Map<String, String> getParams() {
                        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(Context);
                        Map<String, String> params = new HashMap<String, String>();

                        String id=sh.getString("lid","");
                        params.put("lid",id);
                        params.put("toid", lid[pos]);

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





        SharedPreferences sh=PreferenceManager.getDefaultSharedPreferences(Context);
        try
        {
            String hu = sh.getString("ip", "");
            String url = "http://" + hu + ":5000" +pic[position];
            Picasso.with(Context).load(url.toString()).memoryPolicy(MemoryPolicy.NO_CACHE).networkPolicy(NetworkPolicy.NO_CACHE).error(R.drawable.ic_menu_gallery).into(img);

        }catch (Exception e)
        {
            Toast.makeText(Context, e.toString(), Toast.LENGTH_SHORT).show();
        }




        tv1.setTextColor(Color.BLACK);
        tv2.setTextColor(Color.BLACK);
        tv3.setTextColor(Color.BLACK);



        tv1.setText(name[position]);
        tv2.setText(email[position]);
        tv3.setText(phone[position]);

        r1.setRating(Float.parseFloat(rr[position]));
        r1.setEnabled(false);



        return gridView;
    }

}
