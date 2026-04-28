package com.example.expensetracker;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ExpenseDao {

    @Insert
    void insert(Expense expense);

    @Update
    void update(Expense expense);

    @Delete
    void delete(Expense expense);

    @Query("SELECT * FROM expenses ORDER BY date DESC")
    LiveData<List<Expense>> getAllExpenses();

    @Query("SELECT * FROM expenses ORDER BY date DESC")
    List<Expense> getAllExpensesSync();

    @Query("SELECT SUM(amount) FROM expenses")
    LiveData<Double> getTotalExpenses();
}
