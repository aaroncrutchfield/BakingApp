package com.example.ioutd.bakingapp.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ioutd.bakingapp.R;
import com.example.ioutd.bakingapp.model.Step;

import java.util.ArrayList;

/**
 * Created by ioutd on 1/25/2018.
 */

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.StepViewHolder> {
    private final Context context;
    private ArrayList<Step> stepArrayList;

    public StepAdapter(Context context, ArrayList<Step> stepArrayList){
        this.context = context;
        this.stepArrayList = stepArrayList;
    }

    @Override
    public StepViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_item_step, parent, false);

        return new StepViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StepViewHolder holder, int position) {
        Step step = stepArrayList.get(position);
        if (position == 0) {
            holder.tvStepId.setText("");
        } else {
            holder.tvStepId.setText(String.valueOf(step.getId()));
        }

        holder.tvStepShortDescription.setText(step.getShortDescription());
    }

    @Override
    public int getItemCount() {
        return stepArrayList.size();
    }

    class StepViewHolder extends RecyclerView.ViewHolder{

        TextView tvStepId;
        TextView tvStepShortDescription;

        public StepViewHolder(View itemView) {
            super(itemView);
            tvStepId = itemView.findViewById(R.id.tv_step_id);
            tvStepShortDescription = itemView.findViewById(R.id.tv_step_short_description);
        }
    }
}
