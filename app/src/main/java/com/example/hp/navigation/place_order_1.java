package com.example.hp.navigation;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Parshva on 14-Aug-17.
 */

public class place_order_1 extends Fragment {
    EditText username,mobno,altermobno,pincode,town,city,state,address;
    Button btncancel,btnplaceorder;
    ProgressDialog pDialog;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        final View rootView = inflater.inflate(R.layout.payment, container, false);


        username=(EditText)rootView.findViewById(R.id.username);
        mobno=(EditText)rootView.findViewById(R.id.mobno);
        altermobno=(EditText)rootView.findViewById(R.id.altermobno);
        pincode=(EditText)rootView.findViewById(R.id.pincode);
        town=(EditText)rootView.findViewById(R.id.town);
        city=(EditText)rootView.findViewById(R.id.city);
        state=(EditText)rootView.findViewById(R.id.state);
        address=(EditText)rootView.findViewById(R.id.address);

        btncancel=(Button)rootView.findViewById(R.id.btncancel);
        btnplaceorder=(Button)rootView.findViewById(R.id.btnplaceorder);


        btnplaceorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pDialog = new ProgressDialog(getActivity());
                // Showing progress dialog before making http request
                pDialog.setMessage("Loading...");
                pDialog.show();
                pDialog.setCancelable(false);
                String u = username.getText().toString();
                String m = mobno.getText().toString();
                String am = altermobno.getText().toString();
                String pin = pincode.getText().toString();
                String t = town.getText().toString();
                String c = city.getText().toString();
                String s = state.getText().toString();
                String a = address.getText().toString();


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
                    SharedPreferences prefs =getActivity().getSharedPreferences("Login", MODE_PRIVATE);
                    String idName = prefs.getString("id", "");
                    String type = prefs.getString("type", "");
                    String method = "placeorder";

                    BackgroundTask_PlaceOrder backgroundTask = new BackgroundTask_PlaceOrder(getContext(),getActivity());
                    backgroundTask.execute(method, u, m, am, pin, t, c, s, a,idName);


                    Fragment fragment = new place_order_2();
                    FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.content_frame, fragment);
                   // ft.addToBackStack(null);
                    ft.commit();
                    /*Intent i=new Intent(getApplicationContext() ,placeorder.class);
                    startActivity(i);  */

                }
                pDialog.hide();
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
}
