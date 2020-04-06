package Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.example.hp.navigation.AppController;
import com.example.hp.navigation.Catagory_second;
import com.example.hp.navigation.R;
import com.example.hp.navigation.catagory_adapter_second;
import com.example.hp.navigation.item_full_display;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import modle.catagory_second_modle;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Hp on 24-07-2017.
 */

public class profile extends Fragment
{
    private static final String TAG =profile.class.getSimpleName();
    ImageView edit;
    NetworkImageView image_profile;
    TextView username,phone_no,name,address;
    private static final String url = "https://parshva.000webhostapp.com/profile.php";
    private ProgressDialog pDialog;
    private ImageLoader imageloader;
    /**
     * Created by Hp on 24-07-2017.
     */


        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
           View rootView;
            rootView = inflater.inflate(R.layout.fragment_profile, container, false);
           edit=(ImageView) rootView.findViewById(R.id.edit_profile);
            image_profile=(NetworkImageView)rootView.findViewById(R.id.user_profile_photo);
            name=(TextView)rootView.findViewById(R.id.user_profile_name);
            username=(TextView)rootView.findViewById(R.id.txt_username);
            phone_no=(TextView)rootView.findViewById(R.id.txt_phone);
            address=(TextView)rootView.findViewById(R.id.txt_address);
            imageloader = AppController.getInstance().getImageLoader();
            SharedPreferences prefs = getActivity().getSharedPreferences("Login", MODE_PRIVATE);
            final String idName = prefs.getString("id","");
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    /*Fragment ft;
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.content_frame, fragment);
                    ft.commit();*/
                }
            });
            pDialog = new ProgressDialog(getActivity());
            // Showing progress dialog before making http request
            pDialog.setMessage("Loading...");
            pDialog.show();
            pDialog.setCancelable(false);

            try{
                // changing action bar color
                getActivity().getWindow().setStatusBarColor(Color.blue(5));


            }catch(NullPointerException ne){
                ne.printStackTrace();
            }



            JsonArrayRequest movieReq = new JsonArrayRequest(url,
                    new Response.Listener<JSONArray>() {

                        @Override
                        public void onResponse(JSONArray response) {
                            Log.d(TAG, response.toString());
                            hidePDialog();

                            // Parsing json
                            for (int i = 0; i < response.length(); i++) {
                                try {

                                    JSONObject obj = response.getJSONObject(i);
                                    if(obj.getString("id").toString().equals(idName))
                                    {
                                        String im=obj.getString("imageurl");
                                        image_profile.setImageUrl(im,imageloader);
                                        name.setText(obj.getString("name"));
                                        username.setText("UserName :"+obj.getString("user_name"));
                                        phone_no.setText("Phone No."+obj.getString("mobile_no"));
                                    }


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }


                            // notifying list adapter about data changes
                            // so that it renders the list view with updated dgata
                            //a.notifyDataSetChanged();
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
            getActivity().setTitle("Profile");
        }
        public void edit(View view)
        {
            Profile_edit f=new Profile_edit();

            Bundle i = new Bundle();
            // i.putString("ABC", "" + adapterView.getItemAtPosition(position));
            i.putString("image",image_profile.getImageURL());
            i.putString("name",name.getText().toString());
            i.putString("uname", username.getText().toString());
            i.putString("phno",phone_no.getText().toString());
            i.putString("address",address.getText().toString());
            f.setArguments(i);


            //i.putString("user_id","1");;
            // (getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,f).commit();
            //ft = ((FragmentActivity)context).getSupportFragmentManager().beginTransaction();
            //ft.addToBackStack(null);
            // FragmentTransaction ft = rootView.beginTransaction();
            FragmentTransaction ft =getActivity().getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame,f);
            ft.addToBackStack(null);

            ft.commit();
        }

    }



