package com.example.hp.navigation;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import Fragment.profile;

import static android.content.Context.MODE_PRIVATE;

public class HLVAdapter extends RecyclerView.Adapter<HLVAdapter.ViewHolder> {

    private ArrayList<String> alName;
    ArrayList<Integer> alImage;
    Context context;
    FragmentTransaction ft;


    public HLVAdapter(Context context, ArrayList<String> alName, ArrayList<Integer> alImage) {
        super();
        this.context = context;
        this.alName = alName;
        this.alImage = alImage;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.grid_item, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
        viewHolder.tvSpecies.setText(alName.get(i));
        viewHolder.imgThumbnail.setImageResource(alImage.get(i));

        viewHolder.setClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                Log.v("DS", "%%%%%");

               /* if (isLongClick) {
                    Toast.makeText(context, "#" + position + " - " + alName.get(position) + " (Long click)", Toast.LENGTH_SHORT).show();
                    context.startActivity(new Intent(context, slid_catagory.class));
                } else {
                    Toast.makeText(context, "#" + position + " - " + alName.get(position), Toast.LENGTH_SHORT).show();
                }*/
                 //Intent i=new Intent(get, Fragment.catagory_first.class);
                try {
                    String x=alName.get(position);
                    //Log.v("my log",x);
                    //  i.putExtra("catagory",x);
                    //contxt.startActivity(i);
                    Catagory_first f=new Catagory_first();
                    /*Bundle arguments = new Bundle();
                    arguments.putString("catagory",x);
                    f.setArguments(arguments);*/
                    SharedPreferences.Editor editor =context.getSharedPreferences("catagory_sub", MODE_PRIVATE).edit();
                    editor.putString("subcatagory",x);
                    editor.apply();
                    editor.commit();
                    ft = ((FragmentActivity)context).getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.content_frame,f);
                    //((FragmentActivity)context).getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,f).commit();
                    /*FragmentTransaction ft =HLVAdapter.getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.content_frame,fragment);
                    ft.addToBackStack(null);
                    ft.commit();*/

                    ft.addToBackStack(null);
                    ft.commit();
                }
                catch (Exception e)
                {
                    /*String x=alName.get(position);
                    Catagory_first f=new Catagory_first();
                    Bundle arguments = new Bundle();
                    arguments.putString("catagory",x);
                    f.setArguments(arguments);
                    ((FragmentActivity)context).getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,f).commit();
                    ft = ((FragmentActivity)context).getSupportFragmentManager().beginTransaction();
                    ft.addToBackStack(null);
                    ft.commit();*/
                }

            }

        });
    }

    @Override
    public int getItemCount() {
        return alName.size();
    }



    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        public ImageView imgThumbnail;
        public TextView tvSpecies;
        private ItemClickListener clickListener;

        public ViewHolder(View itemView) {
            super(itemView);
            imgThumbnail = (ImageView) itemView.findViewById(R.id.img_thumbnail);
            tvSpecies = (TextView) itemView.findViewById(R.id.tv_species);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        public void setClickListener(ItemClickListener itemClickListener) {
            this.clickListener = itemClickListener;
        }

        @Override
        public void onClick(View view) {

            clickListener.onClick(view, getPosition(), false);

        }

        @Override
        public boolean onLongClick(View view) {
            clickListener.onClick(view, getPosition(), true);
            return true;
        }
    }

}


