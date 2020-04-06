package com.example.hp.navigation;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hp.navigation.mail.SendMail;

import static com.example.hp.navigation.R.id.user_name;

/**
 * Created by om1 on 07-Nov-16.
 */
public class register extends AppCompatActivity {
    EditText et_name, et_user_Name, et_user_pass, mob_no,conform_pass;
    Button btnregister;

    String name, user_Name, user_pass, mobile_no;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        Window window = getWindow();

        window.setStatusBarColor(Color.GRAY);
        et_user_Name = (EditText) findViewById(R.id.new_user_name);
        et_user_pass = (EditText) findViewById(R.id.new_user_pass);
        et_name = (EditText) findViewById(R.id.name);
        mob_no = (EditText) findViewById(R.id.mob_no);
        conform_pass=(EditText)findViewById(R.id.new_user_pass_conform);
        btnregister = (Button) findViewById(R.id.btnregister);

    }

    public void userReg(View view) {
        user_Name = et_user_Name.getText().toString();
        user_pass = et_user_pass.getText().toString();
        name = et_name.getText().toString();
        mobile_no = mob_no.getText().toString();


        String e = et_user_Name.getText().toString();
        String p = et_user_pass.getText().toString();
        String n = et_name.getText().toString();
        String m = mob_no.getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        String MobilePattern = "[0-9]{10}";



               /* Pattern match for email id
                Pattern pattern = Pattern.compile(Utils.regEx);
                Matcher matcher = pattern.matcher(e); */


        if (e.equals("")) {
            Toast toast = Toast.makeText(getApplicationContext(), "Please Enter Username", Toast.LENGTH_SHORT);
            toast.show();

        } else if (!et_user_Name.getText().toString().matches(emailPattern)) {

            Toast.makeText(getApplicationContext(), "Please Enter Valid Email Address", Toast.LENGTH_SHORT).show();
            //  return false;
        } else if (p.equals("")) {
            Toast toast = Toast.makeText(getApplicationContext(), "Enter Password", Toast.LENGTH_SHORT);
            toast.show();
        } else if (n.equals("")) {
            Toast toast = Toast.makeText(getApplicationContext(), "Enter Full name", Toast.LENGTH_SHORT);
            toast.show();
        } else if (m.equals("")) {
            Toast toast = Toast.makeText(getApplicationContext(), "Please Enter Mobile no", Toast.LENGTH_SHORT);
            toast.show();
        } else if (!mob_no.getText().toString().matches(MobilePattern)) {

            Toast.makeText(getApplicationContext(), "Please enter valid 10 digit phone number", Toast.LENGTH_SHORT).show();
            //  return false;
        }
        else if(conform_pass.getText().toString().equals(""))
        {
            Toast.makeText(getApplicationContext(), "Re-enter password", Toast.LENGTH_SHORT).show();
        }
        else if(!conform_pass.getText().toString().equals(p))
        {

            Toast.makeText(getApplicationContext(), "Password Does not match", Toast.LENGTH_SHORT).show();

        }
        else{

            /*Toast.makeText(getApplicationContext(), "Password  match", Toast.LENGTH_SHORT).show();*/
            String method = "register";
            BackgroundTask backgroundTask = new BackgroundTask(register.this,register.this);
            backgroundTask.execute(method, user_Name, user_pass, name, mobile_no);
            SharedPreferences preferences=getSharedPreferences("Mail",MODE_PRIVATE);
            Boolean x=preferences.getBoolean("mail",false);
            if(x=true)
            {
                SendMail sendMail=new SendMail(this,e,"registratation Success".trim(),"welcome to alang bazaar".trim());
                sendMail.execute();
            }
            finish();

        }
    }






}

