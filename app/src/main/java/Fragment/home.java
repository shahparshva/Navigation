package Fragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.hp.navigation.Catagory_second;
import com.example.hp.navigation.HLVAdapter;
import com.example.hp.navigation.HLVAdapter1;
import com.example.hp.navigation.R;
import com.example.hp.navigation.catagory_adapter_first;
import com.example.hp.navigation.main_offer_adapter;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Hp on 24-07-2017.
 */

public class home extends Fragment {
    /**
     * Created by Belal on 18/09/16.
     */
    RecyclerView mRecyclerView,mRecyclerView1;
    RecyclerView.LayoutManager mLayoutManager,mLayoutManager1;
    RecyclerView.Adapter<HLVAdapter.ViewHolder> mAdapter;
   RecyclerView.Adapter<HLVAdapter1.ViewHolder> mAdapter1;
    main_offer_adapter a;
    ArrayList<String> alName;
    ArrayList<Integer> alImage;
   // AutoCompleteTextView completeTextView;
    ImageView i;
    ListView listView_offer;
    private AutoCompleteTextView actv;

   /* @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }*/

    @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            //returning our layout file
            //change R.layout.yourlayoutfilename for each of your fragments

          final View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        getActivity().getWindow().setStatusBarColor(Color.DKGRAY);
       alName = new ArrayList<>(Arrays.asList("Home", "Marine store", "Furniture", "Machinery", "Electronics"));
        alImage = new ArrayList<>(Arrays.asList(R.drawable.home2, R.drawable.marin, R.drawable.office, R.drawable.mech, R.drawable.inditrial2));
       /* alName=new ArrayList<String>();
        String test="R.drawable.home2";
        alImage=new ArrayList<Integer>();

        alName .add("Home");
        Integer integer= Integer.valueOf(test);
        alImage.add(integer);*/

        // Calling the RecyclerView

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);

        // The number of Columns
        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
       // mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.INVALID_OFFSET, false);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new HLVAdapter(getActivity(), alName, alImage);
        mRecyclerView.setAdapter(mAdapter);
       // listView_offer=(ListView)rootView.findViewById(R.id.list_offer);
       /* a= new main_offer_adapter(rootView.getContext(),alName,alImage);

        listView_offer.setAdapter(a);*/
        actv=(AutoCompleteTextView)rootView.findViewById(R.id.actv);
        getActivity().setTitle("home");
        Log.v("######","&&&&&&");

        String auto[] = getResources().getStringArray(R.array.array_country);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, auto);
        actv.setAdapter(adapter);
        actv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT=0;
                final int DRAWABLE_TOP=1;
                final int DRAWABLE_RIGHT=2;
                final int DRAWABLE_BOTTOM=3;

                if (event.getAction()==MotionEvent.ACTION_UP){
                    if (event.getRawX()>=(actv.getRight()- actv.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width()))
                    {
                        Log.v("++++",actv.getText().toString());
                        if (actv.getText().toString().equals(""))
                        {
                            Toast.makeText(getActivity(),"No Item",Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Fragment fragment=null;
                            Toast.makeText(getActivity(),actv.getText(),Toast.LENGTH_SHORT).show();
                             fragment=new Catagory_second();
                            Bundle arguments = new Bundle();
                            final String catagory_temp=actv.getText().toString();
                            arguments.putString("sub_catagory",catagory_temp);
                            fragment.setArguments(arguments);
                         //   ((FragmentActivity)context).getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,f).commit();
                          ///  ft = ((FragmentActivity)context).getSupportFragmentManager().beginTransaction();
                           // ft.addToBackStack(null);
                            FragmentTransaction ft =getActivity().getSupportFragmentManager().beginTransaction();
                            ft.replace(R.id.content_frame,fragment);
                            ft.addToBackStack(null);
                            ft.commit();
                        }

                    }
                }

                return false;
            }
        });
            return rootView;


        }


        @Override
        public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            //you can set the title for your toolbar here for different fragments different titles
            getActivity().setTitle("Home");

        }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        setRetainInstance(true);
    }

}

