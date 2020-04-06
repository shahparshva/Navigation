package com.example.hp.navigation;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import modle.catagory_second_modle;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Parshva on 03-Aug-17.
 */

public class cart_display extends Fragment {
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String url ="https://parshva.000webhostapp.com/cart.php";
    private ProgressDialog pDialog;
    TextView total;
    Button process_pay,shopnow;
    String temp;
    //Spinner spinner_change_qty;
    private List<catagory_second_modle> movieList = new ArrayList<catagory_second_modle>();
    private ListView listView;
    private CartAdapter adapter;
    int price_total=0;
    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        View rootView = inflater.inflate(R.layout.cart_display, container, false);
        SharedPreferences prefs = getActivity().getSharedPreferences("Login", MODE_PRIVATE);
        final String id,type;
        Log.v("type of user",prefs.getString("type",""));
        Log.v("type of user",prefs.getString("id",""));
        id= prefs.getString("id","");
        System.out.println("already have id"+id);
        total=(TextView) rootView.findViewById(R.id.txt_total);
        process_pay=(Button)rootView.findViewById(R.id.btn_process_pay);
       // shopnow=(Button)rootView.findViewById(R.id.)
        listView = (ListView)rootView. findViewById(R.id.list_cart);
        adapter = new CartAdapter(getActivity(), movieList);
        listView.setAdapter(adapter);
       // spinner_change_qty=(Spinner)rootView.findViewById(R.id.cartitem_change);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });


        listView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {


            }
        });
        pDialog = new ProgressDialog(getActivity());
        // Showing progress dialog before making http request
        pDialog.setMessage("Loading...");
        pDialog.show();


        // changing action bar color
       // getActivity().getWindow().setStatusBarColor(Color.WHITE);

        // Creating volley request obj
        JsonArrayRequest movieReq = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());

                        movieList.clear();
                        price_total=0;
                        // Parsing json
                        for (int i = 0; i < response.length(); i++) {
                            try {

                                JSONObject obj = response.getJSONObject(i);
                                catagory_second_modle movie = new catagory_second_modle();
                                movie.setTitle(obj.getString("title"));
                                movie.setThumbnailUrl(obj.getString("image"));
                                movie.setRating( obj.getString("rating"));


                                movie.setYear(obj.getString("quantity"));
                                movie.setDetails(obj.getString("details"));
                                movie.setAbout(obj.getString("about"));
                                movie.setItem_id(obj.getString("item_id"));
                                movie.setMax_item(obj.getString("quantity_max"));
                                temp=(obj.getString("user_id"));
                                Log.v("temppppp",temp);
                                if(temp.equals(id))
                                {
                                    price_total= price_total+Integer.parseInt(movie.getRating())*(Integer.parseInt(movie.getYear()));
                                    Log.v("price+++++", String.valueOf(price_total));
                                    movie.setUser_id(temp);
                                    movieList.add(movie);
                                }


                            } catch (JSONException e) {

                                e.printStackTrace();

                            }


                        }
                        System.out.println(price_total+"////////");
                        if(price_total>0)
                        {

                            total.setText(String.valueOf(price_total)+" Rs.");
                            process_pay.setText("Process to pay");
                           // System.out.println(price_total+"////////");
                        }
                        else
                        {
                            cart_blank cart_blank=new cart_blank();
                            (getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,cart_blank).commit();

                            Log.v("no item found","");
                        }

                        // notifying list adapter about data changes
                        // so that it renders the list view with updated data

                        adapter.notifyDataSetChanged();
                        hidePDialog();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                cart_blank cart_blank=new cart_blank();
                (getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,cart_blank).commit();
            }

        });

        adapter.notifyDataSetChanged();
        Log.v("price+++++", String.valueOf(price_total));

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(movieReq);
        /*if(price_total==0)
        {
            process_pay.setText("Shop now");
        }
        else
        {
            total.setText((int) price_total);
        }*/
        process_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor =getActivity().getSharedPreferences("total", MODE_PRIVATE).edit();
                editor.putInt("totalp",price_total);
                editor.apply();
                editor.commit();

                SharedPreferences prefs =getActivity().getSharedPreferences("order", MODE_PRIVATE);
               int x=prefs.getInt("add",0);


              /*  Log.v("----------------", String.valueOf(x));
                if (x==1)
                {

                    System.out.println("oneeeeeeeeeeeeeeeeeeeeeeeeee"+"");
                    place_order_2 f = new place_order_2();
                    Fragment fragment = new place_order_1();
                    FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.content_frame, f);
                    ft.addToBackStack(null);
                    ft.commit();
                }

                else {*/



                    place_order_3 f = new place_order_3();
                    System.out.println("twoooooooooooooooooooooo"+"");
                    FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.content_frame, f);
                    ft.addToBackStack(null);
                    ft.commit();
               // }

            }
        });


        return rootView;
        // return inflater.inflate(R.layout.catagory_first, container, false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        hidePDialog();
    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("catagory");
    }

}
