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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class Custom_my_request extends BaseAdapter {

    private android.content.Context Context;
    String[] date,user,ph,st,rid;
    public Custom_my_request(android.content.Context applicationContext, String[] date, String[] user, String[] ph,String[] st, String[] rid) {
        this.Context=applicationContext;
        this.date=date;
        this.user=user;
        this.ph=ph;
        this.st=st;
        this.rid=rid;
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
            gridView=inflator.inflate(R.layout.custom_request, null);

        }
        else
        {
            gridView=(View)convertview;

        }

        TextView tv1=(TextView)gridView.findViewById(R.id.date);
        TextView tv2=(TextView)gridView.findViewById(R.id.user);
        TextView tv3=(TextView)gridView.findViewById(R.id.st);
        TextView tvph=(TextView)gridView.findViewById(R.id.ph);

        final TextView t_acc=(TextView)gridView.findViewById(R.id.acc);
        final TextView t_rej=(TextView)gridView.findViewById(R.id.rej);
        final TextView t_com=(TextView)gridView.findViewById(R.id.com);

        if (st[position].equalsIgnoreCase("pending"))
        {

            t_acc.setVisibility(View.VISIBLE);
            t_rej.setVisibility(View.VISIBLE);
            t_com.setVisibility(View.GONE);

        }
        if (st[position].equalsIgnoreCase("accepted"))
        {

            t_acc.setVisibility(View.GONE);
            t_rej.setVisibility(View.GONE);
            t_com.setVisibility(View.VISIBLE);

        }
        if (st[position].equalsIgnoreCase("rejected"))
        {

            t_acc.setVisibility(View.GONE);
            t_rej.setVisibility(View.GONE);
            t_com.setVisibility(View.GONE);

        }
        if (st[position].equalsIgnoreCase("completed"))
        {

            t_acc.setVisibility(View.GONE);
            t_rej.setVisibility(View.GONE);
            t_com.setVisibility(View.GONE);
        }





        tv1.setTextColor(Color.BLACK);
        tv2.setTextColor(Color.BLACK);
        tv3.setTextColor(Color.BLACK);
        tvph.setTextColor(Color.BLACK);



        tv1.setText(date[position]);
        tv2.setText(user[position]);
        tv3.setText(st[position]);
        tvph.setText(ph[position]);


        t_acc.setTag(position);
        t_rej.setTag(position);
        t_com.setTag(position);
        t_acc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos=(int)v.getTag();
                SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(Context);
                String hu = sh.getString("ip", "");
                String url = "http://" + hu + ":5000/usr_acc_wrk_request";
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
                                        Toast.makeText(Context, "Accepted", Toast.LENGTH_SHORT).show();
                                        Intent ij=new Intent(Context,view_work_requests.class);
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

        t_rej.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos=(int)v.getTag();
                SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(Context);
                String hu = sh.getString("ip", "");
                String url = "http://" + hu + ":5000/usr_rej_wrk_request";
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
                                        Toast.makeText(Context, "Rejected", Toast.LENGTH_SHORT).show();
                                        Intent ij=new Intent(Context,view_work_requests.class);
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

        t_com.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos=(int)v.getTag();
                SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(Context);
                String hu = sh.getString("ip", "");
                String url = "http://" + hu + ":5000/usr_com_wrk_request";
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
                                        Toast.makeText(Context, "Completed", Toast.LENGTH_SHORT).show();
                                        Intent ij=new Intent(Context,view_work_requests.class);
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
