package com.example.hp.navigation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import Fragment.home;

/**
 * Created by Parshva on 03-Aug-17.
 */

public class cart_blank extends Fragment {
    Button cartempty;
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.cart_empty, container, false);
        cartempty=(Button)rootView.findViewById(R.id.start_shopping);
        cartempty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("Button continue clicked","");
                Fragment fragment=new home();
                (getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,fragment).commit();
            }
        });

        return rootView;
        //return inflater.inflate(R.layout.cart_empty, container, false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Cart");
    }

}
