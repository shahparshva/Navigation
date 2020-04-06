package Fragment;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.hp.navigation.AppController;
import com.example.hp.navigation.CartAdapter;
import com.example.hp.navigation.MainActivity;
import com.example.hp.navigation.R;
import com.example.hp.navigation.cart_blank;
import com.example.hp.navigation.selleritem_adapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import modle.catagory_second_modle;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Parshva on 15-Aug-17.
 */

public class view_item_seller extends Fragment {
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String url ="https://parshva.000webhostapp.com/ImageJsonData.php";
    private ProgressDialog pDialog;
    ListView listView;
    selleritem_adapter adapter;
    private List<catagory_second_modle> movieList = new ArrayList<catagory_second_modle>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_view_item_seller, container, false);
        SharedPreferences prefs = getActivity().getSharedPreferences("Login", MODE_PRIVATE);
        final String id,type;
        Log.v("type of user",prefs.getString("type",""));
        Log.v("type of user",prefs.getString("id",""));
        id= prefs.getString("id","");
        System.out.println("already have id"+id);
            listView = (ListView)rootView. findViewById(R.id.view_item_listview_seller);
            adapter = new selleritem_adapter(getActivity(),movieList);
        listView.setAdapter(adapter);
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

                        // Parsing json
                        for (int i = 0; i < response.length(); i++) {
                            try {

                                JSONObject obj = response.getJSONObject(i);
                                catagory_second_modle movie = new catagory_second_modle();
                                movie.setTitle(obj.getString("title"));
                                movie.setThumbnailUrl(obj.getString("image"));
                                movie.setRating( obj.getString("rating"));


                                movie.setYear(obj.getString("release Year"));
                                movie.setDetails(obj.getString("details"));
                                movie.setAbout(obj.getString("about"));
                                movie.setItem_id(obj.getString("id"));

                                String temp=(obj.getString("seller_id"));
                                Log.v("temppppp",temp);
                                if(temp.equals(id))
                                {
                                    movieList.add(movie);
                                }


                            } catch (JSONException e) {

                                e.printStackTrace();

                            }


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


        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(movieReq);
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        return rootView;
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
        getActivity().setTitle("View Item");
    }
}
