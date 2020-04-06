package com.example.hp.navigation;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;


/**
 * Created by om1 on 07-Nov-16.
 */

public class BackgroundTask_addtocart extends AsyncTask<String,Void,String> {
    AlertDialog alertDialog;
    Context ctx;
    catagory_adapter_second c;
    public BackgroundTask_addtocart(Context ctx)
    {

        this.ctx=ctx;
    }
    public BackgroundTask_addtocart(catagory_adapter_second customListAdapter)
    {

        this.c=customListAdapter;
    }


    @Override
    protected void onPreExecute() {



    }
    @Override
    protected String doInBackground(String... params) {
        String reg_url="https://parshva.000webhostapp.com/new/insert.php";
        String method=params[0];
        if(method.equals("register"))
        {
            String name=params[1];
            String imgurl=params[2];
            String price=params[3];
            String quantity= params[4];
            //Log.v("----------------------","");
           // System.out.println("************** quantity"+quantity);
            String detail=params[5];
            String about=params[6];
                       try {
                URL url=new URL(reg_url);
                HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                OutputStream OS=httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(OS,"UTF-8"));
                String data= URLEncoder.encode("title","UTF-8") +"="+ URLEncoder.encode(name,"UTF-8") +"&"+
                        URLEncoder.encode("image","UTF-8") +"="+ URLEncoder.encode(imgurl,"UTF-8") +"&"+
                        URLEncoder.encode("rating","UTF-8") +"="+ URLEncoder.encode(price,"UTF-8") +"&"+
                        URLEncoder.encode("release","UTF-8") +"="+ URLEncoder.encode(quantity,"UTF-8") +"&"+
                        URLEncoder.encode("about","UTF-8") +"="+ URLEncoder.encode(detail,"UTF-8")+"&"+
                        URLEncoder.encode("detail","UTF-8") +"="+ URLEncoder.encode(about,"UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();
                InputStream IS=httpURLConnection.getInputStream();
                IS.close();
                return "Item Added to cart....";
            }
            catch (MalformedURLException e)
            {
                e.printStackTrace();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

        }
        return null;
    }
    @Override
    protected void onProgressUpdate(Void... values)
    {
        super.onProgressUpdate(values);

    }
    @Override
    protected void onPostExecute(String result) {

        if (result.equals("Item Added to cart...."))
        {

         //Toast.makeText(,"Item Added To Cart",Toast.LENGTH_SHORT).show();
            Toast.makeText(ctx,"Item Added To cart ...", Toast.LENGTH_SHORT).show();
        }
        else
        {
            alertDialog.setMessage(result);
            alertDialog.show();
        }

    }

}


