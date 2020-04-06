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
 * Created by priyank on 25-Jul-17.
 */

public class forgot1 extends AppCompatActivity {

    EditText edt_email;
    Button btncontinue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot1);
        Window window = getWindow();
        window.setStatusBarColor(Color.GRAY);
        edt_email=(EditText)findViewById(R.id.edt_email);
        btncontinue=(Button)findViewById(R.id.btncontinue);

        btncontinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email=edt_email.getText().toString();

                if (email.equals(""))
                {
                    Toast toast = Toast.makeText(getApplicationContext(), "Enter Email", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else
                {
                    //Toast toast = Toast.makeText(getApplicationContext(), "Error aave che bhai", Toast.LENGTH_SHORT);
                   // toast.show();
                    String method="forgot1";
                    BackgroundTask2 backgroundTask=new BackgroundTask2(forgot1.this);
                    backgroundTask.execute(method,email);

                }
            }
        });


    }

}
