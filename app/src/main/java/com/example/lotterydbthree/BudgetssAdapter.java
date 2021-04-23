package com.example.lotterydbthree;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BudgetssAdapter extends RecyclerView.Adapter<BudgetssAdapter.TasksViewHolder> {

    private Context mCtx;
    private List<Budget> budgetList;

    public BudgetssAdapter(Context mCtx, List<Budget> budgetList) {
        this.mCtx = mCtx;
        this.budgetList = budgetList;
    }

    @Override
    public TasksViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.recyclerview_tasks, parent, false);
        return new TasksViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TasksViewHolder holder, int position) {
        Budget t = budgetList.get(position);
        holder.textViewTask.setText(t.getBudget());
        float total = Float.parseFloat(t.getTotal());
        float amountSpent = Float.parseFloat(t.getAmountSpent());
        float remaining = total - amountSpent;
        holder.textViewDesc.setText(remaining + " remaining out of " + total);
        /*
        if (t.isFinished())
            holder.textViewStatus.setText("Completed");
        else
            holder.textViewStatus.setText("Not Completed");
         */
    }

    @Override
    public int getItemCount() {
        return budgetList.size();
    }

    class TasksViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textViewStatus, textViewTask, textViewDesc, textViewFinishBy;

        public TasksViewHolder(View itemView) {
            super(itemView);


            textViewTask = itemView.findViewById(R.id.tvCategoryName);
            textViewDesc = itemView.findViewById(R.id.info);


            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Budget budget = budgetList.get(getAdapterPosition());

            Intent intent = new Intent(mCtx, UpdateBudgetsActivity.class);
            intent.putExtra("budget", budget);

            mCtx.startActivity(intent);
        }
    }
}