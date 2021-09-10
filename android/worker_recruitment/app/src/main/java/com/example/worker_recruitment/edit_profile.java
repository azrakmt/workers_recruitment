package com.example.worker_recruitment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
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

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

 public class edit_profile extends AppCompatActivity implements View.OnClickListener {
        ImageView ir;
        EditText name;
        EditText pn;
        EditText plc;
        EditText ps;
        EditText ocptn;
        EditText email;
        Button edit;
        Spinner skill;
        String path, atype, fname, attach, attatch1;
        byte[] byteArray = null;
        String url3 = "";
        String[] dis = {"--select--", "Architect", "furniture_designer", "interiors"};
        String[] skill_id,skill_name;
        String[] my_skill_id22,my_skill_id;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_edit_profile);
            ir = (ImageView) findViewById(R.id.imageView9);
            name = (EditText) findViewById(R.id.editTextTextPersonName8);
            pn = (EditText) findViewById(R.id.editTextTextPersonName9);
            plc = (EditText) findViewById(R.id.editTextTextPersonName10);
            ps = (EditText) findViewById(R.id.editTextTextPersonName11);
            ocptn = (EditText) findViewById(R.id.editTextTextPersonName12);
            email = (EditText) findViewById(R.id.editTextTextPersonName13);
            edit = (Button) findViewById(R.id.button6);
            skill=(Spinner)findViewById(R.id.spinner3) ;
            edit.setOnClickListener(this);
            ir.setOnClickListener(this);
            skill_load();
            attach="aa";

            SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            String hu = sh.getString("ip", "");
            String url = "http://" + hu + ":5000/view_profile";


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


                                    String name22=jsonObj.getString("name");
                                    String phone_no=jsonObj.getString("phone_no");
                                    String place=jsonObj.getString("place");
                                    String post=jsonObj.getString("post");
                                    String occupation=jsonObj.getString("occupation");
                                    String email22=jsonObj.getString("email");
                                    String skillid=jsonObj.getString("skillid");
                                    String skill22=jsonObj.getString("skill");
                                    String image=jsonObj.getString("image");
                                    name.setText(name22);
                                    pn.setText(phone_no);
                                    plc.setText(place);
                                    ps.setText(post);
                                    ocptn.setText(occupation);
                                    email.setText(email22);
//
                                    SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                    String ip=sh.getString("ip","");

                                    String url="http://" + ip + ":5000"+image;
                                    Toast.makeText(getApplicationContext(), "img url="+url, Toast.LENGTH_SHORT).show();


                                    Picasso.with(getApplicationContext()).load(url). into(ir);









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


                    params.put("lid", sh.getString("lid",""));

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






     private void skill_load() {
            SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

            String hu = sh.getString("ip", "");
            String url = "http://" + hu + ":5000/and_view_skill";



            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                JSONObject jsonObj = new JSONObject(response);
                                if (jsonObj.getString("status").equalsIgnoreCase("ok")) {
                                    JSONArray js= jsonObj.getJSONArray("users");
                                    skill_id = new String[js.length()];
                                    skill_name=new String[js.length()];
                                    Toast.makeText(com.example.worker_recruitment.edit_profile.this, "data="+js, Toast.LENGTH_SHORT).show();



//

                                    for(int i=0;i<js.length();i++)
                                    {
                                        JSONObject u=js.getJSONObject(i);
                                        skill_id[i]=u.getString("skill_id");
                                        skill_name[i]=u.getString("skill");



//

                                    }
                                    ArrayAdapter<String> adpt=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,skill_name);
                                    skill.setAdapter(adpt);

                                }


                                else {
                                    Toast.makeText(getApplicationContext(), "Not found", Toast.LENGTH_LONG).show();
                                }

                            }    catch (Exception e) {
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

                    String id=sh.getString("lid","");
                    params.put("lid",id);
//                params.put("mac",maclis);

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

        @Override
        public void onClick(View v) {
            if (v == ir) {
                showfilechooser(1);
            }
            if (v == edit) {
                final String n = name.getText().toString();
                final String pno = pn.getText().toString();
                final String plce = plc.getText().toString();
                final String pst = ps.getText().toString();
                final String ocpn = ocptn.getText().toString();
                final String emal = email.getText().toString();

                if (n.length() == 0) {
                    name.setError("Name required");
                } else if (pno.length() == 0) {
                    pn.setError("Phone number required");
                } else if (pno.length() != 10) {
                    pn.setError("Phone number should be 10 digits");
                } else if (plce.length() == 0) {
                    plc.setError("Place required");
                } else if (pst.length() == 0) {
                    ps.setError("Post required");
                } else if (ocpn.length() == 0) {
                    ocptn.setError("Occupation required");
                } else if (emal.length() == 0) {
                    email.setError("Email required");
                } else if (!Patterns.EMAIL_ADDRESS.matcher(emal).matches()) {
                    email.setError("Invalid Email pattern");
                } else {


                    SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    String hu = sh.getString("ip", "");
                    String url = "http://" + hu + ":5000/edit_profile";


                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    //  Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

                                    // response
                                    try {
                                        JSONObject jsonObj = new JSONObject(response);
                                        if (jsonObj.getString("status").equalsIgnoreCase("ok")) {

                                            Toast.makeText(com.example.worker_recruitment.edit_profile.this, "Sucess", Toast.LENGTH_SHORT).show();


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

                            params.put("lid", sh.getString("lid", ""));

                            params.put("name", n);
                            params.put("phone_no", pno);
                            params.put("place", plce);
                            params.put("post", pst);
                            params.put("occupation", ocpn);
                            params.put("email", emal);
                            params.put("image", attach);
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
        }



        void showfilechooser(int string) {
            // TODO Auto-generated method stub
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            //getting all types of files

            intent.setType("*/*");
            intent.addCategory(Intent.CATEGORY_OPENABLE);

            try {
                startActivityForResult(Intent.createChooser(intent, "Select a File to Upload"), string);
            } catch (android.content.ActivityNotFoundException ex) {
                // Potentially direct the user to the Market with a Dialog
                Toast.makeText(getApplicationContext(), "Please install a File Manager.", Toast.LENGTH_SHORT).show();

            }
        }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);

            if (resultCode == RESULT_OK) {
                if (requestCode == 1) {
                    ////
                    Uri uri = data.getData();

                    try {
                        path = FileUtils.getPath(this, uri);

                        File fil = new File(path);
                        float fln = (float) (fil.length() / 1024);
                        atype = path.substring(path.lastIndexOf(".") + 1);


                        fname = path.substring(path.lastIndexOf("/") + 1);
//                    ed15.setText(fname);
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }

                    try {

                        File imgFile = new File(path);

                        if (imgFile.exists()) {

                            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                            ir.setImageBitmap(myBitmap);

                        }


                        File file = new File(path);
                        byte[] b = new byte[8192];
                        Log.d("bytes read", "bytes read");

                        InputStream inputStream = new FileInputStream(file);
                        ByteArrayOutputStream bos = new ByteArrayOutputStream();

                        int bytesRead = 0;

                        while ((bytesRead = inputStream.read(b)) != -1) {
                            bos.write(b, 0, bytesRead);
                        }
                        byteArray = bos.toByteArray();

                        String str = Base64.encodeToString(byteArray, Base64.NO_WRAP);
                        attach = str;


                    } catch (Exception e) {
                        Toast.makeText(this, "String :" + e.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }

                    ///

                }
            }

        }}


