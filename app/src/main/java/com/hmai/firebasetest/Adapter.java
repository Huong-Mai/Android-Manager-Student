package com.hmai.firebasetest;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class Adapter extends BaseAdapter {


    private MainActivity context;
    private int layout;
    private List<Sinhvien> sinhvienList;
    private List<Integer> sort;



    public Adapter(MainActivity context, int layout, List<Sinhvien> sinhvienList, List<Integer> sort) {
        this.setContext(context);
        this.layout = layout;
        this.sinhvienList = sinhvienList;
        this.setSort(sort);
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

    public List<Integer> getSort() {
        return sort;
    }

    public void setSort(List<Integer> sort) {
        this.sort = sort;
    }

    public MainActivity getContext() {
        return context;
    }

    public void setContext(MainActivity context) {
        this.context = context;
    }


    private  class ViewHolder{
        TextView tvhoten,tvstt;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Adapter.ViewHolder holder;

        if(convertView== null){
            holder = new Adapter.ViewHolder();
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout,null);

            holder.tvstt = (TextView) convertView.findViewById(R.id.tvstt);
            holder.tvhoten = (TextView)convertView.findViewById(R.id.tvhoten);
           // holder.id_pmenu=(ImageView)convertView.findViewById(R.id.id_pmenu);


            convertView.setTag(holder);
        }  else {
            holder = (Adapter.ViewHolder) convertView.getTag();
        }

        final Sinhvien sinhvien = sinhvienList.get(position);
        int isort= sort.get(position);
        holder.tvhoten.setText(sinhvien.getHoten());
        holder.tvstt.setText(String.valueOf(isort));

        ImageView id_pmenu = (ImageView) convertView.findViewById(R.id.pmenu);

        id_pmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(getContext(),v);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.show:
                                Intent intent = new Intent(context,ShowSinhvien.class);
                                intent.putExtra("SV",sinhvien);
                                context.startActivity(intent);
                                Log.d("tag","SHOW");
                                return true;
                            case R.id.add:
                                Intent intent1 = new Intent(context,AddSinhvien.class);
                                intent1.putExtra("SV",sinhvien);
                                context.startActivity(intent1);
                                return true;
                            case R.id.update:
                                Intent intent2 = new Intent(context,UpdateSinhvien.class);
                                intent2.putExtra("SV",sinhvien);
                                context.startActivity(intent2);
                                return true;
                            case R.id.delete:
                                context.DeleteData(sinhvien.getId());
                                return true;
                            default:     return false;
                        }
                    }
                });
                popupMenu.getMenuInflater().inflate(R.menu.menu,popupMenu.getMenu());
                popupMenu.show();
            }
        });


        return convertView;
    }
}
