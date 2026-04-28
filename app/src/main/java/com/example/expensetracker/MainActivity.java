package com.example.expensetracker;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private ExpenseViewModel expenseViewModel;
    private TextView tvTotalExpense;
    private PieChart pieChart;
    private ExpenseAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTotalExpense = findViewById(R.id.tvTotalExpense);
        pieChart = findViewById(R.id.pieChart);
        
        setupRecyclerView();

        expenseViewModel = new ViewModelProvider(this).get(ExpenseViewModel.class);

        expenseViewModel.getAllExpenses().observe(this, expenses -> {
            adapter.setExpenses(expenses);
            updateChart(expenses);
        });

        expenseViewModel.getTotalExpenses().observe(this, total -> {
            if (total != null) {
                tvTotalExpense.setText(String.format(Locale.getDefault(), "₹%.2f", total));
            } else {
                tvTotalExpense.setText("₹0.00");
            }
        });

        FloatingActionButton fabAdd = findViewById(R.id.fabAdd);
        fabAdd.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddExpenseActivity.class);
            startActivity(intent);
        });
        
        setupChart();
    }

    private void setupRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ExpenseAdapter();
        recyclerView.setAdapter(adapter);
    }

    private void setupChart() {
        pieChart.getDescription().setEnabled(false);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setTransparentCircleRadius(61f);
        pieChart.setCenterText("Expenses");
        pieChart.setCenterTextSize(16f);
    }

    private void updateChart(List<Expense> expenses) {
        Map<String, Float> categoryTotals = new HashMap<>();

        for (Expense expense : expenses) {
            String category = expense.getCategory();
            float amount = (float) expense.getAmount();
            categoryTotals.put(category, categoryTotals.getOrDefault(category, 0f) + amount);
        }

        ArrayList<PieEntry> entries = new ArrayList<>();
        for (Map.Entry<String, Float> entry : categoryTotals.entrySet()) {
            entries.add(new PieEntry(entry.getValue(), entry.getKey()));
        }

        PieDataSet dataSet = new PieDataSet(entries, "Categories");
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);

        PieData data = new PieData(dataSet);
        data.setValueTextSize(14f);
        data.setValueTextColor(Color.WHITE);

        pieChart.setData(data);
        pieChart.invalidate(); // refresh chart
    }
}