package com.example.worker_recruitment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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


public class Custom_sent_work_request extends BaseAdapter {

    private android.content.Context Context;
    String[] date,user,work,st,rid,lid;
    public Custom_sent_work_request(android.content.Context applicationContext, String[] date, String[] user,String[] work, String[] st, String[] rid,String[] lid) {
        this.Context=applicationContext;
        this.date=date;
        this.user=user;
        this.work=work;
        this.st=st;
        this.rid=rid;
        this.lid=lid;
    }

    @Override
    public int getCount() {

        return user.length;
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
            gridView=inflator.inflate(R.layout.custom_sent_work_request, null);

        }
        else
        {
            gridView=(View)convertview;

        }

        TextView tv1=(TextView)gridView.findViewById(R.id.date);
        TextView tv2=(TextView)gridView.findViewById(R.id.user);
        TextView tvwrk=(TextView)gridView.findViewById(R.id.wrk);
        TextView tv3=(TextView)gridView.findViewById(R.id.st);

        final TextView t_del=(TextView)gridView.findViewById(R.id.acc);
        final TextView t_rt=(TextView)gridView.findViewById(R.id.rt);






        tv1.setTextColor(Color.BLACK);
        tv2.setTextColor(Color.BLACK);
        tv3.setTextColor(Color.BLACK);
        tvwrk.setTextColor(Color.BLACK);



        tv1.setText(date[position]);
        tv2.setText(user[position]);
        tv3.setText(st[position]);
        tvwrk.setText(work[position]);


        t_del.setTag(position);
        t_rt.setTag(position);
        t_rt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos=(int)v.getTag();
                SharedPreferences sh=PreferenceManager.getDefaultSharedPreferences(Context);
                SharedPreferences.Editor ed=sh.edit();
                ed.putString("toid",lid[pos]);
                ed.commit();
                Intent ij=new Intent(Context,Add_rating.class);
                ij.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Context.startActivity(ij);

            }
        });


        t_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos=(int)v.getTag();
                SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(Context);
                String hu = sh.getString("ip", "");
                String url = "http://" + hu + ":5000/user_del_work_req";
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
                                        Toast.makeText(Context, "Deleted", Toast.LENGTH_SHORT).show();
                                        Intent ij=new Intent(Context,view_sent_work_req.class);
                                        ij.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        Context.startActivity(ij);
                                    }


                                    // }
                                    else {
                                        Toast.makeText(Context, "Not found", Toast.LENGTH_LONG).show();
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


                        params.put("rid", rid[pos]);

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


        return gridView;
    }

}
