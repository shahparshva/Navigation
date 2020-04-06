package com.example.hp.navigation;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;

/**
 * Created by Parshva on 04-Aug-17.
 */

public class item_full_display extends Fragment {
    TextView name;
    TextView price;
    TextView quantity;
    TextView details;
    TextView about;
    String item_id, returnedResult;
    String user_id;
    private ProgressDialog pDialog;

    NetworkImageView image;
    Button addtocart;
    private ImageLoader imageLoader;
    Spinner s;
    String[] item_no;
    String quantity_user;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file

        final View rootView = inflater.inflate(R.layout.list_item_full_display, container, false);
        name = (TextView) rootView.findViewById(R.id.title_full);
        price = (TextView) rootView.findViewById(R.id.price);
        quantity = (TextView) rootView.findViewById(R.id.quantity);
        details = (TextView) rootView.findViewById(R.id.txt_detail1);
        about = (TextView) rootView.findViewById(R.id.about_seller);

        //image = (NetworkImageView) rootView.findViewById(R.id.thumbnail);
        addtocart = (Button) rootView.findViewById(R.id.button_add);
        image = (NetworkImageView)rootView.findViewById(R.id.thumbnail);
        Bundle i = getArguments();
        //String desired_string = arguments.getString("sub_catagory");
       // new ImageLoadTask(i.getString("image"), image).execute();
        Log.v("**************",i.getString("image"));
        //i.putString("image", m.getThumbnailUrl());
       /* ProgressDialog pDialog;
        pDialog = new ProgressDialog(getActivity());
        // Showing progress dialog before making http request
        pDialog.setMessage("Loading...");
        pDialog.show();*/
        imageLoader = AppController.getInstance().getImageLoader();
        final String imageurl,title,prices,quantitys,detail,abouts;
        imageurl=i.getString("image");
        title=i.getString("title");
        prices=i.getString("price");
        quantitys=i.getString("year");
        detail=i.getString("detail");
        abouts=i.getString("about");
        int temp=Integer.parseInt(quantitys);
        int j=0;
        item_no=new String[temp];
        //StringBuffer[] item_no = new StringBuffer[temp];
        for (j=1;j<=temp;j++)
        {
            item_no[j-1]= String.valueOf(j);
        }
        Log.v("arrray", Arrays.toString(item_no));
        s=(Spinner)rootView.findViewById(R.id.noofitem);
        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.v("itemmmmmm",item_no[position]);
                Log.v("posssitiooon", String.valueOf(position));
                quantity_user=item_no[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ArrayAdapter<String> no=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,item_no);
        no.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        s.setAdapter(no);


        image.setImageUrl(imageurl, imageLoader);
        name.setText(title);
        price.setText(prices);
        quantity.setText(quantitys);
        details.setText(detail);
        about.setText(abouts);
        item_id = i.getString("item_id");
        Log.v(user_id = i.getString("user_id"),"******");

        BackgroundTask_item_full backgroundTask_item_full=new BackgroundTask_item_full(getActivity());
        String method="login";
        try{
            returnedResult = backgroundTask_item_full.execute(method,item_id,user_id).get();
            Log.v("####","%%%%%"+returnedResult);
        }
        catch (InterruptedException IE){
            IE.printStackTrace();
        }
        catch (ExecutionException EE)
        {
            EE.printStackTrace();
        }
        if(returnedResult.equals("add to card")) {
            addtocart.setText("Add to cart");

        }
        else if(returnedResult.equals("gotocart")) {

            addtocart.setText("Go to cart");


        }

        addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(addtocart.getText().equals("Add to cart"))
                {
                    String method="register";
                    BackgroundTask_addtocart_second backgroundTask = new BackgroundTask_addtocart_second(getActivity());

                    backgroundTask.execute(method,title,imageurl,prices,quantity_user,detail,abouts,item_id,user_id,quantitys);

                }
                else
                {
                    Fragment fragment = new cart_display();
                    FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.content_frame, fragment);
                    ft.addToBackStack(null);
                    ft.commit();
                  //  return true;
                    //Toast.makeText(getActivity(),"gooooooo toooooooo cart",Toast.LENGTH_SHORT).show();
                }
            }
        });
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("catagory");
    }
    private void hidePDialog() {

        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }



}
