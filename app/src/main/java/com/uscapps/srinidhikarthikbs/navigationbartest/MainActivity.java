package com.uscapps.srinidhikarthikbs.navigationbartest;

import android.content.Intent;
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
import android.widget.ListView;
import android.widget.Toast;

import com.uscapps.srinidhikarthikbs.navigationbartest.bills.billsFragment;
import com.uscapps.srinidhikarthikbs.navigationbartest.committees.committeesFragment;
import com.uscapps.srinidhikarthikbs.navigationbartest.favorites.favoritesFragment;
import com.uscapps.srinidhikarthikbs.navigationbartest.legislators.legislatorsFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    Toolbar toolbar;
    legislatorsFragment lFragment = null;
    billsFragment bFragment=null;
    committeesFragment cFragment=null;
    favoritesFragment fFragment=null;
    Fragment activeFragment=null;
    DrawerLayout drawer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.v("just entered", "in MainActivity");

        System.out.print("just entered in MainActivity");

        lFragment = legislatorsFragment.getInstance();
        bFragment = billsFragment.getInstance();
        cFragment = committeesFragment.getInstance();
        fFragment = favoritesFragment.getInstance();

        fragmentManager = getSupportFragmentManager();

        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.content_main,lFragment);
        fragmentTransaction.add(R.id.content_main,bFragment);
        fragmentTransaction.add(R.id.content_main,cFragment);
        fragmentTransaction.add(R.id.content_main,fFragment);
        fragmentTransaction.hide(lFragment);
        fragmentTransaction.hide(bFragment);
        fragmentTransaction.hide(cFragment);
        fragmentTransaction.hide(fFragment);
        fragmentTransaction.commit();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Legislators");

        //DrawerLayout drawer = (DrawerLayout) findViewById(r.id.drawer_layout);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.legislators);

        Log.v("just entered", "in legislators");
        fragmentTransaction = fragmentManager.beginTransaction();
        //fragmentTransaction.replace(r.id.content_main, lFragment);
        fragmentTransaction.show(lFragment);
        fragmentTransaction.commit();
        activeFragment = lFragment;

        Log.v("Done with everything", "in MainActivity");
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
        drawer.closeDrawer(GravityCompat.START);

        if (id == R.id.legislators) {
            Log.v("just entered", "in legislators");
            toolbar.setTitle("Legislators");
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.hide(activeFragment);
            //fragmentTransaction.replace(r.id.content_main, lFragment);
            fragmentTransaction.show(lFragment);
            //fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
            activeFragment = lFragment;

        } else if (id == R.id.bills) {
            Log.v("just entered", "in bills");
            toolbar.setTitle("Bills");
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.hide(activeFragment);
            //fragmentTransaction.replace(r.id.content_main, bFragment);
            fragmentTransaction.show(bFragment);
            //fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
            activeFragment = bFragment;

        } else if (id == R.id.committees) {
            Log.v("just entered", "in committees");
            toolbar.setTitle("Committees");
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.hide(activeFragment);
            //fragmentTransaction.replace(r.id.content_main, cFragment);
            fragmentTransaction.show(cFragment);
            //fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
            activeFragment = cFragment;

        } else if (id == R.id.favorites) {
            Log.v("just entered", "in favorites");
            toolbar.setTitle("Favorites");
            //fFragment = new favoritesFragment();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.hide(activeFragment);
            //fragmentTransaction.replace(r.id.content_main, fFragment);
            fragmentTransaction.show(fFragment);
            //fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
            activeFragment = fFragment;

        } else if (id == R.id.aboutme) {
            Log.v("just entered", "in aboutme");
            //supposed to go to a new activity

            /*fragmentTransaction = fragmentManager.beginTransaction();
            legislatorsFragment fragment = new legislatorsFragment();
            fragmentTransaction.replace(r.id.content_main, fragment);
            //fragmentTransaction.addToBackStack("add");
            fragmentTransaction.commit();*/

            startActivity(new Intent(MainActivity.this, aboutMe.class));
        }

        return true;
    }

    public void changeActionBarTitle(String name){
        toolbar.setTitle(name);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Toast.makeText(getApplicationContext(), "Onresume from MainActivity",Toast.LENGTH_SHORT).show();
    }
}
