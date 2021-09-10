package com.example.worker_recruitment;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

class Custom_viewalllike  extends BaseAdapter {
    private final Context context;
    String[] liked,name;
    public Custom_viewalllike(Context applicationContext, String[] liked, String[] name) {
        this.context=applicationContext;
        this.liked =liked;
        this.name=name;

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
            gridView=inflator.inflate(R.layout.custom_view_alllikes,null);

        }
        else
        {
            gridView=(View)convertView;

        }
        TextView likd=(TextView)gridView.findViewById(R.id.textView28);
        TextView nam=(TextView) gridView.findViewById(R.id.textView29);


        nam.setTextColor(Color.BLACK);




        nam.setText(name[position]);





        return gridView;
    }
}


