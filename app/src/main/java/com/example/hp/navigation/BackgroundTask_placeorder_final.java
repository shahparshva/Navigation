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

public class BackgroundTask_placeorder_final extends AsyncTask<String,Void,String> {
    AlertDialog alertDialog;
    Context ctx;
    Activity a;
    String method;
    ProgressDialog mProgressDialog;
    BackgroundTask_placeorder_final(Context ctx, Activity a)
    {

        this.ctx=ctx;
        this.a = a;
        mProgressDialog = new ProgressDialog(ctx);
        mProgressDialog.setMessage(a.getString(R.string.loading));
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
       // String reg_url="https://parshva.000webhostapp.com/webapp2/webapp2/reg_test1.php";
        //String login_url="http://10.0.2.2/webapp2/login.php";
        String login_url="https://parshva.000webhostapp.com/new/order.php";



            String price = params[0];
            String user_id = params[1];
            try {
                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoOutput(true);
                OutputStream outputStream=httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String data= URLEncoder.encode("price","UTF-8")+"="+ URLEncoder.encode(price,"UTF-8")+"&"+
                        URLEncoder.encode("user_id","UTF-8")+"="+ URLEncoder.encode(user_id,"UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream=httpURLConnection.getInputStream();
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String responce="";
                String line="";
                while ((line =bufferedReader.readLine())!=null)
                {
                    responce= line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                //Log.v("responce++++",responce);
                return responce;


            }
            catch (MalformedURLException e)
            {

            } catch (IOException e) {
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


            Toast.makeText(ctx,"Order succesfully placed", Toast.LENGTH_LONG).show();
        SharedPreferences.Editor editor = ctx.getSharedPreferences("order_id", MODE_PRIVATE).edit();
        editor.putString("order_id_for_mail",result);
        editor.apply();
        editor.commit();

    }
}


