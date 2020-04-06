package Fragment;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.hp.navigation.AppController;
import com.example.hp.navigation.Catagory_second;
import com.example.hp.navigation.R;
import com.example.hp.navigation.catagory_adapter_second;
import com.example.hp.navigation.item_full_display;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import modle.catagory_second_modle;

import static android.content.Context.MODE_PRIVATE;
import static com.example.hp.navigation.R.id.parent;
import static com.example.hp.navigation.R.layout.catagory_second;
import static com.example.hp.navigation.R.layout.fragment_shoping;

/**
 * Created by Hp on 24-07-2017.
 */

public class shoping extends android.support.v4.app.Fragment{
/*
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments

        return inflater.inflate(R.layout.fragment_shoping, container, false);
    }*/
private static final String TAG =Catagory_second.class.getSimpleName();
    private static final String url = "https://parshva.000webhostapp.com/ImageJsonData.php";
    private ProgressDialog pDialog;
    private List<catagory_second_modle> movieList = new ArrayList<catagory_second_modle>();
    private catagory_adapter_second a;
    private GridView listView_cat_second;
    String catagory_from_jason;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        Log.v("TAGGGGGGGGGGg",TAG);
        final View rootView = inflater.inflate(fragment_shoping, container, false);
        Bundle arguments = getArguments();
//        String desired_string = arguments.getString("sub_catagory");
        SharedPreferences prefs = getActivity().getSharedPreferences("Login", MODE_PRIVATE);
        final String user_id = prefs.getString("id","");
        Log.v("type++++++++++++++++",user_id);
        //final String catagory_final =desired_string;
        //final String catagory_final=getIntent().getStringExtra("catagory_type");
        //Log.v("catagory_temp", catagory_final);
        listView_cat_second = (GridView) rootView.findViewById(R.id.list_catagory_two);
        Log.v("DS","XXXX"+movieList.size());
        //

        listView_cat_second.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                item_full_display f=new item_full_display();
                catagory_second_modle m=movieList.get(position);
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
                FragmentTransaction ft =getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame,f);
               /* movieList.add()
                a = new catagory_adapter_second(getActivity(),movieList);
                listView_cat_second.setAdapter(a);*/
                ft.addToBackStack(null);

                ft.commit();

                // ft.addToBackStack(null);
            }
        });


        listView_cat_second.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(final AdapterView<?> adapterView, View view, final int i, long l) {


                //Intent intent = new Intent()
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        /*TextView textView=(TextView)rootView.findViewById(R.id.float_text);
        textView.setText("1");*/
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
        JsonArrayRequest movieReq = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
                        hidePDialog();
                        movieList.clear();
                        // Parsing json
                        for (int i = 0; i < response.length(); i++) {
                            try {

                                JSONObject obj = response.getJSONObject(i);
                                catagory_second_modle movie = new catagory_second_modle();
                                movie.setTitle(obj.getString("title"));
                                movie.setThumbnailUrl(obj.getString("image"));
                                movie.setRating(obj.getString("rating"));
                                movie.setYear(obj.getString("release Year"));
                                movie.setDetails(obj.getString("details"));
                                movie.setAbout(obj.getString("about"));
                                movie.setItem_id(obj.getString("id"));
                                movie.setUser_id(user_id);
                                Log.v("idsssssssss",obj.getString("id"));
                                catagory_from_jason=obj.getString("category");
                                //Log.v("--------------------",catagory_final);
                                // adding movie to movies array

                               // if(catagory_final.equals(catagory_from_jason)) {
                                    movieList.add(movie);
                               // }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                        a = new catagory_adapter_second(getActivity(),movieList);
                        listView_cat_second.setAdapter(a);

                        // notifying list adapter about data changes
                        // so that it renders the list view with updated dgata



                       // listView_cat_second.setLayoutManager( new LinearLayoutManager(this));
                        //a.notifyDataSetChanged();
                        a.notifyDataSetChanged();
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

       /*}
        catch (Exception e)
        {
            Log.v("exception occurs",e.toString());
        }*/

        return rootView;
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
        getActivity().setTitle("Shoping");
    }
}
