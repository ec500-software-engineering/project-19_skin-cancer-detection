package com.example.skincancer;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

/**
 * Created by User on 20-03-2018.
 */

public class AboutUs extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener
{
    ImageButton Wanxuan, Yucheng;
    private DrawerLayout drawer;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        Wanxuan = (ImageButton) findViewById(R.id.WanxuanGit);
        Yucheng = (ImageButton) findViewById(R.id.YuchengGit);
        Wanxuan.setOnClickListener(this);
        Yucheng.setOnClickListener(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar );
        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }


    @Override
    public void onClick(View view){
        Intent i = new Intent(view.getContext(), WebViewActivity.class);
        switch(view.getId()){
            case R.id.WanxuanGit:{
                i.putExtra("link", "https://github.com/vansoncc");
                break;
            }
            case R.id.YuchengGit:{
                i.putExtra("link", "https://github.com/YanzuWuu");
                break;
            }
        }
        startActivity(i);
    }
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                Intent home_intent = new Intent(this, MainActivity.class);
                startActivity(home_intent);
                break;
            case R.id.nav_map:
                Intent map_intent = new Intent(this, MapsActivity.class);
                startActivity(map_intent);
                break;
            case R.id.nav_video:
                Intent video_intent = new Intent(this, YoutubeActivity.class);
                startActivity(video_intent);
                break;
            case R.id.nav_detect:
                Intent detect_intent = new Intent(this, DetectActivity.class);
                startActivity(detect_intent);
                break;
            case R.id.nav_about:
                Toast.makeText(this, "Send", Toast.LENGTH_SHORT).show();
                break;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}