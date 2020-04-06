package com.example.hp.navigation;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by priyank on 23-Jul-17.
 */

public class forgot extends AppCompatActivity {

    EditText edt_pass,confirm_pass;
    Button btnchangepass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgotpassword);
        Window window = getWindow();
        window.setStatusBarColor(Color.GRAY);

        edt_pass = (EditText) findViewById(R.id.edt_pass);
        confirm_pass = (EditText) findViewById(R.id.confirm_pass);

        btnchangepass = (Button) findViewById(R.id.btnchangepass);


        btnchangepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String p = edt_pass.getText().toString();
                String c = confirm_pass.getText().toString();


                if (p.equals("")) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Please Enter password", Toast.LENGTH_SHORT);
                    toast.show();
                } else if (c.equals("")) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Re-enter password", Toast.LENGTH_SHORT);
                    toast.show();
                } else if (p.equals(c)) {
                    String method = "forgot";
                    String u_name=getIntent().getStringExtra("user");
                    System.out.println("username"+u_name);
                    BackgroundTask3 backgroundTask = new BackgroundTask3(forgot.this);
                    backgroundTask.execute(method,u_name,p);

                }  else {
                    Toast toast = Toast.makeText(getApplicationContext(), "Password do not match", Toast.LENGTH_SHORT);
                    toast.show();
                }

            }


        });
        finish();
    }
}
