package mi.ur.de.android.runnersmeetup;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class NavigationDrawer extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initLayout();
    }

    private void initLayout() {
        setContentView(R.layout.activity_navigation_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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
        getMenuInflater().inflate(R.menu.navigation_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.gps_icon) {
            return true;
        } else if(id == R.id.music_icon){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_shoe) {
            startActivity(new Intent(this, RMU_Main.class));
        } else if (id == R.id.nav_statistic) {
            startActivity(new Intent(this, TrainingOverviewActivity.class));
        } else if (id == R.id.nav_vorschlaege){
            startActivity(new Intent(this, SuggestionsActivity.class));
        } else if (id == R.id.nav_friends){
            startActivity(new Intent(this, FriendsActivity.class));
        } else if (id == R.id.nav_veranstaltungen) {
            startActivity(new Intent(this, EventsActivity.class));
        } else if (id == R.id.nav_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
        } else if (id == R.id.nav_logout){
            logout();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void logout() {
        AlertDialog.Builder logout = new AlertDialog.Builder(NavigationDrawer.this);
        logout.setMessage("Ausloggen?")
                .setCancelable(false)
                .setPositiveButton("Ja", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences prefs = getSharedPreferences("LoginData",MODE_PRIVATE);
                        SharedPreferences.Editor prefsEditor = prefs.edit();
                        Constants.setId(-1);
                        Constants.setName(null);
                        Constants.setGewicht(0);
                        Constants.setGroesse(0);
                        prefsEditor.putInt(Constants.KEY_ID, Constants.getId());
                        prefsEditor.putString(Constants.KEY_NAME, Constants.getName());
                        prefsEditor.putFloat(Constants.KEY_WEIGHT, Constants.getWeight());
                        prefsEditor.putInt(Constants.KEY_SIZE, Constants.getSize());
                        prefsEditor.commit();
                        finish();
                        startActivity(new Intent(NavigationDrawer.this, LoginActivity.class));
                    }
                })
                .setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = logout.create();
        alert.setTitle("Logout");
        alert.show();
    }
}
