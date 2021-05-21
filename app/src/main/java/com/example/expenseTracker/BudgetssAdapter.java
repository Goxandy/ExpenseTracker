package com.example.expenseTracker;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;

import java.util.List;

public class BudgetssAdapter extends RecyclerView.Adapter<BudgetssAdapter.TasksViewHolder> {

    private Context mCtx;
    private List<Budget> budgetList;
    private Boolean bTheme;

    public BudgetssAdapter(Context mCtx, List<Budget> budgetList, boolean bTheme) {
        this.mCtx = mCtx;
        this.budgetList = budgetList;
        this.bTheme = bTheme;
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
        String iconLink = t.getIcon();

        switch(iconLink){
            case "car": holder.imgCategory.setImageResource(R.drawable.ic_car);
            break;
            case "cigarette": holder.imgCategory.setImageResource(R.drawable.ic_cigarette);
                break;
            case "coffee": holder.imgCategory.setImageResource(R.drawable.ic_coffee);
                break;
            case "food": holder.imgCategory.setImageResource(R.drawable.ic_food);
                break;
            case "furniture": holder.imgCategory.setImageResource(R.drawable.ic_furniture);
                break;
            case "games": holder.imgCategory.setImageResource(R.drawable.ic_games);
                break;
            case "home": holder.imgCategory.setImageResource(R.drawable.ic_home);
                break;
            case "music": holder.imgCategory.setImageResource(R.drawable.ic_music);
                break;
            case "phone": holder.imgCategory.setImageResource(R.drawable.ic_phone);
                break;
            case "wine": holder.imgCategory.setImageResource(R.drawable.ic_wine);
                break;
            default: holder.imgCategory.setImageResource(R.drawable.ic_music);
        }

        if(Float.parseFloat(t.getAmountSpent())!=Float.parseFloat("0")) {
            float progressRatio = 100 * Float.parseFloat(t.getAmountSpent()) / Float.parseFloat(t.getTotal());

            holder.progressBar.setProgress((int) progressRatio);
        } else {
            holder.progressBar.setProgress(0);
        }


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
        ImageView imgCategory;
        ProgressBar progressBar;

        public TasksViewHolder(View itemView) {
            super(itemView);


            textViewTask = itemView.findViewById(R.id.tvCategoryName);
            textViewDesc = itemView.findViewById(R.id.info);
            imgCategory = itemView.findViewById(R.id.imgCategory);
            progressBar = itemView.findViewById(R.id.progressInfo);


            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Budget budget = budgetList.get(getAdapterPosition());

            Intent intent = new Intent(mCtx, UpdateBudgetsActivity.class);
            intent.putExtra("budget", budget);
            intent.putExtra("theme", bTheme);

            mCtx.startActivity(intent);
        }
    }
}