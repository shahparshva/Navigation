package com.example.hp.navigation;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.hp.navigation.mail.SendMail;

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
import static com.example.hp.navigation.R.id.user_name;

/**
 * Created by om1 on 07-Nov-16.
 */

public class GoogleBackgroundTask extends AsyncTask<String,Void,String> {
    AlertDialog alertDialog;
    Context ctx;
    String login_name;
    GoogleBackgroundTask(Context ctx)
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
       //String reg_url="http://10.0.2.2/webapp2/reg_test1.php";
        String login_url="https://parshva.000webhostapp.com/webapp2/webapp2/login_google.php";
        String method=params[0];


        if (method.equals("google_login"))
        {
             login_name = params[1];
            String name = params[2];
            String imageurl=params[3];

            try {
                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoOutput(true);
                OutputStream outputStream=httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String data=URLEncoder.encode("login_name","UTF-8")+"="+URLEncoder.encode(login_name,"UTF-8")+"&"+
                        URLEncoder.encode("name","UTF-8")+"="+URLEncoder.encode(name,"UTF-8")+"&"+
                        URLEncoder.encode("imageurl","UTF-8")+"="+URLEncoder.encode(imageurl,"UTF-8");
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
                return responce;

            }
            catch (MalformedURLException e)
            {

            } catch (IOException e) {
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


         if(result.equals("User already exist"))
        {
            Toast.makeText(ctx, result, Toast.LENGTH_LONG).show();
        }
        else
        {
            Log.v("responc+++++++++",result);
            int i=0;
            String test=result;
            while (!(test.charAt(i)==','))
            {
                i++;
            }
            String mail=test.substring(0,1);
            Log.v("**************",mail);
            if(mail.equals("1"))
            {
                SendMail sendMail=new SendMail(ctx,login_name,"registratation Success","Welcome To Alang Bazaar....\n" +
                        "\n" +
                        "\n" +
                        "\tYou Are Successfully Register\n" +
                        "\t\n" +
                        "\t\n" +
                        "thank you for visiting Alang Bazaar ");
                sendMail.execute();
            }
            String type=test.substring(1,i);

            String id=test.substring(i+1,test.length());
            Log.v("iddddddddddddddddddddd",id);
            Log.v("type=====",type);
            System.out.println("id============="+id);
            SharedPreferences.Editor editor = ctx.getSharedPreferences("Login", MODE_PRIVATE).edit();
            editor.putString("id",id);
            editor.putString("type",type);
            editor.apply();
            editor.commit();
            SharedPreferences.Editor editor1 = ctx.getSharedPreferences("googlelogin", MODE_PRIVATE).edit();
            editor1.putBoolean("google",true);
            editor1.apply();
            editor1.commit();

           /* Intent intent = new Intent(ctx, MainActivity.class);
            ctx.startActivity(intent);*/

            /*SharedPreferences.Editor editor = ctx.getSharedPreferences("Login", MODE_PRIVATE).edit();
            editor.putString("name", ""+result);
            editor.putString("type", "seller");
            editor.apply();
            editor.commit();
            alertDialog.setMessage(result);
            alertDialog.show();*/
            //Intent intent = new Intent(ctx, Dummy.class);
           // ctx.startActivity(intent);
        }


    }

}


