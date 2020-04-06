package com.example.hp.navigation;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Parshva on 02-Aug-17.
 */

public class Catagory_first extends android.support.v4.app.Fragment {
    catagory_adapter_first c;
    ArrayList<String> name;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        View rootView = inflater.inflate(R.layout.catagory_first, container, false);
        //Bundle arguments = getArguments();
        //String desired_string = arguments.getString("catagory");
       // Log.v("helllllllllllllo",desired_string);
        SharedPreferences prefs = getActivity().getSharedPreferences("catagory_sub",Context.MODE_PRIVATE);
        String desired_string=prefs.getString("subcatagory","");

            ListView list_catagory = (ListView) rootView.findViewById(R.id.list_cat);
            //String catagoryname= getIntent().getStringExtra("catagory");
            Log.v("catagory.....",desired_string);
            name = new ArrayList<>();
            if (desired_string.equals("Home")) {
                name.add("Bracket");
                name.add("Kitchen Item");
               // name.add("fittings items");
                name.add("Dining Items");
                name.add("Tubelights");
                name.add("Crocary");
                name.add("Home Furnishing");
                name.add("Home Decor");
                name.add("Curtains");
                name.add("Wingchain");
                name.add("Watch");
                name.add("Wall Clock");
                name.add("Flower Pots");
                name.add("Home Cleaning");
            }
            else if (desired_string.equals("Marine store"))
            {
                name.add("Mini Boats");
                name.add("Life gards boats");
                name.add("Fishing Net");
                name.add("Fishing Sticks");
                name.add("Swimming Costume");
            }
            else if (desired_string.equals("Furniture")) {
                name.add("Bed");
                name.add("selings material");
                name.add("Sofa");
                name.add("Dining Table");
                name.add("Mattresses");
                name.add("Electronics");
                name.add("Tv Cabinates");
                name.add("Chair");
                name.add("Table");
            }
            else if (desired_string.equals("Machinery"))
            {
                //name.add("inditrial raw matarial");
                name.add("Air Pump");
                name.add("Air Compraser");
                name.add("Boiler");
                name.add("Condenser");
                name.add("Drilling Machine");
                name.add("Chilling Units");
                name.add("Fire Extantion");
                //name.add("electronics item");
                //name.add("machanary");
                name.add("Generator");
                name.add("Water Pump");
                //name.add("safty items");
                //name.add("fire extantion");
            }
            else if (desired_string.equals("Electronics"))
            {
                //name.add("All Electronics");
                name.add("Television");
                name.add("Heating Appliances");
                name.add("Cooling Appliances");
                name.add("Fan");
                name.add("Air Compraser");
                name.add("Washing Machine");
                name.add("Air Conditionar");
                name.add("Fridge");
            name.add("Extention Board");

            }
            else
            {
                name.add("hello");
            }
            //Log.v("nameeeeeee", String.valueOf(name));
            c= new catagory_adapter_first(rootView.getContext(),name);
        //Log.v("adapterrrrrr", String.valueOf(c));
            list_catagory.setAdapter(c);
            //

        return rootView;
       // return inflater.inflate(R.layout.catagory_first, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("catagory");
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        setRetainInstance(true);
    }
   /* public void backToFragment(final Fragment fragment) {

        // go back to something that was added to the backstack
        getActivity().getSupportFragmentManager().popBackStackImmediate(
                fragment.getClass().getName(), 0);
        // use 0 or the below constant as flag parameter
        // FragmentManager.POP_BACK_STACK_INCLUSIVE);

    }*/



}
