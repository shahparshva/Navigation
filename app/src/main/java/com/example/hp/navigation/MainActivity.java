package com.example.hp.navigation;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import Fragment.about;
import Fragment.home;
import Fragment.homes;
import Fragment.items;
import Fragment.profile;
import Fragment.shoping;
import Fragment.view_item_seller;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener  {
    private ProgressDialog pDialog;
    private GoogleApiClient mGoogleApiClient;
    FragmentManager fragmentManager;
    public String type="";
    home buyerhome;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        pDialog = new ProgressDialog(MainActivity.this);
        fragmentManager = getSupportFragmentManager();
        pDialog.setCancelable(false);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        SharedPreferences prefs = getSharedPreferences("Login", MODE_PRIVATE);
        String idName = prefs.getString("id", "");
        type = prefs.getString("type", "");



        if (type.equals("seller")) {
            // hidePDialog();

            navigationView.inflateMenu(R.menu.activity_seller);
            Log.v("#######", "@@@@@@@");
            homes homes = new homes();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, homes);
            ft.addToBackStack(null);
            ft.commit();

        } else {
            // pDialog.hide();
            navigationView.inflateMenu(R.menu.activity_main_drawer);
            Log.v("#######", "$$$$$$$$$$");
            home homes = new home();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, homes);
            ft.addToBackStack(null);
            ft.commit();
            System.out.println("type++++++++++++++++" + idName);

        }
        pDialog.hide();

        //add this line to display menu1 when the activity is loaded
        //displaySelectedScreen(nvs_home);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            //if(fragmentManager.findFragmentByTag("homes").equals("homes")){

                fragmentManager.popBackStack("home",FragmentManager.POP_BACK_STACK_INCLUSIVE);
           // }
           /* if(fragmentManager.findFragmentByTag("home").equals("home")){
                fragmentManager.popBackStack("homes",FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }*/
            super.onBackPressed();
        }
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem menuItem = menu.findItem(R.id.action_addtocart);
        menuItem.setIcon(R.drawable.ca);
        Drawable newIcon = (Drawable)menuItem.getIcon();
        newIcon.mutate().setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_IN);
        if(type.equals("seller")) {

            menuItem.setVisible(false);
        }
        else
        {
            menuItem.setVisible(true);
        }
        /*MenuItem menuItem1 = menu.findItem(R.id.action_settings);
        menuItem1.setVisible(false);
*/
        // return false;
        return super.onPrepareOptionsMenu(menu);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_addtocart) {
            Fragment fragment = new cart_display();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.addToBackStack(null);
            ft.commit();
            return true;
        }
        /*if (id==R.id.action_settings)
        {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    private void displaySelectedScreen(int itemId) {

        //creating fragment object
        Fragment fragment = null;

        //initializing the fragment object which is selected
        Log.v("item id...", String.valueOf(itemId));
        switch (itemId) {

            case R.id.nv_home:
                fragment = new home();
                break;
            case R.id.nv_profile:
                fragment = new profile();
                break;
            case R.id.nv_shoping:
                fragment = new shoping();
                break;
            case R.id.nv_about:
                fragment = new about();
                break;
            //seller***********
            case R.id.nvs_profile:
                // fragment = new profile();
                fragment = new profile();
                break;
            case R.id.nvs_home:

                fragment = new homes();
                break;
            case R.id.nvs_item:
                // fragment = new profile();
                fragment = new items();
                break;
            case R.id.view_item_seller:
                fragment=new view_item_seller();
                break;
            //case R.id.nvs_logout:
            // fragment = new profile();

            case R.id.nv_logout:
                pDialog = new ProgressDialog(MainActivity.this);
                // Showing progress dialog before making http request
              /*  pDialog.setMessage("Loading...");
                pDialog.show();
*/
                SharedPreferences prefs1 = getSharedPreferences("Login", MODE_PRIVATE);
                Boolean flag = prefs1.getBoolean("google", Boolean.parseBoolean(""));
                SharedPreferences.Editor editor = this.getSharedPreferences("Login", MODE_PRIVATE).edit();
                editor.putString("id", "");
                editor.putString("type", "");
                editor.apply();
                editor.commit();
                if (flag = true) {

                    Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                            new ResultCallback<Status>() {
                                @Override
                                public void onResult(Status status) {
                                    // ...
                                    Toast.makeText(getApplicationContext(), "Logged Out", Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(getApplicationContext(), Login.class);
                                    startActivity(i);
                                }
                            });

                } else {
                    Intent i = new Intent(this, Login.class);
                    Toast.makeText(this, "Logout Succesfully", Toast.LENGTH_SHORT).show();
                    startActivity(i);
                    break;

                }
                hidePDialog();
                finish();

        }


        //replacing the fragment
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.addToBackStack(null);
            ft.commit();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    @Override
    protected void onStart() {
        super.onStart();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        mGoogleApiClient.connect();
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        //calling the method displayselectedscreen and passing the id of selected menu
        displaySelectedScreen(item.getItemId());
        //make this method blank
        return true;
    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }


}