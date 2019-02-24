package com.example.tobeisun.bayo;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

public class Driversnavigationdrawer extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //this sets the first page forthe navigation drawer
        selectedDrawerItem(R.id.nav_map);
        MenuItem item = navigationView.getMenu().findItem(0);
        item.setChecked(true);
        setTitle(item.getTitle());

        //this makes sure that the app is always in portrait mode,even if you rotate your phone
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.test, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        selectedDrawerItem(id);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void selectedDrawerItem(int itemId) {
        Fragment fragment = null;
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        FragmentManager fm = getSupportFragmentManager();


        //first hide all the attached fragment
        List<Fragment> fragmentList = fm.getFragments();
        if (fragmentList.size() > 0) {
            for (Fragment f : fragmentList) {
                transaction.hide(f);
            }
        }


//        Class fragmentClass;

        switch (itemId) {
            case R.id.nav_map:


                //checks if the fragment has already been created before so it doesnt recreate
                fragment = fm.findFragmentByTag("DriversMap");

                //if it hasnt been created before, create it with "customersMapFragment" tag
                if (fragment == null) {
                    fragment = DriversMap.newInstance();
                    transaction.add(R.id.fl_content, fragment, "DriversMap");
                    transaction.commit();

                    // if it already exist, just get it from the fragment manager with the tag and show it
                } else {
                    transaction.show(getSupportFragmentManager().findFragmentByTag("DriversMap"));
                    transaction.commit();
                    fm.popBackStack();


                }
                break;

            case R.id.nav_gallery:
                fragment = fm.findFragmentByTag("aboutdriverFragment");

                //if it hasnt been created before, create it with "customersMapFragment" tag
                if (fragment == null) {
                    fragment = new aboutdriver();
                    transaction.add(R.id.fl_content, fragment, "aboutdriverFragment");
                    transaction.commit();

                    // if it already exist, just get it from the fragment manager with the tag and show it
                } else {
                    transaction.show(getSupportFragmentManager().findFragmentByTag("aboutdriverFragment"));
                    transaction.commit();
                    fm.popBackStack();


                }

                break;

            case R.id.nav_manage:

                setTitle("SECOND DRIVER FRAGMENT");
                Rides rides = new Rides();
                FragmentManager fragmentManager1= getSupportFragmentManager();

                fragmentManager1.beginTransaction().replace(R.id.fl_content, rides).commit();

                break;

        }
    }
}
