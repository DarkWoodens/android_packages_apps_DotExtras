package com.dotos.dotextras;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
// Impoort Dots Fragments
import com.dotos.dotextras.fragments.AboutExtrasFragment;
import com.dotos.dotextras.fragments.ButtonFragment;
import com.dotos.dotextras.fragments.DisplayFragment;
import com.dotos.dotextras.fragments.LockScreenFragment;
import com.dotos.dotextras.fragments.MiscFragment;
import com.dotos.dotextras.fragments.NavbarFragment;
import com.dotos.dotextras.fragments.PowerMenuFragment;
import com.dotos.dotextras.fragments.RecentsFragment;
import com.dotos.dotextras.fragments.StatusbarFragment;
import com.dotos.dotextras.fragments.SupportedDevicesFragment;
import com.dotos.dotextras.tools.flasher.Tools_Flasher;
import com.dotos.dotextras.tools.system_manager.ManagerMain;
import com.dotos.dotextras.utils.StaticReload;
import com.dotos.dotextras.utils.Utils;

import com.dotos.R;


public class dotsettings extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private Spinner spThemes;
    private SharedPreferences prefs;
    private String prefName = "spinner_value";
    int id=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.onActivityCreateSetTheme(this);
        setContentView(R.layout.layout_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        Utils.onActivityCreateSetTheme(this);
        setupSpinnerItemSelection();
        spThemes = (Spinner) findViewById(R.id.acc_spinner);
        prefs = getSharedPreferences(prefName, MODE_PRIVATE);
        id=prefs.getInt("last_val",0);
        spThemes.setSelection(id);
    }
    private void setupSpinnerItemSelection() {
        spThemes = (Spinner) findViewById(R.id.acc_spinner);
        spThemes.setSelection(ThemeApplication.currentPosition);
        ThemeApplication.currentPosition = spThemes.getSelectedItemPosition();
        spThemes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                if (ThemeApplication.currentPosition != position) {

                    prefs = getSharedPreferences(prefName, MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putInt("last_val", position);
                    editor.commit();
                    Utils.changeToTheme(dotsettings.this, position);
                }ThemeApplication.currentPosition = position;}
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        spThemes = (Spinner) findViewById(R.id.acc_spinner);
        prefs = getSharedPreferences(prefName, MODE_PRIVATE);
        id=prefs.getInt("last_val",0);
        spThemes.setSelection(id);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dotsettings, menu);
        spThemes = (Spinner) findViewById(R.id.acc_spinner);
        prefs = getSharedPreferences(prefName, MODE_PRIVATE);
        id=prefs.getInt("last_val",0);
        spThemes.setSelection(id);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        spThemes = (Spinner) findViewById(R.id.acc_spinner);
        prefs = getSharedPreferences(prefName, MODE_PRIVATE);
        id=prefs.getInt("last_val",0);
        spThemes.setSelection(id);
        int id = item.getItemId();
        /*if (id == R.id.action_sysreboot) {
            Toast.makeText(getBaseContext(), "Debug Version : " + BuildConfig.VERSION_NAME, Toast.LENGTH_LONG).show();
            return true;
        }*/
        return super.onOptionsItemSelected(item);
    }
    public void openGit(View v){
        Uri uri = Uri.parse("https://www.github.com/DotOS");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }
    public void black_magic(View v){
        final Snackbar snackBar = Snackbar.make(getCurrentFocus(),"No Easteregg Found, nice try", Snackbar.LENGTH_INDEFINITE);
                snackBar.setAction("Ok...", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        snackBar.dismiss();
                    }
                });
                snackBar.show();
    }
    public void show_cm(View v){
        ImageButton show = (ImageButton) findViewById(R.id.imageButton3);
        show.setVisibility(View.GONE);
        ImageButton hide = (ImageButton) findViewById(R.id.imageButton4);
        hide.setVisibility(View.VISIBLE);
        final RelativeLayout show_layout = (RelativeLayout) findViewById(R.id.cm_layouts);
        show_layout.setVisibility(View.VISIBLE);
    }
    public void hide_cm(View v){
        ImageButton show = (ImageButton) findViewById(R.id.imageButton3);
        show.setVisibility(View.VISIBLE);
        ImageButton hide = (ImageButton) findViewById(R.id.imageButton4);
        hide.setVisibility(View.GONE);
        RelativeLayout show_layout = (RelativeLayout) findViewById(R.id.cm_layouts);
        show_layout.setVisibility(View.GONE);
    }
    public void show_pref(View v){
        ImageButton show = (ImageButton) findViewById(R.id.imageButton);
        show.setVisibility(View.GONE);
        ImageButton hide = (ImageButton) findViewById(R.id.imageButton2);
        hide.setVisibility(View.VISIBLE);
        final RelativeLayout show_layout = (RelativeLayout) findViewById(R.id.pref_layouts);
        show_layout.setVisibility(View.VISIBLE);
    }
    public void hide_pref(View v){
        ImageButton hide = (ImageButton) findViewById(R.id.imageButton2);
        hide.setVisibility(View.GONE);
        ImageButton show = (ImageButton) findViewById(R.id.imageButton);
        show.setVisibility(View.VISIBLE);
        final RelativeLayout show_layout = (RelativeLayout) findViewById(R.id.pref_layouts);
        show_layout.setVisibility(View.GONE);
    }
    public void show_acc(View v){
        //Button show = (Button) findViewById(R.id.btn_acc);
        //show.setVisibility(View.GONE);
        //Button hide = (Button) findViewById(R.id.btn_acc_hide);
        //hide.setVisibility(View.VISIBLE);
       // RelativeLayout show_layout = (RelativeLayout) findViewById(R.id.acc_layout);
        //show_layout.setVisibility(View.VISIBLE);
    }
    public void hide_acc(View v){
        //Button show = (Button) findViewById(R.id.btn_acc);
        //show.setVisibility(View.VISIBLE);
        //Button hide = (Button) findViewById(R.id.btn_acc_hide);
        //hide.setVisibility(View.GONE);
        //RelativeLayout show_layout = (RelativeLayout) findViewById(R.id.acc_layout);
        //show_layout.setVisibility(View.GONE);
    }
    public void openTelegram(View v){
        Uri uri = Uri.parse("https://t.me/dotos");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }
    public void openGPlus(View v){
        Uri uri = Uri.parse("https://plus.google.com/communities/101137692192340076065?sqinv=T2lpMjQtWFBBVFVmdnNkT053VWlkeXJkdGJrVmNB");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }
    public void openDonate(View v){
        Toast.makeText(getBaseContext(), "Donation Link comming soon...", Toast.LENGTH_LONG).show();
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_dot_main) {
            ConstraintLayout mainLayout = (ConstraintLayout) findViewById(R.id.main_layout);
            LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.pref_cardview_main, null);
            mainLayout.removeAllViews();
            mainLayout.addView(layout);
            getApplicationContext();
            setupSpinnerItemSelection();
        } else if (id == R.id.nav_flasher) {
            finish();
            startActivity(new Intent(this, Tools_Flasher.class));
        } else if (id == R.id.nav_sys_manager) {
            finish();
            startActivity(new Intent(this, ManagerMain.class));
        } else if (id == R.id.nav_dot_statusbar) {
            ConstraintLayout mainLayout = (ConstraintLayout) findViewById(R.id.main_layout);
            LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.pref_view, null);
            mainLayout.removeAllViews();
            mainLayout.addView(layout);
            getFragmentManager().beginTransaction()
                    .replace(R.id.pref_view_layout, new StatusbarFragment())
                    .commitAllowingStateLoss();
        } else if (id == R.id.nav_dot_buttons) {
            ConstraintLayout mainLayout = (ConstraintLayout) findViewById(R.id.main_layout);
            LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.pref_view, null);
            mainLayout.removeAllViews();
            mainLayout.addView(layout);
            getFragmentManager().beginTransaction()
                    .replace(R.id.pref_view_layout, new ButtonFragment())
                    .commitAllowingStateLoss();
        } else if (id == R.id.nav_dot_display) {
            ConstraintLayout mainLayout = (ConstraintLayout) findViewById(R.id.main_layout);
            LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.pref_view, null);
            mainLayout.removeAllViews();
            mainLayout.addView(layout);
            getFragmentManager().beginTransaction()
                    .replace(R.id.pref_view_layout, new DisplayFragment())
                    .commitAllowingStateLoss();
        } else if (id == R.id.nav_dot_lockscreen) {
            ConstraintLayout mainLayout = (ConstraintLayout) findViewById(R.id.main_layout);
            LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.pref_view, null);
            mainLayout.removeAllViews();
            mainLayout.addView(layout);
            getFragmentManager().beginTransaction()
                    .replace(R.id.pref_view_layout, new LockScreenFragment())
                    .commitAllowingStateLoss();
        } else if (id == R.id.nav_dot_powermenu) {
            ConstraintLayout mainLayout = (ConstraintLayout) findViewById(R.id.main_layout);
            LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.pref_view, null);
            mainLayout.removeAllViews();
            mainLayout.addView(layout);
            getFragmentManager().beginTransaction()
                    .replace(R.id.pref_view_layout, new PowerMenuFragment())
                    .commitAllowingStateLoss();
        } else if (id == R.id.nav_dot_misc) {
            ConstraintLayout mainLayout = (ConstraintLayout) findViewById(R.id.main_layout);
            LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.pref_view, null);
            mainLayout.removeAllViews();
            mainLayout.addView(layout);
            getFragmentManager().beginTransaction()
                    .replace(R.id.pref_view_layout, new MiscFragment())
                    .commitAllowingStateLoss();
        } else if (id == R.id.nav_dot_recents) {
            ConstraintLayout mainLayout = (ConstraintLayout) findViewById(R.id.main_layout);
            LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.pref_view, null);
            mainLayout.removeAllViews();
            mainLayout.addView(layout);
            getFragmentManager().beginTransaction()
                    .replace(R.id.pref_view_layout, new RecentsFragment())
                    .commitAllowingStateLoss();
        } else if (id == R.id.nav_about_extras) {
            ConstraintLayout mainLayout = (ConstraintLayout) findViewById(R.id.main_layout);
            LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.pref_view, null);
            mainLayout.removeAllViews();
            mainLayout.addView(layout);
            getFragmentManager().beginTransaction()
                    .replace(R.id.pref_view_layout, new AboutExtrasFragment())
                    .commitAllowingStateLoss();
        } else if (id == R.id.nav_dot_navbar) {
            ConstraintLayout mainLayout = (ConstraintLayout) findViewById(R.id.main_layout);
            LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.pref_view, null);
            mainLayout.removeAllViews();
            mainLayout.addView(layout);
            getFragmentManager().beginTransaction()
                    .replace(R.id.pref_view_layout, new NavbarFragment())
                    .commitAllowingStateLoss();
        } else if (id == R.id.nav_dot_support) {
            ConstraintLayout mainLayout = (ConstraintLayout) findViewById(R.id.main_layout);
            LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.pref_about, null);
            mainLayout.removeAllViews();
            mainLayout.addView(layout);
            getFragmentManager().beginTransaction()
                    .replace(R.id.pref_about_layout, new SupportedDevicesFragment())
                    .commitAllowingStateLoss();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
