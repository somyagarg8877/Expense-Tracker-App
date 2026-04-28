package com.example.expensetracker;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputEditText;

public class AddExpenseActivity extends AppCompatActivity {

    private TextInputEditText etTitle, etAmount, etNote;
    private Spinner spinnerCategory;
    private Button btnSave;
    private ExpenseViewModel expenseViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        expenseViewModel = new ViewModelProvider(this).get(ExpenseViewModel.class);

        etTitle = findViewById(R.id.etTitle);
        etAmount = findViewById(R.id.etAmount);
        etNote = findViewById(R.id.etNote);
        spinnerCategory = findViewById(R.id.spinnerCategory);
        btnSave = findViewById(R.id.btnSave);

        String[] categories = {"Food", "Transport", "Shopping", "Entertainment", "Bills", "Other"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, categories);
        spinnerCategory.setAdapter(adapter);

        btnSave.setOnClickListener(v -> saveExpense());
    }

    private void saveExpense() {
        String title = etTitle.getText().toString().trim();
        String amountStr = etAmount.getText().toString().trim();
        String note = etNote.getText().toString().trim();
        String category = spinnerCategory.getSelectedItem().toString();

        if (title.isEmpty() || amountStr.isEmpty()) {
            Toast.makeText(this, "Please fill title and amount", Toast.LENGTH_SHORT).show();
            return;
        }

        double amount = Double.parseDouble(amountStr);
        long date = System.currentTimeMillis();

        Expense expense = new Expense(title, amount, category, date, note);
        expenseViewModel.insert(expense);

        Toast.makeText(this, "Expense Saved", Toast.LENGTH_SHORT).show();
        finish();
    }
}
