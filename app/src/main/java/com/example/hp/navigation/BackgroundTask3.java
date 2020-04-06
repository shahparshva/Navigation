package com.example.hp.navigation;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
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

/**
 * Created by priyank on 25-Jul-17.
 */

 class BackgroundTask3 extends AsyncTask<String,Void,String> {
    AlertDialog alertDialog;
    Context ctx;

    BackgroundTask3(Context ctx) {

        this.ctx = ctx;
    }

    @Override
    protected void onPreExecute() {

        alertDialog = new AlertDialog.Builder(ctx).create();
        alertDialog.setTitle("login information");

    }

    @Override
    protected String doInBackground(String... params) {

        //String f_url = "http://10.0.2.2/webapp2/changepass.php";
        String f_url = "https://parshva.000webhostapp.com/webapp2/webapp2/changepass.php";
        String method = params[0];

        if (method.equals("forgot")) {



            String user_name=params[1];
            String user_pass = params[2];
            Log.v("user name",user_name);
            Log.v("passw0rd",user_pass);

            try {
                URL url = new URL(f_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                OutputStream OS = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));

                String data = URLEncoder.encode("user_name", "UTF-8") + "=" + URLEncoder.encode(user_name, "UTF-8")+"&"+
                        URLEncoder.encode("user_pass", "UTF-8") + "=" + URLEncoder.encode(user_pass, "UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();
                InputStream IS = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(IS, "iso-8859-1"));
                String responce = "";
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    responce = line;
                }
                bufferedReader.close();
                IS.close();
                httpURLConnection.disconnect();
                return responce;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
            return "updated success..";

    }


        @Override
        protected void onProgressUpdate (Void...values)
        {
            super.onProgressUpdate(values);

        }
        @Override
        protected void onPostExecute (String result){

            if (result.equals("updated success.."))
            {

                Toast.makeText(ctx, result, Toast.LENGTH_LONG).show();
                Intent i=new Intent(ctx,Login.class);
                ctx.startActivity(i);
            }
            else
            {

                alertDialog.setMessage(result);
                alertDialog.show();
               /* Intent intent = new Intent(ctx, Dummy.class);
                ctx.startActivity(intent);*/
            }


        }

    }

