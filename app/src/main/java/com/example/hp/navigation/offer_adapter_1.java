package com.example.hp.navigation;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;
import java.util.List;

import modle.catagory_second_modle;
import modle.offer_modle_1;

/**
 * Created by Parshva on 30-Nov-17.
 */

public class offer_adapter_1 extends BaseAdapter{

    private Activity activity;
    private LayoutInflater inflater;
   private List<catagory_second_modle> offer_items1_1=new ArrayList<catagory_second_modle>();
    private ImageLoader imageLoader;
    //private ArrayList<String> alName;
    //private ArrayList<String> alImage;
    int width, height;
    LinearLayout.LayoutParams parms;





    public offer_adapter_1(Activity activity,List<catagory_second_modle> offer_items1_1) {
        this.activity = activity;
        //this.alName = alName;
        //his.alImage = alImage;
        this.offer_items1_1=offer_items1_1;
        imageLoader=AppController.getInstance().getImageLoader();

        Display display = activity.getWindowManager().getDefaultDisplay();
        width = display.getWidth(); // ((display.getWidth()*20)/100)
        height = display.getHeight();// ((display.getHeight()*30)/100)
        parms = new LinearLayout.LayoutParams(width/2,height/4);
        Log.v("Test","XXXXX"+offer_items1_1.size());
    }

    @Override
    public int getCount() {

        return offer_items1_1.size();
    }

    @Override
    public Object getItem(int position) {

        return offer_items1_1.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }



    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.card_list_item, null);

        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();

        NetworkImageView offer_img = (NetworkImageView) convertView
                .findViewById(R.id.img_offer);
        TextView offer_title = (TextView) convertView.findViewById(R.id.title_offer);

        final Context context = parent.getContext();

        Log.v("gggggggggggggggggggggg","5555555555555555555555555");

                catagory_second_modle a= offer_items1_1.get(position);

        // thumbnail image
        offer_img.setImageUrl(a.getThumbnailUrl(), imageLoader);

        // title
        offer_title.setText(a.getTitle());
        return convertView;
    }
}
