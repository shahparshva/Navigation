package Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.hp.navigation.AppController;
import com.example.hp.navigation.R;

/**
 * Created by Parshva on 10-Aug-17.
 */

public class Profile_edit extends Fragment {
    EditText name1,no,address,pin;
    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        View rootView;
        rootView = inflater.inflate(R.layout.profile_second, container, false);
        Bundle i = getArguments();
        //String desired_string = arguments.getString("sub_catagory");
        // new ImageLoadTask(i.getString("image"), image).execute();
        Log.v("**************",i.getString("image"));

        final String imageurl,name,uname,phno,address;
        imageurl=i.getString("image").toString();
        name=i.getString("name").toString();
        uname=i.getString("uname").toString();
        phno=i.getString("phno").toString();
        address=i.getString("addrsss").toString();
        return inflater.inflate(R.layout.profile_second, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("items");
    }
}
