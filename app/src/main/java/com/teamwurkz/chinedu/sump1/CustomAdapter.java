package com.teamwurkz.chinedu.sump1;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
/*
This class must be removed

public class CustomAdapter extends ArrayAdapter<NewsObject>
{
    private Context context;
    private ArrayList<NewsObject> arr;

    public CustomAdapter(Context context, ArrayList<NewsObject> arr)
    {
        super(context, 0, arr);
        this.context=context;
        this.arr=arr;
    }

    public View getView(int pos, @Nullable View v, @NonNull ViewGroup vg)
    {
        View item=v;
        if(item == null)
        {
            item= LayoutInflater.from(context).inflate(R.layout.custom_row2, vg, false);
        }
        else
        {
            NewsObject nobj=arr.get(pos);

            TextView titleView=(TextView) item.findViewById(R.id.title_row);
            titleView.setText(nobj.getTitle());

            TextView dateView=(TextView) item.findViewById(R.id.date_row);
            dateView.setText(nobj.getDate());
        }
        return item;
    }
}*/