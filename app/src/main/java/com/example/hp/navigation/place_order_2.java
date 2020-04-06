package com.example.hp.navigation;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.hp.navigation.mail.SendMail2;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import Fragment.home;
import modle.catagory_second_modle;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Parshva on 14-Aug-17.
 */

public class place_order_2 extends Fragment {
    private static final String TAG =place_order_2.class.getSimpleName();
    TextView txtname,txtaddress,total,total1;
    private static final String url = "https://parshva.000webhostapp.com/order_address.php";
    private static final String url1 ="https://parshva.000webhostapp.com/new/mail_json.php";
    private static final String url2 ="https://parshva.000webhostapp.com/contentformailjson.php";
    private ProgressDialog pDialog;
    String address,name_user,phoneno;
    public StringBuffer body1;
    private int i=0;

    Button change,btncancel,btnplaceorder;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.payment2, container, false);
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        txtname=(TextView)rootView.findViewById(R.id.txtname);
        txtaddress=(TextView)rootView.findViewById(R.id.txtaddress);
        total=(TextView)rootView.findViewById(R.id.total);
        total1=(TextView)rootView.findViewById(R.id.total1);
        body1=new StringBuffer();
        change=(Button)rootView.findViewById(R.id.change);
        btncancel=(Button)rootView.findViewById(R.id.btncancel);
        btnplaceorder=(Button)rootView.findViewById(R.id.btnplaceorder);

        SharedPreferences prefs =getActivity().getSharedPreferences("Login", MODE_PRIVATE);
        final String idName = prefs.getString("id", "");
        String method = "placeorder";

        SharedPreferences prefs1 =getActivity().getSharedPreferences("total", MODE_PRIVATE);
         final String price1 = String.valueOf(prefs1.getInt("totalp",0));
        System.out.println("/////////////////////"+price1);
        total.setText(price1);
        total1.setText(price1);

       pDialog = new ProgressDialog(getActivity());
        // Showing progress dialog before making http request
        pDialog.setMessage("Loading...");
        pDialog.show();
        pDialog.setCancelable(false);

        try{
            // changing action bar color
            //  getActivity().getWindow().setStatusBarColor(Color.);


        }catch(NullPointerException ne){
            ne.printStackTrace();
        }


        // Creating volley request obj
        JsonArrayRequest movieReq = new JsonArrayRequest(url2,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
                        hidePDialog();
                       // movieList.clear();
                        // Parsing json
                        for (int i = 0; i < response.length(); i++) {
                            try {

                                JSONObject obj = response.getJSONObject(i);
                                catagory_second_modle movie = new catagory_second_modle();
                                // movieList.clear();
                                if (obj.get("user_id").equals(idName))
                                {
                                    System.out.println(obj.get("address"));
                                    name_user=obj.getString("user_name");
                                    txtname.setText(name_user);
                                    address=obj.getString("address")+","+obj.get("city")+","+obj.getString("town")+"- "+obj.get("pincode")+","+obj.getString("state")+"\n"+"Mobile :"+obj.getString("mobno");
                                    phoneno=obj.getString("mobno");
                                    txtaddress.setText(address);
                                }




                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                hidePDialog();

            }
        });

        // Adding request to request queue
        // try {
        AppController.getInstance().addToRequestQueue(movieReq);
        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // place_order_1 f=new place_order_1();
                Fragment fragment = new cart_display();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, fragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });


        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                place_order_3 f=new place_order_3();
                //Fragment fragment = new place_order_1();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, f);
                ft.addToBackStack(null);
                ft.commit();
            }
        });
        btnplaceorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BackgroundTask_placeorder_final backgroundTask_placeorder_final=new BackgroundTask_placeorder_final(getContext(),getActivity());
                backgroundTask_placeorder_final.execute(price1,idName);


                final String[] img = new String[100];
                final String[] qty = new String[100];
                final String[] price = new String[100];
                final String[] product_name = new String[100];

                JsonArrayRequest movieReq = new JsonArrayRequest(url1,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                Log.d(TAG, response.toString());

                                // pDialog.hide();
                                // Parsing json

                                for (int j = 0; j < response.length(); j++) {
                                    try {

                                        JSONObject obj = response.getJSONObject(j);
                                        SharedPreferences prefs = getActivity().getSharedPreferences("order_id", MODE_PRIVATE);
                                        String order_id = prefs.getString("order_id_for_mail", "");
                                        if(order_id.equals(obj.getString("order_id")))
                                        {

                                            Log.v("valueeeeeeeeeeeeeee", String.valueOf(i));
                                            Log.v("obbbbbbbbbb",obj.toString());
                                            img[i] =obj.getString("image");
                                            Log.v("image urllllllllllll", img[0]);
                                            qty[i] =obj.getString("quantity");
                                            price[i] =obj.getString("rating");

                                            product_name[i] =obj.getString("title");


                                            body1.append("<img src=").append(img[i]).append(">");
                                            body1.append("<H3>Product Name:").append(product_name[i]).append("</H3>");
                                            body1.append("<H3>Quantity:").append(qty[i]).append("</H3>");
                                            body1.append("<H3>Price:").append(price[i]).append("</H3>");
                                            body1.append("<hr width=\"150px\">");
                                            i++;
                                            Log.v("bodyyyyyyyyyyyy",body1.toString());
                                        }

                                        Log.v("bodyyyyyyyyyyyy22222",body1.toString());


                                    } catch (JSONException e) {

                                        e.printStackTrace();

                                    }


                                }

                                // notifying list adapter about data changes
                                // so that it renders the list view with updated data


                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d(TAG, "Error: " + error.getMessage());
                           // Log.v("onnnnnnnnnnn errrrrrror responce","");
                    }

                });
                //adapter.notifyDataSetChanged();



                // Adding request to request queue
                AppController.getInstance().addToRequestQueue(movieReq);

                Log.v("Strinnnnnnnnnnnnnn",body1.toString());
               /* if(body1.equals(""))
                {
                    Toast.makeText(getContext(),"UNsucessfull",Toast.LENGTH_SHORT).show();
                }
                else {*/

               //Log.v("EMailllllllllllllllllllllll",name_user);
                    SendMail2 sendMail2 = new SendMail2(rootView.getContext(),name_user, "your order",address);
                    sendMail2.execute(body1);
                Fragment fragment=new home();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, fragment);
                //ft.addToBackStack(null);
                ft.commit();
               // }
            }

        });





        return rootView;
        // return inflater.inflate(R.layout.payment2, container, false);
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
        getActivity().setTitle("Place Order");
    }

}
