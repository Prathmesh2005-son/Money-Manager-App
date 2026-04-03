package com.example.money_management_app.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.money_management_app.R;
import com.example.money_management_app.models.Transaction;
import java.util.List;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.TxnVH> {
    private final List<Transaction> data;

    public TransactionAdapter(List<Transaction> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public TxnVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_transaction, parent, false);
        return new TxnVH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TxnVH h, int position) {
        Transaction t = data.get(position);

        h.item.setText("• " + t.getItem());
        h.amount.setText("₹" + t.getAmount());
        h.type.setText(t.getType().equals("+") ? "Income" : "Expense");

        // ✅ Set Date
        h.date.setText(t.getDate());

        // ✅ Set Color
        h.amount.setTextColor(
                h.itemView.getResources().getColor(
                        t.getType().equals("+") ?
                                R.color.income_green :
                                R.color.expense_red
                )
        );
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static class TxnVH extends RecyclerView.ViewHolder {

        TextView item, amount, type, date;

        TxnVH(@NonNull View itemView) {
            super(itemView);
            item = itemView.findViewById(R.id.txnItem);
            amount = itemView.findViewById(R.id.txnAmount);
            type = itemView.findViewById(R.id.txnType);
            date = itemView.findViewById(R.id.txnDate);  // 🔥 Added
        }
    }
}