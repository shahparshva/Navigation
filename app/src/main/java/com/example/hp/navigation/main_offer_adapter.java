package com.example.hp.navigation;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hp.navigation.R;

import java.util.ArrayList;

public class main_offer_adapter extends ArrayAdapter<String>{

    private final Context context;
    private final ArrayList<String> web;
    LayoutInflater inflater;
    private final ArrayList<Integer> imageId;
    public main_offer_adapter(Context context,
                              ArrayList<String> web, ArrayList<Integer> imageId)
    {
        super(context, R.layout.grid_item_offer, web);
        this.context = context;
        this.web = web;
        this.imageId = imageId;

    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView= inflater.inflate(R.layout.grid_item_offer, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.tv_species1);

        ImageView imageView = (ImageView) rowView.findViewById(R.id.thumbnail);
        try {
            txtTitle.setText(web.get(position));

            imageView.setImageResource(imageId.get(position));
        }
        catch (Exception e)
        {

        }
        return rowView;
    }
}