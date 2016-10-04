package com.mustacheproject.mustachecontability;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eugenio on 30/10/2015.
 */
public class ListDataAdapter extends ArrayAdapter{
    List list= new ArrayList();
    public ListDataAdapter(Context context, int resource) {
        super(context, resource);
    }

    static class LayoutHandler{
        TextView ACCOUNT, AMOUNT,DATA,REASON;
    }

    public void add (DataProvider dataProvider){
        super.add(dataProvider);
        list.add(dataProvider);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position){
     return list.get(position);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row=convertView;
        LayoutHandler layoutHandler;
        if(row == null){
            LayoutInflater layoutInflater=(LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row=layoutInflater.inflate(R.layout.row_layout,parent,false);
            layoutHandler=new LayoutHandler();
            layoutHandler.ACCOUNT=(TextView)row.findViewById(R.id.text_account_operation);
            layoutHandler.AMOUNT=(TextView)row.findViewById(R.id.text_amount_operation);
            layoutHandler.DATA=(TextView)row.findViewById(R.id.text_data_operation);
            layoutHandler.REASON=(TextView)row.findViewById(R.id.text_reason_operation);
            row.setTag(layoutHandler);
        }else{
            layoutHandler=(LayoutHandler) row.getTag();

        }
        DataProvider dataProvider=(DataProvider)this.getItem(position);
        layoutHandler.ACCOUNT.setText(dataProvider.get_account());
        layoutHandler.AMOUNT.setText(dataProvider.get_amount().toString());
        layoutHandler.DATA.setText(dataProvider.get_data());
        layoutHandler.REASON.setText(dataProvider.get_reason());


        return row;
    }

}
