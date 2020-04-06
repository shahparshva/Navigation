package com.example.hp.navigation;

import android.app.Activity;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import modle.catagory_second_modle;

public class CartAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<catagory_second_modle> movieItems=new ArrayList<catagory_second_modle>();
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public CartAdapter(Activity activity, List<catagory_second_modle> movieItems) {
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
        ImageButton plus=(ImageButton) convertView.findViewById(R.id.plus);
        final ImageButton minus=(ImageButton) convertView.findViewById(R.id.minus);
        final View finalConvertView = convertView;
        removecart.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //Log.v("clicked item ","**********Button clicked");

            catagory_second_modle movie=movieItems.get(position);
            String method="register";
            Context context= finalConvertView.getContext();
            BackgroundTask_remove backgroundTask=new BackgroundTask_remove(context);
           /* System.out.println(movie.getTitle());
            System.out.println(movie.getYear());*/
            backgroundTask.execute(method,movie.getTitle(),movie.getThumbnailUrl(),movie.getRating(),movie.getYear(),movie.getAbout(),movie.getdetails());
            cart_display f=new cart_display();

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

        // genre

		/*String genreStr = "";
        for (String str : m.getGenre()) {
			genreStr += str + ", ";
		}
		genreStr = genreStr.length() > 0 ? genreStr.substring(0,
				genreStr.length() - 2) : genreStr;
		genre.setText(genreStr);
		
		// release year*/
        if (m.getYear().equals("1"))
        {
            minus.setVisibility(View.INVISIBLE);
        }
        year.setText(String.valueOf(m.getYear()));
        if (m.getMax_item().equals(m.getYear()))
        {
            plus.setVisibility(View.INVISIBLE);
        }

       /* Context context= finalConvertView.getContext();
        catagory_second_modle movie=movieItems.get(position);
        BackgroundTask_cart_max  backgroundTask_cart_max=new BackgroundTask_cart_max(context);
        String method="login";
        String returnedResult;
        int x=0;
        try{
            returnedResult = backgroundTask_cart_max.execute(method,movie.getItem_id()).get();
             x= Integer.parseInt(returnedResult);
            Log.v("####","%%%%%"+returnedResult);
        }
        catch (InterruptedException IE){
            IE.printStackTrace();
        }
        catch (ExecutionException EE)
        {
            EE.printStackTrace();
        }
        if(x==Integer.parseInt(movie.getYear().toString()))
        {
            plus.setVisibility(View.INVISIBLE);
        }*/
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                catagory_second_modle movie=movieItems.get(position);
                int temp=Integer.parseInt(year.getText().toString());
                temp=temp+1;
                Context context= finalConvertView.getContext();
                BackgroundTask_changefromcart b=new BackgroundTask_changefromcart(context);
                b.execute("register", String.valueOf(temp),movie.getItem_id(),movie.getUser_id());
                cart_display f=new cart_display();
               // ((FragmentActivity)context).getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,f).commit();

                FragmentTransaction ft;
                ft = ((FragmentActivity)context).getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame,f);
               /* movieList.add()
                a = new catagory_adapter_second(getActivity(),movieList);
                listView_cat_second.setAdapter(a);*/

                ft.addToBackStack(null);

                ft.commit();

            }
        });
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                catagory_second_modle movie = movieItems.get(position);
                Context context = finalConvertView.getContext();
                int temp=Integer.parseInt(year.getText().toString());
                temp=temp-1;
                if (temp==0)
                {
                    String method="register";
                    BackgroundTask_remove backgroundTask=new BackgroundTask_remove(context);
                    backgroundTask.execute(method,movie.getTitle(),movie.getThumbnailUrl(),movie.getRating(),movie.getYear(),movie.getAbout(),movie.getdetails());
                    cart_display f=new cart_display();
                    FragmentTransaction ft;
                    ft = ((FragmentActivity)context).getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.content_frame,f);
               /* movieList.add()
                a = new catagory_adapter_second(getActivity(),movieList);
                listView_cat_second.setAdapter(a);*/

                    ft.addToBackStack(null);

                    ft.commit();
                   // ((FragmentActivity)context).getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,f).commit();
                }
                else {


                    BackgroundTask_changefromcart b = new BackgroundTask_changefromcart(context);
                    b.execute("register", String.valueOf(temp), movie.getItem_id(), movie.getUser_id());
                    cart_display f=new cart_display();
                    FragmentTransaction ft;
                    ft = ((FragmentActivity)context).getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.content_frame,f);
               /* movieList.add()
                a = new catagory_adapter_second(getActivity(),movieList);
                listView_cat_second.setAdapter(a);*/

                    ft.addToBackStack(null);

                    ft.commit();
                    //((FragmentActivity)context).getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,f).commit();

                }
            }
        });
        return convertView;
    }


}