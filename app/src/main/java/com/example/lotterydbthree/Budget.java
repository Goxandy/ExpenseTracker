package com.example.lotterydbthree;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Budget implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "budget")
    private String budget;

    @ColumnInfo(name = "total")
    private String total;

    @ColumnInfo(name = "amountSpent")
    private String amountSpent;

    @ColumnInfo(name = "finished")
    private boolean finished;


    /*
     * Getters and Setters
     * */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getAmountSpent() {
        return amountSpent;
    }

    public void setAmountSpent(String amountSpent) {
        this.amountSpent = amountSpent;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }
}