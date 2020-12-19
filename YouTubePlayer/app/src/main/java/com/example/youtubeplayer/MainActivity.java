package com.example.youtubeplayer;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;


public class MainActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {

    // Control buttons
    Button  playBtn,
            pauseResumeBtn,
            stopBtn;

    // Layout with TextView representations of videos
    LinearLayout ll_videos_list;
    // Represents quantity of videos (actual: 3)
    int videosCount;

    // Boolean representations if the video is paused or resumed
    boolean isPaused = false;

    // Index of actual active video to be played
    // (if player is stopped, video will be reproduced after tapping on play button)
    int currentVideoIndex = 0;

    // Table with video id
    private final String[] videos = {
            "uMeR2W19wT0", // Unravel
            "RUQl6YcMalg", // Therefore I Am
            "WJVXOJrYjl4", // I Can't Sleep
            "2M8Ks0xiaxM"  // The Ride of the Rohirrim
    };

    // YouTube player View
    private YouTubePlayerView youTubePlayerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        youTubePlayerView = findViewById(R.id.youtube_view);

        ll_videos_list = findViewById(R.id.videos_list);

        videosCount = ll_videos_list.getChildCount();

        playBtn = findViewById(R.id.play_btn);

        stopBtn = findViewById(R.id.stop_btn);
        stopBtn.setClickable(false);

        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                youTubePlayerView.initialize("AIzaSyAZrWmp4gi_Y4ZMxCeJISZXc0V0lZnUs1Q",MainActivity.this);
            }
        });
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, final YouTubePlayer youTubePlayer, boolean b) {
        // Configurating of player
        youTubePlayer.setPlayerStateChangeListener(playerStateChangeListener);
        youTubePlayer.setPlaybackEventListener(playbackEventListener);
        youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.CHROMELESS);

        // Setting of the pause/resume button logic
        pauseResumeBtn = findViewById(R.id.pause_resume_btn);
        pauseResumeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isPaused) {
                    pauseResumeBtn.setText(R.string.pause);
                    youTubePlayer.play();
                } else {
                    pauseResumeBtn.setText(R.string.resume);
                    youTubePlayer.pause();
                }

                isPaused = !isPaused;
            }
        });

        // Setting of the stop button logic
        stopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disableActiveVideoFromList();

                // Deleting of TextView video representations` click listeners
                for (int j = 0; j < videosCount; j++) {
                    ll_videos_list.getChildAt(j).setOnClickListener(null);
                }

                playBtn.setClickable(true);
                stopBtn.setClickable(false);

                resetPauseBtn();
                pauseResumeBtn.setClickable(false);

                youTubePlayer.release();
            }
        });


        // Setting of click listener  on each TextView video representations
        for (int i = 0; i < videosCount; i++) {
            final int videoIndex = i;
            ll_videos_list.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    disableActiveVideoFromList();
                    resetPauseBtn();

                    // Update the current video index
                    currentVideoIndex = videoIndex;

                    // Set red bg for active video
                    v.setBackgroundColor(Color.RED);

                    youTubePlayer.loadVideo(videos[videoIndex]);
                }
            });
        }

        // Set red bg color on the active video
        ll_videos_list.getChildAt(currentVideoIndex).setBackgroundColor(Color.RED);
        youTubePlayer.loadVideo(videos[currentVideoIndex]);

        playBtn.setClickable(false);
        pauseResumeBtn.setClickable(true);
        stopBtn.setClickable(true);
    }

    private void disableActiveVideoFromList() {
        ll_videos_list.getChildAt(currentVideoIndex).setBackgroundColor(Color.parseColor("#eeeeee"));
    }

    private void resetPauseBtn() {
        isPaused = false;
        pauseResumeBtn.setText(R.string.pause);
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        // If it's failed, show the message about this
        Toast.makeText(getApplicationContext(),"Initialization failed :(",Toast.LENGTH_LONG).show();
    }

    // Listeners for configuration youtube video player against state changes

    private YouTubePlayer.PlayerStateChangeListener playerStateChangeListener = new YouTubePlayer.PlayerStateChangeListener() {
        @Override
        public void onLoading() {} //unused

        @Override
        public void onLoaded(String s) {} //unused

        @Override
        public void onAdStarted() {} //unused

        @Override
        public void onVideoStarted() {} //unused

        @Override
        public void onVideoEnded() {} //unused

        @Override
        public void onError(YouTubePlayer.ErrorReason errorReason) {
            // If loading this video is impossible, show why
            Toast.makeText(getApplicationContext(),errorReason.toString(), Toast.LENGTH_SHORT).show();
        }
    };

    //This interface is unused, but it can be changed later :)
    private YouTubePlayer.PlaybackEventListener playbackEventListener = new YouTubePlayer.PlaybackEventListener() {
        @Override
        public void onPlaying() {} //unused

        @Override
        public void onPaused() {} //unused

        @Override
        public void onStopped() {} //unused

        @Override
        public void onBuffering(boolean b) {} //unused

        @Override
        public void onSeekTo(int i) {} //unused
    };
}