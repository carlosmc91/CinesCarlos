package com.example.cinescarlos;

import android.os.Bundle;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class Youtube extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {

    YouTubePlayerView youtubeplayerview;
    String claveYoutube="AIzaSyCX1FTj0CCongiSz8vX5_XvjWJq4JmDaL8";
    String enlace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube);

        youtubeplayerview=(YouTubePlayerView)findViewById(R.id.youtubeview);
        youtubeplayerview.initialize(claveYoutube,this);

        enlace= getIntent().getStringExtra("Enlace");


    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean wasRestores) {
            youTubePlayer.cueVideo(enlace);

    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
            if(youTubeInitializationResult.isUserRecoverableError()){
                youTubeInitializationResult.getErrorDialog(this,1).show();
            }
    }
}
