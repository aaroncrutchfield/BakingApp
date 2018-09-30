package com.example.ioutd.bakingapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ioutd.bakingapp.model.Step;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link StepDetailsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link StepDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StepDetailsFragment extends Fragment {

    @BindView(R.id.ep_step_video)
    SimpleExoPlayerView epStepVideo;

    @BindView(R.id.tv_step_description)
    TextView tvStepDescription;

    @BindView(R.id.tv_step_detail_id)
    TextView tvStepDetailId;

    SimpleExoPlayer exoPlayer;

    private static final String STEP_OBJECT = "step_object";

    private Step step;
    private long contentPosition = 0;

    private OnFragmentInteractionListener mListener;
    private Unbinder unbinder;

    public static final String TAG = "StepDetailsFragment";

    public StepDetailsFragment() {
        // Required empty public constructor
    }


    public static StepDetailsFragment newInstance(Step step) {
        StepDetailsFragment fragment = new StepDetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable(STEP_OBJECT, step);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            step = (Step) getArguments().get(STEP_OBJECT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_step_details, container, false);
        unbinder = ButterKnife.bind(this, view);

        setupVideoPlayer();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        setupVideoPlayer();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(STEP_OBJECT, step);
        outState.putLong("content_position", contentPosition);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            step = savedInstanceState.getParcelable(STEP_OBJECT);
            contentPosition = savedInstanceState.getLong("content_position");
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed() {
        if (mListener != null) {
            mListener.onNext();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        releasePlayer();
        mListener = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        releasePlayer();
        unbinder.unbind();
    }

    private void setupVideoPlayer() {
                String shortDescription = step.getShortDescription().replace(".", "");
                ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
                if (actionBar != null)
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
                if (tvStepDescription != null)
                    tvStepDescription.setText(step.getDescription());
                if (tvStepDetailId != null)
                    tvStepDetailId.setText(String.valueOf(step.getId()));
    }


    private void initializeExoPlayer(String url) {
        if (exoPlayer == null) {
            // Instantiate the SimpleExoPlayer and set the player on the SimpleExoPlayerView
            exoPlayer = ExoPlayerFactory.newSimpleInstance(
                    new DefaultRenderersFactory(getContext()),
                    new DefaultTrackSelector(),
                    new DefaultLoadControl());
            epStepVideo.setPlayer(exoPlayer);

            // Prepare the MediaSource using a ExtractorMediaSource.Factory
            String userAgent = Util.getUserAgent(getContext(), "BakingApp");
            DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(getContext(), userAgent);
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onNext();
        void onPrevious();
    }
}
