package com.corp.mrblacky.bsuirnews;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView nvDrawer;
    private ActionBarDrawerToggle drawerToggle;
    private int state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.corp.mrblacky.bsuirnews.R.layout.activity_main);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        toolbar = (Toolbar) findViewById(com.corp.mrblacky.bsuirnews.R.id.toolbar);
        setSupportActionBar(toolbar);

        mDrawer = (DrawerLayout) findViewById(com.corp.mrblacky.bsuirnews.R.id.drawer_layout);
        drawerToggle = setupDrawerToggle();
        mDrawer.addDrawerListener(drawerToggle);

        nvDrawer = (NavigationView) findViewById(com.corp.mrblacky.bsuirnews.R.id.nvView);
        setupDrawerContent(nvDrawer);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(com.corp.mrblacky.bsuirnews.R.id.contentFragment);
        if (fragment == null) {
            fragment = new ProgressFragment();
            fm.beginTransaction()
                    .add(com.corp.mrblacky.bsuirnews.R.id.contentFragment, fragment)
                    .commit();
        }
        nvDrawer.getMenu().getItem(0).setChecked(true);
        state = com.corp.mrblacky.bsuirnews.R.id.nav_home;

    }

    private ActionBarDrawerToggle setupDrawerToggle() {
        return new ActionBarDrawerToggle(this, mDrawer, toolbar, com.corp.mrblacky.bsuirnews.R.string.drawer_open,  com.corp.mrblacky.bsuirnews.R.string.drawer_close);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        if((item.getItemId() == com.corp.mrblacky.bsuirnews.R.id.toolbar_home)&&(state != com.corp.mrblacky.bsuirnews.R.id.nav_home) ) {
            state = com.corp.mrblacky.bsuirnews.R.id.nav_home;
            Fragment fragment = null;
            Class fragmentClass = ProgressFragment.class;
            try {
                fragment = (Fragment) fragmentClass.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(com.corp.mrblacky.bsuirnews.R.id.contentFragment, fragment).addToBackStack(null).commit();
            setTitle("Новости БГУИР");
            nvDrawer.setCheckedItem(com.corp.mrblacky.bsuirnews.R.id.nav_home);

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed(){


        super.onBackPressed();
    }

    // `onPostCreate` called when activity start-up is complete after `onStart()`
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(com.corp.mrblacky.bsuirnews.R.menu.main, menu);
        return true;
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    public void selectDrawerItem(final MenuItem menuItem) {
        // Create a new fragment and specify the fragment to show based on nav item clicked
        int ItemId = menuItem.getItemId();
        if (ItemId != state){
            Fragment fragment = null;
            state = ItemId;
            Class fragmentClass;
            Bundle extras = new Bundle();
            switch(menuItem.getItemId()) {
                case com.corp.mrblacky.bsuirnews.R.id.nav_home:
                    fragmentClass = ProgressFragment.class;
                    break;
                case com.corp.mrblacky.bsuirnews.R.id.nav_achivement:
                    extras.putString("From","achievements");
                    fragmentClass = ThemeNewsFragment.class;
                    break;
                case com.corp.mrblacky.bsuirnews.R.id.nav_education:
                    extras.putString("From","education");
                    fragmentClass = ThemeNewsFragment.class;
                    break;
                case com.corp.mrblacky.bsuirnews.R.id.nav_science:
                    extras.putString("From","science");
                    fragmentClass = ThemeNewsFragment.class;
                    break;
                case com.corp.mrblacky.bsuirnews.R.id.nav_entership:
                    extras.putString("From","cooperation");
                    fragmentClass = ThemeNewsFragment.class;
                    break;
                case com.corp.mrblacky.bsuirnews.R.id.nav_society:
                    extras.putString("From","society");
                    fragmentClass = ThemeNewsFragment.class;
                    break;
                case com.corp.mrblacky.bsuirnews.R.id.nav_sport:
                    extras.putString("From","sport");
                    fragmentClass = ThemeNewsFragment.class;
                    break;
                default:
                    fragmentClass = ProgressFragment.class;
            }

            try {
                fragment = (Fragment) fragmentClass.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }

            fragment.setArguments(extras);
            // Insert the fragment by replacing any existing fragment
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().
                    replace(com.corp.mrblacky.bsuirnews.R.id.contentFragment, fragment).
                    addToBackStack(null).
                    commit();


            // Highlight the selected item has been done by NavigationView
            menuItem.setChecked(true);
            // Set action bar title
            if(menuItem.getItemId() == com.corp.mrblacky.bsuirnews.R.id.nav_home) {
                setTitle("Новости БГУИР");
            }
            else {
                setTitle(menuItem.getTitle());
            }

            // Close the navigation drawer
            mDrawer.closeDrawers();
        }
    }
}
