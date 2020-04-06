package com.example.hp.navigation.mail;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.hp.navigation.AppController;
import com.example.hp.navigation.MainActivity;
import com.example.hp.navigation.R;
import com.example.hp.navigation.cart_blank;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import modle.catagory_second_modle;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Parshva on 15-Aug-17.
 */

public class SendMail2 extends AsyncTask<Void,Void,Void> {
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String url ="https://parshva.000webhostapp.com/new/mail_json.php";
    private ProgressDialog pDialog;
    private StringBuffer body
            = new StringBuffer("<html>"+".<br>");
    StringBuffer body1=new StringBuffer();
    //Declaring Variables
    private Context context;
    private Session session;
    private int i=0;

    //Information to send email
    private String email;
    private String subject;
    private String message;

    //Progressdialog to show while sending email
    private ProgressDialog progressDialog;

    //Class Constructor
    public SendMail2(Context context, String email, String subject, String message){
        //Initializing variables
        this.context = context;
        this.email = email;
        this.subject = subject;
        this.message = message;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //Showing progress dialog while sending email
// progressDialog = ProgressDialog.show(context,"Sending message","Please wait...",false,false);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        //Dismissing the progress dialog
//        progressDialog.dismiss();
        //Showing a success message
        Toast.makeText(context,"Message Sent", Toast.LENGTH_LONG).show();
    }

    @Override
    protected Void doInBackground(Void... params) {
        //Creating properties
        Properties props = new Properties();

        //Configuring properties for gmail
        //If you are not using gmail you may need to change the values
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        //Creating a new session
        session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    //Authenticating the password
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(Config.EMAIL, Config.PASSWORD);
                    }
                });

        try {
            //Creating MimeMessage object
            MimeMessage mm = new MimeMessage(session);

            //Setting sender address
            mm.setFrom(new InternetAddress(Config.EMAIL));
            //Adding receiver
            mm.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            //Adding subject
            mm.setSubject(subject);
            //Adding message
           mm.setText(message);





//        /*  pDialog = new ProgressDialog(context);
            // Showing progress dialog before making http request
           /* pDialog.setMessage("Loading...");
            pDialog.show();*/

            final String[] img = new String[100];
            final String[] qty = new String[100];
            final String[] price = new String[100];
            final String[] product_name = new String[100];

            body.append("<head>").append("<link rel=\"stylesheet\" type=\"text/css\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css\" >").append("</head>");
            body.append("<body>");
            body.append("<table border=\"2px\" align=\"center\">");


            body.append("<tr align=\"center\">"+"<td>").append("<H2>Order Details</H2>").append("</td>"+"</tr>");
           /* JsonArrayRequest movieReq = new JsonArrayRequest(url,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            Log.d(TAG, response.toString());

                               // pDialog.hide();
                            // Parsing json

                            for (int j = 0; j < response.length(); j++) {
                                try {

                                    JSONObject obj = response.getJSONObject(j);
                                    SharedPreferences prefs = context.getSharedPreferences("order_id", MODE_PRIVATE);
                                    String order_id = prefs.getString("order_id_for_mail", "");
                                    if(order_id.equals(obj.getString("order_id")))
                                    {

                                        Log.v("valueeeeeeeeeee", String.valueOf(i));
                                        Log.v("obbbbbbbbbb",obj.toString());
                                         img[i] =obj.getString("image");
                                        Log.v("image urllllllllllll", img[0]);
                                         qty[i] =obj.getString("quantity");
                                        price[i] =obj.getString("rating");

                                         product_name[i] =obj.getString("title");


                                        body.append("<img src=").append(img[i]).append(">");
                                        body.append("<H3>Product Name:").append(product_name[i]).append("</H3>");
                                        body.append("<H3>Quantity:").append(qty[i]).append("</H3>");
                                        body.append("<H3>Price:").append(price[i]).append("</H3>");
                                        body.append("<hr width=\"150px\">");
                                        i++;
                                        Log.v("bodyyyyyyyyyyyy",body.toString());
                                    }

                                    Log.v("bodyyyyyyyyyyyy22222",body.toString());


                                } catch (JSONException e) {

                                    e.printStackTrace();

                                }


                            }

                            // notifying list adapter about data changes
                            // so that it renders the list view with updated data


                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.d(TAG, "Error: " + error.getMessage());

                }

            });
            //adapter.notifyDataSetChanged();


            // Adding request to request queue
            AppController.getInstance().addToRequestQueue(movieReq);*/
            //returning our layout file
            //change R.layout.yourlayoutfilename for each of your fragments






            body.append(body1);
        //Sending email



            SharedPreferences prefs1 =context.getSharedPreferences("total", MODE_PRIVATE);
            final String price1 = String.valueOf(prefs1.getInt("totalp",0));

            body.append("<br>");
            body.append("<br>");
            body.append("<br>");

            body.append("<H2>Total Price</H2>");
            body.append("<H3>").append(price1+"</H3>");
            body.append("<br>");
           body.append("</table>");


            body.append("<H4>Thank you from Alang Bazaar team</H4>");
            body.append("<H2>Address</H2>"+message+"<br>");
            body.append("Contact us: alangbazaar.team@gmail.com");
            body.append("</body>");





            //body.append("<?php echo \"helllo\"?>");
            //String htmlText = "<H1>Hello</H1><img src=\"https://parshva.000webhostapp.com/2.jpg\">";
            body.append("</html>");

            /*String htmlText = "<H1>Hello</H1><img src=\"https://parshva.000webhostapp.com/2.jpg\">"*/
            //String htmlText = "<H1>Hello</H1><img src="+img+">";

            Log.v("body11111111111111111111",body.toString());
            mm.setContent(body.toString(), "text/html");



            Transport.send(mm);


        } catch (MessagingException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void execute(StringBuffer body1) {
        this.body1=body1;
        this.execute();
    }
}