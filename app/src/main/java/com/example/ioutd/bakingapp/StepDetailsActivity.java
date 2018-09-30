package com.example.ioutd.bakingapp;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.ioutd.bakingapp.data.AppViewModel;
import com.example.ioutd.bakingapp.model.Step;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;

public class StepDetailsActivity extends AppCompatActivity implements StepDetailsFragment.OnFragmentInteractionListener{

    private static final String TAG = StepDetailsActivity.class.getSimpleName();

    ActionBar actionBar;
    int stepID;
    long contentPosition = 0;
    FragmentManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_details);
        ButterKnife.bind(this);
        Toolbar toolbar = findViewById(R.id.details_toolbar);
        setSupportActionBar(toolbar);

        actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);

        // Get the Step Object from the Intent
        Intent intent = getIntent();
        stepID = intent.getIntExtra("stepID", -1);

        manager = getSupportFragmentManager();

        setupStepFragment(0);
    }

    private void setupStepFragment(final int caseKey) {
        AppViewModel appViewModel = ViewModelProviders.of(this).get(AppViewModel.class);
        appViewModel.getStepByStepID(stepID).observe(this, new Observer<Step>() {
            @Override
            public void onChanged(@Nullable Step step) {
                if (step != null) {
                    Fragment fragment = StepDetailsFragment.newInstance(step);
                    switch (caseKey) {
                        case 0:
                            manager.beginTransaction()
                                    .replace(R.id.step_fragment_container, fragment)
                                    .commit();
                            break;
                        case 1:
                            manager.beginTransaction()
                                    .replace(R.id.step_fragment_container, fragment)
                                    .commit();
                            break;
                        case 2:
                            manager.beginTransaction()
                                    .replace(R.id.step_fragment_container, fragment)
                                    .commit();
                            break;

                    }
                } else {
                    if (caseKey == 1) {
                        stepID--;
                        Toast.makeText(StepDetailsActivity.this, "This is the last step", Toast.LENGTH_SHORT).show();
                    }
                    if (caseKey == 2) {
                        stepID++;
                        Toast.makeText(StepDetailsActivity.this, "This is the first step", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }

    @Override
    public void onNext() {
        stepID++;
        Log.d(TAG, "onNext: " + stepID);
        setupStepFragment(1);
    }

    @Override
    public void onPrevious() {
        stepID--;
        Log.d(TAG, "onPrevious: " + stepID);
        setupStepFragment(2);
    }

    @Optional
    @OnClick(R.id.btn_next)
    public void onNextBtn(View view) {
        onNext();
    }

    @Optional
    @OnClick(R.id.btn_previous)
    public void onPreviousBtn(View view){
        onPrevious();
    }

//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//        super.onConfigurationChanged(newConfig);
//
//        int visibility = epStepVideo.getVisibility();
//
//        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
//            epStepVideo.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
//        }
//    }

}
