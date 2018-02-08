package com.example.ioutd.bakingapp.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ioutd.bakingapp.R;
import com.example.ioutd.bakingapp.StepDetailsActivity;
import com.example.ioutd.bakingapp.model.Step;

import java.util.List;

/**
 * Created by ioutd on 1/25/2018.
 */

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.StepViewHolder> {
    private final Context context;
    private List<Step> steps;

    public StepAdapter(Context context){
        this.context = context;
    }

    @Override
    public StepViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_item_step, parent, false);

        return new StepViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StepViewHolder holder, int position) {
        Step step = steps.get(position);
        if (position == 0) {
            holder.tvStepId.setText("");
        } else {
            holder.tvStepId.setText(String.valueOf(step.getId()));
        }

        holder.tvStepShortDescription.setText(step.getShortDescription());
    }

    @Override
    public int getItemCount() {
        if (steps == null) return 0;
        return steps.size();
    }

    public void addSteps(List<Step> steps) {
        this.steps = steps;
        notifyDataSetChanged();
    }

    class StepViewHolder extends RecyclerView.ViewHolder{

        TextView tvStepId;
        TextView tvStepShortDescription;

        StepViewHolder(View itemView) {
            super(itemView);
            tvStepId = itemView.findViewById(R.id.tv_step_id);
            tvStepShortDescription = itemView.findViewById(R.id.tv_step_short_description);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, StepDetailsActivity.class);
                    intent.putExtra("step", steps.get(getAdapterPosition()));

                    context.startActivity(intent);
                }
            });
        }
    }
}
