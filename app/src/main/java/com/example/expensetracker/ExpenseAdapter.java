package com.example.expensetracker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder> {

    private List<Expense> expenses = new ArrayList<>();

    @NonNull
    @Override
    public ExpenseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_expense, parent, false);
        return new ExpenseViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseViewHolder holder, int position) {
        Expense currentExpense = expenses.get(position);
        holder.tvTitle.setText(currentExpense.getTitle());
        holder.tvAmount.setText(String.format(Locale.getDefault(), "₹%.2f", currentExpense.getAmount()));
        holder.tvCategory.setText(currentExpense.getCategory());

        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
        holder.tvDate.setText(sdf.format(new Date(currentExpense.getDate())));
    }

    @Override
    public int getItemCount() {
        return expenses.size();
    }

    public void setExpenses(List<Expense> expenses) {
        this.expenses = expenses;
        notifyDataSetChanged();
    }

    class ExpenseViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle;
        private TextView tvAmount;
        private TextView tvCategory;
        private TextView tvDate;

        public ExpenseViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvItemTitle);
            tvAmount = itemView.findViewById(R.id.tvItemAmount);
            tvCategory = itemView.findViewById(R.id.tvItemCategory);
            tvDate = itemView.findViewById(R.id.tvItemDate);
        }
    }
}
