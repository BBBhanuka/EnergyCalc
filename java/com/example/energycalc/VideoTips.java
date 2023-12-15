package com.example.energycalc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

public class VideoTips extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_tips);

        // create variables and assignment
        final Button button = findViewById(R.id.backtotips);
        VideoView video = findViewById(R.id.videoView);
        video.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.savingtips);
        video.start();

        //create MediaController variable and assignment
        MediaController media = new MediaController(this);
        media.setAnchorView(video);
        video.setMediaController(media);



        // create on click listener and assign action
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}