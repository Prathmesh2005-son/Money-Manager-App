package com.example.money_management_app.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.money_management_app.R;
import com.example.money_management_app.models.Friend;

import java.util.ArrayList;

public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.FriendVH> {

    private final ArrayList<Friend> friends;

    public FriendAdapter(ArrayList<Friend> friends) {
        this.friends = friends;
    }

    @NonNull @Override
    public FriendVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_friend, parent, false);
        return new FriendVH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull FriendVH h, int position) {
        Friend f = friends.get(position);
        h.name.setText(f.getName());
        h.total.setText("Total: ₹" + f.getTotal());

        // Nested RecyclerView for transactions
        h.txnList.setLayoutManager(new LinearLayoutManager(h.itemView.getContext()));
        h.txnList.setAdapter(new TransactionAdapter(f.getTransactions()));

        boolean expanded = f.isExpanded();
        h.txnContainer.setVisibility(expanded ? View.VISIBLE : View.GONE);
        h.expandIcon.setImageResource(expanded ? R.drawable.ic_expand_less_24 : R.drawable.ic_expand_more_24);

        View.OnClickListener toggle = v -> {
            f.setExpanded(!f.isExpanded());
            notifyItemChanged(position);
        };
        h.header.setOnClickListener(toggle);
        h.expandIcon.setOnClickListener(toggle);
    }

    @Override
    public int getItemCount() { return friends.size(); }

    static class FriendVH extends RecyclerView.ViewHolder {
        TextView name, total;
        ImageView expandIcon;
        View header, txnContainer;
        RecyclerView txnList;

        FriendVH(@NonNull View itemView) {
            super(itemView);
            header = itemView.findViewById(R.id.friendHeader);
            name = itemView.findViewById(R.id.friendName);
            total = itemView.findViewById(R.id.friendTotal);
            expandIcon = itemView.findViewById(R.id.expandIcon);
            txnContainer = itemView.findViewById(R.id.txnContainer);
            txnList = itemView.findViewById(R.id.rvTransactions);
        }
    }
}
