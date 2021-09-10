package com.example.worker_recruitment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class registration extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    ImageView ir;
    EditText name;
    EditText pn;
    EditText plc;
    EditText ps;
    EditText ocptn;
    EditText email;
    EditText pass;
    EditText cpass;
    Button reg;
    Spinner skill;
    String path, atype, fname, attach="";
    byte[] byteArray = null;
    String url3 = "";
    String[] dis = {"--select--", "Architect", "furniture_designer", "interiors"};
    String[] skill_id,skill_name;
    String my_skill_id="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        ir = (ImageView) findViewById(R.id.imageView6);
        name = (EditText) findViewById(R.id.name);
        pn = (EditText) findViewById(R.id.phone_no);
        plc = (EditText) findViewById(R.id.place);
        ps = (EditText) findViewById(R.id.post);
        ocptn = (EditText) findViewById(R.id.occupation);
        email = (EditText) findViewById(R.id.email);
        pass = (EditText) findViewById(R.id.pass);
        cpass = (EditText) findViewById(R.id.cpass);
        reg = (Button) findViewById(R.id.register);
        skill=(Spinner)findViewById(R.id.spinner) ;
        reg.setOnClickListener(this);
        ir.setOnClickListener(this);
        skill_load();
        skill.setOnItemSelectedListener(this);

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
                                Toast.makeText(registration.this, "data="+js, Toast.LENGTH_SHORT).show();



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
        if (v == reg) {
        final String n = name.getText().toString();
        final String pno = pn.getText().toString();
        final String plce = plc.getText().toString();
        final String pst = ps.getText().toString();
        final String ocpn = ocptn.getText().toString();
        final String emal = email.getText().toString();
        final String pas = pass.getText().toString();
        final String cpas = cpass.getText().toString();
        if(attach.equalsIgnoreCase("")){
            Toast.makeText(getApplicationContext(), "Choose Image", Toast.LENGTH_SHORT).show();
        }
        else if(n.length()==0){
            name.setError("Name required");
        }
        else if(pno.length()==0){
            pn.setError("Phone number required");
        }
        else if(pno.length()!=10){
            pn.setError("Phone number should be 10 digits");
        }
        else if(plce.length()==0){
            plc.setError("Place required");
        }
        else if(pst.length()==0){
            ps.setError("Post required");
        }
        else if(ocpn.length()==0){
            ocptn.setError("Occupation required");
        }
        else if(emal.length()==0){
            email.setError("Email required");
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(emal).matches()){
            email.setError("Invalid Email pattern");
        }
        else if(pas.length()==0){
            pass.setError("Password required");
        }
        else if(pas.length()<8){
            pass.setError("Password should be minimum 8 characters");
        }
        else if (!pas.equalsIgnoreCase(cpas)) {
            cpass.setError("Password mismatch");
        }
        else{


            SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            String hu = sh.getString("ip", "");
            String url = "http://" + hu + ":5000/and_reg";


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

                                    Toast.makeText(registration.this, "Sucess", Toast.LENGTH_SHORT).show();


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


                    params.put("name", n);
                    params.put("phone_no", pno);
                    params.put("place", plce);
                    params.put("post", pst);
                    params.put("occupation", ocpn);
                    params.put("email", emal);
                    params.put("pass", pas);
                    params.put("image", attach);
                    params.put("skill_id",my_skill_id);
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
        } else {
            Toast.makeText(this, "password missmatch", Toast.LENGTH_SHORT).show();
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

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        my_skill_id=skill_id[position];
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}