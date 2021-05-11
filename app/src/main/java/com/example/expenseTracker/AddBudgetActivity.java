package com.example.expenseTracker;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class AddBudgetActivity extends AppCompatActivity {

    private EditText editTextBudget, editTextDesc, editTextFinishBy;
    private List<ImageView> imageViewList = new ArrayList<>();
    private String icon = "ic_car.png";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_budget);

        editTextBudget = findViewById(R.id.editCategoryName);
        editTextDesc = findViewById(R.id.editBudget);
        editTextFinishBy = findViewById(R.id.editTextAlreadySpent);

        findViewById(R.id.button_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveBudget();
            }
        });

        imageViewList.add(findViewById(R.id.icon1));
        imageViewList.add(findViewById(R.id.icon2));
        imageViewList.add(findViewById(R.id.icon3));
        imageViewList.add(findViewById(R.id.icon4));
        imageViewList.add(findViewById(R.id.icon5));
        imageViewList.add(findViewById(R.id.icon6));
        imageViewList.add(findViewById(R.id.icon7));
        imageViewList.add(findViewById(R.id.icon8));
        imageViewList.add(findViewById(R.id.icon9));
        imageViewList.add(findViewById(R.id.icon10));

        for (ImageView iv : imageViewList
        ) {
            iv.setOnClickListener((view) -> {

                for (ImageView elem : imageViewList
                ) {
                    elem.setBackgroundColor(Color.WHITE);
                }

                iv.setBackgroundColor(Color.LTGRAY);
                icon = iv.getContentDescription().toString();
            });
        }
    }

    private void saveBudget() {
        final String sTask = editTextBudget.getText().toString().trim();
        final String sDesc = editTextDesc.getText().toString().trim();
        final String sFinishBy = editTextFinishBy.getText().toString().trim();


        if (sTask.isEmpty()) {
            editTextBudget.setError("Budget required");
            editTextBudget.requestFocus();
            return;
        }

        if (sDesc.isEmpty()) {
            editTextDesc.setError("Desc required");
            editTextDesc.requestFocus();
            return;
        }

        if (sFinishBy.isEmpty()) {
            editTextFinishBy.setError("Finish by required");
            editTextFinishBy.requestFocus();
            return;
        }


        class SaveBudget extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {

                //creating a budget
                Budget budget = new Budget();
                budget.setBudget(sTask);
                budget.setTotal(sDesc);
                budget.setAmountSpent(sFinishBy);
                budget.setFinished(false);
                budget.setIcon(icon);

                //adding to database
                DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                        .taskDao()
                        .insert(budget);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                finish();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();
            }
        }

        SaveBudget st = new SaveBudget();
        st.execute();
    }

}