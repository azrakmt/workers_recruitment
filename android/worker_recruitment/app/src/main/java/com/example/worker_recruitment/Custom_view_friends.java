package com.example.worker_recruitment;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class Custom_view_friends extends BaseAdapter {
    private final Context context;
    String[]name,image,contact_no,occupation;
    public Custom_view_friends(Context applicationContext, String[] contact_no, String[] name, String[] image,String[]occupation) {
        this.context=applicationContext;
        this.name=name;
        this.image=image;
        this.contact_no=contact_no;
        this.occupation=occupation;

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
//            gridView=inflator.inflate(R.layout.custom_view, null);
            gridView=inflator.inflate(R.layout.custom_view_friends,null);

        }
        else
        {
            gridView=(View)convertView;

        }
        TextView cn=(TextView)gridView.findViewById(R.id.custom_name);
        ImageView ci=(ImageView) gridView.findViewById(R.id.imageView5);
        TextView ccn=(TextView)gridView.findViewById(R.id.custom_contact_no);
        TextView ocptn=(TextView)gridView.findViewById(R.id.textView11);

        cn.setTextColor(Color.BLACK);
        ccn.setTextColor(Color.BLACK);
        ocptn.setTextColor(Color.BLACK);




        cn.setText(name[position]);
        ccn.setText(contact_no[position]);
        ocptn.setText(occupation[position]);



        SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(context);
        String ip=sh.getString("ip","");

        String url="http://" + ip + ":5000"+image[position];
        Toast.makeText(context, "img url="+url, Toast.LENGTH_SHORT).show();


        Picasso.with(context).load(url). into(ci);

        return gridView;
    }
}
