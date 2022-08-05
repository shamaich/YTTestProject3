package com.shamai4.yttestproject3;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.ui.StyledPlayerView;
import com.yausername.youtubedl_android.YoutubeDL;
import com.yausername.youtubedl_android.YoutubeDLException;
import com.yausername.youtubedl_android.YoutubeDLRequest;
import com.yausername.youtubedl_android.mapper.VideoInfo;

public class MainActivity extends AppCompatActivity {

    private String TAG = MainActivity.class.getSimpleName();
    private ExoPlayer player;
    private StyledPlayerView playerView;
    private MediaItem mediaItem;
    private String youTubeUrl = "https://youtu.be/eq3PGCPQOOY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        player = new ExoPlayer.Builder(this).build();

        playerView = findViewById(R.id.player_view);

        playerView.setPlayer(player);

        try {
            YoutubeDL.getInstance().init(getApplication());
        } catch (YoutubeDLException e) {
            Log.e(TAG, "failed to initialize youtubedl-android", e);
        }
            //Request DASH link
        YoutubeDLRequest request = new YoutubeDLRequest(youTubeUrl);
        request.addOption("-f", "best");
        VideoInfo streamInfo = null;
        try {
            streamInfo = YoutubeDL.getInstance().getInfo(request);
            Log.d(TAG, "streamInfo: " + streamInfo.getUrl());
        } catch (YoutubeDLException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if(streamInfo != null) {
            // Build the media item.
            mediaItem = MediaItem.fromUri(streamInfo.getUrl());
            // Set the media item to be played.
            player.setMediaItem(mediaItem);
            // Prepare the player.
            player.prepare();
            // Start the playback.
            //player.setPlayWhenReady(true);
        }
    }


}