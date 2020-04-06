package com.example.hp.navigation;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;
import java.util.List;

import Fragment.view_item_seller;
import modle.catagory_second_modle;

public class selleritem_adapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<catagory_second_modle> movieItems=new ArrayList<catagory_second_modle>();
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public selleritem_adapter(Activity activity, List<catagory_second_modle> movieItems) {
        this.activity = activity;
        this.movieItems = movieItems;
    }

    @Override
    public int getCount() {
        return movieItems.size();
    }

    @Override
    public Object getItem(int location) {
        return movieItems.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_row_cart, null);

        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();
        NetworkImageView thumbNail = (NetworkImageView) convertView
                .findViewById(R.id.thumbnail);
        TextView title = (TextView) convertView.findViewById(R.id.title);
        TextView rating = (TextView) convertView.findViewById(R.id.rating);
        //TextView genre = (TextView) convertView.findViewByd(R.id.genre);
        final TextView year = (TextView) convertView.findViewById(R.id.releaseYear);
        ImageView removecart=(ImageView) convertView.findViewById(R.id.btn_remove);
        //ImageButton plus=(ImageButton) convertView.findViewById(R.id.plus);
        //final ImageButton minus=(ImageButton) convertView.findViewById(R.id.minus);
        final View finalConvertView = convertView;
        removecart.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //Log.v("clicked item ","**********Button clicked");

          catagory_second_modle movie=movieItems.get(position);
            String method="register";
            Context context= finalConvertView.getContext();
            BackgroundTask_remove_seller backgroundTask=new BackgroundTask_remove_seller(context);
        /*   System.out.println(movie.getTitle());
            System.out.println(movie.getYear());*/
            backgroundTask.execute(method,movie.getTitle(),movie.getThumbnailUrl(),movie.getRating(),movie.getYear(),movie.getAbout(),movie.getdetails());
            view_item_seller f=new view_item_seller();

            ((FragmentActivity)context).getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,f).commit();

        }
    });


        // getting movie data for the row
        catagory_second_modle m = movieItems.get(position);

        // thumbnail image
        thumbNail.setImageUrl(m.getThumbnailUrl(), imageLoader);

        // title
        title.setText(m.getTitle());
        // rating
        rating.setText("Price: " + String.valueOf(m.getRating()));

        year.setText(m.getYear());
        return convertView;
    }


}