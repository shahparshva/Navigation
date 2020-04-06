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

/**
 * Created by om1 on 07-Nov-16.
 */

public class BackgroundTask extends AsyncTask<String,Void,String> {
    AlertDialog alertDialog;
    Context ctx;
    Activity a;
    String user_name;
    String method;
    ProgressDialog mProgressDialog;
    BackgroundTask(Context ctx, Activity a)
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
        String reg_url="https://parshva.000webhostapp.com/webapp2/webapp2/reg_test1.php";
        //String login_url="http://10.0.2.2/webapp2/login.php";
        String login_url="https://parshva.000webhostapp.com/webapp2/webapp2/login.php";
        method=params[0];

        if(method.equals("register"))
        {
             user_name=params[1];
            String user_pass=params[2];
            String name=params[3];
            String mobile_no=params[4];


            try {
                URL url=new URL(reg_url);
                HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                OutputStream OS=httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(OS,"UTF-8"));
                String data=
                        URLEncoder.encode("user_name","UTF-8") +"="+ URLEncoder.encode(user_name,"UTF-8") +"&"+
                        URLEncoder.encode("user_pass","UTF-8") +"="+ URLEncoder.encode(user_pass,"UTF-8") +"&"+
                        URLEncoder.encode("name","UTF-8") +"="+ URLEncoder.encode(name,"UTF-8") +"&"+
                        URLEncoder.encode("mobile_no","UTF-8") +"="+ URLEncoder.encode(mobile_no,"UTF-8");


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
        else if (method.equals("login"))
        {
            String login_name = params[1];
            String login_pass = params[2];
            try {
                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoOutput(true);
                OutputStream outputStream=httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String data= URLEncoder.encode("login_name","UTF-8")+"="+ URLEncoder.encode(login_name,"UTF-8")+"&"+
                        URLEncoder.encode("login_pass","UTF-8")+"="+ URLEncoder.encode(login_pass,"UTF-8");
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
        if(result.equals("regristratation succes...."))
        {

          Toast.makeText(ctx, result, Toast.LENGTH_LONG).show();
            SharedPreferences.Editor editor = ctx.getSharedPreferences("MAil", MODE_PRIVATE).edit();
            editor.putBoolean("mail",true);
            editor.apply();
            editor.commit();
            /*SendMail sendMail=new SendMail(ctx,user_name,"registratation Success","welcome to alang bazaar");
            sendMail.execute();
*/
        }
        else if(result.equals(" User Already Exist!!!"))
        {
            Toast.makeText(ctx, result, Toast.LENGTH_LONG).show();
        }
        else if(result.equals("login failed try again"))
        {

            Toast.makeText(ctx, result, Toast.LENGTH_LONG).show();
            Intent intent = new Intent(ctx, Login.class);
            ctx.startActivity(intent);
            a.finish();
        }
        else
        {
            //Toast.makeText(ctx, result, Toast.LENGTH_LONG).show();
            Log.v("responc+++++++++",result);
            int i=0;
            String test=result;
            while (!(test.charAt(i)==','))
            {
                i++;
            }
            String type=test.substring(0,i);

            String id=test.substring(i+1,test.length());
            Log.v("iddddddddddddddddddddd",id);
            Log.v("type=====",type);
           System.out.println("id============="+id);
            if(method.equals("login")){
                SharedPreferences.Editor editor = ctx.getSharedPreferences("Login", MODE_PRIVATE).edit();
                editor.putString("id",id);
                editor.putString("type",type);
                editor.putBoolean("login",true);
                editor.apply();
                editor.commit();
            }
            else{
                SharedPreferences.Editor editor1 = ctx.getSharedPreferences("googlelogin", MODE_PRIVATE).edit();
                editor1.putBoolean("google",false);
                editor1.apply();
                editor1.commit();

            }

            Intent intent = new Intent(ctx, MainActivity.class);
            ctx.startActivity(intent);
            a.finish();



        }

    }

}


