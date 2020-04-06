package Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hp.navigation.NothingSelectedSpinnerAdapter;
import com.example.hp.navigation.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Map;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;
import static com.example.hp.navigation.R.id.parent;

/**
 * Created by Hp on 25-07-2017.
 */

public class items extends Fragment implements AdapterView.OnItemSelectedListener {
    //public String[] country = {"India", "USA", "China", "Japan", "Other"};
  public String[] country;// = getResources().getStringArray(R.array.array_country);
    public static String label;

    private Button buttonChoose;
    private Button buttonUpload;
        String co;

    private ImageView imageView;

    private EditText edtname,edtprice,edtqty,edtdec,edtinfo;

    private Bitmap bitmap;

    private int PICK_IMAGE_REQUEST = 1;

    private String UPLOAD_URL ="https://parshva.000webhostapp.com/vollyupload/vollyupload/upload1.php";
    // public static final String UPLOAD_URL = "http://10.0.2.2/vollyupload/u1.php";
    private String KEY_IMAGE = "image";
    private String KEY_NAME = "name";

    private String KEY_PRICE="price";
    private String KEY_QUANTITY="quantity";
    private String KEY_DESCRIPTION="description";
    private String KEY_ABOUTUS="aboutus";
    private String KEY_CATEGORY="category";
    private String KEY_SELLERID="seller_id";
        /**
     * Created by Belal on 18/09/16.
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            //returning our layout file
            View rootView;
            rootView = inflater.inflate(R.layout.fragment_items, container, false);
            buttonChoose = (Button)rootView.findViewById(R.id.buttonChoose);
            buttonUpload = (Button)rootView.findViewById(R.id.buttonUpload);
        country = getResources().getStringArray(R.array.array_country);



            edtname = (EditText)rootView.findViewById(R.id.edtname);
            edtprice=(EditText)rootView.findViewById(R.id.edtprice);
            edtqty=(EditText)rootView.findViewById(R.id.edtqty);
            edtdec=(EditText)rootView.findViewById(R.id.edtdec);
            edtinfo=(EditText)rootView.findViewById(R.id.edtinfo);


            imageView  = (ImageView)rootView.findViewById(R.id.imageView);

            //buttonChoose.setOnClickListener((View.OnClickListener) getActivity());
            //buttonUpload.setOnClickListener((View.OnClickListener) getActivity());
           buttonChoose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Log.v("************","//////////");

                    showFileChooser();
                }
            });
            buttonUpload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    uploadImage();
                }
            });
            //Getting the instance of Spinner and applying OnItemSelectedListener on it
            Spinner spin = (Spinner)rootView.findViewById(R.id.spinner1);
            spin.setOnItemSelectedListener(this);
        Arrays.sort(country);
            ArrayAdapter aa = new ArrayAdapter(getActivity(),R.layout.spinner_item,country);
            aa.setDropDownViewResource(R.layout.spinner_item);
            //Setting the ArrayAdapter data on the Spinner
            spin.setAdapter(aa);




            spin.setAdapter(
                    new NothingSelectedSpinnerAdapter(aa, R.layout.contact_spinner_row_nothing_selected,getActivity()));


            //change R.layout.yourlayoutfilename for each of your fragments
            return rootView;
            //return inflater.inflate(R.layout.fragment_items, container, false);
    }
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
        //((Spinner) arg0.getChildAt(0)).setTextColor(Color.BLUE);


        // On selecting a spinner item
        Log.v("loggggggggggg", String.valueOf(position));

        if(position==0)
        {
            co="";// Toast.makeText(getApplicationContext(),"choose", Toast.LENGTH_LONG).show();
        }
        else
        {
            label = country[position-1].toString();
            co=label;
            Toast.makeText(getActivity(),label, Toast.LENGTH_LONG).show();
        }

    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       // getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try
            {
                //Getting the Bitmap from Gallery
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);
                //Setting the Bitmap to ImageView
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public String getStringImage(Bitmap bmp)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }


    private void uploadImage() {
        //Showing the progress dialog

        if (edtname.getText().toString().equals("")) {
            Toast.makeText(getActivity(),"Fill Up all detail",Toast.LENGTH_SHORT).show();

        } else if (edtprice.getText().toString().equals("")) {
            Toast.makeText(getActivity(),"Fill Up all detail",Toast.LENGTH_SHORT).show();

        } else if (edtdec.getText().toString().equals("")) {
            Toast.makeText(getActivity(),"Fill Up all detail",Toast.LENGTH_SHORT).show();

        } else if (edtinfo.getText().toString().equals("")) {
            Toast.makeText(getActivity(),"Fill Up all detail",Toast.LENGTH_SHORT).show();

        } else if (edtqty.getText().toString().equals("")) {
            Toast.makeText(getActivity(),"Fill Up all detail",Toast.LENGTH_SHORT).show();

        }
        else if (co.equals(""))
        {
            Toast.makeText(getActivity(),"Fill Up all detail",Toast.LENGTH_SHORT).show();
        }
        else {
            final ProgressDialog loading = ProgressDialog.show(getActivity(), "Uploading...", "Please wait...", false, false);
            //else if(country[position-1].toString().t)

            // else if ()
            StringRequest stringRequest = new StringRequest(Request.Method.POST, UPLOAD_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String s) {
                            //Disimissing the progress dialog
                            loading.dismiss();
                            //Showing toast message of the response
                            Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show();


                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            //Dismissing the progress dialog
                            loading.dismiss();
                            try {


                                //Showing toast
                                Toast.makeText(getActivity(), volleyError.getMessage().toString(), Toast.LENGTH_LONG).show();
                            } catch (Exception e) {
                                Toast.makeText(getActivity(), "Successfully Uploaded   1", Toast.LENGTH_LONG).show();
                            }
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    //Converting Bitmap to String
                    String image = getStringImage(bitmap);

                    //Getting Image Name
                    String name = edtname.getText().toString().trim();

                    String price = edtprice.getText().toString().trim();
                    String quantity = edtqty.getText().toString().trim();
                    String description = edtdec.getText().toString().trim();
                    String aboutus = edtinfo.getText().toString().trim();
                    String category = label.trim();
               /* String seller="3";
                String  seller_id=seller.trim();*/
                    SharedPreferences prefs = getActivity().getSharedPreferences("Login", MODE_PRIVATE);
                    String idName = prefs.getString("id", "");
                    String type = prefs.getString("type", "");
                    //Creating parameters
                    Map<String, String> params = new Hashtable<String, String>();

                    //Adding parameters
                    params.put(KEY_IMAGE, image);
                    params.put(KEY_NAME, name);
                    params.put(KEY_PRICE, price);
                    params.put(KEY_QUANTITY, quantity);
                    params.put(KEY_DESCRIPTION, description);
                    params.put(KEY_ABOUTUS, aboutus);
                    params.put(KEY_CATEGORY, category);
                    params.put(KEY_SELLERID, idName);

                    //returning parameters
                    return params;
                }

            };

            //Creating a Request Queue
            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

            //Adding request to the queue
            requestQueue.add(stringRequest);
        }
    }

    public void onClick(View v)
    {

        switch (v.getId())
        {
            case R.id.buttonChoose:
                showFileChooser();
                break;

            case R.id.buttonUpload:
                uploadImage();
                break;

        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Add Item");
    }
}
