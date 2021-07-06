package com.hmai.firebasetest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import java.util.List;

public class AdapterSinhvien extends BaseAdapter {

    private MainActivity context;
    private int layout;
    private List<Sinhvien> sinhvienList;

    public AdapterSinhvien(MainActivity context, int layout, List<Sinhvien> sinhvienList) {
        this.context = context;
        this.layout = layout;
        this.sinhvienList = sinhvienList;
    }

    @Override
    public int getCount() {
        return sinhvienList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    private  class ViewHolder{
        TextView tvhoten, tvmasv, tvlopsh;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if(convertView== null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout,null);

            holder.tvhoten = (TextView)convertView.findViewById(R.id.tvhoten);
            holder.tvmasv = (TextView) convertView.findViewById(R.id.edmasv);
            holder.tvlopsh = (TextView) convertView.findViewById(R.id.edlopsh);

            convertView.setTag(holder);
        }  else {
            holder = (ViewHolder) convertView.getTag();
        }
        Sinhvien sinhvien = sinhvienList.get(position);
        holder.tvhoten.setText(sinhvien.getHoten());
        holder.tvmasv.setText(sinhvien.getMasv());
        holder.tvlopsh.setText(sinhvien.getLopSH());


        return convertView;
    }
}
