package com.example.skincancer;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.ArrayList;
import java.util.List;

public class YoutubeActivity extends YouTubeBaseActivity {
    private static final String TAG = "YoutubeActivity";
    YouTubePlayerView mYouTubePlayerView;
    YouTubePlayer.OnInitializedListener mOnInitializedListener;
    Button btnPlay, btnPrevent, btnCure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        Log.d(TAG, "onCreate:Starting.");
        btnPlay = (Button) findViewById(R.id.btnPlay);
        btnCure =(Button) findViewById(R.id.btnCure);
        btnPrevent=(Button) findViewById(R.id.btnPrevent);
        mYouTubePlayerView =(YouTubePlayerView) findViewById(R.id.youtubePlay);

        mOnInitializedListener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, final YouTubePlayer youTubePlayer, boolean b) {
                Log.d(TAG, "onClick: Done initializing");
                final List<String> videoList = new ArrayList<>();
                btnPrevent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        youTubePlayer.loadVideo("orLyV2qPdvA");
                    }
                });
                btnCure.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        youTubePlayer.loadVideo("Ky2fBbXZ71I");
                    }
                });
                videoList.add("orLyV2qPdvA");
                videoList.add("Ky2fBbXZ71I");
                youTubePlayer.loadVideos(videoList);

            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Log.d(TAG, "onClick: Failed to initializing");

            }
        };



        btnPlay.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Log.d(TAG, "onCreate: Intializing Youtube Player");
                mYouTubePlayerView.initialize(YoutubeConfig.getAPI_key(), mOnInitializedListener);
                Log.d(TAG, "onClick: Done initializing");
            }
        });

    }
}
