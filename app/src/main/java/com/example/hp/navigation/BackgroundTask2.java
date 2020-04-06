package com.example.hp.navigation;

import android.app.AlertDialog;
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
 * Created by priyank on 23-Jul-17.
 */

public class BackgroundTask2 extends AsyncTask<String,Void,String> {
    private AlertDialog alertDialog;
    private Context ctx;
    private Context applicationContext;
    public String user_name;
    BackgroundTask2(Context ctx)
    {

        this.ctx=ctx;
    }
    @Override
    protected void onPreExecute() {

        alertDialog=new AlertDialog.Builder(ctx).create();
        alertDialog.setTitle("login information");

    }
    @Override
    protected String doInBackground(String... params) {
        String forgot_url="https://parshva.000webhostapp.com/webapp2/webapp2/forgot.php";
        //String forgot_url="http://10.0.2.2/webapp2/forgot.php";

        String method=params[0];
        if (method.equals("forgot1"))
        {
            user_name = params[1];
            Log.v("user_name******",user_name);
            SharedPreferences.Editor editor = ctx.getSharedPreferences("user_forgot", MODE_PRIVATE).edit();
            editor.putString("user_name",user_name);
            editor.apply();
            editor.commit();
           /* Log.v("logint name",user_name);
            Log.v("username",user_name);*/
            try {
                URL url = new URL(forgot_url);
                HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoOutput(true);
                OutputStream outputStream=httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String data= URLEncoder.encode("user_name","UTF-8")+"="+ URLEncoder.encode(user_name,"UTF-8");
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
                Log.v("responce",responce);
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

        //Log.v("Username",result);

        if (result.equals("User not found")) {
            Toast.makeText(ctx, result, Toast.LENGTH_LONG).show();

        }
        else if((result.equals("User found")))

        {

            Intent i=new Intent(this.ctx, forgot.class);
            System.out.println("user_name"+user_name);
            i.putExtra("user",user_name);
            ctx.startActivity(i);
            /*alertDialog.setMessage(result);
            alertDialog.show();*/

        }
        else
        {
            Toast.makeText(ctx,"abc", Toast.LENGTH_LONG).show();
        }


    }

}


