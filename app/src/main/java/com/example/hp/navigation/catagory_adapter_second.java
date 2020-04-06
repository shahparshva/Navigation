package com.example.hp.navigation;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;
import java.util.List;

import modle.catagory_second_modle;

import static android.content.Context.MODE_PRIVATE;


public class catagory_adapter_second extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<catagory_second_modle> movieItems = new ArrayList<catagory_second_modle>();
    private ImageLoader imageLoader;// = AppController.getInstance().getImageLoader();
    int width, height;
    LinearLayout.LayoutParams parms;

    public catagory_adapter_second(Activity activity, List<catagory_second_modle> movieItems) {
        this.activity = activity;
        this.movieItems = movieItems;
        imageLoader = AppController.getInstance().getImageLoader();

        Display display = activity.getWindowManager().getDefaultDisplay();
         width = display.getWidth(); // ((display.getWidth()*20)/100)
         height = display.getHeight();// ((display.getHeight()*30)/100)
         parms = new LinearLayout.LayoutParams(width/2,height/4);

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
            convertView = inflater.inflate(R.layout.card_list_item, null);

        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();
        NetworkImageView thumbNail = (NetworkImageView) convertView
                .findViewById(R.id.thumbnail);
        TextView title = (TextView) convertView.findViewById(R.id.title);
        TextView rating = (TextView) convertView.findViewById(R.id.rating);
        //TextView genre = (TextView) convertView.findViewByd(R.id.genre);
        TextView year = (TextView) convertView.findViewById(R.id.releaseYear);
        ImageView addtocart = (ImageView) convertView.findViewById(R.id.btn_addtocart);
        thumbNail.setLayoutParams(parms);
        Button viewmore=(Button)convertView.findViewById(R.id.btn_viewmore);
       final Context context = parent.getContext();
        /*SharedPreferences prefs = context.getSharedPreferences("Login", MODE_PRIVATE);
        final String idName = prefs.getString("type","");
        Log.v("type+++++++++++"+idName,"");*/
      //  Toast.makeText(activity,""+getItemId(position),Toast.LENGTH_SHORT).show();


       // FloatingActionButton a=(FloatingActionButton)convertView.findViewById (R.id.fab);
      /*  TextView textView=(TextView)convertView.findViewById(R.id.float_text);
        textView.setText("1");*/
        addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v("clicked item ", "**********Button clicked");

                catagory_second_modle movie = movieItems.get(position);
                String method = "register";
                Context context = parent.getContext();
                Log.v("+++++++++++++++++++","******"+movie.getUser_id());
               // System.out.println(movie.getTitle());
                //System.out.println(movie.getYear());
               /* SharedPreferences prefs = context.getSharedPreferences("Login", MODE_PRIVATE);
                String idName = prefs.getString("user_id","");
                Log.v("type+++++++++++"+idName.toString(),"");*/
                BackgroundTask_addtocart_second backgroundTask = new BackgroundTask_addtocart_second(context);

                backgroundTask.execute(method, movie.getTitle(), movie.getThumbnailUrl(), movie.getRating(),"1", movie.getAbout(), movie.getdetails(),movie.getItem_id(),movie.getUser_id(),movie.getYear());

            }
        });
        viewmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item_full_display f=new item_full_display();
                catagory_second_modle m=movieItems.get(position);
                Bundle i = new Bundle();

                // i.putString("ABC", "" + adapterView.getItemAtPosition(position));
                i.putString("image", m.getThumbnailUrl());
                i.putString("title", m.getTitle());
                i.putString("price", m.getRating());
                i.putString("year", m.getYear());
                i.putString("detail", m.getdetails());
                i.putString("about", m.getAbout());
                i.putString("item_id",m.getItem_id());
                i.putString("user_id",m.getUser_id());
                f.setArguments(i);


                //i.putString("user_id","1");;
                // (getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,f).commit();
                //ft = ((FragmentActivity)context).getSupportFragmentManager().beginTransaction();
                //ft.addToBackStack(null);
                // FragmentTransaction ft = rootView.beginTransaction();
                FragmentTransaction ft;
                ft = ((FragmentActivity)context).getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame,f);
               /* movieList.add()
                a = new catagory_adapter_second(getActivity(),movieList);
                listView_cat_second.setAdapter(a);*/

                ft.addToBackStack(null);

                ft.commit();
               // pDialog.hide();
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

        // genre

		/*String genreStr = "";
        for (String str : m.getGenre()) {
			genreStr += str + ", ";
		}
		genreStr = genreStr.length() > 0 ? genreStr.substring(0,
				genreStr.length() - 2) : genreStr;
		genre.setText(genreStr);
		
		// release year*/
        year.setText(String.valueOf(m.getYear()));



        return convertView;
    }



}