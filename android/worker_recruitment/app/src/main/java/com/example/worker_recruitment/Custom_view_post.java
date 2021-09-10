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

public class Custom_view_post extends BaseAdapter {
    private final Context context;
    String[]post,image,date,postid,ulid;
    public Custom_view_post(Context applicationContext,String[] post,String[]image,  String[] date, String[] postid,String[] ulid) {
        this.context=applicationContext;
        this.post=post;
        this.image=image;
        this.date=date;
        this.postid=postid;
        this.ulid=ulid;
    }

    @Override
    public int getCount() {
        return post.length;
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
            gridView=inflator.inflate(R.layout.custom_view_post,null);

        }
        else
        {
            gridView=(View)convertView;

        }
        TextView cp=(TextView)gridView.findViewById(R.id.post12);
        ImageView ci=(ImageView)gridView.findViewById(R.id.imageView12);
        TextView cpd=(TextView)gridView.findViewById(R.id.date);
        Button cd=(Button)gridView.findViewById(R.id.button12);
        TextView comment=(TextView)gridView.findViewById(R.id.textView20);
        TextView like=(TextView)gridView.findViewById(R.id.textView21);

        cd.setTag(postid[position]);
        cd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String psid=v.getTag().toString();


                SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
                String hu = sh.getString("ip", "");
                String url = "http://" + hu + ":5000/delete_post";



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
                                        Toast.makeText(context, "deleted", Toast.LENGTH_SHORT).show();

                                    }



                                    else {
                                        Toast.makeText(context.getApplicationContext(), "Not found", Toast.LENGTH_LONG).show();
                                    }

                                }
                                catch (Exception e) {
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



                        params.put("postid",psid);

                     

                        return params;
                    }
                };

                int MY_SOCKET_TIMEOUT_MS=100000;

                postRequest.setRetryPolicy(new DefaultRetryPolicy(
                        MY_SOCKET_TIMEOUT_MS,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                requestQueue.add(postRequest);

            }
        });

        comment.setTag(postid[position]);
        comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String postedid = v.getTag().toString();
                SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
                SharedPreferences.Editor ed = sh.edit();
                ed.putString("postid", postedid);
                ed.commit();

                Intent ii=new Intent(context.getApplicationContext(),view_comment_my_post.class);
                ii.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(ii);
            }
        });

        like.setTag(postid[position]);
        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String postedid = v.getTag().toString();
                SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
                SharedPreferences.Editor ed = sh.edit();
                ed.putString("postid", postedid);
                ed.commit();

                Intent ii=new Intent(context.getApplicationContext(),viewalllikes_my_post.class);
                ii.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(ii);
            }
        });




        cp.setTextColor(Color.BLACK);
        cpd.setTextColor(Color.BLACK);



        cp.setText(post[position]);
        cpd.setText(date[position]);


        SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(context);
        String ip=sh.getString("ip","");

        String url="http://" + ip + ":5000"+image[position];
        Toast.makeText(context, "img url="+url, Toast.LENGTH_SHORT).show();


        Picasso.with(context).load(url). into(ci);
        return gridView;
    }
}
