package com.example.hp.navigation;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;

import java.util.Objects;

import static android.R.attr.id;
import static android.R.attr.theme;

public class Login extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {

    TextView txtforgot;
    EditText ET_NAME, ET_PASS;
    String login_name, login_pass;
    CheckBox show_hide_password;
    private static final String TAG = Login.class.getSimpleName();
    private static final int RC_SIGN_IN = 007;

    private GoogleApiClient mGoogleApiClient;
    private ProgressDialog mProgressDialog;

    private SignInButton btnSignIn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {


            setContentView(R.layout.login);
        }
        catch (Exception e)
        {

        }
        Window window = getWindow();
        window.setStatusBarColor(Color.GRAY);
        ET_NAME = (EditText) findViewById(R.id.user_name);
        ET_PASS = (EditText) findViewById(R.id.user_pass);
        txtforgot = (TextView) findViewById(R.id.txtforgot);
        // show_hide_password = (CheckBox) findViewById(R.id.show_hide_password);
        btnSignIn = (SignInButton) findViewById(R.id.btn_sign_in);

        txtforgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), forgot1.class);
                startActivity(i);
            }
        });

        //*************DS***************************************************
        /*SharedPreferences prefs = getSharedPreferences("Login", MODE_PRIVATE);
        String id,type;
        Log.v("type of user",prefs.getString("type",""));
        Log.v("type of user",prefs.getString("id",""));
        id= prefs.getString("id","");
        System.out.println("already have id"+id);
        //System.out.println("id in integer"+Integer.parseInt(id));
        SharedPreferences prefs1 = getSharedPreferences("Login", MODE_PRIVATE);
        Boolean flag = prefs1.getBoolean("google", Boolean.parseBoolean(""));

            if (!Objects.equals(id, "")) {

                Log.v("#####", "^^^^^^^^^");

//                Intent intent = new Intent(this, MainActivity.class);
//                startActivity(intent);

            }*/

//*************DS***************************************************
        btnSignIn.setVisibility(View.VISIBLE);

        btnSignIn.setOnClickListener(this);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        btnSignIn.setSize(SignInButton.SIZE_STANDARD);
        btnSignIn.setScopes(gso.getScopeArray());
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
        Log.v("intent occurs", "---------------");
    }

    public void userReg(View view) {

        startActivity(new Intent(this, register.class));
    }

    public void userLogin(View view) {
        String getEmailId = ET_NAME.getText().toString();

        /* Pattern match for email id
        Pattern p = Pattern.compile(Utils.regEx);
        Matcher m = p.matcher(getEmailId);*/
        if (ET_NAME.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "please enter user name", Toast.LENGTH_LONG).show();
        }
       /* else if(!m.find())
        {
            Toast.makeText(getApplicationContext(),"Your Email-id is invalid",Toast.LENGTH_LONG).show();
        }*/
        else if (ET_PASS.getEditableText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "please enter password", Toast.LENGTH_LONG).show();
        } else {

            login_name = ET_NAME.getText().toString();
            login_pass = ET_PASS.getText().toString();
            String method = "login";
            // ET_NAME.setText("");
            // ET_PASS.setText("");
            // Intent i=new Intent(this,abc.class);
            try {


            showProgressDialog();}
            catch (Exception e)
            {

            }
            BackgroundTask backgroundTask = new BackgroundTask(this, Login.this);
            backgroundTask.execute(method, login_name, login_pass);

        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();

            Log.e(TAG, "display name: " + acct.getDisplayName());
            //Log.e(TAG, "display account info: " + acct.getAccount());
            Log.e(TAG, "display account id: " + acct.getId());
            //  String x=acct.

            String personName = acct.getDisplayName();
            String imageurl;
            String email = acct.getEmail();
            try {
                imageurl = acct.getPhotoUrl().toString();
            } catch (Exception e) {
                imageurl = "";
            }

            String id = acct.getId();

            Log.e(TAG, "Name: " + personName + ", email: " + email
                    /*+ ", Image: " + personPhotoUrl*/);

            //     txtName.setText(personName);
            //          txtEmail.setText(email);
//            txtId.setText(id);

            String method = "google_login";
            GoogleBackgroundTask googleBackgroundTask = new GoogleBackgroundTask(Login.this);
            googleBackgroundTask.execute(method, email, personName, imageurl);


          /*  Glide.with(getApplicationContext()).load(personPhotoUrl)
                    .thumbnail(0.5f)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imgProfilePic);*/

            updateUI(true);
        } else {
            // Signed out, show unauthenticated UI.
            //  updateUI(false);
            Log.v("********************", "unsucessfull");
          //  Toast.makeText(getApplicationContext(),"Uncucessfull",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.btn_sign_in:
                signIn();
                break;
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
        if (opr.isDone()) {
            // If the user's cached credentials are valid, the OptionalPendingResult will be "done"
            // and the GoogleSignInResult will be available instantly.
            Log.d(TAG, "Got cached sign-in");
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
        } else {
            // If the user has not previously signed in on this device or the sign-in has expired,
            // this asynchronous branch will attempt to sign in the user silently.  Cross-device
            // single sign-on will occur in this branch.
//                    showProgressDialog();
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(GoogleSignInResult googleSignInResult) {
                    hideProgressDialog();
                    handleSignInResult(googleSignInResult);
                }
            });
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed:" + connectionResult);

    }

    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage("Loging in...");
            mProgressDialog.setIndeterminate(true);
        }
        try {
    //            mProgressDialog.show();
        }
        catch (Exception e)
        {

        }
    }

    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
        }
    }

    private void updateUI(boolean isSignedIn) {
        if (isSignedIn) {
            btnSignIn.setVisibility(View.GONE);
            //    btnSignOut.setVisibility(View.VISIBLE);
            Toast.makeText(getApplicationContext(), "Logged in", Toast.LENGTH_SHORT).show();
            //finish();
            //////DS/////////////////////////////////
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
            finish();
            //     btnRevokeAccess.setVisibility(View.VISIBLE);
            //     llProfileLayout.setVisibility(View.VISIBLE);
        } else {
            btnSignIn.setVisibility(View.VISIBLE);
            Toast.makeText(getApplicationContext(), "You are not logged in", Toast.LENGTH_SHORT).show();
            //  btnSignOut.setVisibility(View.GONE);
            //          btnRevokeAccess.setVisibility(View.GONE);
            //    llProfileLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    @Override
    protected void onDestroy() {
//        mProgressDialog.dismiss();
        super.onDestroy();
    }
   /* public void forgo(View view)
    {
        startActivity(new Intent(this,forgot1.class));
    }*/
}
