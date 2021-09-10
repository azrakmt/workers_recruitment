package com.example.worker_recruitment;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

public class Custom_rating extends BaseAdapter {

    private android.content.Context Context;
    String[] date,rating,user;



    public Custom_rating(android.content.Context applicationContext, String[] date, String[] rating, String[] user) {

        this.Context=applicationContext;
        this.date=date;
        this.rating=rating;
        this.user=user;




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
            gridView=inflator.inflate(R.layout.custom_rating, null);

        }
        else
        {
            gridView=(View)convertview;

        }

        TextView tv1=(TextView)gridView.findViewById(R.id.date);
        TextView tv2=(TextView)gridView.findViewById(R.id.user);

        RatingBar r1=(RatingBar) gridView.findViewById(R.id.ratingBar);

        r1.setEnabled(false);



        tv1.setTextColor(Color.BLACK);
        tv2.setTextColor(Color.BLACK);



        tv1.setText(date[position]);
        tv2.setText(user[position]);

        r1.setRating(Float.parseFloat(rating[position]));




        return gridView;
    }

}
