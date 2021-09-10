package com.example.worker_recruitment;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

public class Custom_add_comment  extends BaseAdapter {
    private final Context context;
    String[] user_info,comment,date;
    public Custom_add_comment(Context applicationContext, String[] user_info, String[] comment, String[] date) {
        this.context=applicationContext;
        this.user_info =user_info;
        this.comment=comment;
        this.date=date;

    }

    @Override
    public int getCount() {
        return comment.length;
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
            gridView=inflator.inflate(R.layout.custom_add_comment,null);

        }
        else
        {
            gridView=(View)convertView;

        }
        TextView ui=(TextView)gridView.findViewById(R.id.textView24);
        TextView cmnt=(TextView) gridView.findViewById(R.id.textView25);
        TextView dat=(TextView) gridView.findViewById(R.id.textView26);

        ui.setTextColor(Color.BLACK);
        cmnt.setTextColor(Color.BLACK);
        dat.setTextColor(Color.BLACK);



        ui.setText(user_info[position]);
        cmnt.setText(comment[position]);
        dat.setText(date[position]);




        return gridView;
    }
}


