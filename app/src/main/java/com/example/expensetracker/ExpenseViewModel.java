package com.example.expensetracker;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ExpenseViewModel extends AndroidViewModel {

    private ExpenseDao expenseDao;
    private LiveData<List<Expense>> allExpenses;
    private LiveData<Double> totalExpenses;

    public ExpenseViewModel(@NonNull Application application) {
        super(application);
        AppDatabase db = AppDatabase.getDatabase(application);
        expenseDao = db.expenseDao();
        allExpenses = expenseDao.getAllExpenses();
        totalExpenses = expenseDao.getTotalExpenses();
    }

    public LiveData<List<Expense>> getAllExpenses() {
        return allExpenses;
    }

    public LiveData<Double> getTotalExpenses() {
        return totalExpenses;
    }

    public void insert(Expense expense) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            expenseDao.insert(expense);
        });
    }

    public void delete(Expense expense) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            expenseDao.delete(expense);
        });
    }
}
