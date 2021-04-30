package com.example.expenseTracker;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateBudgetsActivity extends AppCompatActivity {

    private EditText editTextBudget, editTextTotal, editTextAmountSpent;
    private CheckBox checkBoxFinished;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_task);


        editTextBudget = findViewById(R.id.editCategoryName);
        editTextTotal = findViewById(R.id.editBudget);
        editTextAmountSpent = findViewById(R.id.editTextAlreadySpent);

        checkBoxFinished = findViewById(R.id.checkBoxFinished);


        final Budget budget = (Budget) getIntent().getSerializableExtra("budget");

        loadTask(budget);

        findViewById(R.id.button_update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Clicked", Toast.LENGTH_LONG).show();
                updateTask(budget);
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
    }

    private void loadTask(Budget budget) {
        editTextBudget.setText(budget.getBudget());
        editTextTotal.setText(budget.getTotal());
        editTextAmountSpent.setText(budget.getAmountSpent());
        checkBoxFinished.setChecked(budget.isFinished());
    }

    private void updateTask(final Budget budget) {
        final String sTask = editTextBudget.getText().toString().trim();
        final String sDesc = editTextTotal.getText().toString().trim();
        final String sFinishBy = editTextAmountSpent.getText().toString().trim();

        if (sTask.isEmpty()) {
            editTextBudget.setError("Budget required");
            editTextBudget.requestFocus();
            return;
        }

        if (sDesc.isEmpty()) {
            editTextTotal.setError("Desc required");
            editTextTotal.requestFocus();
            return;
        }

        if (sFinishBy.isEmpty()) {
            editTextAmountSpent.setError("Finish by required");
            editTextAmountSpent.requestFocus();
            return;
        }

        class UpdateTask extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                budget.setBudget(sTask);
                budget.setTotal(sDesc);
                budget.setAmountSpent(sFinishBy);
                budget.setFinished(checkBoxFinished.isChecked());
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
                startActivity(new Intent(UpdateBudgetsActivity.this, MainActivity.class));
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
                startActivity(new Intent(UpdateBudgetsActivity.this, MainActivity.class));
            }
        }

        DeleteTask dt = new DeleteTask();
        dt.execute();

    }

}
