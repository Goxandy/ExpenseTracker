package com.example.expenseTracker;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.app.NavUtils;

import android.os.Bundle;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class UpdateBudgetsActivity extends AppCompatActivity {

    private SwitchCompat themeSwitch;
    private TextView tvSpent, tvBudgeted, tvPercentageSpent, tvBudgetTitle;
    private ImageView ivCat;
    private EditText editAmountBudget, editAmountAlreadySpent;
    private ProgressBar progressBar;
    private Boolean bTheme;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        bTheme = intent.getExtras().getBoolean("theme");
        if(bTheme == false){
            setTheme(R.style.AppTheme_Dark);
        }
        setContentView(R.layout.activity_update_budget);


        final Budget budget = (Budget) getIntent().getSerializableExtra("budget");


        findViewById(R.id.button_update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Clicked", Toast.LENGTH_LONG).show();
                updateTask(budget);
            }
        });

        findViewById(R.id.button_reset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Budget has been reset", Toast.LENGTH_LONG).show();
                resetBudget(budget);
            }
        });



        findViewById(R.id.button_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(UpdateBudgetsActivity.this);
                builder.setTitle("Are you sure?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deleteTask(budget);
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                AlertDialog ad = builder.create();
                ad.show();
            }
        });

        themeSwitch = findViewById(R.id.switch_theme);
        themeSwitch.setChecked(bTheme);
        themeSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bTheme = !bTheme;
                finish();
                Intent intent = new Intent(getApplicationContext(), UpdateBudgetsActivity.class);
                intent.putExtra("theme", bTheme);
                intent.putExtra("budget", budget);
                startActivity(intent);
            }
        });

        loadTask(budget);
    }

    private void loadTask(Budget budget) {
        tvBudgetTitle = findViewById(R.id.budgetTitle);
        tvBudgetTitle.setText(budget.getBudget());
        tvSpent = findViewById(R.id.alreadySpentAmount);
        tvSpent.setText(budget.getAmountSpent());
        tvBudgeted = findViewById(R.id.budgetAmount);
        tvBudgeted.setText(budget.getTotal());
        ivCat = findViewById(R.id.imgCategory);
        switch(budget.getIcon()){
            case "car": ivCat.setImageResource(R.drawable.ic_car);
                break;
            case "cigarette": ivCat.setImageResource(R.drawable.ic_cigarette);
                break;
            case "coffee": ivCat.setImageResource(R.drawable.ic_coffee);
                break;
            case "food": ivCat.setImageResource(R.drawable.ic_food);
                break;
            case "furniture": ivCat.setImageResource(R.drawable.ic_furniture);
                break;
            case "games": ivCat.setImageResource(R.drawable.ic_games);
                break;
            case "home": ivCat.setImageResource(R.drawable.ic_home);
                break;
            case "music": ivCat.setImageResource(R.drawable.ic_music);
                break;
            case "phone": ivCat.setImageResource(R.drawable.ic_phone);
                break;
            case "wine": ivCat.setImageResource(R.drawable.ic_wine);
                break;
            default: ivCat.setImageResource(R.drawable.ic_music);
        }
        tvPercentageSpent = findViewById(R.id.percentageSpent);
        progressBar = findViewById(R.id.progressBar);
        if(Float.parseFloat(budget.getAmountSpent())!=Float.parseFloat("0")) {
            float progressRatio = 100 * Float.parseFloat(budget.getAmountSpent()) / Float.parseFloat(budget.getTotal());
            tvPercentageSpent.setText((int) progressRatio + "%");
            progressBar.setProgress((int) progressRatio);
        } else {
            progressBar.setProgress(0);
            tvPercentageSpent.setText("0%");
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        Intent intent = new Intent(UpdateBudgetsActivity.this, MainActivity.class);
        intent.putExtra("theme", bTheme);
        startActivity(intent);
    }


    private void updateTask(Budget budget) {

        editAmountBudget = findViewById(R.id.editAmountBudget);
        editAmountAlreadySpent = findViewById(R.id.addAmountAlreadySpent);

        if(editAmountBudget.getText().toString().trim().isEmpty()){
        } else {
            float newBudgetFloat = Float.parseFloat(budget.getTotal())+Float.parseFloat(editAmountBudget.getText().toString());
            budget.setTotal(String.valueOf(newBudgetFloat));
        }

        if(editAmountAlreadySpent.getText().toString().trim().isEmpty()){
        } else {
            float newSpentAmount = Float.parseFloat(budget.getAmountSpent())+Float.parseFloat(editAmountAlreadySpent.getText().toString());
            budget.setAmountSpent(String.valueOf(newSpentAmount));
        }

        class UpdateTask extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                        .taskDao()
                        .update(budget);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_LONG).show();
                finish();
                Intent intent = new Intent(UpdateBudgetsActivity.this, MainActivity.class);
                intent.putExtra("theme", bTheme);
                startActivity(intent);
            }
        }

        UpdateTask ut = new UpdateTask();
        ut.execute();
    }

    private void resetBudget(Budget budget) {
        budget.setAmountSpent("0");

        class UpdateTask extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                        .taskDao()
                        .update(budget);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_LONG).show();
                finish();
                Intent intent = new Intent(UpdateBudgetsActivity.this, MainActivity.class);
                intent.putExtra("theme", bTheme);
                startActivity(intent);
            }
        }

        UpdateTask ut = new UpdateTask();
        ut.execute();
    }


    private void deleteTask(final Budget budget) {
        class DeleteTask extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                        .taskDao()
                        .delete(budget);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(getApplicationContext(), "Deleted", Toast.LENGTH_LONG).show();
                finish();
                Intent intent = new Intent(UpdateBudgetsActivity.this, MainActivity.class);
                intent.putExtra("theme", bTheme);
                startActivity(intent);
            }
        }

        DeleteTask dt = new DeleteTask();
        dt.execute();

    }

}
