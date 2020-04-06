package com.example.hp.navigation;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by om1 on 07-Nov-16.
 */

public class BackgroundTask_cart_max extends AsyncTask<String,Void,String> {
    AlertDialog alertDialog;
    Context ctx;
    Activity a;
    String method;
    ProgressDialog mProgressDialog;
    BackgroundTask_cart_max(Context ctx)
    {

        this.ctx=ctx;
        mProgressDialog = new ProgressDialog(ctx);
//        mProgressDialog.setMessage(a.getString(R.string.loading));
        mProgressDialog.setIndeterminate(true);
    }
    @Override
    protected void onPreExecute() {

        alertDialog=new AlertDialog.Builder(ctx).create();
        alertDialog.setTitle("login information");

    }
    @Override
    protected String doInBackground(String... params) {
        //String reg_url="http://10.0.2.2/webapp2/reg_test1.php";
        String reg_url="https://parshva.000webhostapp.com/webapp2/webapp2/cart_check_qty.php";
        //String login_url="http://10.0.2.2/webapp2/login.php";
        method=params[0];
            String item_id=params[1];



            try {
                URL url=new URL(reg_url);
                HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                OutputStream OS=httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(OS,"UTF-8"));
                String data=
                        URLEncoder.encode("item_id","UTF-8") +"="+ URLEncoder.encode(item_id,"UTF-8");


                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();
              InputStream IS=httpURLConnection.getInputStream();
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(IS,"iso-8859-1"));
                String responce="";
                String line="";
                while ((line =bufferedReader.readLine())!=null)
                {
                    responce= line;
                }
                bufferedReader.close();
                IS.close();
                httpURLConnection.disconnect();

                return responce;
            }
            catch (MalformedURLException e)
            {
                e.printStackTrace();
            }
            catch (IOException e)
            {
                e.printStackTrace();
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

        mProgressDialog.dismiss();
       // Log.v("reslut................++++++++",result);
        if(result.equals(""))
        {

          //Toast.makeText(ctx, result, Toast.LENGTH_LONG).show();

        }
        else if(result.equals("User already exist!!!"))
        {
            Toast.makeText(ctx, result, Toast.LENGTH_LONG).show();
        }

    }

}


