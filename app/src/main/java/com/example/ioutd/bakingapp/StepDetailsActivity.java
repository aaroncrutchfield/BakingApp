package com.example.ioutd.bakingapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.ioutd.bakingapp.model.Step;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.RenderersFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepDetailsActivity extends AppCompatActivity {

    private static final String TAG = StepDetailsActivity.class.getSimpleName();

    @BindView(R.id.ep_step_video)
    SimpleExoPlayerView epStepVideo;

    @BindView(R.id.tv_step_description)
    TextView tvStepDescription;

    @BindView(R.id.tv_step_detail_id)
    TextView tvStepDetailId;

    SimpleExoPlayer exoPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_details);
        ButterKnife.bind(this);

        ActionBar actionBar = getSupportActionBar();

        // Get the Step Object from the Intent
        Intent intent = getIntent();
        Step step = intent.getParcelableExtra("step");

        String shortDescription = step.getShortDescription().replace(".", "");
        actionBar.setTitle(shortDescription);

        // Set default bitmap
        Bitmap defaultBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_no_video);
        epStepVideo.setDefaultArtwork(defaultBitmap);

        // Initialize the player and pass in the video url
        String url = step.getVideoURL();
        Log.d(TAG, ": url= " + url);

        initializeExoPlayer(url);

        // Set the text on the TextViews
        tvStepDescription.setText(step.getDescription());
        tvStepDetailId.setText(String.valueOf(step.getId()));

    }

    @Override
    protected void onStart() {
        super.onStart();
        // TODO: 1/29/2018 onStart() - initialize player with url
    }

    @Override
    protected void onResume() {
        super.onResume();
        // TODO: 1/29/2018 onStart() - initialize player with url
    }

    @Override
    protected void onPause() {
        super.onPause();
        releasePlayer();
    }

    @Override
    protected void onStop() {
        super.onStop();
        releasePlayer();
    }

    private void initializeExoPlayer(String url) {
        if (exoPlayer == null) {
            // Instantiate the SimpleExoPlayer and set the player on the SimpleExoPlayerView
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            RenderersFactory renderersFactory = new DefaultRenderersFactory(this);
            exoPlayer = ExoPlayerFactory.newSimpleInstance(renderersFactory, trackSelector, loadControl);
            epStepVideo.setPlayer(exoPlayer);

            // Prepare the MediaSource using a ExtractorMediaSource.Factory
            String userAgent = Util.getUserAgent(this, "BakingApp");
            DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(this, userAgent);
            ExtractorMediaSource.Factory mediaSourceFactory = new ExtractorMediaSource.Factory(dataSourceFactory);

            MediaSource mediaSource = buildMediaSource(Uri.parse(url));

            // Prepare the SimpleExoPlayer with the MediaSource and setPlayWhenReady = true
            exoPlayer.prepare(mediaSource);
            exoPlayer.setPlayWhenReady(true);
        }
    }

    // https://codelabs.developers.google.com/codelabs/exoplayer-intro/#2
    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource.Factory(new DefaultHttpDataSourceFactory("BakingApp"))
                .createMediaSource(uri);
    }

    private void releasePlayer() {
        if (exoPlayer != null) {
            exoPlayer.release();
            exoPlayer = null;
        }
    }
}
