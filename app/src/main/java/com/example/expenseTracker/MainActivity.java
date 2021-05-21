package com.example.expenseTracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Color;
import android.os.Bundle;

import android.content.Intent;
import android.os.AsyncTask;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    /* Copyright
    Code taken from Belal Khan https://www.simplifiedcoding.net/android-room-database-example/
    Minor adaptions in naming, style and support libraries (Migration to Android X support libraries)
    */

    private FloatingActionButton buttonAddTask;
    private SwitchCompat themeSwitch;
    private RecyclerView recyclerView;
    private Toolbar toolbar;

    public Boolean getBTheme() {
        return bTheme;
    }

    private Boolean bTheme = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        if(intent.getExtras()!=null) {
                bTheme = intent.getExtras().getBoolean("theme");
                if (bTheme == false) {
                    setTheme(R.style.AppTheme_Dark);
            }
        }
        setContentView(R.layout.activity_main);

        themeSwitch = findViewById(R.id.switch_theme);
        themeSwitch.setChecked(bTheme);
        themeSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bTheme = !bTheme;
                finish();
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                intent.putExtra("theme", bTheme);
                startActivity(intent);
            }
        });
        // setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.recyclerview_tasks);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        buttonAddTask = findViewById(R.id.floating_button_add);
        buttonAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent intent = new Intent(MainActivity.this, AddBudgetActivity.class);
                intent.putExtra("theme", bTheme);
                startActivity(intent);
            }
        });




        getBudgets();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    protected void iconSelection() {


    }

    private void getBudgets() {
        class GetBudgets extends AsyncTask<Void, Void, List<Budget>> {

            @Override
            protected List<Budget> doInBackground(Void... voids) {
                List<Budget> budgetList = DatabaseClient
                        .getInstance(getApplicationContext())
                        .getAppDatabase()
                        .taskDao()
                        .getAll();
                return budgetList;
            }

            @Override
            protected void onPostExecute(List<Budget> budgets) {
                super.onPostExecute(budgets);

                BudgetssAdapter adapter = new BudgetssAdapter(MainActivity.this, budgets, bTheme);
                recyclerView.setAdapter(adapter);
            }
        }

        GetBudgets gt = new GetBudgets();
        gt.execute();
    }


}