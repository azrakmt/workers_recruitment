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

public class Custom_view_complaint extends BaseAdapter {
    private final Context context;
    String[]complaint,date,reply;
    public Custom_view_complaint (Context applicationContext, String[] complaint, String[] date, String[] reply) {
        this.context=applicationContext;
        this.complaint=complaint;
        this.date=date;
        this.reply=reply;
    }

    @Override
    public int getCount() {

        return complaint.length;
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
            gridView=inflator.inflate(R.layout.custom_view_complaints,null);

        }
        else
        {
            gridView=(View)convertView;

        }
        TextView cc=(TextView)gridView.findViewById(R.id.custom_complaint);
        TextView cd=(TextView) gridView.findViewById(R.id.custom_date);
        TextView cr=(TextView) gridView.findViewById(R.id.custom_reply);

        cc.setTextColor(Color.BLACK);
        cd.setTextColor(Color.BLACK);
        cr.setTextColor(Color.BLACK);



        cc.setText(complaint[position]);
        cd.setText(date[position]);
        cr.setText(reply[position]);



        return gridView;
    }
}
