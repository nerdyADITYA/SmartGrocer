package com.example.smartgrocer.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import com.example.smartgrocer.R;
import com.example.smartgrocer.models.BoughtModel;

import java.util.List;

public class MyOrderAdapter extends BaseAdapter {

    String mail;
    Context context;
    List<BoughtModel> list;

//    public MyOrderAdapter(String mail, Context context, List<BoughtModel> list) {
//        this.mail = mail;
//        this.context = context;
//        this.list = list;
//    }

    public MyOrderAdapter(Context context, int design7, List<BoughtModel> bList, String mail) {
        this.context = context;
        this.list = bList;
        this.mail = mail;

    }


    @Override
    public int getCount() {
        return  list.size();
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

        if (convertView == null)
        {
            convertView = LayoutInflater.from(context).inflate(R.layout.design7,parent,false);
        }

        TextView tvName = convertView.findViewById(R.id.textViewName);
        TextView tvQty = convertView.findViewById(R.id.textViewQuantity);
        TextView tvAmount = convertView.findViewById(R.id.textViewAmount);
        TextView tvDate = convertView.findViewById(R.id.textViewDate);
        TextView tvTime = convertView.findViewById(R.id.textViewTime);

        BoughtModel item = list.get(position);

        if (item != null)
        {
            tvName.setText(list.get(position).getName());
            tvAmount.setText("Amount: "+list.get(position).getAmount());
            tvQty.setText("Qty: "+list.get(position).getQty());
            tvDate.setText("Date: "+list.get(position).getDate());
            tvTime.setText("Time: "+list.get(position).getTime());

        }


        return convertView;
    }
}
