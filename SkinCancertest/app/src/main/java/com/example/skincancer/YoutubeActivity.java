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
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.ArrayList;
import java.util.List;

public class YoutubeActivity extends AppCompatActivity implements YouTubePlayer.OnInitializedListener,NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "YoutubeActivity";
    YouTubePlayerView mYouTubePlayerView;
    YouTubePlayer.OnInitializedListener mOnInitializedListener;
    Button btnPlay, btnPrevent, btnCure;
    private DrawerLayout drawer;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        YouTubePlayerFragment youTubePlayerFragment =
                (YouTubePlayerFragment) getFragmentManager().findFragmentById(R.id.youtube_fragment);
        youTubePlayerFragment.initialize(YoutubeConfig.getAPI_key(),
                this);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);



//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_video);
//        Log.d(TAG, "onCreate:Starting.");
//        btnPlay = (Button) findViewById(R.id.btnPlay);
//        btnCure =(Button) findViewById(R.id.btnCure);
//        btnPrevent=(Button) findViewById(R.id.btnPrevent);
//        mYouTubePlayerView =(YouTubePlayerView) findViewById(R.id.youtubePlay);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar );
        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

//        mOnInitializedListener = new YouTubePlayer.OnInitializedListener() {
//            @Override
//            public void onInitializationSuccess(YouTubePlayer.Provider provider, final YouTubePlayer youTubePlayer, boolean b) {
//                Log.d(TAG, "onClick: Done initializing");
//                final List<String> videoList = new ArrayList<>();
//                btnPrevent.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        youTubePlayer.loadVideo("orLyV2qPdvA");
//                    }
//                });
//                btnCure.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        youTubePlayer.loadVideo("Ky2fBbXZ71I");
//                    }
//                });
//                videoList.add("orLyV2qPdvA");
//                videoList.add("Ky2fBbXZ71I");
//                youTubePlayer.loadVideos(videoList);
//
//            }
//
//            @Override
//            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
//                Log.d(TAG, "onClick: Failed to initializing");
//
//            }
//        };
//
//
//
//        btnPlay.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view){
//                Log.d(TAG, "onCreate: Intializing Youtube Player");
//                mYouTubePlayerView.initialize(YoutubeConfig.getAPI_key(), mOnInitializedListener);
//                Log.d(TAG, "onClick: Done initializing");
//            }
//        });

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
                Intent about_intent = new Intent(this, AboutUs.class);
                startActivity(about_intent);
                Toast.makeText(this, "About Us", Toast.LENGTH_SHORT).show();
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

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, final YouTubePlayer youTubePlayer, boolean b) {
        Log.d(TAG, "onClick: Done initializing");
        final List<String> videoList = new ArrayList<>();
//        btnPrevent.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                youTubePlayer.loadVideo("orLyV2qPdvA");
//            }
//        });
//        btnCure.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                youTubePlayer.loadVideo("Ky2fBbXZ71I");
//            }
//        });
        videoList.add("orLyV2qPdvA");
        videoList.add("wEG7BglBuYg");
        videoList.add("-O3fLMg6qwQ");
        youTubePlayer.loadVideos(videoList);

    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        Log.d(TAG, "onClick: Failed to initializing");
    }
}
