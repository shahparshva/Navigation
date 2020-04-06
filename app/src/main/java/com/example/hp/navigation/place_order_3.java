package com.example.hp.navigation;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import modle.catagory_second_modle;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Parshva on 15-Aug-17.
 */

public class place_order_3 extends Fragment {
    private static final String url = "https://parshva.000webhostapp.com/order_address.php";
    private ProgressDialog pDialog;
    private static int SPLASH_TIME_OUT = 2000;
    AutoCompleteTextView username, mobno, altermobno, pincode, town, city, state, address;
    Button btncancel, btnplaceorder;
    String u,m,am,pin,t,c,s,a;
    private static final String TAG = place_order_2.class.getSimpleName();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        final View rootView = inflater.inflate(R.layout.payment, container, false);


        username = (AutoCompleteTextView) rootView.findViewById(R.id.username);
        mobno = (AutoCompleteTextView) rootView.findViewById(R.id.mobno);
        altermobno = (AutoCompleteTextView) rootView.findViewById(R.id.altermobno);
        pincode = (AutoCompleteTextView) rootView.findViewById(R.id.pincode);
        town = (AutoCompleteTextView) rootView.findViewById(R.id.town);
        city = (AutoCompleteTextView) rootView.findViewById(R.id.city);
        state = (AutoCompleteTextView) rootView.findViewById(R.id.state);
        address = (AutoCompleteTextView) rootView.findViewById(R.id.address);
        SharedPreferences prefs = getActivity().getSharedPreferences("Login", MODE_PRIVATE);
        final String idName = prefs.getString("id", "");
        btncancel = (Button) rootView.findViewById(R.id.btncancel);
        btnplaceorder = (Button) rootView.findViewById(R.id.btnplaceorder);
        btnplaceorder.setText("CONTINUE");

        pDialog = new ProgressDialog(getActivity());
        // Showing progress dialog before making http request
        pDialog.setMessage("Loading...");
        pDialog.show();
        pDialog.setCancelable(false);

        try {
            // changing action bar color
            //  getActivity().getWindow().setStatusBarColor(Color.);


        } catch (NullPointerException ne) {
            ne.printStackTrace();
        }


        // Creating volley request obj
        JsonArrayRequest movieReq = new JsonArrayRequest(url,
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
                                if (obj.get("user_id").equals(idName)) {
                                    Log.v("obbbbbbbbbbbbbb", obj.toString());
                                    System.out.println(obj.get("address"));

                                    username.setText(obj.getString("name"));

                                    mobno.setText(obj.getString("mobno"));

                                    altermobno.setText(obj.getString("altermobno"));

                                    pincode.setText(obj.getString("pincode"));

                                    town.setText(obj.getString("town"));

                                    city.setText(obj.getString("city"));

                                    state.setText(obj.getString("state"));

                                    address.setText(obj.getString("address"));
                                    //txtaddress.setText(obj.getString("address")+","+obj.get("city")+","+obj.getString("town")+"- "+obj.get("pincode")+","+obj.getString("state"));
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

        AppController.getInstance().addToRequestQueue(movieReq);

        btnplaceorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                pDialog.setCancelable(false);
                 u = username.getText().toString();
                 m = mobno.getText().toString();
                 am = altermobno.getText().toString();
                 pin = pincode.getText().toString();
               t = town.getText().toString();
               c = city.getText().toString();
              s = state.getText().toString();
                 a = address.getText().toString();


                if (u.equals("")) {
                    Toast toast = Toast.makeText(getActivity(), "Enter Name", Toast.LENGTH_SHORT);
                    toast.show();
                } else if (m.equals("")) {
                    Toast toast = Toast.makeText(getActivity(), "Enter Mobile Number", Toast.LENGTH_SHORT);
                    toast.show();
                } else if (pin.equals("")) {
                    Toast toast = Toast.makeText(getActivity(), "Enter Pincode", Toast.LENGTH_SHORT);
                    toast.show();
                } else if (t.equals("")) {
                    Toast toast = Toast.makeText(getActivity(), "Enter Your Town", Toast.LENGTH_SHORT);
                    toast.show();
                } else if (c.equals("")) {
                    Toast toast = Toast.makeText(getActivity(), "Enter City", Toast.LENGTH_SHORT);
                    toast.show();
                } else if (s.equals("")) {
                    Toast toast = Toast.makeText(getActivity(), "Enter State", Toast.LENGTH_SHORT);
                    toast.show();
                } else if (a.equals("")) {
                    Toast toast = Toast.makeText(getActivity(), "Enter Your Address", Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                   /* SharedPreferences prefs = getActivity().getSharedPreferences("Login", MODE_PRIVATE);
                    String idName = prefs.getString("id", "");
                    String type = prefs.getString("type", "");*/
                    String method = "placeorder";

                    BackgroundTask_PlaceOrder backgroundTask = new BackgroundTask_PlaceOrder(getContext(), getActivity());
                    backgroundTask.execute(method, u, m, am, pin, t, c, s, a, idName);

                    /*Intent i=new Intent(getApplicationContext() ,placeorder.class);
                    startActivity(i);  */
                    Fragment fragment = new place_order_2();
                    FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.content_frame, fragment);
                    ft.addToBackStack(null);
                    ft.commit();

                }
                //pDialog.hide();
            }


        });


        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //cart_display f=new cart_display();
                Fragment fragment = new cart_display();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, fragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });
        return rootView;
        //return inflater.inflate(R.layout.fragment_homes, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Place Order");
    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }

    }

    void get_delay() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                int x,y;

            }
        }, SPLASH_TIME_OUT);
    }
}
