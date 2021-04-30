package com.example.expenseTracker;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface BudgetDao {

    @Query("SELECT * FROM Budget")
    List<Budget> getAll();

    @Insert
    void insert(Budget budget);

    @Delete
    void delete(Budget budget);

    @Update
    void update(Budget budget);

}
