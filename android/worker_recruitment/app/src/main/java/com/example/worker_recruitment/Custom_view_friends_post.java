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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class Custom_view_friends_post extends BaseAdapter {
    private final Context context;
    String[]post,image,date,postid;
    public Custom_view_friends_post(Context applicationContext,String[] post,String[]image,  String[] date,String[] postid) {
        this.context=applicationContext;
        this.post=post;
        this.image=image;
        this.date=date;
        this.postid=postid;

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
            gridView=inflator.inflate(R.layout.custom_view_friends_post,null);

        }
        else
        {
            gridView=(View)convertView;

        }
        TextView cp=(TextView)gridView.findViewById(R.id.post12);
        ImageView ci=(ImageView)gridView.findViewById(R.id.imageView12);
        TextView cpd=(TextView)gridView.findViewById(R.id.date);
        TextView comment=(TextView)gridView.findViewById(R.id.textView20);
        TextView like=(TextView)gridView.findViewById(R.id.textView21);

        comment.setTag(postid[position]);
        comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String postedid = v.getTag().toString();
                SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
                SharedPreferences.Editor ed = sh.edit();
                ed.putString("postid", postedid);
                ed.commit();

                Intent ii=new Intent(context.getApplicationContext(),add_comment.class);
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

                Intent ii=new Intent(context.getApplicationContext(),viewalllikes.class);
                ii.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(ii);



            }
        });






        cp.setTextColor(Color.BLACK);
        cpd.setTextColor(Color.BLACK);
        like.setTextColor(Color.BLACK);
        cp.setText(post[position]);
        cpd.setText(date[position]);


        SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(context);
        String ip=sh.getString("ip","");
        String ippp=sh.getString("pplike","");

        like.setText(ippp);
        String url="http://" + ip + ":5000"+image[position];
        Toast.makeText(context, "img url="+url, Toast.LENGTH_SHORT).show();


        Picasso.with(context).load(url). into(ci);
        return gridView;
    }
}
