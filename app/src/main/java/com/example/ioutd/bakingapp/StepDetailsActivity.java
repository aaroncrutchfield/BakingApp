package com.example.ioutd.bakingapp;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.ioutd.bakingapp.data.AppDatabase;
import com.example.ioutd.bakingapp.data.AppViewModel;
import com.example.ioutd.bakingapp.model.Step;
import com.example.ioutd.bakingapp.repositories.StepRepository;
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

    AppViewModel appViewModel;

    SimpleExoPlayer exoPlayer;

    ActionBar actionBar;
    String stepID;
    long contentPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_details);
        ButterKnife.bind(this);
        Toolbar toolbar = findViewById(R.id.details_toolbar);
        setSupportActionBar(toolbar);

        actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);

        // Get the Step Object from the Intent
        Intent intent = getIntent();
        // TODO: 9/10/18 onSavedInstanceState
        stepID = intent.getStringExtra("stepID");

        // Construct the ViewModel
        appViewModel = new AppViewModel(getApplication());

        setupVideoPlayer();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("step_id", stepID);
        outState.putLong("content_position", contentPosition);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        stepID = savedInstanceState.getString("step_id");
        contentPosition = savedInstanceState.getLong("content_position");
    }

    private void setupVideoPlayer() {
        appViewModel.getStepByStepID(stepID).observe(this, new Observer<Step>() {
            @Override
            public void onChanged(@Nullable Step step) {
                String shortDescription = step.getShortDescription().replace(".", "");
                actionBar.setTitle(shortDescription);

                // Initialize the player and pass in the video url
                String stepVideoURL = step.getVideoURL();
                Log.d(TAG, ": url= " + stepVideoURL);
                if (stepVideoURL.equals("")){
                    epStepVideo.setVisibility(View.GONE);
                } else {
                    initializeExoPlayer(stepVideoURL);
                }

                // Set the text on the TextViews
                tvStepDescription.setText(step.getDescription());
                tvStepDetailId.setText(String.valueOf(step.getId()));
                Log.d(TAG, "onChanged: step=" + step.toString());
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        setupVideoPlayer();
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
            exoPlayer = ExoPlayerFactory.newSimpleInstance(
                    new DefaultRenderersFactory(this),
                    new DefaultTrackSelector(),
                    new DefaultLoadControl());
            epStepVideo.setPlayer(exoPlayer);

            // Prepare the MediaSource using a ExtractorMediaSource.Factory
            String userAgent = Util.getUserAgent(this, "BakingApp");
            DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(this, userAgent);
            ExtractorMediaSource.Factory mediaSourceFactory = new ExtractorMediaSource.Factory(dataSourceFactory);

            MediaSource mediaSource = buildMediaSource(Uri.parse(url));

            // Prepare the SimpleExoPlayer with the MediaSource and setPlayWhenReady = true
            exoPlayer.prepare(mediaSource);
            exoPlayer.seekTo(contentPosition);
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
            contentPosition = exoPlayer.getCurrentPosition();
            exoPlayer.release();
            exoPlayer = null;
        }
    }
}
