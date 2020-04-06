package com.example.hp.navigation;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
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

public class BackgroundTask_PlaceOrder extends AsyncTask<String,Void,String> {
    AlertDialog alertDialog;
    Context ctx;
    Activity a;
    String method;
    ProgressDialog mProgressDialog;
    BackgroundTask_PlaceOrder(Context ctx, Activity a)
    {

        this.ctx=ctx;
        this.a = a;
        mProgressDialog = new ProgressDialog(ctx);
        mProgressDialog.setMessage("");
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
        String reg_url="https://parshva.000webhostapp.com/order_info.php";
        //String login_url="http://10.0.2.2/webapp2/login.php";
        //String login_url="https://parshva.000webhostapp.com/webapp2/webapp2/login.php";
        method=params[0];

        if(method.equals("placeorder"))
        {
            String name=params[1];
            String mobno=params[2];
            String altermobno=params[3];
            String pincode=params[4];
            String town=params[5];
            String city=params[6];
            String state=params[7];
            String address=params[8];
            String user_id=params[9];

            try {
                URL url=new URL(reg_url);
                HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                OutputStream OS=httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(OS,"UTF-8"));
                String data= URLEncoder.encode("name","UTF-8") +"="+ URLEncoder.encode(name,"UTF-8") +"&"+
                             URLEncoder.encode("mobno","UTF-8") +"="+ URLEncoder.encode(mobno,"UTF-8") +"&"+
                             URLEncoder.encode("altermobno","UTF-8") +"="+ URLEncoder.encode(altermobno,"UTF-8") +"&"+
                             URLEncoder.encode("pincode","UTF-8") +"="+ URLEncoder.encode(pincode,"UTF-8") +"&"+
                             URLEncoder.encode("town","UTF-8") +"="+ URLEncoder.encode(town,"UTF-8")+"&"+
                             URLEncoder.encode("city","UTF-8") +"="+ URLEncoder.encode(city,"UTF-8")+"&"+
                             URLEncoder.encode("state","UTF-8") +"="+ URLEncoder.encode(state,"UTF-8")+"&"+
                             URLEncoder.encode("address","UTF-8") +"="+ URLEncoder.encode(address,"UTF-8")+"&"+
                             URLEncoder.encode("user_id","UTF-8") +"="+ URLEncoder.encode(user_id,"UTF-8");


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

        if(result.equals("regristratation succes...."))
        {
            //Intent intent = new Intent(ctx, placeorder.class);
            //ctx.startActivity(intent);
            place_order_2 f=new place_order_2();
           // Fragment fragment = new place_order_2();
            SharedPreferences.Editor editor = ctx.getSharedPreferences("order", MODE_PRIVATE).edit();
            editor.putInt("add",1);
            editor.apply();
            editor.commit();
          /* FragmentTransaction ft;
            ((FragmentActivity)ctx).getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,f).commit();
            ft = ((FragmentActivity)ctx).getSupportFragmentManager().beginTransaction();
          ft.addToBackStack(null);*/
        }
        else
        {
            Toast.makeText(ctx, result, Toast.LENGTH_LONG).show();

        }
    }

}


