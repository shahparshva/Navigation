package com.example.hp.navigation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * Created by Hp on 07-07-2017.
 */

public class catagory_adapter_first extends BaseAdapter {
    ArrayList<String> catagory;
    Context context;
    LayoutInflater inflater;
    FragmentTransaction ft;

    public catagory_adapter_first(Context context, ArrayList<String> catagory) {
        this.context = context;
        this.catagory = catagory;

    }


    @Override
    public int getCount() {
        return catagory.size();
    }

    @Override
    public Object getItem(int i) {
        return catagory.get(i);
    }

    @Override
    public long getItemId(int i) {
        return catagory.size();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
     //   if (convertView != null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = inflater.inflate(R.layout.list_catagory, viewGroup, false);
            final TextView c = (TextView) v.findViewById(R.id.catagory);
            //Log.v("catagory", String.valueOf(catagory.get(position)));
            final String catagory_temp=catagory.get(position);
            c.setText("" +catagory_temp);
           c.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Catagory_second f=new Catagory_second();
                    Bundle arguments = new Bundle();
                    final String catagory_temp=catagory.get(position);
                    arguments.putString("sub_catagory",catagory_temp);
                    f.setArguments(arguments);
                  /*  ((FragmentActivity)context).getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,f).commit();
                    ft = ((FragmentActivity)context).getSupportFragmentManager().beginTransaction();
                     ft.addToBackStack(null);
                     ft.commit();

                      SharedPreferences.Editor editor =context.getSharedPreferences("1catagory_sub", MODE_PRIVATE).edit();
                    editor.putString("1subcatagory",catagory_temp);
                    editor.apply();
                    editor.commit();
                     */
                    FragmentTransaction ft;
                    ft = ((FragmentActivity)context).getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.content_frame,f);

                    ft.addToBackStack(null);

                    ft.commit();
                        }


            });
            return v;
      //  }

        //return null;
    }
}

